package com.cwk.gps.controller.user;

import com.alipay.easysdk.factory.Factory;
import com.cwk.context.BaseContext;
import com.cwk.gps.config.AliPayConfig;
import com.cwk.gps.mapper.OrderMapper;
import com.cwk.pojo.dto.AliPay;
import com.cwk.pojo.entity.Order;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;

import java.util.HashMap;
import java.util.Map;
/**
 * @Author
 * @Date Created in  2023/5/5 15:23
 * @DESCRIPTION:
 * @Version V1.0
 */
@RestController
@RequestMapping("alipay")
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class AliPayController {

    @Resource
    AliPayConfig aliPayConfig;
    @Autowired
    private OrderMapper orderMapper;


    private static final String GATEWAY_URL ="https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    private static final String FORMAT ="JSON";
    private static final String CHARSET ="utf-8";
    private static final String SIGN_TYPE ="RSA2";

    @GetMapping("/pay") // 前端路径参数格式?subject=xxx&traceNo=xxx&totalAmount=xxx
    public void pay(Long orderNo, HttpServletResponse httpResponse) throws Exception {
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, aliPayConfig.getAppId(),
                aliPayConfig.getAppPrivateKey(), FORMAT, CHARSET, aliPayConfig.getAlipayPublicKey(), SIGN_TYPE);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        request.setReturnUrl(aliPayConfig.getReturnUrl());
        Order order = orderMapper.getById(orderNo);
        request.setBizContent("{\"out_trade_no\":\"" + order.getId().toString() + "\","
                + "\"total_amount\":\"" + order.getPrice() + "\","
                + "\"subject\":\"" + order.getType() + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        log.info("---{}",request.getBizContent());
        String form = "";
        try {
            // 调用SDK生成表单
            form = alipayClient.pageExecute(request).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        // 直接将完整的表单html输出到页面
        httpResponse.getWriter().write(form);
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    @PostMapping("/notify")  // 注意这里必须是POST接口
    public String payNotify(HttpServletRequest request) throws Exception {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("=========支付宝异步回调========");

            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
            }

            String tradeNo = params.get("out_trade_no");
            String gmtPayment = params.get("gmt_payment");
            String alipayTradeNo = params.get("trade_no");
            // 支付宝验签
            if (Factory.Payment.Common().verifyNotify(params)) {
                // 验签通过
                System.out.println("交易名称: " + params.get("subject"));
                System.out.println("交易状态: " + params.get("trade_status"));
                System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
                System.out.println("商户订单号: " + params.get("out_trade_no"));
                System.out.println("交易金额: " + params.get("total_amount"));
                System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
                System.out.println("买家付款时间: " + params.get("gmt_payment"));
                System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));

                // 更新订单已支付的逻辑代码
                orderMapper.updateStatusWithId(Order.COMPLETE, Long.valueOf(tradeNo));
            }
        }
        return "success";
    }

}
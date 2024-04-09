package com.cwk.gps.controller.user;

import com.cwk.gps.constant.JwtClaimsConstant;
import com.cwk.gps.properties.JwtProperties;
import com.cwk.gps.result.Result;
import com.cwk.gps.service.UserService;
import com.cwk.gps.utils.JwtUtil;
import com.cwk.gps.utils.SendEmailUtils;
import com.cwk.gps.utils.ValidateCodeUtils;
import com.cwk.pojo.dto.UserLoginDTO;
import com.cwk.pojo.entity.User;
import com.cwk.pojo.vo.UserVo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 发送验证码
     *
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public Result sendMsg(@RequestBody User user, HttpSession session) {
        // 获取邮箱号
        String email = user.getEmail();
        if (email == null) {
            return Result.error("邮箱号为空！");
        }
        // 随机生成4位验证码
        String code = String.valueOf(ValidateCodeUtils.generateValidateCode(4));
        log.info("验证码为：{}", code);
        //发送验证码到邮箱
        SendEmailUtils.sendAuthCodeEmail(email,code);
        // 将验证码保存到session
         session.setAttribute("code", code);
        //将验证码缓存到Redis(有效时间为1分钟)
//        stringRedisTemplate.opsForValue().set("code",code,1, TimeUnit.MINUTES);
        //将邮箱保存到session
        session.setAttribute("email", email);
        return Result.success(code);
    }
    /**
     * 登录功能
     * @param userLoginDTO
     * @param session
     * @return
     */
    @PostMapping("/login")
    public Result<UserVo> login(@RequestBody UserLoginDTO userLoginDTO, HttpSession session) {
        // 获取邮箱号
        String email = userLoginDTO.getEmail();
        // 获取验证码
        String code = userLoginDTO.getCode();
        // 从session中获取验证码
         String codeInSession = (String) session.getAttribute("code");
        // 从缓存中获取验证码
//        String codeInRedis = stringRedisTemplate.opsForValue().get("code");
        //从session中获取请求验证码的邮箱号
        String emailInSession = (String) session.getAttribute("email");
        // 进行验证码比对
        if (codeInSession == null || emailInSession == null || !codeInSession.equals(code) || !emailInSession.equals(email)) {
            return Result.error("验证码错误");
        }
        // 判断该用户是否注册
        User user = userService.getOne(email);
        if (user == null) {
            // 用户还未注册，自动注册
            user = new User();
            user.setUsername("unknown" + email);
            user.setEmail(email);
            user.setPassword(userLoginDTO.getPassword());
            user.setLogo("E:\\softwork\\GPS\\src\\main\\resources\\static\\default.png");
            userService.save(user);
        }

        //为用户生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);

        UserVo userVo = UserVo.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .token(token)
                .build();

        //设置session
        session.setAttribute("user", user.getId());
        session.setMaxInactiveInterval(6*60*60);
        //删除验证码缓存
//        stringRedisTemplate.delete("code");
        return Result.success(userVo);
    }
    /**
     * 退出登录
     * @return
     */
    @PostMapping("/loginout")
    public Result<String> logout(HttpServletRequest request){
        //清除session
        request.getSession().removeAttribute("user");
        request.getSession().removeAttribute("email");
        // request.getSession().removeAttribute("code");
        return Result.success("退出登录成功");
    }

    public Result update(User user){
        userService.update(user);
        return Result.success();
    }


}

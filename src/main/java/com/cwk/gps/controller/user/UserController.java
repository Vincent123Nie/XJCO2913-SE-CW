package com.cwk.gps.controller.user;

import com.cwk.gps.constant.JwtClaimsConstant;
import com.cwk.gps.properties.JwtProperties;
import com.cwk.gps.result.Result;
import com.cwk.gps.service.RouteService;
import com.cwk.gps.service.UserService;
import com.cwk.gps.utils.JwtUtil;
import com.cwk.gps.utils.SendEmailUtils;
import com.cwk.gps.utils.ValidateCodeUtils;
import com.cwk.pojo.dto.UserLoginBypwDTO;
import com.cwk.pojo.dto.UserLoginDTO;
import com.cwk.pojo.dto.UserretriveDTO;
import com.cwk.pojo.entity.User;
import com.cwk.pojo.vo.TotalMotionVo;
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
    private RouteService routeService;

    @Autowired
    private JwtProperties jwtProperties;
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
        //将邮箱保存到session
        session.setAttribute("email", email);
        return Result.success(code);
    }
    /**
     * 登录功能 验证码登陆
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
            user.setEmail(email);
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
        return Result.success(userVo);
    }

    /**
     * 登录功能 密码登陆
     * @param userLoginBypwDTO
     * @param
     * @return
     */
    @PostMapping("/loginBypw")
    public Result loginBypw(@RequestBody UserLoginBypwDTO userLoginBypwDTO) {
        // 获取邮箱号
        String email = userLoginBypwDTO.getEmail();
        // 判断该用户是否注册
        User user = userService.getOne(email);
        if (user == null) {
            return Result.error("用户不存在");
        } else {
            if (!user.getPassword().equals(userLoginBypwDTO.getPassword())){
                return Result.error("密码错误");
            }
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
        return Result.success(userVo);
    }
    /**
     * 退出登录
     * @return
     */
    @PostMapping("/loginout")
    public Result<String> logout(HttpServletRequest request){
        return Result.success("退出登录成功");
    }

    /**
     * 找回密码
     * @param userretriveDTO
     * @param session
     * @return
     */
    @PostMapping("/retrieve")
    public Result forget(@RequestBody UserretriveDTO userretriveDTO, HttpSession session){
        // 获取邮箱号
        String email = userretriveDTO.getEmail();
        // 获取验证码
        String code = userretriveDTO.getCode();
        // 从session中获取验证码
        String codeInSession = (String) session.getAttribute("code");
        //从session中获取请求验证码的邮箱号
        String emailInSession = (String) session.getAttribute("email");
        // 进行验证码比对
        if (codeInSession == null || emailInSession == null || !codeInSession.equals(code) || !emailInSession.equals(email)) {
            return Result.error("验证码错误");
        }
        // 判断该用户是否注册
        User user = userService.getOne(email);
        if (user == null) {
            return Result.error("该用户不存在");
        }
        if (!userretriveDTO.getPassword().equals(userretriveDTO.getConfirmPassword())){
            return Result.error("两次密码不一致");
        }else{
            user.setPassword(userretriveDTO.getPassword());
            userService.update(user);
        }
        return Result.success("修改密码成功");
    }


    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @PutMapping
    public Result update(@RequestBody User user){
        log.info("用户信息：{}",user);
        userService.update(user);
        return Result.success();
    }

    /**
     * 查询我的运动信息
     *
     * @param
     * @return
     */
    @GetMapping("/motioninfo")
    public Result<TotalMotionVo> queryMyInfo() {
        TotalMotionVo totalMotionVo = routeService.queryUserMotionInfo();
        return Result.success(totalMotionVo);
    }
}

package com.ncusoft.app_platform.controller;

import com.ncusoft.app_platform.model.BackendUser;
import com.ncusoft.app_platform.model.Bo.UserLoginBo;
import com.ncusoft.app_platform.model.DevUser;
import com.ncusoft.app_platform.model.enumeration.UserType;
import com.ncusoft.app_platform.service.UserService;
import com.ncusoft.app_platform.utils.JsonResult;
import com.ncusoft.app_platform.utils.JwtUtil;
import com.ncusoft.app_platform.utils.ResultCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * 进行传入数据的验证和
 * @author monkJay
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @Resource
    ValueOperations<String ,Object> valueOperations;

    /**
     * 登录或注册
     * @param user devName-账号、devPassword-密码、userType-账户类型
     * @return 10002-参数为空，20002-密码错误，1-成功(data: devUser)
     */
    @PostMapping("/login")
    public JsonResult registerOrLogin(@RequestBody UserLoginBo user) {
        // 0. 判断用户名和密码不能为空
        if (StringUtils.isBlank(user.getDevName())|| StringUtils.isBlank(user.getDevPassword())) {
            return JsonResult.failure(ResultCode.PARAM_IS_BLANK);
        }

        if (user.getUserType().equals(UserType.DEVELOPER.getType())) {
            // 1. 判断用户名是否存在，如果存在就登录，如果不存在则注册
            boolean usernameIsExist = userService.queryUsernameIsExist(user.getDevName());
            DevUser user1;
            if (usernameIsExist) {
                // 1.1 登录
                String token = "";
                user1 = userService.queryDevUser(user);
                if (user1 == null) {
                    return JsonResult.failure(ResultCode.USER_LOGIN_ERROR);
                }
                // 利用JWT生成token
                token = JwtUtil.generateToken(user1.getDevPassword(), user1.getId());
                // 将生成的token的签证作为redis的键
                String key = token.split("\\.")[2];
                // 将token存入redis并设置过期时间为7小时
                valueOperations.set(key,token,7,TimeUnit.HOURS);
                return JsonResult.success(token);
            } else {
                // 1.2 注册
                userService.saveUser(user);
                return JsonResult.success();
            }
        } else if (user.getUserType().equals(UserType.ADMIN.getType())) {
            // 1. 判断用户名是否存在，如果存在就登录，如果不存在则注册
            boolean usernameIsExist = userService.queryAdminUsernameIsExist(user.getDevName());
            BackendUser user2;
            if (usernameIsExist) {
                // 1.1 登录
                String token = "";
                user2 = userService.queryAdminUser(user);
                if (user2 == null) {
                    return JsonResult.failure(ResultCode.USER_LOGIN_ERROR);
                }
                // 利用JWT生成token
                token = JwtUtil.generateToken(user2.getUserPassword(), user2.getId());
                // 将生成的token的签证作为redis的键
                String key = token.split("\\.")[2];
                // 将token存入redis并设置过期时间为7小时
                valueOperations.set(key, token, 7, TimeUnit.HOURS);
                return JsonResult.success(token);
            } else {
                // 1.2 注册
                userService.saveUser(user);
                return JsonResult.success();
            }
        }
        return JsonResult.failure(ResultCode.PARAM_IS_INVALID);
    }

    /**
     * 登录或注册
     * @param request 请求
     * @return 1-成功
     */
    @DeleteMapping("/login")
    public JsonResult logout(HttpServletRequest request){
        // 从http请求头中取出token
        String token = request.getHeader("Authorization");
        System.out.println(token);
        // 将生成的token的签证作为redis的键
        String key = token.split("\\.")[2];
        // 删除token
        valueOperations.getOperations().delete(key);
        return JsonResult.success();
    }
}
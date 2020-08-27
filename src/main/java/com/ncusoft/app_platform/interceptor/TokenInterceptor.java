package com.ncusoft.app_platform.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.ncusoft.app_platform.model.BackendUser;
import com.ncusoft.app_platform.model.DevUser;
import com.ncusoft.app_platform.selfannotation.LoginToken;
import com.ncusoft.app_platform.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author monkJay
 */
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Autowired
    ValueOperations<String ,Object> valueOperations;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从http请求头中取出token
        String token = request.getHeader("Authorization");

        //如果不是方法级别的请求，则直接通过
        if (!(handler instanceof HandlerMethod)){
            return true;
        }

        //利用java反射获取到请求的方法
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //如果方法添加了LoginToken注解
        if(method.isAnnotationPresent(LoginToken.class)){
            LoginToken loginToken = method.getAnnotation(LoginToken.class);
            if (loginToken.required()){
                //解决跨域问题
                response.addHeader("Access-Control-Allow-Origin", "*");
                response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
                response.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
                response.addHeader("Access-Control-Max-Age", "3600");

                //未登录
                if(token == null){
                    response.setStatus(401);
                    return false;
                }

                String token1 = "";
                // 账号密码登录的token
                if (token.split("\\.").length == 3){
                    String uid = "";
                    try {
                        uid = JWT.decode(token).getAudience().get(0);
                    }catch (JWTDecodeException jd){
                        // token取数据出问题
                        response.setStatus(401);
                        return false;
                    }

                    //获取用户信息
                    DevUser devUser = userService.findByDevId(Integer.parseInt(uid));

                    if (devUser == null) {
                        BackendUser backendUser = userService.findByBackId(Integer.parseInt(uid));
                        if (backendUser == null){
                            //用户不存在
                            response.setStatus(401);
                            return false;
                        }
                    }

                    // 将 uid 传递给 Controller
                    request.setAttribute("userId", uid);

                    token1 = (String)valueOperations.get(token.split("\\.")[2]);
                }else {
                    // 第三方登录的token
                    token1 = (String)valueOperations.get(token);
                }
                //token过期(登录过期)
                if (token1 == null){
                    response.setStatus(403);
                    return false;
                }
                //token错误(未登录)
                if (!StringUtils.equals(token1,token)){
                    response.setStatus(401);
                    return false;
                }
            }
            return true;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
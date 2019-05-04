package com.shop.interceptor;

import com.shop.controller.utils.HpsycheCookieUtils;
import com.shop.jedis.JedisClient;
import com.shop.pojo.OsUser;
import com.shop.uiils.JsonUtils;
import com.shop.uiils.OceanResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Hpsyche
 */
public class CommonInterceptor implements HandlerInterceptor {
    @Autowired
    private JedisClient jedisClient;
    @Value("${USER_COOKIE_INFO}")
    private String USER_COOKIE_INFO;
    @Value("${USER_INFO}")
    private String USER_INFO;
    @Value("${REDIS_USER_EXPIRE}")
    private Integer REDIS_USER_EXPIRE;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token= HpsycheCookieUtils.getCookie(request,USER_COOKIE_INFO);
        String resp = jedisClient.get(USER_INFO + ":" + token);
        String uri=request.getRequestURI();
        System.out.println(uri);
        if(uri.equals("/")||uri.indexOf("/login")>=0||uri.contains("index")){
            return true;
        }
        if(resp!=null){
            int expireTime =jedisClient.ttl(USER_INFO + ":" + token).intValue();
            if(expireTime<REDIS_USER_EXPIRE/2){
                jedisClient.set(USER_INFO + ":" + token,resp);
                jedisClient.expire(USER_INFO + ":" + token,REDIS_USER_EXPIRE);
            }
            return true;
        }else{
            response.sendRedirect("/login.html");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

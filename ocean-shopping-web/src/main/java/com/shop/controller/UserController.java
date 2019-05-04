package com.shop.controller;

import com.shop.controller.utils.HpsycheCookieUtils;
import com.shop.jedis.JedisClient;
import com.shop.pojo.OsUser;
import com.shop.service.UserService;
import com.shop.uiils.JsonUtils;
import com.shop.uiils.OceanResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * @author Hpsyche
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JedisClient jedisClient;
    @Value("${USER_INFO}")
    private String USER_INFO;
    @Value("${USER_COOKIE_INFO}")
    private String USER_COOKIE_INFO;
    @Value("${USER_COOKIE_EXPIRE}")
    private Integer USER_COOKIE_EXPIRE;
    @Value("${REDIS_USER_EXPIRE}")
    private Integer REDIS_USER_EXPIRE;

    /**
     * 查询是否存在用户名
     */
    @RequestMapping(value = "/user/findUserName/{username}", method = RequestMethod.GET)
    @ResponseBody
    public String findUserName(@PathVariable String username) {
        if (userService.findUsername(username)) {
            return "true";
        }
        return "false";
    }

    /**
     * 查询是否存在email
     */
    @RequestMapping(value = "/user/findEmail", method = RequestMethod.POST)
    @ResponseBody
    public String findEmail(String email) {
        if (userService.findEmail(email)) {
            return "true";
        }
        return "false";
    }

    /**
     * 注册
     */
    @RequestMapping(value = "/user/toRegister", method = RequestMethod.POST)
    @ResponseBody
    public String register(OsUser user,HttpServletRequest request,HttpServletResponse response) {
        try {
            OsUser getUser = userService.createUser(user);
            String myUUID=UUID.randomUUID().toString();
            jedisClient.set(USER_INFO+":"+ myUUID, JsonUtils.objectToJson(getUser));
            jedisClient.expire(USER_INFO+":"+myUUID,REDIS_USER_EXPIRE);
            HpsycheCookieUtils.setCookie(request,response,USER_COOKIE_INFO,myUUID,USER_COOKIE_EXPIRE);
            return "true";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "false";
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "false";
        }
    }

    /**
     * 登录
     */
    @RequestMapping(value = "/user/toLogin",method = RequestMethod.POST)
    @ResponseBody
    public OceanResult login(String loginMsg, String password, HttpServletRequest request, HttpServletResponse response){
        //登录
        OceanResult result = null;
        try {
            result = userService.login(loginMsg,password);
        } catch (NoSuchAlgorithmException|UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        OsUser user = (OsUser) result.getData();
        //添加用户信息入redis
        //todo：需要处理异常（若服务器出现问题）！
        String myUUID=UUID.randomUUID().toString();
        jedisClient.set(USER_INFO+":"+ myUUID, JsonUtils.objectToJson(user));
        jedisClient.expire(USER_INFO+":"+myUUID,REDIS_USER_EXPIRE);
        //todo:怎样让用户有事件操作时重新更新Cookie时间。
        //todo:token的使用
        HpsycheCookieUtils.setCookie(request,response,USER_COOKIE_INFO,myUUID,USER_COOKIE_EXPIRE);
        return result;
    }

    /**
     * 查询在redis是否存在用户信息
     */
    @RequestMapping(value = "/user/findUserInRedis",method = RequestMethod.GET)
    @ResponseBody
    public OceanResult findUserInRedis(@RequestParam(required = false) String cookie){
        System.out.println(cookie+":"+6666);
        String resp = jedisClient.get(USER_INFO + ":" + cookie);
        if(resp==null){
            return OceanResult.build(null,"用户已过期",404);
        }else {
            //将json串转为pojo对象
            OsUser user = JsonUtils.jsonToPojo(resp,OsUser.class);
            return OceanResult.build(user,"用户未过期",200);
        }
    }

    /**
     * 退出，从redis中删除数据
     */
    @RequestMapping(value = "/user/exit/{cookie}",method = RequestMethod.GET)
    @ResponseBody
    public OceanResult deleteUserInRedis(@PathVariable String cookie){
        jedisClient.expire(USER_INFO + ":" + cookie,0);
        return OceanResult.ok();
    }

    @RequestMapping(value = "/user/findUserByUserId",method = RequestMethod.GET)
    @ResponseBody
    public OceanResult findUserByUserId(HttpServletRequest request){
        //1.获取用户cookie
        String token=HpsycheCookieUtils.getCookie(request,USER_COOKIE_INFO);
        Long userId=userService.getUserId(token);
        //2.查询OsUser
        OsUser user=userService.getUserById(userId);
        user.setPassword("");
        user.setCreated(null);
        user.setUpdated(null);
        user.setId(null);
        return OceanResult.ok(user);
    }

    /**
     * 更新密码
     * @param originalPassword
     * @param updatePassword
     * @return
     */
    @RequestMapping(value = "/user/updatePassword",method =RequestMethod.POST)
    @ResponseBody
    public OceanResult updatePassword(@RequestParam String originalPassword,@RequestParam String updatePassword,HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String token=HpsycheCookieUtils.getCookie(request,USER_COOKIE_INFO);
        Long userId=userService.getUserId(token);
        OsUser user = userService.getUserById(userId);
        boolean flag=userService.judgeUserPassword(originalPassword,user.getPassword());
        if(!flag){
            return OceanResult.build(null,"密码错误",400);
        }
        userService.updatePassword(user,updatePassword);
        return OceanResult.ok();
    }

    @RequestMapping(value = "/user/forgetAndUpdatePassword",method = RequestMethod.GET)
    @ResponseBody
    public OceanResult forgetAndUpdatePassword(@RequestParam String userId,@RequestParam String password,HttpServletRequest request,HttpServletResponse response){
        OsUser user=userService.getUserById(Long.valueOf(userId));
        try {
            userService.updatePassword(user,password);
            String myUUID=UUID.randomUUID().toString();
            jedisClient.set(USER_INFO+":"+ myUUID, JsonUtils.objectToJson(user));
            jedisClient.expire(USER_INFO+":"+myUUID,REDIS_USER_EXPIRE);
            HpsycheCookieUtils.setCookie(request,response,USER_COOKIE_INFO,myUUID,USER_COOKIE_EXPIRE);
        } catch (Exception e) {
            return OceanResult.build(null,"修改密码错误",500);
        }
        return OceanResult.ok();
    }
}

package com.shop.service.impl;

import com.shop.jedis.JedisClient;
import com.shop.mapper.UserMapper;
import com.shop.pojo.OsUser;
import com.shop.service.UserService;
import com.shop.uiils.JsonUtils;
import com.shop.uiils.LoginMsg;
import com.shop.uiils.OceanResult;
import com.shop.uiils.SaltPasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

/**
 * @author Hpsyche
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JedisClient jedisClient;
    private String REDIS_USER_INFO="REDIS_USER_INFO";
    @Override
    public boolean findUsername(String username) {
        List<OsUser> users = userMapper.findUsername(username);
        return users.size()==0;
    }
    @Override
    public boolean findEmail(String email) {
        List<OsUser> users=userMapper.findEmail(email);
        return users.size()==0;
    }
    @Override
    public OsUser createUser(OsUser user) {
        user.setCreated(new Date());
        user.setUpdated(user.getCreated());
        user.setPassword(SaltPasswordUtils.generate(user.getPassword()));
        Long userId=userMapper.createUser(user);
        user.setPassword("");
        user.setId(userId);
        return user;
    }
    @Override
    public OceanResult login(String username, String password){
        List<OsUser> users = userMapper.findUserByMsg(username);
        if(users.size()==0){
            return OceanResult.build(null,"用户名或密码错误",400);
        }else {
            //获取user，并设置密码为空
            OsUser user=users.get(0);
            if(SaltPasswordUtils.verify(password,user.getPassword())){
                user.setPassword(null);
                return OceanResult.ok(user);
            }
            else {
                return OceanResult.build(null,"用户名或密码错误",400);
            }
        }
    }

    @Override
    public Long getUserId(String token){
        if(jedisClient.get(REDIS_USER_INFO+":"+token)==null){
            return null;
        }
        OsUser user= JsonUtils.jsonToPojo(jedisClient.get(REDIS_USER_INFO+":"+token), OsUser.class);
        return user.getId();
    }

    @Override
    public OsUser getUserById(Long userId) {
        return userMapper.getUserById(userId);
    }

    @Override
    public boolean judgeUserPassword(String originalPassword, String password){
        return SaltPasswordUtils.verify(originalPassword,password);
    }

    @Override
    public void updatePassword(OsUser user, String updatePassword){
        String passwordMd5=SaltPasswordUtils.generate(updatePassword);
        user.setPassword(passwordMd5);
        userMapper.updatePassword(user);
    }

    @Override
    public OsUser findByUsername(String username) {
        List<OsUser> users = userMapper.findUsername(username);
        if(users.size()>0) {
            return users.get(0);
        }
        return null;
    }


}

package com.shop.service;

import com.shop.pojo.OsUser;
import com.shop.uiils.OceanResult;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @author Hpsyche
 */
public interface UserService {
    /**
     * 是否存在此用户名
     * @param username
     * @return
     */
    boolean findUsername(String username);

    /**
     * 是否存在此email
     * @param email
     * @return
     */
    boolean findEmail(String email);

    /**
     * 创建用户
     * @param user
     */
    OsUser createUser(OsUser user) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    /**
     * 用户登录
     * @param loginMsg
     * @param password
     * @return
     */
    OceanResult login(String loginMsg, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException;

    /**
     * 获得用户id
     * @param token
     * @return
     */
    Long getUserId(String token);

    /**
     * 根据用户id查询用户
     * @param userId
     * @return
     */
    OsUser getUserById(Long userId);

    /**
     * 判断密码与用户密码是否相同
     * @param originalPassword
     * @param password
     * @return
     */
    boolean judgeUserPassword(String originalPassword, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    /**
     * 更新密码
     * @param user
     * @param updatePassword
     */
    void updatePassword(OsUser user, String updatePassword) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    /**
     * 通过用户名查找user
     * @param username
     * @return
     */
    OsUser findByUsername(String username);
}

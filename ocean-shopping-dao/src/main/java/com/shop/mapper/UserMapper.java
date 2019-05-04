package com.shop.mapper;

import com.shop.pojo.OsUser;
import com.shop.uiils.LoginMsg;

import java.util.List;

/**
 * @author Hpsyche
 */
public interface UserMapper {
    /**
     * 是否存在用户名
     * @param username
     * @return
     */
    List<OsUser> findUsername(String username);

    /**
     * 是否存在email
     * @param email
     * @return
     */
    List<OsUser> findEmail(String email);

    /**
     * 创建用户
     * @param user
     */
    Long createUser(OsUser user);

    /**
     * 通过信息查询用户
     * @param username
     * @return
     */
    List<OsUser> findUserByMsg(String username);

    /**
     * 根据id查询用户
     * @param userId
     * @return
     */
    OsUser getUserById(Long userId);

    /**
     * 更新密码
     * @param user
     */
    void updatePassword(OsUser user);
}

package com.shop.service;

import com.shop.pojo.OsUserAddress;

import java.util.List;

/**
 * @author Hpsyche
 */
public interface UserAddressService {
    /**
     * 插入用户收货地址信息
     * @param userAddress
     * @return
     */
    OsUserAddress insertUserAddress(OsUserAddress userAddress);

    /**
     * 查询用户的所有地址信息
     * @param userId
     * @return
     */
    List<OsUserAddress> findAllAddressByUserId(Long userId);

    /**
     * 根据地址id查询地址信息
     * @param addId
     * @return
     */
    OsUserAddress selectAddressByAddressId(Long addId);

    /**
     * 更新地址信息
     * @param address
     */
    OsUserAddress updateUserAddress(OsUserAddress address);

    /**
     * 删除地址信息
     * @param addId
     */
    void deleteUserAddress(long addId);
}

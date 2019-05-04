package com.shop.mapper;

import com.shop.pojo.OsUserAddress;

import java.util.List;

/**
 * @author Hpsyche
 */
public interface OsUserAddressMapper {
    /**
     * 插入地址信息
     * @param address
     */
    void insertAddress(OsUserAddress address);

    /**
     * 查询某用户所有地址
     * @param userId
     * @return
     */
    List<OsUserAddress> findAllByUserId(Long userId);

    /**
     * 根据id查询地址
     * @param addId
     * @return
     */
    OsUserAddress selectAddressByAddressId(Long addId);

    /**
     * 更新地址
     * @param userAddress
     */
    void updateAddress(OsUserAddress userAddress);

    /**
     * 删除地址信息
     * @param addId
     */
    void deleteAddress(long addId);
}

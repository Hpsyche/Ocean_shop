package com.shop.service.impl;

import com.shop.mapper.OsUserAddressMapper;
import com.shop.pojo.OsUserAddress;
import com.shop.service.AreaService;
import com.shop.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Hpsyche
 */
@Service
public class UserAddressServiceImpl implements UserAddressService {
    @Autowired
    private OsUserAddressMapper userAddressMapper;
    @Autowired
    private AreaService areaService;
    @Override
    public OsUserAddress insertUserAddress(OsUserAddress userAddress) {
        String area=areaService.selectAreaByAId(userAddress.getReceiverArea()).getName();
        String city=areaService.selectCityByCId(userAddress.getReceiverCity()).getName();
        String province=areaService.selectProvinceByPId(userAddress.getReceiverProvince()).getName();
        userAddress.setReceiverAddress(province+city+area+userAddress.getReceiverAddress());
        Date date=new Date();
        userAddress.setReceiverCreated(date);
        userAddress.setReceiverUpdated(date);
        userAddressMapper.insertAddress(userAddress);
        return userAddress;
    }

    @Override
    public List<OsUserAddress> findAllAddressByUserId(Long userId) {
        return userAddressMapper.findAllByUserId(userId);
    }

    @Override
    public OsUserAddress selectAddressByAddressId(Long addId) {
        return userAddressMapper.selectAddressByAddressId(addId);
    }

    @Override
    public OsUserAddress updateUserAddress(OsUserAddress userAddress) {
        String area=areaService.selectAreaByAId(userAddress.getReceiverArea()).getName();
        String city=areaService.selectCityByCId(userAddress.getReceiverCity()).getName();
        String province=areaService.selectProvinceByPId(userAddress.getReceiverProvince()).getName();
        userAddress.setReceiverAddress(province+city+area+userAddress.getReceiverAddress());
        userAddress.setReceiverUpdated(new Date());
        userAddressMapper.updateAddress(userAddress);
        return userAddress;
    }

    @Override
    public void deleteUserAddress(long addId) {
        userAddressMapper.deleteAddress(addId);
    }
}

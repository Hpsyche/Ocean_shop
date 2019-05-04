package com.shop.controller;

import com.shop.controller.utils.HpsycheCookieUtils;
import com.shop.pojo.OsUser;
import com.shop.pojo.OsUserAddress;
import com.shop.service.UserAddressService;
import com.shop.service.UserService;
import com.shop.uiils.JsonUtils;
import com.shop.uiils.OceanResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * @author Hpsyche
 */
@Controller
public class AddressController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserAddressService userAddressService;
    @Value("${USER_COOKIE_INFO}")
    private String USER_COOKIE_INFO;
    @RequestMapping(value = "/address/saveAddress",method = RequestMethod.POST)
    @ResponseBody
    public OceanResult saveAddress(OsUserAddress address, HttpServletRequest request){
        String token= HpsycheCookieUtils.getCookie(request,USER_COOKIE_INFO);
        Long userId=userService.getUserId(token);
        address.setAddressId(Long.valueOf(String.format("%010d",Math.abs(UUID.randomUUID().toString().hashCode()))));
        address.setUserId(userId);
        OsUserAddress resAddress=userAddressService.insertUserAddress(address);
        return OceanResult.build(resAddress,"保存成功",200);
    }
    @RequestMapping(value = "/address/getAllAddress",method = RequestMethod.GET)
    @ResponseBody
    public OceanResult getAllAddress( HttpServletRequest request){
        String token= HpsycheCookieUtils.getCookie(request,USER_COOKIE_INFO);
        Long userId=userService.getUserId(token);
        List<OsUserAddress> list=userAddressService.findAllAddressByUserId(userId);
        return OceanResult.build(list,"查询成功",200);
    }
    @RequestMapping(value = "/address/updateAddress",method = RequestMethod.POST)
    @ResponseBody
    public OceanResult selectAddress(OsUserAddress address){
        OsUserAddress resAddress=userAddressService.updateUserAddress(address);
        return OceanResult.build(resAddress,"更新成功",200);
    }
    @RequestMapping(value = "/address/deleteAddress/{addId}",method = RequestMethod.GET)
    @ResponseBody
    public OceanResult deleteAddress(@PathVariable Long addId){
        userAddressService.deleteUserAddress(addId);
        return OceanResult.ok();
    }
}

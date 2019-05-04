package com.shop.controller;

import com.shop.controller.utils.HpsycheCookieUtils;
import com.shop.jedis.JedisClient;
import com.shop.pojo.OsItem;
import com.shop.service.ItemService;
import com.shop.service.UserService;
import com.shop.uiils.JsonUtils;
import com.shop.uiils.OceanResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Hpsyche
 * 购物车Contoller
 */
@Controller
public class CartController {
    @Autowired
    private JedisClient jedisClient;
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;
    @Value("${USER_COOKIE_INFO}")
    private String USER_COOKIE_INFO;
    @Value("${USER_CART_INFO}")
    private String USER_CART_INFO;
    @Value("${USER_INFO}")
    private String REDIS_USER_INFO;
    /**
     * 添加商品入购物车（redis中）
     */
    @RequestMapping(value = "/cart/addItems/{itemId}/{num}",method = RequestMethod.GET)
    @ResponseBody
    public OceanResult addItemsToCart(@PathVariable Long itemId, @PathVariable Integer num, HttpServletRequest request){
        //1.获取用户cookie
        String token=HpsycheCookieUtils.getCookie(request,USER_COOKIE_INFO);
        Long userId=userService.getUserId(token);
        //2.判断用户是否过期
        if(jedisClient.get(REDIS_USER_INFO+":"+token)==null){
            return OceanResult.build(null,"用户已过期",400);
        }
        //3.查询itemId的商品信息
        OsItem item=itemService.selectItemById(itemId);
        //4.可利用OsItem，将商品数目封装入item中
        item.setNum(num);
        //5.redis利用hashSet，中key为userId，field为itemId,值为itemList
        //5.1先获取redis中是否已存在此商品
        String temp=jedisClient.hget(USER_CART_INFO+":"+userId,item.getId()+"");
        if(temp==null){
            //若不存在，设置入redis；
            jedisClient.hset(USER_CART_INFO+":"+userId,item.getId()+"", JsonUtils.objectToJson(item));
        }else {
            //若已存在，加num；
            OsItem redisItem = JsonUtils.jsonToPojo(temp, OsItem.class);
            redisItem.setNum(redisItem.getNum() + num);
            //重新存入redis中
            jedisClient.hset(USER_CART_INFO + ":" + userId, item.getId() + "", JsonUtils.objectToJson(redisItem));
        }
        return OceanResult.ok();
    }

    /**
     * 查询redis中某用户的购物车信息
     */
    @RequestMapping(value = "/cart/findItemsByUserId",method = RequestMethod.GET)
    @ResponseBody
    public OceanResult findItemsByUserId(HttpServletRequest request){
        //todo:可以利用redis的缓存机制
        String token=HpsycheCookieUtils.getCookie(request,USER_COOKIE_INFO);
        Long userId=userService.getUserId(token);
        if(userId==null){
            return OceanResult.build(null,"用户已过期",400);
        }
        Set<String> items= jedisClient.hgetAll(USER_CART_INFO + ":" + userId);
        List<OsItem> list=new ArrayList<>();
        for(String itemId:items){
            OsItem item=JsonUtils.jsonToPojo(jedisClient.hget(USER_CART_INFO + ":" + userId,itemId),OsItem.class);
            list.add(item);
        }
        return OceanResult.build(list,"成功",200);
    }

    @RequestMapping(value = "/cart/addItemNum/{itemId}",method = RequestMethod.GET)
    @ResponseBody
    public OceanResult addItemNum(@PathVariable long itemId,HttpServletRequest request){
        String token=HpsycheCookieUtils.getCookie(request,USER_COOKIE_INFO);
        Long userId=userService.getUserId(token);
        if(userId==null){
            return OceanResult.build(null,"用户已过期",400);
        }
        OsItem item=JsonUtils.jsonToPojo(jedisClient.hget(USER_CART_INFO + ":" + userId,itemId+""),OsItem.class);
        item.setNum(item.getNum()+1);
        jedisClient.hset(USER_CART_INFO + ":" + userId,itemId+"",JsonUtils.objectToJson(item));
        return OceanResult.ok();
    }
    @RequestMapping(value = "/cart/subItemNum/{itemId}",method = RequestMethod.GET)
    @ResponseBody
    public OceanResult subItemNum(@PathVariable long itemId,HttpServletRequest request){
        String token=HpsycheCookieUtils.getCookie(request,USER_COOKIE_INFO);
        Long userId=userService.getUserId(token);
        if(userId==null){
            return OceanResult.build(null,"用户已过期",400);
        }
        OsItem item=JsonUtils.jsonToPojo(jedisClient.hget(USER_CART_INFO + ":" + userId,itemId+""),OsItem.class);
        if(item.getNum()!=1) {
            item.setNum(item.getNum() - 1);
            jedisClient.hset(USER_CART_INFO + ":" + userId, itemId + "", JsonUtils.objectToJson(item));
        }
        return OceanResult.ok();
    }

    @RequestMapping(value = "/cart/deleteItems/{itemId}",method = RequestMethod.GET)
    @ResponseBody
    public OceanResult deleteItems(@PathVariable long itemId,HttpServletRequest request){
        String token=HpsycheCookieUtils.getCookie(request,USER_COOKIE_INFO);
        Long userId=userService.getUserId(token);
        if(userId==null){
            return OceanResult.build(null,"用户已过期",400);
        }
        jedisClient.hdel(USER_CART_INFO + ":" + userId,itemId+"");
        return OceanResult.ok();
    }
}

package com.shop.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.OrderItem;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.config.AlipayConfig;
import com.shop.controller.utils.CookieUtils;
import com.shop.controller.utils.HpsycheCookieUtils;
import com.shop.jedis.JedisClient;
import com.shop.mapper.OrderItemMapper;
import com.shop.pojo.*;
import com.shop.service.ItemService;
import com.shop.service.OrderItemService;
import com.shop.service.OrderService;
import com.shop.service.UserService;
import com.shop.uiils.JsonUtils;
import com.shop.uiils.MoneyUtils;
import com.shop.uiils.OceanResult;
import com.shop.uiils.OrderResult;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.sun.tools.internal.ws.wsdl.document.soap.SOAPUse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hpsyche
 */
@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private JedisClient jedisClient;
    @Autowired
    private UserService userService;
    @Value("${USER_COOKIE_INFO}")
    private String USER_COOKIE_INFO;
    @Value("${USER_CART_INFO}")
    private String USER_CART_INFO;
    @Value("${USER_INFO}")
    private String REDIS_USER_INFO;
    private static Pattern p = Pattern.compile("\"id\":\"\\d+\"");
    private static Pattern p2 = Pattern.compile("\"num\":\"\\d+\"");

    /**
     * 创建订单（购物车--订单页面）
     *
     * @param listMap
     * @param request
     * @return
     */
    @RequestMapping(value = "/order/createOrder", method = RequestMethod.POST)
    @ResponseBody
    public OceanResult createOrder(@RequestParam String listMap, HttpServletRequest request) {
        Matcher m = p.matcher(listMap);
        Matcher m2 = p2.matcher(listMap);
        List<String> itemIdList = new ArrayList();
        List<String> itemNumList = new ArrayList();
        while (m.find()) {
            String temp = m.group();
            itemIdList.add(temp.substring(6, temp.length() - 1));
        }
        while (m2.find()) {
            String temp = m2.group();
            itemNumList.add(temp.substring(7, temp.length() - 1));
        }
        //生产订单UUID
        String orderUUID = getOrderIdByUUID();
        //获取userId
        String token = HpsycheCookieUtils.getCookie(request, USER_COOKIE_INFO);
        OsUser user = JsonUtils.jsonToPojo(jedisClient.get(REDIS_USER_INFO + ":" + token), OsUser.class);
        Long userId = user.getId();
        long orderTotalPrice = 0L;
        //查询商品的价格
        for (int i = 0; i < itemIdList.size(); i++) {
            //获取商品单价
            Long price = itemService.selectPriceByItemId(Long.valueOf(itemIdList.get(i)));
            Integer itemNum = Integer.parseInt(itemNumList.get(i));
            //计算总价
            orderTotalPrice += price * itemNum;
            //生成订单详情表
            OsOrderItem orderItem = new OsOrderItem();
            orderItem.setItemId(itemIdList.get(i));
            orderItem.setOrderId(orderUUID);
            orderItem.setNum(Integer.valueOf(itemNumList.get(i)));
            orderItemService.createOrderItem(orderItem);
            //清除购物车redis数据
            jedisClient.hdel(USER_CART_INFO + ":" + userId, itemIdList.get(i) + "");
        }
        OsOrder order = new OsOrder();
        order.setOrderId(orderUUID);
        order.setPayment(orderTotalPrice + "");
        order.setUserId(Long.valueOf(userId));
        //获得UserId
        //生成订单表
        orderService.createOrder(order);
        return OceanResult.build(orderUUID, "订单生成成功", 200);
    }

    /**
     * 根据id查询订单及详情信息
     *
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/order/selectOrderById/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public OceanResult selectOrderById(@PathVariable Long orderId) {
        OrderResult result = orderService.selectOrderById(orderId);
        return OceanResult.build(result, "查询成功", 200);
    }

    @RequestMapping(value = "/order/findOrderByUserId/{orderStatus}", method = RequestMethod.GET)
    @ResponseBody
    public OceanResult findOrderByUserId(@PathVariable Integer orderStatus, HttpServletRequest request) {
        String token = HpsycheCookieUtils.getCookie(request, USER_COOKIE_INFO);
        Long userId = userService.getUserId(token);
        List<OsOrder> orderList = orderService.selectOrdersByUserIdAndStatus(userId, orderStatus);
        List<OrderResult> results = new ArrayList<>();
        for (OsOrder osOrder : orderList) {
            List<OsOrderItem> itemList = orderItemService.selectOrderItemsByOrderId(Long.valueOf(osOrder.getOrderId()));
            OrderResult result = new OrderResult();
            result.setOrder(osOrder);
            result.setOrderItems(itemList);
            results.add(result);
        }
        return OceanResult.build(results, "查询成功", 200);
    }

    @RequestMapping(value = "/order/updateOrder", method = RequestMethod.POST)
    @ResponseBody
    public String updateOrder(@RequestBody CreateAliOrder order, HttpServletRequest request, HttpServletResponse response) {
        HpsycheCookieUtils.setCookie(request, response, "ORDER_TEMP_COOKIE", order.getId() + "");
        orderService.updateOrder(order);
        String param = "";
        param+=order.getId()+":"+String.valueOf(order.getMoney());
        return param;
    }

    @RequestMapping(value = "/order/toAliPay",method = RequestMethod.POST)
    public String toAliPay(String orderId,String price){

        return "myAliPay.jsp?WIDout_trade_no="+orderId+"&WIDsubject=ocean_shop&WIDtotal_amount="
                + price + "&WIDbody=alipay";
    }


    @RequestMapping(value = "/order/updateOrderStatus")
    public String updateOrderStatus(HttpServletRequest request, HttpServletResponse response) {
        String id = HpsycheCookieUtils.getCookie(request, response, "ORDER_TEMP_COOKIE");
        orderService.updateOrderStatus(id);
        return "ok";
    }

    @RequestMapping(value = "/order/toBalance", method = RequestMethod.POST)
    @ResponseBody
    public OceanResult toBalance(@RequestParam String listMap, HttpServletRequest request) {
        listMap = listMap.replace("\\", "");
        Matcher m = p.matcher(listMap);
        Matcher m2 = p2.matcher(listMap);
        List<String> itemIdList = new ArrayList();
        List<String> itemNumList = new ArrayList();
        while (m.find()) {
            String temp = m.group();
            itemIdList.add(temp.substring(6, temp.length() - 1));
        }
        while (m2.find()) {
            String temp = m2.group();
            itemNumList.add(temp.substring(7, temp.length() - 1));
        }
        List<OsOrderItem> orderItems = new ArrayList<>();
        Long count = 0L;
        for (int i = 0; i < itemIdList.size(); i++) {
            String id = itemIdList.get(i);
            String num = itemNumList.get(i);
            OsItem item = itemService.selectItemById(Long.valueOf(id));
            OsOrderItem orderItem = new OsOrderItem();
            orderItem.setNum(Integer.valueOf(num));
            orderItem.setPrice(item.getPrice());
            orderItem.setId(String.valueOf(item.getId()));
            count += (Long.valueOf(num) * Long.valueOf(item.getPrice()));
            orderItem.setPicPath(item.getImage());
            orderItem.setTitle(item.getTitle());
            orderItems.add(orderItem);
        }
        OsOrder order = new OsOrder();
        order.setPayment(String.valueOf(count));
        OrderResult orderResult = new OrderResult();
        orderResult.setOrder(order);
        orderResult.setOrderItems(orderItems);
        return OceanResult.build(orderResult, "查询成功", 200);
    }


    /**
     * 生成唯一16位UUID
     *
     * @return
     */
    private String getOrderIdByUUID() {
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        return String.format("%015d", Math.abs(hashCodeV));
    }
}

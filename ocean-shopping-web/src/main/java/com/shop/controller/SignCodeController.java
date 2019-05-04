package com.shop.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.shop.controller.utils.HpsycheCookieUtils;
import com.shop.jedis.JedisClient;
import com.shop.pojo.OsUser;
import com.shop.service.UserService;
import com.shop.uiils.OceanResult;
import com.shop.uiils.SendmailUtil;
import com.shop.uiils.VerificationCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Hpsyche
 */
@Controller
public class SignCodeController {
    @Autowired
    private UserService userService;
    @Autowired
    private JedisClient jedisClient;
    @Value("${USER_VERIFY}")
    private String USER_VERIFY;
    @Value("${USER_COOKIE_INFO}")
    private String USER_COOKIE_INFO;
    @Value("${USER_VERIFY_EXPIRE}")
    private Integer USER_VERIFY_EXPIRE;
    @RequestMapping("/code/getCode")
    public void getCode(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        VerificationCode verificationCode = new VerificationCode();
        //获取验证码图片
        BufferedImage image = verificationCode.getImage();
        //获取验证码内容
        String text = verificationCode.getText();
        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuffer randomCode = new StringBuffer();
        randomCode.append(text);
        // 将验证码保存到Session中。
        HttpSession session = request.getSession();
        session.setAttribute("signcode", randomCode.toString());
        System.out.println("session-signcode==>" + randomCode.toString());
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos = response.getOutputStream();
        ImageIO.write(image, "jpeg", sos);
        sos.flush();
        sos.close();
    }

    /**
     * 验证图片验证码
     * @param request
     * @param signcode
     * @return
     */
    @RequestMapping("/code/check")
    @ResponseBody
    public OceanResult check(HttpServletRequest request, String username,String signcode) {
        HttpSession session = request.getSession();
        if (signcode==null||signcode.trim().equals("")) {
            return OceanResult.build(null,"验证码为空！",400);
        }
        String signcodeSession = (String) session.getAttribute("signcode");
        if(signcodeSession==null||signcodeSession.trim().equals("")){
            return OceanResult.build(null,"验证码失效，请树新页面！",400);
        }
        System.out.println("signcode==>"+signcode);
        System.out.println("signcodeSession==>"+signcodeSession);
        //验证的时候不区分大小写
        if (signcode.equalsIgnoreCase(signcodeSession)) {
            if(!userService.findUsername(username)) {
                OsUser user = userService.findByUsername(username);
                String email=user.getEmail();
                String verifyCode=VerificationCode.createVerifyCodes(6);
                //redis中存储验证码
                jedisClient.set(user.getId()+":"+USER_VERIFY,verifyCode);
                //设置过期时间600秒
                jedisClient.expire(user.getId()+":"+USER_VERIFY,USER_VERIFY_EXPIRE);
                //邮件主题
                String emailTitle = "【海大小栈】修改密码验证";
                //邮件内容
                String emailContent = "您正在【海大小栈】进行修改密码操作，您的验证码为：" + verifyCode + "，请于10分钟内完成验证！";
                try {
                    SendmailUtil.sendEmail(email,emailTitle,emailContent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return OceanResult.ok(user);
            }else{
                return OceanResult.build(null,"不存在此用户名！",400);
            }
        }
        return OceanResult.build(null,"验证码错误！",400);
    }

    @RequestMapping(value = "/code/verifyEmailCode",method = RequestMethod.GET)
    @ResponseBody
    public OceanResult verifyEmailCode(@RequestParam String verifyCode,@RequestParam String userId,HttpServletRequest request){
        //1.判断redis中是否存在用户的验证码
        System.out.println(userId);
        String verify = jedisClient.get(userId + ":" + USER_VERIFY);
        System.out.println(verify+":"+verifyCode);
        if(verify==null){
            return OceanResult.build(null,"验证码错误或已过期",404);
        }
        if(verify.equals(verifyCode)){
            return OceanResult.ok();
        }
        return OceanResult.build(null,"验证码错误",400);
    }
}

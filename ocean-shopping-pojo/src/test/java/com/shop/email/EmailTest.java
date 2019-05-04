package com.shop.email;


import com.shop.uiils.SendmailUtil;
import com.shop.uiils.VerificationCode;

/**
 * @author Hpsyche
 */
public class EmailTest {
    public static void main(String[] args) {
        //生成验证码
        String verifyCode=VerificationCode.createVerifyCodes(6);
        //邮件主题
        String emailTitle = "【海大小栈】修改密码验证";
        //邮件内容
        String emailContent = "您正在【海大小栈】进行修改密码操作，您的验证码为：" + verifyCode + "，请于10分钟内完成验证！";
        try {
            SendmailUtil.sendEmail("a460204715@163.com",emailTitle,emailContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

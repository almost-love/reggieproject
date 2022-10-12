package com.itheima.reggie.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Configuration
@Slf4j
public class SMSUtils {

//    @Autowired
//    private JavaMailSender mailSender;

//    public  void sendMessage(String phone,String code) throws MessagingException {
//        SimpleMailMessage message = new SimpleMailMessage();
//        //发送者邮箱
//        message.setFrom("481393117@qq.com");
//        //发送到的邮箱
//        message.setTo(phone);
//        message.setSubject("瑞吉外卖");
//        message.setText("您好!您的验证码为:" +code  + "请勿泄露");
//
//        mailSender.send(message);
//        log.info("邮件发送成功");
//    }

    @Autowired
    private JavaMailSenderImpl mailSender;
    public void sendMessage(String phone,String code) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        //标题
        helper.setSubject("您好,您的验证码为" + code);
        //邮件内容
        helper.setText("您好！，感谢使用瑞吉外卖。您的验证码为："+"<h2>"+code+"</h2>"+"千万不能告诉别人哦！",true);
        //邮件接受者
        helper.setTo(phone);
        //邮件发送者
        helper.setFrom("481393117@qq.com");
        mailSender.send(mimeMessage);
        log.info("邮件发送成功");

    }

}

package studio.banner.forumwebsite.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;

/**
 * @Author: Ljx
 * @Date: 2021/5/22 9:24
 * 发邮件类
 */
@Component
public class SendMail {
    @Autowired
    JavaMailSender javaMailSender;

    int max = 99999;
    int min = 10000;
    Random random = new Random();

    /**
     * 忘记密码时验证邮箱
     *
     * @param email 被发送的邮箱
     * @return String
     */
    public String sendSimpleMail(String email) {
        int s = random.nextInt(max - min + 1) + min;
        String code = String.valueOf(s);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("找回密码");
        message.setFrom("2952434583@qq.com");
        message.setTo(email);
        message.setSentDate(new Date());
        message.setText("你的验证码是：" + code);
        javaMailSender.send(message);
        return code;
    }

    /**
     * 生日祝福邮箱
     *
     * @param email   被的发送邮箱地址
     * @param content 邮箱内容
     * @return boolean
     */
    public boolean sendBlessMail(String email, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("生日快乐");
        message.setFrom("2952434583@qq.com");
        message.setTo(email);
        message.setSentDate(new Date());
        message.setText(content);
        javaMailSender.send(message);
        return true;
    }

    /**
     * 自动发送邮箱
     *
     * @param email    邮箱地址
     * @param username 被发送人
     * @return boolean
     */
    public boolean automaticMail(String email, String username) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(username + "生日快乐");
        message.setFrom("2952434583@qq.com");
        message.setTo(email);
        message.setSentDate(new Date());
        message.setText("今天是您的生日，祝您生日快乐！！！");
        javaMailSender.send(message);
        return true;
    }
}
    
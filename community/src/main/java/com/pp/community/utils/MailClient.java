package com.pp.community.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * TODO 邮件发送
 *
 * @author ss_419
 * @version 1.0
 * @date 2023/8/28 09:35
 */
@Component
public class MailClient {
    private static final Log  logger = LogFactory.getLog(MailClient.class);

    // 使用JavaMailSender发送邮件
    @Autowired
    private JavaMailSender javaMailSender;
    /**
     * 1、发件人
     * 2、收件人
     * 3、标题
     */
    @Value("${spring.mail.username}")
    private String from;// 发件人

    public void sendMail(String to,String subject,String content){
        try {
            // 构建MimeMessage
            MimeMessage message = javaMailSender.createMimeMessage();
            // 使用帮助类构建message
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);
            javaMailSender.send(helper.getMimeMessage());
        } catch (MessagingException e) {
            logger.error("发送邮件失败 ： " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

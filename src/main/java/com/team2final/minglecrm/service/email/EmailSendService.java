package com.team2final.minglecrm.service.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailSendService {

    @Value("${spring.mail.username}")
    private String emailFrom;
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void sendMail(String toEmail,
                         String subject,
                         String text,
                         String traceTag) throws MessagingException {

        Context context = new Context();
        context.setVariable("emailText", text);

        String content = templateEngine.process("emailDefaultTemplate", context);
        content += traceTag;

        MimeMessage mail = mailSender.createMimeMessage();
        MimeMessageHelper mailHelper = new MimeMessageHelper(mail, true, "UTF-8");

        mailHelper.setFrom(emailFrom);
        mailHelper.setTo(toEmail);
        mailHelper.setSubject(subject);
        mailHelper.setText(content, true);

        mailSender.send(mail);
    }

    public void sendVoucherMail(String toEmail,
                                String subject,
                                String voucher,
                                String traceTag) throws MessagingException {

        Context context = new Context();
        context.setVariable("voucher", voucher);

        String content = templateEngine.process("voucherTemplate", context);
        content += traceTag;


        MimeMessage mail = mailSender.createMimeMessage();
        MimeMessageHelper mailHelper = new MimeMessageHelper(mail, true, "UTF-8");

        mailHelper.setFrom(emailFrom);
        mailHelper.setTo(toEmail);
        mailHelper.setSubject(subject);
        mailHelper.setText(content, true);

        mailSender.send(mail);
    }


}

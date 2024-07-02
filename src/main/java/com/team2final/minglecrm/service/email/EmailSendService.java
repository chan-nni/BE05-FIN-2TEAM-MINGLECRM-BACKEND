package com.team2final.minglecrm.service.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmailSendService {

    private String loadEmailHtmlTemplate() throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/goldgrade.html");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    @Value("${spring.mail.username}")
    private String emailFrom;
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void sendMail(String toEmail,
                         String subject,
                         String content) throws MessagingException, IOException {

        Context context = new Context();
        context.setVariable("voucher", content);

        String htmlContent = templateEngine.process("goldgrade.html", context);

//        htmlTemplate += content;

        MimeMessage mail = mailSender.createMimeMessage();
        MimeMessageHelper mailHelper = new MimeMessageHelper(mail, true, "UTF-8");

        mailHelper.setFrom(emailFrom);
        mailHelper.setTo(toEmail);
        mailHelper.setSubject(subject);
        mailHelper.setText(htmlContent, true);

        mailSender.send(mail);
    }
}

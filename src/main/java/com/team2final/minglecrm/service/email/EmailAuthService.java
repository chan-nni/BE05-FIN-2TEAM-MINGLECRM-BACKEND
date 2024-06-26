package com.team2final.minglecrm.service.email;

import com.team2final.minglecrm.employee.domain.repository.EmployeeRepository;
import com.team2final.minglecrm.employee.domain.repository.dao.RedisDao;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class EmailAuthService {

    @Value("${spring.mail.username}")
    private String emailFrom;
    private final JavaMailSender mailSender;
    private final RedisDao redisDao;
    private final EmployeeRepository employeeRepository;

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 8;

    public static String authCodeGenerate() {
        SecureRandom random = new SecureRandom();
        StringBuilder key = new StringBuilder(CODE_LENGTH);

        for (int i=0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            key.append(CHARACTERS.charAt(index));
        }
        return key.toString();
    }

    // EmailSendService와 중복. 수정 필요
    public void sendMail(String toEmail,
                         String subject,
                         String text) throws MessagingException {

        MimeMessage mail = mailSender.createMimeMessage();
        MimeMessageHelper mailHelper = new MimeMessageHelper(mail, true, "UTF-8");

        mailHelper.setFrom(emailFrom);
        mailHelper.setTo(toEmail);
        mailHelper.setSubject(subject);
        mailHelper.setText(text, true);

        mailSender.send(mail);
    }

    public void SendSignUpAuthEmail(String toEmail) throws MessagingException {

        if (employeeRepository.existsByEmail(toEmail)) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String authCode = authCodeGenerate();

        sendMail(toEmail, "MingleCRM 회원가입 인증 이메일입니다!", authCode);

        redisDao.setValues(authCode, toEmail, Duration.ofMillis(60 * 5000L));

    }

    public void SendSignInAuthEmail(String toEmail) throws MessagingException {
        String authCode = authCodeGenerate();

        sendMail(toEmail, "MingleCRM 로그인 인증 이메일입니다!", authCode);

        redisDao.setValues(authCode, toEmail, Duration.ofMillis(60 * 5000L));
    }

    public Boolean AuthEmailCheck(String authCode, String toEmail) {

        String authEmail = redisDao.getValues(authCode);

        if (authEmail == null || !authEmail.equals(toEmail)) {
            return false;
        }

        redisDao.deleteValues(authCode);
        return true;
    }
}

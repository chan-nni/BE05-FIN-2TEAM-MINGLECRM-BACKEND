package com.team2final.minglecrm.registration.service;

import com.team2final.minglecrm.employee.domain.repository.EmployeeRepository;
import com.team2final.minglecrm.employee.dto.request.SignUpRequest;
import com.team2final.minglecrm.employee.service.EmployeeService;
import com.team2final.minglecrm.registration.domain.Registration;
import com.team2final.minglecrm.registration.domain.repository.RegistrationRepository;
import com.team2final.minglecrm.registration.domain.repository.RegistrationSearchRepository;
import com.team2final.minglecrm.registration.domain.type.RequestStatus;
import com.team2final.minglecrm.registration.dto.request.RegistrationSearchCondition;
import com.team2final.minglecrm.registration.dto.response.RegistrationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final RegistrationSearchRepository registrationSearchRepository;
    private final EmployeeService employeeService;
    private final JavaMailSender mailSender;

    private final PasswordEncoder passwordEncoder;

    public void sendRegistration(SignUpRequest requestDTO) {
        String encodedPassword = passwordEncoder.encode(requestDTO.getPassword());

        Registration registration = Registration
                .builder()
                .name(requestDTO.getName())
                .email(requestDTO.getEmail())
                .password(encodedPassword)
                .requestedRole(requestDTO.getAuthority())
                .status(RequestStatus.PENDING)
                .build();

        sendNotificationEmail(registration.getEmail());
        registrationRepository.save(registration);
    }

    private void sendNotificationEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("회원 가입 요청 승인 대기 중");

        String text = "안녕하세요,\n\n" +
                "회원 가입 요청이 접수되었습니다. 관리자의 승인이 완료되면 로그인하실 수 있습니다.\n\n" +
                "감사합니다.\n" +
                "mingle 팀 드림";

        message.setText(text);
        mailSender.send(message);
    }


    public Page<RegistrationResponse> getPendingStatusEmployList(Pageable pageable) {
        pageable = (pageable == null) ? PageRequest.of(0, 5) : pageable;
        Page<Registration> registrations = registrationRepository.findByStatusOrderByRegistrationRequestTimeDesc(RequestStatus.PENDING, pageable);
        return registrations.map(RegistrationResponse::from);
    }

    public Page<RegistrationResponse> getAllStatusEmployList(Pageable pageable) {
        pageable = (pageable == null) ? PageRequest.of(0, 5) : pageable;
        Page<Registration> registrations = registrationRepository.findAll(pageable);
        return registrations.map(RegistrationResponse::from);
    }



    @Transactional
    public void approvedEmployeeRequest(SignUpRequest signUpRequest) {
        String userEmail = getMangerEmail();
        Registration registration = getRequestedEmployeeEmail(signUpRequest);
        sendApprovalNotificationEmail(registration.getEmail());
        registration.approveChangeStatus(userEmail);
        employeeService.signUp(signUpRequest);
    }

    @Transactional
    public void rejectEmployeeRequest(SignUpRequest signUpRequest) {
        String userEmail = getMangerEmail();
        Registration registration = getRequestedEmployeeEmail(signUpRequest);
        sendRejectNotificationEmail(registration.getEmail());
        registration.rejectedChangeStatus(userEmail);
    }

    private static String getMangerEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    private Registration getRequestedEmployeeEmail(SignUpRequest signUpRequest) {
        String email = signUpRequest.getEmail();
        return registrationRepository.findByEmail(email);
    }

    public Long getPendingCount() {
        return registrationRepository.countByStatus(RequestStatus.PENDING);
    }

    public Page<RegistrationResponse> searchRegistration(RegistrationSearchCondition condition, Pageable pageable) {
        return registrationSearchRepository.search(condition, pageable);
    }

    private void sendApprovalNotificationEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("회원 가입 요청 승인 완료");

        String text = "안녕하세요,\n\n" +
                "회원 가입 요청이 승인되었습니다. 이제 로그인하여 서비스를 이용하실 수 있습니다.\n\n" +
                "감사합니다.\n" +
                "mingle 팀 드림";

        message.setText(text);
        mailSender.send(message);
    }

    private void sendRejectNotificationEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("회원 가입 요청 거절");

        String text = "안녕하세요,\n\n" +
                "회원 가입 요청이 거절되었습니다. 문의 사항이 있으시면 고객 지원팀에 연락해 주시기 바랍니다.\n\n" +
                "감사합니다.\n" +
                "mingle 팀 드림";

        message.setText(text);
        mailSender.send(message);
    }

}

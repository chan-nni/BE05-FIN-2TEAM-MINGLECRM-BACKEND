package com.team2final.minglecrm.service.inquiry;

import com.team2final.minglecrm.controller.inquiry.request.InquiryReplyRequest;
import com.team2final.minglecrm.controller.inquiry.response.InquiryDetailResponse;
import com.team2final.minglecrm.controller.inquiry.response.InquiryReplyResponse;
import com.team2final.minglecrm.controller.inquiry.response.InquiryResponse;
import com.team2final.minglecrm.entity.employee.Employee;
import com.team2final.minglecrm.entity.inquiry.Inquiry;
import com.team2final.minglecrm.entity.inquiry.InquiryReply;
import com.team2final.minglecrm.persistence.repository.employee.EmployeeRepository;
import com.team2final.minglecrm.persistence.repository.inquiry.InquiryReplyRepository;
import com.team2final.minglecrm.persistence.repository.inquiry.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;
    private final InquiryReplyRepository inquiryReplyRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public List<InquiryResponse> getAllInquiries() {
        List<Inquiry> inquiries = inquiryRepository.findAll();
        return inquiries.stream().map(inquiry -> {
            Optional<InquiryReply> inquiryReplyOptional = inquiryReplyRepository.findByInquiryId(inquiry.getId());
            InquiryReply inquiryReply = inquiryReplyOptional.orElse(null); // 답변 없으면 null
            return convertToDTO(inquiry, inquiryReply);
        }).collect(Collectors.toList());
    }

    @Transactional
    public InquiryDetailResponse getInquiryById(Long inquiryId) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new RuntimeException("문의를 찾을 수 없습니다."));

        InquiryReply reply = inquiryReplyRepository.findByInquiryId(inquiryId).orElse(null);

        InquiryResponse inquiryResponse = convertToDTO(inquiry);
        InquiryReplyResponse inquiryReplyResponse = (reply != null) ? convertToDTO(reply) : null;
        // 문의에 답변 존재하는지 확인 -> 없으면 null

        return InquiryDetailResponse.builder()
                .inquiryResponse(inquiryResponse)
                .inquiryReplyResponse(inquiryReplyResponse)
                .build();
    }

    @Transactional
    public InquiryReplyResponse replyToInquiry(InquiryReplyRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        Employee employee = employeeRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("로그인한 사용자를 찾을 수 없습니다."));

        Inquiry inquiry = inquiryRepository.findById(request.getInquiryId())
                .orElseThrow(() -> new RuntimeException("문의를 찾을 수 없습니다."));

        InquiryReply inquiryReply = InquiryReply.builder()
                .inquiry(inquiry)
                .employee(employee)
                .reply(request.getReply())
                .date(LocalDateTime.now())
                .build();

        InquiryReply saveReply = inquiryReplyRepository.save(inquiryReply);

        return convertToDTO(saveReply);
    }

    @Transactional
    public InquiryReplyResponse updateInquiryReply(Long inquiryReplyId, String updatedReply) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        Employee employee = employeeRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("로그인한 사용자를 찾을 수 없습니다."));

        InquiryReply inquiryReply = inquiryReplyRepository.findById(inquiryReplyId)
                .orElseThrow(() -> new RuntimeException("답변을 찾을 수 없습니다."));

        // 엔티티 메서드 호출
        inquiryReply.updateReply(updatedReply, LocalDateTime.now(), employee);

        return convertToDTO(inquiryReply);
    }


    private InquiryResponse convertToDTO(Inquiry inquiry, InquiryReply inquiryReply) {
        String employName = (inquiryReply != null) ? inquiryReply.getEmployee().getName() : null;
        // 답변이 null이 아닐 경우만 실행

        return InquiryResponse.builder()
                .customerName(inquiry.getCustomer().getName())
                .customerPhone(inquiry.getCustomer().getPhone())
                .date(inquiry.getDate())
                .type(inquiry.getType())
                .employName(employName)
                .inquiryTitle(inquiry.getInquiryTitle())
                .inquiryContent(inquiry.getInquiryContent())
                .build();
    }

    private InquiryResponse convertToDTO(Inquiry inquiry) {
        return InquiryResponse.builder()
                .customerName(inquiry.getCustomer().getName())
                .customerPhone(inquiry.getCustomer().getPhone())
                .date(inquiry.getDate())
                .type(inquiry.getType())
                .inquiryTitle(inquiry.getInquiryTitle())
                .inquiryContent(inquiry.getInquiryContent())
                .build();
    }

    private InquiryReplyResponse convertToDTO(InquiryReply inquiryReply) {
        return InquiryReplyResponse.builder()
                .id(inquiryReply.getId())
                .inquiryId(inquiryReply.getInquiry().getId())
                .email(inquiryReply.getEmployee().getEmail())
                .reply(inquiryReply.getReply())
                .date(inquiryReply.getDate())
                .build();
    }
}

package com.team2final.minglecrm.inquiry.service;

import com.team2final.minglecrm.employee.domain.Employee;
import com.team2final.minglecrm.employee.domain.repository.EmployeeRepository;
import com.team2final.minglecrm.inquiry.domain.ActionStatus;
import com.team2final.minglecrm.inquiry.domain.Inquiry;
import com.team2final.minglecrm.inquiry.domain.InquiryAction;
import com.team2final.minglecrm.inquiry.domain.InquiryReply;
import com.team2final.minglecrm.inquiry.domain.repository.InquiryActionRepository;
import com.team2final.minglecrm.inquiry.domain.repository.InquiryReplyRepository;
import com.team2final.minglecrm.inquiry.domain.repository.InquiryRepository;
import com.team2final.minglecrm.inquiry.dto.request.InquiryActionRequest;
import com.team2final.minglecrm.inquiry.dto.request.InquiryReplyRequest;
import com.team2final.minglecrm.inquiry.dto.response.InquiryActionResponse;
import com.team2final.minglecrm.inquiry.dto.response.InquiryDetailResponse;
import com.team2final.minglecrm.inquiry.dto.response.InquiryReplyResponse;
import com.team2final.minglecrm.inquiry.dto.response.InquiryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;
    private final InquiryReplyRepository inquiryReplyRepository;
    private final InquiryActionRepository inquiryActionRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public Page<InquiryResponse> getAllInquiries(Pageable pageable) {
        Page<Inquiry> inquiries = inquiryRepository.findAll(pageable);
        return inquiries.map(inquiry -> {
            InquiryReply inquiryReply = inquiryReplyRepository.findByInquiryId(inquiry.getId());
            InquiryAction inquiryAction = inquiryActionRepository.findByInquiryId(inquiry.getId());

            return convertToDTO(inquiry, inquiryReply, inquiryAction);
        });
    }


    @Transactional
    public Page<InquiryResponse> getUnansweredInquiries(Pageable pageable) {
        Page<Inquiry> unansweredInquiries = inquiryRepository.findUnansweredInquiries(pageable);
        return unansweredInquiries.map(inquiry -> {
            InquiryAction inquiryAction = inquiryActionRepository.findByInquiryId(inquiry.getId());
            return convertToDTO(inquiry, null, inquiryAction); // 답변이 없는 문의만 조회 - inquiryReply는 항상 null
        });
    }

    @Transactional
    public Page<InquiryResponse> getAnsweredInquiries(Pageable pageable) {
        Page<Inquiry> answeredInquiries = inquiryRepository.findInquiriesWithReply(pageable);

        Map<Long, InquiryReply> inquiryReplyMap = inquiryReplyRepository.findAll().stream()
                .collect(Collectors.toMap(ir -> ir.getInquiry().getId(), ir -> ir));

        return answeredInquiries.map(inquiry -> {
            InquiryReply inquiryReply = inquiryReplyMap.get(inquiry.getId());
            InquiryAction inquiryAction = inquiryActionRepository.findByInquiryId(inquiry.getId());

            return convertToDTO(inquiry, inquiryReply, inquiryAction);
        });
    }

    @Transactional
    public Page<InquiryResponse> getInquiriesWithAction(Pageable pageable) {
        Page<Inquiry> inquiriesWithAction = inquiryRepository.findInquiriesWithAction(pageable);
        return inquiriesWithAction.map(inquiry -> {
            InquiryReply inquiryReply = inquiryReplyRepository.findByInquiryId(inquiry.getId());
            InquiryAction inquiryAction = inquiryActionRepository.findByInquiryId(inquiry.getId());

            return convertToDTO(inquiry, inquiryReply, inquiryAction);
        });
    }

    @Transactional
    public Page<InquiryResponse> getInquiriesWithoutAction(Pageable pageable) {
        Page<Inquiry> inquiriesWithoutAction = inquiryRepository.findInquiriesWithoutAction(pageable);
        return inquiriesWithoutAction.map(inquiry -> {
            InquiryReply inquiryReply = inquiryReplyRepository.findByInquiryId(inquiry.getId());

            return convertToDTO(inquiry, inquiryReply, null);
        });
    }

    @Transactional
    public InquiryDetailResponse getInquiryById(Long inquiryId) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new IllegalArgumentException("문의를 찾을 수 없습니다."));

        InquiryReply reply = inquiryReplyRepository.findByInquiryId(inquiryId);
        InquiryAction action = inquiryActionRepository.findByInquiryId(inquiryId);

        InquiryResponse inquiryResponse = convertToDTO(inquiry, reply, action);
        InquiryReplyResponse inquiryReplyResponse = (reply != null) ? convertToDTO(reply) : null;
        InquiryActionResponse inquiryActionResponse = (action != null) ? convertToActionDTO(action) : null;
        // 문의에 답변 존재하는지 확인 -> 없으면 null

        return InquiryDetailResponse.builder()
                .inquiryResponse(inquiryResponse)
                .inquiryReplyResponse(inquiryReplyResponse)
                .inquiryActionResponse(inquiryActionResponse)
                .build();
    }

    @Transactional
    public InquiryReplyResponse replyToInquiry(InquiryReplyRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        Employee employee = employeeRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("로그인한 사용자를 찾을 수 없습니다."));

        Inquiry inquiry = inquiryRepository.findById(request.getInquiryId())
                .orElseThrow(() -> new IllegalArgumentException("문의를 찾을 수 없습니다."));

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
                .orElseThrow(() -> new IllegalStateException("로그인한 사용자를 찾을 수 없습니다."));

        InquiryReply inquiryReply = inquiryReplyRepository.findById(inquiryReplyId)
                .orElseThrow(() -> new IllegalArgumentException("답변을 찾을 수 없습니다."));

        // 엔티티 메서드 호출
        inquiryReply.updateReply(updatedReply, LocalDateTime.now(), employee);

        return convertToDTO(inquiryReply);
    }

    @Transactional
    public InquiryActionResponse actionToInquiry(InquiryActionRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        Employee employee = employeeRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("로그인한 사용자를 찾을 수 없습니다."));

        Inquiry inquiry = inquiryRepository.findById(request.getInquiryId())
                .orElseThrow(() -> new IllegalArgumentException("문의를 찾을 수 없습니다."));

        System.out.println("Received action status: " + request.getActionStatus());  // 받은 값 로그

        InquiryAction inquiryAction = InquiryAction.builder()
                .inquiry(inquiry)
                .employee(employee)
                .actionStatus(ActionStatus.fromValue(request.getActionStatus()))
                .actionContent(request.getActionContent())
                .date(LocalDateTime.now())
                .build();

        InquiryAction saveAction = inquiryActionRepository.save(inquiryAction);

        return convertToActionDTO(saveAction);
    }

    @Transactional
    public InquiryActionResponse updateInquiryAction(Long inquiryActionId, String updateAction, ActionStatus actionStatus) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        Employee employee = employeeRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("로그인한 사용자를 찾을 수 없습니다."));

        System.out.println("Service layer: Updating action with ID: " + inquiryActionId);
        System.out.println("Service layer: Received action status: " + actionStatus);
        System.out.println("Service layer: Received action content: " + updateAction);

        InquiryAction inquiryAction = inquiryActionRepository.findById(inquiryActionId)
                .orElseThrow(() -> new IllegalArgumentException("조치 내용을 찾을 수 없습니다."));

        if (actionStatus == null) {
            actionStatus = inquiryAction.getActionStatus(); // 기존 상태 유지
        }

        System.out.println("Updating action: " + updateAction);  // 추가 로그
        System.out.println("Updating action status: " + actionStatus);  // 추가 로그

        inquiryAction.updateAction(updateAction, LocalDateTime.now(), employee, actionStatus);

        InquiryActionResponse response = convertToActionDTO(inquiryAction);

        System.out.println("Updated InquiryAction:");
        System.out.println("ID: " + response.getId());
        System.out.println("Action Content: " + response.getActionContent());
        System.out.println("Action Status: " + response.getActionStatus());

        return response;
    }

    @Transactional
    public Page<InquiryResponse> getInquiriesByCustomerId(Long customerId, Pageable pageable) {
        Page<Inquiry> inquiries = inquiryRepository.findByCustomerId(customerId, pageable);

        return inquiries.map(inquiry -> {
            InquiryReply reply = inquiryReplyRepository.findByInquiryId(inquiry.getId());
            InquiryAction action = inquiryActionRepository.findByInquiryId(inquiry.getId());

            return convertToDTO(inquiry, reply, action);
        });
    }

    @Transactional
    public InquiryDetailResponse getInquiryDetailByCustomerId(Long customerId, Long inquiryId) {
        Inquiry inquiry = inquiryRepository.findByIdAndCustomerId(inquiryId, customerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 고객의 문의를 찾을 수 없습니다."));

        InquiryReply reply = inquiryReplyRepository.findByInquiryId(inquiryId);
        InquiryAction action = inquiryActionRepository.findByInquiryId(inquiryId);

        InquiryResponse inquiryResponse = convertToDTO(inquiry, reply, action);
        InquiryReplyResponse inquiryReplyResponse = reply != null ? convertToDTO(reply) : null;
        InquiryActionResponse inquiryActionResponse = action != null ? convertToActionDTO(action) : null;

        return InquiryDetailResponse.builder()
                .inquiryResponse(inquiryResponse)
                .inquiryReplyResponse(inquiryReplyResponse)
                .inquiryActionResponse(inquiryActionResponse)
                .build();
    }

    @Transactional
    public Page<InquiryResponse> searchInquiries(String keyword, LocalDate startDate, LocalDate endDate, Pageable pageable) {

        LocalDateTime startDateTime = startDate.atStartOfDay(); // LocalDate 객체를 LocalDateTime 객체로 변환
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        Page<Inquiry> inquiries = inquiryRepository.searchByKeyword(keyword, startDateTime, endDateTime, pageable);
        return inquiries.map(inquiry -> {

            InquiryReply reply = inquiryReplyRepository.findByInquiryId(inquiry.getId());
            InquiryAction action = inquiryActionRepository.findByInquiryId(inquiry.getId());

            return convertToDTO(inquiry, reply, action);
        });
    }
    public Page<InquiryResponse> searchInquiries(String keyword, String customerName, String customerPhone, String inquiryTitle, String inquiryContent, LocalDateTime startDate, LocalDateTime endDate, String type, Boolean isReply, ActionStatus actionStatus, Pageable pageable) {
        Page<Inquiry> inquiries = inquiryRepository.searchByCondition(keyword, customerName, customerPhone, inquiryTitle, inquiryContent, startDate, endDate, type, isReply, actionStatus, pageable);
        return inquiries.map(inquiry -> {
            InquiryReply reply = inquiryReplyRepository.findByInquiryId(inquiry.getId());
            InquiryAction action = inquiryActionRepository.findByInquiryId(inquiry.getId());
            return convertToDTO(inquiry, reply, action);
        });
    }

    private InquiryResponse convertToDTO(Inquiry inquiry, InquiryReply inquiryReply, InquiryAction inquiryAction) {
        String employName = null;
        if (inquiryReply != null && inquiryReply.getEmployee() != null) {
            employName = inquiryReply.getEmployee().getName();
        }
        boolean isReply = (inquiryReply != null); // 답변이 있으면 true

        ActionStatus actionStatus = (inquiryAction != null) ? inquiryAction.getActionStatus() : null;


        return InquiryResponse.builder()
                .id(inquiry.getId())
                .customerName(inquiry.getCustomer().getName())
                .customerPhone(inquiry.getCustomer().getPhone())
                .date(inquiry.getDate())
                .type(inquiry.getType())
                .employName(employName)
                .inquiryTitle(inquiry.getInquiryTitle())
                .inquiryContent(inquiry.getInquiryContent())
                .isReply(isReply)
                .actionStatus(actionStatus)
                .build();
    }

    private InquiryReplyResponse convertToDTO(InquiryReply inquiryReply) {
        return InquiryReplyResponse.builder()
                .id(inquiryReply.getId())
                .inquiryId(inquiryReply.getInquiry().getId())
                .email(inquiryReply.getEmployee() != null ? inquiryReply.getEmployee().getEmail() : null)
                .name(inquiryReply.getEmployee() != null ? inquiryReply.getEmployee().getName() : null)
                .reply(inquiryReply.getReply())
                .date(inquiryReply.getDate())
                .build();
    }

    private InquiryActionResponse convertToActionDTO(InquiryAction inquiryAction) {
        System.out.println("Action Status: " + inquiryAction.getActionStatus());  // 로그 추가

        return InquiryActionResponse.builder()
                .id(inquiryAction.getId())
                .inquiryId(inquiryAction.getInquiry().getId())
                .actionContent(inquiryAction.getActionContent())
                .actionStatus(inquiryAction.getActionStatus().getValue())
                .email(inquiryAction.getEmployee() != null ? inquiryAction.getEmployee().getEmail() : null)
                .name(inquiryAction.getEmployee() != null ? inquiryAction.getEmployee().getName() : null)
                .date(inquiryAction.getDate())
                .build();
    }
}

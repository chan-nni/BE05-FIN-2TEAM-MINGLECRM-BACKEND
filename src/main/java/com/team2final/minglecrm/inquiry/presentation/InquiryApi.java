package com.team2final.minglecrm.inquiry.presentation;

import com.team2final.minglecrm.common.exception.ResultResponse;
import com.team2final.minglecrm.inquiry.domain.ActionStatus;
import com.team2final.minglecrm.inquiry.dto.request.InquiryActionRequest;
import com.team2final.minglecrm.inquiry.dto.request.InquiryReplyRequest;
import com.team2final.minglecrm.inquiry.dto.request.UpdateInquiryActionRequest;
import com.team2final.minglecrm.inquiry.dto.request.UpdateInquiryReplyRequest;
import com.team2final.minglecrm.inquiry.dto.response.InquiryActionResponse;
import com.team2final.minglecrm.inquiry.dto.response.InquiryDetailResponse;
import com.team2final.minglecrm.inquiry.dto.response.InquiryReplyResponse;
import com.team2final.minglecrm.inquiry.dto.response.InquiryResponse;
import com.team2final.minglecrm.inquiry.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/inquiries")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class InquiryApi {

    private final InquiryService inquiryService;

    @GetMapping
    public ResponseEntity<ResultResponse<Page<InquiryResponse>>> getAllInquiries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InquiryResponse> inquiries = inquiryService.getAllInquiries(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "문의 전체 목록 반환 성공", inquiries));
    }

    @GetMapping("/{inquiryId}")
    public ResponseEntity<ResultResponse<InquiryDetailResponse>> getInquiryById(@PathVariable Long inquiryId) {
        InquiryDetailResponse inquiryDetailResponse = inquiryService.getInquiryById(inquiryId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "문의 개별 조회 반환 성공", inquiryDetailResponse));
    }

    @GetMapping("/unanswered")
    public ResponseEntity<ResultResponse<Page<InquiryResponse>>> getUnansweredInquiries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InquiryResponse> unansweredInquiries = inquiryService.getUnansweredInquiries(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "답변 없는 문의 목록 반환 성공", unansweredInquiries));
    }

    @GetMapping("/answered")
    public ResponseEntity<ResultResponse<Page<InquiryResponse>>> getAnsweredInquiries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InquiryResponse> answeredInquiries = inquiryService.getAnsweredInquiries(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "답변 있는 문의 목록 반환 성공", answeredInquiries));
    }

    @GetMapping("/with-action")
    public ResponseEntity<ResultResponse<Page<InquiryResponse>>> getInquiriesWithAction(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InquiryResponse> inquiriesWithAction = inquiryService.getInquiriesWithAction(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "조치 내용 있는 문의 목록 반환 성공", inquiriesWithAction));
    }

    @GetMapping("/without-action")
    public ResponseEntity<ResultResponse<Page<InquiryResponse>>> getInquiriesWithoutAction(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InquiryResponse> inquiriesWithoutAction = inquiryService.getInquiriesWithoutAction(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "조치 내용 없는 문의 목록 반환 성공", inquiriesWithoutAction));
    }

    @PostMapping("/reply")
//    @PreAuthorize("hasRole('CONSULTANT')")
    public ResponseEntity<ResultResponse<InquiryReplyResponse>> replyToInquiry(@RequestBody InquiryReplyRequest request) {
        InquiryReplyResponse replyResponse = inquiryService.replyToInquiry(request);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "문의 답변 등록 성공", replyResponse));
    }

    @PostMapping("/reply/{inquiryReplyId}")
//    @PreAuthorize("hasRole('CONSULTANT')")
    public ResponseEntity<ResultResponse<InquiryReplyResponse>> updateInquiryReply(@PathVariable Long inquiryReplyId,
                                                                   @RequestBody UpdateInquiryReplyRequest request) {
        InquiryReplyResponse updatedReply = inquiryService.updateInquiryReply(inquiryReplyId, request.getUpdatedReply());
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "문의 답변 수정 성공", updatedReply));
    }

    @PostMapping("/action")
//    @PreAuthorize("hasRole('CONSULTANT')")
    public ResponseEntity<ResultResponse<InquiryActionResponse>> actionToInquiry(@RequestBody InquiryActionRequest request) {
        System.out.println("Received action status: " + request.getActionStatus());  // 추가된 디버깅 로그
        InquiryActionResponse actionResponse = inquiryService.actionToInquiry(request);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "조치 내용 등록 성공", actionResponse));
    }

    @PostMapping("/action/{inquiryActionId}")
//    @PreAuthorize("hasRole('CONSULTANT')")
    public ResponseEntity<ResultResponse<InquiryActionResponse>> updateInquiryAction(@PathVariable Long inquiryActionId,
                                                                     @RequestBody UpdateInquiryActionRequest request) {
        InquiryActionResponse updateAction = inquiryService.updateInquiryAction(inquiryActionId, request.getUpdateActionContent(), request.getActionStatus());
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "조치 내용 수정 성공", updateAction));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ResultResponse<Page<InquiryResponse>>> getInquiriesByCustomerId(
            @PathVariable Long customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InquiryResponse> inquiries = inquiryService.getInquiriesByCustomerId(customerId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "고객 문의 목록 반환 성공", inquiries));
    }

    @GetMapping("/customer/{customerId}/inquiry/{inquiryId}")
    public ResponseEntity<ResultResponse<InquiryDetailResponse>> getInquiryDetailByCustomerId(
            @PathVariable Long customerId,
            @PathVariable Long inquiryId) {
        InquiryDetailResponse inquiryDetail = inquiryService.getInquiryDetailByCustomerId(customerId, inquiryId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "고객 문의 상세 조회 성공", inquiryDetail));
    }

    @GetMapping("/search2")
    public ResponseEntity<ResultResponse<Page<InquiryResponse>>> searchInquiries(
            @RequestParam String keyword,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InquiryResponse> inquiries = inquiryService.searchInquiries(keyword, startDate, endDate, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "문의 전체 목록 검색 성공", inquiries));
    }

    @GetMapping("/search")
    public ResponseEntity<ResultResponse<Page<InquiryResponse>>> searchInquiries(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) String customerPhone,
            @RequestParam(required = false) String inquiryTitle,
            @RequestParam(required = false) String inquiryContent,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Boolean isReply,
            @RequestParam(required = false) ActionStatus actionStatus,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InquiryResponse> inquiries = inquiryService.searchInquiries(
                keyword, customerName, customerPhone, inquiryTitle, inquiryContent,
                startDate, endDate, type, isReply, actionStatus, pageable
        );
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "문의 전체 목록 검색 성공", inquiries));
    }

}

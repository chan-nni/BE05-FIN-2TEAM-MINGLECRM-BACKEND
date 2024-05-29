package com.team2final.minglecrm.controller.inquiry;

import com.team2final.minglecrm.controller.inquiry.request.InquiryReplyRequest;
import com.team2final.minglecrm.controller.inquiry.request.UpdateInquiryReplyRequest;
import com.team2final.minglecrm.controller.inquiry.response.InquiryDetailResponse;
import com.team2final.minglecrm.controller.inquiry.response.InquiryReplyResponse;
import com.team2final.minglecrm.controller.inquiry.response.InquiryResponse;
import com.team2final.minglecrm.service.inquiry.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inquiries")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    @GetMapping
    @PreAuthorize("hasAnyRole('CONSULTANT', 'STAFF', 'MARKETER', 'MANAGER')")
    public List<InquiryResponse> getAllInquiries() {
        return inquiryService.getAllInquiries();
    }

    @GetMapping("/{inquiryId}")
    public ResponseEntity<InquiryDetailResponse> getInquiryById(@PathVariable Long inquiryId) {
        InquiryDetailResponse inquiryDetailResponse = inquiryService.getInquiryById(inquiryId);
        return ResponseEntity.ok(inquiryDetailResponse);
    }

    @PostMapping("/reply")
    @PreAuthorize("hasRole('CONSULTANT')")
    public InquiryReplyResponse replyToInquiry(@RequestBody InquiryReplyRequest request) {
        return inquiryService.replyToInquiry(request);
    }

    @PostMapping("/reply/{inquiryReplyId}")
    @PreAuthorize("hasRole('CONSULTANT')")
    public ResponseEntity<InquiryReplyResponse> updateInquiryReply(@PathVariable Long inquiryReplyId,
                                                                   @RequestBody UpdateInquiryReplyRequest request) {
        InquiryReplyResponse updatedReply = inquiryService.updateInquiryReply(inquiryReplyId, request.getUpdatedReply());
        return ResponseEntity.ok(updatedReply);
    }


}

package com.team2final.minglecrm.controller.inquiry.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class InquiryDetailResponse {
    private InquiryResponse inquiryResponse;
    private InquiryReplyResponse inquiryReplyResponse;

    @Builder
    public InquiryDetailResponse(InquiryResponse inquiryResponse, InquiryReplyResponse inquiryReplyResponse) {
        this.inquiryResponse = inquiryResponse;
        this.inquiryReplyResponse = inquiryReplyResponse;
    }

}

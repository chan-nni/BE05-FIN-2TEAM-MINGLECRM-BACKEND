package com.team2final.minglecrm.inquiry.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class InquiryActionResponse {
    private Long id;
    private Long inquiryId;
    private String actionStatus;
    private String actionContent;
    private String email;
    private String name;
    private LocalDateTime date;

    @Builder
    public InquiryActionResponse(Long id, Long inquiryId, String actionStatus, String actionContent, String email, String name, LocalDateTime date){
        this.id = id;
        this.inquiryId = inquiryId;
        this.actionStatus = actionStatus;
        this.actionContent = actionContent;
        this.email = email;
        this.name = name;
        this.date = date;
    }
}

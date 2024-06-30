package com.team2final.minglecrm.event.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class GroupEmailSendRequest {


    private List<String> toEmails;
    private String title;
    private String content;

    @Builder
    public GroupEmailSendRequest(List<String> toEmails, String title, String content) {
        this.toEmails = toEmails;
        this.title = title;
        this.content = content;

    }
}

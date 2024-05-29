package com.team2final.minglecrm.controller.review.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class WrapperResponse {

    private String status;
    private Object data;

    public WrapperResponse(String status, Object data) {
        // Validation
        if (!status.equals("success") && !status.equals("fail") && !status.equals("error")) {
            throw new IllegalArgumentException("Invalid status value");
        }

        this.status = status;
        this.data = data;
    }

}

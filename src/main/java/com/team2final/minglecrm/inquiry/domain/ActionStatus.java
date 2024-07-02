package com.team2final.minglecrm.inquiry.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ActionStatus {
    ACTION_NOT_NEEDED("조치 불필요"),
    BEFORE_ACTION("조치 전"),
    AFTER_ACTION("조치 후"),
    UNKNOWN("조치 상태 없음");

    private final String value;

    ActionStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ActionStatus fromValue(String value) {
        System.out.println("수신된 값: " + value);
        for (ActionStatus status : ActionStatus.values()) {
            System.out.println("비교 중: " + status.value);  // 모든 값 비교 로그
            if (status.value.equals(value) || status.name().equalsIgnoreCase(value)) {
                System.out.println("매칭된 값: " + value);
                return status;
            }
        }
        System.err.println("알 수 없는 값 : " + value);
        throw new IllegalArgumentException("알 수 없는 값 : " + value);
    }
}

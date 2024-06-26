package com.team2final.minglecrm.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 400 - Client errors
//    INVALID_REQUEST_ARGUMENT("잘못된 요청입니다."),
//    NOT_CUSTOMER_OWNER("해당 고객의 소유자가 아닙니다."),
//    INVALID_ENTRY_TIME("유효하지 않은 입장 시간입니다."),
//    EXPIRED_ENTRY_CODE("만료된 입장 코드입니다."),
//    INVALID_ENTRY_CODE("유효하지 않은 입장 코드입니다."),
//    INVALID_ORDER_TIME("주문 시간이 유효하지 않습니다."),
//    ITEM_NOT_FOUND("상품을 찾을 수 없습니다."),
//    DUPLICATE_CUSTOMER_EMAIL("중복된 고객 이메일입니다."),
//    DUPLICATE_ORDER_NUMBER("중복된 주문 번호입니다."),
//    INVALID_CUSTOMER_ID("유효하지 않은 고객 ID입니다."),
//    NOT_SUPPORT_PAYMENT_TYPE("지원하지 않는 결제 방식입니다."),
//    ORDER_CANCEL_FAILED("주문 취소에 실패했습니다."),
//    INVALID_PAYMENT_AMOUNT("유효하지 않은 결제 금액입니다."),
//    INVALID_PROMO_CODE("유효하지 않은 프로모션 코드입니다."),
//    PROMO_CODE_EXPIRED("만료된 프로모션 코드입니다."),
//    INVALID_FILE_FORMAT("지원하지 않는 파일 형식입니다."),
//    INVALID_DATE_FORMAT("유효하지 않은 날짜 형식입니다."),
//    DUPLICATE_CONTACT_NAME("중복된 연락처 이름입니다."),

    // 401 - Unauthorized
//    EXPIRED_AUTH_TOKEN("만료된 인증 토큰입니다."),
//    INVALID_AUTH_TOKEN("유효하지 않은 인증 토큰입니다."),
//    NOT_BEARER_TOKEN("Bearer 타입의 토큰이 아닙니다."),
//    LOGIN_REQUIRED("로그인이 필요합니다."),
//    INCORRECT_PASSWORD("비밀번호가 잘못되었습니다."),
//    DUPLICATE_ACCOUNT("중복된 계정입니다."),
//    INVALID_REFRESH_TOKEN("유효하지 않은 리프레시 토큰입니다."),
//    EXPIRED_REFRESH_TOKEN("만료된 리프레시 토큰입니다."),

    // 403 - Forbidden
//    INSUFFICIENT_PERMISSION("권한이 부족합니다."),
//    ORDER_NOT_ALLOWED("주문이 허용되지 않습니다."),
//    ACTION_NOT_ALLOWED("해당 동작이 허용되지 않습니다."),
//    CONTACT_NOT_FOUND("연락처를 찾을 수 없습니다."),
//    INVALID_ACCESS("유효하지 않은 접근입니다."),

    // 404 - Not Found
    CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND, "고객을 찾을 수 없습니다."),
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "파일을 찾을 수 없습니다."),
    INVALID_PAGE(HttpStatus.NOT_FOUND, "유효하지 않은 페이지입니다."),

    // 500 - Server errors
//    INTERNAL_SERVER_ERROR("서버 내부 오류가 발생했습니다."),
//    DATABASE_ERROR("데이터베이스 오류가 발생했습니다."),
//    NETWORK_ERROR("네트워크 오류가 발생했습니다."),
//    SERVICE_UNAVAILABLE("서비스를 이용할 수 없습니다."),
    UNEXPECTED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예기치 않은 오류가 발생했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}

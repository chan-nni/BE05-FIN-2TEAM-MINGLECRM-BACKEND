//package com.team2final.minglecrm.entity.inquiry;
//
//import com.team2final.minglecrm.customer.domain.Customer;
//import com.team2final.minglecrm.inquiry.domain.Inquiry;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDateTime;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class InquiryTest {
//
//    @Test
//    void createInquiry() {
//        // Given
//        Customer customer = Customer.builder()
//                .name("강찬미")
//                .phone("010-1234-1234")
//                .build();
//
//        LocalDateTime date = LocalDateTime.now();
//        String title = "문의 제목";
//        String content = "문의 내용";
//        String type = "온라인 문의";
//        Boolean reply = false;
//
//        // When
//        Inquiry inquiry = Inquiry.builder()
//                .customer(customer)
//                .date(date)
//                .inquiryTitle(title)
//                .inquiryContent(content)
//                .type(type)
//                .isReply(reply)
//                .build();
//
//        // Then
//        assertEquals(customer, inquiry.getCustomer());
//        assertEquals(date, inquiry.getDate());
//        assertEquals(title, inquiry.getInquiryTitle());
//        assertEquals(content, inquiry.getInquiryContent());
//        assertEquals(type, inquiry.getType());
//        assertFalse(inquiry.getIsReply());
//    }
//
//}
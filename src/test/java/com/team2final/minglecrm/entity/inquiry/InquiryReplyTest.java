//package com.team2final.minglecrm.entity.inquiry;
//
//import com.team2final.minglecrm.customer.domain.Customer;
//import com.team2final.minglecrm.employee.domain.Employee;
//import com.team2final.minglecrm.inquiry.domain.Inquiry;
//import com.team2final.minglecrm.inquiry.domain.InquiryReply;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDateTime;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class InquiryReplyTest {
//
//    @Test
//    void createInquiryReply() {
//        // Given
//        Employee employee = Employee.builder()
//                .name("관리자")
//                .email("test@test.com")
//                .build();
//
//        Customer customer = Customer.builder()
//                .name("강찬미")
//                .phone("010-1234-1234")
//                .build();
//
//        Inquiry inquiry = Inquiry.builder()
//                .customer(customer)
//                .date(LocalDateTime.of(2024, 6, 4, 10, 30))
//                .inquiryTitle("문의 제목")
//                .inquiryContent("문의 내용")
//                .type("온라인 문의")
//                .isReply(false)
//                .build();
//
//        String replyContent = "답변 내용";
//        LocalDateTime date = LocalDateTime.now();
//
//        // When
//        InquiryReply inquiryReply = InquiryReply.builder()
//                .employee(employee)
//                .inquiry(inquiry)
//                .reply(replyContent)
//                .date(date)
//                .build();
//
//        // Then
//        assertEquals(employee, inquiryReply.getEmployee());
//        assertEquals(inquiry, inquiryReply.getInquiry());
//        assertEquals(replyContent, inquiryReply.getReply());
//        assertEquals(date, inquiryReply.getDate());
//    }
//
//    @Test
//    void updateReply() {
//        // Given
//        Employee employee = Employee.builder()
//                .name("관리자")
//                .email("test@test.com")
//                .build();
//
//        Customer customer = Customer.builder()
//                .name("강찬미")
//                .phone("010-1234-1234")
//                .build();
//
//        Inquiry inquiry = Inquiry.builder()
//                .customer(customer)
//                .date(LocalDateTime.of(2024, 6, 4, 10, 30))
//                .inquiryTitle("문의 제목")
//                .inquiryContent("문의 내용")
//                .type("온라인 문의")
//                .isReply(false)
//                .build();
//
//        String originalReplyContent = "답변 내용";
//        LocalDateTime date = LocalDateTime.now();
//
//        InquiryReply inquiryReply = InquiryReply.builder()
//                .employee(employee)
//                .inquiry(inquiry)
//                .reply(originalReplyContent)
//                .date(date)
//                .build();
//
//        // When
//        String updateReplyContent = "답변 내용 업데이트";
//        LocalDateTime updateDate = LocalDateTime.now().plusDays(1);
//
//        inquiryReply.updateReply(updateReplyContent, updateDate, employee);
//
//        // Then
//        assertEquals(employee, inquiryReply.getEmployee());
//        assertEquals(updateReplyContent, inquiryReply.getReply());
//        assertEquals(updateDate, inquiryReply.getDate());
//    }
//
//}
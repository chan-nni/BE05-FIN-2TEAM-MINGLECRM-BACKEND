//package com.team2final.minglecrm.service.inquiry;
//
//import com.team2final.minglecrm.employee.domain.Employee;
//import com.team2final.minglecrm.inquiry.domain.Inquiry;
//import com.team2final.minglecrm.inquiry.domain.InquiryAction;
//import com.team2final.minglecrm.inquiry.domain.InquiryReply;
//import com.team2final.minglecrm.inquiry.service.InquiryService;
//import com.team2final.minglecrm.inquiry.domain.repository.InquiryActionRepository;
//import com.team2final.minglecrm.inquiry.domain.repository.InquiryReplyRepository;
//import com.team2final.minglecrm.inquiry.domain.repository.InquiryRepository;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.mockito.junit.jupiter.MockitoSettings;
//import org.mockito.quality.Strictness;
//
//@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
//class InquiryServiceTest {
//    @InjectMocks
//    InquiryService inquiryService;
//
//    @Mock
//    InquiryRepository inquiryRepository;
//
//    @Mock
//    InquiryReplyRepository inquiryReplyRepository;
//
//    @Mock
//    InquiryActionRepository inquiryActionRepository;
//
//    private Inquiry inquiry;
//    private InquiryReply inquiryReply;
//    private InquiryAction inquiryAction;
//    private Employee employee;
//
////    @BeforeEach
////    void setUp() {
////        Customer customer = Customer.builder()
////                .name("강찬미")
////                .phone("010-1234-1234")
////                .build();
////
////        employee = Employee.builder()
////                .name("관리자")
////                .email("test@test.com")
////                .build();
////
////        inquiry = Inquiry.builder()
////                .id(1L)
////                .customer(customer)
////                .date(LocalDateTime.of(2024, 6, 4, 10, 30))
////                .inquiryTitle("문의 제목")
////                .inquiryContent("문의 내용")
////                .type("온라인 문의")
////                .isReply(false)
////                .build();
////    }
////
////    @Test
////    @DisplayName("전체 문의 목록 조희 테스트")
////    void GetInquiriesTest() {
////        // given
////        PageRequest pageRequest = PageRequest.of(0, 10);
////        Page<Inquiry> inquiryPage = new PageImpl<>(List.of(inquiry));
////        when(inquiryRepository.findAll(pageRequest)).thenReturn(inquiryPage);
////        when(inquiryReplyRepository.findByInquiryId(inquiry.getId())).thenReturn(inquiryReply);
////
////        Page<InquiryResponse> result = inquiryService.getAllInquiries(pageRequest);
////
////        assertNotNull(result);
////        assertEquals(1, result.getTotalElements());
////        verify(inquiryRepository).findAll(pageRequest);
////    }
////
////    @Test
////    @DisplayName("전체 문의 목록 조회 테스트 - 여러 개의 문의가 있는 경우")
////    void GetAllInquiriesTest() {
////        // given
////        PageRequest pageRequest = PageRequest.of(0, 10);
////        List<Inquiry> inquiryList = List.of(
////                inquiry,
////                Inquiry.builder()
////                        .id(2L)
////                        .customer(Customer.builder().name("홍길동").phone("010-1245-2356").build())
////                        .date(LocalDateTime.of(2024, 6, 4, 11, 30))
////                        .inquiryTitle("문의 제목2")
////                        .inquiryContent("문의 내용2")
////                        .type("전화 문의")
////                        .isReply(false)
////                        .build()
////        );
////        Page<Inquiry> inquiryPage = new PageImpl<>(inquiryList);
////        when(inquiryRepository.findAll(pageRequest)).thenReturn(inquiryPage);
////        when(inquiryReplyRepository.findByInquiryId(inquiry.getId())).thenReturn(inquiryReply);
////
////        // when
////        Page<InquiryResponse> result = inquiryService.getAllInquiries(pageRequest);
////
////        // then
////        assertNotNull(result);
////        assertEquals(2, result.getTotalElements());
////        verify(inquiryRepository).findAll(pageRequest);
////    }
////
////    @Test
////    @DisplayName("답변이 있으면 isReply가 true 반환")
////    void getAnsweredInquiriesTest() {
////        // given
////        PageRequest pageRequest = PageRequest.of(0, 10);
////
////        // 답변 객체 생성
////        InquiryReply inquiryReply = InquiryReply.builder()
////                .id(1L)
////                .inquiry(inquiry)
////                .employee(employee)
////                .reply("답변 내용")
////                .date(LocalDateTime.now())
////                .build();
////
////        // InquiryReplyRepository에서 findByInquiryId 메서드가 호출될 때 답변 객체 반환하도록 설정
////        when(inquiryReplyRepository.findByInquiryId(inquiry.getId())).thenReturn(inquiryReply);
////
////        // InquiryRepository에서 findInquiriesWithReply 메서드가 호출될 때 Inquiry 객체 반환하도록 설정
////        List<Inquiry> inquiryList = List.of(inquiry);
////        Page<Inquiry> answeredInquiriesPage = new PageImpl<>(inquiryList);
////        when(inquiryRepository.findInquiriesWithReply(pageRequest)).thenReturn(answeredInquiriesPage);
////
////        // when
////        Page<InquiryResponse> result = inquiryService.getAnsweredInquiries(pageRequest);
////
////        // then
////        assertNotNull(result);
////        assertEquals(1, result.getTotalElements());
////        assertEquals(1, result.getContent().size());
////        InquiryResponse inquiryResponse = result.getContent().get(0);
////        assertNotNull(inquiryResponse);
////        assertTrue(inquiryResponse.getIsReply()); // 답변이 있는 상황이므로 true가 반환되어야 함
////    }
//
//
//}
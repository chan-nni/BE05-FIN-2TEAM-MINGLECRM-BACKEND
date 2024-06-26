package com.team2final.minglecrm.service.email;

import com.team2final.minglecrm.customer.domain.Customer;
import com.team2final.minglecrm.customer.domain.repository.CustomerRepository;
import com.team2final.minglecrm.employee.domain.repository.EmployeeRepository;
import com.team2final.minglecrm.event.domain.Event;
import com.team2final.minglecrm.event.domain.repository.EventRepository;
import com.team2final.minglecrm.event.dto.request.CreateEventRequest;
import com.team2final.minglecrm.event.dto.request.PersonalEmailSendRequest;
import com.team2final.minglecrm.event.service.EventService;
import com.team2final.minglecrm.log.domain.EmailLog;
import com.team2final.minglecrm.log.domain.repository.EmailLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailSendService emailSendService;
    private final EmployeeRepository employeeRepository;
    private final EventService eventService;
    private final EmailLogRepository emailLogRepository;
    private final CustomerRepository customerRepository;
    private final EventRepository eventRepository;


    public void sendPersonalEmail(PersonalEmailSendRequest request, String employeeEmail) throws Exception {

        // 이벤트 생성
        CreateEventRequest createEventRequest = CreateEventRequest.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .employeeEmail(employeeEmail)
                .sendCount(1L)
                .build();

        Long eventId = eventService.createEvent(createEventRequest);

        Event event = eventRepository.findById(eventId).orElseThrow( () -> new Exception("없는 이벤트입니다."));
        Customer customer = customerRepository.findByEmail(request.getToEmail()).orElseThrow( () ->
                new IllegalArgumentException("없는 고객입니다."));

        // 이메일 보내기
        String content = request.getContent();
        content = "<img src=http://localhost:8080/api/readcheck/" + eventId.toString() + "/" + request.getToEmail() + "\" " + "onerror=this.style.display='none';>";
        emailSendService.sendMail(request.getToEmail(), request.getTitle(), content);

        // 이메일 로그 생성


        EmailLog emailLog = EmailLog.builder()
                .event(event)
                .customer(customer)
                .build();

        emailLogRepository.save(emailLog);
    }
}

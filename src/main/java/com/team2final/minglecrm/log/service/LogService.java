package com.team2final.minglecrm.log.service;

import com.team2final.minglecrm.customer.domain.Customer;
import com.team2final.minglecrm.customer.domain.repository.CustomerRepository;
import com.team2final.minglecrm.event.domain.Event;
import com.team2final.minglecrm.event.domain.repository.EventRepository;
import com.team2final.minglecrm.log.domain.EmailLog;
import com.team2final.minglecrm.log.domain.repository.EmailLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LogService {

    private final EmailLogRepository emailLogRepository;
    private final CustomerRepository customerRepository;
    private final EventRepository eventRepository;

    public void createEmailLog(Long eventId, String customerEmail) throws Exception {

        Event event =
                eventRepository.findById(eventId).orElseThrow(Exception::new);

        Customer customer =
                customerRepository.findByEmail(customerEmail).orElseThrow(Exception::new);

        EmailLog emailLog = EmailLog.builder()
                .customer(customer)
                .event(event)
                .build();

        emailLogRepository.save(emailLog);
    }

    @Transactional
    public Long getPagesNumberByEventId(Long eventId) {
        return emailLogRepository.countByEventId(eventId);
    }

}

package com.team2final.minglecrm.event.presentation;


import com.team2final.minglecrm.auth.infrastructure.JwtUtil;
import com.team2final.minglecrm.common.exception.ResultResponse;
import com.team2final.minglecrm.event.dto.request.EventEmailSendRequest;
import com.team2final.minglecrm.event.dto.request.GroupEmailSendRequest;
import com.team2final.minglecrm.event.dto.request.PersonalEmailSendRequest;
import com.team2final.minglecrm.event.dto.response.EmailLogResponse;
import com.team2final.minglecrm.event.dto.response.EventLogResponse;
import com.team2final.minglecrm.event.service.EventService;
import com.team2final.minglecrm.log.service.LogService;
import com.team2final.minglecrm.service.email.EmailSendService;
import com.team2final.minglecrm.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventApi {

    private final EventService eventService;
    private final EmailSendService emailSendService;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;
    private final LogService logService;

    @PostMapping("/api/event/email")
    public ResultResponse<Void> sendEventEmail(@RequestBody EventEmailSendRequest request) throws Exception {
        eventService.sendEventEmail(request);
        return new ResultResponse<>(HttpStatus.OK.value(), "success", null);
    }

    @GetMapping("/api/readcheck/{eventId}/{customerEmail}")
    public ResultResponse<Void> emailOpenLogging(
            @PathVariable(name = "eventId") Long eventId,
            @PathVariable(name = "customerEmail") String customerEmail ) throws Exception {

            eventService.emailOpenCheck(eventId, customerEmail);

        return new ResultResponse<>();
    }

    @PostMapping("/api/email/personal")
    public ResultResponse<Void> sendPersonalEmail(@RequestBody PersonalEmailSendRequest request) throws Exception {
        String EmployeeEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        emailService.sendPersonalEmail(request, EmployeeEmail);

        return new ResultResponse<>(HttpStatus.OK.value(), "success", null);
    }

    @PostMapping("/api/email/group")
    public ResultResponse<Void> sendGroupEmail(@RequestBody GroupEmailSendRequest request) throws Exception {
        String EmployeeEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        emailService.sendGroupEmail(request, EmployeeEmail);

        return new ResultResponse<>(HttpStatus.OK.value(), "success", null);
    }

    @GetMapping("/api/events/{pageno}")
    public ResultResponse<List<EventLogResponse>> getAllEvents(@PathVariable(name="pageno") int pageNo) {
        return new ResultResponse<>(HttpStatus.OK.value(), "success", eventService.getAllEvents(pageNo));
    }

    @GetMapping("/api/emaillog/{eventid}/{pageno}")
    public ResultResponse<List<EmailLogResponse>> getEmailLogsByEventId(
            @PathVariable(name="eventid") Long eventId,
            @PathVariable(name="pageno") int pageNo) {
        List<EmailLogResponse> response = eventService.getEmailLogsByEventId(pageNo, eventId);
        return new ResultResponse<>(HttpStatus.OK.value(), "success", response);
    }

    @GetMapping("/api/event/pagesnumber")
    public ResultResponse<Long> getEventPagesNumber() {
        Long pagesNumber = eventService.getPagesNumber();
        return new ResultResponse<>(HttpStatus.OK.value(), "success", pagesNumber);
    }

    @GetMapping("/api/emaillog/pagesnumber/{eventid}")
    public ResultResponse<Long> getEmailLogPagesNumberByEventId(@PathVariable(name="eventid") Long eventId) {
        Long pagesNumber = logService.getPagesNumberByEventId(eventId);
        return new ResultResponse<>(HttpStatus.OK.value(), "success", pagesNumber);
    }
}

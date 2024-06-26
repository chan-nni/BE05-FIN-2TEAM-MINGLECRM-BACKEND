package com.team2final.minglecrm.payment.service;

import com.team2final.minglecrm.payment.domain.Payment;
import com.team2final.minglecrm.payment.domain.repository.PaymentRepository;
import com.team2final.minglecrm.payment.domain.repository.PaymentSearchRepository;
import com.team2final.minglecrm.payment.dto.request.PaymentSearchCondition;
import com.team2final.minglecrm.payment.dto.response.PaymentDetailResponse;
import com.team2final.minglecrm.payment.dto.response.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentSearchRepository paymentSearchRepository;

    public List<PaymentResponse> findById(Long customerId) {
        List<Payment> payments = paymentRepository.findByCustomerId(customerId);

        return payments.stream()
                .map(PaymentResponse::from)
                .collect(Collectors.toList());
    }

    public PaymentDetailResponse findPaymentById(Long customerId, Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow();
        return PaymentDetailResponse.from(payment);
    }

    public List<PaymentResponse> searchPaymentDetail(Long customerId, PaymentSearchCondition condition) {
        return paymentSearchRepository.search(customerId, condition);
    }

}
package com.team2final.minglecrm.service.reward;

import com.team2final.minglecrm.controller.reward.request.VoucherCreateRequest;
import com.team2final.minglecrm.controller.reward.response.VoucherApprovalResponse;
import com.team2final.minglecrm.controller.reward.response.VoucherHistoryResponse;
import com.team2final.minglecrm.controller.reward.response.VoucherResponse;
import com.team2final.minglecrm.entity.customer.Customer;
import com.team2final.minglecrm.entity.employee.Employee;
import com.team2final.minglecrm.entity.reward.Voucher;
import com.team2final.minglecrm.entity.reward.VoucherHistory;
import com.team2final.minglecrm.entity.reward.status.VoucherStatusType;
import com.team2final.minglecrm.persistence.repository.customer.CustomerRepository;
import com.team2final.minglecrm.persistence.repository.employee.EmployeeRepository;
import com.team2final.minglecrm.persistence.repository.reward.VoucherHistoryRepository;
import com.team2final.minglecrm.persistence.repository.reward.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final VoucherHistoryRepository voucherHistoryRepository;

    @Transactional
    public VoucherResponse saveVoucher(VoucherCreateRequest request) {

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = authentication.getName();
//
//        Employee employee = employeeRepository.findByEmail(userEmail)
//                .orElseThrow(() -> new RuntimeException("로그인한 사용자를 찾을 수 없습니다."));

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("고객을 찾을 수 없습니다."));

//        String createdVoucherCode = generateUniqueVoucherCode();

        Voucher voucher = Voucher.builder()
                .employee(null)
                .customer(customer)
                .amount(request.getAmount())
                .createdReason(request.getReason())
                .build();

        voucherRepository.save(voucher);

        return VoucherResponse.of(voucher);
    }

    // voucher code 생성 메서드
    private String generateUniqueVoucherCode() {
        int n = 16; // n자리 쿠폰
        char[] chs = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        Random rd = new Random();
        String createdVoucherCode = "";
        boolean isUnique = false;

        while (!isUnique) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                char ch = chs[rd.nextInt(chs.length)];
                sb.append(ch);
            }
            createdVoucherCode = sb.toString();

            // 데이터베이스에서 중복 체크
            isUnique = !voucherHistoryRepository.existsByVoucherCode(createdVoucherCode);
        }

        return createdVoucherCode;
    }

    @Transactional
    public List<VoucherResponse> getAllVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();

        return vouchers.stream()
                .map(VoucherResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<VoucherHistoryResponse> getAllRequestedVouchers() {
        List<VoucherHistory> voucherHistories = voucherHistoryRepository.findAllByStatus(VoucherStatusType.REQUESTED);

        return voucherHistories.stream()
                .map(VoucherHistoryResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<VoucherHistoryResponse> getAllVoucherHistories() {
        List<VoucherHistory> voucherHistories = voucherHistoryRepository.findAll();

        return voucherHistories.stream()
                .map(VoucherHistoryResponse::of)
                .collect(Collectors.toList());
    }




    @Transactional
    public VoucherHistoryResponse requestVoucher(Long voucherId) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = authentication.getName();

//        Employee employee = employeeRepository.findByEmail(userEmail)
//                .orElseThrow(() -> new RuntimeException("로그인한 사용자를 찾을 수 없습니다."));

        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 바우처를 찾을 수 없습니다."));

//        String createdVoucherCode = generateUniqueVoucherCode();

        voucher.requestVoucher(voucher);


        VoucherHistory voucherHistory = VoucherHistory.builder()
                .voucher(voucher)
                .customer(voucher.getCustomer())
                .status(VoucherStatusType.REQUESTED)
                .requestDate(LocalDateTime.now())
                .build();

        voucherHistoryRepository.save(voucherHistory);

        return VoucherHistoryResponse.of(voucherHistory);
    }

    @Transactional
    public VoucherHistoryResponse approveVoucher(Long voucherId) {

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = authentication.getName();

//        Employee approver = employeeRepository.findByEmail(userEmail)
//                .orElseThrow(() -> new RuntimeException("로그인한 사용자를 찾을 수 없습니다."));

        VoucherHistory voucherHistory = voucherHistoryRepository.findByVoucherId(voucherId);
//                .orElseThrow(() -> new RuntimeException("해당 ID의 바우처의 히스토리를 찾을 수 없습니다."));

        String generatedUniqueVoucherCode = generateUniqueVoucherCode();

//        voucherHistory.approveVoucher(approver);
        voucherHistory.approveVoucher(generatedUniqueVoucherCode);
//        voucherHistoryRepository.save(voucherHistory);

        return VoucherHistoryResponse.of(voucherHistory);
    }

    public List<VoucherHistoryResponse> getApprovedVouchersByManager() {
        List<VoucherHistory> approvedVouchers = voucherHistoryRepository.findAllByStatus(VoucherStatusType.APPROVED);

        return approvedVouchers.stream()
                .map(VoucherHistoryResponse::of)
                .collect(Collectors.toList());
    }

    public VoucherHistoryResponse rejectVoucher(Long voucherId, String rejectReason) {
        //        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = authentication.getName();

//        Employee approver = employeeRepository.findByEmail(userEmail)
//                .orElseThrow(() -> new RuntimeException("로그인한 사용자를 찾을 수 없습니다."));

        VoucherHistory voucherHistory = voucherHistoryRepository.findByVoucherId(voucherId);
//                orElseThrow(() -> new RuntimeException("해당 ID의 바우처의 히스토리를 찾을 수 없습니다."));

//        voucherHistory.approveVoucher(approver);
        voucherHistory.rejectVoucher(rejectReason);
//        voucherHistoryRepository.save(voucherHistory);

        return VoucherHistoryResponse.of(voucherHistory);
    }

    public List<VoucherHistoryResponse> getRejectedVouchersByManager() {
        List<VoucherHistory> rejectedVouchers = voucherHistoryRepository
                .findAllByStatus(VoucherStatusType.REJECTED);

        return rejectedVouchers.stream()
                .map(VoucherHistoryResponse::of)
                .collect(Collectors.toList());
    }

    public VoucherHistoryResponse getVoucherHistory(Long voucherId) {
        VoucherHistory voucherHistory = voucherHistoryRepository.findByVoucherId(voucherId);
        return VoucherHistoryResponse.of(voucherHistory);
    }

    public List<VoucherResponse> getNotRequestedVouchers() {
        List<Voucher> rejectedVouchers = voucherRepository.findAllByIsRequested(false);

        return rejectedVouchers.stream()
                .map(VoucherResponse::of)
                .collect(Collectors.toList());
    }

    public List<VoucherHistoryResponse> getRequestedVouchersByMarketer() {
//                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = authentication.getName();
//
//        Employee creator = employeeRepository.findByEmail(userEmail)
//                .orElseThrow(() -> new RuntimeException("로그인한 사용자를 찾을 수 없습니다."));
//
        Employee employeeTest = employeeRepository.findById(7L).orElseThrow();
        List<VoucherHistory> requestedVouchers = voucherHistoryRepository
                .findAllByEmployeeStaffAndStatus(employeeTest, VoucherStatusType.REQUESTED);

        return requestedVouchers.stream()
                .map(VoucherHistoryResponse::of)
                .toList();
    }

    @Transactional
    public void deleteVoucher(Long voucherId) {
        voucherRepository.findById(voucherId).orElseThrow(
                () -> new RuntimeException("no voucherId"));

        voucherRepository.deleteById(voucherId);
    }

    public List<VoucherHistoryResponse> getApprovedVouchers() {

        //                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = authentication.getName();

//        Employee creator = employeeRepository.findByEmail(userEmail)
//                .orElseThrow(() -> new RuntimeException("로그인한 사용자를 찾을 수 없습니다."));
        Employee employeeTest = employeeRepository.findById(8L).orElseThrow();

        List<VoucherHistory> allByEmployeeStaffAndStatus = voucherHistoryRepository.
                findAllByEmployeeStaffAndStatus(employeeTest, VoucherStatusType.APPROVED);

        return allByEmployeeStaffAndStatus.stream()
                .map(VoucherHistoryResponse::of)
                .toList();
    }

    public List<VoucherHistoryResponse> getRejectedVouchers() {
        //                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = authentication.getName();

//        Employee creator = employeeRepository.findByEmail(userEmail)
//                .orElseThrow(() -> new RuntimeException("로그인한 사용자를 찾을 수 없습니다."));
        Employee employeeTest = employeeRepository.findById(14L).orElseThrow();

        List<VoucherHistory> allByEmployeeStaffAndStatus = voucherHistoryRepository.
                findAllByEmployeeStaffAndStatus(employeeTest, VoucherStatusType.REJECTED);

        return allByEmployeeStaffAndStatus.stream()
                .map(VoucherHistoryResponse::of)
                .toList();
    }

    // customer-detail
    @Transactional
    public List<VoucherHistoryResponse> getCustomerVouchers(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 고객을 찾을 수 없습니다."));

        List<VoucherHistory> voucherHistories = voucherHistoryRepository.findAllByCustomer(customer);

        return voucherHistories.stream()
                .map(VoucherHistoryResponse::of)
                .collect(Collectors.toList());
    }

    // customer-detail
    @Transactional
    public VoucherHistoryResponse getCustomerVoucher(Long customerId, Long voucherId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 고객을 찾을 수 없습니다."));
        VoucherHistory voucherHistory = voucherHistoryRepository.findByCustomerAndVoucherId(customer, voucherId);
        return VoucherHistoryResponse.of(voucherHistory);
    }

}
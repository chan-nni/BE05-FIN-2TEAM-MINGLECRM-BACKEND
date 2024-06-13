package com.team2final.minglecrm.controller.reward;


import com.team2final.minglecrm.controller.ResultResponse;
import com.team2final.minglecrm.controller.reward.request.VoucherCreateRequest;
import com.team2final.minglecrm.controller.reward.request.VoucherRejectRequest;
import com.team2final.minglecrm.controller.reward.response.VoucherHistoryResponse;
import com.team2final.minglecrm.controller.reward.response.VoucherResponse;
import com.team2final.minglecrm.service.reward.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vouchers")
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    // 매니저 권한일 때

    // 1. 승인 요청된 바우처 목록
    @GetMapping //@PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ResultResponse<List<VoucherHistoryResponse>>> getRequestedVouchers(){
        List<VoucherHistoryResponse> voucherList = voucherService.getAllRequestedVouchers();
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "승인 요청된 바우처 전체 조회 성공", voucherList));
    }

    // 2. 바우처 승인
    @PostMapping("/approval/{voucherId}") // @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ResultResponse<VoucherHistoryResponse>> approveVoucher(@PathVariable("voucherId") Long voucherId) {
        VoucherHistoryResponse voucherApprovalResponse = voucherService.approveVoucher(voucherId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "바우처 승인 성공", voucherApprovalResponse));
    }

    // 3. 바우처 승인 거절
    @PostMapping("/rejection/{voucherId}") // @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ResultResponse<VoucherHistoryResponse>> rejectVoucher(@PathVariable("voucherId") Long voucherId, @RequestBody VoucherRejectRequest request) {
        VoucherHistoryResponse voucherRejectResponse = voucherService.rejectVoucher(voucherId, request.getReason());
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "바우처 승인 성공", voucherRejectResponse));
    }

    // 4. 승인 완료된 바우처 목록
    @GetMapping("/approved") //@PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ResultResponse<List<VoucherHistoryResponse>>> getApprovedVouchersByManager(){
        List<VoucherHistoryResponse> approvedVouchers = voucherService.getApprovedVouchersByManager();
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "바우처 목록 확인", approvedVouchers));
    }


    // 5. 승인 거절된 바우처 목록
    @GetMapping("/rejected") //@PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ResultResponse<List<VoucherHistoryResponse>>> getRejectedVouchersByManager(){
        List<VoucherHistoryResponse> voucherList = voucherService.getRejectedVouchersByManager();
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "승인 전&후 바우처 전체 조회 성공", voucherList));
    }


    // 6. 모든 바우처(히스토리) 목록
    @GetMapping("/histories") //@PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ResultResponse<List<VoucherHistoryResponse>>> getVoucherHistories(){
        List<VoucherHistoryResponse> voucherList = voucherService.getAllVoucherHistories();
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "승인 전&후 바우처 전체 조회 성공", voucherList));
    }

    // 7. 리워드 전환된 바우처 목록 ?

    // 8. (요청된) 바우처 상세
    @GetMapping("/{voucherId}") //@PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ResultResponse<VoucherHistoryResponse>> getVoucher(@PathVariable Long voucherId){
        VoucherHistoryResponse voucherHistory = voucherService.getVoucherHistory(voucherId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "사용자별 사용 가능 바우처 상세 조회 성공", voucherHistory));
    }


    // 마케터 권한일 때

    // 1. 바우처 생성
    @PostMapping ()// @PreAuthorize("hasRole('MARKETER')")
    public ResponseEntity<ResultResponse<VoucherResponse>> createVoucher(@RequestBody VoucherCreateRequest request) {
        VoucherResponse createdVoucher = voucherService.saveVoucher(request);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "바우처 생성 성공", createdVoucher));
    }

    // 2. 승인 요청 전 바우처 목록
    @GetMapping("/before-requested") //@PreAuthorize("hasRole('MARKETER')")
    public ResponseEntity<ResultResponse<List<VoucherResponse>>> getNotRequestedVouchers(){
        List<VoucherResponse> voucherList = voucherService.getNotRequestedVouchers();
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "생성된 바우처 전체 조회 성공", voucherList));
    }

    // 3. 바우처 승인 요청
    // voucher - isRequested : True로
    // voucherHistory - requestedDate : now로
    @PostMapping("/request/{voucherId}") //@PreAuthorize("hasRole('MARKETER')")
    public ResponseEntity<ResultResponse<VoucherHistoryResponse>> requestVoucher(@PathVariable Long voucherId) {
        VoucherHistoryResponse voucherRequestResponse = voucherService.requestVoucher(voucherId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "바우처 승인 요청 완료", voucherRequestResponse));
    }


    // 4. 승인 요청 전 바우처 삭제
    @DeleteMapping("/{voucherId}") //@PreAuthorize("hasRole('MARKETER')")
    public ResponseEntity<ResultResponse<Void>> deleteVoucher(@PathVariable Long voucherId) {
        voucherService.deleteVoucher(voucherId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "바우처 삭제 완료", null));
    }
    // 5. 승인 요청한 바우처 목록
    @GetMapping("/requested-list") //@PreAuthorize("hasRole('MARKETER')")
    public ResponseEntity<ResultResponse<List<VoucherHistoryResponse>>> getRequestedVouchersByMarketer() {
        List<VoucherHistoryResponse> voucherList = voucherService.getRequestedVouchersByMarketer();
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "생성된 바우처 전체 조회 성공", voucherList));
    }

    // 6. 승인 완료된 바우처 목록
    @GetMapping("/approved-marketer") //@PreAuthorize("hasRole('MARKETER')")
    public ResponseEntity<ResultResponse<List<VoucherHistoryResponse>>> getApprovedVouchers() {
        List<VoucherHistoryResponse> voucherList = voucherService.getApprovedVouchers();
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "승인 완료된 바우처 목록", voucherList));
    }


    // 7. 승인 거절된 바우처 목록
    @GetMapping("/rejected-marketer") //@PreAuthorize("hasRole('MARKETER')")
    public ResponseEntity<ResultResponse<List<VoucherHistoryResponse>>> getRejectedVouchers() {
        List<VoucherHistoryResponse> voucherList = voucherService.getRejectedVouchers();
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "승인 거절된 바우처 목록", voucherList));
    }






}
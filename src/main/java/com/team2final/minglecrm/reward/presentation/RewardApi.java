package com.team2final.minglecrm.reward.presentation;

import com.team2final.minglecrm.common.exception.ResultResponse;
import com.team2final.minglecrm.reward.dto.response.RewardHistoryResponse;
import com.team2final.minglecrm.reward.dto.response.RewardResponse;
import com.team2final.minglecrm.reward.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rewards")
@RequiredArgsConstructor
public class RewardApi {
    private final RewardService rewardService;

    @GetMapping
    public ResponseEntity<ResultResponse<List<RewardResponse>>> getRewards() {
        List<RewardResponse> rewards = rewardService.getAllRewards();
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(),"리워드 리스트 조회 성공", rewards));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<ResultResponse<RewardResponse>> getReward(@PathVariable("customerId") Long customerId) {
        RewardResponse reward = rewardService.getReward(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "사용자별 리워드 조회 성공", reward));
    }

    @GetMapping("/history/{customerId}")
    public ResponseEntity<ResultResponse<List<RewardHistoryResponse>>> getRewardHistory(@PathVariable("customerId") Long customerId) {
        List<RewardHistoryResponse> rewardHistories = rewardService.getRewardHistories(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "사용자별 리워드 히스토리 조회 성공", rewardHistories));
    }

    @GetMapping("/histories")
    public ResponseEntity<ResultResponse<List<RewardHistoryResponse>>> getRewardHistories() {
        List<RewardHistoryResponse> rewardHistories = rewardService.getAllRewardHistories();
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "전체 리워드 히스토리 목록 조회 성공", rewardHistories));
    }

}

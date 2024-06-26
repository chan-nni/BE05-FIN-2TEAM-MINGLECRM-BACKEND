package com.team2final.minglecrm.statistics.service.purchase;

import com.team2final.minglecrm.statistics.domain.repository.purchase.PurchaseListRepository;
import com.team2final.minglecrm.statistics.dto.response.purchase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsPurchaseService {

    private final PurchaseListRepository purchaseListRepository;

    public List<StatisticsDishNameResponse> getStatisticsByDishName(LocalDate startDate, LocalDate endDate) {
        return purchaseListRepository.findStatisticsByDishName(startDate, endDate);
    }

    public List<StatisticsRoomTypeResponse> getStatisticsByRoomType(LocalDate startDate, LocalDate endDate) {
        return purchaseListRepository.findStatisticsByRoomType(startDate, endDate);
    }

    public List<StatisticsDishNameAndRoomTypeResponse> getStatisticsByDishNameAndRoomType(LocalDate startDate, LocalDate endDate) {
        return purchaseListRepository.findStatisticsByDishNameAndRoomType(startDate, endDate);
    }

    public List<StatisticsGenderResponse> getStatisticsByGender(LocalDate startDate, LocalDate endDate, String gender) {
        return purchaseListRepository.findStatisticsByGender(startDate, endDate, gender);
    }

    public List<StatisticsGradeResponse> getStatisticsByGrade(LocalDate startDate, LocalDate endDate, String grade) {
        return purchaseListRepository.findStatisticsByGrade(startDate, endDate, grade);
    }
}
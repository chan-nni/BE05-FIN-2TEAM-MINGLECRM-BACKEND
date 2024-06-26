package com.team2final.minglecrm.statistics.presentation.purchase;

import com.team2final.minglecrm.statistics.dto.response.purchase.*;
import com.team2final.minglecrm.statistics.service.purchase.StatisticsPurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistics/purchase")
public class StatisticsPurchaseApi {

    private final StatisticsPurchaseService statisticsPurchaseService;

    @GetMapping("/statistics/dishname")
    public List<StatisticsDishNameResponse> getStatisticsByDishName(@RequestParam(value = "startDate", required = false) LocalDate startDate,
                                                                    @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        return statisticsPurchaseService.getStatisticsByDishName(startDate, endDate);
    }

    @GetMapping("/statistics/roomtype")
    public List<StatisticsRoomTypeResponse> getStatisticsByRoomType(@RequestParam(value = "startDate", required = false) LocalDate startDate,
                                                                    @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        return statisticsPurchaseService.getStatisticsByRoomType(startDate, endDate);
    }

    @GetMapping("/statistics/dishname-and-roomtype")
    public List<StatisticsDishNameAndRoomTypeResponse> getStatisticsByDishNameAndRoomType(@RequestParam(value = "startDate", required = false) LocalDate startDate,
                                                                                          @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        return statisticsPurchaseService.getStatisticsByDishNameAndRoomType(startDate, endDate);
    }

    @GetMapping("/statistics/gender")
    public List<StatisticsGenderResponse> getStatisticsByGender(@RequestParam(value = "startDate", required = false) LocalDate startDate,
                                                                @RequestParam(value = "endDate", required = false) LocalDate endDate,
                                                                @RequestParam("gender") String gender) {
        return statisticsPurchaseService.getStatisticsByGender(startDate, endDate, gender);
    }

    @GetMapping("/statistics/grade")
    public List<StatisticsGradeResponse> getStatisticsByGrade(@RequestParam(value = "startDate", required = false) LocalDate startDate,
                                                              @RequestParam(value = "endDate", required = false) LocalDate endDate,
                                                              @RequestParam("grade") String grade) {
        return statisticsPurchaseService.getStatisticsByGrade(startDate, endDate, grade);
    }
}
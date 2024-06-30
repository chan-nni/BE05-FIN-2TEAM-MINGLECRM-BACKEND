package com.team2final.minglecrm.statistics.service.customer;

import com.team2final.minglecrm.customer.domain.repository.CustomerSearchRepository;
import com.team2final.minglecrm.statistics.dto.response.customer.RevisitCustomerStatisticsResponse;
import com.team2final.minglecrm.statistics.dto.response.customer.StatisticsCustomerResponse;
import com.team2final.minglecrm.statistics.dto.response.customer.VisitCustomerResponse;
import com.team2final.minglecrm.customer.domain.Customer;
import com.team2final.minglecrm.statistics.domain.repository.customer.FrequentCustomerRepository;
import com.team2final.minglecrm.statistics.domain.repository.customer.StatisticsCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsCustomerService {

    private final StatisticsCustomerRepository statisticsCustomerRepository;
    private final FrequentCustomerRepository frequentCustomerRepository;
    private final CustomerSearchRepository customerSearchRepository;

    private Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public List<StatisticsCustomerResponse> findNewCustomers(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        pageable = pageable == null ? PageRequest.of(0, 3) : pageable;
        Page<Customer> customers = statisticsCustomerRepository.findByCreatedDateBetween(startDate, endDate, pageable);
        return customers.stream()
                .map(customer -> new StatisticsCustomerResponse(
                        customer.getId(),
                        customer.getName(),
                        customer.getPhone(),
                        customer.getEmployee() != null ? customer.getEmployee().getName() : null,
                        customer.getCreatedDate(),
                        customer.getMemo(),
                        customer.getGrade(),
                        customer.getAddress(),
                        customer.getGender(),
                        customer.getVisitCnt(),
                        customer.getBirth(),
                        customer.getReward() != null ? customer.getReward().getAmount() : null
                ))
                .collect(Collectors.toList());
    }

    public List<VisitCustomerResponse> findCustomersByReservationDateBetween(LocalDate startLocalDate, LocalDate endLocalDate, Pageable pageable) {
        Page<Customer> customers = statisticsCustomerRepository.findCustomersByReservationDateBetween(startLocalDate, endLocalDate, pageable);
        return customers.stream()
                .map(customer -> customer.getRoomReservations().stream()
                        .map(reservation -> VisitCustomerResponse.builder()
                                .id(customer.getId())
                                .name(customer.getName())
                                .phone(customer.getPhone())
                                .employeeName(customer.getEmployee() != null ? customer.getEmployee().getName() : null)
                                .createdDate(customer.getCreatedDate())
                                .grade(customer.getGrade())
                                .address(customer.getAddress())
                                .memo(customer.getMemo())
                                .gender(customer.getGender())
                                .birth(customer.getBirth())
                                .visitStartDate(reservation.getStartDate())
                                .visitEndDate(reservation.getEndDate())
                                .build()
                        )
                        .collect(Collectors.toList())
                )
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    // 페이징처리 x
    public List<VisitCustomerResponse> findCustomersByReservationDateBetween(LocalDate startLocalDate, LocalDate endLocalDate) {
        List<Customer> customers = statisticsCustomerRepository.findCustomersByReservationDateBetween(startLocalDate, endLocalDate);
        return customers.stream()
                .map(customer -> customer.getRoomReservations().stream()
                        .map(reservation -> VisitCustomerResponse.builder()
                                .id(customer.getId())
                                .name(customer.getName())
                                .phone(customer.getPhone())
                                .employeeName(customer.getEmployee() != null ? customer.getEmployee().getName() : null)
                                .createdDate(customer.getCreatedDate())
                                .grade(customer.getGrade())
                                .address(customer.getAddress())
                                .memo(customer.getMemo())
                                .gender(customer.getGender())
                                .birth(customer.getBirth())
                                .visitStartDate(reservation.getStartDate())
                                .visitEndDate(reservation.getEndDate())
                                .build()
                        )
                        .collect(Collectors.toList())
                )
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public List<StatisticsCustomerResponse> findByVisitCntGreaterThan(Integer visitCnt, Pageable pageable) {
        Page<Customer> customers = statisticsCustomerRepository.findByVisitCntGreaterThan(visitCnt, pageable);
        return customers.stream()
                .map(customer -> new StatisticsCustomerResponse(
                        customer.getId(),
                        customer.getName(),
                        customer.getPhone(),
                        customer.getEmployee() != null ? customer.getEmployee().getName() : null,
                        customer.getCreatedDate(),
                        customer.getMemo(),
                        customer.getGrade(),
                        customer.getAddress(),
                        customer.getGender(),
                        customer.getVisitCnt(),
                        customer.getBirth(),
                        customer.getReward() != null ? customer.getReward().getAmount() : null
                ))
                .collect(Collectors.toList());
    }

    public double calculateRevisitRate() {
        long totalCustomers = statisticsCustomerRepository.countByIsDeletedFalse();
        long frequentCustomers = frequentCustomerRepository.count();

        if (totalCustomers == 0) {
            return 0.0;
        }

        return ((double) frequentCustomers / totalCustomers) * 100;
    }

    public Map<String, Double> calculateRevisitRateByGender() {
        Map<String, Double> revisitRateByGender = new HashMap<>();
        String[] genders = {"Male", "Female"};

        for (String gender : genders) {
            long totalCustomersByGender = statisticsCustomerRepository.countByGender(gender);
            long frequentCustomersByGender = frequentCustomerRepository.countByGender(gender);

            if (totalCustomersByGender == 0) {
                revisitRateByGender.put(gender, 0.0);
            } else {
                double rate = ((double) frequentCustomersByGender / totalCustomersByGender * 100);
                revisitRateByGender.put(gender, rate);
            }
        }
        return revisitRateByGender;
    }

    public Map<String, Double> calculateRevisitRateByGrade() {
        Map<String, Double> revisitRateByGrade = new HashMap<>();
        String[] grades = {"BROWN", "SILVER", "GOLD", "DIAMOND"};

        for (String grade : grades) {
            long totalCustomersByGrade = statisticsCustomerRepository.countByGrade(grade);
            long frequentCustomersByGender = frequentCustomerRepository.countByGrade(grade);

            if (totalCustomersByGrade == 0) {
                revisitRateByGrade.put(grade, 0.0);
            } else {
                double rate = ((double) frequentCustomersByGender / totalCustomersByGrade * 100);
                revisitRateByGrade.put(grade, rate);
            }
        }
        return revisitRateByGrade;
    }

    public RevisitCustomerStatisticsResponse getRevisitCustomerStatistics() {
        return customerSearchRepository.findRevisitCustomerStatistics();
    }
    public RevisitCustomerStatisticsResponse getVisitCustomerStatistics() {
        return customerSearchRepository.findVisitCustomerStatistics();
    }
}
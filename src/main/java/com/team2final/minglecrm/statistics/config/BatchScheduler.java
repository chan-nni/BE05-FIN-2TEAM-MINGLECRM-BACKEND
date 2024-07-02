
package com.team2final.minglecrm.statistics.config;

import com.team2final.minglecrm.service.email.EmailSendService;
import com.team2final.minglecrm.statistics.domain.BirthdayReminderCustomers;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BatchScheduler {

    @PersistenceContext
    private EntityManager entityManager;

    private final JobLauncher jobLauncher;
    private final Job reservationStatisticsJob;
    private final Job birthdayReminderJob;
    private final Job importFrequentCustomerJob;
    private final Job reservationRoomJob;
    private final Job purchaseItemJob;
    private final EmailSendService emailSendService;

    //    @Scheduled(cron = "0 * * * * ?")
    @Scheduled(cron = "0 0 0/1 * * *")
    public void runImportFrequentCustomerJob() {
        try {
            jobLauncher.run(importFrequentCustomerJob, new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    @Scheduled(cron = "0 * * * * ?")
    @Scheduled(cron = "0 0 0/1 * * *")
    public void runReservationRoomJob() {
        try {
            jobLauncher.run(reservationRoomJob, new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    @Scheduled(fixedDelay = 10000)
//    @Scheduled(cron = "0 * * * * ?")
    @Scheduled(cron = "0 0 0/1 * * *")
    public void runReservationStatisticsJob() {
        try {
            jobLauncher.run(reservationStatisticsJob, new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    @Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(cron = "0 0 0/1 * * *")
    public void runPurchaseItemJob() {
        try {
            jobLauncher.run(purchaseItemJob, new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Scheduled(cron = "0 0 0/1 * * *")
    public void runBirthdayReminderJob() {
        try {
            jobLauncher.run(birthdayReminderJob, new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //    @Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(cron = "0 0 0/1 * * *")
    public void birthdayReminderCustomersSendEmail() {
        List<BirthdayReminderCustomers> customers = entityManager.createQuery(
                        "SELECT b FROM BirthdayReminderCustomers b", BirthdayReminderCustomers.class)
                .getResultList();

        for (BirthdayReminderCustomers customer : customers) {
            try {
                emailSendService.sendMail(customer.getEmail(), "다가오는 생일을 축하합니다.",
                        customer.getName() + "님, " + ",\n\n다가오는 생일을 축하드리며 생일기념 바우처를 드립니다.", null);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
}

//package com.team2final.minglecrm.statistics.config;
//
//import com.team2final.minglecrm.service.email.EmailSendService;
//import com.team2final.minglecrm.statistics.entity.BirthdayReminderCustomers;
//import jakarta.mail.MessagingException;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class BatchScheduler {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    private final JobLauncher jobLauncher;
//    private final Job reservationStatisticsJob;
//    private final Job birthdayReminderJob;
//    private final Job importFrequentCustomerJob;
//    private final Job reservationRoomJob;
//    private final EmailSendService emailSendService;
//
//    //    @Scheduled(cron = "0 * * * * ?")
//    @Scheduled(cron = "0 0 0/1 * * *")
//    public void runImportFrequentCustomerJob() {
//        try {
//            jobLauncher.run(importFrequentCustomerJob, new JobParametersBuilder()
//                    .addLong("time", System.currentTimeMillis())
//                    .toJobParameters());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    //    @Scheduled(cron = "0 * * * * ?")
//    @Scheduled(cron = "0 0 0/1 * * *")
//    public void runReservationRoomJob() {
//        try {
//            jobLauncher.run(reservationRoomJob, new JobParametersBuilder()
//                    .addLong("time", System.currentTimeMillis())
//                    .toJobParameters());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    //    @Scheduled(fixedDelay = 10000)
////    @Scheduled(cron = "0 * * * * ?")
//    @Scheduled(cron = "0 0 0/1 * * *")
//    public void runReservationStatisticsJob() {
//        try {
//            jobLauncher.run(reservationStatisticsJob, new JobParametersBuilder()
//                    .addLong("time", System.currentTimeMillis())
//                    .toJobParameters());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    //    @Scheduled(cron = "0 0 0 * * ?")
//    @Scheduled(cron = "0 0 0/1 * * *")
//    public void runBirthdayReminderJob() {
//        try {
//            jobLauncher.run(birthdayReminderJob, new JobParametersBuilder()
//                    .addLong("time", System.currentTimeMillis())
//                    .toJobParameters());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    //    @Scheduled(cron = "0 0 0 * * ?")
//    @Scheduled(cron = "0 0 0/1 * * *")
//    public void birthdayReminderCustomersSendEmail() {
//        List<BirthdayReminderCustomers> customers = entityManager.createQuery(
//                        "SELECT b FROM BirthdayReminderCustomers b", BirthdayReminderCustomers.class)
//                .getResultList();
//
//        for (BirthdayReminderCustomers customer : customers) {
//            try {
//                emailSendService.sendMail(customer.getEmail(), "다가오는 생일을 축하합니다.",
//                        customer.getName() + "님, " + ",\n\n다가오는 생일을 축하드리며 생일기념 바우처를 드립니다.");
//            } catch (MessagingException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}


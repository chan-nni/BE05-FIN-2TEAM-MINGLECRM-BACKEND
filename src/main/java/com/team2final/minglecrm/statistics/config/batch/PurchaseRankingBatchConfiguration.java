package com.team2final.minglecrm.statistics.config.batch;

import com.team2final.minglecrm.reservation.domain.dining.DishReservation;
import com.team2final.minglecrm.reservation.domain.dining.DishReservationDetail;
import com.team2final.minglecrm.reservation.domain.hotel.RoomReservation;
import com.team2final.minglecrm.statistics.config.support.JobCompletionNotificationListener;
import com.team2final.minglecrm.statistics.domain.PurchaseDish;
import com.team2final.minglecrm.statistics.domain.PurchaseList;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class PurchaseRankingBatchConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final EntityManagerFactory entityManagerFactory;


    @Bean
    public Job purchaseItemJob(JobCompletionNotificationListener listener, Step purchaseDishStep, Step purchaseDishDeleteStep, Step purchaseListDeleteStep, Step purchaseDishListStep, Step purchaseRoomListStep) {
        return new JobBuilder("purchaseItemJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(purchaseDishDeleteStep)
                .next(purchaseDishStep)
                .start(purchaseListDeleteStep)
                .next(purchaseDishListStep)
                .next(purchaseRoomListStep)
                .build();
    }
    @Bean
    public Step purchaseDishDeleteStep() {
        return new StepBuilder("purchaseDishDeleteStep", jobRepository)
                .tasklet(purchaseDishDeleteTasklet(), platformTransactionManager)
                .build();
    }

    @Bean
    public Tasklet purchaseDishDeleteTasklet() {
        return (contribution, chunkContext) -> {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                entityManager.createQuery("DELETE FROM PurchaseDish").executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            } finally {
                entityManager.close();
            }
            return RepeatStatus.FINISHED;
        };
    }


    @Bean
    public Step purchaseDishStep() {
        return new StepBuilder("purchaseDishStep", jobRepository)
                .<DishReservation, PurchaseDish>chunk(10, platformTransactionManager)
                .reader(purchaseDishReader())
                .processor(purchaseDishProcessor())
                .writer(purchaseDishWriter())
                .build();
    }


    @Bean
    public JpaCursorItemReader<DishReservation> purchaseDishReader() {
        return new JpaCursorItemReaderBuilder<DishReservation>()
                .name("purchaseDishReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT r FROM DishReservation r")
                .build();
    }

    @Bean
    public ItemProcessor<DishReservation, PurchaseDish> purchaseDishProcessor() {
        return dishReservation -> {
            Map<String, List<DishReservationDetail>> groupedByDish = dishReservation.getDishReservationDetails().stream()
                    .collect(Collectors.groupingBy(detail -> detail.getDish().getName()));

            for (Map.Entry<String, List<DishReservationDetail>> entry : groupedByDish.entrySet()) {
                String dishName = entry.getKey();
                List<DishReservationDetail> details = entry.getValue();

                Integer totalQuantity = details.stream().mapToInt(DishReservationDetail::getQuantity).sum();
                Long totalAmount = details.stream().mapToLong(DishReservationDetail::getItemTotalPrice).sum();

                return PurchaseDish.builder()
                        .purchaseDate(dishReservation.getReservationDate().toLocalDate())
                        .name(dishName)
                        .quantity(totalQuantity)
                        .amount(totalAmount)
                        .build();
            }
            return null;
        };
    }

    @Bean
    public ItemWriter<PurchaseDish> purchaseDishWriter() {
        return items -> {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            try {
                for (PurchaseDish item : items) {
                    entityManager.persist(item);
                }
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            } finally {
                entityManager.close();
            }
        };
    }

    @Bean
    public Step purchaseListDeleteStep() {
        return new StepBuilder("purchaseListDeleteStep", jobRepository)
                .tasklet(purchaseRankDeleteTasklet(), platformTransactionManager)
                .build();
    }

    @Bean
    public Tasklet purchaseRankDeleteTasklet() {
        return (contribution, chunkContext) -> {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                entityManager.createQuery("DELETE FROM PurchaseList").executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            } finally {
                entityManager.close();
            }
            return RepeatStatus.FINISHED;
        };
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////

    @Bean
    public Step purchaseDishListStep() {
        return new StepBuilder("purchaseDishListStep", jobRepository)
                .<DishReservation, PurchaseList>chunk(10, platformTransactionManager)
                .reader(dishPurchaseReader())
                .processor(dishPurchaseProcessor())
                .writer(purchaseDishListWriter())
                .build();
    }


    @Bean
    public JpaCursorItemReader<DishReservation> dishPurchaseReader() {
        return new JpaCursorItemReaderBuilder<DishReservation>()
                .name("dishPurchaseReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT r FROM DishReservation r")
                .build();
    }

    @Bean
    public ItemProcessor<DishReservation, PurchaseList> dishPurchaseProcessor() {
        return dishReservation -> {
            List<PurchaseList> purchaseLists = dishReservation.getDishReservationDetails().stream().map(detail -> {
                String consumeType = "Dish";
                String dishName = detail.getDish().getName();
                return PurchaseList.builder()
                        .purchaseDate(dishReservation.getReservationDate().toLocalDate())
                        .customerName(dishReservation.getCustomer().getName())
                        .customerGrade(dishReservation.getCustomer().getGrade())
                        .customerGender(dishReservation.getCustomer().getGender())
                        .consumeType(consumeType)
                        .dishName(dishName)
                        .build();
            }).collect(Collectors.toList());

            return purchaseLists.isEmpty() ? null : purchaseLists.get(0);
        };
    }

    @Bean
    public Step purchaseRoomListStep() {
        return new StepBuilder("purchaseRoomListStep", jobRepository)
                .<RoomReservation, PurchaseList>chunk(10, platformTransactionManager)
                .reader(roomReservationReader())
                .processor(roomReservationProcessor())
                .writer(purchaseRoomListWriter())
                .build();
    }

    @Bean
    public JpaCursorItemReader<RoomReservation> roomReservationReader() {
        return new JpaCursorItemReaderBuilder<RoomReservation>()
                .name("roomReservationReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT r FROM RoomReservation r")
                .build();
    }

    @Bean
    public ItemProcessor<RoomReservation, PurchaseList> roomReservationProcessor() {
        return roomReservation -> {
            String consumeType = "Room";
            String roomType = roomReservation.getHotelRoom().getRoomType().name();
            return PurchaseList.builder()
                    .purchaseDate(roomReservation.getReservationDate().toLocalDate())
                    .customerName(roomReservation.getCustomer().getName())
                    .customerGrade(roomReservation.getCustomer().getGrade())
                    .customerGender(roomReservation.getCustomer().getGender())
                    .consumeType(consumeType)
                    .roomType(roomType)
                    .build();
        };
    }

    @Bean
    public ItemWriter<PurchaseList> purchaseDishListWriter() {
        return new JpaItemWriterBuilder<PurchaseList>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }


    @Bean
    public ItemWriter<PurchaseList> purchaseRoomListWriter() {
        return new JpaItemWriterBuilder<PurchaseList>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

}
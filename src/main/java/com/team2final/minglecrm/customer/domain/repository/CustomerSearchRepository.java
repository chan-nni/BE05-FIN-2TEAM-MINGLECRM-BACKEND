package com.team2final.minglecrm.customer.domain.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team2final.minglecrm.customer.domain.QCustomer;
import com.team2final.minglecrm.customer.dto.request.CustomerSearchCondition;
import com.team2final.minglecrm.customer.dto.response.CustomerResponse;
import com.team2final.minglecrm.customer.dto.response.QCustomerResponse;
import com.team2final.minglecrm.statistics.dto.response.customer.QRevisitCustomerStatisticsResponse;
import com.team2final.minglecrm.statistics.dto.response.customer.RevisitCustomerStatisticsResponse;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class CustomerSearchRepository {

    private final JPAQueryFactory queryFactory;

    public CustomerSearchRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Page<CustomerResponse> search(CustomerSearchCondition condition, Pageable pageable) {
        QCustomer customer = QCustomer.customer;

        List<CustomerResponse> results = queryFactory
                .select(new QCustomerResponse(
                        customer.id,
                        customer.name,
                        customer.email,
                        customer.phone,
                        customer.employee.name,
                        customer.grade,
                        customer.gender,
                        customer.birth
                ))
                .from(customer)
                .where(
                        customerNameEq(condition.getCustomerName()),
                        customerGradeEq(condition.getGrade()),
                        genderEq(condition.getGender()),
                        customerEmailEq(condition.getEmail())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCount = queryFactory
                .select(customer)
                .from(customer)
                .where(
                        customerNameEq(condition.getCustomerName()),
                        customerGradeEq(condition.getGrade()),
                        genderEq(condition.getGender()),
                        customerEmailEq(condition.getEmail())
                )
                .fetchCount();

        return new PageImpl<>(results, pageable, totalCount);
    }

    public RevisitCustomerStatisticsResponse findRevisitCustomerStatistics() {

        QCustomer customer = QCustomer.customer;

        BooleanBuilder builder = new BooleanBuilder();


        NumberTemplate<Integer> age = Expressions.numberTemplate(Integer.class,
                "TIMESTAMPDIFF(YEAR, {0}, {1})", customer.birth, LocalDate.now());


        return queryFactory
                .select(new QRevisitCustomerStatisticsResponse(
                        Expressions.asNumber(
                                new CaseBuilder()
                                        .when(age.between(20, 29).and(customer.gender.eq("Male"))).then(1)
                                        .otherwise(0).sum().intValue()
                        ),
                        Expressions.asNumber(
                                new CaseBuilder()
                                        .when(age.between(20, 29).and(customer.gender.eq("Female"))).then(1)
                                        .otherwise(0).sum().intValue()
                        ),
                        Expressions.asNumber(
                                new CaseBuilder()
                                        .when(age.between(30, 39).and(customer.gender.eq("Male"))).then(1)
                                        .otherwise(0).sum().intValue()
                        ),
                        Expressions.asNumber(
                                new CaseBuilder()
                                        .when(age.between(30, 39).and(customer.gender.eq("Female"))).then(1)
                                        .otherwise(0).sum().intValue()
                        ),
                        Expressions.asNumber(
                                new CaseBuilder()
                                        .when(age.between(40, 49).and(customer.gender.eq("Male"))).then(1)
                                        .otherwise(0).sum().intValue()
                        ),
                        Expressions.asNumber(
                                new CaseBuilder()
                                        .when(age.between(40, 49).and(customer.gender.eq("Female"))).then(1)
                                        .otherwise(0).sum().intValue()
                        ),
                        Expressions.asNumber(
                                new CaseBuilder()
                                        .when(age.between(50, 59).and(customer.gender.eq("Male"))).then(1)
                                        .otherwise(0).sum().intValue()
                        ),
                        Expressions.asNumber(
                                new CaseBuilder()
                                        .when(age.between(50, 59).and(customer.gender.eq("Female"))).then(1)
                                        .otherwise(0).sum().intValue()
                        ),
                        Expressions.asNumber(
                                new CaseBuilder()
                                        .when(age.notBetween(0, 59).and(customer.gender.eq("Male"))).then(1)
                                        .otherwise(0).sum().intValue()
                        ),
                        Expressions.asNumber(
                                new CaseBuilder()
                                        .when(age.notBetween(0, 59).and(customer.gender.eq("Female"))).then(1)
                                        .otherwise(0).sum().intValue()
                        )
                ))
                .from(customer)
                .where(customer.visitCnt.gt(1))
                .fetchOne();

    }

    public RevisitCustomerStatisticsResponse findVisitCustomerStatistics() {

        QCustomer customer = QCustomer.customer;

        BooleanBuilder builder = new BooleanBuilder();


        NumberTemplate<Integer> age = Expressions.numberTemplate(Integer.class,
                "TIMESTAMPDIFF(YEAR, {0}, {1})", customer.birth, LocalDate.now());


        return queryFactory
                .select(new QRevisitCustomerStatisticsResponse(
                        Expressions.asNumber(
                                new CaseBuilder()
                                        .when(age.between(20, 29).and(customer.gender.eq("Male"))).then(1)
                                        .otherwise(0).sum().intValue()
                        ),
                        Expressions.asNumber(
                                new CaseBuilder()
                                        .when(age.between(20, 29).and(customer.gender.eq("Female"))).then(1)
                                        .otherwise(0).sum().intValue()
                        ),
                        Expressions.asNumber(
                                new CaseBuilder()
                                        .when(age.between(30, 39).and(customer.gender.eq("Male"))).then(1)
                                        .otherwise(0).sum().intValue()
                        ),
                        Expressions.asNumber(
                                new CaseBuilder()
                                        .when(age.between(30, 39).and(customer.gender.eq("Female"))).then(1)
                                        .otherwise(0).sum().intValue()
                        ),
                        Expressions.asNumber(
                                new CaseBuilder()
                                        .when(age.between(40, 49).and(customer.gender.eq("Male"))).then(1)
                                        .otherwise(0).sum().intValue()
                        ),
                        Expressions.asNumber(
                                new CaseBuilder()
                                        .when(age.between(40, 49).and(customer.gender.eq("Female"))).then(1)
                                        .otherwise(0).sum().intValue()
                        ),
                        Expressions.asNumber(
                                new CaseBuilder()
                                        .when(age.between(50, 59).and(customer.gender.eq("Male"))).then(1)
                                        .otherwise(0).sum().intValue()
                        ),
                        Expressions.asNumber(
                                new CaseBuilder()
                                        .when(age.between(50, 59).and(customer.gender.eq("Female"))).then(1)
                                        .otherwise(0).sum().intValue()
                        ),
                        Expressions.asNumber(
                                new CaseBuilder()
                                        .when(age.notBetween(0, 59).and(customer.gender.eq("Male"))).then(1)
                                        .otherwise(0).sum().intValue()
                        ),
                        Expressions.asNumber(
                                new CaseBuilder()
                                        .when(age.notBetween(0, 59).and(customer.gender.eq("Female"))).then(1)
                                        .otherwise(0).sum().intValue()
                        )
                ))
                .from(customer)
                .where(customer.visitCnt.gt(0))
                .fetchOne();

    }

    private BooleanExpression customerEmailEq(String customerEmail) {
        return customerEmail != null ? QCustomer.customer.email.contains(customerEmail) : null;
    }

    private BooleanExpression customerNameEq(String customerName) {
        return customerName != null ? QCustomer.customer.name.contains(customerName) : null;
    }

    private BooleanExpression customerGradeEq(String grade) {
        return grade != null ? QCustomer.customer.grade.eq(grade) : null;
    }

    private BooleanExpression genderEq(String gender) {
        return gender != null ? QCustomer.customer.gender.eq(gender) : null;
    }



}

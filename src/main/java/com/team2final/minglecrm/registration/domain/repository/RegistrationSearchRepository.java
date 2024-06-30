package com.team2final.minglecrm.registration.domain.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team2final.minglecrm.registration.domain.QRegistration;
import com.team2final.minglecrm.registration.domain.type.RequestStatus;
import com.team2final.minglecrm.registration.dto.request.RegistrationSearchCondition;
import com.team2final.minglecrm.registration.dto.response.QRegistrationResponse;
import com.team2final.minglecrm.registration.dto.response.RegistrationResponse;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RegistrationSearchRepository {

    private final JPAQueryFactory queryFactory;

    public RegistrationSearchRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Page<RegistrationResponse> search(RegistrationSearchCondition condition, Pageable pageable) {
        QRegistration registration = QRegistration.registration;


        List<RegistrationResponse> results = queryFactory
                .select(new QRegistrationResponse(
                        registration.name,
                        registration.email,
                        registration.approvalManagerName,
                        registration.requestedRole,
                        registration.status,
                        registration.registrationRequestTime
                ))
                .from(registration)
                .where(
                        nameEq(condition.getName()),
                        requestedRoleEq(condition.getRequestedRole()),
                        statusIn(condition.getStatus())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long countQuery = queryFactory
                .select(registration.count())
                .from(registration)
                .where(
                        nameEq(condition.getName()),
                        requestedRoleEq(condition.getRequestedRole()),
                        statusIn(condition.getStatus()))
                .fetchCount();

        return new PageImpl<>(results, pageable, countQuery);
    }


    private BooleanExpression nameEq(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        return QRegistration.registration.name.eq(name);
    }

    private BooleanExpression requestedRoleEq(String requestedRole) {
        if (requestedRole == null) {
            return null;
        }
        return QRegistration.registration.requestedRole.contains(requestedRole);
    }

    private BooleanExpression statusIn(RequestStatus status) {
        if (status == null) {
            return null;
        }
        return QRegistration.registration.status.in(status);
    }


}

package com.team2final.minglecrm.registration.domain.repository;

import com.team2final.minglecrm.registration.domain.Registration;
import com.team2final.minglecrm.registration.domain.type.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    Page<Registration> findByStatusOrderByRegistrationRequestTimeDesc(RequestStatus requestStatus, Pageable pageable);

    Registration findByEmail(String email);

    Long countByStatus(RequestStatus pending);
}

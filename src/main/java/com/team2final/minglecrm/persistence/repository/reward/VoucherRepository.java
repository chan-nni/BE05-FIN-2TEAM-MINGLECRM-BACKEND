package com.team2final.minglecrm.persistence.repository.reward;

import com.team2final.minglecrm.entity.reward.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {

    List<Voucher> findAllByIsRequested(boolean isRequest);

}

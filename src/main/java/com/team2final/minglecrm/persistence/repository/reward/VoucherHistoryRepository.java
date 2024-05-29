package com.team2final.minglecrm.persistence.repository.reward;

import com.team2final.minglecrm.entity.reward.VoucherHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherHistoryRepository extends JpaRepository<VoucherHistory, Long> {
}

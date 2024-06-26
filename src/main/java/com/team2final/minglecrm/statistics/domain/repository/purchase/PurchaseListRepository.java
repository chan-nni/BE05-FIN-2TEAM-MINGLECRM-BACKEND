package com.team2final.minglecrm.statistics.domain.repository.purchase;

import com.team2final.minglecrm.statistics.domain.PurchaseList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseListRepository extends JpaRepository<PurchaseList, Long>, PurchaseListRepositoryCustom {
}
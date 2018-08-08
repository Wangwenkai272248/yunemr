package jhmk.clinic.entity.pojo.repository;

import jhmk.clinic.entity.pojo.PatVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author ziyu.zhou
 * @date 2018/7/6 17:32
 */

public interface PatVisitRepository extends JpaRepository<PatVisit, Long>,JpaSpecificationExecutor<PatVisit> {
}

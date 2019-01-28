package jhmk.clinic.entity.pojo.repository;

import jhmk.clinic.entity.pojo.DiagnosisReqLog;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DiagnosisReqLogRepository extends PagingAndSortingRepository<DiagnosisReqLog, Integer>, JpaSpecificationExecutor<DiagnosisReqLog> {

}

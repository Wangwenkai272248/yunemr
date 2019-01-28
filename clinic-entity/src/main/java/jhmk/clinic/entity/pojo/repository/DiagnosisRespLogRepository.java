package jhmk.clinic.entity.pojo.repository;

import jhmk.clinic.entity.pojo.DiagnosisRespLog;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DiagnosisRespLogRepository extends PagingAndSortingRepository<DiagnosisRespLog, Integer>, JpaSpecificationExecutor<DiagnosisRespLog> {

}

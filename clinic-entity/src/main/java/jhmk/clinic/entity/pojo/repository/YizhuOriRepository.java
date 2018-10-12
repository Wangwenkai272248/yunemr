package jhmk.clinic.entity.pojo.repository;

import jhmk.clinic.entity.bean.Yizhu;
import jhmk.clinic.entity.pojo.YizhuOri;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author ziyu.zhou
 * @date 2018/7/6 17:32
 */

public interface YizhuOriRepository extends JpaRepository<YizhuOri, Integer>, JpaSpecificationExecutor<YizhuOri> {

    List<YizhuOri>findAllByPatientIdAndVisitId(String patient_id,String visit_id);

    Yizhu findByFId(Integer fid);
}

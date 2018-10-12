package jhmk.clinic.entity.pojo.repository;

import jhmk.clinic.entity.pojo.YizhuChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author ziyu.zhou
 * @date 2018/7/6 17:32
 */

public interface YizhuChangeRepository extends JpaRepository<YizhuChange, Integer>, JpaSpecificationExecutor<YizhuChange> {

    List<YizhuChange> findAllByBId(String bid);
    List<YizhuChange> findAllByBIdAndNum(String bid,int num);


}

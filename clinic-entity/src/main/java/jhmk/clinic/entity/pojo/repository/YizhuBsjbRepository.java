package jhmk.clinic.entity.pojo.repository;

import jhmk.clinic.entity.pojo.YizhuBsjb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author ziyu.zhou
 * @date 2018/7/6 17:32
 */

public interface YizhuBsjbRepository extends JpaRepository<YizhuBsjb, Integer>, JpaSpecificationExecutor<YizhuBsjb> {

    List<YizhuBsjb> findAllByBId(String bid);
    List<YizhuBsjb> findAllByBIdAndNum(String bid, int num);
    void  deleteAllByBIdAndNum(String bid,int num);

}

package jhmk.clinic.entity.pojo.repository;

import jhmk.clinic.entity.pojo.YizhuResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author ziyu.zhou
 * @date 2018/7/6 17:32
 */

public interface YizhuResultRepository extends JpaRepository<YizhuResult, Integer>, JpaSpecificationExecutor<YizhuResult> {

    List<YizhuResult> findAllByBId(String bid);
    List<YizhuResult> findAllByBIdAndNum(String bid,int num);

    @Query("select  max (y.num) from YizhuResult  y where y.bId=?1")
    int getMaxBid(String bid);

    @Query("select  distinct (y.mainIllName) from YizhuResult  y where  y.mainIllName<>null")
    List<String> getDistinctIllName();

    @Query("select  distinct (y.bId) from YizhuResult  y where  y.mainIllName=?1")
    List<String> getDistinctBidByIllName(String illName);

}

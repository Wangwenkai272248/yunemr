package jhmk.clinic.entity.service;

import jhmk.clinic.entity.base.BaseRepService;
import jhmk.clinic.entity.pojo.YizhuResult;
import jhmk.clinic.entity.pojo.repository.YizhuResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ziyu.zhou
 * @date 2018/8/6 13:55
 */
@Service
public class YizhuResultRepService extends BaseRepService<YizhuResult, Integer> {
    @Autowired
    YizhuResultRepository repository;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public YizhuResult save(YizhuResult basicInfo) {
        return repository.save(basicInfo);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Iterable<YizhuResult> save(List<YizhuResult> list) {
        return repository.save(list);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(YizhuResult YizhuResult) {
        repository.delete(YizhuResult);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(List<YizhuResult> list) {
        repository.delete(list);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public YizhuResult findOne(Integer id) {
        return repository.findOne(id);
    }


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Iterable<YizhuResult> findAll() {
        return repository.findAll();
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<YizhuResult> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<YizhuResult> findAllByBId(String bid) {
        return repository.findAllByBId(bid);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<YizhuResult> findAllByBIdAndNum(String bid, int num) {
        return repository.findAllByBIdAndNum(bid,num);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public int getMaxBid(String bid) {
        return repository.getMaxBid(bid);
    }

}

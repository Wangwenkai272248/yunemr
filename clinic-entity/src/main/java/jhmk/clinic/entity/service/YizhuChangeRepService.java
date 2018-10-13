package jhmk.clinic.entity.service;

import jhmk.clinic.entity.base.BaseRepService;
import jhmk.clinic.entity.pojo.YizhuChange;
import jhmk.clinic.entity.pojo.repository.YizhuChangeRepository;
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
public class YizhuChangeRepService extends BaseRepService<YizhuChange, Integer> {
    @Autowired
    YizhuChangeRepository repository;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public YizhuChange save(YizhuChange basicInfo) {
        return repository.save(basicInfo);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Iterable<YizhuChange> save(List<YizhuChange> list) {
        return repository.save(list);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(YizhuChange YizhuChange) {
        repository.delete(YizhuChange);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(List<YizhuChange> list) {
        repository.delete(list);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public YizhuChange findOne(Integer id) {
        return repository.findOne(id);
    }


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Iterable<YizhuChange> findAll() {
        return repository.findAll();
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<YizhuChange> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<YizhuChange> findAllByBId(String bid) {
        return repository.findAllByBId(bid);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<YizhuChange> findAllByBIdAndNum(String bid, int num) {
        return repository.findAllByBIdAndNum(bid, num);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteAllByBIdAndNum(String bid, int num) {
        repository.deleteAllByBIdAndNum(bid, num);
    }

}

package jhmk.clinic.entity.service;

import jhmk.clinic.entity.base.BaseRepService;
import jhmk.clinic.entity.pojo.YizhuOri;
import jhmk.clinic.entity.pojo.repository.YizhuOriRepository;
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
public class YizhuOriRepService extends BaseRepService<YizhuOri, Integer> {
    @Autowired
    YizhuOriRepository repository;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public YizhuOri save(YizhuOri basicInfo) {
        return repository.save(basicInfo);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Iterable<YizhuOri> save(List<YizhuOri> list) {
        return repository.save(list);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(YizhuOri YizhuOri) {
        repository.delete(YizhuOri);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(List<YizhuOri> list) {
        repository.delete(list);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public YizhuOri findOne(Integer id) {
        return repository.findOne(id);
    }


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Iterable<YizhuOri> findAll() {
        return repository.findAll();
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<YizhuOri> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }


}

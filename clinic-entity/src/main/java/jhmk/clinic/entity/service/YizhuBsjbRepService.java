package jhmk.clinic.entity.service;

import jhmk.clinic.entity.base.BaseRepService;
import jhmk.clinic.entity.pojo.YizhuBsjb;
import jhmk.clinic.entity.pojo.repository.YizhuBsjbRepository;
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
public class YizhuBsjbRepService extends BaseRepService<YizhuBsjb, Integer> {
    @Autowired
    YizhuBsjbRepository repository;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public YizhuBsjb save(YizhuBsjb basicInfo) {
        return repository.save(basicInfo);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Iterable<YizhuBsjb> save(List<YizhuBsjb> list) {
        return repository.save(list);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(YizhuBsjb YizhuBsjb) {
        repository.delete(YizhuBsjb);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(List<YizhuBsjb> list) {
        repository.delete(list);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public YizhuBsjb findOne(Integer id) {
        return repository.findOne(id);
    }


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Iterable<YizhuBsjb> findAll() {
        return repository.findAll();
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<YizhuBsjb> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<YizhuBsjb> findAllByBId(String bid) {
        return repository.findAllByBId(bid);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<YizhuBsjb> findAllByBIdAndNum(String bid, int num) {
        return repository.findAllByBIdAndNum(bid,num);
    }

}

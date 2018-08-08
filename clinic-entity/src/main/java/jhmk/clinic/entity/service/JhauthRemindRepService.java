package jhmk.clinic.entity.service;

import jhmk.clinic.entity.base.BaseRepService;
import jhmk.clinic.entity.pojo.JhauthRemind;
import jhmk.clinic.entity.pojo.repository.JhauthRemindRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author ziyu.zhou
 * @date 2018/8/6 13:55
 */
@Service
public class JhauthRemindRepService extends BaseRepService<JhauthRemind, Integer> {
    @Autowired
    JhauthRemindRepository repository;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public JhauthRemind save(JhauthRemind basicInfo) {
        return repository.save(basicInfo);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Iterable<JhauthRemind> save(List<JhauthRemind> list) {
        return repository.save(list);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(JhauthRemind JhauthRemind) {
        repository.delete(JhauthRemind);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(List<JhauthRemind> list) {
        repository.delete(list);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public JhauthRemind findOne(Integer id) {
        return repository.findOne(id);
    }


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Iterable<JhauthRemind> findAll() {
        return repository.findAll();
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<JhauthRemind> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<JhauthRemind> findJhauthRemind(String doctorId, String patiendId, String remindStatus, Date remindTime) {
        return repository.findJhauthRemind(doctorId, patiendId, remindStatus, remindTime);
    }


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<JhauthRemind> findJhauthRemindByDoctorId(String doctorId, String remindStatus) {
        return repository.findJhauthRemindByDoctorId(doctorId, remindStatus);
    }


    /**
     * 修改状态
     *
     * @param status
     * @param id
     */


    @Transactional(propagation = Propagation.SUPPORTS)
    public void updateStatus(String status, int id) {
        repository.updateStatus(status, id);
    }


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<JhauthRemind> findAllJhauthRemindByRemindTime(String doctorId, String remindStatus,Date remindTime) {
        return repository.findAllJhauthRemindByRemindTime(doctorId, remindStatus,remindTime);
    }

}

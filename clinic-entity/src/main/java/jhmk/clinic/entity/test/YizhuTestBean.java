package jhmk.clinic.entity.test;

import jhmk.clinic.entity.pojo.YizhuBsjb;
import jhmk.clinic.entity.pojo.YizhuChange;
import jhmk.clinic.entity.pojo.YizhuResult;

import java.util.List;

/**
 * @author ziyu.zhou
 * @date 2018/10/11 11:21
 */

public class YizhuTestBean {
    //病历id
    private String bid;
    //次数
    private int num;
    private List<YizhuResult> resultList;
    private List<YizhuChange> changeList;
    private List<YizhuBsjb>bsjbList;
    private YizhuTestBean childYizhuTestBean;

    public String getBid() {
        return bid;
    }

    public void setBid(final String bid) {
        this.bid = bid;
    }

    public int getNum() {
        return num;
    }

    public void setNum(final int num) {
        this.num = num;
    }

    public List<YizhuResult> getResultList() {
        return resultList;
    }

    public void setResultList(final List<YizhuResult> resultList) {
        this.resultList = resultList;
    }

    public List<YizhuChange> getChangeList() {
        return changeList;
    }

    public void setChangeList(final List<YizhuChange> changeList) {
        this.changeList = changeList;
    }

    public YizhuTestBean getChildYizhuTestBean() {
        return childYizhuTestBean;
    }

    public void setChildYizhuTestBean(final YizhuTestBean childYizhuTestBean) {
        this.childYizhuTestBean = childYizhuTestBean;
    }

    public List<YizhuBsjb> getBsjbList() {
        return bsjbList;
    }

    public void setBsjbList(final List<YizhuBsjb> bsjbList) {
        this.bsjbList = bsjbList;
    }
}

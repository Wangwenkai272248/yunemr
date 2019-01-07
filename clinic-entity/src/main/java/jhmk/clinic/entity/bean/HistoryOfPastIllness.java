package jhmk.clinic.entity.bean;

/**
 * @author ziyu.zhou
 * @date 2019/1/7 17:27
 */

import java.util.List;

/**
 * 既往史
 */
public class HistoryOfPastIllness {
    private List<Disease>diseaseList;

    public List<Disease> getDiseaseList() {
        return diseaseList;
    }

    public void setDiseaseList(final List<Disease> diseaseList) {
        this.diseaseList = diseaseList;
    }
}

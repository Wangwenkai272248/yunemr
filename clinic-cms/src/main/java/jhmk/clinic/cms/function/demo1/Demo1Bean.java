package jhmk.clinic.cms.function.demo1;

import java.util.List;
import java.util.Map;

/**
 * @author ziyu.zhou
 * @date 2018/8/24 13:18
 */

public class Demo1Bean {
    private String id;
    private String pid;
    private String vid;
    //医嘱用药所属大类
    private String bigClazz;
    //口服药 药品 大类
    private Map<String, String> koufu;
    //吸入药

    private Map<String, String> xiru;
    private List<Map<String, String>> drugList;
    //药品
    // 用药or口服
    private String type;
    private String age;
    private String inHospitalDay;
    private String allNames;
    private double fee;

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getBigClazz() {
        return bigClazz;
    }

    public void setBigClazz(String bigClazz) {
        this.bigClazz = bigClazz;
    }

    public Map<String, String> getKoufu() {
        return koufu;
    }

    public void setKoufu(Map<String, String> koufu) {
        this.koufu = koufu;
    }

    public Map<String, String> getXiru() {
        return xiru;
    }

    public void setXiru(Map<String, String> xiru) {
        this.xiru = xiru;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getInHospitalDay() {
        return inHospitalDay;
    }

    public void setInHospitalDay(String inHospitalDay) {
        this.inHospitalDay = inHospitalDay;
    }

    public String getAllNames() {
        return allNames;
    }

    public void setAllNames(String allNames) {
        this.allNames = allNames;
    }

    public List<Map<String, String>> getDrugList() {
        return drugList;
    }

    public void setDrugList(List<Map<String, String>> drugList) {
        this.drugList = drugList;
    }
}

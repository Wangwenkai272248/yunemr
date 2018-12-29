package jhmk.clinic.entity.cdss;

import jhmk.clinic.entity.bean.Shouyeshoushu;

import java.util.List;
import java.util.Map;

public class CdssRuleBean {


    private String id;
    private String doctor_id;
    private String doctor_name;
    private String dept_code;
    private String patient_id;
    private String warnSource;//住院 门诊
    private String visit_id;
    private String pageSource;
    //入院初诊
    private String rycz;
    //出院诊断
    private String cyzd;
    private String quezhen;
    private String mainIllName;
    private String discharge_time;//出院
    private String admission_time;//入院
    private Map<String, String> binganshouye;
    private Map<String, String> physicalSign;
    private List<Map<String, String>> binglizhenduan;
    private List<Map<String, String>> shouyezhenduan;
    private List<Map<String, String>> ruyuanjilu;
//    private List<Map<String, List<Map<String, String>>>> jianyanbaogao;
    private List<Map<String, String>> jianyanbaogao;
    private List<Map<String, String>> jianchabaogao;
    private List<Map<String, String>> yizhu;
    private List<Shouyeshoushu> shouyeshoushu;

    public List<Shouyeshoushu> getShouyeshoushu() {
        return shouyeshoushu;
    }

    public void setShouyeshoushu(final List<Shouyeshoushu> shouyeshoushu) {
        this.shouyeshoushu = shouyeshoushu;
    }

//    private List<>shouyezhenduan;


    public String getDept_code() {
        return dept_code;
    }

    public void setDept_code(String dept_code) {
        this.dept_code = dept_code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getQuezhen() {
        return quezhen;
    }

    public void setQuezhen(String quezhen) {
        this.quezhen = quezhen;
    }


    public String getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(String visit_id) {
        this.visit_id = visit_id;
    }

    public String getPageSource() {
        return pageSource;
    }

    public void setPageSource(String pageSource) {
        this.pageSource = pageSource;
    }

    public Map<String, String> getBinganshouye() {
        return binganshouye;
    }

    public void setBinganshouye(Map<String, String> binganshouye) {
        this.binganshouye = binganshouye;
    }

    public Map<String, String> getPhysicalSign() {
        return physicalSign;
    }

    public void setPhysicalSign(Map<String, String> physicalSign) {
        this.physicalSign = physicalSign;
    }

    public List<Map<String, String>> getBinglizhenduan() {
        return binglizhenduan;
    }

    public void setBinglizhenduan(List<Map<String, String>> binglizhenduan) {
        this.binglizhenduan = binglizhenduan;
    }

    public List<Map<String, String>> getRuyuanjilu() {
        return ruyuanjilu;
    }

    public void setRuyuanjilu(List<Map<String, String>> ruyuanjilu) {
        this.ruyuanjilu = ruyuanjilu;
    }

    public List<Map<String, String>> getJianyanbaogao() {
        return jianyanbaogao;
    }

    public void setJianyanbaogao(List<Map<String, String>> jianyanbaogao) {
        this.jianyanbaogao = jianyanbaogao;
    }

    public List<Map<String, String>> getJianchabaogao() {
        return jianchabaogao;
    }

    public List<Map<String, String>> getShouyezhenduan() {
        return shouyezhenduan;
    }

    public void setShouyezhenduan(List<Map<String, String>> shouyezhenduan) {
        this.shouyezhenduan = shouyezhenduan;
    }

    public void setJianchabaogao(List<Map<String, String>> jianchabaogao) {


        this.jianchabaogao = jianchabaogao;
    }

    public List<Map<String, String>> getYizhu() {
        return yizhu;
    }


    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getWarnSource() {
        return warnSource;
    }

    public void setWarnSource(String warnSource) {
        this.warnSource = warnSource;
    }

    public void setYizhu(List<Map<String, String>> yizhu) {
        this.yizhu = yizhu;
    }

    public String getMainIllName() {
        return mainIllName;
    }

    public void setMainIllName(String mainIllName) {
        this.mainIllName = mainIllName;
    }

    public String getRycz() {
        return rycz;
    }

    public void setRycz(final String rycz) {
        this.rycz = rycz;
    }

    public String getCyzd() {
        return cyzd;
    }

    public void setCyzd(final String cyzd) {
        this.cyzd = cyzd;
    }

    public String getDischarge_time() {
        return discharge_time;
    }

    public void setDischarge_time(final String discharge_time) {
        this.discharge_time = discharge_time;
    }

    public String getAdmission_time() {
        return admission_time;
    }

    public void setAdmission_time(final String admission_time) {
        this.admission_time = admission_time;
    }
}


package jhmk.clinic.entity.cdss;

import jhmk.clinic.entity.bean.Binganshouye;
import jhmk.clinic.entity.bean.Shangjiyishichafanglu;
import jhmk.clinic.entity.bean.Shouyezhenduan;

import java.util.List;

/**
 * @author ziyu.zhou
 * @date 2018/9/11 16:26
 */

public class CdssDiffBean {
    private String id;
    //判断上级医师查房 是否等于 出院主诊断
    private boolean flag;
    private boolean zhuanke;
    //上级医师查房疾病名
    private String sjyscfName;
    //上级医师查房时间
    private String sjyscfTime;
    //就诊时间
//
    private String jz_time;
    private String discharge_time;
    private String admission_time;
    private Binganshouye binganshouye;
    private String ruyuanchuzhen;
    private String chuyuanzhenduan;
    private List<Shangjiyishichafanglu> shangjiyishichafangluList;
    private List<Shouyezhenduan> shouyezhenduanList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getAdmission_time() {
        return admission_time;
    }

    public void setAdmission_time(String admission_time) {
        this.admission_time = admission_time;
    }

    public String getSjyscfName() {
        return sjyscfName;
    }

    public void setSjyscfName(String sjyscfName) {
        this.sjyscfName = sjyscfName;
    }

    public String getSjyscfTime() {
        return sjyscfTime;
    }

    public void setSjyscfTime(String sjyscfTime) {
        this.sjyscfTime = sjyscfTime;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isZhuanke() {
        return zhuanke;
    }

    public void setZhuanke(boolean zhuanke) {
        this.zhuanke = zhuanke;
    }

    public Binganshouye getBinganshouye() {
        return binganshouye;
    }

    public void setBinganshouye(Binganshouye binganshouye) {
        this.binganshouye = binganshouye;
    }

    public String getRuyuanchuzhen() {
        return ruyuanchuzhen;
    }

    public void setRuyuanchuzhen(String ruyuanchuzhen) {
        this.ruyuanchuzhen = ruyuanchuzhen;
    }

    public String getChuyuanzhenduan() {
        return chuyuanzhenduan;
    }

    public void setChuyuanzhenduan(String chuyuanzhenduan) {
        this.chuyuanzhenduan = chuyuanzhenduan;
    }

    public List<Shangjiyishichafanglu> getShangjiyishichafangluList() {
        return shangjiyishichafangluList;
    }

    public void setShangjiyishichafangluList(List<Shangjiyishichafanglu> shangjiyishichafangluList) {
        this.shangjiyishichafangluList = shangjiyishichafangluList;
    }

    public List<Shouyezhenduan> getShouyezhenduanList() {
        return shouyezhenduanList;
    }

    public void setShouyezhenduanList(List<Shouyezhenduan> shouyezhenduanList) {
        this.shouyezhenduanList = shouyezhenduanList;
    }

    public String getJz_time() {
        return jz_time;
    }

    public void setJz_time(final String jz_time) {
        this.jz_time = jz_time;
    }

    public String getDischarge_time() {
        return discharge_time;
    }

    public void setDischarge_time(final String discharge_time) {
        this.discharge_time = discharge_time;
    }
}

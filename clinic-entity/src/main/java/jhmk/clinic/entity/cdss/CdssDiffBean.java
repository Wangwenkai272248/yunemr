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
    private Binganshouye binganshouye;
    private String ruyuanchuzhen;
    private String chuyuanzhenduan;
    private List<Shangjiyishichafanglu> shangjiyishichafangluList;
    private List<Shouyezhenduan> shouyezhenduanList;

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
}

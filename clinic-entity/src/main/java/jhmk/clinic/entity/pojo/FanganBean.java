package jhmk.clinic.entity.pojo;

/**
 * @author ziyu.zhou
 * @date 2018/10/25 14:10
 */

import java.util.List;

/**
 * 治疗方案实体类
 */
public class FanganBean {
    private String type;//类型 方案、增加、减少、伴随疾病
    private String illName;//疾病名
    private String typeDesc;//方案 描述
    private String patinet_id;
    private String visit_id;
    private String id;
    private List<FanganFieldBean>fieldBeanList;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIllName() {
        return illName;
    }

    public void setIllName(String illName) {
        this.illName = illName;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public String getPatinet_id() {
        return patinet_id;
    }

    public void setPatinet_id(String patinet_id) {
        this.patinet_id = patinet_id;
    }

    public String getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(String visit_id) {
        this.visit_id = visit_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<FanganFieldBean> getFieldBeanList() {
        return fieldBeanList;
    }

    public void setFieldBeanList(List<FanganFieldBean> fieldBeanList) {
        this.fieldBeanList = fieldBeanList;
    }
}

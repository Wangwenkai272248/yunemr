package jhmk.clinic.entity.pojo;

/**
 * @author ziyu.zhou
 * @date 2018/10/25 15:28
 */

public class FanganFieldBean {
    private String bsjbName;//伴随疾病
    private String purpose;
    private String durg;
    private String order_item_name;
    private String change;

    public String getBsjbName() {
        return bsjbName;
    }

    public void setBsjbName(String bsjbName) {
        this.bsjbName = bsjbName;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getDurg() {
        return durg;
    }

    public void setDurg(String durg) {
        this.durg = durg;
    }

    public String getOrder_item_name() {
        return order_item_name;
    }

    public void setOrder_item_name(String order_item_name) {
        this.order_item_name = order_item_name;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }
}

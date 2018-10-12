package jhmk.clinic.entity.pojo;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author ziyu.zhou
 * @date 2018/10/10 9:40
 */

@Entity
@Table(name = "yizhu_ori", schema = "yunemr", catalog = "")
public class YizhuOri {
    private int id;
    private String patientId;
    private String visitId;
    private Integer fId;
    private String purpose;
    private String drug;
    private String orderItemName;
    private String oldId;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "patient_id", nullable = true, length = 255)
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    @Basic
    @Column(name = "visit_id", nullable = true, length = 11)
    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    @Basic
    @Column(name = "f_id", nullable = true)
    public Integer getfId() {
        return fId;
    }

    public void setfId(Integer fId) {
        this.fId = fId;
    }

    @Basic
    @Column(name = "purpose", nullable = true, length = 255)
    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    @Basic
    @Column(name = "drug", nullable = true, length = 255)
    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    @Basic
    @Column(name = "order_item_name", nullable = true, length = 255)
    public String getOrderItemName() {
        return orderItemName;
    }

    public void setOrderItemName(String orderItemName) {
        this.orderItemName = orderItemName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YizhuOri yizhuOri = (YizhuOri) o;
        return id == yizhuOri.id &&
                Objects.equals(patientId, yizhuOri.patientId) &&
                Objects.equals(visitId, yizhuOri.visitId) &&
                Objects.equals(fId, yizhuOri.fId) &&
                Objects.equals(purpose, yizhuOri.purpose) &&
                Objects.equals(drug, yizhuOri.drug) &&
                Objects.equals(orderItemName, yizhuOri.orderItemName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, patientId, visitId, fId, purpose, drug, orderItemName);
    }

    @Basic
    @Column(name = "old_id", nullable = true)
    public String getOldId() {
        return oldId;
    }

    public void setOldId( String oldId) {
        this.oldId = oldId;
    }
}

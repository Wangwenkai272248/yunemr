package jhmk.clinic.entity.pojo;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author ziyu.zhou
 * @date 2018/10/12 11:14
 */

@Entity
@Table(name = "yizhu_bsjb", schema = "yunemr", catalog = "")
public class YizhuBsjb {
    private int id;
    private Integer relevanceId;
    private String purpose;
    private String orderItemName;
    private Integer num;
    private String drug;
    private String bId;
    private String bsjb;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "relevance_id", nullable = true)
    public Integer getRelevanceId() {
        return relevanceId;
    }

    public void setRelevanceId(final Integer relevanceId) {
        this.relevanceId = relevanceId;
    }

    @Basic
    @Column(name = "purpose", nullable = true, length = 255)
    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(final String purpose) {
        this.purpose = purpose;
    }

    @Basic
    @Column(name = "order_item_name", nullable = true, length = 255)
    public String getOrderItemName() {
        return orderItemName;
    }

    public void setOrderItemName(final String orderItemName) {
        this.orderItemName = orderItemName;
    }

    @Basic
    @Column(name = "num", nullable = true)
    public Integer getNum() {
        return num;
    }

    public void setNum(final Integer num) {
        this.num = num;
    }

    @Basic
    @Column(name = "drug", nullable = true, length = 255)
    public String getDrug() {
        return drug;
    }

    public void setDrug(final String drug) {
        this.drug = drug;
    }

    @Basic
    @Column(name = "b_id", nullable = true, length = 50)
    public String getbId() {
        return bId;
    }

    public void setbId(final String bId) {
        this.bId = bId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final YizhuBsjb yizhuBsjb = (YizhuBsjb) o;
        return id == yizhuBsjb.id &&
                Objects.equals(relevanceId, yizhuBsjb.relevanceId) &&
                Objects.equals(purpose, yizhuBsjb.purpose) &&
                Objects.equals(orderItemName, yizhuBsjb.orderItemName) &&
                Objects.equals(num, yizhuBsjb.num) &&
                Objects.equals(drug, yizhuBsjb.drug) &&
                Objects.equals(bId, yizhuBsjb.bId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, relevanceId, purpose, orderItemName, num, drug, bId);
    }

    @Basic
    @Column(name = "bsjb", nullable = true, length = 255)
    public String getBsjb() {
        return bsjb;
    }

    public void setBsjb(final String bsjb) {
        this.bsjb = bsjb;
    }
}

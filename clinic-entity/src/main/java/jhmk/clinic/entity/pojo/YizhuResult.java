package jhmk.clinic.entity.pojo;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author ziyu.zhou
 * @date 2018/10/10 9:40
 */

@Entity
@Table(name = "yizhu_result", schema = "yunemr", catalog = "")
public class YizhuResult {
    private int id;
    private Integer relevanceId;
    private String purpose;
    private String orderItemName;
    private Integer num;
    private String bId;
    private String drug;
    private String mainIllName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "relevance_id", nullable = true)
    public Integer getRelevanceId() {
        return relevanceId;
    }

    public void setRelevanceId(Integer relevanceId) {
        this.relevanceId = relevanceId;
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
        YizhuResult that = (YizhuResult) o;
        return id == that.id &&
                Objects.equals(relevanceId, that.relevanceId) &&
                Objects.equals(purpose, that.purpose) &&
                Objects.equals(orderItemName, that.orderItemName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, relevanceId, purpose, orderItemName);
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
    @Column(name = "b_id", nullable = true, length = 50)
    public String getbId() {
        return bId;
    }

    public void setbId(final String bId) {
        this.bId = bId;
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
    @Column(name = "mainIllName", nullable = true, length = 255)
    public String getMainIllName() {
        return mainIllName;
    }

    public void setMainIllName(final String mainIllName) {
        this.mainIllName = mainIllName;
    }
}

package jhmk.clinic.entity.bean;

import java.io.Serializable;

/**
 * @author ziyu.zhou
 * @date 2018/7/30 11:46
 */


public class Yizhu implements Serializable {
    private String order_item_name;
    private String order_begin_time;
    private String order_end_time;
    private String order_properties_name;//长期 临时医嘱
    private String order_status_name;//停止
    private String frequency_name;//频次
    private String dosage_value;//剂量
    private String dosage_value_unit;//单位

    public String getOrder_item_name() {
        return order_item_name;
    }

    public void setOrder_item_name(String order_item_name) {
        this.order_item_name = order_item_name;
    }

    public String getOrder_begin_time() {
        return order_begin_time;
    }

    public void setOrder_begin_time(String order_begin_time) {
        this.order_begin_time = order_begin_time;
    }

    public String getOrder_end_time() {
        return order_end_time;
    }

    public void setOrder_end_time(String order_end_time) {
        this.order_end_time = order_end_time;
    }

    public String getOrder_properties_name() {
        return order_properties_name;
    }

    public void setOrder_properties_name(String order_properties_name) {
        this.order_properties_name = order_properties_name;
    }

    public String getOrder_status_name() {
        return order_status_name;
    }

    public void setOrder_status_name(String order_status_name) {
        this.order_status_name = order_status_name;
    }

    public String getFrequency_name() {
        return frequency_name;
    }

    public void setFrequency_name(final String frequency_name) {
        this.frequency_name = frequency_name;
    }

    public String getDosage_value() {
        return dosage_value;
    }

    public void setDosage_value(final String dosage_value) {
        this.dosage_value = dosage_value;
    }

    public String getDosage_value_unit() {
        return dosage_value_unit;
    }

    public void setDosage_value_unit(final String dosage_value_unit) {
        this.dosage_value_unit = dosage_value_unit;
    }
}
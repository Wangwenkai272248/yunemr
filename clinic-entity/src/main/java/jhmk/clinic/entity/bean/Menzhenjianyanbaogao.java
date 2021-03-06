package jhmk.clinic.entity.bean;

/**
 * @author ziyu.zhou
 * @date 2018/7/23 14:55
 */

public class Menzhenjianyanbaogao {

    private String lab_item_name;//检验项名称
    private String specimen;
    private String lab_qual_result;//定性结果
    private String lab_result_value;//检验定量结果值
    private String report_time;//检验结果产生时间
    private String report_no;
    private String reference_range;//参考区间
    private String lab_result_value_unit;//参考单位
    private String lab_sub_item_name;//检验细项
    private String result_status_code;//结果编码，和参考范围比较的定性结果，H、L等
    private String result_status_name;//结果描述，和参考范围比较的定性结果，偏高、偏低等

    public String getLab_item_name() {
        return lab_item_name;
    }

    public void setLab_item_name(String lab_item_name) {
        this.lab_item_name = lab_item_name;
    }

    public String getSpecimen() {
        return specimen;
    }

    public void setSpecimen(String specimen) {
        this.specimen = specimen;
    }

    public String getLab_qual_result() {
        return lab_qual_result;
    }

    public void setLab_qual_result(String lab_qual_result) {
        this.lab_qual_result = lab_qual_result;
    }

    public String getLab_result_value() {
        return lab_result_value;
    }

    public void setLab_result_value(String lab_result_value) {
        this.lab_result_value = lab_result_value;
    }

    public String getReport_time() {
        return report_time;
    }

    public void setReport_time(String report_time) {
        this.report_time = report_time;
    }

    public String getReport_no() {
        return report_no;
    }

    public void setReport_no(String report_no) {
        this.report_no = report_no;
    }

    public String getLab_sub_item_name() {
        return lab_sub_item_name;
    }

    public void setLab_sub_item_name(String lab_sub_item_name) {
        this.lab_sub_item_name = lab_sub_item_name;
    }

    public String getResult_status_code() {
        return result_status_code;
    }

    public void setResult_status_code(String result_status_code) {
        this.result_status_code = result_status_code;
    }

    public String getResult_status_name() {
        return result_status_name;
    }

    public void setResult_status_name(String result_status_name) {
        this.result_status_name = result_status_name;
    }

    public String getReference_range() {
        return reference_range;
    }

    public void setReference_range(String reference_range) {
        this.reference_range = reference_range;
    }

    public String getLab_result_value_unit() {
        return lab_result_value_unit;
    }

    public void setLab_result_value_unit(String lab_result_value_unit) {
        this.lab_result_value_unit = lab_result_value_unit;
    }



    //做排序用 删除重复项
    public String getIwantData() {
        return this.specimen + this.lab_sub_item_name;
    }
  }

/**
 * Copyright 2018 bejson.com
 */
package jhmk.clinic.cms.entity;
import java.util.List;

/**
 * Auto-generated: 2018-09-26 19:7:49
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Disease_level {

    private List<Disease_attribution> disease_attribution;
    private List<Disease_category> disease_category;
    private int disease_level;
    private String department;
    public void setDisease_attribution(List<Disease_attribution> disease_attribution) {
        this.disease_attribution = disease_attribution;
    }
    public List<Disease_attribution> getDisease_attribution() {
        return disease_attribution;
    }

    public void setDisease_category(List<Disease_category> disease_category) {
        this.disease_category = disease_category;
    }
    public List<Disease_category> getDisease_category() {
        return disease_category;
    }

    public void setDisease_level(int disease_level) {
        this.disease_level = disease_level;
    }
    public int getDisease_level() {
        return disease_level;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
    public String getDepartment() {
        return department;
    }

}
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
public class Disease_obj {

    private List<Disease_level> disease_level;
    private String disease_name;
    private List<String> disease_alias;
    public void setDisease_level(List<Disease_level> disease_level) {
        this.disease_level = disease_level;
    }
    public List<Disease_level> getDisease_level() {
        return disease_level;
    }

    public void setDisease_name(String disease_name) {
        this.disease_name = disease_name;
    }
    public String getDisease_name() {
        return disease_name;
    }

    public void setDisease_alias(List<String> disease_alias) {
        this.disease_alias = disease_alias;
    }
    public List<String> getDisease_alias() {
        return disease_alias;
    }

}
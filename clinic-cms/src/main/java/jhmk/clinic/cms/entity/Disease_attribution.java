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
public class Disease_attribution {

    private int level;
    private String name;
    private List<String> alias;
    public void setLevel(int level) {
        this.level = level;
    }
    public int getLevel() {
        return level;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setAlias(List<String> alias) {
        this.alias = alias;
    }
    public List<String> getAlias() {
        return alias;
    }

}
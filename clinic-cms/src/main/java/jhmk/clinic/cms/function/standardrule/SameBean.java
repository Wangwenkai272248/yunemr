package jhmk.clinic.cms.function.standardrule;

import java.util.List;

/**
 * @author ziyu.zhou
 * @date 2018/12/21 16:20
 */

public class SameBean {
    private String field;
    private List<String> value;
    private String exp;

    public String getField() {
        return field;
    }

    public void setField(final String field) {
        this.field = field;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(final List<String> value) {
        this.value = value;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(final String exp) {
        this.exp = exp;
    }
}

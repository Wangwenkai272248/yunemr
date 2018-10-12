package jhmk.clinic.cms.entity;

import java.util.List;

/**
 * @author ziyu.zhou
 * @date 2018/9/27 9:45
 */
public class ResultService {
    //获取一级病
    public static String getGrandFa(List<Result> result) {
        if (result == null) {
            return null;
        }
        Disease_obj disease_obj = result.get(0).getDisease_obj();
         List<Disease_level> disease_level = disease_obj.getDisease_level();
         String name = disease_level.get(0).getDisease_attribution().get(0).getName();
        if ("".equals(name)||"None".equals(name)) {
            name = disease_obj.getDisease_name();
        }
        return name;
    }

    //获取别名
    public static List<String> getAlias(List<Result> result) {
        Disease_obj disease_obj = result.get(0).getDisease_obj();
        final List<String> disease_alias = disease_obj.getDisease_alias();
        if (disease_alias.size() == 1 && disease_alias.get(0).equals("None")) {
            return null;
        }
        return disease_alias;
    }
}

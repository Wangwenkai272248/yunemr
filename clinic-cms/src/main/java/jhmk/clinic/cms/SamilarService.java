package jhmk.clinic.cms;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jhmk.clinic.core.config.CdssConstans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @author ziyu.zhou
 * @date 2018/9/6 16:05
 */

@Service
public class SamilarService {
    @Autowired
    RestTemplate restTemplate;
    public String symbol = "[]";

    /**
     * 获取同义词
     *
     * @param name
     * @return
     */
    public List<String> getSamilarWord(String name) {
        String params = getParam(name);
        System.out.println(params);
        Object parse = JSONObject.parse(params);
        try {

            String sames = restTemplate.postForObject(CdssConstans.QUERY, parse, String.class);
            List<String> list1 = analyzeData2getSamilar(sames, name);
        } catch (Exception e) {

        }
//        if (sames != null && !symbol.equals(sames.trim())) {
//            JSONArray objects = JSONArray.parseArray(sames);
//            Iterator<Object> iterator = objects.iterator();
//            while (iterator.hasNext()) {
//                Object next = iterator.next();
//                list.add(next.toString());
//            }
//        }
        return null;
    }

    public String getQuery(String name) {
        List<String> list = new ArrayList<>();
        String params = getParam(name);
        Object parse = JSONObject.parse(params);
        try {

            String sames = restTemplate.postForObject(CdssConstans.QUERY, parse, String.class);
            return sames;
        } catch (Exception e) {

        }
//        if (sames != null && !symbol.equals(sames.trim())) {
//            JSONArray objects = JSONArray.parseArray(sames);
//            Iterator<Object> iterator = objects.iterator();
//            while (iterator.hasNext()) {
//                Object next = iterator.next();
//                list.add(next.toString());
//            }
//        }
        return null;
    }


    /**
     * 判断是都是同类别病
     *
     * @param name
     * @return
     */
//    public boolean isFatherAndSon(String name1, String name2) {
//        String params1 = getParam(name1);
//        String param2 = getParam(name2);
//        Object parse1 = JSONObject.parse(params1);
//        Object parse2 = JSONObject.parse(param2);
//        try {
//
//            String sames1 = restTemplate.postForObject(CdssConstans.QUERY, parse1, String.class);
//            String sames2 = restTemplate.postForObject(CdssConstans.QUERY, parse2, String.class);
//            final JsonRootBean ryczNameBean1 = JSONObject.parseObject(sames1, JsonRootBean.class);
//            final JsonRootBean ryczNameBean2 = JSONObject.parseObject(sames2, JsonRootBean.class);
//            final String grandFa1 = ResultService.getGrandFa(ryczNameBean1.getResult());
//            final String grandFa2 = ResultService.getGrandFa(ryczNameBean2.getResult());
//            if (grandFa1.equals(grandFa2)) {
//
//                return true;
//            } else {
//                return false;
//            }
//        } catch (Exception e) {
//        }
//
//        return false;
//    }
    public boolean isFatherAndSon(String name1, String name2) {
        if (name1.contains(name2) || name2.contains(name1)) {
            return true;
        }
        List<String> parentList = getParentList(name1);
        if (parentList.contains(name2)){
            return true;
        }
        return false;
    }


    /**
     * 解析json数据获取同义词
     *
     * @return
     */
    private List<String> analyzeData2getSamilar(String jsonData, String name) {
        List<String> nameList = new ArrayList<>();
        JSONObject jsonObject = JSONObject.parseObject(jsonData);
        Object result = jsonObject.get("result");
        if (result != null) {
            JSONArray resultArray = (JSONArray) result;
            Iterator<Object> iterator = resultArray.iterator();
            while (iterator.hasNext()) {
                Object next = iterator.next();
                System.out.println(next);
                if (Objects.nonNull(next)) {
                    JSONObject next1 = (JSONObject) next;
                    Object disease_obj = next1.get("disease_obj");
                }
            }
        }
        return nameList;
    }

    /**
     * 获取子疾病
     *
     * @param name
     * @return
     */

//    public List<String> getDiseaseChildrenList(String name) {
//        List<String> list = new ArrayList<>();
//        Object params = getParams(name);
//        String sames = restTemplate.postForObject(CdssConstans.getDiseaseChildrenList, params, String.class);
//        if (sames != null && !symbol.equals(sames.trim())) {
//            JSONArray objects = JSONArray.parseArray(sames);
//            Iterator<Object> iterator = objects.iterator();
//            while (iterator.hasNext()) {
//                Object next = iterator.next();
//                list.add(next.toString());
//            }
//        }
//        return list;
//    }
//
//    /**
//     * 获取肤疾病
//     *
//     * @param name
//     * @return
//     */
//
    public List<String> getParentList(String name) {
        List<String> list = new ArrayList<>();
        Object params = getParams(name);
        String sames = restTemplate.postForObject(CdssConstans.getParentList, params, String.class);
        if (sames != null && !symbol.equals(sames.trim())) {
            JSONArray objects = JSONArray.parseArray(sames);
            Iterator<Object> iterator = objects.iterator();
            while (iterator.hasNext()) {
                Object next = iterator.next();
                list.add(next.toString());
            }
        }
        return list;
    }


    private Object getParams(String name) {
        Map<String, String> param = new HashMap<>();
        param.put("diseaseName", name);
        Object parse1 = JSONObject.toJSON(param);
        return parse1;
    }

    public String getParam(String name) {
        String param = "{\n" +
                "\t\"flag\": \"or\",\n" +
                "\t\"size\": 0,\n" +
                "\t\"fields\": [{\n" +
                "\t\t\"name\": \"disease\",\n" +
                "\t\t\"type\": \"disease\",\n" +
                "\t\t\"value\": \"" + name + "\"\n" +
                "\t}],\n" +
                "\t\"results\": [\"disease_obj.disease_name\", \"disease_obj.disease_alias\",\"disease_obj.disease_level\"]\n" +
                "}";
        return param;
    }
//    public Set<String> getAllIllNames(String name) {
//        Set<String> set = new HashSet<>();
//        List<String> samilarWord = getSamilarWord(name);
//        set.addAll(samilarWord);
//        List<String> diseaseChildrenList = getDiseaseChildrenList(name);
//        set.addAll(diseaseChildrenList);
//        List<String> parentList = getParentList(name);
//        set.add(name);
//        set.addAll(parentList);
//        return set;
//    }

}

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
        List<String> list = new ArrayList<>();
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
     * 解析json数据获取同义词
     * @return
     */
    private List<String>analyzeData2getSamilar(String jsonData,String name){
        List<String>nameList=new ArrayList<>();
        JSONObject jsonObject = JSONObject.parseObject(jsonData);
        Object result = jsonObject.get("result");
        if (result!=null){
            JSONArray resultArray = (JSONArray) result;
            Iterator<Object> iterator = resultArray.iterator();
            while (iterator.hasNext()){
                Object next = iterator.next();
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
//    public List<String> getParentList(String name) {
//        List<String> list = new ArrayList<>();
//        Object params = getParams(name);
//        String sames = restTemplate.postForObject(CdssConstans.getParentList, params, String.class);
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

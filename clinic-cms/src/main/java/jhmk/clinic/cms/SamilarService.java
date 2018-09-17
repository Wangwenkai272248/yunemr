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
        RestTemplate restTemplate=new RestTemplate();
        List<String> list = new ArrayList<>();
        Object params = getParams(name);
        String sames = restTemplate.postForObject(CdssConstans.getSamilarWord, params, String.class);
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

    /**
     * 获取子疾病
     *
     * @param name
     * @return
     */

    public List<String> getDiseaseChildrenList(String name) {
        RestTemplate restTemplate=new RestTemplate();
        List<String> list = new ArrayList<>();
        Object params = getParams(name);
        String sames = restTemplate.postForObject(CdssConstans.getDiseaseChildrenList, params, String.class);
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

    /**
     * 获取肤疾病
     *
     * @param name
     * @return
     */

    public List<String> getParentList(String name) {
        RestTemplate restTemplate=new RestTemplate();
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

    public Set<String> getAllIllNames(String name) {
        Set<String> set = new HashSet<>();
        List<String> samilarWord = getSamilarWord(name);
        set.addAll(samilarWord);
        List<String> diseaseChildrenList = getDiseaseChildrenList(name);
        set.addAll(diseaseChildrenList);
        List<String> parentList = getParentList(name);
        set.add(name);
        set.addAll(parentList);
        return set;
    }

}

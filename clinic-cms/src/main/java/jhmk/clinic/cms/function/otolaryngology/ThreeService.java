package jhmk.clinic.cms.function.otolaryngology;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import jhmk.clinic.core.config.CdssConstans;
import jhmk.clinic.entity.bean.Misdiagnosis;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static jhmk.clinic.core.util.MongoUtils.getCollection;

/**
 * @author ziyu.zhou
 * @date 2018/8/8 16:52
 */

@Service
public class ThreeService {

    MongoCollection<Document> shouyezhenduan = getCollection(CdssConstans.DATASOURCE, CdssConstans.SHOUYEZHENDUAN);
    MongoCollection<Document> binganshouye = getCollection(CdssConstans.DATASOURCE, CdssConstans.BINGANSHOUYE);


    public void write2file(Map<String, Map<String, Integer>> data) {
        BufferedWriter bufferedWriter = null;
//        File file = new File("/data/1/CDSS/3院骨科漏诊数据.txt");
        File file = new File(CdssConstans.DEVURL + "3院疾病不同科室诊断列表.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(JSONObject.toJSONString(data));
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 获取最终结果
     *
     * @return
     */
    public Map<String, Map<String, Integer>> getResultData() {
        Map<String, List<String>> stringListMap = geIllNamesAndIdsCount();
        Map<String, List<String>> tempMap = new HashMap<>();
        for (Map.Entry<String, List<String>> entries : stringListMap.entrySet()) {
            String key = entries.getKey();
            List<String> value = entries.getValue();
            int size = value.size();
            if (size > 1 && !"null".equals(key) && StringUtils.isNotBlank(key)) {
                tempMap.put(key, value);
            }
        }

        Map<String, Map<String, Integer>> temp = new HashMap<>();
        Map<String, Map<String, Integer>> deptIllCount = getDeptIllCount(tempMap);
        for (Map.Entry<String, Map<String, Integer>> entry : deptIllCount.entrySet()) {
            String key = entry.getKey();
            Map<String, Integer> value = entry.getValue();
            int size = value.size();
            if (size > 1) {
                temp.put(key, value);
            }

        }
        return temp;
    }

    /**
     * 心脏病  心内科 1 骨科 2
     *
     * @return
     */
    public Map<String, Map<String, Integer>> getDeptIllCount(Map<String, List<String>> listMap) {
        //存放结果集合 心脏病  心内科 1 骨科 2
        Map<String, Map<String, Integer>> result = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : listMap.entrySet()) {
            Map<String, Integer> countMap = new HashMap<>();
            //疾病名
            String key = entry.getKey();
            //id集合
            List<String> value = entry.getValue();
            for (String id : value) {
                //获取部门名称
                String deptName = getDeptById(id);
                if (countMap.containsKey(deptName)) {
                    countMap.put(deptName, countMap.get(deptName) + 1);
                } else {
                    countMap.put(deptName, 1);
                }
            }
            result.put(key, countMap);
        }
        return result;
    }

    /**
     * 获取疾病对应的 id 集合 如 心脏病 id=1 id=2 主诊断都是心脏病
     *
     * @return
     */
    public Map<String, List<String>> geIllNamesAndIdsCount() {
        Map<String, List<String>> maps = new HashMap<>();

        List<Document> countPatientId = Arrays.asList(
                new Document("$unwind", "$shouyezhenduan"),
                new Document("$project", new Document("_id", 1).append("shouyezhenduan", 1))
//                , new Document("$skip", 0),
//                new Document("$limit", 5000)
        );
        AggregateIterable<Document> binli = shouyezhenduan.aggregate(countPatientId);
        for (Document document : binli) {
            String id = document.getString("_id");
            Document binglizhenduan = (Document) document.get("shouyezhenduan");
            String diagnosis_name = binglizhenduan.getString("diagnosis_name");
            String diagnosis_desc = binglizhenduan.getString("diagnosis_desc");
            String diagnosis_type_code = binglizhenduan.getString("diagnosis_type_code");
            String diagnosis_num = binglizhenduan.getString("diagnosis_num");
            if (StringUtils.isNotBlank(diagnosis_type_code)) {
                if ("3".equals(diagnosis_type_code) && "1".equals(diagnosis_num)) {
                    if (maps.containsKey(diagnosis_name)) {
                        List<String> list = maps.get(diagnosis_name);
                        list.add(id);
                        maps.put(diagnosis_name, list);
                    } else {
                        List<String> list = new LinkedList<>();
                        list.add(id);
                        maps.put(diagnosis_name, list);
                    }
                } else {
                    if ("1".equals(diagnosis_num)) {
                        if (StringUtils.isNotBlank(diagnosis_name)) {
                            if (maps.containsKey(diagnosis_name)) {
                                List<String> list = maps.get(diagnosis_name);
                                list.add(id);
                                maps.put(diagnosis_name, list);
                            } else {
                                List<String> list = new LinkedList<>();
                                list.add(id);
                                maps.put(diagnosis_name, list);
                            }
                        }
                    }
                }
            } else {
                if ("1".equals(diagnosis_num)) {
                    if (StringUtils.isNotBlank(diagnosis_name)) {
                        if (maps.containsKey(diagnosis_name)) {
                            List<String> list = maps.get(diagnosis_name);
                            list.add(id);
                            maps.put(diagnosis_name, list);
                        } else {
                            List<String> list = new LinkedList<>();
                            list.add(id);
                            maps.put(diagnosis_name, list);
                        }
                    }
                }
            }

        }
        return maps;
    }


    public String getDeptById(String id) {
        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("patient_id", 1).append("_id", 1).append("visit_id", 1).append("binganshouye", 1))
        );
        AggregateIterable<Document> output = binganshouye.aggregate(countPatientId);
        String dept_admission_to_name = "";
        for (Document document : output) {
            Misdiagnosis misdiagnosis = new Misdiagnosis();
            if (document == null) {
                continue;
            }
            Document binganshouye = (Document) document.get("binganshouye");
            Document patVisit = (Document) binganshouye.get("pat_visit");
            //科室
            dept_admission_to_name = patVisit.getString("dept_admission_to_name");

        }
        return dept_admission_to_name;
    }

}
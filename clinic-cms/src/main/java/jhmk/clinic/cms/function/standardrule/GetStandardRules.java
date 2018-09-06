package jhmk.clinic.cms.function.standardrule;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import jhmk.clinic.core.config.CdssConstans;
import jhmk.clinic.core.util.ReadResourceUtil;
import org.bson.Document;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;

import java.io.*;
import java.util.*;

import static jhmk.clinic.core.util.MongoUtils.getCollection;

/**
 * @author ziyu.zhou
 * @date 2018/8/14 11:35
 */
@Controller
public class GetStandardRules {

    static MongoCollection<Document> decisionRule = getCollection(CdssConstans.CDSSDATASOURCE, CdssConstans.DECISION_RULE);

    public static void main(String[] args) {

        BufferedWriter bufferedWriter = null;
//        File file = new File("/data/1/CDSS/3院骨科漏诊数据.txt");
        File file = new File("C:/嘉和美康文档/3院测试数据/标准规则数据.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            Map<String, String> stringStringMap = getHlyy();
            Map<String, String> menzhenzhenduan = getStand(stringStringMap);
            for (Map.Entry<String, String> entry : menzhenzhenduan.entrySet()) {
                bufferedWriter.write(entry.getKey() + "," + entry.getValue());
                bufferedWriter.newLine();
            }
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


    public static Map<String, String> getStand(Map<String, String> map) {
        Map<String, String> temMap = new LinkedHashMap<>();
        List<List<String>> nameList = getNameList();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            String s = value.replaceAll("\\(", "").replaceAll("\\)", "");
            String eq = s.substring(s.lastIndexOf("等于") + 2);

            for (List<String> values : nameList) {
                if (values.contains(eq)) {
                    String s1 = "";
                    if (values.size() > 5) {
                        s1 = values.get(2);
                    } else if (values.size() == 4) {
                        s1 = values.get(1);
                    } else {
                        s1 = values.get(0);
                    }
                    String s2 = s.replaceAll(eq, s1);
                    temMap.put(key, s2);
                }
            }
        }
        return temMap;
    }


    public static List<List<String>> getNameList() {
        Set<String> strListByFileNmae = ReadResourceUtil.getStrListByFileNmae("层次.txt");
        List<List<String>> list = new LinkedList<>();
        for (String s : strListByFileNmae) {
            list.add(Arrays.asList(s.split(",")));
        }
        return list;
    }

    /**
     * 获取合理用药
     *
     * @return
     */
    public static Map<String, String> getHlyy() {

        Set<String> names = readFile2Cache();
        Map<String, String> map = new HashMap<>();
        List<Document> countPatientId2 = Arrays.asList(
//                 new Document("$skip", 0),
//                new Document("$limit", 1000)
        );
        AggregateIterable<Document> output = decisionRule.aggregate(countPatientId2);

        for (Document document : output) {

            String id = document.getString("_id");
            String rule_condition = document.getString("rule_condition");
            String classification = document.getString("classification");
            if (!"合理用药".equals(classification)) {
                continue;
            }
            for (String name : names) {
//                if (rule_condition.contains(name)) {
                map.put(id, rule_condition);
//                }
            }
        }

        return map;
    }


    public static Set<String> readFile2Cache() {
        Set<String> liiNames = new LinkedHashSet<>();
        Resource resource = new ClassPathResource("standardName");
        File file = null;
        BufferedReader br = null;
        try {
            file = resource.getFile();
            br = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = br.readLine()) != null) {
                String ill = line.replaceAll("\\(", "（").replaceAll("\\)", "）");
                liiNames.add(ill);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return liiNames;
    }
    public static Set<String> readFile2Cache(String name) {
        Set<String> liiNames = new LinkedHashSet<>();
        Resource resource = new ClassPathResource(name);
        File file = null;
        BufferedReader br = null;
        try {
            file = resource.getFile();
            br = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = br.readLine()) != null) {
                liiNames.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return liiNames;
    }


}

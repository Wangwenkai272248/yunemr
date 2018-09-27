package jhmk.clinic.cms.function.demo1;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import jhmk.clinic.cms.function.standardrule.GetStandardRules;
import jhmk.clinic.core.config.CdssConstans;
import org.bson.Document;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

import static jhmk.clinic.core.util.MongoUtils.getCollection;

/**
 * @author ziyu.zhou
 * @date 2018/8/24 13:17
 */

@Service
public class Demo1Service {
    static MongoCollection<Document> shouyezhenduan = getCollection(CdssConstans.DATASOURCE, CdssConstans.SHOUYEZHENDUAN);
    MongoCollection<Document> binganshouye = getCollection(CdssConstans.DATASOURCE, CdssConstans.BINGANSHOUYE);
    MongoCollection<Document> ZHUYUANFEIYONG = getCollection(CdssConstans.DATASOURCE, CdssConstans.ZHUYUANFEIYONG);
    static MongoCollection<Document> yizhu = getCollection(CdssConstans.DATASOURCE, CdssConstans.YIZHU);

    //获取出院诊断中存在慢性阻塞性肺疾病急性加重的疾病的id
    public Set<String> getIdsByIllName(String name) {
        Set<String> maps = new LinkedHashSet<>();

        List<Document> countPatientId = Arrays.asList(
                new Document("$unwind", "$shouyezhenduan"),
                new Document("$match", new Document("shouyezhenduan.diagnosis_name", name)),
                new Document("$match", new Document("shouyezhenduan.diagnosis_num", "1")),
                new Document("$match", new Document("shouyezhenduan.diagnosis_type_name", "出院诊断")),
                new Document("$project", new Document("_id", 1).append("shouyezhenduan", 1))

        );
        AggregateIterable<Document> binli = shouyezhenduan.aggregate(countPatientId);
        for (Document document : binli) {
            String id = document.getString("_id");
            maps.add(id);
            continue;
        }
        return maps;
    }

    /**
     * 消费费用
     *
     * @param id
     */
    public double getTotalFeeById(String id) {
        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("_id", id))
        );
        AggregateIterable<Document> binli = ZHUYUANFEIYONG.aggregate(countPatientId);
        double total_fee = 0;
        for (Document document : binli) {
            Document zhuyuanfeiyong = (Document) document.get("zhuyuanfeiyong");
            total_fee = Double.valueOf(zhuyuanfeiyong.getString("total_fee"));

        }
        return total_fee;
    }


    /**
     * 获取住院天数
     *
     * @param id
     * @return
     */
    public String getInHosoitalDayById(String id) {
        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("_id", id))
        );
        AggregateIterable<Document> binli = binganshouye.aggregate(countPatientId);
        String in_hospital_days = "";
        for (Document document : binli) {


            if (document == null) {
                continue;
            }
            Document binganshouye = (Document) document.get("binganshouye");
            Document patInfo = (Document) binganshouye.get("pat_info");
            Document patVisit = (Document) binganshouye.get("pat_visit");
            if (Objects.nonNull(patVisit)) {

                in_hospital_days = patVisit.getString("in_hospital_days");
            }

        }
        return in_hospital_days;
    }

    public static void main(String[] args) {
        Double integer = Double.valueOf("22.33");
        System.out.println(integer);
    }

    /**
     * 获取年龄
     *
     * @param id
     * @return
     */
    public String getAgeValueById(String id) {
        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("_id", id))
        );
        AggregateIterable<Document> binli = binganshouye.aggregate(countPatientId);
        String total_fee = "";
        for (Document document : binli) {

            if (document == null) {
                continue;
            }
            Document binganshouye = (Document) document.get("binganshouye");
            Document patInfo = (Document) binganshouye.get("pat_info");
            Document patVisit = (Document) binganshouye.get("pat_visit");
            total_fee = patVisit.getString("age_value");

        }
        return total_fee;
    }

    public List<String> getDrudList() {
        List<String> list = new ArrayList<>();
        Resource resource = new ClassPathResource("drugSynonym");
        File file = null;
        String s;
        try {
            file = resource.getFile();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            while ((s = br.readLine()) != null) {
                String line = br.readLine();
                if (line == null || "".equals(line) || "null".equals(line)) {
                    continue;
                }
                list.add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;

    }

    public String getDrugStandardName(String name, List<String> list) {
        for (String str : list) {
            if (str.contains(name)) {
                if (isExist(name, str)) {
                    return getStandardDrugName(str);
                }
            }
        }
        return name;
    }


    public boolean isExist(String name, String str) {
        String[] split = str.split("#@#");
        String sName = split[0];
        String otherNames = split[1];
        String[] otherNameList = otherNames.split("@");
        List<String> list = new LinkedList();
        list.add(sName);
        list.addAll(Arrays.asList(otherNameList));
        if (list.indexOf(name) != -1) {
            return true;
        } else {
            return false;
        }
    }

    public String getStandardDrugName(String str) {
        String sName = "";
        if (str.contains("#@#")) {
            String[] split = str.split("#@#");
            sName = split[0];
        } else {
        }
        return sName;
    }


    public List<Map<String, String>> selYizhu(String id, List<String> list, List<String> drugList) {
        List<Document> countPatientId2 = Arrays.asList(
                //过滤数据
                new Document("$match", new Document("_id", id)),
                new Document("$unwind", "$yizhu"),
                new Document("$project", new Document("patient_id", 1).append("visit_id", 1).append("yizhu", 1)
                )

        );
        AggregateIterable<Document> output = yizhu.aggregate(countPatientId2);
        List<Map<String, String>> orderList = new LinkedList<>();
        for (Document document : output) {
            Document yizhuDocu = (Document) document.get("yizhu");
            if (yizhuDocu == null) {
                continue;
            }
            if (yizhuDocu.get("order_item_name") != null) {
                String order_item_name = yizhuDocu.getString("order_item_name");
                if (order_item_name.contains("(")) {
                    String[] split = order_item_name.split("\\(");
                    order_item_name = split[0];
                }
                for (String s : list) {


                    String[] split = s.split(",");
                    List<String> list1 = Arrays.asList(split);

                    String drugStandardName = getDrugStandardName(order_item_name, drugList);

                    if (s.contains(drugStandardName) && list1.indexOf(drugStandardName) != -1) {

                        String firstLevel = getFirstLevel(s);
                        Map<String, String> orderMap = new HashMap<>();
                        orderMap.put(order_item_name, firstLevel);
                        //方式 吸入or口服
                        String pharmacy_way_name = yizhuDocu.getString("pharmacy_way_name");
                        //名称
                        String china_approved_drug_name = yizhuDocu.getString("china_approved_drug_name");
                        if (china_approved_drug_name == null) {
                            continue;
                        }
                        String type = "";
                        if (pharmacy_way_name != null && (pharmacy_way_name.contains("口服") || pharmacy_way_name.contains("胶囊") || pharmacy_way_name.contains("片"))) {
                            type = "口服";
                        } else if (pharmacy_way_name != null && (pharmacy_way_name.contains("吸入"))) {
                            type = "吸入";
                        } else if (pharmacy_way_name != null && (pharmacy_way_name.contains("注射"))) {
                            type = "注射";
                        } else {
                            if (china_approved_drug_name != null && (china_approved_drug_name.contains("吸入"))) {
                                type = "吸入";
                            } else if (china_approved_drug_name != null & (china_approved_drug_name.contains("口服") || china_approved_drug_name.contains("胶囊") || china_approved_drug_name.contains("片"))) {
                                type = "口服";
                            } else if (china_approved_drug_name != null & (china_approved_drug_name.contains("注射"))) {
                                type = "注射";
                            } else {
                                type = "不明确" + pharmacy_way_name + "@" + china_approved_drug_name;
                            }
                        }
                        orderMap.put("type", type);
                        orderList.add(orderMap);
                    }
                }

            }

        }
        return orderList;
    }

    public Map<String, Set<String>> selYizhuById(Set<String> ids) {
        Map<String, Set<String>> map = new HashMap<>();
        for (String id : ids) {
            List<Document> countPatientId2 = Arrays.asList(
                    //过滤数据
                    new Document("$match", new Document("_id", id)),
                    new Document("$unwind", "$yizhu"),
                    new Document("$project", new Document("patient_id", 1).append("visit_id", 1).append("yizhu", 1)
                    )

            );
            AggregateIterable<Document> output = yizhu.aggregate(countPatientId2);
            Set<String> set = new HashSet<>();
            for (Document document : output) {
                Document yizhuDocu = (Document) document.get("yizhu");
                if (yizhuDocu == null) {
                    continue;
                }
                if (yizhuDocu.get("order_item_name") != null) {
                    set.add(yizhuDocu.getString("order_item_name"));
                }

            }
            map.put(id, set);
        }

        return map;
    }
    public Map<String, Set<String>> selDrugYizhuById(Set<String> ids) {
        Map<String, Set<String>> map = new HashMap<>();
        for (String id : ids) {
            List<Document> countPatientId2 = Arrays.asList(
                    //过滤数据
                    new Document("$unwind", "$yizhu"),
                    new Document("$match", new Document("_id", id)),
                    new Document("$match", new Document("yizhu.order_class_convert_name", "药品")),
                    new Document("$project", new Document("patient_id", 1).append("visit_id", 1).append("yizhu", 1)
                    )

            );
            AggregateIterable<Document> output = yizhu.aggregate(countPatientId2);
            Set<String> set = new HashSet<>();
            for (Document document : output) {
                Document yizhuDocu = (Document) document.get("yizhu");
                if (yizhuDocu == null) {
                    continue;
                }
                if (yizhuDocu.get("order_item_name") != null) {
                    set.add(yizhuDocu.getString("order_item_name"));
                }

            }
            map.put(id, set);
        }

        return map;
    }

    public List<Demo1ZhenduanBean> selShouyezhenduan(Set<String> ids) {
        List<Demo1ZhenduanBean> beanList = new ArrayList<>();
        for (String id : ids) {
            List<Document> countPatientId2 = Arrays.asList(
                    //过滤数据
                    new Document("$match", new Document("_id", id)),
                    new Document("$unwind", "$shouyezhenduan"),
                    new Document("$match", new Document("shouyezhenduan.diagnosis_name", "慢性阻塞性肺疾病急性加重")),
                    new Document("$match", new Document("shouyezhenduan.diagnosis_num", "1")),
                    new Document("$match", new Document("shouyezhenduan.diagnosis_type_name", "出院诊断")),
                    new Document("$project", new Document("patient_id", 1).append("visit_id", 1).append("shouyezhenduan", 1)
                    )

            );
            AggregateIterable<Document> output = shouyezhenduan.aggregate(countPatientId2);
            Set<String> set = new HashSet<>();
            for (Document document : output) {
                Document shouyezhenduandoc = (Document) document.get("shouyezhenduan");
                Demo1ZhenduanBean bean = new Demo1ZhenduanBean();
                bean.setId(id);
                if (shouyezhenduandoc == null) {
                    System.out.println("这是啥");
                    continue;
                } else {
                    String diagnosis_type_code = shouyezhenduandoc.getString("diagnosis_type_code");
                    String diagnosis_desc = shouyezhenduandoc.getString("diagnosis_desc");
                    String treat_result_name = shouyezhenduandoc.getString("treat_result_name");
                    String diagnosis_name = shouyezhenduandoc.getString("diagnosis_name");
                    String diagnosis_time = shouyezhenduandoc.getString("diagnosis_time");
                    String diagnosis_num = shouyezhenduandoc.getString("diagnosis_num");
                    bean.setDiagnosis_name(diagnosis_name);
                    bean.setDiagnosis_desc(diagnosis_desc);
                    bean.setDiagnosis_num(diagnosis_num);
                    bean.setDiagnosis_time(diagnosis_time);
                    bean.setTreat_result_name(treat_result_name);
                    bean.setDiagnosis_type_code(diagnosis_type_code);
                }
                beanList.add(bean);
            }
        }

        return beanList;
    }


    /**
     * 获取树
     *
     * @return
     */
    public List<DrugNode> getTree() {
        List<DrugNode> drugNodes = ReadFile.creatChildMedicineTree();
        return drugNodes;
    }

    /**
     * 获取子级
     *
     * @param name
     * @param drugNodes
     * @return
     */
    public Set<String> getAllChild(String name, List<DrugNode> drugNodes) {
        Set<String> allChildNameList = ReadFile.getAllChildNameList(name, drugNodes);
        return allChildNameList;

    }

    public List<String> getStrDataList() {
        List<String> allChildNameList = ReadFile.getStrData();
        return allChildNameList;

    }

    /**
     * 获取一级大类
     *
     * @param str
     * @return
     */
    public String getFirstLevel(String str) {
        String s = str.split(",")[0];
        return s;

    }


//    public static void main(String[] args) {
//        String illName = "慢性阻塞性肺疾病急性加重";
//        Set<String> strings = getIdsByIllName(illName);
//    }

    public void write2File(List<Demo1Bean> list) {
        BufferedWriter bufferedWriter = null;
//        File file = new File("/data/1/CDSS/tempData.txt");
//        File file = new File("/data/yiwendao/zzy/tempData.txt");
        File file = new File("C:/嘉和美康文档/3院测试数据/tempData.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (Demo1Bean demo1Bean : list) {
                StringBuffer sb = new StringBuffer();
                String id = demo1Bean.getId();
                sb.append(id + "/");
                String age = demo1Bean.getAge();
                sb.append(age + "/");
                double fee = demo1Bean.getFee();
                sb.append(fee + "/");
                String inHospitalDay = demo1Bean.getInHospitalDay();
                sb.append(inHospitalDay + "/");
                List<Map<String, String>> drugList = demo1Bean.getDrugList();
                sb.append(JSONObject.toJSONString(drugList == null ? "" : drugList));
                bufferedWriter.write(sb.toString());
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

    public void write2FileByStr(List<String> list) {
        BufferedWriter bufferedWriter = null;
        File file = new File("/data/1/CDSS/tempData.txt");
//        File file = new File("/data/yiwendao/zzy/tempData.txt");
//        File file = new File("C:/嘉和美康文档/3院测试数据/tempData.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (String s : list) {

                bufferedWriter.write(s);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
                System.out.println("====================写入成功");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public Set<String> getIds() {
        Set<String> ids = GetStandardRules.readFile2Cache("tempids");

        return ids;
    }

    public List<Demo1ZhenduanBean> getAllZhenduan(String id) {
        List<Demo1ZhenduanBean> beanList = new ArrayList<>();
        List<Document> countPatientId2 = Arrays.asList(
                //过滤数据
                new Document("$match", new Document("_id", id)),
                new Document("$unwind", "$shouyezhenduan"),
                new Document("$project", new Document("patient_id", 1).append("visit_id", 1).append("shouyezhenduan", 1)
                )

        );
        AggregateIterable<Document> output = shouyezhenduan.aggregate(countPatientId2);
        Set<String> set = new HashSet<>();
        for (Document document : output) {
            Document shouyezhenduandoc = (Document) document.get("shouyezhenduan");
            Demo1ZhenduanBean bean = new Demo1ZhenduanBean();
            bean.setId(id);
            if (shouyezhenduandoc == null) {
                System.out.println("这是啥");
                continue;
            } else {
                String diagnosis_type_code = shouyezhenduandoc.getString("diagnosis_type_code");
                String diagnosis_desc = shouyezhenduandoc.getString("diagnosis_desc");
                String treat_result_name = shouyezhenduandoc.getString("treat_result_name");
                String diagnosis_name = shouyezhenduandoc.getString("diagnosis_name");
                String diagnosis_time = shouyezhenduandoc.getString("diagnosis_time");
                String diagnosis_num = shouyezhenduandoc.getString("diagnosis_num");
                bean.setDiagnosis_name(diagnosis_name);
                bean.setDiagnosis_desc(diagnosis_desc);
                bean.setDiagnosis_num(diagnosis_num);
                bean.setDiagnosis_time(diagnosis_time);
                bean.setTreat_result_name(treat_result_name);
                bean.setDiagnosis_type_code(diagnosis_type_code);
            }
            beanList.add(bean);
        }
        return beanList;
    }

    public Set<String> getAllYizhu(String id) {
        Set<String> orderList = new HashSet<>();
        List<Document> countPatientId2 = Arrays.asList(
                //过滤数据
                new Document("$match", new Document("_id", id)),
                new Document("$unwind", "$yizhu"),
                new Document("$project", new Document("patient_id", 1).append("visit_id", 1).append("yizhu", 1)
                )

        );
        AggregateIterable<Document> output = yizhu.aggregate(countPatientId2);
        for (Document document : output) {
            Document yizhuDocu = (Document) document.get("yizhu");
            if (yizhuDocu == null) {
                continue;
            }
            if (yizhuDocu.get("order_item_name") != null) {
                String order_item_name = yizhuDocu.getString("order_item_name");

                orderList.add(order_item_name);
            }
        }
        return orderList;

    }


}



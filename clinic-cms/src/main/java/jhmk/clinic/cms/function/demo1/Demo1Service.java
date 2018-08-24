package jhmk.clinic.cms.function.demo1;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import jhmk.clinic.core.config.CdssConstans;
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
//                new Document("$match", new Document("shouyezhenduan.diagnosis_type_name", "慢性阻塞性肺疾病急性加重")),
//                        append("shouyezhenduan.diagnosis_num", "1")),
//                new Document("$match", new Document("shouyezhenduan.diagnosis_num", "1")),
//                new Document("$match", new Document("shouyezhenduan.diagnosis_type_code", "3")),
                new Document("$match", new Document("shouyezhenduan.diagnosis_name", name)),
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
    public double getInHosoitalDayById(String id) {
        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("_id", id))
        );
        AggregateIterable<Document> binli = ZHUYUANFEIYONG.aggregate(countPatientId);
        double total_fee = 0;
        for (Document document : binli) {


            if (document == null) {
                continue;
            }
            Document zhuyuanfeiyong = (Document) document.get("zhuyuanfeiyong");
            if (Objects.nonNull(zhuyuanfeiyong)) {

                total_fee = Double.valueOf(zhuyuanfeiyong.getString("total_fee"));
            }

        }
        return total_fee;
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

    public List<Map<String, String>> selYizhu(String id, List<String> list) {
        List<Document> countPatientId2 = Arrays.asList(
                //过滤数据
                new Document("$match", new Document("_id", id)),
                new Document("$unwind", "$yizhu"),
                new Document("$project", new Document("patient_id", 1).append("visit_id", 1).append("yizhu", 1)
                )

        );
        Map<String, String> orderMap = new HashMap<>();
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
                    if (s.contains(order_item_name) && list1.indexOf(order_item_name) != -1) {

                        String firstLevel = getFirstLevel(s);
                        orderMap.put("order_item_name", order_item_name);

                        orderMap.put(order_item_name, firstLevel);
                        //方式 吸入or口服
                        String pharmacy_way_name = yizhuDocu.getString("pharmacy_way_name");
                        //名称
                        String china_approved_drug_name = yizhuDocu.getString("china_approved_drug_name");
                        String type = "";
                        if (pharmacy_way_name!=null&&(pharmacy_way_name.contains("口服") || pharmacy_way_name.contains("胶囊") || pharmacy_way_name.contains("片"))) {
                            type = "口服";
                        } else if (pharmacy_way_name.contains("吸入") || pharmacy_way_name.contains("液")) {
                            type = "吸入";
                        } else {
                            if (china_approved_drug_name!=null&&(china_approved_drug_name.contains("吸入") || china_approved_drug_name.contains("胶囊") || china_approved_drug_name.contains("片"))) {
                                type = "吸入";
                            } else if (china_approved_drug_name.contains("口服") || china_approved_drug_name.contains("液")) {
                                type = "口服";
                            } else {
                                type = "不明确" + pharmacy_way_name + "/" + china_approved_drug_name;
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
                sb.append(id + ",");
                String age = demo1Bean.getAge();
                sb.append(age + ",");
                double fee = demo1Bean.getFee();
                sb.append(fee + ",");
                Double inHospitalDay = demo1Bean.getInHospitalDay();
                sb.append(inHospitalDay + ",");
                List<Map<String, String>> drugList = demo1Bean.getDrugList();
                sb.append(JSONObject.toJSONString(drugList));

//                for (Map<String, String> map : drugList) {
//                    for (Map.Entry<String, String> entry : map.entrySet()) {
//                        String key = entry.getKey();
//                        String value = entry.getValue();
//                    }
//                }

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
}

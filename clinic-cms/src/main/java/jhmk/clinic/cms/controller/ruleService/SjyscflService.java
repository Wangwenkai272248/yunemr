package jhmk.clinic.cms.controller.ruleService;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import jhmk.clinic.cms.SamilarService;
import jhmk.clinic.core.config.CdssConstans;
import jhmk.clinic.core.util.CompareUtil;
import jhmk.clinic.entity.bean.Misdiagnosis;
import jhmk.clinic.entity.bean.Shangjiyishichafanglu;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static jhmk.clinic.core.util.MongoUtils.getCollection;

/**
 * @author ziyu.zhou
 * @date 2018/9/6 14:28
 * 上级医师查房录
 */


@Service
public class SjyscflService {
    @Autowired
    SamilarService samilarService;
    MongoCollection<Document> binganshouye = getCollection(CdssConstans.DATASOURCE, CdssConstans.BINGANSHOUYE);
    //入院记录
    static MongoCollection<Document> shangjiyishichafanglu = getCollection(CdssConstans.DATASOURCE, CdssConstans.SJYSCFL);

    /**
     * 根据id获取上级医师查房录
     *
     * @param id
     * @return
     */

    public Misdiagnosis getSJYSCFL(String id) {
        Misdiagnosis jiwangshi = new Misdiagnosis();
        //是否明确诊断
        List<Map<String, String>> maptemp = new ArrayList<>();
        List<Document> countPatientId2 = Arrays.asList(
                //过滤数据
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("patient_id", 1).append("visit_id", 1).append("shangjiyishichafanglu", 1)
                )

        );
        AggregateIterable<Document> output = shangjiyishichafanglu.aggregate(countPatientId2);

        Map<String, String> stringMap = null;
        for (Document document : output) {

            ArrayList<Document> shangjiyishichafangluDocList = (ArrayList<Document>) document.get("shangjiyishichafanglu");
            for (Document shangjiyishichafangluDoc : shangjiyishichafangluDocList) {

                //既往史
                Document treatment_plan = (Document) shangjiyishichafangluDoc.get("treatment_plan");
                if (Objects.nonNull(treatment_plan)) {
                    String clear_diagnose = treatment_plan.getString("clear_diagnose");
                    String clear_diagnose_name = treatment_plan.getString("clear_diagnose_name");
                    if ("是".equals(clear_diagnose)) {
                        stringMap = new HashMap<>();
                        String last_modify_date_time = shangjiyishichafangluDoc.getString("last_modify_date_time");
                        stringMap.put(clear_diagnose_name, last_modify_date_time);
                        maptemp.add(stringMap);
                    }
                }
            }
        }
        jiwangshi.setSjData(maptemp);
        return jiwangshi;
    }

    public List<Shangjiyishichafanglu> getSJYSCFLBean(String id) {
        List<Shangjiyishichafanglu> list = new ArrayList<>();
        //是否明确诊断
        List<Map<String, String>> maptemp = new ArrayList<>();
        List<Document> countPatientId2 = Arrays.asList(
                //过滤数据
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("patient_id", 1).append("visit_id", 1).append("shangjiyishichafanglu", 1)
                )

        );
        AggregateIterable<Document> output = shangjiyishichafanglu.aggregate(countPatientId2);

        Shangjiyishichafanglu bean = null;

        for (Document document : output) {

            ArrayList<Document> shangjiyishichafangluDocList = (ArrayList<Document>) document.get("shangjiyishichafanglu");
            for (Document shangjiyishichafangluDoc : shangjiyishichafangluDocList) {

                //既往史
                Document treatment_plan = (Document) shangjiyishichafangluDoc.get("treatment_plan");
                if (Objects.nonNull(treatment_plan)) {
                    String clear_diagnose = treatment_plan.getString("clear_diagnose");
                    if (!"是".equals(clear_diagnose)) {
                        continue;
                    }
                    String clear_diagnose_name = treatment_plan.getString("clear_diagnose_name");
                    bean = new Shangjiyishichafanglu();
                    String last_modify_date_time = shangjiyishichafangluDoc.getString("last_modify_date_time");
                    bean.setLast_modify_date_time(last_modify_date_time);
                    bean.setClear_diagnose(clear_diagnose);
                    bean.setClear_diagnose_name(clear_diagnose_name);
                    list.add(bean);
                }
            }
        }
        Collections.sort(list, new CompareUtil.ImComparator(1, "last_modify_date_time"));
        return list;
    }

    public Map.Entry<String, String> getSJYSCFL(String id, Set<String> illNames) {
        //是否明确诊断
        List<Document> countPatientId2 = Arrays.asList(
                //过滤数据
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("patient_id", 1).append("visit_id", 1).append("shangjiyishichafanglu", 1)
                )

        );
        AggregateIterable<Document> output = shangjiyishichafanglu.aggregate(countPatientId2);

        Map<String, String> stringMap = new HashMap<>();
        for (Document document : output) {

            ArrayList<Document> shangjiyishichafangluDocList = (ArrayList<Document>) document.get("shangjiyishichafanglu");
            for (Document shangjiyishichafangluDoc : shangjiyishichafangluDocList) {

                //既往史
                Document treatment_plan = (Document) shangjiyishichafangluDoc.get("treatment_plan");
                if (Objects.nonNull(treatment_plan)) {
                    String clear_diagnose = treatment_plan.getString("clear_diagnose");
                    String clear_diagnose_name = treatment_plan.getString("clear_diagnose_name");
                    if ("是".equals(clear_diagnose)) {
                        String last_modify_date_time = shangjiyishichafangluDoc.getString("last_modify_date_time");
                        String[] split = clear_diagnose_name.split(" ");
                        for (String s : split) {
                            for (String s1 : illNames) {
                                if (s.contains(s1) || s1.contains(s)) {
                                    stringMap.put(clear_diagnose_name, last_modify_date_time);
                                    continue;
                                }
                            }
                        }
                    }
                }
            }
        }

        ArrayList<Map.Entry<String, String>> list = new ArrayList<>(stringMap.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        if (list.size() == 0) {
            return null;
        }
        Map.Entry<String, String> entry = list.get(0);
        return entry;
    }

    public Map<String, String> getNoSJYSCFL(String id, Set<String> illNames) {
        //是否明确诊断
        List<Document> countPatientId2 = Arrays.asList(
                //过滤数据
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("patient_id", 1).append("visit_id", 1).append("shangjiyishichafanglu", 1)
                )

        );
        AggregateIterable<Document> output = shangjiyishichafanglu.aggregate(countPatientId2);

        Map<String, String> stringMap = new HashMap<>();
        for (Document document : output) {

            ArrayList<Document> shangjiyishichafangluDocList = (ArrayList<Document>) document.get("shangjiyishichafanglu");
            if (shangjiyishichafangluDocList != null) {

                for (Document shangjiyishichafangluDoc : shangjiyishichafangluDocList) {

                    //既往史
                    Document treatment_plan = (Document) shangjiyishichafangluDoc.get("treatment_plan");
                    if (Objects.nonNull(treatment_plan)) {
                        String clear_diagnose = treatment_plan.getString("clear_diagnose");
                        String clear_diagnose_name = treatment_plan.getString("clear_diagnose_name");
                        boolean flag = true;
                        if ("是".equals(clear_diagnose)) {
                            String last_modify_date_time = shangjiyishichafangluDoc.getString("last_modify_date_time");
                            String[] split = clear_diagnose_name.split(" ");
                            for (String s : split) {
                                for (String s1 : illNames) {
                                    if (s.contains(s1) || s1.contains(s)) {
                                        flag = false;
                                    }
                                }
                            }
                        } else {
                            stringMap.put("是否标志", clear_diagnose);
                        }
                        if (!flag) {
                            stringMap.put("是否标志", clear_diagnose);
                            stringMap.put("诊断名称标志", "诊断名称不相同");
                        }
                    }
                }
            } else {
                stringMap.put("是否标志", "上级医师查房录为空");
            }

        }


        return stringMap;
    }


}

package jhmk.clinic.cms.controller.ruleService;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import jhmk.clinic.cms.SamilarService;
import jhmk.clinic.core.config.CdssConstans;
import jhmk.clinic.core.util.CompareUtil;
import jhmk.clinic.core.util.DateFormatUtil;
import jhmk.clinic.entity.bean.*;
import org.apache.commons.lang3.StringUtils;
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

        for (Document document : output) {
            if (document.get("shangjiyishichafanglu") == null) {
                continue;
            }
            ArrayList<Document> shangjiyishichafangluDocList = (ArrayList<Document>) document.get("shangjiyishichafanglu");
            if (shangjiyishichafangluDocList == null) {
                continue;
            }
            for (Document shangjiyishichafangluDoc : shangjiyishichafangluDocList) {

                //既往史
                Document treatment_plan = (Document) shangjiyishichafangluDoc.get("treatment_plan");
                if (Objects.nonNull(treatment_plan)) {
                    String clear_diagnose = treatment_plan.getString("clear_diagnose");
                    String clear_diagnose_name = treatment_plan.getString("clear_diagnose_name");
                    if ("是".equals(clear_diagnose)) {
                        Map<String, String> stringMap = new HashMap<>();
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
            if (Objects.isNull(shangjiyishichafangluDocList)){
                continue;
            }
            for (Document shangjiyishichafangluDoc : shangjiyishichafangluDocList) {

                //既往史
                Document treatment_plan = (Document) shangjiyishichafangluDoc.get("treatment_plan");
                if (Objects.nonNull(treatment_plan)) {
                    String clear_diagnose = treatment_plan.getString("clear_diagnose");
                    String src = treatment_plan.getString("src");
                    bean.setZjsrc(src);
                    String disease_name = treatment_plan.getString("disease_name");
                    bean.setJbmc(disease_name);
//                    if (!"是".equals(clear_diagnose)) {
//                        continue;
//                    }
                    String clear_diagnose_name = treatment_plan.getString("clear_diagnose_name");
                    bean = new Shangjiyishichafanglu();
                    String last_modify_date_time = shangjiyishichafangluDoc.getString("last_modify_date_time");
                    bean.setLast_modify_date_time(last_modify_date_time);
                    bean.setClear_diagnose(clear_diagnose);
                    bean.setClear_diagnose_name(clear_diagnose_name);
                    if (StringUtils.isNotBlank(clear_diagnose_name) && StringUtils.isNotBlank(last_modify_date_time)) {
                        list.add(bean);
                    }
                }
            }
        }
        if (list.size() > 1) {
            Collections.sort(list, new CompareUtil.ImComparator(1, "last_modify_date_time"));
        }
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

    public List<TreatmentPlan> getSrcById(String id, List<Yizhu> yizhus) {
        List<Document> countPatientId2 = Arrays.asList(
                //过滤数据
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("patient_id", 1).append("visit_id", 1).append("shangjiyishichafanglu", 1)
                )

        );
        AggregateIterable<Document> output = shangjiyishichafanglu.aggregate(countPatientId2);

        List<TreatmentPlan> list = new ArrayList<>();
        //第一次医嘱时间
        String yizhudate = yizhus.get(0).getOrder_begin_time().substring(0, 10);
        for (Document document : output) {

            ArrayList<Document> shangjiyishichafangluDocList = (ArrayList<Document>) document.get("shangjiyishichafanglu");
            if (shangjiyishichafangluDocList != null) {
                for (Document document1 : shangjiyishichafangluDocList) {
                    Object treatment_plan = document1.get("treatment_plan");
                    String file_time_value = document1.getString("file_time_value");

                    if (Objects.nonNull(treatment_plan)) {
                        Document treatment_plan1 = (Document) treatment_plan;
                        TreatmentPlan treatmentPlan = new TreatmentPlan();
                        String src = ((Document) treatment_plan).getString("src");
                        String medicine = ((Document) treatment_plan).getString("medicine");
                        treatmentPlan.setSrc(src);
                        if (medicine == null) {
                            continue;
                        }
                        String[] split = medicine.split(" ");
                        List<Drug2Yizhu> list1 = new ArrayList<>();
                        for (int i = 0; i < split.length; i++) {
                            Drug2Yizhu drug2Yizhu = new Drug2Yizhu();
                            String s = split[i];
                            drug2Yizhu.setName(s);
                            if (file_time_value.substring(0, 10).equals(yizhudate)) {
                                for (Yizhu yizhu : yizhus) {
                                    if (yizhu.getOrder_item_name().contains(s)) {
                                        String order_begin_time = yizhu.getOrder_begin_time().substring(0, 10);
                                        if (order_begin_time.equals(file_time_value.substring(0, 10))) {
                                            drug2Yizhu.setYizhuName(yizhu.getOrder_item_name());
                                            continue;
                                        }
                                    }
                                }
                            }
                            list1.add(drug2Yizhu);
                        }
                        treatmentPlan.setMedicine(list1);
                        treatmentPlan.setFile_time_value(file_time_value);
                        list.add(treatmentPlan);
                    }
                }
            }
        }
        Collections.sort(list, new Comparator<TreatmentPlan>() {
            @Override
            public int compare(TreatmentPlan o1, TreatmentPlan o2) {
                String file_time_value1 = o1.getFile_time_value();
                String file_time_value2 = o2.getFile_time_value();
                Date date1 = DateFormatUtil.parseDateBySdf(file_time_value1, DateFormatUtil.DATETIME_PATTERN_SS);
                Date date2 = DateFormatUtil.parseDateBySdf(file_time_value2, DateFormatUtil.DATETIME_PATTERN_SS);
                if (date1.getTime() >= date2.getTime()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        return list;
    }

}

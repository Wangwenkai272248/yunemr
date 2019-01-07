package jhmk.clinic.cms.controller.ruleService;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import jhmk.clinic.core.config.CdssConstans;
import jhmk.clinic.core.util.DateFormatUtil;
import jhmk.clinic.entity.bean.Jianyanbaogao;
import jhmk.clinic.entity.bean.JianyanbaogaoForAuxiliary;
import jhmk.clinic.entity.bean.JianyanbaogaoNew;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static jhmk.clinic.core.util.MongoUtils.getCollection;

/**
 * @author ziyu.zhou
 * @date 2018/12/21 16:50
 */

@Service
public class JybgService {
    MongoCollection<Document> jybg = getCollection(CdssConstans.DATASOURCE, CdssConstans.JYBG);

    /**
     * 检验报告
     *
     * @param id
     */
    public List<Jianyanbaogao> gtJybgById(String id) {

        List<Jianyanbaogao> jianyanbaogaoList = new ArrayList<>();
        Map<String, Jianyanbaogao> tempMap = new HashMap<>();
        List<Document> countPatientId2 = Arrays.asList(
                new Document("$match", new Document("_id", id))
        );
        AggregateIterable<Document> output = jybg.aggregate(countPatientId2);
        for (Document document : output) {
            Document jianyanbaogao = (Document) document.get("jianyanbaogao");
            if (jianyanbaogao == null) {
                continue;
            }
            Object lab_report = jianyanbaogao.get("lab_report");
            if (lab_report == null) {
                continue;
            }
            ArrayList<Document> labReportList = (ArrayList<Document>) lab_report;
            for (Document doc : labReportList) {
                String report_no = doc.getString("report_no");

                if (tempMap.containsKey(report_no)) {
                    Jianyanbaogao jianyanbaogaoTemp = tempMap.get(report_no);
                    List<JianyanbaogaoForAuxiliary> list = jianyanbaogaoTemp.getLabTestItems();
                    JianyanbaogaoForAuxiliary jianyanbaogaoForAuxiliary = new JianyanbaogaoForAuxiliary();
                    jianyanbaogaoForAuxiliary.setName(doc.getString("lab_sub_item_name"));
//                    jianyanbaogaoForAuxiliary.setLab_result(doc.getString("lab_result_value"));
//                    jianyanbaogaoForAuxiliary.setLab_result_value(doc.getString("lab_result_value"));
                    jianyanbaogaoForAuxiliary.setReference_range(doc.getString("range"));
                    jianyanbaogaoForAuxiliary.setLab_qual_result(doc.getString("lab_qual_result"));

                    Optional.ofNullable(doc.getString("lab_result_value_unit")).ifPresent(s -> jianyanbaogaoForAuxiliary.setUnit(s));
                    Optional.ofNullable(doc.getString("lab_result_value")).ifPresent(s -> jianyanbaogaoForAuxiliary.setLab_result_value(s));
                    Optional.ofNullable(doc.getString("lab_result_value")).ifPresent(s -> jianyanbaogaoForAuxiliary.setLab_result(s));
//                    jianyanbaogaoForAuxiliary.setUnit(doc.getString("lab_result_value_unit"));
                    jianyanbaogaoForAuxiliary.setResult_status_code(doc.getString("result_status_code"));
                    list.add(jianyanbaogaoForAuxiliary);
                    jianyanbaogaoTemp.setLabTestItems(list);
                    tempMap.put(report_no, jianyanbaogaoTemp);
                } else {
                    Jianyanbaogao jianyanbaogaoTemp = new Jianyanbaogao();
                    jianyanbaogaoTemp.setLab_item_name(doc.getString("lab_item_name"));
                    jianyanbaogaoTemp.setReport_no(report_no);
                    jianyanbaogaoTemp.setSpecimen(doc.getString("specimen"));
                    jianyanbaogaoTemp.setReport_time(doc.getString("report_time"));
                    List<JianyanbaogaoForAuxiliary> list = new ArrayList<>();
                    JianyanbaogaoForAuxiliary jianyanbaogaoForAuxiliary = new JianyanbaogaoForAuxiliary();
                    jianyanbaogaoForAuxiliary.setName(doc.getString("lab_sub_item_name"));
//                    jianyanbaogaoForAuxiliary.setLab_result(doc.getString("lab_result_value"));
//                    jianyanbaogaoForAuxiliary.setLab_result_value(doc.getString("lab_result_value"));
                    jianyanbaogaoForAuxiliary.setReference_range(doc.getString("range"));
                    jianyanbaogaoForAuxiliary.setLab_qual_result(doc.getString("lab_qual_result"));
//                    jianyanbaogaoForAuxiliary.setUnit(doc.getString("lab_result_value_unit"));
                    jianyanbaogaoForAuxiliary.setResult_status_code(doc.getString("result_status_code"));
                    Optional.ofNullable(doc.getString("lab_result_value_unit")).ifPresent(s -> jianyanbaogaoForAuxiliary.setUnit(s));
                    Optional.ofNullable(doc.getString("lab_result_value")).ifPresent(s -> jianyanbaogaoForAuxiliary.setLab_result_value(s));
                    Optional.ofNullable(doc.getString("lab_result_value")).ifPresent(s -> jianyanbaogaoForAuxiliary.setLab_result(s));
                    list.add(jianyanbaogaoForAuxiliary);
                    jianyanbaogaoTemp.setLabTestItems(list);
                    tempMap.put(report_no, jianyanbaogaoTemp);
                }

            }
        }
        for (Map.Entry<String, Jianyanbaogao> entry : tempMap.entrySet()) {
            jianyanbaogaoList.add(entry.getValue());
        }
        //删除重复项 一局spec ,保留最大时间
        Map<String, Optional<Jianyanbaogao>> collect = jianyanbaogaoList.stream().collect(Collectors.groupingBy(Jianyanbaogao::getSpecimen, Collectors.maxBy((o1, o2) -> DateFormatUtil.parseDate(o1.getReport_time(), DateFormatUtil.DATETIME_PATTERN_SS).compareTo(DateFormatUtil.parseDate(o2.getReport_time(), DateFormatUtil.DATETIME_PATTERN_SS)))));
        List<Jianyanbaogao> resultList = new ArrayList<>();
        for (Map.Entry<String, Optional<Jianyanbaogao>> entry : collect.entrySet()) {
            Jianyanbaogao originalJianyanbaogao = entry.getValue().get();
            resultList.add(originalJianyanbaogao);
        }
        return resultList;
    }

    public List<JianyanbaogaoNew> gtJybgnewById(String id) {

        List<JianyanbaogaoNew> jianyanbaogaoList = new ArrayList<>();
        List<Document> countPatientId2 = Arrays.asList(
                new Document("$match", new Document("_id", id))
        );
        AggregateIterable<Document> output = jybg.aggregate(countPatientId2);
        for (Document document : output) {
            Document jianyanbaogao = (Document) document.get("jianyanbaogao");
            if (jianyanbaogao == null) {
                continue;
            }
            Object lab_report = jianyanbaogao.get("lab_report");
            if (lab_report == null) {
                continue;
            }
            ArrayList<Document> labReportList = (ArrayList<Document>) lab_report;
            for (Document doc : labReportList) {
                JianyanbaogaoNew jianyanbaogaoTemp = new JianyanbaogaoNew();
                String report_no = doc.getString("report_no");
                jianyanbaogaoTemp.setReport_no(report_no);
                jianyanbaogaoTemp.setLab_sub_item_name(doc.getString("lab_sub_item_name"));
                jianyanbaogaoTemp.setSpecimen(doc.getString("specimen"));
                jianyanbaogaoTemp.setReport_time(doc.getString("report_time"));
                jianyanbaogaoTemp.setLab_item_name(doc.getString("lab_item_name"));
//                    jianyanbaogaoForAuxiliary.setLab_result_value(doc.getString("lab_result_value"));
                jianyanbaogaoTemp.setReference_range(doc.getString("range"));
                jianyanbaogaoTemp.setLab_qual_result(doc.getString("lab_qual_result"));

                Optional.ofNullable(doc.getString("lab_result_value_unit")).ifPresent(s -> jianyanbaogaoTemp.setLab_result_value_unit(s));
                Optional.ofNullable(doc.getString("lab_result_value")).ifPresent(s -> jianyanbaogaoTemp.setLab_result_value(s));
//                    jianyanbaogaoForAuxiliary.setUnit(doc.getString("lab_result_value_unit"));
                jianyanbaogaoTemp.setResult_status_code(doc.getString("result_status_code"));
                jianyanbaogaoList.add(jianyanbaogaoTemp);
            }
        }
        return jianyanbaogaoList;
    }

}

package jhmk.clinic.cms.controller.ruleService;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import jhmk.clinic.core.config.CdssConstans;
import jhmk.clinic.entity.bean.Menzhenjianyanbaogao;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static jhmk.clinic.core.util.MongoUtils.getCollection;

/**
 * @author ziyu.zhou
 * @date 2018/8/7 13:42
 * 门诊检验报告
 */
@Service
public class MzjybgService {
    static MongoCollection<Document> mzjybg = getCollection(CdssConstans.DATASOURCE, CdssConstans.MZJYBG);

    /**
     * 获取门诊检验报告
     * @param id
     * @return
     */
    public List<Menzhenjianyanbaogao> getMzjybgById(String id) {

        List<Menzhenjianyanbaogao> jianyanbaogaoList = new ArrayList<>();
        List<Document> countPatientId2 = Arrays.asList(
                new Document("$match", new Document("_id", id))
        );
        AggregateIterable<Document> output = mzjybg.aggregate(countPatientId2);
        for (Document document : output) {
            Document jianyanbaogao = (Document) document.get("menzhenjianyanbaogao");
            if (jianyanbaogao == null) {
                continue;
            }
            Object lab_report = jianyanbaogao.get("lab_report");
            if (lab_report == null) {
                continue;
            }
            ArrayList<Document> labReportList = (ArrayList<Document>) lab_report;
            for (Document doc : labReportList) {
                Menzhenjianyanbaogao jianyanbaogaoTemp = new Menzhenjianyanbaogao();
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

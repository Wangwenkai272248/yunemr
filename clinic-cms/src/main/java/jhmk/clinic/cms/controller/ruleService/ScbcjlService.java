package jhmk.clinic.cms.controller.ruleService;

import com.alibaba.fastjson.JSONArray;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import jhmk.clinic.core.config.CdssConstans;
import jhmk.clinic.entity.bean.Misdiagnosis;
import jhmk.clinic.entity.bean.Shoucibingchengjilu;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.*;

import static jhmk.clinic.core.util.MongoUtils.getCollection;

/**
 * @author ziyu.zhou
 * @date 2018/7/31 14:24
 * 首次病程记录sercice
 */
@Service
public class ScbcjlService {
    MongoCollection<Document> scbcjl = getCollection(CdssConstans.DATASOURCE, CdssConstans.SHOUCIBINGCHENGJILU);


    /**
     * 获取入院时间
     *
     * @param id
     * @return
     */
    public String getAdmissionTime(String id) {
        List<Misdiagnosis> misdiagnosisList = new LinkedList<>();
        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("patient_id", 1).append("_id", 1).append("visit_id", 1).append("binganshouye", 1))
//                , new Document("$skip", 5000),
//                new Document("$limit", 10000)
        );
        AggregateIterable<Document> output = scbcjl.aggregate(countPatientId);
        for (Document document : output) {
            Misdiagnosis misdiagnosis = new Misdiagnosis();
            if (document == null) {
                continue;
            }
            misdiagnosis.setPatient_id(document.getString("patient_id"));
            misdiagnosis.setVisit_id(document.getString("visit_id"));
            misdiagnosis.setId(document.getString("_id"));
            Document binganshouye = (Document) document.get("binganshouye");
            Document patVisit = (Document) binganshouye.get("pat_visit");

            String admission_time = patVisit.getString("admission_time");
            return admission_time;
        }
        return null;
    }

    public Shoucibingchengjilu getScbcjlById(String id) {
        List<Shoucibingchengjilu> list = new ArrayList<>();
        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("patient_id", 1).append("_id", 1).append("visit_id", 1).append("shoucibingchengjilu", 1))
//                , new Document("$skip", 5000),
//                new Document("$limit", 10000)
        );
        AggregateIterable<Document> output = scbcjl.aggregate(countPatientId);
        for (Document document : output) {
            Shoucibingchengjilu shoucibingchengjilu = new Shoucibingchengjilu();
            if (document == null) {
                continue;
            }
            Document shoucibingchengjiluDoc = (Document) document.get("shoucibingchengjilu");
            //诊断与鉴别诊断
            Object diagnosis_and_differential_diagnosis = shoucibingchengjiluDoc.get("diagnosis_and_differential_diagnosis");
            if (Objects.nonNull(diagnosis_and_differential_diagnosis)) {
                JSONArray jsonArray = (JSONArray) diagnosis_and_differential_diagnosis;
                Iterator<Object> iterator = jsonArray.iterator();
                while (iterator.hasNext()) {
                    Map next = (Map)iterator.next();
                    next.get("");
                }
                shoucibingchengjilu.setDifferential_diagnostic_disease_name("");
            }

            return shoucibingchengjilu;
        }
        return null;
    }
}

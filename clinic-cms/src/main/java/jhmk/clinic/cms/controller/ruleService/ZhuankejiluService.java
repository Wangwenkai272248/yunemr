package jhmk.clinic.cms.controller.ruleService;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import jhmk.clinic.core.config.CdssConstans;
import jhmk.clinic.entity.bean.Misdiagnosis;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static jhmk.clinic.core.util.MongoUtils.getCollection;

/**
 * @author ziyu.zhou
 * @date 2018/9/11 20:41
 */
@Service
public class ZhuankejiluService {
    MongoCollection<Document> zhuankejiluDoc = getCollection(CdssConstans.DATASOURCE, CdssConstans.ZHUANKEJILU);

    /**
     * 获取所有骨科信息
     *
     * @return
     */
//    public List<Misdiagnosis> getGukeData() {
//        List<Misdiagnosis> misdiagnosisList = new LinkedList<>();
//        List<Document> countPatientId = Arrays.asList(
//                new Document("$project", new Document("patient_id", 1).append("_id", 1).append("visit_id", 1).append("binganshouye", 1))
////                , new Document("$skip", 5000),
////                new Document("$limit", 10000)
//        );
//        AggregateIterable<Document> output = zhuankejiluDoc.aggregate(countPatientId);
//        for (Document document : output) {
//            Misdiagnosis misdiagnosis = new Misdiagnosis();
//            if (document == null) {
//                continue;
//            }
//            misdiagnosis.setPatient_id(document.getString("patient_id"));
//            misdiagnosis.setVisit_id(document.getString("visit_id"));
//            misdiagnosis.setId(document.getString("_id"));
//            Document binganshouye = (Document) document.get("binganshouye");
//            Document patVisit = (Document) binganshouye.get("pat_visit");
//
//            misdiagnosis.setDept_discharge_from_name(patVisit.getString("dept_admission_to_name"));
//            misdiagnosis.setDistrict_discharge_from_name(patVisit.getString("district_discharge_from_name"));
//            if (patVisit.getString("dept_admission_to_name").contains("骨科")) {
//                misdiagnosisList.add(misdiagnosis);
//            }
//        }
//        return misdiagnosisList;
//    }


    /**
     * 是否存在转科记录
     *
     * @param id
     * @return
     */
    public boolean getIsZhuanke(String id) {
        List<Misdiagnosis> misdiagnosisList = new LinkedList<>();
        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("patient_id", 1).append("_id", 1).append("visit_id", 1).append("zhuankejilu", 1))
        );
        AggregateIterable<Document> output = zhuankejiluDoc.aggregate(countPatientId);
        for (Document document : output) {
            if (document != null) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }



}

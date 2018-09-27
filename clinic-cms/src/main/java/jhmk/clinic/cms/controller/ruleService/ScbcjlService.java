package jhmk.clinic.cms.controller.ruleService;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import jhmk.clinic.core.config.CdssConstans;
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
    static MongoCollection<Document> scbcjl = getCollection(CdssConstans.DATASOURCE, CdssConstans.SHOUCIBINGCHENGJILU);


    /**
     * 根据id查询首次病程记录的鉴别诊断疾病名 鉴别原疾病
     * @param id
     * @return
     */
    public static List<Shoucibingchengjilu> getScbcjlById(String id) {
        List<Shoucibingchengjilu> list = new ArrayList<>();
        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("patient_id", 1).append("_id", 1).append("visit_id", 1).append("shoucibingchengjilu", 1))
        );
        AggregateIterable<Document> output = scbcjl.aggregate(countPatientId);
        for (Document document : output) {
            if (document == null) {
                continue;
            }
            Document shoucibingchengjiluDoc = (Document) document.get("shoucibingchengjilu");
            //诊断与鉴别诊断
            Object diagnosis_and_differential_diagnosis = shoucibingchengjiluDoc.get("diagnosis_and_differential_diagnosis");
            if (Objects.nonNull(diagnosis_and_differential_diagnosis)) {
                Document diagnosisAndDifferentialDiagnosisDoc = (Document) diagnosis_and_differential_diagnosis;
                Object first_course_differential_diagnosis = diagnosisAndDifferentialDiagnosisDoc.get("first_course_differential_diagnosis");
                if (Objects.nonNull(first_course_differential_diagnosis)) {
                    ArrayList diaArray = (ArrayList) first_course_differential_diagnosis;
                    Iterator<Object> iterator = diaArray.iterator();
                    List<String> differential_diagnostic_disease_nameList = new ArrayList<>();

                    while (iterator.hasNext()) {
                        Shoucibingchengjilu shoucibingchengjilu = new Shoucibingchengjilu();
                        Map<String, Object> next = (Map) iterator.next();
                        //鉴别诊断疾病名
                        Object differential_diagnostic_disease_name = next.get("differential_diagnostic_disease_name");
                        shoucibingchengjilu.setDifferential_diagnostic_disease_name(differential_diagnostic_disease_name.toString());
//                        鉴别原疾病
                        Object disease_name = next.get("disease_name");
                        if (Objects.nonNull(disease_name)) {
                            final ArrayList disease_nameList = (ArrayList) disease_name;
                            final String diseasename = disease_nameList.get(0).toString();
                            shoucibingchengjilu.setDisease_name(diseasename);
                        }
                        if (shoucibingchengjilu.getDisease_name() != null && shoucibingchengjilu.getDifferential_diagnostic_disease_name() != null) {
                            list.add(shoucibingchengjilu);
                        }
                    }
                }

            }

            return list;
        }
        return null;
    }

    public static void main(String[] args) {
        final List<Shoucibingchengjilu> scbcjlById = getScbcjlById("BJDXDSYY#000001559700#3");
        System.out.println(scbcjlById);
    }
}

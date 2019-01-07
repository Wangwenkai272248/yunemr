package jhmk.clinic.cms.controller.ruleService;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import jhmk.clinic.core.config.CdssConstans;
import jhmk.clinic.entity.bean.Binglizhenduan;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static jhmk.clinic.core.util.MongoUtils.getCollection;

/**
 * @author ziyu.zhou
 * @date 2018/7/31 14:19
 */
//首页诊断

@Service
public class BlzdService {
    MongoCollection<Document> binglizhenduan = getCollection(CdssConstans.DATASOURCE, CdssConstans.BINGLIZHENDUAN);

    /**
     * 获取首页诊断疾病集合
     *
     * @param id
     * @return
     */
    public List<String> getDiseaseList(String id) {
        List<String> list = new ArrayList<>();

        List<Document> countPatientId = Arrays.asList(
                new Document("$unwind", "$binglizhenduan"),
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("_id", 1).append("patient_id", 1).append("visit_id", 1).append("binglizhenduan", 1))
        );
        AggregateIterable<Document> binli = binglizhenduan.aggregate(countPatientId);
        for (Document document : binli) {
            Document binglizhenduan = (Document) document.get("binglizhenduan");
            String diagnosis_name = binglizhenduan.getString("diagnosis_name");
            list.add(diagnosis_name);
        }
        return list;
    }

    public List<Binglizhenduan> getBinglizhenduanById(String id) {
        List<Binglizhenduan> list = new ArrayList<>();

        List<Document> countPatientId = Arrays.asList(
                new Document("$unwind", "$binglizhenduan"),
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("_id", 1).append("patient_id", 1).append("visit_id", 1).append("binglizhenduan", 1))
        );
        AggregateIterable<Document> binli = binglizhenduan.aggregate(countPatientId);
        for (Document document : binli) {
            Binglizhenduan bean=new Binglizhenduan();
            Document binglizhenduan = (Document) document.get("binglizhenduan");
            String diagnosis_name = binglizhenduan.getString("diagnosis_name");
            bean.setDiagnosis_name(diagnosis_name);
            bean.setDiagnosis_time(binglizhenduan.getString("diagnosis_time"));
            bean.setDiagnosis_num(binglizhenduan.getString("diagnosis_num"));
            bean.setDiagnosis_type_name(binglizhenduan.getString("diagnosis_type_name"));
            list.add(bean);
        }
        return list;
    }

    /**
     * 获取出院诊断主诊断
     *
     * @param id
     * @return
     */
    public String getMainDisease(String id) {
        List<String> list = new ArrayList<>();

        List<Document> countPatientId = Arrays.asList(
                new Document("$unwind", "$binglizhenduan"),
                new Document("$match", new Document("_id", id)),
                new Document("$match", new Document("binglizhenduan.diagnosis_type_name", "初步诊断")),
                new Document("$match", new Document("binglizhenduan.diagnosis_num", "1")),
                new Document("$project", new Document("_id", 1).append("patient_id", 1).append("visit_id", 1).append("binglizhenduan", 1))
        );
        AggregateIterable<Document> binli = binglizhenduan.aggregate(countPatientId);
        String diagnosis_name = "";
        for (Document document : binli) {
            Document binglizhenduan = (Document) document.get("binglizhenduan");
            diagnosis_name = binglizhenduan.getString("diagnosis_name");
        }
        return diagnosis_name;
    }

}

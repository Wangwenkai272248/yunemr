package jhmk.clinic.cms.controller.ruleService;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import jhmk.clinic.core.config.CdssConstans;
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
public class SyzdService {

    MongoCollection<Document> shouyezhenduan = getCollection(CdssConstans.DATASOURCE, CdssConstans.SHOUYEZHENDUAN);

    /**
     * 获取首页诊断疾病集合
     *
     * @param id
     * @return
     */
    public List<String> getDiseaseList(String id) {
        List<String> list = new ArrayList<>();

        List<Document> countPatientId = Arrays.asList(
                new Document("$unwind", "$shouyezhenduan"),
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("_id", 1).append("patient_id", 1).append("visit_id", 1).append("shouyezhenduan", 1))
        );
        AggregateIterable<Document> binli = shouyezhenduan.aggregate(countPatientId);
        for (Document document : binli) {
            Document binglizhenduan = (Document) document.get("shouyezhenduan");
            String diagnosis_name = binglizhenduan.getString("diagnosis_name");
            list.add(diagnosis_name);
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
                new Document("$unwind", "$shouyezhenduan"),
                new Document("$match", new Document("_id", id)),
                new Document("$match", new Document("shouyezhenduan.diagnosis_type_name", "出院诊断")),
                new Document("$match", new Document("shouyezhenduan.diagnosis_num", "1")),
                new Document("$project", new Document("_id", 1).append("patient_id", 1).append("visit_id", 1).append("shouyezhenduan", 1))
        );
        AggregateIterable<Document> binli = shouyezhenduan.aggregate(countPatientId);
        String diagnosis_name = "";
        for (Document document : binli) {
            Document binglizhenduan = (Document) document.get("shouyezhenduan");
            diagnosis_name = binglizhenduan.getString("diagnosis_name");
        }
        return diagnosis_name;
    }

    /**
     * 获取入院初诊
     * @param id
     * @return
     */
    public String getRycz(String id) {
        List<String> list = new ArrayList<>();

        List<Document> countPatientId = Arrays.asList(
                new Document("$unwind", "$shouyezhenduan"),
                new Document("$match", new Document("_id", id)),
                new Document("$match", new Document("shouyezhenduan.diagnosis_type_name", "入院初诊")),
                new Document("$match", new Document("shouyezhenduan.diagnosis_num", "1")),
                new Document("$project", new Document("_id", 1).append("patient_id", 1).append("visit_id", 1).append("shouyezhenduan", 1))
        );
        AggregateIterable<Document> binli = shouyezhenduan.aggregate(countPatientId);
        String diagnosis_name = "";
        for (Document document : binli) {
            Document binglizhenduan = (Document) document.get("shouyezhenduan");
            diagnosis_name = binglizhenduan.getString("diagnosis_name");
        }
        return diagnosis_name;
    }


}

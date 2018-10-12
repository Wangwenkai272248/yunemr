package jhmk.clinic.cms.controller.ruleService;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import jhmk.clinic.core.config.CdssConstans;
import jhmk.clinic.entity.bean.Chuyuanjilu;
import jhmk.clinic.entity.bean.Shoucibingchengjilu;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.*;

import static jhmk.clinic.core.util.MongoUtils.getCollection;

/**
 * @author ziyu.zhou
 * @date 2018/10/8 18:29
 */

//出院记录
@Service
public class CyjlService {
    MongoCollection<Document> chuyuanjilu = getCollection(CdssConstans.DATASOURCE, CdssConstans.CHUYUANJILU);

    public Chuyuanjilu getScbcjlById(String id) {
        List<Shoucibingchengjilu> list = new ArrayList<>();
        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("patient_id", 1).append("_id", 1).append("visit_id", 1).append("chuyuanjilu", 1))
        );
        Chuyuanjilu chuyuanjiluBean = new Chuyuanjilu();
        List<Map<String, String>> mapList = new ArrayList<>();
        AggregateIterable<Document> output = chuyuanjilu.aggregate(countPatientId);
        String src = null;
        for (Document document : output) {
            if (document == null) {
                continue;
            }
            Document chuyuanjiluDoc = (Document) document.get("chuyuanjilu");
            if (Objects.nonNull(chuyuanjiluDoc)) {
                src = chuyuanjiluDoc.getString("src");
                chuyuanjiluBean.setSrc(src);
                Object treatment_list = chuyuanjiluDoc.get("treatment_list");
                if (Objects.nonNull(treatment_list)) {
                    ArrayList treatment_list1 = (ArrayList) treatment_list;
                    if (treatment_list1 != null) {
                        for (Object object : treatment_list1) {
                            Map object1 = (Map) object;
                            Object medicine = object1.get("medicine");
                            if (Objects.nonNull(medicine)) {
                                ArrayList medicineList = (ArrayList) medicine;
                                for (Object obj : medicineList) {
                                    Map obj1 = (Map) obj;
                                    mapList.add(obj1);
                                }
                            }
                        }


                    }

                }
            }

        }
        chuyuanjiluBean.setMedicineList(mapList);
        return chuyuanjiluBean;
    }
}

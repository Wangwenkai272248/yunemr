package jhmk.clinic.cms.controller.ruleService;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import jhmk.clinic.core.config.CdssConstans;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static jhmk.clinic.core.util.MongoUtils.getCollection;

/**
 * @author ziyu.zhou
 * @date 2018/7/31 14:24
 */
@Service
public class ZhuyuanfeiyongService {
    MongoCollection<Document> ZHUYUANFEIYONG = getCollection(CdssConstans.DATASOURCE, CdssConstans.ZHUYUANFEIYONG);
    /**
     * 消费费用
     *
     * @param id
     */
    public Float getTotalFeeById(String id) {
        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("_id", id))
        );
        AggregateIterable<Document> binli = ZHUYUANFEIYONG.aggregate(countPatientId);
        float total_fee = 0;
        for (Document document : binli) {
            Document zhuyuanfeiyong = (Document) document.get("zhuyuanfeiyong");
            total_fee = Float.valueOf(zhuyuanfeiyong.getString("total_fee"));

        }
        return total_fee;
    }



}

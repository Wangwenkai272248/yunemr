package jhmk.clinic.cms.controller.ruleService;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import jhmk.clinic.core.config.CdssConstans;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.*;

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

    /**
     *功能描述
     *@author swq
     *@date 2019-1-30  17:40
     *@param: idList
     *@return java.util.List<java.util.Map < java.lang.String , java.lang.Object>>
     *@desc 获取mongo中ZHUYUANFEIYONG罕见病相关信息
     */
    public List<Map<String,Object>> zhuyuanfeiyongService(List<Object> idList){
        List<Map<String,Object>> listMap = new ArrayList<>();
        //处理首页诊断
        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("_id", new Document("$in",idList)))
        );
        AggregateIterable<Document> zhuyuanfeiyong = ZHUYUANFEIYONG.aggregate(countPatientId);
        for (Document document : zhuyuanfeiyong) {
            Map<String,Object> returnMap = new HashMap<>();
            Document zyfy = (Document) document.get("zhuyuanfeiyong");
            String id = document.getString("_id");
            String totalFee = zyfy.getString("total_fee");
            returnMap.put("id",id);
            returnMap.put("totalFee",totalFee);
            listMap.add(returnMap);
        }
        return listMap;
    }


}

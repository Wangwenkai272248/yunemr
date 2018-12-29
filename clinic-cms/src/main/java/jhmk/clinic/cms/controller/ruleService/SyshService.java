package jhmk.clinic.cms.controller.ruleService;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import jhmk.clinic.core.config.CdssConstans;
import jhmk.clinic.entity.bean.Shouyeshoushu;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.*;

import static jhmk.clinic.core.util.MongoUtils.getCollection;

/**
 * 首页手术
 *
 * @author ziyu.zhou
 * @date 2018/12/29 10:14
 */
@Service
public class SyshService {
    MongoCollection<Document> sysh = getCollection(CdssConstans.DATASOURCE, CdssConstans.SHOUYESHOUSHU);

    /**
     * 获取首页诊断疾病集合
     *
     * @param id
     * @return
     */
    public List<Shouyeshoushu> getShouyeshoushu(String id) {
        List<Shouyeshoushu> list = new ArrayList<>();

        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("_id", id))
        );
        AggregateIterable<Document> binli = sysh.aggregate(countPatientId);
        for (Document document : binli) {
            Shouyeshoushu shouyeshoushu = new Shouyeshoushu();
            Object shouyeshoushuDoc = document.get("shouyeshoushu");
            if (Objects.nonNull(shouyeshoushuDoc)) {
                ArrayList listSysh = (ArrayList) shouyeshoushuDoc;
                Iterator iterator = listSysh.iterator();
                while (iterator.hasNext()) {
                    Document next = (Document) iterator.next();
                    String operation_name = next.getString("operation_name");
                    shouyeshoushu.setOperation_name(operation_name);
                    shouyeshoushu.setAnesthesia_method_name(next.getString("anesthesia_method_name"));
                    shouyeshoushu.setOperation_type(next.getString("operation_type"));
                    shouyeshoushu.setOperation_date(next.getString("operation_date"));
                    shouyeshoushu.setOperation_num(next.getString("operation_num"));
                    list.add(shouyeshoushu);
                }
            }

        }
        return list;
    }

}

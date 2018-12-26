package jhmk.clinic.cms.controller.ruleService;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import jhmk.clinic.core.config.CdssConstans;
import jhmk.clinic.core.util.DateFormatUtil;
import jhmk.clinic.entity.bean.Yizhu;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.*;

import static jhmk.clinic.core.util.MongoUtils.getCollection;

/**
 * @author ziyu.zhou
 * @date 2018/10/8 19:01
 */

@Service
public class YizhuService {
    static MongoCollection<Document> yizhu = getCollection(CdssConstans.DATASOURCE, CdssConstans.YIZHU);

    public List<Yizhu> selYizhu(String id) {
        List<Document> countPatientId2 = Arrays.asList(
//过滤数据
                new Document("$match", new Document("_id", id)),
                new Document("$unwind", "$yizhu"),
                new Document("$project", new Document("patient_id", 1).append("visit_id", 1).append("yizhu", 1)
                )

        );
        AggregateIterable<Document> output = yizhu.aggregate(countPatientId2);
        List<Yizhu> orderList = new LinkedList<>();
        for (Document document : output) {
            Document yizhuDocu = (Document) document.get("yizhu");
            String patient_id = document.getString("patient_id");
            String visit_id = document.getString("visit_id");
            if (yizhuDocu == null) {
                continue;
            }
            Yizhu yizhu = new Yizhu();
            if (yizhuDocu.get("order_item_name") != null) {
                String order_class_convert_name = yizhuDocu.getString("order_class_convert_name");
                //给药途径和方法/用途  如果是出院带药排除
                String pharmacy_way_name = yizhuDocu.getString("pharmacy_way_name");
                if (!"药品".equals(order_class_convert_name)||"出院带药".equals(pharmacy_way_name)) {
                    continue;
                }
                String order_item_name = yizhuDocu.getString("order_item_name");
                yizhu.setOrder_item_name(order_item_name);
                String order_begin_time = yizhuDocu.getString("order_begin_time");
                yizhu.setOrder_begin_time(order_begin_time);
                String order_end_time = yizhuDocu.getString("order_end_time");
                yizhu.setOrder_end_time(order_end_time);
                String order_properties_name = yizhuDocu.getString("order_properties_name");
                yizhu.setOrder_properties_name(order_properties_name);
                String frequencyName = yizhuDocu.getString("frequency_name");
                yizhu.setFrequency_name(frequencyName);
                String dosageValue = yizhuDocu.getString("dosage_value");
                yizhu.setDosage_value(dosageValue);
                String dosageValueUnit = yizhuDocu.getString("dosage_value_unit");
                yizhu.setDosage_value_unit(dosageValueUnit);
                Optional.ofNullable(yizhuDocu.getString("order_class_code")).ifPresent(s -> yizhu.setOrder_class_code(s));
                Optional.ofNullable(yizhuDocu.getString("pharmacy_purpose_name")).ifPresent(s -> yizhu.setPharmacy_way_name(s));
                Optional.ofNullable(yizhuDocu.getString("order_time")).ifPresent(s -> yizhu.setOrder_time(s));
                Optional.ofNullable(yizhuDocu.getString("order_class_convert_name")).ifPresent(s -> yizhu.setOrder_class_convert_name(s));
                Optional.ofNullable(yizhuDocu.getString("china_approved_drug_name")).ifPresent(s -> yizhu.setChina_approved_drug_name(s));
                Optional.ofNullable(yizhuDocu.getString("frequency_code")).ifPresent(s -> yizhu.setFrequency_code(s));
                Optional.ofNullable(yizhuDocu.getString("drug_amount_value_unit")).ifPresent(s -> yizhu.setDrug_amount_value_unit(s));
                Optional.ofNullable(yizhuDocu.getString("drug_amount_value")).ifPresent(s -> yizhu.setDrug_amount_value(s));
                Optional.ofNullable(yizhuDocu.getString("pharmacy_way_name")).ifPresent(s -> yizhu.setPharmacy_way_name(s));
                Optional.ofNullable(yizhuDocu.getString("drug_amount_value")).ifPresent(s -> yizhu.setDrug_amount_value(s));
                Optional.ofNullable(yizhuDocu.getString("total_dosage_unit")).ifPresent(s -> yizhu.setTotal_dosage_unit(s));
                Optional.ofNullable(yizhuDocu.getString("total_dosage_value")).ifPresent(s -> yizhu.setTotal_dosage_value(s));
                Optional.ofNullable(yizhuDocu.getString("specification")).ifPresent(s -> yizhu.setSpecification(s));
                yizhu.setPatient_id(patient_id);
                yizhu.setVisit_id(visit_id);
                orderList.add(yizhu);
            }

        }
        Collections.sort(orderList, new Comparator<Yizhu>() {
            @Override
            public int compare(Yizhu o1, Yizhu o2) {
                String order_begin_time1 = o1.getOrder_begin_time();
                String order_begin_time2 = o2.getOrder_begin_time();
                Date date1 = DateFormatUtil.parseDateBySdf(order_begin_time1, DateFormatUtil.DATETIME_PATTERN_SS);
                Date date2 = DateFormatUtil.parseDateBySdf(order_begin_time2, DateFormatUtil.DATETIME_PATTERN_SS);
                if (date1.getTime() >= date2.getTime()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        return orderList;
    }

}

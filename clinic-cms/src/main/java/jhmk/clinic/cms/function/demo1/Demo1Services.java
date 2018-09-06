//package jhmk.clinic.cms.function.demo1;
//
//import com.mongodb.client.AggregateIterable;
//import com.mongodb.client.MongoCollection;
//import jhmk.clinic.core.config.CdssConstans;
//import org.bson.Document;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static jhmk.clinic.core.util.MongoUtils.getCollection;
//
///**
// * @author ziyu.zhou
// * @date 2018/8/27 13:59
// */
//@Service
//public class Demo1Services {
//
//    static MongoCollection<Document> shouyezhenduan = getCollection(CdssConstans.DATASOURCE, CdssConstans.SHOUYEZHENDUAN);
//    MongoCollection<Document> binganshouye = getCollection(CdssConstans.DATASOURCE, CdssConstans.BINGANSHOUYE);
//    MongoCollection<Document> ZHUYUANFEIYONG = getCollection(CdssConstans.DATASOURCE, CdssConstans.ZHUYUANFEIYONG);
//    static MongoCollection<Document> yizhu = getCollection(CdssConstans.DATASOURCE, CdssConstans.YIZHU);
//
//
//    public List<Demo1Bean> getIdsByIllName(String name) {
//        List<Demo1Bean> maps = new ArrayList<>();
//
//        List<Document> countPatientId = Arrays.asList(
//                new Document("$unwind", "$shouyezhenduan"),
//                new Document("$match", new Document("shouyezhenduan.diagnosis_name", name)),
////                new Document("$match", new Document("shouyezhenduan.diagnosis_num", "1")),
////                new Document("$match", new Document("shouyezhenduan.diagnosis_type_name", "出院诊断")),
//                new Document("$project", new Document("_id", 1).append("shouyezhenduan", 1))
//
//        );
//        AggregateIterable<Document> binli = shouyezhenduan.aggregate(countPatientId);
//        for (Document document : binli) {
//            String id = document.getString("_id");
//            maps.add(id);
//            continue;
//        }
//        return maps;
//    }
//
//}

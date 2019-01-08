package jhmk.clinic.cms.controller.ruleService;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import jhmk.clinic.core.config.CdssConstans;
import jhmk.clinic.entity.bean.Menzhenjianchabaogao;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.*;

import static jhmk.clinic.core.util.MongoUtils.getCollection;

/**
 * @author ziyu.zhou
 * @date 2018/8/7 13:42
 * 门诊检查报告
 */

@Service
public class MzjcbgService {
    static MongoCollection<Document> mzjybg = getCollection(CdssConstans.DATASOURCE, CdssConstans.MZJCBG);


    //检查报告
    public List<Menzhenjianchabaogao> getMzjcbgById(String id) {

        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("_id", id))
        );
        AggregateIterable<Document> aggregate = mzjybg.aggregate(countPatientId);
        MongoCursor<Document> iterator = aggregate.iterator();
        List<Menzhenjianchabaogao> jiancha = new LinkedList<>();
        while (iterator.hasNext()) {
            Document next = iterator.next();
            Document jianchabaogao = next.get("menzhenjianchabaogao", Document.class);
            if (Objects.nonNull(jianchabaogao)) {
                if (jianchabaogao.get("exam_report") != null) {
                    Object lab_report1 = jianchabaogao.get("exam_report");

                    List<Document> lab_report = jianchabaogao.get("exam_report", List.class);
                    for (Document d : lab_report) {
                        Menzhenjianchabaogao menzhenjianchabaogao=new Menzhenjianchabaogao();
                        Optional.ofNullable(d.getString("exam_item_name")).ifPresent(s->menzhenjianchabaogao.setExam_item_name(s));
                        Optional.ofNullable(d.getString("exam_diag")).ifPresent(s->menzhenjianchabaogao.setExam_diag(s));
                        Optional.ofNullable(d.getString("exam_feature")).ifPresent(s->menzhenjianchabaogao.setExam_feature(s));
                        Optional.ofNullable(d.getString("exam_time")).ifPresent(s->menzhenjianchabaogao.setExam_time(s));
                        Optional.ofNullable(d.getString("standard_name")).ifPresent(s->menzhenjianchabaogao.setStandard_name(s));
                        Optional.ofNullable(d.getString("exam_class_name")).ifPresent(s->menzhenjianchabaogao.setExam_class_name(s));
                        jiancha.add(menzhenjianchabaogao);
                    }
                }
            }
        }

        return jiancha;
    }


}

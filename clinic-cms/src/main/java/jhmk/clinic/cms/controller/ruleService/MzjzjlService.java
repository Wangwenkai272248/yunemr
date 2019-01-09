package jhmk.clinic.cms.controller.ruleService;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import jhmk.clinic.core.config.CdssConstans;
import jhmk.clinic.core.util.MongoUtils;
import jhmk.clinic.entity.bean.Mzbinganshouye;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author ziyu.zhou
 * @date 2018/7/31 14:24
 * 门诊就诊记录
 */
@Service
public class MzjzjlService {

    MongoCollection<Document> mzjzjl = MongoUtils.getCollection(CdssConstans.DATASOURCE, CdssConstans.mzjzjl);

    public Mzbinganshouye getMzBinganshouyeById(String id) {
//        Pattern pattern = Pattern.compile("^.*耳鼻.*$", Pattern.CASE_INSENSITIVE);
        Mzbinganshouye mzbinganshouye=new Mzbinganshouye();
        List<Document> countPatientId2 = Arrays.asList(
                //过滤数据
                new Document("$match", new Document("_id", id))

        );

        AggregateIterable<Document> output = mzjzjl.aggregate(countPatientId2);

        for (Document document : output) {

            Object menzhenjiuzhenjilu = document.get("menzhenjiuzhenjilu");
            if (menzhenjiuzhenjilu!=null){
                Document menzhenjiuzhenjiluDoc = (Document) menzhenjiuzhenjilu;
                Optional.ofNullable(menzhenjiuzhenjiluDoc.getString("visit_dept_name")).ifPresent(s->mzbinganshouye.setPat_visit_dept_admission_to_name(s));
                Optional.ofNullable(menzhenjiuzhenjiluDoc.getString("visit_dept_code")).ifPresent(s->mzbinganshouye.setPat_visit_dept_admission_to_code(s));
                Optional.ofNullable(menzhenjiuzhenjiluDoc.getString("age_value")).ifPresent(s->mzbinganshouye.setPat_info_age_value(s));
                Optional.ofNullable(menzhenjiuzhenjiluDoc.getString("sex_name")).ifPresent(s->mzbinganshouye.setPat_info_sex_name(s));
            }
        }
        return mzbinganshouye;
    }


}

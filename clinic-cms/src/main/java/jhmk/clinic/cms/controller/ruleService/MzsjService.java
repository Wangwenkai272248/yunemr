package jhmk.clinic.cms.controller.ruleService;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import jhmk.clinic.core.config.CdssConstans;
import jhmk.clinic.entity.bean.Mzbinganshouye;
import org.apache.commons.lang3.ObjectUtils;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.*;

import static jhmk.clinic.core.util.MongoUtils.getCollection;

/**
 * @author ziyu.zhou
 * @date 2018/8/7 13:42
 * 门诊数据  =门诊的入院记录
 */
@Service
public class MzsjService {
    static MongoCollection<Document> mzsj = getCollection(CdssConstans.DATASOURCE, CdssConstans.MENZHENSHUJU);

    /**
     * 获取门诊检验报告
     *
     * @param id
     * @return
     */

    public Mzbinganshouye getMzbasyById(String id) {
        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("_id", id))
        );
        AggregateIterable<Document> output = mzsj.aggregate(countPatientId);
        Mzbinganshouye misdiagnosis = new Mzbinganshouye();
        for (Document document : output) {
            if (document == null) {
                continue;
            }
            misdiagnosis.setPatient_id(document.getString("patient_id"));
            misdiagnosis.setVisit_id(document.getString("visit_id"));
            misdiagnosis.setId(document.getString("_id"));
            Document menzhenshuju = (Document) document.get("menzhenshuju");
            if (Objects.isNull(menzhenshuju)) {
                return null;
            }
            if (Objects.nonNull(menzhenshuju.get("pat_info"))) {
                Document patInfo = (Document) menzhenshuju.get("pat_info");
                String sexName = patInfo.getString("sex_name");
                misdiagnosis.setPat_info_sex_name(sexName);
            }
            if (Objects.nonNull(menzhenshuju.get("pat_visit"))) {

                Document patVisit = (Document) menzhenshuju.get("pat_visit");
                Optional.ofNullable(patVisit.getString("age_value")).ifPresent(s -> misdiagnosis.setPat_info_age_value(s));
                Optional.ofNullable(patVisit.getString("dept_admission_to_name")).ifPresent(s -> misdiagnosis.setDept_admission_to_name(s));
            }

            return misdiagnosis;
        }
        return null;
    }


    //根据id查询入院记录
    public List<Map<String, String>> getMzsjById(String id) throws NullPointerException {

        List<Map<String, String>> menzhenshujuList = new ArrayList<>();
        List<Document> countPatientId2 = Arrays.asList(
                new Document("$match", new Document("_id", id))
        );
        AggregateIterable<Document> output = mzsj.aggregate(countPatientId2);
        for (Document document : output) {

            Document menzhenshuju = (Document) document.get("menzhenshuju");
            if (menzhenshuju == null) {
                return null;
            }
            if (Objects.nonNull(menzhenshuju.get("chief_complaint"))) {
                Document chief_complaint = (Document) menzhenshuju.get("chief_complaint");
                if (chief_complaint != null) {
                    Map<String, String> mapzs = new LinkedHashMap<>();
                    mapzs.put("key", "主诉");
                    mapzs.put("value", chief_complaint.getString("src"));
                    //主诉
                    menzhenshujuList.add(mapzs);
                }
            }
            //现病史
            Document history_of_present_illness = (Document) menzhenshuju.get("history_of_present_illness");
            if (history_of_present_illness != null) {

                Map<String, String> mapxbs = new LinkedHashMap<>();
                mapxbs.put("key", "现病史");
                mapxbs.put("value", history_of_present_illness.getString("src"));
                menzhenshujuList.add(mapxbs);
            }
            //既往史
            Document history_of_past_illness = (Document) menzhenshuju.get("history_of_past_illness");
            if (history_of_past_illness != null) {
                Map<String, String> jws = new LinkedHashMap<>();
                jws.put("key", "既往史");
                jws.put("value", history_of_past_illness.getString("src"));
                menzhenshujuList.add(jws);
            }
            //个人史

            Document social_history = (Document) menzhenshuju.get("social_history");
            if (social_history != null) {

                Map<String, String> grs = new LinkedHashMap<>();
                grs.put("key", "个人史");
                grs.put("value", social_history.getString("src"));
                menzhenshujuList.add(grs);
            }

            //婚姻史
            Document menstrual_and_obstetrical_histories = (Document) menzhenshuju.get("menstrual_and_obstetrical_histories");
            if (menstrual_and_obstetrical_histories != null) {

                Map<String, String> hys = new LinkedHashMap<>();
                hys.put("key", "婚姻史");
                hys.put("value", menstrual_and_obstetrical_histories.getString("src"));
                menzhenshujuList.add(hys);
            }

            //家族史
            Document history_of_family_member_diseases = (Document) menzhenshuju.get("history_of_family_member_diseases");
            if (history_of_family_member_diseases != null) {
                Map<String, String> jzs = new LinkedHashMap<>();
                jzs.put("key", "家族史");
                jzs.put("value", history_of_family_member_diseases.getString("src"));
                menzhenshujuList.add(jzs);
            }
            //辅助检查
            Document physical_examination = (Document) menzhenshuju.get("physical_examination");
            if (physical_examination != null) {
                Map<String, String> tgjc = new LinkedHashMap<>();
                tgjc.put("key", "体格检查");
                tgjc.put("value", physical_examination.getString("src"));
                menzhenshujuList.add(tgjc);
            }
            //专科检查
            Document special_examination = (Document) menzhenshuju.get("special_examination");
            if (special_examination != null) {
                Map<String, String> zkjc = new LinkedHashMap<>();
                zkjc.put("key", "专科检查");
                zkjc.put("value", special_examination.getString("src"));
                menzhenshujuList.add(zkjc);
            }


            //专科查体
            Document auxiliary_examination = (Document) menzhenshuju.get("auxiliary_examination");
            if (ObjectUtils.anyNotNull(auxiliary_examination)) {
                Map<String, String> fzjc = new LinkedHashMap<>();
                fzjc.put("key", "辅助检查");
                fzjc.put("value", auxiliary_examination.getString("src"));
                menzhenshujuList.add(fzjc);
            }
        }
        return menzhenshujuList;
    }

}

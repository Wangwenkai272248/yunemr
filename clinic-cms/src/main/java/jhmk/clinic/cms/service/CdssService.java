package jhmk.clinic.cms.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import jhmk.clinic.cms.SamilarService;
import jhmk.clinic.cms.controller.ruleService.BasyService;
import jhmk.clinic.cms.controller.ruleService.SjyscflService;
import jhmk.clinic.core.config.CdssConstans;
import jhmk.clinic.core.util.CompareUtil;
import jhmk.clinic.core.util.DateFormatUtil;
import jhmk.clinic.entity.bean.Shangjiyishichafanglu;
import jhmk.clinic.entity.cdss.CdssDiffBean;
import jhmk.clinic.entity.cdss.CdssRuleBean;
import jhmk.clinic.entity.cdss.StatisticsBean;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static jhmk.clinic.cms.service.InitService.liiNames;
import static jhmk.clinic.core.util.MongoUtils.getCollection;

/**
 * @author ziyu.zhou
 * @date 2018/7/9 10:51
 */


@Service
public class CdssService {
    @Autowired
    SjyscflService sjyscflService;
    @Autowired
    BasyService basyService;
    @Autowired
    SamilarService samilarService;

    MongoCollection<Document> binganshouye = getCollection(CdssConstans.DATASOURCE, CdssConstans.BINGANSHOUYE);
    //入院记录
    static MongoCollection<Document> ruyuanjilu = getCollection(CdssConstans.DATASOURCE, CdssConstans.RUYUANJILU);
    static MongoCollection<Document> yizhu = getCollection(CdssConstans.DATASOURCE, CdssConstans.YIZHU);
    //病理诊断 初诊
    MongoCollection<Document> binglizhenduan = getCollection(CdssConstans.DATASOURCE, CdssConstans.BINGLIZHENDUAN);
    MongoCollection<Document> shouyezhenduan = getCollection(CdssConstans.DATASOURCE, CdssConstans.SHOUYEZHENDUAN);
    MongoCollection<Document> jianchabaogao = getCollection(CdssConstans.DATASOURCE, CdssConstans.JCBG);
    MongoCollection<Document> jianyanbaogao = getCollection(CdssConstans.DATASOURCE, CdssConstans.JYBG);


    //查询病案首页
    public CdssRuleBean selBasy(CdssRuleBean cdssRuleBean) {
        List<Document> countPatientId = Arrays.asList(

                new Document("$match", new Document("_id", cdssRuleBean.getId())),
                new Document("$project", new Document("patient_id", 1).append("_id", 1).append("visit_id", 1).append("binganshouye", 1))
        );
        AggregateIterable<Document> output = binganshouye.aggregate(countPatientId);
        Map<String, String> map = new HashMap<>();

        for (Document document : output) {
            if (document == null) {
                continue;
            }
            Document binganshouye = (Document) document.get("binganshouye");
            Document patInfo = (Document) binganshouye.get("pat_info");
            Document patVisit = (Document) binganshouye.get("pat_visit");
            //病案首页
            map.put("pat_info_sex_name", (String) patInfo.get("sex_name"));
            map.put("pat_info_age_value", (String) patVisit.get("age_value"));
            map.put("pat_info_age_value_unit", (String) patVisit.get("age_value_unit"));
            map.put("pat_info_marital_status_name", (String) patVisit.get("marital_status_name"));
            map.put("pat_visit_dept_discharge_from_name", (String) patVisit.get("dept_discharge_from_name"));

            map.put("pat_visit_dept_admission_to_name", (String) patVisit.get("dept_admission_to_name"));
            map.put("pat_visit_dept_admission_to_code", patVisit.getString("dept_admission_to_code"));
            cdssRuleBean.setBinganshouye(map);
            //医生id
            cdssRuleBean.setId(document.getString("_id"));
            cdssRuleBean.setDept_code(patVisit.getString("dept_admission_to_name"));
            cdssRuleBean.setDoctor_name(patVisit.getString("attending_doctor_name"));
            break;
        }
        return cdssRuleBean;
    }

    public String getRycz(String id) {
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

    //查询病案首页
    public Map selBasy(String _id) {
        List<Document> countPatientId = Arrays.asList(

                new Document("$match", new Document("_id", _id)),
                new Document("$project", new Document("patient_id", 1).append("_id", 1).append("visit_id", 1).append("binganshouye", 1))
        );
        AggregateIterable<Document> output = binganshouye.aggregate(countPatientId);
        Map<String, String> map = new HashMap<>();

        for (Document document : output) {
            if (document == null) {
                continue;
            }
            Document binganshouye = (Document) document.get("binganshouye");
            Document patInfo = (Document) binganshouye.get("pat_info");
            Document patVisit = (Document) binganshouye.get("pat_visit");
            //病案首页
            if (StringUtils.isEmpty(patVisit.getString("dept_admission_to_code"))) {
                return null;
            }
            map.put("pat_visit_dept_admission_to_code", patVisit.getString("dept_admission_to_code"));
            map.put("pat_info_sex_name", (String) patInfo.get("sex_name"));
            map.put("pat_info_age_value", (String) patVisit.get("age_value"));
            map.put("pat_info_age_value_unit", (String) patVisit.get("age_value_unit"));
            map.put("pat_info_marital_status_name", (String) patVisit.get("marital_status_name"));
            map.put("pat_visit_dept_discharge_from_name", patVisit.getString("dept_discharge_from_name"));
            map.put("pat_visit_dept_admission_to_name", patVisit.getString("dept_admission_to_name"));
            break;
        }
        return map;
    }

    //获取病理诊断
    public List<Map<String, String>> selbinglizhenduan(String id) {
        List<Map<String, String>> list = new ArrayList<>();

        List<Document> countPatientId = Arrays.asList(
                new Document("$unwind", "$binglizhenduan"),
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("_id", 1).append("patient_id", 1).append("visit_id", 1).append("binglizhenduan", 1))
        );
        AggregateIterable<Document> binli = binglizhenduan.aggregate(countPatientId);
        for (Document document : binli) {
            Map<String, String> map = new HashMap<>();
            Document binglizhenduan = (Document) document.get("binglizhenduan");
            String diagnosis_num = binglizhenduan.getString("diagnosis_num");
            String diagnosis_name = binglizhenduan.getString("diagnosis_name");
            if (StringUtils.isEmpty(diagnosis_name)) {
                diagnosis_name = binglizhenduan.getString("diagnosis_desc");
            }
            map.put("diagnosis_name", diagnosis_name);
            map.put("diagnosis_time", binglizhenduan.getString("diagnosis_time"));
            map.put("diagnosis_num", diagnosis_num);
            map.put("diagnosis_type_name", binglizhenduan.getString("diagnosis_type_name"));

            map.put("diagnosis_sub_num", binglizhenduan.getString("diagnosis_sub_num"));


            list.add(map);
        }
        return list;
    }

    public List<Map<String, String>> getShouYeZhenDuan(String id) {
        List<Map<String, String>> list = new ArrayList<>();

        List<Document> countPatientId = Arrays.asList(
                new Document("$unwind", "$shouyezhenduan"),
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("_id", 1).append("patient_id", 1).append("visit_id", 1).append("shouyezhenduan", 1))
        );
        AggregateIterable<Document> binli = shouyezhenduan.aggregate(countPatientId);
        for (Document document : binli) {
            Map<String, String> map = new HashMap<>();
            Document binglizhenduan = (Document) document.get("shouyezhenduan");
            String diagnosis_num = binglizhenduan.getString("diagnosis_num");
            String diagnosis_name = binglizhenduan.getString("diagnosis_name");
            if (StringUtils.isEmpty(diagnosis_name)) {
                diagnosis_name = binglizhenduan.getString("diagnosis_desc");
            }
            map.put("diagnosis_name", diagnosis_name);
            map.put("diagnosis_time", binglizhenduan.getString("diagnosis_time"));
            map.put("diagnosis_num", diagnosis_num);
            map.put("diagnosis_type_name", binglizhenduan.getString("diagnosis_type_name"));
            map.put("diagnosis_sub_num", binglizhenduan.getString("diagnosis_sub_num"));
            list.add(map);
        }
        return list;
    }


    //类似于3目运算符
    public static String flagObj(Object str) {
        return (String) Optional.ofNullable(str)
                .orElse("");
    }


    private static String getLevelStr(int level) {
        StringBuffer levelStr = new StringBuffer();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }


    /**
     * 查询所有_id
     *
     * @return
     */
    public List<String> getAllIds() {
        List<String> idList = new LinkedList<>();
        List<Document> output = Arrays.asList(
                new Document("$project", new Document("_id", 1))
        );
        AggregateIterable<Document> aggregate = ruyuanjilu.aggregate(output);
        for (Document document : aggregate) {
            String id = document.get("_id").toString();
            idList.add(id);
        }
        return idList;
    }

    public List<String> getAllIdsByAddmissionTime() {
        List<String> idList = new LinkedList<>();
        List<Document> output = Arrays.asList(
                new Document("$project", new Document("_id", 1))
        );
        AggregateIterable<Document> aggregate = binganshouye.aggregate(output);
        for (Document document : aggregate) {
            String id = document.get("_id").toString();
            idList.add(id);
        }
        return idList;
    }

    //获取出院主疾病在疾病列表中的id
    public List<CdssRuleBean> getAllIdsByIllName() {

        List<CdssRuleBean> beanList = new LinkedList<>();
        List<Document> countPatientId = Arrays.asList(
                new Document("$unwind", "$shouyezhenduan"),
                new Document("$project", new Document("_id", 1).append("patient_id", 1).append("visit_id", 1).append("shouyezhenduan", 1))
                , new Document("$skip", CdssConstans.BEGINCOUNT),
                new Document("$limit", CdssConstans.ENDCOUNT)
//new Document("$limit", 500)
        );
        AggregateIterable<Document> binli = shouyezhenduan.aggregate(countPatientId);
        for (Document document : binli) {
            Document shoueyezhenduan = (Document) document.get("shouyezhenduan");
            if ("1".equals(shoueyezhenduan.getString("diagnosis_num")) && "3".equals(shoueyezhenduan.getString("diagnosis_type_code"))) {
                CdssRuleBean cdssRuleBean = new CdssRuleBean();
                String diagnosis_name = shoueyezhenduan.getString("diagnosis_name");
                if (liiNames.contains(diagnosis_name)) {
                    cdssRuleBean.setMainIllName(diagnosis_name);
                    cdssRuleBean.setId(document.getString("_id"));
                    beanList.add(cdssRuleBean);
                }

            }

        }
        return beanList;
    }

    public List<CdssRuleBean> getAllIdsByIllName(List<String> idList) {

        List<CdssRuleBean> beanList = new LinkedList<>();
        for (String id : idList) {
            String[] split = id.split(",");
            String pid = split[0];
            String vid = split[1];
            List<Document> countPatientId = Arrays.asList(
                    new Document("$unwind", "$shouyezhenduan"),
                    new Document("$match", new Document("patient_id", pid)),
                    new Document("$match", new Document("visit_id", vid)),
                    new Document("$project", new Document("_id", 1).append("patient_id", 1).append("visit_id", 1).append("shouyezhenduan", 1))
            );
            AggregateIterable<Document> binli = shouyezhenduan.aggregate(countPatientId);
            for (Document document : binli) {
                Document shoueyezhenduan = (Document) document.get("shouyezhenduan");
                if ("1".equals(shoueyezhenduan.getString("diagnosis_num")) && "3".equals(shoueyezhenduan.getString("diagnosis_type_code"))) {
                    CdssRuleBean cdssRuleBean = new CdssRuleBean();
                    String diagnosis_name = shoueyezhenduan.getString("diagnosis_name");
                    cdssRuleBean.setMainIllName(diagnosis_name);
                    cdssRuleBean.setId(document.getString("_id"));
                    beanList.add(cdssRuleBean);
                }

            }
        }
        return beanList;
    }

    public String getCyzdByPidAndVid(String id) {
        String name = "";
        String[] split = id.split(",");
        String pid = split[0];
        String vid = split[1];
        List<Document> countPatientId = Arrays.asList(
                new Document("$unwind", "$shouyezhenduan"),
                new Document("$match", new Document("shouyezhenduan.diagnosis_num", "1")),
                new Document("$match", new Document("shouyezhenduan.diagnosis_type_name", "出院诊断")),
                new Document("$match", new Document("patient_id", pid)),
                new Document("$match", new Document("visit_id", vid)),
                new Document("$project", new Document("_id", 1).append("patient_id", 1).append("visit_id", 1).append("shouyezhenduan", 1))
        );
        AggregateIterable<Document> binli = shouyezhenduan.aggregate(countPatientId);
        for (Document document : binli) {
            Document shoueyezhenduan = (Document) document.get("shouyezhenduan");
            if (Objects.nonNull(shoueyezhenduan)) {
                name = shoueyezhenduan.getString("diagnosis_name");
            }

        }
        return name;
    }


    //根据id查询入院记录
    public CdssRuleBean selruyuanjiluById(String id) throws NullPointerException {

        CdssRuleBean selbinglizhenduan = new CdssRuleBean();
        List<Map<String, String>> ruyuanjiluList = new ArrayList<>();
        List<Document> countPatientId2 = Arrays.asList(
//过滤数据
                new Document("$match", new Document("_id", id)),
//new Document("$unwind","$diagnosis_name"),
                new Document("$project", new Document("patient_id", 1).append("visit_id", 1).append("ruyuanjilu", 1)
                )

        );
        AggregateIterable<Document> output = ruyuanjilu.aggregate(countPatientId2);
        for (Document document : output) {
            selbinglizhenduan.setPatient_id(document.get("patient_id").toString());
            selbinglizhenduan.setVisit_id(document.get("visit_id").toString());
            selbinglizhenduan.setId(document.get("_id").toString());
            Document ruyuanjilu = (Document) document.get("ruyuanjilu");
            if (ruyuanjilu == null) {
                return selbinglizhenduan;
            }

            List<Document> diagnosisName = (List) ruyuanjilu.get("diagnosis_name");
            if (diagnosisName != null) {
                for (Document d : diagnosisName) {
                    if (d.get("diagnosis_num") != null && d.get("diagnosis_num").toString().equals("1")) {
                        selbinglizhenduan.setQuezhen(d.get("diagnosis_name").toString());
                        break;
                    }
                }
            }

            if (Objects.nonNull(ruyuanjilu.get("chief_complaint"))) {
                Document chief_complaint = (Document) ruyuanjilu.get("chief_complaint");
                if (chief_complaint != null) {
                    Map<String, String> mapzs = new LinkedHashMap<>();
                    mapzs.put("key", "主诉");
                    mapzs.put("value", flagObj(chief_complaint.get("src")));
                    //主诉
                    ruyuanjiluList.add(mapzs);
                }
            }
            //现病史
            Document history_of_present_illness = (Document) ruyuanjilu.get("history_of_present_illness");
            if (history_of_present_illness != null) {

                Map<String, String> mapxbs = new LinkedHashMap<>();
                mapxbs.put("key", "现病史");
                mapxbs.put("value", flagObj(history_of_present_illness.get("src")));
                ruyuanjiluList.add(mapxbs);
            }
            //既往史
            Document history_of_past_illness = (Document) ruyuanjilu.get("history_of_past_illness");
            if (history_of_past_illness != null) {
                Map<String, String> jws = new LinkedHashMap<>();
                jws.put("key", "既往史");
                jws.put("value", flagObj(history_of_past_illness.get("src")));
                ruyuanjiluList.add(jws);
            }
            //个人史

            Document social_history = (Document) ruyuanjilu.get("social_history");
            if (social_history != null) {

                Map<String, String> grs = new LinkedHashMap<>();
                grs.put("key", "个人史");
                grs.put("value", flagObj(social_history.get("src")));
                ruyuanjiluList.add(grs);
            }

            //婚姻史
            Document menstrual_and_obstetrical_histories = (Document) ruyuanjilu.get("menstrual_and_obstetrical_histories");
            if (menstrual_and_obstetrical_histories != null) {

                Map<String, String> hys = new LinkedHashMap<>();
                hys.put("key", "婚姻史");
                hys.put("value", flagObj(menstrual_and_obstetrical_histories.get("src")));
                ruyuanjiluList.add(hys);
            }

            //家族史
            Document history_of_family_member_diseases = (Document) ruyuanjilu.get("history_of_family_member_diseases");
            if (history_of_family_member_diseases != null) {
                Map<String, String> jzs = new LinkedHashMap<>();
                jzs.put("key", "家族史");
                jzs.put("value", flagObj(history_of_family_member_diseases.get("src")));
                ruyuanjiluList.add(jzs);
            }
            //辅助检查
            Document physical_examination = (Document) ruyuanjilu.get("physical_examination");
            if (physical_examination != null) {
                Map<String, String> tgjc = new LinkedHashMap<>();
                tgjc.put("key", "体格检查");
                tgjc.put("value", flagObj(physical_examination.get("src")));
                ruyuanjiluList.add(tgjc);
            }
            //专科检查
            Document special_examination = (Document) ruyuanjilu.get("special_examination");
            if (special_examination != null) {
                Map<String, String> zkjc = new LinkedHashMap<>();
                zkjc.put("key", "专科检查");
                zkjc.put("value", flagObj(special_examination.get("src")));
                ruyuanjiluList.add(zkjc);
            }


            //专科查体
            Document auxiliary_examination = (Document) ruyuanjilu.get("auxiliary_examination");
            if (ObjectUtils.anyNotNull(auxiliary_examination)) {
                Map<String, String> fzjc = new LinkedHashMap<>();
                fzjc.put("key", "辅助检查");
                fzjc.put("value", flagObj(auxiliary_examination.get("src")));
                ruyuanjiluList.add(fzjc);
            }
            selbinglizhenduan.setRuyuanjilu(ruyuanjiluList);
        }
        return selbinglizhenduan;
    }


    /**
     * 获取入院记录
     *
     * @param id
     * @return
     * @throws NullPointerException
     */


    /**
     * 获取首页诊断
     *
     * @param id
     * @return
     */

    public List<Map<String, String>> selSyzd(String id) {
        List<Map<String, String>> list = new ArrayList<>();

        List<Document> countPatientId = Arrays.asList(
                new Document("$unwind", "$shouyezhenduan"),
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("_id", 1).append("patient_id", 1).append("visit_id", 1).append("shouyezhenduan", 1))
        );
        AggregateIterable<Document> binli = shouyezhenduan.aggregate(countPatientId);
        for (Document document : binli) {
            Map<String, String> shouyezhenduanMap = new HashMap<>();
            Document shoueyezhenduan = (Document) document.get("shouyezhenduan");

            String diagnosis_name = shoueyezhenduan.getString("diagnosis_name");
            if (StringUtils.isNotBlank(diagnosis_name)) {
                diagnosis_name = shoueyezhenduan.getString("diagnosis_desc");
            }
            shouyezhenduanMap.put("diagnosis_name", diagnosis_name);
            shouyezhenduanMap.put("diagnosis_time", shoueyezhenduan.getString("diagnosis_time"));
            shouyezhenduanMap.put("diagnosis_num", shoueyezhenduan.getString("diagnosis_num"));
            shouyezhenduanMap.put("diagnosis_type_name", shoueyezhenduan.getString("diagnosis_type_name"));
            shouyezhenduanMap.put("diagnosis_type_code", shoueyezhenduan.getString("diagnosis_type_code"));
            list.add(shouyezhenduanMap);
        }
        return list;
    }

    /**
     * 获取检验报告
     *
     * @param id
     * @return
     */
    public List<Map<String, String>> selJybgList(String id) {
        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("_id", 1).append("patient_id", 1).append("visit_id", 1).append("jianyanbaogao", 1))
        );
        AggregateIterable<Document> aggregate = jianyanbaogao.aggregate(countPatientId);
        MongoCursor<Document> iterator = aggregate.iterator();
        List<Map<String, String>> jianyan = new LinkedList<>();
        while (iterator.hasNext()) {
            Document next = iterator.next();
            Document jianyanbaogao = next.get("jianyanbaogao", Document.class);
            if (jianyanbaogao == null) {
                continue;
            }
            if (jianyanbaogao.get("lab_report") != null) {
                List<Document> lab_report = jianyanbaogao.get("lab_report", List.class);
                for (Document d : lab_report) {
                    Map<String, String> map = new HashMap<>();
                    // 检验项目名
                    map.put("lab_item_name", flagObj(d.get("lab_item_name")));
                    //检验子项名
                    map.put("lab_sub_item_name", flagObj(d.get("lab_sub_item_name")));
                    //检验子项值
                    map.put("lab_result_value", flagObj(d.get("lab_result_value")));
                    map.put("lab_result_value_unit", flagObj(d.get("lab_result_value_unit")));
                    jianyan.add(map);
                }
            }
        }
        return jianyan;
    }

    /**
     * 检验报告
     *
     * @param id
     * @return
     */
    public List<Map<String, String>> selJybg(String id) {
        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("_id", 1).append("patient_id", 1).append("visit_id", 1).append("jianyanbaogao", 1))
        );
        AggregateIterable<Document> aggregate = jianyanbaogao.aggregate(countPatientId);
        MongoCursor<Document> iterator = aggregate.iterator();
        List<Map<String, String>> jianyan = new LinkedList<>();
        while (iterator.hasNext()) {
            Document next = iterator.next();
            Document jianyanbaogao = next.get("jianyanbaogao", Document.class);

            if (jianyanbaogao.get("lab_report") != null) {
                List<Document> lab_report = jianyanbaogao.get("lab_report", List.class);
                for (Document d : lab_report) {
                    Map<String, String> map = new HashMap<>();
                    // 检验项目名
                    map.put("lab_item_name", flagObj(d.get("lab_item_name")));
                    //检验子项名
                    map.put("lab_sub_item_name", flagObj(d.get("lab_sub_item_name")));
                    //检验子项值
                    map.put("lab_result_value", flagObj(d.get("lab_result_value")));
                    map.put("lab_result_value_unit", flagObj(d.get("lab_result_value_unit")));
                    map.put("report_time", flagObj(d.get("report_time")));
                    jianyan.add(map);
                }
            }
        }
        return jianyan;

    }

    public List<Map<String, List<Map<String, String>>>> getJianYan(String id) {

        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("_id", 1).append("patient_id", 1).append("visit_id", 1).append("jianyanbaogao", 1))
        );
        AggregateIterable<Document> aggregate = jianyanbaogao.aggregate(countPatientId);
        MongoCursor<Document> iterator = aggregate.iterator();
        List<Map<String, List<Map<String, String>>>> jianyan = new LinkedList<>();
        Map<String, List<Map<String, String>>> map = new HashMap<>();
        while (iterator.hasNext()) {
            Document next = iterator.next();
            Document jianyanbaogao = next.get("jianyanbaogao", Document.class);
            if (jianyanbaogao == null) {
                continue;
            }

            if (jianyanbaogao.get("lab_report") != null) {
                List<Document> lab_report = jianyanbaogao.get("lab_report", List.class);
                for (Document d : lab_report) {
                    // 检验项目名
                    Map<String, String> cMap = new HashMap<>();
                    cMap.put("name", d.getString("lab_sub_item_name"));
                    cMap.put("lab_result", flagObj(d.get("lab_result_value")));
                    cMap.put("report_time", d.getString("report_time"));
                    if (map.containsKey(d.getString("lab_item_name"))) {
                        List<Map<String, String>> lab_item_name = map.get(d.getString("lab_item_name"));
                        lab_item_name.add(cMap);
                        map.put(d.getString("lab_item_name"), lab_item_name);
                    } else {
                        List<Map<String, String>> clist = new ArrayList<>();
                        clist.add(cMap);
                        map.put(d.getString("lab_item_name"), clist);
                    }
                }
                jianyan.add(map);

            }
        }
        return jianyan;

    }


    //检查报告
    public List<Map<String, String>> selJcbgList(String id) {

        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("_id", 1).append("patient_id", 1).append("visit_id", 1).append("jianchabaogao", 1))
        );
        AggregateIterable<Document> aggregate = jianchabaogao.aggregate(countPatientId);
        MongoCursor<Document> iterator = aggregate.iterator();
        List<Map<String, String>> jiancha = new LinkedList<>();
        while (iterator.hasNext()) {
            Document next = iterator.next();
            Document jianchabaogao = next.get("jianchabaogao", Document.class);
            if (Objects.nonNull(jianchabaogao)) {
                if (jianchabaogao.get("exam_report") != null) {
                    Object lab_report1 = jianchabaogao.get("exam_report");

                    List<Document> lab_report = jianchabaogao.get("exam_report", List.class);
                    for (Document d : lab_report) {
                        Map<String, String> map = new HashMap<>();
// 检查项目名
                        map.put("exam_item_name", flagObj(d.get("exam_item_name")));
//检查类别名
                        map.put("exam_class_name", flagObj(d.get("exam_class_name")));
//检查所见
                        map.put("exam_feature", flagObj(d.get("exam_feature")));
                        map.put("exam_diag", flagObj(d.get("exam_diag")));
                        map.put("exam_time", flagObj(d.get("exam_time")));
//    map.put("exam_feature_quantization", flagObj(d.get("exam_feature_quantization")));
//    map.put("exam_diag_quantization", flagObj(d.get("exam_diag_quantization")));
//map.put("检验子项值单位",d.get("").toString());
                        jiancha.add(map);
                    }
                }
            }
        }

        return jiancha;
    }


    public List<Map<String, String>> selYizhu(String id) {
        List<Document> countPatientId2 = Arrays.asList(
//过滤数据
                new Document("$match", new Document("_id", id)),
                new Document("$unwind", "$yizhu"),
                new Document("$project", new Document("patient_id", 1).append("visit_id", 1).append("yizhu", 1)
                )

        );
        AggregateIterable<Document> output = yizhu.aggregate(countPatientId2);
        List<Map<String, String>> orderList = new LinkedList<>();
        for (Document document : output) {
            Document yizhuDocu = (Document) document.get("yizhu");
            if (yizhuDocu == null) {
                continue;
            }
            Map<String, String> orderMap = new HashMap<>();
            if (yizhuDocu.get("order_item_name") != null) {
                String order_item_name = yizhuDocu.getString("order_item_name");
                orderMap.put("order_item_name", order_item_name);
                String order_begin_time = yizhuDocu.getString("order_begin_time");
                orderMap.put("order_begin_time", order_begin_time);

                String order_end_time = yizhuDocu.getString("order_end_time");
                orderMap.put("order_end_time", order_end_time);

                String frequency_name = yizhuDocu.getString("frequency_name");
                orderMap.put("frequency_name", frequency_name);

                String order_properties_name = yizhuDocu.getString("order_properties_name");
                orderMap.put("order_properties_name", order_properties_name);

                orderList.add(orderMap);
            }

        }
        return orderList;
    }

    public String anaRule(String map) {
        Map<String, String> paramMap = (Map) JSON.parse(map);
        Map<String, Object> endparamMap = new HashMap<String, Object>();
        endparamMap.putAll(paramMap);
        for (String key : paramMap.keySet()) {
            if (key.equals("ruyuanjilu")) {
                String ryjl = String.valueOf(paramMap.get("ruyuanjilu"));
                JSONArray jsonArray = JSON.parseArray(ryjl);
                Iterator<Object> it = jsonArray.iterator();
                Map<String, String> ryjlMap = new HashMap<String, String>();
                while (it.hasNext()) {
                    JSONObject ob = (JSONObject) it.next();
                    String ryjlkey = ob.getString("key");
                    String value = ob.getString("value");
                    if (value != null && !value.isEmpty()) {
                        if (ryjlkey.equals("既往史")) {
                            ryjlMap.put("history_of_past_illness", value);
                        } else if (ryjlkey.equals("主诉")) {
                            ryjlMap.put("chief_complaint", value);
                        } else if (ryjlkey.equals("现病史")) {
                            ryjlMap.put("history_of_present_illness", value);
                        } else if (ryjlkey.equals("家族史")) {
                            ryjlMap.put("history_of_family_member_diseases", value);
                        } else if (ryjlkey.equals("婚姻史")) {
                            ryjlMap.put("menstrual_and_obstetrical_histories", value);
                        } else if (ryjlkey.equals("辅助检查")) {
                            ryjlMap.put("auxiliary_examination", value);
                        } else if (ryjlkey.equals("体格检查")) {
                            ryjlMap.put("physical_examination", value);
                        }
                    }
                }
                endparamMap.put("ruyuanjilu", ryjlMap);
            } else if (key.equals("jianyanbaogao")) {
                String jybg = String.valueOf(paramMap.get("jianyanbaogao"));
                JSONArray jsonArray = JSON.parseArray(jybg);
                Iterator<Object> it = jsonArray.iterator();
                List<Map<String, String>> jybgMap = new ArrayList<Map<String, String>>();
                while (it.hasNext()) {
                    Map<String, String> jcbg = new HashMap<String, String>();
                    JSONObject ob = (JSONObject) it.next();
                    if (ob.containsKey("labTestItems")) {
                        Object labTestItems = ob.get("labTestItems");
                        JSONArray sbjsonArray = JSON.parseArray(labTestItems.toString());
                        for (Object object : sbjsonArray) {
                            JSONObject sbobj = (JSONObject) object;
                            if (sbobj.getString("name") != null) {
                                jcbg.put("lab_sub_item_name", sbobj.getString("name"));
                            }
                            if (sbobj.getString("unit") != null) {
                                jcbg.put("lab_result_value_unit", sbobj.getString("unit"));
                            }
                            if (sbobj.getString("lab_result_value") != null) {
                                jcbg.put("lab_result_value", sbobj.getString("lab_result_value"));
                            }
                            if (sbobj.getString("result_status_code") != null) {
                                jcbg.put("result_status_code", sbobj.getString("result_status_code"));
                            }
                        }
                    }
                    jybgMap.add(jcbg);

                }
                endparamMap.put(key, jybgMap);
            }
        }
        return JSONObject.toJSONString(endparamMap);
    }

    public String getJsonStr(String deptName, String start, String end, int page, int size) {
        String json = "{\"expressions\": [[{  \"field\": \"病案首页_就诊信息_就诊科室\",  \"exp\": \"=\",     \"flag\": \"or\",   \"unit\": \"\",     \"values\": [\"" + deptName + "\"]       }], [{  \"field\": \"病案首页_就诊信息_就诊时间\",  \"exp\": \">=\",    \"flag\": \"or\",   \"unit\": \"\",     \"values\": [\"" + start + "\"]}], [{  \"field\": \"病案首页_就诊信息_就诊时间\",  \"exp\": \"<=\",    \"flag\": \"or\",   \"unit\": \"\",     \"values\": [\"" + end + "\"]}]  ],  \"page\": " + page + ",  \"size\": " + size + ",   \"result\": [ [{  \"field\": \"病案首页_就诊信息_就诊时间\",  \"exp\": \"等于\",    \"values\": [],   \"flag\": \"0\",    \"unit\": \"\"      }], [{  \"field\": \"住院首页诊断_诊断名称,住院首页诊断_诊断类型=入院初诊,住院首页诊断_诊断序号=1\",  \"exp\": \"等于\",    \"values\": [],   \"flag\": \"0\",    \"unit\": \"\"      }], [{  \"field\": \"住院转科记录_转科时间\",     \"exp\": \"等于\",    \"values\": [],   \"flag\": \"0\",    \"unit\": \"\"      }], [{  \"field\": \"住院首页诊断_诊断名称,住院首页诊断_诊断类型=出院诊断,住院首页诊断_诊断序号=1\",  \"exp\": \"等于\",    \"values\": [],     \"flag\": \"0\",    \"unit\": \"\"      }], [{  \"field\": \"住院上级医师查房录_上级医师查房示_是否明确诊断\",    \"exp\": \"等于\",    \"values\": [],    \"flag\": \"0\",    \"unit\": \"\"      }, {    \"field\": \"住院上级医师查房录_上级医师查房示_明确诊断名称\",    \"exp\": \"等于\",    \"values\": [],   \"flag\": \"0\",    \"unit\": \"\"      }, {    \"field\": \"住院上级医师查房录_文书最终提交时间\",  \"exp\": \"等于\",    \"values\": [],   \"flag\": \"0\",    \"unit\": \"\"      },{  \"field\": \"住院上级医师查房录_上级医师查房示_章节src\",    \"exp\": \"等于\",    \"values\": [],    \"flag\": \"0\",    \"unit\": \"\"      },{  \"field\": \"住院上级医师查房录_上级医师查房示_疾病名称\",    \"exp\": \"等于\",    \"values\": [],    \"flag\": \"0\",    \"unit\": \"\"      }]  ] }";
//        String json = "{\"expressions\": [[{  \"field\": \"病案首页_就诊信息_就诊科室\",  \"exp\": \"=\",     \"flag\": \"or\",   \"unit\": \"\",     \"values\": [\"" + deptName + "\"]       }], [{  \"field\": \"病案首页_就诊信息_就诊时间\",  \"exp\": \">=\",    \"flag\": \"or\",   \"unit\": \"\",     \"values\": [\"" + start + "\"]}], [{  \"field\": \"病案首页_就诊信息_就诊时间\",  \"exp\": \"<=\",    \"flag\": \"or\",   \"unit\": \"\",     \"values\": [\"" + end + "\"]}]  ],  \"page\": 0,  \"size\": 3000,   \"result\": [ [{  \"field\": \"病案首页_就诊信息_就诊时间\",  \"exp\": \"等于\",    \"values\": [],   \"flag\": \"0\",    \"unit\": \"\"      }], [{  \"field\": \"住院首页诊断_诊断名称,住院首页诊断_诊断类型=入院初诊,住院首页诊断_诊断序号=1\",  \"exp\": \"等于\",    \"values\": [],   \"flag\": \"0\",    \"unit\": \"\"      }], [{  \"field\": \"住院转科记录_转科时间\",     \"exp\": \"等于\",    \"values\": [],   \"flag\": \"0\",    \"unit\": \"\"      }], [{  \"field\": \"住院首页诊断_诊断名称,住院首页诊断_诊断类型=出院诊断,住院首页诊断_诊断序号=1\",  \"exp\": \"等于\",    \"values\": [\"入院初诊\", \"出院诊断\"],     \"flag\": \"0\",    \"unit\": \"\"      }], [{    \"field\": \"住院上级医师查房录_上级医师查房示_明确诊断名称\",    \"exp\": \"等于\",    \"values\": [],   \"flag\": \"0\",    \"unit\": \"\"      }, {    \"field\": \"住院上级医师查房录_文书最终提交时间\",  \"exp\": \"等于\",    \"values\": [],   \"flag\": \"0\",    \"unit\": \"\"      }]  ] }";
        return json;
    }

    String s = "{\n" +
            "\t\"expressions\": [\n" +
            "\t\t[{\n" +
            "\t\t\t\"field\": \"病案首页_就诊信息_就诊科室\",\n" +
            "\t\t\t\"exp\": \"=\",\n" +
            "\t\t\t\"flag\": \"or\",\n" +
            "\t\t\t\"unit\": \"\",\n" +
            "\t\t\t\"values\": [\"\" + deptName + \"\"]\n" +
            "\t\t}],\n" +
            "\t\t[{\n" +
            "\t\t\t\"field\": \"病案首页_就诊信息_就诊时间\",\n" +
            "\t\t\t\"exp\": \">=\",\n" +
            "\t\t\t\"flag\": \"or\",\n" +
            "\t\t\t\"unit\": \"\",\n" +
            "\t\t\t\"values\": [\"\" + start + \"\"]\n" +
            "\t\t}],\n" +
            "\t\t[{\n" +
            "\t\t\t\"field\": \"病案首页_就诊信息_就诊时间\",\n" +
            "\t\t\t\"exp\": \"<=\",\n" +
            "\t\t\t\"flag\": \"or\",\n" +
            "\t\t\t\"unit\": \"\",\n" +
            "\t\t\t\"values\": [\"\" + end + \"\"]\n" +
            "\t\t}]\n" +
            "\t],\n" +
            "\t\"page\": 0,\n" +
            "\t\"size\": 3000,\n" +
            "\t\"result\": [\n" +
            "\t\t[{\n" +
            "\t\t\t\"field\": \"病案首页_就诊信息_就诊时间\",\n" +
            "\t\t\t\"exp\": \"等于\",\n" +
            "\t\t\t\"values\": [],\n" +
            "\t\t\t\"flag\": \"0\",\n" +
            "\t\t\t\"unit\": \"\"\n" +
            "\t\t}],\n" +
            "\t\t[{\n" +
            "\t\t\t\"field\": \"住院首页诊断_诊断名称,住院首页诊断_诊断类型=入院初诊,住院首页诊断_诊断序号=1\",\n" +
            "\t\t\t\"exp\": \"等于\",\n" +
            "\t\t\t\"values\": [],\n" +
            "\t\t\t\"flag\": \"0\",\n" +
            "\t\t\t\"unit\": \"\"\n" +
            "\t\t}],\n" +
            "\t\t[{\n" +
            "\t\t\t\"field\": \"住院转科记录_转科时间\",\n" +
            "\t\t\t\"exp\": \"等于\",\n" +
            "\t\t\t\"values\": [],\n" +
            "\t\t\t\"flag\": \"0\",\n" +
            "\t\t\t\"unit\": \"\"\n" +
            "\t\t}],\n" +
            "\t\t[{\n" +
            "\t\t\t\"field\": \"住院首页诊断_诊断名称,住院首页诊断_诊断类型=出院诊断,住院首页诊断_诊断序号=1\",\n" +
            "\t\t\t\"exp\": \"等于\",\n" +
            "\t\t\t\"values\": [\"入院初诊\", \"出院诊断\"],\n" +
            "\t\t\t\"flag\": \"0\",\n" +
            "\t\t\t\"unit\": \"\"\n" +
            "\t\t}],\n" +
            "\t\t[{\n" +
            "\t\t\t\"field\": \"住院上级医师查房录_上级医师查房示_是否明确诊断\",\n" +
            "\t\t\t\"exp\": \"等于\",\n" +
            "\t\t\t\"values\": [],\n" +
            "\t\t\t\"flag\": \"0\",\n" +
            "\t\t\t\"unit\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"field\": \"住院上级医师查房录_上级医师查房示_明确诊断名称\",\n" +
            "\t\t\t\"exp\": \"等于\",\n" +
            "\t\t\t\"values\": [],\n" +
            "\t\t\t\"flag\": \"0\",\n" +
            "\t\t\t\"unit\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"field\": \"住院上级医师查房录_文书最终提交时间\",\n" +
            "\t\t\t\"exp\": \"等于\",\n" +
            "\t\t\t\"values\": [],\n" +
            "\t\t\t\"flag\": \"0\",\n" +
            "\t\t\t\"unit\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"field\": \"住院上级医师查房录_上级医师查房示_章节src\",\n" +
            "\t\t\t\"exp\": \"等于\",\n" +
            "\t\t\t\"values\": [],\n" +
            "\t\t\t\"flag\": \"0\",\n" +
            "\t\t\t\"unit\": \"\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"field\": \"住院上级医师查房录_上级医师查房示_疾病名称\",\n" +
            "\t\t\t\"exp\": \"等于\",\n" +
            "\t\t\t\"values\": [],\n" +
            "\t\t\t\"flag\": \"0\",\n" +
            "\t\t\t\"unit\": \"\"\n" +
            "\t\t}]\n" +
            "\t]\n" +
            "}";

    public String getJsonStr1(String[] ids) {
        StringBuilder sb = new StringBuilder();
        for (String id : ids) {
            sb.append("\"").append(id).append("\"").append(",");
        }
        String substring = sb.substring(0, sb.length() - 1);
        String json = "{\n" +
                "\t\"expressions\": [\n" +
                "\t\t[{\n" +
                "\t\t\t\"field\": \"fieldId\",\n" +
                "\t\t\t\"exp\": \"=\",\n" +
                "\t\t\t\"flag\": \"or\",\n" +
                "\t\t\t\"unit\": \"\",\n" +
                "\t\t\t\"values\": [" + substring + "]\n" +
                "\t\t}]\n" +
                "\t],\n" +
                "\t\"page\": 0,\n" +
                "\t\"size\": 3000,\n" +
                "\t\"result\": [\n" +
                "\t\t[{\n" +
                "\t\t\t\"field\": \"病案首页_就诊信息_就诊时间\",\n" +
                "\t\t\t\"exp\": \"等于\",\n" +
                "\t\t\t\"values\": [],\n" +
                "\t\t\t\"flag\": \"0\",\n" +
                "\t\t\t\"unit\": \"\"\n" +
                "\t\t}],\n" +
                "\t\t[{\n" +
                "\t\t\t\"field\": \"住院首页诊断_诊断名称,住院首页诊断_诊断类型=入院初诊,住院首页诊断_诊断序号=1\",\n" +
                "\t\t\t\"exp\": \"等于\",\n" +
                "\t\t\t\"values\": [],\n" +
                "\t\t\t\"flag\": \"0\",\n" +
                "\t\t\t\"unit\": \"\"\n" +
                "\t\t}],\n" +
                "\t\t[{\n" +
                "\t\t\t\"field\": \"住院转科记录_转科时间\",\n" +
                "\t\t\t\"exp\": \"等于\",\n" +
                "\t\t\t\"values\": [],\n" +
                "\t\t\t\"flag\": \"0\",\n" +
                "\t\t\t\"unit\": \"\"\n" +
                "\t\t}],\n" +
                "\t\t[{\n" +
                "\t\t\t\"field\": \"住院首页诊断_诊断名称,住院首页诊断_诊断类型=出院诊断,住院首页诊断_诊断序号=1\",\n" +
                "\t\t\t\"exp\": \"等于\",\n" +
                "\t\t\t\"values\": [\"入院初诊\", \"出院诊断\"],\n" +
                "\t\t\t\"flag\": \"0\",\n" +
                "\t\t\t\"unit\": \"\"\n" +
                "\t\t}],\n" +
                "\t\t[{\n" +
                "\t\t\t\"field\": \"住院上级医师查房录_上级医师查房示_是否明确诊断\",\n" +
                "\t\t\t\"exp\": \"等于\",\n" +
                "\t\t\t\"values\": [],\n" +
                "\t\t\t\"flag\": \"0\",\n" +
                "\t\t\t\"unit\": \"\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"field\": \"住院上级医师查房录_上级医师查房示_明确诊断名称\",\n" +
                "\t\t\t\"exp\": \"等于\",\n" +
                "\t\t\t\"values\": [],\n" +
                "\t\t\t\"flag\": \"0\",\n" +
                "\t\t\t\"unit\": \"\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"field\": \"住院上级医师查房录_文书最终提交时间\",\n" +
                "\t\t\t\"exp\": \"等于\",\n" +
                "\t\t\t\"values\": [],\n" +
                "\t\t\t\"flag\": \"0\",\n" +
                "\t\t\t\"unit\": \"\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"field\": \"住院上级医师查房录_上级医师查房示_章节src\",\n" +
                "\t\t\t\"exp\": \"等于\",\n" +
                "\t\t\t\"values\": [],\n" +
                "\t\t\t\"flag\": \"0\",\n" +
                "\t\t\t\"unit\": \"\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"field\": \"住院上级医师查房录_上级医师查房示_疾病名称\",\n" +
                "\t\t\t\"exp\": \"等于\",\n" +
                "\t\t\t\"values\": [],\n" +
                "\t\t\t\"flag\": \"0\",\n" +
                "\t\t\t\"unit\": \"\"\n" +
                "\t\t}]\n" +
                "\t]\n" +
                "}";

        return json;
    }

    public List<CdssDiffBean> getDiffBeanList(String data) {
        List<CdssDiffBean> resultList = new ArrayList<>();
        if (StringUtils.isNotBlank(data)) {
            JSONObject jsonObject = JSONObject.parseObject(data);

            Object result = jsonObject.get("result");
            if (Objects.nonNull(result)) {
                JSONArray jsonArray = (JSONArray) result;
                Iterator<Object> iterator = jsonArray.iterator();
                while (iterator.hasNext()) {
                    JSONObject next = (JSONObject) iterator.next();
                    Set<String> strings = next.keySet();
                    for (String keyname : strings) {
                        String temId = keyname.replaceAll("#2#", "");
                        String admissionTime = basyService.getAdmissionTime(temId);
                        String dischargeTime = basyService.getDischargeTime(temId);
                        String inpNo = basyService.getInpNo(temId);
                        String string = next.getString(keyname);
                        JSONArray array = JSONArray.parseArray(string);
                        CdssDiffBean cdssDiffBean = getCdssDiffBean(array);
                        List<Shangjiyishichafanglu> sjyscflBean = sjyscflService.getSJYSCFLBean(temId);
                        if (sjyscflBean.size() == 0) {
                            continue;
                        }
                        cdssDiffBean.setShangjiyishichafangluList(sjyscflBean);
                        cdssDiffBean.setId(keyname);
                        cdssDiffBean.setAdmission_time(admissionTime);
                        cdssDiffBean.setInp_no(inpNo);
                        cdssDiffBean.setDischarge_time(dischargeTime);
                        resultList.add(cdssDiffBean);
                    }

                }
            }
        }
        return resultList;
    }

    /**
     * 筛选数据 入=出 专科=false  上级医师查房病=出  是否诊断=是
     *
     * @param oldList
     * @return
     */
    public List<CdssDiffBean> getDiffBeanList(List<CdssDiffBean> oldList) {
        List<CdssDiffBean> resultList = new ArrayList<>();
        for (CdssDiffBean bean : oldList) {
            String chuyuanzhenduan = bean.getChuyuanzhenduan();
            //出诊断=null
            if (StringUtils.isEmpty(chuyuanzhenduan)) {
                continue;
            }
            boolean isFas = samilarService.isFatherAndSon(chuyuanzhenduan, bean.getRuyuanchuzhen());
            //如果入院初诊=出院诊断 过滤
            if (isFas) {
                continue;
            }
            //有专科记录 过滤
            if (bean.isZhuanke() == true) {
                continue;
            }
            if (bean.getShangjiyishichafangluList() == null) {
                continue;
            }
//            List<String> illNames = samilarService.getSamilarWord(chuyuanzhenduan);
            List<Shangjiyishichafanglu> shangjiyishichafangluList = bean.getShangjiyishichafangluList();

            Collections.sort(shangjiyishichafangluList, new Comparator<Shangjiyishichafanglu>() {
                @Override
                public int compare(Shangjiyishichafanglu o1, Shangjiyishichafanglu o2) {
                    String d1 = o1.getLast_modify_date_time();
                    String d2 = o2.getLast_modify_date_time();
                    Date date1 = DateFormatUtil.parseDateBySdf(d1, DateFormatUtil.DATETIME_PATTERN_SS);
                    Date date2 = DateFormatUtil.parseDateBySdf(d2, DateFormatUtil.DATETIME_PATTERN_SS);
                    if (date1.getTime() > date2.getTime()) {
                        return 1;
                    } else {
                        return -1;
                    }

                }
            });
            //跳出循环
            lable1:
            for (Shangjiyishichafanglu shangjiyishichafanglu : shangjiyishichafangluList) {
                String clear_diagnose_name = shangjiyishichafanglu.getClear_diagnose_name();
                String last_modify_date_time = shangjiyishichafanglu.getLast_modify_date_time();
                String[] split = clear_diagnose_name.split(" ");
                for (String s : split) {
                    boolean isFas1 = samilarService.isFatherAndSon(s, chuyuanzhenduan);
                    if (isFas1) {
                        bean.setSjyscfTime(last_modify_date_time);
                        bean.setSjyscfName(clear_diagnose_name);
                        bean.setFlag(true);
                        resultList.add(bean);
                        break lable1;
                    }

                }

            }


        }
        return resultList;
    }

    public List<CdssDiffBean> getAllDiffBeanList(List<CdssDiffBean> oldList) {
        List<CdssDiffBean> resultList = new ArrayList<>();
        for (CdssDiffBean bean : oldList) {
            String chuyuanzhenduan = bean.getChuyuanzhenduan();
            String ruyuanchuzhen = bean.getRuyuanchuzhen();
            //出诊断=null
            if (StringUtils.isEmpty(chuyuanzhenduan) || StringUtils.isEmpty(ruyuanchuzhen)) {
                bean.setFlag(false);
                resultList.add(bean);
                continue;
            }
            //如果入院初诊=出院诊断 过滤

            //有专科记录 过滤
            if (bean.isZhuanke() == true) {
                bean.setFlag(false);
                resultList.add(bean);
                continue;
            }
            if (bean.getShangjiyishichafangluList() == null) {
                bean.setFlag(false);
                resultList.add(bean);
                continue;
            }
            if (chuyuanzhenduan.contains(bean.getRuyuanchuzhen()) || bean.getRuyuanchuzhen().contains(chuyuanzhenduan)) {
                bean.setFlag(false);
                resultList.add(bean);
                continue;
            }
            List<Shangjiyishichafanglu> shangjiyishichafangluList = bean.getShangjiyishichafangluList();

            Collections.sort(shangjiyishichafangluList, CompareUtil.createComparator(1, "last_modify_date_time"));
            //跳出循环
            boolean flag = false;
            lable1:
            for (Shangjiyishichafanglu shangjiyishichafanglu : shangjiyishichafangluList) {
                String clear_diagnose_name = shangjiyishichafanglu.getClear_diagnose_name();
                if (StringUtils.isNotBlank(clear_diagnose_name)) {

                    String last_modify_date_time = shangjiyishichafanglu.getLast_modify_date_time();
                    String[] split = clear_diagnose_name.split(" ");
                    for (String s : split) {
                        if (s.contains(chuyuanzhenduan) || chuyuanzhenduan.contains(s)) {
                            bean.setSjyscfTime(last_modify_date_time);
                            bean.setSjyscfName(clear_diagnose_name);
                            bean.setFlag(true);
                            resultList.add(bean);
                            flag = true;
                            break lable1;
                        }

                    }
                }

            }
            if (!flag) {
                bean.setFlag(false);
                resultList.add(bean);
                continue;
            }


        }
        return resultList;
    }

    /**
     * 获取软院诊断不等于出院诊断 且上级医师查房录 确诊等于是的
     *
     * @param oldList
     * @return
     */
    public List<CdssDiffBean> getDiffBean(List<CdssDiffBean> oldList) {
        List<CdssDiffBean> resultList = new ArrayList<>();
        for (CdssDiffBean bean : oldList) {
            String chuyuanzhenduan = bean.getChuyuanzhenduan();
            String ruyuanchuzhen = bean.getRuyuanchuzhen();
            //出诊断=null
            if (StringUtils.isEmpty(chuyuanzhenduan) || StringUtils.isEmpty(ruyuanchuzhen)) {
                continue;
            }
            //如果入院初诊=出院诊断 过滤

            //有专科记录 过滤
            if (bean.isZhuanke() == true) {
                continue;
            }
            if (bean.getShangjiyishichafangluList() == null) {
                continue;
            }
            if (chuyuanzhenduan.contains(bean.getRuyuanchuzhen()) || bean.getRuyuanchuzhen().contains(chuyuanzhenduan)) {
                continue;
            }
            List<Shangjiyishichafanglu> shangjiyishichafangluList = bean.getShangjiyishichafangluList();

            Collections.sort(shangjiyishichafangluList, CompareUtil.createComparator(1, "last_modify_date_time"));
            //跳出循环
            boolean flag = false;
            lable1:
            for (Shangjiyishichafanglu shangjiyishichafanglu : shangjiyishichafangluList) {
                String clear_diagnose_name = shangjiyishichafanglu.getClear_diagnose_name();
                if (StringUtils.isNotBlank(clear_diagnose_name)) {

                    String last_modify_date_time = shangjiyishichafanglu.getLast_modify_date_time();
                    String[] split = clear_diagnose_name.split(" ");
                    for (String s : split) {
                        if (s.contains(chuyuanzhenduan) || chuyuanzhenduan.contains(s)) {
                            bean.setSjyscfTime(last_modify_date_time);
                            bean.setSjyscfName(clear_diagnose_name);
                            bean.setFlag(true);
                            resultList.add(bean);
                            flag = true;
                            break lable1;
                        }

                    }

                }
            }

            if (!flag) {
                continue;
            }


        }
        return resultList;
    }

    public Map<String, StatisticsBean> analyzeData(List<CdssDiffBean> list) {
        Map<String, StatisticsBean> staMap = new HashMap<>();
        for (CdssDiffBean cdssDiffBean : list) {
            String chuyuanzhenduan = cdssDiffBean.getChuyuanzhenduan();
            String addmissionTime = cdssDiffBean.getAdmission_time();
            String resultValue = cdssDiffBean.getSjyscfTime();
            int l = (int) DateFormatUtil.dateDiff(DateFormatUtil.parseDateBySdf(resultValue, DateFormatUtil.DATETIME_PATTERN_SS), DateFormatUtil.parseDateBySdf(addmissionTime, DateFormatUtil.DATETIME_PATTERN_SS));
            if (staMap.containsKey(chuyuanzhenduan)) {
                StatisticsBean statisticsBean = staMap.get(chuyuanzhenduan);
                statisticsBean.setIllName(chuyuanzhenduan);
                statisticsBean.setCount(statisticsBean.getCount() + 1);
                statisticsBean.setDay(statisticsBean.getDay() + l);
                staMap.put(chuyuanzhenduan, statisticsBean);
            } else {
                StatisticsBean statisticsBean = new StatisticsBean();
                statisticsBean.setIllName(chuyuanzhenduan);
                statisticsBean.setCount(1);
                statisticsBean.setDay(l);
                staMap.put(chuyuanzhenduan, statisticsBean);
            }

        }
        return staMap;
    }

    /**
     * 统计分析数据  确诊时间 每天出现的次数
     *
     * @param list
     * @return
     */
    public Map<Integer, Integer> analyzeData2DayHaveCount(List<CdssDiffBean> list) {
        Map<Integer, Integer> staMap = new HashMap<>();
        for (CdssDiffBean cdssDiffBean : list) {
            String addmissionTime = cdssDiffBean.getAdmission_time();
            String resultValue = cdssDiffBean.getSjyscfTime();
            int l = (int) DateFormatUtil.dateDiff(DateFormatUtil.parseDateBySdf(resultValue, DateFormatUtil.DATETIME_PATTERN_SS), DateFormatUtil.parseDateBySdf(addmissionTime, DateFormatUtil.DATETIME_PATTERN_SS));
            if (staMap.containsKey(l)) {
                staMap.put(l, staMap.get(l) + 1);
            } else {
                staMap.put(l, 1);
            }

        }
        return staMap;
    }

    /**
     * 统计分析数据
     * key 次数 value 平均时长
     *
     * @param list
     * @return
     */
    public Map<Integer, Integer> analyzeData2CountAndAvgDay(List<CdssDiffBean> list) {
        if (list.size() == 0) {
            return null;
        }
        Map<Integer, Integer> staMap = new HashMap<>();
        int l = 0;
        for (CdssDiffBean cdssDiffBean : list) {
            String addmissionTime = cdssDiffBean.getAdmission_time();
            String resultValue = cdssDiffBean.getSjyscfTime();
            l += (int) DateFormatUtil.dateDiff(DateFormatUtil.parseDateBySdf(resultValue, DateFormatUtil.DATETIME_PATTERN_SS), DateFormatUtil.parseDateBySdf(addmissionTime, DateFormatUtil.DATETIME_PATTERN_SS));
        }
        int avgDay = l / list.size();//平均时长
        staMap.put(list.size(), avgDay);
        return staMap;
    }

    public CdssDiffBean getCdssDiffBean(JSONArray array) {
        Iterator<Object> iterator = array.iterator();
        CdssDiffBean bean1 = new CdssDiffBean();
        List<Shangjiyishichafanglu> sjysList = new ArrayList<>();
        while (iterator.hasNext()) {
            Map<String, JSONArray> next = (Map) iterator.next();
            JSONArray array1 = next.get("病案首页_就诊信息_就诊时间");
            String value = getValue(array1);
            if (StringUtils.isNotBlank(value)) {
                bean1.setJz_time(value);
                continue;
            }
            JSONArray rycz = next.get("住院首页诊断_诊断名称,住院首页诊断_诊断类型=入院初诊,住院首页诊断_诊断序号=1");
            String ryczvalue = getValue(rycz);
            if (StringUtils.isNotBlank(ryczvalue)) {
                bean1.setRuyuanchuzhen(ryczvalue);
                continue;
            }
            JSONArray syzd = next.get("住院首页诊断_诊断名称,住院首页诊断_诊断类型=出院诊断,住院首页诊断_诊断序号=1");
            String syzdValue = getValue(syzd);
            if (StringUtils.isNotBlank(syzdValue)) {
                bean1.setChuyuanzhenduan(syzdValue);
                continue;
            }
            JSONArray zksj = next.get("住院转科记录_转科时间");
            String zksjValue = getValue(zksj);

            if (StringUtils.isNotBlank(zksjValue)) {
//明确时间
                bean1.setZhuanke(true);
            } else {
                bean1.setZhuanke(false);

            }
            JSONArray sjzdmc = next.get("住院上级医师查房录_上级医师查房示_明确诊断名称");
            JSONArray sjzdmq = next.get("住院上级医师查房录_上级医师查房示_是否明确诊断");
            JSONArray sjtjsj = next.get("住院上级医师查房录_文书最终提交时间");
            JSONArray jbmc = next.get("住院上级医师查房录_上级医师查房示_疾病名称");
            JSONArray zjsrc = next.get("住院上级医师查房录_上级医师查房示_章节src");
            if (sjzdmc != null && sjtjsj != null) {
                Shangjiyishichafanglu bean = new Shangjiyishichafanglu();
                String sjzdmcValue = getValue(sjzdmc);
                if (StringUtils.isNotBlank(sjzdmcValue)) {
                    //明确诊断名称
                    bean.setClear_diagnose_name(sjzdmcValue);
                }

                String jbmcValue = getValue(jbmc);
                if (StringUtils.isNotBlank(jbmcValue)) {
                    bean.setJbmc(jbmcValue);
                }
                String zjsrcValue = getValue(zjsrc);
                if (StringUtils.isNotBlank(zjsrcValue)) {
                    bean.setZjsrc(zjsrcValue);
                }
                String sjzdmqValue = getValue(sjzdmq);
                if (StringUtils.isNotBlank(sjzdmqValue)) {
                    bean.setClear_diagnose(sjzdmqValue);
                }

                String sjtjsjValue = getValue(sjtjsj);

                if (StringUtils.isNotBlank(sjtjsjValue)) {
                    //明确时间
                    bean.setLast_modify_date_time(sjtjsjValue);
                }
                if (StringUtils.isNotBlank(sjtjsjValue) && StringUtils.isNotBlank(sjzdmcValue)) {
                    sjysList.add(bean);
                }

            }
            bean1.setShangjiyishichafangluList(sjysList);
        }
        return bean1;
    }

    public String getValue(JSONArray array) {
        String value = null;
        if (array != null) {
            value = array.getString(0);
            if (value.equals("null")) {
                return "";
            }
        }
        return value;
    }

    public List<CdssDiffBean> getRyeqCy(List<CdssDiffBean> diffBeanList) {
        List<CdssDiffBean> resultList = new ArrayList<>();
        for (CdssDiffBean bean : diffBeanList) {
            String chuyuanzhenduan = bean.getChuyuanzhenduan();
            String ruyuanchuzhen = bean.getRuyuanchuzhen();
            if (StringUtils.isEmpty(chuyuanzhenduan)) {
                continue;
            }
            if (chuyuanzhenduan.contains(ruyuanchuzhen) || ruyuanchuzhen.contains(chuyuanzhenduan)) {
                bean.setFlag(true);
                resultList.add(bean);
            }
        }
        return resultList;
    }

    /**
     * 获取优质病例
     *
     * @param diffBeanList
     * @param inpNoList
     * @return
     */
    public List<CdssDiffBean> getGoodData(List<CdssDiffBean> diffBeanList, List<String> inpNoList) {
        List<CdssDiffBean> resultList = new ArrayList<>();
        for (CdssDiffBean bean : diffBeanList) {
            if (inpNoList.contains(bean.getInp_no())) {
                resultList.add(bean);
            }
        }
        return resultList;
    }
}



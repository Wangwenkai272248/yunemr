package jhmk.clinic.cms.controller.ruleService;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import jhmk.clinic.cms.SamilarService;
import jhmk.clinic.cms.entity.JsonRootBean;
import jhmk.clinic.cms.entity.ResultService;
import jhmk.clinic.core.config.CdssConstans;
import jhmk.clinic.core.util.CompareUtil;
import jhmk.clinic.entity.bean.Shouyezhenduan;
import jhmk.clinic.entity.pojo.DiagnosisReqLog;
import jhmk.clinic.entity.pojo.DiagnosisRespLog;
import jhmk.clinic.entity.pojo.repository.service.DiagnosisReqLogService;
import jhmk.clinic.entity.pojo.repository.service.DiagnosisRespLogService;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static jhmk.clinic.core.util.MongoUtils.getCollection;

/**
 * @author ziyu.zhou
 * @date 2018/7/31 14:19
 */
//首页诊断

@Service
public class SyzdService {

    MongoCollection<Document> shouyezhenduan = getCollection(CdssConstans.DATASOURCE, CdssConstans.SHOUYEZHENDUAN);
    @Autowired
    SamilarService samilarService;
    @Autowired
    DiagnosisReqLogService diagnosisReqLogService;
    @Autowired
    DiagnosisRespLogService diagnosisRespLogService;

    /**
     * 获取首页诊断疾病集合
     *
     * @param id
     * @return
     */
    public List<String> getDiseaseList(String id) {
        List<String> list = new ArrayList<>();

        List<Document> countPatientId = Arrays.asList(
                new Document("$unwind", "$shouyezhenduan"),
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("_id", 1).append("patient_id", 1).append("visit_id", 1).append("shouyezhenduan", 1))
        );
        AggregateIterable<Document> binli = shouyezhenduan.aggregate(countPatientId);
        for (Document document : binli) {
            Document binglizhenduan = (Document) document.get("shouyezhenduan");
            String diagnosis_name = binglizhenduan.getString("diagnosis_name");
            list.add(diagnosis_name);
        }
        return list;
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

    /**
     * 获取入院初诊
     *
     * @param id
     * @return
     */
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

    public String getRyczByPidAndVid(String id) {
        String[] split = id.split(",");
        String pid = split[0];
        String vid = split[1];
        List<Document> countPatientId = Arrays.asList(
                new Document("$unwind", "$shouyezhenduan"),
                new Document("$match", new Document("patient_id", pid)),
                new Document("$match", new Document("visit_id", vid)),
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
    public String getCyzd(String id) {
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
    /**
     * 获取所有出院诊断个数
     *
     * @return
     */
    public Map<String, Integer> getAllData() {
        List<String> strList = new ArrayList<>();
        Map<String, Integer> resultMap = new HashMap<>();

        List<Document> countPatientId = Arrays.asList(
                new Document("$unwind", "$shouyezhenduan"),
                new Document("$match", new Document("shouyezhenduan.diagnosis_num", "1")),
                new Document("$match", new Document("shouyezhenduan.diagnosis_type_name", "出院诊断")),
                new Document("$project", new Document("_id", 1).append("patient_id", 1).append("visit_id", 1).append("shouyezhenduan", 1))
        );
        AggregateIterable<Document> binli = shouyezhenduan.aggregate(countPatientId);
        for (Document document : binli) {
            String id = document.getString("_id");
            Document binglizhenduan = (Document) document.get("shouyezhenduan");
            if (Objects.nonNull(binglizhenduan)) {
                String diagnosis_name = binglizhenduan.getString("diagnosis_name");
                if (StringUtils.isEmpty(diagnosis_name)) {
                    strList.add(id);
                }
                if (resultMap.containsKey(diagnosis_name)) {
                    resultMap.put(diagnosis_name, resultMap.get(diagnosis_name) + 1);
                } else {
                    resultMap.put(diagnosis_name, 1);
                }
            }
        }

        return resultMap;
    }


    public List<Shouyezhenduan> getShoueyezhenduanBean(String id) {
        List<Shouyezhenduan> list = new ArrayList<>();

        List<Document> countPatientId = Arrays.asList(
                new Document("$unwind", "$shouyezhenduan"),
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("_id", 1).append("patient_id", 1).append("visit_id", 1).append("shouyezhenduan", 1))
        );
        AggregateIterable<Document> binli = shouyezhenduan.aggregate(countPatientId);
        for (Document document : binli) {
            Shouyezhenduan shouyezhenduan = new Shouyezhenduan();
            Map<String, String> map = new HashMap<>();
            Document binglizhenduan = (Document) document.get("shouyezhenduan");
            String diagnosis_num = binglizhenduan.getString("diagnosis_num");
            String diagnosis_name = binglizhenduan.getString("diagnosis_name");
            String diagnosis_desc = binglizhenduan.getString("diagnosis_desc");
            if (StringUtils.isNotBlank(diagnosis_name)) {
                diagnosis_name = binglizhenduan.getString("diagnosis_desc");
            }
            shouyezhenduan.setDiagnosis_desc(diagnosis_desc);
            shouyezhenduan.setDiagnosis_name(diagnosis_name);
            shouyezhenduan.setDiagnosis_num(diagnosis_num);
            shouyezhenduan.setDiagnosis_type_name(binglizhenduan.getString("diagnosis_type_name"));
            shouyezhenduan.setDiagnosis_time(binglizhenduan.getString("diagnosis_time"));

            list.add(shouyezhenduan);
        }
        Collections.sort(list, new CompareUtil.ImComparator(1, "diagnosis_time"));
        return list;
    }

    //获取出院诊断为目标疾病名的数据
    public Set<String> getSyzdByDiseaseName(String name) {
        Set<String> list = new HashSet<>();
        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("shouyezhenduan.diagnosis_num", "1")),
                new Document("$match", new Document("shouyezhenduan.diagnosis_name", name)),
                new Document("$match", new Document("shouyezhenduan.diagnosis_type_name", "出院诊断")),
                new Document("$project", new Document("_id", 1).append("patient_id", 1).append("visit_id", 1).append("shouyezhenduan", 1))
                , new Document("$skip", CdssConstans.BEGINCOUNT),
                new Document("$limit", 100)
        );
        AggregateIterable<Document> binli = shouyezhenduan.aggregate(countPatientId);
        for (Document document : binli) {
            final String id = document.getString("_id");
            ArrayList binglizhenduanList = (ArrayList) document.get("shouyezhenduan");
            final Iterator iterator = binglizhenduanList.iterator();
            while (iterator.hasNext()) {
                final Document binglizhenduan = (Document) iterator.next();
                String diagnosis_num = binglizhenduan.getString("diagnosis_num");
                String diagnosis_name = binglizhenduan.getString("diagnosis_name");
                String diagnosis_type_name = binglizhenduan.getString("diagnosis_type_name");
                if (name.equals(diagnosis_name) && "1".equals(diagnosis_num) && "入院初诊".equals(diagnosis_type_name)) {
                    list.add(id);
                }
            }
        }
        return list;
    }


    //获取出院诊断为目标疾病名的数据
    public Set<String> getIdList(String name) {
        Set<String> list = new HashSet<>();
        List<Document> countPatientId = Arrays.asList(
                new Document("$unwind", "$shouyezhenduan"),
                new Document("$match", new Document("shouyezhenduan.diagnosis_type_name", "出院诊断")),
                new Document("$match", new Document("shouyezhenduan.diagnosis_num", "1")),
                new Document("$project", new Document("_id", 1).append("patient_id", 1).append("visit_id", 1).append("shouyezhenduan", 1))
        );
        AggregateIterable<Document> binli = shouyezhenduan.aggregate(countPatientId);
        for (Document document : binli) {
            String id = document.getString("_id");
            Document binglizhenduan = (Document) document.get("shouyezhenduan");
            if (Objects.nonNull(binglizhenduan)) {
                String diagnosis_name = binglizhenduan.getString("diagnosis_name");
                final String query = samilarService.getQuery(diagnosis_name);
                final JsonRootBean ryczNameBean = JSONObject.parseObject(query, JsonRootBean.class);
                final String grandFa = ResultService.getGrandFa(ryczNameBean.getResult());
                if (grandFa != null && grandFa.equals(name)) {
                    list.add(id);
                }
            }
        }
        return list;

    }

    @Test
    public void main() {
        getSyzdByDiseaseName("肺炎");
    }

    /**
     * 根据出院主诊断查询病例个数
     */
    /*public Map<String,Integer> getListByDiseaseName(List<String> diseaseCodeList,List<String> diseaseNameList,Map<String,Integer> disseaseMap) {
        List<Document> countPatientId = Arrays.asList(
                new Document("$unwind", "$shouyezhenduan"),
                new Document("$match", new Document("shouyezhenduan.diagnosis_type_name", "出院诊断")),
                new Document("$match", new Document("shouyezhenduan.diagnosis_num", "1")),
//                new Document("$match", new Document("shouyezhenduan.diagnosis_name", diseaseName)),
//                new Document("$match", new Document("shouyezhenduan.diagnosis_code", diseaseCode)),
                new Document("$project", new Document("_id", 1).append("patient_id", 1).append("visit_id", 1).append("shouyezhenduan", 1))
        );
        AggregateIterable<Document> binli = shouyezhenduan.aggregate(countPatientId);

        for (Document document : binli) {
            String id = document.getString("_id");
            Document binglizhenduan = (Document) document.get("shouyezhenduan");
            if (Objects.nonNull(binglizhenduan)) {
                String diagnosis_code = binglizhenduan.getString("diagnosis_code");
                String diagnosis_name = binglizhenduan.getString("diagnosis_name");
                for(int m=0;m<diseaseCodeList.size();m++){
                    if(!org.springframework.util.StringUtils.isEmpty(diagnosis_code)){
                        if(diagnosis_code.equals(diseaseCodeList.get(m))){
                            for(Map.Entry map : disseaseMap.entrySet()){
                                String code = (String) map.getKey();
                                if(diseaseCodeList.get(m).equals(code)){
                                    map.setValue((int)map.getValue()+1);
                                }
                            }
                        }
                    }else if(!org.springframework.util.StringUtils.isEmpty(diagnosis_name)){
                        if(diagnosis_name.equals(diseaseNameList.get(m))) {
                            for (Map.Entry map : disseaseMap.entrySet()) {
                                String code = (String) map.getKey();
                                if (diseaseCodeList.get(m).equals(code)) {
                                    map.setValue((int) map.getValue() + 1);
                                }
                            }
                        }
                    }
                }
            }
         }
        return disseaseMap;
    }*/
    /**
     * 根据出院主诊断统计次数
     */
    public List<List<Object>> getTotalNum(){
        List<Document> countPatientId = Arrays.asList(
                new Document("$unwind", "$shouyezhenduan"),
                new Document("$match", new Document("shouyezhenduan.diagnosis_type_name", "出院诊断")),
                new Document("$match", new Document("shouyezhenduan.diagnosis_num", "1")),
                new Document("$group", new Document("_id", "$shouyezhenduan.diagnosis_name").append("total",new Document("$sum",1)))

//                new Document("$project", new Document("_id", 1).append("patient_id", 1).append("visit_id", 1).append("shouyezhenduan", 1))
        );
        AggregateIterable<Document> binli = shouyezhenduan.aggregate(countPatientId);
        List<List<Object>> listObject = getLists(binli);
        return listObject;
    }

    /**
     *功能描述
     *@author swq
     *@date 2019-1-24  18:59
     *@param: binli
     *@return  java.util.List<java.util.List<java.lang.Object>>
     *@desc  遍历从mongo中获取的数据，转为List<List<Object>>格式
     */
    private List<List<Object>> getLists(AggregateIterable<Document> binli) {
        List<List<Object>> listObject = new ArrayList<>();
        for (Document document : binli) {
            List<Object> list = new ArrayList<>();
            String id = document.getString("_id");
            Integer total = document.getInteger("total");
            list.add(id);
            list.add(total);
            listObject.add(list);
        }
        return listObject;
    }

    /**
     *功能描述
     *@author swq
     *@date 2019-1-24  17:43
     *@param: null
     *@return
     *@desc 根据病历ID查询测试库是否包含该病历
     * @param listObject
     */
    public List<List<Object>> isExistInLib(List<List<Object>> listObject){
        /*List<Document> countPatientId = Arrays.asList(
                new Document("$unwind", "$shouyezhenduan"),
                new Document("$project", new Document("_id", 1))
        );
        AggregateIterable<Document> binli = shouyezhenduan.aggregate(countPatientId);
        //格式化查询结果
        List<List<Object>> listArray = new ArrayList<>();
        for(List<Object> str : listObject){
            String ele = (String) str.get(0);
            List<Object> list = new ArrayList<>();
            boolean flag = false;
            for (Document document : binli) {
                String id = document.getString("_id");
                if (id.equals(ele)) {
                    flag = true;
                    list.add(id);
                    list.add(1);
                }
            }
            if(!flag){
                list.add(ele);
                list.add(0);
            }
            listArray.add(list);
        }
        return listArray;
    }*/
        List<List<Object>> listArray = new ArrayList<>();
        for(List<Object> str : listObject){
            String ele = (String) str.get(0);
            List<Document> countPatientId = Arrays.asList(
                    new Document("$unwind", "$shouyezhenduan"),
                    new Document("$match", new Document("_id", ele)),
                    new Document("$project", new Document("_id", 1))
            );
            AggregateIterable<Document> binli = shouyezhenduan.aggregate(countPatientId);
            List<Object> list = new ArrayList<>();
            if(binli.iterator().hasNext()){
                list.add(ele);
                list.add(1);
            }else{
                list.add(ele);
                list.add(0);
            }
            listArray.add(list);
        }
        return listArray;
    }

    public List<List<Object>> exportLog(){
        List<List<Object>> listObject = new ArrayList<>();
        List<DiagnosisReqLog> reqList = (List<DiagnosisReqLog>) diagnosisReqLogService.findAll();
        List<DiagnosisRespLog> respList = (List<DiagnosisRespLog>) diagnosisRespLogService.findAll();
        for(DiagnosisReqLog reqLog : reqList ){
            List<Object> listArray = new ArrayList<>();
            int id = reqLog.getId();
            String deptCode = reqLog.getDeptCode();
            String diagnosisName = reqLog.getDiagnosisName();
            String diagnosisTime = reqLog.getDiagnosisTime();
            String doctorId = reqLog.getDoctorId();
            String doctorName = reqLog.getDoctorName();
            String inTime = reqLog.getInTime();
            String pageSource = reqLog.getPageSource();
            String pid = reqLog.getPid();
            String vid = reqLog.getVid();
            listArray.add(id);
            listArray.add(deptCode);
            listArray.add(diagnosisName);
            listArray.add(diagnosisTime);
            listArray.add(doctorId);
            listArray.add(doctorName);
            listArray.add(inTime);
            listArray.add(pageSource);
            listArray.add(pid);
            listArray.add(vid);
            boolean flag = false;
            String diseaseInfo = "";
            for(DiagnosisRespLog respLog : respList ){
                if(id == respLog.getReqId()){
                    flag = true;
                    diseaseInfo = diseaseInfo+respLog.getDiseaseName()+" : "+respLog.getScore();
                }
            }
            if(flag){
                listArray.add(1);
                listArray.add(diseaseInfo);
            }else{
                listArray.add(0);
                listArray.add("");
            }
            listObject.add(listArray);
        }
        return listObject;
    }

    /**
     *功能描述
     *@author swq
     *@date 2019-1-29  10:39
     *@param: null
     *@return
     *@desc 为TRUE，不管有没有问号都不删除。其余名字带问号整条删除
     */
    public List<List<Object>> updateName(List<List<Object>> listObject){
        String[] strs1 = {"别嘌呤醇", "五氟利多", "甲苯磺丁脲（D-860）", "罗红霉素片剂", "盐酸氟西泮", "阿维A", "普伐他丁", "氯化筒箭毒碱", "硫酸依替米星注射剂", "异丙嗪", "泮库溴胺", "萘夫西林", "达利全片（卡维地洛）", "西拉普利", "紫杉醇", "乙硫异烟胺", "利伐沙班", "利奈唑胺", "乙胺嘧啶", "左西孟旦", "苯海索", "舍曲林", "特非那定", "盐酸度洛西汀", "替利定", "核黄素磷酸钠", "氯美噻唑", "头孢西丁", "头孢替安", "培哚普利", "环索奈德", "苯噻啶", "吡格列酮", "美托拉宗", "金刚烷胺", "盐酸氯普鲁卡因", "地拉韦啶", "阿莫地喹", "美洛昔康", "柳氮磺吡啶", "雷米普利", "罗氟司特", "甲氯芬酯", "头孢呋辛酯", "阿利吉仑", "枸橼酸西地那非", "苯溴马隆", "维生素B2", "维生素B1", "维生素B6", "拉帕替尼", "硝普钠", "盐酸甲哌卡因", "戊巴比妥钠", "帕吉林", "三氟拉嗪", "苯妥英", "甲磺酸瑞波西汀", "依维莫司", "替考拉宁", "卡马西平", "盐酸齐拉西酮", "氢氯噻嗪", "氯雷他定泡腾片片", "替加氟", "非布司他", "盐酸索他洛尔", "盐酸哌罗匹隆", "氯诺昔康", "盐酸普鲁卡因胺", "索他洛尔", "沙利度胺", "舒必利", "拉西地平", "替尼泊苷", "马拉韦罗", "氢氯噻嗪", "盐酸阿莫地喹", "头孢布烯", "甲硫氧嘧啶", "氨苄西林", "羟吗啡酮", "普伐他汀", "氨苯蝶啶", "马来酸左旋氨氯地平", "呋喃妥因", "炔诺酮", "奈法唑酮", "阿昔莫司", "氟西泮", "磷酸萘酚喹", "氢化可的松琥珀酸钠", "胍乙啶", "考来替兰", "盐酸舍曲林", "米氮平", "美司钠", "尼可地尔", "胆茶碱", "白细胞介素-2", "马来酸左旋氨氯地平片散片", "妥布霉素", "头孢克肟颗粒", "阿司眯唑", "阿西美辛", "胺碘酮等", "香豆素", "羟氯喹", "氟西汀", "氨磺必利", "利福昔明", "普罗布考", "地氟烷", "去甲肾上腺素", "头孢丙烯", "格拉司琼", "右美托咪定", "特比萘芬", "厄洛替尼", "阿普林定", "单硝酸异山梨酯", "苦参素", "盐酸达克罗宁", "阿折地平", "巴利昔单抗", "薄荷脑", "吗替麦考酚酯干混悬剂", "孟鲁司特钠", "丙硫氧嘧啶", "螺旋霉素", "利多卡因气雾剂", "司维拉姆", "匹莫林", "烟酰胺", "硫酸阿托品", "佐匹克隆", "盐酸阿罗洛尔", "盐酸氟奋乃静", "盐酸普萘洛尔片注射液", "达那唑", "地尔硫卓", "波生坦", "十一酸睾酮", "白介素", "西尼地平", "诺氟沙星乳膏", "丙酸氟替卡松鼻喷雾剂", "去氯羟嗪", "异烟肼", "法链吉（链激酶）", "盐酸三氟拉嗪", "伐昔洛韦", "氯解磷定", "辛伐他汀酸", "盐酸氟西汀", "布洛芬", "醋酸泼尼松龙乳膏", "尿激酶", "劳拉西泮", "匹维溴铵", "盐酸地尔硫卓", "格列齐特", "丙硫异烟胺", "刺五加", "山梨醇", "舍曲林等", "舒尼替尼", "磺胺嘧啶", "环孢素口服液", "阿托伐他汀", "6-巯嘌呤", "泛昔洛韦", "尼古丁酸", "胃蛋白酶", "咪唑斯汀", "更昔洛韦", "吗氯贝胺", "酒石酸美托洛尔", "恩氟烷", "沙美特罗", "干酵母", "吉米沙星", "舒林酸", "磺胺嘧啶钠", "竹林胺（酚苄明）", "奥司他韦", "罗非昔布", "盐酸奥布卡因凝胶", "苯唑西林钠", "伊来西胺", "硫黄", "头孢泊肟酯", "多元维生素", "氢化可的松乳膏", "低分子肝素", "雷尼替丁", "苯丁酸氮芥", "螺内酯", "苯丙酸诺龙", "罗格列酮", "硫酸奎宁", "干扰素", "琥珀酸氢化可的松钠", "马来酸依那普利", "土霉素", "磺胺嘧啶银", "氯普噻吨", "海捷亚（氯沙坦钾）", "帕利哌酮", "泛影葡胺", "麦白霉素", "丹曲林", "利福平", "吡喹酮", "缬沙坦", "盐酸贝那普利片富马酸比索洛尔胶囊", "哌替啶（度冷丁）", "鹅去氧胆酸", "维库溴铵", "氟尿嘧啶植入剂", "保泰松", "泮库溴铵", "去铁胺", "水杨酸钠", "丝氨酸", "吗啡", "司可巴比妥", "米索前列醇", "唑来膦酸", "131碘", "苯甲醇", "马来酸噻吗洛尔", "奥美拉唑镁", "博苏（比索洛尔）", "羟考酮", "甲酰四氢叶酸", "纳洛酮", "丁二酸洛沙平", "碘海醇", "拉莫三嗪", "溴化钙", "苯丙氨酯", "奥沙西泮", "泼尼松龙", "左乙拉西坦", "开塞露（含山梨醇）", "卡比多巴", "氨己烯酸", "甲磺酸罗哌卡因", "倍他米松软膏", "噻托溴铵", "丙戊酸镁片左乙拉西坦片", "卡比马唑", "地特胰岛素", "硝酸甘油", "甲苯磺丁脲", "盐酸罗哌卡因", "氨基水杨酸钠", "丙戊酸钠口服液", "呋喃唑酮", "罗库溴铵", "氨苯砜", "腺苷钴胺", "青蒿琥酯", "双氢麦角胺", "黄连素", "伊维菌素", "双氯西林", "琥珀胆碱", "罗可曼针（重组人促红细胞生成素）", "文拉法辛", "羟基保泰松", "氯米帕明", "非诺贝特", "枸橼酸铋钾", "红霉素", "垂体后叶素", "铁", "慢溶型剂型地高辛", "他克莫司软膏", "培美曲塞", "盐酸阿米洛利", "吡罗昔康", "加压素", "溴莫尼定", "可的松", "西咪替丁", "扑米酮", "克拉霉素", "佐米曲普坦", "酮洛芬", "葡萄糖胰岛素液", "硫鸟嘌呤", "氨鲁米特", "甲氧沙林", "尼可刹米", "阿仑膦酸", "锂", "哌甲酯", "丙磺舒", "交沙霉素", "乙酰半胱氨酸", "碘酸钾", "链激酶", "锌", "磺胺药等抑菌药", "卡维地洛", "司帕沙星", "盐酸多西环素", "去氧氟尿苷", "米库氯铵", "丁酸氢化可的松乳膏", "氨力农", "甲芬那酸", "伊班膦酸钠", "利塞膦酸钠", "联苯双酯", "比卡鲁胺", "谷维素", "奥昔布宁", "利培酮", "普萘洛尔（心得安）", "氟尿嘧啶乳膏", "乳糖酸红霉素", "西洛他唑", "过氧苯甲酰", "奋乃静", "康可（比索洛尔）", "含有乙醇的制剂", "雷洛昔芬", "头孢呋辛钠", "熊去氧胆酸", "门冬氨酸钾镁", "雷公藤", "司他夫定", "普鲁卡因青霉素", "奥美拉唑", "盐酸丙美卡因", "头孢美唑", "精蛋白锌胰岛素注射剂", "布地奈德鼻喷雾剂", "长春新碱", "甲氨蝶呤", "盐酸多塞平乳膏", "黄体酮阴道缓释凝胶", "多拉司琼", "哌唑嗪", "蚓激酶", "苯妥英钠针", "枸地氯雷他定", "托伐普坦", "普拉格雷", "盐酸尼卡地平氯化钠", "罗哌卡因", "美洛西林", "布地奈德", "人凝血酶原复合物", "更生霉素", "金诺芬", "罗通定", "毛花苷丙", "榄香烯口服乳", "氨甲环酸", "药用炭", "氯氮平", "重组葡激酶", "利鲁唑", "地塞米松", "法莫替丁颗粒", "阿替卡因", "聚卡波非钙", "咪唑立宾", "利福不汀", "吡斯的明", "非洛地平", "盐酸雷尼替丁泡腾颗粒", "氨磷汀", "磺胺药", "氯吡格雷", "尼群地平", "咖啡因", "苯巴比妥钠", "新双香豆素", "富马酸比索洛尔", "阿莫西林", "倍他米松", "羟保泰松", "脂溶性维生素", "顺铂以", "普罗帕酮", "盐酸异丙嗪", "异丙托溴铵气雾剂", "伏立康唑", "盐酸丁卡因", "甲地高辛", "美罗培南", "去乙酰毛花苷", "0.25%盐酸普鲁卡因", "钾制剂", "奈韦拉平", "磷酸咯萘啶", "奎尼丁", "莫索尼定", "萘维拉平", "安乃近", "硝苯地平", "阿扑吗啡", "吲哚布芬", "左旋门冬酰胺酶", "盐酸利多卡因胶浆", "维生素K", "维生素E", "维生素A", "万古霉素", "维生素C", "维生素D", "依替巴肽", "麦迪霉素", "头孢哌酮钠舒巴坦钠", "兰索拉唑", "依托度酸", "格列本脲", "利福布汀", "头孢唑林钠", "盐酸苯海拉明", "盐酸西那卡塞", "肾上腺素（付肾）", "布地奈德吸入剂", "索拉非尼", "琥珀胆碱等", "噻嘧啶", "洛莫司汀", "西索米星", "左乙拉西坦口服溶液", "盐酸曲唑酮", "阿加曲班", "亚叶酸钙", "二羟丙茶碱", "羧苄西林", "溴丙胺太林（普鲁本辛）", "左旋多巴合", "二甲硅油", "加兰他敏", "右旋糖酐", "马普替林", "塞来昔布", "盐酸拉贝洛尔", "碘化钾", "噻奈普汀", "青霉胺等", "阿尼普酶", "水合氯醛", "索利那新", "卡莫司汀", "考来烯胺", "银杏提取物", "洛沙平", "磷酸氢钙", "链霉素", "氯丙嗪", "伊马替尼", "苯巴比妥", "非索非那定", "萘丁美酮", "拉坦前列素", "依伦平厄贝沙坦氢氯噻嗪片", "头孢妥仑", "美托洛尔", "棓丙酯", "醋氮酰胺", "奈替米星", "多西环素", "阿替洛尔", "头孢孟多", "依诺沙星", "氢化可的松", "丙酸氟替卡松乳膏", "氯沙坦钾", "莫西沙星", "纳曲酮", "卵磷脂络合碘", "肾上腺素", "重组链激酶", "硫喷妥钠", "罂粟碱", "西沙必利", "普鲁卡因胺", "地塞米松磷酸钠", "噻氯匹定", "戊四硝酯", "前列地尔", "透明质酸酶", "盐酸贝那普利片缬沙坦分散片", "前列腺素", "鲑鱼降钙素", "达卡巴嗪", "西那卡塞", "硝酸毛果芸香碱", "诺氟沙星", "芬氟拉明", "尼莫地平", "氯化琥珀胆碱", "盐酸利多卡因凝胶", "去氧肾上腺素", "磷酸氟达拉滨", "舒马普坦", "胺碘酮", "怡那林（依那普利）", "青霉胺", "阿米洛利", "西地那非", "环丙沙星", "米卡芬净", "冰醋酸", "依托考昔", "他巴唑", "拉贝洛尔", "塞替哌", "莫雷西嗪", "棕榈哌泊塞嗪", "苯海拉明", "氯氮?", "来氟米特", "水杨酸", "碘解磷定", "引达帕胺", "布美他尼", "泮托拉唑钠", "思凯通（链激酶）", "卡那霉素", "丙胺太林", "琥珀酸氢化可的松", "异维A酸", "林可霉素", "依托咪酯", "地氯雷他定糖浆", "盐酸林可霉素", "洋地黄苷", "美西律", "放线菌素", "异戊巴比妥", "溴夫定", "吉非替尼", "巴比妥", "青霉素V钾", "硝酸异山梨酯", "多潘立酮", "甘草锌", "硼替佐米", "阿洛西林", "酒石酸唑吡坦", "二甲双胍", "利巴韦林", "青霉素G", "硫唑嘌呤", "去甲替林", "精蛋白锌胰岛素", "培门冬酶溶液", "黄体酮", "麦角胺咖啡因", "左氧氟沙星", "丙磺舒等", "苄普地尔", "盐酸氯胺酮", "盐酸四环素", "右美沙芬", "左炔诺孕酮", "甲泼尼龙", "舒托必利", "碘", "丙泊酚", "那格列奈", "依普黄酮", "麦考酚吗乙酯", "磺胺甲噻二唑", "阿司匹林栓", "博来霉素", "依诺肝素钠", "他莫昔芬", "甲硝唑", "乌洛托品", "利妥昔单抗", "奥沙利铂", "乐卡地平", "尿素", "吲哚洛尔", "复方异丙托溴铵", "福辛普利钠", "巴曲酶", "氯磺丙脲", "环磷酰胺", "齐多夫定", "哌拉西林钠", "伏格列波糖", "地奥司明", "氯氮卓", "奥美沙坦", "去氨加压素", "盐酸贝那普利片囊", "噻托溴铵粉吸入剂", "酮康唑", "地塞米松植入剂", "苯磺酸氨氯地平", "马来酸氯苯那敏", "伊曲康唑", "溴吡斯的明", "吲哚美辛", "肝素钙", "维拉帕米", "鞣酸蛋白", "单硝酸异山梨酯喷雾剂", "甲氧氯普胺", "肝素钠", "戊酸雌二醇", "依巴斯汀", "胰岛素", "高锰酸钾", "头孢氨苄", "左旋咪唑", "尼索地平胶丸", "氢氧化铝", "奥卡西平口服混悬液", "抗人淋巴细胞猪免疫球蛋白", "吡拉西坦", "赖氨匹林", "鱼精蛋白", "盐酸维拉帕米", "卷曲霉素", "甲苯咪唑", "格列美脲", "吡贝地尔", "青蒿素", "盐酸利多卡因胶浆（Ⅰ）", "双氢克尿噻（氢氯噻嗪）", "呋塞米", "罗红霉素", "阿昔洛韦", "复方异丙托溴铵气雾剂", "枸橼酸舒芬太尼", "马来酸氯苯那敏混合", "茚地那韦", "氟比洛芬", "布地奈德气雾剂", "依那西普", "吸入用七氟烷", "厄贝沙坦颗粒", "盐酸多巴酚丁胺", "表柔比星", "谷氨酸钾", "氧氟沙星", "异丙托溴铵", "硝西泮", "甲基多巴", "地红霉素", "睾酮", "地氯雷他定干混悬剂", "替拉那韦/利托那韦", "甲氧苄啶", "布比卡因", "天麻素", "格列喹酮", "头孢地尼", "孟鲁司特", "丙酸睾酮", "毒毛花苷K", "盐酸舒托必利", "帕妥珠单抗注射剂", "阿普唑仑", "辛伐他丁", "桂枝茯苓丸", "苯妥英钠", "替硝唑", "硫酸钡", "头孢噻吩钠", "雌二醇", "盐酸美西律", "艾司唑仑", "NI呋喃妥因", "克拉维酸", "丙酸倍氯米松鼻喷雾剂", "氯唑沙宗", "阿扎那韦", "枸橼酸钙", "阿奇霉素干混悬剂", "舌下用硝酸甘油", "多奈哌齐", "富马酸喹硫平", "枸橼酸钠", "尼洛替尼", "氯雷他定颗粒", "胞磷胆碱钠", "甲硫酸新斯的明", "四环素", "洛美沙星", "二氮嗪", "盐酸达克罗宁胶浆", "可乐定", "奥替溴铵", "依折麦布", "多柔比星", "普鲁卡因", "酚妥拉明", "溴隐亭", "氟哌啶醇", "昂丹司琼", "秋水仙碱", "替加色罗", "多奈哌弃", "糜蛋白酶", "三唑仑", "马来酸氟伏沙明", "1%盐酸普鲁卡因", "维A酸", "吡嗪酰胺", "他达拉非", "氟尿嘧啶", "左甲状腺素", "盐酸普萘洛尔", "甲状腺素", "帕罗西汀", "卡培他滨", "甲磺酸齐拉西酮三水合物", "盐酸普鲁卡因", "亚油酸", "噻奈普汀钠", "埃索美拉唑", "安非他酮", "长春碱", "左甲状腺素钠", "环丝氨酸", "丙戊酸钠口服溶液", "复方颠茄氢氧化铝散", "樟脑", "氯硝西泮", "依替膦酸二钠", "华法林钠", "磷胺多辛/乙胺嘧啶", "甲砜霉素", "依那普利", "金霉素", "盐酸多塞平", "灰黄霉素", "舒巴坦钠", "间苯二酚", "达肝素钠", "格列美脲滴丸", "乳酸菌素", "抑肽酶", "氟维司群", "盐酸利多卡因缓释滴丸", "头孢孟多酯钠", "氟哌利多", "头孢唑肟", "己酮可可碱", "酚磺乙胺", "阿司匹林", "左旋多巴", "阿巴卡韦", "氯氮?", "依曲康唑", "酚酞", "利血平", "磺胺甲嗯唑", "乙酰水杨酸", "雌激素", "碳酸镁", "复方醋酸地塞米松乳膏", "葡萄糖", "萘普生", "尼美舒利", "西拉普利片喹那普利", "盐酸利多卡因", "氢溴酸右美沙芬", "赖诺普利", "阿瑞匹坦", "非吲哚美辛", "促皮质素", "1%硫酸阿托品针", "顺铂", "枸橼酸锌", "甲睾酮", "双嘧达莫", "盐酸氯米帕明", "棕榈酸帕利哌酮", "磷酸氯喹", "复方利血平氨苯蝶啶", "地高辛", "吉西他滨", "碳酸锂", "头孢泊肟", "碳酸钙", "头孢曲松钠", "辛伐他汀", "水杨酸盐", "坎地沙坦酯", "盐酸帕罗西汀", "枸橼酸钾", "膦甲酸钠", "吗多明", "普萘洛尔", "头孢噻吩", "硫酸阿托品眼膏", "阿那曲唑", "多巴酚丁胺", "硫酸阿米卡星", "阿苯达唑", "盐酸文拉法辛", "乙琥胺糖浆", "细辛脑", "醋酸地塞米松乳膏", "白消安", "奎宁", "罗匹尼罗", "司可巴比妥钠", "氯沙坦", "艾司西酞普兰", "伊那普利", "麻黄碱", "复方地塞米松乳膏", "葡萄糖酸锌", "降纤酶", "硫酸庆大霉素", "长春西汀", "格列吡嗪", "柔红霉素", "呋塞米", "茶碱", "甲氯芬那酸", "盐酸咪达普利", "吗替麦考酚酯", "L-门冬氨酸氨氯地平", "氟康唑", "细胞色素C", "阿哌沙班", "氮芥", "尼立苏（尼莫地平针）", "吲达帕胺", "维生素", "巴氯芬", "丙酸倍氯米松气雾剂", "利培酮口服液", "异氟烷吸入用液体剂", "叶酸", "烟酸等", "瑞格列奈", "重组人组织型纤溶酶原激酶衍生物", "甲氧沙林溶液", "咪达普利", "间羟胺", "西酞普兰", "华法林", "烟酸", "氯化钙（静脉用）", "烟酸制剂", "对氨基水杨酸", "林可霉素等", "吉他霉素", "阿司咪唑", "酚苄明", "舒洛地特", "色甘酸钠", "枸橼酸坦度螺酮", "尼扎替丁", "阿糖胞苷", "丙酸倍氯米松粉雾剂", "扎来普隆", "阿替普酶", "他克莫司", "盐酸尼卡地平", "乙琥胺", "托吡酯", "布地奈德混悬液", "氯氮革", "氢溴酸西酞普兰", "盐酸阿米替林", "果胶", "二氟尼柳", "氟他胺", "哌仑西平", "氟伐他汀", "盐酸羟嗪", "麦角胺", "依立替康", "右佐匹克隆", "马来酸依那普利口腔崩解片片", "氨甲苯酸", "阿佐塞米", "碘[131I]化钠", "头孢哌酮钠舒巴坦钠", "西罗莫司", "阿立哌唑", "阿司匹林（肠溶）", "多西他赛", "托拉塞米", "多塞平", "氟伏沙明", "扎西他滨", "氯雷他定", "干扰素a-2b", "芬布芬", "放线菌素D", "氯美扎酮", "磺胺甲噁唑", "硫酸羟氯喹", "阿曲库铵", "碘苷", "西罗莫司口服溶液", "地氯雷他定", "依非韦伦", "盐酸利多卡因气雾剂", "羧甲淀粉钠", "克林霉素", "西沙比利", "奥曲肽", "氨曲南", "贝那普利", "奥氮平", "多巴胺", "两性霉素B（静脉用药）", "非那雄胺", "卡托普利滴丸", "唑吡坦", "氯胺酮", "哌甲酯（利他林）", "硫利达嗪", "葡萄糖酸钙", "新斯的明", "潘通针（己酮可可碱）", "东莨菪碱", "尼卡地平", "奥卡西平", "伊立替康", "泊沙康唑", "复方地塞米松软膏", "硫糖铝", "硫酸镁", "咳必清（喷托维林）", "两性霉素B", "阿托品", "丝裂霉素", "阿奇霉素", "哌库溴铵", "亚胺培南", "卡托普利", "丙戊酸", "草酸艾司西酞普", "非诺洛芬钙", "羟基脲", "盐酸金霉素", "维生素B12", "利多卡因", "阿司匹林散", "碘[131I]化钠口服溶液", "氯膦酸二钠", "氨甲蝶呤", "卡介苗", "厄贝沙坦", "喜树碱软膏", "盐酸丁螺环酮", "甲丙氨酯", "枸橼酸盐", "硫酸锌", "美沙酮", "曲普瑞林", "异环磷酰胺", "诺氟沙星栓", "丙戊酸钠", "氟尿嘧啶口服乳", "氟奋乃静", "芬太尼", "坎地沙坦", "司来吉兰", "甲磺酸氨氯地平", "磺苄西林钠", "癸氟奋乃静", "绒促性素", "白芍总苷", "鞣酸", "盐酸米那普仑", "甲巯咪唑乳膏", "咪康唑", "茶苯海明", "伊布利特", "地蒽酚", "氨氯地平", "磺胺异恶唑", "异氟烷", "西替利嗪", "比伐芦定", "加替沙星", "炔雌醚", "硫酸链霉素", "甘露醇", "甲氨蝶呤（鞘内）", "氯雷他定糖浆", "精氨酸", "维生素B族", "丁丙诺啡", "吉非罗齐", "甲磺酸培高利特", "炔雌醇", "盐酸硫利达嗪", "氯苯那敏", "阿维A酯", "精蛋白重组人胰岛素注射剂", "2%硫酸阿托品针", "胰酶", "喷他佐辛", "右旋糖酐铁", "阿法骨化醇", "依托泊苷", "氟罗沙星", "艾塞那肽", "达比加群酯", "丁螺环酮", "氢溴酸东莨菪碱", "水解蛋白", "对氨基水杨酸钠", "纤溶酶", "三氧化二砷", "双氯芬酸", "盐酸贝尼地平", "曲安奈德鼻喷雾剂", "生长激素", "低分子量肝素", "咪唑安定", "拉米夫定", "乳酸钠", "牛磺酸", "乳酸钙", "甲氧氯普胺（胃复安）", "头孢拉定", "氟马西尼", "替米沙坦", "洛伐他汀", "盐酸胺碘酮", "葡萄糖酸亚铁", "乙酰氨基酚", "替加氟栓", "路泰（吗啡）", "马来酸咪达唑仑", "洛哌丁胺", "福辛普利", "多黏菌素B", "泮托拉唑", "多黏菌素E", "来曲唑", "异博定（维拉帕米）", "氯法齐明", "甲基硫氧嘧啶", "地西泮", "碘化油", "尼莫地平胶丸", "曲马多", "硫辛酸", "洛匹那韦", "硝酸咪康唑", "白介素-2", "乙酰唑胺", "悦复隆针（普罗帕酮）", "六甲蜜胺", "溴丙胺太林", "莫达非尼", "盐酸氨溴索", "甲磺酸培氟沙星", "头孢他啶", "复方肝素钠二甲亚砜凝胶", "氯喹", "巯嘌呤", "硝酸甘油喷雾剂", "甘草酸二铵", "苯扎贝特", "加巴喷丁", "2%盐酸普鲁卡因", "咪达唑仑", "阿仑膦酸钠", "阿米替林", "奥利司他", "曲匹地尔", "塞替派", "复方山莨菪碱滴眼液", "碳酸氢钠", "盐酸氯丙嗪", "扎鲁司特", "喹硫平", "磷霉素", "氨茶碱", "头孢噻肟钠", "米力农", "比沙可啶", "左卡巴斯汀", "美沙拉嗪", "马来酸氨氯地平", "甲巯咪唑", "尼索地平", "利托那韦" };
        String[] strs2 = {"别嘌呤醇片", "五氟利多片", "甲苯磺丁脲片", "罗红霉素片", "盐酸氟西泮胶囊", "阿维A胶囊", "普伐他汀钠片", "氯化筒箭毒碱注射液", "硫酸依替米星注射液", "盐酸异丙嗪片", "泮库溴胺注射液", "萘夫西林钠胶囊", "达利全（卡维地洛片）", "西拉普利片", "紫杉醇注射液", "乙硫异烟胺肠溶片", "利伐沙班片", "利奈唑胺片", "乙胺嘧啶片", "左西孟旦注射液", "盐酸苯海索片", "舍曲林片", "特非那定分散片", "盐酸度洛西汀胶囊", "盐酸替利定片", "核黄素磷酸钠片", "氯美噻唑片", "注射用头孢西丁", "注射用盐酸头孢替安", "培哚普利片", "环索奈德气雾剂", "苯噻啶片", "乙托咪酯片", "美托拉宗片", "盐酸金刚烷胺胶囊", "注射用盐酸氯普鲁卡因", "地拉韦啶分散片", "盐酸阿莫地喹片", "美洛昔康肠溶片", "柳氮磺吡啶肠溶片", "雷米普利片", "罗氟司特片", "盐酸甲氯芬酯胶囊", "头孢呋辛酯片", "阿利吉仑片", "枸橼酸西地那非片", "苯溴马隆分散片", "维生素B2片", "维生素B1片", "维生素B6片", "拉帕替尼片", "硝普钠片", "盐酸甲哌卡因注射液", "戊巴比妥钠片", "帕吉林片", "盐酸三氟拉嗪片", "苯妥英钠片", "甲磺酸瑞波西汀片", "依维莫司片", "替考拉宁注射液", "卡马西平片", "盐酸齐拉西酮片", "氢氯噻嗪片", "氯雷他定泡腾片", "替加氟片", "非布司他片", "盐酸索他洛尔片", "盐酸哌罗匹隆片", "氯诺昔康片", "盐酸普鲁卡因胺片", "索他洛尔片", "沙利度胺片", "舒必利片", "拉西地平片", "替尼泊苷注射液", "马拉韦罗片", "氢氯噻嗪片", "盐酸阿莫地喹片", "头孢布烯片", "甲硫氧嘧啶片", "氨苄西林胶囊", "羟吗啡酮片", "普伐他汀片", "氨苯蝶啶片", "马来酸左旋氨氯地平片", "呋喃妥因片", "炔诺酮片", "奈法唑酮片", "阿昔莫司胶囊", "氟西泮片", "磷酸萘酚喹片", "氢化可的松琥珀酸钠片", "盐酸胍乙啶片", "考来替兰片", "盐酸舍曲林片", "米氮平片", "美司钠注射液", "尼可地尔片", "胆茶碱片", "重组人白细胞介素-2(125Ala)注射液", "马来酸左旋氨氯地平片", "妥布霉素眼膏", "头孢克肟颗粒", "阿司咪唑片", "阿西美辛胶囊", "盐酸胺碘酮片", "羟甲香豆素胶囊", "硫酸羟氯喹片", "盐酸氟西汀片", "氨磺必利片", "利福昔明片", "普罗布考片", "地氟烷溶液", "去甲肾上腺素注射液", "头孢丙烯胶囊", "格拉司琼注射液", "盐酸右美托咪定注射液", "盐酸特比萘芬片", "盐酸厄洛替尼片", "盐酸阿普林定片", "单硝酸异山梨酯分散片", "苦参素胶囊", "盐酸达克罗宁胶浆", "阿折地平片", "注射用巴利昔单抗", "复方薄荷脑软膏", "吗替麦考酚酯干混悬剂", "孟鲁司特钠片", "丙硫氧嘧啶片", "螺旋霉素片", "利多卡因气雾剂", "碳酸司维拉姆片", "匹莫林片", "烟酰胺片", "硫酸阿托品注射液", "佐匹克隆片", "盐酸阿罗洛尔片", "盐酸氟奋乃静片", "盐酸普萘洛尔片", "达那唑栓", "盐酸地尔硫卓片", "波生坦片", "十一酸睾酮胶丸", "注射用重组人白介素-2", "西尼地平胶囊", "诺氟沙星乳膏", "丙酸氟替卡松鼻喷雾剂", "复方羟丙茶碱去氯羟嗪胶囊", "异烟肼片", "注射用重组链激酶", "盐酸三氟拉嗪片", "盐酸伐昔洛韦片", "氯解磷定注射液", "辛伐他汀片", "盐酸氟西汀分散片", "布洛芬片", "醋酸泼尼松龙乳膏", "注射用尿激酶", "劳拉西泮片", "匹维溴铵片", "盐酸地尔硫卓片", "格列齐特缓释片", "丙硫异烟胺片", "刺五加注射液", "开塞露(含山梨醇)", "盐酸舍曲林片", "苹果酸舒尼替尼胶囊", "磺胺嘧啶片", "环孢素口服液", "阿托伐他汀钙片", "巯嘌呤片", "泛昔洛韦片", "尼古丁透皮贴剂", "胃蛋白酶颗粒", "咪唑斯汀缓释片", "注射用更昔洛韦", "吗氯贝胺片", "酒石酸美托洛尔片", "恩氟烷", "沙美特罗替卡松粉吸入剂", "干酵母片", "甲磺酸吉米沙星片吉米沙星片", "舒林酸片", "磺胺嘧啶钠", "盐酸酚苄明片", "磷酸奥司他韦胶囊", "罗非昔布片", "盐酸奥布卡因凝胶", "注射用苯唑西林钠", "伊来西胺片", "复方硫黄乳膏", "头孢泊肟酯片", "复合维生素片", "氢化可的松乳膏", "低分子肝素钙注射液", "盐酸雷尼替丁胶囊", "苯丁酸氮芥片", "螺内酯片", "苯丙酸诺龙注射液", "罗格列酮片", "硫酸奎宁片", "重组人干扰素α1b", "注射用氢化可的松琥珀酸钠", "马来酸依那普利片", "土霉素片", "磺胺嘧啶银乳膏", "氯普噻吨片", "氯沙坦钾片", "帕利哌酮缓释片", "泛影葡胺注射液", "麦白霉素胶囊", "丹曲林钠胶囊", "利福平胶囊", "吡喹酮片", "缬沙坦胶囊", "盐酸贝那普利片", "盐酸哌替啶注射液", "鹅去氧胆酸胶囊", "注射用维库溴铵", "氟尿嘧啶植入剂", "保泰松片", "泮库溴铵注射液", "注射用甲磺酸去铁胺", "水杨酸钠片", "环丝氨酸胶囊", "盐酸吗啡注射液", "司可巴比妥钠胶囊", "米索前列醇片", "唑来膦酸注射液", "邻碘[131I]马尿酸钠注射液", "苯甲醇注射液", "马来酸噻吗洛尔滴眼液", "奥美拉唑镁肠溶片", "比索洛尔氢氯噻嗪片", "盐酸羟考酮缓释片", "甲酰四氢叶酸钙注射液", "盐酸纳洛酮注射液", "丁二酸洛沙平胶囊", "碘海醇注射液", "拉莫三嗪片", "溴化钙注射液", "苯丙氨酯片", "奥沙西泮片", "醋酸泼尼松龙注射液", "左乙拉西坦片", "开塞露(含山梨醇)", "复方卡比多巴片", "氨己烯酸片", "甲磺酸罗哌卡因注射液", "倍他米松软膏", "噻托溴铵粉吸入剂", "丙戊酸镁片", "卡比马唑片", "地特胰岛素注射液", "硝酸甘油片", "甲苯磺丁脲片", "盐酸罗哌卡因注射液", "对氨基水杨酸钠片", "丙戊酸钠口服液", "呋喃唑酮片", "罗库溴铵注射液", "氨苯砜片", "腺苷钴胺片", "青蒿琥酯片", "甲磺酸双氢麦角胺缓释胶囊", "盐酸黄连素片", "伊维菌素片", "阿莫西林双氯西林钠胶囊", "氯化琥珀胆碱注射液", "重组人促红素-β注射液（CHO细胞）", "盐酸文拉法辛胶囊", "保泰松片", "盐酸氯米帕明片", "非诺贝特胶囊", "枸橼酸铋钾片", "红霉素软膏", "垂体后叶注射液", "硫酸亚铁片", "地高辛片", "他克莫司软膏", "注射用培美曲塞二钠", "盐酸阿米洛利片", "吡罗昔康片", "醋酸去氨加压素片", "酒石酸溴莫尼定滴眼液", "醋酸可的松片", "西咪替丁片", "扑米酮片", "克拉霉素片", "佐米曲普坦片", "酮洛芬胶囊", "葡萄糖胰岛素液", "硫鸟嘌呤片", "氨鲁米特片", "甲氧沙林片", "尼可刹米注射液", "阿仑膦酸钠片", "碳酸锂片", "盐酸哌甲酯片", "丙磺舒片", "交沙霉素片", "乙酰半胱氨酸颗粒", "碘酸钾片", "注射用重组链激酶", "葡萄糖酸锌口服液", "复方磺胺甲噁唑片", "卡维地洛片", "司帕沙星片", "盐酸多西环素片", "去氧氟尿苷胶囊", "米库氯铵注射液", "丁酸氢化可的松乳膏", "注射用氨力农", "甲芬那酸片", "伊班膦酸钠注射液", "利塞膦酸钠片", "联苯双酯片", "比卡鲁胺胶囊", "谷维素片", "盐酸奥昔布宁胶囊", "利培酮片", "盐酸普萘洛尔片", "氟尿嘧啶乳膏", "注射用乳糖酸红霉素", "西洛他唑片", "过氧苯甲酰凝胶", "奋乃静片", "富马酸比索洛尔片", "三乙醇胺乳膏", "盐酸雷洛昔芬片", "注射用头孢呋辛钠", "熊去氧胆酸胶囊", "门冬氨酸钾镁片", "雷公藤片", "司他夫定片", "注射用普鲁卡因青霉素", "奥美拉唑镁肠溶片", "盐酸丙美卡因滴眼液", "注射用头孢美唑钠", "低精蛋白锌胰岛素注射液", "布地奈德鼻喷雾剂", "注射用硫酸长春新碱", "甲氨蝶呤注射液", "盐酸多塞平乳膏", "黄体酮阴道缓释凝胶", "甲磺酸多拉司琼注射液", "盐酸哌唑嗪片", "蚓激酶肠溶胶囊", "苯妥英钠片", "枸地氯雷他定片", "托伐普坦片", "普拉格雷片", "盐酸尼卡地平氯化钠注射液", "盐酸罗哌卡因注射液", "注射用美洛西林钠", "布地奈德鼻喷雾剂", "人凝血酶原复合物", "注射用放线菌素D", "金诺芬片", "罗通定片", "毛花苷丙注射液", "榄香烯口服乳", "氨甲环酸注射液", "药用炭片", "氯氮平片", "注射用重组葡激酶", "利鲁唑片", "醋酸地塞米松片", "法莫替丁颗粒", "阿替卡因肾上腺素注射液", "聚卡波非钙片", "咪唑立宾片", "利福布汀胶囊", "溴吡斯的明片", "非洛地平片", "盐酸雷尼替丁泡腾颗粒", "注射用氨磷汀", "复方磺胺甲噁唑片", "硫酸氢氯吡格雷片", "尼群地平片", "麦角胺咖啡因片", "苯巴比妥钠注射液", "双香豆素片", "富马酸比索洛尔片", "阿莫西林胶囊", "倍他米松片", "保泰松片", "注射用脂溶性维生素（II）", "顺铂注射液", "盐酸普罗帕酮片", "盐酸异丙嗪片", "异丙托溴铵气雾剂", "注射用伏立康", "注射用盐酸丁卡因", "甲地高辛片", "注射用美罗培南", "去乙酰毛花苷注射液", "盐酸普鲁卡因注射液", "氯化钾注射液", "奈韦拉平片", "磷酸咯萘啶肠溶片", "硫酸奎尼丁片", "盐酸莫索尼定片", "奈韦拉平片", "安乃近片", "硝苯地平片", "盐酸阿扑吗啡注射液", "吲哚布芬片", "注射用左旋门冬酰胺酶", "盐酸利多卡因胶浆", "维生素K1注射液", "维生素E软胶囊", "维生素A胶丸", "注射用盐酸万古霉素", "维生素C片", "维生素D滴剂", "依替巴肽注射液", "麦迪霉素片", "注射用头孢哌酮钠舒巴坦钠", "兰索拉唑胶囊", "依托度酸胶囊", "格列本脲片", "利福布汀胶囊", "注射用头孢唑林钠", "盐酸苯海拉明片", "西那卡塞片", "盐酸肾上腺素注射液", "布地奈德吸入剂", "甲苯磺酸索拉非尼片", "氯化琥珀胆碱注射液", "双羟萘酸噻嘧啶片", "洛莫司汀胶囊", "硫酸西索米星注射液", "左乙拉西坦口服溶液", "盐酸曲唑酮片", "阿加曲班注射液", "亚叶酸钙注射液", "二羟丙茶碱注射液", "注射用羧苄西林钠", "溴丙胺太林片", "左旋多巴片", "二甲硅油片", "氢溴酸加兰他敏片", "右旋糖酐铁注射液", "盐酸马普替林片", "塞来昔布胶囊", "盐酸拉贝洛尔片", "碘化钾片", "噻奈普汀钠片", "青霉胺片", "注射用阿替普酶", "樟脑水合氯醛酊", "琥珀酸索利那新片", "卡莫司汀注射液", "考来烯胺散", "银杏叶提取物片", "丁二酸洛沙平胶囊", "维D2磷酸氢钙片", "注射用硫酸链霉素", "盐酸氯丙嗪片", "甲磺酸伊马替尼胶囊", "苯巴比妥片", "盐酸非索非那定片", "萘丁美酮胶囊", "拉坦前列素滴眼液", "厄贝沙坦氢氯噻嗪片", "头孢妥仑匹酯片", "酒石酸美托洛尔片", "注射用棓丙酯", "乙酰唑胺片", "硫酸奈替米星注射液", "盐酸多西环素片", "阿替洛尔片", "注射用头孢孟多酯钠", "依诺沙星片", "氢化可的松注射液", "丙酸氟替卡松乳膏", "氯沙坦钾片", "盐酸莫西沙星片", "盐酸纳曲酮片", "卵磷脂络合碘胶囊", "阿替卡因肾上腺素注射液", "注射用重组链激酶", "注射用硫喷妥钠", "盐酸罂粟碱注射液", "西沙必利片", "盐酸普鲁卡因胺注射液", "地塞米松磷酸钠注射液", "盐酸噻氯匹定片", "戊四硝酯片", "前列地尔注射液", "透明质酸钠滴眼液", "盐酸贝那普利片", "地诺前列酮（前列腺素E2）栓", "注射用鲑鱼降钙素", "注射用达卡巴嗪", "西那卡塞片", "硝酸毛果芸香碱片", "诺氟沙星胶囊", "盐酸芬氟拉明片", "尼莫地平片", "氯化琥珀胆碱注射液", "盐酸利多卡因凝胶", "盐酸去氧肾上腺素注射液", "注射用磷酸氟达拉滨", "琥珀酸舒马普坦片", "盐酸胺碘酮片", "马来酸依那普利片", "青霉胺片", "盐酸阿米洛利片", "枸橼酸西地那非片", "盐酸环丙沙星片", "注射用米卡芬净钠", "水杨酸冰醋酸溶液", "依托考昔片", "甲巯咪唑片", "盐酸拉贝洛尔片", "塞替派注射液", "盐酸莫雷西嗪片", "棕榈哌泊塞嗪注射液", "盐酸苯海拉明片", "氯氮平片", "来氟米特片", "水杨酸软膏", "碘解磷定注射液", "吲达帕胺片", "布美他尼片", "泮托拉唑钠肠溶片", "注射用重组链激酶", "硫酸丁胺卡那霉素注射液", "溴丙胺太林片", "注射用氢化可的松琥珀酸钠", "异维A酸胶丸", "盐酸林可霉素注射液", "依托咪酯注射液", "地氯雷他定糖浆", "盐酸林可霉素片", "洋地黄毒甙片", "盐酸美西律片", "注射用放线菌素D", "异戊巴比妥片", "溴夫定片", "吉非替尼片", "苯巴比妥钠注射液", "青霉素V钾片", "硝酸异山梨酯片", "多潘立酮片", "甘草锌胶囊", "注射用硼替佐米", "注射用阿洛西林钠", "酒石酸唑吡坦片", "盐酸二甲双胍片", "利巴韦林注射液", "注射用青霉素钠", "硫唑嘌呤片", "盐酸阿米替林片", "低精蛋白锌胰岛素注射液", "培门冬酶注射液", "黄体酮胶囊", "麦角胺咖啡因片", "盐酸左氧氟沙星注射液", "丙磺舒片", "盐酸苄普地尔片", "盐酸氯胺酮注射液", "盐酸四环素片", "注射用氢溴酸右美沙芬", "左炔诺孕酮肠溶片", "甲泼尼龙片", "盐酸舒托必利片", "邻碘[131I]马尿酸钠注射液", "丙泊酚注射液", "那格列奈片", "依普黄酮片", "吗替麦考酚酯片", "复方磺胺甲噁唑片", "阿司匹林栓", "注射用盐酸博来霉素", "依诺肝素钠注射液", "枸橼酸他莫昔芬片", "甲硝唑片", "乌洛托品片", "利妥昔单抗注射液", "注射用奥沙利铂", "盐酸乐卡地平片", "尿素乳膏", "吲哚洛尔片", "吸入用复方异丙托溴铵溶液", "福辛普利钠片", "巴曲酶注射液", "氯磺丙脲片", "注射用环磷酰胺", "齐多夫定片", "注射用哌拉西林钠", "伏格列波糖片", "地奥司明片", "氯氮卓片", "奥美沙坦酯片", "醋酸去氨加压素片", "盐酸贝那普利片", "噻托溴铵粉吸入剂", "酮康唑片", "地塞米松植入剂", "苯磺酸氨氯地平片", "马来酸氯苯那敏片", "伊曲康唑胶囊", "溴吡斯的明片", "吲哚美辛片", "注射用低分子量肝素钙", "盐酸维拉帕米片", "鞣酸蛋白片", "单硝酸异山梨酯喷雾剂", "甲氧氯普胺片", "低分子量肝素钠注射液", "戊酸雌二醇片", "依巴斯汀片", "甘精胰岛素注射液", "高锰酸钾外用片", "头孢氨苄片", "盐酸左旋咪唑片", "尼索地平胶丸", "复方氢氧化铝片", "奥卡西平口服混悬液", "抗人T细胞猪免疫球蛋白", "吡拉西坦片", "赖氨匹林散", "硫酸鱼精蛋白注射液", "盐酸维拉帕米片", "注射用硫酸卷曲霉素", "甲苯咪唑片", "格列美脲片", "吡贝地尔缓释片", "双氢青蒿素片", "盐酸利多卡因胶浆", "氢氯噻嗪片", "呋塞米片", "罗红霉素片", "阿昔洛韦片", "丙托溴铵气雾剂", "枸橼酸舒芬太尼注射液", "马来酸氯苯那敏片", "硫酸茚地那韦片", "氟比洛芬酯注射液", "布地奈德气雾剂", "注射用依那西普", "吸入用七氟烷", "厄贝沙坦颗粒", "盐酸多巴酚丁胺注射液", "注射用盐酸表柔比星", "谷氨酸钾注射液", "盐酸左氧氟沙星注射液", "异丙托溴铵气雾剂", "硝西泮片", "甲基多巴片", "地红霉素肠溶胶囊", "睾酮贴剂", "地氯雷他定干混悬剂", "洛匹那韦利托那韦片", "甲氧苄啶片", "盐酸左布比卡因注射液", "天麻素注射液", "格列喹酮片", "头孢地尼胶囊", "孟鲁司特钠片", "丙酸睾酮注射液", "毒毛花苷k注射液", "盐酸舒托必利片", "帕妥珠单抗注射剂", "阿普唑仑片", "辛伐他汀片", "桂枝茯苓丸", "苯妥英钠片", "替硝唑片", "硫酸钡混悬液", "注射用头孢噻吩钠", "雌二醇片", "盐酸美西律片", "艾司唑仑片", "呋喃妥因片", "注射用阿莫西林钠克拉维酸钾", "丙酸倍氯米松鼻喷雾剂", "氯唑沙宗片", "硫酸阿扎那韦胶囊", "枸橼酸钙片", "阿奇霉素干混悬剂", "硝酸甘油片", "盐酸多奈哌齐片", "富马酸喹硫平片", "输血用枸橼酸钠注射液", "尼洛替尼胶囊", "氯雷他定颗粒", "胞磷胆碱钠胶囊", "甲硫酸新斯的明注射液", "四环素片", "盐酸洛美沙星注射液", "二氮嗪注射液", "盐酸达克罗宁胶浆", "盐酸可乐定片", "奥替溴铵片", "依折麦布片", "注射用盐酸多柔比星", "盐酸普鲁卡因注射液", "甲磺酸酚妥拉明片", "甲磺酸溴隐亭片", "氟哌啶醇片", "盐酸昂丹司琼片", "秋水仙碱片", "马来酸替加色罗片", "盐酸多奈哌齐片", "注射用糜蛋白酶", "三唑仑片", "马来酸氟伏沙明片", "盐酸普鲁卡因注射液", "维A酸片", "吡嗪酰胺片", "他达拉非片", "氟尿嘧啶注射液", "左甲状腺素钠片", "盐酸普萘洛尔片", "左甲状腺素钠片", "盐酸帕罗西汀片", "卡培他滨片", "甲磺酸齐拉西酮注射液", "盐酸普鲁卡因注射液", "复方三维亚油酸胶丸Ⅰ", "噻奈普汀钠片", "埃索美拉唑肠溶胶囊", "盐酸安非他酮片", "硫酸长春碱注射液", "左甲状腺素钠片", "环丝氨酸胶囊", "丙戊酸钠口服溶液", "复方颠茄氢氧化铝散", "樟脑醑", "氯硝西泮片", "依替膦酸二钠片", "华法林钠片", "乙胺嘧啶片", "甲砜霉素片", "马来酸依那普利片", "盐酸金霉素眼膏", "盐酸多塞平片", "灰黄霉素片", "注射用头孢哌酮钠舒巴坦钠", "复方间苯二酚乳膏", "达肝素钠注射液", "格列美脲滴丸", "乳酸菌素片", "注射用抑肽酶", "氟维司群注射液", "盐酸利多卡因缓释滴丸", "注射用头孢孟多酯钠", "氟哌利多注射液", "注射用头孢唑肟钠", "己酮可可碱肠溶片", "酚磺乙胺注射液", "阿司匹林肠溶片", "左旋多巴片", "阿巴卡韦双夫定片", "氯氮平片", "伊曲康唑胶囊", "酚酞片", "复方利血平片", "复方磺胺甲噁唑片", "复方乙酰水杨酸片", "结合雌激素片", "铝碳酸镁片", "复方醋酸地塞米松乳膏", "葡萄糖注射液", "萘普生片", "尼美舒利片", "西拉普利片", "盐酸利多卡因注射液", "注射用氢溴酸右美沙芬", "赖诺普利片", "阿瑞匹坦胶囊", "吲哚美辛片", "注射用促皮质素", "硫酸阿托品注射液", "顺铂注射液", "枸橼酸锌片", "甲睾酮片", "双嘧达莫片", "盐酸氯米帕明片", "棕榈酸帕利哌酮注射液", "磷酸氯喹片", "复方利血平氨苯蝶啶片", "地高辛片", "注射用盐酸吉西他滨", "碳酸锂片", "头孢泊肟酯片", "碳酸钙片", "注射用头孢曲松钠", "辛伐他汀片", "水杨酸软膏", "坎地沙坦酯片", "盐酸帕罗西汀片", "枸橼酸钾颗粒", "注射用膦甲酸钠", "吗多明气雾剂", "盐酸普萘洛尔片", "注射用头孢噻吩钠", "硫酸阿托品眼膏", "阿那曲唑片", "盐酸多巴酚丁胺注射液", "硫酸阿米卡星注射液", "阿苯达唑片", "盐酸文拉法辛胶囊", "乙琥胺糖浆", "细辛脑注射液", "醋酸地塞米松乳膏", "白消安片", "硫酸奎宁片", "盐酸罗匹尼罗片", "司可巴比妥钠胶囊", "氯沙坦钾片", "草酸艾司西酞普兰片", "马来酸依那普利片", "盐酸麻黄碱片", "复方地塞米松乳膏", "葡萄糖酸锌片", "注射用降纤酶", "硫酸庆大霉素注射液", "长春西汀片", "格列吡嗪片", "注射用盐酸柔红霉素", "呋塞米片", "氨茶碱片", "单氯芬那酸片", "盐酸咪达普利片", "吗替麦考酚酯片", "L-门冬氨酸氨氯地平片", "氟康唑胶囊", "细胞色素C注射液", "阿哌沙班片", "盐酸氮芥搽剂", "尼莫地平注射液", "吲达帕胺片", "维生素C片", "巴氯芬片", "丙酸倍氯米松气雾剂", "利培酮口服液", "异氟烷液", "叶酸片", "烟酸片", "瑞格列奈片", "重组人组织型纤溶酶原激酶衍生物", "甲氧沙林溶液", "盐酸咪达普利片", "重酒石酸间羟胺注射液", "氢溴酸西酞普兰片", "华法林钠片", "烟酸片", "氯化钙注射液", "烟酸片", "对氨基水杨酸钠片", "林可霉素维B6乳膏", "吉他霉素片", "阿司咪唑片", "盐酸酚苄明片", "舒洛地特软胶囊", "色甘酸钠滴眼液", "枸橼酸坦度螺酮片", "尼扎替丁片", "注射用阿糖胞苷", "丙酸倍氯米松粉雾剂", "扎来普隆胶囊", "注射用阿替普酶", "他克莫司胶囊", "盐酸尼卡地平注射液", "乙琥胺糖浆", "托吡酯片", "吸入用布地奈德混悬液", "氯氮平片", "氢溴酸西酞普兰片", "盐酸阿米替林片", "胶体果胶铋胶囊", "二氟尼柳片", "氟他胺片", "盐酸哌仑西平片", "氟伐他汀钠胶囊", "盐酸羟嗪片", "麦角胺咖啡因片", "盐酸伊立替康注射液", "右佐匹克隆片", "马来酸依那普利口腔崩解片", "氨甲苯酸片", "阿佐塞米片", "邻碘[131I]马尿酸钠注射液", "注射用头孢哌酮钠舒巴坦钠", "西罗莫司片", "阿立哌唑片", "阿司匹林肠溶片", "多西他赛注射液", "托拉塞米片", "多塞平片", "马来酸氟伏沙明片", "注射用地西他滨", "氯雷他定片", "聚乙二醇干扰素α-2b注射剂", "芬布芬片", "注射用放线菌素D", "氯美扎酮片", "复方磺胺甲噁唑片", "硫酸羟氯喹片", "注射用苯磺酸阿曲库铵", "碘苷滴眼液", "西罗莫司口服溶液", "枸地氯雷他定片", "依非韦伦片", "盐酸利多卡因气雾剂", "羧甲淀粉钠溶液", "盐酸克林霉素注射液", "西沙必利片", "醋酸奥曲肽注射液", "注射用氨曲南", "盐酸贝那普利片", "奥氮平片", "盐酸多巴胺注射液", "注射用两性霉素B", "非那雄胺片", "卡托普利滴丸", "酒石酸唑吡坦片", "盐酸氯胺酮注射液", "盐酸哌甲酯片", "盐酸硫利达嗪片", "葡萄糖酸钙片", "甲硫酸新斯的明注射液", "己酮可可碱肠溶片", "丁溴东莨菪碱注射液", "盐酸尼卡地平注射液", "奥卡西平片", "盐酸伊立替康注射液", "泊沙康唑口服混悬液", "复方地塞米松软膏", "硫糖铝分散片", "硫酸镁注射液", "枸橼酸喷托维林片", "注射用两性霉素B", "硫酸阿托品注射液", "注射用丝裂霉素", "注射用阿奇霉素", "注射用哌库溴铵", "注射用亚胺培南西司他丁钠", "卡托普利片", "丙戊酸钠缓释片", "草酸艾司西酞普兰片", "非诺洛芬钙片", "羟基脲片", "盐酸金霉素眼膏", "维生素B12片", "复方盐酸利多卡因注射液", "阿司匹林散", "碘[131I]化钠口服溶液", "氯膦酸二钠片", "氨甲蝶呤", "皮内注射用卡介苗", "厄贝沙坦片", "喜树碱软膏", "盐酸丁螺环酮片", "甲丙氨酯片", "枸橼酸铋钾片", "硫酸锌片", "盐酸美沙酮片", "注射用醋酸曲普瑞林", "注射用异环磷酰胺", "诺氟沙星栓", "丙戊酸钠片", "氟尿嘧啶口服乳", "盐酸氟奋乃静片", "芬太尼透皮贴剂", "坎地沙坦酯片", "盐酸司来吉兰片", "甲磺酸氨氯地平片", "注射用磺苄西林钠", "癸氟奋乃静注射液", "注射用绒促性素", "白芍总苷胶囊", "鞣酸蛋白片", "盐酸米那普仑片", "甲巯咪唑乳膏", "硝酸咪康唑散", "茶苯海明片", "富马酸伊布利特注射液", "地蒽酚软膏", "马来酸氨氯地平片", "复方磺胺甲恶唑片", "异氟烷液", "盐酸西替利嗪片", "注射用比伐芦定", "加替沙星片", "左炔诺孕酮炔雌醚片", "注射用硫酸链霉素", "甘露醇注射液", "甲氨蝶呤注射液", "氯雷他定糖浆", "盐酸精氨酸片", "复合维生素B片", "丁丙诺啡透皮贴剂", "吉非罗齐胶囊", "甲磺酸培高利特片", "炔雌醇片", "盐酸硫利达嗪片", "马来酸氯苯那敏片", "阿维A胶囊", "精蛋白重组人胰岛素注射液", "硫酸阿托品注射液", "胰酶肠溶胶囊", "喷他佐辛注射液", "右旋糖酐铁注射液", "阿法骨化醇胶丸", "依托泊苷胶囊", "氟罗沙星片", "艾塞那肽注射液", "达比加群酯胶囊", "盐酸丁螺环酮片", "氢溴酸东莨菪碱注射液", "水解蛋白注射液", "对氨基水杨酸钠片", "注射用纤溶酶", "注射用三氧化二砷", "双氯芬酸钾片", "盐酸贝尼地平片", "曲安奈德鼻喷雾剂", "重组人生长激素注射液", "低分子量肝素钙注射液", "咪达唑仑注射液", "拉米夫定片", "乳酸钠注射液", "牛磺酸颗粒", "乳酸钙片", "甲氧氯普胺片", "头孢拉定胶囊", "氟马西尼注射液", "替米沙坦片", "洛伐他汀片", "盐酸胺碘酮注射液", "葡萄糖酸亚铁糖浆", "对乙酰氨基酚片", "替加氟栓", "盐酸吗啡注射液", "马来酸咪达唑仑片", "盐酸洛哌丁胺胶囊", "福辛普利钠片", "注射用硫酸多粘菌素B", "泮托拉唑钠肠溶胶囊", "注射用硫酸抗敌素", "来曲唑片", "盐酸维拉帕米片", "氯法齐明胶丸", "丙硫氧嘧啶片", "地西泮", "碘化油注射液", "尼莫地平胶丸", "盐酸曲马多缓释片", "硫辛酸注射液", "洛匹那韦利托那韦片", "硝酸咪康唑乳膏", "重组人白介素-2注射液", "乙酰唑胺片", "盐酸普罗帕酮片", "六甲蜜胺胶囊", "溴丙胺太林片", "莫达非尼片", "盐酸氨溴索注射液", "甲磺酸培氟沙星片", "注射用头孢他啶", "复方肝素钠二甲亚砜凝胶", "磷酸氯喹片", "巯嘌呤片", "硝酸甘油喷雾剂", "甘草酸二铵胶囊", "苯扎贝特片", "加巴喷丁胶囊", "盐酸普鲁卡因注射液", "咪达唑仑注射液", "阿仑膦酸钠片", "盐酸阿米替林片", "奥利司他胶囊", "曲匹地尔片", "塞替派注射液", "复方山莨菪碱滴眼液", "碳酸氢钠片", "盐酸氯丙嗪片", "扎鲁司特片", "富马酸喹硫平片", "磷霉素钙片", "氨茶碱片", "注射用头孢噻肟钠", "米力农注射液", "比沙可啶片", "盐酸左卡巴斯汀鼻喷雾剂", "美沙拉嗪肠溶片", "马来酸氨氯地平片", "甲巯咪唑片", "尼索地平片", "利托那韦片" };
        String[] strs3 = {"盐酸盐酸", "碳酸碳酸", "复方复方", "硫酸硫酸", "注射用重组人注射用重组人", "复方羟丙茶碱复方羟丙茶碱", "乳膏乳膏", "喷雾剂喷雾剂", "胶丸胶丸", "栓栓", "气雾剂气雾剂", "软膏软膏", "注射用注射用", "胶浆胶浆", "分散片分散片", "注射液注射液", "溶液溶液", "颗粒颗粒", "眼膏眼膏", "注射用重组注射用重组", "胶囊胶囊", "缓释片缓释片", "片片", "开塞露开塞露", "苹果酸苹果酸", "口服液口服液", "钙钙", "透皮贴剂透皮贴剂", "替卡松粉吸入剂替卡松粉吸入剂", "甲磺酸甲磺酸", "磷酸磷酸", "复合复合", "重组人重组人", "α1bα1b", "琥珀酸琥珀酸", "钠钠", "注射用甲磺酸注射用甲磺酸", "环环", "马尿酸钠注射液马尿酸钠注射液", "邻碘邻碘", "滴眼液滴眼液", "肠溶片肠溶片", "氢氯噻嗪片氢氯噻嗪片", "富马酸富马酸", "醋酸醋酸", "粉吸入剂粉吸入剂", "对对", "甲磺酸甲磺酸", "缓释胶囊缓释胶囊", "钠胶囊钠胶囊", "阿莫西林阿莫西林", "氯化氯化", "重组人促红素-β重组人促红素-β", "硫酸亚硫酸亚", "二钠二钠", "醋酸去氨醋酸去氨", "酒石酸酒石酸", "醋酸醋酸", "注射用重组注射用重组", "葡萄糖酸葡萄糖酸", "口服液口服液", "甲噁唑片甲噁唑片", "三三", "胺乳膏胺乳膏", "镁肠溶片镁肠溶片", "钠钠", "低低", "注射用硫酸注射用硫酸", "肠溶胶囊肠溶胶囊", "鼻喷雾剂鼻喷雾剂", "注射用放线菌素D注射用放线菌素D", "肾上腺素注射液肾上腺素注射液", "硫酸氢硫酸氢", "麦角胺麦角胺", "（II）（II）", "氯化氯化", "肠溶片肠溶片", "1注射液1注射液", "软胶囊软胶囊", "胶丸胶丸", "注射用盐酸注射用盐酸", "滴剂滴剂", "双羟萘酸双羟萘酸", "樟脑樟脑", "琥珀酸琥珀酸", "散散", "丁二酸丁二酸", "维D2维D2", "注射用硫酸注射用硫酸", "匹酯片匹酯片", "乙酰唑胺片乙酰唑胺片", "酯钠酯钠", "阿替卡因阿替卡因", "钠滴眼液钠滴眼液", "地诺前列酮（地诺前列酮（", "琥珀酸琥珀酸", "马来酸马来酸", "枸橼酸枸橼酸", "水杨酸水杨酸", "溶液溶液", "平片平片", "硫酸丁胺硫酸丁胺", "琥珀酸钠琥珀酸钠", "胶丸胶丸", "毒毒", "DD", "苯苯", "注射用氢溴酸注射用氢溴酸", "肠溶片肠溶片", "吗替吗替", "七七", "吸入用吸入用", "酯片酯片", "注射用低分子量注射用低分子量", "低分子量低分子量", "甘精甘精", "外用片外用片", "T细胞T细胞", "双氢双氢", "酯注射液酯注射液", "吸入用吸入用", "盐酸左盐酸左", "气雾剂气雾剂", "(30/70)(30/70)", "贴剂贴剂", "洛匹那韦洛匹那韦", "钡混悬液钡混悬液", "注射用阿莫西林钠注射用阿莫西林钠", "钾钾", "输血用输血用", "钠片钠片", "左左", "复方三维复方三维", "胶丸Ⅰ胶丸Ⅰ", "醑醑", "注射用头孢哌酮钠注射用头孢哌酮钠", "注射用盐酸注射用盐酸", "钾片钾片", "草酸草酸", "氨氨", "葡萄糖注射液葡萄糖注射液", "搽剂搽剂", "重酒石酸重酒石酸", "氢溴酸氢溴酸", "维B6乳膏维B6乳膏", "胶体胶体", "铋胶囊铋胶囊", "咖啡因片咖啡因片", "聚乙二醇聚乙二醇", "注射用苯磺酸注射用苯磺酸", "枸枸", "甲硫酸甲硫酸", "丁溴丁溴", "口服混悬液口服混悬液", "西司他丁钠西司他丁钠", "口服溶液口服溶液", "皮内注射用皮内注射用", "透皮贴剂透皮贴剂", "30注射液30注射液", "蛋白片蛋白片", "硝酸硝酸", "左炔诺孕酮左炔诺孕酮", "氢溴酸氢溴酸", "重组人重组人", "钙注射液钙注射液", "唑仑唑仑", "糖浆糖浆", "对对", "注射用硫酸注射用硫酸", "鼻喷雾剂鼻喷雾剂", };
        String[] strs4 = {"盐酸", "碳酸", "复方", "硫酸", "注射用重组人", "复方羟丙茶碱", "乳膏", "喷雾剂", "胶丸", "栓", "气雾剂", "软膏", "注射用", "胶浆", "分散片", "注射液", "溶液", "颗粒", "眼膏", "注射用重组", "胶囊", "缓释片", "片", "开塞露", "苹果酸", "口服液", "钙", "透皮贴剂", "替卡松粉吸入剂", "甲磺酸", "磷酸", "复合", "重组人", "α1b", "琥珀酸", "钠", "注射用甲磺酸", "环", "马尿酸钠注射液", "邻碘", "滴眼液", "肠溶片", "氢氯噻嗪片", "富马酸", "醋酸", "粉吸入剂", "对", "甲磺酸", "缓释胶囊", "钠胶囊", "阿莫西林", "氯化", "重组人促红素-β", "硫酸亚", "二钠", "醋酸去氨", "酒石酸", "醋酸", "注射用重组", "葡萄糖酸", "口服液", "甲噁唑片", "三", "胺乳膏", "镁肠溶片", "钠", "低", "注射用硫酸", "肠溶胶囊", "鼻喷雾剂", "注射用放线菌素D", "肾上腺素注射液", "硫酸氢", "麦角胺", "（II）", "氯化", "肠溶片", "1注射液", "软胶囊", "胶丸", "注射用盐酸", "滴剂", "双羟萘酸", "樟脑", "琥珀酸", "散", "丁二酸", "维D2", "注射用硫酸", "匹酯片", "乙酰唑胺片", "酯钠", "阿替卡因", "钠滴眼液", "地诺前列酮（", "琥珀酸", "马来酸", "枸橼酸", "水杨酸", "溶液", "平片", "硫酸丁胺", "琥珀酸钠", "胶丸", "毒", "D", "苯", "注射用氢溴酸", "肠溶片", "吗替", "七", "吸入用", "酯片", "注射用低分子量", "低分子量", "甘精", "外用片", "T细胞", "双氢", "酯注射液", "吸入用", "盐酸左", "气雾剂", "(30/70)", "贴剂", "洛匹那韦", "钡混悬液", "注射用阿莫西林钠", "钾", "输血用", "钠片", "左", "复方三维", "胶丸Ⅰ", "醑", "注射用头孢哌酮钠", "注射用盐酸", "钾片", "草酸", "氨", "葡萄糖注射液", "搽剂", "重酒石酸", "氢溴酸", "维B6乳膏", "胶体", "铋胶囊", "咖啡因片", "聚乙二醇", "注射用苯磺酸", "枸", "甲硫酸", "丁溴", "口服混悬液", "西司他丁钠", "口服溶液", "皮内注射用", "透皮贴剂", "30注射液", "蛋白片", "硝酸", "左炔诺孕酮", "氢溴酸", "重组人", "钙注射液", "唑仑", "糖浆", "对", "注射用硫酸", "鼻喷雾剂" };
        String[] strs5 = {" 喜树碱", "头孢菌素类", "他克林", "烯丙吗啡", "肝素", "地高辛酏剂", "噻替哌", "头孢唑林", "瑞芬太尼", "羧甲淀粉钠溶液", "比索洛尔", "噻吨类", "青霉素", "脑蛋白水解物注射液", "铝碳酸镁颗粒", "环孢素", "抗过敏药物", "钙通道阻滞药", "托特罗定", "硫酸镁溶液", "甲状腺激素类", "抗菌药", "乙托咪酯", "硫酸亚铁", "碘[131I]化钠", "咪哒唑仑", "诺氟沙星脚气水", "锌离子的制剂", "磺胺异嗯唑", "屋尘螨变应原制剂", "呋喃妥因钠", "伪麻黄碱", "诺氟沙星散", "乙胺丁醇", "腺苷", "氯米芬", "氯化铵", "莫沙必利", "头孢哌酮钠", "注射用盐酸地尔硫?", "贝通（己酮可可碱）", "沙丁胺醇", "盐酸奥布卡因", "长效硝酸甘油（戊四硝酯）", "七氟烷", "羟丁酸钠", "异丙肾上腺素", "钙剂", "氨基糖苷类抗生素", "黄豆苷元", "头孢布腾", "茚满二酮", "甲哌氯丙嗪", "本芴醇", "维生素Bi", "其他对肝脏有损害药", "盐酸艾司洛尔", "琥珀酸美托洛尔", "吸入用复方异丙托溴铵溶液", "舒乐安定（艾司唑仑）", "曲美他嗪", "二氢麦角碱", "保太松", "银杏", "黄体酮胶丸", "阿莫沙平", "氯化钙", "非索罗定", "氯化钠", "苯佐卡因", "泼尼松", "氯化钡", "乐衡（吉非罗齐）", "氢化可的松软膏", "乳酶生", "美洛培南", "盐酸土霉素", "甲基纤维素", "胡萝卜素", "双氢青蒿素", "依地酸钙钠", "溴新斯的明", "安定", "抗人T细胞猪免疫球蛋白", "甘草", "茵栀黄", "美法仑", "丁卡因等", "吩噻嗪类", "酒", "别嘌醇", "乳酸依沙吖啶", "酚", "硫酸阿托品眼用凝胶", "曲唑酮", "哌替啶", "锌化合物", "对乙酰氨基酚", "六甲铵", "促肾上腺皮质激素", "氯?酸钾", "氯化钾", "新霉素", "锂制剂", "伊托必利", "乙胺嗪", "求偶二醇", "艾司洛尔", "噻加宾", "氯丙咪嗪", "山莨菪碱", "厄贝沙坦和氢氯噻嗪", "米诺地尔", "磷酸铝凝胶", "枸橼酸", "蒿甲醚", "法莫替丁", "左氧氪沙星", "去甲斑蝥酸钠氯化钠注射液", "替可克肽", "光辉霉素", "喷他脒", "美喹他嗪", "琥珀酸亚铁", "三氟丙嗪", "司坦夫定", "维生素K1", "卡巴胆碱", "硫酸多黏菌素B", "双香豆素", "去羟肌苷", "噻托溴铵喷雾剂", "西洛司特", "奥扎格雷钠氯化钠注射液", "阿米卡星", "磺胺甲恶唑", "枸橼酸钠抗凝剂", "磺酰脲", "高三尖杉酯碱氯化钠注射液", "立迈青（低分子量肝素钙）", "噻嗪类利尿剂", "头孢菌素", "鞣酸等", "尿碱化剂", "皮质激素", "庆大霉素", "高三尖杉酯碱", "复方醋酸地塞米松凝胶", "硝酸酯类", "氯霉素", "格列苯脲", "氟胞嘧啶", "锂盐", "唑尼沙胺", "六甲溴胺", "门冬酰胺酶", "氢氧化铝凝胶", "营养补充剂", "磺吡酮", "哌拉西林", "盐酸肼酞嗪", "头孢克洛混悬剂", "替罗非班", "对氨水杨酸", "降血糖药", "吗多明气雾剂", "苯丙醇胺", "左醋美沙多", "三硅酸镁", "美卡拉明", "洋地黄", "血清氨基转移酶", "别嘌呤", "非去极化肌松药", "麻醉止痛药", "溴化丁基东莨菪碱", "丙米嗪", "腺苷注射液", "硫酸多黏菌素", "英利昔单抗", "可卡因", "乙酰丙嗪", "曲米帕明", "酮咯酸", "金属氧化物", "去甲肾上腺索", "丙氯拉嗪", "非尔氨脂", "噻苯唑", "异戊巴比妥钠", "氧化镁", "免疫抑制剂", "胰脂肪酶等", "依发韦仑", "皮质激素", "非尔氨酯", "奥马曲拉", "异烟胼", "泊利噻嗪", "激动仅受体的拟肾上腺素药", "钙通道阻断药", "洋地黄毒苷", "依普利酮", "氯丙嗪类", "葡萄糖酸红霉素", "齐留通", "格鲁米特", "碱性的胺", "培哚普利叔丁胺盐", "肾毒性药物", "酮色林", "甾体激素", "普兰林肽", "佐替平", "盐酸丙米嗪", "链脲霉素", "美索达嗪", "氨基糖苷类", "海索比妥", "去极化型肌松药", "依法韦恩茨", "氨普那韦", "美西林", "新生霉素钠", "氯烯雌醚", "注射用盐酸地尔硫?", "Y-氨基丁酸", "坎地沙坦酯分散片", "呋山那韦", "二盐酸奎宁", "麦考酚酸", "茚满二酮衍生物", "双胍类降糖药", "曲咪芬", "醋酸磺胺米隆", "利福布丁", "酒石酸锑钾", "氨苯喋啶", "天存注射液（己酮可可碱葡萄糖）", "异卡波肼", "钙", "替拉那韦", "碱性药物", "阿莫非尼", "大同惠达", "β-内酰胺类抗生素", "口服避孕药", "注射液", "乳酸杆菌", "溴西泮", "脱甲氯四环素", "利多格雷", "乙酰卡尼", "丙戊酸镁片加巴喷丁胶囊", "氟尿嘧啶软膏", "双氯苯咪唑（全身途径，口服凝胶）", "癸酸诺龙", "坎利酸钾", "碱式碳酸铋", "麦角碱类", "甲乙炔巴比妥", "伊沙匹隆", "氟卡尼", "苯妥因", "氟化钠", "磷酸伯氨喹", "四氯化碳", "重金属", "膦甲酸盐", "妥拉唑啉", "双膦酸盐", "纤溶酶注射液", "磺胺甲氧嗪", "抗生素", "强心苷", "镁", "环戊氯噻嗪", "镁盐等", "钙盐", "钙盐注射剂", "芝麻油", "赛克利嗪", "奥芬那君", "碘化物", "硫氯酚", "氟西丁", "胆汁酸", "激素", "食物", "洛伐他", "心电图平板运动", "谷固醇达克罗宁膜", "蒽环类抗生素", "帕瑞考昔", "蛋白质同化激素", "重组人5型腺病毒注射液", "拟交感神经药", "铁剂", "氨基苯甲酸", "盐酸丁卡因注射液（0.5%）", "决奈达隆", "多黏菌素甲磺酸钠", "喷他咪", "氯磺丁脲", "谷固醇达克罗宁", "盐酸地尔硫?", "氯琥珀胆碱", "双硫仑", "硫胺", "阿伐他汀", "强利尿药", "克列莫佛", "桶箭毒碱", "盐酸地尔硫?缓释胶囊", "异氟醚）", "磺胺地索辛", "含铝", "双碘喹啉", "曲美苄胺", "甲氯噻嗪", "氯氮?片", "肾上腺皮质激素", "阿法链道酶", "他索沙坦", "丁苯那嗪", "洁霉素", "茶碱类", "环戊噻嗪", "考来维仑", "喷他眯", "替马西泮", "溴化六烃季铵", "氮芥类", "氧化亚氮", "洋地黄类", "拉索昔芬", "依酚氯铵", "托洛沙酮", "贝美噻嗪", "胶碘酮", "铋剂", "GLP-1", "苯佐卡因糊剂", "地拉韦定", "酸化尿液药物", "盐酸普鲁卡因注射液（10%）", "匹格列酮", "甲氧西林", "四环素类等抗生素", "重组人干扰素α1b注射液", "萘普酮", "维拉必利", "希美加群", "盐酸普鲁卡因注射液（0.5%）", "地尔硫革", "双胍类", "纳豆激酶", "溶栓药", "氯噻酮", "抗凝药", "丙戊酸钠糖浆", "巴比妥类", "纳多洛尔", "饮茶", "其它降压药", "长效硝酸盐", "舒喘宁，三丁喘宁", "硫酸镁注射液", "劳卡尼", "硫酸阿托品滴眼液", "四环素类", "全身麻醉药", "安氟醚", "阿片类", "替利霉素", "甲状腺", "小儿诺氟沙星糖粉", "蒿甲醚胶丸", "屁古丁", "抗震颤麻痹药", "依他尼酸", "美拉加群", "氨甲酰胆碱", "舍吲哚", "锶盐", "磺达肝素钠", "乙基吗啡", "单克隆抗体CD3", "多黏菌素", "尿碱化", "吸着剂", "清蛋白", "醋硝香豆素", "葡萄柚汁", "沙奎那韦", "阿齐利特", "肉毒碱", "硝酸盐", "磷酸钾", "碱", "氟烷", "螺普利", "苯丙胺", "肌松药", "姜黄素", "姜黄索", "β受体拮抗剂", "金硫丁二钠", "乙酰胆碱", "利尿剂", "苄氟噻嗪", "芦米考昔", "喹诺酮类", "亚硝酸盐", "盐酸丁卡因注射液（1%）", "恩扎妥林", "氢氧化镁", "苯丙香豆素", "头孢匹林", "麦角生物碱", "酸性药物", "双水杨酯", "醋竹桃霉素", "镇痛", "吉非贝齐", "布克利嗪", "双氢麦角隐亭", "吡啶斯的明", "三环类抗抑郁药", "抗人淋巴细胞免疫球蛋白", "红霉素类", "替吉环素", "青蒿素栓", "精蛋白重组人胰岛素混合注射剂（50", "磷酸克林霉素", "索立夫定", "溴苄胺", "替拉凡星", "卤泛群", "皮质类固醇激素", "其他阴离子交换树脂", "美施康定（硫酸吗啡缓释片.）", "普鲁本辛", "注射用重组人白细胞介素-2（125ser）", "别嘌醇缓释颗粒", "苯酚以", "甲丙氮酯", "甲状腺粉", "苯丁酸氮芥纸型片", "美克利嗪", "其他降压药", "盐酸利多卡因胃镜润滑剂", "碘番酸", "抗人T-淋巴细胞兔免疫球蛋白", "铁盐", "酸性注射液", "镇痛药", "马来酸左旋氨氯地平片尼索地平", "磷酸盐", "脑多肽", "头孢噻啶", "6%右旋糖酐-40葡萄糖注射液", "矿物油", "阿尼芬净", "磺酰脲类", "司美利特", "瑞马西胺", "双硫仑等乙醛脱氢酶抑制药", "依酚氯胺", "黏菌素甲磺酸钠", "维A酯", "吡二丙胺", "盐酸地尔硫?缓释片", "组胺", "恩氟醚吸入剂", "醋磺酰脲", "前列地尔尿道栓", "盐酸羟嗪片（安他乐）", "磺胺异噁唑", "阿洛司琼", "凝血因子", "噻托溴铵粉雾剂", "胰脂肪酶", "氟尿嘧啶栓", "阿芬太尼", "丝力霉素", "奈莫必利", "卡索匹坦", "重组人p53腺病毒注射液", "复方磺胺甲嗯唑", "阿魏酸钠散", "脑下垂体促性腺激素", "多酶片", "地西泮膜", "钙离子拮抗剂", "妥拉唑林", "碘[131I]化钠胶囊", "氢氯化铝", "白陶土", "阿芬他尼", "丙卡巴肼", "琥珀氯霉素", "抗高血压药", "升麻提取物", "双氯非那胺", "艾司利卡西平", "环吡咯酮类", "N受体阻断药", "盐皮质激素", "门冬酸钾镁", "阿维莫泮", "甲苄咪唑", "糖皮质激素", "氢氧化物", "乙噻嗪", "活病毒疫苗", "罗诺西康", "磷酸盐类", "依曲韦林", "伊拉地平", "阿霉素", "环索奈德气雾剂", "碘他拉葡胺", "碘酸钾颗粒", "氟伏草胺", "抗组胺药品混和注射", "非选择性B受体拮抗药", "农吉利碱", "氨苄噻哌啶", "瑞替加滨", "雷诺嗪", "氯?酸钾胶囊", "双氢克尿噻", "右旋糖酐-40（含糖）", "核苷类似物", "甲磺丁脲", "可溶性碳酸盐", "磺胺甲二唑", "葡萄糖酸锂", "咪达那新", "胆酸螯合树脂", "盐酸地尔硫?注射液", "水杨酸类", "链佐星", "异丙醇", "头孢替坦", "碘[131I]美妥昔单抗注射液", "头孢氧哌唑", "珀酰胆碱", "香草醛", "青霉素类", "碘剂", "那屈肝素钙", "锂化物", "对氨基苯甲酸", "丙酸倍氯米松粉末吸入剂", "马来酸左旋氨氯地平片尼群地平5mg，阿替洛尔10mg", "洛伐他丁", "磺酸替奥芬", "三氯甲噻嗪", "新生霉素", "磺胺类", "他林洛尔", "佐米曲坦", "巴西昔单抗", "复方磺胺甲噁唑", "左卡尼汀", "汞撒利", "血浆", "利尿酸钠", "氯贝丁酯（安妥明）", "帕立骨化醇", "右丙氧芬", "产生呕吐反应的抗癌药", "皮质醇", "舒屁替尼", "避孕药", "抗酸药物", "盐酸地尔硫?片", "二氢奎尼丁", "麻醉药", "胆影葡胺", "水杨酸盐", "依米丁", "注射用抗肿瘤免疫核糖核酸", "铝盐", "碱性溶液", "阿昔单抗", "卡铂", "异丙酚", "阿义马林", "锂剂", "氯贝丁酯", "碳酸盐", "硫柳汞", "氮甲片", "甲氧沙林搽剂", "茶碱类药物", "抗凝血药", "抗胆碱能药物", "巴比妥类钠盐等碱性液", "呋喃苯胺酸", "乙内酰脲", "丹那唑", "丙吡胺", "M受体阻断药", "吩噻嗪", "氯?酸钾片", "巴比妥酸盐", "地拉夫定", "环孢素A", "甘草酸单胺盐", "孕激素", "门冬胰岛素", "苯巴比妥类", "乙醇", "烟草", "活性炭", "恩卡尼", "其他降血压药物", "伊伐雷定", "莫昔普利", "二硫仑（戒酒硫）", "氨苯磺胺", "磷酸二酯酶-5抑制剂", "乙醚", "安替比林", "钠型降钾交换树脂", "芬太尼衍生物", "黄铜", "单硝酸异山梨酯滴丸", "乳酸脱氢酶", "苯乙酰盐", "异氟醚", "溴", "病毒疫苗", "氯环力嗪", "皮质激素类", "碘化油注射液", "含铝药物", "普卡霉素", "镁的抗酸剂", "苯磺唑酮", "氨多索韦", "西拉非班", "氯?酸钾片", "胰蛋白酶", "筒箭毒碱", "达芦那韦", "益生菌", "抗痛风药", "苯磺酸氨氯地平滴丸", "氯化物", "考来替泊", "甘珀酸钠", "安妥明", "伯氨喹", "多非利特", "三环类", "地昔帕明", "嘌呤饮食", "阿马林", "雄性激素", "琥珀酰胆碱", "氟尿嘧啶凝胶", "甲状腺激素", "倍硫磷", "重组人尿激酶原", "萝芙木碱", "盐酸地尔硫?控释胶囊", "二巯丙醇", "碱剂", "盐酸小檗碱片（黄连素）", "安度芬（氨酚待因片[Ⅱ]）", "硫酸盐", "夸西泮", "卡巴拉汀", "奈非那韦", "屈奈达隆", "0.9%氯化钠注射液", "氯?酸钾", "甲氟喹", "匹莫齐特", "双硫磷", "D-甘露糖醇", "苯二氮卓类" };
        System.out.println("strs1.length----->"+strs1.length);
        System.out.println("strs2.length----->"+strs2.length);
        System.out.println("strs3.length----->"+strs3.length);
        System.out.println("strs4.length----->"+strs4.length);
        System.out.println("strs5.length----->"+strs5.length);
        List<List<Object>> list = new ArrayList<>();
        for(int m=0;m<listObject.size();m++){
            System.out.println("当前excel行数------》"+m);
            //获取excel每一行
            List<Object> json2List = listObject.get(m);
            List<Object> jsonLine = new ArrayList<>();
            //其余列原样输出
            for(int n = 0;n<json2List.size();n++){
                String bool = (String) json2List.get(17);
                //只处理非TRUE
                if(!"TRUE".equals(bool.trim())) {
                    boolean bools = false;
                    //循环遍历，不符合的药名删除
                    for(int a= 0;a<json2List.size();a++){
                        if(json2List.get(a).toString().contains("?") || json2List.get(a).toString().contains("？")){
                            bools = true;
                            break;
                        }
                        for(int b=0;b<strs5.length;b++){
                            if(json2List.get(a).toString().contains(strs5[b])){
                                bools = true;
                                break;
                            }
                        }
                    }
                    if(bools){
                        break;
                    }
                    //获取每个cell
                    /*if (n == 2 || n == 3) {
                        String jsonObject = (String) json2List.get(n);
                        JSONArray jsonArray = (JSONArray) JSONObject.parse(jsonObject);
                        JSONArray jsonArrays = new JSONArray();
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JSONObject json = jsonArray.getJSONObject(i);
                            String value = json.get("value").toString().replace("[", "").replace("]", "").replaceAll("\"", "");

                            for (int j = 0; j < strs1.length; j++) {
                                if (strs1[j].equals(value)) {
                                    json.put("value", strs2[j]);
                                }
                            }
                            jsonArrays.add(JSONObject.parse(json.toString()));
                        }
                        jsonLine.add(jsonArray.toString());
                        //截取药名替换
                    } else*/ if(n == 2 || n == 3 ||n==4 || n==6 || n==14){
                        String jsonObject = (String) json2List.get(n);
                        for(int i=0;i<strs1.length;i++){
                            if(jsonObject.contains(strs1[i])){
                                jsonObject = jsonObject.replace(strs1[i],strs2[i]);
                            }
                        }
                        for(int j=0;j<strs3.length;j++){
                            while(jsonObject.contains(strs3[j])){
                                jsonObject = jsonObject.replaceAll(strs3[j],strs4[j]);
                            }
                        }
                        jsonLine.add(jsonObject);
                    } else {
                        jsonLine.add(json2List.get(n));
                    }
                }else{
                    jsonLine.add(json2List.get(n));
                }
            }
            if(jsonLine.size()>0){
                list.add(jsonLine);
            }
        }
        return list;
    }

    /**
     *功能描述
     *@author swq
     *@date 2019-1-30  17:38
     *@param: idList
     *@return java.util.List<java.util.Map < java.lang.String , java.lang.Object>>
     *@desc 获取mongo中shouyezhenduan罕见病相关信息
     */
    public List<Map<String,Object>> getRareDiseaseInfo(List<Object> idList){
        List<Map<String,Object>> listArray = new ArrayList<>();
        List<Map<String,Object>> listMap = new ArrayList<>();
        //处理首页诊断
        List<Document> countPatientId = Arrays.asList(
                new Document("$unwind", "$shouyezhenduan"),
                new Document("$match", new Document("_id", new Document("$in",idList))),
                new Document("$project", new Document("_id", 1).append("patient_id", 1).append("shouyezhenduan", 1))
        );
        AggregateIterable<Document> binli = shouyezhenduan.aggregate(countPatientId);
        for (Document document : binli) {
            Map<String,Object> map = new HashMap<>();
            String id = document.getString("_id");
            String patientId = document.getString("patient_id");
            map.put("id",id);
            map.put("patientId",patientId);
            Document binglizhenduan = (Document) document.get("shouyezhenduan");
            Map<String,Object> returnMap = parseBingLiZhenDuanData(binglizhenduan,map);
            listMap.add(returnMap);
        }
        //将listMap中相同得id放一个map中
        listArray = parseMap(listMap);
        return listArray;
    }

    /**
     *功能描述
     *@author swq
     *@date 2019-1-30  17:37
     *@param: binglizhenduan
     *@param: map
     *@return java.util.Map<java.lang.String , java.lang.Object>
     *@desc 将根据shouyezhenduan分组查询后的数据放入map
     */
    private Map<String,Object> parseBingLiZhenDuanData(Document binglizhenduan,Map<String,Object> map) {
        String inName = "入院初诊";
        String outName = "出院诊断";
        if (Objects.nonNull(binglizhenduan)) {
            String diagnosisTypeName = binglizhenduan.getString("diagnosis_type_name");
            Integer diagnosisNum = Integer.valueOf(binglizhenduan.getString("diagnosis_num"));
            String diagnosisName = binglizhenduan.getString("diagnosis_name");
            String diagnosisDesc = binglizhenduan.getString("diagnosis_desc");
            String diagnosisTime = binglizhenduan.getString("diagnosis_time");
            String diagnosisCode = binglizhenduan.getString("diagnosis_code");
            if(null==diagnosisName || "".equals(diagnosisName)){
                diagnosisName = diagnosisDesc;
            }
            if(inName.equals(diagnosisTypeName) && diagnosisNum==1){
                String inDiagnosisName1 = diagnosisName;
                map.put("inDiagnosisName1",inDiagnosisName1);
                map.put("inDiagnosisTime",diagnosisTime);
            }
           if(inName.equals(diagnosisTypeName) && diagnosisNum==2){
                String inDiagnosisName2 = diagnosisName;
                map.put("inDiagnosisName2",inDiagnosisName2);
            }
           if(inName.equals(diagnosisTypeName) && diagnosisNum==3){
               String inDiagnosisName3 = diagnosisName;
                map.put("inDiagnosisName3",inDiagnosisName3);
            }
           if(inName.equals(diagnosisTypeName) && diagnosisNum==4){
               String inDiagnosisName4 = diagnosisName;
                map.put("inDiagnosisName4",inDiagnosisName4);
            }
           if(inName.equals(diagnosisTypeName) && diagnosisNum==5){
               String inDiagnosisName5 = diagnosisName;
                map.put("inDiagnosisName5",inDiagnosisName5);
            }
           if(outName.equals(diagnosisTypeName) && diagnosisNum==1){
               String outDiagnosisCode1 = diagnosisCode;
               map.put("outDiagnosisCode1",outDiagnosisCode1);
               String outDiagnosisName1 = diagnosisName;
               map.put("outDiagnosisName1",outDiagnosisName1);
               map.put("outDiagnosisTime",diagnosisTime);
            }
           if(outName.equals(diagnosisTypeName) && diagnosisNum==2){
               String outDiagnosisCode2 = diagnosisCode;
               map.put("outDiagnosisCode2",outDiagnosisCode2);
               String outDiagnosisName2 = diagnosisName;
                map.put("outDiagnosisName2",outDiagnosisName2);
            }
           if(outName.equals(diagnosisTypeName) && diagnosisNum==3){
               String outDiagnosisCode3 = diagnosisCode;
               map.put("outDiagnosisCode3",outDiagnosisCode3);
               String outDiagnosisName3 = diagnosisName;
                map.put("outDiagnosisName3",outDiagnosisName3);
            }
           if(outName.equals(diagnosisTypeName) && diagnosisNum==4){
               String outDiagnosisCode4 = diagnosisCode;
               map.put("outDiagnosisCode4",outDiagnosisCode4);
               String outDiagnosisName4 = diagnosisName;
                map.put("outDiagnosisName4",outDiagnosisName4);
            }
           if(outName.equals(diagnosisTypeName) && diagnosisNum==5){
               String outDiagnosisCode5 = diagnosisCode;
               map.put("outDiagnosisCode5",outDiagnosisCode5);
               String outDiagnosisName5 = diagnosisName;
                map.put("outDiagnosisName5",outDiagnosisName5);
            }
           if(outName.equals(diagnosisTypeName) && diagnosisNum==6){
               String outDiagnosisCode6 = diagnosisCode;
               map.put("outDiagnosisCode6",outDiagnosisCode6);
               String outDiagnosisName6 = diagnosisName;
                map.put("outDiagnosisName6",outDiagnosisName6);
            }
           if(outName.equals(diagnosisTypeName) && diagnosisNum==7){
               String outDiagnosisCode7 = diagnosisCode;
               map.put("outDiagnosisCode7",outDiagnosisCode7);
               String outDiagnosisName7 = diagnosisName;
                map.put("outDiagnosisName7",outDiagnosisName7);
            }
           if(outName.equals(diagnosisTypeName) && diagnosisNum==8){
               String outDiagnosisCode8 = diagnosisCode;
               map.put("outDiagnosisCode8",outDiagnosisCode8);
               String  outDiagnosisName8 = diagnosisName;
                map.put("outDiagnosisName8",outDiagnosisName8);
            }
           if(outName.equals(diagnosisTypeName) && diagnosisNum==9){
               String outDiagnosisCode9 = diagnosisCode;
               map.put("outDiagnosisCode9",outDiagnosisCode9);
               String  outDiagnosisName9 = diagnosisName;
                map.put("outDiagnosisName9",outDiagnosisName9);
            }
           if(outName.equals(diagnosisTypeName) && diagnosisNum==10){
               String outDiagnosisCode10 = diagnosisCode;
               map.put("outDiagnosisCode10",outDiagnosisCode10);
               String  outDiagnosisName10 = diagnosisName;
               map.put("outDiagnosisName10",outDiagnosisName10);
            }
        }
        return map;
    }

    /**
     *功能描述
     *@author swq
     *@date 2019-1-30  17:38
     *@param: lists
     *@return java.util.List<java.util.Map < java.lang.String , java.lang.Object>>
     *@desc 将list集合中的map中id相同的集合合并
     */
    private List<Map<String,Object>> parseMap(List<Map<String,Object>> lists){
        for (int i = 0; i < lists.size() ;i++){
            Map map1 = lists.get(i);
            for (int j = i + 1; j < lists.size();){
                Map<String,Object> map2 = lists.get(j);
                if (map1.get("id").equals(map2.get("id"))){
                    for (Map.Entry entry : map2.entrySet()) {
                        //即使map2中有map1中相同的key，他们的value也一样，直接替换
                        map1.put(entry.getKey(),entry.getValue());
                    }
                    //删除已合并的map避免重复比较
                    lists.remove(j);
                    continue;
                }
                j++;
            }
        }
        return lists;
    }
}

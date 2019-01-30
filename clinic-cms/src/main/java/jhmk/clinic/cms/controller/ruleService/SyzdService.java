package jhmk.clinic.cms.controller.ruleService;

import com.alibaba.fastjson.JSONArray;
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
        String[] strs1 = {"羟丁酸钠","比沙可啶","福辛普利钠","吲达帕胺","螺内酯"};
        String[] strs2 = {"羟丁酸钠注射液","比沙可啶片","福辛普利钠片","吲达帕胺片","螺内酯片"};
        String[] strs3 = {"羟丁酸钠注射液","比沙可啶片","福辛普利钠片","吲达帕胺片","螺内酯片"};
        String[] strs4 = {"羟丁酸钠注射液","比沙可啶片","福辛普利钠片","吲达帕胺片","螺内酯片"};
        List<List<Object>> list = new ArrayList<>();
        for(int m=0;m<listObject.size();m++){
            //获取excel每一行
            List<Object> json2List = listObject.get(m);
            List<Object> jsonLine = new ArrayList<>();
            //其余列原样输出
            for(int n = 0;n<json2List.size();n++){
                String bool = (String) json2List.get(17);
                //只处理非TRUE
                if(!"TRUE".equals(bool.trim())) {
                    boolean bools = false;
                    for(int a= 0;a<json2List.size();a++){
                        if(json2List.get(a).toString().contains("?") || json2List.get(a).toString().contains("？")){
                            bools = true;
                            break;
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
                        if (entry.getKey().equals("id")){
                            //不保存ID
                            continue;
                        }
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

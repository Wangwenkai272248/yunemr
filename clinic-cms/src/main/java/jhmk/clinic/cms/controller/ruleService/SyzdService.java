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
}

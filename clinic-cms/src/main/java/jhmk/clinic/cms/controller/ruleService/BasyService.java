package jhmk.clinic.cms.controller.ruleService;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import jhmk.clinic.cms.service.ReadFileService;
import jhmk.clinic.core.config.CdssConstans;
import jhmk.clinic.entity.bean.Binganshouye;
import jhmk.clinic.entity.bean.Misdiagnosis;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.*;

import static jhmk.clinic.core.util.MongoUtils.getCollection;

/**
 * @author ziyu.zhou
 * @date 2018/7/31 14:24
 */
@Service
public class BasyService {
    MongoCollection<Document> binganshouye = getCollection(CdssConstans.DATASOURCE, CdssConstans.BINGANSHOUYE);

    /**
     * 获取所有骨科信息
     *
     * @return
     */
    public List<Misdiagnosis> getGukeData() {
        List<Misdiagnosis> misdiagnosisList = new LinkedList<>();
        List<Document> countPatientId = Arrays.asList(
                new Document("$project", new Document("patient_id", 1).append("_id", 1).append("visit_id", 1).append("binganshouye", 1))
//                , new Document("$skip", 5000),
//                new Document("$limit", 10000)
        );
        AggregateIterable<Document> output = binganshouye.aggregate(countPatientId);
        for (Document document : output) {
            Misdiagnosis misdiagnosis = new Misdiagnosis();
            if (document == null) {
                continue;
            }
            misdiagnosis.setPatient_id(document.getString("patient_id"));
            misdiagnosis.setVisit_id(document.getString("visit_id"));
            misdiagnosis.setId(document.getString("_id"));
            Document binganshouye = (Document) document.get("binganshouye");
            Document patVisit = (Document) binganshouye.get("pat_visit");

            misdiagnosis.setDept_discharge_from_name(patVisit.getString("dept_admission_to_name"));
            misdiagnosis.setDistrict_discharge_from_name(patVisit.getString("district_discharge_from_name"));
            if (patVisit.getString("dept_admission_to_name").contains("骨科")) {
                misdiagnosisList.add(misdiagnosis);
            }
        }
        return misdiagnosisList;
    }

    public List<Misdiagnosis> getDataByDept(String deptName) {
        List<Misdiagnosis> misdiagnosisList = new LinkedList<>();
        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("binganshouye.pat_visit.dept_discharge_from_name", deptName)),
                new Document("$project", new Document("patient_id", 1).append("_id", 1).append("visit_id", 1).append("binganshouye", 1))
//                , new Document("$skip", 5000),
//                new Document("$limit", 10000)
        );
        AggregateIterable<Document> output = binganshouye.aggregate(countPatientId);
        for (Document document : output) {
            Misdiagnosis misdiagnosis = new Misdiagnosis();
            if (document == null) {
                continue;
            }
            misdiagnosis.setPatient_id(document.getString("patient_id"));
            misdiagnosis.setVisit_id(document.getString("visit_id"));
            misdiagnosis.setId(document.getString("_id"));
            Document binganshouye = (Document) document.get("binganshouye");
            Document patVisit = (Document) binganshouye.get("pat_visit");

            misdiagnosis.setDept_discharge_from_name(patVisit.getString("dept_admission_to_name"));
            //出院时间
            String discharge_time = patVisit.getString("discharge_time");
            if ("2016".equals(discharge_time.substring(0, 4))) {
                misdiagnosisList.add(misdiagnosis);
            } else {
                continue;
            }
        }
        return misdiagnosisList;
    }

    /**
     * 获取入院时间
     *
     * @param id
     * @return
     */
    public String getAdmissionTime(String id) {
        List<Misdiagnosis> misdiagnosisList = new LinkedList<>();
        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("patient_id", 1).append("_id", 1).append("visit_id", 1).append("binganshouye", 1))
//                , new Document("$skip", 5000),
//                new Document("$limit", 10000)
        );
        AggregateIterable<Document> output = binganshouye.aggregate(countPatientId);
        for (Document document : output) {
            Misdiagnosis misdiagnosis = new Misdiagnosis();
            if (document == null) {
                continue;
            }
            misdiagnosis.setPatient_id(document.getString("patient_id"));
            misdiagnosis.setVisit_id(document.getString("visit_id"));
            misdiagnosis.setId(document.getString("_id"));
            Document binganshouye = (Document) document.get("binganshouye");
            Document patVisit = (Document) binganshouye.get("pat_visit");

            String admission_time = patVisit.getString("admission_time");
            return admission_time;
        }
        return null;
    }

    public String getDischargeTime(String id) {
        List<Misdiagnosis> misdiagnosisList = new LinkedList<>();
        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("patient_id", 1).append("_id", 1).append("visit_id", 1).append("binganshouye", 1))
//                , new Document("$skip", 5000),
//                new Document("$limit", 10000)
        );
        AggregateIterable<Document> output = binganshouye.aggregate(countPatientId);
        for (Document document : output) {
            Misdiagnosis misdiagnosis = new Misdiagnosis();
            if (document == null) {
                continue;
            }
            misdiagnosis.setPatient_id(document.getString("patient_id"));
            misdiagnosis.setVisit_id(document.getString("visit_id"));
            misdiagnosis.setId(document.getString("_id"));
            Document binganshouye = (Document) document.get("binganshouye");
            Document patVisit = (Document) binganshouye.get("pat_visit");

            String discharge_time = patVisit.getString("discharge_time");
            return discharge_time;
        }
        return null;
    }

    public Binganshouye getBeanById(String id) {
        List<Misdiagnosis> misdiagnosisList = new LinkedList<>();
        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("patient_id", 1).append("_id", 1).append("visit_id", 1).append("binganshouye", 1))
//                , new Document("$skip", 5000),
//                new Document("$limit", 10000)
        );
        AggregateIterable<Document> output = binganshouye.aggregate(countPatientId);
        for (Document document : output) {
            Binganshouye misdiagnosis = new Binganshouye();
            if (document == null) {
                continue;
            }
            misdiagnosis.setPatient_id(document.getString("patient_id"));
            misdiagnosis.setVisit_id(document.getString("visit_id"));
            misdiagnosis.setId(document.getString("_id"));
            Document binganshouye = (Document) document.get("binganshouye");
            Document patVisit = (Document) binganshouye.get("pat_visit");

            String admission_time = patVisit.getString("admission_time");
            misdiagnosis.setAdmission_time(admission_time);
            String discharge_time = patVisit.getString("discharge_time");
            misdiagnosis.setDischarge_time(discharge_time);
            String district_discharge_from_name = patVisit.getString("district_discharge_from_name");
            misdiagnosis.setPat_visit_dept_discharge_from_name(district_discharge_from_name);
            String district_admission_to_name = patVisit.getString("district_admission_to_name");
            misdiagnosis.setPat_visit_dept_admission_to_name(district_admission_to_name);
            String dept_admission_to_name = patVisit.getString("dept_admission_to_name");
            misdiagnosis.setDept_admission_to_name(dept_admission_to_name);
            return misdiagnosis;
        }
        return null;
    }

    public Binganshouye getBeanByPidAndVid(String id) {

        List<Misdiagnosis> misdiagnosisList = new LinkedList<>();
        String[] split = id.split(",");
        String pid = split[0];
        String vid = split[1];
        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("patient_id", pid)),
                new Document("$match", new Document("visit_id", vid)),
                new Document("$project", new Document("patient_id", 1).append("_id", 1).append("visit_id", 1).append("binganshouye", 1))
//                , new Document("$skip", 5000),
//                new Document("$limit", 10000)
        );
        AggregateIterable<Document> output = binganshouye.aggregate(countPatientId);
        for (Document document : output) {
            Binganshouye misdiagnosis = new Binganshouye();
            if (document == null) {
                continue;
            }
            misdiagnosis.setPatient_id(document.getString("patient_id"));
            misdiagnosis.setVisit_id(document.getString("visit_id"));
            misdiagnosis.setId(document.getString("_id"));
            Document binganshouye = (Document) document.get("binganshouye");
            Document patVisit = (Document) binganshouye.get("pat_visit");

            String admission_time = patVisit.getString("admission_time");
            misdiagnosis.setAdmission_time(admission_time);
            String discharge_time = patVisit.getString("discharge_time");
            misdiagnosis.setDischarge_time(discharge_time);
            String district_discharge_from_name = patVisit.getString("district_discharge_from_name");
            misdiagnosis.setPat_visit_dept_discharge_from_name(district_discharge_from_name);
            String district_admission_to_name = patVisit.getString("district_admission_to_name");
            misdiagnosis.setPat_visit_dept_admission_to_name(district_admission_to_name);
            String dept_admission_from_name = patVisit.getString("dept_admission_from_name");
            misdiagnosis.setPat_visit_dept_discharge_from_name(dept_admission_from_name);
            String dept_admission_to_name = patVisit.getString("dept_admission_to_name");
            misdiagnosis.setPat_visit_dept_admission_to_name(dept_admission_to_name);
            misdiagnosis.setDept_admission_to_name(dept_admission_to_name);
            return misdiagnosis;
        }
        return null;
    }

    public Set<String> getAllDepts() {
        Set<String> names = new HashSet<>();
        List<Document> countPatientId = Arrays.asList(
                new Document("$project", new Document("patient_id", 1).append("_id", 1).append("visit_id", 1).append("binganshouye", 1))
                , new Document("$skip", CdssConstans.BEGINCOUNT),
                new Document("$limit", CdssConstans.ENDCOUNT)
        );
        AggregateIterable<Document> output = binganshouye.aggregate(countPatientId);
        for (Document document : output) {
            if (document == null) {
                continue;
            }
            Document binganshouye = (Document) document.get("binganshouye");
            Document patVisit = (Document) binganshouye.get("pat_visit");

            names.add(patVisit.getString("dept_admission_to_name"));
        }
        return names;
    }

    /**
     * 获取出院时间是2017年的id
     *
     * @return
     */
    public List<String> getAllIdByDate(String date) {
        List<String> misdiagnosisList = new LinkedList<>();
        Set<String> dept = getDept("departmentMap", "呼吸科");
        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("binganshouye.pat_visit.discharge_time", new Document("$gte", "2016-01-01 00:00:00"))),
                new Document("$match", new Document("binganshouye.pat_visit.discharge_time", new Document("$lt", "2017-01-01 00:00:00"))),
                new Document("$project", new Document("patient_id", 1).append("_id", 1).append("visit_id", 1).append("binganshouye", 1))
        );
        AggregateIterable<Document> output = binganshouye.aggregate(countPatientId);
        for (Document document : output) {
            String id = document.getString("_id");
            Document binganshouye = (Document) document.get("binganshouye");
            Document patVisit = (Document) binganshouye.get("pat_visit");
            String dept_discharge_from_name = patVisit.getString("dept_discharge_from_name");
            if (dept.contains(dept_discharge_from_name)) {
                misdiagnosisList.add(id);
            }
        }
        return misdiagnosisList;
    }

    public List<String> getAllIdByDate1(String date) {
        Set<String> dept = getDept("departmentMap", "呼吸科");
        System.out.println("部门总数量" + dept.size());
        List<String> misdiagnosisList = new LinkedList<>();
        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("binganshouye.pat_visit.discharge_time", new Document("$gte", "2017-01-01 00:00:00"))),
                new Document("$match", new Document("binganshouye.pat_visit.discharge_time", new Document("$lt", "2018-01-01 00:00:00"))),
                new Document("$project", new Document("patient_id", 1).append("_id", 1).append("visit_id", 1).append("binganshouye", 1))
        );
        AggregateIterable<Document> output = binganshouye.aggregate(countPatientId);
        for (Document document : output) {
            String id = document.getString("_id");
            Document binganshouye = (Document) document.get("binganshouye");
            Document patVisit = (Document) binganshouye.get("pat_visit");
            String discharge_time = patVisit.getString("discharge_time");
            String dept_discharge_from_name = patVisit.getString("dept_discharge_from_name");
            if (dept.contains(dept_discharge_from_name)) {
                misdiagnosisList.add(id);
            }

        }
        return misdiagnosisList;
    }

    /**
     * @param deptName  部门名称
     * @param startTime 入院开始时间
     * @param endTime   入院结束
     * @return 查询科室部门 =deptName 入院时间在范围内的id
     */
    public List<String> getIdsByDeptAndTime(String deptName, String startTime, String endTime) {
        List<String> misdiagnosisList = new LinkedList<>();
        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("binganshouye.pat_visit.dept_admission_to_name", deptName)),
                new Document("$match", new Document("binganshouye.pat_visit.discharge_time", new Document("$gte", startTime))),
                new Document("$match", new Document("binganshouye.pat_visit.discharge_time", new Document("$lt", endTime))),
                new Document("$project", new Document("patient_id", 1).append("_id", 1).append("visit_id", 1).append("binganshouye", 1))
        );
        AggregateIterable<Document> output = binganshouye.aggregate(countPatientId);
        for (Document document : output) {
            String id = document.getString("_id");
            misdiagnosisList.add(id);
        }
        return misdiagnosisList;
    }


    public Set<String> getDept(String filename, String deptname) {
        Set<String> list = ReadFileService.readSource(filename);
        Set<String> set = new HashSet<>();
        for (String s : list) {
            if (s.contains(deptname)) {
                String[] split = s.split(",");
                set.addAll(Arrays.asList(split));
            }
        }
        return set;
    }

    public List<String> getDataByNoDept(String deptName) {
        List<String> idsList = new ArrayList<>();
        AggregateIterable<Document> output = binganshouye.aggregate(Arrays.asList());
        for (Document document : output) {
            String id = document.getString("_id");
            Document binganshouye = (Document) document.get("binganshouye");
            Document patVisit = (Document) binganshouye.get("pat_visit");
            String discharge_time = patVisit.getString("dept_discharge_from_name");
            String dept_discharge_from_name = patVisit.getString("dept_discharge_from_name");
            if (deptName.equals(dept_discharge_from_name)) {
                idsList.add(id);
            }
        }
        return idsList;
    }

}

//package jhmk.clinic.cms.controller.testController;
//
//import com.alibaba.fastjson.JSONObject;
//import jhmk.clinic.cms.SamilarService;
//import jhmk.clinic.cms.controller.ruleService.*;
//import jhmk.clinic.cms.service.ReadFileService;
//import jhmk.clinic.cms.service.Write2File;
//import jhmk.clinic.core.base.BaseController;
//import jhmk.clinic.core.util.DateFormatUtil;
//import jhmk.clinic.entity.bean.Misdiagnosis;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.*;
//
///**
// * @author ziyu.zhou
// * @date 2018/9/3 9:39
// */
//@Controller
//@RequestMapping("/test/huxike")
//public class HuxikeVCOntroller extends BaseController {
//
//    @Autowired
//    SamilarService samilarService;
//    @Autowired
//    BasyService basyService;
//    @Autowired
//    RyjuService ryjuService;
//    @Autowired
//    BlzdService blzdService;
//    @Autowired
//    SjyscflService sjyscflService;
//    @Autowired
//    SyzdService syzdService;
//
//    @PostMapping("/getHuxikeData")
//    public void getGukedata(HttpServletResponse response) {
//        List<Misdiagnosis> gukeData = basyService.getDataByDept("呼吸科");
//        System.out.println("呼吸科总数量为：===========================" + gukeData.size());
//        Set<Misdiagnosis> saveData = new HashSet<>();
//        for (Misdiagnosis bean : gukeData) {
//            String id = bean.getId();
//
//            //获取出院诊断集合
//            String shouyemainDisease = syzdService.getMainDisease(id);
//            bean.setCymainIllName(shouyemainDisease);
//            String mainDisease = syzdService.getRycz(id);
//            bean.setRymainIllName(mainDisease);
//            saveData.add(bean);
//        }
//        BufferedWriter bufferedWriter = null;
//        File file = new File("/data/1/CDSS/3院呼吸科主诊断数量.txt");
////        File file = new File("C:/嘉和美康文档/3院测试数据/3院呼吸科主诊断数量.txt");
//        if (!file.exists()) {
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        try {
//            bufferedWriter = new BufferedWriter(new FileWriter(file));
//            for (Misdiagnosis mz : saveData) {
//
//                bufferedWriter.write(mz.getId() + "," + mz.getCymainIllName() + "," + mz.getRymainIllName());
//
//                bufferedWriter.newLine();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                bufferedWriter.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        wirte(response, "导入文件成功");
//    }
//
//
//    @PostMapping("/getAllDate")
//    public void getAllChuyuanData(HttpServletResponse response) {
//        List<String> resulrList = new ArrayList<>();
//        String date = "2016";
//        List<String> allIdByDate = basyService.getAllIdByDate(date);
//        System.out.println(date + "年出院总数为：" + allIdByDate.size());
//        for (String id : allIdByDate) {
//            String chuyuan = syzdService.getMainDisease(id);
//            String ruyuan = syzdService.getRycz(id);
//            resulrList.add(id + "/" + ruyuan + "/" + chuyuan);
//        }
//        Write2File.w2fileList(resulrList, "/data/1/CDSS/" + date + "年呼吸科数据.txt");
//    }
//
//    @PostMapping("/getAllDate1")
//    public void getAllChuyuanData1(HttpServletResponse response) {
//        List<String> resulrList = new ArrayList<>();
//        String date = "2017";
//        List<String> allIdByDate = basyService.getAllIdByDate1(date);
//        System.out.println(date + "年出院总数为：" + allIdByDate.size());
//        for (String id : allIdByDate) {
//            String chuyuan = syzdService.getMainDisease(id);
//            String ruyuan = syzdService.getRycz(id);
//            resulrList.add(id + "/" + ruyuan + "/" + chuyuan);
//        }
//        Write2File.w2fileList(resulrList, "/data/1/CDSS/" + date + "年呼吸科数据.txt");
//    }
//
//    @PostMapping("/getCFDataBy2016")
//    public void getCFData() {
//        Set<String> ids = ReadFileService.readSource("ids2016");
//        List<String> list = new ArrayList<>();
//        for (String id : ids) {
//            String rycz = syzdService.getRycz(id);
//            String mainDisease = syzdService.getMainDisease(id);
//            //获取此疾病所有上疾病 下疾病 同义词
//            List<String> allIllNames = samilarService.getSamilarWord(mainDisease);
//            Map.Entry<String, String> sjyscfl = sjyscflService.getSJYSCFL(id, allIllNames);
//            Misdiagnosis sjyscfl1 = sjyscflService.getSJYSCFL(id);
//
//
//            String admissionTime = basyService.getAdmissionTime(id);
//            StringBuilder sb = new StringBuilder();
//            sb.append(id).append("/");
//            long i = 0;
//            if (sjyscfl != null) {
//                //上级医师查房确认疾病名
//                String key = sjyscfl.getKey();
//                //确认时间
//                String value = sjyscfl.getValue();
//                sb.append(key).append("/");
//                sb.append(value).append("/");
//                if (admissionTime != null && value != null) {
//                    Date date = DateFormatUtil.parseDate(value, DateFormatUtil.DATETIME_PATTERN_SS);
//                    Date date1 = DateFormatUtil.parseDate(admissionTime, DateFormatUtil.DATETIME_PATTERN_SS);
//                    i = DateFormatUtil.dateDiff(date, date1);
//                }
//
//            } else {
//                sb.append("null").append("/");
//                sb.append("null").append("/");
//                continue;
//            }
//            sb.append(rycz).append("/");
//            sb.append(mainDisease).append("/");
//            sb.append(admissionTime).append("/");
//            sb.append(i).append("/");
//            sb.append(JSONObject.toJSONString(sjyscfl1)).append("/");
//            list.add(sb.toString());
//        }
////        Write2File.w2fileList(list, "/data/1/CDSS/2016年呼吸科确诊数据.txt");
//        Write2File.w2fileList(list, "2016年呼吸科确诊数据.txt");
//    }
//
//    @PostMapping("/getCFDataBy2017")
//    public void getCFDataBy2017() {
//        Set<String> ids = ReadFileService.readSource("ids2017");
//        List<String> list = new ArrayList<>();
//        for (String id : ids) {
//            String rycz = syzdService.getRycz(id);
//            String mainDisease = syzdService.getMainDisease(id);
//            //获取此疾病所有上疾病 下疾病 同义词
//            Set<String> allIllNames = samilarService.getAllIllNames(mainDisease);
//            Map.Entry<String, String> sjyscfl = sjyscflService.getSJYSCFL(id, allIllNames);
//            Misdiagnosis sjyscfl1 = sjyscflService.getSJYSCFL(id);
//
//
//            String admissionTime = basyService.getAdmissionTime(id);
//            StringBuilder sb = new StringBuilder();
//            sb.append(id).append("/");
//            long i = 0;
//
//            if (sjyscfl != null) {
//                //上级医师查房确认疾病名
//                String key = sjyscfl.getKey();
//                //确认时间
//                String value = sjyscfl.getValue();
//                sb.append(key).append("/");
//                sb.append(value).append("/");
//                if (admissionTime != null && value != null) {
//                    Date date = DateFormatUtil.parseDate(value, DateFormatUtil.DATETIME_PATTERN_SS);
//                    Date date1 = DateFormatUtil.parseDate(admissionTime, DateFormatUtil.DATETIME_PATTERN_SS);
//                    i = DateFormatUtil.dateDiff(date, date1);
//                }
//            } else {
//                sb.append("null").append("/");
//                sb.append("null").append("/");
//                continue;
//            }
//            sb.append(rycz).append("/");
//            sb.append(mainDisease).append("/");
//            sb.append(admissionTime).append("/");
//            sb.append(i).append("/");
//            sb.append(JSONObject.toJSONString(sjyscfl1)).append("/");
//            list.add(sb.toString());
//        }
////        Write2File.w2fileList(list, "/data/1/CDSS/2017年呼吸科确诊数据.txt");
//        Write2File.w2fileList(list, "2017年呼吸科确诊数据.txt");
//    }
//}

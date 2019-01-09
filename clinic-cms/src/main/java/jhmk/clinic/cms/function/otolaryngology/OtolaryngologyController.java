//package jhmk.clinic.cms.function.otolaryngology;
//
//import jhmk.clinic.cms.controller.ruleService.MzzdService;
//import jhmk.clinic.core.base.BaseController;
//import jhmk.clinic.core.config.CdssConstans;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author ziyu.zhou
// * @date 2018/8/7 13:40
// */
//
//@Controller
//@RequestMapping("/test")
//public class OtolaryngologyController extends BaseController {
//
//    @Autowired
//    MzzdService menzhenzhenduanService;
//
//
//    @RequestMapping("/getOtolaryngologyCount")
//    @ResponseBody
//    public void getOtolaryngologydata(HttpServletResponse response) {
//        List<String> list = menzhenzhenduanService.get("耳鼻喉科门诊");
//        Map<String,Integer> menzhenzhenduanByIdList = menzhenzhenduanService.getMenzhenzhenduanByIdList(list);
//        BufferedWriter bufferedWriter = null;
////        File file = new File("/data/1/CDSS/3院骨科漏诊数据.txt");
//        File file = new File(CdssConstans.DEVURL+"3院耳鼻喉科门诊数量.txt");
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
//            for (Map.Entry<String, Integer> entry : menzhenzhenduanByIdList.entrySet()) {
//                bufferedWriter.write(entry.getKey() + "," + entry.getValue());
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
//    @RequestMapping("/getOtolaryngologyDiagnosisCount")
//    @ResponseBody
//    public void getOtolaryngologyDiagnosisCount(HttpServletResponse response) {
//        List<String> list = menzhenzhenduanService.get("耳鼻喉科门诊");
//        Map<String, Integer> menzhenzhenduanByIdList = menzhenzhenduanService.getDiagnosisCountByIdList(list);
//
//        BufferedWriter bufferedWriter = null;
////        File file = new File("/data/1/CDSS/3院骨科漏诊数据.txt");
//        File file = new File(CdssConstans.DEVURL+"/3院耳鼻喉科门诊诊断数量.txt");
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
//            for (Map.Entry<String, Integer> entry : menzhenzhenduanByIdList.entrySet()) {
//                bufferedWriter.write(entry.getKey() + "," + entry.getValue());
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
//}

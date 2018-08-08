package jhmk.clinic.cms.controller.cdss;

import jhmk.clinic.cms.controller.ruleService.BasyService;
import jhmk.clinic.cms.controller.ruleService.RyjuService;
import jhmk.clinic.cms.controller.ruleService.SyzdService;
import jhmk.clinic.core.base.BaseController;
import jhmk.clinic.entity.bean.Misdiagnosis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ziyu.zhou
 * @date 2018/7/31 14:42
 */
//获取测试数据相关
@Controller
@RequestMapping("/test/data")
public class DataController  extends BaseController {
    @Autowired
    BasyService basyService;
    @Autowired
    RyjuService ryjuService;
    @Autowired
    SyzdService syzdService;

    @RequestMapping("/getGukedata")
    @ResponseBody
    public void getGukedata(HttpServletResponse response) {
        List<Misdiagnosis> gukeData = basyService.getGukeData();
        Set<Misdiagnosis> saveData = new HashSet<>();
        for (Misdiagnosis bean : gukeData) {
            String id = bean.getId();
            //获取既往史存在疾病
            Misdiagnosis jwSdieases = ryjuService.getJWSdieases(id);
            if (jwSdieases==null||jwSdieases.getHisDiseaseList()==null||jwSdieases.getHisDiseaseList().size()==0){
                continue;
            }
            //获取出院诊断集合
            List<String> syzd = syzdService.getDiseaseList(id);
            List<String> hisDiseaseList = jwSdieases.getHisDiseaseList();
            //遍历既往史存在疾病 如果首页诊断不存在 则保存
            for (String s : hisDiseaseList) {
                if (!syzd.contains(s)) {
                    bean.setNowDiseaseList(hisDiseaseList);
                    saveData.add(bean);
                    continue;
                }
            }

        }
        BufferedWriter bufferedWriter = null;
//        File file = new File("/data/1/CDSS/3院骨科漏诊数据.txt");
        File file = new File("C:/嘉和美康文档/3院测试数据/3院骨科漏诊数据.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (Misdiagnosis  mz : saveData) {

                bufferedWriter.write(mz.getId()+","+mz.getPatient_id()+","+mz.getVisit_id()
                +mz.getDept_discharge_from_name()+","
                +mz.getDistrict_discharge_from_name()+","
                +mz.getHisDiseaseList()+","
                +mz.getNowDiseaseList()+","
                +mz.getSrc()
                );

                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        wirte(response,"导入文件成功");
    }

    @RequestMapping("/getOtolaryngologydata")
    @ResponseBody
    public void getOtolaryngologydata(HttpServletResponse response) {
        List<Misdiagnosis> gukeData = basyService.getGukeData();
        Set<Misdiagnosis> saveData = new HashSet<>();
        for (Misdiagnosis bean : gukeData) {
            String id = bean.getId();
            //获取既往史存在疾病
            Misdiagnosis jwSdieases = ryjuService.getJWSdieases(id);
            if (jwSdieases==null||jwSdieases.getHisDiseaseList()==null||jwSdieases.getHisDiseaseList().size()==0){
                continue;
            }
            //获取出院诊断集合
            List<String> syzd = syzdService.getDiseaseList(id);
            List<String> hisDiseaseList = jwSdieases.getHisDiseaseList();
            //遍历既往史存在疾病 如果首页诊断不存在 则保存
            for (String s : hisDiseaseList) {
                if (!syzd.contains(s)) {
                    bean.setNowDiseaseList(hisDiseaseList);
                    saveData.add(bean);
                    continue;
                }
            }

        }
        BufferedWriter bufferedWriter = null;
//        File file = new File("/data/1/CDSS/3院骨科漏诊数据.txt");
        File file = new File("C:/嘉和美康文档/3院测试数据/3院骨科漏诊数据.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (Misdiagnosis  mz : saveData) {

                bufferedWriter.write(mz.getId()+","+mz.getPatient_id()+","+mz.getVisit_id()
                +mz.getDept_discharge_from_name()+","
                +mz.getDistrict_discharge_from_name()+","
                +mz.getHisDiseaseList()+","
                +mz.getNowDiseaseList()+","
                +mz.getSrc()
                );

                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        wirte(response,"导入文件成功");
    }

}

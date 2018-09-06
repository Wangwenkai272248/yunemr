package jhmk.clinic.cms.controller.testController;

import jhmk.clinic.cms.controller.ruleService.BasyService;
import jhmk.clinic.cms.controller.ruleService.BlzdService;
import jhmk.clinic.cms.controller.ruleService.RyjuService;
import jhmk.clinic.cms.controller.ruleService.SyzdService;
import jhmk.clinic.cms.service.Write2File;
import jhmk.clinic.core.base.BaseController;
import jhmk.clinic.entity.bean.Misdiagnosis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ziyu.zhou
 * @date 2018/9/3 9:39
 */
@Controller
@RequestMapping("/test/huxike")
public class HuxikeVCOntroller extends BaseController {
    @Autowired
    BasyService basyService;
    @Autowired
    RyjuService ryjuService;
    @Autowired
    BlzdService blzdService;
    @Autowired
    SyzdService syzdService;

    @PostMapping("/getHuxikeData")
    public void getGukedata(HttpServletResponse response) {
        List<Misdiagnosis> gukeData = basyService.getDataByDept("呼吸科");
        System.out.println("呼吸科总数量为：===========================" + gukeData.size());
        Set<Misdiagnosis> saveData = new HashSet<>();
        for (Misdiagnosis bean : gukeData) {
            String id = bean.getId();

            //获取出院诊断集合
            String shouyemainDisease = syzdService.getMainDisease(id);
            bean.setCymainIllName(shouyemainDisease);
            String mainDisease = syzdService.getRycz(id);
            bean.setRymainIllName(mainDisease);
            saveData.add(bean);
        }
        BufferedWriter bufferedWriter = null;
        File file = new File("/data/1/CDSS/3院呼吸科主诊断数量.txt");
//        File file = new File("C:/嘉和美康文档/3院测试数据/3院呼吸科主诊断数量.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (Misdiagnosis mz : saveData) {

                bufferedWriter.write(mz.getId() + "," + mz.getCymainIllName() + "," + mz.getRymainIllName());

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
        wirte(response, "导入文件成功");
    }


    @PostMapping("/getAllDate")
    public void getAllChuyuanData(HttpServletResponse response) {
        List<String> resulrList = new ArrayList<>();
        String date = "2016";
        List<String> allIdByDate = basyService.getAllIdByDate(date);
        System.out.println(date + "年出院总数为：" + allIdByDate.size());
        for (String id : allIdByDate) {
            String chuyuan = syzdService.getMainDisease(id);
            String ruyuan = syzdService.getRycz(id);
            resulrList.add(id + "/" + ruyuan + "/" + chuyuan);
        }
        Write2File.w2fileList(resulrList, "/data/1/CDSS/" + date + "年呼吸科数据.txt");
    }

    @PostMapping("/getAllDate1")
    public void getAllChuyuanData1(HttpServletResponse response) {
        List<String> resulrList = new ArrayList<>();
        String date = "2017";
        List<String> allIdByDate = basyService.getAllIdByDate1(date);
        System.out.println(date + "年出院总数为：" + allIdByDate.size());
        for (String id : allIdByDate) {
            String chuyuan = syzdService.getMainDisease(id);
            String ruyuan = syzdService.getRycz(id);
            resulrList.add(id + "/" + ruyuan + "/" + chuyuan);
        }
        Write2File.w2fileList(resulrList, "/data/1/CDSS/" + date + "年呼吸科数据.txt");
    }
}

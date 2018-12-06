package jhmk.clinic.cms.controller.cdss;

import com.alibaba.fastjson.JSONObject;
import jhmk.clinic.cms.controller.ruleService.BasyService;
import jhmk.clinic.cms.controller.ruleService.RyjuService;
import jhmk.clinic.cms.controller.ruleService.SyzdService;
import jhmk.clinic.cms.service.CdssService;
import jhmk.clinic.core.base.BaseController;
import jhmk.clinic.core.config.CdssConstans;
import jhmk.clinic.core.util.HttpClient;
import jhmk.clinic.entity.bean.Misdiagnosis;
import jhmk.clinic.entity.cdss.CdssDiffBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @author ziyu.zhou
 * @date 2018/7/31 14:42
 */
//获取测试数据相关
@Controller
@RequestMapping("/test/data")
public class DataController extends BaseController {
    Logger logger = LoggerFactory.getLogger(DataController.class);

    @Autowired
    BasyService basyService;
    @Autowired
    RyjuService ryjuService;
    @Autowired
    SyzdService syzdService;
    @Autowired
    CdssService cdssService;

    @RequestMapping("/getGukedata")
    @ResponseBody
    public void getGukedata(HttpServletResponse response) {
        List<Misdiagnosis> gukeData = basyService.getGukeData();
        Set<Misdiagnosis> saveData = new HashSet<>();
        for (Misdiagnosis bean : gukeData) {
            String id = bean.getId();
            //获取既往史存在疾病
            Misdiagnosis jwSdieases = ryjuService.getJWSdieases(id);
            if (jwSdieases == null || jwSdieases.getHisDiseaseList() == null || jwSdieases.getHisDiseaseList().size() == 0) {
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
            for (Misdiagnosis mz : saveData) {

                bufferedWriter.write(mz.getId() + "," + mz.getPatient_id() + "," + mz.getVisit_id()
                        + mz.getDept_discharge_from_name() + ","
                        + mz.getDistrict_discharge_from_name() + ","
                        + mz.getHisDiseaseList() + ","
                        + mz.getNowDiseaseList() + ","
                        + mz.getSrc()
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
        wirte(response, "导入文件成功");
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
            if (jwSdieases == null || jwSdieases.getHisDiseaseList() == null || jwSdieases.getHisDiseaseList().size() == 0) {
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
            for (Misdiagnosis mz : saveData) {

                bufferedWriter.write(mz.getId() + "," + mz.getPatient_id() + "," + mz.getVisit_id()
                        + mz.getDept_discharge_from_name() + ","
                        + mz.getDistrict_discharge_from_name() + ","
                        + mz.getHisDiseaseList() + ","
                        + mz.getNowDiseaseList() + ","
                        + mz.getSrc()
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
        wirte(response, "导入文件成功");
    }


    /**
     * 根据条件获取确诊时间
     *
     * @param response
     */
    @RequestMapping("/getAvgDateByCondition")
    @ResponseBody
    public void getAvgDateByCondition(HttpServletResponse response, @RequestBody String map) {
        JSONObject jsonObject = JSONObject.parseObject(map);
        String deptName = jsonObject.getString("deptName");
        String startTime = jsonObject.getString("startTime");
        String endTime = jsonObject.getString("endTime");
        String diseaseName = jsonObject.getString("diseaseName");
        int page = jsonObject.getInteger("page") == null ? 1 : jsonObject.getInteger("page");
        int pageSize = jsonObject.getInteger("pageSize") == null ? 20 : jsonObject.getInteger("page");

        String jsonStr = cdssService.getJsonStr(deptName, startTime, endTime, page, pageSize);
        logger.info("条件信息：{}", jsonStr);
        String s = HttpClient.doPost(CdssConstans.patients, jsonStr);
        logger.info("结果信息：{}", s);
        List<CdssDiffBean> diffBeanList = cdssService.getDiffBeanList(s);
//        List<CdssDiffBean> diffBeanList1 = cdssService.getDiffBeanList(diffBeanList);
        List<CdssDiffBean> allDiffBeanList = cdssService.getDiffBean(diffBeanList);
        List<CdssDiffBean> resultList = new LinkedList<>();
        if (StringUtils.isNotBlank(diseaseName)) {
            for (CdssDiffBean bean : allDiffBeanList) {
                if (diseaseName.contains(bean.getChuyuanzhenduan()) || bean.getChuyuanzhenduan().contains(diseaseName)) {
                    resultList.add(bean);
                }
            }
        } else {
            resultList = allDiffBeanList;
        }
        Map<Integer, Integer> stringStatisticsBeanMap = cdssService.analyzeData2CountAndAvgDay(resultList);
        System.out.println(allDiffBeanList);
//        Write2File.w2fileList(allDiffBeanList, "2017年呼吸科确诊数据.txt");

        wirte(response, stringStatisticsBeanMap);
    }

}

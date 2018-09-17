package jhmk.clinic.cms.controller.cdss;

import com.alibaba.fastjson.JSONObject;
import jhmk.clinic.cms.controller.ruleService.*;
import jhmk.clinic.cms.service.CdssRunRuleService;
import jhmk.clinic.cms.service.CdssService;
import jhmk.clinic.cms.service.ReadFileService;
import jhmk.clinic.cms.service.TestService;
import jhmk.clinic.core.base.BaseController;
import jhmk.clinic.core.base.Constants;
import jhmk.clinic.core.config.CdssConstans;
import jhmk.clinic.core.util.DateFormatUtil;
import jhmk.clinic.core.util.RedisCacheUtil;
import jhmk.clinic.core.util.StringUtil;
import jhmk.clinic.core.util.ThreadUtil;
import jhmk.clinic.entity.bean.Binganshouye;
import jhmk.clinic.entity.bean.MenZhen;
import jhmk.clinic.entity.bean.Shangjiyishichafanglu;
import jhmk.clinic.entity.bean.Shouyezhenduan;
import jhmk.clinic.entity.cdss.CdssDiffBean;
import jhmk.clinic.entity.cdss.CdssRuleBean;
import jhmk.clinic.entity.cdss.CdssRunRuleBean;
import jhmk.clinic.entity.pojo.repository.SysDiseasesRepository;
import jhmk.clinic.entity.pojo.repository.SysHospitalDeptRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


@Controller
@RequestMapping("/test/cdss")
public class CdssController extends BaseController {
    Logger logger = LoggerFactory.getLogger(CdssController.class);

    @Autowired
    SysDiseasesRepository sysDiseasesRepository;
    @Autowired
    SysHospitalDeptRepository sysHospitalDeptRepository;
    @Autowired
    BasyService basyService;
    @Autowired
    BlzdService blzdService;
    @Autowired
    RyjuService ryjuService;
    @Autowired
    SjyscflService sjyscflService;
    @Autowired
    SyzdService syzdService;

    @Autowired
    CdssService cdssService;
    @Autowired
    TestService testService;
    @Autowired
    CdssRunRuleService cdssRunRuleService;
    @Autowired
    RestTemplate restTemplate;

    /**
     * ?????? ???????
     *
     * @param response
     */
    @PostMapping("/ranDomSelold")
    @ResponseBody
    public void ranDomSel(HttpServletResponse response, @RequestBody(required = false) String map) {
        Subject subject = SecurityUtils.getSubject();
        boolean authenticated = subject.isAuthenticated();
        System.out.println(authenticated);
        //查询所有patientod
        List<String> idList = cdssService.getAllIds();
        int size = idList.size();
        int round = (int) (Math.random() * size);
        String id = idList.get(round);
        //查询  ruyuanjilu 一诉五史
        CdssRuleBean cdssTestBean = cdssService.selruyuanjiluById(id);
        //病案首页
        Map selbinganshouye = cdssService.selBasy(id);
        cdssTestBean.setBinganshouye(selbinganshouye);
        //病例诊断
        List<Map<String, String>> selbinglizhenduan1 = cdssService.selbinglizhenduan(id);
        cdssTestBean.setBinglizhenduan(selbinglizhenduan1);
        //首页诊断
        List<Map<String, String>> syzdList = cdssService.selSyzd(id);
        cdssTestBean.setShouyezhenduan(syzdList);
        List<Map<String, List<Map<String, String>>>> jianYan = cdssService.getJianYan(id);
//        cdssTestBean.setJianyanbaogao(jianYan);
        Object o = JSONObject.toJSON(cdssTestBean);
        wirte(response, o);
    }

    @PostMapping("/getDataByPIdAndVId")
    @ResponseBody
    public void getDataByPIdAndVId(HttpServletResponse response, @RequestBody(required = false) String map) {
        JSONObject jsonObject = JSONObject.parseObject(map);
        String pid = jsonObject.getString("pid");
        String vid = jsonObject.getString("vid");
        //查询所有patientod
        List<String> idList = cdssService.getAllIds();
        int size = idList.size();
        int round = (int) (Math.random() * size);
        String id = idList.get(round);
        //查询  ruyuanjilu 一诉五史
        CdssRuleBean cdssTestBean = cdssService.selruyuanjiluById(id);
        //病案首页
        Map selbinganshouye = cdssService.selBasy(id);
        cdssTestBean.setBinganshouye(selbinganshouye);
        //病例诊断
        List<Map<String, String>> selbinglizhenduan1 = cdssService.selbinglizhenduan(id);
        cdssTestBean.setBinglizhenduan(selbinglizhenduan1);
        //首页诊断
        List<Map<String, String>> syzdList = cdssService.selSyzd(id);
        cdssTestBean.setShouyezhenduan(syzdList);
        List<Map<String, List<Map<String, String>>>> jianYan = cdssService.getJianYan(id);
//        cdssTestBean.setJianyanbaogao(jianYan);
        Object o = JSONObject.toJSON(cdssTestBean);
        wirte(response, o);
    }

    //    @PostMapping("/ranDomSel")
//    @ResponseBody
//    public void ranDomSelByIllName(HttpServletResponse response, @RequestBody(required = false) String map) {
//        JSONObject jsonObject = JSONObject.parseObject(map);
//        if (StringUtils.isNotBlank(map) && jsonObject != null && StringUtils.isNotBlank(jsonObject.getString("dept_code"))) {
//            String dept_code = jsonObject.getString("dept_code");
//            List<CdssRuleBean> tem = new LinkedList<>();
//            if (StringUtil.isChinese(dept_code)) {
//
//                for (CdssRuleBean cdssRuleBean : caseList) {
//                    if (dept_code.equals(cdssRuleBean.getBinganshouye().get("pat_visit_dept_admission_to_name"))) {
//                        tem.add(cdssRuleBean);
//                    }
//                }
//                int round = (int) (Math.random() * tem.size());
//                CdssRuleBean cdssTestBean = null;
//                try {
//
//                    cdssTestBean = tem.get(round);
//                } catch (NullPointerException e) {
//
//                    e.printStackTrace();
//                    logger.info("错误提示{}" + e.getMessage());
//                    ranDomSelByIllName(response, map);
//                }
//                Object o = JSONObject.toJSON(cdssTestBean);
//                wirte(response, o);
//            }
//
//        } else {
//            int round = (int) (Math.random() * caseList.size());
//            CdssRuleBean cdssTestBean = null;
//            try {
//
//                cdssTestBean = caseList.get(round);
//            } catch (NullPointerException e) {
//
//                e.printStackTrace();
//                logger.info("错误提示{}" + e.getMessage());
//                ranDomSelByIllName(response, map);
//            }
//            Object o = JSONObject.toJSON(cdssTestBean);
//            wirte(response, o);
//        }
//
//    }
    @PostMapping("/ranDomSel")
    @ResponseBody
    public void ranDomSelByIllName(HttpServletResponse response, @RequestBody(required = false) String map) {
        JSONObject jsonObject = JSONObject.parseObject(map);
        if (StringUtils.isNotBlank(map)) {
            String dept_code = jsonObject.getString("dept_code");
            String illName = jsonObject.getString("illname");
            List<CdssRuleBean> tem = new LinkedList<>();
            List<CdssRuleBean> resultTem = new LinkedList<>();
            if (StringUtils.isNotBlank(dept_code)) {
                if (StringUtil.isChinese(dept_code)) {

                    for (CdssRuleBean cdssRuleBean : caseList) {
                        if (dept_code.equals(cdssRuleBean.getBinganshouye().get("pat_visit_dept_admission_to_name"))) {
                            tem.add(cdssRuleBean);
                        }
                    }
                } else {
                    tem = caseList;
                }
            } else {
                tem = caseList;
            }
            if (StringUtils.isNotBlank(illName)) {
                for (CdssRuleBean cdssRuleBean : tem) {
                    List<Map<String, String>> shouyezhenduan = cdssRuleBean.getShouyezhenduan();
                    for (int i = 0; i < shouyezhenduan.size(); i++) {
                        Map<String, String> map1 = shouyezhenduan.get(i);
                        if (illName.equals(map1.get("diagnosis_name")) && "1".equals(map1.get("diagnosis_num"))) {
                            if (StringUtils.isNotBlank(map1.get("diagnosis_type_code"))) {
                                if ("3".equals(map1.get("diagnosis_type_code"))) {
                                    resultTem.add(cdssRuleBean);
                                }
                            } else {
                                resultTem.add(cdssRuleBean);

                            }
                        }
                    }
                }
            } else {
                resultTem = tem;
            }
            if (resultTem.size() == 0) {
                resultTem = caseList;
            }
            int round = (int) (Math.random() * resultTem.size());
            CdssRuleBean cdssTestBean = null;
            try {

                cdssTestBean = resultTem.get(round);
            } catch (NullPointerException e) {

                e.printStackTrace();
                logger.info("错误提示{}" + e.getMessage());
                ranDomSelByIllName(response, map);
            }
            Object o = JSONObject.toJSON(cdssTestBean);
            wirte(response, o);
        } else {
            int round = (int) (Math.random() * caseList.size());
            CdssRuleBean cdssTestBean = null;
            try {

                cdssTestBean = caseList.get(round);
            } catch (NullPointerException e) {

                e.printStackTrace();
                logger.info("错误提示{}" + e.getMessage());
                ranDomSelByIllName(response, map);
            }
            Object o = JSONObject.toJSON(cdssTestBean);
            wirte(response, o);
        }
    }

    /**
     * 模糊查询(疾病)
     *
     * @param response
     * @throws IOException
     */
    @PostMapping("/fuzzySearchForDisease")
    @ResponseBody
    public void fuzzySearchForDisease(HttpServletResponse response, @RequestBody(required = true) String map) throws IOException {
        JSONObject jsonObject = JSONObject.parseObject(map);
        //疾病名称
        String disease = jsonObject.getString("disease");
        List<String> allByChinaDiseasesAndEngDiseases = null;
        String firstSpell = StringUtil.getFirstSpell(disease);
        if (StringUtil.isChinese(disease)) {
            allByChinaDiseasesAndEngDiseases = sysDiseasesRepository.findAllByChinaDiseasesAndEngDiseases(disease, firstSpell);
        } else {
            allByChinaDiseasesAndEngDiseases = sysDiseasesRepository.findAllByEngDiseases(firstSpell);
        }
        wirte(response, allByChinaDiseasesAndEngDiseases);
    }

//    @PostMapping("/fuzzySearchForDept")
//    @ResponseBody
//    public void fuzzySearchForDept(HttpServletResponse response, @RequestBody(required = true) String map) throws IOException {
//        JSONObject jsonObject = JSONObject.parseObject(map);
//        //疾病名称
//        String dept = jsonObject.getString("dept");
//        List<String> allByChinaDiseasesAndEngDiseases = null;
//        String firstSpell = StringUtil.getUppercaseFirstSpell(dept);
//        if (StringUtil.isChinese(dept)) {
//            allByChinaDiseasesAndEngDiseases = sysHospitalDeptRepository.findAllByDeptNameAndInputCode(dept, firstSpell);
//        } else {
//            allByChinaDiseasesAndEngDiseases = sysHospitalDeptRepository.findAllByInputCode(firstSpell);
//        }
//        if (allByChinaDiseasesAndEngDiseases.size()==0){
//            allByChinaDiseasesAndEngDiseases = sysHospitalDeptRepository.findAllByInputCode("");
//
//        }
//        wirte(response, allByChinaDiseasesAndEngDiseases);
//    }

    @PostMapping("/fuzzySearchForDept")
    @ResponseBody
    public void fuzzySearchForDept(HttpServletResponse response, @RequestBody(required = true) String map) throws IOException {

        Set<String> diseaseNames = redisCacheUtil.getCacheSet("diseaseNames");
        JSONObject jsonObject = JSONObject.parseObject(map);
        //疾病名称
        String dept = jsonObject.getString("dept");
        List<String> tempName = new LinkedList<>();

        for (String s : diseaseNames) {
            if (s.contains(dept)) {
                tempName.add(s);
            }
        }

        wirte(response, tempName);
    }


    /**
     * 3院真实数据 去处罚规则
     *
     * @param response
     * @throws IOException
     */
    @PostMapping("/runRuleDatabase")
    @ResponseBody
    public void runRuleZhenDuan(HttpServletResponse response) throws IOException {
//        ExecutorService exec = Executors.newFixedThreadPool(32);
        ThreadUtil.ThreadPool instance = ThreadUtil.getInstance();
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("C:/嘉和美康文档/cdss文本文件/数据库拼接信息.txt")));

        //查询所有patientod
        List<String> list = cdssService.getAllIds();
        int size = list.size();
        //每个线程操作 数量
        int ncount = size / 32;
        List<String> tlist = null;
        Runnable runnable = null;
        for (int j = 0; j < 32; j++) {
            if (j == 32 - 1) {
                tlist = list.subList(j * ncount, size);
            } else {
                tlist = list.subList(j * ncount, (j + 1) * ncount);

            }
            final List<String> templist = tlist;
            runnable = new Runnable() {
                @Override
                public void run() {
                    String s = "";

                    for (String _id : templist) {

                        CdssRunRuleBean bean = cdssRunRuleService.getBASY(_id);
                        List<Map<String, String>> blzd = cdssRunRuleService.getBLZD(_id);
                        bean.setBinglizhenduan(blzd);
                        Map<String, String> ryjl = cdssRunRuleService.getRYJL(_id);
                        bean.setRuyuanjilu(ryjl);
                        List<Map<String, String>> zy = cdssRunRuleService.getZY(_id);
                        bean.setYizhu(zy);
                        List<Map<String, String>> jcbg = cdssRunRuleService.getJCBG(_id);
                        bean.setJianchabaogao(jcbg);
                        List<Map<String, String>> jybg = cdssRunRuleService.getJYBG(_id);
                        bean.setJianyanbaogao(jybg);
                        bean.setWarnSource("住院");

                        String string = JSONObject.toJSONString(bean);
//                        System.out.println(string);
                        if (string == null || "".equals(string)) {
                            continue;
                        }
                        Object parse = JSONObject.parse(string);
                        try {
                            s = restTemplate.postForObject(CdssConstans.URLFORRULE, parse, String.class);
                            logger.info("匹配规则返回信息为{}", s);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    wirte(response, s);

                }
            };
            instance.execute(runnable);
        }

    }

    @PostMapping("/runRuleDatabaseSimple")
    @ResponseBody
    public void runRuleDatabaseSimple(HttpServletResponse response) throws IOException {

        //查询所有patientod
        List<String> list = cdssService.getAllIds();

        for (String _id : list) {
            CdssRunRuleBean bean = cdssRunRuleService.getBASY(_id);
            List<Map<String, String>> blzd = cdssRunRuleService.getBLZD(_id);
            bean.setBinglizhenduan(blzd);
            Map<String, String> ryjl = cdssRunRuleService.getRYJL(_id);
            bean.setRuyuanjilu(ryjl);
            List<Map<String, String>> zy = cdssRunRuleService.getZY(_id);
            bean.setYizhu(zy);
            List<Map<String, String>> jcbg = cdssRunRuleService.getJCBG(_id);
            bean.setJianchabaogao(jcbg);
            List<Map<String, String>> jybg = cdssRunRuleService.getJYBG(_id);
            bean.setJianyanbaogao(jybg);
            bean.setWarnSource("住院");

            String string = JSONObject.toJSONString(bean);
//                        System.out.println(string);
            if (string == null || "".equals(string)) {
                continue;
            }
            Object parse = JSONObject.parse(string);
            try {
                String s = restTemplate.postForObject(CdssConstans.URLFORRULE, parse, String.class);
                logger.info("匹配规则返回信息为{}", s);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }


    @PostMapping("/getDateByIll")
    @ResponseBody
    public void getDateByIll(HttpServletResponse response, @RequestBody String map) {
        JSONObject jsonObject = JSONObject.parseObject(map);
        String illName = jsonObject.getString("illName");
        List<CdssRuleBean> tem = new LinkedList<>();

        for (CdssRuleBean cdssRuleBean : caseList) {
            List<Map<String, String>> shouyezhenduan = cdssRuleBean.getShouyezhenduan();
            for (int i = 0; i < shouyezhenduan.size(); i++) {
                Map<String, String> map1 = shouyezhenduan.get(i);
                if (illName.equals(map1.get("diagnosis_name")) && "1".equals(map1.get("diagnosis_num"))) {
                    tem.add(cdssRuleBean);
                }
            }
        }
        int round = (int) (Math.random() * tem.size());
        CdssRuleBean cdssTestBean = null;
        try {

            cdssTestBean = tem.get(round);
        } catch (NullPointerException e) {

            e.printStackTrace();
            logger.info("错误提示{}" + e.getMessage());
            ranDomSelByIllName(response, map);
        }
        Object o = JSONObject.toJSON(cdssTestBean);
        wirte(response, o);
    }

    @PostMapping("/getDataByDeptAndTime")
    public void getDataByDeptAndTime(HttpServletResponse response, @RequestBody String map) {
        JSONObject jsonObject = JSONObject.parseObject(map);
        String deptName = jsonObject.getString("deptName");
        String startTime = jsonObject.getString("startTime");
        String endTime = jsonObject.getString("endTime");
        String jsonStr = cdssService.getJsonStr(deptName, startTime, endTime);
        System.out.println(jsonStr);
        String s = HttpClient.doPost(CdssConstans.patients, jsonStr);
//        System.out.println(new String(s.getBytes("utf-8"),""));
        System.out.println(s);
        List<CdssDiffBean> diffBeanList = cdssService.getDiffBeanList(s);
        System.out.println(diffBeanList);
    }

}

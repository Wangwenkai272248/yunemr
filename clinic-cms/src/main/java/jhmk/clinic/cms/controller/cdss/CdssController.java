package jhmk.clinic.cms.controller.cdss;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jhmk.clinic.cms.SamilarService;
import jhmk.clinic.cms.controller.ruleService.*;
import jhmk.clinic.cms.entity.MenzhenRule;
import jhmk.clinic.cms.service.*;
import jhmk.clinic.core.base.BaseController;
import jhmk.clinic.core.config.CdssConstans;
import jhmk.clinic.core.util.HttpClient;
import jhmk.clinic.core.util.StringUtil;
import jhmk.clinic.core.util.ThreadUtil;
import jhmk.clinic.entity.bean.*;
import jhmk.clinic.entity.cdss.CdssDiffBean;
import jhmk.clinic.entity.cdss.CdssRuleBean;
import jhmk.clinic.entity.cdss.CdssRunRuleBean;
import jhmk.clinic.entity.cdss.StatisticsBean;
import jhmk.clinic.entity.model.AtResponse;
import jhmk.clinic.entity.model.ResponseCode;
import jhmk.clinic.entity.pojo.YizhuBsjb;
import jhmk.clinic.entity.pojo.YizhuChange;
import jhmk.clinic.entity.pojo.YizhuResult;
import jhmk.clinic.entity.pojo.repository.SysDiseasesRepository;
import jhmk.clinic.entity.pojo.repository.SysHospitalDeptRepository;
import jhmk.clinic.entity.service.YizhuBsjbRepService;
import jhmk.clinic.entity.service.YizhuChangeRepService;
import jhmk.clinic.entity.service.YizhuOriRepService;
import jhmk.clinic.entity.service.YizhuResultRepService;
import jhmk.clinic.entity.test.YizhuTestBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static jhmk.clinic.cms.service.InitService.*;


@Controller
@RequestMapping("/cdss")
public class CdssController extends BaseController {
    Logger logger = LoggerFactory.getLogger(CdssController.class);

    @Autowired
    SysDiseasesRepository sysDiseasesRepository;
    @Autowired
    SysHospitalDeptRepository sysHospitalDeptRepository;
    @Autowired
    BasyService basyService;
    @Autowired
    YizhuService yizhuService;
    @Autowired
    CyjlService cyjlService;
    @Autowired
    BlzdService blzdService;
    @Autowired
    RyjuService ryjuService;
    @Autowired
    SjyscflService sjyscflService;
    @Autowired
    SamilarService samilarService;
    @Autowired
    SyzdService syzdService;

    @Autowired
    CyCdssService cyCdssService;
    @Autowired
    CdssService cdssService;
    @Autowired
    TestService testService;
    @Autowired
    CdssRunRuleService cdssRunRuleService;
    @Autowired
    YizhuOriRepService yizhuOriRepService;
    @Autowired
    YizhuBsjbRepService yizhuBsjbRepService;
    @Autowired
    YizhuChangeRepService yizhuChangeRepService;
    @Autowired
    YizhuResultRepService yizhuResultRepService;
    @Autowired
    BiaozhuService biaozhuService;
    @Autowired
    SyshService syshService;
    @Autowired
    MzjcbgService mzjcbgService;
    @Autowired
    MzjybgService mzjybgService;
    @Autowired
    MzsjService mzsjService;
    @Autowired
    MzzdService mzzdService;

    @Autowired
    RestTemplate restTemplate;


    @PostMapping("/getDataByPIdAndVId")
    public void getDataByPIdAndVId(HttpServletResponse response, @RequestBody(required = false) String map) {
        JSONObject jsonObject = JSONObject.parseObject(map);
        String pid = jsonObject.getString("pid");
        String vid = jsonObject.getString("vid");
        String hospitalName = jsonObject.getString("hospitalName");
        if ("cyyy".equals(hospitalName)) {
//            String id = "BJCYYY#2#" + pid + "#" + vid;
            String id = "BJCYYY#" + pid + "#" + vid;
            CdssRuleBean cdssTestBean = cyCdssService.selruyuanjiluById(id);
            cdssTestBean.setId(id);
            cdssTestBean.setPatient_id(pid);
            cdssTestBean.setVisit_id(vid);
            //病案首页
            Map selbinganshouye = cyCdssService.selBasy(id);
            cdssTestBean.setBinganshouye(selbinganshouye);
            //病例诊断
            List<Map<String, String>> selbinglizhenduan1 = cyCdssService.selbinglizhenduan(id);
            cdssTestBean.setBinglizhenduan(selbinglizhenduan1);
            //首页诊断
            List<Map<String, String>> syzdList = cyCdssService.selSyzd(id);
            cdssTestBean.setShouyezhenduan(syzdList);
            List<Map<String, List<Map<String, String>>>> jianYan = cyCdssService.getJianYan(id);
//        cdssTestBean.setJianyanbaogao(jianYan);
            Object o = JSONObject.toJSON(cdssTestBean);
            wirte(response, o);
        } else {
            String id = "BJDXDSYY#" + pid + "#" + vid;
//            String id = "BJDXDSYY#2#" + pid + "#" + vid;
            CdssRuleBean cdssTestBean = cdssService.selruyuanjiluById(id);
            cdssTestBean.setId(id);
            cdssTestBean.setPatient_id(pid);
            cdssTestBean.setVisit_id(vid);
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
            List<Shouyeshoushu> shouyeshoushu = syshService.getShouyeshoushu(id);
            cdssTestBean.setShouyeshoushu(shouyeshoushu);
//        cdssTestBean.setJianyanbaogao(jianYan);
            Object o = JSONObject.toJSON(cdssTestBean);
            wirte(response, o);
        }
        //查询  ruyuanjilu 一诉五史

    }


    @PostMapping("/getMenzhenDataByPIdAndVId")
    public void getMenzhenDataByPIdAndVId(HttpServletResponse response, @RequestBody(required = false) String map) {
        JSONObject jsonObject = JSONObject.parseObject(map);
        String pid = jsonObject.getString("pid");
        String vid = jsonObject.getString("vid");
        String id = jsonObject.getString("id");
//            String id = "BJCYYY#2#" + pid + "#" + vid;
        if (StringUtils.isEmpty(id)) {
            id = "BJDXDSYY#" + pid + "#" + vid;
        }
            MenzhenRule cdssTestBean = new MenzhenRule();
        cdssTestBean.setId(id);
        cdssTestBean.setPatient_id(pid);
        cdssTestBean.setVisit_id(vid);
        //病案首页

        Mzbinganshouye mzbasyById = mzsjService.getMzbasyById(id);
        cdssTestBean.setMzbinganshouye(mzbasyById);
        //门诊诊断
        List<Menzhenzhenduan> menzhenzhenduanById = mzzdService.getMenzhenzhenduanById(id);
        cdssTestBean.setMenzhenzhenduanList(menzhenzhenduanById);
        List<Menzhenjianchabaogao> mzjcbgById = mzjcbgService.getMzjcbgById(id);
        cdssTestBean.setMenzhenjianchabaogaoList(mzjcbgById);
        List<Menzhenjianyanbaogao> mzjybgById = mzjybgService.getMzjybgById(id);
        cdssTestBean.setMenzhenjianyanbaogaoList(mzjybgById);
        List<Map<String, String>> mzsjById = mzsjService.getMzsjById(id);
        cdssTestBean.setMenzhenzhenshuju(mzsjById);
        wirte(response, cdssTestBean);
    }


    /**
     * 随机病例 北医三院
     *
     * @param response
     * @param map
     */
    @PostMapping("/ranDomSel")
    @ResponseBody
    public void ranDomSel(HttpServletResponse response, @RequestBody(required = false) String map) {
        JSONObject jsonObject = JSONObject.parseObject(map);
        logger.info("随机病历查询条件为{}：", map);
        if (StringUtils.isNotBlank(map)) {
            String dept_code = jsonObject.getString("dept_code");
            String illName = jsonObject.getString("illname");
            String same = jsonObject.getString("same");
            List<CdssRuleBean> tem = new LinkedList<>();
            List<CdssRuleBean> resultTem = new LinkedList<>();
            if (StringUtils.isNotBlank(dept_code)) {
                if (StringUtil.isChinese(dept_code)) {
                    logger.info("caseList的数量为：{}", caseList.size());
                    for (CdssRuleBean cdssRuleBean : caseList) {
                        if (cdssRuleBean == null || cdssRuleBean.getBinganshouye() == null || cdssRuleBean.getBinganshouye().get("pat_visit_dept_admission_to_name") == null) {
                            logger.info("有问题的数据：{}", JSONObject.toJSONString(cdssRuleBean));
                            continue;
                        }
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
            logger.info("过完部门表结果数量为：{}", tem.size());
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
            logger.info("过完疾病名结果数量为：{}", resultTem.size());

            if (resultTem.size() == 0) {
                resultTem = caseList;
            }
            List<CdssRuleBean> list = new ArrayList<>();
            if ("true".equals(same)) {
                for (CdssRuleBean cdssRuleBean : resultTem) {
                    String rycz = cdssRuleBean.getRycz();
                    String cyzd = cdssRuleBean.getCyzd();
                    if (rycz == null || cyzd == null) {
                        continue;
                    }
//                    if (samilarService.isFatherAndSon(rycz, cyzd)) {
                    if (rycz.equals(cyzd)) {
                        list.add(cdssRuleBean);
                    }
                }
                if (list.size() == 0) {
                    list.addAll(resultTem);
                }
            } else {
                list.addAll(resultTem);
            }
            logger.info("过完同义词结果数量为：{}", list.size());

            int round = (int) (Math.random() * list.size());
            CdssRuleBean cdssTestBean = null;
            try {

                cdssTestBean = list.get(round);
            } catch (NullPointerException e) {

                e.printStackTrace();
                logger.info("错误提示{}" + e.getMessage());
                ranDomSelBysy(response, map);
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
                ranDomSelBysy(response, map);
            }
            Object o = JSONObject.toJSON(cdssTestBean);
            wirte(response, o);
        }
    }

    /**
     * 随机病例 北医三院
     *
     * @param response
     * @param map
     */
    @PostMapping("/ranDomSelBysy")
    @ResponseBody
    public void ranDomSelBysy(HttpServletResponse response, @RequestBody(required = false) String map) {
        JSONObject jsonObject = JSONObject.parseObject(map);
        logger.info("随机病历查询条件为{}：", map);
        if (StringUtils.isNotBlank(map)) {
            String dept_code = jsonObject.getString("dept_code");
            String illName = jsonObject.getString("illname");
            String same = jsonObject.getString("same");
            List<CdssRuleBean> tem = new LinkedList<>();
            List<CdssRuleBean> resultTem = new LinkedList<>();
            if (StringUtils.isNotBlank(dept_code)) {
                if (StringUtil.isChinese(dept_code)) {
                    logger.info("caseList的数量为：{}", caseList.size());
                    for (CdssRuleBean cdssRuleBean : caseList) {
                        if (cdssRuleBean == null || cdssRuleBean.getBinganshouye() == null || cdssRuleBean.getBinganshouye().get("pat_visit_dept_admission_to_name") == null) {
                            logger.info("有问题的数据：{}", JSONObject.toJSONString(cdssRuleBean));
                            continue;
                        }
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
            logger.info("过完部门表结果数量为：{}", tem.size());
            if (StringUtils.isNotBlank(illName)) {
                for (CdssRuleBean cdssRuleBean : tem) {
                    if (illName.equals(cdssRuleBean.getCyzd())) {
                        resultTem.add(cdssRuleBean);
                    }
                }
            } else {
                resultTem = tem;
            }
            logger.info("过完疾病名结果数量为：{}", resultTem.size());

            if (resultTem.size() == 0) {
                resultTem = caseList;
            }
            List<CdssRuleBean> list = new ArrayList<>();
            if ("true".equals(same)) {
                for (CdssRuleBean cdssRuleBean : resultTem) {
                    String rycz = cdssRuleBean.getRycz();
                    String cyzd = cdssRuleBean.getCyzd();
                    if (rycz == null || cyzd == null) {
                        continue;
                    }
//                    if (samilarService.isFatherAndSon(rycz, cyzd)) {
                    if (rycz.equals(cyzd)) {
                        list.add(cdssRuleBean);
                    }
                }
                if (list.size() == 0) {
                    list.addAll(resultTem);
                }
            } else {
                list.addAll(resultTem);
            }
            logger.info("过完同义词结果数量为：{}", list.size());

            int round = (int) (Math.random() * list.size());
            CdssRuleBean cdssTestBean = null;
            try {

                cdssTestBean = list.get(round);
            } catch (NullPointerException e) {

                e.printStackTrace();
                logger.info("错误提示{}" + e.getMessage());
                ranDomSelBysy(response, map);
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
                ranDomSelBysy(response, map);
            }
            Object o = JSONObject.toJSON(cdssTestBean);
            wirte(response, o);
        }
    }

    /**
     * 随机病例 朝阳
     *
     * @param response
     * @param map
     */
    @PostMapping("/ranDomSelCyyy")
    @ResponseBody
    public void ranDomSelCyyy(HttpServletResponse response, @RequestBody(required = false) String map) {
        JSONObject jsonObject = JSONObject.parseObject(map);
        logger.info("随机病历查询条件为{}：", map);
        if (StringUtils.isNotBlank(map)) {
            String dept_code = jsonObject.getString("dept_code");
            String illName = jsonObject.getString("illname");
            String same = jsonObject.getString("same");
            List<CdssRuleBean> tem = new LinkedList<>();
            List<CdssRuleBean> resultTem = new LinkedList<>();
            if (StringUtils.isNotBlank(dept_code)) {
                if (StringUtil.isChinese(dept_code)) {
                    logger.info("caseList的数量为：{}", caseListCy.size());
                    for (CdssRuleBean cdssRuleBean : caseListCy) {
                        if (cdssRuleBean == null || cdssRuleBean.getBinganshouye() == null || cdssRuleBean.getBinganshouye().get("pat_visit_dept_admission_to_name") == null) {
                            logger.info("有问题的数据：{}", JSONObject.toJSONString(cdssRuleBean));
                            continue;
                        }
                        if (dept_code.equals(cdssRuleBean.getBinganshouye().get("pat_visit_dept_admission_to_name"))) {
                            tem.add(cdssRuleBean);
                        }
                    }
                } else {
                    tem = caseListCy;
                }
            } else {
                tem = caseListCy;
            }
            logger.info("过完部门表结果数量为：{}", tem.size());
            if (StringUtils.isNotBlank(illName)) {
                for (CdssRuleBean cdssRuleBean : tem) {
                    String cyzd = cdssRuleBean.getCyzd();
                    if (illName.equals(cyzd)) {
                        resultTem.add(cdssRuleBean);
                    }
                }
            } else {
                resultTem = tem;
            }
            logger.info("过完疾病名结果数量为：{}", resultTem.size());

            if (resultTem.size() == 0) {
                resultTem = caseListCy;
            }
            List<CdssRuleBean> list = new ArrayList<>();
            if ("true".equals(same)) {
                for (CdssRuleBean cdssRuleBean : resultTem) {
                    String rycz = cdssRuleBean.getRycz();
                    String cyzd = cdssRuleBean.getCyzd();
                    if (rycz == null || cyzd == null) {
                        continue;
                    }
//                    if (samilarService.isFatherAndSon(rycz, cyzd)) {
                    if (rycz.equals(cyzd)) {
                        list.add(cdssRuleBean);
                    }
                }
                if (list.size() == 0) {
                    list.addAll(resultTem);
                }
            } else {
                list.addAll(resultTem);
            }
            logger.info("过完同义词结果数量为：{}", list.size());

            int round = (int) (Math.random() * list.size());
            CdssRuleBean cdssTestBean = null;
            try {

                cdssTestBean = list.get(round);
            } catch (NullPointerException e) {

                e.printStackTrace();
                logger.info("错误提示{}" + e.getMessage());
                ranDomSelCyyy(response, map);
            }
            Object o = JSONObject.toJSON(cdssTestBean);
            wirte(response, o);
        } else {
            int round = (int) (Math.random() * caseListCy.size());
            CdssRuleBean cdssTestBean = null;
            try {
                cdssTestBean = caseListCy.get(round);
            } catch (NullPointerException e) {

                e.printStackTrace();
                logger.info("错误提示{}" + e.getMessage());
                ranDomSelCyyy(response, map);
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
        logger.info("进来啦===========开始查询数据库拉");
        ThreadUtil.ThreadPool instance = ThreadUtil.getInstance();
        //查询所有patientod
        List<String> list = basyService.getAllIdByAddmissionDate("2017-10-01 00:00:00");
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
                        bean.setPageSource("testDatabasse");

                        String string = JSONObject.toJSONString(bean);
//                        System.out.println(string);
                        if (string == null || "".equals(string)) {
                            continue;
                        }
                        Object parse = JSONObject.parse(string);
                        try {
                            s = restTemplate.postForObject(CdssConstans.earlywarnRuleMtch, parse, String.class);
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
        logger.info("结束啦==================================");

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
                String s = restTemplate.postForObject(CdssConstans.earlywarnRuleMtch, parse, String.class);
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
            ranDomSelBysy(response, map);
        }
        Object o = JSONObject.toJSON(cdssTestBean);
        wirte(response, o);
    }


    @GetMapping("/demo1")
    public void demo1(HttpServletResponse response) {
        List<String> ss = samilarService.getSamilarWord("心力衰竭");
    }

    @PostMapping("/getDataByDeptAndTime")
    public void getDataByDeptAndTime(HttpServletResponse response, @RequestBody String map) {
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
        List<CdssDiffBean> diffBeanList1 = cdssService.getAllDiffBeanList(diffBeanList);
        //根据科室
        if (StringUtils.isNotBlank(diseaseName)) {
            List<CdssDiffBean> resultList = new ArrayList<>();
            for (CdssDiffBean bean : diffBeanList1) {
                if (diseaseName.equals(bean.getChuyuanzhenduan())) {
                    resultList.add(bean);
                }
            }
            wirte(response, resultList);
        } else {
            wirte(response, diffBeanList1);
        }
    }


    @PostMapping("/getDataByDeptAndTimeSecond")
    public void getDataByDeptAndTimeSecond(HttpServletResponse response, @RequestBody String map) {
        List<String> goodBingli = biaozhuService.getGoodBingli();
        logger.info("优质病历数量为,{},格式为：{}", goodBingli.size(), goodBingli.get(3));
        logger.info("优质病历测试{}", goodBingli.contains("BJDXDSYY##2#001439833200#1"));
        JSONObject jsonObject = JSONObject.parseObject(map);
        String deptName = jsonObject.getString("deptName");
        String startTime = jsonObject.getString("startTime");
        String endTime = jsonObject.getString("endTime");
        String diseaseName = jsonObject.getString("diseaseName");
        //优质病例标志
        String flag = jsonObject.getString("flag");
        int page = jsonObject.getInteger("page") == null ? 1 : jsonObject.getInteger("page");
        int pageSize = jsonObject.getInteger("pageSize") == null ? 20 : jsonObject.getInteger("page");
        String jsonStr = cdssService.getJsonStr(deptName, startTime, endTime, page, pageSize);
//        String jsonStr = cdssService.getJsonStr(deptName, startTime, endTime);
        String s = HttpClient.doPost(CdssConstans.patients, jsonStr);
        List<CdssDiffBean> diffBeanList = cdssService.getDiffBeanList(s);
        logger.info("ES查询获取到的疾病为：{}", diffBeanList.size());
//        List<CdssDiffBean> diffBeanList1 = cdssService.getDiffBeanList(diffBeanList);
        //获取入院等于出院数据
        List<CdssDiffBean> diffBeanList1 = cdssService.getRyeqCy(diffBeanList);
        logger.info("入等于出过滤后到的疾病数量为：{}", diffBeanList1.size());

        //根据疾病名
        if (StringUtils.isNotBlank(diseaseName)) {
            List<CdssDiffBean> resultList = new ArrayList<>();
            for (CdssDiffBean bean : diffBeanList1) {
                if (diseaseName.equals(bean.getChuyuanzhenduan())) {
                    if ("true".equals(flag)) {
                        String id = bean.getId();
                        if (goodBingli.contains(id)) {
                            resultList.add(bean);
                        }
                    } else {
                        resultList.add(bean);
                    }

                }
            }
            logger.info("总结果为：======================={}", resultList.size());
            wirte(response, resultList);
        } else {
            wirte(response, diffBeanList1);
        }
    }

    @PostMapping("/getGoodRecords")
    public void getGoodRecords(HttpServletResponse response, @RequestBody String map) {
        List<String> goodBingli = biaozhuService.get3HosputalGoodBingli();
        logger.info("优质病历数量为,{}", goodBingli.size());
        JSONObject jsonObject = JSONObject.parseObject(map);
        String deptName = jsonObject.getString("deptName");
        String startTime = jsonObject.getString("startTime");
        String endTime = jsonObject.getString("endTime");
        String diseaseName = jsonObject.getString("diseaseName");
        //优质病例标志
        int page = jsonObject.getInteger("page") == null ? 1 : jsonObject.getInteger("page");
        int pageSize = jsonObject.getInteger("pageSize") == null ? 20 : jsonObject.getInteger("page");
        String jsonStr = cdssService.getJsonStr(deptName, startTime, endTime, page, pageSize);
        logger.info("ES返回数据为：{}", jsonObject);
//        String jsonStr = cdssService.getJsonStr(deptName, startTime, endTime);
        String s = HttpClient.doPost(CdssConstans.patients, jsonStr);
        List<CdssDiffBean> diffBeanList = cdssService.getDiffBeanList(s);
        logger.info("总共：{}份数据", diffBeanList.size());

        //获取优质病例数据
        //根据科室
        if (StringUtils.isNotBlank(diseaseName)) {
            List<CdssDiffBean> resultList = new ArrayList<>();
            for (CdssDiffBean bean : diffBeanList) {
                if (diseaseName.equals(bean.getChuyuanzhenduan())) {
                    String inpNo = bean.getInp_no();
                    if (goodBingli.contains(inpNo)) {
                        resultList.add(bean);
                    }

                }
            }
            wirte(response, resultList);
        } else {
            List<CdssDiffBean> resultList = new ArrayList<>();
            for (CdssDiffBean bean : diffBeanList) {
                String inpNo = bean.getInp_no();
                if (goodBingli.contains(inpNo)) {
                    resultList.add(bean);
                }
            }
            wirte(response, resultList);
        }
    }

    /**
     * 分析数据
     *
     * @param response
     * @param map
     */
    @PostMapping("/analyzeData")
    public void analyzeData(HttpServletResponse response, @RequestBody String map) {
        JSONObject jsonObject = JSONObject.parseObject(map);
        String idList = jsonObject.getString("idList");
        String[] split = idList.split(",");
        String jsonStr = cdssService.getJsonStr1(split);
        String s = HttpClient.doPost(CdssConstans.patients, jsonStr);
        List<CdssDiffBean> diffBeanList = cdssService.getDiffBeanList(s);
        List<CdssDiffBean> diffBeanList1 = cdssService.getDiffBeanList(diffBeanList);
        Map<String, StatisticsBean> stringStatisticsBeanMap = cdssService.analyzeData(diffBeanList1);
        List<StatisticsBean> result = new ArrayList<>();
        for (Map.Entry<String, StatisticsBean> entry : stringStatisticsBeanMap.entrySet()) {
            StatisticsBean value = entry.getValue();
            value.setAvgDay(value.getDay() / value.getCount());
            result.add(value);
        }
        wirte(response, result);
    }

    @PostMapping("/getData")
    public void getData(HttpServletResponse response, @RequestBody String map) {
        AtResponse resp = new AtResponse();
        JSONObject jsonObject = JSONObject.parseObject(map);
        String id = jsonObject.getString("id").replaceAll("##2", "");
        //获取出院记录src
        Map<String, Object> params = new HashMap<>();
        Chuyuanjilu cyjlSrrc = cyjlService.getScbcjlById(id);
        params.put("cyjl", cyjlSrrc);
        List<Yizhu> yizhus = yizhuService.selYizhu(id);
        params.put("yizhu", yizhus);
        List<TreatmentPlan> srcById = sjyscflService.getSrcById(id, yizhus);
        params.put("sjyscf", srcById);
        resp.setData(params);
        resp.setResponseCode(ResponseCode.OK);
        wirte(response, resp);
    }

    @PostMapping("/getTimeByName")
    public void getTimeByName(HttpServletResponse response, @RequestBody String map) {
        AtResponse resp = new AtResponse();
        JSONObject jsonObject = JSONObject.parseObject(map);
        String id = jsonObject.getString("id").replaceAll("##2", "");
        String name = jsonObject.getString("name");
        //获取出院记录src
        List<Yizhu> yizhus = yizhuService.selYizhu(id);
        List<Yizhu> yizhusTemp = new ArrayList<>();
        for (Yizhu yizhu : yizhus) {
            if (name.equals(yizhu.getOrder_item_name())) {
                yizhusTemp.add(yizhu);
            }
        }
        resp.setData(yizhusTemp);
        resp.setResponseCode(ResponseCode.OK);
        wirte(response, resp);
    }

    /**
     * 医嘱模糊查询
     *
     * @param response
     * @param map
     */
    @PostMapping("/fuzzyQueryYizhu")
    public void fuzzyQueryYizhu(HttpServletResponse response, @RequestBody String map) {
        AtResponse resp = new AtResponse();
        JSONObject jsonObject = JSONObject.parseObject(map);
        String id = jsonObject.getString("id").replaceAll("##2", "");
        String str = jsonObject.getString("str");
        //获取出院记录src
        Map<String, Object> params = new HashMap<>();
        List<Yizhu> yizhus = yizhuService.selYizhu(id);
        List<Yizhu> temp = new ArrayList<>();
        for (Yizhu yizhu : yizhus) {
            if (yizhu.getOrder_item_name().contains(str)) {
                temp.add(yizhu);
            }
        }
        params.put("yizhu", temp);
        resp.setData(params);
        resp.setResponseCode(ResponseCode.OK);
        wirte(response, resp);
    }


    /**
     * 记录分析后的数据 供机器学习
     *
     * @param response
     * @param map
     */
    @PostMapping("/jiluData")
    public void jiluData(HttpServletResponse response, @RequestBody String map) {
        Map<String, Object> param = new HashMap<>();
        JSONObject jsonObject = JSONObject.parseObject(map);
        List<YizhuResult> tempList = new ArrayList<>();
        //病历id
        String id = jsonObject.getString("id");
        String mainIllName = jsonObject.getString("mainIllName");
        int num = jsonObject.getInteger("num");
        System.out.println("数量为：========" + num);
        String ori1 = jsonObject.getString("ori");
        yizhuResultRepService.deleteAllByBIdAndNum(id, num);
        yizhuChangeRepService.deleteAllByBIdAndNum(id, num);
        yizhuBsjbRepService.deleteAllByBIdAndNum(id, num);
        List<YizhuResult> yizhuResults = JSONArray.parseArray(ori1, YizhuResult.class);
        tempList.addAll(yizhuResults);
        String add = jsonObject.getString("add");
        //伴随疾病
        String bsjb = jsonObject.getString("bsjb");
        //如过是第一次 保存原始医嘱 否则 不保存
        for (YizhuResult yizhuResult : yizhuResults) {
            //病历id
            yizhuResult.setbId(id);
            //次数
            yizhuResult.setNum(num);
            yizhuResult.setMainIllName(mainIllName);
            yizhuResultRepService.save(yizhuResult);
        }
        if (add != null) {
            List<YizhuChange> addChangeList = JSONArray.parseArray(add, YizhuChange.class);
            for (YizhuChange yizhuChange : addChangeList) {
                //病历id
                yizhuChange.setbId(id);
                //次数
                yizhuChange.setNum(num);
                yizhuChangeRepService.save(yizhuChange);
            }
        }
        String delete = jsonObject.getString("delete");
        if (delete != null) {
            List<YizhuResult> deleteList = JSONArray.parseArray(delete, YizhuResult.class);
            for (YizhuResult yizhuResult : deleteList) {
                for (YizhuResult yizhuResult1 : tempList) {
                    String orderItemName = yizhuResult.getOrderItemName();
                    String purpose = yizhuResult.getPurpose();
                    String orderItemName1 = yizhuResult1.getOrderItemName();
                    String purpose1 = yizhuResult1.getPurpose();
                    if (orderItemName.equals(orderItemName1) && purpose.equals(purpose1)) {
                        tempList.remove(yizhuResult1);

                    }
                }

            }

            List<YizhuChange> deleteChangeList = JSONArray.parseArray(delete, YizhuChange.class);
            for (YizhuChange yizhuChange : deleteChangeList) {
                //病历id
                yizhuChange.setbId(id);
                //次数
                yizhuChange.setNum(num);
                yizhuChangeRepService.save(yizhuChange);
            }
        }


        if (bsjb != null) {
            List<YizhuBsjb> deleteList = JSONArray.parseArray(bsjb, YizhuBsjb.class);
            for (YizhuBsjb yizhuBsjb : deleteList) {
                //病历id
                yizhuBsjb.setbId(id);
                //次数
                yizhuBsjb.setNum(num);
                yizhuBsjbRepService.save(yizhuBsjb);
            }
            param.put("bsjb", deleteList);
        }
        param.put("result", tempList);
        wirte(response, param);
    }

    /**
     * 根据id获取之前所有内容
     *
     * @param response
     * @param map
     */
    @PostMapping("/getDataById")
    public void getDataById(HttpServletResponse response, @RequestBody String map) {
        JSONObject jsonObject = JSONObject.parseObject(map);
        Map<Integer, YizhuTestBean> params = new HashMap<>();
        //病历id
        String id = jsonObject.getString("id");
        try {
            int num = yizhuResultRepService.getMaxBid(id);
            for (int i = 1; i <= num; i++) {
                YizhuTestBean tempyizhuTestBean = new YizhuTestBean();

                List<YizhuResult> resultList = yizhuResultRepService.findAllByBIdAndNum(id, i);
                List<YizhuChange> changeList = yizhuChangeRepService.findAllByBIdAndNum(id, i);
                List<YizhuBsjb> nsjbList = yizhuBsjbRepService.findAllByBIdAndNum(id, i);
                tempyizhuTestBean.setBid(id);
                tempyizhuTestBean.setNum(i);
                tempyizhuTestBean.setChangeList(changeList);
                tempyizhuTestBean.setResultList(resultList);
                tempyizhuTestBean.setBsjbList(nsjbList);
                tempyizhuTestBean.setNum(i);
                params.put(i, tempyizhuTestBean);
            }
            wirte(response, params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
            wirte(response, "无数据");
        }

    }

    @PostMapping("/getData1019")
    public void test() {
        List<String> list = ReadFileService.readSourceList("testId");
        List<String> result = new ArrayList<>();
        StringBuilder sb = null;
        for (String id : list) {
            sb = new StringBuilder();
            String cyzd = cdssService.getCyzdByPidAndVid(id);
            String rycz = syzdService.getRyczByPidAndVid(id);
            String admissionTime = basyService.getAdmissionTimByPidAndVid(id);
            Binganshouye beanByPidAndVid = basyService.getBeanByPidAndVid(id);
            if (beanByPidAndVid == null) {
                continue;
            }
            String pat_visit_dept_admission_to_name = beanByPidAndVid.getPat_visit_dept_admission_to_name();
            String pat_visit_dept_discharge_from_name = beanByPidAndVid.getPat_visit_dept_discharge_from_name();
            sb.append(id).append("=").append(rycz).append("=").append(cyzd).append("=").append(pat_visit_dept_admission_to_name).append("=").append(pat_visit_dept_discharge_from_name).append("=").append(admissionTime);

            result.add(sb.toString());
        }
        Write2File.w2fileList(result, "/data/1/CDSS/1019data.txt");
    }
}

package jhmk.clinic.cms.controller.cdss;

import com.alibaba.fastjson.JSONObject;
import jhmk.clinic.cms.SamilarService;
import jhmk.clinic.cms.controller.ruleService.*;
import jhmk.clinic.cms.service.CdssRunRuleService;
import jhmk.clinic.cms.service.CdssService;
import jhmk.clinic.cms.service.TestService;
import jhmk.clinic.cms.service.ZlfaService;
import jhmk.clinic.core.base.BaseController;
import jhmk.clinic.core.config.CdssConstans;
import jhmk.clinic.core.util.HttpClient;
import jhmk.clinic.entity.cdss.CdssDiffBean;
import jhmk.clinic.entity.pojo.repository.SysDiseasesRepository;
import jhmk.clinic.entity.pojo.repository.SysHospitalDeptRepository;
import jhmk.clinic.entity.service.YizhuBsjbRepService;
import jhmk.clinic.entity.service.YizhuChangeRepService;
import jhmk.clinic.entity.service.YizhuOriRepService;
import jhmk.clinic.entity.service.YizhuResultRepService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author ziyu.zhou
 * @date 2018/10/23 16:31
 */
@RequestMapping("/test/analyze")
public class AnalyzeDataController extends BaseController {
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
    ZlfaService zlfaService;
    @Autowired
    RestTemplate restTemplate;

    /**
     * 下拉列表 病历质控所有唯一疾病名
     *
     * @param response
     */
    @PostMapping("/analyzeData2DayHaveCount")
    @ResponseBody
    public void analyzeData2DayHaveCount(HttpServletResponse response, @RequestBody String map) {
        JSONObject jsonObject = JSONObject.parseObject(map);
        String idList = jsonObject.getString("idList");
        String[] split = idList.split(",");
        String jsonStr = cdssService.getJsonStr1(split);
        String s = HttpClient.doPost(CdssConstans.patients, jsonStr);
        List<CdssDiffBean> diffBeanList = cdssService.getDiffBeanList(s);
        List<CdssDiffBean> diffBeanList1 = cdssService.getDiffBeanList(diffBeanList);
        Map<Integer,Integer> stringStatisticsBeanMap = cdssService.analyzeData2DayHaveCount(diffBeanList1);
        wirte(response, stringStatisticsBeanMap);
    }
}

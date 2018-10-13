package jhmk.clinic.cms.controller.cdss;

import com.alibaba.fastjson.JSONObject;
import jhmk.clinic.cms.SamilarService;
import jhmk.clinic.cms.controller.ruleService.*;
import jhmk.clinic.cms.service.CdssRunRuleService;
import jhmk.clinic.cms.service.CdssService;
import jhmk.clinic.cms.service.TestService;
import jhmk.clinic.core.base.BaseController;
import jhmk.clinic.core.util.CompareUtil;
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
import java.util.*;

/**
 * @author ziyu.zhou
 * @date 2018/10/12 18:26
 * 病历质控
 */
@Controller
@RequestMapping("/test/blzk")
public class BlzkController extends BaseController {
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
    RestTemplate restTemplate;

    /**
     * 下拉列表 病历质控所有唯一疾病名
     *
     * @param response
     */
    @PostMapping("/getDistinctIllName")
    @ResponseBody
    public void getDistinctIllName(HttpServletResponse response) {
        List<String> distinctIllName = yizhuResultRepService.getDistinctIllName();
        wirte(response, distinctIllName);
    }

    @PostMapping("/getDistinctBidByIllName")
    @ResponseBody
    public void getDistinctBidByIllName(HttpServletResponse response, @RequestBody(required = false) String map) {
        JSONObject jsonObject = JSONObject.parseObject(map);
        String name = jsonObject.getString("name");
        List<String> distinctIllName = yizhuResultRepService.getDistinctBidByIllName(name);
        List<Map<Integer, YizhuTestBean>> paramsList = new ArrayList<>();
        //病历id

        for (String id : distinctIllName) {
            Map<Integer, YizhuTestBean> params = new HashMap<>();
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
            paramsList.add(params);
        }
        wirte(response, paramsList);
    }

    /**
     * 分析疾病  得到数量
     *
     * @param response
     * @param map
     */
    @PostMapping("/analyzeDistinctBidByIllName")
    @ResponseBody
    public void analyzeDistinctBidByIllName(HttpServletResponse response, @RequestBody(required = false) String map) {
        JSONObject jsonObject = JSONObject.parseObject(map);
        String name = jsonObject.getString("name");
        List<String> distinctIllName = yizhuResultRepService.getDistinctBidByIllName(name);
        List<Map<Integer, YizhuTestBean>> paramsList = new ArrayList<>();
        //病历id

        for (String id : distinctIllName) {
            Map<Integer, YizhuTestBean> params = new HashMap<>();
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
            paramsList.add(params);
        }
        wirte(response, paramsList);
    }

    /**
     * 分析数据 集中治疗方案
     * @param response
     * @param map
     */
    @PostMapping("/analyzeDistinctBidByIllNameAndManager")
    @ResponseBody
    public void analyzeDistinctBidByIllNameAndManager(HttpServletResponse response, @RequestBody(required = false) String map) {
        JSONObject jsonObject = JSONObject.parseObject(map);
        String name = jsonObject.getString("name");
        List<String> distinctIllName = yizhuResultRepService.getDistinctBidByIllName(name);
        List<Map<Integer, YizhuTestBean>> paramsList = new ArrayList<>();
        Map<Integer, List<List<YizhuResult>>> tempMap = new HashMap<>();
        //病历id
        int i = 1;
        for (String id : distinctIllName) {
            List<YizhuResult> yizhuResults = yizhuResultRepService.findAllByBIdAndNum(id, i);
            Collections.sort(yizhuResults, CompareUtil.createComparator(1,"purpose","orderItemName"));
            int yizhuSize = yizhuResults.size();
            if (tempMap.containsKey(yizhuSize)) {
                List<List<YizhuResult>> lists = tempMap.get(yizhuSize);
                lists.add(yizhuResults);
                tempMap.put(yizhuSize, lists);
            } else {
                List<List<YizhuResult>> lists = new ArrayList<>();
                lists.add(yizhuResults);
                tempMap.put(yizhuSize, lists);
            }
        }
        wirte(response, tempMap);
    }
}

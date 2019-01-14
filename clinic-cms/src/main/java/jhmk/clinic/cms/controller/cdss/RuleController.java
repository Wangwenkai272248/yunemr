package jhmk.clinic.cms.controller.cdss;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jhmk.clinic.cms.controller.ruleService.*;
import jhmk.clinic.cms.entity.Rule;
import jhmk.clinic.cms.service.CdssService;
import jhmk.clinic.core.base.BaseController;
import jhmk.clinic.core.config.CdssConstans;
import jhmk.clinic.core.util.DateFormatUtil;
import jhmk.clinic.entity.bean.*;
import jhmk.clinic.entity.model.AtResponse;
import jhmk.clinic.entity.model.ResponseCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ziyu.zhou
 * @date 2018/12/21 16:48
 */
@Controller
@RequestMapping("/rule")
@Api("swaggerRuleController相关的api")
public class RuleController extends BaseController {
    Logger logger = LoggerFactory.getLogger(RuleController.class);
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CdssService cdssService;

    @Autowired
    JybgService jybgService;
    @Autowired
    BasyService basyService;
    @Autowired
    YizhuService yizhuService;
    @Autowired
    SyshService syshService;
    @Autowired
    SyzdService syzdService;
    @Autowired
    ZhuyuanfeiyongService zhuyuanfeiyongService;
    @Autowired
    ScbcjlService scbcjlService;

    /**
     *
     */
    @PostMapping("/labWarnById")
    public void getJybgById(HttpServletResponse response, @RequestBody(required = false) String map) {
        JSONObject object = JSONObject.parseObject(map);
        String id = object.getString("id");
        List<Jianyanbaogao> jianyanbaogaoList = jybgService.gtJybgById(id);
        object.put("jianyanbaogao", jianyanbaogaoList);
        System.out.println(JSONObject.toJSONString(object));
        String result = restTemplate.postForObject("http://192.168.8.20:8011/med/cdss/getTipList.json", object, String.class);
        System.out.println(result);
        wirte(response, result);
//        List<SmShowLog> tipList2ShowLogList = smShowLogService.getTipList2ShowLogList(result);
//        List<SmShowLog> labShowLogList = smShowLogService.getLabShowLogList(tipList2ShowLogList);
//        resp.setData(labShowLogList);
//        resp.setResponseCode(ResponseCode.OK);
//        wirte(response, resp);
    }

    @PostMapping("/getRuleById")
    public void getRuleById(HttpServletResponse response, @RequestBody(required = false) String map) {
        JSONObject object = JSONObject.parseObject(map);
        String id = object.getString("id");
        List<Jianyanbaogao> jianyanbaogaoList = jybgService.gtJybgById(id);
        object.put("jianyanbaogao", jianyanbaogaoList);
        System.out.println(JSONObject.toJSONString(object));
        String result = restTemplate.postForObject("http://192.168.8.20:8011/med/cdss/getTipList.json", object, String.class);
        System.out.println(result);
        wirte(response, result);
//        List<SmShowLog> tipList2ShowLogList = smShowLogService.getTipList2ShowLogList(result);
//        List<SmShowLog> labShowLogList = smShowLogService.getLabShowLogList(tipList2ShowLogList);
//        resp.setData(labShowLogList);
//        resp.setResponseCode(ResponseCode.OK);
//        wirte(response, resp);
    }


    /**
     * 根据patient_id 和visit_id查询
     *
     * @param response
     * @param map
     */

    @ApiOperation(value = "根据id查询鉴别诊断信息", notes = "优先查询mysql，没有数据再查询mongo")
    @PostMapping("/getDataByPIdAndVId")
    public void getDataByPIdAndVId(HttpServletResponse response, @RequestBody(required = false) String map) {
        logger.info("获取到的初始数据为：{}", map);
        JSONObject jsonObject = JSONObject.parseObject(map);
        String pid = jsonObject.getString("pid");
        String vid = jsonObject.getString("vid");
        String id = jsonObject.getString("id");
        if (StringUtils.isEmpty(id)) {
            id = "BJDXDSYY#" + pid + "#" + vid;
        }
        String[] ids = null;
        if(id!=null && id.length()>0){
            ids=id.split("#");
        }
        if(ids.length==3){
            pid = ids[1];
            vid = ids[2];
        }
        Rule rule = new Rule();
        rule.setPatient_id(pid);
        rule.setVisit_id(vid);
        rule.setId(id);
        Binganshouye binganshouye = basyService.getBinganshouyeById(id);
        rule.setBinganshouye(binganshouye);
        List<Yizhu> yizhus = yizhuService.selYizhu(id);
        rule.setYizhu(yizhus);
        List<Shouyezhenduan> shoueyezhenduanBean = syzdService.getShoueyezhenduanBean(id);
        rule.setShouyezhenduan(shoueyezhenduanBean);
        String mainDisease = syzdService.getMainDisease(id);//出院诊断
        String rycz = syzdService.getRycz(id);//入院初诊
        rule.setRycz(rycz);
        rule.setCyzd(mainDisease);
        List<Shouyeshoushu> shouyeshoushu = syshService.getShouyeshoushu(id);
        rule.setShouyeshoushu(shouyeshoushu);
        Shoucibingchengjilu shoucibingcheng = scbcjlService.getShoucibingchengById(id);
        rule.setShoucibingchengjilu(shoucibingcheng);
        Object o = JSONObject.toJSON(rule);
        logger.info("返回的结果数据为：{}", JSONObject.toJSONString(o));

        wirte(response, o);
    }

    /**
     * 使用历史数据获取新版治疗方案
     *
     * @param response
     */
    @RequestMapping("/runZlfaByRule")
    public void runZlfaByRule(HttpServletResponse response, @RequestBody(required = false) String map) {
        List<Rule> gukeDataByCondition = basyService.getGukeDataByCondition(JSONObject.parseObject(map));
        logger.info("数量为：====》》》》{}", gukeDataByCondition.size());
        for (Rule bean : gukeDataByCondition) {
            String id = bean.getId();
            String rycz = cdssService.getRycz(id);
            String cyzd = syzdService.getCyzd(id);
            Float totalFeeById = zhuyuanfeiyongService.getTotalFeeById(id);
            bean.setTotal_costs(totalFeeById);
            //入等于出
            if (StringUtils.isNotBlank(rycz) && rycz.equals(cyzd)) {
                bean.setRycz(rycz);
                bean.setCyzd(cyzd);
            } else {
                continue;
            }
            List<Yizhu> yizhus = yizhuService.selYizhu(id);
            bean.setYizhu(yizhus);
            List<Shouyeshoushu> shouyeshoushu = syshService.getShouyeshoushu(id);
            bean.setShouyeshoushu(shouyeshoushu);
            Object o = JSONObject.toJSON(bean);
            String s = null;
            try {
                s = restTemplate.postForObject(CdssConstans.modelHead + "/bzgj/zlfaZhubiao/saveZlfazhubiaoByRule", o, String.class);
                logger.info(">>>>>>>>>>>>>>>" + s);
            } catch (Exception e) {
                e.printStackTrace();
            }

            wirte(response, s);
        }
    }

    /**
     * 获取 住院时长和 总花费
     *
     * @param response
     * @param map
     */
    @RequestMapping("/getTotalFeeAndHospitalDay")
    public void getTotalFeeAndHospitalDay(HttpServletResponse response, @RequestBody(required = false) String map) {
        JSONObject object = JSONObject.parseObject(map);
        String patientId = object.getString("patientId");
        String visitId = object.getString("visitId");
        String id = "BJDXDSYY#" + patientId + "#" + visitId;
        Float totalFeeById = zhuyuanfeiyongService.getTotalFeeById(id);
        String admissionTime = basyService.getAdmissionTime(id);
        String dischargeTime = basyService.getDischargeTime(id);
        int i = DateFormatUtil.dateDiff1(DateFormatUtil.parseDateBySdf(dischargeTime, DateFormatUtil.DATETIME_PATTERN_SS), DateFormatUtil.parseDateBySdf(admissionTime, DateFormatUtil.DATETIME_PATTERN_SS));
        Map<String, Object> data = new HashMap<>(2);
        data.put("admissionTime", admissionTime);
        data.put("dischargeTime", dischargeTime);
        data.put("total_costs", totalFeeById);
        data.put("in_hospital_days", i);
        wirte(response, data);
    }
    /**
     *
     * 获取特征因素详情
     */
    @RequestMapping("/getFeaturesInfo")
    public void getFeaturesInfo(HttpServletResponse response, @RequestBody(required = false) String map) {
        AtResponse resp=new AtResponse(System.currentTimeMillis());

        logger.info("获取到的初始数据为：{}",map);
        JSONObject jsonObject = JSONObject.parseObject(map);
        String pid = jsonObject.getString("pid");
        String vid = jsonObject.getString("vid");
        String id = jsonObject.getString("id");
        if (StringUtils.isEmpty(id)) {
            id = "BJDXDSYY#" + pid + "#" + vid;
        }
        String shoucibingchengjiluSrc = scbcjlService.getFeaturesInfo(id);
        Object o = JSONObject.toJSON(shoucibingchengjiluSrc);
        logger.info("返回的结果数据为：{}",JSONObject.toJSONString(o));
        resp.setData(shoucibingchengjiluSrc);
        resp.setResponseCode(ResponseCode.OK);
        wirte(response, resp);
    }
}

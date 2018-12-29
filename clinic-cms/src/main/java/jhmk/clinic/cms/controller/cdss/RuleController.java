package jhmk.clinic.cms.controller.cdss;

import com.alibaba.fastjson.JSONObject;
import jhmk.clinic.cms.controller.ruleService.BasyService;
import jhmk.clinic.cms.controller.ruleService.JybgService;
import jhmk.clinic.cms.controller.ruleService.SyzdService;
import jhmk.clinic.cms.controller.ruleService.YizhuService;
import jhmk.clinic.cms.entity.Rule;
import jhmk.clinic.cms.service.CdssService;
import jhmk.clinic.core.base.BaseController;
import jhmk.clinic.entity.bean.Binganshouye;
import jhmk.clinic.entity.bean.Jianyanbaogao;
import jhmk.clinic.entity.bean.Shouyezhenduan;
import jhmk.clinic.entity.bean.Yizhu;
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
import java.util.List;

/**
 * @author ziyu.zhou
 * @date 2018/12/21 16:48
 */
@Controller
@RequestMapping("/rule")
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
    SyzdService syzdService;

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

    @PostMapping("/getDataByPIdAndVId")
    public void getDataByPIdAndVId(HttpServletResponse response, @RequestBody(required = false) String map) {
        logger.info("获取到的初始数据为：{}",map);
        JSONObject jsonObject = JSONObject.parseObject(map);
        String pid = jsonObject.getString("pid");
        String vid = jsonObject.getString("vid");
        String id = jsonObject.getString("id");
        if (StringUtils.isEmpty(id)) {
            id = "BJDXDSYY#" + pid + "#" + vid;
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
        Object o = JSONObject.toJSON(rule);
        logger.info("返回的结果数据为：{}",JSONObject.toJSONString(o));

        wirte(response, o);
    }

}

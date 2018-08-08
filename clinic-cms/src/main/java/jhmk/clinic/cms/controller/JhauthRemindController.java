package jhmk.clinic.cms.controller;

import com.alibaba.fastjson.JSONObject;
import jhmk.clinic.core.base.BaseController;
import jhmk.clinic.core.util.DateFormatUtil;
import jhmk.clinic.entity.model.AtResponse;
import jhmk.clinic.entity.model.ResponseCode;
import jhmk.clinic.entity.pojo.JhauthRemind;
import jhmk.clinic.entity.service.JhauthRemindRepService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author ziyu.zhou
 * @date 2018/8/6 14:02
 */

@Controller
@RequestMapping("test/jhauthRemind")
public class JhauthRemindController extends BaseController {
    @Autowired
    JhauthRemindRepService jhauthRemindRepService;

    /**
     *
     * @param response
     * @param map
     */
    @PostMapping("/findJhauthRemind")
    @ResponseBody
    public void findJhauthRemind(HttpServletResponse response, @RequestBody String map) {
        AtResponse resp = new AtResponse();
        JSONObject jsonObject = JSONObject.parseObject(map);
        String doctorId = jsonObject.getString("doctorId");
        String patiendId = jsonObject.getString("patiendId");
        String remindStatus = jsonObject.getString("remindStatus");
        String remindTime = jsonObject.getString("remindTime");
        Date date = null;
        if (StringUtils.isNotBlank(remindStatus)) {
            date = DateFormatUtil.parseDate(remindTime, DateFormatUtil.DATE_PATTERN_S);
        }

        List<JhauthRemind> jhauthRemind = jhauthRemindRepService.findJhauthRemind(doctorId, patiendId, remindStatus, date);
        jhauthRemind.forEach(s -> s.setRemindDate(DateFormatUtil.formatBySdf(s.getRemindTime(), DateFormatUtil.DATE_PATTERN_S)));
        resp.setData(jhauthRemind);
        resp.setResponseCode(ResponseCode.OK);
        wirte(response, resp);
    }

//    @PostMapping("/findJhauthRemindByDoctorId")
//    @ResponseBody
//    public void findJhauthRemindByDoctorId(HttpServletResponse response, @RequestBody String map) {
//        AtResponse resp = new AtResponse();
//        JSONObject jsonObject = JSONObject.parseObject(map);
//        String doctorId = jsonObject.getString("doctorId");
//        String remindStatus = jsonObject.getString("remindStatus");
//        List<JhauthRemind> jhauthRemindByDoctorId = jhauthRemindRepService.findJhauthRemindByDoctorId(doctorId, remindStatus);
//        resp.setData(jhauthRemindByDoctorId);
//        resp.setResponseCode(ResponseCode.OK);
//        wirte(response, resp);
//    }

    /**
     * 查询当前医生 所有未提醒信息
     * @param response
     * @param map
     */
    @PostMapping("/findAllJhauthRemindByRemindTime")
    @ResponseBody
    public void findAllJhauthRemindByRemindTime(HttpServletResponse response, @RequestBody String map) {
        AtResponse resp = new AtResponse();
        JSONObject jsonObject = JSONObject.parseObject(map);
        String doctorId = jsonObject.getString("doctorId");
        String remindStatus = jsonObject.getString("remindStatus");
        Date remindTime = jsonObject.getDate("remindTime");
        List<JhauthRemind> jhauthRemindByDoctorId = jhauthRemindRepService.findAllJhauthRemindByRemindTime(doctorId, remindStatus, remindTime);
        Map<String, List<JhauthRemind>> resultMap = new HashMap<>();
        for (JhauthRemind jhauthRemind : jhauthRemindByDoctorId) {
            if (resultMap.containsKey(jhauthRemind.getPatiendId())) {
                List<JhauthRemind> jhauthReminds = resultMap.get(jhauthRemind.getPatiendId());
                jhauthReminds.add(jhauthRemind);
                resultMap.put(jhauthRemind.getPatiendId(), jhauthReminds);
            } else {
                List<JhauthRemind> jhauthReminds = new LinkedList<>();
                jhauthReminds.add(jhauthRemind);
                resultMap.put(jhauthRemind.getPatiendId(), jhauthReminds);
            }
        }
        resp.setData(jhauthRemindByDoctorId);
        resp.setResponseCode(ResponseCode.OK);
        wirte(response, resultMap);
    }


    @PostMapping("/updateStatus")
    @ResponseBody
    public void updateStatus(HttpServletResponse response, @RequestBody String map) {
        AtResponse resp = new AtResponse();
        JSONObject jsonObject = JSONObject.parseObject(map);
        int id = jsonObject.getInteger("id");
        String remindStatus = jsonObject.getString("remindStatus");
        jhauthRemindRepService.updateStatus(remindStatus, id);
        resp.setResponseCode(ResponseCode.OK);
        wirte(response, resp);
    }

    @PostMapping(value = "/add")
    @ResponseBody
    public AtResponse save(@RequestBody JhauthRemind jhauthRemind) {
        JhauthRemind save = jhauthRemindRepService.save(jhauthRemind);
        AtResponse<Map<String, Object>> resp = new AtResponse(System.currentTimeMillis());
        resp.setResponseCode(ResponseCode.OK);
        return resp;
    }


    //删除
    @RequestMapping(value = "/delete")
    @ResponseBody
    public AtResponse<String> delete(@RequestBody(required = true) String map) {
        JSONObject jsonObject = JSONObject.parseObject(map);
        Integer id = jsonObject.getInteger("id");
        jhauthRemindRepService.delete(id);
        String message;
        AtResponse<String> resp = new AtResponse(System.currentTimeMillis());
        message = "删除成功";
        resp.setResponseCode(ResponseCode.OK);
        resp.setData(message);
        return resp;

    }


    @RequestMapping(value = "/view")
    @ResponseBody
    public AtResponse view(@RequestBody String map) {
        JSONObject jsonObject = JSONObject.parseObject(map);
        Integer id = jsonObject.getInteger("id");
        JhauthRemind one = jhauthRemindRepService.findOne(id);
        AtResponse resp = new AtResponse(System.currentTimeMillis());
        if (one != null) {
            resp.setResponseCode(ResponseCode.OK);
        } else {
            resp.setResponseCode(ResponseCode.INERERROR);
        }
        resp.setData(one);
        return resp;

    }

    @RequestMapping(value = "/editor")
    @ResponseBody
    public AtResponse edit(@RequestBody JhauthRemind dept) {
        AtResponse<Object> resp = new AtResponse(System.currentTimeMillis());
        JhauthRemind save = jhauthRemindRepService.save(dept);
        if (save != null) {
            resp.setResponseCode(ResponseCode.OK);
            resp.setData(save);
        } else {
            resp.setResponseCode(ResponseCode.INERERROR);
            resp.setData(save);
        }
        return resp;
    }
}

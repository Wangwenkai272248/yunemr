//package com.jhmk.cliniccms.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import jhmk.clinic.core.base.BaseController;
//import jhmk.clinic.core.model.AtResponse;
//import jhmk.clinic.core.model.ResponseCode;
//import jhmk.clinic.entity.service.PatVisitService;
//import jhmk.clinic.entity.sys.entity.PageVO;
//import jhmk.clinic.entity.sys.entity.PatVisitDO;
//import jhmk.clinic.entity.sys.entity.request.UserRequest;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import javax.servlet.http.HttpServletResponse;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Controller
//public class PatVisitControl extends BaseController {
//
//
//    @Autowired
//    private PatVisitService patVisitService;
//
//
//    private static final Logger logger = LoggerFactory.getLogger(PatVisitControl.class);
//
//    /**
//     * "pageNo":1,
//     * "iPageSize":20,
//     * "patientName":"", 病人姓名 张三
//     * "visitDoctor":  kongxinlin
//     *
//     * @param response
//     * @param map
//     */
//    @PostMapping("/PatVisit")
//    public void getPatVisit(HttpServletResponse response, @RequestBody String map) {
//
//        JSONObject parse = JSONObject.parseObject(map);
//        UserRequest userRequest = new UserRequest();
//        userRequest.setCurrPageNo(parse.getInteger("pageNo"));//当前页码
//        userRequest.setLimit(parse.getInteger("iPageSize"));//每页数据量
//
//        if (StringUtils.isNotBlank(parse.getString("patientName"))) {
//            userRequest.setPatientName(parse.getString("patientName"));
//        }
//        if (StringUtils.isNotBlank(parse.getString("visitDoctor"))) {
//            userRequest.setVisitDoctor(parse.getString("visitDoctor"));
//        }
//        if (StringUtils.isNotBlank(parse.getString("state"))) {
//            userRequest.setState(parse.getString("state"));
//        }
//
//        PageVO<PatVisitDO> pageVO = patVisitService.getAllPatVisit(userRequest);
//        List<PatVisitDO> lstUsers = pageVO.getDatas();
//
//        AtResponse atResponse = new AtResponse();
//        Map mav = new HashMap();
////        mav.setViewName("patvisit");
//        mav.put("patvisit", lstUsers);
//        mav.put("pageVO", pageVO);
//        atResponse.setData(mav);
//        atResponse.setResponseCode(ResponseCode.OK);
//
//        wirte(response, atResponse);
//    }
//
//    @PostMapping("/addPatVisit")
//    public void addPatVisit(HttpServletResponse response, @RequestBody PatVisitDO patVisitVO) {
//        AtResponse resp = new AtResponse();
//        patVisitVO.setState("未就诊");
//        patVisitVO.setVisitDate(new Date());
//        patVisitService.insert(patVisitVO);
//        resp.setResponseCode(ResponseCode.OK);
//        wirte(response, resp);
//    }
//
//    @PostMapping("/api/batchPatVisit")
//    public void createBatchCity(HttpServletResponse response, @RequestBody List<PatVisitDO> lstPatVisit) {
//        AtResponse resp = new AtResponse();
//        for (PatVisitDO patVisitVO : lstPatVisit) {
//            patVisitService.insert(patVisitVO);
//        }
//        resp.setResponseCode(ResponseCode.OK);
//        wirte(response, resp);
//    }
//
//    @PostMapping(value = "/api/AddPatVisit")
//    public void createCity(HttpServletResponse response,@RequestBody PatVisitDO patVisitVO) {
//        patVisitService.insert(patVisitVO);
//    }
//
//    @PostMapping(value = "/api/updatePatVisit")
//    public void modifyCity(HttpServletResponse response,@RequestBody PatVisitDO patVisitVO) {
////        patVisitService.updateCity(patVisitVO);
//    }
//}

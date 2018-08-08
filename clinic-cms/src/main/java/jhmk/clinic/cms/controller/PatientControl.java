//package jhmk.clinic.cms.controller;
//
//
//import jhmk.clinic.core.base.BaseController;
//import jhmk.clinic.entity.model.AtResponse;
//import jhmk.clinic.entity.model.ResponseCode;
//import jhmk.clinic.entity.pojo.ShiroUser;
//import jhmk.clinic.entity.pojo.repository.ShiroUserRepository;
//import org.apache.shiro.SecurityUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpServletResponse;
//import java.util.List;
//
//@Controller
//@RequestMapping("clinic/patient")
//public class PatientControl extends BaseController {
//
//    @Autowired
//    private ShiroUserRepository repository;
//
//    /**
//     * 获取所有病病人信息
//     *
//     * @param response
//     */
////    @RequiresPermissions(value = "clinic/patient/getPatientList")
//    @GetMapping(path = "/getPatientList")
//    public void findAllPatients(HttpServletResponse response) {
//
//        boolean[] permitted = SecurityUtils.getSubject().isPermitted();
//        AtResponse resp = new AtResponse();
//        List<ShiroUser> all = repository.findAll();
//        resp.setResponseCode(ResponseCode.OK);
//        resp.setData(all);
//        wirte(response, resp);
//    }
//
//    /**
//     * 获取病病人信息（分页 条件查询）
//     *
//     * @param response
//     */
////    @PostMapping("/getPatientByPage")
////    public void getPatientByPage(HttpServletResponse response, @RequestBody String data) {
////        AtResponse resp = new AtResponse();
////        List<PatVisitDO> listPatient = iPatientListService.findAllPatients();
////        resp.setResponseCode(ResponseCode.OK);
////        resp.setData(listPatient);
//
////        wirte(response, resp);
////    }
////
////
////    @PostMapping(path = "findByPatientName")
////    public void findByPatientName(HttpServletResponse response, @RequestBody String data) {
////        Map<String, String> parse = (Map) JSONObject.parse(data);
////        AtResponse resp = new AtResponse();
////        List<PatVisitDO> listPatient = iPatientListService.findByPatientName(parse.get("patientName"));
////        resp.setResponseCode(ResponseCode.OK);
////        resp.setData(listPatient);
////        wirte(response, resp);
////
////    }
//
//}

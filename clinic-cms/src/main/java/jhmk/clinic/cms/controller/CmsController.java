//package jhmk.clinic.cms.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import jhmk.clinic.core.base.BaseController;
//import jhmk.clinic.entity.model.AtResponse;
//import jhmk.clinic.entity.model.ResponseCode;
//import jhmk.clinic.entity.pojo.shiro.UserInfo;
//import jhmk.clinic.entity.pojo.shiro.repository.UserInfoRepository;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.*;
//import org.apache.shiro.subject.Subject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//@Controller
//@RequestMapping("/cms")
//public class CmsController extends BaseController {
////    @Autowired
//    UserInfoRepository userInfoRepository;
//
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    @ResponseBody
//    public void login(HttpServletResponse response, @RequestBody(required = true) String map, String vcode, Boolean rememberMe) {
//        AtResponse resp = new AtResponse();
//        JSONObject jsonObject = JSONObject.parseObject(map);
//        String username = jsonObject.getString("username");
//        System.out.println(username);
//        String password = jsonObject.getString("password");
//
//        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
//        Subject subject = SecurityUtils.getSubject();
////        onlineSessionManager.addOnlineSession(subject.getSession().getId());
//        try {
//            subject.login(usernamePasswordToken);
//            System.out.println("身份认证成功！");
//            resp.setResponseCode(ResponseCode.OK);
//            UserInfo byUsername = userInfoRepository.findByUsername(username);
//            Object token = jsonObject.put("token", subject.getSession().getId());
//            resp.setData(token);
//            subject.getSession().setAttribute("userLogin", byUsername);
//        } catch (UnknownAccountException e) {
//            e.printStackTrace();
//            System.out.println("账号不存在！");
//            resp.setResponseCode(ResponseCode.INERERROR3);
//
//        } catch (LockedAccountException e) {
//            e.printStackTrace();
//            System.out.println("账号被锁定！");
//            resp.setResponseCode(ResponseCode.INERERROR1);
//
//
//        } catch (DisabledAccountException e) {
//            e.printStackTrace();
//            System.out.println("账号被禁用！");
//            resp.setResponseCode(ResponseCode.INERERROR4);
//
//
//        } catch (IncorrectCredentialsException e) {
//            e.printStackTrace();
//            System.out.println("凭证/密码错误！");
//            resp.setResponseCode(ResponseCode.INERERROR2);
//
//        } catch (ExpiredCredentialsException e) {
//            e.printStackTrace();
//            System.out.println("凭证/密码过期！");
//            resp.setResponseCode(ResponseCode.INERERROR2);
//
//
//        } catch (ExcessiveAttemptsException e) {
//            e.printStackTrace();
//            System.out.println("登录失败次数过多！");
//
//        }
//        // 是否认证通过
//        boolean isAuthenticated1 = subject.isAuthenticated();
//        System.out.println("登录后,是否认证通过：" + isAuthenticated1);
//        wirte(response, resp);
//    }
//
//    @RequestMapping(value = "/loginOut", method = RequestMethod.POST)
//    @ResponseBody
//    public void loginOut(HttpServletResponse response, @RequestBody(required = true) String map, String vcode, Boolean rememberMe) {
//        Subject subject = SecurityUtils.getSubject();
//        subject.logout();
//        // 是否认证通过
//        boolean isAuthenticated2 = subject.isAuthenticated();
//        System.out.println("退出登录后,是否认证通过：" + isAuthenticated2);
//        wirte(response, "loginOut");
//    }
//
//
//    @PostMapping("/401")
//    @ResponseBody
//    public void runRuleZhenDuan(HttpServletResponse response) throws IOException {
//        wirte(response, "401");
//    }
//
//    @RequestMapping(value = "/unauth")
//    @ResponseBody
//    public Object unauth() {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("code", "1000000");
//        map.put("msg", "未登录");
//        return map;
//    }
//}

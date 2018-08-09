package jhmk.clinic.cms.function.otolaryngology;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author ziyu.zhou
 * @date 2018/8/8 16:48
 */

@Controller
@RequestMapping("test")
public class ThreeHospitalController {
    @Autowired
    ThreeService threeService;
    @RequestMapping("/getDeptIllCount")
    public void getDeptIllCount(){
        Map<String, Map<String, Integer>> resultData = threeService.getResultData();
        String s = threeService.sortMap(resultData);
        threeService.write2file(s);
        System.out.println("斜土文件成功");
    }

}

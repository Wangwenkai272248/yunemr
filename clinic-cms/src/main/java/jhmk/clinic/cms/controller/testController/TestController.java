package jhmk.clinic.cms.controller.testController;

import jhmk.clinic.cms.controller.ruleService.BasyService;
import jhmk.clinic.cms.controller.ruleService.SyzdService;
import jhmk.clinic.cms.function.demo1.Demo1Service;
import jhmk.clinic.cms.service.Write2File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author ziyu.zhou
 * @date 2018/9/27 14:51
 */
@Controller
@RequestMapping("/test/tests")
public class TestController {
    @Autowired
    Demo1Service demo1Service;
    @Autowired
    BasyService basyService;
    @Autowired
    SyzdService syzdService;

    @PostMapping("/demo1")
    public void getAllData(HttpServletResponse response, @RequestBody(required = false) String map) {
        Map<String, Integer> resuiltMap = new HashMap<>();
        Set<String> syzdByDiseaseName = syzdService.getSyzdByDiseaseName(map);
        Map<String, Set<String>> stringSetMap = demo1Service.selDrugYizhuById(syzdByDiseaseName);
        for (Map.Entry<String, Set<String>> entry : stringSetMap.entrySet()) {
            Set<String> value = entry.getValue();
            for (String name : value) {
                if (resuiltMap.containsKey(name)) {
                    resuiltMap.put(name, resuiltMap.get(name) + 1);
                } else {
                    resuiltMap.put(name, 1);

                }

            }
        }
        ArrayList<Map.Entry<String, Integer>> entries = new ArrayList<>(resuiltMap.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        List<String> data = new ArrayList<>();
        for (Map.Entry<String, Integer> enrty : entries) {
            data.add(enrty.getKey() + ":" + enrty.getValue());
        }
        String fileNmae = "/data/1/CDSS/" + map + ".txt";
        Write2File.w2fileList(data, fileNmae);
        System.out.println("写完啦");
    }
}

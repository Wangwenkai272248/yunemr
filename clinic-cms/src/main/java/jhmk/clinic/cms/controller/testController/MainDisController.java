package jhmk.clinic.cms.controller.testController;

import jhmk.clinic.cms.controller.ruleService.BasyService;
import jhmk.clinic.cms.controller.ruleService.SyzdService;
import jhmk.clinic.cms.service.Write2File;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author ziyu.zhou
 * @date 2018/9/11 15:07
 */
@Controller
@RequestMapping("/test/dis")
public class MainDisController {
    @Autowired
    BasyService basyService;
    @Autowired
    SyzdService syzdService;

    //获取出院主诊断+个数
    @PostMapping("/getAllData")
    public void getAllData(HttpServletResponse response) {

        Map<String, Integer> allData = syzdService.getAllData();
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(allData.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        List<String> list = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : entries) {
            list.add(entry.getKey() + "/" + entry.getValue());
        }
        Write2File.w2fileList(list, "/data/1/CDSS/allData.txt");
    }

    //获取出院主诊断+个数
    @PostMapping("/getAllDataByNotDept")
    public void getAllDataByNotDept(HttpServletResponse response) {
        List<String> ids = basyService.getDataByNoDept("儿科");
        Map<String, Integer> allData = new HashMap<>();
        for (String id : ids) {
            String mainDisease = syzdService.getMainDisease(id);
            if (StringUtils.isEmpty(mainDisease)) {
                continue;
            }
            mainDisease = mainDisease.replaceAll("\\(", "（").replaceAll("\\)", "）");
            if (allData.containsKey(mainDisease)) {
                allData.put(mainDisease, allData.get(mainDisease) + 1);
            } else {
                allData.put(mainDisease, 1);
            }
        }
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(allData.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        List<String> list = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : entries) {
            list.add(entry.getKey() + "/" + entry.getValue());
        }
        Write2File.w2fileList(list, "/data/1/CDSS/allData.txt");
    }
}

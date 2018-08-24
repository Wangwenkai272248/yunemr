package jhmk.clinic.cms.function.demo1;

import jhmk.clinic.core.base.BaseController;
import jhmk.clinic.core.util.ThreadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author ziyu.zhou
 * @date 2018/8/24 13:16
 */
@Controller
@RequestMapping("/test")
public class Demo1Controller extends BaseController {
    @Autowired
    Demo1Service demo1Service;

    @PostMapping("/demo1")
    public void getData() {
        ThreadUtil.ThreadPool instance = ThreadUtil.getInstance();
        List<Demo1Bean> list = new ArrayList<>();
        Set<String> idsByIllName = demo1Service.getIdsByIllName("慢性阻塞性肺疾病急性加重");
        for (String id : idsByIllName) {
            Demo1Bean demo1Bean = new Demo1Bean();
            demo1Bean.setId(id);
            String age = demo1Service.getAgeValueById(id);
            demo1Bean.setAge(age);
            double inHosoitalDayById = demo1Service.getInHosoitalDayById(id);
            demo1Bean.setInHospitalDay(inHosoitalDayById);
            double totalFeeById = demo1Service.getTotalFeeById(id);
            demo1Bean.setFee(totalFeeById);
            List<String> strDataList = demo1Service.getStrDataList();
            List<Map<String, String>> maps = demo1Service.selYizhu(id, strDataList);
            demo1Bean.setDrugList(maps);
            if (maps.size() == 0) {
                continue;
            }
            list.add(demo1Bean);
        }
        demo1Service.write2File(list);

    }

    @PostMapping("/demo1ByThread")
    public void demo1ByThread() {
        ExecutorService exec = Executors.newFixedThreadPool(32);
        List<Demo1Bean> list = new ArrayList<>();
        Set<String> idsByIllName = demo1Service.getIdsByIllName("慢性阻塞性肺疾病急性加重");
        for (String id : idsByIllName) {
            Callable<Demo1Bean> callable = new Callable<Demo1Bean>() {
                @Override
                public Demo1Bean call() throws Exception {
                    Demo1Bean demo1Bean = new Demo1Bean();
                    demo1Bean.setId(id);
                    String age = demo1Service.getAgeValueById(id);
                    demo1Bean.setAge(age);
                    double inHosoitalDayById = demo1Service.getInHosoitalDayById(id);
                    demo1Bean.setInHospitalDay(inHosoitalDayById);
                    double totalFeeById = demo1Service.getTotalFeeById(id);
                    demo1Bean.setFee(totalFeeById);
                    List<String> strDataList = demo1Service.getStrDataList();
                    List<Map<String, String>> maps = demo1Service.selYizhu(id, strDataList);
                    demo1Bean.setDrugList(maps);
                    if (maps.size() > 0) {
                        return demo1Bean;
                    }
                    return null;
                }
            };
            Future<Demo1Bean> submit = exec.submit(callable);
            try {
                if (submit != null) {

                    Demo1Bean demo1Bean = submit.get();
                    if (demo1Bean != null) {
                        list.add(demo1Bean);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


        }
        demo1Service.write2File(list);

    }


}

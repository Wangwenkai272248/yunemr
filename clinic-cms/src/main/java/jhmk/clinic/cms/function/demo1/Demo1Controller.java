package jhmk.clinic.cms.function.demo1;

import com.alibaba.fastjson.JSONObject;
import jhmk.clinic.cms.service.ReadFileService;
import jhmk.clinic.cms.service.Write2File;
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
            String inHosoitalDayById = demo1Service.getInHosoitalDayById(id);
            demo1Bean.setInHospitalDay(inHosoitalDayById);
            double totalFeeById = demo1Service.getTotalFeeById(id);
            demo1Bean.setFee(totalFeeById);
            List<String> strDataList = demo1Service.getStrDataList();
//            List<Map<String, String>> maps = demo1Service.selYizhu(id, strDataList);
//            demo1Bean.setDrugList(maps);
////            if (maps.size() == 0) {
////                continue;
////            }
            list.add(demo1Bean);
        }
        demo1Service.write2File(list);

    }

    @PostMapping("/demo1AllByThread")
    public void demo1AllByThread() {
        System.out.println("=======================进来啦");
        ExecutorService exec = Executors.newFixedThreadPool(32);
        List<Demo1Bean> list = new ArrayList<>();
        List<String> strDataList = demo1Service.getStrDataList();
        List<String> drudList = demo1Service.getDrudList();
        Set<String> idsByIllName = demo1Service.getIdsByIllName("慢性阻塞性肺疾病急性加重");
        System.out.println("主诊断为慢性阻塞性肺疾病急性加重总共数量为：============================" + idsByIllName.size());
        for (String id : idsByIllName) {
            Callable<Demo1Bean> callable = new Callable<Demo1Bean>() {
                @Override
                public Demo1Bean call() throws Exception {
                    Demo1Bean demo1Bean = new Demo1Bean();
                    demo1Bean.setId(id);
                    String age = demo1Service.getAgeValueById(id);
                    demo1Bean.setAge(age);
                    String inHosoitalDayById = demo1Service.getInHosoitalDayById(id);
                    demo1Bean.setInHospitalDay(inHosoitalDayById);
                    double totalFeeById = demo1Service.getTotalFeeById(id);
                    demo1Bean.setFee(totalFeeById);

                    List<Map<String, String>> maps = demo1Service.selYizhu(id, strDataList, drudList);
                    demo1Bean.setDrugList(maps);
                    return demo1Bean;

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
        System.out.println("======================结束啦");
    }

    @PostMapping("/demo1BygetDataByIds")
    public void getDataByIds() {
        Set<String> ids = demo1Service.getIds();
        List<String>list=new ArrayList<>();
        for (String id : ids) {
            StringBuffer sb=new StringBuffer(id);
            sb.append(",");
            List<Map<String, String>> shouYeZhenDuan = cdssService.getShouYeZhenDuan(id);
            for (Map<String, String> map:shouYeZhenDuan) {
                sb.append(map.get("diagnosis_name")).append("/");
            }
            sb.append(",");

            List<Map<String, String>> maps = cdssService.selYizhu(id);
            for (Map<String, String> map:maps) {
                sb.append(map.get("order_item_name")).append("/");
            }
            list.add(sb.toString());
        }
        demo1Service.write2FileByStr(list);
    }


    @PostMapping("/demo1ByThread")
    public void demo1ByThread() {
        System.out.println("=======================进来啦");
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
                    String inHosoitalDayById = demo1Service.getInHosoitalDayById(id);
                    demo1Bean.setInHospitalDay(inHosoitalDayById);
                    double totalFeeById = demo1Service.getTotalFeeById(id);
                    demo1Bean.setFee(totalFeeById);
                    List<String> strDataList = demo1Service.getStrDataList();
                    List<String> drudList = demo1Service.getDrudList();
                    List<Map<String, String>> maps = demo1Service.selYizhu(id, strDataList,drudList);
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
        System.out.println("======================结束啦");
    }

    @PostMapping("/getYizhuByIdy")
    public void getYizhuById() {
        Set<String> ids = ReadFileService.readSource("ids");
        Map<String, Set<String>> stringSetMap = demo1Service.selYizhuById(ids);
        String s = JSONObject.toJSONString(stringSetMap);
        String fileNmae="/data/1/CDSS/idAndYizhu.txt";
        Write2File.wfile(s,fileNmae);
    }

    @PostMapping("/getMainYizhuById")
    public void getMainYizhuById() {
        Set<String> ids = ReadFileService.readSource("ids");
        List<Demo1ZhenduanBean> beanList = demo1Service.selShouyezhenduan(ids);

        String fileNmae = "/data/1/CDSS/mainidAndYizhu.txt";
//        String fileNmae="C:/嘉和美康文档/3院测试数据/data.txt";
        Write2File.wfile(beanList, fileNmae);
    }
}

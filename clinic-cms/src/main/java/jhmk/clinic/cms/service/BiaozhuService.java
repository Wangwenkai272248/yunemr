package jhmk.clinic.cms.service;

import jhmk.clinic.cms.SamilarService;
import jhmk.clinic.entity.cdss.CdssRuleBean;
import jhmk.clinic.entity.cdss.Date1206;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

/**
 * @author ziyu.zhou
 * @date 2018/10/22 13:10
 */
@Service
public class BiaozhuService {
    @Autowired
    SamilarService samilarService;

    //获取优质病例
    public List<String> getGoodBingli() {
        List<String> list = readSourceList("testId");
        return list;

    }   //获取优质病例

    public List<String> get3HosputalGoodBingli() {
        List<String> list = readSourceListNew("GoodRecords");
        return list;

    }

    public static List<String> readSourceList(String name) {
        List<String> liiNames = new LinkedList<>();
        Resource resource = new ClassPathResource(name);
        File file = null;
        BufferedReader br = null;
        try {
            file = resource.getFile();
            br = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = br.readLine()) != null) {
                StringBuilder sb = new StringBuilder();
                String[] split = line.split(",");
                sb.append("BJDXDSYY##2#").append(split[0]).append("#").append(split[1]);
                liiNames.add(sb.toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return liiNames;
    }

    public static List<String> readSourceListNew(String name) {
        List<String> liiNames = new LinkedList<>();
        Resource resource = new ClassPathResource(name);
        File file = null;
        BufferedReader br = null;
        try {
            file = resource.getFile();
            br = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = br.readLine()) != null) {

                liiNames.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return liiNames;
    }

    public void method2(List<CdssRuleBean> resultList) {
        System.out.println("总数量为："+resultList.size());
        Map<String, Date1206> map = new HashMap<>();
        //在excel表中添加表头
        for (CdssRuleBean bean : resultList) {
            String doctor_id = bean.getDoctor_id();
            String rycz = bean.getRycz();
            String cyzd = bean.getCyzd();
            String admission_time = bean.getAdmission_time().substring(0, 7);
            boolean fatherAndSon = samilarService.isFatherAndSon(rycz, cyzd);
            String bz = doctor_id + "/" + admission_time;
            if (map.containsKey(bz)) {
                Date1206 date1206 = map.get(bz);
                if (fatherAndSon) {
                    date1206.setAll(date1206.getAll() + 1);
                    date1206.setCurrect(date1206.getCurrect() + 1);
                } else {
                    date1206.setError(date1206.getError() + 1);
                    date1206.setAll(date1206.getAll() + 1);
                }
                map.put(bz, date1206);
            } else {
                Date1206 date1206 = new Date1206();
                if (fatherAndSon) {
                    date1206.setAll(1);
                    date1206.setCurrect(1);
                    date1206.setError(0);
                    date1206.setDoctorId(bz);
                } else {
                    date1206.setAll(1);
                    date1206.setCurrect(0);
                    date1206.setError(1);
                    date1206.setDoctorId(bz);
                }
                map.put(bz, date1206);
            }
        }
        List<String> stringList = new ArrayList<>();
        for (Map.Entry<String, Date1206> entry : map.entrySet()) {
            String key = entry.getKey();
            Date1206 value = entry.getValue();
            float all = value.getAll();
            float currect = value.getCurrect();
            float error = value.getError();
            float avg = currect / all;
            stringList.add(key + "/" + currect + "/" + error + "/" + all + "/" + avg);
        }
        Collections.sort(stringList);

        Write2File.w2fileList(stringList, "/data/1/CDSS/avg.txt" );
//        Write2File.w2fileList(stringList, "C:/嘉和美康文档/3院测试数据/avg.txt" );
        System.out.println("平均数写入完啦===========");

    }

    public static void main(String[] args) {
        int a = 5;
        int b = 2;
        float i = a / b;
        System.out.println(i);
    }
}

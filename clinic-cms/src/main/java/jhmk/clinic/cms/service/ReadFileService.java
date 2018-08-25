package jhmk.clinic.cms.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.*;

/**
 * @author ziyu.zhou
 * @date 2018/8/25 11:03
 */

public class ReadFileService {
    public static List<String> readFile2List(String fileName) {
        List<String> list = new LinkedList<>();
        File file = new File(fileName);
        if (!file.isFile()) {
            return null;
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line != null && !"".equals(line.trim())) {

                    list.add(line);
                }
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
        return list;
    }


    public static Set<String> readSource(String name) {
        Set<String> liiNames = new LinkedHashSet<>();
        Resource resource = new ClassPathResource(name);
        File file = null;
        BufferedReader br = null;
        try {
            file = resource.getFile();
            br = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = br.readLine()) != null) {
                String ill = line.replaceAll("\\(", "（").replaceAll("\\)", "）");
                liiNames.add(ill);
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

    /**
     * 出院主诊断=慢性阻塞性肺疾病急性加重，数量为xxx  平均住院天数  平均花费费用
     * 对医嘱进行统计，
     * 单独使用支气管扩张药的患者数量xxx，
     * 既使用支气管扩张药，又使用肾上腺皮质激素类的患者数量，xxx
     * 既使用支气管扩张药，又使用肾上腺皮质激素类的患者数量中，肾上腺皮质激素类单独使用口服药的数量
     * 既使用支气管扩张药，又使用肾上腺皮质激素类的患者数量中，肾上腺皮质激素类单独使用吸入药数量
     * 既使用支气管扩张药，又使用肾上腺皮质激素类的患者数量中，肾上腺皮质激素类即使用口服药又使用吸入药的数量
     *
     * @param args
     */
    public static void main(String[] args) {

//        Map all = getAll();
//        geName1();
        geName2();
//        geName3();
//        geName4();
//        geName5();

    }

    static String fileName = "C:/嘉和美康文档/3院测试数据/用药统计/三院.txt";
//    static String fileName = "C:/嘉和美康文档/3院测试数据/用药统计/朝阳.txt";

    public static Map getAll() {
        int allAge = 0;
        int allday = 0;
        double allFee = 0;

        List<String> list = readFile2List(fileName);
        System.out.println("诊断=慢性阻塞性肺疾病急性加重，数量为" + list.size());
        Map<String, Integer> resultMap = new HashMap<>();
        double d = 0;
        for (String line : list) {
            String[] split = line.split("/");
            String id = split[0];
            int age = Integer.valueOf(split[1]);
            //总共年龄
            allAge += age;
            double fee = Double.valueOf(split[2]);
            //总共费用
            allFee += fee;
            int inHospitalDay = Integer.valueOf(split[3]);
            allday += inHospitalDay;
            String str = split[4];
//            List<Map<String, String>> mapList = JSONObject.parseObject(str, List.class);
        }
        System.out.println("诊断=慢性阻塞性肺疾病急性加重，平均费用" + allFee / list.size());
        System.out.println("诊断=慢性阻塞性肺疾病急性加重，平均天数" + allday / list.size());

        return resultMap;
    }

    static String name1 = "单独使用支气管扩张药的患者数量";
    static String name2 = "既使用支气管扩张药，又使用肾上腺皮质激素类的患者数量";
    static String name3 = "既使用支气管扩张药，又使用肾上腺皮质激素类的患者数量中，肾上腺皮质激素类单独使用口服药的数量";
    static String name4 = "既使用支气管扩张药，又使用肾上腺皮质激素类的患者数量中，肾上腺皮质激素类单独使用吸入药的数量";
    static String name5 = "既使用支气管扩张药，又使用肾上腺皮质激素类的患者数量中，肾上腺皮质激素类即使用口服药又使用吸入药的数量";


    //单独使用支气管扩张药的患者数量xxx，
    public static void geName1() {

        int allAge = 0;
        double allFee = 0;
        double allinHospitalDay = 0;

        List<String> list = readFile2List(fileName);
        Map<String, Integer> resultMap = new HashMap<>();
        double d = 0;
        for (String line : list) {
            String[] split = line.split("/");
            String id = split[0];
            int age = Integer.valueOf(split[1]);
            //总共年龄
            //总共费用
            int inHospitalDay = Integer.valueOf(split[3]);
            String str = split[4];
            double fee = Double.valueOf(split[2]);
            if (str.contains("支气管扩张药") && !str.contains("肾上腺皮质激素类")) {
                allinHospitalDay += inHospitalDay;
                allAge += age;
                allFee += fee;
                if (resultMap.containsKey(name1)) {
                    resultMap.put(name1, resultMap.get(name1) + 1);
                } else {
                    resultMap.put(name1, 1);
                }
            }

        }
        Integer integer = resultMap.get(name1);
        int i = allAge / integer;
        double v = allFee / integer;
        double x = allinHospitalDay / integer;

        System.out.println(name1 + ",");
        System.out.print(integer + ",");
        System.out.print(x + ",");
        System.out.print(v + ",");
        return;
    }

    // "既使用支气管扩张药，又使用肾上腺皮质激素类的患者数量";
    public static void geName2() {

        int allAge = 0;
        int allcount = 0;
        double allFee = 0;
        double allinHospitalDay = 0;

        List<String> list = readFile2List(fileName);
        Map<String, Integer> resultMap = new HashMap<>();
        double d = 0;
        for (String line : list) {
            String[] split = line.split("/");
            String id = split[0];
            int age = Integer.valueOf(split[1]);
            //总共年龄
            //总共费用
            int inHospitalDay = Integer.valueOf(split[3]);
            String str = split[4];
            double fee = Double.valueOf(split[2]);
            if (!str.contains("支气管扩张药") && str.contains("肾上腺皮质激素类")) {
                System.out.println(line);
//            if (str.contains("肾上腺皮质激素类")) {
//            if (str.contains("支气管扩张药")) {
                allinHospitalDay += inHospitalDay;
                allAge += age;
                allFee += fee;
                allcount += 1;
            }

        }
        Integer integer = allcount;
        int i = allAge / integer;
        double v = allFee / integer;
        double x = allinHospitalDay / integer;

        System.out.println(name2 + ",");
        System.out.print(integer + ",");
        System.out.print(x + ",");
        System.out.print(v + ",");
    }

    //   "既使用支气管扩张药，又使用肾上腺皮质激素类的患者数量中，肾上腺皮质激素类单独使用口服药的数量";
    public static void geName3() {
        int allAge = 0;
        int allcount = 0;
        double allFee = 0;
        double allinHospitalDay = 0;
        List<String> list = readFile2List(fileName);
        Map<String, Integer> resultMap = new HashMap<>();
        double d = 0;
        for (String line : list) {
            String[] split = line.split("/");
            String id = split[0];
            int age = Integer.valueOf(split[1]);
            //总共年龄
            //总共费用
            int inHospitalDay = Integer.valueOf(split[3]);
            String str = split[4];
            double fee = Double.valueOf(split[2]);
            boolean flag = true;
            if (str.contains("支气管扩张药") && str.contains("肾上腺皮质激素类")) {
                JSONArray jsonArray = null;
                try {

                    jsonArray = JSONObject.parseArray(str);
                } catch (Exception e) {
                    continue;
                }
                Iterator<Object> iterator = jsonArray.iterator();
                while (iterator.hasNext()) {
                    Map<String, String> next = (Map) iterator.next();
                    Collection<String> values = next.values();
//                    单独使用口服药
                    if (values.contains("肾上腺皮质激素类") && !values.contains("口服")) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    allcount += 1;
                    allinHospitalDay += inHospitalDay;
                    allAge += age;
                    allFee += fee;
                }
            }

        }
        Integer integer = allcount;
        int i = allAge / integer;
        double v = allFee / integer;
        double x = allinHospitalDay / integer;

        System.out.println(name3 + ",");
        System.out.print(integer + ",");
        System.out.print(x + ",");
        System.out.print(v + ",");

        return;
    }

    public static void geName4() {
        int allAge = 0;
        int allcount = 0;
        double allFee = 0;
        double allinHospitalDay = 0;
        List<String> list = readFile2List(fileName);
        Map<String, Integer> resultMap = new HashMap<>();
        double d = 0;
        for (String line : list) {
            String[] split = line.split("/");
            String id = split[0];
            int age = Integer.valueOf(split[1]);
            //总共年龄
            //总共费用
            int inHospitalDay = Integer.valueOf(split[3]);
            String str = split[4];
            double fee = Double.valueOf(split[2]);
            boolean flag = true;
            if (str.contains("支气管扩张药") && str.contains("肾上腺皮质激素类")) {
                JSONArray jsonArray = null;
                try {

                    jsonArray = JSONObject.parseArray(str);
                } catch (Exception e) {
                    continue;
                }
                Iterator<Object> iterator = jsonArray.iterator();
                while (iterator.hasNext()) {
                    Map<String, String> next = (Map) iterator.next();
                    Collection<String> values = next.values();
//                    单独使用口服药
                    if (values.contains("肾上腺皮质激素类") && !values.contains("注射")) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    allcount += 1;
                    allinHospitalDay += inHospitalDay;
                    allAge += age;
                    allFee += fee;
                }
            }

        }
        Integer integer = allcount;
        int i = allAge / integer;
        double v = allFee / integer;
        double x = allinHospitalDay / integer;

        System.out.println(name4 + ",");
        System.out.print(integer + ",");
        System.out.print(x + ",");
        System.out.print(v + ",");

        return;
    }

    public static void geName5() {
        int allAge = 0;
        int allcount = 0;
        double allFee = 0;
        double allinHospitalDay = 0;
        List<String> list = readFile2List(fileName);
        Map<String, Integer> resultMap = new HashMap<>();
        double d = 0;
        for (String line : list) {
            List<String> typeList = new ArrayList<>();

            String[] split = line.split("/");
            String id = split[0];
            int age = Integer.valueOf(split[1]);
            //总共年龄
            //总共费用
            int inHospitalDay = Integer.valueOf(split[3]);
            String str = split[4];
            double fee = Double.valueOf(split[2]);
            if (str.contains("支气管扩张药") && str.contains("肾上腺皮质激素类")) {
                JSONArray jsonArray = null;
                try {

                    jsonArray = JSONObject.parseArray(str);
                } catch (Exception e) {
                    continue;
                }
                Iterator<Object> iterator = jsonArray.iterator();
                while (iterator.hasNext()) {
                    Map<String, String> next = (Map) iterator.next();
                    Collection<String> values = next.values();
//                    单独使用口服药
                    if (values.contains("肾上腺皮质激素类")) {
                        typeList.addAll(values);
                    }
                }
                if (typeList.contains("口服") && typeList.contains("注射")) {
                    allcount += 1;
                    allinHospitalDay += inHospitalDay;
                    allAge += age;
                    allFee += fee;
                }
            }

        }
        Integer integer = allcount;
        int i = allAge / integer;
        double v = allFee / integer;
        double x = allinHospitalDay / integer;

        System.out.println(name5 + ",");
        System.out.print(integer + ",");
        System.out.print(x + ",");
        System.out.print(v + ",");
        return;
    }

}

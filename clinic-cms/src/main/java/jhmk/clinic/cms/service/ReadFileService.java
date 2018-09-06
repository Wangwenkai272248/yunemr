package jhmk.clinic.cms.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jhmk.clinic.cms.function.demo1.DrugNode;
import jhmk.clinic.cms.function.demo1.ReadFile;
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

        Map all = getAll();
        geName1();
        geName2();
        geName3();
        geName4();
        geName5();
//        get1();
    }

    //    static String fileName = "C:/嘉和美康文档/3院测试数据/用药统计/三院新.txt";
    static String fileName = "C:/嘉和美康文档/3院测试数据/用药统计/tempDataNew.txt";
//    static String fileName = "C:/嘉和美康文档/3院测试数据/用药统计/三院.txt";
//    static String fileName = "C:/嘉和美康文档/3院测试数据/用药统计/朝阳.txt";
//    static String fileName = "C:/嘉和美康文档/3院测试数据/用药统计/朝阳1.txt";

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
        Integer integer = list.size();
        int i = allAge / integer;
        double v = allFee / integer;
        double x = allday / integer;

        System.out.println("总数量" + "," + integer + "," + x + "," + v + "," + i);
        return null;
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

        System.out.println(name1 + "," + integer + "," + x + "," + v + "," + i);


        return;
    }

    public static List<String> geName11() {

        int allAge = 0;
        double allFee = 0;
        double allinHospitalDay = 0;

        List<String> list = readFile2List(fileName);
        List<String> listStr = new ArrayList<>();
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
                listStr.add(str);
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

        System.out.println(name1 + "," + integer + "," + x + "," + v + "," + i);


        return listStr;
    }

    public static void get1() {
        List<DrugNode> tree = ReadFile.creatChildMedicineTree();
        Set<String> sets = ReadFile.getChildNameList("茶碱类", tree);
        Set<String> sets1 = ReadFile.getChildNameList("肾上腺素受体激动药", tree);
        Set<String> sets2 = ReadFile.getChildNameList("M受体阻断药", tree);
        Set<String> sets3 = ReadFile.getAllChildNameList("支气管扩张药", tree);
        Set<String> sets4 = ReadFile.getAllChildNameList("肾上腺皮质激素类", tree);

        List<String> list = geName11();
        System.out.println("==========" + list.size());
        Map<String, Integer> map = new HashMap<>();
        map.put("茶碱", 0);
        map.put("肾上腺素受体激动药", 0);
        map.put("M受体阻断药", 0);
        map.put("支气管扩张药", 0);
        map.put("肾上腺皮质激素类", 0);

        for (String str : list) {
            for (String str1 : sets) {
                if (str.contains(str1)) {
                    map.put("茶碱", map.get("茶碱") + 1);
                    break;
                }
            }
            for (String str1 : sets1) {
                if (str.contains(str1)) {
                    map.put("肾上腺素受体激动药", map.get("肾上腺素受体激动药") + 1);
                    break;
                }
            }
            for (String str1 : sets2) {
                if (str.contains(str1)) {
                    map.put("M受体阻断药", map.get("M受体阻断药") + 1);
                    break;
                }
            }
            for (String str1 : sets3) {
                if (str.contains(str1)) {
                    map.put("支气管扩张药", map.get("支气管扩张药") + 1);
                    break;
                }
            }
            for (String str1 : sets4) {
                if (str.contains(str1)) {
                    map.put("肾上腺皮质激素类", map.get("肾上腺皮质激素类") + 1);
                    break;
                }
            }

        }
        System.out.println(JSONObject.toJSONString(map));
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
            if (str.contains("支气管扩张药") && str.contains("肾上腺皮质激素类")) {
//                System.out.println(line);
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

        System.out.println(name2 + "," + integer + "," + x + "," + v + "," + i);

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

        System.out.println(name3 + "," + integer + "," + x + "," + v + "," + i);


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
        System.out.println(name4 + "," + integer + "," + x + "," + v + "," + i);

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

        System.out.println(name5 + "," + integer + "," + x + "," + v + "," + i);


        return;
    }

}

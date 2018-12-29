package jhmk.clinic.cms.controller.cdss;

import com.alibaba.fastjson.JSONObject;
import jhmk.clinic.cms.SamilarService;
import jhmk.clinic.cms.controller.ruleService.*;
import jhmk.clinic.cms.entity.Rule;
import jhmk.clinic.cms.service.BiaozhuService;
import jhmk.clinic.cms.service.CdssService;
import jhmk.clinic.cms.service.ReadFileService;
import jhmk.clinic.cms.service.Write2File;
import jhmk.clinic.core.base.BaseController;
import jhmk.clinic.core.config.CdssConstans;
import jhmk.clinic.core.util.CompareUtil;
import jhmk.clinic.core.util.DateFormatUtil;
import jhmk.clinic.core.util.HttpClient;
import jhmk.clinic.entity.bean.CollectionType;
import jhmk.clinic.entity.bean.Misdiagnosis;
import jhmk.clinic.entity.bean.Shangjiyishichafanglu;
import jhmk.clinic.entity.cdss.CdssDiffBean;
import jhmk.clinic.entity.cdss.CdssRuleBean;
import jhmk.clinic.entity.cdss.Date1206;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * @author ziyu.zhou
 * @date 2018/7/31 14:42
 */
//获取测试数据相关
@Controller
@RequestMapping("/data")
public class DataController extends BaseController {
    Logger logger = LoggerFactory.getLogger(DataController.class);

    @Autowired
    SamilarService samilarService;
    @Autowired
    BasyService basyService;
    @Autowired
    RyjuService ryjuService;
    @Autowired
    SyzdService syzdService;
    @Autowired
    BiaozhuService biaozhuService;
    @Autowired
    SjyscflService sjyscflService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CdssService cdssService;
    @Autowired
    RuleService ruleService;

    public static final String sympol = "&&";

    @RequestMapping("/getGukedata")
    @ResponseBody
    public void getGukedata1(HttpServletResponse response, @RequestBody(required = false) String map) {
        List<Rule> gukeDataByCondition = basyService.getGukeDataByCondition(JSONObject.parseObject(map));
        logger.info("数量为：====》》》》{}", gukeDataByCondition.size());
        for (Rule bean : gukeDataByCondition) {
            String id = bean.getId();
            String rycz = cdssService.getRycz(id);
            String cyzd = syzdService.getCyzd(id);
            bean.setRycz(rycz);
            bean.setCyzd(cyzd);
            //入等于出
            if (StringUtils.isNotBlank(rycz) && rycz.equals(cyzd)) {
                bean.setReqc(1);
            } else {
                bean.setReqc(0);
            }
            String admission_time = bean.getBinganshouye().getAdmission_time();
            List<Shangjiyishichafanglu> sjyscflBean = sjyscflService.getSJYSCFLBean(id);
            String sjqzDate = sjyscflService.getSjqzDate(sjyscflBean, cyzd);
            if (StringUtils.isNotBlank(sjqzDate)) {
                int i = DateFormatUtil.dateDiff1(DateFormatUtil.parseDateBySdf(sjqzDate, DateFormatUtil.DATETIME_PATTERN_SS), DateFormatUtil.parseDateBySdf(admission_time, DateFormatUtil.DATETIME_PATTERN_SS));
                bean.setQzDay(i);
            }
            bean.setShangjiyishichafangluList(sjyscflBean);
            Map<String, String> param = new HashMap<>();
            param.put("id", id.replaceAll("BJDXDSYY", "BYSY"));
            Object o = JSONObject.toJSON(param);
            String s = null;
            try {
//                s = restTemplate.postForObject("http://localhost:8115/bzgj/collectionType/findById", o, String.class);
                s = restTemplate.postForObject("http://192.168.132.7:8115/bzgj/collectionType/findById", o, String.class);
                logger.info(">>>>>>>>>>>>>>>" + s);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (StringUtils.isNotBlank(s)) {
                JSONObject object = JSONObject.parseObject(s);
                if (object.getInteger("code") == 200) {
                    if (StringUtils.isNotBlank(object.getString("data"))) {
                        CollectionType data = object.getObject("data", CollectionType.class);
                        String 模型结果 = data.get模型结果();
                        int hitNum = data.getHitNum();
                        bean.setNumRate(hitNum);
                        bean.setModelList(模型结果);
                    } else {
                        bean.setNumRate(-1);
                        bean.setModelList("空");
                    }
                }
            } else {
                bean.setNumRate(-1);
                bean.setModelList("空");
            }
        }


        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("sheet1");
//        String fileName = "C:/嘉和美康文档/3院测试数据/" + DateFormatUtil.format(new Date(), DateFormatUtil.DATETIME_PATTERN_S) + "骨科数据.xls";//设置要导出的文件的名字 //新增数据行，并且设置单元格数据
        String fileName = "/data/1/CDSS/data/" + DateFormatUtil.format(new Date(), DateFormatUtil.DATETIME_PATTERN_S) + "骨科数据.xlsx";//设置要导出的文件的名字 //新增数据行，并且设置单元格数据
        int rowNum = 1;
        String[] headers = {"ID", "PID", "VID", "科室名", "科室编码", "管床医师姓名", "入院时间", "出院时间", "出院主诊断", "入院主诊断", "上级医师查房", "入院诊断与出院诊断符合标识", "确诊时长(天)", "确诊项目与出院诊断是否一致", "推荐的列表", "辅助诊断命中的序列数"}; //headers表示excel表中第一行的表头
        XSSFRow row = sheet.createRow(0);
        //在excel表中添加表头
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = row.createCell(i);
//            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        } //在表中存放查询到的数据放入对应的列
        for (Rule bean : gukeDataByCondition) {
            XSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(bean.getId());
            row1.createCell(1).setCellValue(bean.getPatient_id());
            row1.createCell(2).setCellValue(bean.getVisit_id());
            row1.createCell(3).setCellValue(bean.getBinganshouye().getPat_visit_dept_admission_to_name());
            row1.createCell(4).setCellValue(bean.getBinganshouye().getPat_visit_dept_admission_to_code());
            row1.createCell(5).setCellValue(bean.getBinganshouye().getPat_visit_dept_request_doctor_name());
            row1.createCell(6).setCellValue(bean.getBinganshouye().getAdmission_time());
            row1.createCell(7).setCellValue(bean.getBinganshouye().getDischarge_time());
            row1.createCell(8).setCellValue(bean.getCyzd());
            row1.createCell(9).setCellValue(bean.getRycz());
            row1.createCell(10).setCellValue(JSONObject.toJSONString(bean.getShangjiyishichafangluList()));
            row1.createCell(11).setCellValue(bean.getReqc());
            row1.createCell(12).setCellValue(bean.getQzDay());
            row1.createCell(13).setCellValue(bean.getQzeqc());
            row1.createCell(14).setCellValue(bean.getModelList());
            row1.createCell(15).setCellValue(bean.getNumRate());

            rowNum++;
        }
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
//            FileOutputStream fos = new FileOutputStream("C:/嘉和美康文档/3院测试数据/"+fileName);
            workbook.write(fos);
            System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        biaozhuService.method2(resultList);
//        wirte(response, "写入成功");
//
//
//        BufferedWriter bufferedWriter = null;
////        File file = new File("/data/1/CDSS/3院骨科漏诊数据.txt");
//        File file = new File("C:/嘉和美康文档/3院测试数据/2018_12_20_3院骨科数据.txt");
//        if (!file.exists()) {
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        try {
//            bufferedWriter = new BufferedWriter(new FileWriter(file));
//            StringBuilder sb = null;
//            for (Rule mz : gukeDataByCondition) {
//                sb = new StringBuilder();
//                sb.append(mz.getId())
//                        .append(sympol)
//                        .append(mz.getPatient_id())
//                        .append(sympol)
//                        .append(mz.getVisit_id())
//                        .append(sympol)
//                        .append(mz.getBinganshouye().getAdmission_time())
//                        .append(sympol)
//                        .append(mz.getBinganshouye().getDischarge_time())
//                        .append(sympol)
//                        .append(mz.getBinganshouye().getPat_visit_dept_admission_to_name())
//                        .append(sympol)
//                        .append(mz.getBinganshouye().getPat_visit_dept_admission_to_code())
//                        .append(sympol)
//                        .append(mz.getBinganshouye().getPat_visit_dept_request_doctor_name())
//                        .append(sympol)
//                        .append(JSONObject.toJSONString(mz.getShangjiyishichafangluList()));
//                bufferedWriter.write(sb.toString());
//                bufferedWriter.newLine();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                bufferedWriter.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        wirte(response, "导入文件成功");
    }

    public void getGukedata(HttpServletResponse response, @RequestBody(required = false) String map) {
        List<Rule> gukeDataByCondition = basyService.getGukeDataByCondition(JSONObject.parseObject(map));
        for (Rule bean : gukeDataByCondition) {
            String id = bean.getId();
            String rycz = cdssService.getRycz(id);
            String cyzd = syzdService.getCyzd(id);
            bean.setRycz(rycz);
            bean.setCyzd(cyzd);
            List<Shangjiyishichafanglu> sjyscflBean = sjyscflService.getSJYSCFLBean(id);
            bean.setShangjiyishichafangluList(sjyscflBean);
        }
        BufferedWriter bufferedWriter = null;
//        File file = new File("/data/1/CDSS/3院骨科漏诊数据.txt");
        File file = new File("C:/嘉和美康文档/3院测试数据/2018_12_20_3院骨科数据.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            StringBuilder sb = null;
            for (Rule mz : gukeDataByCondition) {
                sb = new StringBuilder();
                sb.append(mz.getId())
                        .append(sympol)
                        .append(mz.getPatient_id())
                        .append(sympol)
                        .append(mz.getVisit_id())
                        .append(sympol)
                        .append(mz.getBinganshouye().getAdmission_time())
                        .append(sympol)
                        .append(mz.getBinganshouye().getDischarge_time())
                        .append(sympol)
                        .append(mz.getBinganshouye().getPat_visit_dept_admission_to_name())
                        .append(sympol)
                        .append(mz.getBinganshouye().getPat_visit_dept_admission_to_code())
                        .append(sympol)
                        .append(mz.getBinganshouye().getPat_visit_dept_request_doctor_name())
                        .append(sympol)
                        .append(JSONObject.toJSONString(mz.getShangjiyishichafangluList()));
                bufferedWriter.write(sb.toString());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        wirte(response, "导入文件成功");
    }

    @RequestMapping("/getOtolaryngologydata")
    @ResponseBody
    public void getOtolaryngologydata(HttpServletResponse response) {
        List<Misdiagnosis> gukeData = basyService.getGukeData();
        Set<Misdiagnosis> saveData = new HashSet<>();
        for (Misdiagnosis bean : gukeData) {
            String id = bean.getId();
            //获取既往史存在疾病
            Misdiagnosis jwSdieases = ryjuService.getJWSdieases(id);
            if (jwSdieases == null || jwSdieases.getHisDiseaseList() == null || jwSdieases.getHisDiseaseList().size() == 0) {
                continue;
            }
            //获取出院诊断集合
            List<String> syzd = syzdService.getDiseaseList(id);
            List<String> hisDiseaseList = jwSdieases.getHisDiseaseList();
            //遍历既往史存在疾病 如果首页诊断不存在 则保存
            for (String s : hisDiseaseList) {
                if (!syzd.contains(s)) {
                    bean.setNowDiseaseList(hisDiseaseList);
                    saveData.add(bean);
                    continue;
                }
            }

        }
        BufferedWriter bufferedWriter = null;
//        File file = new File("/data/1/CDSS/3院骨科漏诊数据.txt");
        File file = new File("C:/嘉和美康文档/3院测试数据/3院骨科漏诊数据.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (Misdiagnosis mz : saveData) {

                bufferedWriter.write(mz.getId() + "," + mz.getPatient_id() + "," + mz.getVisit_id()
                        + mz.getDept_discharge_from_name() + ","
                        + mz.getDistrict_discharge_from_name() + ","
                        + mz.getHisDiseaseList() + ","
                        + mz.getNowDiseaseList() + ","
                        + mz.getSrc()
                );

                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        wirte(response, "导入文件成功");
    }


    /**
     * 根据条件获取确诊时间
     *
     * @param response
     */
    @RequestMapping("/getAvgDateByCondition")
    @ResponseBody
    public void getAvgDateByCondition(HttpServletResponse response, @RequestBody String map) {
        JSONObject jsonObject = JSONObject.parseObject(map);
        String deptName = jsonObject.getString("deptName");
        String startTime = jsonObject.getString("startTime");
        String endTime = jsonObject.getString("endTime");
        String diseaseName = jsonObject.getString("diseaseName");
        int page = jsonObject.getInteger("page") == null ? 1 : jsonObject.getInteger("page");
        int pageSize = jsonObject.getInteger("pageSize") == null ? 20 : jsonObject.getInteger("page");

        String jsonStr = cdssService.getJsonStr(deptName, startTime, endTime, page, pageSize);
        logger.info("条件信息：{}", jsonStr);
        String s = HttpClient.doPost(CdssConstans.patients, jsonStr);
        logger.info("结果信息：{}", s);
        List<CdssDiffBean> diffBeanList = cdssService.getDiffBeanList(s);
//        List<CdssDiffBean> diffBeanList1 = cdssService.getDiffBeanList(diffBeanList);
        List<CdssDiffBean> allDiffBeanList = cdssService.getDiffBean(diffBeanList);
        List<CdssDiffBean> resultList = new LinkedList<>();
        if (StringUtils.isNotBlank(diseaseName)) {
            for (CdssDiffBean bean : allDiffBeanList) {
                if (diseaseName.contains(bean.getChuyuanzhenduan()) || bean.getChuyuanzhenduan().contains(diseaseName)) {
                    resultList.add(bean);
                }
            }
        } else {
            resultList = allDiffBeanList;
        }
        Map<Integer, Integer> stringStatisticsBeanMap = cdssService.analyzeData2CountAndAvgDay(resultList);
        System.out.println(allDiffBeanList);
//        Write2File.w2fileList(allDiffBeanList, "2017年呼吸科确诊数据.txt");

        wirte(response, stringStatisticsBeanMap);
    }

    /**
     * 对比下这些医生ID，2018年7月-11月，初诊正确率有没有改变
     */

    @RequestMapping("/analyzeData20181206")
    public void analyzeData20181206(HttpServletResponse response, @RequestBody(required = false) String map) {
        JSONObject jsonObject = JSONObject.parseObject(map);
        String startTime = jsonObject.getString("startTime");
        String endTime = jsonObject.getString("endTime");
        List<String> list = ReadFileService.readSourceList("20181206doctorId");
        List<CdssRuleBean> resultList = new ArrayList<>();
        for (String id : list) {
            List<CdssRuleBean> beanByDoctorIdAndDate = basyService.getBeanByDoctorIdAndDate(id, startTime, endTime);
            resultList.addAll(beanByDoctorIdAndDate);
        }
        logger.info("结果数量为：{}", resultList.size());
        Collections.sort(resultList, CompareUtil.createComparator(1, "doctor_id"));
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("主表");
        String fileName = "20181206data" + ".xls";//设置要导出的文件的名字 //新增数据行，并且设置单元格数据
        int rowNum = 1;
        String[] headers = {"入院时间", "PID", "VID", "DoctorId", "DoctorName", "入院主诊断", "出院主诊断"}; //headers表示excel表中第一行的表头
        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        } //在表中存放查询到的数据放入对应的列
        for (CdssRuleBean bean : resultList) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(bean.getAdmission_time());
            row1.createCell(1).setCellValue(bean.getPatient_id());
            row1.createCell(2).setCellValue(bean.getVisit_id());
            row1.createCell(3).setCellValue(bean.getDoctor_id());
            row1.createCell(4).setCellValue(bean.getDoctor_name());
            row1.createCell(5).setCellValue(bean.getRycz());
            row1.createCell(6).setCellValue(bean.getCyzd());
            rowNum++;
        }
        try {
            FileOutputStream fos = new FileOutputStream("/data/1/CDSS/" + fileName);
//            FileOutputStream fos = new FileOutputStream("C:/嘉和美康文档/3院测试数据/"+fileName);
            workbook.write(fos);
            System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        biaozhuService.method2(resultList);
        wirte(response, "写入成功");

    }

    @RequestMapping("/temp")
    public void temp(HttpServletResponse response) {
        List<String> list = ReadFileService.readSourceList("1206");
        Map<String, Date1206> map = new HashMap<>();
        for (String str : list) {
            String[] split = str.split(",");
            String doctor_id = split[3];
            String rycz = split[5];
            String cyzd = split[6];
            String admission_time = split[0].substring(0, 7);
//            boolean fatherAndSon = samilarService.isFatherAndSon(rycz, cyzd);
            List<String> samilarWord = samilarService.getSamilarWord(cyzd);
            boolean fatherAndSon = rycz.contains(cyzd) || cyzd.contains(rycz);
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

//        Write2File.w2fileList(stringList, "/data/1/CDSS/avg.txt");
        Write2File.w2fileList(stringList, "c:/嘉和美康文档/3院测试数据/avg.txt");


    }

//    public void method2(List<CdssRuleBean> resultList) {
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFSheet sheet = workbook.createSheet("统计表");
//        String fileName = "20181206data" + ".xls";//设置要导出的文件的名字 //新增数据行，并且设置单元格数据
//        int rowNum = 1;
//        String[] headers = {"时间", "DoctorId", "符合率"}; //headers表示excel表中第一行的表头
//        HSSFRow row = sheet.createRow(0);
//        Map<String, Date1206> map = new HashMap<>();
//        //在excel表中添加表头
//        for (CdssRuleBean bean : resultList) {
//            String doctor_id = bean.getDoctor_id();
//            String rycz = bean.getRycz();
//            String cyzd = bean.getCyzd();
//            boolean fatherAndSon = samilarService.isFatherAndSon(rycz, cyzd);
//            if (map.containsKey(doctor_id)) {
//                Date1206 date1206 = map.get(doctor_id);
//                if (fatherAndSon) {
//                    date1206.setAll(date1206.getAll() + 1);
//                    date1206.setCurrect(date1206.getCurrect() + 1);
//                } else {
//                    date1206.setError(date1206.getError() + 1);
//                    date1206.setAll(date1206.getAll() + 1);
//                }
//                map.put(doctor_id, date1206);
//            } else {
//                Date1206 date1206 = new Date1206();
//                if (fatherAndSon) {
//                    date1206.setAll(1);
//                    date1206.setCurrect(1);
//                    date1206.setError(0);
//                    date1206.setDoctorId(doctor_id);
//                } else {
//                    date1206.setAll(1);
//                    date1206.setCurrect(0);
//                    date1206.setError(1);
//                    date1206.setDoctorId(doctor_id);
//                }
//                map.put(doctor_id, date1206);
//            }
//        }
//        for (int i = 0; i < headers.length; i++) {
//            HSSFCell cell = row.createCell(i);
//            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
//            cell.setCellValue(text);
//        } //在表中存放查询到的数据放入对应的列
//        Set<Map.Entry<String, Date1206>> entries = map.entrySet();
//        for (Map.Entry<String, Date1206>  bean : map.entrySet()) {
//            HSSFRow row1 = sheet.createRow(rowNum);
//            row1.createCell(0).setCellValue(bean.getAdmission_time());
//            row1.createCell(1).setCellValue(bean.getPatient_id());
//            row1.createCell(2).setCellValue(bean.getVisit_id());
//            rowNum++;
//        }
//        try {
//            FileOutputStream fos = new FileOutputStream("/data/1/CDSS/" + fileName);
////            FileOutputStream fos = new FileOutputStream("C:/嘉和美康文档/3院测试数据/"+fileName);
//            workbook.write(fos);
//            System.out.println("写入成功");
//            fos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) {

//        int fbna = fbna(5);
//        System.out.println(fbna);

        hanio(6, 'a', 'b', 'c');
    }

    public static void hanio(int n, char a, char b, char c) {
        if (n == 1) {
//            hanio(1, a, b, c);
            System.out.println("移动" + n + "号盘子从" + a + "到" + c);
        } else {
            hanio(n - 1, a, c, b);//把上面n-1个盘子从a借助b搬到c
            System.out.println("移动" + n + "号盘子从" + a + "到" + c);//紧接着直接把n搬动c
            hanio(n - 1, b, a, c);//再把b上的n-1个盘子借助a搬到c
        }
    }

    public static int fbna(int n) {
        if (n <= 2) {
            return 1;
        } else {
            return fbna(n - 1) + fbna(n - 2);
        }

    }




}

package jhmk.clinic.cms.controller.cdss;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.*;
import jhmk.clinic.cms.SamilarService;
import jhmk.clinic.cms.controller.ruleService.*;
import jhmk.clinic.cms.entity.Disease_attribution;
import jhmk.clinic.cms.entity.Rule;
import jhmk.clinic.cms.exception.MyException;
import jhmk.clinic.cms.service.BiaozhuService;
import jhmk.clinic.cms.service.CdssService;
import jhmk.clinic.cms.service.ReadFileService;
import jhmk.clinic.cms.service.Write2File;
import jhmk.clinic.cms.util.ExportExcelUtil;
import jhmk.clinic.cms.util.ImportExcelUtil;
import jhmk.clinic.core.base.BaseController;
import jhmk.clinic.core.config.CdssConstans;
import jhmk.clinic.core.util.CompareUtil;
import jhmk.clinic.core.util.DateFormatUtil;
import jhmk.clinic.core.util.DocumentUtil;
import jhmk.clinic.core.util.HttpClient;
import jhmk.clinic.entity.bean.*;
import jhmk.clinic.entity.cdss.CdssDiffBean;
import jhmk.clinic.entity.cdss.CdssRuleBean;
import jhmk.clinic.entity.cdss.Date1206;
import jhmk.clinic.entity.model.AtResponse;
import jhmk.clinic.entity.model.ResponseCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
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
    BlzdService blzdService;
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
    @Autowired
    DocumentUtil documentUtil;
    @Autowired
    JybgService jybgService;
    @Autowired
    ZhuyuanfeiyongService zhuyuanfeiyongService;

    public static final String sympol = "&&";
    private static final String SUFFIX_2003 = ".xls";
    private static final String SUFFIX_2007 = ".xlsx";

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

            Shangjiyishichafanglu shangjiyishichafanglu = sjyscflService.getQzShangjiyishichafanglu(sjyscflBean);
            if (Objects.nonNull(shangjiyishichafanglu)) {
                String sjqzDate = shangjiyishichafanglu.getLast_modify_date_time();
                String clearDiagnoseName = shangjiyishichafanglu.getClear_diagnose_name();
                if (StringUtils.isNotBlank(sjqzDate)) {
                    int i = DateFormatUtil.dateDiff1(DateFormatUtil.parseDateBySdf(sjqzDate, DateFormatUtil.DATETIME_PATTERN_SS), DateFormatUtil.parseDateBySdf(admission_time, DateFormatUtil.DATETIME_PATTERN_SS));
                    bean.setQzDay(i);
                } else {
                    bean.setQzDay(-100);
                }
                if (StringUtils.isNotBlank(clearDiagnoseName)) {
                    List<String> list = Arrays.asList(clearDiagnoseName.split(" "));
                    if (list.contains(cyzd)) {
                        bean.setQzeqc(1);
                    } else {
                        bean.setQzeqc(0);
                    }
                }
            } else {
                bean.setQzDay(-100);
            }
//            String sjqzDate = sjyscflService.getSjqzDate(sjyscflBean, cyzd);

            bean.setShangjiyishichafangluList(sjyscflBean);
            Map<String, String> param = new HashMap<>();
            param.put("id", id.replaceAll("BJDXDSYY", "BYSY"));
            Object o = JSONObject.toJSON(param);
            String s = null;
            try {
//                s = restTemplate.postForObject("http://localhost:8115/bzgj/collectionType/findById", o, String.class);
//                s = restTemplate.postForObject("http://192.168.8.20:8115/bzgj/collectionType/findById", o, String.class);
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
                        if (StringUtils.isNotBlank(模型结果)) {
                            int hitNum = data.getHitNum();
                            bean.setNumRate(hitNum);
                            bean.setModelList(模型结果);
                        } else {
                            bean.setNumRate(-100);
                            bean.setModelList("空");
                        }

                    } else {
                        bean.setNumRate(-100);
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
        String[] headers = {"ID", "PID", "VID", "科室名", "科室编码", "管床医师姓名", "入院时间", "出院时间", "出院主诊断", "入院主诊断", "上级医师查房", "入院诊断与出院诊断符合标识", "确诊时长(天)", "确诊项目与出院诊断是否一致", "推荐的列表", "辅助诊断命中的序列数", "住院时长"}; //headers表示excel表中第一行的表头
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

    /**
     * 骨科 ：1020500
     * 普外科 ：1020100
     * 耳鼻喉科 ：1200000
     * 血液病科 ：1010400
     * 呼吸科 ：1010300
     * 心血管科 ：1010100
     *
     * @param response
     * @param map
     */
    @RequestMapping("/getDataByCondition")
    @ResponseBody
    public void getDataByCondition(HttpServletResponse response, @RequestBody(required = false) String map) {
        List<Rule> dataByCondition = basyService.getDataByCondition(JSONObject.parseObject(map));
        String dept_admission_to_name = dataByCondition.get(0).getBinganshouye().getDept_admission_to_name();
        logger.info("数量为：====》》》》{}", dataByCondition.size());
        for (Rule bean : dataByCondition) {
            String id = bean.getId();
            String rycz = cdssService.getRycz(id);
            String cyzd = syzdService.getCyzd(id);
            bean.setRycz(rycz);
            bean.setCyzd(cyzd);

            if (isParentOrChildByEs(rycz, cyzd)) {
                bean.setReqc(1);
            } else {
                bean.setReqc(0);

            }

//            //入等于出
//            if (StringUtils.isNotBlank(rycz) && rycz.equals(cyzd)) {
//                bean.setReqc(1);
//            } else {
//                bean.setReqc(0);
//            }
            String admission_time = bean.getBinganshouye().getAdmission_time();
            List<Shangjiyishichafanglu> sjyscflBean = sjyscflService.getSJYSCFLBean(id);

            Shangjiyishichafanglu shangjiyishichafanglu = sjyscflService.getQzShangjiyishichafanglu(sjyscflBean);
            if (Objects.nonNull(shangjiyishichafanglu)) {
                String sjqzDate = shangjiyishichafanglu.getLast_modify_date_time();
                String clearDiagnoseName = shangjiyishichafanglu.getClear_diagnose_name();
                if (StringUtils.isNotBlank(sjqzDate)) {
                    int i = DateFormatUtil.dateDiff1(DateFormatUtil.parseDateBySdf(sjqzDate, DateFormatUtil.DATETIME_PATTERN_SS), DateFormatUtil.parseDateBySdf(admission_time, DateFormatUtil.DATETIME_PATTERN_SS));
                    bean.setQzDay(i);
                } else {
                    bean.setQzDay(-100);
                }
                if (StringUtils.isNotBlank(clearDiagnoseName)) {
                    List<String> list = Arrays.asList(clearDiagnoseName.split(" "));
                    if (list.contains(cyzd)) {
                        bean.setQzeqc(1);
                    } else {
                        bean.setQzeqc(0);
                    }
                }
            } else {
                bean.setQzDay(-100);
            }
//            String sjqzDate = sjyscflService.getSjqzDate(sjyscflBean, cyzd);

            bean.setShangjiyishichafangluList(sjyscflBean);
            Map<String, String> param = new HashMap<>();
            param.put("id", id.replaceAll("BJDXDSYY", "BYSY"));
            Object o = JSONObject.toJSON(param);
            String s = null;
            try {
//                s = restTemplate.postForObject("http://localhost:8115/bzgj/collectionType/findById", o, String.class);
//                s = restTemplate.postForObject("http://192.168.8.20:8115/bzgj/collectionType/findById", o, String.class);
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
                        if (StringUtils.isNotBlank(模型结果)) {
                            int hitNum = data.getHitNum();
                            bean.setNumRate(hitNum);
                            bean.setModelList(模型结果);
                        } else {
                            bean.setNumRate(-100);
                            bean.setModelList("空");
                        }

                    } else {
                        bean.setNumRate(-100);
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
        String fileName = "/data/1/CDSS/data/" + DateFormatUtil.format(new Date(), DateFormatUtil.DATE_PATTERN) + "_" + dept_admission_to_name + dataByCondition.size() + "条数据.xlsx";//设置要导出的文件的名字 //新增数据行，并且设置单元格数据
        int rowNum = 1;
        String[] headers = {"ID", "PID", "VID", "科室名", "科室编码", "管床医师姓名", "入院时间", "出院时间", "出院主诊断", "入院主诊断", "上级医师查房", "入院诊断与出院诊断符合标识", "确诊时长(天)", "确诊项目与出院诊断是否一致", "推荐的列表", "辅助诊断命中的序列数", "住院时长"}; //headers表示excel表中第一行的表头
        XSSFRow row = sheet.createRow(0);
        //在excel表中添加表头
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = row.createCell(i);
//            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        } //在表中存放查询到的数据放入对应的列
        for (Rule bean : dataByCondition) {
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
            long l = DateFormatUtil.dateDiff(DateFormatUtil.parseDateBySdf(bean.getBinganshouye().getDischarge_time(), DateFormatUtil.DATETIME_PATTERN_SS), DateFormatUtil.parseDateBySdf(bean.getBinganshouye().getAdmission_time(), DateFormatUtil.DATETIME_PATTERN_SS));
            row1.createCell(16).setCellValue(l);
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

    public boolean isParentOrChildByEs(String name1, String name2) {
        if (StringUtils.isEmpty(name1)||StringUtils.isEmpty(name2)){
            return false;
        }
        if (name2.equals(name1)) {
            return true;
        }
        List<String> allChildDisease = documentUtil.getAllChildDisease(name2);
        if (allChildDisease != null && allChildDisease.contains(name1)) {
            return true;
        } else {
            return false;
        }
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


    /**
     * 入选标准：
     * 科室：呼吸科
     * 入院诊断不包含：呼吸衰竭 ； 出院诊断不做筛选。
     * <p>
     * 三、考虑纳入的字段
     * 3.1 一般资料 包括性别，年龄，心功能分级(I,II,III等)，入院时间，出院时间。入院诊断(包括主要和其他)，出院诊断(包括主要和其他)。
     * 3.1   文书最终提交时间,明确诊断名称,治疗原则,是否明确诊断
     * 3.3 吸烟史 糖尿病史 高血脂病史 呼吸衰竭病史
     * 3.4 身体质量指数 BMI
     * 3.6 血清乳酸，血清白蛋白，前白蛋白，总淋巴细胞，白细胞。血尿酸，肺功能(FEV)，
     * 血气指标包括PaO2(氧气分压),PaCO2(二氧化碳分压),PH值，血尿素氮.C反应蛋白，
     * 量表评分，急性生理与慢性健康(APACHE)评分指标，
     * <p>
     * 需要包含所有的检查时间
     *
     * @param response
     */
    @RequestMapping("/getHuxukeData")
    public void getHuxukeData(HttpServletResponse response) {
        List<Rule> huxikeData = basyService.getDataByDept("呼吸科");
        List<Rule> resultList = new ArrayList<>();
        for (Rule rule : huxikeData) {
            String id = rule.getId();
            List<Binglizhenduan> binglizhenduanList = blzdService.getBinglizhenduanById(id);
            boolean isHaveName = false;
            for (Binglizhenduan binglizhenduan : binglizhenduanList) {
                if (binglizhenduan.getDiagnosis_name() != null && binglizhenduan.getDiagnosis_name().contains("呼吸衰竭")) {
                    isHaveName = true;
                    break;
                }
            }
            if (isHaveName) {
                continue;
            }
            rule.setBinglizhenduan(binglizhenduanList);
            List<Shouyezhenduan> shoueyezhenduanBean = syzdService.getShoueyezhenduanBean(id);
            rule.setShouyezhenduan(shoueyezhenduanBean);
            List<Shangjiyishichafanglu> sjyscflBean = sjyscflService.getSJYSCFLBean(id);
            rule.setShangjiyishichafangluList(sjyscflBean);
            List<JianyanbaogaoNew> jianyanbaogaoList = jybgService.gtJybgnewById(id);
            rule.setJianyanbaogaoNew(jianyanbaogaoList);
            Ruyuanjilu ruyuanjiluById = ryjuService.getRuyuanjiluById(id);
            rule.setRuyuanjilu(ruyuanjiluById);
            resultList.add(rule);
        }


        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("sheet1");
        String fileName = "C:/嘉和美康文档/3院测试数据/" + DateFormatUtil.format(new Date(), DateFormatUtil.DATETIME_PATTERN_S) + "呼吸科数据.xlsx";//设置要导出的文件的名字 //新增数据行，并且设置单元格数据
//        String fileName = "/data/1/CDSS/data/" + DateFormatUtil.format(new Date(), DateFormatUtil.DATETIME_PATTERN_S) + "骨科数据.xlsx";//设置要导出的文件的名字 //新增数据行，并且设置单元格数据
        int rowNum = 0;
        //在excel表中添加表头
        for (Rule rule : resultList) {
            XSSFRow row1 = sheet.createRow(rowNum);
            int cellNum = 0;
            row1.createCell(cellNum++).setCellValue(rule.getId());
            Binganshouye binganshouye = rule.getBinganshouye();
            row1.createCell(cellNum++).setCellValue("入院时间" + sympol + binganshouye.getAdmission_time());
            row1.createCell(cellNum++).setCellValue("出院时间" + sympol + binganshouye.getDischarge_time());
            row1.createCell(cellNum++).setCellValue("性别" + sympol + binganshouye.getPat_info_sex_name());
            row1.createCell(cellNum++).setCellValue("年龄" + sympol + binganshouye.getPat_info_age_value() + binganshouye.getPat_info_age_value_unit());
            List<Shouyezhenduan> shouyezhenduanList = rule.getShouyezhenduan();
            for (Shouyezhenduan bean : shouyezhenduanList) {
                row1.createCell(cellNum++).setCellValue(bean.getDiagnosis_type_name() + sympol + bean.getDiagnosis_name() + sympol + bean.getDiagnosis_time() + sympol + bean.getDiagnosis_num());
            }
            List<Shangjiyishichafanglu> shangjiyishichafangluList = rule.getShangjiyishichafangluList();
            for (Shangjiyishichafanglu bean : shangjiyishichafangluList) {
                if ("是".equals(bean.getClear_diagnose())) {
                    row1.createCell(cellNum++).setCellValue("文书最终提交时间" + sympol + bean.getLast_modify_date_time());
                    row1.createCell(cellNum++).setCellValue("明确诊断名称" + sympol + bean.getClear_diagnose_name());
                    row1.createCell(cellNum++).setCellValue("治疗原则" + sympol + bean.getTreatment());
                }
            }
            Ruyuanjilu ruyuanjilu = rule.getRuyuanjilu();
            if (ruyuanjilu != null) {

                HistoryOfPastIllness historyOfPastIllness = ruyuanjilu.getHistoryOfPastIllness();
                if (historyOfPastIllness != null) {
                    List<Disease> diseaseList = historyOfPastIllness.getDiseaseList();
                    if (diseaseList != null && diseaseList.size() > 0) {
                        for (Disease disease : diseaseList) {
                            row1.createCell(cellNum++).setCellValue("既往史存在疾病：" + sympol + disease.getDisease_name() + sympol + disease.getDuration_of_illness() + disease.getDuration_of_illness_unit());
                        }
                    }
                }
            }
            List<JianyanbaogaoNew> jianyanbaogao = rule.getJianyanbaogaoNew();
            for (JianyanbaogaoNew bean : jianyanbaogao) {
                String lab_item_name = bean.getLab_item_name();
                String specimen = bean.getSpecimen();
                String report_time = bean.getReport_time();
                String name = bean.getLab_sub_item_name();
                String lab_result_value = bean.getLab_result_value();
                String lab_qual_result = bean.getLab_qual_result();
                row1.createCell(cellNum++).setCellValue("检验单：" + lab_item_name + sympol + "检验细项名称：" + name + sympol + "检验时间：" + report_time + sympol + "检验定量结果：" + lab_result_value + sympol + "检验定性结果:" + lab_qual_result);
            }
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
        System.out.println(resultList);

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


    /**
     *根据罕见病，获取患者的信息
     */
    @RequestMapping("/getDiseaseInfo")
    public void getDiseaseInfo(MultipartFile file, HttpServletRequest req, HttpServletResponse response) throws MyException {
        /*Map<String,Integer> map = new HashMap();
        if (file == null) {
            throw new MyException("对象不能为空");
        }
        String originalFilename = file.getOriginalFilename();
        Workbook workbook = null;
        try {
            if (originalFilename.endsWith(SUFFIX_2003)) {
                workbook = new HSSFWorkbook(file.getInputStream());
            } else if (originalFilename.endsWith(SUFFIX_2007)) {
                workbook = new XSSFWorkbook(file.getInputStream());
            }
        } catch (IOException e) {
            logger.info(originalFilename);
            e.printStackTrace();
            throw new MyException("文件格式不对");
        }

        List<String> diseaseCodeList = new ArrayList();
        List<String> diseaseNameList = new ArrayList();
        if (workbook == null) {
            logger.info(originalFilename);
            throw new MyException("内容为空");
        } else {
            //获取所有的工作表的的数量
//            int numOfSheet = workbook.getNumberOfSheets();
            //获取第二个sheet页的内容
            Sheet sheet = workbook.getSheetAt(1);
            int lastRowNum = sheet.getLastRowNum();
            for (int j = 1; j <= lastRowNum; j++) {
                Row row = sheet.getRow(j);
                if (row.getCell(1) != null) {
                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    String diseaseName = row.getCell(1).getStringCellValue();
                    diseaseNameList.add(diseaseName);
                }
                if (row.getCell(2) != null) {
                    row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                    String diseaseCode = row.getCell(2).getStringCellValue();
                    diseaseCodeList.add(diseaseCode);
                    map.put(diseaseCode,0);
                }
            }
        }
        //根据出院主诊断，查询对应的入院记录
        Map<String,Integer> maps = syzdService.getListByDiseaseName(diseaseCodeList,diseaseNameList,map);
        System.out.println(maps);*/
    }


    /**
     * 根据出院主诊断，统计病例
     */
    @RequestMapping("/getTotalNum")

    @ApiOperation(value = "根据出院主诊断查询病历个数", notes = "无参请求",
            httpMethod = "get/post", responseContainer = "excel")
    public void getTotalNum(HttpServletResponse response) throws Exception {
        //查询数据
        List<List<Object>>  listObject = syzdService.getTotalNum();
        //设置表头
        String[] headers = {"疾病名称","病例数量"};
        //插入表头信息
        ExportExcelUtil.exportExcelByBrowers("病历导出",response, Arrays.asList(headers),listObject);
    }

    /**
     *功能描述
     *@author swq
     *@date 2019-1-24  17:43
     *@param: null
     *@return
     *@desc 根据病历ID查询测试库是否包含该病历
     */
    @RequestMapping("/isExistInLib")
    @ApiOperation(value = "根据病历ID查询测试库是否包含该病历", notes = "file:**.excel",
            responseContainer = "excel")
    @ApiResponses({@ApiResponse(code = 200, message = "成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "请求参数", required = true, paramType = "MultipartFile")
    })
    public AtResponse isExistInLib(MultipartFile file,HttpServletResponse response) throws Exception {
        AtResponse atResponse = new AtResponse();
        //文件非空判断
        if (file==null) {
            atResponse.setResponseCode(ResponseCode.COMERROR);
            atResponse.setData("文件不能为空");
            return atResponse;
        }
        //文件格式判断
        String originalFileName = file.getOriginalFilename();
        if(!(originalFileName.endsWith(".xls") || originalFileName.endsWith(".xlsx"))){
            atResponse.setResponseCode(ResponseCode.COMERROR);
            atResponse.setData("文件格式不对");
            return atResponse;
        }
        //解析excel数据
        List<Map<String,Object>> listMapObject = ImportExcelUtil.parseExcelData(file);
        Map<String,Object> mapObject = listMapObject.get(0);
        List headersList = new ArrayList();
        List<List<Object>> listObject = new ArrayList<>();
        for(Map.Entry<String,Object> map : mapObject.entrySet()){
            if("headers".equals(map.getKey())){
                headersList = (List) map.getValue();
            }
            if("data".endsWith("data")){
                listObject= (List<List<Object>>) map.getValue();
            }
        }
        //查询数据库
        List<List<Object>> existInLib = syzdService.isExistInLib(listObject);
        //设置表头
        String[] headers = (String[]) headersList.toArray(new String[headersList.size()]);

        ExportExcelUtil.exportExcelToDisk("D:/是否存在.xlsx", Arrays.asList(headers),existInLib);
        return atResponse;
    }

    @RequestMapping("/exportLog")
    public AtResponse exportLog(HttpServletResponse response) throws Exception {
        AtResponse atResponse = new AtResponse();
        //查询数据
        List<List<Object>>  list = syzdService.exportLog();
        //设置表头
        String[] headers = {"ID","科室","病历主诊断","下诊断时间","医生编号","医生名称","入库时间","页面来源","PID","VID","是否有返回值","疾病名称和概率"};
        ExportExcelUtil.exportExcelByBrowers("日志导出",response, Arrays.asList(headers),list);

        return atResponse;
    }




    @RequestMapping("updateName")
    public AtResponse updateName(MultipartFile file) throws Exception {
        AtResponse atResponse = new AtResponse();
        //解析excel数据
        List<Map<String,Object>> listMapObject = ImportExcelUtil.parseExcelData(file);
        Map<String,Object> mapObject = listMapObject.get(0);
        List headersList = new ArrayList();
        List<List<Object>> listObject = new ArrayList<>();
        for(Map.Entry<String,Object> map : mapObject.entrySet()){
            if("headers".equals(map.getKey())){
                headersList = (List) map.getValue();
            }else if("data".equals(map.getKey())){
                listObject= (List<List<Object>>) map.getValue();
            }
        }
        //查询数据库
        List<List<Object>> data = syzdService.updateName(listObject);
        String fileName = "D:/修改药名"+System.currentTimeMillis()+".xlsx";
        //导出excel
        ExportExcelUtil.exportExcelToDisk(fileName,headersList,data);
        return atResponse;
    }

    @ApiOperation(value = "导出罕见病相关信息", notes = "导出北医三院罕见病相关信息",
             responseContainer = "Map")
    @ApiResponses({@ApiResponse(code = 200, message = "成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "请求参数", required = true, paramType = "body")
    })
    @RequestMapping("getRareDiseaseInfo")
    public AtResponse get(MultipartFile file) throws Exception {
        AtResponse atResponse = new AtResponse(System.currentTimeMillis());
        //解析excel数据
        List<Map<String,Object>> listMapObject = ImportExcelUtil.parseExcelData(file);
        Map<String,Object> mapObject = listMapObject.get(0);
        List headersList = new ArrayList();
        List idList = new ArrayList();
        List<List<Object>> listObject;
        for(Map.Entry<String,Object> map : mapObject.entrySet()){
            if("headers".equals(map.getKey())){
                headersList = (List) map.getValue();
            }else if("data".equals(map.getKey())){
                listObject= (List<List<Object>>) map.getValue();
                for(List<Object> objectList : listObject){
                    if(null!=objectList.get(0)){
                        idList.add(objectList.get(0));
                    }
                }
            }
        }
        //查询数据库(首页诊断)
        List<Map<String,Object>> syzdData = syzdService.getRareDiseaseInfo(idList);
        //查询数据库(病案首页)
        List<Map<String,Object>> basyData = basyService.getRareDiseaseInfo(idList);
        //查询数据库（住院费用）
        List<Map<String,Object>> zyfyData = zhuyuanfeiyongService.zhuyuanfeiyongService(idList);

        List<List<Object>> listArray = new ArrayList<>();
        for(Map<String,Object> syzdMap : syzdData){
            String syzdId = (String) syzdMap.get("id");
            List<Object> list = new ArrayList<>();
            for(Map<String,Object> basyMap : basyData){
                String basyId = (String) basyMap.get("id");
                for(Map<String,Object> zyfyMap : zyfyData){
                    String zyfyId = (String) zyfyMap.get("id");
                    if(syzdId.equals(basyId) && syzdId.equals(zyfyId)){
                        list.add(syzdMap.get("id"));
                        list.add(syzdMap.get("patientId"));
                        list.add(basyMap.get("sexName"));
                        list.add(basyMap.get("ageValue"));
                        list.add(basyMap.get("deptDischargeFromName"));
                        list.add(basyMap.get("inHospitalDays"));
                        list.add(syzdMap.get("inDiagnosisName1"));
                        list.add(syzdMap.get("inDiagnosisName2"));
                        list.add(syzdMap.get("inDiagnosisName3"));
                        list.add(syzdMap.get("inDiagnosisName4"));
                        list.add(syzdMap.get("inDiagnosisName5"));
                        list.add(syzdMap.get("outDiagnosisName1"));
                        list.add(syzdMap.get("outDiagnosisName2"));
                        list.add(syzdMap.get("outDiagnosisName3"));
                        list.add(syzdMap.get("outDiagnosisName4"));
                        list.add(syzdMap.get("outDiagnosisName5"));
                        list.add(syzdMap.get("outDiagnosisName6"));
                        list.add(syzdMap.get("outDiagnosisName7"));
                        list.add(syzdMap.get("outDiagnosisName8"));
                        list.add(syzdMap.get("outDiagnosisName9"));
                        list.add(syzdMap.get("outDiagnosisName10"));
                        list.add(syzdMap.get("outDiagnosisCode1"));
                        list.add(syzdMap.get("outDiagnosisCode2"));
                        list.add(syzdMap.get("outDiagnosisCode3"));
                        list.add(syzdMap.get("outDiagnosisCode4"));
                        list.add(syzdMap.get("outDiagnosisCode5"));
                        list.add(syzdMap.get("outDiagnosisCode6"));
                        list.add(syzdMap.get("outDiagnosisCode7"));
                        list.add(syzdMap.get("outDiagnosisCode8"));
                        list.add(syzdMap.get("outDiagnosisCode9"));
                        list.add(syzdMap.get("outDiagnosisCode10"));
                        list.add(syzdMap.get("inDiagnosisTime"));
                        list.add(syzdMap.get("outDiagnosisTime"));
                        list.add(zyfyMap.get("totalFee"));
                    }
                }
            }
            listArray.add(list);
        }

//        String fileName = "C:\\Users\\songw\\Desktop\\嘉和美康工作\\201901\\20190130\\北医三院罕见病.xlsx";
        String fileName = "北医三院罕见病.xlsx";
        //导出excel
        ExportExcelUtil.exportExcelToDisk(fileName,headersList,listArray);
        atResponse.setResponseCode(ResponseCode.OK);
        atResponse.setData("导出成功，请在"+fileName+"下查找相关文件");
        return atResponse;
    }

}

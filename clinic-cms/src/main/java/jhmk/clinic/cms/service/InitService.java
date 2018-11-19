package jhmk.clinic.cms.service;

import jhmk.clinic.cms.controller.ruleService.BasyService;
import jhmk.clinic.core.util.MyThreadPoolManager;
import jhmk.clinic.entity.cdss.CdssRuleBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;

/**
 * @author ziyu.zhou
 * @date 2018/7/17 16:32
 */
@Service
public class InitService {


    //疾病集合
    static Set<String> liiNames = new HashSet<>();
    //病例集合
    public volatile static List<CdssRuleBean> caseList = new LinkedList<>();
    public volatile static List<CdssRuleBean> caseListCy = new LinkedList<>();
    public volatile static Set<String> diseaseNames = new HashSet<>();

    @PostConstruct
    public void init() throws Exception {
        System.out.println("初始化方法进来了啊");
        readFile2Cache();
//        getRandomAllData();
//        addCase2cache2();
        addCyCase2cache2();
        addDiseaseName2Cache();
    }
//    public void test() {
//        CdssService cdssService = new CdssService();
//        BasyService basyService=new BasyService();
//        List<String> list = ReadFileService.readSourceList("testid");
//        List<String> result = new ArrayList<>();
//        StringBuilder sb = null;
//        for (String id : list) {
//            sb = new StringBuilder();
//            String cyzd = cdssService.getCyzdByPidAndVid(id);
//            Binganshouye beanByPidAndVid = basyService.getBeanByPidAndVid(id);
//            String pat_visit_dept_admission_to_name = beanByPidAndVid.getPat_visit_dept_admission_to_name();
//            String pat_visit_dept_discharge_from_name = beanByPidAndVid.getPat_visit_dept_discharge_from_name();
//            sb.append(cyzd).append("==").append(pat_visit_dept_admission_to_name).append("==").append(pat_visit_dept_discharge_from_name);
//            result.add(sb.toString());
//        }
//        Write2File.w2fileList(list, "/data/1/CDSS/1019data.txt");
//    }

    //经理指定随机病例
    private void getRandomAllData() {
        List<String> list = ReadFileService.readSourceList("assignId");
        CdssService cdssService = new CdssService();
        List<CdssRuleBean> idList = cdssService.getAllIdsByIllName(list);

        for (CdssRuleBean cdssRuleBean : idList) {
            String id = cdssRuleBean.getId();
            //查询  ruyuanjilu 一诉五史
            CdssRuleBean cdssTestBean = cdssService.selruyuanjiluById(id);
            if (cdssTestBean.getRuyuanjilu() == null) {
                continue;
            }
            cdssTestBean.setMainIllName(cdssRuleBean.getMainIllName());
            //病案首页
            Map selbinganshouye = cdssService.selBasy(id);
            cdssTestBean.setBinganshouye(selbinganshouye);
            //病例诊断
            List<Map<String, String>> selbinglizhenduan1 = cdssService.selbinglizhenduan(id);
            cdssTestBean.setBinglizhenduan(selbinglizhenduan1);
            String rycz = cdssService.getRycz(id);
            cdssTestBean.setRycz(rycz);
            String cyzd = cdssService.getMainDisease(id);
            cdssTestBean.setCyzd(cyzd);

            //首页诊断
            List<Map<String, String>> syzdList = cdssService.selSyzd(id);
            cdssTestBean.setShouyezhenduan(syzdList);
//            List<Map<String, List<Map<String, String>>>> jianYan = cdssService.getJianYan(id);
//            cdssTestBean.setJianyanbaogao(jianYan);
            caseList.add(cdssTestBean);
        }
    }


    private void addDiseaseName2Cache() {
        BasyService basyService = new BasyService();
        Set<String> allDepts = basyService.getAllDepts();
        diseaseNames.addAll(allDepts);
    }

    private void addCase2cache2() {
        CdssService cdssService = new CdssService();
        List<CdssRuleBean> idList = cdssService.getAllIdsByIllName();
//        MyThreadPoolManager myThreadPoolManager = MyThreadPoolManager.getsInstance();
        ExecutorService executorService = MyThreadPoolManager.newBlockingExecutorsUseCallerRun();
        for (CdssRuleBean cdssRuleBean : idList) {
            String id = cdssRuleBean.getId();
            CdssRuleBean cdssTestBean = cdssService.selruyuanjiluById(id);
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    //查询  ruyuanjilu 一诉五史
                    if (cdssTestBean.getRuyuanjilu() != null) {
                        cdssTestBean.setMainIllName(cdssRuleBean.getMainIllName());
                        //病案首页
                        Map selbinganshouye = cdssService.selBasy(id);
                        cdssTestBean.setBinganshouye(selbinganshouye);
                        //病例诊断
                        List<Map<String, String>> selbinglizhenduan1 = cdssService.selbinglizhenduan(id);
                        cdssTestBean.setBinglizhenduan(selbinglizhenduan1);
                        //首页诊断
                        List<Map<String, String>> syzdList = cdssService.selSyzd(id);
                        cdssTestBean.setShouyezhenduan(syzdList);
                        List<Map<String, List<Map<String, String>>>> jianYan = cdssService.getJianYan(id);
//                        if (jianYan.size() > 0) {
//                            cdssTestBean.setJianyanbaogao(jianYan);
//                        }
                        String rycz = cdssService.getRycz(id);
                        cdssTestBean.setRycz(rycz);
                        String cyzd = cdssService.getMainDisease(id);
                        cdssTestBean.setCyzd(cyzd);
                        if (Objects.nonNull(cdssTestBean)) {
                            caseList.add(cdssTestBean);
                        }
                    }
                }

            };
            executorService.execute(task);
        }
    }

    /**
     * 朝阳医院
     */
    private void addCyCase2cache2() {
        CyCdssService cdssService = new CyCdssService();
        List<CdssRuleBean> idList = cdssService.getAllIdsByIllName();
//        MyThreadPoolManager myThreadPoolManager = MyThreadPoolManager.getsInstance();
        ExecutorService executorService = MyThreadPoolManager.newBlockingExecutorsUseCallerRun();
        for (CdssRuleBean cdssRuleBean : idList) {
            String id = cdssRuleBean.getId();
            CdssRuleBean cdssTestBean = cdssService.selruyuanjiluById(id);
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    //查询  ruyuanjilu 一诉五史
                    if (cdssTestBean.getRuyuanjilu() != null) {
                        cdssTestBean.setMainIllName(cdssRuleBean.getMainIllName());
                        //病案首页
                        Map selbinganshouye = cdssService.selBasy(id);
                        cdssTestBean.setBinganshouye(selbinganshouye);
                        //病例诊断
                        List<Map<String, String>> selbinglizhenduan1 = cdssService.selbinglizhenduan(id);
                        cdssTestBean.setBinglizhenduan(selbinglizhenduan1);
                        //首页诊断
                        List<Map<String, String>> syzdList = cdssService.selSyzd(id);
                        cdssTestBean.setShouyezhenduan(syzdList);
                        String rycz = cdssService.getCyyyRycz(id);
                        cdssTestBean.setRycz(rycz);
                        String cyzd = cdssService.getMainDisease(id);
                        cdssTestBean.setCyzd(cyzd);
                        if (Objects.nonNull(cdssTestBean)) {
                            caseListCy.add(cdssTestBean);
                        }
                    }
                }

            };
            executorService.execute(task);
        }
    }

    private void readFile2Cache() {
        Resource resource = new ClassPathResource("commonDiseases");
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
    }


    private void addCase2cache() {
        CdssService cdssService = new CdssService();
        List<CdssRuleBean> idList = cdssService.getAllIdsByIllName();

        for (CdssRuleBean cdssRuleBean : idList) {
            String id = cdssRuleBean.getId();
            //查询  ruyuanjilu 一诉五史
            CdssRuleBean cdssTestBean = cdssService.selruyuanjiluById(id);
            if (cdssTestBean.getRuyuanjilu() == null) {
                continue;
            }
            cdssTestBean.setMainIllName(cdssRuleBean.getMainIllName());
            //病案首页
            Map selbinganshouye = cdssService.selBasy(id);
            cdssTestBean.setBinganshouye(selbinganshouye);
            //病例诊断
            List<Map<String, String>> selbinglizhenduan1 = cdssService.selbinglizhenduan(id);
            cdssTestBean.setBinglizhenduan(selbinglizhenduan1);
            //首页诊断
            List<Map<String, String>> syzdList = cdssService.selSyzd(id);
            cdssTestBean.setShouyezhenduan(syzdList);
            List<Map<String, List<Map<String, String>>>> jianYan = cdssService.getJianYan(id);
//            cdssTestBean.setJianyanbaogao(jianYan);
            caseList.add(cdssTestBean);
        }
    }
}

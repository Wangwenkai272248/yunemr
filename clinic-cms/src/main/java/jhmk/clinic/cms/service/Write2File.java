package jhmk.clinic.cms.service;

import jhmk.clinic.cms.function.demo1.Demo1ZhenduanBean;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author ziyu.zhou
 * @date 2018/8/25 15:32
 */

public class Write2File {
    @Value("${yyConfig.fileUrl}")
    private String fileUrl;

    public static void wfile(String data, String fileName) {

        BufferedWriter bufferedWriter = null;
        //        File file = new File("/data/1/CDSS/3院骨科漏诊数据.txt");
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(data);
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void wfile(List<Demo1ZhenduanBean> data, String fileName) {

        BufferedWriter bufferedWriter = null;
        //        File file = new File("/data/1/CDSS/3院骨科漏诊数据.txt");
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (Demo1ZhenduanBean demo1ZhenduanBean : data) {

                bufferedWriter.write(demo1ZhenduanBean.getId() + "/" + demo1ZhenduanBean.getDiagnosis_name() + "/" + demo1ZhenduanBean.getDiagnosis_desc() + "/" + demo1ZhenduanBean.getDiagnosis_num() + "/"
                        + demo1ZhenduanBean.getDiagnosis_type_code() + "/" + demo1ZhenduanBean.getTreat_result_name() + "/" + demo1ZhenduanBean.getDiagnosis_time());

                bufferedWriter.newLine();
            }
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("===============写完啦");
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

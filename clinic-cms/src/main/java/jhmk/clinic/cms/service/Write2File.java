package jhmk.clinic.cms.service;

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

    public static void w2fileList(List<String> data, String fileName) {
        System.out.println("开始写了========");
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
            for (int i = 0; i < data.size(); i++) {
                bufferedWriter.write(data.get(i));
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
                System.out.println("=========结束啦");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

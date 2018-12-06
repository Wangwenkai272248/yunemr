package jhmk.clinic.cms.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ziyu.zhou
 * @date 2018/10/22 13:10
 */
@Service
public class BiaozhuService {
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

}

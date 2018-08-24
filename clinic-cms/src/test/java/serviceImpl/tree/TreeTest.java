package serviceImpl.tree;

import java.io.*;

/**
 * @author ziyu.zhou
 * @date 2018/7/12 9:50
 */

public class TreeTest {

    public static void main(String[] args) throws IOException {
        File file = new File("C:/Users/11075/Desktop/3院耳鼻喉科门诊诊断数量.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = null;
        while ((line = br.readLine()) != null) {
            int i = line.lastIndexOf(",");
            int length = line.length();
            String name = line.substring(0, i);
            String value = line.substring(i + 1);
            System.out.println(name+"#"+value);
        }
        br.close();
    }
}

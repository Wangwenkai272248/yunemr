package jhmk.clinic.core.util;

/**
 * @author ziyu.zhou
 * @date 2018/12/27 13:00
 */

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 全局参数获取工具类
 */
public class PropertiesConfigUtil extends PropertyPlaceholderConfigurer {
    private static Map<String, String> propertyMap;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        propertyMap = new HashMap<>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            propertyMap.put(keyStr, value);
        }
    }

    //
//    static {
//        Properties pro = new Properties();
//        InputStreamReader in = null;
//        try {
//            ClassPathResource resource = new ClassPathResource("config/mongo.properties");
//            InputStream inputStream = resource.getInputStream();
//            in = new InputStreamReader(inputStream, "utf-8");
//            pro.load(in);
//            propertyMap = new HashMap<>();
//            for (Object key : pro.keySet()) {
//                String keyStr = key.toString();
//                String value = pro.getProperty(keyStr);
//                propertyMap.put(keyStr, value);
//            }
//            System.out.println("配置文件读取成功：\n{isDebug:" + isDebug + ",activeTime:" + activeTime + ",log_path:" + log_path);
//        } catch (FileNotFoundException e) {
//            System.out.println("找不到配置文件");
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (in != null) {
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
    public static Object getProperty(String name) {
        return propertyMap.get(name);
    }

    public static void main(String[] ss) {
        System.out.println("开始业务处理！");
    }
}
package jhmk.clinic.cms.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @Author songw
 * @DATE 2019-2-14  18:09
 * @DESCRIPTION TODO
 */
public class JsonFile2MapUtil {

    public static void main(String[] args) throws IOException {
        JsonFile2MapUtil.readJsonData("child_first_diseaseRelationship.txt");
    }

    /**
     * 读取文件数据加入到map缓存中
     * @throws IOException
     */
    public static Map readJsonData(String fileName) throws IOException {
        Map<Object, Object> map = new HashMap();
        ClassPathResource resource = new ClassPathResource(fileName);
        File file = resource.getFile();
        String jsonString = FileUtils.readFileToString(file);
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        for (Object o : jsonObject.keySet()){
            map.put(o, jsonObject.get(o));
        }
        return map;
    }

    public static List<Object> getMapValues(Map<Object, Object> map){
        Collection<Object> valueCollection = map.values();
        final int size = valueCollection.size();

        List<Object> valueList = new ArrayList<Object>(valueCollection);

        String[] valueArray = new String[size];
        map.values().toArray(valueArray);
        return Arrays.asList(valueArray);
    }
}

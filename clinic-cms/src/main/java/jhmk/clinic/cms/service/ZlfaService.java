package jhmk.clinic.cms.service;

import jhmk.clinic.entity.pojo.YizhuBsjb;
import jhmk.clinic.entity.pojo.YizhuChange;
import jhmk.clinic.entity.pojo.YizhuResult;
import jhmk.clinic.entity.service.YizhuBsjbRepService;
import jhmk.clinic.entity.service.YizhuChangeRepService;
import jhmk.clinic.entity.service.YizhuOriRepService;
import jhmk.clinic.entity.service.YizhuResultRepService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author ziyu.zhou
 * @date 2018/10/13 15:37
 * 治疗方案serivce
 */
@Service
public class ZlfaService {


    @Autowired
    YizhuOriRepService yizhuOriRepService;
    @Autowired
    YizhuBsjbRepService yizhuBsjbRepService;
    @Autowired
    YizhuChangeRepService yizhuChangeRepService;
    @Autowired
    YizhuResultRepService yizhuResultRepService;


    /**
     * 解析医嘱 获取治疗方案相同项
     *
     * @param valueList
     */

    public List<Map.Entry<String, Integer>> analyzeYizhuResult(List<List<YizhuResult>> valueList) {
        Map<String, Integer> resultMap = new HashMap<>();
        for (int i = 0; i < valueList.size(); i++) {
            List<YizhuResult> yizhuResults = valueList.get(i);
            String proposeAndYizhuName = getProposeAndDrug(yizhuResults);
            if (StringUtils.isNotBlank(proposeAndYizhuName)) {

                if (resultMap.containsKey(proposeAndYizhuName)) {
                    resultMap.put(proposeAndYizhuName, resultMap.get(proposeAndYizhuName) + 1);
                } else {
                    resultMap.put(proposeAndYizhuName, 1);
                }
            }

        }
        ArrayList<Map.Entry<String, Integer>> entries = new ArrayList<>(resultMap.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(final Map.Entry<String, Integer> o1, final Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        return entries;
    }

    public String getProposeAndDrug(List<YizhuResult> value) {
        StringBuilder sb = new StringBuilder();
        for (YizhuResult yizhuResult : value) {
            sb.append(yizhuResult.getPurpose() + "-" + yizhuResult.getDrug() + "/");
        }
        return sb.toString();
    }

    public String getProposeAndDrugForBsjb(List<YizhuBsjb> value) {
        StringBuilder sb = new StringBuilder();
        for (YizhuBsjb yizhuResult : value) {
            if (StringUtils.isNotBlank(yizhuResult.getPurpose()) || StringUtils.isNotBlank(yizhuResult.getDrug())) {
                sb.append(yizhuResult.getPurpose() + "-" + yizhuResult.getDrug() + "/");
            }
        }
        return sb.toString();
    }

    public String getProposeAndDrugForChange(List<YizhuChange> value) {
        StringBuilder sb = new StringBuilder();
        for (YizhuChange yizhuResult : value) {
            sb.append(yizhuResult.getPurpose() + "-" + yizhuResult.getDrug() + "/");
        }
        return sb.toString();
    }

    public List<Map.Entry<String, Integer>> analyzeYizhuChange(List<List<YizhuChange>> valueList) {
        Map<String, Integer> resultMap = new HashMap<>();
        for (int i = 0; i < valueList.size(); i++) {
            List<YizhuChange> yizhuResults = valueList.get(i);
            String proposeAndYizhuName = getProposeAndDrugForChange(yizhuResults);
            if (StringUtils.isNotBlank(proposeAndYizhuName)) {

                if (resultMap.containsKey(proposeAndYizhuName)) {
                    resultMap.put(proposeAndYizhuName, resultMap.get(proposeAndYizhuName) + 1);
                } else {
                    resultMap.put(proposeAndYizhuName, 1);
                }
            }
        }
        ArrayList<Map.Entry<String, Integer>> entries = new ArrayList<>(resultMap.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(final Map.Entry<String, Integer> o1, final Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        return entries;
    }

    public List<Map.Entry<String, Integer>> analyzeYizhuBsjb(List<List<YizhuBsjb>> valueList) {
        Map<String, Integer> resultMap = new HashMap<>();
        for (int i = 0; i < valueList.size(); i++) {
            List<YizhuBsjb> yizhuResults = valueList.get(i);
            String proposeAndYizhuName = getProposeAndDrugForBsjb(yizhuResults);
            if (StringUtils.isNotBlank(proposeAndYizhuName)) {

                if (resultMap.containsKey(proposeAndYizhuName)) {
                    resultMap.put(proposeAndYizhuName, resultMap.get(proposeAndYizhuName) + 1);
                } else {
                    resultMap.put(proposeAndYizhuName, 1);
                }
            }
        }
        ArrayList<Map.Entry<String, Integer>> entries = new ArrayList<>(resultMap.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(final Map.Entry<String, Integer> o1, final Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        return entries;
    }

}

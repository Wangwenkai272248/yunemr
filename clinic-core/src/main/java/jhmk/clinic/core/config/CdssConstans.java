package jhmk.clinic.core.config;

import jhmk.clinic.core.util.PropertiesConfigUtil;

public class CdssConstans {


    public static final String BINGANSHOUYE = "binganshouye";
    public static final String ZHUANKEJILU = "zhuankejilu";
    public static final String BINGLIZHENDUAN = "binglizhenduan";
    public static final String SHOUYESHOUSHU = "shouyeshoushu";
    public static final String SHOUCIBINGCHENGJILU = "shoucibingchengjilu";
    public static final String SHOUYEZHENDUAN = "shouyezhenduan";
    public static final String RUYUANJILU = "ruyuanjilu";
    public static final String SJYSCFL = "shangjiyishichafanglu";
    public static final String JCBG = "jianchabaogao";
    public static final String JYBG = "jianyanbaogao";
    public static final String YIZHU = "yizhu";
    public static final String CHUYUANJILU = "chuyuanjilu";
    public static final String ZHUYUANFEIYONG = "zhuyuanfeiyong";


    public static final String MENZHENSHUJU = "menzhenshuju";
    public static final String MZJCBG = "menzhenjianchabaogao";
    public static final String MZJYBG = "menzhenjianyanbaogao";
    public static final String MENZHENZHENDUAN = "menzhenzhenduan";
    public static final String mzjzjl = "menzhenjiuzhenjilu";
    public static final String DECISION_RULE = "decision_rule";

//        public static final String URL = "http://localhost:8111/warn/rule/ruleMatch";
    //    public static final String URLFORRULE = "http://192.168.132:8111/warn/match/ruleMatch";
//    public static final String URLFORRULE = "http://192.168.8.22:8111/warn/match/ruleMatch";


    //3院服务器
//    public static final String DATASOURCE = "BJDXDSYY_ETL_V20180204";
//    public static final String HOST = "192.168.132.4";
//    public static final String URL = "http://192.168.132.7:8111/warn/rule/ruleMatch";
//    public static final String URLFORRULE = "http://192.168.132.7:8111/warn/match/ruleMatch";


    //朝阳服务器
//    public static final String HOST = "172.16.19.212";
//    public static final String URL = "http://192.168.132.7:8111/warn/rule/ruleMatch";
//    public static final String URLFORRULE = "http://192.168.132.7:8111/warn/match/ruleMatch";

    //        数据库
    public static final String CDSSDATASOURCE = PropertiesConfigUtil.getProperty("mongo.cdssdatasource").toString();
    public static final String DATASOURCE = PropertiesConfigUtil.getProperty("mongo.bysydatasource").toString();
    public static final String CYDATASOURCE = PropertiesConfigUtil.getProperty("mongo.cyyydatasource").toString();
    public static final String HOST = PropertiesConfigUtil.getProperty("mongo.host").toString();


    //model 前缀
    public static final String modelHead = PropertiesConfigUtil.getProperty("url.model").toString();

    //cdss 前缀
    public static final String cdssHead = PropertiesConfigUtil.getProperty("url.cdss").toString();
    //earlywarn 前缀
    public static final String earlywarnHead = PropertiesConfigUtil.getProperty("url.earlywarn").toString();
    //page小页面 前缀
    public static final String pageHead = PropertiesConfigUtil.getProperty("url.page").toString();
    //ES查询 前缀
    public static final String earlywarnRuleMtch = earlywarnHead + "/warn/match/ruleMatch";

    private static final String ESHEAD = PropertiesConfigUtil.getProperty("url.es").toString();


    public static final String QUERY = ESHEAD + "/med/cdss/query.json";

    //获取疾病同义词
    public static final String getSamilarWord = cdssHead + "/med/cdss/getSamilarWord.json";

    //获取疾病子节点
    public static final String getDiseaseChildrenList = cdssHead + "/med/cdss/getDiseaseChildrenList.json";
    //获取疾病父节点
    public static final String getParentList = cdssHead + "/med/cdss/getParentList.json";

    public static final int BEGINCOUNT = 0;
    public static final int ENDCOUNT = 20000;


    //es查询
//    public static final String patients = "http://192.168.8.31:8833/med/advanced/allVariableJilian.json";
    public static final String patients = ESHEAD+"/med/advanced/allVariableJilian.json";
//    public static final String patients="http://192.168.132.13:8800/med/advanced/allVariableJilian.json";


    static final public String DEVURL = "/data/1/CDSS/data/";
    public String LOCALURL = "";

}

package serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jhmk.clinic.cms.ApplicationCms;
import jhmk.clinic.core.util.StringUtil;
import jhmk.clinic.entity.pojo.SysDiseases;
import jhmk.clinic.entity.pojo.repository.SysDiseasesRepository;
import jhmk.clinic.entity.pojo.shiro.SysRole;
import jhmk.clinic.entity.pojo.shiro.UserInfo;
import jhmk.clinic.entity.pojo.shiro.repository.UserInfoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@SpringBootTest(classes = ApplicationCms.class)
@RunWith(SpringRunner.class)
public class PatVisitServiceImplTest1 {
    @Autowired
    SysDiseasesRepository diseasesListRepository;
    @Autowired
    UserInfoRepository repository;

    @Test
    public void findAllPatients() {
        List<UserInfo> all = repository.findAll();
        all.stream().forEach(s -> System.out.println(s.toString()));
        List<SysRole> roleList = all.get(0).getRoleList();
        roleList.stream().forEach(s -> System.out.println(s.toString()));

    }

    String s1 = "  {\n" +
            "      \"CNN\": \"恶性贫血\",\n" +
            "      \"ENN\": \"expx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"结核性脑膜炎\",\n" +
            "      \"ENN\": \"jhxnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"植物神经功能紊乱\",\n" +
            "      \"ENN\": \"zwsjgnwl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"单侧膝关节骨性关节病\",\n" +
            "      \"ENN\": \"dcxgjgxgjb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"主动脉弓综合征\",\n" +
            "      \"ENN\": \"zdmgzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性风湿性纵隔心包炎\",\n" +
            "      \"ENN\": \"mxfsxzgxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脑卒中\",\n" +
            "      \"ENN\": \"nzz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"酒精性肝病\",\n" +
            "      \"ENN\": \"jjxgb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"结核性胸腔积液\",\n" +
            "      \"ENN\": \"jhxxqjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"甲状腺激素不敏感综合征\",\n" +
            "      \"ENN\": \"jzxjsbmgzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"白喉性心肌炎\",\n" +
            "      \"ENN\": \"bhxxjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"频发性期前收缩\",\n" +
            "      \"ENN\": \"pfxqqss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"窦性停搏\",\n" +
            "      \"ENN\": \"nxtb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性肾小球肾炎\",\n" +
            "      \"ENN\": \"mxsxqsy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"舌咽神经痛\",\n" +
            "      \"ENN\": \"sysjt\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺结核\",\n" +
            "      \"ENN\": \"fjh\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"腰骶椎间盘脱出\",\n" +
            "      \"ENN\": \"ypzjptc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性前壁高侧壁心肌梗死\",\n" +
            "      \"ENN\": \"cjxqbgcbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"阔韧带妊娠\",\n" +
            "      \"ENN\": \"krdrs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"原发性单侧膝关节病\",\n" +
            "      \"ENN\": \"yfxdcxgjb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性下壁高侧壁正后壁心肌梗死\",\n" +
            "      \"ENN\": \"jxxbgcbzhbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"颈动脉狭窄\",\n" +
            "      \"ENN\": \"jdmxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肩关节脱位\",\n" +
            "      \"ENN\": \"jgjtw\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脓毒性休克\",\n" +
            "      \"ENN\": \"ndxxk\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"主动脉瓣狭窄伴关闭不全\",\n" +
            "      \"ENN\": \"zdmbxzbgbbq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"阵发性心动过速\",\n" +
            "      \"ENN\": \"zfxxdgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肝恶性肿瘤\",\n" +
            "      \"ENN\": \"gexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"完全性右束支传导阻滞\",\n" +
            "      \"ENN\": \"wqxyszcdzz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"外耳道胆脂瘤\",\n" +
            "      \"ENN\": \"weddzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"射精管囊肿\",\n" +
            "      \"ENN\": \"sjgnz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"支气管扩张症伴咯血\",\n" +
            "      \"ENN\": \"zqgkzzbkx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"乳腺原位癌\",\n" +
            "      \"ENN\": \"rxywa\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"难治性支气管哮喘\",\n" +
            "      \"ENN\": \"nzxzqgxc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性前间壁下壁心肌梗死\",\n" +
            "      \"ENN\": \"cjxqjbxbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性侧壁再发心肌梗死\",\n" +
            "      \"ENN\": \"jxcbzfxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"卵巢恶性肿瘤\",\n" +
            "      \"ENN\": \"lcexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"系统性红斑狼疮\",\n" +
            "      \"ENN\": \"xtxhblc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"遗传性嗜酸粒细胞增多\",\n" +
            "      \"ENN\": \"ycxsslxbzd\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性膝半月板损伤\",\n" +
            "      \"ENN\": \"cjxxbybss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"中叶性肺炎\",\n" +
            "      \"ENN\": \"zyxfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"结核性心肌炎\",\n" +
            "      \"ENN\": \"jhxxjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急进性肾小球肾炎\",\n" +
            "      \"ENN\": \"jjxsxqsy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"室性逸搏\",\n" +
            "      \"ENN\": \"sxyb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"革兰阴性细菌性肺炎\",\n" +
            "      \"ENN\": \"glyxxjxfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"淋巴结炎\",\n" +
            "      \"ENN\": \"lbjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"非风湿性二尖瓣狭窄\",\n" +
            "      \"ENN\": \"ffsxejbxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"反应性低血糖症\",\n" +
            "      \"ENN\": \"fyxdxtz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性侧壁正后壁心肌梗死\",\n" +
            "      \"ENN\": \"cjxcbzhbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脂肪肝\",\n" +
            "      \"ENN\": \"zfg\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性间壁再发心肌梗死\",\n" +
            "      \"ENN\": \"jxjbzfxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"紧张性头痛\",\n" +
            "      \"ENN\": \"jzxtt\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"亚急性细菌性心内膜炎\",\n" +
            "      \"ENN\": \"yjxxjxxnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性下壁高侧壁再发心肌梗死\",\n" +
            "      \"ENN\": \"jxxbgcbzfxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"下肢静脉曲张合并静脉炎\",\n" +
            "      \"ENN\": \"xzjmqzhbjmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性前间壁再发心肌梗死\",\n" +
            "      \"ENN\": \"jxqjbzfxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"完全性肠梗阻\",\n" +
            "      \"ENN\": \"wqxcgz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺泡蛋白沉着症\",\n" +
            "      \"ENN\": \"fpdbczz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"妊娠合并交界性心动过速\",\n" +
            "      \"ENN\": \"rshbjjxxdgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"丘脑出血\",\n" +
            "      \"ENN\": \"qncx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"低置胎盘\",\n" +
            "      \"ENN\": \"dztp\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺下叶恶性肿瘤\",\n" +
            "      \"ENN\": \"fxyexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"念珠菌性心内膜炎\",\n" +
            "      \"ENN\": \"nzjxxnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性下壁侧壁正后壁心肌梗死\",\n" +
            "      \"ENN\": \"jxxbcbzhbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺良性肿瘤\",\n" +
            "      \"ENN\": \"flxzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"附睾炎\",\n" +
            "      \"ENN\": \"fey\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"甲状腺恶性肿瘤\",\n" +
            "      \"ENN\": \"jzxexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"下肢静脉功能不全\",\n" +
            "      \"ENN\": \"xzjmgnbq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"期前收缩\",\n" +
            "      \"ENN\": \"qqss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"皮克病性痴呆\",\n" +
            "      \"ENN\": \"pkbxcd\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"产褥期轻度贫血\",\n" +
            "      \"ENN\": \"crqqdpx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性淋巴细胞性甲状腺炎\",\n" +
            "      \"ENN\": \"mxlbxbxjzxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"高脂血症\",\n" +
            "      \"ENN\": \"gzxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"梅毒性二尖瓣狭窄\",\n" +
            "      \"ENN\": \"mdxejbxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肩关节复发性脱位\",\n" +
            "      \"ENN\": \"jgjffxtw\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺结核(仅痰涂片证实)\",\n" +
            "      \"ENN\": \"fjhAjttpzsA\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"真菌性心内膜炎\",\n" +
            "      \"ENN\": \"zjxxnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"非毒性多结节性甲状腺肿\",\n" +
            "      \"ENN\": \"fdxdjjxjzxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肥厚性心肌病\",\n" +
            "      \"ENN\": \"fhxxjb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"大脑镰下疝\",\n" +
            "      \"ENN\": \"dnlxf\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"二尖瓣狭窄\",\n" +
            "      \"ENN\": \"ejbxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"阵发性室性心动过速\",\n" +
            "      \"ENN\": \"zfxsxxdgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"结核性胸膜炎(初治)\",\n" +
            "      \"ENN\": \"jhxxmyAczA\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"膜性肾病\",\n" +
            "      \"ENN\": \"mxsb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"结节性淋巴细胞为主型霍奇金淋巴瘤\",\n" +
            "      \"ENN\": \"jjxlbxbwzxhqjlbl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"二度I型房室传导阻滞\",\n" +
            "      \"ENN\": \"edIxfscdzz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"甲状腺良性肿瘤\",\n" +
            "      \"ENN\": \"jzxlxzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肝硬化\",\n" +
            "      \"ENN\": \"gyh\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"一度房室阻滞\",\n" +
            "      \"ENN\": \"ydfszz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"腹部恶性肿瘤\",\n" +
            "      \"ENN\": \"fbexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新型隐球菌脑膜炎\",\n" +
            "      \"ENN\": \"xxyqjnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"十二指肠球部溃疡\",\n" +
            "      \"ENN\": \"sezcqbky\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"重症肌无力\",\n" +
            "      \"ENN\": \"zzjwl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"房室结折返性心动过速\",\n" +
            "      \"ENN\": \"fsjzfxxdgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胸膜炎伴积液\",\n" +
            "      \"ENN\": \"xmybjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺恶性肿瘤\",\n" +
            "      \"ENN\": \"fexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性胎儿宫内窘迫(胎心型)\",\n" +
            "      \"ENN\": \"jxtegnjpAtxxA\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"非风湿性主动脉瓣关闭不全\",\n" +
            "      \"ENN\": \"ffsxzdmbgbbq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性前间壁下壁再发心肌梗死\",\n" +
            "      \"ENN\": \"jxqjbxbzfxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性心肌梗死\",\n" +
            "      \"ENN\": \"jxxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"细菌性心包炎\",\n" +
            "      \"ENN\": \"xjxxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新生儿室性期前收缩\",\n" +
            "      \"ENN\": \"xsesxqqss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"特发性胸腔积液\",\n" +
            "      \"ENN\": \"tfxxqjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"隐源性机化性肺炎\",\n" +
            "      \"ENN\": \"yyxjhxfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"塞扎里综合征\",\n" +
            "      \"ENN\": \"szlzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性ST段抬高型心肌梗死\",\n" +
            "      \"ENN\": \"jxSTdtgxxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肝性脑病\",\n" +
            "      \"ENN\": \"gxnb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"病毒性心肌炎\",\n" +
            "      \"ENN\": \"bdxxjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"膝髌骨外侧过度挤压综合征\",\n" +
            "      \"ENN\": \"xwgwcgdjyzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"双侧腹股沟疝\",\n" +
            "      \"ENN\": \"scfggr\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"支气管脂肪瘤\",\n" +
            "      \"ENN\": \"zqgzfl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"玻璃体积血\",\n" +
            "      \"ENN\": \"bltjx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"产褥期心功能不全\",\n" +
            "      \"ENN\": \"crqxgnbq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"颈内动脉海绵窦瘘\",\n" +
            "      \"ENN\": \"jndmhmpt\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心内膜炎并二尖瓣穿孔\",\n" +
            "      \"ENN\": \"xnmybejbck\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"中毒性心肌炎\",\n" +
            "      \"ENN\": \"zdxxjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性浅表性疣状胃炎\",\n" +
            "      \"ENN\": \"mxqbxuzwy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性心肌心包炎\",\n" +
            "      \"ENN\": \"jxxjxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胸膜炎\",\n" +
            "      \"ENN\": \"xmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"神经纤维瘤病\",\n" +
            "      \"ENN\": \"sjxwlb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"卧位型心绞痛\",\n" +
            "      \"ENN\": \"wwxxjt\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性心肌炎\",\n" +
            "      \"ENN\": \"jxxjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"妊娠合并感染性心包炎\",\n" +
            "      \"ENN\": \"rshbgrxxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性风湿性心内膜炎\",\n" +
            "      \"ENN\": \"jxfsxxnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"卵巢黄体破裂\",\n" +
            "      \"ENN\": \"lchtpl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"持续性室性心动过速\",\n" +
            "      \"ENN\": \"cxxsxxdgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性膝内侧半月板损伤\",\n" +
            "      \"ENN\": \"cjxxncbybss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"股骨头缺血性坏死\",\n" +
            "      \"ENN\": \"ggtqxxhs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"抗磷脂综合征\",\n" +
            "      \"ENN\": \"klzzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"痛风性关节炎\",\n" +
            "      \"ENN\": \"tfxgjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"输卵管妊娠\",\n" +
            "      \"ENN\": \"slgrs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"宫颈上皮瘤样病变II级\",\n" +
            "      \"ENN\": \"gjsplybbIIj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心绞痛\",\n" +
            "      \"ENN\": \"xjt\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"左心自体瓣膜性心内膜炎\",\n" +
            "      \"ENN\": \"zxztbmxxnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性下壁正后壁心肌梗死\",\n" +
            "      \"ENN\": \"cjxxbzhbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"初发心房颤动\",\n" +
            "      \"ENN\": \"cfxfcd\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"隐球菌脑膜炎\",\n" +
            "      \"ENN\": \"yqjnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"难治性高血压\",\n" +
            "      \"ENN\": \"nzxgxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性前壁下壁心肌梗死\",\n" +
            "      \"ENN\": \"cjxqbxbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肥胖\",\n" +
            "      \"ENN\": \"fp\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"围手术期心肌梗死\",\n" +
            "      \"ENN\": \"wssqxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肾功能不全\",\n" +
            "      \"ENN\": \"sgnbq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"主动脉夹层A3C型\",\n" +
            "      \"ENN\": \"zdmjcA3Cx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"原发性醛固酮增多症\",\n" +
            "      \"ENN\": \"yfxqgtzdz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"主动脉夹层形成\",\n" +
            "      \"ENN\": \"zdmjcxc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"卵巢良性肿瘤\",\n" +
            "      \"ENN\": \"lclxzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脑膜炎球菌性心包炎\",\n" +
            "      \"ENN\": \"nmyqjxxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性广泛前壁下壁高侧壁心肌梗死\",\n" +
            "      \"ENN\": \"jxgfqbxbgcbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性广泛前壁下壁心肌梗死\",\n" +
            "      \"ENN\": \"jxgfqbxbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性胆囊炎\",\n" +
            "      \"ENN\": \"mxdny\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺曲霉菌病\",\n" +
            "      \"ENN\": \"fqmjb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"流行性腮腺炎伴心肌炎\",\n" +
            "      \"ENN\": \"lxxsxybxjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脑血栓形成\",\n" +
            "      \"ENN\": \"nxsxc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肠易激综合征\",\n" +
            "      \"ENN\": \"cyjzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"横纹肌溶解\",\n" +
            "      \"ENN\": \"hwjrj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"痴呆\",\n" +
            "      \"ENN\": \"cd\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"膝外侧半月板撕裂\",\n" +
            "      \"ENN\": \"xwcbybsl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"后循环缺血\",\n" +
            "      \"ENN\": \"hxhqx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"妊娠合并室上性心动过速\",\n" +
            "      \"ENN\": \"rshbssxxdgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性非ST段抬高型心肌梗死\",\n" +
            "      \"ENN\": \"jxfSTdtgxxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"西部马脑炎\",\n" +
            "      \"ENN\": \"xbmny\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"链球菌性心内膜炎\",\n" +
            "      \"ENN\": \"lqjxxnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"冠状动脉旁路移植手术相关性心肌梗死\",\n" +
            "      \"ENN\": \"gzdmplyzssxgxxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"癫痫\",\n" +
            "      \"ENN\": \"lx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性下壁后壁心肌梗死\",\n" +
            "      \"ENN\": \"cjxxbhbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"2型糖尿病\",\n" +
            "      \"ENN\": \"2xtnb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"输卵管妊娠破裂\",\n" +
            "      \"ENN\": \"slgrspl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肾结核\",\n" +
            "      \"ENN\": \"sjh\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"子宫纵隔\",\n" +
            "      \"ENN\": \"zgzg\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"门内特里尔病\",\n" +
            "      \"ENN\": \"mntleb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"蛛网膜下腔出血\",\n" +
            "      \"ENN\": \"zwmxqcx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"卵巢妊娠\",\n" +
            "      \"ENN\": \"lcrs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"圆锥角膜\",\n" +
            "      \"ENN\": \"yzjm\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"妊娠合并急性心肌梗死\",\n" +
            "      \"ENN\": \"rshbjxxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性闭角型青光眼\",\n" +
            "      \"ENN\": \"jxbjxqgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺不张\",\n" +
            "      \"ENN\": \"fbz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"非风湿性二尖瓣关闭不全\",\n" +
            "      \"ENN\": \"ffsxejbgbbq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新生儿低血糖症\",\n" +
            "      \"ENN\": \"xsedxtz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性肾功能不全尿毒症期\",\n" +
            "      \"ENN\": \"mxsgnbqndzq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性下壁后壁右心室心肌梗死\",\n" +
            "      \"ENN\": \"jxxbhbyxsxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性风湿性心肌炎\",\n" +
            "      \"ENN\": \"jxfsxxjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"阵发性心房扑动\",\n" +
            "      \"ENN\": \"zfxxfpd\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"输卵管积水\",\n" +
            "      \"ENN\": \"slgjs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"膈疝\",\n" +
            "      \"ENN\": \"uf\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"变应性支气管肺曲霉病\",\n" +
            "      \"ENN\": \"byxzqgfqmb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"高血压3级\",\n" +
            "      \"ENN\": \"gxy3j\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"右束支传导阻滞\",\n" +
            "      \"ENN\": \"yszcdzz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"永久性心房颤动\",\n" +
            "      \"ENN\": \"yjxxfcd\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"帕金森病\",\n" +
            "      \"ENN\": \"pjsb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"下肢深静脉血栓形成\",\n" +
            "      \"ENN\": \"xzsjmxsxc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"真菌性心肌炎\",\n" +
            "      \"ENN\": \"zjxxjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"衣原体性肺炎\",\n" +
            "      \"ENN\": \"yytxfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"δ-β型地中海贫血\",\n" +
            "      \"ENN\": \"bAdxdzhpx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"高尿酸血症\",\n" +
            "      \"ENN\": \"gnsxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"原发性甲状腺功能亢进\",\n" +
            "      \"ENN\": \"yfxjzxgnkj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"反射性晕厥\",\n" +
            "      \"ENN\": \"fsxyy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"横结肠恶性肿瘤\",\n" +
            "      \"ENN\": \"hjcexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"亚急性非化脓性甲状腺炎\",\n" +
            "      \"ENN\": \"yjxfhnxjzxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"单侧腹股沟直疝\",\n" +
            "      \"ENN\": \"dcfggzx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺栓塞\",\n" +
            "      \"ENN\": \"fss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"睾丸扭转\",\n" +
            "      \"ENN\": \"rwnz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"HIV性心内膜炎\",\n" +
            "      \"ENN\": \"HIVxxnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"病态窦房结综合征\",\n" +
            "      \"ENN\": \"btufjzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"遗传性球形红细胞增多症\",\n" +
            "      \"ENN\": \"ycxqxhxbzdz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脑出血恢复期\",\n" +
            "      \"ENN\": \"ncxhfq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性广泛前壁下壁心肌梗死\",\n" +
            "      \"ENN\": \"cjxgfqbxbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脑干梗死\",\n" +
            "      \"ENN\": \"nggs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"类风湿性关节炎\",\n" +
            "      \"ENN\": \"lfsxgjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"冠状动脉供血不足\",\n" +
            "      \"ENN\": \"gzdmgxbz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新生儿心肌损害\",\n" +
            "      \"ENN\": \"xsexjsh\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"膝关节后十字韧带完全断裂\",\n" +
            "      \"ENN\": \"xgjhszrdwqdl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性阑尾炎伴腹膜炎\",\n" +
            "      \"ENN\": \"jxlwybfmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"高血压2级\",\n" +
            "      \"ENN\": \"gxy2j\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"吉兰-巴雷综合征\",\n" +
            "      \"ENN\": \"jlAblzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"主动脉夹层B2C型\",\n" +
            "      \"ENN\": \"zdmjcB2Cx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺结核(显微镜检证实)\",\n" +
            "      \"ENN\": \"fjhAxwjjzsA\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"小脑扁桃体下疝畸形\",\n" +
            "      \"ENN\": \"xnbttxjjx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"膝外侧半月板伴十字韧带损伤\",\n" +
            "      \"ENN\": \"xwcbybbszrdss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"亚急性甲状腺炎\",\n" +
            "      \"ENN\": \"yjxjzxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"局灶性大脑挫裂伤\",\n" +
            "      \"ENN\": \"jzxdncls\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"弥漫性肺间质疾病\",\n" +
            "      \"ENN\": \"mmxfjzjb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"降结肠恶性肿瘤\",\n" +
            "      \"ENN\": \"jjcexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心源性猝死\",\n" +
            "      \"ENN\": \"xyxvs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"妊娠合并绒毛膜羊膜炎\",\n" +
            "      \"ENN\": \"rshbrmmymy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胆管梗阻\",\n" +
            "      \"ENN\": \"dggz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性纵隔炎\",\n" +
            "      \"ENN\": \"mxzgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"机化性肺炎\",\n" +
            "      \"ENN\": \"jhxfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"左主支气管恶性肿瘤\",\n" +
            "      \"ENN\": \"zzzqgexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"A型预激综合征\",\n" +
            "      \"ENN\": \"Axyjzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新生儿一度房室传导阻滞\",\n" +
            "      \"ENN\": \"xseydfscdzz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"乳腺恶性肿瘤\",\n" +
            "      \"ENN\": \"rxexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性支气管炎急性发作\",\n" +
            "      \"ENN\": \"mxzqgyjxfz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"额颞叶痴呆\",\n" +
            "      \"ENN\": \"egycd\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"鼻窦炎\",\n" +
            "      \"ENN\": \"bvy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"恶性高血压\",\n" +
            "      \"ENN\": \"exgxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"膀胱侧壁恶性肿瘤\",\n" +
            "      \"ENN\": \"bicbexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"主动脉夹层A1C型\",\n" +
            "      \"ENN\": \"zdmjcA1Cx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肾结石\",\n" +
            "      \"ENN\": \"sjs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"扩张性心肌病\",\n" +
            "      \"ENN\": \"kzxxjb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"后天性肾囊肿\",\n" +
            "      \"ENN\": \"htxsnz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性扁桃体炎\",\n" +
            "      \"ENN\": \"jxbtty\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"室性折返性心律失常\",\n" +
            "      \"ENN\": \"sxzfxxlsc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性下侧壁再发心肌梗死\",\n" +
            "      \"ENN\": \"jxxcbzfxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脑膜良性肿瘤\",\n" +
            "      \"ENN\": \"nmlxzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性重症胰腺炎\",\n" +
            "      \"ENN\": \"jxzzyxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"二尖瓣脱垂伴关闭不全\",\n" +
            "      \"ENN\": \"ejbtcbgbbq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性下壁高侧壁正后壁心肌梗死\",\n" +
            "      \"ENN\": \"cjxxbgcbzhbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"右肺下叶恶性肿瘤\",\n" +
            "      \"ENN\": \"yfxyexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"1型糖尿病\",\n" +
            "      \"ENN\": \"1xtnb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性出血性白质脑炎\",\n" +
            "      \"ENN\": \"jxcxxbzny\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心源性休克\",\n" +
            "      \"ENN\": \"xyxxk\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性肾衰竭\",\n" +
            "      \"ENN\": \"mxssj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"语义性痴呆\",\n" +
            "      \"ENN\": \"yyxcd\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"阻塞性肺气肿\",\n" +
            "      \"ENN\": \"zsxfqz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"非风湿性主动脉瓣狭窄\",\n" +
            "      \"ENN\": \"ffsxzdmbxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性广泛前壁下壁高侧壁心肌梗死\",\n" +
            "      \"ENN\": \"cjxgfqbxbgcbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"冠状动脉粥样硬化性心脏病\",\n" +
            "      \"ENN\": \"gzdmzyyhxxzb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胸腔积液\",\n" +
            "      \"ENN\": \"xqjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"多重感染的肺炎\",\n" +
            "      \"ENN\": \"dzgrdfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"弥漫性特发性骨肥厚\",\n" +
            "      \"ENN\": \"mmxtfxgfh\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性下壁高侧壁心肌梗死\",\n" +
            "      \"ENN\": \"cjxxbgcbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"妊娠期高血压\",\n" +
            "      \"ENN\": \"rsqgxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性心包炎\",\n" +
            "      \"ENN\": \"mxxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"黏膜皮肤淋巴结综合征\",\n" +
            "      \"ENN\": \"ampflbjzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"梗阻性黄疸\",\n" +
            "      \"ENN\": \"gzxhm\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"咳嗽变异型哮喘\",\n" +
            "      \"ENN\": \"ksbyxxc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性肝衰竭\",\n" +
            "      \"ENN\": \"jxgsj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"横结肠良性肿瘤\",\n" +
            "      \"ENN\": \"hjclxzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性肺栓塞\",\n" +
            "      \"ENN\": \"jxfss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"皮肌炎\",\n" +
            "      \"ENN\": \"pjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺间质纤维化\",\n" +
            "      \"ENN\": \"fjzxwh\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肝内胆管结石\",\n" +
            "      \"ENN\": \"gndgjs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性正后壁再发心肌梗死\",\n" +
            "      \"ENN\": \"jxzhbzfxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"子宫内膜息肉\",\n" +
            "      \"ENN\": \"zgnmxr\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"艾森曼格综合征\",\n" +
            "      \"ENN\": \"asmgzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"尿潴留\",\n" +
            "      \"ENN\": \"nml\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"毛细血管扩张症\",\n" +
            "      \"ENN\": \"mxxgkzz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"先天性冠状动脉肺动脉瘘\",\n" +
            "      \"ENN\": \"xtxgzdmfdmg\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"多发性骨髓瘤\",\n" +
            "      \"ENN\": \"dfxgsl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"风湿性二尖瓣关闭不全\",\n" +
            "      \"ENN\": \"fsxejbgbbq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"三分支传导阻滞\",\n" +
            "      \"ENN\": \"sfzcdzz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性细支气管炎\",\n" +
            "      \"ENN\": \"jxxzqgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胃溃疡伴出血\",\n" +
            "      \"ENN\": \"wkybcx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"妊娠合并甲状腺功能减退\",\n" +
            "      \"ENN\": \"rshbjzxgnjt\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"功能性子宫出血\",\n" +
            "      \"ENN\": \"gnxzgcx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肾积水伴肾结石\",\n" +
            "      \"ENN\": \"sjsbsjs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"手术后心力衰竭伴肺水肿\",\n" +
            "      \"ENN\": \"sshxlsjbfsz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"单侧腹股沟疝\",\n" +
            "      \"ENN\": \"dcfggi\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"复发性髌骨不全脱位\",\n" +
            "      \"ENN\": \"ffxjgbqtw\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"蚕豆病\",\n" +
            "      \"ENN\": \"cdb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"边缘性前置胎盘伴出血\",\n" +
            "      \"ENN\": \"byxqztpbcx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"白塞病\",\n" +
            "      \"ENN\": \"bsb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺上叶恶性肿瘤\",\n" +
            "      \"ENN\": \"fsyexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"周期性瘫痪\",\n" +
            "      \"ENN\": \"zqxth\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"创伤性蛛网膜下腔出血\",\n" +
            "      \"ENN\": \"csxzwmxqcx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胃食管反流\",\n" +
            "      \"ENN\": \"wsgfl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"结肠恶性肿瘤\",\n" +
            "      \"ENN\": \"jcexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"高血压性心脏病和肾脏病\",\n" +
            "      \"ENN\": \"gxyxxzbhszb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"窦性心动过缓\",\n" +
            "      \"ENN\": \"oxxdgh\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺脓肿\",\n" +
            "      \"ENN\": \"fnz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心律失常\",\n" +
            "      \"ENN\": \"xlsc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"二度II型房室传导阻滞\",\n" +
            "      \"ENN\": \"edIIxfscdzz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"自律性增高性房性心动过速\",\n" +
            "      \"ENN\": \"zlxzgxfxxdgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性膝前十字韧带断裂\",\n" +
            "      \"ENN\": \"cjxxqszrddl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性踝距骨软骨损伤\",\n" +
            "      \"ENN\": \"cjxxjgrgss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胆总管结石伴慢性胆囊炎\",\n" +
            "      \"ENN\": \"dzgjsbmxdny\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性阻塞性肺疾病\",\n" +
            "      \"ENN\": \"mxzsxfjb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"帕金森病性痴呆\",\n" +
            "      \"ENN\": \"pjsbxcd\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"腹壁疝\",\n" +
            "      \"ENN\": \"fbn\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"绿脓杆菌性肺炎\",\n" +
            "      \"ENN\": \"lngjxfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"血栓闭塞性脉管炎\",\n" +
            "      \"ENN\": \"xsbsxmgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"荨麻疹\",\n" +
            "      \"ENN\": \"vmz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"结性心律\",\n" +
            "      \"ENN\": \"jxxl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性前间壁高侧壁再发心肌梗死\",\n" +
            "      \"ENN\": \"jxqjbgcbzfxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"真菌性肺炎\",\n" +
            "      \"ENN\": \"zjxfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"高度房室传导阻滞\",\n" +
            "      \"ENN\": \"gdfscdzz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"恶性胸腔积液\",\n" +
            "      \"ENN\": \"exxqjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"系统性血管炎\",\n" +
            "      \"ENN\": \"xtxxgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"尿毒症\",\n" +
            "      \"ENN\": \"ndz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性下壁再发心肌梗死\",\n" +
            "      \"ENN\": \"jxxbzfxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"痔\",\n" +
            "      \"ENN\": \"z\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺血栓栓塞症\",\n" +
            "      \"ENN\": \"fxsssz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肾输尿管结石\",\n" +
            "      \"ENN\": \"ssngjs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性腹泻\",\n" +
            "      \"ENN\": \"mxfx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脊髓良性肿瘤\",\n" +
            "      \"ENN\": \"jslxzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"结核性胸膜炎\",\n" +
            "      \"ENN\": \"jhxxmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"椎-基底动脉供血不全\",\n" +
            "      \"ENN\": \"zAjddmgxbq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性间质性肺炎\",\n" +
            "      \"ENN\": \"jxjzxfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性右心室心肌梗死\",\n" +
            "      \"ENN\": \"cjxyxsxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"混合型心绞痛\",\n" +
            "      \"ENN\": \"hhxxjt\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"输卵管峡部妊娠\",\n" +
            "      \"ENN\": \"slgxbrs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"李斯特菌性心内膜炎\",\n" +
            "      \"ENN\": \"lstjxxnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"风湿性三尖瓣关闭不全\",\n" +
            "      \"ENN\": \"fsxsjbgbbq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"感染性心内膜炎\",\n" +
            "      \"ENN\": \"grxxnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"淋球菌性心内膜炎\",\n" +
            "      \"ENN\": \"lqjxxnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性心肌梗死后心脏破裂伴心包积血\",\n" +
            "      \"ENN\": \"jxxjgshxzplbxbjx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"混合型颈椎病\",\n" +
            "      \"ENN\": \"hhxjzb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"I型呼吸衰竭\",\n" +
            "      \"ENN\": \"Ixhxsj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"多发性脑梗死\",\n" +
            "      \"ENN\": \"dfxngs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心肌炎\",\n" +
            "      \"ENN\": \"xjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"中枢性睡眠呼吸暂停低通气综合征\",\n" +
            "      \"ENN\": \"zsxsmhxztdtqzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"糖尿病性肾病\",\n" +
            "      \"ENN\": \"tnbxsb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"复发性视网膜脱离\",\n" +
            "      \"ENN\": \"ffxswmtl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"膝色素沉着绒毛结节性滑膜炎\",\n" +
            "      \"ENN\": \"xssczrmjjxhmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"间质性肺炎\",\n" +
            "      \"ENN\": \"jzxfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"室间隔缺损\",\n" +
            "      \"ENN\": \"sjgqs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"阿尔茨海默病\",\n" +
            "      \"ENN\": \"aechmb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性广泛前壁下壁高侧壁再发心肌梗死\",\n" +
            "      \"ENN\": \"jxgfqbxbgcbzfxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"亚急性感染性心内膜炎\",\n" +
            "      \"ENN\": \"yjxgrxxnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心室颤动伴扑动\",\n" +
            "      \"ENN\": \"xscdbpd\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺炎链球菌性肺炎\",\n" +
            "      \"ENN\": \"fylqjxfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胃肠炎\",\n" +
            "      \"ENN\": \"wcy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"腔隙性脑梗死\",\n" +
            "      \"ENN\": \"qxxngs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"老年性白内障\",\n" +
            "      \"ENN\": \"lnxbnz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"尿毒症性心肌炎\",\n" +
            "      \"ENN\": \"ndzxxjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性细支气管炎\",\n" +
            "      \"ENN\": \"mxxzqgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺血管畸形\",\n" +
            "      \"ENN\": \"fxgjx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"经典型霍奇金淋巴瘤\",\n" +
            "      \"ENN\": \"jdxhqjlbl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脊髓型颈椎病\",\n" +
            "      \"ENN\": \"jsxjzb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺出血肾炎综合征\",\n" +
            "      \"ENN\": \"fcxsyzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"乙状结肠息肉\",\n" +
            "      \"ENN\": \"yzjcxr\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"晶状体不全脱位\",\n" +
            "      \"ENN\": \"jztbqtw\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺非结核分枝杆菌病\",\n" +
            "      \"ENN\": \"ffjhfzgjb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"产褥期尿潴留\",\n" +
            "      \"ENN\": \"crqnsl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"甲状腺炎\",\n" +
            "      \"ENN\": \"jzxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性前间壁高侧壁心肌梗死\",\n" +
            "      \"ENN\": \"cjxqjbgcbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性广泛前壁高侧壁心肌梗死\",\n" +
            "      \"ENN\": \"jxgfqbgcbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"右心感染性心内膜炎\",\n" +
            "      \"ENN\": \"yxgrxxnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性心肌缺血\",\n" +
            "      \"ENN\": \"jxxjqx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"输卵管壶腹部妊娠\",\n" +
            "      \"ENN\": \"slghfbrs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"低血压\",\n" +
            "      \"ENN\": \"dxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"单形性室性心动过速\",\n" +
            "      \"ENN\": \"dxxsxxdgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"卵巢子宫内膜异位囊肿\",\n" +
            "      \"ENN\": \"lczgnmywnz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胃淋巴瘤\",\n" +
            "      \"ENN\": \"wlbl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"腹股沟斜疝合并直疝\",\n" +
            "      \"ENN\": \"fggxyhbzi\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"二尖瓣关闭不全\",\n" +
            "      \"ENN\": \"ejbgbbq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"消化性溃疡\",\n" +
            "      \"ENN\": \"xhxky\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"包裹性心包积液\",\n" +
            "      \"ENN\": \"bgxxbjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性下后壁心肌梗死\",\n" +
            "      \"ENN\": \"jxxhbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性肺血栓栓塞症\",\n" +
            "      \"ENN\": \"jxfxsssz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"右肺上叶恶性肿瘤\",\n" +
            "      \"ENN\": \"yfsyexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"包涵体肌炎\",\n" +
            "      \"ENN\": \"bhtjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"阵发性室性期前收缩\",\n" +
            "      \"ENN\": \"zfxsxqqss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"共同性外斜视\",\n" +
            "      \"ENN\": \"gtxwxs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心脏手术后心力衰竭\",\n" +
            "      \"ENN\": \"xzsshxlsj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"疾病四\",\n" +
            "      \"ENN\": \"jbs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"放射性心包炎\",\n" +
            "      \"ENN\": \"fsxxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性下壁心肌梗死\",\n" +
            "      \"ENN\": \"cjxxbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性下后壁再发心肌梗死\",\n" +
            "      \"ENN\": \"jxxhbzfxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"细支气管炎\",\n" +
            "      \"ENN\": \"xzqgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脊髓栓系综合征\",\n" +
            "      \"ENN\": \"jssxzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"皮层下动脉硬化性脑病\",\n" +
            "      \"ENN\": \"pcxdmyhxnb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺原位癌\",\n" +
            "      \"ENN\": \"fywa\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"甲状腺肿\",\n" +
            "      \"ENN\": \"jzxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性前间壁下壁心肌梗死\",\n" +
            "      \"ENN\": \"jxqjbxbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"低钠血症\",\n" +
            "      \"ENN\": \"dnxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"颈内动脉闭塞\",\n" +
            "      \"ENN\": \"jndmbs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"伤寒并发中毒性心肌炎\",\n" +
            "      \"ENN\": \"shbfzdxxjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胃体恶性肿瘤\",\n" +
            "      \"ENN\": \"wtexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性心内膜下心肌梗死\",\n" +
            "      \"ENN\": \"jxxnmxxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"十二指肠球部溃疡伴出血\",\n" +
            "      \"ENN\": \"sezcqbkybcx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"鼾症\",\n" +
            "      \"ENN\": \"dz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性化脓性阑尾炎伴腹膜炎\",\n" +
            "      \"ENN\": \"jxhnxlwybfmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性右心室心肌梗死\",\n" +
            "      \"ENN\": \"jxyxsxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"痛风\",\n" +
            "      \"ENN\": \"tf\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"原发性开角型青光眼\",\n" +
            "      \"ENN\": \"yfxkjxqgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"糖尿病足\",\n" +
            "      \"ENN\": \"tnbz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肿瘤性心包炎\",\n" +
            "      \"ENN\": \"zlxxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"玻璃体黄斑牵拉综合征\",\n" +
            "      \"ENN\": \"blthbqlzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"膀胱结石\",\n" +
            "      \"ENN\": \"bujs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"腹膜子宫内膜异位症\",\n" +
            "      \"ENN\": \"fmzgnmywz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"室性心动过速\",\n" +
            "      \"ENN\": \"sxxdgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性心房颤动\",\n" +
            "      \"ENN\": \"jxxfcd\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肾良性肿瘤\",\n" +
            "      \"ENN\": \"slxzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性前壁高侧壁心肌梗死\",\n" +
            "      \"ENN\": \"jxqbgcbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"毒性结节性甲状腺肿\",\n" +
            "      \"ENN\": \"dxjjxjzxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"疾病五\",\n" +
            "      \"ENN\": \"jbw\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"巨细胞动脉炎\",\n" +
            "      \"ENN\": \"jxbdmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"尿道结石\",\n" +
            "      \"ENN\": \"ndjs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"乳腺内上象限恶性肿瘤\",\n" +
            "      \"ENN\": \"rxnsxxexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性高血压并发子痫前期\",\n" +
            "      \"ENN\": \"mxgxybfzkqq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性前壁下壁心肌梗死\",\n" +
            "      \"ENN\": \"jxqbxbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"椎间盘突出\",\n" +
            "      \"ENN\": \"zjptc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"梅毒性心包炎\",\n" +
            "      \"ENN\": \"mdxxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"疾病二\",\n" +
            "      \"ENN\": \"jbe\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"十二指肠溃疡伴出血\",\n" +
            "      \"ENN\": \"sezckybcx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"幽门管溃疡\",\n" +
            "      \"ENN\": \"ymgky\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"左束支半传导阻滞\",\n" +
            "      \"ENN\": \"zszbcdzz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"乳腺增生\",\n" +
            "      \"ENN\": \"rxzs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"左室室性心动过速\",\n" +
            "      \"ENN\": \"zssxxdgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新型隐球菌肺炎\",\n" +
            "      \"ENN\": \"xxyqjfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性下壁后壁右心室心肌梗塞\",\n" +
            "      \"ENN\": \"cjxxbhbyxsxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"加利福尼亚脑炎\",\n" +
            "      \"ENN\": \"jlfnyny\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"膝半月板撕裂\",\n" +
            "      \"ENN\": \"xbybsl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性前壁高侧壁下壁心肌梗死\",\n" +
            "      \"ENN\": \"jxqbgcbxbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"上消化道出血\",\n" +
            "      \"ENN\": \"sxhdcx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"视神经脊髓炎\",\n" +
            "      \"ENN\": \"ssjjsy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性呼吸窘迫综合征\",\n" +
            "      \"ENN\": \"jxhxjpzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"感染性贫血\",\n" +
            "      \"ENN\": \"grxpx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"左心衰竭合并急性肺水肿\",\n" +
            "      \"ENN\": \"zxsjhbjxfsz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肩袖肌腱损伤\",\n" +
            "      \"ENN\": \"jxjbss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"弥漫性轴索损伤\",\n" +
            "      \"ENN\": \"mmxzsss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肘骨关节病\",\n" +
            "      \"ENN\": \"zggjb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性下壁右心室心肌梗死\",\n" +
            "      \"ENN\": \"jxxbyxsxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心脏传导系统退行性变\",\n" +
            "      \"ENN\": \"xzcdxttxxb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性侧壁正后壁心肌梗死\",\n" +
            "      \"ENN\": \"jxcbzhbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"输卵管妊娠流产\",\n" +
            "      \"ENN\": \"slgrslc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肩撞击综合征\",\n" +
            "      \"ENN\": \"jzjzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心室扑动\",\n" +
            "      \"ENN\": \"xspd\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"膀胱后壁恶性肿瘤\",\n" +
            "      \"ENN\": \"bfhbexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"前列腺增生\",\n" +
            "      \"ENN\": \"qlxzs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胆总管恶性肿瘤\",\n" +
            "      \"ENN\": \"dzgexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性出血坏死性胰腺炎\",\n" +
            "      \"ENN\": \"jxcxhsxyxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"免疫性血小板减少\",\n" +
            "      \"ENN\": \"myxxxbjs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肾积水伴肾和输尿管结石梗阻\",\n" +
            "      \"ENN\": \"sjsbshsngjsgz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胆源性胰腺炎\",\n" +
            "      \"ENN\": \"dyxyxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"主动脉夹层A2S型\",\n" +
            "      \"ENN\": \"zdmjcA2Sx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肝脓肿\",\n" +
            "      \"ENN\": \"gnz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脐疝\",\n" +
            "      \"ENN\": \"qa\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"下肢静脉血栓形成\",\n" +
            "      \"ENN\": \"xzjmxsxc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"病毒性胃肠炎\",\n" +
            "      \"ENN\": \"bdxwcy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性下壁右心室再发心肌梗死\",\n" +
            "      \"ENN\": \"jxxbyxszfxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"混合痔\",\n" +
            "      \"ENN\": \"hhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"角膜白斑\",\n" +
            "      \"ENN\": \"jmbb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"柯萨奇病毒性心包炎\",\n" +
            "      \"ENN\": \"ksqbdxxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"持续性心房颤动\",\n" +
            "      \"ENN\": \"cxxxfcd\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"妊娠合并左束支传导阻滞\",\n" +
            "      \"ENN\": \"rshbzszcdzz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"分娩期麻醉引起的心脏停搏\",\n" +
            "      \"ENN\": \"fmqmzyqdxztb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"一氧化碳中毒\",\n" +
            "      \"ENN\": \"yyhtzd\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"乳腺导管原位癌\",\n" +
            "      \"ENN\": \"rxdgywa\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性化脓性中耳炎\",\n" +
            "      \"ENN\": \"mxhnxzey\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"左后分支传导阻滞\",\n" +
            "      \"ENN\": \"zhfzcdzz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"升结肠恶性肿瘤\",\n" +
            "      \"ENN\": \"sjcexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"颈椎病\",\n" +
            "      \"ENN\": \"jzb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心肌梗死后心绞痛\",\n" +
            "      \"ENN\": \"xjgshxjt\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脑疝\",\n" +
            "      \"ENN\": \"nb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胰腺恶性肿瘤\",\n" +
            "      \"ENN\": \"yxexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肾恶性肿瘤\",\n" +
            "      \"ENN\": \"sexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肾性高血压伴高血压性心脏病\",\n" +
            "      \"ENN\": \"sxgxybgxyxxzb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"子宫肌腺症\",\n" +
            "      \"ENN\": \"zgjxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"间质性肺病\",\n" +
            "      \"ENN\": \"jzxfb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脑膜炎球菌性心内膜炎\",\n" +
            "      \"ENN\": \"nmyqjxxnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"中隔束支传导阻滞\",\n" +
            "      \"ENN\": \"zgszcdzz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"舒张性心力衰竭\",\n" +
            "      \"ENN\": \"szxxlsj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胸椎管狭窄\",\n" +
            "      \"ENN\": \"xzgxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"混合性睡眠呼吸暂停低通气综合征\",\n" +
            "      \"ENN\": \"hhxsmhxztdtqzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性呼吸衰竭\",\n" +
            "      \"ENN\": \"mxhxsj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"鼻窦瘘\",\n" +
            "      \"ENN\": \"bii\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胆总管结石\",\n" +
            "      \"ENN\": \"dzgjs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"原发性闭角型青光眼\",\n" +
            "      \"ENN\": \"yfxbjxqgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"血红蛋白病\",\n" +
            "      \"ENN\": \"xhdbb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"膀胱炎\",\n" +
            "      \"ENN\": \"byy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脾功能亢进\",\n" +
            "      \"ENN\": \"pgnkj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性支气管炎\",\n" +
            "      \"ENN\": \"jxzqgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"康恩综合征\",\n" +
            "      \"ENN\": \"kezhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"继发性青光眼\",\n" +
            "      \"ENN\": \"jfxqgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"先天性耳前瘘\",\n" +
            "      \"ENN\": \"xtxeqc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"左心人工瓣膜性心内膜炎\",\n" +
            "      \"ENN\": \"zxrgbmxxnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"老年钙化性二尖瓣狭窄\",\n" +
            "      \"ENN\": \"lnghxejbxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"疾病一\",\n" +
            "      \"ENN\": \"jby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"主动脉瓣关闭不全\",\n" +
            "      \"ENN\": \"zdmbgbbq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"疾病七\",\n" +
            "      \"ENN\": \"jbq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"喉恶性肿瘤\",\n" +
            "      \"ENN\": \"hexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肢体闭塞性动脉硬化\",\n" +
            "      \"ENN\": \"ztbsxdmyh\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新生儿室性心动过速\",\n" +
            "      \"ENN\": \"xsesxxdgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"血小板减少\",\n" +
            "      \"ENN\": \"xxbjs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"传染性单核细胞增多症\",\n" +
            "      \"ENN\": \"crxdhxbzdz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"变应性血管炎\",\n" +
            "      \"ENN\": \"byxxgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性附睾炎\",\n" +
            "      \"ENN\": \"jxfgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肝硬化失代偿期\",\n" +
            "      \"ENN\": \"gyhsdcq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"冈上肌肌腱损伤\",\n" +
            "      \"ENN\": \"gsjjxss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"系统性硬化症性肺间质纤维化\",\n" +
            "      \"ENN\": \"xtxyhzxfjzxwh\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺结核(细菌学和组织学检查为阴性)\",\n" +
            "      \"ENN\": \"fjhAxjxhzzxjcwyxA\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性尖-侧壁心肌梗死\",\n" +
            "      \"ENN\": \"jxjAcbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"结肠梗阻\",\n" +
            "      \"ENN\": \"jcgz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新生儿呼吸窘迫综合征\",\n" +
            "      \"ENN\": \"xsehxjpzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"疾病三\",\n" +
            "      \"ENN\": \"jbs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"食道血管瘤\",\n" +
            "      \"ENN\": \"sdxgl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"水痘并发心肌炎\",\n" +
            "      \"ENN\": \"sdbfxjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺淋巴瘤\",\n" +
            "      \"ENN\": \"flbl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性肾功能不全\",\n" +
            "      \"ENN\": \"jxsgnbq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肾梗死\",\n" +
            "      \"ENN\": \"sgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"法特壶腹恶性肿瘤\",\n" +
            "      \"ENN\": \"fthfexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"妊娠合并慢性高血压\",\n" +
            "      \"ENN\": \"rshbmxgxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"大泡性角膜病\",\n" +
            "      \"ENN\": \"dpxjmb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"低钾血症\",\n" +
            "      \"ENN\": \"djxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"男性不育症\",\n" +
            "      \"ENN\": \"nxbyz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性阑尾炎\",\n" +
            "      \"ENN\": \"mxlwy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"眼球震颤\",\n" +
            "      \"ENN\": \"yqzc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性支气管炎\",\n" +
            "      \"ENN\": \"mxzqgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"癫痫持续状态\",\n" +
            "      \"ENN\": \"xpcxzt\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"汉坦病毒心肺综合征\",\n" +
            "      \"ENN\": \"htbdxfzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"骨关节病\",\n" +
            "      \"ENN\": \"ggjb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺炎\",\n" +
            "      \"ENN\": \"fy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性高侧壁正后壁心肌梗死\",\n" +
            "      \"ENN\": \"cjxgcbzhbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性心内膜下心肌梗死\",\n" +
            "      \"ENN\": \"cjxxnmxxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性下侧壁心肌梗死\",\n" +
            "      \"ENN\": \"jxxcbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"扁桃体炎\",\n" +
            "      \"ENN\": \"btty\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"右肺中上叶恶性肿瘤\",\n" +
            "      \"ENN\": \"yfzsyexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"频发性室性期前收缩\",\n" +
            "      \"ENN\": \"pfxsxqqss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"特发性低血压\",\n" +
            "      \"ENN\": \"tfxdxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"束支传导阻滞\",\n" +
            "      \"ENN\": \"szcdzz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"炎症后肺间质纤维化\",\n" +
            "      \"ENN\": \"yzhfjzxwh\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性支气管炎急性加重期\",\n" +
            "      \"ENN\": \"mxzqgyjxjzq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"输卵管系膜囊肿\",\n" +
            "      \"ENN\": \"slgxmnz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"呼吸衰竭\",\n" +
            "      \"ENN\": \"hxsj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"巨细胞病毒感染\",\n" +
            "      \"ENN\": \"jxbbdgr\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"子宫多发性平滑肌瘤\",\n" +
            "      \"ENN\": \"zgdfxphjl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脑膜炎球菌性心肌炎\",\n" +
            "      \"ENN\": \"nmyqjxxjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胆道感染\",\n" +
            "      \"ENN\": \"ddgr\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"X综合征\",\n" +
            "      \"ENN\": \"Xzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性粟粒性肺结核\",\n" +
            "      \"ENN\": \"jxslxfjh\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"支气管淋巴结结核\",\n" +
            "      \"ENN\": \"zqglbjjh\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新生儿咽下综合征\",\n" +
            "      \"ENN\": \"xseyxzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"单纯疱疹病毒性脑炎\",\n" +
            "      \"ENN\": \"dcbzbdxny\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"支气管哮喘(急性发作期)\",\n" +
            "      \"ENN\": \"zqgxcAjxfzqA\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新生儿短暂性心肌缺血\",\n" +
            "      \"ENN\": \"xsedzxxjqx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"湿疹\",\n" +
            "      \"ENN\": \"sz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺脓肿伴肺炎\",\n" +
            "      \"ENN\": \"fnzbfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"葡萄球菌性心内膜炎\",\n" +
            "      \"ENN\": \"ptqjxxnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"完全性左束支传导阻滞\",\n" +
            "      \"ENN\": \"wqxzszcdzz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"主动脉瓣狭窄\",\n" +
            "      \"ENN\": \"zdmbxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性冠脉综合征\",\n" +
            "      \"ENN\": \"jxgmzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肥厚性主动脉瓣下狭窄\",\n" +
            "      \"ENN\": \"fhxzdmbxxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"支气管哮喘急性发作(重度)\",\n" +
            "      \"ENN\": \"zqgxcjxfzAzdA\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"溃疡性结肠炎\",\n" +
            "      \"ENN\": \"kyxjcy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"颈动脉硬化\",\n" +
            "      \"ENN\": \"jdmyh\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"自发型心绞痛\",\n" +
            "      \"ENN\": \"zfxxjt\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"阵发性房性心动过速\",\n" +
            "      \"ENN\": \"zfxfxxdgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"骨髓增生异常综合征\",\n" +
            "      \"ENN\": \"gszsyczhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"右肺恶性肿瘤\",\n" +
            "      \"ENN\": \"yfexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"妊娠合并预激综合征\",\n" +
            "      \"ENN\": \"rshbyjzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"输尿管恶性肿瘤\",\n" +
            "      \"ENN\": \"sngexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性冠状动脉供血不足\",\n" +
            "      \"ENN\": \"mxgzdmgxbz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"额叶癫痫\",\n" +
            "      \"ENN\": \"eyru\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"支气管炎\",\n" +
            "      \"ENN\": \"zqgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"风湿性二尖瓣狭窄\",\n" +
            "      \"ENN\": \"fsxejbxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"房性心动过速\",\n" +
            "      \"ENN\": \"fxxdgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"后尿道损伤\",\n" +
            "      \"ENN\": \"hndss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"间歇性外斜视\",\n" +
            "      \"ENN\": \"jxxwxs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新生儿感染\",\n" +
            "      \"ENN\": \"xsegr\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"自身免疫性肝炎\",\n" +
            "      \"ENN\": \"zsmyxgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心内膜炎伴主动脉瓣脱垂\",\n" +
            "      \"ENN\": \"xnmybzdmbtc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"妊娠合并贫血\",\n" +
            "      \"ENN\": \"rshbpx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"子宫颈上皮瘤样病变\",\n" +
            "      \"ENN\": \"zgjsplybb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"细菌性心内膜炎\",\n" +
            "      \"ENN\": \"xjxxnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"输卵管间质部妊娠\",\n" +
            "      \"ENN\": \"slgjzbrs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"良性阵发性位置性眩晕\",\n" +
            "      \"ENN\": \"lxzfxwzxxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"高血压危象\",\n" +
            "      \"ENN\": \"gxywx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性高侧壁正后壁心肌梗死\",\n" +
            "      \"ENN\": \"jxgcbzhbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"鲍曼不动杆菌性肺炎\",\n" +
            "      \"ENN\": \"bmbdgjxfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"大隐静脉曲张\",\n" +
            "      \"ENN\": \"dyjmqz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"双侧膝关节骨性关节病\",\n" +
            "      \"ENN\": \"scxgjgxgjb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"败血症\",\n" +
            "      \"ENN\": \"bxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"女性压力性尿失禁\",\n" +
            "      \"ENN\": \"nxylxnsj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性呼吸衰竭\",\n" +
            "      \"ENN\": \"jxhxsj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"食管裂孔疝\",\n" +
            "      \"ENN\": \"sglkj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性前间壁心肌梗死\",\n" +
            "      \"ENN\": \"jxqjbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺结核(组织学证实)\",\n" +
            "      \"ENN\": \"fjhAzzxzsA\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"枕大神经痛\",\n" +
            "      \"ENN\": \"zdsjt\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"小脑幕裂孔疝\",\n" +
            "      \"ENN\": \"xnmlkr\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"全心衰竭\",\n" +
            "      \"ENN\": \"qxsj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"革兰阳性杆菌性心内膜炎\",\n" +
            "      \"ENN\": \"glyxgjxxnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"主支气管恶性肿瘤\",\n" +
            "      \"ENN\": \"zzqgexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"核性老年性白内障\",\n" +
            "      \"ENN\": \"hxlnxbnz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"产褥期甲状腺炎\",\n" +
            "      \"ENN\": \"crqjzxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脑梗死\",\n" +
            "      \"ENN\": \"ngs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"类风湿性心包炎\",\n" +
            "      \"ENN\": \"lfsxxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"颞叶性癫痫\",\n" +
            "      \"ENN\": \"iyxbn\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"气胸\",\n" +
            "      \"ENN\": \"qx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新生儿高胆红素血症\",\n" +
            "      \"ENN\": \"xsegdhsxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心房颤动\",\n" +
            "      \"ENN\": \"xfcd\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新生儿房室传导阻滞\",\n" +
            "      \"ENN\": \"xsefscdzz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脑外伤恢复期\",\n" +
            "      \"ENN\": \"nwshfq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"类风湿性膝关节炎\",\n" +
            "      \"ENN\": \"lfsxxgjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"风湿性主动脉瓣狭窄\",\n" +
            "      \"ENN\": \"fsxzdmbxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"药物性皮炎\",\n" +
            "      \"ENN\": \"ywxpy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性上呼吸道感染\",\n" +
            "      \"ENN\": \"jxshxdgr\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性荨麻疹\",\n" +
            "      \"ENN\": \"jxdmz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"非霍奇金淋巴瘤\",\n" +
            "      \"ENN\": \"fhqjlbl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"左肺恶性肿瘤\",\n" +
            "      \"ENN\": \"zfexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"继发性腹膜炎\",\n" +
            "      \"ENN\": \"jfxfmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"药物性心肌炎\",\n" +
            "      \"ENN\": \"ywxxjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"主动脉夹层B3S型\",\n" +
            "      \"ENN\": \"zdmjcB3Sx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性侧壁心肌梗死\",\n" +
            "      \"ENN\": \"cjxcbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺血管瘤\",\n" +
            "      \"ENN\": \"fxgl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"正常钾型周期性瘫痪\",\n" +
            "      \"ENN\": \"zcjxzqxth\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"基底节脑梗死\",\n" +
            "      \"ENN\": \"jdjngs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"过敏性紫癜(混合型)\",\n" +
            "      \"ENN\": \"gmxzaAhhxA\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"颈胸间盘突出\",\n" +
            "      \"ENN\": \"jxjptc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"单侧腹股沟斜疝\",\n" +
            "      \"ENN\": \"dcfggxn\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"腹壁子宫内膜异位\",\n" +
            "      \"ENN\": \"fbzgnmyw\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"升结肠良性肿瘤\",\n" +
            "      \"ENN\": \"sjclxzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性肾功能不全\",\n" +
            "      \"ENN\": \"mxsgnbq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"劳力初发型心绞痛\",\n" +
            "      \"ENN\": \"llcfxxjt\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性细菌性前列腺炎\",\n" +
            "      \"ENN\": \"jxxjxqlxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"风湿性心肌炎\",\n" +
            "      \"ENN\": \"fsxxjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"妊娠合并右束支传导阻滞\",\n" +
            "      \"ENN\": \"rshbyszcdzz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"腹部良性肿瘤\",\n" +
            "      \"ENN\": \"fblxzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新生儿心律失常\",\n" +
            "      \"ENN\": \"xsexlsc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"房性期前收缩\",\n" +
            "      \"ENN\": \"fxqqss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"输卵管峡部妊娠破裂\",\n" +
            "      \"ENN\": \"slgxbrspl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"子宫不全纵隔\",\n" +
            "      \"ENN\": \"zgbqzg\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"结核性肺炎\",\n" +
            "      \"ENN\": \"jhxfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"左肺上下叶恶性肿瘤\",\n" +
            "      \"ENN\": \"zfsxyexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"非特异性间质性肺炎\",\n" +
            "      \"ENN\": \"ftyxjzxfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"神经根型颈椎病\",\n" +
            "      \"ENN\": \"sjgxjzb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"卡氏肺孢子菌肺炎\",\n" +
            "      \"ENN\": \"ksfnzjfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"纵隔肿物\",\n" +
            "      \"ENN\": \"zgzw\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"房室传导阻滞\",\n" +
            "      \"ENN\": \"fscdzz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"特发性心房颤动\",\n" +
            "      \"ENN\": \"tfxxfcd\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性前壁再发心肌梗死\",\n" +
            "      \"ENN\": \"jxqbzfxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性心肌梗死后室间隔穿孔\",\n" +
            "      \"ENN\": \"jxxjgshsjgck\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"下肢静脉曲张\",\n" +
            "      \"ENN\": \"xzjmqz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"大面积脑梗死\",\n" +
            "      \"ENN\": \"dmjngs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"椎管内囊肿\",\n" +
            "      \"ENN\": \"zgnnz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"淋球菌性心包炎\",\n" +
            "      \"ENN\": \"lqjxxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"单纯性肥胖\",\n" +
            "      \"ENN\": \"dcxfp\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"斜视\",\n" +
            "      \"ENN\": \"xs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性心内膜炎\",\n" +
            "      \"ENN\": \"jxxnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"缩窄性心包炎\",\n" +
            "      \"ENN\": \"szxxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性浅表性胃炎\",\n" +
            "      \"ENN\": \"mxqbxwy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"二度房室传导阻滞\",\n" +
            "      \"ENN\": \"edfscdzz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"非毒性单结节性甲状腺肿\",\n" +
            "      \"ENN\": \"fdxdjjxjzxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胆管结石伴胆管炎\",\n" +
            "      \"ENN\": \"dgjsbdgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性心房颤动\",\n" +
            "      \"ENN\": \"mxxfcd\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性心肌梗死后心室附壁血栓形成\",\n" +
            "      \"ENN\": \"jxxjgshxsfbxsxc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"ANCA相关性小血管炎\",\n" +
            "      \"ENN\": \"ANCAxgxxxgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脑出血\",\n" +
            "      \"ENN\": \"ncx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"主动脉夹层动脉瘤破裂\",\n" +
            "      \"ENN\": \"zdmjcdmlpl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"关节炎\",\n" +
            "      \"ENN\": \"gjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"再生障碍性贫血\",\n" +
            "      \"ENN\": \"zszaxpx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心肌梗死后综合征\",\n" +
            "      \"ENN\": \"xjgshzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性粒细胞性白血病部分分化型伴缓解(M2型)\",\n" +
            "      \"ENN\": \"jxlxbxbxbbffhxbhjAM2xA\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"异位妊娠\",\n" +
            "      \"ENN\": \"ywrs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"糖尿病性周围神经病\",\n" +
            "      \"ENN\": \"tnbxzwsjb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性前间壁高侧壁心肌梗死\",\n" +
            "      \"ENN\": \"jxqjbgcbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"二尖瓣脱垂\",\n" +
            "      \"ENN\": \"ejbtc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心内膜炎伴主动脉瓣关闭不全\",\n" +
            "      \"ENN\": \"xnmybzdmbgbbq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"巨球蛋白血症\",\n" +
            "      \"ENN\": \"jqdbxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"继发性肺动脉高压\",\n" +
            "      \"ENN\": \"jfxfdmgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肌萎缩侧索硬化症\",\n" +
            "      \"ENN\": \"jwscsyhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"颅内损伤\",\n" +
            "      \"ENN\": \"lnss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性肺源性心脏病\",\n" +
            "      \"ENN\": \"jxfyxxzb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性肾小球肾炎\",\n" +
            "      \"ENN\": \"jxsxqsy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"东部马脑炎\",\n" +
            "      \"ENN\": \"dbmny\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"原发性肺结核\",\n" +
            "      \"ENN\": \"yfxfjh\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"十二指肠恶性肿瘤\",\n" +
            "      \"ENN\": \"sezcexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性膀胱炎\",\n" +
            "      \"ENN\": \"jxbuy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"暴发性心肌炎\",\n" +
            "      \"ENN\": \"bfxxjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"边缘性前置胎盘\",\n" +
            "      \"ENN\": \"byxqztp\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"感音神经性聋\",\n" +
            "      \"ENN\": \"gysjxl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"支气管错构瘤\",\n" +
            "      \"ENN\": \"zqgcgl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性粒细胞性白血病\",\n" +
            "      \"ENN\": \"jxlxbxbxb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肠套叠\",\n" +
            "      \"ENN\": \"ctd\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"阑尾周围脓肿\",\n" +
            "      \"ENN\": \"lwzwnz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"类风湿性心内膜炎\",\n" +
            "      \"ENN\": \"lfsxxnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"二尖瓣腱索断裂\",\n" +
            "      \"ENN\": \"ejbssdl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"假单胞菌性心内膜炎\",\n" +
            "      \"ENN\": \"jdbjxxnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性髓系白血病\",\n" +
            "      \"ENN\": \"mxsxbxb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性胰腺炎\",\n" +
            "      \"ENN\": \"mxyxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"病毒性心肌炎后遗症\",\n" +
            "      \"ENN\": \"bdxxjyhyz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"快慢综合征\",\n" +
            "      \"ENN\": \"kmzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"支气管哮喘(未控制)\",\n" +
            "      \"ENN\": \"zqgxcAwkzA\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"小脑梗死\",\n" +
            "      \"ENN\": \"xngs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肾小球肾炎\",\n" +
            "      \"ENN\": \"sxqsy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新生儿高血压\",\n" +
            "      \"ENN\": \"xsegxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"短暂性脑缺血发作\",\n" +
            "      \"ENN\": \"dzxnqxfz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"低血糖症\",\n" +
            "      \"ENN\": \"dxtz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"枕神经痛\",\n" +
            "      \"ENN\": \"zsjt\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性坏疽性阑尾炎\",\n" +
            "      \"ENN\": \"jxhjxlwy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"主动脉夹层B1S型\",\n" +
            "      \"ENN\": \"zdmjcB1Sx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"2型糖尿病伴血糖控制不佳\",\n" +
            "      \"ENN\": \"2xtnbbxtkzbj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脑震荡\",\n" +
            "      \"ENN\": \"nzd\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性粘连性渗出性心包炎\",\n" +
            "      \"ENN\": \"mxzlxscxxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"左心衰竭\",\n" +
            "      \"ENN\": \"zxsj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"冠状动脉狭窄\",\n" +
            "      \"ENN\": \"gzdmxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新生儿病理性黄疸\",\n" +
            "      \"ENN\": \"xseblxhu\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";
    String s = "{\n" +
            "  \"code\": 200,\n" +
            "  \"message\": \"success\",\n" +
            "  \"result\": [\n" +
            "    {\n" +
            "      \"CNN\": \"急性左心衰竭\",\n" +
            "      \"ENN\": \"jxzxsj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"纵隔炎\",\n" +
            "      \"ENN\": \"zgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"妊娠合并左心衰竭\",\n" +
            "      \"ENN\": \"rshbzxsj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"体位性低血压\",\n" +
            "      \"ENN\": \"twxdxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肾盂肾炎\",\n" +
            "      \"ENN\": \"sysy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"过敏性心肌炎\",\n" +
            "      \"ENN\": \"gmxxjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"局限性硬皮病\",\n" +
            "      \"ENN\": \"jxxypb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胆道恶性肿瘤\",\n" +
            "      \"ENN\": \"ddexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"支原体性肺炎\",\n" +
            "      \"ENN\": \"zytxfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"小肠良性肿瘤\",\n" +
            "      \"ENN\": \"xclxzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"子宫颈上皮内瘤变III级\",\n" +
            "      \"ENN\": \"zgjspnlbIIIj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性气管炎\",\n" +
            "      \"ENN\": \"jxqgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心包积液\",\n" +
            "      \"ENN\": \"xbjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"过敏性紫癜\",\n" +
            "      \"ENN\": \"gmxzv\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性后壁心肌梗死\",\n" +
            "      \"ENN\": \"jxhbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺结核(痰涂片及培养均证实)\",\n" +
            "      \"ENN\": \"fjhAttpjpyjzsA\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"腺垂体功能减退症\",\n" +
            "      \"ENN\": \"xctgnjtz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"卵巢囊肿蒂扭转\",\n" +
            "      \"ENN\": \"lcnzdnz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"子宫颈恶性肿瘤\",\n" +
            "      \"ENN\": \"zgjexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性非Q波型心肌梗死\",\n" +
            "      \"ENN\": \"jxfQbxxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胃息肉\",\n" +
            "      \"ENN\": \"wxr\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"吸毒性心内膜炎\",\n" +
            "      \"ENN\": \"xdxxnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"过敏性紫癜(皮肤型)\",\n" +
            "      \"ENN\": \"gmxzdApfxA\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"子宫内膜异位\",\n" +
            "      \"ENN\": \"zgnmyw\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性心房心肌梗死\",\n" +
            "      \"ENN\": \"jxxfxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"室性心律失常\",\n" +
            "      \"ENN\": \"sxxlsc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肾性高血压\",\n" +
            "      \"ENN\": \"sxgxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"反流性食管炎\",\n" +
            "      \"ENN\": \"flxsgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"劳力恶化型心绞痛\",\n" +
            "      \"ENN\": \"llehxxjt\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"多发性大脑挫裂伤\",\n" +
            "      \"ENN\": \"dfxdncls\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"2型糖尿病性酮症酸中毒\",\n" +
            "      \"ENN\": \"2xtnbxtzszd\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"踝距腓前韧带断裂\",\n" +
            "      \"ENN\": \"ejyqrddl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"药物性低血压\",\n" +
            "      \"ENN\": \"ywxdxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性膝外侧半月板损伤\",\n" +
            "      \"ENN\": \"cjxxwcbybss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"高甘油三酯血症\",\n" +
            "      \"ENN\": \"ggysoxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"喘息性支气管肺炎\",\n" +
            "      \"ENN\": \"cxxzqgfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"医源性高血压\",\n" +
            "      \"ENN\": \"yyxgxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"非炎性心包积液\",\n" +
            "      \"ENN\": \"fyxxbjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"窄QRS心动过速\",\n" +
            "      \"ENN\": \"zQRSxdgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"右肺中叶恶性肿瘤\",\n" +
            "      \"ENN\": \"yfzyexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"膀胱颈梗阻\",\n" +
            "      \"ENN\": \"bgjgz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"结性期前收缩\",\n" +
            "      \"ENN\": \"jxqqss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胆管结石\",\n" +
            "      \"ENN\": \"dgjs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"小肠恶性肿瘤\",\n" +
            "      \"ENN\": \"xcexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"嗜铬细胞瘤性高血压\",\n" +
            "      \"ENN\": \"sgxblxgxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"房室折返性心动过速\",\n" +
            "      \"ENN\": \"fszfxxdgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新生儿房性期前收缩\",\n" +
            "      \"ENN\": \"xsefxqqss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"皮质醇增多症\",\n" +
            "      \"ENN\": \"pzczdz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"室性期前收缩\",\n" +
            "      \"ENN\": \"sxqqss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性风湿性心包炎\",\n" +
            "      \"ENN\": \"mxfsxxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胃炎\",\n" +
            "      \"ENN\": \"wy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性心尖部心肌梗死\",\n" +
            "      \"ENN\": \"jxxjbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"冠状动脉介入术相关性心肌梗死\",\n" +
            "      \"ENN\": \"gzdmjrsxgxxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性肾盂肾炎\",\n" +
            "      \"ENN\": \"mxsysy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脑动静脉畸形\",\n" +
            "      \"ENN\": \"ndjmjx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性踝外侧副韧带断裂\",\n" +
            "      \"ENN\": \"cjxtwcfrddl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"卵巢黄体囊肿破裂\",\n" +
            "      \"ENN\": \"lchtnzpl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性心包积液\",\n" +
            "      \"ENN\": \"jxxbjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性淋巴细胞白血病\",\n" +
            "      \"ENN\": \"mxlbxbbxb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胆总管结石伴急性化脓性胆管炎\",\n" +
            "      \"ENN\": \"dzgjsbjxhnxdgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"支气管良性肿瘤\",\n" +
            "      \"ENN\": \"zqglxzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性胃炎\",\n" +
            "      \"ENN\": \"mxwy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"频发性房性期前收缩\",\n" +
            "      \"ENN\": \"pfxfxqqss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肾衰竭\",\n" +
            "      \"ENN\": \"ssj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"高钾血症\",\n" +
            "      \"ENN\": \"gjxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"支气管恶性肿瘤\",\n" +
            "      \"ENN\": \"zqgexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"风湿性粘连性心包炎\",\n" +
            "      \"ENN\": \"fsxzlxxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"阵发性房室折返性心动过速\",\n" +
            "      \"ENN\": \"zfxfszfxxdgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"恶性心内膜炎\",\n" +
            "      \"ENN\": \"exxnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"闭合性气胸\",\n" +
            "      \"ENN\": \"bhxqx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"弥漫性肺泡出血综合征\",\n" +
            "      \"ENN\": \"mmxfpcxzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胆囊结石伴慢性胆囊炎\",\n" +
            "      \"ENN\": \"dnjsbmxdny\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"子宫内膜增生\",\n" +
            "      \"ENN\": \"zgnmzs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心包炎\",\n" +
            "      \"ENN\": \"xby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性肾盂肾炎\",\n" +
            "      \"ENN\": \"jxsysy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"右肺中下叶恶性肿瘤\",\n" +
            "      \"ENN\": \"yfzxyexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"贫血\",\n" +
            "      \"ENN\": \"px\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胆囊息肉\",\n" +
            "      \"ENN\": \"dnxr\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"窦房折返性心动过速\",\n" +
            "      \"ENN\": \"bfzfxxdgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"病毒性心包炎\",\n" +
            "      \"ENN\": \"bdxxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肾盂恶性肿瘤\",\n" +
            "      \"ENN\": \"syexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"宽QRS心动过速\",\n" +
            "      \"ENN\": \"kQRSxdgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性间质性膀胱炎\",\n" +
            "      \"ENN\": \"mxjzxbcy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肾积水伴输尿管狭窄\",\n" +
            "      \"ENN\": \"sjsbsngxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"食管良性肿瘤\",\n" +
            "      \"ENN\": \"sglxzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"阻塞性睡眠呼吸暂停低通气综合征\",\n" +
            "      \"ENN\": \"zsxsmhxztdtqzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"膝关节前十字韧带损伤\",\n" +
            "      \"ENN\": \"xgjqszrdss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"2型糖尿病性周围神经病\",\n" +
            "      \"ENN\": \"2xtnbxzwsjb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心肌功能不全\",\n" +
            "      \"ENN\": \"xjgnbq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"附睾囊肿\",\n" +
            "      \"ENN\": \"fenz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"克雷伯杆菌性肺炎\",\n" +
            "      \"ENN\": \"klbgjxfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"三度房室传导阻滞\",\n" +
            "      \"ENN\": \"sdfscdzz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"结节病\",\n" +
            "      \"ENN\": \"jjb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心脏神经症\",\n" +
            "      \"ENN\": \"xzsjz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性广泛前壁心肌梗死\",\n" +
            "      \"ENN\": \"cjxgfqbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性髓系白血病\",\n" +
            "      \"ENN\": \"jxsxbxb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"二尖瓣术后狭窄\",\n" +
            "      \"ENN\": \"ejbshxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心内膜炎伴主动脉瓣穿孔\",\n" +
            "      \"ENN\": \"xnmybzdmbck\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺肉芽肿\",\n" +
            "      \"ENN\": \"fryz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性左心功能不全\",\n" +
            "      \"ENN\": \"mxzxgnbq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"主动脉夹层A2C型\",\n" +
            "      \"ENN\": \"zdmjcA2Cx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"室性自搏\",\n" +
            "      \"ENN\": \"sxzb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性前侧壁心肌梗死\",\n" +
            "      \"ENN\": \"jxqcbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性后间壁再发心肌梗死\",\n" +
            "      \"ENN\": \"jxhjbzfxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"高位肛瘘\",\n" +
            "      \"ENN\": \"gwgq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"带状疱疹后神经痛\",\n" +
            "      \"ENN\": \"dzhzhsjt\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肾积水\",\n" +
            "      \"ENN\": \"sjs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"下消化道出血\",\n" +
            "      \"ENN\": \"xxhdcx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脾损伤\",\n" +
            "      \"ENN\": \"pss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"1型糖尿病伴血糖控制不佳\",\n" +
            "      \"ENN\": \"1xtnbbxtkzbj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性后壁心肌梗死\",\n" +
            "      \"ENN\": \"cjxhbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"煤工尘肺\",\n" +
            "      \"ENN\": \"mgcf\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"妊娠期麻醉引起的心脏停搏\",\n" +
            "      \"ENN\": \"rsqmzyqdxztb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"大叶性肺炎\",\n" +
            "      \"ENN\": \"dyxfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"高血压急症\",\n" +
            "      \"ENN\": \"gxyjz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"腮腺良性肿瘤\",\n" +
            "      \"ENN\": \"sxlxzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肢体血管瘤\",\n" +
            "      \"ENN\": \"ztxgl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"风湿性多肌痛\",\n" +
            "      \"ENN\": \"fsxdjt\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"支气管结核\",\n" +
            "      \"ENN\": \"zqgjh\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"阴道前壁脱垂\",\n" +
            "      \"ENN\": \"ydqbtc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性血栓栓塞性肺动脉高压\",\n" +
            "      \"ENN\": \"mxxsssxfdmgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"高钾型周期性瘫痪\",\n" +
            "      \"ENN\": \"gjxzqxth\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"结核性心包炎\",\n" +
            "      \"ENN\": \"jhxxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"继发性高血压\",\n" +
            "      \"ENN\": \"jfxgxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"鼻炎\",\n" +
            "      \"ENN\": \"by\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"不完全性右束支传导阻滞\",\n" +
            "      \"ENN\": \"bwqxyszcdzz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"阵发性睡眠性血红蛋白尿\",\n" +
            "      \"ENN\": \"zfxsmxxhdbn\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心脏停搏\",\n" +
            "      \"ENN\": \"xztb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"外伤性白内障\",\n" +
            "      \"ENN\": \"wsxbnz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胃交界性肿瘤\",\n" +
            "      \"ENN\": \"wjjxzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"感染性心肌炎\",\n" +
            "      \"ENN\": \"grxxjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"感染性腹泻\",\n" +
            "      \"ENN\": \"grxfx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性胎儿宫内窘迫\",\n" +
            "      \"ENN\": \"jxtegnjp\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"子宫内膜息肉样增生\",\n" +
            "      \"ENN\": \"zgnmxryzs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"加速性室性自主心律\",\n" +
            "      \"ENN\": \"jsxsxzzxl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性右心衰竭\",\n" +
            "      \"ENN\": \"jxyxsj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新生儿湿肺\",\n" +
            "      \"ENN\": \"xsesf\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"房性逸搏\",\n" +
            "      \"ENN\": \"fxyb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性白血病\",\n" +
            "      \"ENN\": \"jxbxb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"特发性脊柱侧弯\",\n" +
            "      \"ENN\": \"tfxjzcw\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"腰神经根炎\",\n" +
            "      \"ENN\": \"ysjgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"室内传导阻滞\",\n" +
            "      \"ENN\": \"sncdzz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脂质性肺炎\",\n" +
            "      \"ENN\": \"zzxfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脑动脉粥样硬化\",\n" +
            "      \"ENN\": \"ndmzyyh\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢快综合征\",\n" +
            "      \"ENN\": \"mkzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"大动脉炎性高血压\",\n" +
            "      \"ENN\": \"ddmyxgxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性低血压\",\n" +
            "      \"ENN\": \"mxdxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"妊娠合并心包积液\",\n" +
            "      \"ENN\": \"rshbxbjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"Ⅱ型呼吸衰竭\",\n" +
            "      \"ENN\": \"exhxsj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"放射性肺炎\",\n" +
            "      \"ENN\": \"fsxfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肛瘘\",\n" +
            "      \"ENN\": \"gw\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"麻疹并发心肌炎\",\n" +
            "      \"ENN\": \"mzbfxjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"不稳定性心绞痛\",\n" +
            "      \"ENN\": \"bwdxxjt\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"无晶状体眼\",\n" +
            "      \"ENN\": \"wjzty\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肠梗阻\",\n" +
            "      \"ENN\": \"cgz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"非典型疣状心内膜炎\",\n" +
            "      \"ENN\": \"fdxuzxnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性再发心肌梗死\",\n" +
            "      \"ENN\": \"jxzfxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肝细胞癌\",\n" +
            "      \"ENN\": \"gxba\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"单纯性甲状腺肿\",\n" +
            "      \"ENN\": \"dcxjzxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"开角型青光眼\",\n" +
            "      \"ENN\": \"kjxqgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"特发性肺动脉高压\",\n" +
            "      \"ENN\": \"tfxfdmgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"亚急性血行播散性肺结核\",\n" +
            "      \"ENN\": \"yjxxxbsxfjh\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"中枢性睡眠呼吸暂停综合征\",\n" +
            "      \"ENN\": \"zsxsmhxztzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性前壁高侧壁再发心肌梗死\",\n" +
            "      \"ENN\": \"jxqbgcbzfxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"颈部血管瘤\",\n" +
            "      \"ENN\": \"jbxgl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"安眠药中毒\",\n" +
            "      \"ENN\": \"amyzd\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心内膜炎\",\n" +
            "      \"ENN\": \"xnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"膈缺如\",\n" +
            "      \"ENN\": \"wqr\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性下壁右心室心肌梗死\",\n" +
            "      \"ENN\": \"cjxxbyxsxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"恶性心包积液\",\n" +
            "      \"ENN\": \"exxbjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脑血管供血不足\",\n" +
            "      \"ENN\": \"nxggxbz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性胆管炎\",\n" +
            "      \"ENN\": \"jxdgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"甲状腺功能亢进症\",\n" +
            "      \"ENN\": \"jzxgnkjz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"毛细胞白血病\",\n" +
            "      \"ENN\": \"mxbbxb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"孔源性视网膜脱离\",\n" +
            "      \"ENN\": \"kyxswmtl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"先天性心脏传导阻滞\",\n" +
            "      \"ENN\": \"xtxxzcdzz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性会厌炎\",\n" +
            "      \"ENN\": \"jxhyy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脑动脉供血不足\",\n" +
            "      \"ENN\": \"ndmgxbz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"坐骨神经痛\",\n" +
            "      \"ENN\": \"zgsjt\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"强直性脊柱炎\",\n" +
            "      \"ENN\": \"qzxjzy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性感染心包积液\",\n" +
            "      \"ENN\": \"jxgrxbjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性心功能不全急性加重\",\n" +
            "      \"ENN\": \"mxxgnbqjxjz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性心肌梗死后心脏破裂\",\n" +
            "      \"ENN\": \"jxxjgshxzpl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"阻塞性尿路病伴有感染\",\n" +
            "      \"ENN\": \"zsxnlbbygr\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"髋骨关节病\",\n" +
            "      \"ENN\": \"sggjb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"输尿管狭窄\",\n" +
            "      \"ENN\": \"sngxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性肺源性心脏病\",\n" +
            "      \"ENN\": \"mxfyxxzb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"贲门恶性肿瘤\",\n" +
            "      \"ENN\": \"kmexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肾损伤\",\n" +
            "      \"ENN\": \"sss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"右心衰竭\",\n" +
            "      \"ENN\": \"yxsj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"缺氧性脑损害\",\n" +
            "      \"ENN\": \"qyxnsh\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新生儿低血压\",\n" +
            "      \"ENN\": \"xsedxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肝继发恶性肿瘤\",\n" +
            "      \"ENN\": \"gjfexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"阵发性心房颤动\",\n" +
            "      \"ENN\": \"zfxxfcd\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"白血病\",\n" +
            "      \"ENN\": \"bxb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"卵巢巧克力样囊肿\",\n" +
            "      \"ENN\": \"lcqklynz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"韦格纳肉芽肿病\",\n" +
            "      \"ENN\": \"wgnryzb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"梅毒性主动脉瓣狭窄\",\n" +
            "      \"ENN\": \"mdxzdmbxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"支气管哮喘(中度持续)\",\n" +
            "      \"ENN\": \"zqgxcAzdcxA\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"原发性高血压\",\n" +
            "      \"ENN\": \"yfxgxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"左侧输卵管积水\",\n" +
            "      \"ENN\": \"zcslgjs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"2型糖尿病性增殖性视网膜病\",\n" +
            "      \"ENN\": \"2xtnbxzzxswmb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"纵隔心包炎\",\n" +
            "      \"ENN\": \"zgxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性高侧壁再发心肌梗死\",\n" +
            "      \"ENN\": \"jxgcbzfxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"阿司匹林性支气管哮喘\",\n" +
            "      \"ENN\": \"asplxzqgxc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"狼疮性心包炎\",\n" +
            "      \"ENN\": \"lcxxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心源性晕厥\",\n" +
            "      \"ENN\": \"xyxyt\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性胎儿宫内窘迫\",\n" +
            "      \"ENN\": \"mxtegnjp\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新生儿室上性心动过速\",\n" +
            "      \"ENN\": \"xsessxxdgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脑膜瘤\",\n" +
            "      \"ENN\": \"nml\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肾上腺良性肿瘤\",\n" +
            "      \"ENN\": \"ssxlxzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"银屑病\",\n" +
            "      \"ENN\": \"yxb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性阑尾炎\",\n" +
            "      \"ENN\": \"jxlwy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"低颅压性头痛\",\n" +
            "      \"ENN\": \"dlyxtt\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"白内障\",\n" +
            "      \"ENN\": \"bnz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脾破裂\",\n" +
            "      \"ENN\": \"ppl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"液气胸\",\n" +
            "      \"ENN\": \"yqx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"职业性过敏性肺泡炎\",\n" +
            "      \"ENN\": \"zyxgmxfpy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"腹部开放性损伤\",\n" +
            "      \"ENN\": \"fbkfxss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"多囊卵巢综合征\",\n" +
            "      \"ENN\": \"dnlczhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"产科手术或操作后心脏停搏\",\n" +
            "      \"ENN\": \"cksshczhxztb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"妊娠合并冠状动脉供血不足\",\n" +
            "      \"ENN\": \"rshbgzdmgxbz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"重叠综合征\",\n" +
            "      \"ENN\": \"zdzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"乙状结肠良性肿瘤\",\n" +
            "      \"ENN\": \"yzjclxzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性播散性脑脊髓炎\",\n" +
            "      \"ENN\": \"jxbsxnjsy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性前壁心肌梗死\",\n" +
            "      \"ENN\": \"cjxqbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"束支折返性室性心动过速\",\n" +
            "      \"ENN\": \"szzfxsxxdgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"B型预激综合征\",\n" +
            "      \"ENN\": \"Bxyjzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"创伤后单侧膝关节病\",\n" +
            "      \"ENN\": \"cshdcxgjb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"高胆固醇血症\",\n" +
            "      \"ENN\": \"gdgcxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"致心律失常性右室心肌病\",\n" +
            "      \"ENN\": \"zxlscxysxjb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性心包炎\",\n" +
            "      \"ENN\": \"jxxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性腹膜炎\",\n" +
            "      \"ENN\": \"jxfmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"高血压亚急症\",\n" +
            "      \"ENN\": \"gxyyjz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"黄斑前膜\",\n" +
            "      \"ENN\": \"hbqm\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"椎管狭窄\",\n" +
            "      \"ENN\": \"zgxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"路易体痴呆\",\n" +
            "      \"ENN\": \"lytcd\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性肾衰竭\",\n" +
            "      \"ENN\": \"jxssj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"特发性肺间质纤维化\",\n" +
            "      \"ENN\": \"tfxfjzxwh\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"糖尿病\",\n" +
            "      \"ENN\": \"tnb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺继发恶性肿瘤\",\n" +
            "      \"ENN\": \"fjfexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"双向性室性心动过速\",\n" +
            "      \"ENN\": \"sxxsxxdgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性胃炎\",\n" +
            "      \"ENN\": \"jxwy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胃良性肿瘤\",\n" +
            "      \"ENN\": \"wlxzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性侧壁心肌梗死\",\n" +
            "      \"ENN\": \"jxcbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"难治性心力衰竭\",\n" +
            "      \"ENN\": \"nzxxlsj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性胎儿宫内窘迫(羊水型)\",\n" +
            "      \"ENN\": \"jxtegnjpAysxA\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"支气管狭窄\",\n" +
            "      \"ENN\": \"zqgxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"原发性进行性失语\",\n" +
            "      \"ENN\": \"yfxjxxsy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"主动脉夹层B3C型\",\n" +
            "      \"ENN\": \"zdmjcB3Cx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"风湿性心脏病\",\n" +
            "      \"ENN\": \"fsxxzb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"侵袭性肺曲霉菌病\",\n" +
            "      \"ENN\": \"qxxfqmjb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"右主支气管恶性肿瘤\",\n" +
            "      \"ENN\": \"yzzqgexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脑动脉炎\",\n" +
            "      \"ENN\": \"ndmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"结核性胸膜炎(复治)\",\n" +
            "      \"ENN\": \"jhxxmyAfzA\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"炎性肌病\",\n" +
            "      \"ENN\": \"yxjb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"结肠息肉\",\n" +
            "      \"ENN\": \"jcxr\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"乙状结肠恶性肿瘤\",\n" +
            "      \"ENN\": \"yzjcexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性心力衰竭\",\n" +
            "      \"ENN\": \"jxxlsj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"膈肌麻痹\",\n" +
            "      \"ENN\": \"xjmb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"原发性醛固酮增多症性高血压\",\n" +
            "      \"ENN\": \"yfxqgtzdzxgxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胃恶性肿瘤\",\n" +
            "      \"ENN\": \"wexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性正后壁心肌梗死\",\n" +
            "      \"ENN\": \"jxzhbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"干燥综合征\",\n" +
            "      \"ENN\": \"gzzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"2型糖尿病性视网膜病变\",\n" +
            "      \"ENN\": \"2xtnbxswmbb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心肌梗死恢复期\",\n" +
            "      \"ENN\": \"xjgshfq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心力衰竭\",\n" +
            "      \"ENN\": \"xlsj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肝囊肿\",\n" +
            "      \"ENN\": \"gnz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"窦性心律失常\",\n" +
            "      \"ENN\": \"hxxlsc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"眶上神经痛\",\n" +
            "      \"ENN\": \"kssjt\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"临界性高血压\",\n" +
            "      \"ENN\": \"ljxgxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"双侧腹股沟斜疝\",\n" +
            "      \"ENN\": \"scfggxl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"妊娠期糖尿病\",\n" +
            "      \"ENN\": \"rsqtnb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"静脉曲张\",\n" +
            "      \"ENN\": \"jmqz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"汞中毒\",\n" +
            "      \"ENN\": \"gzd\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"气管炎\",\n" +
            "      \"ENN\": \"qgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"紊乱性房性心律\",\n" +
            "      \"ENN\": \"wlxfxxl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性弥漫性腹膜炎\",\n" +
            "      \"ENN\": \"jxmmxfmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性心肌梗死后乳头肌断裂\",\n" +
            "      \"ENN\": \"jxxjgshrtjdl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"复发性髌骨脱位\",\n" +
            "      \"ENN\": \"ffxkgtw\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"膝内侧半月板伴十字韧带损伤\",\n" +
            "      \"ENN\": \"xncbybbszrdss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性糜烂性胃炎\",\n" +
            "      \"ENN\": \"jxmlxwy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"并发性白内障\",\n" +
            "      \"ENN\": \"bfxbnz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"石棉肺\",\n" +
            "      \"ENN\": \"smf\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脑炎\",\n" +
            "      \"ENN\": \"ny\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"乳腺内下象限恶性肿瘤\",\n" +
            "      \"ENN\": \"rxnxxxexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"儿茶酚胺敏感性室性心动过速\",\n" +
            "      \"ENN\": \"ecfamgxsxxdgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新生儿菌血症\",\n" +
            "      \"ENN\": \"xsejxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"结核性腹膜炎\",\n" +
            "      \"ENN\": \"jhxfmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性冠状动脉供血不足\",\n" +
            "      \"ENN\": \"jxgzdmgxbz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"变异型心绞痛\",\n" +
            "      \"ENN\": \"byxxjt\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"直肠脱垂\",\n" +
            "      \"ENN\": \"zctc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胃潴留\",\n" +
            "      \"ENN\": \"wyl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"左前分支传导阻滞\",\n" +
            "      \"ENN\": \"zqfzcdzz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"显微镜下多血管炎\",\n" +
            "      \"ENN\": \"xwjxdxgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"良性高血压\",\n" +
            "      \"ENN\": \"lxgxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"妊娠合并室性期前收缩\",\n" +
            "      \"ENN\": \"rshbsxqqss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"膝骨关节病\",\n" +
            "      \"ENN\": \"xggjb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"偶发房室性期前收缩\",\n" +
            "      \"ENN\": \"offsxqqss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胆管炎\",\n" +
            "      \"ENN\": \"dgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"腹主动脉夹层\",\n" +
            "      \"ENN\": \"fzdmjc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肝血管瘤\",\n" +
            "      \"ENN\": \"gxgl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性喘息性支气管炎\",\n" +
            "      \"ENN\": \"jxcxxzqgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肾病综合征伴微小病变型肾小球肾炎\",\n" +
            "      \"ENN\": \"sbzhzbwxbbxsxqsy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"膀胱多处恶性肿瘤\",\n" +
            "      \"ENN\": \"bqdcexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"冠状动脉瘘\",\n" +
            "      \"ENN\": \"gzdmx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"妊娠合并高血压性心脏病\",\n" +
            "      \"ENN\": \"rshbgxyxxzb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"链球菌性心包炎\",\n" +
            "      \"ENN\": \"lqjxxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"支架内血栓相关性心肌梗死\",\n" +
            "      \"ENN\": \"zjnxsxgxxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"进行性系统性硬化症\",\n" +
            "      \"ENN\": \"jxxxtxyhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"寻常型银屑病\",\n" +
            "      \"ENN\": \"xcxyxb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"高原性高血压\",\n" +
            "      \"ENN\": \"gyxgxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性广泛前壁心肌梗死\",\n" +
            "      \"ENN\": \"jxgfqbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"支气管扩张症合并感染\",\n" +
            "      \"ENN\": \"zqgkzzhbgr\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性风湿性心包炎\",\n" +
            "      \"ENN\": \"jxfsxxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"顶叶癫痫\",\n" +
            "      \"ENN\": \"dywo\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性右心室再发心肌梗死\",\n" +
            "      \"ENN\": \"jxyxszfxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"食管恶性肿瘤\",\n" +
            "      \"ENN\": \"sgexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"圣路易脑炎\",\n" +
            "      \"ENN\": \"slyny\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"病毒性肺炎\",\n" +
            "      \"ENN\": \"bdxfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"原发性肺动脉高压\",\n" +
            "      \"ENN\": \"yfxfdmgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"多发性肌炎\",\n" +
            "      \"ENN\": \"dfxjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胸膜病变\",\n" +
            "      \"ENN\": \"xmbb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"糖尿病性视网膜病变\",\n" +
            "      \"ENN\": \"tnbxswmbb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"手术后胸腔积液\",\n" +
            "      \"ENN\": \"sshxqjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"狼疮性肾炎\",\n" +
            "      \"ENN\": \"lcxsy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性小灶心肌梗死\",\n" +
            "      \"ENN\": \"jxxzxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺隐球菌病\",\n" +
            "      \"ENN\": \"fyqjb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性化脓性胆管炎\",\n" +
            "      \"ENN\": \"jxhnxdgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"跟腱断裂\",\n" +
            "      \"ENN\": \"gndl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"溶血性贫血\",\n" +
            "      \"ENN\": \"rxxpx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"病毒性脑膜炎\",\n" +
            "      \"ENN\": \"bdxnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"淋巴细胞性间质性肺炎\",\n" +
            "      \"ENN\": \"lbxbxjzxfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"窦房传导阻滞\",\n" +
            "      \"ENN\": \"gfcdzz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心房良性肿瘤\",\n" +
            "      \"ENN\": \"xflxzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"尿毒症性心包炎\",\n" +
            "      \"ENN\": \"ndzxxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"风湿性二尖瓣狭窄伴关闭不全\",\n" +
            "      \"ENN\": \"fsxejbxzbgbbq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胃小弯恶性肿瘤\",\n" +
            "      \"ENN\": \"wxwexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"主动脉夹层B1C型\",\n" +
            "      \"ENN\": \"zdmjcB1Cx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"2型糖尿病性肾病\",\n" +
            "      \"ENN\": \"2xtnbxsb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新生儿窦性心动过缓\",\n" +
            "      \"ENN\": \"xseqxxdgh\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"冠心病心律失常型\",\n" +
            "      \"ENN\": \"gxbxlscx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"类癌瘤综合征\",\n" +
            "      \"ENN\": \"lalzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新生儿甲状腺功能亢进症\",\n" +
            "      \"ENN\": \"xsejzxgnkjz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"子宫浆膜下平滑肌瘤\",\n" +
            "      \"ENN\": \"zgjmxphjl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"膝内侧半月板撕裂\",\n" +
            "      \"ENN\": \"xncbybsl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"长程持续性心房颤动\",\n" +
            "      \"ENN\": \"cccxxxfcd\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"结节性甲状腺肿\",\n" +
            "      \"ENN\": \"jjxjzxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胃肠功能紊乱\",\n" +
            "      \"ENN\": \"wcgnwl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"腹内疝\",\n" +
            "      \"ENN\": \"fnh\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"病毒性心肌心包炎\",\n" +
            "      \"ENN\": \"bdxxjxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"腹腔感染\",\n" +
            "      \"ENN\": \"fqgr\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性紧张型头痛\",\n" +
            "      \"ENN\": \"mxjzxtt\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肋间神经痛\",\n" +
            "      \"ENN\": \"ljsjt\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"室性并行心律\",\n" +
            "      \"ENN\": \"sxbxxl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新生儿二度房室传导阻滞\",\n" +
            "      \"ENN\": \"xseedfscdzz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"双侧输卵管积水\",\n" +
            "      \"ENN\": \"scslgjs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肝硬化伴食管静脉曲张破裂出血\",\n" +
            "      \"ENN\": \"gyhbsgjmqzplcx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"原发性血小板增多症\",\n" +
            "      \"ENN\": \"yfxxxbzdz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"左肺下叶恶性肿瘤\",\n" +
            "      \"ENN\": \"zfxyexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"多发性硬化\",\n" +
            "      \"ENN\": \"dfxyh\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"猩红热并发急性心肌炎\",\n" +
            "      \"ENN\": \"xhrbfjxxjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"高血压性心脏病\",\n" +
            "      \"ENN\": \"gxyxxzb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"左束支传导阻滞\",\n" +
            "      \"ENN\": \"zszcdzz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"结节病性心肌炎\",\n" +
            "      \"ENN\": \"jjbxxjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"单纯鼾症\",\n" +
            "      \"ENN\": \"dchz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性非ST段抬高性心肌梗死\",\n" +
            "      \"ENN\": \"cjxfSTdtgxxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性胆囊炎\",\n" +
            "      \"ENN\": \"jxdny\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性高侧壁心肌梗死\",\n" +
            "      \"ENN\": \"jxgcbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肾盂输尿管连接处狭窄\",\n" +
            "      \"ENN\": \"sysngljcxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性胰腺炎急性发作\",\n" +
            "      \"ENN\": \"mxyxyjxfz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"踝骨关节病\",\n" +
            "      \"ENN\": \"rggjb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"外斜视\",\n" +
            "      \"ENN\": \"wxs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"烟雾病\",\n" +
            "      \"ENN\": \"ywb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"消化不良\",\n" +
            "      \"ENN\": \"xhbl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性下壁正后壁心肌梗死\",\n" +
            "      \"ENN\": \"jxxbzhbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"直肠乙状结肠连接部恶性肿瘤\",\n" +
            "      \"ENN\": \"zcyzjcljbexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新生儿心力衰竭\",\n" +
            "      \"ENN\": \"xsexlsj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肩钙化性肌腱炎\",\n" +
            "      \"ENN\": \"jghxjfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"结核性心内膜炎\",\n" +
            "      \"ENN\": \"jhxxnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性前壁下壁高侧壁心肌梗死\",\n" +
            "      \"ENN\": \"cjxqbxbgcbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"细菌性肺炎\",\n" +
            "      \"ENN\": \"xjxfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"阵发性室上性心动过速\",\n" +
            "      \"ENN\": \"zfxssxxdgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺部感染\",\n" +
            "      \"ENN\": \"fbgr\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肛周脓肿\",\n" +
            "      \"ENN\": \"gznz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"腰椎间盘突出\",\n" +
            "      \"ENN\": \"yzjptc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"支气管哮喘\",\n" +
            "      \"ENN\": \"zqgxc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺错构瘤\",\n" +
            "      \"ENN\": \"fcgl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"重症肺炎\",\n" +
            "      \"ENN\": \"zzfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"巨细胞型心肌炎\",\n" +
            "      \"ENN\": \"jxbxxjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性细菌性心内膜炎\",\n" +
            "      \"ENN\": \"jxxjxxnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"卵巢过度刺激综合征\",\n" +
            "      \"ENN\": \"lcgdcjzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺大疱\",\n" +
            "      \"ENN\": \"fdv\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"视网膜脱离\",\n" +
            "      \"ENN\": \"swmtl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"髂动脉狭窄\",\n" +
            "      \"ENN\": \"bdmxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"葡萄球菌性肺炎\",\n" +
            "      \"ENN\": \"ptqjxfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性白血病\",\n" +
            "      \"ENN\": \"mxbxb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"椎管内良性肿瘤\",\n" +
            "      \"ENN\": \"zgnlxzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"创伤性硬膜下血肿\",\n" +
            "      \"ENN\": \"csxymxxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脓胸\",\n" +
            "      \"ENN\": \"nx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"症状性癫痫\",\n" +
            "      \"ENN\": \"zzxix\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"糖尿病性增殖性视网膜病\",\n" +
            "      \"ENN\": \"tnbxzzxswmb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"卵巢囊肿\",\n" +
            "      \"ENN\": \"lcnz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"三尖瓣关闭不全\",\n" +
            "      \"ENN\": \"sjbgbbq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胸膜恶性肿瘤\",\n" +
            "      \"ENN\": \"xmexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新生儿黄疸\",\n" +
            "      \"ENN\": \"xsehx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性前侧壁下壁心肌梗死\",\n" +
            "      \"ENN\": \"jxqcbxbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"精索静脉曲张\",\n" +
            "      \"ENN\": \"jsjmqz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"革兰阳性细菌性肺炎\",\n" +
            "      \"ENN\": \"glyxxjxfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"膝关节前十字韧带完全断裂\",\n" +
            "      \"ENN\": \"xgjqszrdwqdl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"颈部脊髓损伤\",\n" +
            "      \"ENN\": \"jbjsss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"结节性多动脉炎\",\n" +
            "      \"ENN\": \"jjxddmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心肌损害\",\n" +
            "      \"ENN\": \"xjsh\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"声带息肉\",\n" +
            "      \"ENN\": \"sdxr\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"膝关节滑膜炎\",\n" +
            "      \"ENN\": \"xgjhmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胃溃疡\",\n" +
            "      \"ENN\": \"wky\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"喉原位癌\",\n" +
            "      \"ENN\": \"hywa\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"睡眠呼吸暂停低通气综合征\",\n" +
            "      \"ENN\": \"smhxztdtqzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"右心心肌病\",\n" +
            "      \"ENN\": \"yxxjb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"高血压\",\n" +
            "      \"ENN\": \"gxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心室颤动\",\n" +
            "      \"ENN\": \"xscd\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"前列腺恶性肿瘤\",\n" +
            "      \"ENN\": \"qlxexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"子宫阴道完全脱垂\",\n" +
            "      \"ENN\": \"zgydwqtc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性下壁正后壁再发心肌梗死\",\n" +
            "      \"ENN\": \"jxxbzhbzfxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性心肌梗死后腱索断裂\",\n" +
            "      \"ENN\": \"jxxjgshvsdl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性下壁心肌梗死\",\n" +
            "      \"ENN\": \"jxxbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"变性性近视\",\n" +
            "      \"ENN\": \"bxxjs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胃窦恶性肿瘤\",\n" +
            "      \"ENN\": \"wqexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"尿道损伤\",\n" +
            "      \"ENN\": \"ndss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性下壁侧壁心肌梗死\",\n" +
            "      \"ENN\": \"jxxbcbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性下后壁右心室心肌梗死\",\n" +
            "      \"ENN\": \"jxxhbyxsxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"突发性聋\",\n" +
            "      \"ENN\": \"tfxl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"结性心动过速\",\n" +
            "      \"ENN\": \"jxxdgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胆囊结石伴急性胆囊炎\",\n" +
            "      \"ENN\": \"dnjsbjxdny\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性膝后十字韧带断裂\",\n" +
            "      \"ENN\": \"cjxxhszrddl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"结核性心包积液\",\n" +
            "      \"ENN\": \"jhxxbjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"手术后心肌梗死\",\n" +
            "      \"ENN\": \"sshxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性广泛前壁高侧壁心肌梗死\",\n" +
            "      \"ENN\": \"cjxgfqbgcbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胆石症\",\n" +
            "      \"ENN\": \"dsz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脑栓塞\",\n" +
            "      \"ENN\": \"nss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"阵发性交界性心动过速\",\n" +
            "      \"ENN\": \"zfxjjxxdgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"IgA肾病\",\n" +
            "      \"ENN\": \"IgAsb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"偏头痛\",\n" +
            "      \"ENN\": \"ptt\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胆囊结石伴慢性胆囊炎急性发作\",\n" +
            "      \"ENN\": \"dnjsbmxdnyjxfz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"非冠心病性心肌梗死\",\n" +
            "      \"ENN\": \"fgxbxxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"钙化性肌腱炎\",\n" +
            "      \"ENN\": \"ghxjry\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性阻塞性肺疾病伴急性下呼吸道感染\",\n" +
            "      \"ENN\": \"mxzsxfjbbjxxhxdgr\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"套细胞淋巴瘤\",\n" +
            "      \"ENN\": \"txblbl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"双胎输血综合征\",\n" +
            "      \"ENN\": \"stsxzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性高侧壁心肌梗死\",\n" +
            "      \"ENN\": \"cjxgcbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"非风湿性三尖瓣关闭不全\",\n" +
            "      \"ENN\": \"ffsxsjbgbbq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"腹主动脉瘤\",\n" +
            "      \"ENN\": \"fzdml\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"原发性痛风\",\n" +
            "      \"ENN\": \"yfxtf\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胆囊结石\",\n" +
            "      \"ENN\": \"dnjs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺炎恢复期\",\n" +
            "      \"ENN\": \"fyhfq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性恰加斯病伴心肌炎\",\n" +
            "      \"ENN\": \"mxqjsbbxjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"先天性白内障\",\n" +
            "      \"ENN\": \"xtxbnz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"中耳胆脂瘤\",\n" +
            "      \"ENN\": \"zedzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脱屑性间质性肺炎\",\n" +
            "      \"ENN\": \"txxjzxfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性后壁再发心肌梗死\",\n" +
            "      \"ENN\": \"jxhbzfxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性化脓性缩窄性心包炎\",\n" +
            "      \"ENN\": \"mxhnxszxxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"结性逸搏\",\n" +
            "      \"ENN\": \"jxyb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"低血氧症\",\n" +
            "      \"ENN\": \"dxyz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"妊娠合并窦性心动过速\",\n" +
            "      \"ENN\": \"rshbcxxdgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"黄斑水肿\",\n" +
            "      \"ENN\": \"hbsz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肥厚性非梗阻性心肌病\",\n" +
            "      \"ENN\": \"fhxfgzxxjb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"硬脊膜外囊肿\",\n" +
            "      \"ENN\": \"yjmwnz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性广泛前壁再发心肌梗死\",\n" +
            "      \"ENN\": \"jxgfqbzfxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"基底型偏头痛\",\n" +
            "      \"ENN\": \"jdxptt\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"持续性心房扑动\",\n" +
            "      \"ENN\": \"cxxxfpd\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"窦性心动过速\",\n" +
            "      \"ENN\": \"cxxdgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"机械相关性心内膜炎\",\n" +
            "      \"ENN\": \"jxxgxxnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胆囊恶性肿瘤\",\n" +
            "      \"ENN\": \"dnexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺源性心脏病\",\n" +
            "      \"ENN\": \"fyxxzb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺血管炎\",\n" +
            "      \"ENN\": \"fxgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"稳定性心绞痛\",\n" +
            "      \"ENN\": \"wdxxjt\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"高血压1级\",\n" +
            "      \"ENN\": \"gxy1j\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脂肪瘤\",\n" +
            "      \"ENN\": \"zfl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"血管迷走性晕厥\",\n" +
            "      \"ENN\": \"xgmzxyr\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"原发性双侧膝关节病\",\n" +
            "      \"ENN\": \"yfxscxgjb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"子宫恶性肿瘤\",\n" +
            "      \"ENN\": \"zgexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"股骨颈骨折\",\n" +
            "      \"ENN\": \"ggjgz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"枕骨大孔疝\",\n" +
            "      \"ENN\": \"zgdkx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胆囊结石伴胆囊炎\",\n" +
            "      \"ENN\": \"dnjsbdny\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"分水岭脑梗死\",\n" +
            "      \"ENN\": \"fslngs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肠扭转\",\n" +
            "      \"ENN\": \"cnz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"内斜视\",\n" +
            "      \"ENN\": \"nxs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"预激综合征\",\n" +
            "      \"ENN\": \"yjzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"锁骨下动脉狭窄\",\n" +
            "      \"ENN\": \"sgxdmxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"子宫平滑肌瘤\",\n" +
            "      \"ENN\": \"zgphjl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"已知病毒的流感性肺炎\",\n" +
            "      \"ENN\": \"yzbddlgxfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"颅内动脉瘤\",\n" +
            "      \"ENN\": \"lndml\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"甲状腺功能减低性心包积液\",\n" +
            "      \"ENN\": \"jzxgnjdxxbjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"主动脉夹层A3S型\",\n" +
            "      \"ENN\": \"zdmjcA3Sx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"腹腔妊娠\",\n" +
            "      \"ENN\": \"fqrs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"糖耐量异常\",\n" +
            "      \"ENN\": \"tnlyc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性下壁高侧壁正后壁再发心肌梗死\",\n" +
            "      \"ENN\": \"jxxbgcbzhbzfxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肾积水伴输尿管结石\",\n" +
            "      \"ENN\": \"sjsbsngjs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"动脉导管未闭\",\n" +
            "      \"ENN\": \"dmdgwb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"霍奇金淋巴瘤\",\n" +
            "      \"ENN\": \"hqjlbl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性前列腺炎\",\n" +
            "      \"ENN\": \"jxqlxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新生儿窦性心动过速\",\n" +
            "      \"ENN\": \"xsebxxdgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"泛发性湿疹\",\n" +
            "      \"ENN\": \"ffxsz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"风湿性主动脉瓣关闭不全\",\n" +
            "      \"ENN\": \"fsxzdmbgbbq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"后发性白内障\",\n" +
            "      \"ENN\": \"hfxbnz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"眼球破裂伤\",\n" +
            "      \"ENN\": \"yqpls\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"阻塞性睡眠呼吸暂停综合征\",\n" +
            "      \"ENN\": \"zsxsmhxztzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性前间壁心肌梗死\",\n" +
            "      \"ENN\": \"cjxqjbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"右侧输卵管积水\",\n" +
            "      \"ENN\": \"ycslgjs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性恰加斯病伴心肌炎\",\n" +
            "      \"ENN\": \"jxqjsbbxjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺结节病\",\n" +
            "      \"ENN\": \"fjjb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心脏淀粉样变性\",\n" +
            "      \"ENN\": \"xzdfybx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"遗传性出血性毛细血管扩张症\",\n" +
            "      \"ENN\": \"ycxcxxmxxgkzz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性小灶性心肌梗死\",\n" +
            "      \"ENN\": \"cjxxzxxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"产褥期麻醉引起的心脏停搏\",\n" +
            "      \"ENN\": \"crqmzyqdxztb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性前壁下壁再发心肌梗死\",\n" +
            "      \"ENN\": \"jxqbxbzfxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脊柱侧弯\",\n" +
            "      \"ENN\": \"jzcw\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性肺结核\",\n" +
            "      \"ENN\": \"cjxfjh\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"支气管哮喘急性发作(轻度)\",\n" +
            "      \"ENN\": \"zqgxcjxfzAqdA\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性风湿性心肌心包炎\",\n" +
            "      \"ENN\": \"mxfsxxjxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"隐睾\",\n" +
            "      \"ENN\": \"yl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"线状硬皮病\",\n" +
            "      \"ENN\": \"xzypb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"不纯性心房扑动\",\n" +
            "      \"ENN\": \"bcxxfpd\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"弥漫性泛细支气管炎\",\n" +
            "      \"ENN\": \"mmxfxzqgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"乳腺外下象限恶性肿瘤\",\n" +
            "      \"ENN\": \"rxwxxxexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新生儿交界性期前收缩\",\n" +
            "      \"ENN\": \"xsejjxqqss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"冠状动脉肌桥\",\n" +
            "      \"ENN\": \"gzdmjq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"双侧感音神经性聋\",\n" +
            "      \"ENN\": \"scgysjxl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"老年收缩期高血压\",\n" +
            "      \"ENN\": \"lnssqgxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺动脉高压\",\n" +
            "      \"ENN\": \"fdmgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"大动脉炎\",\n" +
            "      \"ENN\": \"ddmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"妊娠期麻醉引起的心力衰竭\",\n" +
            "      \"ENN\": \"rsqmzyqdxlsj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"自发性气胸\",\n" +
            "      \"ENN\": \"zfxqx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"感染性心包炎\",\n" +
            "      \"ENN\": \"grxxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"变应性肉芽肿性脉管炎\",\n" +
            "      \"ENN\": \"byxryzxmgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"间歇性预激综合征\",\n" +
            "      \"ENN\": \"jxxyjzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"非创伤性硬膜下血肿\",\n" +
            "      \"ENN\": \"fcsxymxxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性化脓性中耳炎胆脂瘤型\",\n" +
            "      \"ENN\": \"mxhnxzeydzlx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性阻塞性肺疾病急性加重\",\n" +
            "      \"ENN\": \"mxzsxfjbjxjz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性前侧壁心肌梗死\",\n" +
            "      \"ENN\": \"cjxqcbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脊柱继发恶性肿瘤\",\n" +
            "      \"ENN\": \"jzjfexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"神经痛\",\n" +
            "      \"ENN\": \"sjt\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"小肠血管瘤\",\n" +
            "      \"ENN\": \"xcxgl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"膝关节十字韧带断裂\",\n" +
            "      \"ENN\": \"xgjszrddl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"姿位性低血压\",\n" +
            "      \"ENN\": \"zwxdxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"骨质疏松\",\n" +
            "      \"ENN\": \"gzss\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"主动脉夹层B型\",\n" +
            "      \"ENN\": \"zdmjcBx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"甲状腺功能减退\",\n" +
            "      \"ENN\": \"jzxgnjt\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"直肠息肉\",\n" +
            "      \"ENN\": \"zcxr\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"细菌性心肌炎\",\n" +
            "      \"ENN\": \"xjxxjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性侧壁正后壁再发心肌梗死\",\n" +
            "      \"ENN\": \"jxcbzhbzfxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脑脓肿\",\n" +
            "      \"ENN\": \"nnz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"颈内动脉狭窄\",\n" +
            "      \"ENN\": \"jndmxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性心力衰竭\",\n" +
            "      \"ENN\": \"mxxlsj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"高血压性心力衰竭\",\n" +
            "      \"ENN\": \"gxyxxlsj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性前列腺炎\",\n" +
            "      \"ENN\": \"mxqlxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"不完全性肠梗阻\",\n" +
            "      \"ENN\": \"bwqxcgz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"房内折返性心动过速\",\n" +
            "      \"ENN\": \"fnzfxxdgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性肥厚性鼻炎\",\n" +
            "      \"ENN\": \"mxfhxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新生儿心包积液\",\n" +
            "      \"ENN\": \"xsexbjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"2型糖尿病性酮症\",\n" +
            "      \"ENN\": \"2xtnbxtz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"2型糖尿病性增殖性玻璃体出血性视网膜病\",\n" +
            "      \"ENN\": \"2xtnbxzzxbltcxxswmb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"左肺上叶恶性肿瘤\",\n" +
            "      \"ENN\": \"zfsyexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"牵拉性视网膜脱离\",\n" +
            "      \"ENN\": \"qlxswmtl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"主动脉夹层B2S型\",\n" +
            "      \"ENN\": \"zdmjcB2Sx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"翼状胬肉\",\n" +
            "      \"ENN\": \"yznr\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"主动脉夹层A型\",\n" +
            "      \"ENN\": \"zdmjcAx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"血管炎\",\n" +
            "      \"ENN\": \"xgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性淋巴细胞白血病\",\n" +
            "      \"ENN\": \"jxlbxbbxb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"食管息肉\",\n" +
            "      \"ENN\": \"sgxr\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"原虫性心肌炎\",\n" +
            "      \"ENN\": \"ycxxjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肠系膜裂孔疝\",\n" +
            "      \"ENN\": \"cxmlkm\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"双肺恶性肿瘤\",\n" +
            "      \"ENN\": \"sfexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"支气管肺炎\",\n" +
            "      \"ENN\": \"zqgfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"二尖瓣后叶脱垂\",\n" +
            "      \"ENN\": \"ejbhytc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"疾病六\",\n" +
            "      \"ENN\": \"jbl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肺结核(仅痰培养证实)\",\n" +
            "      \"ENN\": \"fjhAjtpyzsA\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心室预激\",\n" +
            "      \"ENN\": \"xsyj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"输尿管结石\",\n" +
            "      \"ENN\": \"sngjs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"卵巢交界性肿瘤\",\n" +
            "      \"ENN\": \"lcjjxzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心房扑动\",\n" +
            "      \"ENN\": \"xfpd\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"糖尿病性酮症酸中毒\",\n" +
            "      \"ENN\": \"tnbxtzszd\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"主动脉夹层A1S型\",\n" +
            "      \"ENN\": \"zdmjcA1Sx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性气管炎\",\n" +
            "      \"ENN\": \"mxqgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"出血性脑梗死\",\n" +
            "      \"ENN\": \"cxxngs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"十二指肠溃疡\",\n" +
            "      \"ENN\": \"sezcky\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"妊娠合并二尖瓣关闭不全\",\n" +
            "      \"ENN\": \"rshbejbgbbq\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"高血压性心脏病和肾脏病伴心力衰竭和肾衰竭\",\n" +
            "      \"ENN\": \"gxyxxzbhszbbxlsjhssj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"乳腺外上象限恶性肿瘤\",\n" +
            "      \"ENN\": \"rxwsxxexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"会厌囊肿\",\n" +
            "      \"ENN\": \"hynz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肉芽肿型心肌炎\",\n" +
            "      \"ENN\": \"ryzxxjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"支气管哮喘(临床缓解期)\",\n" +
            "      \"ENN\": \"zqgxcAlchjqA\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"双侧腹股沟直疝\",\n" +
            "      \"ENN\": \"scfggza\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"黄斑裂孔\",\n" +
            "      \"ENN\": \"hblk\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"右室室性心动过速\",\n" +
            "      \"ENN\": \"yssxxdgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"吸入性肺炎\",\n" +
            "      \"ENN\": \"xrxfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"原发性腹膜炎\",\n" +
            "      \"ENN\": \"yfxfmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"女性盆腔炎\",\n" +
            "      \"ENN\": \"nxpqy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"难治性癫痫\",\n" +
            "      \"ENN\": \"nzxse\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"结肠良性肿瘤\",\n" +
            "      \"ENN\": \"jclxzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"股动脉闭塞\",\n" +
            "      \"ENN\": \"gdmbs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肾挫伤\",\n" +
            "      \"ENN\": \"scs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"鞘膜积液\",\n" +
            "      \"ENN\": \"qmjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性萎缩性胃炎\",\n" +
            "      \"ENN\": \"mxwsxwy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性前壁心肌梗死\",\n" +
            "      \"ENN\": \"jxqbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肝豆状核变性\",\n" +
            "      \"ENN\": \"gdzhbx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"嗜酸粒细胞增多综合征\",\n" +
            "      \"ENN\": \"sslxbzdzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"基底节出血\",\n" +
            "      \"ENN\": \"jdjcx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新生儿ABO血型不合溶血病\",\n" +
            "      \"ENN\": \"xseABOxxbhrxb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"下腔静脉血栓形成\",\n" +
            "      \"ENN\": \"xqjmxsxc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"输卵管伞部妊娠\",\n" +
            "      \"ENN\": \"slgsbrs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"支气管哮喘(慢性持续期)\",\n" +
            "      \"ENN\": \"zqgxcAmxcxqA\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"过敏性肺炎\",\n" +
            "      \"ENN\": \"gmxfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"皮质性老年性白内障\",\n" +
            "      \"ENN\": \"pzxlnxbnz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"支气管扩张症\",\n" +
            "      \"ENN\": \"zqgkzz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"吸入有毒气体性肺炎\",\n" +
            "      \"ENN\": \"xrydqtxfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"股外侧皮神经痛\",\n" +
            "      \"ENN\": \"gwcpsjt\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"自身免疫性溶血性贫血\",\n" +
            "      \"ENN\": \"zsmyxrxxpx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"膀胱恶性肿瘤\",\n" +
            "      \"ENN\": \"blexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新生血管性青光眼\",\n" +
            "      \"ENN\": \"xsxgxqgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胰头恶性肿瘤\",\n" +
            "      \"ENN\": \"ytexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"类风湿性心肌炎\",\n" +
            "      \"ENN\": \"lfsxxjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"颈椎间盘突出\",\n" +
            "      \"ENN\": \"jzjptc\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"前列腺炎\",\n" +
            "      \"ENN\": \"qlxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"原发性单侧髋关节病\",\n" +
            "      \"ENN\": \"yfxdcigjb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肛裂\",\n" +
            "      \"ENN\": \"gl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"腰椎管狭窄\",\n" +
            "      \"ENN\": \"yzgxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性心肌梗死\",\n" +
            "      \"ENN\": \"cjxxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性鼻窦炎\",\n" +
            "      \"ENN\": \"mxbty\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"葡萄球菌性心包炎\",\n" +
            "      \"ENN\": \"ptqjxxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"屈光不正\",\n" +
            "      \"ENN\": \"qgbz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胆总管结石伴急性胆管炎\",\n" +
            "      \"ENN\": \"dzgjsbjxdgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"房间隔缺损\",\n" +
            "      \"ENN\": \"fjgqs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心尖肥厚型心肌病\",\n" +
            "      \"ENN\": \"xjfhxxjb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"子宫内膜恶性肿瘤\",\n" +
            "      \"ENN\": \"zgnmexzl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胆囊炎\",\n" +
            "      \"ENN\": \"dny\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"窦房结游走性心律\",\n" +
            "      \"ENN\": \"afjyzxxl\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"青光眼\",\n" +
            "      \"ENN\": \"qgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"膀胱原位癌\",\n" +
            "      \"ENN\": \"bmywa\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"隐匿性冠状动脉粥样硬化性心脏病\",\n" +
            "      \"ENN\": \"ynxgzdmzyyhxxzb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"上呼吸道感染\",\n" +
            "      \"ENN\": \"shxdgr\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"纵隔淋巴结结核\",\n" +
            "      \"ENN\": \"zglbjjh\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肾动脉狭窄\",\n" +
            "      \"ENN\": \"sdmxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"粘连性肠梗阻\",\n" +
            "      \"ENN\": \"zlxcgz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性风湿性心脏病\",\n" +
            "      \"ENN\": \"mxfsxxzb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"腰椎间盘突出伴神经根病\",\n" +
            "      \"ENN\": \"yzjptcbsjgb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"新生儿肺炎\",\n" +
            "      \"ENN\": \"xsefy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"阻塞性肺炎\",\n" +
            "      \"ENN\": \"zsxfy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"淋球菌性心肌炎\",\n" +
            "      \"ENN\": \"lqjxxjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性下壁侧壁正后壁再发心肌梗死\",\n" +
            "      \"ENN\": \"jxxbcbzhbzfxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"镰状细胞性贫血\",\n" +
            "      \"ENN\": \"lzxbxpx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性鼻炎\",\n" +
            "      \"ENN\": \"mxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"重症肌无力危象\",\n" +
            "      \"ENN\": \"zzjwlwx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性气管支气管炎\",\n" +
            "      \"ENN\": \"jxqgzqgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"脑动脉瘤\",\n" +
            "      \"ENN\": \"ndml\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"食管贲门黏膜撕裂综合征\",\n" +
            "      \"ENN\": \"sgtmjmslzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"结核性胸膜炎(细菌学和组织学证实)\",\n" +
            "      \"ENN\": \"jhxxmyAxjxhzzxzsA\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"支气管哮喘急性发作(中度)\",\n" +
            "      \"ENN\": \"zqgxcjxfzAzdA\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"非特异性心包炎\",\n" +
            "      \"ENN\": \"ftyxxby\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"非霍奇金淋巴瘤(B细胞型)\",\n" +
            "      \"ENN\": \"fhqjlblABxbxA\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"十二指肠憩室\",\n" +
            "      \"ENN\": \"sezcjs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性下壁高侧壁心肌梗死\",\n" +
            "      \"ENN\": \"jxxbgcbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"柯萨奇病毒性心内膜炎\",\n" +
            "      \"ENN\": \"ksqbdxxnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"人工晶体脱位\",\n" +
            "      \"ENN\": \"rgjttw\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"子宫内膜炎\",\n" +
            "      \"ENN\": \"zgnmy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"乳糜性心包积液\",\n" +
            "      \"ENN\": \"rmxxbjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"陈旧性正后壁心肌梗死\",\n" +
            "      \"ENN\": \"cjxzhbxjgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"前庭神经炎\",\n" +
            "      \"ENN\": \"qtsjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"胆囊肌腺症\",\n" +
            "      \"ENN\": \"dnjxz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"地中海贫血\",\n" +
            "      \"ENN\": \"dzhpx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"低钾型周期性瘫痪\",\n" +
            "      \"ENN\": \"djxzqxth\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"一氧化碳中毒迟发性脑病\",\n" +
            "      \"ENN\": \"yyhtzdcfxnb\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"血管性帕金森综合征\",\n" +
            "      \"ENN\": \"xgxpjszhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"阑尾炎\",\n" +
            "      \"ENN\": \"lwy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"下肢动脉粥样硬化闭塞症\",\n" +
            "      \"ENN\": \"xzdmzyyhbsz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"子宫脱垂Ⅲ度\",\n" +
            "      \"ENN\": \"zgtcgd\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"慢性膀胱炎\",\n" +
            "      \"ENN\": \"mxbgy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性胰腺炎\",\n" +
            "      \"ENN\": \"jxyxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"特发性血小板减少性紫癜\",\n" +
            "      \"ENN\": \"tfxxxbjsxzo\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"肾病综合征\",\n" +
            "      \"ENN\": \"sbzhz\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"腰骶椎间盘变性\",\n" +
            "      \"ENN\": \"ynzjpbx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"心动过速\",\n" +
            "      \"ENN\": \"xdgs\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"直立性低血压\",\n" +
            "      \"ENN\": \"zlxdxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"梅毒性心肌炎\",\n" +
            "      \"ENN\": \"mdxxjy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"异常子宫出血\",\n" +
            "      \"ENN\": \"yczgcx\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"急性化脓性阑尾炎\",\n" +
            "      \"ENN\": \"jxhnxlwy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"CNN\": \"柯萨奇病毒性心肌炎\",\n" +
            "      \"ENN\": \"ksqbdxxjy\"\n" +
            "    },\n" +
            "  ";

    @Test
    public void addDisease2table() {
        JSONObject jsonObject = JSONObject.parseObject(s + s1);
        JSONArray result = jsonObject.getJSONArray("result");
        List<SysDiseases> list = new LinkedList<>();

        Iterator<Object> iterator = result.iterator();
        Map<String, String>map=new HashMap<>();
        while (iterator.hasNext()) {
            JSONObject next = (JSONObject) iterator.next();
            SysDiseases diseasesList=new SysDiseases();
//            map.put(next.getString("CNN"),next.getString("ENN"));
            diseasesList.setChinaDiseases(next.getString("CNN"));
            diseasesList.setEngDiseases(next.getString("ENN"));
            diseasesListRepository.save(diseasesList);
//            list.add(diseasesList);
        }
//        List<DiseasesList> save = diseasesListRepository.save(list);

    }

    @Test
    public void testFind(){
//        List<SysDiseases> z = diseasesListRepository.findAllByEngDiseases("z");
//        z.forEach(s-> System.out.println(s.toString()));

        String xj = StringUtil.getFirstSpell("肿瘤");
        List<String> xj1 = diseasesListRepository.findAllByChinaDiseasesAndEngDiseases("肿瘤",xj);
        xj1.forEach(s-> System.out.println(s));
    }

    public static void main(String[] args) {
        List<Map<String, String>> list = new LinkedList<>();


//        JSONObject jsonObject = JSONObject.parseObject(s+s1);
//        Object result = jsonObject.get("result");
    }
}
package jhmk.clinic.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author ziyu.zhou
 * @date 2018/12/10 15:00
 */
@Component
@ConfigurationProperties()
@PropertySource(value = "classpath:config/mongoConfig.yml")
public class MongoPropertiesConfig {
    //端口号
    private int post;
    //数据库地址
    private String  host;
    //随机病例数据库
    private String dataDourceForRandom;
    //规则数据库
    private String dataDourceForDecisionRule;
    //好养数据库
    private String dataDourceForCyyy;

    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDataDourceForRandom() {
        return dataDourceForRandom;
    }

    public void setDataDourceForRandom(String dataDourceForRandom) {
        this.dataDourceForRandom = dataDourceForRandom;
    }

    public String getDataDourceForDecisionRule() {
        return dataDourceForDecisionRule;
    }

    public void setDataDourceForDecisionRule(String dataDourceForDecisionRule) {
        this.dataDourceForDecisionRule = dataDourceForDecisionRule;
    }

    public String getDataDourceForCyyy() {
        return dataDourceForCyyy;
    }

    public void setDataDourceForCyyy(String dataDourceForCyyy) {
        this.dataDourceForCyyy = dataDourceForCyyy;
    }
}

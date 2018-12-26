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
@PropertySource(value = "classpath:config/urlConfig.yml")
public class UrlPropertiesConfig {

    private String cdssurl;
    private String pageurl;
    private String earlywarnurl;
    private String testurl;

    public String getCdssurl() {
        return cdssurl;
    }

    public void setCdssurl(String cdssurl) {
        this.cdssurl = cdssurl;
    }

    public String getPageurl() {
        return pageurl;
    }

    public void setPageurl(String pageurl) {
        this.pageurl = pageurl;
    }

    public String getEarlywarnurl() {
        return earlywarnurl;
    }

    public void setEarlywarnurl(String earlywarnurl) {
        this.earlywarnurl = earlywarnurl;
    }

    public String getTesturl() {
        return testurl;
    }

    public void setTesturl(String testurl) {
        this.testurl = testurl;
    }
}

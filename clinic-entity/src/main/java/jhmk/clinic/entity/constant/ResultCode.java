package jhmk.clinic.entity.constant;

/**
 * @author liujinlong
 * @date 2018/01/28
 */
public enum  ResultCode {

    SUCCESS("请求成功"),
    FAIL_BIZ_WRONG_PARAMETER("参数错误"),
    FAIL_BIZ_USER_NOT_EXISTS("用户不存在"),

    FAIL_SYS_ERROR("系统错误,请稍后重试"),
    ;

    ResultCode(String desc) {
        this.desc = desc;
    }
    private String desc;

    public String getDesc() {
        return desc;
    }
}

package hu.helper.bang.center.notify.enums;

/**
 * 通知状态枚举
 * @Author : Jiang XingKun
 * @Date : 2023/2/22
 * @Description :
 */
public enum NotifyResultEnum {
    /**
     * 成功
     */
    SUCCESS(1,"1.成功"),
    /**
     * 失败
     */
    FAILED(2,"2.失败");


    private final Integer code;
    private final String desc;

    NotifyResultEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}

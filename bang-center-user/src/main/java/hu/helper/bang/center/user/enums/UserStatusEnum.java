package hu.helper.bang.center.user.enums;

/**
 * 用户状态枚举
 * @Author : Luo Siwei
 * @Date : 2023/2/11 20:50
 * @Description :
 */
public enum UserStatusEnum {
    /**
     * 已封禁
     */
    BANNED(0,"BANNED"),
    /**
     * 正常
     */
    NORMAL(1,"NORMAL");

    private Integer code;
    private String desc;

    UserStatusEnum(Integer code, String desc) {
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

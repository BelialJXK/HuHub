package hu.helper.bang.center.user.enums;

/**
 * 用户角色枚举
 *
 * @Author : Luo Siwei
 * @Date : 2023/2/11 21:28
 * @Description :
 */
public enum UserRoleEnum {
    /**
     * 超级管理员
     */
    SUPER_ADMIN(1L, "超级管理员"),
    /**
     * 板块管理员
     */
    MODULE_ADMIN(2L, "板块管理员"),
    /**
     * 普通用户
     */
    NORMAL_USER(3L, "普通用户");

    private Long code;
    private String desc;

    UserRoleEnum(Long code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Long getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}

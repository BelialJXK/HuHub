package hu.helper.bang.center.common.exception;

/**
 * @Author : Luo Siwei
 * @Date : 2023/3/9 10:35
 * @Description : 业务错误码集合
 */
public enum BangErrorCodes {
    /**
     * 通用，参数异常
     */
    COMMON_INVALID_PARAM("参数异常");

    private String desc;

    BangErrorCodes(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}

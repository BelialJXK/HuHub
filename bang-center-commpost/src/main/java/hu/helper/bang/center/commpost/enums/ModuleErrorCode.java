package hu.helper.bang.center.commpost.enums;

/**
 * @author lin
 * @date 2023/03/17
 */
public enum ModuleErrorCode {
    /**
     *
     */
    VOTE_MODULE_ERROR(-2,"新建点赞条目失败，无法往interaction表内初始化指定post id的点赞条目,（插入interaction表操作返回值不为1"),
    CREATE_POST_IFO_ERROR(-3,"内部服务出错，插入数据库post_info时遇到错误（插入操作返回值不为1,无法在post_info表里新建数据)");
    final int code;
    final String desc;
    ModuleErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}

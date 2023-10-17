package hu.helper.bang.center.commpost.enums;




/**
 * @author lin
 * @date 2023/03/14
 */
public enum NotifyTypeEnum {

    /**
     *
     */
    COMMENT(1, "评论"),
    /**
     *
     */
    LIKE(2, "点赞");

    final int code;
    final String desc;


    NotifyTypeEnum(int code, String desc) {
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

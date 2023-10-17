package hu.helper.bang.center.commpost.enums;

/**
 * @author lin
 * @date 2023/03/14
 */
public enum PostTypeEnum {
    /**
     *
     */
    POST("post"),
    ANON("tree_hole"),
    STUDY("study_material"),
    ACTIVE("active"),
    SECOND("second_hand");
    final String desc;

    PostTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}

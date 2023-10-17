package hu.helper.bang.center.anon.enums;


/**
 * @author lin
 * @date 2023/03/21
 */
public enum AnonElement {
    /**
     *
     */
    RED("匿名用户","/red.png"),
    BLUE("匿名用户","/blue.png"),
    PURPLE("匿名用户","/purple.png"),
    GREEN("匿名用户","/green.png"),
    YELLOW("匿名用户","/yellow.png");

    final String name;
    String photoUrl;

    AnonElement(String name, String photoUrl) {
        this.name = name;
        this.photoUrl = photoUrl;
    }

    public void updatePhotoUrl(String prefix) {
        this.photoUrl = prefix + this.photoUrl;
    }


}

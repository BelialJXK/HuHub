package hu.helper.bang.center.user.controller.respond;

import lombok.Data;

/**
 * @Author : Luo Siwei
 * @Date : 2023/2/20 23:46
 * @Description : userVO
 */
@Data
public class UserVO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 角色
     */
    private String role;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像url
     */
    private String avatarUrl;

    /**
     * 性别
     */
    private String sex;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态（被封禁、正常）
     */
    private Integer status;

    /**
     * 额外信息
     */
    private String extra;

}

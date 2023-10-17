package hu.helper.bang.center.user.service.model;

import lombok.Data;

import java.util.Date;

/**
 * 用户DTO
 * @Author : Luo Siwei
 * @Date : 2023/2/11 20:46
 * @Description :
 */
@Data
public class UserDTO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 微信openid
     */
    private String openId;

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

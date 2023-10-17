package hu.helper.bang.center.admin.dao.model;

import lombok.Data;


/**
 * @author WCY
 * @date 2023/04/21
 */

@Data
public class UserInfo {


    private Long id;

    private Long roleId;

    private String openId;

    private String nickname;

    private String avatarUrl;

    private String sex;

    private String phone;

    private String email;

    private Integer status;

    private String extra;

}
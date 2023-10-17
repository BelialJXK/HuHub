package hu.helper.bang.center.user.service;

import hu.helper.bang.center.user.service.model.UserDTO;

/**
 * @Author : Luo Siwei
 * @Date : 2023/2/11 20:58
 * @Description : 用户服务
 */
public interface UserService {
    /**
     * 获取openId
     *
     * @param code
     * @return
     */
    public String getOpenId(String code);

    /**
     * 注册用户
     *
     * @param openId
     * @return
     */
    Long registerUser(String openId);

    /**
     * 检查用户是否存在,存在则返回用户id
     *
     * @param openId
     * @return
     */
    Long checkUserExist(String openId);

    /**
     * 通过id查找用户
     *
     * @param id
     * @return
     */
    UserDTO findUserById(Long id);

    /**
     * 当前用户是否登录
     *
     * @return
     */
    Boolean isCurrentUserLogin();

    /**
     * 获取当前用户信息
     *
     * @return
     */
    UserDTO findCurrentUser();

    /**
     * 当前用户退出登录
     */
    void exitCurrentUser();

    /**
     * 通过id查询用户对应的角色名称
     *
     * @param id
     * @return
     */
    String findRoleNameByUserId(Long id);
}

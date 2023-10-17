package hu.helper.bang.center.admin.service;

import hu.helper.bang.center.admin.dao.model.RoleInfo;
import hu.helper.bang.center.admin.dao.model.UserInfo;

/**
 * @author WCY
 * @date 2023/04/21
 */

public interface UserService {
    /**
     * 根据 ID 查找 UserInfo 对象。
     *
     * @param id 用户的唯一标识符
     * @return 如果找到则返回 UserInfo 对象，否则返回 null
     */
    UserInfo findUserById(Long id);
    /**
     * 更新现有的 UserInfo 对象。
     *
     * @param user 要更新的 UserInfo 对象
     * @return 如果更新成功则返回 true，否则返回 false
     */
    boolean updateUser(UserInfo user);
    /**
     * 根据 ID 查找 RoleInfo 对象。
     *
     * @param id 角色的唯一标识符
     * @return 如果找到则返回 RoleInfo 对象，否则返回 null
     */
    RoleInfo findRoleById(Long id);

}

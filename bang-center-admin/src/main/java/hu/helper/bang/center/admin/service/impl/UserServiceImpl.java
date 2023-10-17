package hu.helper.bang.center.admin.service.impl;

import hu.helper.bang.center.admin.dao.UserMapper;
import hu.helper.bang.center.admin.dao.model.RoleInfo;
import hu.helper.bang.center.admin.dao.model.UserInfo;
import hu.helper.bang.center.admin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author WCY
 * @date 2023/04/21
 */

@Slf4j
@Service("adminUserServiceImpl")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserInfo findUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        return userMapper.findUserById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUser(UserInfo user) {
        userMapper.updateUser(user.getId(), user.getStatus());
        return true;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleInfo findRoleById(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        UserInfo userInfo = userMapper.findUserById(userId);
        if (userInfo == null) {
            throw new IllegalArgumentException("User not found for the given ID");
        }
        return userMapper.findRoleByRoleId(userInfo.getRoleId());
    }


}

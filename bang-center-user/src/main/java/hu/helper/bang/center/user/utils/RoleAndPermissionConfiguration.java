package hu.helper.bang.center.user.utils;

import cn.dev33.satoken.stp.StpInterface;
import hu.helper.bang.center.user.dao.UserMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author : Luo Siwei
 * @Date : 2023/2/21 19:59
 * @Description : 自定义权限验证接口扩展
 */
@Component
public class RoleAndPermissionConfiguration implements StpInterface {
    @Resource
    private UserMapper userMapper;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 根据具体业务逻辑来查询权限
        Long userId = Long.parseLong(loginId.toString());
        Set<String> permissions = userMapper.queryUserPermissionsById(userId);
        return new ArrayList<>(permissions);
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // 根据具体业务逻辑来查询角色
        Long userId = Long.parseLong(loginId.toString());
        Set<String> roles = userMapper.queryUserRolesById(userId);
        return new ArrayList<>(roles);
    }

}

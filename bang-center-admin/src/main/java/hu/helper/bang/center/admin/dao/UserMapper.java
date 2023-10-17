package hu.helper.bang.center.admin.dao;
import hu.helper.bang.center.admin.dao.model.RoleInfo;
import hu.helper.bang.center.admin.dao.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

/**
 * @author WCY
 * @date 2023-04-26
 */

@Mapper
@Component("adminUserMapper")
public interface UserMapper {

    /**
     * 根据用户ID查询用户信息。
     *
     * @param id 用户ID
     * @return UserInfo 用户信息实体类
     */
    @Select("SELECT * FROM bang_center_user_info WHERE id = #{id}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "status", property = "status")
    })
    UserInfo findUserById(@Param("id") Long id);

    /**
     * 更新用户状态。
     *
     * @param id 用户ID
     * @param status 用户状态
     * @return int 受影响的行数
     */
    @Update("UPDATE bang_center_user_info SET status = #{status} WHERE id = #{id}")
    int updateUser(@Param("id") Long id, @Param("status") Integer status);
    /**
     * 根据角色ID查询角色信息。
     *
     * @param roleId 角色ID
     * @return RoleInfo 角色信息实体类
     */
    @Select("SELECT * FROM bang_center_user_role WHERE id = #{roleId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "role_name", property = "roleName"),
    })
    RoleInfo findRoleByRoleId(@Param("roleId") Long roleId);

}

package hu.helper.bang.center.user.dao;

import hu.helper.bang.center.user.dao.model.UserDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * 用户mapper
 *
 * @Author : Luo Siwei
 * @Date : 2023/2/11 20:39
 * @Description :
 */
@Mapper
public interface UserMapper {

    String TABLE = " bang_center_user_info ";

    String TABLE_ROLE = " bang_center_user_role ";

    String TABLE_PERMISSON = " bang_center_user_permission ";

    String FIELD = " id, gmt_create, gmt_modified, role_id, open_id, nickname," +
            "avatar_url,sex,phone,email,`status`,extra ";

    /**
     * 注册用户
     *
     * @param userDO
     * @return Integer
     */
    @Insert({
            "insert into " + TABLE + "(" + FIELD + ") " +
                    "values (now(), now(), #{roleId}, #{openId}, #{nickname}, #{avatarUrl}, null, null, null, #{status}, null) "
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Long registerUser(UserDO userDO);

    /**
     * 是否存在用户
     *
     * @param openId
     * @return
     */
    @Select({
            "select id from " + TABLE +
                    "where open_id = #{openId}"
    })
    Long haveUser(String openId);

    /**
     * 通过id查询用户
     *
     * @param id
     * @return
     */
    @Select({
            "select " + FIELD + " from " + TABLE +
                    "where id = #{id}"
    })
    UserDO queryUserById(Long id);

    /**
     * 通过id查询用户对应的角色名称
     *
     * @param id
     * @return
     */
    @Select({
            "select distinct b.role_name from " + TABLE + " c " +
                    "join " + TABLE_ROLE + " b on c.role_id = b.id " +
                    "WHERE c.id = #{id} " +
                    "  AND c.status = 1"
    })
    Set<String> queryUserRolesById(Long id);

    /**
     * 通过id查询用户对应的权限id
     *
     * @param id
     * @return
     */
    @Select({
            "select distinct p.permission_name " +
                    "from " + TABLE + " u " +
                    "         join " + TABLE_ROLE + " r on u.role_id = r.id " +
                    "         join " + TABLE_PERMISSON + " p on JSON_CONTAINS(r.permissions, CAST(p.id AS CHAR)) " +
                    "where u.id = #{id} " +
                    "  and u.status = 1"
    })
    Set<String> queryUserPermissionsById(Long id);

}

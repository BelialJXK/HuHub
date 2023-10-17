package hu.helper.bang.center.user.dao;

import hu.helper.bang.center.user.dao.model.TestDo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

/**
 * @Author : Luo Siwei
 * @Date : 2023/2/5 16:12
 * @Description : 测试数据库连接
 */

@Mapper
public interface TestMapper {
    String TABLE = " bang_center_test_test ";

    String FIELD = "gmt_create, gmt_modified, test_varchar ";


    /**
     * 插入或更新测试值
     * @param testDo
     * @return
     */
    @Insert({
            "insert into " + TABLE + "( id, " + FIELD + ") " +
                    "values (#{id}, now(), now(), #{testVarchar}) " +
                    "on duplicate key update " +
                    "gmt_modified = now(), " +
                    "test_varchar = #{testVarchar} "
    })
    @Options(useGeneratedKeys = true,keyProperty = "id")
    Integer insertOrUpdateTestInfo(TestDo testDo);
}

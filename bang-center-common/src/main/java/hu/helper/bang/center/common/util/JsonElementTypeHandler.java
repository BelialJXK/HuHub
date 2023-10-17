package hu.helper.bang.center.common.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author lin
 * @date 2023/03/14
 */
@MappedTypes(JsonElement.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class JsonElementTypeHandler extends BaseTypeHandler<JsonElement> {
    /**
     * Json编码，对象 ==> Json字符串
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JsonElement parameter, JdbcType jdbcType) throws SQLException {
        String value = parameter.toString();
        if (jdbcType == null) {
            ps.setObject(i, value);
        } else {
            ps.setObject(i, value, jdbcType.TYPE_CODE);
        }
    }

    /**
     * Json解码，Json字符串 ==> 对象
     */
    @Override
    public JsonElement getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String result = rs.getString(columnName);
        return result == null ? null : JsonParser.parseString(result);
    }

    /**
     * Json解码，Json字符串 ==> 对象
     */
    @Override
    public JsonElement getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String result = rs.getString(columnIndex);
        return result == null ? null : JsonParser.parseString(result);
    }

    /**
     * Json解码，Json字符串 ==> 对象
     */
    @Override
    public JsonElement getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String result = cs.getString(columnIndex);
        return result == null ? null : JsonParser.parseString(result);
    }

}

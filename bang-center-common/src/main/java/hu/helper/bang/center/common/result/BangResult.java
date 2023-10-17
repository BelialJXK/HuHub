package hu.helper.bang.center.common.result;

import org.apache.http.HttpStatus;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author : Luo Siwei
 * @Date : 2023/1/27 21:02
 * @Description : 帮帮网结果返回格式
 */
public class BangResult extends HashMap<String, Object> {

    public BangResult() {
        put("code", HttpStatus.SC_OK);
        put("msg", "success");
    }

    public static BangResult error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static BangResult error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static BangResult error(int code, String msg) {
        BangResult r = new BangResult();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static BangResult ok(String msg) {
        BangResult r = new BangResult();
        r.put("msg", msg);
        return r;
    }
    public static BangResult ok(Object object) {
        BangResult r = new BangResult();
        r.put("data", object);
        return r;
    }

    public static BangResult ok(Map<String, Object> map) {
        BangResult r = new BangResult();
        r.putAll(map);
        return r;
    }

    public static BangResult ok() {
        return new BangResult();
    }



    @Override
    public BangResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}


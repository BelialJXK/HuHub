package hu.helper.bang.center.common.util;

import hu.helper.bang.center.common.exception.BangException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * @Author : Luo Siwei
 * @Date : 2023/1/27 21:02
 * @Description : 帮帮网判断工具
 */
public class BangPreconditions {

    /**
     * 检查对象是否为null
     * @param object
     */
    public static void checkNotNull(Object object) {
        if (object == null){
            throw new BangException("对象不能为空");
        }
    }

    /**
     * 检查字符串不为空
     * @param str
     */
    public static void checkNotNull(String str){
        if (StringUtils.isBlank(str)){
            throw new BangException("对象不能为空");
        }
    }


    /**
     * 检查集合不为空
     */
    public static void checkNotEmpty(Collection<?> collection){
        if (CollectionUtils.isEmpty(collection)){
            throw new BangException("集合不能为空");
        }
    }

    /**
     * 检查是否为正数
     * @param value
     */
    public static void checkPositive(Integer value){
        checkNotNull(value);
        if (value <= 0){
            throw new BangException("值不为正数");
        }
    }

    /**
     * 检查表达式是否为true
     * @param expression
     */
    public static void checkTrue(boolean expression){
        if (!expression){
            throw new BangException("表达式不为true");
        }
    }
}

package hu.helper.bang.center.commpost.utils;

import hu.helper.bang.center.commpost.enums.PostTypeEnum;

/**
 * @author lin
 * @date 2023/03/17
 */
public class  UtilsTools {
    /**
     * @param typeValue post info中要显示的类型
     * @return {@link Boolean}
     */
    public static Boolean judgeType(String typeValue){
        for(PostTypeEnum postTypeEnum : PostTypeEnum.values()){
            if(typeValue.equals(postTypeEnum.getDesc())){
                return true;
            }
        }
        return false;
    }
}

package io.github.anshily.ipdb.core.util;

import com.alibaba.fastjson.JSON;

public class StringUtil {
    public static String getString(Object object){
        return JSON.toJSONString(object);
    }
}

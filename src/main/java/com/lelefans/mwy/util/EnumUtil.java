package com.lelefans.mwy.util;

import com.lelefans.mwy.enums.CodeEnum;

public class EnumUtil {
    public static <T extends CodeEnum>T getEnum(Class<T> tClass, Integer code){
        T[] enumConstants = tClass.getEnumConstants();
        for(T t : enumConstants){
            if(Integer.valueOf(t.getCode()).equals(code)){
                return t;
            }
        }
        return null;
    }
}

package com.lelefans.mwy.util;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.lelefans.mwy.enums.CodeEnum;

import java.io.IOException;
import java.lang.reflect.Type;

public class SimpleSerializer implements ObjectSerializer, ObjectDeserializer {
    @Override
    public void write(JSONSerializer jsonSerializer, Object o, Object filedName, Type type, int i) throws IOException {
        if (o instanceof Double || o instanceof Float) {
            jsonSerializer.write(((Number) o).intValue());
        } else if (o instanceof CodeEnum) {
            jsonSerializer.write(((CodeEnum) o).getCode());
        } else {
            jsonSerializer.write(o);
        }
    }

    @Override
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object o) {

        if(CodeEnum.class.isAssignableFrom((Class<?>) type)){
            Object parse = defaultJSONParser.parse();
            if(parse instanceof Integer){
                return (T) EnumUtil.getEnum((Class)type,(Integer) parse);
            }
            return null;
        }else {
            return defaultJSONParser.parseObject(type, o);
        }
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}

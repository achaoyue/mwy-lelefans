package com.lelefans.mwy.util;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.lelefans.mwy.enums.CodeEnum;

import java.io.IOException;
import java.lang.reflect.Type;

public class SimpleSerializer implements ObjectSerializer {
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
}

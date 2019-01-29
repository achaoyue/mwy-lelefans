package com.lelefans.mwy.game.kdou;

import com.alibaba.fastjson.annotation.JSONField;
import com.lelefans.mwy.util.SimpleSerializer;
import lombok.Data;

@Data
public class Point {
    /**
     * x坐标
     */
    @JSONField(serializeUsing = SimpleSerializer.class)
    private double x;
    /**
     * y坐标
     */
    @JSONField(serializeUsing = SimpleSerializer.class)
    private double y;
}

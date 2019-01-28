package com.lelefans.mwy;

import java.util.HashMap;
import java.util.Map;

public class TestMap {
    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        map.put("aaa","aaa");
        map.put("bbb","bbb");
        map.put("ccc","ccc");
        map.put("ddd","ddd");

        map.entrySet().stream().filter(e->e.getKey().equals("aaa")).forEach(e->map.remove(e.getKey()));

        System.out.println(map);
    }
}

package com.guo.analyze.crawler.common;


import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * 存储清洗的数据
 */

@ToString
public class DataSet {
    /**
     * data把DOM解析，清洗之后存储的数据
     */
    private Map<String,Object> data = new HashMap<>();

    public void putData(String key,Object value){
        this.data.put(key,value);
    }

    public Object getData(String key) {
        return this.data.get(key);
    }
    public Map<String,Object>getData(){
        return new HashMap<>(this.data);
    }
}

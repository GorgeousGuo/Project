package com.guo.analyze.crawler.pipeline;

import com.guo.analyze.crawler.common.Page;

import java.util.Map;

public class ConsolePipeline implements Pipeline{
    @Override
    public void pipeline(final Page page) {
        Map<String,Object> data = page.getDataSet().getData();
        //存储
        System.out.println(data);
    }
}

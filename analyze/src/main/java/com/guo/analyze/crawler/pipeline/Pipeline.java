package com.guo.analyze.crawler.pipeline;

import com.guo.analyze.crawler.common.Page;

public interface Pipeline {
    //管道处理page中的数据
    void pipeline(final Page page);
}

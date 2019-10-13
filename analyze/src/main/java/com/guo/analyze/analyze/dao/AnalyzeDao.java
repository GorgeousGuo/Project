package com.guo.analyze.analyze.dao;

import com.guo.analyze.analyze.entity.PoetryInfo;
import com.guo.analyze.analyze.model.AuthorCount;
import com.guo.analyze.analyze.model.DynastyCount;


import java.util.List;

public interface AnalyzeDao {
    /**
     *分析朝代的词数
     */

    List<DynastyCount> analyzeDynastyCount();
    /**
     * 查询所有的诗文，提供给业务层进行分析
      */
    List<PoetryInfo> queryAllPoetry();


    List<AuthorCount> analyzeAuthorCount();

}

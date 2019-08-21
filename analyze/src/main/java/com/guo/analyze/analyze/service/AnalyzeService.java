package com.guo.analyze.analyze.service;

import com.guo.analyze.analyze.model.AuthorCount;
import com.guo.analyze.analyze.model.DynastyCount;

import com.guo.analyze.analyze.model.WordCount;
import org.nlpcn.commons.lang.util.WordAlert;

import java.util.List;

public interface AnalyzeService {

    /**
     *分析朝代的词数
     */
    List<DynastyCount> analyzeDynastyCount();
    /**\
     *词云分析
     */
    List<WordCount> analyzeWordCloud();
    /**
     *分析作者的词数
     */
    List<AuthorCount> analyzeAuthorCount();
}

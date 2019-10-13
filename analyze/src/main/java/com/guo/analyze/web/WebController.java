package com.guo.analyze.web;

import com.google.gson.Gson;
import com.guo.analyze.analyze.model.AuthorCount;
import com.guo.analyze.analyze.model.DynastyCount;
import com.guo.analyze.analyze.model.WordCount;
import com.guo.analyze.analyze.service.AnalyzeService;
import spark.ResponseTransformer;
import spark.Spark;
import java.util.List;

/*
* Web API
* 1·Sparkjava 框架完成Web API 开发
* */

public class WebController {

    private final AnalyzeService analyzeService;
    public WebController(AnalyzeService analyzeService) {
        this.analyzeService = analyzeService;
    }


    //http://127.0.0.1:4567/
    // ->/analyze/DynastyCount
    public List<DynastyCount> analyzeDynastyCount(){
        return analyzeService.analyzeDynastyCount();
    }
    //http://127.0.0.1:4567/
    // ->/analyze/Word_Cloud
    public List<WordCount>analyzeWordCloud(){
        return analyzeService.analyzeWordCloud();
    }
    //http://127.0.0.1:4567/
    // ->/analyze/Author_Cloud
    public List<AuthorCount>analyzeAuthorCount(){
        return analyzeService.analyzeAuthorCount();
    }



    //运行Web
   public void launch() {
        ResponseTransformer transformer = new JSONResponseTransformer();
        //src/main/resources/static
       //前端静态文件目录
        Spark.staticFileLocation("/static");
        //服务端接口
        Spark.get("/analyze/dynasty_count",
                ((request, response) -> analyzeDynastyCount()),transformer);
        Spark.get("/analyze/word_cloud",
                ((request, response) -> analyzeWordCloud()),transformer);
        Spark.get("/analyze/author_count",
                ((request, response) ->analyzeAuthorCount()),transformer);
    }
    public static class JSONResponseTransformer implements ResponseTransformer {
        //Object -> String
       private Gson gson = new Gson();
        @Override
        public String render(Object o) throws Exception {
            return gson.toJson(o);
        }
    }
}

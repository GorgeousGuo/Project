package com.guo.analyze.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.guo.analyze.analyze.dao.AnalyzeDao;
import com.guo.analyze.analyze.dao.impl.AnalyzeDomImpl;
import com.guo.analyze.analyze.service.AnalyzeService;
import com.guo.analyze.analyze.service.imple.AnalyzeServiceImpl;
import com.guo.analyze.crawler.Crawler;
import com.guo.analyze.crawler.common.Page;
import com.guo.analyze.crawler.parse.DataPageParse;
import com.guo.analyze.crawler.parse.DocumentParse;
import com.guo.analyze.crawler.pipeline.ConsolePipeline;
import com.guo.analyze.crawler.pipeline.DatabasePipeline;
import com.guo.analyze.web.WebController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public final class ObjectFactory {

    private static final  ObjectFactory instance = new ObjectFactory();

    private final Logger logger = LoggerFactory.getLogger(ObjectFactory.class);

    private  final Map<Class,Object> objectHashMap = new HashMap<>();
    private ObjectFactory(){
        //1·初始化配置对象
        initConfigProperties();
        //2·创建数据源对象
        initDataSource();
        //3·爬虫对象
        initCrawler();
        //4·Web对象
        initWebController();

        //5·对象清单打印输出
        printObjectList();
    }

    private void initWebController() {
        DataSource dataSource = getObject(DataSource.class);
        AnalyzeDao analyzeDao = new AnalyzeDomImpl(dataSource);
        AnalyzeService analyzeService = new AnalyzeServiceImpl(analyzeDao);
        WebController webController = new WebController(analyzeService);
        objectHashMap.put(WebController.class,webController);
    }

    private void initCrawler(){
        ConfigProperties configProperties = getObject(ConfigProperties.class);
        DataSource dataSource = getObject(DataSource.class);
        final Page page = new Page(
                configProperties.getCrawlerBase(),
                configProperties.getCrawlerPath(),
                configProperties.isCrawlerDetail()
        );
        Crawler crawler = new Crawler();
        crawler.addParse(new DocumentParse());
        crawler.addParse(new DataPageParse());
        if(configProperties.isEnableConsole()){
            crawler.addPipeline(new ConsolePipeline());
        }
        crawler.addPipeline(new DatabasePipeline(dataSource));
        crawler.addPage(page);
        objectHashMap.put(Crawler.class,crawler);
    }
    private void initDataSource(){
        ConfigProperties configProperties = getObject(ConfigProperties.class);
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(configProperties.getDbUsername());
        dataSource.setPassword(configProperties.getDbPassword());
        dataSource.setDriverClassName(configProperties.getDbDriverClass());
        dataSource.setUrl(configProperties.getDbUrl());
        objectHashMap.put(DataSource.class,dataSource);
    }

    private void initConfigProperties(){
        ConfigProperties configProperties = new ConfigProperties();
        objectHashMap.put(ConfigProperties.class,configProperties);
        logger.info("CongProperties info:\n{}",configProperties.toString());
    }

    public  <T> T getObject (Class classz){
        if(!objectHashMap.containsKey(classz)){
            throw new  IllegalArgumentException("Class"+classz.getName()+"not found Object!");
        }
        return (T) objectHashMap.get(classz);
    }

    public static ObjectFactory getInstance(){
        return instance;
    }

    private void printObjectList(){
        logger.info("=======ObjectFactor List==========");
        for(Map.Entry<Class, Object>entry:objectHashMap.entrySet()){
            logger.info(String.format("[%-5s] ==>[%s]",
                    entry.getKey().getCanonicalName(),
                    entry.getValue().getClass().getCanonicalName()));
        }
        logger.info("=========================");
    }

}

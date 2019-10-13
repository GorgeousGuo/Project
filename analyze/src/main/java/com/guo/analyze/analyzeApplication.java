package com.guo.analyze;
import com.guo.analyze.config.ObjectFactory;
import com.guo.analyze.crawler.Crawler;
import com.guo.analyze.web.WebController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class analyzeApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(analyzeApplication.class);
    public static void main(String[] args ) {
        WebController webController = ObjectFactory.getInstance().getObject(WebController.class);
        //运行了web服务，提供接口
        LOGGER.info("Web Server launch ...");
        webController.launch();
        //启动爬虫

        if(args.length ==1 && args[0].equals("run-crawler")) {
            Crawler crawler = ObjectFactory.getInstance().getObject(Crawler.class);
            LOGGER.info("Crawler started ...");
            crawler.start();
        }
    }
}

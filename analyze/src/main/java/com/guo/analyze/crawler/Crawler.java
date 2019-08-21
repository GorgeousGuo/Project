package com.guo.analyze.crawler;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.guo.analyze.crawler.common.Page;
import com.guo.analyze.crawler.parse.Parse;
import com.guo.analyze.crawler.pipeline.Pipeline;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class Crawler {

    private final Logger logger = LoggerFactory.getLogger(Crawler.class);
    /**
     * 放置文档页面（超链接）
     * 放置详情页面（数据）
     * 未被采集和解析的页面
     * Page htmlPage dataSet
     */
    private final Queue<Page> docQueue = new LinkedBlockingQueue<>();
   /**
     * 放置详情页面(处理完成，数据在dataSet)
    */
   private  final Queue<Page> detailQueue = new LinkedBlockingQueue<>();

    /**
     * 采集器
     */
    private final WebClient webClient;

    /**
     * 所有的解析器
     */

    private final List<Parse> parseList = new LinkedList<>();
    /**
     *所有的清洗器（管道）
     */

    private final List<Pipeline> pipelineList = new LinkedList<>();

    /**
     *线程调度器
     */
    private final ExecutorService executorService;
    public Crawler(){
        this.webClient= new WebClient(BrowserVersion.CHROME);
        this.webClient.getOptions().setJavaScriptEnabled(false);
        this.executorService = Executors.newFixedThreadPool(8, new ThreadFactory(){
            private final AtomicInteger id = new AtomicInteger(0);
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("Crawler-Thread-"+id.getAndIncrement());
                return thread;
            }
        });
    }


    //爬虫的启动
    public void start(){
        //爬取
        //解析
        //清洗
        this.executorService.submit(this::parse);
        this.executorService.submit(this::pipeline);
    }

    private void parse(){
        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("Parse occur exception{}.",e.getMessage());
            }
            final Page page = this.docQueue.poll();
            if ( page == null ){
                continue;
            }
            //base path detail htmlpage
            this.executorService.submit(() -> {
                try {
                    //采集
                    HtmlPage htmlPage = this.webClient.getPage(page.getUrl());
                    page.setHtmlPage(htmlPage);
                    for(Parse parse:this.parseList){
                        parse.parse(page);
                    }
                    if(page.isDetail()){
                      this.detailQueue.add(page);
                    }else{
                        Iterator<Page> iterator = page.getSubPage().iterator();
                        while (iterator.hasNext()) {
                            Page subPage = iterator.next();
                            this.docQueue.add(subPage);
                            iterator.remove();
                        }
                    }
                } catch (IOException e) {
                    logger.error("Parse task occur exception{}.",e.getMessage());
                }
            });
        }
    }

    private void pipeline(){
        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("Parse occur exception{}.",e.getMessage());
            }
            final Page page = this.detailQueue.poll();
            if(page==null){
                continue;
            }
           this.executorService.submit(() -> {
                for (Pipeline pipeline:this.pipelineList){
                    pipeline.pipeline(page);
                }
           });
        }
    }

  public void addPage(Page page){
        this.docQueue.add(page);
  }
  public void addParse(Parse parse){
        this.parseList.add(parse);
    }
  public void addPipeline(Pipeline pipeline){
        this.pipelineList.add(pipeline);
    }

    //停止爬虫
    public void stop(){
        if ( this.executorService!= null && !this.executorService.isShutdown()){
            this.executorService.shutdown();
        }
        logger.info("Crawler stopped ...");
    }

}

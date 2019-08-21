package com.guo.analyze.crawler.common;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import javafx.scene.effect.SepiaTone;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;


@Data
public  class Page {

    /**
     * 数据网站的根地址
     * eg：
     */

    private final String base;

    /**
     * 具体网页路径
     * eg：
     */
    private final String path;

    /**
    * 网页的Dom对象
    */
    private HtmlPage htmlPage;

    /**
     * 标志网页是否是详情页
     */
    private final boolean detail;

    /**
     * 子页面对象集合
     */
    private Set<Page> subPage = new HashSet<>();
    /**
     * 数据对象
     */
    private DataSet dataSet = new DataSet();

    public String getUrl(){
        return this.base+this.path;
    }


}

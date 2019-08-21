package com.guo.analyze.analyze.entity;

import lombok.Data;

@Data
public class PoetryInfo {
    /*
    标题
    作者
    朝代
    正文
    * */
    private String title;
    private String dynasty;
    private String author;
    private String content;
}

package com.guo.analyze.analyze.dao.impl;

import com.guo.analyze.analyze.dao.AnalyzeDao;
import com.guo.analyze.analyze.entity.PoetryInfo;
import com.guo.analyze.analyze.model.AuthorCount;
import com.guo.analyze.analyze.model.DynastyCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnalyzeDomImpl implements AnalyzeDao {
    private final Logger logger = LoggerFactory.getLogger(AnalyzeDomImpl.class);
    private final DataSource dataSource;
    public AnalyzeDomImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }
    @Override
    public List<DynastyCount> analyzeDynastyCount() {
        List<DynastyCount> datas = new ArrayList<>();
        //try()自动关闭
        String sql = "select count(*) as count,dynasty from poetry_info group by dynasty;";
        try (Connection connection=dataSource.getConnection();
           PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()
        ){
            while (rs.next()){
                DynastyCount dynastyCount = new DynastyCount();
                dynastyCount.setDynasty(rs.getString("dynasty"));
                dynastyCount.setCount(rs.getInt("count"));
                datas.add(dynastyCount);
            }
        } catch (SQLException e) {
            logger.error("Database query occur exception{}.",e.getMessage());
        }
        return datas;
    }

    @Override
    //PoetryInfo
    public List<PoetryInfo> queryAllPoetry() {
        List<PoetryInfo> datas = new ArrayList<>();
        //try()自动关闭
        String sql = "select title,dynasty,author,content from poetry_info;";
        try (Connection connection=dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()
        ){
            while (rs.next()){
                PoetryInfo poetry = new PoetryInfo();
                poetry.setTitle(rs.getString("title"));
                poetry.setDynasty(rs.getString("dynasty"));
                poetry.setAuthor(rs.getString("author"));
                poetry.setContent(rs.getString("content"));
                //该方法太麻烦优化方法：ORM mybatis Spring-Data-JDBC hibernate JOOQ TopLink dbUtils
                datas.add(poetry);
            }
        } catch (SQLException e) {
            logger.error("Database query occur exception{}.",e.getMessage());
        }
        return datas;
    }

    @Override
    public List<AuthorCount> analyzeAuthorCount() {
        List<AuthorCount> datas = new ArrayList<>();
        //try()自动关闭
        String sql = "select count(*) as count,author from poetry_info group by author;";
        try (Connection connection=dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()
        ){
            while (rs.next()){
                AuthorCount authorCount = new AuthorCount();
                authorCount.setAuthor(rs.getString("author"));
                authorCount.setCount(rs.getInt("count"));
                datas.add(authorCount);
            }
        } catch (SQLException e) {
            logger.error("Database query occur exception{}.",e.getMessage());
        }
        return datas;
    }
}



package com.guo.analyze.analyze.service.imple;

import com.guo.analyze.analyze.dao.AnalyzeDao;
import com.guo.analyze.analyze.entity.PoetryInfo;
import com.guo.analyze.analyze.model.AuthorCount;
import com.guo.analyze.analyze.model.DynastyCount;
import com.guo.analyze.analyze.model.WordCount;
import com.guo.analyze.analyze.service.AnalyzeService;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;

import java.util.*;

public class AnalyzeServiceImpl implements AnalyzeService {
  private  final  AnalyzeDao analyzeDao;

    public AnalyzeServiceImpl(AnalyzeDao analyzeDao) {
        this.analyzeDao = analyzeDao;
    }

    private static int compare(DynastyCount o1, DynastyCount o2) {
        return o1.getCount().compareTo(-o2.getCount());
    }


    @Override
    public List<DynastyCount> analyzeDynastyCount() {
//        //此处结果并未排序
//        //排序方式：1·DAO层SQL排序
//        //          2·Service层进行数据排序：方法如下：
        List<DynastyCount>dynastyCounts = analyzeDao.analyzeDynastyCount();
        //此处为升序
        dynastyCounts.sort(Comparator.comparing(DynastyCount::getCount));
        return dynastyCounts;
    }

    @Override
    public List<WordCount> analyzeWordCloud() {
        //1·查询出所有数据
        //2·取出 title content
        //3·（ansj _seg参考文档）分词/过滤：w，null,空，len<2,
        //4·统计k-v  k：词 v：频
        Map<String,Integer>map = new HashMap<>();
        List<PoetryInfo> poetryInfos = analyzeDao.queryAllPoetry();
        for (PoetryInfo poetryInfo:poetryInfos){
            List<Term>terms = new ArrayList<>();
            String title = poetryInfo.getTitle();
            String content = poetryInfo.getContent();
           terms.addAll(NlpAnalysis.parse(title).getTerms()) ;
           terms.addAll(NlpAnalysis.parse(content).getTerms());
           //过滤
            Iterator<Term> iterator= terms.iterator();
            while (iterator.hasNext()){
                Term term = iterator.next();
                //词性的过滤
                if ( term.getNatureStr() == null || term.getNatureStr().equals("W")){
                    iterator.remove();
                    continue;
                }
                //词的过滤
                if(term.getRealName().length()<2){
                    iterator.remove();
                    continue;
                }
                //统计
                String realName = term.getRealName();
                int count;
                if(map.containsKey(realName)){
                   count= map.get(realName)+1;
                }else{
                    count = 1;
                }
                map.put(realName,count);
            }
        }
        List<WordCount> wordCounts = new ArrayList<>();
        for (Map.Entry<String,Integer>entry:map.entrySet()){
            WordCount wordCount = new WordCount();
            wordCount.setCount(entry.getValue());
            wordCount.setWord(entry.getKey());
            wordCounts.add(wordCount);
        }
        return wordCounts;
    }

    @Override
    public List<AuthorCount> analyzeAuthorCount() {
        List<AuthorCount>authorCounts = analyzeDao.analyzeAuthorCount();
        //此处为升序
        authorCounts.sort(Comparator.comparing(AuthorCount::getCount));
        return authorCounts;
    }


}

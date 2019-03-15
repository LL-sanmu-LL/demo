package com.example.demo.elasticsearch;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.cluster.Health;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchResult.Hit;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.mapping.PutMapping;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestElasticsearch {
  @Resource
  private JestItemServiceImpl jestItemService;
  private String indexName = "article";
  private String typeName = "article";
  @Resource
  private JestClient jestClient;

  @Test
  public void testMethodB() throws Exception{
    Health health = new Health.Builder().build();
    JestResult result = jestClient.execute(health);
    System.out.println(result.getJsonString());
  }
  @Test
  public void createIndex(){
    try {
      JestResult jestResult = jestClient.execute(new CreateIndex.Builder(indexName).build());
      System.out.println("createIndex:{}" + jestResult.isSucceeded());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  /**
   * 删除索引
   * @throws Exception
   */
  @Test
  public void deleteIndex() throws Exception {
    JestResult jr = jestClient.execute(new DeleteIndex.Builder(indexName).build());
    boolean result = jr.isSucceeded();
    System.out.println(result);
  }
  @Test
  public void createIndexMapping() throws Exception {
    String source = "{\"" + typeName + "\":{\"properties\":{"
        + "\"author\":{\"type\":\"text\",\"index\":\"false\"}"
        + ",\"title\":{\"type\":\"text\"}"
        + ",\"content\":{\"type\":\"text\"}"
        + ",\"price\":{\"type\":\"text\"}"
        + ",\"view\":{\"type\":\"text\"}"
        + ",\"tag\":{\"type\":\"text\"}"
        + ",\"date\":{\"type\":\"date\",\"format\":\"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis\"}"
        + "}}}";
    System.out.println(source);

    PutMapping putMapping = new PutMapping.Builder(indexName, typeName, source).build();
    JestResult jr = jestClient.execute(putMapping);
    System.out.println(jr.isSucceeded());
  }
  /**
   * 插入多个
   * @throws IOException
   */
  @Test
  public void insertMultiple() throws IOException {
    BookDoc csdnBlog1=new BookDoc();
    csdnBlog1.setAuthor("AAAA");
    csdnBlog1.setTitle("中国获租巴基斯坦瓜达尔港2000亩土地 为期43年");
    csdnBlog1.setContent("据了解，瓜达尔港务局于今年6月完成了1500亩土地的征收工作，另外500亩的征收工作也将很快完成");
//    csdnBlog1.setDate(new Date());
    csdnBlog1.setView("100");
    csdnBlog1.setTag("JAVA,ANDROID,C++,LINUX");


    BookDoc csdnBlog2=new BookDoc();
    csdnBlog2.setAuthor("BBBB");
    csdnBlog2.setTitle("中国获租巴基斯坦瓜达尔港2000亩土地 为期43年");
    csdnBlog2.setContent("据了解，瓜达尔港务局于今年6月完成了1500亩土地的征收工作，另外500亩的征收工作也将很快完成");
//    csdnBlog2.setDate(new Date());
    csdnBlog2.setView("200");
    csdnBlog2.setTag("JAVA,ANDROID,C++,LINUX");


    BookDoc csdnBlog3=new BookDoc();
    csdnBlog3.setAuthor("CCCC");
    csdnBlog3.setTitle("中国获租巴基斯坦瓜达尔港2000亩土地 为期43年");
    csdnBlog3.setContent("据了解，瓜达尔港务局于今年6月完成了1500亩土地的征收工作，另外500亩的征收工作也将很快完成");
//    csdnBlog3.setDate(new Date());
    csdnBlog3.setView("300");
    csdnBlog3.setTag("JAVA,ANDROID,C++,LINUX");

    BookDoc csdnBlog4=new BookDoc();
    csdnBlog4.setAuthor("CCCC");
    csdnBlog4.setTitle("中国获租巴基斯坦瓜达尔港2000亩土地 为期43年");
    csdnBlog4.setContent("据了解，瓜达尔港务局于今年6月完成了1500亩土地的征收工作，另外500亩的征收工作也将很快完成");
//    csdnBlog4.setDate(new Date());
    csdnBlog4.setView("400");
    csdnBlog4.setTag("JAVA,ANDROID,C++,LINUX");


    Index index1 = new Index.Builder(csdnBlog1).index("article").type("article").build();
    Index index2 = new Index.Builder(csdnBlog2).index("article").type("article").build();
    Index index3 = new Index.Builder(csdnBlog3).index("article").type("article").build();
    Index index4 = new Index.Builder(csdnBlog4).index("article").type("article").build();

    JestResult jestResult1 = jestClient.execute(index1);
    System.out.println(jestResult1.getJsonString());
    JestResult jestResult2 = jestClient.execute(index2);
    System.out.println(jestResult2.getJsonString());
    JestResult jestResult3 = jestClient.execute(index3);
    System.out.println(jestResult3.getJsonString());
    JestResult jestResult4 = jestClient.execute(index3);
    System.out.println(jestResult4.getJsonString());
  }
  /**
   * 单值完全匹配查询
   * @throws Exception
   */
  @Test
  public void termQuery() throws Exception {
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    QueryBuilder queryBuilder = QueryBuilders
        .termQuery("tag", "java");//单值完全匹配查询

    searchSourceBuilder.query(queryBuilder);
    searchSourceBuilder.size(10);
    searchSourceBuilder.from(0);
    String query = searchSourceBuilder.toString();
    System.out.println(query);


    Search search = new Search.Builder(query)
        .addIndex(indexName)
        .addType(typeName)
        .build();
    SearchResult result = jestClient.execute(search);

    List<Hit<Object, Void>> hits = result.getHits(Object.class);
    System.out.println("Size:" + hits.size());
    for (Hit<Object, Void> hit : hits) {
      Object news = hit.source;
      System.out.println(hit.highlight);
      System.out.println(news.toString());
    }
  }
  @Test
  public void highlightSearch() throws IOException {
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(QueryBuilders.termQuery("tag", "java"));
    HighlightBuilder highlightBuilder = new HighlightBuilder();
    highlightBuilder.field("tag");//高亮title
    highlightBuilder.preTags("<em>").postTags("</em>");//高亮标签
    highlightBuilder.fragmentSize(500);//高亮内容长度
    searchSourceBuilder.highlighter(highlightBuilder);
    Search search = new Search.Builder(searchSourceBuilder.toString())
        .addIndex(indexName)
        .addType(typeName)
        .build();


    SearchResult result = jestClient.execute(search);
    System.out.println("查询结果"+result.getJsonString());
    System.out.println("本次查询共查到："+result.getTotal()+"篇文章！");
    List<Hit<BookDoc,Void>> hits = result.getHits(BookDoc.class);
    System.out.println(hits.size());
    for (Hit<BookDoc, Void> hit : hits) {
      BookDoc source = hit.source;
      System.out.println(source.toString());
      //获取高亮后的内容
      Map<String, List<String>> highlight = hit.highlight;
      System.out.println(highlight);

    }
  }
}

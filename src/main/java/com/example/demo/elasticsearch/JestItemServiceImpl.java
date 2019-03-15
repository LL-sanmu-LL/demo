package com.example.demo.elasticsearch;

import com.google.common.collect.Lists;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchResult.Hit;
import io.searchbox.core.Update;
import io.searchbox.indices.CreateIndex;
import java.io.IOException;
import java.util.List;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@ConfigurationProperties(prefix = "spring.elasticsearch")
public class JestItemServiceImpl {

  private static final Logger LOG = LoggerFactory.getLogger(JestItemServiceImpl.class);

  //indices 名必须小写
  private String indices;
  // type 名必须小写
  private String type;
  private JestClient client;

  public void setIndices(String indices) {
    this.indices = indices;
  }

  public void setType(String type) {
    this.type = type;
  }

  public JestItemServiceImpl(JestClient client) {
    this.client = client;
  }

  /**
   * 插入
   *
   * @param doc 文档
   * @return 是否插入成功
   */
  public boolean insertOrUpdate(UserDoc doc) {
    try {
      DocumentResult result = client.execute(new Index.Builder(doc)
        .index(indices)
        .type(type)
//          .id(doc.getId())
//        .refresh(true)
        .build());
      if (!result.isSucceeded()) {
        LOG.debug("insert or update jest failed,errorMessage = {}", result.getErrorMessage());
      }
      return result.isSucceeded();
    } catch (Exception e) {
      throw new RuntimeException("insert exception" + e.getMessage(), e);
    }
  }

  /**
   * 更新
   *
   * @return 是否更新成功
   */
  public boolean update(UserDoc doc) {
    try {
      DocumentResult result = client.execute(new Update.Builder(doc.toString())
        .index(indices)
        .type(type)
        .id(doc.getId())
        .refresh(true)
        .build());
      if (!result.isSucceeded()) {
        LOG.debug("update jest failed,errorMessage = {}", result.getErrorMessage());
      }
      return result.isSucceeded();
    } catch (Exception e) {
      throw new RuntimeException("update exception" + e.getMessage(), e);
    }
  }

  /**
   * 删除
   *
   * @param id 文档id
   * @return 是否执行成功
   */
  public boolean delete(String id) {
    try {
      DocumentResult result = client.execute(new Delete.Builder(id)
        .index(indices)
        .type(type)
        .build());
      if (!result.isSucceeded()) {
        LOG.debug("delete jest failed,errorMessage = {}", result.getErrorMessage());
      }
      return result.isSucceeded();
    } catch (Exception e) {
      throw new RuntimeException("delete exception" + e.getMessage(), e);
    }
  }

  /**
   * 根据ID查询
   *
   * @param id id
   * @return 文档
   */
  public UserDoc searchById(String id) {
    try {
      DocumentResult result = client.execute(new Get.Builder(indices, id)
        .type(type)
        .build());
      return result.getSourceAsObject(UserDoc.class);
    } catch (Exception e) {
      throw new RuntimeException("searchById exception" + e.getMessage(), e);
    }
  }

  /**
   * 条件查询
   *
   * @param criterias 条件列表
   * @return 结果集
   */
  public List<UserDoc> search(List<JestCriteria> criterias, Integer size) {
    try {
      SearchResult result = client
        .execute(new Search.Builder(buildSearch(criterias, size).toString())
          // multiple index or types can be added.
          .addIndex(indices)
          .addType(type)
          .build());
      List<UserDoc> js = Lists.newArrayList();

      List<Hit<UserDoc, Void>> hits = result.getHits(UserDoc.class);
      for (Hit<UserDoc, Void> hit : hits) {
        UserDoc UserDoc = hit.source;
        js.add(UserDoc);
      }
      return js;
    } catch (Exception e) {
      throw new RuntimeException("search exception" + e.getMessage(), e);
    }
  }

  public List<UserDoc> search(String query) {
    try {
      SearchResult result = client
        .execute(new Search.Builder(query)
          .addIndex(indices)
          .addType(type)
          .build());
      List<UserDoc> js = Lists.newArrayList();

      List<Hit<UserDoc, Void>> hits = result.getHits(UserDoc.class);
      for (Hit<UserDoc, Void> hit : hits) {
        UserDoc UserDoc = hit.source;
        js.add(UserDoc);
      }
      return js;
    } catch (Exception e) {
      throw new RuntimeException("search exception" + e.getMessage(), e);
    }
  }

  private SearchSourceBuilder buildSearch(List<JestCriteria> criterias, Integer size) {
    //指定查询的库表
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    if (size != null) {
      searchSourceBuilder.size(size);
    }
    if (criterias != null && !criterias.isEmpty()) {
      //构建查询条件必须嵌入filter中！
      BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
      for (JestCriteria c : criterias) {
        boolQueryBuilder.filter(QueryBuilders.termQuery(c.getFieldName(), c.getFieldValue()));
      }

      searchSourceBuilder.query(boolQueryBuilder);
    }
    return searchSourceBuilder;
  }

  /**
   * 条件删除 ，ElasticSearch V5.1 以上可用
   *
   * @param criterias 条件
   * @return 删除的document数量
   */
  public int deleteByQuery(List<JestCriteria> criterias, Integer size) {
    try {
      JestResult result = client
        .execute(new DeleteByQuery.Builder(buildSearch(criterias, size).toString())
          .addIndex(indices)
          .addType(type)
          .build());

      return result.getJsonObject().get("deleted").getAsInt();
    } catch (Exception e) {
      throw new RuntimeException("deleteByQuery exception" + e.getMessage(), e);
    }
  }

  /**
   * 创建索引
   */
  public void methodA(){
    try {
      JestResult jestResult = client.execute(new CreateIndex.Builder("b2b").build());
      System.out.println("createIndex:{}" + jestResult.isSucceeded());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
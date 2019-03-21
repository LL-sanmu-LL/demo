package com.example.demo.mongo;

import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.bson.Document;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@SpringBootApplication
public class Application implements CommandLineRunner {
  @Resource
  private MongoTemplate mongoTemplate;
  public static void main(String[] args) {
    System.out.println("mongo...");
    SpringApplication.run(Application.class);
    System.out.println("mongo...");
  }

  @Override
  public void run(String... args) throws Exception {
//    m1();
  m2();
  }
  private void m1(){
    People p1 = new People();
    p1.setAge("12");
    p1.setDesc("神仙");
    p1.setName("二郎神");
    List<People> list = new ArrayList<>();
    for (int i=0;i<10;i++){
      list.add(p1);
    }
    Group g = new Group();
    g.setList(list);
    mongoTemplate.insert(g, "c4");
  }
  private void m2(){
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is("5c8f5d878bcaba3e0c8782f4"));
    List<Group> list = mongoTemplate.find(query, Group.class, "c4");
    System.out.println(1);
  }
  public void m3(){

  }
}

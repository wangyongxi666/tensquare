package com.tensquare.article.config;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MongoConfig
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年03月11日 20:50
 * @Version 1.0.0
*/
@Configuration
public class MongoConfig {

  @Value("${spring.data.mongodb.database}")
  private String db;

  @Bean
  public GridFSBucket genGridFSBucket(MongoClient mongoClient){
    MongoDatabase database = mongoClient.getDatabase(db);
    GridFSBucket gridFSBucket = GridFSBuckets.create(database);
    return gridFSBucket;
  }
}

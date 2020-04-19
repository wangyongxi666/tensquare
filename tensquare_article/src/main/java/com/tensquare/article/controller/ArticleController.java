package com.tensquare.article.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.article.pojo.Article;
import com.tensquare.article.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ArticleController
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年04月08日 1:26
 * @Version 1.0.0
*/
@RestController
@RequestMapping("/article")
@CrossOrigin
@Api(value = "文章接口",description = "文章管理")
public class ArticleController {

  @Autowired
  private ArticleService articleService;
  @Autowired
  private RedisTemplate redisTemplate;

  @GetMapping()
  @ApiOperation("查询文章全部列表")
  public Result findAll(){
    List<Article> list = articleService.findAll();
    return new Result(true, StatusCode.OK,"查询成功",list);
  }

  //根据id查询文章
  @GetMapping("/{articleId}")
  @ApiOperation("根据id查询文章")
  @ApiImplicitParam(name = "articleId",value = "主键",required = true,dataType = "String",paramType = "path")
  public Result findById(@PathVariable String articleId){
    Article article = articleService.findById(articleId);
    return new Result(true, StatusCode.OK,"查询成功",article);
  }

  //添加文章
  @PostMapping
  @ApiOperation("添加文章")
  @ApiImplicitParam(name = "article",value = "新增数据",required = true,dataType = "Article",paramType = "body")
  public Result add(@RequestBody Article article){
    articleService.add(article);
    return new Result(true, StatusCode.OK,"添加成功");
  }

  //修改文章
  @PutMapping("/{articleId}")
  @ApiOperation("修改文章")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "articleId",value = "主键",required = true,dataType = "String",paramType = "path"),
          @ApiImplicitParam(name = "article",value = "修改数据",required = true,dataType = "Article",paramType = "body")
  })
  public Result updateById(@PathVariable String articleId,@RequestBody Article article){
    article.setId(articleId);
    articleService.updateById(article);
    return new Result(true, StatusCode.OK,"修改成功");
  }

  @DeleteMapping("/{articleId}")
  @ApiOperation("删除文章")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "articleId",value = "主键",required = true,dataType = "String",paramType = "path")
  })
  public Result deleteById(@PathVariable String articleId){
    articleService.deleteById(articleId);
    return new Result(true, StatusCode.OK,"删除成功");
  }

  //根据条件进行分页查询
  @PostMapping("/search/{page}/{size}")
  @ApiOperation("条件分页查询文章")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "page",value = "起始页码",required = true,dataType = "int",paramType = "path"),
          @ApiImplicitParam(name = "size",value = "每页条数",required = true,dataType = "int",paramType = "path"),
          @ApiImplicitParam(name = "map",value = "封装条件",required = true,dataType = "map",paramType = "body")
  })
  public Result findByPage(@PathVariable Integer page,
                           @PathVariable Integer size,
                           @RequestBody(required = false) Map<String,Object> map){
    Page result = articleService.findPage(page,size,map);
    PageResult pageResult = new PageResult();
    pageResult.setRows(result.getRecords());
    pageResult.setTotal(result.getTotal());

    return new Result(true, StatusCode.OK,"查询成功",pageResult);
  }

  //测试异常处理类
  @GetMapping("/exc")
  public Result testException(){
    int i = 1 / 0;

    return new Result(true, StatusCode.OK,"测试成功");
  }

  @PostMapping("/subscribe")
  @ApiOperation("根据文章id和用户id，建立了订阅关系 保存文章作者id和订阅者的id的关系")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "map",value = "文章id和订阅者id",required = true,dataType = "map",paramType = "body")
  })
  public Result subscribe(@RequestBody Map map){

    //返回true 订阅作者，反之者取消
    Boolean flag = articleService.subscribe(map.get("articleId").toString(),map.get("userId").toString());

    //判断订阅还说取消订阅
    if (flag) {
      return new Result(true, StatusCode.OK,"订阅成功");
    }else {
      return new Result(true, StatusCode.OK,"取消成功");
    }
  }

  @PostMapping("/thumbup/{articleId}/{userId}")
  @ApiOperation("点赞文章")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "articleId",value = "主键",required = true,dataType = "String",paramType = "path"),
          @ApiImplicitParam(name = "userId",value = "点赞操作的用户id",required = true,dataType = "String",paramType = "path")
  })
  public Result thumbupForArticle(
          @PathVariable String articleId,
          @PathVariable String userId
  ){

    // TODO: 2020/4/12 使用jwt鉴权方式获取当前用户的id
    //暂时以传入的方式 将userId传入

    //查询该用户是否已经点赞过
    String key = "thumbup_" + articleId + "_" + userId;
    String value = (String) redisTemplate.opsForValue().get(key);


    if(StringUtils.isEmpty(value)){
      //不能查出信息，则能够点赞
      articleService.thumbupForArticle(articleId,userId);

      //存放对应关系
      redisTemplate.opsForValue().set(key,"exist");
      return new Result(true, StatusCode.OK,"点赞成功");
    }

    //能够查询出信息，则不能再次点赞
    return new Result(false, StatusCode.OK,"点赞失败，重复点赞");

  }

}

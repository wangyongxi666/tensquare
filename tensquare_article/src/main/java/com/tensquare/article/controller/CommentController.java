package com.tensquare.article.controller;

import com.tensquare.article.pojo.Comment;
import com.tensquare.article.service.CommentService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName CommentController
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年04月08日 23:25
 * @Version 1.0.0
*/
@RestController
@RequestMapping("/comment")
public class CommentController {

  @Autowired
  private CommentService commentService;

  @Autowired
  private RedisTemplate redisTemplate;

  //查询所有评论
  @GetMapping()
  public Result findAll(){
    List<Comment> list = commentService.findAll();

    return new Result(true, StatusCode.OK,"查询成功",list);
  }

  //根据id查询评论
  @GetMapping("/{commentId}")
  public Result findById(@PathVariable String commentId){
    Comment comment = commentService.findById(commentId);

    return new Result(true, StatusCode.OK,"查询成功",comment);
  }

  //新增评论
  @PostMapping()
  public Result add(@RequestBody Comment comment){
    commentService.inster(comment);

    return new Result(true, StatusCode.OK,"新增成功");
  }

  //修改评论
  @PutMapping("/{commentId}")
  public Result update(@RequestBody Comment comment,@PathVariable String commentId){

    comment.set_id(commentId);
    commentService.update(comment);

    return new Result(true, StatusCode.OK,"修改成功");
  }

  //根据id删除
  @DeleteMapping("/{commentId}")
  public Result deleteById(@PathVariable String commentId){

    commentService.deleteById(commentId);

    return new Result(true, StatusCode.OK,"删除成功");
  }

  //根据文章id查询评论
  @GetMapping("/article/{articleId}")
  public Result findByArticleId(@PathVariable String articleId){

    List<Comment> list = commentService.findByArticleId(articleId);

    return new Result(true, StatusCode.OK,"查询成功",list);
  }

  //根据评论id 点赞增加  每个用户对每条 评论只能点赞一次
  @PutMapping("/thumbup/{commentId}")
  public Result updateThumbupByCommentId(@PathVariable String commentId){

    String userId = "123";

    System.out.println("key 为 ：" + "thumbup_" + userId + "_" + commentId);

    //查询redis中用户是否已经点赞
    String value = (String)redisTemplate.opsForValue().get("thumbup_" + userId + "_" + commentId);
    if(!StringUtils.isEmpty(value)){
      return new Result(false, StatusCode.OK,"点赞失败，每个人对每条评论只能点赞一次");
    }

    //点赞前把点赞信息保存在redis中
    redisTemplate.opsForValue().set("thumbup_" + userId + "_" + commentId,commentId);

    commentService.updateThumbupByCommentId(commentId);

    return new Result(true, StatusCode.OK,"点赞成功");
  }

}

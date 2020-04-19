package com.tensquare.notice.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.notice.pojo.Notice;
import com.tensquare.notice.pojo.NoticeFresh;
import com.tensquare.notice.service.NoticeService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName NoticeController
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年04月11日 15:57
 * @Version 1.0.0
*/
@RestController
@RequestMapping("/notice")
@Api(value = "消息推送接口",description = "消息推送管理")
public class NoticeController {

  @Autowired
  private NoticeService noticeService;

  @ApiOperation("根据id查询推送消息")
  @GetMapping("/{id}")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "id",value = "主键",required = true,dataType = "string",paramType = "path"),
  })
  public Result selectById(@PathVariable String id){

    Notice notice = noticeService.selectById(id);

    return new Result(true, StatusCode.OK,"查询成功",notice);
  }

  //根据条件进行分页查询
  @PostMapping("/search/{page}/{size}")
  @ApiOperation("条件分页查询文章")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "page",value = "起始页码",required = true,dataType = "int",paramType = "path"),
          @ApiImplicitParam(name = "size",value = "每页条数",required = true,dataType = "int",paramType = "path"),
          @ApiImplicitParam(name = "map",value = "封装条件",required = true,dataType = "Map",paramType = "body")
  })
  public Result findByPage(@PathVariable Integer page,
                           @PathVariable Integer size,
                           @RequestBody(required = false) Map<String,Object> map){
    Page result = noticeService.findPage(page,size,map);
    PageResult pageResult = new PageResult();
    pageResult.setRows(result.getRecords());
    pageResult.setTotal(result.getTotal());

    return new Result(true, StatusCode.OK,"查询成功",pageResult);
  }

  @ApiOperation("新增消息数据")
  @PostMapping
  @ApiImplicitParams({
          @ApiImplicitParam(name = "notice",value = "新增数据",required = true,dataType = "Notice",paramType = "body")
  })
  public Result save(@RequestBody Notice notice){

    noticeService.save(notice);

    return new Result(true, StatusCode.OK,"新增成功");
  }

  @ApiOperation("修改消息数据")
  @PutMapping
  @ApiImplicitParams({
          @ApiImplicitParam(name = "notice",value = "修改数据",required = true,dataType = "Notice",paramType = "body")
  })
  public Result update(@RequestBody Notice notice){

    noticeService.update(notice);

    return new Result(true, StatusCode.OK,"修改成功");
  }

  @ApiOperation("根据用户id查询待推送的消息（新消息）")
  @GetMapping("/fresh/{userId}/{page}/{size}")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "userId",value = "用户id",required = true,dataType = "string",paramType = "path"),
          @ApiImplicitParam(name = "size",value = "每页条数",required = true,dataType = "int",paramType = "path"),
          @ApiImplicitParam(name = "page",value = "起始页码",required = true,dataType = "int",paramType = "path")
  })
  public Result selectNoticeFreshByUserId(
          @PathVariable String userId,
          @PathVariable Integer page,
          @PathVariable Integer size
  ){

    Page<NoticeFresh> pageList = noticeService.selectNoticeFreshByUserId(userId,page,size);

    PageResult<NoticeFresh> pageResult = new PageResult<>();
    pageResult.setTotal(pageList.getTotal());
    pageResult.setRows(pageList.getRecords());

    return new Result(true, StatusCode.OK,"查询成功",pageResult);
  }

  @ApiOperation("根据id删除待推送消息(新消息)")
  @DeleteMapping("/fresh/{userId}")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "userId",value = "主键",required = true,dataType = "string",paramType = "path"),
  })
  public Result deleteById(@PathVariable String userId){

    noticeService.deleteById(userId);

    return new Result(true, StatusCode.OK,"删除成功");
  }


  @ApiOperation("测试 rb 事务")
  @GetMapping("/fresh/{userId}")
  public Result rnTest(){

    noticeService.raTest();

    return new Result(true, StatusCode.OK,"测试成功");
  }
}

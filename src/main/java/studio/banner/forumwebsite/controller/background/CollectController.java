package studio.banner.forumwebsite.controller.background;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.banner.forumwebsite.bean.CollectBean;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.service.ICollectService;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2021/5/13 22:01
 */
@RestController
@Api(tags = "收藏接口", value = "CollectController")
public class CollectController {
    private static final Logger logger = LoggerFactory.getLogger(CollectController.class);

    @Autowired
    private ICollectService iCollectService;
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "colId",
            value = "帖子收藏id",required = false,dataType = "int"),
            @ApiImplicitParam(paramType = "query",name = "colArtId",
            value = "帖子id",required = true,dataType = "int"),
            @ApiImplicitParam(paramType = "query",name = "colArtTit",
            value = "帖子标题",required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name = "cloUserId",
            value = "用户id",required = true,dataType = "int")

    })

    @ApiOperation(value = "收藏增加", notes ="收藏对象不能为空",httpMethod = "POST")
    @PostMapping("/insertCollect")
    public RespBean insert(CollectBean collectBean){
        for (CollectBean list: iCollectService.selectCollectByUserId(collectBean.getCloUserId())) {
            if (collectBean.getColArtId().equals(list.getColArtId())){
                logger.error("收藏"+collectBean.getColArtTit()+"添加失败");
                return RespBean.error("收藏"+collectBean.getColArtTit()+"已存在");
            }
        }
        if (iCollectService.insertCollect(collectBean)){
            logger.info("收藏"+collectBean.getColArtTit()+"添加成功");
            return RespBean.ok("收藏"+collectBean.getColArtTit()+"添加成功");
        }else {
            logger.error("收藏"+collectBean.getColArtTit()+"添加失败");
            return RespBean.error("收藏"+collectBean.getColArtTit()+"添加失败");
        }
    }
    @ApiImplicitParam(paramType = "query",name = "id",
            value = "帖子收藏id",required = true,dataType = "int")
    @ApiOperation(value = "删除收藏", notes = "id不能为空", httpMethod = "DELETE")
    @DeleteMapping("/deleteCollect")
    public RespBean delete(Integer id){
        if (iCollectService.deleteCollect(id)){
            logger.info("收藏删除成功");
            return RespBean.ok("删除成功");
        }else {
            logger.error("收藏删除失败");
            return RespBean.error("删除失败");
        }
    }

    @ApiImplicitParam(paramType = "query",name = "userid",
            value = "用户id",required = true,dataType = "int")
    @ApiOperation(value = "清除用户收藏", notes = "用户id不能为空", httpMethod = "DELETE")
    @DeleteMapping("/deleteAll")
    public RespBean deleteAll(Integer userid){
        if (iCollectService.deleteCollectByUserId(userid)){
            logger.info("清除成功");
            return RespBean.ok("清除所有收藏成功");
        }else {
            logger.error("清除失败");
            return RespBean.error("清除所有收藏失败");
        }
    }

    @ApiImplicitParam(paramType = "query",name = "userid",
            value = "用户id",required = true,dataType = "int")
    @ApiOperation(value = "查询收藏内容", notes = "", httpMethod = "GET")
    @GetMapping ("/selectCollect")
    public RespBean select(Integer userid){
        List<CollectBean> artTit = iCollectService.selectCollectByUserId(userid);
        if (artTit.size() != 0){
            logger.info("查询成功");
            return RespBean.ok("查询成功",artTit);
        }else {
            logger.error("查询失败");
            return RespBean.error("查询失败");
        }
    }
}


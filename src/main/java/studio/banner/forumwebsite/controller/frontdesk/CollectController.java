package studio.banner.forumwebsite.controller.frontdesk;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import studio.banner.forumwebsite.bean.CollectBean;
import studio.banner.forumwebsite.bean.CollectFavoriteBean;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.service.ICollectService;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2022/3/8 16:27
 * @role:
 */
@RestController
@Api(tags = "前台用户收藏夹接口", value = "CollectController")
@RequestMapping("/frontDesk")
public class CollectController {

    private static final Logger logger = LoggerFactory.getLogger(CollectController.class);

    @Autowired
    private ICollectService iCollectService;

    @PostMapping("/insertCollectFavorite")
    @ApiOperation(value = "收藏夹增加", notes = "单个用户收藏夹不能重名", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "favoriteId",
                    value = "帖子收藏夹id", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "userId",
                    value = "用户id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "favoriteName",
                    value = "收藏夹名", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "privacy",
                    value = "0：公开 1：隐私", required = true, dataTypeClass = Integer.class)
    })
    public RespBean insertCollectFavorite(CollectFavoriteBean collectFavoriteBean){
        return iCollectService.insertCollectFavorite(collectFavoriteBean);
    }


    @PostMapping("/updateCollectFavorite")
    @ApiOperation(value = "更新收藏夹", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "favoriteId",
                    value = "帖子收藏夹id", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "userId",
                    value = "用户id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "favoriteName",
                    value = "收藏夹名", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "privacy",
                    value = "0：公开 1：隐私", required = true, dataTypeClass = Integer.class)
    })
    public RespBean updateCollectFavorite(CollectFavoriteBean collectFavoriteBean){
        return iCollectService.updateCollectFavorite(collectFavoriteBean);
    }

    @GetMapping("/selectCollectFavoriteById/{userId}/{selectId}")
    @ApiOperation(value = "查询收藏夹", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId",
                    value = "收藏夹用户id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "selectId",
                    value = "查看用户id", required = true, dataTypeClass = Integer.class)
    })
    public RespBean selectCollectFavoriteById(@PathVariable Integer userId,@PathVariable Integer selectId){
        return iCollectService.selectCollectFavoriteById(userId,selectId);
    }

    @DeleteMapping("/selectCollectFavoriteById/{favoriteId}/{moveFavoriteId}")
    @ApiOperation(value = "删除收藏夹", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "favoriteId",
                    value = "收藏夹id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "moveFavoriteId",
                    value = "收藏移动到其他收藏夹id，如果为-1则删除该收藏夹文章", required = true, dataTypeClass = Integer.class)
    })
    public RespBean deleteCollectFavorite(@PathVariable Integer favoriteId,@PathVariable Integer moveFavoriteId){
        return iCollectService.deleteCollectFavorite(favoriteId,moveFavoriteId);
    }

    @GetMapping("/judgeCollectPost/{userId}/{postId}")
    @ApiOperation(value = "判断该用户是否收藏该帖子", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postId",
                    value = "帖子id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "userId",
                    value = "用户id", required = true, dataTypeClass = Integer.class)
    })
    private boolean judgeCollectPost(@PathVariable("userId") Integer userId,@PathVariable("postId") Integer postId){
        System.out.println(userId);
        System.out.println(postId);
        return iCollectService.judgeCollectPost(userId, postId);
    }

    @PostMapping("/insertCollect")
    @ApiOperation(value = "收藏增加", notes = "收藏对象不能为空", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "colId",
                    value = "帖子收藏id", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "colArtId",
                    value = "帖子id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "colArtTit",
                    value = "帖子标题", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "favoriteId",
                    value = "收藏夹id", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "cloUserId",
                    value = "用户id", required = true, dataTypeClass = Integer.class)

    })
    public RespBean insert(CollectBean collectBean) {
        if (iCollectService.insertCollect(collectBean)) {
            return RespBean.ok("收藏" + collectBean.getColArtTit() + "添加成功");
        } else {
            return RespBean.error("收藏" + collectBean.getColArtTit() + "添加失败");
        }
    }

    @DeleteMapping("/deleteCollect/{postId}/{userId}")
    @ApiOperation(value = "删除收藏", notes = "id不能为空", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postId",
                    value = "帖子id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "userId",
                    value = "用户id", required = true, dataTypeClass = Integer.class)
    })
    public RespBean deleteCollect(@PathVariable Integer postId,@PathVariable Integer userId) {
        if (iCollectService.deleteCollect(postId,userId)) {
            logger.info("收藏删除成功");
            return RespBean.ok("删除成功");
        } else {
            logger.error("收藏删除失败");
            return RespBean.error("删除失败");
        }
    }


    @DeleteMapping("/deleteBatchCollectByIds/{colIds}/{userId}")
    @ApiOperation(value = "批量删除收藏", notes = "id不能为空", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "colIds",
                    value = "收藏集合id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "userId",
                    value = "用户id", required = true, dataTypeClass = Integer.class)
    })
    public RespBean deleteBatchCollectByIds(@PathVariable List<Integer> colIds,@PathVariable Integer userId){
        return iCollectService.deleteBatchCollectByIds(colIds,userId);
    }


    @DeleteMapping("/deleteCollectByUserId/{userId}")
    @ApiOperation(value = "清除用户收藏", notes = "用户id不能为空", httpMethod = "DELETE")
    @ApiImplicitParam(paramType = "query", name = "userId",
            value = "用户id", required = true, dataTypeClass = Integer.class)
    public RespBean deleteCollectByUserId(@PathVariable Integer userId) {
        if (iCollectService.deleteCollectByUserId(userId)) {
            logger.info("清除成功");
            return RespBean.ok("清除所有收藏成功");
        } else {
            logger.error("清除失败");
            return RespBean.error("清除所有收藏失败");
        }
    }

    @PostMapping("/updateCollectById")
    @ApiOperation(value = "移动收藏内容到指定收藏夹",  httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "colId",
                    value = "收藏主键id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "favoriteId",
                    value = "收藏夹id", required = true, dataTypeClass = Integer.class)
    })
    public RespBean updateCollectById(Integer colId, Integer favoriteId){
        return iCollectService.updateCollectById(colId,favoriteId);
    }

    @PostMapping("/updateCollectByIds")
    @ApiOperation(value = "移动收藏内容到指定收藏夹",  httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "colIds",
                    value = "收藏主键ids", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "favoriteId",
                    value = "收藏夹id", required = true, dataTypeClass = Integer.class)
    })
    public RespBean updateCollectByIds(List<Integer> colIds, Integer favoriteId) {
        return iCollectService.updateCollectByIds(colIds, favoriteId);
    }

    @GetMapping("/selectCollectByFavoriteId/{favoriteId}")
    @ApiOperation(value = "根据收藏夹id获取收藏夹中的数据",  httpMethod = "GET")
    @ApiImplicitParam(paramType = "query", name = "favoriteId",
            value = "收藏夹id", required = true, dataTypeClass = Integer.class)
    public RespBean selectCollectByFavoriteId(@PathVariable Integer favoriteId) {
        return iCollectService.selectCollectByFavoriteId(favoriteId);
    }

    @GetMapping("selectCollectFavoriteId/{userId}/{postId}")
    @ApiOperation(value = "根据用户id和帖子id查询收藏夹",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId",
                    value = "用户id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "postId",
                    value = "帖子id", required = true, dataTypeClass = Integer.class)
    })
    public RespBean selectCollectFavoriteId(@PathVariable("userId") Integer userId,@PathVariable("postId") Integer postId){
        return iCollectService.selectCollectFavoriteId(userId, postId);
    }
}

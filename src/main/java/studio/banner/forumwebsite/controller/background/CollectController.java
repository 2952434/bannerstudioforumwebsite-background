package studio.banner.forumwebsite.controller.background;

import io.swagger.annotations.Api;
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

import java.util.ArrayList;
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


    @ApiOperation(value = "收藏增加", notes ="收藏对象不能为空",httpMethod = "POST")
    @PostMapping("/insertCollect")
    public void insert(CollectBean collectBean){
        if (iCollectService.insertCollect(collectBean)){
            logger.info("收藏"+collectBean.getColArtTit()+"添加成功");
        }else {
            logger.error("收藏"+collectBean.getColArtTit()+"添加失败");
        }
    }
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
    @ApiOperation(value = "查询收藏内容", notes = "", httpMethod = "GET")
    @GetMapping ("/selectCollect")
    public RespBean select(Integer userid){
        List<CollectBean> artTit = iCollectService.selectCollectByUserId(userid);
        if (artTit != null){
            logger.info("查询成功");
            List<String> l = new ArrayList<>(artTit.size());
            for(CollectBean c:artTit){
                l.add(c.getColArtTit());
            }
            return RespBean.ok("查询成功",l);
        }else {
            logger.error("查询失败");
            return RespBean.error("查询失败");
        }
    }
}

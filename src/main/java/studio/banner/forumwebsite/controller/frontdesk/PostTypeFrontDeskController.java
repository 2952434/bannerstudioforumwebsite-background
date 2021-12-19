package studio.banner.forumwebsite.controller.frontdesk;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.banner.forumwebsite.bean.PostBean;
import studio.banner.forumwebsite.bean.PostTypeBean;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.service.IPostTypeService;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2021/12/12 18:41
 * @role:
 */
@RestController
@Api(tags = "前台帖子类型接口",value = "PostTypeFrontDeskController")
public class PostTypeFrontDeskController {

    @Autowired
    private IPostTypeService iPostTypeService;

    @GetMapping("/postTypeFrontDesk/selectAllPostType")
    @ApiOperation(value = "查询所有帖子类型",httpMethod = "GET")
    public RespBean selectAllPostType(){
        List<PostTypeBean> list = iPostTypeService.selectAllPostType();
        if (list.size()!=0){
            return RespBean.ok("查询成功", list);
        }else {
            return RespBean.error("查询失败！！！");
        }
    }

    @GetMapping("/postTypeFrontDesk/selectPostByType")
    @ApiOperation(value = "通过类型查询该类型下的所有帖子",notes = "帖子类型不为空",httpMethod = "GET")
    @ApiImplicitParam(type = "query",name = "postType",
            value = "帖子类型",required = true,dataTypeClass = String.class)
    public RespBean selectPostByType(String postType){
        List<PostBean> list = iPostTypeService.selectPostByType(postType);
        if (list!=null){
            return RespBean.ok("查询成功",list);
        }else {
            return RespBean.error("该类型还没有帖子，查询失败！！！",list);
        }
    }

    @GetMapping("/postTypeFrontDesk/selectPostDescByType")
    @ApiOperation(value = "通过类型查询该类型下帖子前十",notes = "帖子类型不为空",httpMethod = "GET")
    @ApiImplicitParam(type = "query",name = "postType",
            value = "帖子类型",required = true,dataTypeClass = String.class)
    public RespBean selectPostDescByType(String postType) {
        List<PostBean> list = iPostTypeService.selectPostDescByType(postType);
        return RespBean.ok(list);
    }
}

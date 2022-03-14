package studio.banner.forumwebsite.controller.frontdesk;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.bean.UserAttentionBean;
import studio.banner.forumwebsite.service.IUserAttentionService;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2022/3/13 12:00
 * @role:
 */
@RestController
@Api(tags = "前台关注展示接口", value = "ShowAttentionController")
@RequestMapping("/frontDesk")
public class ShowAttentionController {


    @Autowired
    private IUserAttentionService iUserAttentionService;

    @GetMapping("/selectAttentionInformation")
    @ApiOperation(value = "根据用户Id分页查询其粉丝信息",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "memberId",
                    value = "用户id",required = true,dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query",name = "page",
                    value = "页数",required = true,dataTypeClass = Integer.class)
    })
    public RespBean selectAttentionInformation(Integer memberId, Integer page) {
        List<UserAttentionBean> userAttentionBeans = iUserAttentionService.selectAttentionInformation(memberId, page);
        if (userAttentionBeans.size()==0){
            return RespBean.error("该页无数据");
        }
        return RespBean.ok("查询成功",userAttentionBeans);
    }


    @DeleteMapping("/deleteAttentionInformation")
    @ApiOperation(value = "根据关注id取消关注信息展示",httpMethod = "DELETE")
    @ApiImplicitParam(paramType = "query",name = "id",
            value = "关注id",required = true,dataTypeClass = Integer.class)
    public RespBean deleteAttentionInformation(Integer id) {
        return iUserAttentionService.deleteAttentionInformation(id);
    }

    @DeleteMapping("/deleteAllAttentionInformation")
    @ApiOperation(value = "根据用户id取消全部关注信息展示",httpMethod = "DELETE")
    @ApiImplicitParam(paramType = "query",name = "userId",
            value = "用户id",required = true,dataTypeClass = Integer.class)
    public RespBean deleteAllAttentionInformation(Integer userId) {
        return iUserAttentionService.deleteAllAttentionInformation(userId);
    }

}
package studio.banner.forumwebsite.controller.frontdesk;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.bean.UserAttentionBean;
import studio.banner.forumwebsite.service.IUserAttentionService;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/selectAttentionInformation/{memberId}/{page}")
    @ApiOperation(value = "根据用户Id分页查询其粉丝信息",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "memberId",
                    value = "用户id",required = true,dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query",name = "page",
                    value = "页数",required = true,dataTypeClass = Integer.class)
    })
    public RespBean selectAttentionInformation(@PathVariable Integer memberId,@PathVariable Integer page) {
        List<Map<String, String>> userAttentionBeans = iUserAttentionService.selectAttentionInformation(memberId, page);
        for (Map<String, String> userAttentionBean : userAttentionBeans) {
            boolean contacted = iUserAttentionService.contacted(Integer.parseInt(String.valueOf(userAttentionBean.get("be_attention_id"))), Integer.parseInt(String.valueOf(userAttentionBean.get("attention_id"))));
            if (contacted) {
                userAttentionBean.put("isFollow", "true");
                userAttentionBean.put("texts", "已关注");
            } else {
                userAttentionBean.put("isFollow", "false");
                userAttentionBean.put("texts", "关注");
            }
        }
        return RespBean.ok("查询成功",userAttentionBeans);
    }


    @DeleteMapping("/deleteAttentionInformation/{id}")
    @ApiOperation(value = "根据关注id取消关注信息展示",httpMethod = "DELETE")
    @ApiImplicitParam(paramType = "query",name = "id",
            value = "关注id",required = true,dataTypeClass = Integer.class)
    public RespBean deleteAttentionInformation(@PathVariable Integer id) {
        return iUserAttentionService.deleteAttentionInformation(id);
    }

    @DeleteMapping("/deleteAllAttentionInformation/{userId}")
    @ApiOperation(value = "根据用户id取消全部关注信息展示",httpMethod = "DELETE")
    @ApiImplicitParam(paramType = "query",name = "userId",
            value = "用户id",required = true,dataTypeClass = Integer.class)
    public RespBean deleteAllAttentionInformation(@PathVariable Integer userId) {
        return iUserAttentionService.deleteAllAttentionInformation(userId);
    }

}

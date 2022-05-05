package studio.banner.forumwebsite.controller.background;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.service.IFixedInformationService;
import studio.banner.forumwebsite.service.IRedisService;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2022/3/13 15:24
 * @role:
 */
@RestController
@Api(tags = "后台论坛数据接口",value = "BackForumDateController")
@RequestMapping("/backGround")
public class BackForumDateController {


    @Autowired
    private IRedisService iRedisService;
    @Autowired
    private IFixedInformationService iFixedInformationService;

    @GetMapping("/selectForumEveryDayAddViewNum")
    @ApiOperation(value = "查询论坛15天每天新增浏览量",httpMethod = "GET")
    public RespBean selectForumEveryDayAddViewNum() {
        List<Integer> list = iRedisService.selectForumEveryDayAddViewNum();
        if (list.size()==0){
            return RespBean.error("未查询到数据");
        }
        return RespBean.ok("查询成功",list);
    }


    @GetMapping("/selectForumPostInsertNum")
    @ApiOperation(value = "查询论坛15天每天新增发帖量",httpMethod = "GET")
    public RespBean selectForumPostInsertNum() {
        List<Integer> list = iRedisService.selectForumPostInsertNum();
        System.out.println(list);
        if (list.size()==0){
            return RespBean.error("未查询到数据");
        }
        return RespBean.ok("查询成功",list);
    }

    @GetMapping("/selectDirectionNum")
    @ApiOperation(value = "查询每个方向的人数",httpMethod = "GET")
    public RespBean selectDirectionNum() {
        List<HashMap<String, String>> hashMaps = iFixedInformationService.selectDirectionNum();
        if (hashMaps.size()!=0){
            return RespBean.ok("查询成功",hashMaps);
        }else {
            return RespBean.error("查询失败");
        }
    }

    @GetMapping("/selectDirectionPostNum")
    @ApiOperation(value = "查询每个方向的发帖数",httpMethod = "GET")
    public RespBean selectDirectionPostNum() {
        List<HashMap<String, String>> hashMaps = iFixedInformationService.selectDirectionPostNum();
        if (hashMaps.size()!=0){
            return RespBean.ok("查询成功",hashMaps);
        }else {
            return RespBean.error("查询失败");
        }
    }

}

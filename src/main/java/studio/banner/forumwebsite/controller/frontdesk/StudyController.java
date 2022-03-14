package studio.banner.forumwebsite.controller.frontdesk;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.service.IStudyRouteService;

/**
 * @Author: Ljx
 * @Date: 2022/3/5 21:44
 * @role: 前台学习路线接口
 */
@RestController
@Api(tags = "前台学习路线接口", value = "BackStudyController")
@RequestMapping("/frontDesk")
public class StudyController {

    @Autowired
    private IStudyRouteService iStudyRouteService;

    @GetMapping("/selectStudyRouteByDirection")
    @ApiOperation(value = "根据用户方向和id查询学习计划", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "direction",
                    value = "用户方向", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "userId",
                    value = "用户id", required = true, dataTypeClass = Integer.class)
    })
    public RespBean selectStudyRouteByDirection(String direction,int userId){

        return iStudyRouteService.selectStudyRouteByDirection(direction,userId);
    }

}

package studio.banner.forumwebsite.controller.frontdesk;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.bean.StudyRoute;
import studio.banner.forumwebsite.service.IStudyRouteService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Re
 * @Date: 2021/5/25 18:21
 */
@RestController
@Slf4j
@Api(tags = "学习路线前台接口", value = "StudyRouteFrontDeskController")
public class StudyRouteFrontDeskController {
    @Resource
    private IStudyRouteService iStudyRouteService;

    @GetMapping("/studyRouteFrontDesk/selectByStageNumber")
    @ApiOperation(value = "根据学习方向和阶段编号查询指定学习方向的指定阶段的内容", httpMethod = "GET")
    public RespBean selectByStageNumber(@RequestParam String studyDirection, @RequestParam Integer stageNumber) {
        if (studyDirection == null || stageNumber == null) {
            return RespBean.error("查询失败，stageNumber和studyDirection不可以为空");
        }
        StudyRoute studyRoute = iStudyRouteService.selectByStageNumber(studyDirection, stageNumber);
        if (studyRoute != null) {
            log.info("查询成功");
            return RespBean.ok("查询成功", studyRoute);
        }
        log.info("查询失败");
        return RespBean.error(String.format("学习路线中未找到%s方向的第%d阶段", studyDirection, stageNumber));
    }

    @GetMapping("/studyRouteFrontDesk/selectByStudyDirection")
    @ApiOperation(value = "根据学习方向查询该方向的所有阶段的内容", httpMethod = "GET")
    public RespBean selectByStudyDirection(@RequestParam String studyDirection) {
        if (studyDirection == null) {
            return RespBean.error("查询失败，studyDirection不可以为空");
        }
        List<StudyRoute> list = iStudyRouteService.selectByStudyDirection(studyDirection);
        if (list != null) {
            log.info("查询成功");
            return RespBean.ok("查询成功", list);
        }
        log.info("查询失败");
        return RespBean.error(String.format("学习路线中未找到%s方向", studyDirection));
    }

    @GetMapping("/studyRouteFrontDesk/selectAll")
    @ApiOperation(value = "查询所有学习方向内容", httpMethod = "GET")
    public RespBean selectAll() {
        List<StudyRoute> list = iStudyRouteService.selectAll();
        if (list != null) {
            log.info("查询成功");
            return RespBean.ok("查询成功", list);
        }
        log.info("查询失败");
        return RespBean.error("查询无数据");
    }
}

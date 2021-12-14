package studio.banner.forumwebsite.controller.frontdesk;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.bean.StudyRoute;
import studio.banner.forumwebsite.bean.StudyRouteContent;
import studio.banner.forumwebsite.service.IStudyRouteContentService;
import studio.banner.forumwebsite.service.IStudyRouteService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Re
 * @Date: 2021/5/25 18:12
 */
@RestController
@Slf4j
@Api(tags = "学习路线内容前台接口",value = "StudyRouteContentFrontDeskController")
public class StudyRouteContentFrontDeskController {
    @Resource
    private IStudyRouteContentService iStudyRouteContentService;
    @Resource
    private IStudyRouteService iStudyRouteService;
    @GetMapping("/studyRouteContentFrontDesk/selectSingle")
    @ApiOperation(value = "根据 内容序号、学习路线id 查询指定条 学习路线内容",httpMethod = "GET")
    public RespBean selectSingle(@RequestParam String studyDirection, @RequestParam Integer stageNumber, @RequestParam Integer serialNumber) {
        if (studyDirection==null||stageNumber==null||serialNumber==null){
            return RespBean.error("查询失败，stageNumber和studyDirection和serialNumber不可以为空");
        }
        StudyRoute studyRoute = iStudyRouteService.selectByStageNumber(studyDirection, stageNumber);
        if (studyRoute != null) {
            StudyRouteContent studyRouteContent = iStudyRouteContentService.selectSingle(serialNumber, studyRoute.getId());
            if (studyRouteContent != null) {
                log.info("查询成功！");
                return RespBean.ok("查询成功", studyRouteContent);
            } else {
                log.info("查询失败！");
                return RespBean.error(String.format("查询失败，未找到%s学习路线的第%d阶段的第%d条内容", studyDirection, stageNumber, serialNumber));
            }
        }
        log.info("查询失败");
        return RespBean.error(String.format("查询失败，未找到%s学习路线的第%d阶段", studyDirection, stageNumber));
    }
    @GetMapping("/studyRouteContentFrontDesk/selectByDirectionId")
    @ApiOperation(value = "查询指定学习路线的指定阶段的所有内容",httpMethod = "GET")
    public RespBean selectByDirectionId(@RequestParam String studyDirection, @RequestParam Integer stageNumber) {
        if (studyDirection==null||stageNumber==null){
            return RespBean.error("删除失败，stageNumber和studyDirection不可以为空");
        }
        StudyRoute studyRoute = iStudyRouteService.selectByStageNumber(studyDirection, stageNumber);
        if (studyRoute != null) {
            List<StudyRouteContent> list = iStudyRouteContentService.selectByDirectionId(studyRoute.getId());
            if (list != null) {
                log.info("查询成功！");
                return RespBean.ok("查询成功", list);
            } else {
                log.info("查询失败！");
                return RespBean.error(String.format("删除失败，未找到%s学习路线的第%d阶段", studyDirection, stageNumber));
            }
        }
        log.info("查询失败！");
        return RespBean.error(String.format("删除失败，未找到%s学习路线的第%d阶段", studyDirection, stageNumber));
    }
    @GetMapping("/studyRouteContentFrontDesk/selectAll")
    @ApiOperation(value = "查询学习路线内容表中的所有内容",httpMethod = "GET")
    public RespBean selectAll() {
        List<StudyRouteContent> list = iStudyRouteContentService.selectAll();
        if (list != null) {
            log.info("查询成功！");
            return RespBean.ok("查询成功！", list);
        }
        log.info("查询失败！");
        return RespBean.error("查询无数据");
    }

}

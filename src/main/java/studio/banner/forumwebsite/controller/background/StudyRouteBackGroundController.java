package studio.banner.forumwebsite.controller.background;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.bean.StudyRoute;
import studio.banner.forumwebsite.service.IStudyRouteService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author hyy
 * @date 2021/5/14 20:35
 * @role 学习路线控制层
 */
@RestController
@Api(tags = "学习路线接口", value = "StudyRouteBackGroundController")
//@PreAuthorize("hasRole('ROLE_ADMIN')")
public class StudyRouteBackGroundController {
    protected static final Logger logger = LoggerFactory.getLogger(StudyRoute.class);
    @Resource
    private IStudyRouteService iStudyRouteService;

    @PostMapping(value = "/studyRouteBackGround/insert")
    @ApiOperation("插入一条学习路线")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studyDirection", value = "学习方向", dataTypeClass = String.class),
            @ApiImplicitParam(name = "studyStage", value = "学习阶段", dataTypeClass = String.class),
            @ApiImplicitParam(name = "stageNumber", value = "阶段编号", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "examinationForm", value = "阶段考核形式", dataTypeClass = String.class)
    })
    public RespBean insert(@Valid StudyRoute studyRoute, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> map = new HashMap<>();
            List<FieldError> errors = bindingResult.getFieldErrors();
            logger.error("插入失败！");
            for (FieldError error : errors) {
                logger.error("错误的字段名：" + error.getField());
                logger.error("错误信息：" + error.getDefaultMessage());
                map.put(error.getField(), error.getDefaultMessage());
            }
            return RespBean.error(map);
        }
        if (iStudyRouteService.insert(studyRoute)) {
            logger.info("插入成功！");
            return RespBean.ok("插入成功！");
        }
        logger.info("插入失败！");
        return RespBean.error(String.format("插入失败，%s方向的第%d阶段已存在", studyRoute.getStudyDirection(), studyRoute.getStageNumber()));
    }

    @DeleteMapping(value = "/studyRouteBackGround/deleteByStageNumber")
    @ApiOperation("根据学习方向和阶段编号删除指定学习方向的指定阶段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studyDirection", value = "学习方向", dataTypeClass = String.class),
            @ApiImplicitParam(name = "stageNumber", value = "阶段编号", dataTypeClass = Long.class)
    })
    public RespBean deleteByStageNumber(@RequestParam String studyDirection, @RequestParam Integer stageNumber) {
        if (studyDirection == null || stageNumber == null) {
            return RespBean.error("删除失败，stageNumber和studyDirection不可以为空");
        }
        if (iStudyRouteService.deleteByNumber(studyDirection, stageNumber)) {
            logger.info("删除成功");
            return RespBean.ok("删除成功！");
        }
        logger.info("删除失败！");
        return RespBean.error(String.format("删除失败，未找到%s方向的第%d阶段", studyDirection, stageNumber));

    }

    @DeleteMapping(value = "/studyRouteBackGround/deleteByStudyDirection")
    @ApiOperation("根据学习方向删除指定方向的所有内容")
    @ApiImplicitParam(name = "studyDirection", value = "学习方向", dataTypeClass = String.class)
    public RespBean deleteByStudyDirection(@RequestParam String studyDirection) {
        if (studyDirection == null) {
            return RespBean.error("删除失败，studyDirection不可以为空");
        }
        if (iStudyRouteService.deleteByDirection(studyDirection)) {
            logger.info("删除成功！");
            return RespBean.ok("删除成功！");
        } else {
            logger.info("删除失败！");
            return RespBean.error(String.format("删除失败，学习路线中未找到%s方向", studyDirection));
        }
    }

    @PutMapping(value = "/studyRouteBackGround/update")
    @ApiOperation("根据学习方向和阶段编号更新指定学习方向的指定阶段的内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studyDirection", value = "学习方向", dataTypeClass = String.class),
            @ApiImplicitParam(name = "studyStage", value = "学习阶段", dataTypeClass = String.class),
            @ApiImplicitParam(name = "stageNumber", value = "阶段编号", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "examinationForm", value = "阶段考核形式", dataTypeClass = String.class)
    })
    public RespBean update(@Valid StudyRoute studyRoute, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> map = new HashMap<>();
            List<FieldError> errors = bindingResult.getFieldErrors();
            logger.error("插入失败！");
            for (FieldError error : errors) {
                logger.error("错误的字段名：" + error.getField());
                logger.error("错误信息：" + error.getDefaultMessage());
                map.put(error.getField(), error.getDefaultMessage());
            }
            return RespBean.error(map);
        }
        if (iStudyRouteService.update(studyRoute)) {
            logger.info("更新成功");
            return RespBean.ok("更新成功！");
        }
        logger.info("更新失败");
        return RespBean.error(String.format("学习路线中未找到%s方向的第%d阶段", studyRoute.getStudyDirection(), studyRoute.getStageNumber()));
    }

    @GetMapping(value = "/studyRouteBackGround/selectByStageNumber")
    @ApiOperation("根据学习方向和阶段编号查询指定学习方向的指定阶段的内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studyDirection", value = "学习方向", dataTypeClass = String.class),
            @ApiImplicitParam(name = "stageNumber", value = "阶段编号", dataTypeClass = Long.class)
    })
    public RespBean selectByStageNumber(@RequestParam String studyDirection, @RequestParam Integer stageNumber) {
        if (studyDirection == null || stageNumber == null) {
            return RespBean.error("查询失败，stageNumber和studyDirection不可以为空");
        }
        StudyRoute studyRoute = iStudyRouteService.selectByStageNumber(studyDirection, stageNumber);
        if (studyRoute != null) {
            logger.info("查询成功");
            return RespBean.ok("查询成功", studyRoute);
        }
        logger.info("查询失败");
        return RespBean.error(String.format("学习路线中未找到%s方向的第%d阶段", studyDirection, stageNumber));
    }

    @GetMapping(value = "/studyRouteBackGround/selectByStudyDirection")
    @ApiOperation("根据学习方向查询该方向的所有阶段的内容")
    @ApiImplicitParam(name = "studyDirection", value = "学习方向", dataTypeClass = String.class)
    public RespBean selectByStudyDirection(@RequestParam String studyDirection) {
        if (studyDirection == null) {
            return RespBean.error("查询失败，studyDirection不可以为空");
        }
        List<StudyRoute> list = iStudyRouteService.selectByStudyDirection(studyDirection);
        if (list != null) {
            logger.info("查询成功");
            return RespBean.ok("查询成功", list);
        }
        logger.info("查询失败");
        return RespBean.error(String.format("学习路线中未找到%s方向", studyDirection));
    }

    @GetMapping(value = "/studyRouteBackGround/selectAll")
    @ApiOperation("查询所有方向内容")
    public RespBean selectAll() {
        List<StudyRoute> list = iStudyRouteService.selectAll();
        if (list != null) {
            logger.info("查询成功");
            return RespBean.ok("查询成功", list);
        }
        logger.info("查询失败");
        return RespBean.error("查询无数据");
    }

    @GetMapping(value = "/studyRouteBackGround/selectByPage")
    @ApiOperation("分页查询所有方向内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "页码数", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "pageSize", value = "每页容量", dataTypeClass = Long.class)
    })
    public RespBean selectByPage(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        if (pageNumber == null || pageSize == null) {
            return RespBean.error("查询失败，pageNumber和pageSize不可以为空");
        }
        IPage<StudyRoute> iPage = iStudyRouteService.selectByPage(pageNumber, pageSize);
        if (iPage != null) {
            logger.info("查询成功");
            return RespBean.ok("查询成功", iPage);
        }
        logger.info("查询失败");
        return RespBean.error("查询无数据");
    }
}

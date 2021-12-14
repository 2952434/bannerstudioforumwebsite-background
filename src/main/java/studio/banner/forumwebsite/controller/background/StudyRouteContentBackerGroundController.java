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
import studio.banner.forumwebsite.bean.StudyRouteContent;
import studio.banner.forumwebsite.service.IStudyRouteContentService;
import studio.banner.forumwebsite.service.IStudyRouteService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hyy
 * @date 2021/5/15 10:27
 * @role 学习路线内容 控制层
 */
@RestController
@Api(tags = "后台学习路线内容接口", value = "StudyRouteContentBackerGroundController")
//@PreAuthorize("hasRole('ROLE_ADMIN')")
public class StudyRouteContentBackerGroundController {
    protected static final Logger logger = LoggerFactory.getLogger(StudyRouteContentBackerGroundController.class);
    @Resource
    private IStudyRouteContentService iStudyRouteContentService;
    @Resource
    private IStudyRouteService iStudyRouteService;

    @PostMapping(value = "/studyRouteContentBackerGround/insert")
    @ApiOperation("插入一条学习路线内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studyContent", value = "学习路线内容",dataTypeClass = String.class),
            @ApiImplicitParam(name = "serialNumber", value = "内容序号",dataTypeClass = Long.class),
            @ApiImplicitParam(name = "studyDirection", value = "学习路线",dataTypeClass = String.class),
            @ApiImplicitParam(name = "stageNumber", value = "学习阶段编号",dataTypeClass = Long.class)
    })
    public RespBean insert(@Valid StudyRouteContent studyRouteContent, BindingResult bindingResult, @RequestParam String studyDirection, @RequestParam Integer stageNumber) {
        if (bindingResult.hasErrors()){
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
        StudyRoute studyRoute = iStudyRouteService.selectByStageNumber(studyDirection, stageNumber);
        if (studyRoute != null) {
            studyRouteContent.setDirectionId(studyRoute.getId());
            if (iStudyRouteContentService.insert(studyRouteContent)) {
                return RespBean.ok("插入成功！");
            }
            return RespBean.error(String.format("%s学习路线中第%d阶段的第%d条内容已存在", studyDirection, stageNumber, studyRouteContent.getSerialNumber()));
        }
        return RespBean.error(String.format("未找到%s学习路线的第%d阶段", studyDirection, stageNumber));
    }

    @DeleteMapping(value = "/studyRouteContentBackerGround/deleteSingle")
    @ApiOperation("根据学习路线、学习阶段编号以及内容序号删除指定条内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studyDirection", value = "学习路线",dataTypeClass = String.class),
            @ApiImplicitParam(name = "stageNumber", value = "学习阶段编号",dataTypeClass = Long.class),
            @ApiImplicitParam(name = "serialNumber", value = "内容序号",dataTypeClass = Long.class)
    })
    public RespBean deleteSingle(@RequestParam String studyDirection, @RequestParam Integer stageNumber, @RequestParam Integer serialNumber) {
        if (studyDirection==null||stageNumber==null||serialNumber==null){
            return RespBean.error("删除失败，stageNumber和studyDirection和serialNumber不可以为空");
        }
        StudyRoute studyRoute = iStudyRouteService.selectByStageNumber(studyDirection, stageNumber);
        if (studyRoute != null) {
            if (iStudyRouteContentService.delete(serialNumber, studyRoute.getId())) {
                logger.info("删除成功！");
                return RespBean.ok("删除成功!");
            }
            logger.info("删除失败！");
            return RespBean.error(String.format("删除失败，未找到%s学习路线的第%d阶段的第%d条内容", studyDirection, stageNumber, serialNumber));
        }
        return RespBean.error(String.format("删除失败，未找到%s学习路线的第%d阶段", studyDirection, stageNumber));
    }
    @DeleteMapping(value = "/studyRouteContentBackerGround/deleteByStageNumber")
    @ApiOperation("根据学习路线、阶段编号删除指定阶段所有内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studyDirection", value = "学习方向",dataTypeClass = String.class),
            @ApiImplicitParam(name = "stageNumber", value = "阶段编号",dataTypeClass = Long.class)
    })
    public RespBean deleteByStageNumber(@RequestParam String studyDirection, @RequestParam Integer stageNumber) {
        if (studyDirection==null||stageNumber==null){
            return RespBean.error("删除失败，stageNumber和studyDirection不可以为空");
        }
        if (iStudyRouteService.deleteByNumber(studyDirection, stageNumber)) {
            logger.info("删除成功");
            return RespBean.ok("删除成功！");
        }
        logger.info("删除失败！");
        return RespBean.error(String.format("删除失败，未找到%s方向的第%d阶段", studyDirection, stageNumber));
    }

    @DeleteMapping(value = "/studyRouteContentBackerGround/deleteByStudyDirection")
    @ApiOperation("根据学习方向删除指定方向的所有内容")
    @ApiImplicitParam(name = "studyDirection", value = "学习方向",dataTypeClass = String.class)
    public RespBean deleteByStudyDirection(@RequestParam String studyDirection) {
        if (studyDirection==null){
            return RespBean.error("删除失败，studyDirection不可以为空");
        }
        if (iStudyRouteService.deleteByDirection(studyDirection)) {
            logger.info("删除成功");
            return RespBean.ok("删除成功！");
        }
        logger.info("删除失败！");
        return RespBean.error(String.format("删除失败，未找到%s方向", studyDirection));

    }

    @PutMapping(value = "/studyRouteContentBackerGround/update")
    @ApiOperation("更新指定条学习路线内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studyContent", value = "学习路线内容",dataTypeClass = String.class),
            @ApiImplicitParam(name = "serialNumber", value = "内容序号",dataTypeClass = Long.class),
            @ApiImplicitParam(name = "studyDirection", value = "学习路线",dataTypeClass = String.class),
            @ApiImplicitParam(name = "stageNumber", value = "学习阶段编号",dataTypeClass = Long.class)
    })
    public RespBean update(@Valid StudyRouteContent studyRouteContent, BindingResult bindingResult, @RequestParam String studyDirection, @RequestParam Integer stageNumber) {
        if (bindingResult.hasErrors()){
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
        StudyRoute studyRoute = iStudyRouteService.selectByStageNumber(studyDirection, stageNumber);
        if (studyRoute != null) {
            studyRouteContent.setDirectionId(studyRoute.getId());
            if (iStudyRouteContentService.update(studyRouteContent)) {
                logger.info("更新成功");
                return RespBean.ok("更新成功！");
            } else {
                logger.info("更新失败");
                return RespBean.error(String.format("删除失败，未找到%s学习路线的第%d阶段的第%d条内容", studyDirection, stageNumber, studyRouteContent.getSerialNumber()));
            }
        }
        logger.info("更新失败！");
        return RespBean.error(String.format("删除失败，未找到%s学习路线的第%d阶段", studyDirection, stageNumber));
    }

    @GetMapping(value = "/studyRouteContentBackerGround/selectSingle")
    @ApiOperation("根据学习路线,学习阶段编号,内容序号 查询指定条内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studyDirection", value = "学习路线",dataTypeClass = String.class),
            @ApiImplicitParam(name = "stageNumber", value = "学习阶段编号",dataTypeClass = Long.class),
            @ApiImplicitParam(name = "serialNumber", value = "内容序号",dataTypeClass = Long.class)
    })
    public RespBean selectSingle(@RequestParam String studyDirection, @RequestParam Integer stageNumber, @RequestParam Integer serialNumber) {
        if (studyDirection==null||stageNumber==null||serialNumber==null){
            return RespBean.error("查询失败，stageNumber和studyDirection和serialNumber不可以为空");
        }
        StudyRoute studyRoute = iStudyRouteService.selectByStageNumber(studyDirection, stageNumber);
        if (studyRoute != null) {
            StudyRouteContent studyRouteContent = iStudyRouteContentService.selectSingle(serialNumber, studyRoute.getId());
            if (studyRouteContent != null) {
                logger.info("查询成功！");
                return RespBean.ok("查询成功", studyRouteContent);
            } else {
                logger.info("查询失败！");
                return RespBean.error(String.format("查询失败，未找到%s学习路线的第%d阶段的第%d条内容", studyDirection, stageNumber, serialNumber));
            }
        }
        logger.info("查询失败");
        return RespBean.error(String.format("查询失败，未找到%s学习路线的第%d阶段", studyDirection, stageNumber));
    }

    @GetMapping(value = "/studyRouteContentBackerGround/selectByDirectionId")
    @ApiOperation("根据学习路线，学习阶段编号查询指定阶段的所有内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studyDirection", value = "学习路线",dataTypeClass = String.class),
            @ApiImplicitParam(name = "stageNumber", value = "学习阶段编号",dataTypeClass = Long.class)
    })
    public RespBean selectByDirectionId(@RequestParam String studyDirection, @RequestParam Integer stageNumber) {
        if (studyDirection==null||stageNumber==null){
            return RespBean.error("删除失败，stageNumber和studyDirection不可以为空");
        }
        StudyRoute studyRoute = iStudyRouteService.selectByStageNumber(studyDirection, stageNumber);
        if (studyRoute != null) {
            List<StudyRouteContent> list = iStudyRouteContentService.selectByDirectionId(studyRoute.getId());
            if (list != null) {
                logger.info("查询成功！");
                return RespBean.ok("查询成功", list);
            } else {
                logger.info("查询失败！");
                return RespBean.error(String.format("删除失败，未找到%s学习路线的第%d阶段", studyDirection, stageNumber));
            }
        }
        logger.info("查询失败！");
        return RespBean.error(String.format("删除失败，未找到%s学习路线的第%d阶段", studyDirection, stageNumber));
    }

    @GetMapping(value = "/studyRouteContentBackerGround/selectAll")
    @ApiOperation("查询 学习路线内容表 中的所有内容")
    public RespBean selectAll() {
        List<StudyRouteContent> list = iStudyRouteContentService.selectAll();
        if (list != null) {
            logger.info("查询成功！");
            return RespBean.ok("查询成功！", list);
        }
        logger.info("查询失败！");
        return RespBean.error("查询无数据");
    }

    @GetMapping(value = "/studyRouteContentBackerGround/selectByPage")
    @ApiOperation("分页查询学习路线内容表的所有内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "页码数",dataTypeClass = Long.class),
            @ApiImplicitParam(name = "pageSize", value = "每页容量",dataTypeClass = Long.class)
    })
    public RespBean selectByPage(@RequestParam Integer pageNumber,@RequestParam Integer pageSize) {
        if (pageNumber==null||pageSize==null){
            return RespBean.error("删除失败，pageNumber和pageSize不可以为空");
        }
        IPage<StudyRouteContent> iPage = iStudyRouteContentService.selectByPage(pageNumber, pageSize);
        if (iPage != null) {
            logger.info("查询成功");
            return RespBean.ok("查询成功", iPage);
        }
        logger.info("查询失败！");
        return RespBean.error("查询无数据");
    }
}

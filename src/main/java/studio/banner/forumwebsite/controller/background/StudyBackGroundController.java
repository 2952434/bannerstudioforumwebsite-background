package studio.banner.forumwebsite.controller.background;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.bean.StudyRouteBean;
import studio.banner.forumwebsite.service.IStudyRouteService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Ljx
 * @Date: 2022/3/5 23:38
 * @role:
 */
@RestController
@Api(tags = "后台学习计划接口", value = "StudyBackGroundController")
@RequestMapping("/backGround")
public class StudyBackGroundController {

    protected static final Logger logger = LoggerFactory.getLogger(StudyBackGroundController.class);
    @Autowired
    private IStudyRouteService iStudyRouteService;


    @PostMapping("/insertStudyRoute")
    @ApiOperation(value = "增加学习路线", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id",
                    value = "主键id", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "studyDirection",
                    value = "学习方向", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "studyContent",
                    value = "学习路线内容", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "studyTitle",
                    value = "学习路线标题", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "publishTime",
                    value = "发表时间", required = true, dataTypeClass = String.class)
    })
    public RespBean insertStudyRoute(@Valid StudyRouteBean studyRouteBean, BindingResult bindingResult){
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
        return iStudyRouteService.insertStudyRoute(studyRouteBean);
    }


    @GetMapping("/selectAllStudyRoute")
    @ApiOperation(value = "查询所有学习路线", httpMethod = "GET")
    public RespBean selectAllStudyRoute(){
        return iStudyRouteService.selectAllStudyRoute();
    }

    @GetMapping("/selectStudyRouteByDirection")
    @ApiOperation(value = "根据用户方向和id查询学习计划", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "direction",
                    value = "用户方向", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "userId",
                    value = "用户id", required = true, dataTypeClass = Integer.class)
    })
    public RespBean selectStudyRouteByDirection(String direction,Integer userId){

        return iStudyRouteService.selectStudyRouteByDirection(direction,userId);
    }


    @DeleteMapping("/deleteStudyRouteById")
    @ApiOperation(value = "根据id删除学习路线",httpMethod = "DELETE")
    @ApiImplicitParam(paramType = "query", name = "id",
            value = "主键id", required = true, dataTypeClass = Integer.class)
    public RespBean deleteStudyRouteById(Integer id){
        return iStudyRouteService.deleteStudyRouteById(id);
    }

    @GetMapping("/selectStudyRouteById")
    @ApiOperation(value = "根据id查询学习路线",httpMethod = "GET")
    @ApiImplicitParam(paramType = "query", name = "id",
            value = "主键id", required = true, dataTypeClass = Integer.class)
    public RespBean selectStudyRouteById(Integer id){
        return iStudyRouteService.selectStudyRouteById(id);
    }

    @PostMapping("/updateStudyRoute")
    @ApiOperation(value = "更新学习路线",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id",
                    value = "主键id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "studyDirection",
                    value = "学习方向", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "studyContent",
                    value = "学习路线内容", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "studyTitle",
                    value = "学习路线标题", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "publishTime",
                    value = "发表时间", required = true, dataTypeClass = String.class)
    })
    public RespBean updateStudyRoute(@Valid StudyRouteBean studyRouteBean,BindingResult bindingResult){
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
        return iStudyRouteService.updateStudyRoute(studyRouteBean);
    }
}

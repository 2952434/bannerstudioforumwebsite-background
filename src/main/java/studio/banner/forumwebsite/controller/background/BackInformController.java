package studio.banner.forumwebsite.controller.background;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import studio.banner.forumwebsite.bean.InformBean;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.service.IInformService;

/**
 * @Author: Ljx
 * @Date: 2021/12/19 14:12
 * @role:
 */
@RestController
@Api(tags = "后台通知接口", value = "BackInformController")
@RequestMapping("/backGround")
public class BackInformController {

    @Autowired
    private IInformService informService;

    @PostMapping("/inertInform")
    @ApiOperation(value = "添加通知")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "informId",
                    value = "通知id", dataTypeClass = Integer.class),
            @ApiImplicitParam(type = "query", name = "informTitle",
                    value = "通知标题", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(type = "query", name = "informContent",
                    value = "通知内容", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(type = "query", name = "informTime",
                    value = "通知时间", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(type = "query", name = "informAuthor",
                    value = "通知作者", required = true, dataTypeClass = String.class)
    })
    public RespBean inertInform(InformBean informBean) {
        boolean insertInform = informService.insertInform(informBean);
        if (insertInform) {
            return RespBean.ok("添加成功！！！");
        } else {
            return RespBean.error("添加失败！！！");
        }
    }

    @GetMapping("/selectAllInform/{page}")
    @ApiOperation(value = "分页查询所有通知", notes = "页数不为空")
    @ApiImplicitParam(type = "query", name = "page",
            value = "分页页数", required = true, dataTypeClass = Integer.class)
    public RespBean selectAllInform(@PathVariable("page") Integer page) {
        IPage<InformBean> informBeanIPage = informService.selectInform(page);
        if (informBeanIPage.getSize() != 0) {
            return RespBean.ok("查询成功！！！", informBeanIPage);
        } else {
            return RespBean.error("查询失败！！！");
        }
    }

    @GetMapping("/selectInformById/{id}")
    @ApiOperation(value = "通过通知id查询通知")
    @ApiImplicitParam(type = "query", name = "id",
            value = "通知id", required = true, dataTypeClass = Integer.class)
    public RespBean selectInformById(@PathVariable("id") Integer id) {
        InformBean informBean = informService.selectInformById(id);
        if (informBean == null) {
            return RespBean.error("查询失败，未找到该通知");
        } else {
            return RespBean.ok("查询成功", informBean);
        }
    }

    @PostMapping("/updateInformById")
    @ApiOperation(value = "更新通知信息")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "informId",
                    value = "通知id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(type = "query", name = "informTitle",
                    value = "通知标题", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(type = "query", name = "informContent",
                    value = "通知内容", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(type = "query", name = "informTime",
                    value = "通知时间", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(type = "query", name = "informAuthor",
                    value = "通知作者", required = true, dataTypeClass = String.class)
    })
    public RespBean updateInformById(InformBean informBean) {
        boolean updateInformById = informService.updateInformById(informBean);
        if (updateInformById) {
            return RespBean.ok("更新成功！！！");
        } else {
            return RespBean.error("更新失败,未找到该通知！！！");
        }
    }

    @DeleteMapping("/deleteInformById/{id}")
    @ApiOperation(value = "根据id删除通知", notes = "id需存在")
    @ApiImplicitParam(type = "query", name = "id",
            value = "通知id", required = true, dataTypeClass = Integer.class)
    public RespBean deleteInformById(@PathVariable("id") Integer id) {
        boolean deleteInformById = informService.deleteInformById(id);
        if (deleteInformById) {
            return RespBean.ok("删除成功!!!");
        } else {
            return RespBean.error("未找到该通知，删除失败!!!");
        }
    }
}

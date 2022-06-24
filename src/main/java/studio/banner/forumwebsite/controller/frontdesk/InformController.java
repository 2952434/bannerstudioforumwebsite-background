package studio.banner.forumwebsite.controller.frontdesk;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.banner.forumwebsite.bean.InformBean;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.service.IInformService;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2021/12/19 15:09
 * @role:
 */
@RestController
@Api(tags = "前台通知接口", value = "BackInformController")
@RequestMapping("/frontDesk")
public class InformController {

    @Autowired
    private IInformService informService;
    @GetMapping("/selectInformNum")
    @ApiOperation(value = "查询通知总数量",httpMethod = "GET")
    public RespBean selectInformNum() {
        return informService.selectInformNum();
    }

    @GetMapping("/selectAllInform/{page}/{size}")
    @ApiOperation(value = "分页查询所有通知", notes = "页数不为空")
    @ApiImplicitParam(type = "query", name = "page",
            value = "分页页数", required = true, dataTypeClass = Integer.class)
    public RespBean selectAllInform(@PathVariable Integer page,@PathVariable Integer size) {
        IPage<InformBean> informBeanIPage = informService.selectInform(page,size);
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
    public RespBean selectInformById(@PathVariable Integer id) {
        InformBean informBean = informService.selectInformById(id);
        if (informBean == null) {
            return RespBean.error("查询失败，未找到该通知");
        } else {
            return RespBean.ok("查询成功", informBean);
        }
    }

    @GetMapping("/selectAllInform")
    @ApiOperation(value = "查询所有通知")
    public RespBean selectAllInform(){
        List<InformBean> informBeans = informService.selectAllInform();
        if (informBeans.size()==0){
            return RespBean.error("查询失败");
        }
        return RespBean.ok("查询成功",informBeans);
    }
}

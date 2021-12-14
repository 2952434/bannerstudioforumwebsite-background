package studio.banner.forumwebsite.controller.background;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.config.MyHttpSessionListener;
import studio.banner.forumwebsite.service.impl.ListenerServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author: Ljx
 * @Date: 2021/5/13 22:02
 */
@RestController
@Api(tags = "后台统计在线人数", value = "ListenerBackGroundController")
public class ListenerBackGroundController {

    /**
     * 查询在线人数
     */
    @ApiOperation(value = "查询在线人数", httpMethod = "GET")
    @GetMapping("/listenerBackGround/online")
    public RespBean online() {
        return RespBean.ok("当前在线人数：" + MyHttpSessionListener.online + "人");
    }

}

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
@Api(tags = "统计在线人数", value = "ListenerController")
public class ListenerController {
    private static final Logger logger = LoggerFactory.getLogger(ListenerController.class);
    /**
     * 登录
     */
    @Autowired
    private ListenerServiceImpl listenerService;

    @ApiOperation(value = "用户登录", notes = "用户对象不能为空", httpMethod = "POST")
    @PostMapping("/login")
    public RespBean getUser(Integer username, String password, HttpSession session) {
        session.setMaxInactiveInterval(60*30);
            if ( password.equals(listenerService.selectAllUser(username).get(0).getMemberPassword())){
                logger.info("用户【"+username+"】登陆开始！");
                session.setAttribute("loginName",username);
                logger.info("用户【"+username+"】登陆成功！");
                return RespBean.ok("用户【"+username+"】登陆成功！");
            }
        logger.info("用户【"+username+"】登录失败！");
        return RespBean.error("用户【"+username+"】登录失败！");
    }
    /**
     *查询在线人数
     */
    @ApiOperation(value = "查询在线人数", httpMethod = "GET")
    @GetMapping("/online")
    public RespBean online() {
        return  RespBean.ok("当前在线人数：" + MyHttpSessionListener.online + "人");
    }
    /**
     * 退出登录
     */
    @ApiOperation(value = "退出登录", httpMethod = "GET")
    @GetMapping ("/logout")
    public RespBean logout(HttpServletRequest request) {
        logger.info("用户退出登录开始！");
        HttpSession session = request.getSession(false);
        if(session != null){
            session.removeAttribute("loginName");
            session.invalidate();
        }
        logger.info("用户退出登录结束！");
        return RespBean.ok("退出成功");
    }
    /**
     * 判断session是否有效
     * @param httpServletRequest
     * @return String
     */
    @ApiOperation(value = "判断session是否有效",httpMethod = "GET")
    @GetMapping("/getSession")
    public RespBean getSession(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        Integer loginName = (Integer) session.getAttribute("loginName");
        if (loginName != null) {
            return RespBean.ok("session有效");
        }
        return null;
    }
}

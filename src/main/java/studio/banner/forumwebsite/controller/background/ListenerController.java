package studio.banner.forumwebsite.controller.background;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.config.MyHttpSessionListener;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author: Ljx
 * @Date: 2021/5/13 22:02
 */
@RestController
@Api(tags = "收藏接", value = "CollectController")
public class ListenerController {
    private static final Logger logger = LoggerFactory.getLogger(ListenerController.class);
    /**
     * 登录
     */
    @ApiOperation(value = "用户增加", notes = "用户对象不能为空", httpMethod = "POST")
    @PostMapping("/Login")
    public void getUserByUserNameAndPassword(String username, String password, HttpSession session) {
        logger.info("用户【"+username+"】登陆开始！");
        if("admin".equals(username) && "123456".equals(password)){
            session.setAttribute("loginName",username);
            logger.info("用户【"+username+"】登陆成功！");
        }else{
            logger.info("用户【"+username+"】登录失败！");
        }
    }
    /**
     *查询在线人数
     */
    @RequestMapping("/online")
    public Object online() {
        return  "当前在线人数：" + MyHttpSessionListener.online + "人";
    }
    /**
     * 退出登录
     */
    @RequestMapping("/Logout")
    public RespBean logout(HttpServletRequest request) {
        logger.info("用户退出登录开始！");
        //防止创建Session
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
     * @return
     */
    @RequestMapping("/getSession")
    public String getSession(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        String loginName = (String) session.getAttribute("loginName");
        if (StringUtils.isNotBlank(loginName)) {
            return "200";
        }
        return "";
    }


}

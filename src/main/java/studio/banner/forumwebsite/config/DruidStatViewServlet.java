package studio.banner.forumwebsite.config;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * @Author: Ljx
 * @Date: 2021/5/15 17:54
 */
@WebServlet(urlPatterns = "/druid/*", initParams = {
        // IP白名单 (没有配置或者为空，则允许所有访问)
        @WebInitParam(name = "allow", value = "qqzsh.top,127.0.0.1"),
        // IP黑名单 (存在共同时，deny优先于allow)
        @WebInitParam(name = "deny", value = "qqzsh.top"),
        // 用户名
        @WebInitParam(name = "loginUsername", value = "admin"),
        // 密码
        @WebInitParam(name = "loginPassword", value = "admin"),
        // 禁用HTML页面上的“Reset All”功能
        @WebInitParam(name = "resetEnable", value = "false") })
@SuppressWarnings("serial")
public class DruidStatViewServlet extends StatViewServlet {
}


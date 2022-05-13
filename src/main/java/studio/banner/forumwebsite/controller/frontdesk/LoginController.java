package studio.banner.forumwebsite.controller.frontdesk;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import studio.banner.forumwebsite.bean.AuthUser;
import studio.banner.forumwebsite.bean.FixedInformationBean;
import studio.banner.forumwebsite.bean.MemberInformationBean;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.service.IFixedInformationService;
import studio.banner.forumwebsite.service.IMemberInformationService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author: Ljx
 * @Date: 2022/3/20 8:51
 * @role:
 */
@RestController
@Api(tags = "用户授权登录", value = "LoginController")
@RequestMapping("/frontDesk/login")
public class LoginController {

    public String access_token = "";
    public String refresh_token = "";
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private IMemberInformationService iMemberInformationService;
    @Autowired
    private IFixedInformationService iFixedInformationService;

    @GetMapping("/getInformation")
    @ApiOperation(value = "访问授权服务器信息初始化资源服务器用户信息")
    @Transactional(rollbackFor = Exception.class)
    public RespBean getInformationByUserName(HttpServletRequest request) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(username);
        FixedInformationBean fixedInformationBean = iFixedInformationService.selectUsersInformationByAccount(username);
        if (fixedInformationBean != null) {
            return RespBean.ok("用户信息已存在", fixedInformationBean.getUserId());
        }
        String header = request.getHeader("Authorization");
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", header);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<AuthUser> entity = restTemplate.exchange("https://oauth.bannerstudio.club/admin/getInformation/" + username, HttpMethod.GET, httpEntity, AuthUser.class);
        AuthUser authUser = entity.getBody();
//        更新数据库操作
        if (authUser == null) {
            return RespBean.error("登录失败，未查询到该用户信息");
        } else {
            iFixedInformationService.insertUsersInformation(new FixedInformationBean(authUser, username));
            iMemberInformationService.insertUserMsg(new MemberInformationBean(authUser));
        }
        return RespBean.ok("数据库更新成功", authUser.getId());
    }

    @GetMapping("/selectUserRole")
    @ApiOperation(value = "返回用户权限", httpMethod = "GET")
    public RespBean selectUserRole() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().toString());
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        return RespBean.ok(authorities.toArray()[0]);
    }


    @DeleteMapping("/clearCookie/{path}")
    @ApiOperation(value = "清除cook", httpMethod = "DELETE")
    @ApiImplicitParam(paramType = "query", name = "path",
            value = "网址", required = true, dataTypeClass = String.class)
    public static void clearCookie(HttpServletRequest request, HttpServletResponse response, @PathVariable String path) {
        Cookie[] cookies = request.getCookies();
        try {
            for (int i = 0; i < cookies.length; i++) {
                //System.out.println(cookies[i].getName() + ":" + cookies[i].getValue());
                Cookie cookie = new Cookie(cookies[i].getName(), null);
                cookie.setMaxAge(0);
                cookie.setPath(path);//根据你创建cookie的路径进行填写
                response.addCookie(cookie);
            }
        } catch (Exception ex) {
            System.out.println("清空Cookies发生异常！");
        }

    }
}

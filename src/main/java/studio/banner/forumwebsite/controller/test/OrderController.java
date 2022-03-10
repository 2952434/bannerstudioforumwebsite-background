package studio.banner.forumwebsite.controller.test;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.banner.forumwebsite.bean.AuthUser;

/**
 * @author Administrator
 * @version 1.0
 **/
@RestController
@RequestMapping("/frontDesk")
public class OrderController {

    @GetMapping(value = "/r1")
//    @PreAuthorize("hasAuthority('IntranetUser')")//拥有p1权限方可访问此url
    public String r1(){
        //获取用户身份信息
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return username+"访问资源1";
    }

}
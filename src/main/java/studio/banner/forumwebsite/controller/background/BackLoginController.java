package studio.banner.forumwebsite.controller.background;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import studio.banner.forumwebsite.bean.RespBean;

import java.util.Collection;
import java.util.Map;

/**
 * @Author: Ljx
 * @Date: 2022/3/20 8:51
 * @role:
 */
@RestController
@Api(tags = "管理员授权登录", value = "BackLoginController")
@RequestMapping("/login")
public class BackLoginController {

    public String access_token = "";
    public String refresh_token = "";
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/backGround/callback/{code}")
    @ApiOperation(value = "回调地址",httpMethod = "GET")
    public RespBean callback(@PathVariable String code){
        if ("".equals(access_token) && code != null) {
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("code", code);
            map.add("client_id", "bannerstudioofficialwebsite");
            map.add("client_secret", "bannerstudioofficialwebsite");
            map.add("redirect_uri", "http://localhost:8081/transfer");
            map.add("grant_type", "authorization_code");
            Map<String, String> resp = restTemplate.postForObject("https://oauth.bannerstudio.club/oauth/token", map, Map.class);
            access_token = resp.get("access_token");
            System.out.println(access_token);
            refresh_token = resp.get("refresh_token");
            return RespBean.ok("token获取成功",access_token);
        } else {
            return RespBean.error("token获取失败");
        }
    }




}

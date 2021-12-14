package studio.banner.forumwebsite.controller.test;

//import org.springframework.security.access.annotation.Secured;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Ljx
 * @Date: 2021/12/8 12:20
 * @role:
 *
 * test security
 */
@RestController
@Api(tags = "测试security",value = "ProductController")
public class ProductController {


//    @Secured("admin")
    @RequestMapping("/product/findAll")
    public String findAll(){
        return "列表查询成功！！！";
    }
}

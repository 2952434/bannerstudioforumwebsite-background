package studio.banner.forumwebsite.controller.test;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/** test websocket
 * @author jijunxiang
 */
@Controller
@Api(tags = "测试websocket", value = "IndexController")
public class IndexController {

    @GetMapping(value = "/toIndex")
    public String toIndexPage() {
        return "index1";
    }

    @GetMapping(value = "/")
    public String toIndexPageDirectly() {
        return "index1";
    }


}

package studio.banner.forumwebsite.controller.frontdesk;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.banner.forumwebsite.bean.RespBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HYK
 * @Date: 2021/05/29/15:31
 * @Description: 得到目前时间
 */
@Api(tags = "前台得到当前时间接口", value = "GetTimeFrontDeskController")
@RestController
public class GetTimeFrontDeskController {
    /**
     * 得到当前时间
     *
     * @return
     */

    @GetMapping("/getTimeFrontDesk/getTime")
    public RespBean getTime() {

        SimpleDateFormat bjSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        bjSdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        Date date = new Date();
        String time = bjSdf.format(date);
        return RespBean.ok("获取成功", time);
    }
}

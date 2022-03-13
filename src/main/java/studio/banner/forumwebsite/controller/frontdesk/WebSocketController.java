package studio.banner.forumwebsite.controller.frontdesk;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.banner.forumwebsite.bean.Message;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.service.IMessageService;

import java.util.List;
import java.util.Map;

/**
 * test websocket
 *
 * @author jijunxiang
 */
@RestController
@Api(tags = "websocket接口", value = "WebSocketController")
@RequestMapping("/frontDesk")
public class WebSocketController {
    @Autowired
    private IMessageService messageService;

    @GetMapping("/IndexController/queryMessageList")
    @ApiOperation(value = "查询聊天记录", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "fromId",
                    value = "发送人id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(type = "query", name = "toId",
                    value = "接收人id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(type = "query", name = "page",
                    value = "页数", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(type = "query", name = "rows",
                    value = "每页的信息条数", required = true, dataTypeClass = Integer.class)
    })
    public RespBean queryMessageList(Integer fromId, Integer toId, Integer page, Integer rows) {
        List<Message> messages = messageService.queryMessageList(fromId, toId, page, rows);
        return RespBean.ok(messages);
    }


    @GetMapping("/IndexController/queryUserList")
    @ApiOperation(value = "查询用户列表页面", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "userId",
                    value = "用户id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(type = "query", name = "page",
                    value = "页数", required = true, dataTypeClass = Integer.class)
    })
    public RespBean queryUserList(Integer userId, Integer page) {
        List<Map<String, Object>> maps = messageService.selectUserList(userId, page);
        return RespBean.ok(maps);
    }

    @PostMapping("/IndexController/saveMessage")
    @ApiOperation(value = "发送信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "userId",
                    value = "发送人id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(type = "query", name = "toId",
                    value = "接收人id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(type = "query", name = "msg",
                    value = "发送的信息", required = true, dataTypeClass = String.class)
    })
    public RespBean saveMessage(Integer userId, Integer toId, String msg) {
        Message saveMessage = messageService.saveMessage(userId, toId, msg);
        if (saveMessage != null) {
            return RespBean.ok("发送成功！！！");
        } else {
            return RespBean.error("发送失败！！！");
        }
    }
}

package studio.banner.forumwebsite.ws;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import studio.banner.forumwebsite.bean.Message;
import studio.banner.forumwebsite.bean.UserData;
import studio.banner.forumwebsite.service.IMessageService;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Ljx
 * @Date: 2021/12/10 14:03
 * @role:
 */
@Component
public class MessageHandler extends TextWebSocketHandler {
    @Autowired
    private IMessageService messageService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final Map<Integer, WebSocketSession> SESSION = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Integer uid = (Integer) session.getAttributes().get("uid");
        //将当前用户的session放置到map中，后面会使用相应的session通信
        SESSION.put(uid,session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        Integer uid = (Integer) session.getAttributes().get("uid");

        JsonNode jsonNode = MAPPER.readTree(textMessage.getPayload());
        int toId = jsonNode.get("toId").asInt();
        String msg = jsonNode.get("msg").asText();

        Message message = Message.builder()
                .from(UserData.USER_MAP.get(uid))
                .to(UserData.USER_MAP.get(toId))
                .msg(msg)
                .build();
        //将消息保存到MongoDB
        message = this.messageService.saveMessage(message);

//        判断to用户是否在线
        WebSocketSession toSession = SESSION.get(toId);
        if (toSession != null&&toSession.isOpen()){
            //TODO 具体格式需要和前端对接
            toSession.sendMessage(new TextMessage(MAPPER.writeValueAsString(message)));

            //更新消息为已读
            this.messageService.updateMessageState(message.getId(),2);

        }
    }
}

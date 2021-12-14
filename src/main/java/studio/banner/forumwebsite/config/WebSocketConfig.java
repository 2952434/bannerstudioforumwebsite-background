package studio.banner.forumwebsite.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import studio.banner.forumwebsite.ws.MessageHandler;
import studio.banner.forumwebsite.ws.MessageHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private MessageHandler messageHandler;
    @Autowired
    private MessageHandshakeInterceptor messageHandshakeInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(this.messageHandler,"/ws/{uid}")
                .setAllowedOrigins("*")
                .addInterceptors(this.messageHandshakeInterceptor);
    }
}

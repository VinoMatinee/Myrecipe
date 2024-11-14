package dev.mvc.team3;

import dev.mvc.chat.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSockChatHandler webSocketChatHandler;

    public WebSocketConfig(WebSockChatHandler webSocketChatHandler) {
        this.webSocketChatHandler = webSocketChatHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketChatHandler, "/ws/chat")
                .setAllowedOrigins("*"); // CORS 설정
    }
    
    @Bean
    public WebSockChatHandler webSocketHandler() {
        return new WebSockChatHandler();
    }
}

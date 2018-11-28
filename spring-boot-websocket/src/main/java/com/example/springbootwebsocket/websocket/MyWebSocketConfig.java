package com.example.springbootwebsocket.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@EnableWebSocket
public class MyWebSocketConfig implements WebSocketConfigurer {

    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry
                .addHandler(myTextWebSocketHandler(), "/zhangwei/webSocket")
                .addInterceptors(myHttpSessionHandshakeInterceptor());
                //.setAllowedOrigins("http://mydomain.com");
                 //可指定多个跨域，如果无需限制可使用 *
    }

    //用于定义 WebSocket 的消息处理
    @Bean
    public WebSocketHandler myTextWebSocketHandler() {
        return new MyTextWebSocketHandler();
    }

    //用于处理 WebSocket 连接, 连接前后
    @Bean
    public HandshakeInterceptor myHttpSessionHandshakeInterceptor(){
        return new MyHttpSessionHandshakeInterceptor();
    }

//    @Bean
//    public ServerEndpointExporter serverEndpointExporter() {
//        return new ServerEndpointExporter();
//    }

}
package com.example.springbootwebsocket.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

public class MyHttpSessionHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    private static final Logger log = LoggerFactory.getLogger(MyHttpSessionHandshakeInterceptor.class);

    //握手前
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        log.info("Before Handshake");
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            String username = servletRequest.getServletRequest().getParameter("username");
            //必须提供 username 请求参数，否则不允许连接
            if (null == username || username.equals(""))
                return false;
            attributes.put(MyTextWebSocketHandler.WEB_SOCKET_USERNAME, username);
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    //握手后
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
        log.info("After Handshake");
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
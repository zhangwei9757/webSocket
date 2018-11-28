package com.example.springbootwebsocket.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyTextWebSocketHandler extends TextWebSocketHandler {

    public static final String WEB_SOCKET_USERNAME = "webSocketUsername";

    private static final Map<String, WebSocketSession> users = new ConcurrentHashMap<>();
    private static final Logger log = LoggerFactory.getLogger(MyTextWebSocketHandler.class);

    //处理文本消息
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        log.info("用户 " + session.getAttributes().get(WEB_SOCKET_USERNAME) + " 发来一条信息：" + message);
        try {
            session.sendMessage(new TextMessage("已收到，谢谢"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //连接建立后处理
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        String username = (String) session.getAttributes().get(WEB_SOCKET_USERNAME);
        users.put(username, session);
        log.info("用户 " + username + " 连接 WebSocket 成功......当前用户数量为:" + users.size());
        session.sendMessage(new TextMessage("欢迎光临，热烈欢迎"));
    }

    //连接关闭后处理
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String username = (String) session.getAttributes().get(WEB_SOCKET_USERNAME);
        users.remove(username);
        log.info(username + " 退出......剩余在线用户数量:" + users.size());

        super.afterConnectionClosed(session, status);
    }

    //抛出异常时处理
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
        String username = (String) session.getAttributes().get(WEB_SOCKET_USERNAME);
        if(session.isOpen())
            session.close();
        users.remove(username);
        log.error("Web Socket 发生异常！");
    }


    //是否支持局部消息
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 给某个用户发送消息
     *
     * @param username
     * @param message
     */
    public static void sendMessageToUser(String username, TextMessage message) {
        WebSocketSession user = users.get(username);
        doSendMessage(user, message);
        log.info("给指定用户（" + username + "）发送一条消息：" + message);
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public static void sendMessageToAllUser(TextMessage message) {
        for (WebSocketSession user : users.values()) {
            doSendMessage(user, message);
        }
        log.info("给所有 WebSocket 用户发送一条消息：" + message);
    }

    private static void doSendMessage(WebSocketSession user, TextMessage message){
        try {
            if (null != user && user.isOpen()) {
                user.sendMessage(message);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
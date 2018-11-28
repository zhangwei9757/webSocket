package com.example.springbootwebsocket.servers;

import com.example.springbootwebsocket.websocket.MyTextWebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

@Component
@EnableScheduling
public class AlarmTask {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static int count = 0;

    @Scheduled(cron="0/10 * * * * ?")
    public void executeAlarmCheckTask() {
        //logger.warn("aheeeeeeeeeeeeeeeeee " + ++count);
        //MyTextWebSocketHandler.sendMessageToAllUser(new TextMessage("报警一下：" + count));
    }
}

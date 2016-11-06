package de.florianstendel.apps.websocketexample;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Florian on 25.10.2016.
 */
@ServerEndpoint("/websocket")
public class WebsocketEndpoint {

    private Session session;

    @Inject
    private MessageController messageController;

    @OnMessage
    public void onMessage(String message) {
        this.messageController.broadcastMessage(message);
    }

    @OnOpen
    public void onWebsocketConnect(Session session) {

        this.session = session;
        this.messageController.register(this);

    }

    @OnClose
    public void onWebsocketDisconnect(CloseReason reason) {

        this.messageController.unregister(this);
        this.session = null;
    }


    public void publishMessage(final String text){

        if(session != null){
            try {
                session.getBasicRemote().sendText(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

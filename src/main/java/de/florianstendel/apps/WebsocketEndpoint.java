package de.florianstendel.apps;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
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

    private ScheduledExecutorService executorService;

    private List<Session> connectedSessions;

    public WebsocketEndpoint(){


        connectedSessions = new ArrayList<Session>();


        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                for(Session connectionSession : connectedSessions) {
                    try {
                        connectionSession.getBasicRemote().sendText("Das Leben ist schön:" + new Date());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        },1000L,5000, TimeUnit.MILLISECONDS);

    }


    @OnMessage
    public String hello(String message) {
        System.out.println("Received : "+ message);
        return message;
    }

    @OnOpen
    public void myOnOpen(Session session) {

        System.out.println("WebSocket opened: " + session.getId());
        connectedSessions.add(session);
    }




    @OnClose
    public void myOnClose(Session session, CloseReason reason) {

        System.out.println("Closing a WebSocket due to " + reason.getReasonPhrase());
        connectedSessions.remove(session);
    }
}

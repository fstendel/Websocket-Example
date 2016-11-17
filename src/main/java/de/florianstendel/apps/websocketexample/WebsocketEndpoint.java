package de.florianstendel.apps.websocketexample;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * Class implementing an websocket-endpoint used
 * for publishing messages.
 *
 * @author Florian Stendel
 * @since 1.0
 */
@ServerEndpoint("/websocket")
public class WebsocketEndpoint {

    private Session session;

    @Inject
    private MessageServer messageServer;


    /**
     * Method called when the connected websocket client pushes a message
     * to the endpoint.
     *
     * @param message
     *      String containing the text message.
     */
    @OnMessage
    public void onMessage(String message) {
        this.messageServer.broadcastMessage(message);
    }

    /**
     * Method called when a websocket client connects.
     *
     * @param session
     *      Session data of the connecting websocket client.
     */
    @OnOpen
    public void onWebsocketConnect(Session session) {

        this.session = session;
        this.messageServer.register(this);

    }

    /**
     * Method called when a websocket client disconnects.
     *
     * @param reason
     *      A CloseReason object containing the reason why a websocket client cancelled
     *      or has been ask to cancel the connection.
     *      */
    @OnClose
    public void onWebsocketDisconnect(CloseReason reason) {

        this.messageServer.unregister(this);
        this.session = null;
    }


    /**
     * Method called by other classes to publish a text message via
     * this websocketendpoint instance.
     *
     * @param text
     *      Text to be published.
     */
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

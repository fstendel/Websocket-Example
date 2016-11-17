package de.florianstendel.apps.websocketexample;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * Class implementing a messageserver, where websocket endpoints
 * can register/unregister themselves to send and receive broadcasted messages.
 *
 * @author Florian Stendel
 * @since 1.0
 */
@Singleton
public class MessageServer {

    private List<WebsocketEndpoint> websocketEndpoints = new ArrayList<WebsocketEndpoint>();


    /**
     * Method to broadcast a text message.
     *
     * @param message
     *      Textmessage to be broadcasted.
     */
    public void broadcastMessage(final String message){

        for(WebsocketEndpoint websocketEndpoint: websocketEndpoints){
            websocketEndpoint.publishMessage(message);
        }
    }


    /**
     * Method for registering a websocket endpoint.
     *
     * @param websocketEndpoint
     *      Websocket endpoint to register.
     */
    public void register(final WebsocketEndpoint websocketEndpoint){

        websocketEndpoints.add(websocketEndpoint);
    }

    /**
     * Method to unregister a websocket endpoint.
     *
     * @param websocketEndpoint
     *      Websocket endpoint to unregister.
     */
    public void unregister(final WebsocketEndpoint websocketEndpoint){

        websocketEndpoints.remove(websocketEndpoint);
    }

}

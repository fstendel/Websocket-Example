package de.florianstendel.apps.websocketexample;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Flori on 02.11.2016.
 */
@Singleton
public class MessageController {

    private List<WebsocketEndpoint> websocketEndpoints = new ArrayList<WebsocketEndpoint>();


    public void broadcastMessage(final String message){

        for(WebsocketEndpoint websocketEndpoint: websocketEndpoints){
            websocketEndpoint.publishMessage(message);
        }
    }


    public void register(final WebsocketEndpoint websocketEndpoint){

        websocketEndpoints.add(websocketEndpoint);
    }

    public void unregister(final WebsocketEndpoint websocketEndpoint){


        websocketEndpoints.remove(websocketEndpoint);
    }

}

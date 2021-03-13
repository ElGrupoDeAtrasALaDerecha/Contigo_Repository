/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usa.contibot;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Santiago Pérez
 */
@ServerEndpoint("/contiBot")
public class ContigoBot {

    @OnOpen
    public void onOpen() {
        
        //ws.send(objeto);
    }
    @OnMessage
    public String onMessage(String message) {
        return null;
    }
    @OnError
    public void onError(){
        
    }
    @OnClose
    public void onClose(){
        
    }
}

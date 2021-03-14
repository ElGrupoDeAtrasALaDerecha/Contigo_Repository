/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usa.contibot;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.json.JSONObject;
import usa.modelo.dto.Sala;

/**
 * Clase Websocket contigo Bot
 * @author Valeria Bermúdez y Santiago Pérez
 * @since 2020-03-09
 * @version 0.0.1
 */
@ServerEndpoint("/contiBot")
public class ContigoBot {

    @OnOpen
    public void onOpen(Session sesion) {
        System.out.println("Abrió "+sesion.getId());
        //ws.send(objeto);
    }
    @OnMessage
    public void onMessage(String mensaje,Session sesion) {
        System.out.println("Mensaje entrante "+mensaje);
        JSONObject obj = new JSONObject(mensaje);
        String tipo = (String) obj.get("tipo");
        JSONObject objRespuesta = new JSONObject();
        Sala sala = null;
        String objeto = "";
        try {
        switch(tipo){
                case "ping":
                    sesion.getBasicRemote().sendText("pong");
                    break;
                case "primer ingreso":
                    objRespuesta.put("tipo","codigo sala");
                    objRespuesta.put("numero", "1");
                    sesion.getBasicRemote().sendText(objRespuesta.toString());
                    break;
                case "mensaje":
                    objRespuesta.put("tipo","respuesta");
                    objRespuesta.put("mensaje","hola");
                    sesion.getBasicRemote().sendText(objRespuesta.toString());
                    break;
        }
            System.out.println(mensaje);
        } catch (IOException ex) {
            Logger.getLogger(ContigoBot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @OnClose
    public void onClose(Session sesion){
        System.out.println("Conexión cerrada "+sesion.getId());
    }
}

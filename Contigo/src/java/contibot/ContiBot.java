package contibot;

import java.net.InetSocketAddress;
import java.util.LinkedList;
import modelo.dto.Sala;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONObject;

/**
 * Servidor
 *
 * @author Valeria Bermúdez -Santiago Pérez
 * @since 2020-03-09
 * @version 0.0.1
 */
public class ContiBot extends WebSocketServer {

    //private LinkedList<Usuario> usuarios;
    private LinkedList<Sala> salas;

    public ContiBot(InetSocketAddress address) {
        super(address);
        //this.usuarios = new LinkedList();
        this.salas = new LinkedList();
    }

    @Override
    public void onOpen(WebSocket ws, ClientHandshake ch) {
        System.out.println("Nuevo usuario conectado: " + ws.getRemoteSocketAddress() + " hash " + ws.getRemoteSocketAddress().hashCode());
        //ws.send(objeto);
    }

    @Override
    public void onClose(WebSocket ws, int p, String razon, boolean bln) {
        System.out.println("Client " + p + " disconnected: " + razon);
        
    }

    @Override
    public void onMessage(WebSocket ws, String mensaje) {
        System.out.println("Mensaje entrante "+mensaje);
        JSONObject obj = new JSONObject(mensaje);
        String tipo = (String) obj.get("tipo");
        Sala sala = null;
        String objeto = "";
        switch(tipo){
                case "ping":
                    ws.send("pong");
        
        } 
    }

    /**
     *
     * @param ws
     * @param excptn
     */
    @Override
    public void onError(WebSocket ws, Exception excptn) {
        System.out.println(excptn);
    }

}
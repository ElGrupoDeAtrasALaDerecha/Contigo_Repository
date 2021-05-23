package usa.adapter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import usa.utils.Correo;
import usa.utils.Utils;

/**
 * 
 * @author Valeria Bermúdez, Laura Blanco, Santiago Cáceres, Camila Fernández,
 * Andrés López, Natalia Montenegro, Santiago Pérez y Miguel Rippe
 */
public class CorreoClaveAdapter extends Email{

    private final CorreoClave correoClave;

    public CorreoClaveAdapter(CorreoClave correoClave) {
        this.correoClave = correoClave;
    }
    
    
    
    @Override
    public void enviarCorreo(String receptor) {
        if(correoClave.validarCodigoExistente(receptor)){
            String link= correoClave.generarLink(Utils.crearCodigoRecuperacion());
            cuerpo="<h1>Recuperación de contraseña</h1>\n" +
                    "En este link podrá recuperar su contraseña:\n" +
                    "link para la recuperación= "+link;
            try {
                Correo.enviarCorreo(receptor, "Recuperación de contraseña", cuerpo);
            } catch (IOException | MessagingException ex) {
                Logger.getLogger(CorreoClaveAdapter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            System.out.println("No se manda el correo");
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

package usa.adapter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import usa.modelo.dto.Cita;
import usa.utils.Correo;

/**
 * @author Valeria Bermúdez, Laura Blanco, Santiago Cáceres, Camila Fernández,
 * Andrés López, Natalia Montenegro, Santiago Pérez y Miguel Rippe
 */
public class CorreoCita extends Email{

    private final Cita CITA;

    public CorreoCita(Cita CITA) {
        this.CITA = CITA;
    }
    
    
    @Override
    public void enviarCorreo(String receptor) {
        cuerpo="<h1>Confirmación de cita</h1>"
                        + "Ha confirmado su cita. \n"
                        + "Día: "+CITA.getFecha()+"\n"
                        + "Hora: "+CITA.getHoraInicio()+":00 \n"
                        + "Lugar: "+CITA.getLugar()+"\n\n"
                        + "Se recomienda estar con 10 minutos de anticipación.";
        try {
            Correo.enviarCorreo(receptor, "Confirmación de cita", cuerpo);
        } catch (IOException | MessagingException ex) {
            Logger.getLogger(CorreoCita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

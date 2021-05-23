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
public class CorreoCita extends Email {

    private final Cita CITA;
    private String estado;

    public CorreoCita(Cita CITA) {
        this.CITA = CITA;
    }

    public CorreoCita(Cita CITA, String estado) {
        this.CITA = CITA;
        this.estado = estado;
    }

    @Override
    public void enviarCorreo(String receptor) {
        try {
            String motivo="";
            if (CITA.getEstado()==2) {
                cuerpo = "<h1>Confirmación de cita</h1>"
                        + "Ha confirmado su cita. \n"
                        + "Día: " + CITA.getFecha() + "\n"
                        + "Hora: " + CITA.getHoraInicio() + ":00 \n"
                        + "Lugar: " + CITA.getLugar() + "\n\n"
                        + "Se recomienda estar con 10 minutos de anticipación.";
                motivo="Confirmación de cita";
            } else if(CITA.getEstado()==4 || CITA.getEstado()==5) {
                cuerpo = "<h1>Actualización de su cita</h1>\n"
                        + "Su cita del día "+CITA.getFecha()+" a las "+CITA.getHoraInicio()+":00"
                        + " ahora está en estado "+estado;
                motivo="Cancelación de cita";
            }
            Correo.enviarCorreo(receptor, motivo, cuerpo);
        } catch (IOException | MessagingException ex) {
            Logger.getLogger(CorreoCita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

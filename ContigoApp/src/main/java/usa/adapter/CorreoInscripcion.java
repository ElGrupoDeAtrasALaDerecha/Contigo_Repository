package usa.adapter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import usa.utils.Correo;

/**
 * @author Valeria Bermúdez, Laura Blanco, Santiago Cáceres, Camila Fernández,
 * Andrés López, Natalia Montenegro, Santiago Pérez y Miguel Rippe
 */
public class CorreoInscripcion extends Email{

    private final String tipoInscripcion;
    public CorreoInscripcion(String tipoInscripcion) {
        this.tipoInscripcion=tipoInscripcion;
    }

    
    @Override
    public void enviarCorreo(String receptor) {
        switch (tipoInscripcion) {
            case "estudiante":
                cuerpo="<h1>Cuenta confirmada</h1>\n" +
                        "¡Muchas gracias por registrarte! <br>\n" +
                        "Ahora cuentas con acceso a:\n" +
                        "\n" +
                        "<ol>\n" +
                        "    <li>\n" +
                        "        Conversatorios: nuestro personal calificado brinda su servicio<br>\n" +
                        "        dando conversatorios acerca de temas particulares\n" +
                        "    </li>\n" +
                        "    <li>\n" +
                        "        Historias de situaciones de decisión: puedes verte inmerso en distintas <br>\n" +
                        "        situaciones en las cuales debes tomar decisiones que te permitirán tener <br>\n" +
                        "        realimentación de ellas.\n" +
                        "    </li>\n" +
                        "    <li>\n" +
                        "        Chat privado: si tienes problemas con algún tema particular, <br>\n" +
                        "        puedes contar con nuestro personal\n" +
                        "    </li>\n" +
                        "</ol>";
                break;
            case "personalCalificado":
                cuerpo="<body>\n" +
                        "    <h1>Cuenta confirmada</h1>\n" +
                        "    ¡Muchas gracias por trabajar con nosostros!:\n" +
                        "        \n" +
                        "</body>";
                break;
            case "institucion":
                cuerpo="<h1>Cuenta confirmada</h1>\n" +
                        "¡Muchas gracias por comprar nuestros servicios!";
                break;
            default:
                break;
        }
        try {
            Correo.enviarCorreo(receptor, "Confirmación de inscripcion", cuerpo);
        } catch (IOException ex) {
            Logger.getLogger(CorreoInscripcion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(CorreoInscripcion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

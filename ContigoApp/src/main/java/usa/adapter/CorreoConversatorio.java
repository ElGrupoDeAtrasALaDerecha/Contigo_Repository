package usa.adapter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import usa.factory.AbstractFactory;
import usa.factory.Producer;
import usa.modelo.dao.IDao;
import usa.modelo.dto.Conversatorio;
import usa.modelo.dto.Estudiante;
import usa.modelo.dto.EstudianteConversatorio;
import usa.modelo.dto.PersonalCalificado;
import usa.modelo.dto.decorador.Biografia;
import usa.modelo.dto.decorador.Especialidad;
import usa.modelo.dto.decorador.IInformacion;
import usa.utils.Correo;

/**
 * @author Valeria Bermúdez, Laura Blanco, Santiago Cáceres, Camila Fernández,
 * Andrés López, Natalia Montenegro, Santiago Pérez y Miguel Rippe
 */
public class CorreoConversatorio extends Email {

    EstudianteConversatorio estudianteTieneConversatorio;

    public CorreoConversatorio(EstudianteConversatorio estudianteTieneConversatorio) {
        this.estudianteTieneConversatorio = estudianteTieneConversatorio;
    }

    @Override
    public void enviarCorreo(String receptor) {
        AbstractFactory factoryDao = Producer.getFabrica("DAO");
        IDao dao =(IDao) factoryDao.obtener("ConversatorioDao");
        Conversatorio c = (Conversatorio) dao.consultar(String.valueOf(estudianteTieneConversatorio.getIdConversatorio()));
        dao = (IDao) factoryDao.obtener("PersonalCalificadoDao");
        PersonalCalificado p = (PersonalCalificado) dao.consultar(c.getOrador());
        
        
        try {
            String motivo = "";
            if (estudianteTieneConversatorio.getEstado() == 1) {
                String infoAdicional="";
                for (IInformacion info : p.getInfo()) {
                    if(info instanceof Biografia){
                        Biografia b = (Biografia) info;
                        infoAdicional+="<p> <b>"
                                + "Biografía: </b> "+b.getBiografia()+"</p>";
                    }else if(info instanceof Especialidad){
                        Especialidad esp = (Especialidad) info;
                        infoAdicional +="<p><b>"
                                + "Especialidad: </b> "+esp.getEspecialidad()+"</p>";
                    }
                }
                cuerpo = ""
                        + "<body>\n"
                        + "\n"
                        + "    <div class=\"ui grid\">\n"
                        + "        <div class=\"ui row\">\n"
                        + "            <div class=\"ui sixteen grey inverted wide column\">\n"
                        + "                <h1>¡Muchas gracias por registrarte!</h1>\n"
                        + "\n"
                        + "                Esperamos que el conversatorio te sea de amplio aprendizaje\n"
                        + "            </div>\n"
                        + "        </div>\n"
                        + "        <div class=\"ui row\">\n"
                        + "            <div class=\"ui sixteen grey inverted wide column\">\n"
                        + "                <div class=\"ui segment\">\n"
                        + "                    <div class=\"ui items\">\n"
                        + "                        <div class=\"item\">\n"
                        + "                            <div class=\"image\">\n"
                        //+ "                                <img src=\""+p.getImagen()+">\n"
                        + "                            </div>\n"
                        + "                            <div class=\"content\">\n"
                        + "                                <a class=\"header\">"+c.getTitulo()+"</a>\n"
                        + "                                <div class=\"meta\">\n"
                        + "                                    <span>Por <a>"+p.getPrimerNombre()+" "+p.getPrimerApellido()+"</a></span>\n"
                        + "                                </div>\n"
                        + "                                <div class=\"description\">\n"
                        + "                                    "+infoAdicional
                        + "                                </div>\n"
                        + "                                <div class=\"extra\">\n"
                        + "                                    <b>Lugar: </b> "+c.getLugar()
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                        </div>\n"
                        + "                        \n"
                        + "                    </div>\n"
                        + "                </div>\n"
                        + "            </div>\n"
                        + "        </div>\n"
                        + "        <div class=\"ui row\">\n"
                        + "            <div class=\"ui sixteen grey inverted wide column\">\n"
                        + "                <h3>¡No te pierdas el conversatorio!</h3>\n"
                        + "            </div>\n"
                        + "        </div>\n"
                        + "        <div class=\"ui row\">\n"
                        + "            <div class=\"ui sixteen wide column\">\n"
                        + "                <h6>Recuerda que puedes cancelar la inscripción en cualquier momento en la página del conversatorio</h6> \n"
                        + "            </div>\n"
                        + "        </div>\n"
                        + "\n"
                        + "    </div>\n"
                        + "\n"
                        + "</body>\n"
                        + "<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js\"></script>\n"
                        + "\n"
                        + "\n"
                        + "</html>";
                motivo = "Confirmación de inscripción";
            } else if (estudianteTieneConversatorio.getEstado() == 2) {
                cuerpo = "<body>\n"
                        + "\n"
                        + "    <h1>Usted ha cancelado el registro al conversatorio: "+c.getTitulo()+"</h1>\n"
                        + "    \n"
                        + "    <h3>Si cree que se trate de un error, comuníquese con nosotros.</h3>\n"
                        + "\n"
                        + "</body>";
                motivo = "Cancelación de inscripción";
            }
            Correo.enviarCorreo(receptor, motivo, cuerpo);
        } catch (IOException | MessagingException ex) {
            Logger.getLogger(CorreoConversatorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

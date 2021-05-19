package usa.observer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import usa.contibot.Sala;
import usa.factory.AbstractFactory;
import usa.factory.Producer;
import usa.modelo.dao.IDao;
import usa.modelo.dao.IGradoDao;
import usa.modelo.dao.InstitucionDao;
import usa.modelo.dto.Clasificacion;
import usa.modelo.dto.Grado;
import usa.modelo.dto.Institucion;
import usa.utils.Utils;

/**
 * @author Valeria Bermúdez, Laura Blanco, Santiago Cáceres, Camila Fernández,
 * Andrés López, Natalia Montenegro, Santiago Pérez y Miguel Rippez
 */
public class ObservadorSala extends Observer {

    AbstractFactory factoryDao = Producer.getFabrica("DAO");
    IDao dao;
    public ObservadorSala(Subject sujeto) {
        super(sujeto);
    }

    @Override
    public void actualizar() {
        Sala sala = (Sala) sujeto;
        if (sala.getEstado() == 2) {
            mostrar();
        }
    }

    @Override
    public void mostrar() {
        Sala sala = (Sala) sujeto;
        try {
            //Se manda la conversación al personal calificado y los datos del estudiante
            JSONObject objEstudiante = new JSONObject(Utils.toJson(sala.getEstudiante()));
            dao = (IDao) factoryDao.obtener("GradoDao");
            IGradoDao daoGrado = (IGradoDao)dao;
            Grado grado=daoGrado.consultarUnico(objEstudiante.getString("grado"));
            System.out.println(grado.getCodigo());
            dao = (IDao) factoryDao.obtener("ClasificacionDao");
            Clasificacion clasificacion= (Clasificacion) dao.consultar(String.valueOf(grado.getClasificacion_id()));
            dao = (IDao) factoryDao.obtener("InstitucionDao");
            InstitucionDao daoInstitucion = (InstitucionDao) dao;
            Institucion institucion = (Institucion) daoInstitucion.consultarPorId(String.valueOf(grado.getInstitucion_id()));
            
            objEstudiante.put("institucion",institucion.getNombre());
            objEstudiante.put("grado",grado.getCodigo());
            objEstudiante.put("clasificacion",clasificacion.getGrado());
            
            JSONObject objRespuesta = new JSONObject();
            objRespuesta.put("tipo", "conversacion");
            objRespuesta.put("conversacion", new JSONArray(Utils.toJson(sala.getMensajes())));
            objRespuesta.put("numeroSala", sala.getCodigo());
            objRespuesta.put("estudiante", objEstudiante);
            sala.getSesionPersonal().getBasicRemote().sendText(objRespuesta.toString());

            //Aviso al estudiante que se conectó el personal calificado.
            JSONObject obj = new JSONObject();
            obj.put("mensaje", "Hola. Soy " + sala.getPersonaCalificada().getPrimerNombre() + " " + sala.getPersonaCalificada().getPrimerApellido() + ""
                    + " Dame un momento reviso tu pregunta");

            //Se mandan datos del personal calificado al estudiante (solo los que se pueden compartir)
            JSONObject personal = new JSONObject(Utils.toJson(sala.getPersonaCalificada()));
            personal.remove("token");
            personal.remove("contraseña");
            personal.remove("correo");
            personal.remove("documento");
            objRespuesta.put("personal", personal);
            sala.recibirMensajePersonal(obj, objRespuesta);

        } catch (IOException ex) {
            Logger.getLogger(ObservadorSala.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

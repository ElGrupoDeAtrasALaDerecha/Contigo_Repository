package usa.utils;

import com.google.gson.Gson;
import java.io.BufferedReader;
import javax.servlet.http.HttpServletRequest;
import usa.strategy.Contexto;
import usa.strategy.MailConfirmacionCita;
import usa.strategy.MailConfirmacionEstudiante;
import usa.strategy.MailConfirmacionInstitucion;
import usa.strategy.MailConfirmacionPersonal;

/**
 * Clase de utilidades. Contiene métodos estáticos para hacer tareas
 * específicas. Origen de Juan Leyva
 *
 * @author Valeria Bermúdez, Laura Blanco, Santiago Cáceres, Camila Fernández,
 * Andrés López, Natalia Montenegro, Santiago Pérez y Miguel Rippe
 * @version 2.0
 */
public class Utils {

    private static final Gson gson = new Gson();

    /**
     * Método que lee los parametros (en formato JSON) de una petición HTTP
     *
     * @param request que es el objeto request.
     * @return Un string con los parámetros de la petición
     */
    public static String readParams(HttpServletRequest request) {
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            request.setCharacterEncoding("UTF-8");
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
        } catch (Exception e) {
            /* report an error a*/ }

        line = jb.toString();
        return line;
    }

    /**
     * Función que genera un número aleatorio para la sala
     *
     * @return un entero con el numero generado
     */
    public static int generarNumeroSala() {
        return Integer.parseInt(GeneradorCodigos.getCodigo("N", 4));
    }

    /**
     * Método que permite generar un token de usuario.
     *
     * @return Un string con letras aleatorias para asignarla a un token de
     * usuario
     */
    public static String generateNewToken() {
        return GeneradorCodigos.getCodigo("N-M-m", 24);
    }

    /**
     * Método que permite convertir un objeto de cualquier clase a un string en
     * formato JSON. El método utiliza un objeto de la clase Gson y su método
     * toJson para hacer la conversión .
     *
     * @param o que es el objeto que se quiere convertir en formato JSON
     * @return un string con el objeto en formato JSON
     */
    public static String toJson(Object o) {
        return gson.toJson(o);
    }

    /**
     * Método que permite convertir una cadena String en formato JSON a un
     * objeto con clase concreta. El método usiliza un objeto de la clase Gson y
     * su método from JSON para hacer la conversión.
     *
     * @param s que es la cadena en JSON del objeto
     * @param T que es la clase del objeto en la cual se espera que se convierta
     * el json
     * @return un objeto de la clase T y con atributos de s.
     */
    public static Object fromJson(String s, Class T) {
        return gson.fromJson(s, T);
    }

    /**
     * Método que permite crear un codigo de curso con numeros y mayusculas.
     *
     * @return un codigo
     */
    public static String crearCodigoCurso() {
        return GeneradorCodigos.getCodigo("N-M", 6);
    }

    /**
     * Método que permite enviar un correo a un tipo de persona.
     *
     * @param tipoCorreo
     * @param correo
     */
    public static void enviarCorreoA(String tipoCorreo, String correo) {
        Contexto contexto = null;
        if (tipoCorreo.equals("confirmacionEstudiante")) {
            contexto = new Contexto(new MailConfirmacionEstudiante(correo));
        } else if (tipoCorreo.equals("confirmacionPersonal")) {
            contexto = new Contexto(new MailConfirmacionPersonal(correo));
        } else if (tipoCorreo.equals("confirmacionInstitucion")) {
            contexto = new Contexto(new MailConfirmacionInstitucion(correo));
        }
        contexto.enviarCorreo();
    }    
    /**
     * Método que permite enviar un correo a un tipo de persona.
     *
     * @param tipoCorreo
     * @param correo
     * @param contenido
     */
    public static void enviarCorreoA(String tipoCorreo, String correo,String contenido) {
        Contexto contexto = null;
        if(tipoCorreo.equals("confirmacionCita")){
            contexto = new Contexto(new MailConfirmacionCita(correo,"Confirmación de cita",contenido));
        }
        contexto.enviarCorreo();
    }
}

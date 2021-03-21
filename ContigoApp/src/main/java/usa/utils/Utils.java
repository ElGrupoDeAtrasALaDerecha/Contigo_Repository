package usa.utils;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.security.SecureRandom;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

public class Utils {

    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe
    private static final Gson gson = new Gson();

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
            /* report an error */ }

        line = jb.toString();
        return line;
    }

    /**
     * Función que genera un número aleatorio para la sala
     *
     * @return un entero con el numero generado
     */
    public static int generarNumeroSala() {
        return (int) (9000 * (Math.random()) + 1000);
    }

    public static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
    
    
    public static String toJson(Object o){
        return gson.toJson(o);
    }
    public static Object fromJson(String s, Class T){
        return gson.fromJson(s,T);
    }
}

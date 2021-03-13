package usa.utils;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;

public class Utils {
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
}
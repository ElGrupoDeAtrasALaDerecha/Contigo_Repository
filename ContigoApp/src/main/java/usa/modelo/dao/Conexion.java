package usa.modelo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de conexión con la base de datos
 * @author Santiago Pérez
 */
public class Conexion {
/**/
    String bd = "contigoBD";
    String user= "root";
    String password="1234";
    String host="localhost";
    String port="3306";
    private final String url = "jdbc:mysql://"+host+":"+port+"/"+bd+"?user="+user+"&password="+password+"&serverTimezone=UTC&autoReconnect=true&useSSL=false";
    static Connection con = null;
    /**
     * Constructor privado de la clase conexión
     */
    private Conexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url);

            
            if (con != null) {
                System.out.println("Conexion a base de datos exitosa");
            }
        } catch (SQLException e) {
            System.out.println(e.toString() + " Error de conexión 1");
        } catch (ClassNotFoundException e) {
            System.out.println(e.toString() + " Error de conexión 2");
        }
    }
    /**
     * Método de que permite obtener la conexión con la base de datos
     * @return la conexión con la base de datos
     */
    public static Connection tomarConexion() {
        if (con == null) {
            Conexion conexion = new Conexion();
        }
        return con;
    }
    /**
     * Método que cierra la conexión con la base de datos
     */
    public static void desconectar() {
        con = null;
        System.out.println("conexion terminada");
    }
}


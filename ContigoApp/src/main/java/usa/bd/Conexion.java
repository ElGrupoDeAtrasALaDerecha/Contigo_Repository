package usa.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de conexión con la base de datos. funciona polx 
 * @author Santiago Pérez
 */

public class Conexion extends IConexionBD{
    String bd = "contigoBD";
    String user= "root";
    String password="1234";
    String host="localhost";
    String port="3306";
    private final String url = "jdbc:mysql://"+host+":"+port+"/"+bd+"?user="+user+"&password="+password+"&serverTimezone=UTC&autoReconnect=true&useSSL=false";
    private static Connection con = null;
    /**
     * Constructor privado de la clase conexión
     * 
     * 
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
    @Override
    public Connection tomarConexion() {
        return con;
    }
    /**
     * Método que cierra la conexión con la base de datos
     */
    @Override
    public void desconectar() {
        con = null;
        System.out.println("conexion terminada");
    }

    public static IConexionBD getInstance() {
        if(instance==null){
            instance=new Conexion();
        }
        return instance;
    }
}
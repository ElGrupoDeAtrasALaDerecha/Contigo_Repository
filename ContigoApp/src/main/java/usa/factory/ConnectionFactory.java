package usa.factory;

import usa.bd.IConexionBD;
import usa.bd.Conexion;

/**
 * 
 * @author Valeria Bermúdez, Andrés López Santiago Pérez
 */
public class ConnectionFactory implements AbstractFactory<IConexionBD>{

    @Override
    public IConexionBD obtener(String motor) {
        IConexionBD conexion=null;
        if(motor.equals("mysql")){
            conexion=Conexion.getInstance();
        }
        //Aquí irían más condiciones si hubiesen más motores de bases de datos
        return conexion;
    }
}

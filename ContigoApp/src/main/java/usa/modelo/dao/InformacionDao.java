package usa.modelo.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import usa.factory.AbstractFactory;
import usa.factory.Producer;
import static usa.modelo.dao.IDao.conn;
import usa.modelo.dto.PersonalCalificado;
import usa.modelo.dto.decorador.IInformacion;
import usa.modelo.dto.decorador.Informacion;

/**
 *
 * @author Valeria Bermúdez, Laura Blanco, Santiago Cáceres, Camila Fernández,
 * Andrés López, Natalia Montenegro, Santiago Pérez y Miguel Rippe
 */
public class InformacionDao implements IDaoInformacion {

    AbstractFactory informacionFactory = Producer.getFabrica("INFO");

    @Override
    public boolean crear(IInformacion t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IInformacion consultar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizar(IInformacion t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList<IInformacion> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Método que obtiene toda la información extra de un personal calificado.
     * Todo se hace a partir del patrón decorador
     * @param p que son los datos del personal calificado
     * @return la información extra
     */
    @Override
    public IInformacion consultarPorPersonal(PersonalCalificado p) {
        IInformacion informacion = p;

        String tipos[] = {"Biografia", "Certificado", "Especialidad", "Experiencia", "RedSocial"};

        for (String tipo : tipos) {
            //Se consulta por cada tipo de información personal
            String sql = "select i.*,t.* from informacion as i\n"
                    + "inner join " + tipo.toUpperCase() + " as t on t.INFORMACION_id=i.id\n"
                    + "where i.PERSONAL_PERSONA_documento=\"" + p.getDocumento() + "\";";
            //Se inicia la consulta
            try {
                PreparedStatement pat = conn.prepareStatement(sql);
                ResultSet rs = pat.executeQuery();
                while (rs.next()) {
                    IInformacion nuevaInformacion = (IInformacion) informacionFactory.obtener(tipo);
                    ((Informacion) nuevaInformacion).setInformacion(informacion);
                    Method method = null;
                    Informacion info = (Informacion) nuevaInformacion;
                    info.setId(rs.getInt(1));
                    info.setDocumento(rs.getString(2));
                    info.setPublico(rs.getBoolean(3));
                    
                    //Se obtiene el número de columnas para saber cuántos parámetros agregar a los objetos
                    //Mediante getters y setters
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int column_count = rsmd.getColumnCount();
                    int j = 4;
                    
                    //Se calculan el total de parámetros del objeto
                    Field fields[] = info.getClass().getDeclaredFields();
                    Field padreField[] = info.getClass().getSuperclass().getDeclaredFields();
                    Field[] allFields = new Field[fields.length + padreField.length];
                    Arrays.setAll(allFields, i
                            -> (i < padreField.length ? padreField[i] : fields[i - padreField.length]));
                    //Se asignan los valores mediante la consulta
                    while (j < column_count) {
                        Field campoAEditar = allFields[j];
                        String str = campoAEditar.getName();
                        String nombre = "set" + str.substring(0, 1).toUpperCase() + str.substring(1);

                        try {
                            method = (nuevaInformacion).getClass().getMethod(nombre, String.class);
                            method.invoke(nuevaInformacion, rs.getString(j + 1));
                        } catch (NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException | IllegalAccessException ex) {
                            Logger.getLogger(InformacionDao.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        j++;
                    }
                    //Se hace la asignación recursiva correspondiente al patrón
                    //Decorador
                    informacion = nuevaInformacion;
                }
                rs.close();
                pat.close();

            } catch (SQLException ex) {
                Logger.getLogger(PersonalCalificadoDao.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return informacion;
    }

}

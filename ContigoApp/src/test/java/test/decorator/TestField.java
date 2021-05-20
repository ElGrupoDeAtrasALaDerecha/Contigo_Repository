package test.decorator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.Boolean;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import usa.factory.AbstractFactory;
import usa.factory.Producer;
import static usa.modelo.dao.IDao.conn;
import usa.modelo.dao.PersonalCalificadoDao;
import usa.modelo.dto.PersonalCalificado;
import usa.modelo.dto.decorador.IInformacion;
import usa.modelo.dto.decorador.Informacion;
import usa.utils.Utils;

public class TestField {

    public static void main(String[] args) throws IllegalAccessException {
        PersonalCalificado p = new PersonalCalificadoDao().consultar("1000853623");
        AbstractFactory informacionFactory = Producer.getFabrica("INFO");

        IInformacion informacion = p;

        String tipos[] = {"Biografia", "Certificado", "Especialidad", "Experiencia", "RedSocial"};

        for (String tipo : tipos) {

            String sql = "select i.*,t.* from informacion as i\n"
                    + "inner join " + tipo.toUpperCase() + " as t on t.INFORMACION_id=i.id\n"
                    + "where i.PERSONAL_PERSONA_documento=\"" + p.getDocumento() + "\";";

            try {
                PreparedStatement pat = conn.prepareStatement(sql);
                ResultSet rs = pat.executeQuery();
                while(rs.next()) {
                    IInformacion nuevaInformacion = (IInformacion) informacionFactory.obtener(tipo);
                    ((Informacion) nuevaInformacion).setInformacion(informacion);
                    Method method = null;
                    Informacion info = (Informacion) nuevaInformacion;
                    info.setId(rs.getInt(1));
                    info.setDocumento(rs.getString(2));
                    info.setPublico(rs.getBoolean(3));

                    //Retrieving the ResultSetMetaData object
                    ResultSetMetaData rsmd = rs.getMetaData();
                    //getting the column type
                    int column_count = rsmd.getColumnCount();
                    int j = 4;
                    Field fields[] = info.getClass().getDeclaredFields();
                    Field padreField[] = info.getClass().getSuperclass().getDeclaredFields();
                    Field[] allFields = new Field[fields.length + padreField.length];
                    Arrays.setAll(allFields, i
                            -> (i < padreField.length ? padreField[i] : fields[i - padreField.length]));

                    while (j < column_count) {
                        Field campoAEditar = allFields[j];
                        String str = campoAEditar.getName();
                        String nombre = "set" + str.substring(0, 1).toUpperCase() + str.substring(1);

                        try {
                            method = (nuevaInformacion).getClass().getMethod(nombre, String.class);
                            method.invoke(nuevaInformacion, rs.getString(j + 1));
                        } catch (NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex) {
                            Logger.getLogger(TestField.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        j++;
                    }
                    informacion = nuevaInformacion;
                }
                rs.close();
                pat.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(PersonalCalificadoDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        informacion.agregarInformacion();
        p.limpiar();
        System.out.println(Utils.toJson(p));
    }
}

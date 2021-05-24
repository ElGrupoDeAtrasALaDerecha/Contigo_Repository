package usa.modelo.dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import usa.factory.AbstractFactory;
import usa.factory.Producer;
import usa.modelo.dto.PersonalCalificado;
import usa.modelo.dto.decorador.IInformacion;
import usa.modelo.dto.decorador.Informacion;
import usa.utils.Utils;

/**
 * Clase de acceso a datos del personal calificado
 *
 * @author Valeria Bermúdez y Santiago Pérez
 * @version 1.0.0
 * @since 2021-03-16
 */
public class PersonalCalificadoDao implements IPersonalCalificadoDao {

    AbstractFactory factoryDao = Producer.getFabrica("DAO");
    IDao informacionDao = (IDao) factoryDao.obtener("InformacionDao");

    /**
     * Método que permite crear personal calificado. Obtiene los datos de un
     * objeto y los establece en la base de datos
     *
     * @param personal que es una instancia personal calificado
     * @return verdadero si crea y falso si hay errores al insertar el personal
     */
    @Override
    public boolean crear(PersonalCalificado personal) {
        try {
            String sql = "call insertarPersonalCalificado(?,?,?,?,?,?,?,?,?,?,?,?)";
            CallableStatement call = conn.prepareCall(sql);
            call.setString("_documento", personal.getDocumento());
            call.setInt("_TIPO_DOCUMENTO_ID", personal.getTipoDocumento());
            call.setString("_primerNombre", personal.getPrimerNombre());
            call.setString("_segundoNombre", personal.getSegundoNombre());
            call.setString("_primerApellido", personal.getPrimerApellido());
            call.setString("_segundoApellido", personal.getSegundoApellido());
            call.setString("_token", Utils.generateNewToken());
            call.setString("_fechaNacimiento", personal.getFechaDeNacimiento());
            call.setString("_contraseña", personal.getContraseña());
            call.setString("_genero", personal.getGenero());
            call.setString("_correo", personal.getCorreo());
            call.setString("_imagen", personal.getImagen());
            call.execute();
            call.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PersonalCalificadoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Método que permite consultar personal calificado por su número de
     * identificación. Se realiza una consulta en la base de datos por el número
     * de documento del personal calificado
     *
     * @param id que es el número de identificación
     * @return Un objeto de personal calificado o nulo si no lo encuentra
     */
    @Override
    public PersonalCalificado consultar(String id) {

        PersonalCalificado personalCalificado = null;
        String sql = "select p.*,pc.* from Persona as p inner join Personal as pc on pc.PERSONA_documento=p.documento "
                + "where p.documento=" + id + ";";
        try {
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while (rs.next()) {
                personalCalificado = new PersonalCalificado();
                personalCalificado.setDocumento(rs.getString("documento"));
                personalCalificado.setPrimerNombre(rs.getString("primerNombre"));
                personalCalificado.setSegundoNombre(rs.getString("segundoNombre"));
                personalCalificado.setPrimerApellido(rs.getString("primerApellido"));
                personalCalificado.setSegundoApellido(rs.getString("segundoApellido"));
                personalCalificado.setFechaDeNacimiento(rs.getDate("fechaNacimiento").toString());
                personalCalificado.setGenero(rs.getString("genero"));
                personalCalificado.setCorreo(rs.getString("correo"));
                personalCalificado.setToken(rs.getString("token"));
                personalCalificado.setImagen(rs.getString("imagen"));
                //personalCalificado.setBiografia(rs.getString("biografia"));
                IDaoInformacion infoDao = (IDaoInformacion) informacionDao;
              IInformacion informacion = infoDao.consultarPorPersonal(personalCalificado);
                informacion.agregarInformacion();
                personalCalificado.limpiar();
                personalCalificado.setDocumento(null);
            }
            rs.close();
            pat.close();
        } catch (SQLException ex) {
            Logger.getLogger(PersonalCalificadoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return personalCalificado;
    }

    /**
     * Método que permite actualizar los datos de un personal calificado en la
     * base de datos
     *
     * @param p que son los datos del personal a modificar
     * @return verdadero si actualiza y falso si no
     */
    @Override
    public boolean actualizar(PersonalCalificado p) {
        try {
            String sql = "update personal as pc ,persona as p\n"
                    + "set p.primerNombre=?,\n"
                    + "p.segundoNombre=?,\n"
                    + "p.primerApellido=?,\n"
                    + "p.segundoApellido=?,\n"
                    + "p.fechaNacimiento=?,\n"
                    + "p.genero=?,\n"
                    + "pc.imagen =?,\n"
                    + //"pc.biografia =?\n" +
                    "where p.documento=? and pc.PERSONA_documento=p.documento;";
            PreparedStatement pat = conn.prepareStatement(sql);
            pat.setString(1, p.getPrimerNombre());
            pat.setString(2, p.getSegundoNombre());
            pat.setString(3, p.getPrimerApellido());
            pat.setString(4, p.getSegundoApellido());
            pat.setString(5, p.getFechaDeNacimiento());
            pat.setString(6, p.getGenero());
            pat.setString(7, p.getImagen());
            //pat.setString(8, p.getBiografia());
            pat.execute();
            pat.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PersonalCalificadoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean eliminar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Método que permite listar todos los datos de personal calificados en la
     * base de datos. Se realiza una consulta sin criterios
     *
     * @return Una lista con los datos del personal calificado en la base de
     * datos
     */
    @Override
    public LinkedList<PersonalCalificado> listarTodos() {
        LinkedList<PersonalCalificado> personales = new LinkedList();;
        String sql = "select p.*,pc.* from Persona as p inner join Personal as pc on pc.PERSONA_documento=p.documento;";
        try {
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();

            while (rs.next()) {
                PersonalCalificado personalCalificado = new PersonalCalificado();
                personalCalificado.setDocumento(rs.getString("documento"));
                personalCalificado.setPrimerNombre(rs.getString("primerNombre"));
                personalCalificado.setSegundoNombre(rs.getString("segundoNombre"));
                personalCalificado.setPrimerApellido(rs.getString("primerApellido"));
                personalCalificado.setSegundoApellido(rs.getString("segundoApellido"));
                personalCalificado.setFechaDeNacimiento(rs.getDate("fechaNacimiento").toString());
                personalCalificado.setGenero(rs.getString("genero"));
                personalCalificado.setCorreo(rs.getString("correo"));
                //personalCalificado.setToken(rs.getString("token"));
                personalCalificado.setImagen(rs.getString("imagen"));
                //personalCalificado.setBiografia(rs.getString("biografia"));
                IDaoInformacion infoDao = (IDaoInformacion) informacionDao;
                IInformacion informacion = infoDao.consultarPorPersonal(personalCalificado);
                informacion.agregarInformacion();
                personalCalificado.limpiar();
                //personalCalificado.setDocumento(null);
                personales.add(personalCalificado);
                
            }
            rs.close();
            pat.close();
        } catch (SQLException ex) {
            Logger.getLogger(PersonalCalificadoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return personales;
    }

    /**
     * Método que permite consultar en la base de datos por el token
     *
     * @param token que es un token propio del usuario
     * @return Un objeto de personal calificado o nulo si no lo encuentra
     */
    @Override
    public PersonalCalificado consultarPorToken(String token) {
        PersonalCalificado personalCalificado = null;
        String sql = "select p.*,pc.* from Persona as p inner join Personal as pc on pc.PERSONA_documento=p.documento "
                + "where p.token='" + token + "';";
        try {
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while (rs.next()) {
                personalCalificado = new PersonalCalificado();
                personalCalificado.setDocumento(rs.getString("documento"));
                personalCalificado.setPrimerNombre(rs.getString("primerNombre"));
                personalCalificado.setSegundoNombre(rs.getString("segundoNombre"));
                personalCalificado.setPrimerApellido(rs.getString("primerApellido"));
                personalCalificado.setSegundoApellido(rs.getString("segundoApellido"));
                personalCalificado.setFechaDeNacimiento(rs.getDate("fechaNacimiento").toString());
                personalCalificado.setGenero(rs.getString("genero"));
                personalCalificado.setCorreo(rs.getString("correo"));
                personalCalificado.setImagen(rs.getString("imagen"));
                //personalCalificado.setBiografia(rs.getString("biografia"));
                IDaoInformacion infoDao = (IDaoInformacion) informacionDao;
                infoDao.consultarPorPersonal(personalCalificado);
                personalCalificado.limpiar();
            }
            rs.close();
            pat.close();
        } catch (SQLException ex) {
            Logger.getLogger(PersonalCalificadoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return personalCalificado;
    }

    /**
     * Metodo que permite consultar personal calificado por usuario y contraseña
     *
     * @param correo que es el correo del usuario
     * @param contraseña que es la contraseña del usuario
     * @return Un objeto de personal calificado o nulo si no lo encuentra
     */
    @Override
    public PersonalCalificado consultarPorCredenciales(String correo, String contraseña) {
        PersonalCalificado personal = null;
        try {

            String sql = "select p.*,pc.* from Persona as p inner join Personal as pc on pc.PERSONA_documento=p.documento "
                    + "where p.correo = \"" + correo + "\"  and p.contraseña = sha(\"" + contraseña + "\");";
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while (rs.next()) {
                personal = new PersonalCalificado();
                personal.setPrimerApellido("primerNombre");
                personal.setDocumento(rs.getString("documento"));
                personal.setPrimerNombre(rs.getString("primerNombre"));
                personal.setSegundoNombre(rs.getString("segundoNombre"));
                personal.setPrimerApellido(rs.getString("primerApellido"));
                personal.setSegundoApellido(rs.getString("segundoApellido"));
                personal.setFechaDeNacimiento(rs.getDate("fechaNacimiento").toString());
                personal.setGenero(rs.getString("genero"));
                personal.setCorreo(rs.getString("correo"));
                personal.setToken(rs.getString("token"));
                //personal.setBiografia(rs.getString("biografia"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PersonalCalificadoDao.class.getName()).log(Level.SEVERE, null, ex);

        }
        return personal;
    }

    /**
     * Metodo que permite consultar personal calificado por usuario y contraseña
     *
     * @return Un objeto de personal calificado o nulo si no lo encuentra
     */
    @Override
    public LinkedList<PersonalCalificado> consultarConBiografia() {
        LinkedList<PersonalCalificado> todos = this.listarTodos();
        for (PersonalCalificado p : todos) {
            LinkedList<IInformacion> informacion = p.getInfo();
            
            if (informacion != null) {
                LinkedList<IInformacion> nuevaLista = new LinkedList();
                for (IInformacion info : informacion) {
                    if (((Informacion) info).isPublico()) {
                        nuevaLista.add(info);
                    }
                }
                p.setInfo(nuevaLista);
            }
            
        }

        return todos;
    }
}

package usa.modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import usa.modelo.dto.Institucion;
import usa.modelo.dto.Persona;

/**
 *
 * @author Santiago Pérez
 */
public class RecuperacionDao implements IRecuperacionDao {

    @Override
    public boolean crear(String t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String consultar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizar(String t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList<String> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminarCodigoRecuperacion(Institucion institucion) {
        PreparedStatement pat;
        String sql = "update peticion_contrasena_institucion\n"
                + "set valido=false\n"
                + "where institucion_id=" + institucion.getId() + " and valido=true;";
        try {
            pat = conn.prepareStatement(sql);
            pat.executeUpdate();
            pat.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean eliminarCodigoRecuperacion(String codigo) {
        try {
            PreparedStatement pat;
            String sql = "update peticion_contrasena_persona \n"
                    + "set valido=false\n"
                    + "where codigo=\"" + codigo + "\";";

            pat = conn.prepareStatement(sql);
            pat.executeUpdate();
            pat.close();
            sql = "update peticion_contrasena_institucion\n"
                    + "set valido=false\n"
                    + "where codigo=\"" + codigo + "\";";
            pat = conn.prepareStatement(sql);
            pat.executeUpdate();
            pat.close();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean eliminarCodigoRecuperacion(Persona persona) {
        PreparedStatement pat;
        String sql = "update peticion_contrasena_persona \n"
                + "set valido=false\n"
                + "where persona_documento=\"" + persona.getDocumento() + "\" and valido=true;";
        try {
            pat = conn.prepareStatement(sql);
            pat.executeUpdate();
            pat.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean cambiarContraseña(String codigo, String contraseña) {
        try {
            String sql = "select institucion_id from peticion_contrasena_institucion \n"
                    + "where codigo=\"" + codigo + "\";";
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            if (rs.next()) {
                int idInstitucion = rs.getInt("institucion_id");
                rs.close();
                pat.close();
                sql = "update institucion as i \n"
                        + "set i.contraseña=sha(\"" + contraseña + "\")\n"
                        + "where i.id=\"" + idInstitucion + "\";";
                pat = conn.prepareStatement(sql);
                pat.executeUpdate();
                pat.close();
                return true;

            } else {
                sql = "select persona_documento from peticion_contrasena_persona \n"
                        + "where codigo=\"" + codigo + "\";";
                pat = conn.prepareStatement(sql);
                rs = pat.executeQuery();
                if (rs.next()) {
                    String documento = rs.getString("persona_documento");
                    sql = "update persona as p \n"
                            + "set p.contraseña=sha(\"" + contraseña + "\")\n"
                            + "where p.documento=\"" + documento + "\";";
                    rs.close();
                    pat.close();
                    pat = conn.prepareStatement(sql);
                    pat.executeUpdate();

                    pat.close();
                    return true;
                } else {
                    rs.close();
                    pat.close();
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RecuperacionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean verificarCodigoRecuperacion(Institucion i) {
        PreparedStatement pat;
        ResultSet rs;
        boolean tieneCodigo = false;
        String sql = "select pci.* from peticion_contrasena_institucion as pci \n"
                + "inner join institucion as i on i.id=pci.institucion_id\n"
                + "where i.correo=\"" + i.getCorreo() + "\" and pci.valido=true;";
        try {
            pat = conn.prepareStatement(sql);
            rs = pat.executeQuery();
            if (rs.next()) {
                tieneCodigo = true;
            }
            rs.close();
            pat.close();
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tieneCodigo;
    }

    @Override
    public boolean verificarCodigoRecuperacion(Persona p) {
        PreparedStatement pat;
        ResultSet rs;
        boolean tieneCodigo = false;
        String sql = "select pcp.* from peticion_contrasena_persona as pcp\n"
                + "inner join persona as p on p.documento=pcp.persona_documento\n"
                + "where p.documento=\""+p.getDocumento()+"\" and pcp.valido=true;";
        try {
            pat = conn.prepareStatement(sql);
            rs = pat.executeQuery();
            if (rs.next()) {
                tieneCodigo = true;
            }
            rs.close();
            pat.close();
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tieneCodigo;
    }

    @Override
    public boolean crearCodigoRecuperacion(Object o, String codigo) {
        String sql = "";
        PreparedStatement pat = null;
        try {
            if (o instanceof Institucion) {
                Institucion i = (Institucion) o;
                sql = "insert into peticion_contrasena_institucion (codigo,institucion_id,valido,fecha)\n"
                        + " values(?,?,?,date(sysdate())); ";
                pat = conn.prepareStatement(sql);
                pat.setString(1, codigo);
                pat.setInt(2, i.getId());
                pat.setBoolean(3, true);
                pat.execute();
                pat.close();
                return true;
            } else if (o instanceof Persona) {
                Persona p = (Persona) o;
                sql = "insert into peticion_contrasena_persona (codigo,persona_documento,valido,fecha)\n"
                        + "values(?,?,?,date(sysdate()));";
                pat = conn.prepareStatement(sql);
                pat.setString(1, codigo);
                pat.setString(2, p.getDocumento());
                pat.setBoolean(3, true);
                pat.execute();
                pat.close();
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RecuperacionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean validarCodigoRecuperacion(String codigo) {
        try {
            String sql = "select institucion_id from peticion_contrasena_institucion \n"
                    + "where codigo=\"" + codigo + "\" and valido=true;";
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            if (rs.next()) {
                rs.close();
                pat.close();
                return true;
            } else {
                rs.close();
                pat.close();
                sql = "select persona_documento from peticion_contrasena_persona \n"
                        + "where codigo=\"" + codigo + "\" and valido=true;";
                pat = conn.prepareStatement(sql);
                rs = pat.executeQuery();
                if (rs.next()) {
                    rs.close();
                    pat.close();
                    return true;
                } else {
                    rs.close();
                    pat.close();
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RecuperacionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}

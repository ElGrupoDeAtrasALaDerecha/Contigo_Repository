package usa.adapter;

import usa.modelo.dao.IDao;

/**
 * @author Valeria Bermúdez, Laura Blanco, Santiago Cáceres, Camila Fernández,
 * Andrés López, Natalia Montenegro, Santiago Pérez y Miguel Rippe
 */
public class CorreoClave {
    private String codigo;
    private IDao daoCodigoRec;
    
    public String generarLink(String codigo){
        return "ContigoApp/Recuperar?code="+codigo;
    }
    /**
     * Método que valida códigos
     * @param correo
     * @return verdadero si no existe un código y falso si ya se solicitó
     */
    public boolean validarCodigoExistente(String correo){
        //Aquí se 
        return true;
    }
}

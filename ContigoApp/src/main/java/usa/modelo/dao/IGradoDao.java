package usa.modelo.dao;

import java.util.LinkedList;
import org.json.JSONArray;
import usa.modelo.dto.Grado;
import usa.modelo.dto.GradoClasf;

/**
 * Interface de objetos de acceso a datos de los grados
 * @author Natalia Montenegro y Santiago Pérez
 */
public interface IGradoDao extends IDao<Grado>{
    public LinkedList<GradoClasf> listarGradosClasf();
    JSONArray consultarBtnPorGrado(String grado);
}

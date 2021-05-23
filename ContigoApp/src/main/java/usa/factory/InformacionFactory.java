package usa.factory;

import java.util.logging.Level;
import java.util.logging.Logger;
import usa.modelo.dto.decorador.IInformacion;

/**
 *
 * @author Valeria Bermúdez, Laura Blanco, Santiago Cáceres, Camila Fernández,
 * Andrés López, Natalia Montenegro, Santiago Pérez y Miguel Rippe
 */
public class InformacionFactory implements AbstractFactory<IInformacion>{
    
    @Override
    public IInformacion obtener(String param) {
        IInformacion informacion=null;    
        try {
            informacion = (IInformacion) Class.forName("usa.modelo.dto.decorador."+param).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(FactoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return informacion;
    }
    
}

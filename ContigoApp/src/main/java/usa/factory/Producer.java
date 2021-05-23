package usa.factory;

/**
 * Clase que produce fábricas
 * @author Camila fernandez, Miguel Angel Rippe, Laura Blanco, Andres McQueen
 * López, Valeria Bermúdez, Natalia Montenegro Quevedo, Santiago Cáceres,
 * Santiago Pérez
 */
public class Producer {
    public static AbstractFactory getFabrica(String param){
        AbstractFactory fabrica=null;
        if(param.equals("DAO")){   
            fabrica=new FactoryDao();
        }else if (param.equals("BD")){
            fabrica=new ConnectionFactory();
        }else if (param.equals("INFO")){
            fabrica= new InformacionFactory();
        }
        return fabrica;
    }
}

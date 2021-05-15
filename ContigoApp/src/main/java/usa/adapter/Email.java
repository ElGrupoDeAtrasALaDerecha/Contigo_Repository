package usa.strategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Santiago Pérez
 */
public abstract class Email {
    
    protected final String DESTINO;
    protected final String NOMBRE_ARCHIVO;
    protected final String ASUNTO;
    protected File template;

    public Email(String DESTINO,String ASUNTO,String NOMBRE_ARCHIVO) {
        this.DESTINO=DESTINO;
        this.NOMBRE_ARCHIVO=NOMBRE_ARCHIVO;
        this.ASUNTO=ASUNTO;
    }
    
    
    /**
     * Método que permite leer el archivo de datos
     * @return 
     */
    public String leerArchivo(){
        String cadena="";
        this.template = new File(this.NOMBRE_ARCHIVO);
        try {
            Scanner input = new Scanner(this.template,"utf-8");
            while (input.hasNextLine()) {
                cadena += input.nextLine();
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return cadena;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usa.utils;

/**
 *
 * @author Andrés López
 */
public class GeneradorCodigos {
    
    public static String NUMEROS = "0123456789";
 
    public static String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
 
    public static String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";
    
    public static String getPinNumber() {
	return getCodigo(NUMEROS, 4);
    }

    public static String getCodigo() {
            return getCodigo(8);
    }

    public static String getCodigo(int length) {
            return getCodigo(NUMEROS + MAYUSCULAS + MINUSCULAS, length);
    }

    public static String getCodigo(String key, int length) {
            String codigo = "";
            for (int i = 0; i < length; i++) {
                    codigo+=(key.charAt((int)(Math.random() * key.length())));
            }
            return codigo;
    }
}

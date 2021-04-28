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


    public static String getCodigo(String tipoCodigo, int length) {
        String key = "";
        String[] arreglo = tipoCodigo.split("-");
        for (int i = 0; i < arreglo.length; i++) {
            if (arreglo[i].equals("N")) {
                key += NUMEROS;
            } else if (arreglo[i].equals("M")) {
                key += MAYUSCULAS;
            } else if (arreglo[i].equals("m")) {
                key += MINUSCULAS;
            }
        }
        String codigo = "";
        for (int i = 0; i < length; i++) {
            codigo += (key.charAt((int) (Math.random() * key.length())));
        }
        return codigo;
    }
}

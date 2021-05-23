/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.decorator;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import usa.utils.Correo;

/**
 *
 * @author usuario
 */
public class correo {
    public static void main(String[] args) {
        try {
            Correo.enviarCorreo("valeria.bermudez01@correo.usa.edu.co", "prueba", "hola");
        } catch (IOException ex) {
            Logger.getLogger(correo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(correo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

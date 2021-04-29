package usa.utils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Clase de correo electrónico para enviar mensajes como ContigoApp
 *
 * @author Santiago Pérez
 */
public class Correo {

    /**
     * Correo empresarial
     */
    private static final String from = "contigoedsxapp@gmail.com";
    /**
     * Contraseña del correo empresarial
     */
    private static final String password = "suiB7uFIJDBT";

    /**
     * Método que envía un correo
     *
     * @param to que es la persona que recibe el mensaje
     * @param subject que es el tema del mensaje
     * @param content que es el contenido del mensaje (en formato html)
     * @param files archivos que puede contener el correo
     * @throws IOException
     * @throws AddressException
     * @throws MessagingException
     */
    public static void enviarCorreo(String to, String subject, String content, File[] files) throws IOException, AddressException, MessagingException {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.debug", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(content, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        
        if (files != null) {
            for (File file : files) {
                MimeBodyPart attachmentBodyPart = new MimeBodyPart();
                attachmentBodyPart.attachFile(file);
                multipart.addBodyPart(attachmentBodyPart);
            }
        }
        message.setContent(multipart);
        Transport transport = session.getTransport("smtp");
        transport.connect(prop.getProperty("mail.smtp.host"), from, password);
        transport.send(message);

        System.out.println("Correo enviado");

    }
    public static void enviarCorreo(String to, String subject, String content) throws IOException, MessagingException{
        enviarCorreo(to,subject,content,null);
    }
}

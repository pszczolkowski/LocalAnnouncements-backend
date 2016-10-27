package pl.weeia.localannouncements.sharedkernel.util;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by marcin on 24.10.16.
 * 
 * TODO
 * - extract configuration parameters to proper .properties files
 * - handle exceptions properly
 * - do it all asynchronously
 * - there should be services for each email send case
 */
public class MailSender {

    private static final String username = "";
    private static final String password = "";

    public static void sendMail(String clientAddress, String subject, String clientMessage) {


        Properties props = new Properties();
        props.put("mail.smtp.user", "konsultant");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "25");
        props.put("mail.debug", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.EnableSSL.enable", "true");

        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(clientAddress));

            message.setSubject(subject);
            message.setText(clientMessage);

            Transport.send(message);
        }
        catch (MessagingException e) {
            //throw new RuntimeException(e);
            clientAddress += "Wrong address? Unable to send mail.\n";

        }
    }
}

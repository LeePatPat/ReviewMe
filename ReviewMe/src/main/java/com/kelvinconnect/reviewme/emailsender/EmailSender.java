package com.kelvinconnect.reviewme.emailsender;

import com.kelvinconnect.reviewme.ticket.Ticket;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Email sending task
 * 
 * @author Lee Paterson
 */
public class EmailSender {
    public static boolean send(List<Ticket> tickets){
        List<String> emails = null;
        Properties properties = null;
        boolean sent = false;
        
        try{
            emails = EmailReader.read();
            properties = PropertiesReader.read();
        } catch(IOException e) {
            e.printStackTrace();
        }
        
        if(emails == null || properties == null)
            return false;
        
        Session session = Session.getDefaultInstance(properties);
        
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(properties.getProperty("SENDER_EMAIL")));
            message.addRecipients(Message.RecipientType.TO, emailListToAddressArray(emails));
            message.setSubject("ATTENTION: " + tickets.size() + " Trac Ticket(s) in Review!");
            message.setText(composeEmailBody(tickets));

            Transport.send(message);
            sent = true;
            System.out.println("Email sent!");
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        
        return sent;
    }
    
    private static String composeEmailBody(List<Ticket> tickets){
        String body = "Hi all, \n \nThe following tickets are in review: \n ";
        
        for(Ticket ticket : tickets){
            body += tracTicketLink(ticket.getId()) + " - " + ticket.getSummary() + "\n";
        }
        
        body += "\nKind regards,\nReviewMe";
        
        return body;
    }
    
    private static Address[] emailListToAddressArray(List<String> emails) throws AddressException{
        Address[] addresses = new InternetAddress[emails.size()];
        
        for(int i=0; i<emails.size(); i++){
            addresses[i] = new InternetAddress(emails.get(i));
        }
        
        return addresses;
    }
    
    private static String tracTicketLink(String id){
        id = id.replace("#", "");
        return "trac.pronto.pri/KC/ticket/" + id;
    }
}

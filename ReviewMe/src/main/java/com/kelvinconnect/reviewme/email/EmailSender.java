package reviewme.email;

import java.util.List;

/**
 * Email sending delegated to this class
 * 
 * @author Lee Paterson
 */
public class EmailSender {
    
    List<String> emails;
    
    public EmailSender(List<String> emails){
        this.emails = emails;
    }
    
    
    
}

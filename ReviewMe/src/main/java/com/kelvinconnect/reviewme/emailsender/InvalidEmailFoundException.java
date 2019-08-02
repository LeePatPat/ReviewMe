package com.kelvinconnect.reviewme.emailsender;

import java.io.IOException;

/**
 *
 * @author Lee Paterson
 */
public class InvalidEmailFoundException extends IOException{
    
    public InvalidEmailFoundException(){
        super();
    }
    
    public InvalidEmailFoundException(String email){
        super(email + " is not a valid email syntax.");
    }
    
}

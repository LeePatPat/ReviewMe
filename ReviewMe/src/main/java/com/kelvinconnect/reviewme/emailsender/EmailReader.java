package com.kelvinconnect.reviewme.emailsender;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Reads and retrieves the emails from the emails.config file in the same directory as the executable
 * 
 * @author Lee Paterson
 */
class EmailReader {
    static List<String> read() throws IOException, FileNotFoundException, EmailFileEmptyException{
        List<String> emails = new ArrayList<>();
        String path = "./maillist.config";
        
        FileInputStream fis = new FileInputStream(path);
        BufferedReader emailsFile = new BufferedReader(new InputStreamReader(fis));
        
        String strCurrentLine = null;
        while((strCurrentLine = emailsFile.readLine()) != null){
            if(!isValidEmailFormat(strCurrentLine))
                throw new InvalidEmailFoundException(strCurrentLine);

            emails.add(strCurrentLine);
        }
        
        if(emails.isEmpty())
            throw new EmailFileEmptyException();
        
        return emails;
    }
    
    private static boolean isValidEmailFormat(String email){
        final Pattern validEmailRegex = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
        
        Matcher matcher = validEmailRegex.matcher(email);
        
        return matcher.find();
    }
}

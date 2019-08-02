package com.kelvinconnect.reviewme.emailsender;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Read the properties for the email being sent
 * 
 * @author Lee Paterson
 */
class PropertiesReader {
    static Properties read() throws IOException, FileNotFoundException{
        String path = "./config.properties";
        Properties properties = new Properties();
        properties.load(new FileInputStream(path));
        
        return properties;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kelvinconnect.reviewme.emailsender;

import java.io.IOException;

/**
 * 
 * @author Lee Paterson
 */
public class EmailFileEmptyException extends IOException{
    public EmailFileEmptyException(){
        super("emails.config file is empty!");
    }
}

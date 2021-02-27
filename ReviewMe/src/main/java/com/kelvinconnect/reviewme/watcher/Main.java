package com.kelvinconnect.reviewme.watcher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
//import org.jsoup.helper.Validate;

/**
 * App to keep an eye on tickets that have been in review too long in a given milestone
 * An email reminder will be sent to all team members.
 * 
 * Email addresses will be read from a config file, comma delimited.
 * 
 * @author Lee Paterson
 */
public class Main {

    public static void main(String[] args) {
        if(args.length != 1){
            System.out.println("Usage: supply milestone URL (no other GET parameters other than 'milestone')");
            System.exit(1);
        }
        
        //main parameter assumed: trac/KC/query?milestone=NAME+%20+HERE
        
        String milestoneLink = args[0];
        try {
            URL link = new URL(milestoneLink);
            link.toURI();
            TicketWatcher.watch(milestoneLink);
        } catch (MalformedURLException | URISyntaxException e) {
            e.printStackTrace();
            System.out.println("ERROR: Link given is not valid URL syntax.");
            System.out.println("The only GET parameter should be 'milestone'");
            System.out.println("Expected format for user input: trac.pronto.pri/KC/query?milestone=NAME+%20+HERE");
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find config.properties or emails.config file.");
            System.out.println("Refer to the README.md file on how to format these.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

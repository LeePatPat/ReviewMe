package com.kelvinconnect.reviewme;

import java.io.IOException;
import java.net.URL;
import java.net.URI;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import org.jsoup.helper.Validate;

/**
 * App to keep an eye on tickets that have been in review too long in a given milestone
 * An email reminder will be sent to all team members.
 * 
 * Email addresses will be read from a config file, comma delimited.
 * 
 * @author Lee Paterson
 */
public class ReviewMe {

    public static void main(String[] args) {
        Validate.isFalse(args.length == 1, "Usage: supply milestone URL (no other GET parameters other than 'milestone')");
        
        //main parameter assumed: trac.pronto.pri/KC/query?milestone=NAME+%20+HERE
        
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
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

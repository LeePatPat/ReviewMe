package com.kelvinconnect.reviewme;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Connects to Trac - decides if a ticket has been in review for too long
 * 
 * @author Lee Paterson
 */
public class TicketWatcher {
    public static void watch(String link) throws IOException, InterruptedException{       
        TracParser parser = new TracParser();
        Map<String, Integer> ticketHourCounter = new HashMap<>();
        
        while(isWorkingHours()){
            Document document = Jsoup.connect(link).get();
            List<Ticket> currentReviewTickets = parser.parse(document);
            
            ticketHourCounter = updateReviewTimeMap(ticketHourCounter, currentReviewTickets);
            
            List<Ticket> emailTicketList = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : ticketHourCounter.entrySet())
                if(entry.getValue() >= 2)
                    for(Ticket ticket : currentReviewTickets)
                        if(ticket.getId().equals(entry.getKey()))
                            emailTicketList.add(ticket);
            
            
            
            Thread.sleep(1000 * 60 * 60);
        }
    }
    
    private static Map<String, Integer> updateReviewTimeMap(Map<String, Integer> ticketHourCounter, List<Ticket> ticketsInReview) {
        Map<String, Integer> newTicketHourCounter = new HashMap<>();
        
        for (Map.Entry<String, Integer> entry : ticketHourCounter.entrySet()) {
            for(int i = 0; i < ticketsInReview.size(); i++){
                if(entry.getKey().equals(ticketsInReview.get(i).getId())){
                    newTicketHourCounter.put(entry.getKey(), entry.getValue()+1);
                    break;
                }
            }
        }
        
        return newTicketHourCounter;
    }
    
    private static boolean isWorkingHours(){
        return true;
    }
}

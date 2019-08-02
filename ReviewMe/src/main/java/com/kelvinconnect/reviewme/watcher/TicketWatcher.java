package com.kelvinconnect.reviewme.watcher;

import com.kelvinconnect.reviewme.ticket.Ticket;
import com.kelvinconnect.reviewme.emailsender.EmailSender;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Calendar;
import java.util.Date;
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
        
        while(true){
            if(isWorkingHours()){
                Document document = Jsoup.connect(link).get();
                //System.out.println("document1: " + document.toString());
                
                List<Ticket> currentReviewTickets = parser.parse(document);

                ticketHourCounter = updateReviewTimeMap(ticketHourCounter, currentReviewTickets);

                List<Ticket> emailTicketList = new ArrayList<>();
                for (Map.Entry<String, Integer> entry : ticketHourCounter.entrySet())
                    if(entry.getValue() >= 2)
                        for(Ticket ticket : currentReviewTickets)
                            if(ticket.getId().equals(entry.getKey()))
                                emailTicketList.add(ticket);
                
                System.out.println("Tickets in review: " + ticketHourCounter.size());
                
                if(!emailTicketList.isEmpty())
                    EmailSender.send(emailTicketList);
                else
                    System.out.println("There are currently no tickets in review!");
            } else {
                System.out.println("It is not reasonable working hours to send emails!");
            }
            
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
        Calendar now = Calendar.getInstance();
        
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        
        Date currentTime = parseDate(hour + ":" + minute);
        Date lowerLimitTime = parseDate("9:00");
        Date upperLimitTime = parseDate("18:00");
        
        return currentTime.after(lowerLimitTime) && currentTime.before(upperLimitTime);
    }
    
    private static Date parseDate(String date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        
        try {
            return dateFormat.parse(date);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }
}

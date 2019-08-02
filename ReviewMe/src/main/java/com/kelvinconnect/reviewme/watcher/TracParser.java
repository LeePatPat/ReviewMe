package com.kelvinconnect.reviewme.watcher;

import com.kelvinconnect.reviewme.ticket.Ticket;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Lee Paterson
 * 
 * Parses HTML and returns the tickets   in review
 */
public class TracParser {
    
    public List<Ticket> parse(Document document){
        System.out.println("document: " + document.toString());
        
        Elements statusFieldOfReviewTickets = document.select("td:contains(review)");
        System.out.println("tableHeaderElements: " + statusFieldOfReviewTickets.toString() + " Size: " + statusFieldOfReviewTickets.size());
        
        List<Ticket> reviewTicketObjects = new ArrayList<>();
        for(Element reviewTd : statusFieldOfReviewTickets){
            Element reviewTableRow = reviewTd.parent();
            
            String id = reviewTableRow.select("td.id").first().text().trim();
            System.out.println(id);
            String summary = reviewTableRow.select("td.summary").first().text().trim();
            System.out.println(summary);
            
            
            reviewTicketObjects.add(new Ticket(id, summary));
            
            System.out.println("Ticket: " + reviewTicketObjects.get(reviewTicketObjects.size()-1));
        }
        
        return reviewTicketObjects;
    }
}

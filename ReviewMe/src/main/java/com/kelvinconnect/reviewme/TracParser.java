package com.kelvinconnect.reviewme;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Lee Paterson
 * 
 * Parses HTML and returns the tickets in review
 */
public class TracParser {
    
    
    public List<Ticket> parse(Document document){
        Elements tableHeaderElements = document.getElementsByClass("report-result");
        Element reviewTableHeaderElement = this.getReviewElement(tableHeaderElements);
        
        Element reviewTicketsTable = reviewTableHeaderElement.parent().parent().parent().nextElementSibling();
        
        Elements reviewTickets = reviewTicketsTable.children();
        
        List<Ticket> reviewTicketObjects = new ArrayList<>();
        for(Element reviewRow : reviewTickets){
            String id = reviewRow.getElementsByClass("id").first().text().trim();
            String summary = reviewRow.getElementsByClass("summary").first().text().trim();
            String project = reviewRow.getElementsByClass("project").first().text().trim();
            String modified = reviewRow.getElementsByClass("modified").first().text().trim();
            reviewTicketObjects.add(new Ticket(id, summary, modified, project));
        }
        
        return reviewTicketObjects;
    }
    
    private Element getReviewElement(Elements elements){
        for(Element element : elements){
            if(elements.text().contains("Status: review"))
                return element;
        }
        return null;
    }
}

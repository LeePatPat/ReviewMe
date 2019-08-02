package com.kelvinconnect.reviewme.ticket;

/**
 * Ticket object to represent a Trac ticket
 * 
 * @author Lee Paterson
 */
public class Ticket {
    
    private String id,summary = null;
    
    public Ticket(String ticketId, String ticketSummary){
        id = ticketId;
        summary = ticketSummary;
    }

    public String getId() {
        return id;
    }

    public String getSummary() {
        return summary;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    public boolean equalsTicket(Ticket ticket){
        return (this.getId().equals(ticket.getId()))
                && (this.getSummary().equals(ticket.getSummary()));
    }
    
    @Override
    public String toString(){
        return "ID: " + this.getId() + "\n" +
                "Summary: " + this.getSummary()+ "\n";
    }
    
}

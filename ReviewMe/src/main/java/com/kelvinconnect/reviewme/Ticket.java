package reviewme;

/**
 * Ticket object to represent a Trac ticket
 * 
 * @author Lee Paterson
 */
public class Ticket {
    
    private String id,summary,modified,project = null;
    
    public Ticket(String ticketId, String ticketSummary, String ticketModified, String ticketProject){
        id = ticketId;
        summary = ticketSummary;
        modified = ticketModified;
        project = ticketProject;
    }

    public String getId() {
        return id;
    }

    public String getModified() {
        return modified;
    }

    public String getProject() {
        return project;
    }

    public String getSummary() {
        return summary;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    public boolean equalsTicket(Ticket ticket){
        return (this.getId().equals(ticket.getId()))
                && (this.getModified().equals(ticket.getModified()))
                && (this.getSummary().equals(ticket.getSummary()))
                && (this.getProject().equals(ticket.getProject()));
    }
    
    @Override
    public String toString(){
        return "ID: " + this.getId() + "\n" +
                "Summary: " + this.getSummary()+ "\n" +
                "Modified: " + this.getModified()+ "\n" +
                "Project: " + this.getProject()+ "\n";
    }
    
}

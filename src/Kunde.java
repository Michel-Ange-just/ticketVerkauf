import java.util.concurrent.Callable;

public class Kunde implements Callable<Boolean> {
    private String name;
    private int tickets;
    private int event;
    private Ticketverkauf ticketverkauf;

    public Kunde(Ticketverkauf ticketverkauf, int event, int tickets, String name){
        this.ticketverkauf = ticketverkauf;
        this.event = event;
        this.tickets = tickets;
        this.name = name;
    }

    @Override
    public Boolean call() throws Exception {
        return this.ticketverkauf.kaufen(event, tickets);
    }

    public String getName(){return this.name;}
}

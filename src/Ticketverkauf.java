import java.util.concurrent.atomic.AtomicIntegerArray;

public class Ticketverkauf {
    AtomicIntegerArray tickets_verfuegbar;
    AtomicIntegerArray tickets_verkauft;
    int[] tickets_maximal;

    public Ticketverkauf(int[] verfuegbar, int[] maximal) {
        this.tickets_maximal = maximal;
        this.tickets_verkauft = new AtomicIntegerArray(verfuegbar.length);
        this.tickets_verfuegbar = new AtomicIntegerArray(verfuegbar);
    }

    public synchronized boolean kaufen(int event, int tickets){
        if(event < 0 || event > tickets_maximal.length){
            return false;
        }
        if(tickets < 1){
            return false;
        }
        while(tickets_verfuegbar.get(event) < tickets){
            //nicht genug tickets
            if(tickets_verkauft.get(event) + tickets > tickets_maximal[event]){
                return false;
            }
            //nicht genug aber es kommen mehr
            try{
                this.wait();
            }catch(InterruptedException e){
                throw new RuntimeException(e);
            }
        }
        tickets_verkauft.getAndAdd(event, tickets);
        tickets_verfuegbar.getAndAdd(event, -tickets);
        return true;
    }

    public synchronized boolean ticketsFreigeben(int event, int tickets){
        if(event < 0 || event > tickets_maximal.length){
            return false;
        }
        if(tickets < 1){return false;}
        if(tickets_maximal[event] < tickets + tickets_verkauft.get(event) + tickets_verfuegbar.get(event)){
            return false;
        }
        tickets_verfuegbar.getAndAdd(event,tickets);
        this.notifyAll();
        return true;
    }
}

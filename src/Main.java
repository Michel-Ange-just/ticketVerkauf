import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[] verfuegbar = {5};
        int[] maximal = {10};
        Ticketverkauf t1 = new Ticketverkauf(verfuegbar,maximal);
        Kunde k1 = new Kunde(t1, 0, 3, "K1");
        Kunde k2 = new Kunde(t1, 0, 3, "K2");
        FutureTask<Boolean>[] simple =  new FutureTask[2];
        simple[0] = new FutureTask<>(k1);
        simple[1] = new FutureTask<>(k2);
        new Thread(simple[0]).start();
        new Thread(simple[1]).start();
        t1.ticketsFreigeben(0,5);
        System.out.println(k1.getName()+":"+ simple[0].get().booleanValue());
        System.out.println(k2.getName()+":"+ simple[1].get().booleanValue());
    }
}
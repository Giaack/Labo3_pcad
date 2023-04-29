import java.util.*;

public class myUser extends Thread {

    private Eventi listaEventi;

    public myUser(Eventi listaEventi) {
        this.listaEventi = listaEventi;
        this.setName("User");
    }

    public synchronized void prenota(String nome, int posti) {
        listaEventi.prenota(nome, posti);
    }

    public synchronized void listaEventi() {
        listaEventi.listaEventi();
    }

    @Override
    public void run() {    
        ArrayList<String> nomi = new ArrayList<String>();
        nomi.add("Evento1");
        nomi.add("Evento2");
        nomi.add("Evento3");
        nomi.add("Evento4");
        nomi.add("Evento5");


        for (String nome : nomi) {
            prenota(nome, ((int) Math.random() * 5) + 1);
        }
    }
}

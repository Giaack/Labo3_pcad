import java.util.*;

public class myAdmin extends Thread {

    private Eventi listaEventi;

    public myAdmin(Eventi listaEventi) {
        this.listaEventi = listaEventi;
        this.setName("Admin");
    }

    public synchronized void crea(String nome, int posti) {
        listaEventi.crea(nome, posti);
    }

    public synchronized boolean aggiungiPosti(String nome, int posti) {
        return listaEventi.aggiungiPosti(nome, posti);
    }

    public synchronized void prenota(String nome, int posti) {
        listaEventi.prenota(nome, posti);
    }

    public synchronized void chiudiEvento(String nome) {
        listaEventi.chiudiEvento(nome);
    }

    // simula la creazione di eventi
    @Override
    public void run() {
        ArrayList<String> nomi = new ArrayList<String>();
        nomi.add("Evento1");
        nomi.add("Evento2");
        nomi.add("Evento3");
        nomi.add("Evento4");
        nomi.add("Evento5");

        for (String nome : nomi) {
            crea(nome, (int) Math.random() * 100);
        }

        // simuliamo una pausa, dove il thread user andrà ad operare sulla lista
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        //simuliamo l'eliminazione di un evento
        chiudiEvento("Evento1");

    }
}

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Eventi {

    private LinkedBlockingQueue<Evento> listaEventi = new LinkedBlockingQueue<Evento>();

    // crea un nuovo evento con nome (String) e posti (int) e lo aggiunge alla lista
    public synchronized void crea(String nome, int posti) {
        if (cercaEvento(nome) == false) {
            try {
                listaEventi.put(new Evento(nome, posti));
            } catch (Exception e) {
                System.err.println("Errore nella creazione dell'evento" + e.getMessage());
            }
        }
    }

    // controlla se esiste l'evento con il nome inserito
    public boolean cercaEvento(String nome) {
        for (Evento e : listaEventi) {
            if (e.getNome().equals(nome)) {
                return true;
            }
        }
        return false;
    }

    // cerca nella lista l'evento inserito e aumenta la capienza (posti) di posti
    // (int)
    public boolean aggiungiPosti(String nome, int posti) {
        for (Evento e : listaEventi) {
            if (e.getNome().equals(nome)) {
                e.aggiungiPostiAux(posti);
                return true;
            }
        }
        return false;
    }

    // la funzione controlla la disponibilità dei posti, se il numero di posti da
    // prenotare è <= 0 solleva un'eccezione, se non ci sono abbastanza posti
    // disponibili, ritorna falso, se tutto va a buon fine ritorna true
    public void prenota(String nome, int posti) {
        if (posti <= 0)
            System.out.println("Numero di posti non valido, sto cercando di inserire " + posti);
        for (Evento e : listaEventi) {
            if (e.getNome().equals(nome)) {
                while (!e.ciSonoPosti(posti)) {
                    try {
                        wait();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                        System.err.println("ERRRRi");
                    }
                } // cicliamo finché non si liberano dei posti (bloccante)
                if (e.getPostiDisponibili() >= posti) { // guardiamo che i posti disponibili siano maggiori o uguali a
                                                        // quelli richiesti (controlliamo disponibilità)
                    e.aggiungiPostiAux(-posti);
                }
            }
        }
    }

    // stampa su console tutti gli eventi con i posti disponibili
    public void listaEventi() {
        for (Evento e : listaEventi) {
            if (e.getPosti() > 0) {
                System.out.println(e.getNome() + " " + e.getPosti());
            }
        }
    }

    public void chiudiEvento(String nome) {
        for (Evento e : listaEventi) {
            if (e.getNome().equals(nome)) {
                notifyAll(); // svegliamo tutti i thread in attesa
                listaEventi.remove(e); // rimuoviamo l'evento dalla lista
            }
        }
    }

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
/*
    public Evento[] getListaEventi() {
        return (Evento[]) listaEventi.toArray();
    }
 */
    public static void main(String[] args) {

        ArrayList<String> nomi = new ArrayList<String>();
        nomi.add("Evento1");
        nomi.add("Evento2");
        nomi.add("Evento3");
        nomi.add("Evento4");
        nomi.add("Evento5");

        Eventi eventiTest = new Eventi();

        myAdmin admin = eventiTest.new myAdmin(eventiTest);
        myUser user = eventiTest.new myUser(eventiTest);

        admin.start();
        user.start();

        try {
            admin.join();
            user.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
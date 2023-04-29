import java.util.*;

public class Eventi {

    private sharedMap listaEventi = new sharedMap();

    // crea un nuovo evento con nome (String) e posti (int) e lo aggiunge alla lista
    public synchronized void crea(String nome, int posti) {
        if (!listaEventi.containsKey(nome)) {
            try {
                listaEventi.put(nome, posti); // se non esiste un evento con nome, nella lista eventi, allora
            } catch (Exception e) {
                System.err.println("Errore nella creazione dell'evento" + e.getMessage());
            }
        } else
            return; // se esiste già un evento con quel nome, allora non deve venir creato
    }

    // aumenta la capienza dell'evento di posti
    public void aggiungiPosti(String nome, int posti) {
        listaEventi.addTo(nome, posti);
    }

    // la funzione controlla la disponibilità dei posti, se il numero di posti da
    // prenotare è <= 0 solleva un'eccezione, se non ci sono abbastanza posti
    // disponibili, ritorna falso, se tutto va a buon fine ritorna true
    public void prenota(String nome, int posti) {
        while(!listaEventi.containsKey(nome)) {}
        if (posti <= 0)
            System.out.println("Numero di posti non valido, sto cercando di inserire " + posti);
        if (listaEventi.get(nome) >= posti) // controllo che il numero di posti disponibili sia maggiore o uguale a
                                            // numero di posti da prenotare
            listaEventi.addTo(nome, -posti);
        else {
            try {
                System.out.println("Sono in attesa di prenotare l'evento " + nome + " con " + posti + " posti, nel thread : " + Thread.currentThread().getName() + "\n");
                while (listaEventi.get(nome) < posti) {
                }
                System.out.println("Sto prenotando l'evento " + nome + " con " + posti + " posti, nel thread : " + Thread.currentThread().getName() + "\n");
                listaEventi.addTo(nome, -posti);
            } catch (Exception e) {
                System.err.println("Errore nella prenotazione dell'evento " + nome + " " + e.getMessage() + " nel thread : " + Thread.currentThread().getName() + "\n ");
            }
        } // se non ci sono abbastanza posti disponibili, allora non posso prenotare,
          // metto perciò il thread in attesa sulla lista
    }

    // stampa su console tutti gli eventi con i posti disponibili
    public void listaEventi() {
        // devo scorrere su tutta la listaEventi e stampare ogni evento con i posti
        // disponibili
        listaEventi.printValueGreaterThan0();
    }

    public void chiudiEvento(String nome) {
        /*
         * if(!listaEventi.containsKey(nome))
         * throw new IllegalArgumentException(); //teoricamente non serve, perché la
         * remova non fa niente se non trova nome nella lista
         */
        listaEventi.remove(nome);
    }

    public static void main(String[] args) {
        // bisogna creare un main che simuli l'interazione tra un thread che crea eventi
        // e un thread che prenota, più un altro thread che prenota
        Eventi e = new Eventi();
        Thread t1 = new Thread(new Runnable() {
            public void run() {
            
                e.crea("evento1", 10);
                System.out.println("Io thread " + Thread.currentThread().getName() + " Ho creato l'evento1");
                e.crea("evento2", 20);
                System.out.println("Io thread " + Thread.currentThread().getName() + " Ho creato l'evento2");
                e.crea("evento3", 30);
                System.out.println("Io thread " + Thread.currentThread().getName() + " Ho creato l'evento3");
                e.crea("evento4", 40);
                System.out.println("Io thread " + Thread.currentThread().getName() + " Ho creato l'evento4");
                e.crea("evento5", 50);
                System.out.println("Io thread " + Thread.currentThread().getName() + " Ho creato l'evento5");
                e.crea("evento6", 60);
                e.crea("evento8", 80);
                e.crea("evento9", 90);
                e.crea("evento10", 100);
                e.aggiungiPosti("evento1", 100);
                e.aggiungiPosti("evento8", 15);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                e.crea("evento7", 70);
                System.out.println("Io thread " + Thread.currentThread().getName() + " Ho creato l'evento7");
            }
        });
        Thread t2 = new Thread(new Runnable() {
            public void run() {
    
                e.prenota("evento1", 5);
                System.out.println(
                        "Io thread " + Thread.currentThread().getName() + " Ho prenotato l'evento1 con 5 posti");
                e.prenota("evento2", 10);
                System.out.println(
                        "Io thread " + Thread.currentThread().getName() + " Ho prenotato l'evento2 con 10 posti");
                e.prenota("evento3", 15);
                System.out.println(
                        "Io thread " + Thread.currentThread().getName() + " Ho prenotato l'evento3 con 15 posti");
                e.prenota("evento4", 20);
                System.out.println(
                        "Io thread " + Thread.currentThread().getName() + " Ho prenotato l'evento4 con 20 posti");
                e.prenota("evento5", 25);
                System.out.println(" Ho prenotato l'evento5 con 25 posti");
                e.prenota("evento6", 30);
                System.out.println(" Ho prenotato l'evento6 con 30 posti");
                e.prenota("evento7", 35);
                System.out.println(" Ho prenotato l'evento7 con 35 posti");
                e.prenota("evento8", 40);
                System.out.println(" Ho prenotato l'evento8 con 40 posti");
                e.prenota("evento9", 45);
                System.out.println(" Ho prenotato l'evento9 con 45 posti");
                e.prenota("evento10", 50);
                System.out.println(" Ho prenotato l'evento10 con 50 posti");
                e.prenota("evento10", 38);
                /*
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    System.err.println("Errore nel thread sleep" + e.getMessage());
                }    

                e.chiudiEvento("evento6");
                e.chiudiEvento("evento7");  */
                e.prenota("evento1", 100);
            }
        });
        //creo un nuovo thread che prenota
        
        Thread t3 = new Thread(new Runnable() {
            public void run() {
                
                e.prenota("evento1", 3);
                System.out.println(
                        "Io thread " + Thread.currentThread().getName() + " Ho prenotato l'evento1 con 5 posti");
                e.prenota("evento2", 4);
                System.out.println(
                        "Io thread " + Thread.currentThread().getName() + " Ho prenotato l'evento2 con 10 posti");
                e.prenota("evento3", 7);
                System.out.println(
                        "Io thread " + Thread.currentThread().getName() + " Ho prenotato l'evento3 con 15 posti");
                e.prenota("evento4", 10);
                System.out.println(
                        "Io thread " + Thread.currentThread().getName() + " Ho prenotato l'evento4 con 20 posti");
                e.prenota("evento5", 13);
                System.out.println(" Ho prenotato l'evento5 con 25 posti");
                e.prenota("evento6", 11);
                System.out.println(" Ho prenotato l'evento6 con 30 posti");
                e.prenota("evento7", 20);
                System.out.println(" Ho prenotato l'evento7 con 35 posti");
                e.prenota("evento8", 20);
                System.out.println(" Ho prenotato l'evento8 con 40 posti");
                e.prenota("evento9", 25);
                System.out.println(" Ho prenotato l'evento9 con 45 posti");
                e.prenota("evento10", 10);
                System.out.println(" Ho prenotato l'evento10 con 50 posti");
                e.prenota("evento8",  30);
            }
        });      
        t1.start();
        t2.start();
        t3.start();
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (Exception ex) {
            System.err.println("Errore nel join dei thread" + ex.getMessage());
        }
        e.listaEventi();
    }

}
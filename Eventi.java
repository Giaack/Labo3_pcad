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

    /* la funzione controlla la disponibilità dei posti, se il numero di posti da
     prenotare è <= 0 solleva un'eccezione, se non ci sono abbastanza posti
     disponibili, aspetta finché questi non vengano aggiunti, se tutto va a buon fine ritorna true */
    public void prenota(String nome, int posti) {
        while(!listaEventi.containsKey(nome)) { // se l'evento che si cerca di prenotare non esiste nella lista, rimango in attesa che questo venga creato
            try {
                System.out.println("Thread : " + Thread.currentThread().getName() + "\t Sono in attesa di prenotare l'evento (che non esiste ancora) " + nome + " con " + posti + " posti, se non vuoi attendere che l'evento venga creato fai \n\tctrl+c\n");
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (posti <= 0) {
            System.err.println("Numero di posti non valido, sto cercando di inserire " + posti);
            throw new IllegalArgumentException();
            }
        if (listaEventi.get(nome) >= posti) // se ci sono abbastanza posti disponibili allora prenoto
            listaEventi.addTo(nome, -posti);
        else {
            try {  
                System.out.println("Thread : " + Thread.currentThread().getName() +  "\t Sono in attesa di prenotare l'evento " + nome + " con " + posti + " posti, aspetto che la disponibilità aumenti " + "\n");
                while (listaEventi.get(nome) < posti) {}
                   // aspetto che ci siano abbastanza posti disponibili
                System.out.println("Thread : " + Thread.currentThread().getName() + "Sto prenotando l'evento " + nome + " con " + posti + "\n");
                listaEventi.addTo(nome, -posti);
            } catch (Exception e) {
                System.err.println("Thread : " + Thread.currentThread().getName() + "\t Errore nella prenotazione dell'evento " + nome + " " + e.getMessage() + "\n ");
            }
        } 
    }

    // stampa su console tutti gli eventi con i posti disponibili
    public void listaEventi() {
        listaEventi.printValueGreaterThan0();
    }

    // chiude l'evento con nome nome, quindi lo rimuove dalla lista
    public void chiudiEvento(String nome) {
        // non servono ulteriori controlli, perché la remove non fa niente se non trova l'elemento nella lista
        listaEventi.remove(nome);
    }

    public static void main(String[] args) {
        // bisogna creare un main che simuli l'interazione tra un thread che crea eventi
        // e un thread che prenota, più un altro thread che prenota
        Eventi e = new Eventi();
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
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
                System.out.println("Io thread " + Thread.currentThread().getName() + " Ho creato l'evento6");
                e.crea("evento8", 80);
                System.out.println("Io thread " + Thread.currentThread().getName() + " Ho creato l'evento8");
                e.crea("evento9", 90);
                System.out.println("Io thread " + Thread.currentThread().getName() + " Ho creato l'evento9");
                e.crea("evento10", 100);
                System.out.println("Io thread " + Thread.currentThread().getName() + " Ho creato l'evento10");
                e.aggiungiPosti("evento1", 100);
                System.out.println("Io thread " + Thread.currentThread().getName() + " Ho aggiunto 100 posti all'evento1");
                e.aggiungiPosti("evento8", 15);
                System.out.println("Io thread " + Thread.currentThread().getName() + " Ho aggiunto 15 posti all'evento8");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                e.crea("evento7", 70);
                System.out.println("Io thread " + Thread.currentThread().getName() + " Ho creato l'evento7");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                e.chiudiEvento("evento1");
                System.out.println("Io thread " + Thread.currentThread().getName() + " Ho chiuso l'evento1");
                System.out.println("Thread T0 vado a dormire 5 secondi e poi creo \'nome\'");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                e.crea("nome", 100);
                e.crea("evento1", 150);
                System.out.println("Io thread " + Thread.currentThread().getName() + " vado a dormire per altri 5 secondi e poi aggiungo posti all'evento 4");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                e.aggiungiPosti("evento4", 30);
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
                System.out.println("Io thread " + Thread.currentThread().getName() +" Ho prenotato l'evento5 con 25 posti");
                e.prenota("evento6", 30);
                System.out.println("Io thread " + Thread.currentThread().getName() +" Ho prenotato l'evento6 con 30 posti");
                e.prenota("evento7", 35);
                System.out.println("Io thread " + Thread.currentThread().getName() +" Ho prenotato l'evento7 con 35 posti");
                e.prenota("evento8", 40);
                System.out.println("Io thread " + Thread.currentThread().getName() +" Ho prenotato l'evento8 con 40 posti");
                e.prenota("evento9", 45);
                System.out.println("Io thread " + Thread.currentThread().getName() + " Ho prenotato l'evento9 con 45 posti");
                e.prenota("evento10", 50);
                System.out.println("Io thread " + Thread.currentThread().getName() + " Ho prenotato l'evento10 con 50 posti");
                e.prenota("evento10", 38);
                e.prenota("nome", 10);
                System.out.println("Io thread " + Thread.currentThread().getName() + " Ho prenotato nome con 10 posti");
                e.prenota("evento1", 100);
                System.out.println(
                        "Io thread " + Thread.currentThread().getName() + " Ho prenotato l'evento1 con 100 posti");
                e.prenota("evento1", 20);
                System.out.println(
                        "Io thread " + Thread.currentThread().getName() + " Ho prenotato l'evento1 con 20 posti");
                e.prenota("evento4", 20);
            }
        });
        
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
                System.out.println("Io thread " + Thread.currentThread().getName() + " Ho prenotato l'evento5 con 25 posti");
                e.prenota("evento6", 11);
                System.out.println("Io thread " + Thread.currentThread().getName() + " Ho prenotato l'evento6 con 30 posti");
                e.prenota("evento7", 20);
                System.out.println("Io thread " + Thread.currentThread().getName() +" Ho prenotato l'evento7 con 35 posti");
                e.prenota("evento8", 20);
                System.out.println("Io thread " + Thread.currentThread().getName() + " Ho prenotato l'evento8 con 40 posti");
                e.prenota("evento9", 25);
                System.out.println("Io thread " + Thread.currentThread().getName() + " Ho prenotato l'evento9 con 45 posti");
                e.prenota("evento10", 10);
                System.out.println("Io thread " + Thread.currentThread().getName() + " Ho prenotato l'evento10 con 50 posti");
                e.prenota("evento8",  30);
                System.out.println("Io thread " + Thread.currentThread().getName() + " Ho prenotato l'evento8 con 30 posti");
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

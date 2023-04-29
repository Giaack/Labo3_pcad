import java.util.*;

public class Evento {
    private String nome;
    private int posti;
    private int postiDisponibili;
    private ArrayList<Thread> listaThreadInAttesa = new ArrayList<Thread>();  

    public Evento(String nome, int posti) {
        this.nome = nome;
        this.posti = posti;
        this.postiDisponibili = posti;
    }

    public String getNome() {
        return nome;
    }

    public int getPosti() {
        return posti;
    }

    public synchronized int getPostiDisponibili() {
        return postiDisponibili;
    }

    public synchronized void aggiungiPostiAux(int posti) {
        this.postiDisponibili += posti;
    }

    public synchronized boolean ciSonoPosti(int num) {
        return postiDisponibili >= num;
    }

    public void chiudi() {

    }
}

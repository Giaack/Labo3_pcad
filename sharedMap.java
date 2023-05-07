import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class sharedMap {
    private ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

    public synchronized void put(String key, int value) {
        if(map.containsKey(key)){
            System.out.println("Errore: esiste già un evento con questo nome : " + key + "\n");
            throw new IllegalArgumentException();
        }
        map.put(key, value);
    }

    public synchronized int get(String key) {
        if(!map.containsKey(key)){
            System.out.println("Errore: non esiste un evento con questo nome : " + key + "\n");
            throw new IllegalArgumentException();
        }
        return map.get(key);
    }

    public synchronized void remove(String key) {
        if(!map.containsKey(key)){
            System.out.println("Errore: non esiste un evento con questo nome : " + key + "\n");
            throw new IllegalArgumentException();
        }
        map.remove(key);
    }

    public synchronized void addTo(String key, int toAdd) {
        if(!map.containsKey(key)){
            System.out.println("Errore: non esiste un evento con questo nome : " + key + "\n");
            throw new IllegalArgumentException();
        }
        Integer prev = map.get(key);
        if(prev == null) 
            return; //se non esiste un elem con quel nome, allora non possiamo operare su di esso
        map.replace(key, prev+toAdd);
    } 

    public synchronized boolean containsKey (String key) {
        return map.containsKey(key);
    }

    public synchronized void printValueGreaterThan0() {
        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();

        while(iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            if(entry.getValue() > 0)    //stampiamo solo gli elem con int associato > 0 (equivale a stampare solo gli eventi con posti disponibili )
                System.out.println("Evento: " + entry.getKey() + ", Valore: " + entry.getValue());
        }
    }
}

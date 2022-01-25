import java.util.*;

public class HashMapLinearProbing<K extends Comparable<K>, V> implements Map<K,V> {
    
    private int			getLoops;
    private int			putLoops;
    private int         tableSize;
    
    private List<Entry<K,V>> 	table;
    private int			count;
    
    public HashMapLinearProbing() {
        tableSize = 10007;
        table = new ArrayList<Entry<K,V>>(tableSize);
        
        for (int i = 0; i < tableSize; i++)
            table.add(null);
        count = 0;
        
        resetGetLoops();
        resetPutLoops();
    }
    
    public int getGetLoopCount() {
        return getLoops;
    }
    
    public int getPutLoopCount() {
        return putLoops;
    }
    
    public void resetGetLoops() {
        getLoops = 0;
    }
    public void resetPutLoops() {
        putLoops = 0;
    }
    
    public boolean containsKey(K key) {
        Entry<K,V>	p = table.get(key.hashCode() % tableSize);
        if (p!=null & p.key.equals(key))
            return true;
        else
            return false;
    }
    
    public V get (K key) throws KeyNotFoundException {
        int index = Math.abs(key.hashCode()) % tableSize;
        Entry<K,V>    p = table.get(index);
        getLoops++;
        while (p!=null) {
            if (p.key.equals(key))
                return p.value;
            getLoops++;
            p = table.get(++index % tableSize);
        }
        
        throw new KeyNotFoundException();
    }
    
    public List<Entry<K,V> >	entryList() {
        List <Entry<K,V> > l = new LinkedList<Entry<K,V> >();
        ListIterator<Entry<K,V>> iter = table.listIterator();
        while (iter.hasNext()) {
            Entry<K,V> e = iter.next();
            if (e!=null)
                l.add(e);
        }
        return l;
    }
    
    public void put (K key, V value){

        int index = Math.abs(key.hashCode())%tableSize;

        Entry<K,V>    p = table.get(index);
        boolean inserted = false;
        while (!inserted) {
            putLoops++;
            if (p==null) {
                table.add(index, new Entry<K,V> (key, value));
                count++;
                inserted = true;
            } else if (p.key.equals(key)) {
                p.value = value;
                inserted = true;
            } else if (count == tableSize) {
				// could throw an exception here as key cannot be inserted
                System.out.println("table is full: " + key +":"+ value);
                inserted = true;
            } else {
                p = table.get(++index % tableSize);
            }
        }
    }
    
    public int size() {
        return count;
    }
    
    public void clear() {
        table.clear();
        count = 0;
    }
    
}

import java.util.*;

public class HashMap<K extends Comparable<K>, V> implements Map<K,V> {

	private List<List<Entry<K,V>>> 	table;
	private int	count;
	private int  tableSize;

	// For Part III
	private long getLoops;
	private long putLoops;
	

	public HashMap() {
		tableSize = 1000003; // prime number
		table = new ArrayList<List<Entry<K,V>>>(tableSize);

		// initializes table as a list of empty lists
		for (int i = 0; i < tableSize; i++) {
			table.add(new LinkedList<Entry<K,V>>());
		}

		count = 0;

		// For Part III:
		resetGetLoops();
		resetPutLoops();
	}

	// For Part III
	public long getGetLoopCount() {
		return getLoops;
	}

	// For Part III
	public long getPutLoopCount() {
		return putLoops;
	}

	// For Part III
	public void resetGetLoops() {
		getLoops = 0;
	}
	
	// For Part III
	public void resetPutLoops() {
		putLoops = 0;
	}

	public boolean containsKey(K key) {
		try {
			get(key);
			return true;
		} catch (KeyNotFoundException e) {
			return false;
		}
	}

	public V get (K key) throws KeyNotFoundException {
		// gets the index in the table this key should be in
		int index = Math.abs(key.hashCode()) % tableSize;
		List<Entry<K,V>> list = table.get(index);
		Iterator<Entry<K,V>> iter = list.iterator();
		Entry<K,V> data = null;
		
		while(iter.hasNext()) {
			data = iter.next();
			if (data.getKey().equals(key)) {
				return data.getValue();
			}
		}
		
		throw new KeyNotFoundException();
	}


	public List<Entry<K,V> >	entryList() {
		List <Entry<K,V>> resultList = new LinkedList<Entry<K,V>>();
		Iterator<List<Entry<K,V>>> iter;
		List<Entry<K,V>> nestedList;
		Iterator<Entry<K,V>> nestedIter;
		Entry<K,V> value;

		iter = table.iterator();
		while (iter.hasNext()) {
			nestedList = iter.next();
			nestedIter = nestedList.iterator();
			while (nestedIter.hasNext()) {
				value = nestedIter.next();
				resultList.add(value);
			}
		} return resultList;
	}

	public void put (K key, V value){
		// gets the index in the table this key should be in
		Entry<K,V> input = new Entry<K,V>(key,value);
		int index = Math.abs(key.hashCode())%tableSize;
		List<Entry<K,V>> list = table.get(index);
		Iterator<Entry<K,V>> iter = list.iterator();
		Entry<K,V> data = null;
		
		// if key is found, update value.  if key is not found add a new Entry with key,value
		// The tester expects that you will add the newest Entry to the END of the list
		while (iter.hasNext()) {
			data = iter.next();
			if (data.getKey().equals(key)) {
				data.setValue(value);
				return;
			}
		} 
		list.add(input);
		table.set(index,list);
		count++;
	}

	public int size() {
		return count;
	}

	public void clear() {
		table.clear();
		count = 0;
	}

}
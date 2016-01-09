package es.ficonlan.web.backend.util.cache;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SimpleMemCache<KeyT, ValueT> implements SimpleCache<KeyT, ValueT> {

	private Map<KeyT, ValueT> cache;
	private Queue<KeyT> cacheFIFO;
	private int maxSize;
	
	public SimpleMemCache(int _maxSize) {
		maxSize = _maxSize;
		clear();			
	}
	
	@Override
	public SimpleCache<KeyT, ValueT> insert(KeyT key, ValueT value) {
		cache.put(key, value);
		cacheFIFO.add(key);
		if(cacheFIFO.size() > maxSize) {
			KeyT keyRemoved = cacheFIFO.poll();
			cache.remove(keyRemoved);
		}
		return this;
	}
	
	@Override
	public boolean contains(KeyT key) {
		return cache.containsKey(key);
	}
	
	@Override
	public boolean remove(KeyT key) {
		//HashMap.remove() returns null if nothing deleted
		if(cache.remove(key) != null) {
			cacheFIFO.remove(key);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public ValueT get(KeyT key) {
		return cache.get(key);
	}
	
	@Override
	public void clear() {
		cache = new ConcurrentHashMap<KeyT, ValueT>(maxSize);
		cacheFIFO = new ConcurrentLinkedQueue<KeyT>();
	}

}

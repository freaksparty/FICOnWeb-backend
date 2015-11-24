package es.ficonlan.web.backend.util.cache;

import java.util.concurrent.ConcurrentHashMap;

//TODO maxValue enforce
public class SimpleMemCache<KeyT, ValueT> implements SimpleCache<KeyT, ValueT> {

	private ConcurrentHashMap<KeyT, ValueT> cache;
	private int maxSize;
	
	public SimpleMemCache(int _maxSize) {
		maxSize = _maxSize;
		cache = new ConcurrentHashMap<KeyT, ValueT>(maxSize);
	}
	
	@Override
	public SimpleCache<KeyT, ValueT> insert(KeyT key, ValueT value) {
		cache.put(key, value);
		return this;
	}
	
	@Override
	public boolean contains(KeyT key) {
		return cache.containsKey(key);
	}
	
	@Override
	public boolean remove(KeyT key) {
		//HashMap.remove() returns null if nothing deleted
		return (cache.remove(key) != null);
	}
	
	@Override
	public ValueT get(KeyT key) {
		return cache.get(key);
	}
	
	@Override
	public void clear() {
		cache = new ConcurrentHashMap<KeyT, ValueT>();
	}

}

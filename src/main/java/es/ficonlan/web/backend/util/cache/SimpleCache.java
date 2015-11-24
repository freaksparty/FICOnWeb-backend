package es.ficonlan.web.backend.util.cache;

public interface SimpleCache<KeyT, ValueT> {

	SimpleCache<KeyT, ValueT> insert(KeyT key, ValueT value);
	boolean contains(KeyT key);
	boolean remove(KeyT key);
	ValueT get(KeyT key);
	void clear();

}
/*
 * Copyright 2020 Asociación Cultural Freak's Party
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.ficonlan.web.backend.util.cache;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SimpleMemCache<KeyT, ValueT extends Cacheable> implements SimpleCache<KeyT, ValueT> {

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
		ValueT value = cache.get(key);
		if(value != null && value.timeToExpire() < 0) {
			this.remove(key);
			return null;
		}
		return value;
	}
	
	@Override
	public void clear() {
		cache = new ConcurrentHashMap<KeyT, ValueT>(maxSize);
		cacheFIFO = new ConcurrentLinkedQueue<KeyT>();
	}

}

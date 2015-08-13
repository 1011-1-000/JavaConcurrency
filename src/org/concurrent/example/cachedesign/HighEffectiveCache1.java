package org.concurrent.example.cachedesign;

import java.util.HashMap;
import java.util.Map;

public class HighEffectiveCache1<A,V> implements Computable<A, V> {
	
	private final Map<A,V> cache = new HashMap<A,V>();
	private final Computable<A,V> c;
	
	public HighEffectiveCache1(Computable<A, V> c){
		this.c = c;
	}

	@Override
	public synchronized V compute(A arg) throws InterruptedException {
		// TODO Auto-generated method stub
		V result = cache.get(arg);
		if(result == null){
			result = c.compute(arg);
			cache.put(arg, result);
		}
		return result;
	}
}

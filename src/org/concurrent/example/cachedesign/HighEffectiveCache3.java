package org.concurrent.example.cachedesign;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class HighEffectiveCache3<A,V> implements Computable<A, V> {
	
	private final Map<A,Future<V>> cache = new ConcurrentHashMap<A,Future<V>>();
	private final Computable<A,V> c;
	
	public HighEffectiveCache3(Computable<A, V> c){
		this.c = c;
	}

	@Override
	public V compute(final A arg) throws InterruptedException {
		// TODO Auto-generated method stub
		Future<V> f = cache.get(arg);
		if(f == null){
			Callable<V> eval = new Callable<V>(){
				public V call() throws InterruptedException{
					return c.compute(arg);
				}
			};
			
			FutureTask<V> ft = new FutureTask<V>(eval);
			f = ft;
			cache.put(arg, ft);
			ft.run();
		}
		try {
			return f.get();
		} catch (ExecutionException e) {
			e.printStackTrace();
			return null;
		}
	}
}

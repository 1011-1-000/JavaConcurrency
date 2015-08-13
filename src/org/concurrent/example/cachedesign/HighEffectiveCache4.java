package org.concurrent.example.cachedesign;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class HighEffectiveCache4<A,V> implements Computable<A, V> {
	
	private final ConcurrentMap<A,Future<V>> cache = new ConcurrentHashMap<A,Future<V>>();
	private final Computable<A,V> c;
	
	public HighEffectiveCache4(Computable<A, V> c){
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
			cache.putIfAbsent(arg, ft);
			ft.run();
		}
		try {
			return f.get(5000,TimeUnit.MILLISECONDS);
		}catch (CancellationException e){
			f.cancel(true);
			cache.remove(arg,f);//当被取消时，把原来的值移除，以免造成污染
		}catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			System.out.println("执行超时");
			f.cancel(true);
			cache.remove(arg,f);
		}
		return null;
	}
}

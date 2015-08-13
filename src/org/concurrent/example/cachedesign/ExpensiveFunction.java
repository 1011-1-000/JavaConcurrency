package org.concurrent.example.cachedesign;

import java.math.BigInteger;

public class ExpensiveFunction implements Computable<String, BigInteger>{

	@Override
	public BigInteger compute(String arg) throws InterruptedException {
		// TODO 这中间是一些很耗时的操作，我暂时用线程Thread.sleep代替
		// 2秒计算时间
		Thread.sleep(2000);
		return new BigInteger(arg);
	}

}

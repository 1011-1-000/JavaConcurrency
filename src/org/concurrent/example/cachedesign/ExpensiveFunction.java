package org.concurrent.example.cachedesign;

import java.math.BigInteger;

public class ExpensiveFunction implements Computable<String, BigInteger>{

	@Override
	public BigInteger compute(String arg) throws InterruptedException {
		// TODO ���м���һЩ�ܺ�ʱ�Ĳ���������ʱ���߳�Thread.sleep����
		// 2�����ʱ��
		Thread.sleep(2000);
		return new BigInteger(arg);
	}

}

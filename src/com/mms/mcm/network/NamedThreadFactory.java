package com.mms.mcm.network;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {
	AtomicInteger integer=new AtomicInteger(1);
	String id;
	public NamedThreadFactory(String id)
	{
		this.id=id;
	}
	
	public Thread newThread(Runnable r) {
		String threadName=id+""+integer.getAndIncrement();
		return new Thread(r,threadName);
	}

}

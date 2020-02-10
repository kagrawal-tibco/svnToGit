package com.tibco.rta.common.service;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public interface StickyThreadPool extends StartStopService {
	
	public static final String STICKY_POOL_SIZE = "sticky.pool.size";
	
	public static final String STICKY_POOL_QUEUE = "sticky.pool.queue";

	<T> Future<T> submit(Callable<T> task);
	
	<T> Future<T> submit(String key, Callable<T> task);

	<T> Future<T> submit(String key, Callable<T> task, StickyThreadAllocator threadAllocator);

	Map<String, ExecutorService> getAllExecutors();

}

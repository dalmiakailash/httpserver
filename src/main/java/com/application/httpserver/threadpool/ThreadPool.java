/**
 * 
 */
package com.application.httpserver.threadpool;

import java.util.ArrayList;
import java.util.List;

/**
 * This is going to be a fixed thread pool with initial size of 10
 * 
 * @author kailash.dalmia
 *
 */
public class ThreadPool {

	private final List<ThreadExecutor> threadExecutors = new ArrayList<>();
	private final int maxThreadCount;
	private final ThreadQueue threadQueue;
	
	
	public ThreadPool(int maxThreadCount, int maxTaskInQueue) {
		this.maxThreadCount = maxThreadCount;
		this.threadQueue = new ThreadQueue(maxTaskInQueue);
	}
	
	public void startPool() {
		for(int i=0; i<maxThreadCount; i++) {
			final ThreadExecutor executor = new ThreadExecutor(threadQueue);
			threadExecutors.add(executor);
			new Thread(executor, "Executor-"+(i+1)).start();
		}
	}
	
	public void submit(final Runnable task) throws InterruptedException {
		threadQueue.push(task);
	}
	
	public void shutDown() {
		for(ThreadExecutor executor : threadExecutors) {
			executor.stop();
		}
	}
	
}

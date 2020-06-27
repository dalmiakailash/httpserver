/**
 * 
 */
package com.application.httpserver.threadpool;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Queue implementation for threads handling.
 * 
 * @author kailash.dalmia
 *
 */
public class ThreadQueue {

	private final Queue<Runnable> queue = new LinkedList<>();
	
	private final int maxSize;

	public ThreadQueue(int queueSize) {
		this.maxSize = queueSize;
	}
	
	public synchronized Runnable poll() throws InterruptedException {
		while(queue.isEmpty()) {
			wait();
		}
		if(queue.size() == maxSize) {
			notifyAll();
		}
		return queue.poll();
	}
	
	public synchronized void push(Runnable task) throws InterruptedException {
		while(queue.size() == maxSize) {
			wait();
		}
		if(queue.isEmpty()) {
			notifyAll();
		}
		queue.offer(task);
	}
	
}

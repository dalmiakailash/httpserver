/**
 * 
 */
package com.application.httpserver.threadpool;

/**
 * @author kailash.dalmia
 *
 */
public class ThreadExecutor implements Runnable {

	private final ThreadQueue queue;
	private boolean stop = false;
	
	public ThreadExecutor(ThreadQueue queue) {
		this.queue = queue;
	}

	public void run() {
		while(!stop) {
			try {
				final String name = Thread.currentThread().getName();
				final Runnable task = queue.poll();
				System.out.println("Task started by : "+name);
				task.run();
				System.out.println("Task finished by : "+name);
			} catch (InterruptedException e) {
				System.err.println("Error while picking up task from queue");
			}
		}
	}	
	
	public void stop() {
		stop = true;
	}

}

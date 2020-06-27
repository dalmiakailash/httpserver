/**
 * 
 */
package com.application.httpserver.handler;

import java.io.File;
import java.io.IOException;

import com.application.httpserver.connection.HttpClient;
import com.application.httpserver.connection.HttpServer;
import com.application.httpserver.executor.HttpClientRequestExecutor;
import com.application.httpserver.threadpool.ThreadPool;

/**
 * This connection handler kept server up and running always.
 * 
 * @author kailash.dalmia
 *
 */
public class HttpServerConnectionHandler {

	private final HttpServer server;
	private final File directory;
	
	public HttpServerConnectionHandler(HttpServer server, File file) {
		this.server = server;
		this.directory = file;
	}
	
	public void handleConnections() {
		final ThreadPool threadPool = new ThreadPool(10, 3);
		threadPool.startPool();
		while(server.isBound()) {
			try {
				final HttpClient client = server.acceptClientConnection();
				threadPool.submit(new HttpClientRequestExecutor(client, directory));
			} catch (IOException e) {
				System.err.println("Exception while trying to accept client connection:"+e.getMessage());
				e.printStackTrace();
			} catch (InterruptedException e) {
				System.err.println("Exception while trying to accept client connection:"+e.getMessage());
				e.printStackTrace();
			}
		}
		System.err.println("#### Server terminated. ####");
	}
	
}

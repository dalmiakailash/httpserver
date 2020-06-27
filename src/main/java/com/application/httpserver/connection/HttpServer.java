package com.application.httpserver.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

/**
 * This is custom server socket which will entertain all connectivity towards
 * server
 * 
 * @author kailash.dalmia
 *
 */
public class HttpServer extends ServerSocket {
	
	public HttpServer(final int port) throws IOException {
		super(port);
	}
	

	public HttpClient acceptClientConnection() throws IOException {
		final Socket client = super.accept();
		return new HttpClient(UUID.randomUUID().toString(), client);
	}
}

package com.application.httpserver;

import java.io.File;
import java.io.IOException;

import com.application.httpserver.connection.HttpServer;
import com.application.httpserver.handler.HttpServerConnectionHandler;

/**
 * This class is main entry point for HttpServer application.
 * This class will initiate Http server.
 * 
 * @author kailash.dalmia
 *
 */
public class Application {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		final int port = Integer.parseInt(args[0]);
		final String directoryPath = args[1];
		System.out.println("Starting server on port :"+port);
		final HttpServer httpServer = new HttpServer(port);
		System.out.println("Started server on port :"+port);
		final File file = new File(directoryPath);
		if(!file.exists()) {
			file.mkdir();
		}
		final HttpServerConnectionHandler httpServerConnectionHandler = new HttpServerConnectionHandler(httpServer, file);
		httpServerConnectionHandler.handleConnections();
	}

}

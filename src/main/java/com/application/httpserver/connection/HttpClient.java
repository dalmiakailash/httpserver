/**
 * 
 */
package com.application.httpserver.connection;

import java.net.Socket;

/**
 * @author kailash.dalmia
 *
 */
public class HttpClient {

	private final String clientId;

	private final Socket socket;

	public HttpClient(String clientId, Socket socket) {
		this.clientId = clientId;
		this.socket = socket;
	}

	public String getClientId() {
		return clientId;
	}

	public Socket getSocket() {
		return socket;
	}

}

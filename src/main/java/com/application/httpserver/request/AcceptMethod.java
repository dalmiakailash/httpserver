/**
 * 
 */
package com.application.httpserver.request;

/**
 * Acceptable methods on HttpServer
 * 
 * @author kailash.dalmia
 *
 */
public enum AcceptMethod {

	GET,
	POST;
	
	public static AcceptMethod getMethod(final String method) {
		for(AcceptMethod acceptMethod : values()) {
			if(acceptMethod.name().equalsIgnoreCase(method)) {
				return acceptMethod;
			}
		}
		return null;
	}
	
}

/**
 * 
 */
package com.application.httpserver.request;

/**
 * Response code and messages.
 * 
 * @author kailash.dalmia
 *
 */
public enum ResponseCode {

	OK("OK", 200),
	INTERNAL_SERVER_ERROR("Internal server error", 500),
	UNSUPPORTED_METHOD("Method not allowed", 405);
	
	private final String message;
	private final int code;
	
	
	private ResponseCode(String message, int code) {
		this.message = message;
		this.code = code;
	}


	public String getMessage() {
		return message;
	}


	public int getCode() {
		return code;
	}
	
	public String toString() {
		return code+" "+message;
	}
	
}

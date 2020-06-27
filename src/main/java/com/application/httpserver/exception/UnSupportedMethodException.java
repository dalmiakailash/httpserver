/**
 * 
 */
package com.application.httpserver.exception;

/**
 * Exception while supported method is not requested.
 * 
 * @author kailash.dalmia
 *
 */
public class UnSupportedMethodException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5826070515685334060L;

	public UnSupportedMethodException(String message) {
		super(message);
	}
	
}

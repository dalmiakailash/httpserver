/**
 * 
 */
package com.application.httpserver.request;

/**
 * Represents headers available in request.
 * 
 * @author kailash.dalmia
 *
 */
public class RequestHeader {

	private final long contentLength;
	private final String requestUri;

	public RequestHeader(long contentLength, String requestUri) {
		this.contentLength = contentLength;
		this.requestUri = requestUri;
	}
	
	public long getContentLength() {
		return contentLength;
	}
	
	public String getRequestUri() {
		return requestUri;
	}
	
}

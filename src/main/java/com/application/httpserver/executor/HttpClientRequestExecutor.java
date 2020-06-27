/**
 * 
 */
package com.application.httpserver.executor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.application.httpserver.connection.HttpClient;
import com.application.httpserver.exception.UnSupportedMethodException;
import com.application.httpserver.request.AcceptMethod;
import com.application.httpserver.request.RequestHeader;
import com.application.httpserver.request.ResponseCode;

/**
 * @author kailash.dalmia
 *
 */
public class HttpClientRequestExecutor implements Runnable {

	private final HttpClient client;
	private final File directory;
	
	
	public HttpClientRequestExecutor(HttpClient client, File directory) {
		this.client = client;
		this.directory = directory;
	}
	
	public void run() {
		InputStream is = null;
		try {
			is = client.getSocket().getInputStream();
			System.out.println("Handling client id : "+client.getClientId());
			parse(is);
			sendResponse(ResponseCode.OK);
		} catch (UnSupportedMethodException e) {
			e.printStackTrace();
			sendResponse(ResponseCode.UNSUPPORTED_METHOD);
		} catch (IOException e) {
			e.printStackTrace();
			sendResponse(ResponseCode.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				is.close();
				client.getSocket().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void sendResponse(ResponseCode responseCode) {
		System.out.println("Response code:"+responseCode);
		try {
			final OutputStream os = client.getSocket().getOutputStream();
			os.write(("HTTP/1.0 "+responseCode.toString()+"\r\n\r\n").getBytes("UTF-8"));
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void parse(InputStream inputStream) throws IOException, UnSupportedMethodException {
		final BufferedReader bis = new BufferedReader(new InputStreamReader(inputStream));
		final String line = bis.readLine();
		final AcceptMethod acceptMethod = parseAcceptMethod(line);
		if(null == acceptMethod || !acceptMethod.equals(AcceptMethod.POST)) {
			throw new UnSupportedMethodException("Unsupported method found. Only supported method is POST.");
		}
		final String requestUri = line.split("\\s")[1];
		final long contentLength = fetchContentLength(bis);
		System.out.println("Content-Length:"+contentLength);
		final RequestHeader requestHeader = new RequestHeader(contentLength, requestUri);
		readPayload(requestHeader, inputStream);
	}

	private void readPayload(RequestHeader requestHeader, InputStream inputStream) {
		final String fileName = requestHeader.getRequestUri().substring(requestHeader.getRequestUri().lastIndexOf("/"),
				requestHeader.getRequestUri().length());
		final File file = new File(directory.getAbsolutePath().concat("/").concat(fileName));
		try(OutputStream os = new FileOutputStream(file)) {
			long byteLeft = requestHeader.getContentLength();
			while(byteLeft > 0) {
				final byte[] data = new byte[(int)Math.min(byteLeft, 1024)];
				final int read = inputStream.read(data);
				if(read == -1) {	
					throw new IOException("Not able to read content.");
				}
				os.write(data);
				byteLeft -= read;
			}
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
			sendResponse(ResponseCode.INTERNAL_SERVER_ERROR);
		}
	}

	private long fetchContentLength(BufferedReader bis) throws IOException {
		long contentLength = 0;
		String line;
		while(!(line = bis.readLine()).equals("")) {
			if(line.startsWith("Content-Length")) {
				contentLength = Long.parseLong(line.split(":")[1].trim());
			}
		}
		return contentLength;
	}

	private AcceptMethod parseAcceptMethod(String line) throws IOException {
		if(line != null && line.length() > 0) {
			final String[] tokens = line.split("\\s");
			return AcceptMethod.getMethod(tokens[0]);
		}
		return null;
	}


}

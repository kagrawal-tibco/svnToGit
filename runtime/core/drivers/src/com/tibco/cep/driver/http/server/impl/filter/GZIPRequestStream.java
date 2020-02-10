/**
 * 
 */
package com.tibco.cep.driver.http.server.impl.filter;

import java.io.IOException;
import java.util.zip.GZIPInputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

/**
 * Wrap servlet input stream into GZip compatible input stream
 * 
 * @author vpatil
 */
public class GZIPRequestStream extends ServletInputStream {
	
    private GZIPInputStream gzipInputStream;
	private ServletInputStream  servletInputStream;
	
	public GZIPRequestStream(HttpServletRequest request) throws IOException {
		servletInputStream = request.getInputStream();
		gzipInputStream = new GZIPInputStream(servletInputStream);
	}
	
	@Override
	public int read() throws IOException {
		return gzipInputStream.read();
	}
	
	@Override
	public int read(byte[] b) throws IOException {
		return gzipInputStream.read(b);
	}
	
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		return gzipInputStream.read(b, off, len);
	}
	
	@Override
	public void close() throws IOException {
		gzipInputStream.close();
	}

	@Override
	public boolean isFinished() {
		return servletInputStream.isFinished();

	}

	@Override
	public boolean isReady() {
		return servletInputStream.isReady();
	}

	@Override
	public void setReadListener(ReadListener readListener) {
		servletInputStream.setReadListener(readListener);
		
	}
}

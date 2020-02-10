package com.tibco.be.parser.codegen.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.tools.JavaFileObject;

public class StreamURLConnection extends URLConnection {

	private JavaFileObject file;

	public StreamURLConnection(URL u,JavaFileObject javaFileObject) {
		super(u);
		this.file = javaFileObject;
		setAllowUserInteraction(false);
		setConnectTimeout(0);
		setDoInput(true);
		setDoOutput(false);
		setReadTimeout(0);
	}

	@Override
	public void connect() throws IOException {			
		
	}
	
	@Override
	public InputStream getInputStream() throws IOException {
		return file.openInputStream();
	}
	
	@Override
	public OutputStream getOutputStream() throws IOException {
		return file.openOutputStream();
	}
}
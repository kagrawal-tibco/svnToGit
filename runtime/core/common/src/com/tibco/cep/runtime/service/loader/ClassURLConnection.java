package com.tibco.cep.runtime.service.loader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import com.tibco.cep.runtime.service.loader.ClassManager.ClassInfo;

public class ClassURLConnection extends URLConnection {
	ClassInfo cinfo;

	public ClassURLConnection(URL u,ClassInfo clInfo) {
		super(u);
		this.cinfo = clInfo;
	}

	@Override
	public void connect() throws IOException {
		// TODO Auto-generated method stub

	}
	
	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(cinfo.byteCode);
	}
	
	@Override
	public OutputStream getOutputStream() throws IOException {
		return new ByteArrayOutputStream();
	}

}

package com.tibco.cep.runtime.service.loader;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import com.tibco.cep.runtime.service.loader.ClassManager.ClassInfo;

public class ClassURLHandler extends URLStreamHandler {
	
	public static final String PROTOCOL = "BEClassLoader";
	private ClassInfo clInfo;

	public ClassURLHandler(ClassInfo cinfo) {
		this.clInfo = cinfo;
	}

	@Override
	protected URLConnection openConnection(URL u) throws IOException {
		return new ClassURLConnection(u,clInfo);
	}

}

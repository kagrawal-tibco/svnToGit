package com.tibco.be.parser.codegen.stream;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Map;

import javax.tools.JavaFileObject;


public class StreamURLHandler extends URLStreamHandler {
	public static final String PROTOCOL = "codegen"; 

	private Map<String, JavaFileObject> classes;

	public StreamURLHandler(Map<String, JavaFileObject> classes) {
		this.classes = classes;
	}

	@Override
	protected URLConnection openConnection(URL u) throws IOException {
		final String path = u.getPath().replaceAll("/",".");
		if(classes.containsKey(path)) {
			return new StreamURLConnection(u,classes.get(path));
		}
		return null;
	}
	
	
	
	
}
package com.tibco.cep.runtime.service.logging.impl;

import java.io.IOException;

import org.apache.log4j.Layout;
import org.apache.log4j.RollingFileAppender;

/*
 * Inherits the RollingFileAppender and sets the encoding to UTF-8.
 * As RollingFileAppender create the OutputStreamWriter in the constructor,
 * there is no way to set the encoding to UTF-8. Overriding the getEncoding 
 * in this subclass.
 */
public class BERollingFileAppender extends RollingFileAppender {
	
	private static final String ENCODING = "UTF-8";

	public BERollingFileAppender() {
		super();
		super.setEncoding(ENCODING);
	}

	public BERollingFileAppender(Layout layout, String filename)
			throws IOException {
		super(layout, filename);
		super.setEncoding(ENCODING);
	}

	public BERollingFileAppender(Layout layout, String filename, boolean append)
			throws IOException {
		super(layout, filename, append);
		super.setEncoding(ENCODING);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.apache.log4j.WriterAppender#getEncoding()
	 * 
	 * Encoding is set to UTF-8
	 */
	public String getEncoding() {
		return ENCODING;
	}
}

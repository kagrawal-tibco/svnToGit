package com.tibco.rta.service.persistence.tools;

import java.util.Iterator;

public class SqlLineBrowser implements Iterator<String> {
	private StringBuffer buf;
	private String lineSep = RDBMSType.sSqlType.getNewline();

	SqlLineBrowser(StringBuffer buf) {
		this.buf = buf;
	}

	@Override
	public boolean hasNext() {
		if(buf == null || lineSep == null){
			return false;
		}
		return buf.indexOf(lineSep) > 0;
	}

	@Override
	public String next() {
		String result = buf.substring(0, buf.indexOf(lineSep));
		buf = buf.replace(0, buf.indexOf(lineSep) + lineSep.length(), "");
		return result;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

}

package com.tibco.cep.dashboard.config;

import java.io.File;

public class CNSEvent implements java.io.Serializable {
	
	private static final long serialVersionUID = 9066994296493593732L;
	
	public static final int FILE_CHANGED = 1;

	private File _file;
	private int _eventType = 0;
	
	CNSEvent(int type, File myFile) {
		_eventType = type;
		_file = myFile;
	}

	public File getFile() {
		return this._file;
	}

	public int getType() {
		return this._eventType;
	}

}
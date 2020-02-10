package com.tibco.cep.studio.core;

public class OverlayKey {

	public enum OVERLAYKEYTYPES { STRING, INT, DOUBLE, LONG, BOOLEAN };
	
	private String 			key;
	private OVERLAYKEYTYPES type;

	public OverlayKey(String key, OVERLAYKEYTYPES type) {
		this.key = key;
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public OVERLAYKEYTYPES getType() {
		return type;
	}

}

package com.tibco.cep.dashboard.psvr.plugin;

import com.tibco.cep.dashboard.psvr.mal.model.MALElement;

public class BuilderResult {
	
	public static enum SEVERITY {
		INFO,
		WARNING,
		ERROR
	};
	
	private SEVERITY severity;
	private String message;
	private MALElement element;
	
	public BuilderResult(SEVERITY severity, String message, MALElement element) {
		super();
		this.severity = severity;
		this.message = message;
		this.element = element;
	}

	public final SEVERITY getSeverity() {
		return severity;
	}

	public final String getMessage() {
		return message;
	}

	public final MALElement getElement() {
		return element;
	}
	
}
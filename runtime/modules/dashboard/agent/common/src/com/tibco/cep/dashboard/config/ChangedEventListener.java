package com.tibco.cep.dashboard.config;

public interface ChangedEventListener {
	
	// Implementer needs to getEventType() and if it is a FILE_CHANGED event, it
	// needs to getFilename() and do its thing (reload)
	public void handleCNSEvent(CNSEvent event);
	
}

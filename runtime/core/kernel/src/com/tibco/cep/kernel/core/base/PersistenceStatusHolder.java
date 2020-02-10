package com.tibco.cep.kernel.core.base;

public interface PersistenceStatusHolder {
//	public void setRtcReverseRef();
//	public void setRtcContainerRef();
//	public boolean getRtcReverseRef();
//	public boolean getRtcContainerRef();
//	public void clearRtcRRefStatus();
	public void setPersistenceModified();
	public boolean getPersistenceModified();
	public void clearPersistenceModified();
}

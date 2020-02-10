package com.tibco.cep.dashboard.psvr.biz;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class BizSession {
	
	private String tokenId;
	private String id;
	private long creationTime;
	private boolean newlyCreated;
	
	private Map<String, Object> attributes;
	
	private long lastAccessedTime;
	
	BizSession(String tokenId, String id) {
		this.tokenId = tokenId;
		this.id = id;
		this.creationTime = System.currentTimeMillis();
		this.newlyCreated = true;
		this.attributes = new HashMap<String, Object>();
		this.lastAccessedTime = System.currentTimeMillis();
	}
	
	public String getTokenId(){
		return tokenId;
	}
	
	public String getId() {
		return id;
	}
	
	void setIsOld(){
		this.newlyCreated = false;
	}

	public boolean isNew() {
		return newlyCreated;
	}	
	
	public void setAttribute(String name, Object value) {
		if (attributes == null){
			throw new IllegalStateException("Session[token="+tokenId+",id="+id+"] is no longer valid");
		}
		lastAccessedTime = System.currentTimeMillis();
		attributes.put(name, value);
		if (value instanceof BizSessionBindingListener){
			((BizSessionBindingListener)value).valueBound(this, name);
		}
	}
	
	public Object getAttribute(String name) {
		if (attributes == null){
			throw new IllegalStateException("Session[token="+tokenId+",id="+id+"] is no longer valid");
		}
		lastAccessedTime = System.currentTimeMillis();
		return attributes.get(name);
	}

	public void removeAttribute(String name) {
		if (attributes == null){
			throw new IllegalStateException("Session[token="+tokenId+",id="+id+"] is no longer valid");
		}
		lastAccessedTime = System.currentTimeMillis();
		Object removedValue = attributes.remove(name);
		if (removedValue instanceof BizSessionBindingListener){
			((BizSessionBindingListener)removedValue).valueBound(this, name);
		}		
	}
	
	public Iterator<String> getAttributeNames() {
		if (attributes == null){
			throw new IllegalStateException("Session[token="+tokenId+",id="+id+"] is no longer valid");
		}
		lastAccessedTime = System.currentTimeMillis();
		return attributes.keySet().iterator();
	}

	public long getCreationTime() {
		lastAccessedTime = System.currentTimeMillis();
		return creationTime;
	}
	
	void setLastAccessedTime(long lastAccessedTime){
		if (attributes == null){
			throw new IllegalStateException("Session[token="+tokenId+",id="+id+"] is no longer valid");
		}
		this.lastAccessedTime = lastAccessedTime;
	}

	public long getLastAccessedTime() {
		return lastAccessedTime;
	}

	public void invalidate() {
		BizSessionProvider.getInstance().removeSession(id);
		shutdown();
	}

	void shutdown() {
		Map<String,Object> copy = new HashMap<String, Object>(this.attributes);
		this.attributes.clear();
		for (Map.Entry<String, Object> entry: copy.entrySet()) {
			if (entry.getValue() instanceof BizSessionBindingListener){
				((BizSessionBindingListener)entry.getValue()).valueUnbound(this, entry.getKey());
			}	
		}
		this.attributes = null;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getName());
		sb.append("[id="+id);
		sb.append(",tokenid="+tokenId);
		sb.append("]");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		BizSession other = (BizSession) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
	
}
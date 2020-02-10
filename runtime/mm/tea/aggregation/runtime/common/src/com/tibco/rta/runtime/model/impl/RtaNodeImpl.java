package com.tibco.rta.runtime.model.impl;

import com.tibco.rta.Fact;
import com.tibco.rta.Key;
import com.tibco.rta.common.service.ServiceProviderManager;

import com.tibco.rta.query.Browser;
import com.tibco.rta.runtime.model.RtaNode;
import com.tibco.rta.service.persistence.PersistenceService;

/**
 * @author bgokhale
 * 
 * Implementation of some common methods used by MetricNode
 * 
 *
 */
abstract public class RtaNodeImpl implements RtaNode {

	private static final long serialVersionUID = -5544036704374313935L;

	protected Key key;
	
	protected Key parentKey;
	
	protected RtaNodeImpl parent;
	
	transient PersistenceService pService;
	
	protected long createdTime;
	
	protected long lastModifiedTime;
	
	public RtaNodeImpl() {
		try {
			pService = ServiceProviderManager.getInstance().getPersistenceService();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public RtaNodeImpl(Key key) {
		this();
		this.key = key;
	}

	@Override
	public Key getKey() {
		return key;
	}
	
	@Override
	public RtaNode getParent() {
		if (parent == null) {
			parent = (RtaNodeImpl) pService.getParentNode(this);
		}
		return parent;
	}
	
	public void setParent (RtaNode parent) {
		this.parent = (RtaNodeImpl) parent;
		this.parentKey = parent.getKey();
	}


	@Override
	public Key getParentKey() {
		return parentKey;
	}
	
	public void setParentKey (Key parentKey) {
		this.parentKey = parentKey;
	}

	@Override
	public Browser<Fact> getFactBrowser() {
		return pService.getFactBrowser(this,null);
	}
	
	@Override
	public long getCreatedTime() {
		return createdTime;
	}
	
	@Override
	public long getLastModifiedTime() {
		return lastModifiedTime;
	}
}

package com.tibco.cep.studio.debug.core.model.var;

import com.sun.jdi.Value;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;

public class LocationVariable extends RuleDebugVariable {
	
	@SuppressWarnings("unused")
	private static final String LOCATION_LOCALCACHE = "Local Cache";
	@SuppressWarnings("unused")
	private static final String LOCATION_DISTRIBUTEDCACHE = "Distributed Cache";
	@SuppressWarnings("unused")
	private static final String LOCATION_BACKINGSTORE = "Backing Store";
	@SuppressWarnings("unused")
	private static final String LOCATION_NA = "N/A";
	private String location;
	/**
	 * @param frame
	 * @param tinfo
	 * @param name
	 * @param value
	 */
	public LocationVariable(RuleDebugStackFrame frame, RuleDebugThread tinfo,
			String name, Value value) {
		super(frame, tinfo, name, value);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void init() {
//		try{
//		ObjectReference ruleSessionRef = DebuggerSupport.getCurrentRuleSession(getThreadInfo());
//			ObjectReference omRef = DebuggerSupport.getObjectManager(getThreadInfo(), ruleSessionRef);
//			
//			String omtype = DebuggerSupport.getObjectManagerType(getThreadInfo(), omRef);
//			if (omtype.equals(DebuggerConstants.OM_TYPE_INMEMORY)) {
//				setLocation(DebuggerConstants.OM_TYPE_INMEMORY);
//			} else if (omtype.equals(DebuggerConstants.OM_TYPE_DB)) {
//				setLocation(DebuggerConstants.OM_TYPE_DB);
//			} else if (omtype.equals(DebuggerConstants.OM_TYPE_CACHE)) {
//				ObjectReference agentRef = DebuggerSupport.getCacheAgent(getThreadInfo(), omRef);
//				ObjectReference cacheRef = DebuggerSupport.getLocalCache(getThreadInfo(), agentRef);
//				boolean isLocal = DebuggerSupport.hasEntity(getThreadInfo(), cacheRef, getJdiValue());
//				
//				if (isLocal) {
//					setLocation(LOCATION_LOCALCACHE);
//				} else {
//					ObjectReference rspRef = DebuggerSupport.getRuleServiceProvider(getThreadInfo(), ruleSessionRef);
//					ObjectReference tmRef = DebuggerSupport.getTypeManager(getThreadInfo(), rspRef);
//					StringReference uriRef = DebuggerSupport.getUri(getThreadInfo(), tmRef, ((ObjectReference) getJdiValue()).referenceType());
//					if (uriRef == null) {
//						setLocation(LOCATION_NA);
//					}
//					ObjectReference providerRef = DebuggerSupport.getEntityProvider(getThreadInfo(), omRef, uriRef);
//					BooleanValue isDistCache = DebuggerSupport.containsKey(getThreadInfo(), providerRef, getJdiValue());
//					if (isDistCache.booleanValue()) {
//						setLocation(LOCATION_DISTRIBUTEDCACHE);
//					} else {
//						//Backing Store
//						BooleanValue hasBSRef = DebuggerSupport.hasBackingStore(getThreadInfo(), providerRef);
//						if (hasBSRef.booleanValue()) {
//							DebuggerSupport.checkIfInBackingStore(getThreadInfo(), getJdiValue());
//							setLocation(LOCATION_BACKINGSTORE);
//						}
//					}
//				}
//			}
//		}catch(DebugException e) {
//			setLocation(LOCATION_NA);
//		}
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	

}

package com.tibco.cep.runtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.tibco.as.space.Member;
import com.tibco.cep.as.kit.map.SpaceMap;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.dao.impl.tibas.ASConstants;
import com.tibco.cep.runtime.service.dao.impl.tibas.ASDaoProvider;
import com.tibco.xdc.Receiver;

/**
 * This class handles the reconnect of remote client after the proxy node had went down for a longer time and the AS's remote client timeout expired,
 * and then the proxy node came back.
 * At this point all the metaspace, space handles held by this agent becomes unusable and needs to be reestablished.
 * 
 * @author moshaikh
 */
public class ASMetaspaceReconnectHandler {
	
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(ASMetaspaceReconnectHandler.class);
	private static final String DEFAULT_ALLOW_RECONNECT = "true";	
	private static ASMetaspaceReconnectHandler instance;
	
	private ASDaoProvider asDaoProvider;
	private List<SpaceMap> spaceMaps = new ArrayList<SpaceMap>();
	
	private boolean isRemoteMember;
	private boolean isRemoteMemberAllowReconnect;
	
	private ASMetaspaceReconnectHandler() {}
	
	/**
	 * Initialize the ASMetaspaceReconnectHandler instance.
	 * The reconnect handler would be able to perform connection resets only after it is initialized.
	 * @param asDaoProvider
	 * @param remoteClient
	 */
	public static void initReconnectHandlerInstance(ASDaoProvider asDaoProvider) {
		ASMetaspaceReconnectHandler reconnectHandlerInstance = getInstance();
		reconnectHandlerInstance.asDaoProvider = asDaoProvider;
		reconnectHandlerInstance.isRemoteMember = reconnectHandlerInstance.asDaoProvider.getMetaspace().getSelfMember().isRemote();
		Cluster cluster = reconnectHandlerInstance.asDaoProvider.getCluster();
		Properties properties = cluster.getRuleServiceProvider().getProperties();
		GlobalVariables gVs = cluster.getRuleServiceProvider().getGlobalVariables();
		
		reconnectHandlerInstance.isRemoteMemberAllowReconnect = Boolean.parseBoolean(gVs
				.substituteVariables(properties.getProperty(ASConstants.PROP_AS_REMOTE_MEMBER_ALLOW_RECONNECT, DEFAULT_ALLOW_RECONNECT))
				.toString());
	}
	
	/**
	 * Returns the singleton instance creating it if necessary.
	 * @return
	 */
	public static ASMetaspaceReconnectHandler getInstance() {
		if (instance == null) {
			synchronized (ASMetaspaceReconnectHandler.class) {
				if (instance == null) {
					instance = new ASMetaspaceReconnectHandler();
				}
			}
		}
		return instance;
	}
	
	/**
	 * Records a newly created SpaceMap, only the recorded SpaceMaps would get fresh handles to spaces in case of reconnect.
	 * @param spaceMap
	 */
	public void registerSpaceMap(SpaceMap spaceMap) {
		if (!isRemoteMemberAllowReconnect || !isRemoteMember) {
			return;
		}
		spaceMaps.add(spaceMap);
	}
	
	/**
	 * Detects whether a reconnect is needed and resets metaspace and all space handles (IF needed).
	 * @param ase
	 */
	public boolean resetAllConnections(Exception ase) {
		//Caused by: com.tibco.as.space.ASException: remote_client_timed_out
		
		if (asDaoProvider == null || asDaoProvider.isMetaspaceClosingConn()) {
			LOGGER.log(Level.DEBUG, "Metaspace Reconnect Handler not initialized (or is shutting down)");
			return false;
		}
		
		boolean isResetNeeded = isRemoteMember && isASRemoteClientTimeoutException(ase);
		
		if (!isResetNeeded) {
			LOGGER.log(Level.TRACE, "Space Connections reset NOT needed - " + ase.getMessage());
			return false;
		}
		
		if (!isRemoteMemberAllowReconnect) {
			LOGGER.log(Level.WARN, "Space Connections reset needed but NOT enabled - " + ase.getMessage());
			return false;
		}
		
		synchronized (ASMetaspaceReconnectHandler.class) {
			if (isMetaspaceAlreadyRestored()) {
				LOGGER.log(Level.TRACE, "Space connections are already reset."); 
				return true;
			}
			try {
				LOGGER.log(Level.DEBUG, "Proceeding with Space Connections reset - " + ase.getMessage()); 
				asDaoProvider.getMetaspace().closeAll();
			} catch (Exception e) {
				// Expected as the connection would be stale.
				LOGGER.log(Level.WARN, "Error while closing metaspace connection.", e);
			}
			
			try {
				LOGGER.log(Level.INFO, "Reconnecting to metaspace..");
				asDaoProvider.connectMetaspaceAfresh();
				
				//Reset statSpace connection
				SpaceMap.statSpace = asDaoProvider.getMetaspace().getSpace("$space_stats");
			} catch(Exception e) {
				LOGGER.log(Level.ERROR, "Error while reconnecting to metaspace.", e);
			}
			
			for (SpaceMap spaceMap : spaceMaps) {
				try {
					LOGGER.log(Level.DEBUG, "Reconnecting to space - " + spaceMap.getSpaceName());
					
					spaceMap.getSpace().close();
					spaceMap.setSpace(asDaoProvider.getMetaspace().getSpace(spaceMap.getSpaceName()));
					
					if (SpaceMap.statMap == null) {
						SpaceMap.statMap = new ConcurrentHashMap<String, AtomicLong>();
					}
			    	AtomicLong stat = SpaceMap.statMap.get(spaceMap.getSpaceName());
			    	SpaceMap.statMap.put(spaceMap.getSpaceName(), stat == null ? new AtomicLong(0L) : stat);
			    	
			    	Receiver.INSTANCE.spaces.put(spaceMap.getSpaceName(), spaceMap.getSpace());
    			} catch(Exception e) {
    				LOGGER.log(Level.WARN, "Exception while resetting Space Connection for - " + spaceMap.getSpaceName(), e);
    			}
    		}
			
			LOGGER.log(Level.INFO, "Successfully reconnected to metaspace.");
    	}
		return true;
	}
	
	private boolean isASRemoteClientTimeoutException(Exception ase) {
		return ase.getMessage() != null && (ase.getMessage().contains("remote_client_timed_out")
				|| ase.getMessage().contains("metaspace_invalid"));
	}
	
	/**
	 * Returns true if the metaspace connection is already good (Used to prevent multiple time resets when multiple threads detect stale connections simultaneously)
	 * @return
	 */
	private boolean isMetaspaceAlreadyRestored() {
		try {
			Member member = asDaoProvider.getMetaspace().getSelfMember();
			return member != null;
		} catch (Exception e) {
			return !isASRemoteClientTimeoutException(e);
		}
	}
}

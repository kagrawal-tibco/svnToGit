package com.tibco.rta.service.cluster.as;

import com.tibco.as.space.Member;
import com.tibco.as.space.Member.ManagementRole;
import com.tibco.as.space.Metaspace;
import com.tibco.as.space.listener.MetaspaceMemberListener;
import com.tibco.rta.as.kit.ASResourceFactory;
import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.service.cluster.ClusterService;

import java.util.Properties;

public class ASClusterService extends AbstractStartStopServiceImpl implements ClusterService, MetaspaceMemberListener	{

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_CLUSTER.getCategory());

	private ASResourceFactory resourceFactory;
	
	private boolean isCacheNode;

	private Metaspace metaspace;

	@Override
	public void init(Properties configuration) throws Exception {
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "Initializing Cluster service..");
		}
		super.init(configuration);
		resourceFactory = ASResourceFactory.getInstance(configuration);
		isCacheNode = Boolean.parseBoolean(configuration.getProperty(ConfigProperty.IS_CACHE_NODE.getPropertyName()));
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "Initializing Cluster service.. Complete");
		}
	}

	@Override
	public void start() throws Exception {
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "Started Cluster service..");
		}
		resourceFactory.init();
		metaspace = resourceFactory.getMetaspace();
		resourceFactory.getMetaspace().listenMetaspaceMembers(this);
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "Starting Cluster service.. Complete.");
		}
	}

	@Override
	public void stop() throws Exception {
		resourceFactory.stop();
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "Stopped Cluster service.");
		}
		
	}

	@Override
	public boolean isCacheNode() {
		return isCacheNode;
	}

	@Override
	public void onJoin(Member member, ManagementRole arg1) {
		if (member.getName().equals(metaspace.getSelfMember().getName())) {
			return;
		}
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
		    LOGGER.log(Level.DEBUG, "Member %s from %s joined the cluster %s", member.getName(), member.getHostAddress(), metaspace.getName());
        }
		
	}

	@Override
	public void onLeave(Member member) {
		if (member.getName().equals(metaspace.getSelfMember().getName())) {
			return;
		}
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Member %s from %s left the cluster %s", member.getName(), member.getHostAddress(), metaspace.getName());
        }
	}

	@Override
	public void onUpdate(Member arg0, ManagementRole arg1) {
		// TODO Auto-generated method stub
		
	}

}

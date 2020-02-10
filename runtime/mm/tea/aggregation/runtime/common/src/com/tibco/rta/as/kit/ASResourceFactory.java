package com.tibco.rta.as.kit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.as.space.ASCommon;
import com.tibco.as.space.ASException;
import com.tibco.as.space.ASStatus;
import com.tibco.as.space.IndexDef;
import com.tibco.as.space.Member;
import com.tibco.as.space.Member.DistributionRole;
import com.tibco.as.space.MemberDef;
import com.tibco.as.space.Metaspace;
import com.tibco.as.space.RecoveryOptions;
import com.tibco.as.space.Space;
import com.tibco.as.space.SpaceDef;
import com.tibco.as.space.SpaceDef.CachePolicy;
import com.tibco.as.space.SpaceDef.DistributionPolicy;
import com.tibco.as.space.SpaceDef.EvictionPolicy;
import com.tibco.as.space.SpaceDef.LockScope;
import com.tibco.as.space.SpaceDef.PersistencePolicy;
import com.tibco.as.space.SpaceDef.PersistenceType;
import com.tibco.as.space.SpaceDef.ReplicationPolicy;
import com.tibco.as.space.SpaceDef.UpdateTransport;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.listener.Listener;
import com.tibco.as.space.listener.MetaspaceMemberListener;
import com.tibco.as.space.listener.SpaceMemberListener;
import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;

public class ASResourceFactory implements SpaceMemberListener {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_PERSISTENCE.getCategory());

    private boolean initialized;
    private Properties config;
    private Metaspace metaspace;
    private String metaspaceName;
    private long invocationTimeout = 30000;
    private long receiveBufferSize = 1000L;
    private int workerThreadCount = 32;
    private int virtualNodeCount = 100;

    private boolean createIndex = true;
    private boolean applyTxn = false;
    private int batchSize = 100;

    boolean stopped;


    SpaceDef spaceDef;


    private Map<Listener, Object> allListeners = new HashMap<Listener, Object>();
//	private Map <Channels, SyncService> syncServiceMap = new HashMap<Channels, SyncService>();
//	private Map <Channels, ASyncService> asyncServiceMap = new HashMap<Channels, ASyncService>();


    private static Map<String, ASResourceFactory> factories = new HashMap<String, ASResourceFactory>();
//	private Map<ServiceContext, InvocationHandler> invocationHandlerRegistry;

    synchronized public static ASResourceFactory getInstance(Properties config) throws Exception {
        ASResourceFactory fac = factories.get(ConfigProperty.CLUSTER_NAME.getValue(config));
        if (fac == null) {
            fac = new ASResourceFactory(config);
            factories.put((String) ConfigProperty.CLUSTER_NAME.getValue(config), fac);
        }
        return fac;
    }


    synchronized public static ASResourceFactory getInstance(String domainName) {
        return factories.get(domainName);
    }

//	synchronized public void registerInvocationHandler(ServiceContext context, InvocationHandler invocationHandler) {
//		invocationHandlerRegistry.put(context, invocationHandler);
//	}

//	synchronized public InvocationHandler getInvocationHandler(ServiceContext context) {
//		return invocationHandlerRegistry.get(context);
//	}

    private ASResourceFactory(Properties config) throws Exception {
        this.config = config;
//		this.config.put(ASConstants.PROP_AS_METASPACE_NAME,
//					config.get(ConfigProperty.CLUSTER_NAME.getPropertyName()));
//		this.config.put(ASConstants.PROP_AS_MEMBER_NAME,
//				config.get(ConfigProperty.CLUSTER_MEMBER_NAME.getPropertyName()));		   

        spaceDef = configureSpace("dummy", this.config);
//		this.invocationHandlerRegistry = new ConcurrentHashMap<ServiceContext,InvocationHandler>();;
    }

    // added an overloaded method that takes context tuple. now need to use it various places.
    synchronized public void init() {
        init(null);
    }

    synchronized public void init(Tuple context) {
        if (initialized) {
            return;
        }

        metaspaceName = (String) config.get(ConfigProperty.CLUSTER_NAME.getPropertyName());
        // sanitizeName(am.getDomainName());
        if (metaspaceName == null || metaspaceName.equals("")) {
            metaspaceName = "default";
        }
        String listenUrl = (String) config.get(ConfigProperty.AS_LISTEN_URL.getPropertyName());
        if (listenUrl != null && listenUrl.equals("\"\"")) {
            listenUrl = "";
        }
        String discoverUrl = (String) config.get(ConfigProperty.AS_DISCOVER_URL.getPropertyName());
        if (discoverUrl != null && discoverUrl.equals("\"\"")) {
            discoverUrl = "";
        }

        try {
            String timeoutStr = (String) config.get(ConfigProperty.AS_TRANSPORT_TIMEOUT.getPropertyName());
            invocationTimeout = Long.parseLong(timeoutStr);
        } catch (Exception e) {
            LOGGER.log(Level.WARN, "Using default value for %s: %d", ConfigProperty.AS_TRANSPORT_TIMEOUT.getPropertyName(), invocationTimeout);
        }

        try {
            String receiveBufferSizeStr = (String) config.get(ConfigProperty.AS_RECEIVE_BUFFER_SIZE.getPropertyName());
            receiveBufferSize = Long.parseLong(receiveBufferSizeStr);
        } catch (Exception e) {
            LOGGER.log(Level.WARN, "Using default value for %s: %d", ConfigProperty.AS_RECEIVE_BUFFER_SIZE.getPropertyName(), receiveBufferSize);
        }

        try {
            String workerThreadCountStr = (String) config.get(ConfigProperty.AS_WORKER_THREAD_COUNT.getPropertyName());
            workerThreadCount = Integer.parseInt(workerThreadCountStr);
        } catch (Exception e) {
            LOGGER.log(Level.WARN, "Using default value for %s: %d", ConfigProperty.AS_WORKER_THREAD_COUNT.getPropertyName(), workerThreadCount);
        }

        try {
            String createIndexStr = (String) config.get(ConfigProperty.AS_CREATE_INDEX.getPropertyName());
            createIndex = Boolean.parseBoolean(createIndexStr);
        } catch (Exception e) {
            LOGGER.log(Level.WARN, "Using default value for %s: %d", ConfigProperty.AS_CREATE_INDEX.getPropertyName(), createIndex);
        }

        try {
            String applyTxnStr = (String) config.get(ConfigProperty.AS_APPLY_TXN.getPropertyName());
            applyTxn = Boolean.parseBoolean(applyTxnStr);
        } catch (Exception e) {
            LOGGER.log(Level.WARN, "Using default value for %s: %d", ConfigProperty.AS_APPLY_TXN.getPropertyName(), applyTxn);
        }

        try {
            String batchSizeStr = (String) config.get(ConfigProperty.AS_BATCH_SIZE.getPropertyName());
            batchSize = Integer.parseInt(batchSizeStr);
        } catch (Exception e) {
            LOGGER.log(Level.WARN, "Using default value for %s: %d", ConfigProperty.AS_BATCH_SIZE.getPropertyName(), batchSize);
        }

        String memberName = (String) config.get(ConfigProperty.CLUSTER_MEMBER_NAME.getPropertyName());

        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, String.format("Connecting to Metaspace:[%s] with listen url [%s] and discovery url[%s]",
                    metaspaceName, (listenUrl == null ? "default" : listenUrl), (discoverUrl == null ? "default" : discoverUrl)));
        }

        boolean connected = false;

        // Try to get existing metaspace if process already connected to metaspace, i.e. Re-deployment of webconsole on Tomcat
        metaspace = ASCommon.getMetaspace(metaspaceName);
        if (metaspace != null) {
            if (metaspace.getName().equals(metaspaceName)) {
                connected = true;
            }
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Process got connected to already existing Metaspace : %s ", metaspace.getName());
            }
        }


        while (!connected) {
            try {
                MemberDef cd = MemberDef.create();
                cd.setDiscovery(discoverUrl);
                cd.setListen(listenUrl);
                cd.setContext(context);
                cd.setWorkerThreadCount(workerThreadCount);
                cd.setRxBufferSize(receiveBufferSize);
                if (memberName != null) {
                    cd.setMemberName(memberName);
                }

                if (config.get(ConfigProperty.AS_SPACE_PERSISTENCE_DATASTORE.getPropertyName()) != null) {
                    String dataStore = (String) config.get(ConfigProperty.AS_SPACE_PERSISTENCE_DATASTORE.getPropertyName());
                    cd.setDataStore(getAbsolutePath(dataStore));
                }

                metaspace = Metaspace.connect(metaspaceName, cd);
                if (metaspace.getName().equals(metaspaceName)) {
                    connected = true;
                }
            } catch (Exception asex) {
                int sleepTimeSec = 10;
                if (LOGGER.isEnabledFor(Level.WARN)) {
                    LOGGER.log(Level.WARN, "Failed Connecting to Metaspace:[%s] with listen url [%s] and discovery url[%s]. Retrying in [%d] seconds", asex,
                            metaspaceName, (listenUrl == null ? "default" : listenUrl), (discoverUrl == null ? "default" : discoverUrl), sleepTimeSec);
                }
                try {
                    Thread.sleep(sleepTimeSec * 1000);
                } catch (InterruptedException e) {
                    LOGGER.log(Level.ERROR, "Error while sleeping for %d", e, sleepTimeSec*1000);
                }
            }
        }

        listenUrl = metaspace.getMemberDef().getListen();
        discoverUrl = metaspace.getMemberDef().getDiscovery();

        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, String.format("ActiveSpaces Data Grid Version: [%s] - Connected to Metaspace:[%s] member-name=[%s] member-id=[%s] with listen url [%s] and discovery url[%s]",
                    Metaspace.version(), metaspaceName, metaspace.getSelfMember().getName(), metaspace.getSelfMember().getId(),
                    (listenUrl == null ? "default" : listenUrl),
                    (discoverUrl == null ? "default" : discoverUrl)));
        }


        initialized = true;
    }

    public Metaspace getMetaspace() {
        return metaspace;
    }

    public Space defineSpace(String spaceName, SpaceDef spacedef,
                             boolean isSeeder) throws ASException {
        return defineSpace(spaceName, spacedef, isSeeder, true);
    }

    public Space defineSpace(String spaceName, SpaceDef spacedef,
                             boolean isSeeder, boolean shouldWait) throws ASException {
//    	try {
//    		Space space = metaspace.getSpace(spacedef.getName());
//    		if (space != null) {
//    			if (shouldWait) {
//    				for (; !space.waitForReady(5000); ) {
//    					if (LOGGER.isEnabledFor(Level.WARN)) {
//    						LOGGER.log(Level.WARN, "** Waiting for space %s to get ready", spaceName);
//    					}
//    				}
//    			}
//    			return space;
//    		}
//    		
//    	} catch (Exception e) {
//    		if(!(e instanceof ASException && ((ASException)e).getStatus().compareTo(ASStatus.SYS_ERROR) == 0)) {
//    			throw e;
//    		}
//            if (LOGGER.isEnabledFor(Level.WARN)) {
//            	LOGGER.log(Level.WARN, "** Space [%s] not found, trying to create **", spaceName);
//            }
//    	}
    	
        try {
//			spacedef.setTTL(SpaceDef.TTL_FOREVER);
            metaspace.defineSpace(spacedef);
        } catch (ASException e) {
            if (!(e.getStatus().compareTo(ASStatus.ALREADY_EXISTS) == 0)) {
                throw e;
            }
        }
        DistributionRole role = DistributionRole.SEEDER;
        if (!isSeeder) {
            role = DistributionRole.LEECH;
        }
        Space space = metaspace.getSpace(spacedef.getName(), role);

        if (spacedef.getPersistenceType() == PersistenceType.SHARE_NOTHING) {
            if (isSeeder) {
                performSpaceRecovery(metaspace, space);
            }
        }

        if (shouldWait) {
            for (; !space.waitForReady(5000); ) {
                if (LOGGER.isEnabledFor(Level.WARN)) {
                    LOGGER.log(Level.WARN, "** Waiting for space %s to get ready", spaceName);
                }
            }
        }

        return space;
    }

    public Space defineSpace(String spaceName, SpaceDef spacedef, List<IndexDef> indexDefs,
    		boolean isSeeder, boolean shouldWait) throws ASException {
//    	try {
//    		Space space = metaspace.getSpace(spacedef.getName());
//    		if (space != null) {
//    			if (shouldWait) {
//    				for (; !space.waitForReady(5000); ) {
//    					if (LOGGER.isEnabledFor(Level.WARN)) {
//    						LOGGER.log(Level.WARN, "** Waiting for space %s to get ready", spaceName);
//    					}
//    				}
//    			}
//    			return space;
//    		}
//
//    	} catch (Exception e) {
//    		if(!(e instanceof ASException && ((ASException)e).getStatus().compareTo(ASStatus.SYS_ERROR) == 0)) {
//    			throw e;
//    		}    		
//    	}
    	try {
    		for(IndexDef index:indexDefs) {
    			spacedef.addIndexDef(index);	
    		}
    		metaspace.defineSpace(spacedef);
    	} catch (ASException e) {
    		if (!(e.getStatus().compareTo(ASStatus.ALREADY_EXISTS) == 0)) {
    			throw e;
    		}
    	}
    	DistributionRole role = DistributionRole.SEEDER;
    	if (!isSeeder) {
    		role = DistributionRole.LEECH;
    	}
    	Space space = metaspace.getSpace(spacedef.getName(), role);

    	if (spacedef.getPersistenceType() == PersistenceType.SHARE_NOTHING) {
    		if (isSeeder) {
    			performSpaceRecovery(metaspace, space);
    		}
    	}

    	if (shouldWait) {
    		for (; !space.waitForReady(5000); ) {
    			if (LOGGER.isEnabledFor(Level.WARN)) {
    				LOGGER.log(Level.WARN, "** Waiting for space %s to get ready", spaceName);
    			}
    		}
    	}

    	return space;
    }
    
    public Member getMember(String spaceName, String memberId) {
        Member m = null;
        for (int i = 0; i < 3; i++) { // have seen a race condition, so try a few times.
            try {
                m = metaspace.getMember(memberId);
                if (m != null) {
                    break;
                }
                try {
                    Thread.sleep(100);
                    continue;
                } catch (Exception e) {
                }
            } catch (ASException e) {
            	LOGGER.log(Level.ERROR, "Error while getMember()", e);
            }
        }
        return m;
    }

    public Member getMember2(String spaceName, String memberId) {
        try {
            for (Member m : metaspace.getSpaceMembers(spaceName)) {
                if (m.getId().equals(memberId)) {
                    return m;
                }
            }
            return null;
        } catch (ASException e) {
            LOGGER.log(Level.ERROR, "Error while getting member",e);
        }
        return null;
    }


//	public SyncService getSyncService (Channels channel, boolean isServer, 
//			boolean isSeeder, boolean shouldWait) {
//		
//		SyncService service = syncServiceMap.get(channel);
//		if (service == null) {
//			String spaceName = prefix;
//			if (channel == Channels.SAMPLE) {
//				spaceName +=  channel.getName();
//			} else {
//				throw new RuntimeException ("Cannot create service: invalid name: " + channel);
//			}
//			service = new SyncService(this, spaceName, isSeeder, shouldWait);
//			syncServiceMap.put(channel, service);
//		}
//		return service;
//	}

//	public ASyncService getASyncService (Channels channel, 
//			boolean isSubscriber, boolean isSeeder, boolean shouldWait) {
//		
//		ASyncService service = asyncServiceMap.get(channel);
//		if (service == null) {
//			String spaceName = prefix;
//			if (channel == Channels.SAMPLE) {
//				spaceName += channel.getName();
//			} else {
//				throw new RuntimeException ("Cannot create service: invalid name: " + channel);
//			}
//			service = new ASyncService(this, spaceName, isSubscriber, isSeeder, shouldWait);
//			asyncServiceMap.put(channel, service);
//		}
//		return service;
//	}

    public void registerMemberListener(MetaspaceMemberListener lsnr) {

        try {
            Listener l = metaspace.listenMetaspaceMembers(lsnr);
            allListeners.put(l, metaspace);
        } catch (ASException e) {
            LOGGER.log(Level.ERROR, "", e);
        }
    }

    public void registerSpaceMemberListener(Space space, SpaceMemberListener lsnr) {
        try {
            Listener l = metaspace.listenSpaceMembers(space.getName(), lsnr);
            allListeners.put(l, space);
        } catch (ASException e) {
            LOGGER.log(Level.ERROR, "", e);
        }
    }

    //@Override
    public void onJoin(String arg0, Member arg1, DistributionRole arg2) {
        System.out.println("MEMBER JOINED SPACE> " + arg0);
    }

    //@Override
    public void onLeave(String arg0, Member arg1) {
        System.out.println("MEMBER LEFT SPACE> " + arg0);

    }

    //@Override
    public void onUpdate(String arg0, Member arg1, DistributionRole arg2) {
        System.out.println("MEMBER UPDT SPACE> " + arg0);

    }


    public void stop() {
        synchronized (this) {
            if (stopped) {
                return;
            }

//			for (Map.Entry<Channels, SyncService> e : syncServiceMap.entrySet()) {
//				e.getValue().destroy();
//			}
//			for (Map.Entry<Channels, ASyncService> e : asyncServiceMap
//					.entrySet()) {
//				e.getValue().destroy();
//			}

            for (Map.Entry<Listener, Object> e : allListeners.entrySet()) {

                Object value = e.getValue();
                try {
                    if (value instanceof Metaspace) {
                        ((Metaspace) value).stopListener(e.getKey());
                    } else if (value instanceof Space) {
                        ((Space) value).stopListener(e.getKey());
                    }
                } catch (ASException e1) {
                    LOGGER.log(Level.ERROR, "", e1);
                }

            }
            try {
                metaspace.closeAll();
            } catch (ASException e1) {
                LOGGER.log(Level.ERROR, "", e1);
            }

            //also remove it from the factories map
            factories.remove(metaspaceName);

            stopped = true;
        }

    }

    public long getTimeout() {
        return invocationTimeout;
    }

    public int getVirtualNodeCount() {
        return virtualNodeCount;
    }

    public boolean isCreateIndex() {
		return createIndex;
	}


	public boolean isApplyTxn() {
		return applyTxn;
	}


	public int getBatchSize() {
		return batchSize;
	}


    public static SpaceDef configureSpace(String spaceName, Properties config) {

        String spaceCachePolicy = (String) config.get(ConfigProperty.AS_SPACE_CACHE_POLICY.getPropertyName());
        String spaceCapacity = (String) config.get(ConfigProperty.AS_SPACE_CAPACITY.getPropertyName());
        String spaceEvictionPolicy = (String) config.get(ConfigProperty.AS_SPACE_EVICTION_POLICY.getPropertyName());
        String spaceForgetPolicy = (String) config.get(ConfigProperty.AS_SPACE_FORGET_POLICY.getPropertyName());
        String spaceHostAwareReplication = (String) config.get(ConfigProperty.AS_SPACE_HOST_AWARE_REPLICATION
                .getPropertyName());
        String spaceMinSeederCount = (String) config.get(ConfigProperty.AS_SPACE_MINSEEDER_COUNT.getPropertyName());

        String spacePersistenceDistributionPolicy = (String) config.get(ConfigProperty.AS_SPACE_PERSISTENCE_DISTRIBUTION_POLICY
                .getPropertyName());
        String spacePersistenceType = (String) config.get(ConfigProperty.AS_SPACE_PERSISTENCE_TYPE.getPropertyName());
        String spacePhaseCount = (String) config.get(ConfigProperty.AS_SPACE_PHASE_COUNT.getPropertyName());
        String spacePhaseInterval = (String) config.get(ConfigProperty.AS_SPACE_PHASE_INTERVAL.getPropertyName());
        String spaceReadTimeout = (String) config.get(ConfigProperty.AS_SPACE_READ_TIMEOUT.getPropertyName());
        String spaceReplicationPolicy = (String) config.get(ConfigProperty.AS_SPACE_REPLICATION_POLICY.getPropertyName());
        String spaceWriteTimeout = (String) config.get(ConfigProperty.AS_SPACE_WRITE_TIMEOUT.getPropertyName());
        String spaceUpdateTransport = (String) config.get(ConfigProperty.AS_SPACE_UPDATE_TRANSPORT.getPropertyName());
        String spaceVirtualNodeCount = (String) config.get(ConfigProperty.AS_SPACE_VIRTUAL_NODE_COUNT.getPropertyName());
        String spaceReplicationCount = (String) config.get(ConfigProperty.AS_SPACE_REPLICATION_COUNT.getPropertyName());
        String spaceDistributionPolicy = (String) config.get(ConfigProperty.AS_SPACE_DISTRIBUTION_POLICY
                .getPropertyName());
        String spacePersistencePolicy = (String) config.get(ConfigProperty.AS_SPACE_PERSISTENCE_POLICY
                .getPropertyName());
        String spaceLockScope = (String) config.get(ConfigProperty.AS_SPACE_LOCK_SCOPE.getPropertyName());
        String spaceLockTTL = (String) config.get(ConfigProperty.AS_SPACE_LOCK_TTL.getPropertyName());
        String spaceLockWait = (String) config.get(ConfigProperty.AS_SPACE_LOCK_WAIT.getPropertyName());
        String spaceWait = (String) config.get(ConfigProperty.AS_SPACE_WAIT.getPropertyName());


        SpaceDef spaceDef = SpaceDef.create();
        spaceDef.setName(spaceName);

        if (spaceCachePolicy != null) {
            if (spaceCachePolicy.equals("READ_THROUGH")) {
                spaceDef.setCachePolicy(CachePolicy.READ_THROUGH);
            } else if (spaceCachePolicy.equals("READ_WRITE_THROUGH")) {
                spaceDef.setCachePolicy(CachePolicy.READ_WRITE_THROUGH);
            }
        }

        if (spaceCapacity != null) {
            try {
                int capacity = Integer.parseInt(spaceCapacity);
                spaceDef.setCapacity(capacity);
            } catch (Exception e) {
            }
        } else {
            //spaceDef.setCapacity(10000);
        }


        if (spaceEvictionPolicy != null) {
            if (spaceEvictionPolicy.equals("LRU")) {
                spaceDef.setEvictionPolicy(EvictionPolicy.LRU);
            } else if (spaceEvictionPolicy.equals("NONE")) {
                spaceDef.setEvictionPolicy(EvictionPolicy.NONE);
            }
        }

        if (spaceForgetPolicy != null) {
            try {
                spaceDef.setForgetOldValue(Boolean.parseBoolean(spaceForgetPolicy));
            } catch (Exception e) {
            }
        }

        if (spaceHostAwareReplication != null) {
            try {
                spaceDef.setHostAwareReplication(Boolean.parseBoolean(spaceHostAwareReplication));
            } catch (Exception e) {
            }
        }

        if (spaceMinSeederCount != null) {
            try {
                spaceDef.setMinSeederCount(Integer.parseInt(spaceMinSeederCount));
            } catch (Exception e) {
            }
        }

        if (spacePersistenceDistributionPolicy != null) {
            if (spacePersistenceDistributionPolicy.equals("DISTRIBUTED")) {
                spaceDef.setPersistenceDistributionPolicy(DistributionPolicy.DISTRIBUTED);
            } else if (spacePersistenceDistributionPolicy.equals("ADDRESSED")) {
                spaceDef.setPersistenceDistributionPolicy(DistributionPolicy.ADDRESSED);
            } else if (spacePersistenceDistributionPolicy.equals("NON_DISTRIBUTED")) {
                spaceDef.setPersistenceDistributionPolicy(DistributionPolicy.NON_DISTRIBUTED);
            }
        }

        if (spacePersistenceType != null) {
            if (spacePersistenceType.equals("SHARE_NOTHING")) {
                spaceDef.setPersistenceType(PersistenceType.SHARE_NOTHING);
            } else if (spacePersistenceType.equals("SHARE_ALL")) {
                spaceDef.setPersistenceType(PersistenceType.SHARE_ALL);
            } else if (spacePersistenceType.equals("NONE")) {
                spaceDef.setPersistenceType(PersistenceType.NONE);
            }
        }

		if (spacePersistencePolicy != null) {
			if (spacePersistencePolicy.equals("SYNC")) {
				spaceDef.setPersistencePolicy(PersistencePolicy.SYNC);
			} else if (spacePersistencePolicy.equals("ASYNC")) {
				spaceDef.setPersistencePolicy(PersistencePolicy.ASYNC);
			} else if (spacePersistencePolicy.equals("NONE")) {
				spaceDef.setPersistencePolicy(PersistencePolicy.NONE);
			}
		}
		
        if (spacePhaseCount != null) {
            try {
                spaceDef.setPhaseCount(Integer.parseInt(spacePhaseCount));
            } catch (NumberFormatException e) {
            }
        }

        if (spacePhaseInterval != null) {
            try {
                spaceDef.setPhaseInterval(Integer.parseInt(spacePhaseInterval));
            } catch (NumberFormatException e) {
            }
        }

        if (spaceReadTimeout != null) {
            try {
                spaceDef.setReadTimeout(Integer.parseInt(spaceReadTimeout));
            } catch (NumberFormatException e) {
            }
        }

        if (spaceReplicationPolicy != null) {
            if (spaceReplicationPolicy.equals("SYNC")) {
                spaceDef.setReplicationPolicy(ReplicationPolicy.SYNC);
            } else if (spaceReplicationPolicy.equals("ASYNC")) {
                spaceDef.setReplicationPolicy(ReplicationPolicy.ASYNC);
            }
        }


        if (spaceWriteTimeout != null) {
            try {
                spaceDef.setWriteTimeout(Integer.parseInt(spaceWriteTimeout));
            } catch (NumberFormatException e) {
            }
        }

        if (spaceUpdateTransport != null) {
            if (spaceUpdateTransport.equals("MULTICAST")) {
                spaceDef.setUpdateTransport(UpdateTransport.MULTICAST);
            } else if (spaceUpdateTransport.equals("UNICAST")) {
                spaceDef.setUpdateTransport(UpdateTransport.UNICAST);
            }
        }


        if (spaceVirtualNodeCount != null) {
            try {
                spaceDef.setVirtualNodeCount(Integer.parseInt(spaceVirtualNodeCount));
            } catch (NumberFormatException e) {
            }
        }


        if (spaceReplicationCount != null) {
            try {
                spaceDef.setReplicationCount(Integer.parseInt(spaceReplicationCount));
            } catch (NumberFormatException e) {
            }
        }

        if (spaceDistributionPolicy != null) {
            if (spaceDistributionPolicy.equals("DISTRIBUTED")) {
                spaceDef.setDistributionPolicy(SpaceDef.DistributionPolicy.DISTRIBUTED);
            } else if (spaceDistributionPolicy.equals("DISTRIBUTED")) {
                spaceDef.setDistributionPolicy(SpaceDef.DistributionPolicy.ADDRESSED);
            } else if (spaceDistributionPolicy.equals("NON_DISTRIBUTED")) {
                spaceDef.setDistributionPolicy(SpaceDef.DistributionPolicy.NON_DISTRIBUTED);
            }
        }


        if (spaceLockScope != null) {
            if (spaceLockScope.equals("NONE")) {
                spaceDef.setLockScope(LockScope.NONE);
            } else if (spaceLockScope.equals("PROCESS")) {
                spaceDef.setLockScope(LockScope.PROCESS);
            } else if (spaceLockScope.equals("THREAD")) {
                spaceDef.setLockScope(LockScope.THREAD);
            }
        }

        if (spaceLockTTL != null) {
            if (spaceLockTTL.equals("NO_WAIT")) {
                spaceDef.setLockTTL(SpaceDef.NO_WAIT);
            } else if (spaceLockTTL.equals("WAIT_FOREVER")) {
                spaceDef.setLockTTL(SpaceDef.WAIT_FOREVER);
            } else {
                try {
                    spaceDef.setLockTTL(Long.parseLong(spaceLockTTL));
                } catch (Exception e) {
                }
            }
        }

		if (spaceWait != null) {
			try {
				spaceDef.setSpaceWait(Long.valueOf(spaceWait));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		
        if (spaceLockWait != null) {
            if (spaceLockWait.equals("NO_WAIT")) {
                spaceDef.setLockWait(SpaceDef.NO_WAIT);
            } else if (spaceLockWait.equals("WAIT_FOREVER")) {
                spaceDef.setLockWait(SpaceDef.WAIT_FOREVER);
            } else {
                try {
                    spaceDef.setLockWait(Long.parseLong(spaceLockTTL));
                } catch (Exception e) {
                }
            }
        } else {
        	spaceDef.setLockWait(SpaceDef.WAIT_FOREVER);
        }

        return spaceDef;
    }

    private void performSpaceRecovery(Metaspace metaspace, Space space) throws ASException {
        if (!space.isReady()) {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Space [%s] recovery is about to begin", space.getName());
            }

            RecoveryOptions recoveryOptions = RecoveryOptions.create().setLoadWithData(true);

            int quorum = 1;
            recoveryOptions.setQuorum(quorum);
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Setting quorum to [%s] for recovery", quorum);
            }


            try {
                metaspace.recoverSpace(space.getName(), recoveryOptions);

                for (; !space.waitForReady(30 * 1000); ) {
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG, "Space [%s] recovery in progress. Current number of items in space [%d]",
                                space.getName(), space.size());
                    }
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "Error performing recovery", e);
            }

            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Space [%s] recovery completed. Current number of items in space [%d]",
                            space.getName(), space.size());
            }
        }
    }

    String getAbsolutePath(String relativePath) {
        //return System.getProperty("user.dir") + "\\" + relativePath;
    	if (System.getProperty("os.is.windows", "false").equalsIgnoreCase("true")) {
        return System.getProperty("user.dir") + "\\" + relativePath;
    	} else {
    		return relativePath;
    	}
    }

}
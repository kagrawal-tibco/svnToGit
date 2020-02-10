package com.tibco.cep.dashboard.psvr.mal.store;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.dashboard.logging.LoggingService;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.management.ServiceDependent;
import com.tibco.cep.dashboard.psvr.mal.MALException;

public final class PersistentStoreFactory extends ServiceDependent {

	private static String SAR_PSTORE_CLASS_NAME = "com.tibco.cep.dashboard.psvr.mal.store.SharedArchivePersistentStore";

	private static String FILE_PSTORE_CLASS_NAME = "com.tibco.cep.dashboard.psvr.mal.store.FilePersistentStore";

	private static PersistentStoreFactory instance;

	public synchronized static final PersistentStoreFactory getInstance() {
		if (instance == null) {
			instance = new PersistentStoreFactory();
		}
		return instance;
	}

	private Map<Identity, PersistentStore> stores;

	protected String storeType;

	private Map<String, Constructor<? extends PersistentStore>> storageTypeToConstructorMap;

	private PersistentStoreFactory() {
		super("persistentstorefactory", "Persistent Store Factory");
		stores = new HashMap<Identity, PersistentStore>();
	}

	@Override
	protected void doInit() {
		storageTypeToConstructorMap = new HashMap<String, Constructor<? extends PersistentStore>>(2);
		storageTypeToConstructorMap.put("sar", getStoreConstructor("sar"));
		storageTypeToConstructorMap.put("file", getStoreConstructor("file"));
	}

	private Constructor<? extends PersistentStore> getStoreConstructor(String type) {
		String className = FILE_PSTORE_CLASS_NAME;
		if (type.equalsIgnoreCase("sar") == true) {
			className = SAR_PSTORE_CLASS_NAME;
		}
		try {
			return Class.forName(className).asSubclass(PersistentStore.class).getConstructor(PersistentStore.class, Identity.class);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("could not load " + className, e);
		} catch (SecurityException e) {
			throw new RuntimeException("could not query " + className + " for constrcutor information", e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("could not find appropriate constructor in " + className, e);
		}
	}

	public PersistentStore getStore(Identity identity) throws MALException {
		try {
			boolean isUserIdentity = identity instanceof UserIdentity;
			PersistentStore persistentStore = stores.get(identity);
			if (persistentStore == null) {
				String type = isUserIdentity == true ? "file" : "sar" ;
				Constructor<? extends PersistentStore> constructor = storageTypeToConstructorMap.get(type);
				if (identity.getParent() != null) {
					persistentStore = constructor.newInstance(getStore(identity.getParent()), identity);
				} else {
					persistentStore = constructor.newInstance(null, identity);
				}
				persistentStore.logger = LoggingService.getChildLogger(logger, type + "pstore");
				persistentStore.properties = properties;
				persistentStore.exceptionHandler = new ExceptionHandler(logger);
				persistentStore.messageGenerator = new MessageGenerator(parent.getName(),persistentStore.exceptionHandler);
				persistentStore.serviceContext = serviceContext;
				persistentStore.init();
				if (isUserIdentity == false) {
					//cache all non user persistence stores
					stores.put(identity, persistentStore);
				}
			}
			return persistentStore;
		} catch (IllegalArgumentException e) {
			throw new MALException("could not create persistent store for " + identity, e);
		} catch (InstantiationException e) {
			throw new MALException("could not create persistent store for " + identity, e);
		} catch (IllegalAccessException e) {
			throw new MALException("could not create persistent store for " + identity, e);
		} catch (InvocationTargetException e) {
			throw new MALException("could not create persistent store for " + identity, e);
		}
	}

	@Override
	protected boolean doStop() {
		for (PersistentStore persistentStore : stores.values()) {
			persistentStore.shutdown();
		}
		stores.clear();
		return true;
	}

}

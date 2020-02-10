package com.tibco.cep.dashboard.psvr.mal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.management.ServiceDependent;
import com.tibco.cep.dashboard.psvr.mal.managers.MALElementManager;
import com.tibco.cep.dashboard.psvr.mal.managers.MALRangeAlertElementPostProcessor;
import com.tibco.cep.dashboard.psvr.mal.managers.MALRangeAlertManager;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.kernel.service.logging.Logger;

public class MALElementManagerFactory extends ServiceDependent {

    private final String PACKAGE_NAME = MALElementManager.class.getPackage().getName() + ".";

    private static MALElementManagerFactory instance;

    public static final synchronized MALElementManagerFactory getInstance() {
        if (instance == null) {
            instance = new MALElementManagerFactory();
        }
        return instance;
    }

    private Map<String, MALElementManager> managerMap = new ConcurrentHashMap<String, MALElementManager>();

    private MALElementManagerFactory() {
        super("malelementmanagerfactory","MALElement Manager Factory");
    }

    void init(Properties properties, Logger logger, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator){
        this.properties = properties;
        this.logger = logger;
        this.exceptionHandler = exceptionHandler;
        this.messageGenerator = messageGenerator;
    }

    /**
     * Returns an instance of {@link MALElementManager} given a definition type
     * @param type The definition type of the MALElement
     * @return An instance of MALElementManager
     * @throws MALException if an instance of MALElementManager cannot be created
     * @see MALElement#getDefinitionType()
     */
    public synchronized final MALElementManager getManager(String type) throws MALException {
        try {
            MALElementManager manager = (MALElementManager) managerMap.get(type);
            if (manager == null) {
                String managerName = PACKAGE_NAME + "MAL" + type + "Manager";
                Class<? extends MALElementManager> mgrClass = Class.forName(managerName).asSubclass(MALElementManager.class);
                Constructor<? extends MALElementManager> constructor = mgrClass.getConstructor(Logger.class);
                manager = constructor.newInstance(this.logger);
                //PATCH not a good idea to have post processors , fix post 5.1.1
                if (type.equals(MALRangeAlertManager.DEFINITION_TYPE) == true) {
                	manager.setElementPostProcessor(new MALRangeAlertElementPostProcessor());
                }
                managerMap.put(type, manager);
            }
            return manager;
        } catch (ClassNotFoundException e) {
            throw new MALException("could not find manager for "+type,e);
        } catch (InstantiationException e) {
            throw new MALException("could not instantiate manager for "+type,e);
        } catch (IllegalAccessException e) {
            throw new MALException("could not access manager for "+type,e);
        } catch (SecurityException e) {
            throw new MALException("could not access manager for "+type,e);
        } catch (NoSuchMethodException e) {
            throw new MALException("could not find appropriate constructor in manager for "+type,e);
        } catch (IllegalArgumentException e) {
            throw new MALException("could not instantiate manager for "+type,e);
        } catch (InvocationTargetException e) {
            throw new MALException("could not instantiate manager for "+type,e);
        }
    }

}
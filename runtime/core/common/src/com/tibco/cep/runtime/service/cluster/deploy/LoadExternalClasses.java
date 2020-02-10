package com.tibco.cep.runtime.service.cluster.deploy;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.util.FileUtils;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.event.AdvisoryEvent;
import com.tibco.cep.runtime.model.event.AdvisoryEventDictionary;
import com.tibco.cep.runtime.model.event.impl.AdvisoryEventImpl;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.service.om.api.Invocable;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Utility to load external classes: used by cluster hot-deployer as well as startup
 * of the cluster.
 */
public class LoadExternalClasses implements Invocable {

    private static final long serialVersionUID = -3298941450895085582L;

    //TODO This should not be static really.
    private static EngineActivationListener engineActivationListener;
    
    private static final String PACKAGE_EXCLUSION_DELIM = ";";
    private static final String[] EXTENSIONS = new String[]{".class", ".rulefunctionimpl"};
    
    @Override
    public Object invoke(Map.Entry entry) throws Exception {
        Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
        Map<String, byte[]> allExternalClasses = cluster.getExternalClassesCache().getExternalClasses();
        redefineClasses(allExternalClasses, cluster.getRuleServiceProvider(), cluster, false);
        return true;
    }

    /**
     * Redefine each class in the map such that the class is reloaded.
     * @param classes
     * @param startup -> Whether executed at startup of the cluster or hot-deploy case.
     * @throws Exception
     */
	public static void redefineClasses(Map<String, byte[]> classes, RuleServiceProvider RSP, Cluster cluster, boolean startup) throws Exception {
        RuleSession ruleSession = RuleSessionManager.getCurrentRuleSession();
        if (null == ruleSession) {
            if (RSP.getRuleRuntime().getRuleSessions().length > 0) {
                ruleSession = RSP.getRuleRuntime().getRuleSessions()[0];
            } else {
                return;
            }
        }
        Logger logger = RSP.getLogger(LoadExternalClasses.class);
        if (startup) {
            engineActivationListener = new EngineActivationListener();
            if (ruleSession instanceof RuleSessionImpl) {
                RuleSessionImpl ruleSessionImpl = (RuleSessionImpl)ruleSession;
                WorkingMemoryImpl workingMemoryImpl = ((WorkingMemoryImpl)ruleSessionImpl.getWorkingMemory());
                workingMemoryImpl.addChangeListener(engineActivationListener);
            }
        }
        BEClassLoader beClassLoader = (BEClassLoader) RSP.getTypeManager();
        Map<String, byte[]> classesToLoad = new HashMap<String, byte[]>();

        for(Map.Entry<String, byte[]> entry : classes.entrySet()) {
            String className = entry.getKey();
            logger.log(Level.INFO, "Defining External Class - %s", className);

            byte[] bytes = entry.getValue();
            if (beClassLoader.activateClass(className, bytes)) {
                classesToLoad.put(className, bytes);
            }
            //a VRF impl may be a new class, so check if its
            //corresponding VRF exists
            else if (null != beClassLoader.getRuleFunctionInstance(ModelNameUtil.vrfImplClassFQNToVRFURI(className))) {
                try {
                    boolean result = beClassLoader.directAddVRFImpl(className, bytes);
                    if (result) {
                        logger.log(Level.DEBUG, "Virtual Rule Function impl class %s loaded", className);
                        //Send a success advisory
                        AdvisoryEvent advEvent =
                                new AdvisoryEventImpl(RSP.getIdGenerator().nextEntityId(AdvisoryEventImpl.class), null, AdvisoryEvent.CATEGORY_DEPLOYMENT,
                                        AdvisoryEventDictionary.DEPLOYMENT_EXTERNAL_CLASSES_SUCCESS, "External class " + className + " deployment successful");
                        if (startup) {
                            engineActivationListener.addEvent(advEvent);
                        } else {
                            ruleSession.assertObject(advEvent, true);
                        }
                    } else {
                        logger.log(Level.DEBUG, "Error loading Virtual Rule Function impl class %s", className);
                        AdvisoryEvent advEvent =
                                new AdvisoryEventImpl(RSP.getIdGenerator().nextEntityId(), null, AdvisoryEvent.CATEGORY_DEPLOYMENT,
                                        AdvisoryEventDictionary.DEPLOYMENT_EXTERNAL_CLASSES_FAILURE, "External class " + className + " deployment on member Failed");
                        if (startup) {
                            engineActivationListener.addEvent(advEvent);
                        } else {
                            ruleSession.assertObject(advEvent, true);
                        }
                    }
                } catch (Throwable throwable) {
                    //Example is bad class format.
                    logger.log(Level.ERROR, "Class Deployment Failed", throwable);
                    AdvisoryEvent advEvent =
                          new AdvisoryEventImpl(RSP.getIdGenerator().nextEntityId(), null, AdvisoryEvent.CATEGORY_DEPLOYMENT,
                                        AdvisoryEventDictionary.DEPLOYMENT_EXTERNAL_CLASSES_FAILURE, "External class " + className + " deployment on member Failed");
                    if (startup) {
                        engineActivationListener.addEvent(advEvent);
                    } else {
                        ruleSession.assertObject(advEvent, true);
                    }
                }
            } else {
                logger.log(Level.INFO, "Pre-existing class Not Found, Ignoring External Class %s", className);
                if(cluster != null) {
                	cluster.getExternalClassesCache().updateLoadInfo(className, false, null, "Virtual Function Not Registered");
                }
            }
        }

        if (classesToLoad.size() > 0) {
            beClassLoader.redefineClasses(classesToLoad);
            logger.log(Level.INFO, "Classes Loaded %s", classes);
            Iterator allClassesLoaded = classesToLoad.entrySet().iterator();
            while (allClassesLoaded.hasNext()) {
                Map.Entry<String, byte[]> entry = (Map.Entry<String, byte[]>) allClassesLoaded.next();
                String className = entry.getKey();
                if(cluster != null) {
                	cluster.getExternalClassesCache().updateLoadInfo(className, true, new Date(), "");
                }
            }
            if (null != cluster) {
                cluster.getDaoProvider().modelChanged();
            }
        }
    }
    
    public static Map<String, byte[]> loadClasses(String dirPath, String packageExclusions) {
        Map<String, byte[]> classes = new HashMap<String, byte[]>();
        String[] externalClassesExcludedPackages = null;
        if (packageExclusions != null) {
            externalClassesExcludedPackages = packageExclusions.split(PACKAGE_EXCLUSION_DELIM);
        }
        File dir = new File(dirPath);

        if (dir.isFile()) {
            FileUtils.addBytesFromJar(dir, classes, EXTENSIONS);
        } else {
            FileUtils.addBytesFromPath(dir, classes, EXTENSIONS, externalClassesExcludedPackages);
        }
        
        return classes;
    }
    
    public static String pathToClassName(String path) {
        String className;
        className = path.replace('/', '.');
        return className;
    }
    
    public static byte[] readClassFromArchiveOrDirectory(String dirPath, String className) throws Exception {
    	File dir = new File(dirPath);
        if (!dir.canRead()) {
            throw new Exception(String.format("The directory[%s] to read external classes does not have read permissions", dirPath));
        }

        byte[] classBytes;
        //Assuming it is a jar/zip
        classBytes = (dir.isFile()) ? FileUtils.getClassBytesFromJar(dir, className, EXTENSIONS)
                : FileUtils.getClassBytesFromPath(dir, className);

        if (classBytes == null || classBytes.length == 0) {
            throw new Exception("No contents for specified class could be loaded");
        }
        
        return classBytes;
    }
    
    public static String resolveClass(Entity entity, String implName, Logger logger) throws Exception {
        boolean isDebug = logger.isEnabledFor(Level.DEBUG);
        if (entity == null || !(entity instanceof RuleFunction)) {
            throw new Exception("Rule function not found");
        }
        RuleFunction vrf = (RuleFunction) entity;
        //Get fully qualified class name
        String pkgName = ModelNameUtil.vrfImplPkg(vrf);

        if (isDebug) {
            logger.log(Level.DEBUG, "Package name for Virtual Rule Function: %s", pkgName);
        }
        String implClass = ModelNameUtil.vrfImplClassShortName(implName);
        if (isDebug) {
            logger.log(Level.DEBUG, "Class name for Implementation: %s", implClass);
        }
        return pkgName + "." + implClass;
    }
}



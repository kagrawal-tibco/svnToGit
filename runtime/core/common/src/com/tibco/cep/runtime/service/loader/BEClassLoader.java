package com.tibco.cep.runtime.service.loader;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import javax.tools.JavaFileObject;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.parser.codegen.CGConstants;
import com.tibco.be.parser.codegen.stream.JavaFileObjectImpl;
import com.tibco.be.parser.codegen.stream.StreamURLHandler;
import com.tibco.be.util.OversizeStringConstants;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.entity.NamedInstance;
import com.tibco.cep.kernel.model.rule.Condition;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.BEArchiveResource;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.NullContainedConcept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.StateMachineConcept;
import com.tibco.cep.runtime.model.element.stategraph.StateGraphDefinition;
import com.tibco.cep.runtime.model.element.stategraph.StateMachineRule;
import com.tibco.cep.runtime.model.element.stategraph.StateVertex;
import com.tibco.cep.runtime.model.event.PayloadFactory;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.model.event.impl.PayloadFactoryImpl;
import com.tibco.cep.runtime.model.pojo.Pojo;
import com.tibco.cep.runtime.model.process.ObjectBean;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.om.api.DaoProvider;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.EntityDaoConfig;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.runtime.session.impl.RuleSessionManagerImpl;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.cep.util.ResourceManager;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmElement;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jan 18, 2006
 * Time: 6:29:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class BEClassLoader extends ClassManager implements TypeManager, ByteCodeLoader, DynamicLoader {

    private Map entityNSRegistry = new HashMap ();
    private Map entityClassRegistry = new HashMap () ;
    private Map entityPathRegistry = new HashMap();
    private Map ruleFunctionRegistry = new HashMap();

    private final VRFRegistry vrfRegistry;
    final protected RuleServiceProvider serviceProvider;
    final protected Logger logger;
    final protected PayloadFactory payloadFactory;
    final protected ClassLoader parentClassLoader;

    protected BEClassLoader(ClassLoader classLoader, int jdiPort, RuleServiceProvider serviceProvider) {
        super(classLoader, jdiPort);
        this.parentClassLoader = classLoader;
        this.serviceProvider = serviceProvider;
        this.logger = serviceProvider.getLogger(this.getClass());
        payloadFactory = new PayloadFactoryImpl(this);
        Class c = com.tibco.cep.runtime.model.event.impl.XiNodePayload.class;
        c = com.tibco.cep.runtime.model.event.impl.ObjectPayload.class;
        try {
            Class.forName("com.tibco.cep.driver.tibrv.serializer.TibrvMsgPayload");
        } catch (Throwable t) {
            serviceProvider.getLogger(BEClassLoader.class).log(Level.DEBUG, "TibrvMsgPayload is not available.");
        }
        vrfRegistry = new VRFRegistry(this, serviceProvider.getLogger(VRFRegistry.class), serviceProvider);
    }
    
    public RuleServiceProvider getRuleServiceProvider() {
    	return serviceProvider;
    }

    public BEClassLoader(RuleServiceProvider provider, Properties env) {
        this(getCurrentContextClassLoader(env), getJdiPort(env), provider);
        String payloadClasses = env.getProperty("be.event.payload.classes");
        if(payloadClasses != null) {
            String[] payloads = payloadClasses.split(",");
            for (final String className : payloads) {
                try {
                    Class.forName(className.trim());
                } catch (Throwable t) {
                    provider.getLogger(BEClassLoader.class).log(Level.WARN,
                            "Could not load payload class: %s", className);
                }
            }
        }
    }

    private static ClassLoader getCurrentContextClassLoader(Properties env) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader != null) return loader;

        loader = (ClassLoader)env.get("com.tibco.cep.entity.classloader.parent");
        return loader;
    }

    public PayloadFactory getPayloadFactory() {
        return payloadFactory;
    }

    private static int getJdiPort(Properties env) {
        return Integer.parseInt(env.getProperty("tibco.env.JDI_PORT", "-1"));
    }

    /**
     * Return true if valid and there is changes. false if valid but no changes.  Exception is not valid.
     * @param resultMap list of ClassLoadingResult
     * @return true if valid and there is changes. false if valid but no changes.
     * @throws Exception if not valid
     */
    public boolean validate(Map resultMap) throws Exception {
        boolean allowModify = (classesNotRedefineableMsg() == null);
        boolean hasChanges = false;
        final Cluster cluster = this.serviceProvider.getCluster();
        final DaoProvider daoProvider = (null == cluster) ? null : cluster.getDaoProvider();
        Iterator allResult = resultMap.values().iterator();
        while (allResult.hasNext()) {
            ClassLoadingResult result = (ClassLoadingResult) allResult.next();
            if (result.status == ClassLoadingResult.RESOURCE) continue;
            	
            if(result.status == ClassLoadingResult.NEW) {
            	hasChanges = true;
            	continue;
            }
            //avoid call to loadClass for VRF impls
            else if (result.status == ClassLoadingResult.VRF_ADD) {
            	//TODO: check if the rulefunction class for this impl is defined in the project, but handle the case
            	//where the virtual rulefunction and its impl are hotdeployed together
            	hasChanges = true;
            	continue;
            } else if(result.status == ClassLoadingResult.VRF_REMOVE) {
            	hasChanges = true;
            	continue;
            }

            Class jclass = loadClass(result.className);
            String beModelName = jclass.getName();

            String prefix = serviceProvider.getProperties().getProperty("be.codegen.rootPackage", "be.gen");
            if(beModelName.startsWith(prefix)) {
            	beModelName = beModelName.substring(prefix.length() +1);
            }

            switch (result.status) {
            case ClassLoadingResult.NEW:
            	hasChanges = true;
            	break;
            case ClassLoadingResult.CHANGED:
            	hasChanges = true;
            	if(!allowModify) {
            		throw new Exception(beModelName + " is modified.  Hot deployment is enabled for addition/deletion of rule or rule function only.");
            	}
            	if (isEventClass(jclass) || isPropertyClass(jclass) || (!CGConstants.HDPROPS_RUNTIME && isConceptClass(jclass))) {
            		throw new Exception(beModelName + " is modified.  Modification of Event, TimeEvent, or Property is not allowed in hot deploy.");
            	} else if (CGConstants.HDPROPS_RUNTIME && isConceptClass(jclass)) {
            		boolean ok = false;
            		final EntityDao entityDao = daoProvider.getEntityDao(jclass);
            		ok = (null != entityDao)
            				&& (entityDao.getConfig().getCacheMode() == EntityDaoConfig.CacheMode.Cache);
            		if (!ok) {
            			throw new Exception(beModelName + " is modified.  Modification of ScoreCard or Concept is not allowed in hot deploy.");
            		}
            	}
            	if(isStateMachineClass(jclass) || isStateMachineState(jclass)) {
            		throw new Exception(beModelName + " is modified.  Modification of StateMachine or it's State is not allowed in hot deploy.");
            	}
            	break;
            case ClassLoadingResult.DEACTIVATE:
            	if (isJobContextClass(jclass) || isMapperClass(jclass)) {
            		break;
            	}
            	hasChanges = true;

            	if(isConceptClass(jclass) || isEventClass(jclass) || isPropertyClass(jclass)) {
            		throw new Exception(beModelName + " is removed.  Removal of Event, TimeEvent, ScoreCard, Concept, or Property is not allowed in hot deploy.");
            	}
            	if(isStateMachineClass(jclass) || isStateMachineState(jclass)) {
            		throw new Exception(beModelName + " is deleted.  Removal of StateMachine or it's State is not allowed in hot deploy.");
            	}
            	break;
            case ClassLoadingResult.REACTIVATE_CHANGED:
            	hasChanges = true;
            	if(!allowModify) {
            		throw new Exception(beModelName + " was removed, recreated, and modified.  Hot deployment is enabled for addition/deletion of rule or rule function only.");
            	}
            	if(isConceptClass(jclass) || isEventClass(jclass) || isPropertyClass(jclass)) {
            		throw new Exception(beModelName + " was removed, recreated, and modified.  Modification of Event, TimeEvent, ScoreCard, Concept, or Property is not allowed in hot deploy.");
            	}
            	if(isStateMachineClass(jclass) || isStateMachineState(jclass)) {
            		throw new Exception(beModelName + " was removed, recreated, and modified.  Modification of StateMachine or it's State is not allowed in hot deploy.");
            	}
            	break;
            case ClassLoadingResult.REACTIVATE_NOCHANGE:
            	hasChanges = true;
            	break;
            case ClassLoadingResult.NOCHANGE:
            	//don't do anything
            	break;
            }
        }
        return hasChanges;
    }

    /*
    * The ...FunctionsOnly methods are only meant to be used by the bui for deploying decision tables in the tester.
    * For example, updateFunctionsOnly doesn't call notifyHotDeployment like update does, because deploying tables
    * isn't a full hot deployment, and deploying tables via the cluster / jmx (vs in the BUI tester) does not call notifyHotDeployment either.
    */
    public Map<String, ClassLoadingResult> loadFunctionsOnly(Map<String, byte[]> nameToByteCode) throws Exception {
        Map<String, ClassLoadingResult> resultMap = analyzeByteCodes(nameToByteCode, true);
        lockAndUpdateFunctionsOnly(resultMap);
        return resultMap;
    }

    private void lockAndUpdateFunctionsOnly(Map<String, ClassLoadingResult> resultMap) throws Exception {
        RuleSession[] archs = serviceProvider.getRuleRuntime().getRuleSessions();
        lockAndUpdateFunctionsOnly(resultMap, archs, 0);
    }

    private void lockAndUpdateFunctionsOnly(Map<String, ClassLoadingResult> resultMap, RuleSession[] archives, int sync) throws Exception {
        if(sync == archives.length) {
            updateFunctionsOnly(resultMap);
        }
        else {
            //synchronized(((RuleSessionImpl)archives[sync]).getWorkingMemory()) {
            ((RuleSessionImpl)archives[sync]).getWorkingMemory().getGuard().lock();
            try {
                lockAndUpdateFunctionsOnly(resultMap, archives, sync+1);
            } finally {
                ((RuleSessionImpl)archives[sync]).getWorkingMemory().getGuard().unlock();
            }
        }
    }

    private void updateFunctionsOnly(Map<String, ClassLoadingResult> resultMap) throws Exception {

        if ((resultMap != null) && this.logger.isEnabledFor(Level.DEBUG)) {
            String loadedClass = ResultListToString(resultMap);
            String s = ResourceManager.getInstance().formatMessage("class.loaded", loadedClass);
            this.logger.log(Level.DEBUG, s);
        }

        if(resultMap != null) {
            //commit the changes
            commitChanges(resultMap);
            //register classes
            registerClasses(resultMap);
        }

        //updateFunctionsOnly doesn't call notifyHotDeployment like update does, because deploying tables
        //isn't a full hot deployment, and deploying tables via the cluster / jmx (vs in the BUI tester) does not call notifyHotDeployment either.
        //((RuleSessionManagerImpl)serviceProvider.getRuleRuntime()).notifyHotDeployment(true);
    }

    public boolean lockAndUpdate(Map newArchives, Map resultMap, Properties oversizeStringConstants) throws Exception {
        RuleSession[] archs = serviceProvider.getRuleRuntime().getRuleSessions();
        return lockAndUpdate(newArchives, archs, 0, resultMap, oversizeStringConstants);
    }

    private boolean lockAndUpdate(Map newArchives, RuleSession[] archives, int sync, Map resultMap, Properties oversizeStringConstants) throws Exception {
        if(sync == archives.length) {
            return update(newArchives, resultMap, oversizeStringConstants);
        }
        else {
            //synchronized(((RuleSessionImpl)archives[sync]).getWorkingMemory()) {
            ((RuleSessionImpl)archives[sync]).getWorkingMemory().getGuard().lock();
            try {
                return lockAndUpdate(newArchives, archives, sync+1, resultMap, oversizeStringConstants);
            } finally {
                ((RuleSessionImpl)archives[sync]).getWorkingMemory().getGuard().unlock();
            }
        }
    }

//    private void setupRuleToScore() {
//        Map temp = new HashMap(ScoreBasedConflictResolver.ruleToScore);
//
//        Iterator ite = temp.entrySet().iterator();
//        while(ite.hasNext()) {
//            Map.Entry e = (Map.Entry) ite.next();
//            Object func = e.getValue();
//            if(func instanceof String) {
//                RuleFunction f = this.getRuleFunctionInstance((String) func);
//                ScoreBasedConflictResolver.ruleToScore.put(e.getKey(), f);
//            }
//        }
//    }

    private boolean update(Map newArchives, Map resultMap, Properties oversizeStringConstants) throws Exception {
        if ((resultMap != null) && this.logger.isEnabledFor(Level.DEBUG)) {
            String loadedClass = ResultListToString(resultMap);
            String s = ResourceManager.getInstance().formatMessage("class.loaded", loadedClass);
            this.logger.log(Level.DEBUG, s);
        }

        if (resultMap != null) {
            //commit the changes
            commitChanges(resultMap);

            //register classes
            registerClasses(resultMap);

            //setupRuleToScore();

            //update rule sessions
            updateRuleSessions(newArchives, resultMap);
        }

        //deploy and undeploy ruleSets
        boolean result = deployRuleSets(newArchives);
        OversizeStringConstants.init(oversizeStringConstants, this);

        applyChanges(newArchives, true);

        ((RuleSessionManagerImpl)serviceProvider.getRuleRuntime()).notifyHotDeployment(true);

        return result;
    }

    private boolean deployRuleSets(Map newArchives) throws Exception {
        boolean changes = false;
        Iterator ite = newArchives.values().iterator();
        while(ite.hasNext()) {
            BEArchiveResource archive = (BEArchiveResource) ite.next();
            RuleSessionManagerImpl rsmgr = (RuleSessionManagerImpl) serviceProvider.getRuleRuntime();
            List<RuleSessionImpl> sessions = (List<RuleSessionImpl>) (List) rsmgr.getRelatedRuleSessions(archive.getReferenceClassName());
            for(RuleSessionImpl session : sessions) {
                if(session == null) continue;
                session.setSession();
                if (session.getWorkingMemory().getRuleLoader().deployRulesToWM(this.getResolvedRuleUris(archive))) {
                    changes = true;
                }
                //sharing levels only matter w/ cache and since it appears cache doesn't let you add or remove concepts
                //or change sharing levels then this could be simplified to only call initJoinTables on any newly created rules
                ((WorkingMemoryImpl)session.getWorkingMemory()).initEntitySharingLevels();
                session.resetSession();
            }
        }
        return changes;
    }

    private Set<String> getResolvedRuleUris(BEArchiveResource archive) {
        final Set<String> resolvedRuleUris = new HashSet<String>();

        final Set<String> unresolvedRuleUris = archive.getDeployedRuleUris();
        final String separator = String.valueOf(Folder.FOLDER_SEPARATOR_CHAR);

        for (Object o : this.getTypeDescriptors(TypeManager.TYPE_RULE)) {
            final TypeManager.TypeDescriptor descriptor = (TypeManager.TypeDescriptor) o;

            // Ear based Hot deployment is applicable for only Rule/Rule Functions, exclude RT's if any
            RuleSessionManagerImpl rsmgr = (RuleSessionManagerImpl) serviceProvider.getRuleRuntime();
            RuleSessionImpl currentRuleSession = (RuleSessionImpl) rsmgr.getCurrentRuleSession();
            if(currentRuleSession == null) continue;

            if (!currentRuleSession.isRuleTemplate(descriptor)) {
                final String uri = descriptor.getURI();
                for (String unresolvedRuleUri : unresolvedRuleUris) {
                    if (uri.equals(unresolvedRuleUri)
                            || (unresolvedRuleUri.endsWith(separator) && uri.startsWith(unresolvedRuleUri))
                            || ((!unresolvedRuleUri.endsWith(separator)) && uri.startsWith(unresolvedRuleUri + separator))) {
                        resolvedRuleUris.add(uri);
                    }
                }
            }
        }
        
        // check for existing RTIs that were previously loaded
        try {
        	Class templateConfigRegistryClass = Class.forName("com.tibco.cep.interpreter.template.TemplateConfigRegistry");
        	Method getInstanceDef = templateConfigRegistryClass.getMethod("getInstance");
        	Object templateConfigRegistryInstance = getInstanceDef.invoke(null, null);
        	
        	Method getTemplateURIsDef = templateConfigRegistryClass.getMethod("getTemplateURIs");
        	
        	resolvedRuleUris.addAll((Collection<String>)getTemplateURIsDef.invoke(templateConfigRegistryInstance, null));
        } catch (Exception exception) {
        	logger.log(Level.ERROR, "Failed retrive existing RTI uri's.", exception);
        }
        
        return resolvedRuleUris;
    }

    private void applyChanges(Map newArchives, boolean loadOnly) throws Exception {
        Iterator ite = newArchives.values().iterator();
        while(ite.hasNext()) {
            BEArchiveResource archive = (BEArchiveResource) ite.next();
            for(RuleSession rs: serviceProvider.getRuleRuntime().getRuleSessions()) {
//            for(RuleSessionImpl session: getRuleSessions(archive)) {
                RuleSessionImpl session = (RuleSessionImpl) rs;
                if(!session.getName().startsWith(archive.getName())) {
                    continue;
                }
//                RuleSessionImpl session = (RuleSessionImpl) serviceProvider.getRuleRuntime().getRuleSession(archive.getName());
                if(session == null) continue;
                if(loadOnly)
                    session.loadObjectToAddedRule();
                else
                    session.applyObjectToAddedRules();
            }
        }
    }

    public Set getRegisteredEntities() {
        return entityNSRegistry.entrySet();
    }

    /**
     * Loads classes based on class loading Result
     * @param result
     * @throws Exception
     */
    public void registerClass(ClassLoadingResult result) throws Exception {
        switch (result.status) {
            case ClassLoadingResult.NEW:
                registerClass(loadClass(result.className));
                break;
            case ClassLoadingResult.CHANGED:
                //do nothing - event/concept is not changable, rule will be handled next
                //todo - how about state machine rule changes
                break;
            case ClassLoadingResult.DEACTIVATE:
                unregisterClass(loadClass(result.className));
                break;
            case ClassLoadingResult.REACTIVATE_CHANGED:
                registerClass(loadClass(result.className));
                break;
            case ClassLoadingResult.REACTIVATE_NOCHANGE:
                registerClass(loadClass(result.className));
                break;
            case ClassLoadingResult.NOCHANGE:
                //don't do anything
                break;
            case ClassLoadingResult.VRF_ADD:
                break;
            case ClassLoadingResult.VRF_REMOVE:
                String vrfURI = ModelNameUtil.vrfImplClassFQNToVRFURI(result.className);
                String implName = ModelNameUtil.vrfImplClassFQNToImplName(result.className);
                vrfRegistry.removeVRFImpl(vrfURI, implName);
        }
    }

    public void registerClasses(Map resultMap) throws Exception {
        //2 pahse - remove first and then add
        Iterator i = resultMap.values().iterator();
        while (i.hasNext()) {
            ClassLoadingResult result = (ClassLoadingResult) i.next();
            switch (result.status) {
                case ClassLoadingResult.NEW:
                    break;
                case ClassLoadingResult.CHANGED:
                    //do nothing - event/concept is not changable, rule will be handled next
                    //todo - how about state machine rule changes
                    break;
                case ClassLoadingResult.DEACTIVATE:
                    unregisterClass(loadClass(result.className));
                    break;
                case ClassLoadingResult.REACTIVATE_CHANGED:
                    break;
                case ClassLoadingResult.REACTIVATE_NOCHANGE:
                    break;
                case ClassLoadingResult.NOCHANGE:
                    //don't do anything
                    break;
                case ClassLoadingResult.VRF_ADD:
                    break;
                case ClassLoadingResult.VRF_REMOVE:
                    String vrfURI = ModelNameUtil.vrfImplClassFQNToVRFURI(result.className);
                    String implName = ModelNameUtil.vrfImplClassFQNToImplName(result.className);
                    vrfRegistry.removeVRFImpl(vrfURI, implName);
                    break;
            }
        }
        i = resultMap.values().iterator();
        while (i.hasNext()) {
            ClassLoadingResult result = (ClassLoadingResult) i.next();
            Class cls;
            switch (result.status) {
                case ClassLoadingResult.NEW:
                    cls = loadClass(result.className);
                    if(StaticInitHelper.initializeClass(cls, serviceProvider.getLogger(StaticInitHelper.class), serviceProvider))
                        registerClass(cls);
                    break;
                case ClassLoadingResult.CHANGED:
                    //for concept property addition
                    cls = loadClass(result.className);
                    StaticInitHelper.initializeClass(cls, serviceProvider.getLogger(StaticInitHelper.class), serviceProvider);
                    break;
                case ClassLoadingResult.DEACTIVATE:
                    break;
                case ClassLoadingResult.REACTIVATE_CHANGED:
                    cls = loadClass(result.className);
                    if(StaticInitHelper.initializeClass(cls, serviceProvider.getLogger(StaticInitHelper.class), serviceProvider))
                        registerClass(cls);
                    break;
                case ClassLoadingResult.REACTIVATE_NOCHANGE:
                    cls = loadClass(result.className);
                    if(StaticInitHelper.initializeClass(cls, serviceProvider.getLogger(StaticInitHelper.class), serviceProvider))
                        registerClass(cls);
                    break;
                case ClassLoadingResult.NOCHANGE:
                    //don't do anything
                    break;
                case ClassLoadingResult.VRF_ADD:
                    vrfRegistry.addVRFImpl(result.className, result.byteCode);
                    break;
                case ClassLoadingResult.VRF_REMOVE:
                    break;
            }
        }
    }

    private void updateRuleSessions(Map newArchives, Map resultMap) throws Exception {
        Iterator ite = newArchives.values().iterator();
        while(ite.hasNext()) {
            BEArchiveResource archive = (BEArchiveResource) ite.next();
            for(RuleSession rs: serviceProvider.getRuleRuntime().getRuleSessions()) {
                RuleSessionImpl session = (RuleSessionImpl) rs;
                if(!session.getName().startsWith(archive.getName())) {
                    continue;
                }
//            RuleSessionImpl session = (RuleSessionImpl) serviceProvider.getRuleRuntime().getRuleSession(archive.getName());
//            if(session == null) continue;
                session.setSession();

                Iterator allResult = resultMap.values().iterator();
                while (allResult.hasNext()) {
                    ClassLoadingResult result = (ClassLoadingResult) allResult.next();
                    
                    if (result.status == ClassLoadingResult.RESOURCE) continue;
                    
                    //don't load VRF impl classes with BEClassLoader
                    if((result.status == ClassLoadingResult.VRF_ADD) || (result.status == ClassLoadingResult.VRF_REMOVE)) {
                        continue;
                    }
                    Class jclass = loadClass(result.className);
//                if(isNamedInstance(jclass)) {
//                    NamedInstance inst = null;
//                    long id;
//                    switch (result.status) {
//                        case ClassLoadingResult.NEW:
//                        case ClassLoadingResult.REACTIVATE_NOCHANGE:
//                            id = serviceProvider.getIdGenerator().nextEntityId();
//                            Constructor cons = jclass.getConstructor(new Class[] {long.class});
//                            inst = (NamedInstance) cons.newInstance(new Object[] {new Long(id)});
//                            if(session.getObjectManager().getElement(((Concept)inst).getId()) == null)
//                                session.assertObject(inst, true);
//                            break;
//                        case ClassLoadingResult.DEACTIVATE:
//                            Field staticIdField = jclass.getField("staticId");
//                            id = staticIdField.getLong(null);
//                            inst = (NamedInstance) session.getObjectManager().getElement(id);
//                            if(inst != null)
//                                session.retractObject(inst, true);
//                            break;
//                        case ClassLoadingResult.CHANGED:
//                        case ClassLoadingResult.REACTIVATE_CHANGED:
//                            //not allow
//                            break;
//                    }
//                }

                    if (!session.cacheServer && isRuleClass(jclass) && !isRuleTemplateClass(jclass)) {
                        switch (result.status) {
                            case ClassLoadingResult.NEW:
                            case ClassLoadingResult.REACTIVATE_CHANGED:
                            case ClassLoadingResult.REACTIVATE_NOCHANGE: {
                                final Rule rule = session.getWorkingMemory().getRuleLoader().loadRule(jclass);
                                if (this.isStateMachineRuleClass(jclass)) {
                                    session.getWorkingMemory().getRuleLoader().deployRuleToWM(rule.getUri());
                                }
                            }
                            break;
                            case ClassLoadingResult.CHANGED:
                                session.getWorkingMemory().getRuleLoader().deployModifiedRuleToWM(jclass);
                                break;
                            case ClassLoadingResult.DEACTIVATE:
                                session.getWorkingMemory().getRuleLoader().unloadRule(jclass);
                                break;
                            case ClassLoadingResult.NOCHANGE:
                                Rule r = session.getWorkingMemory().getRuleLoader().getRule(jclass);
                                if(r != null && isRuleChanged(r, resultMap)) {
                                    session.getWorkingMemory().getRuleLoader().deployModifiedRuleToWM(jclass);
                                }
                                break;
                        }
                    }
                }
                session.registerTypes();
                //session.loadNamedInstances();
                session.resetSession();
            }
        }
    }

    public void registerClass(Class c) {
        try {
            if (isRuleClass(c)) {
                registerRuleClass(c);
            } else if (isStateMachineClass(c)) {
                registerStateMachine(c);
            } else if (isConceptClass(c)) {
                registerConceptClass(c);
            } else if (isEventClass(c)) {
                registerEventClass(c);
            } else if (isFunctionClass(c)) {
                registerFunctionClass(c);
            } else if (isPojoClass(c)) {
                registerPojoClass(c);
            } else if(isObjectTupleClass(c)) {
                registerObjectTupleClass(c);
            }
            else {
                //todo - nl - check other classes
                //System.out.println("Remove this later - register class " + c + " ?????");
            }
        } finally {
            try {
                // do static initialization
                Class z=Class.forName(c.getName(), true, this);
            } catch (Throwable e) {
                logger.log(Level.ERROR, "Failed to load class:"+c.getCanonicalName(),e);
            }
        }
    }

    private void registerPojoClass(Class c) {
        String modelPath = ModelNameUtil.generatedClassNameToModelPath(c.getName());
        ExpandedName namespace= getNamespaceFromJavaClass(c);
        SmElement element = serviceProvider.getProject().getSmElement(namespace);

        TypeDescriptor oldDescriptor = (TypeDescriptor) entityNSRegistry.get(namespace);
        if(oldDescriptor != null && !oldDescriptor.getImplClass().equals(c)) {
            throw new RuntimeException("Trying to register namespace <" + namespace + "> for class <"
                    + c.getName() + "> but it is already registered for <" + oldDescriptor.getImplClass().getName() +">");
        }
        TypeDescriptor descriptor;
        descriptor = new TypeDescriptor(TypeManager.TYPE_POJO, modelPath, c, namespace, element, TypeManager.METRIC_TYPE_FALSE);

        entityNSRegistry.put(namespace, descriptor);
        entityClassRegistry.put(c, descriptor);
        entityPathRegistry.put(modelPath, descriptor);
    }

    private void registerObjectTupleClass(Class c) {
        String modelPath = ModelNameUtil.generatedClassNameToModelPath(c.getName());
        ExpandedName namespace= getNamespaceFromJavaClass(c);
        SmElement element = serviceProvider.getProject().getSmElement(namespace);

        TypeDescriptor oldDescriptor = (TypeDescriptor) entityNSRegistry.get(namespace);
        if(oldDescriptor != null && !oldDescriptor.getImplClass().equals(c)) {
            throw new RuntimeException("Trying to register namespace <" + namespace + "> for class <"
                    + c.getName() + "> but it is already registered for <" + oldDescriptor.getImplClass().getName() +">");
        }
        TypeDescriptor descriptor;
        descriptor = new TypeDescriptor(TypeManager.TYPE_OBJECT_BEAN, modelPath, c, namespace, element, TypeManager.METRIC_TYPE_FALSE);

        entityNSRegistry.put(namespace, descriptor);
        entityClassRegistry.put(c, descriptor);
        entityPathRegistry.put(modelPath, descriptor);
    }

    private boolean isPojoClass(Class c) {
        return (c != null && Pojo.class.isAssignableFrom(c));
    }

    private boolean isObjectTupleClass(Class c) {
        return (c != null && ObjectBean.class.isAssignableFrom(c));
    }

    public void unregisterClass(Class c) {
        if (isRuleClass(c)) {
            unregisterRuleClass(c);
        } else if (isConceptClass(c)) {
            unregisterConceptClass(c);
        } else if (isEventClass(c)) {
            unregisterEventClass(c);
        } else if (isFunctionClass(c)){
            unregisterFunctionClass(c);
        }
        else {
            //todo - nl - check other classes
            //System.out.println("Unregister class " + c + "....");
        }
        StaticInitHelper.uninitializeClass(c, serviceProvider.getLogger(StaticInitHelper.class), serviceProvider);
    }

    private void registerFunctionClass(Class c) {
        String modelPath = ModelNameUtil.generatedClassNameToModelPath(c.getName());
        ExpandedName ns = getExpandedName(c);
        TypeDescriptor oldDescriptor = (TypeDescriptor) entityNSRegistry.get(ns);
        if(oldDescriptor != null && !oldDescriptor.getImplClass().equals(c)) {
            throw new RuntimeException("Trying to register namespace <" + ns + "> for class <"
                    + c.getName() + "> but it is already registered for <" + oldDescriptor.getImplClass().getName() +">");
        }
        TypeDescriptor descriptor = new TypeDescriptor(TypeManager.TYPE_RULEFUNCTION, modelPath, c, ns, null, TypeManager.METRIC_TYPE_FALSE);
        entityNSRegistry.put(ns, descriptor);
        entityClassRegistry.put(c, descriptor);
        entityPathRegistry.put(modelPath, descriptor);
        try {
            RuleFunction function = (RuleFunction) c.newInstance();
            ruleFunctionRegistry.put(modelPath, function);
            if (this.logger.isEnabledFor(Level.DEBUG)) {
                this.logger.log(Level.DEBUG,
                        ResourceManager.getInstance().formatMessage("rulefunction.deployed", function.getSignature()));
            }
        }
        catch(Exception ex) {
            this.logger.log(Level.ERROR, ex, ex.getMessage());
        }
    }

    private void unregisterFunctionClass(Class c) {
        String modelPath = ModelNameUtil.generatedClassNameToModelPath(c.getName());
        ExpandedName ns = getExpandedName(c);
        entityNSRegistry.remove(ns);
        entityClassRegistry.remove(c);
        entityPathRegistry.remove(modelPath);
        RuleFunction function = (RuleFunction) ruleFunctionRegistry.remove(modelPath);
        vrfRegistry.clearVRFImpls(modelPath);
        if (this.logger.isEnabledFor(Level.DEBUG)) {
            this.logger.log(Level.DEBUG,
                    ResourceManager.getInstance().formatMessage("rulefunction.undeployed", function.getSignature()));
        }
    }

    public RuleFunction getRuleFunctionInstance(String URI) {
        return (RuleFunction) ruleFunctionRegistry.get(URI);
    }

    public void lockAndClearAllVRFImpls() throws Exception {
        RuleSession[] archs = serviceProvider.getRuleRuntime().getRuleSessions();
        lockAndClearAllVRFImpls(archs, null, 0);
    }

    /**
     * All impls of each specified VRF will be cleared
     * @param vrfURIs a list of Virtual Rule function URIs
     * @throws Exception
     */
    public void lockAndClearVRFImpls(Collection<String> vrfURIs) throws Exception {
        RuleSession[] archs = serviceProvider.getRuleRuntime().getRuleSessions();
        lockAndClearAllVRFImpls(archs, vrfURIs, 0);
    }

    //used by InferenceAgent
    public boolean directClearVRFImpl(String vrfURI, String implName) {
        return vrfRegistry.removeVRFImpl(vrfURI, implName);
    }

    public boolean directAddVRFImpl(String generatedClassName, byte[] classBytes) throws IllegalAccessException, InstantiationException {
        return vrfRegistry.addVRFImpl(generatedClassName, classBytes);
    }

    private void lockAndClearAllVRFImpls(RuleSession[] archives, Collection<String> vrfURIs, int sync) throws Exception {
        if(sync == archives.length) {
            if(vrfURIs == null) {
                vrfRegistry.clearAllImpls();
            } else {
                vrfRegistry.clearVRFImpls(vrfURIs);
            }
        }
        else {
            //synchronized(((RuleSessionImpl)archives[sync]).getWorkingMemory()) {
            ((RuleSessionImpl)archives[sync]).getWorkingMemory().getGuard().lock();
            try {
                lockAndClearAllVRFImpls(archives, vrfURIs, sync+1);
            } finally {
                ((RuleSessionImpl)archives[sync]).getWorkingMemory().getGuard().unlock();
            }
        }
    }

    public VRFRegistry getVRFRegistry() {
        return vrfRegistry;
    }

    /**
     * Registers an Event Class with this ClassLoader.
     * In order to be successfully registered, the Event Class must have a static field named <code>type</code>,
     * which will define the namespace of this Event Class.
     * @param c a Class that is Event or a superclass of Event.
     */
    private void registerEventClass(Class c) {
        String modelPath = ModelNameUtil.generatedClassNameToModelPath(c.getName());
        ExpandedName namespace= getNamespaceFromJavaClass(c);
        SmElement element = serviceProvider.getProject().getSmElement(namespace);

        TypeDescriptor oldDescriptor = (TypeDescriptor) entityNSRegistry.get(namespace);
        if(oldDescriptor != null && !oldDescriptor.getImplClass().equals(c)) {
            throw new RuntimeException("Trying to register namespace <" + namespace + "> for class <"
                    + c.getName() + "> but it is already registered for <" + oldDescriptor.getImplClass().getName() +">");
        }
        TypeDescriptor descriptor;
        if(isTimeEventClass(c))
            descriptor = new TypeDescriptor(TypeManager.TYPE_TIMEEVENT, modelPath, c, namespace, element, TypeManager.METRIC_TYPE_FALSE);
        else
            descriptor = new TypeDescriptor(TypeManager.TYPE_SIMPLEEVENT, modelPath, c, namespace, element, TypeManager.METRIC_TYPE_FALSE);
        entityNSRegistry.put(namespace, descriptor);
        entityClassRegistry.put(c, descriptor);
        entityPathRegistry.put(getFolderPathFromClass(c), descriptor);
    }//registerEventClass

    private void unregisterEventClass(Class c) {
        ExpandedName namespace= getNamespaceFromJavaClass(c);
        entityNSRegistry.remove(namespace);
        entityClassRegistry.remove(c);
        entityPathRegistry.remove(getFolderPathFromClass(c));
    }

    /**
     * Registers an StateMachine Class with this ClassLoader.
     * In order to be successfully registered, the Concept Class must have a static field named <code>type</code>,
     * which will define the namespace of this Concept Class.
     * @param c a Class that is Concept or a superclass of Concept.
     */
    private void registerStateMachine(Class c) {
        String smURI=ModelNameUtil.generatedClassNameToStateMachineModelPath(c.getName());
        ExpandedName namespace= getNamespaceFromJavaClass(c);

        TypeDescriptor oldDescriptor = (TypeDescriptor) entityNSRegistry.get(namespace);
        if(oldDescriptor != null && !oldDescriptor.getImplClass().equals(c)) {
            throw new RuntimeException("Trying to register namespace <" + namespace + "> for class <"
                    + c.getName() + "> but it is already registered for <" + oldDescriptor.getImplClass().getName() +">");
        }
        TypeDescriptor descriptor;
        descriptor = new TypeDescriptor(TypeManager.TYPE_STATEMACHINE, smURI, c, namespace, null, TypeManager.METRIC_TYPE_FALSE);
        entityNSRegistry.put(namespace, descriptor);
        entityClassRegistry.put(c, descriptor);
        entityPathRegistry.put(getFolderPathFromClass(c), descriptor);
        try {
            if (this.logger.isEnabledFor(Level.DEBUG)) {
                String conceptInfo = ElementIndexAssigner.printIndex(c);
                String s = ResourceManager.getInstance().formatMessage("concept.descriptor", conceptInfo);
                logger.log(Level.DEBUG, s);
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Registers an Concept Class with this ClassLoader. The Event Class will subsequently be available
     * through
     * In order to be successfully registered, the Concept Class must have a static field named <code>type</code>,
     * which will define the namespace of this Concept Class.
     * @param c a Class that is Concept or a superclass of Concept.
     */
    private void registerConceptClass(Class c) {
        String modelPath = ModelNameUtil.generatedClassNameToModelPath(c.getName());
        ExpandedName namespace= getNamespaceFromJavaClass(c);

        TypeDescriptor oldDescriptor = (TypeDescriptor) entityNSRegistry.get(namespace);
        if(oldDescriptor != null && !oldDescriptor.getImplClass().equals(c)) {
            throw new RuntimeException("Trying to register namespace <" + namespace + "> for class <"
                    + c.getName() + "> but it is already registered for <" + oldDescriptor.getImplClass().getName() +">");
        }
        SmElement element = serviceProvider.getProject().getSmElement(namespace);
        TypeDescriptor descriptor;
        if(isNamedInstance(c))
            descriptor = new TypeDescriptor(TypeManager.TYPE_NAMEDINSTANCE, modelPath, c, namespace, element, TypeManager.METRIC_TYPE_FALSE);
        else {
            int metricType = TypeManager.METRIC_TYPE_FALSE;
            if (c.getSuperclass().getName().equals("com.tibco.cep.runtime.model.element.impl.MetricImpl")) {
                metricType = TypeManager.METRIC_TYPE_PRIMARY;
            } else if (c.getSuperclass().getName().equals("com.tibco.cep.runtime.model.element.impl.MetricDVMImpl")) {
                metricType = TypeManager.METRIC_TYPE_DVM;
            }
            /* Need to fix this to go to the super class
            Class[] interfaces = c.getInterfaces();
            for (int i=0; i<interfaces.length; i++) {
                if (interfaces[i].getName().equals("com.tibco.cep.runtime.model.element.Metric")) {
                    metricType = TypeManager.METRIC_TYPE_PRIMARY;
                    break;
                } else if (interfaces[i].getName().equals("com.tibco.cep.runtime.model.element.MetricDVM")) {
                    metricType = TypeManager.METRIC_TYPE_DVM;
                    break;
                }
            }
            */
            descriptor = new TypeDescriptor(TypeManager.TYPE_CONCEPT, modelPath, c, namespace, element, metricType);
        }




        try {
            entityNSRegistry.put(namespace, descriptor);
            entityClassRegistry.put(c, descriptor);
            entityPathRegistry.put(getFolderPathFromClass(c), descriptor);
            if (this.logger.isEnabledFor(Level.DEBUG)) {
                String conceptInfo = ElementIndexAssigner.printIndex(c);
                String s = ResourceManager.getInstance().formatMessage("concept.descriptor", conceptInfo);
                logger.log(Level.DEBUG, s);
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private String getFolderPathFromClass(Class clz) {
        try {
            Entity entity = (Entity) clz.newInstance();
            return entity.getType().substring(TypeManager.DEFAULT_BE_NAMESPACE_URI.length());

        }
        catch (Exception e) { }
        return null;
    }

    private void unregisterConceptClass(Class c) {

        ExpandedName namespace = getNamespaceFromJavaClass(c);
        entityNSRegistry.remove(namespace);
        entityClassRegistry.remove(c);
        entityPathRegistry.remove(getFolderPathFromClass(c));
        try {
            final Field f = c.getField("$$rootStateGraph");
            StateGraphDefinition sgd = (StateGraphDefinition) f.get(null);
            final Class[] rules = sgd.getRuleClasses();
            for (int i = 0; i < rules.length; i++) {
                Class rc = rules[i];
                unregisterRuleClass(rc);    //todo - state machine class???
            }
        }
        catch (java.lang.NoSuchFieldException ex) {
//            ex.printStackTrace();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

//    private void unregisterProcessClass(Class c) {
//        ExpandedName namespace = getNamespaceFromJavaClass(c);
//        entityNSRegistry.remove(namespace);
//        entityClassRegistry.remove(c);
//    }

    private void registerRuleClass(Class c) {
        String modelPath = ModelNameUtil.generatedClassNameToModelPath(c.getName());
        ExpandedName ns = getExpandedName(c);
        TypeDescriptor oldDescriptor = (TypeDescriptor) entityNSRegistry.get(ns);
        if(oldDescriptor != null && !oldDescriptor.getImplClass().equals(c)) {
            throw new RuntimeException("Trying to register namespace <" + ns + "> for class <"
                    + c.getName() + "> but it is already registered for <" + oldDescriptor.getImplClass().getName() +">");
        }
        TypeDescriptor descriptor = new TypeDescriptor(TypeManager.TYPE_RULE, modelPath, c, ns, null, TypeManager.METRIC_TYPE_FALSE);
        entityNSRegistry.put(ns, descriptor);
        entityClassRegistry.put(c, descriptor);
        entityPathRegistry.put(modelPath, descriptor);

//        String path = "be.engine.rule.score" + modelPath.replace('/', '.');
//        String scoreFunc = System.getProperty(path);
//        if(scoreFunc != null) {
//            ScoreBasedConflictResolver.ruleToScore.put(c, scoreFunc);
//        }
    }

    private void unregisterRuleClass(Class c) {
        entityNSRegistry.remove(getExpandedName(c));
        entityClassRegistry.remove(c);
        entityPathRegistry.remove(ModelNameUtil.generatedClassNameToModelPath(c.getName()));
    }

    private boolean isFunctionClass(Class c) {
        return (c != null) && RuleFunction.class.isAssignableFrom(c);
    }

    private boolean isStateMachineRuleClass(Class c) {
        return (c != null) && StateMachineRule.class.isAssignableFrom(c);
    }

    private boolean isRuleClass(Class c) {
        return (c != null) && Rule.class.isAssignableFrom(c);
    }//isaRule


    private boolean isRuleTemplateClass(Class c) {
        try {
            return isType(c, Class.forName("com.tibco.cep.interpreter.template.TemplatedRule"));
        } catch(ClassNotFoundException e) {
            return false;
        }
    }//isRuleTemplateClass


    private boolean isType(Class c, Class type) {
        return (c != null) && type.isAssignableFrom(c) ;
    }//isConceptClass

    private boolean isConceptClass(Class c) {
        return (c != null) && Concept.class.isAssignableFrom(c) && !NullContainedConcept.class.isAssignableFrom(c);
    }//isConceptClass

    private boolean isJobContextClass(Class c) {
        try {
            return isType(c,Class.forName("com.tibco.cep.bpmn.runtime.model.JobContext"));
        } catch (ClassNotFoundException e) {
            return false;
        }
    }//isJobContextClass
    
    private boolean isMapperClass(Class c) {
        try {
            return isType(c,Class.forName("com.tibco.cep.bpmn.runtime.activity.mapper.Mapper"));
        } catch (ClassNotFoundException e) {
            return false;
        }
    }//isMapperClass

    private boolean isTimeEventClass(Class c) {
        return (c != null) && TimeEvent.class.isAssignableFrom(c);
    }//isAnEventClass

    private boolean isEventClass(Class c) {
        return (c != null) && Event.class.isAssignableFrom(c);
    }//isAnEventClass

    private boolean isNamedInstance(Class c) {
        return (c != null) && NamedInstance.class.isAssignableFrom(c);
    }

    private boolean isPropertyClass(Class c) {
        return (c != null) && Property.class.isAssignableFrom(c);
    }

    private boolean isStateMachineClass (Class c) {
        assert!((c != null) && StateMachineConcept.class.isAssignableFrom(c) && NullContainedConcept.class.isAssignableFrom(c));
        return (c != null) && StateMachineConcept.class.isAssignableFrom(c) && !NullContainedConcept.class.isAssignableFrom(c);
    }

    private boolean isStateMachineState(Class c) {
        return (c != null) && StateVertex.class.isAssignableFrom(c);
    }

    private boolean isRuleChanged(Rule ruleObj, Map resultMap) throws Exception {
        Condition[] conditions = ruleObj.getConditions();
        for(int i = 0; i < conditions.length; i++) {
            String className = conditions[i].getClass().getName();
            ClassLoadingResult result = (ClassLoadingResult) resultMap.get(className);
            if(result != null && result.status != ClassLoadingResult.NOCHANGE) {
                return true;
            }
            else {  //equal no changes
                String[] dependsOn = conditions[i].getDependsOn();
                for(int j = 0; j < dependsOn.length; j++) {
                    ClassLoadingResult dependsResult = (ClassLoadingResult) resultMap.get(dependsOn[j]);
                    if(dependsResult != null && dependsResult.status != ClassLoadingResult.NOCHANGE)
                        return true;
                }
            }
        }
        return false;
    }

    //Entity Manager Interfaces
    public ExpandedName getNamespaceFromJavaClass(Class c) {
        if (c != null) return getExpandedName(c);
        try {
            if (isStateMachineClass(c)) {
                final Field namespaceField = c.getField("concept_expandedName");
                return (ExpandedName) namespaceField.get(null);
                /**
                 final Field namespaceField = c.getField("type");
                 String entityName= c.getName().substring(c.getName().lastIndexOf('.')+1);
                 entityName=entityName.substring(entityName.lastIndexOf('$'));
                 entityName
                 return ExpandedName.makeName((String)namespaceField.get(null),entityName);
                 **/
            }
            else if (isEventClass(c)) {
                final Field namespaceField = c.getField("event_expandedName");
                return (ExpandedName) namespaceField.get(null);
            } else if(isJobContextClass(c)){
//              final Field namespaceField = c.getField("process_expandedName"); //Pranab - this is wrong
//              return (ExpandedName) namespaceField.get(null);
                return getExpandedName(c);
            } else {

                final Field namespaceField = c.getField("concept_expandedName");
                return (ExpandedName) namespaceField.get(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private ExpandedName getExpandedName(Class e) {
        String modelPath = (isStateMachineClass(e)) ? ModelNameUtil
                .generatedClassNameToStateMachineModelPath(e.getName())
                : ModelNameUtil.generatedClassNameToModelPath(e.getName());
        String localName = modelPath.substring(modelPath.lastIndexOf('/') + 1);
        return ExpandedName.makeName(TypeManager.DEFAULT_BE_NAMESPACE_URI
                + modelPath, localName);
    }

    private ExpandedName getExpandedName(String modelPath) {
//        String localName = fullName.substring(fullName.lastIndexOf('/')+1);
//        String ns = fullName;
//        if (!fullName.startsWith(TypeManager.DEFAULT_BE_NAMESPACE_URI))
//            ns = TypeManager.DEFAULT_BE_NAMESPACE_URI + fullName;
//        ExpandedName name = ExpandedName.makeName(ns, localName);
        String localName = modelPath.substring(modelPath.lastIndexOf('/')+1);
        return ExpandedName.makeName(TypeManager.DEFAULT_BE_NAMESPACE_URI + modelPath, localName);
    }

    public Collection getTypeDescriptors(int type) {
        ArrayList classes = new ArrayList();
        Iterator ite = entityNSRegistry.values().iterator();
        while(ite.hasNext()) {
            TypeDescriptor des = (TypeDescriptor) ite.next();
            if( ((type & TypeManager.TYPE_CONCEPT) != 0) && (des.getType() == TypeManager.TYPE_CONCEPT))
                classes.add(des);
            else if( ((type & TypeManager.TYPE_NAMEDINSTANCE) != 0) && (des.getType() == TypeManager.TYPE_NAMEDINSTANCE))
                classes.add(des);
            else if( ((type & TypeManager.TYPE_RULE) != 0) && (des.getType() == TypeManager.TYPE_RULE))
                classes.add(des);
            else if( ((type & TypeManager.TYPE_RULEFUNCTION) != 0) && (des.getType() == TypeManager.TYPE_RULEFUNCTION))
                classes.add(des);
            else if( ((type & TypeManager.TYPE_SIMPLEEVENT) != 0) && (des.getType() == TypeManager.TYPE_SIMPLEEVENT))
                classes.add(des);
            else if( ((type & TypeManager.TYPE_TIMEEVENT) != 0) && (des.getType() == TypeManager.TYPE_TIMEEVENT))
                classes.add(des);
            else if( ((type & TypeManager.TYPE_STATEMACHINE) != 0) && (des.getType() == TypeManager.TYPE_STATEMACHINE))
                classes.add(des);
            else if( ((type & TypeManager.TYPE_POJO) != 0) && (des.getType() == TypeManager.TYPE_POJO))
                classes.add(des);
        }
        return classes;
    }

    public TypeDescriptor[] getTypeDescriptorArray(int type) {
        Collection c =  getTypeDescriptors(type);
        return (TypeDescriptor[]) c.toArray(new TypeDescriptor[c.size()]);
    }

    public TypeDescriptor getTypeDescriptor(Class cls) {
        return (TypeDescriptor) entityClassRegistry.get(cls);
    }

    public TypeDescriptor getTypeDescriptor(ExpandedName uri)  {
        return (TypeDescriptor) entityNSRegistry.get(uri);
    }

    public TypeDescriptor getTypeDescriptor(String fullName) {
        return (TypeDescriptor) entityPathRegistry.get(fullName);
    }

    public Entity createEntity(String fullName) throws Exception {
        return createEntity(getTypeDescriptor(fullName));
    }


    protected Entity createEntity(TypeDescriptor descriptor) throws Exception
    {
        if (descriptor == null) throw new ClassNotFoundException();
        int type = descriptor.getType();
        if ((type == TYPE_CONCEPT) ||(type == TYPE_NAMEDINSTANCE) ||  (type == TYPE_SIMPLEEVENT) || (type == TYPE_STATEMACHINE)) {
            //Constructor cons = descriptor.getImplClass().getConstructor(new Class[] {long.class});
            Constructor cons = descriptor.getConstructor();
            return (Entity) cons.newInstance(new Object[] {new Long(serviceProvider.getIdGenerator().nextEntityId(descriptor.getImplClass()))});
        }
        throw new Exception("Not a Concept or SimpleEvent Type");
    }

    public Entity createEntity(ExpandedName en) throws Exception {
        TypeDescriptor descriptor = (TypeDescriptor) entityNSRegistry.get(en);
        return createEntity(descriptor);


    }

    public boolean instanceOf(Entity entity, String fullName) {
        try {
            Class c = this.findClass(ModelNameUtil.modelPathToGeneratedClassName(fullName));
            return (c.isInstance(entity));
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public boolean instanceOf(Entity entity, ExpandedName en) {
        TypeDescriptor descriptor = (TypeDescriptor) entityNSRegistry.get(en);
        if(descriptor == null) return false;
        return descriptor.getImplClass().isInstance(entity);
    }

    /**
     *  Returns a collectioo of classes loaded by the classloader
     * @return  Collection
     */
    public Collection getClasses() {
        return entityClassRegistry.keySet();
    }

    /**
     * Extracts bytecode of runtime classes loaded by the classloader.
     * @param classname
     * @return  byte[]
     */
    public byte[] getByteCode(String classname) {
        return super.getByteCode(classname);
    }

    /**
     * Returns true if the specified class is known to the loader else false
     *
     * @param classname
     * @return boolean
     */
    public boolean containsClass(String classname) {
        return super.containsClass(classname);
    }

    /**
     * returns the number of classes in the loader
     *
     * @return int
     */
    public int size() {
        return super.size();
    }

    /**
     * Returns the parent ByteCodeLoader
     *
     * @return ByteCodeLoader
     */
    public ByteCodeLoader getParentLoader() {
        if(parentClassLoader instanceof ByteCodeLoader) {
            return (ByteCodeLoader) parentClassLoader;
        }
        return null;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        for(Iterator it = super.getActiveClassNames().iterator();it.hasNext();) {
            sb.append(it.next()).append(",");
        }
        sb.append("]");
        return sb.toString();
    }

    @Deprecated
    //Suresh:  Nicolas removed this method, as this no longer needed.
    //Concepts, Events all implements SerializableLite which has the
    //same signature. See injectInterfaceExtension.
    protected byte[] _getConvertedByteCode(ClassInfo classInfo) throws Exception {

        final byte[] byteCode = classInfo.byteCode;

        if (Boolean.FALSE.toString().equalsIgnoreCase(
                System.getProperty(SystemProperty.INJECT_EXTERNALIZABLE_LITE.getPropertyName()))) {
            return byteCode;
        }

        final ClassPool pool = new ClassPool(true);
        final CtClass cachedInterface = pool.makeInterface("com.tangosol.io.ExternalizableLite");
        final CtClass conceptImplClass = pool.makeClass("com.tibco.cep.runtime.model.element.impl.ConceptImpl");
        final CtClass eventImplClass = pool.makeClass("com.tibco.cep.runtime.model.event.impl.SimpleEventImpl");
        final CtClass timeeventImplClass = pool.makeClass("com.tibco.cep.runtime.model.event.impl.TimeEventImpl");

        //TODO: Add externalizable lite only for objects types that actually use the cache?
        final CtClass clazz = pool.makeClass(new ByteArrayInputStream(byteCode), false);
        for (CtClass c = clazz; !Object.class.getName().equals(c.getName()); c = c.getSuperclass()) {
            if (c.isFrozen()) {
                c.defrost();
            }
            String superClass = c.getClassFile().getSuperclass();
            CtClass superClazz = null;
            if (superClass != null) {
                // Check if it exists in pool
                logger.log(Level.DEBUG,
                        "Making superclass %1$s for class %2$s", superClass, clazz.getName());
                try {
                    superClazz = pool.get(superClass);
                } catch (NotFoundException nfe) {
                    logger.log(Level.WARN,
                            "Class %1$s not found in pool. Adding to pool..", superClass);
                    superClazz = pool.makeClass(superClass);
                }
            }
            if (superClazz != null) {
                if (superClazz.getName().equals(conceptImplClass.getName()) ||
                        superClazz.getName().equals(eventImplClass.getName()) ||
                        superClazz.getName().equals(timeeventImplClass.getName())) {

                    for (int i = 0; i < 100 /*Avoid infinite loop*/; i++) {
                        try {
                            clazz.getInterfaces();

                            //All done.
                            break;
                        }
                        catch (NotFoundException nfe) {
                            logger.log(Level.WARN,
                                    "Class %1$s not found in pool. Adding to pool..",
                                    nfe.getMessage());

                            pool.makeClass(nfe.getMessage());
                        }
                    }

                    if (!Arrays.asList(clazz.getInterfaces()).contains(cachedInterface)) {
                        clazz.addInterface(cachedInterface);
                        return clazz.toBytecode();
                    }
                    break;
                }
            }
        }
        return byteCode;
    }

    public static void injectInterfaceExtension(String sourceClass, String extendsClass) throws Exception
    {
        final ClassPool pool = new ClassPool(true);
        final CtClass c = pool.makeInterface(sourceClass);

        if (c.isFrozen()) {
            c.defrost();
        }

        c.addInterface(pool.makeInterface(extendsClass));
        c.toClass(BEClassLoader.class.getClassLoader(),BEClassLoader.class.getProtectionDomain());
    }

    public static void addInterfaceToClass(String sourceClass, String interfaceType) throws Exception
    {
        final ClassPool pool = ClassPool.getDefault();
        final CtClass srcKlazz = pool.get(sourceClass);
        if (srcKlazz == null) {
            throw new Exception(String.format("Class %s not found", sourceClass));
        }

        final CtClass addType = pool.get(interfaceType);
        if (addType == null) {
            throw new Exception(String.format("Class %s not found", sourceClass));
        }

        if (srcKlazz.isFrozen()) {
            srcKlazz.defrost();
        }

        srcKlazz.addInterface(addType);
        srcKlazz.toClass(BEClassLoader.class.getClassLoader(), BEClassLoader.class.getProtectionDomain());
    }

    //---------------------- StreamClassLoaderImpl code is moved here-----------------------
    private final Map<String, JavaFileObject> classMap = new HashMap<String, JavaFileObject>();
    @Override
    public void addJavaFile(String qualifiedName, JavaFileObject file) {
        classMap.put(qualifiedName, file);
    }

    public Collection<? extends JavaFileObject> files() {
        return classMap.values();
    }

    @Override
    protected Class<?> findClass(final String qualifiedClassName)
            throws ClassNotFoundException {
        JavaFileObject file = classMap.get(qualifiedClassName);
        if (file != null) {
            byte[] bytes = ((JavaFileObjectImpl) file).getByteCode();
            return defineClass(qualifiedClassName, bytes, 0, bytes.length);
        }
        // Workaround for "feature" in Java 6
        // see http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6434149
        try {
            Class<?> c = Class.forName(qualifiedClassName);
            return c;
        } catch (ClassNotFoundException nf) {
            // Ignore and fall through
        }
        return super.findClass(qualifiedClassName);
    }


    @Override
    protected URL findResource(String name) {
    	if(name.endsWith(".class")) {
    		String clName = name.endsWith(".class") ? name.substring(0,name.length() - ".class".length()):name;
        	clName = clName.replaceAll("/", ".");
            if(classMap.containsKey(name.replaceAll("/", "."))) {
                try {
                    return  new URL(StreamURLHandler.PROTOCOL,null,0,name,new StreamURLHandler(classMap));
                } catch (MalformedURLException e1) {
                    return parentClassLoader.getResource(name);
                }
            } else if(active.containsKey(clName)){
            	ClassInfo cinfo = (ClassInfo) active.get(clName);
            	try {
    				return new URL(ClassURLHandler.PROTOCOL,null,0,name,new ClassURLHandler(cinfo));
    			} catch (MalformedURLException e) {
    				
    				return parentClassLoader.getResource(name);
    			}
            } else
                return parentClassLoader.getResource(name);
    	} else {
    		String resName = name;
    		if(name.startsWith("/")) {
    			resName = name.substring(1);
    		}
    		resName = resName.replaceAll("/", ".");
    		if(resourceMap.containsKey(resName)){
            	ClassInfo cinfo = (ClassInfo) resourceMap.get(resName);
            	try {
    				return new URL(ClassURLHandler.PROTOCOL,null,0,name,new ClassURLHandler(cinfo));
    			} catch (MalformedURLException e) {
    				
    				return parentClassLoader.getResource(name);
    			}
            } else
                return parentClassLoader.getResource(name);
    	}
    	
    }

	@Override
	protected Enumeration<URL> findResources(String name) throws IOException {
		Vector<URL> rSet = new Vector<URL>();
		try {
			if (name.endsWith(".class")) {
				if (classMap.containsKey(name.replaceAll("/", "."))) {

					rSet.add(new URL(StreamURLHandler.PROTOCOL, null, 0, name, new StreamURLHandler(classMap)));

				}
			} else {
				// since the be.jar resource map is a single classloader
				// namespace only one resource can exist for the same package
				// and file name.
				if (this.resourceMap.containsKey(name)) {
					ClassInfo cinfo = (ClassInfo) resourceMap.get(name);

					rSet.add(new URL(ClassURLHandler.PROTOCOL, null, 0, name, new ClassURLHandler(cinfo)));

				}
			}
		} catch (MalformedURLException e) {
			logger.log(Level.ERROR, String.format("Invalid resource URI:%s", name),e);
		}
		
		return rSet.elements();	
	}
}
    

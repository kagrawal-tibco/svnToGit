package com.tibco.cep.runtime.session.impl;


import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.repo.ArchiveInputDestinationConfig;
import com.tibco.cep.repo.BEArchiveResource;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.config.Configuration;
import com.tibco.cep.runtime.service.ServiceRegistry;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSessionConfig;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.util.SystemProperty;


public class RuleSessionConfigImpl
        implements RuleSessionConfig {

    String name;
    Set<String> ruleUris;
    Properties cacheConfig;
    Class startup;
    Class shutdown;
    InputDestinationConfig cfg[] = new InputDestinationConfig[0];
    boolean deleteStateTimeoutOnStateExit = false;
    boolean removeRefOnDeleteNonRepeatingTimeEvent = false;
    boolean allowEventModificationInRTC;
    Configuration ruleSessionConfig;
    List<String> startupFunctionUris;
    List<String> shutdownFunctionUris;

    public RuleSessionConfigImpl(BEArchiveResource barResource, RuleServiceProvider provider) throws Exception{
        this.name = barResource.getName();
        this.ruleUris = new HashSet<String>();
        final Set<String> ruleUris = barResource.getDeployedRuleUris();
        if (null != ruleUris) {
            this.ruleUris.addAll(ruleUris);
        }

        this.cacheConfig = new Properties();
        if (!RuleSessionManager.DEFAULT_CLUSTER_RULESESSION_NAME.equals(this.name)) {
            this.cacheConfig.putAll(barResource.getCacheConfig());
        }

        final GlobalVariables gvs = provider.getGlobalVariables();
        for (Map.Entry<Object, Object> e : this.cacheConfig.entrySet()) {
            final Object value = e.getValue();
            if (value instanceof CharSequence) {
                e.setValue(gvs.substituteVariables((CharSequence) value).toString());
            }
        }

        String className =
                provider.getProperties().getProperty(SystemProperty.OM_CLASS.getPropertyName());
//        if (PersistentStore.class.getName().equals(className)) {
//            String[] copyProps =
//                    {       "be.engine.om.berkeleydb.dbenv",
//                            "be.engine.om.berkeleydb.truncate",
//                            "be.engine.om.berkeleydb.nocreatedbenv",
//                            "be.engine.om.berkeleydb.conceptable",
//                            //"be.engine.om.berkeleydb.concepturimap",
//                            "be.engine.om.berkeleydb.propertiestable",
//                            "be.engine.om.berkeleydb.eventslog",
//                            "omCheckPtInterval",
//                            "omPropCacheSize",
//                            "omCheckPtOpsLimit",
//                            "omDeletePolicy",
//                            "omNoRecovery",
//                            "omtgGlobalCache",
//                            "omDbEnvDir",
//                            "omClass",
//                            //"be.engine.om.berkeleydb.eventurimap",
//                            //"be.engine.om.berkeleydb.payloadstable",
//                            //"be.engine.om.berkeleydb.namedinstancemap",
//                            "be.engine.om.berkeleydb.propertyindextable",
//                            "be.engine.om.berkeleydb.beversiontable",
//                            "be.engine.om.eventcache.defaultmaxsize",
//                            "be.engine.om.eventcache.maxsize." + getName()
//                    };
//            Properties from = provider.getProperties();
//            for(int ii = 0; ii < copyProps.length; ii++) {
//                String key = copyProps[ii];
//                String prop = from.getProperty(key);
//                if(prop != null && prop.length() > 0) {
//                    cacheConfig.setProperty(key, prop);
//                }
//            }
//        }

        setInputDestinations(barResource, provider);

        this.setStartupShutdown(barResource);

        deleteStateTimeoutOnStateExit = Boolean.valueOf(provider.getProperties().getProperty("be.engine.statemachine.deleteStateTimeoutOnStateExit", "true")).booleanValue();
        removeRefOnDeleteNonRepeatingTimeEvent = Boolean.valueOf(provider.getProperties().getProperty("be.engine.om.removeRefOnDeleteNonRepeatingTimeEvent", "true")).booleanValue();
        allowEventModificationInRTC = Boolean.parseBoolean(provider.getProperties().getProperty(SystemProperty.ALLOW_MODIFY_EVENT_IN_RTC.getPropertyName(), "false"));

        //------------

        ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();

        Configuration rootConfig = registry.getConfiguration();

        String projectName = provider.getProject().getName();
        ruleSessionConfig = new Configuration(projectName, cacheConfig);
        rootConfig.addChild(ruleSessionConfig);
    }

    private void setInputDestinations(BEArchiveResource archive, RuleServiceProvider provider) throws Exception {
        final Set<ArchiveInputDestinationConfig> inputDestinations = archive.getInputDestinations();
        if (inputDestinations == null) return;

        cfg = new InputDestinationConfig[inputDestinations.size()];
        int i = 0;
        for (ArchiveInputDestinationConfig config : inputDestinations) {
            String threadingModel = config.getThreadingModel();
            RuleFunction preprocessor = null;
            RuleFunction threadAffinityRuleFunction = null;

            if ((config.getPreprocessor() != null)
                    && !config.getPreprocessor().isEmpty()) {

                String preprocessorClassName = ModelNameUtil.modelPathToGeneratedClassName(config.getPreprocessor());
                final Class rf = Class.forName(preprocessorClassName, true, provider.getClassLoader());
                preprocessor = (RuleFunction) rf.newInstance();
            }
            
            if(config.getThreadAffinityRuleFunction() != null && 
            		!config.getThreadAffinityRuleFunction().isEmpty()) {
            	String tarfClassName = ModelNameUtil.modelPathToGeneratedClassName(config.getThreadAffinityRuleFunction());
                final Class rf = Class.forName(tarfClassName, true, provider.getClassLoader());
                threadAffinityRuleFunction = (RuleFunction) rf.newInstance();
            }
            ThreadingModel threadingModelType = ThreadingModel.getByLiteral(threadingModel);
            cfg[i] = new RuleSessionImpl.InputDestinationConfigImpl(config.getDestinationURI(),
                    null, preprocessor, threadAffinityRuleFunction, true, threadingModelType, config.getNumWorker(), config.getQueueSize(), config.getWeight());
            ++i;
        }
    }


    private void setStartupShutdown(
            BEArchiveResource barResource)
            throws Exception {

        this.startupFunctionUris = barResource.getStartupFunctionUris();
        this.shutdownFunctionUris = barResource.getShutdownFunctionUris();
    }


    public Configuration getRuleSessionConfig() {
        return ruleSessionConfig;
    }

    public String getName() {
        return name;

    }

    public Set<String> getDeployedRuleUris() {
        return ruleUris;
    }

    public InputDestinationConfig[] getInputDestinations() {
        return cfg;
    }

    public Properties getCacheConfig() {
        return cacheConfig;
    }

    public Class getStartupClass() {
        return startup;
    }

    public Class getShutdownClass() {
        return shutdown;
    }

    public boolean deleteStateTimeoutOnStateExit() {
        return deleteStateTimeoutOnStateExit;
    }

    public boolean removeRefOnDeleteNonRepeatingTimeEvent() {
        return removeRefOnDeleteNonRepeatingTimeEvent;
    }

    public boolean allowEventModificationInRTC() {
    	return allowEventModificationInRTC;
    }

    public List<String> getStartupFunctionUris() {
        return this.startupFunctionUris;
    }

    public List<String> getShutdownFunctionUris() {
        return this.shutdownFunctionUris;
    }

}

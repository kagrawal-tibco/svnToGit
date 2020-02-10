/*
 * Copyright (c) 2011.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.be.migration.expimp;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.Security;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarInputStream;

import com.sun.net.ssl.internal.ssl.Provider;
import com.tibco.be.util.BEProperties;
import com.tibco.be.util.OversizeStringConstants;
import com.tibco.cep.kernel.service.IdGenerator;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManager;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.ArchiveResourceProvider;
import com.tibco.cep.repo.BEArchiveResource;
import com.tibco.cep.repo.ChangeListener;
import com.tibco.cep.repo.DeployedProject;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.repo.Workspace;
import com.tibco.cep.repo.provider.BEArchiveResourceProvider;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.repo.provider.JavaArchiveResourceProvider;
import com.tibco.cep.repo.provider.SharedArchiveResourceProvider;
import com.tibco.cep.repo.provider.impl.BEArchiveResourceProviderImpl;
import com.tibco.cep.repo.provider.impl.JavaArchiveResourceProviderImpl;
import com.tibco.cep.repo.provider.impl.SharedArchiveResourceProviderImpl;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.service.loader.JarInputStreamReader;
import com.tibco.cep.runtime.service.logging.impl.LogManagerImpl;
import com.tibco.cep.runtime.session.RuleAdministrator;
import com.tibco.cep.runtime.session.RuleRuntime;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleSessionManagerImpl;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.cep.util.ResourceManager;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 20, 2008
 * Time: 7:15:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class MigrationRuleServiceProvider implements RuleServiceProvider, ChangeListener {
    private final static Logger logger = LogManagerFactory.getLogManager().getLogger(MigrationRuleServiceProvider.class);
    private String name;
    private BEProperties beProperties;
    private DeployedProject m_project;
    private GlobalVariables m_globalVariables;
    private TypeManager m_typeManager;
    private IdGenerator m_idGenerator;
    private String m_repoUrl;

    public MigrationRuleServiceProvider(String name, String repoUrl, Properties env) throws Exception {
        this.name = name;
        this.m_repoUrl = repoUrl;
        env.put("tibco.repourl",repoUrl);
        initialize(env);
        RuleServiceProviderManager.getInstance().setDefaultProvider(this);
        configure(0);
    }

    private void initialize(Properties env) throws Exception {
        createProperties(env);

        final LogManagerImpl logManager = new LogManagerImpl(this.beProperties);
        LogManagerFactory.setLogManager(logManager);
        
        ResourceManager manager = ResourceManager.getInstance();
        manager.addResourceBundle("com.tibco.cep.runtime.messages",getLocale());

        if (env.get("tibco.repoFactory") != null) beProperties.put("tibco.repoFactory", env.get("tibco.repoFactory")); //XML Canon repourl
        logger.log(Level.INFO, "Loading project ...");
        
        final Class deployedProjectClass = Class.forName("com.tibco.cep.studio.core.repo.emf.DeployedEMFProject");
        final Constructor deployedProjectConstructor = deployedProjectClass.getConstructor(
                Properties.class, ChangeListener.class);
        m_project = (DeployedProject) deployedProjectConstructor.newInstance(beProperties, this);  // Can be very slow!
        m_project.load();

        //this.m_repoUrl = beProperties.getProperty("tibco.repourl");
        //m_project = new DeployedBEProject(beProperties, null);
        //m_project = loadProject(m_repoUrl);
        
        m_globalVariables = m_project.getGlobalVariables();
        logger.log(Level.INFO, "Loaded project - %s@%s",
                this.m_project.getName(),
                ((GlobalVariablesProvider) m_globalVariables).getPackagedComponentVersion("be-engine"));
        //m_typeManager = EntityFactory.createTypeManager(this, beProperties);
        String clazzName = env.getProperty("com.tibco.cep.entity.factory", BEClassLoader.class.getName());
        Class factoryClass = Class.forName(clazzName);
        Constructor constructor = factoryClass.getConstructor(new Class[] {RuleServiceProvider.class, Properties.class});
        m_typeManager = (TypeManager) constructor.newInstance( new Object[] {this, env});
    }

    public void configure(int mode) throws Exception {
        Security.addProvider(new Provider());
        if (!((GlobalVariablesProvider) m_globalVariables).getPackagedComponentVersion("be-engine").startsWith("1.4")) {
            registerClasses();
        }
    }

    public RuleSessionManager createRuleSessionManager(Properties env) throws Exception {
        String className = env.getProperty("com.tibco.cep.rulesession.factory", RuleSessionManagerImpl.class.getName());
        Class clazz = Class.forName(className);

        Constructor constructor = clazz.getConstructor(new Class[]{RuleServiceProvider.class, Properties.class});
        RuleSessionManager manager = (RuleSessionManager) constructor.newInstance(new Object[]{this, env});
        return manager;
    }

    private void createProperties(Properties env) throws Exception {
        beProperties = BEProperties.loadDefault();
        beProperties.mergeProperties(env);
        beProperties.mergeProperties(System.getProperties());
        beProperties.substituteTibcoEnvironmentVariables();
        overrideDefaultProperties();
        //TODO: Fix this call using SystemProperty if applicable
        if (env.get("tibco.repoFactory") != null) {
            beProperties.put("tibco.repoFactory", env.get("tibco.repoFactory")); //XML Canon repourl
        }
    }

    protected void overrideDefaultProperties() {
        // engine name
        this.beProperties.remove(SystemProperty.ENGINE_NAME.getPropertyName());
        this.beProperties.put(SystemProperty.ENGINE_NAME.getPropertyName(), this.name);

        // trace dir
        final String traceDir = beProperties.getString("Engine.Log.Dir", "./logs");
        this.beProperties.remove(SystemProperty.TRACE_FILE_DIR.getPropertyName());
        this.beProperties.put(SystemProperty.TRACE_FILE_DIR.getPropertyName(), traceDir);

        // max nb of trace files
        final String nbFiles = beProperties.getString("Engine.Log.MaxNum",
                beProperties.getString("be.trace.maxnum", "5"));
        beProperties.remove("be.trace.maxnum");
        beProperties.put("be.trace.maxnum", nbFiles);

        // max size of trace files
        final String maxSize = beProperties.getString("Engine.Log.MaxSize",
                beProperties.getString("be.trace.maxsize", "20480000"));
        beProperties.remove("be.trace.maxsize");
        beProperties.put("be.trace.maxsize", maxSize);

        // trace file name
        final String traceName = this.beProperties.getString(SystemProperty.TRACE_NAME.getPropertyName());
        if (traceName == null) {
            this.beProperties.put(SystemProperty.TRACE_NAME.getPropertyName(), this.name);
        }
    }

    public Locale getLocale() {
        String lang = beProperties.getString("be.locale.language");
        if (lang == null || lang.length() == 0) {
            return Locale.getDefault();
        } else {
            return new Locale(lang, beProperties.getString("be.locale.country"),
                    beProperties.getString("be.locale.variant"));
        }
    }

    @Deprecated
    @SuppressWarnings("unused")
    private DeployedProject loadProject(String repoUrl) throws Exception {
        final Workspace ws = (Workspace) Class.forName("com.tibco.cep.studio.core.repo.emf.EMFWorkspace")
                .getMethod("getInstance").invoke(null);

        List providers = new ArrayList();
        JavaArchiveResourceProvider jarProvider;
        SharedArchiveResourceProvider sharedResourceProvider;
        BEArchiveResourceProvider beArchiveProvider;

        jarProvider = new JavaArchiveResourceProviderImpl();
        sharedResourceProvider = new SharedArchiveResourceProviderImpl();
        beArchiveProvider = new BEArchiveResourceProviderImpl();

        providers.add(jarProvider);
        providers.add(sharedResourceProvider);
        providers.add(beArchiveProvider);

        DeployedProject proj = (DeployedProject) ws.loadProject(providers, repoUrl);
        return proj;
    }

    public void initProject() throws Exception {

    }

    private void registerClasses() throws Exception {
        final HashMap nameToByteCode = new HashMap();
        final ArchiveResourceProvider jarp = m_project.getJavaArchiveResourceProvider();
        final Collection uris = jarp.getAllResourceURI();
        for (Object uri : uris) {
            byte[] raw = jarp.getResourceAsByteArray(uri.toString());
            if ((raw != null) && (raw.length != 0)) {
                ByteArrayInputStream bis = new ByteArrayInputStream(raw);
                nameToByteCode.putAll(JarInputStreamReader.read(new JarInputStream(bis)));
            }
        }
        BEClassLoader beClassLoader = (BEClassLoader) m_typeManager;
        //remove this so that analyzeByteCodes doesn't choke on it
        Properties oversizeStringConstants = (Properties) nameToByteCode.remove(OversizeStringConstants.PROPERTY_FILE_NAME);
        Map resultMap = beClassLoader.analyzeByteCodes(nameToByteCode);
        //commit the changes
        beClassLoader.commitChanges(resultMap);
        Method regclass = beClassLoader.getClass().getMethod("registerClasses",new Class[] {Map.class});
        regclass.invoke(beClassLoader,new Object[] {resultMap});
        //beClassLoader.registerClasses(resultMap);
        String loadedClass = beClassLoader.ResultListToString(resultMap);
        logger.log(Level.DEBUG, loadedClass);
        //free up the shared resource
        for (Object uri : new ArrayList(uris)) {
            jarp.removeResource(uri.toString());
        }
    }

    @Deprecated
    @SuppressWarnings("unused")
    private Map newArchives(DeployedProject beproject) {
        Map ret = new HashMap();
        Collection c = beproject.getDeployedBEArchives();
        Iterator ite = c.iterator();
        while (ite.hasNext()) {
            BEArchiveResource archive = (BEArchiveResource) ite.next();
            ret.put(archive.getName(), archive);
        }
        return ret;
    }

    public ChannelManager getChannelManager() {
        return null;
    }

    public ClassLoader getClassLoader() {
        return (ClassLoader) m_typeManager;
    }

    public GlobalVariables getGlobalVariables() {
        return m_globalVariables;
    }

    public IdGenerator getIdGenerator() {
        return m_idGenerator;
    }

    public com.tibco.cep.kernel.service.logging.Logger getLogger(Class clazz) {
        return logger; //FIXME: Implement
    }

    public com.tibco.cep.kernel.service.logging.Logger getLogger(String name) {
        return logger; //FIXME: Implement
    }

    public LogManager getLogManager() {
        return null; //FIXME: Implement
    }

    public String getName() {
        return getClass().getName();
    }

    public DeployedProject getProject() {
        return m_project;
    }

    public Properties getProperties() {
        return this.beProperties;
    }

    public RuleAdministrator getRuleAdministrator() {
        return null;
    }

    public RuleRuntime getRuleRuntime() {
        return null;
    }

    public TypeManager getTypeManager() {
        return m_typeManager;
    }

    public void shutdown() {

    }

    public boolean isMultiEngineMode() {
        return false;
    }

    @Override
    public void notify(ChangeEvent e) {
    }
    
    @Override
    public Cluster getCluster() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }    
    
    @Override
    public boolean isCacheServerMode() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }    
}

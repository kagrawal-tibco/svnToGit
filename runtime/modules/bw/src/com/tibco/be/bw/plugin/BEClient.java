package com.tibco.be.bw.plugin;


import java.util.Properties;

import com.tibco.be.bw.factory.BWLogger;
import com.tibco.be.bw.factory.XiEntityFactory;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.repo.VFileHelper;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.session.RuleRuntime;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.objectrepo.vfile.VFileFactory;
import com.tibco.pe.PEMain;
import com.tibco.pe.core.Engine;
import com.tibco.pe.plugin.EngineContext;



public class BEClient {

    //todo Clean up this static mess.
    static protected RuleServiceProvider m_ruleServiceProvider;
    protected static Boolean m_initialized = new Boolean(false);


    public BEClient (EngineContext context, RspConfigValues rspConfig) throws Exception{
        Properties env = new Properties(Engine.props);

        //todo Clean up this static mess.
        synchronized(m_initialized) {
            if (! m_initialized.booleanValue()) {

                String instanceName = context.getEngineName();
                final String repoUrl = rspConfig.getRepoUrl();
                final VFileFactory factory = VFileHelper.createVFileFactory(repoUrl, null);
                //repoUri = repoAgent.getRepoURI(repoAgent.getProjectName());

                env.setProperty("tibco.repourl", repoUrl);
                env.setProperty(SystemProperty.CLUSTER_CONFIG_PATH.getPropertyName(), rspConfig.getCddUrl());
                env.setProperty(SystemProperty.PROCESSING_UNIT_ID.getPropertyName(), rspConfig.getPuid());
                env.setProperty(SystemProperty.TRACE_LOGGER_CLASS_NAME.getPropertyName(), BWLogger.class.getName());
                if (PEMain.isMain) {
                	env.setProperty("com.tibco.pe.PEMain", "true");
                }
                if (Engine.testMode) {
                    env.setProperty("com.tibco.pe.Engine.testMode", "true");
                    System.clearProperty(SystemProperty.CLUSTER_CONFIG.getPropertyName());
                    System.clearProperty(SystemProperty.CLUSTER_CONFIG_PATH.getPropertyName());
                    System.clearProperty(SystemProperty.PROCESSING_UNIT_ID.getPropertyName());
                }
                env.setProperty("com.tibco.cep.entity.factory", XiEntityFactory.class.getName());
                env.setProperty("com.tibco.cep.rsp.isClient", "true");
                env.put("tibco.repoFactory", factory); //XML Canon, and performance update when using repository client
                RuleServiceProviderManager.getInstance().removeProvider(instanceName);
                m_ruleServiceProvider = RuleServiceProviderManager.getInstance().newProvider(instanceName, env);
                m_ruleServiceProvider.configure(RuleServiceProvider.MODE_INIT);
                m_initialized = new Boolean(true);
                this.m_ruleServiceProvider.getLogger("BEBW001").log(Level.INFO, "BEClient Created");

            }
        }//synchronized
    }//constr



    public RuleServiceProvider getRuleServiceProvider() {
        return m_ruleServiceProvider;
    }


    public RuleRuntime getRuleruntime () throws Exception{
        return m_ruleServiceProvider.getRuleRuntime();
    }


//    public RuleMetadata getRuleMetadata () throws Exception{
//        return m_ruleServiceProvider.getRuleMetadata();
//    }

    public Ontology getOntology() {
        return m_ruleServiceProvider.getProject().getOntology();
    }


    public String getEventDefaultDestinationURI(String eventRef) throws Exception{
        Event event = getEventDescription(eventRef);
        String chURI = event.getChannelURI();
        String dest = event.getDestinationName();

        return chURI + "/" + dest;
    }

//    public AsyncRuleSession getAsyncRuleSession(String uri) throws Exception{
//        return (AsyncRuleSession) getRuleruntime().createRuleSession(uri, null, RuleRuntime.ASYNC_STATEFUL_SESSION_TYPE);
//    }


    public Event getEventDescription(String uri) throws Exception {
        if (uri.endsWith(".event")) {
            uri = uri.substring(0, uri.length() - ".event".length());
        }
        return getOntology().getEvent(uri);
    }

    public  Channel.Destination getDestination(String uri) {
        return m_ruleServiceProvider.getChannelManager().getDestination(uri);
    }

    public void destroy() {
        //todo Clean up this static mess.
        try {
            if (m_initialized.booleanValue()) {
                if (m_ruleServiceProvider != null) {
                    m_ruleServiceProvider.shutdown();
                }
                m_initialized = new Boolean(false);
                m_ruleServiceProvider = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }//catch
    }//destroy

}//class

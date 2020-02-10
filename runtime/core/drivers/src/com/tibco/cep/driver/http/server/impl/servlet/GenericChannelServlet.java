package com.tibco.cep.driver.http.server.impl.servlet;

import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tibco.cep.driver.http.HttpChannel;
import com.tibco.cep.driver.http.HttpChannelConfig;
import com.tibco.cep.driver.http.HttpDestination;
import com.tibco.cep.driver.http.server.ConnectorInfo;
import com.tibco.cep.driver.http.server.impl.servlet.model.HTTPSessionConceptImpl;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.service.cluster.system.MetadataCache;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionEx;

/**
 * Created by IntelliJ IDEA.
 * User: aditya
 * Date: 31/8/11
 * Time: 7:19 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class GenericChannelServlet extends HttpServlet {

    private static transient Logger LOGGER = LogManagerFactory.getLogManager().getLogger(GenericChannelServlet.class);
    
    protected static final String HTTP_LATEST_REQUEST_RESPONSE_ENABLED_PROPERTY = "be.engine.channel.http.latest.requestResponse.enabled";

    protected HttpChannel httpChannel;

    protected Map<String, HttpDestination> httpDestinations;
    
    //Same as connection timeout set on the channel to avoid specifying different time out properties.
    //-1 means never time out.
    protected long DEFAULT_TIMEOUT_INTERVAL;

    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        try {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Initializing GenericChannelServlet...");
            }
            Object c = servletConfig.getServletContext().getAttribute(HttpChannel.class.getName());
            if (c instanceof HttpChannel) {
                httpChannel = (HttpChannel) c;
            }
            if (httpChannel == null) {
                throw new ServletException("Channel parameter not found in the servlet context");
            }
            HttpChannelConfig channelConfig = httpChannel.getConfig();
            ConnectorInfo connectorInfo = channelConfig.getConnectorInfo();
            DEFAULT_TIMEOUT_INTERVAL = connectorInfo.getConnectionTimeout();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    /**
     * Called by the servlet container to indicate to a servlet that the
     * servlet is being taken out of service.  See {@link javax.servlet.Servlet#destroy()}.
     */
    public void destroy() {
        //Flush the cached destinations, and hope they will be collected
        httpDestinations.clear();
    }


    /**
     * Create and assert concept mapping to {@link javax.servlet.http.HttpSession} if it is not already asserted.
     * @param httpSession
     * @param ruleSession
     * @return
     */
    protected Concept getSessionConcept(HttpSession httpSession, RuleSession ruleSession, boolean assertConcept) {

        //TODO Fix this later by checking for existence of concept.
//        Entity sessionConceptEntity = ruleSession.getObjectManager().getElement(httpSession.getId());
//        if (sessionConceptEntity instanceof HTTPSessionConceptImpl) {
//            return (HTTPSessionConceptImpl)sessionConceptEntity;
//        }
        long id = ruleSession.getRuleServiceProvider().getIdGenerator().nextEntityId(HTTPSessionConceptImpl.class);
        HTTPSessionConceptImpl sessionConcept = new HTTPSessionConceptImpl(id, httpSession);
        //Set attributes if any
        sessionConcept.setAttributeProperties();
        if (assertConcept) {
            try {
                ruleSession.assertObject(sessionConcept, true);
            } catch (DuplicateExtIdException e) {
                LOGGER.log(Level.ERROR, "Session ID %s already registered with working memory", httpSession.getId());
                throw new RuntimeException(e);
            }
        }
        return sessionConcept;
    }

    /**
     * Register the newly created session concept class with the working memory of each
     * bound rule session.
     * @param httpDestination
     */
    protected void registerSessionConcept(HttpDestination httpDestination) {
        Collection<RuleSession> boundRuleSessions = httpDestination.getBoundedRuleSessions();
        if (boundRuleSessions != null) {
            for (RuleSession ruleSession : boundRuleSessions) {
                ((RuleSessionEx)ruleSession).registerType(HTTPSessionConceptImpl.class);
            }
        }
    }

    /**
     * Register the {@link HTTPSessionConceptImpl} with WM, {@link BEClassLoader}
     * and {@link MetadataCache}
     * @param httpDestination
     * @throws Exception
     */
    protected void coordinateRuntimeRegistration(HttpDestination httpDestination) throws Exception {
        //Register with WM
        registerSessionConcept(httpDestination);
//        RuleServiceProvider RSP = httpChannel.getChannelManager().getRuleServiceProvider();
//        registerSessionConceptClass(RSP);
//        Cluster cluster = RSP.getCluster();
//        int typeId = getSessionConceptTypeId(RSP);
//        LOGGER.log(Level.INFO, "Type id for session concept %s", typeId);
//        //Register the DAO for this entity
//        MetadataCache metadataCache = cluster.getMetadataCache();
//        Map<Class, EntityDaoConfig> entityDaoConfigs = metadataCache.getEntityConfigurations();
//        EntityDaoConfig existingDaoConfig = entityDaoConfigs.get(HTTPSessionConceptImpl.class);
//        EntityDaoConfig entityDaoConfig = (existingDaoConfig != null) ? existingDaoConfig : (EntityDaoConfigCreator.createDefault(cluster, HTTPSessionConceptImpl.class, EntityDaoConfig.CacheMode.Cache));
//        entityDaoConfigs.put(HTTPSessionConceptImpl.class, entityDaoConfig);
//
//        EntityDao sessionConceptDao = metadataCache.createEntityDaoAndRegister(HTTPSessionConceptImpl.class, HTTPSessionConceptImpl.concept_expandedName.getNamespaceURI(), typeId);
//        //Start this to initialize the cache.
//        sessionConceptDao.start();
    }

    /**
     * Register {@link HTTPSessionConceptImpl} with BE classloader.
     * @param RSP
     */
    private void registerSessionConceptClass(RuleServiceProvider RSP) {
        BEClassLoader typeManager = (BEClassLoader)RSP.getTypeManager();
        typeManager.registerClass(HTTPSessionConceptImpl.class);
    }

    /**
     * TODO The typeid gen logic needs to be revisited.
     * @param
     * @return
     */
//    private int getSessionConceptTypeId(RuleServiceProvider RSP) throws Exception {
//        Cluster cluster = RSP.getCluster();
////        Map<String, Integer> classRegistry = cluster.getRecoveryBackingStore().getClassRegistry();
//        return cluster.getMetadataCache().getLastTypeId() + 1;
////        return cluster.getMetadataCache().size() + 1;
//    }

    protected abstract HttpDestination getMatchingDestination(HttpServletRequest httpServerRequest) throws ServletException;

	/**
	 * Return the session timeout interval based on the channel configuration
	 * 
	 * @return
	 */
	protected int getSessionTimeoutInterval() {
		return httpChannel.getConfig().getConnectorInfo().getSessionTimeout();
	}
}

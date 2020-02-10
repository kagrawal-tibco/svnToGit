package com.tibco.cep.runtime.plugin.lv;

import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.api.QueryException;
import com.tibco.cep.query.functions.QueryFunctions;
import com.tibco.cep.query.functions.QueryUtilFunctions;
import com.tibco.cep.query.service.QuerySession;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;
import com.tibco.cep.studio.core.adapters.ConceptAdapter;
import com.tibco.cep.util.ResourceManager;

import java.util.*;


/**
 * Created with IntelliJ IDEA.
 * User: Ameya
 * Date: 7/24/14
 * Time: 9:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class LVBEConfig {
    RuleSession ruleSession;
    TypeManager typeManager;
    String cddFile = getClass().getResource("resources/InferenceAndQuery.cdd").getPath();
    String repoUrl = getClass().getResource("resources/QueryTrades.ear").getPath();
    String traFile = getClass().getResource("resources/be-engine.tra").getPath();
    String processingUnit = "Query";
    RuleServiceProvider provider;
//    private static Object monitor = new Object();

//    Thread th1;
//    Thread th2 ;

    //Set debug to true to start BE in debug mode.
    boolean debug;

    final static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LVBEConfig.class);

    private void beConfig() throws Exception {
        Properties env = new Properties();

        env.put("tibco.repourl", repoUrl);
        env.put("be.bootstrap.property.file", traFile);
        env.put("com.tibco.be.config.path", cddFile);
        env.put("com.tibco.be.config.unit.id", processingUnit);

        if (debug) {
            env.put("com.tibco.cep.debugger.service.enabled", Boolean.TRUE.toString());
        }


        provider = RuleServiceProviderManager.getInstance().newProvider("LVBEConfig", env);
        RuleServiceProviderManager.getInstance().setDefaultProvider(provider);
        ResourceManager manager = ResourceManager.getInstance();
        manager.addResourceBundle("com.tibco.cep.container.messages", provider.getLocale());
        provider.initProject();
        Collection conceptsScorecard = provider.getProject().getOntology().getConcepts();
        Object [] arr = filterScoreCard(conceptsScorecard);
        com.tibco.cep.designtime.model.element.Concept concept = (Concept) arr[0];
        concept.getAllPropertyDefinitions();//ConceptPropertyDefinition
        Collection events = provider.getProject().getOntology().getEvents();
        com.tibco.cep.designtime.model.event.Event  event = (com.tibco.cep.designtime.model.event.Event) events.toArray()[2];
        event.getAllUserProperties();//EventPropertyDefinition

        provider.configure(RuleServiceProvider.MODE_CLUSTER);

    }

    public Object [] filterScoreCard(Collection conceptsScorecard) {

        List conceptList = new ArrayList();
        Iterator i = conceptsScorecard.iterator();
        while(i.hasNext()){
            ConceptAdapter obj = (ConceptAdapter) i.next();

            if(!obj.isAScorecard()) {
                conceptList.add(obj);
            }

        }
        return conceptList.toArray();

    }


    /**
     *
     * @throws InterruptedException
     * @throws QueryException
     */

    public void onInit() throws InterruptedException, QueryException {

        provider = RuleServiceProviderManager.getInstance().getDefaultProvider();


        while (((RuleServiceProviderImpl) provider).getStatus() != 15) {
            Thread.currentThread().sleep(10);
        }
        ruleSession = provider.getRuleRuntime().getRuleSessions()[0];
        typeManager = provider.getTypeManager();
        provider.getCluster().getClusterName();

        //RuleSessionManager.currentRuleSessions.set(ruleSession);
        String actualQuery = "SELECT s FROM /DataGenerator/Entities/Counter s";

        String queryName = UUID.randomUUID().toString();
        String sessionName = provider.getRuleRuntime().getRuleSessions()[0].getName();
        QuerySession rs = (QuerySession) provider.getRuleRuntime().getRuleSessions()[0];
        rs.createQuery(queryName,actualQuery);
        QueryFunctions.create(queryName,actualQuery);
        List results = (List) QueryUtilFunctions.executeInQuerySession(sessionName, actualQuery, null, false);
        System.out.println("results = " + results);
        LOGGER.log(Level.INFO, "Query Results obtained successfully");
        LOGGER.log(Level.INFO, "Result set size : " + results.size());

    }


    public static void main(String[] args) {
        try {

            LVBEConfig test = new LVBEConfig();
//            test.init();
//
//            test.th1.join();
//            test.th2.join();

            test.beConfig();
            test.onInit();

        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            LOGGER.log(Level.WARN, "Error %s", e);
            e.printStackTrace();
        }
    }
}

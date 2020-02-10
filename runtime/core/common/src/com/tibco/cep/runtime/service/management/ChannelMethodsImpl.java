package com.tibco.cep.runtime.service.management;

import java.util.ArrayList;

import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.TabularDataSupport;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.AgentClassConfig;
import com.tibco.be.util.config.cdd.AgentConfig;
import com.tibco.be.util.config.cdd.DashboardAgentClassConfig;
import com.tibco.be.util.config.cdd.DestinationConfig;
import com.tibco.be.util.config.cdd.InferenceAgentClassConfig;
import com.tibco.be.util.config.cdd.QueryAgentClassConfig;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.management.exception.BEMMEntityNotFoundException;
import com.tibco.cep.runtime.service.management.exception.BEMMUserActivityException;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.util.GvCommonUtils;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Mar 8, 2010
 * Time: 5:45:45 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class ChannelMethodsImpl extends EntityMBeansHelper {

    protected RuleServiceProvider ruleServiceProvider;

    public TabularDataSupport GetSessionInputDestinations(String sessionName) throws Exception {
        try {
            final String INVOKED_METHOD = "getSessionInputDestinations";
            MBeanTabularDataHandler tabularDataHandler = new MBeanTabularDataHandler(logger);
            tabularDataHandler.setTabularData(INVOKED_METHOD);

            if ((null == sessionName) || (sessionName.trim().length() == 0)) {
                throw new BEMMUserActivityException("Session name cannot be empty or null");
            } else {
                final RuleSession ruleSession = ruleServiceProvider.getRuleRuntime().getRuleSession(sessionName);
                if (null == ruleSession) {
                    throw new BEMMUserActivityException("Could not retrieve ruleSession for session: " + sessionName +
                            ". Verify if the session name is correct.");
                }

                if (getAgentType(ruleSession) == CacheAgent.Type.CACHESERVER)
                    throw new BEMMEntityNotFoundException("No destinations enabled for cache only agent nodes");

                putSessionInputDestsInTableRow(tabularDataHandler, getAgentDestinationsConfig(sessionName, ruleSession));

                return tabularDataHandler.getTabularData(INVOKED_METHOD);
            }
        }
        catch(BEMMUserActivityException uae) {
            logger.log(Level.WARN, uae.getMessage());
            throw uae;
        }
        catch (Exception e){
            logger.log(Level.ERROR, e, e.getMessage());
            throw e;
		}
    } //getSessionInputDestinations

    private ArrayList<DestinationConfig> getAgentDestinationsConfig(String sessionName, RuleSession ruleSession) {

        ArrayList<DestinationConfig> destsConfigList = new ArrayList<DestinationConfig>();
        ArrayList<String> refsTracking = new ArrayList<String>();

        //only returns the session input destinations for agents of type inference, query, and dashboard
        final CacheAgent.Type agentType = getAgentType(ruleSession);

        for (AgentConfig agentConfig : ruleServiceProvider.getProject().getProcessingUnitConfig().getAgents().getAgent()) {
        	GlobalVariables gvs=ruleServiceProvider.getProject().getGlobalVariables();
            final String key = GvCommonUtils.getStringValueIfExists(gvs,CddTools.getValueFromMixed(agentConfig.getKey()));
            if (agentConfig.getRef().getId().equals(sessionName) || key.equals(sessionName)) {
                // Found the Agent config
                AgentClassConfig aacc = agentConfig.getRef();

                // Find the type of class
                if (agentType == CacheAgent.Type.INFERENCE) {
                    InferenceAgentClassConfig iacc = (InferenceAgentClassConfig) aacc;
                    destsConfigList.addAll(iacc.getDestinations().getAllDestinations());
                }
                else if (agentType == CacheAgent.Type.QUERY) {
                    QueryAgentClassConfig qacc = (QueryAgentClassConfig) aacc;
                    destsConfigList.addAll(qacc.getDestinations().getAllDestinations());
                }
                if (agentType == CacheAgent.Type.DASHBOARD) {
                    DashboardAgentClassConfig dacc = (DashboardAgentClassConfig) aacc;
                    destsConfigList.addAll(dacc.getDestinations().getAllDestinations());
                }
                break; //agentConfig found for the current rule session. It's useless to loop over the remaining agents
            }
        }
        return destsConfigList;
    }


    private void putSessionInputDestsInTableRow(MBeanTabularDataHandler tabularDataHandler,
                                                ArrayList<DestinationConfig> destsConf) throws OpenDataException {
        //iterates over every destination
        int row=0;
        for (DestinationConfig dc : destsConf) {
            Object[] itemValues = new Object[tabularDataHandler.getNumItems()];

            itemValues[0] = row;
            itemValues[1] = dc.getUri();
            itemValues[2] = dc.getPreProcessor();
            row++;
            //ads current row to the table
            tabularDataHandler.put(itemValues);
        }
    } //putSessionInputDestsInTableRow

    /*private void putSessionInputDestsInTableRow(MBeanTabularDataHandler tabularDataHandler, int row,
                                        RuleSessionConfig.InputDestinationConfig dest ) throws OpenDataException {

        Object[] itemValues = new Object[tabularDataHandler.getNumItems()];
        itemValues[0] = row;
        itemValues[1] = dest.getURI();

        final RuleFunction preprocessor = dest.getPreprocessor();
        String preprocessorUri = "";
        if (null != preprocessor) {
            preprocessorUri = preprocessor.getSignature();
        }
        itemValues[2] = preprocessorUri;
        //ads current row to the table
        tabularDataHandler.put(itemValues);
    } //putSessionInputDestsInTableRow
*/
}

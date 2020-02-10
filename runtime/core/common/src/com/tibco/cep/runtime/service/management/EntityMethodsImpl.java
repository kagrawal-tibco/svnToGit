package com.tibco.cep.runtime.service.management;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.management.exception.BEMMUserActivityException;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;

import javax.management.openmbean.TabularDataSupport;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Mar 8, 2010
 * Time: 11:47:39 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class EntityMethodsImpl extends EntityMBeansHelper {

    protected RuleServiceProvider ruleServiceProvider;

    public TabularDataSupport GetNumberOfEvents(String sessionName) throws Exception{
        try {
            final String INVOKED_METHOD = "getNumberOfEvents";
            MBeanTabularDataHandler tabularDataHandler = new MBeanTabularDataHandler(logger);
            tabularDataHandler.setTabularData(INVOKED_METHOD);

            RuleSession[] sessions = getRuleSessions(ruleServiceProvider, sessionName);
		    for(int i=0; i<sessions.length; i++) {
                //only returns the number of events for agents of type inference
                if (getAgentType(sessions[i]) == CacheAgent.Type.INFERENCE)
                    putNumberOfEventsOrInstancesInTableRow(tabularDataHandler, i, sessions[i].getName(),
                                                           sessions[i].getObjectManager().numOfEvent());
            }

                return tabularDataHandler.getTabularData(INVOKED_METHOD);
        } catch (BEMMUserActivityException be) {
            logger.log(Level.INFO, "Illegal usage: %s", be.getMessage());
            throw be;
        } catch (Exception e) {
            logger.log(Level.ERROR, e, e.getMessage());
            throw e;
        } //catch
    } //getNumberOfEvents

    private void putNumberOfEventsOrInstancesInTableRow(MBeanTabularDataHandler tabularDataHandler, int row, String sessionName, int numOfEvents) {
        Object[] itemValues = new Object[tabularDataHandler.getNumItems()];
        itemValues[0] = row;
        itemValues[1] = sessionName;
        itemValues[2] = numOfEvents;
        //ads current row to the table
        tabularDataHandler.put(itemValues);
    }  //putNumberOfEventsOrInstancesInTableRow

    public TabularDataSupport GetNumberOfInstances(String sessionName) throws Exception {
        try {
            final String INVOKED_METHOD = "getNumberOfInstances";
            MBeanTabularDataHandler tabularDataHandler = new MBeanTabularDataHandler(logger);
            tabularDataHandler.setTabularData(INVOKED_METHOD);

            RuleSession[] sessions = getRuleSessions(ruleServiceProvider, sessionName);
		    for(int i=0; i<sessions.length; i++) {
                //only returns the number of instances for agents of type inference
                if (getAgentType(sessions[i]) == CacheAgent.Type.INFERENCE)
                putNumberOfEventsOrInstancesInTableRow(tabularDataHandler, i, sessions[i].getName(),
                            sessions[i].getObjectManager().numOfElement());
            }

            return tabularDataHandler.getTabularData(INVOKED_METHOD);
        } catch (BEMMUserActivityException be) {
            logger.log(Level.INFO, "Illegal usage: %s", be.getMessage());
            throw be;
        } catch (Exception e) {
            logger.log(Level.ERROR, e, e.getMessage());
            throw e;
        } //catch
    } //getNumberOfInstances

}

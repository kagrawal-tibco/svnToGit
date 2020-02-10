package com.tibco.cep.runtime.service.management;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.openmbean.TabularDataSupport;

import com.tibco.cep.kernel.core.rete.ReteWM;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.management.exception.BEMMEntityNotFoundException;
import com.tibco.cep.runtime.service.management.exception.BEMMException;
import com.tibco.cep.runtime.service.management.exception.BEMMUserActivityException;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.util.InmemoryRecorder;
import com.tibco.cep.util.InmemoryRecorder.EntityStatistic;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Mar 8, 2010
 * Time: 11:57:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class WMMethodsImpl extends EntityMBeansHelper {
    protected RuleServiceProvider ruleServiceProvider;

    public TabularDataSupport GetTotalNumberRulesFired(String sessionName)
            throws BEMMUserActivityException {
        try {
            final String INVOKED_METHOD = "getTotalNumberRulesFired";
            MBeanTabularDataHandler tabularDataHandler = new MBeanTabularDataHandler(logger);
            tabularDataHandler.setTabularData(INVOKED_METHOD);

            //gets the number of rules fired for every rule session if no session name provided
            final RuleSession[] sessions = getRuleSessions(ruleServiceProvider, sessionName);
            for(int i=0; i<sessions.length; i++) {
                //only gets the total number of rules fired for agents of type inference
                if (getAgentType(sessions[i]) == CacheAgent.Type.INFERENCE) {
                    final WorkingMemory wm = ((RuleSessionImpl)sessions[i]).getWorkingMemory();
                    putTotalNumRulesFiredInTableRow(tabularDataHandler, i, wm.getTotalNumberRulesFired(), wm.getName());
                }
            }

            return tabularDataHandler.getTabularData(INVOKED_METHOD);
        } catch (BEMMUserActivityException be){
            logger.log(Level.INFO, be.getMessage());
            throw be;
        } catch (Exception e){
            logger.log(Level.ERROR, e, e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }//catch
    } //getTotalNumberRulesFired

    private void putTotalNumRulesFiredInTableRow(MBeanTabularDataHandler tabularDataHandler, int row,
                                                 long numRulesFired, String wmName) {
        Object[] itemValues = new Object[tabularDataHandler.getNumItems()];
        itemValues[0] = row;
        itemValues[1] = wmName;
        itemValues[2] = numRulesFired;
        //ads current row to the table
        tabularDataHandler.put(itemValues);
    }  //putTotalNumRulesFiredInTableRow

    public TabularDataSupport GetRules(String sessionName) throws Exception {
        try {
            final String INVOKED_METHOD = "getRules";
            boolean inferenceFound=false;
            MBeanTabularDataHandler tabularDataHandler = new MBeanTabularDataHandler(logger);
            tabularDataHandler.setTabularData(INVOKED_METHOD);

            RuleSession[] sessions = getRuleSessions(ruleServiceProvider, sessionName);
            int line = 0;
		    for(int i=0; i<sessions.length; i++) {
                //only gets list of deployed rules for agents of type inference
                if (getAgentType(sessions[i]) == CacheAgent.Type.INFERENCE) {
                    inferenceFound = true;
                    logger.log(Level.DEBUG,"Retrieving list of rules deployed for session: " + sessions[i].getName());
                    WorkingMemory wm = ((RuleSessionImpl)sessions[i]).getWorkingMemory();
                       if (null != wm) {
                           for (Rule rule : wm.getRuleLoader().getDeployedRules()) {
                        	   putRulesInTableRow(tabularDataHandler, ++line, wm.getNumberOfRulesFired(rule.getUri()), rule, wm.getName());//BE-22598
                           }//for
                       }//if
                    else{
                       logger.log(Level.INFO, "Could not retrieve Working Memory for session: " + sessions[i].getName());
                   }
                }
            } //for

            if (!inferenceFound) {
                createInferenceNotFoundException(sessionName, sessions);
            }

           return tabularDataHandler.getTabularData(INVOKED_METHOD);
        } catch (BEMMException bemme) {
            logger.log(Level.WARN, bemme.getMessage());
            throw bemme;
        }
        catch (Exception e) {
            logger.log(Level.ERROR, e, e.getMessage());
            throw e;
        } //catch
    } //getRules

    private void putRulesInTableRow(MBeanTabularDataHandler tabularDataHandler, int row, long firedCount, Rule rule, String wmName) {
        Object[] itemValues = new Object[tabularDataHandler.getNumItems()];
        itemValues[0] = row;
        itemValues[1] = wmName;
        itemValues[2] = rule.getUri();
        itemValues[3] = rule.isActive();
        itemValues[4] = firedCount;
        
        //ads current row to the table
        tabularDataHandler.put(itemValues);
    }  //putRulesInTableRow

    //'wmName' and 'session' are used interchangeably and mean the same thing. It's just a matter of naming preference
    public TabularDataSupport GetRule(String sessionName, String ruleUri) throws Exception {
        try {
            final String INVOKED_METHOD = "getRule";
            boolean inferenceFound=false;
            MBeanTabularDataHandler tabularDataHandler = new MBeanTabularDataHandler(logger);
            tabularDataHandler.setTabularData(INVOKED_METHOD);

            if ((null == ruleUri) || (ruleUri.trim().length() == 0)) {
                throw new BEMMUserActivityException("Rule URI argument is empty and is required. Please provide a valid rule URI");
            }

            RuleSession[] sessions = getRuleSessions(ruleServiceProvider, sessionName);
		    for(int i=0; i<sessions.length; i++) {
                //only gets rules info for agents of type inference
                if (getAgentType(sessions[i]) == CacheAgent.Type.INFERENCE) {
                    inferenceFound = true;
                    logger.log(Level.DEBUG,"Retrieving rule info for rule: "+ruleUri+" in session: "+sessions[i].getName());
                    putRuleInTableRow(tabularDataHandler, i, ruleUri, ((RuleSessionImpl) sessions[i]).getWorkingMemory());

                    //it means that no rule with the provided URI was found
                    if (tabularDataHandler.getTabularData(INVOKED_METHOD).size() == 0) {
                        throw new BEMMEntityNotFoundException("No rule deployed with URI: " + ruleUri + ". Please enter a valid rule URI.");
                    }
                }
            }
            //if the code gets here it means that there are no inference agents in this PU
            if (!inferenceFound) {
                createInferenceNotFoundException(sessionName, sessions);
            }

            return tabularDataHandler.getTabularData(INVOKED_METHOD);
        } catch (BEMMException bemme) {
            logger.log(Level.WARN, bemme.getMessage());
            throw bemme;
        }
        catch (Exception e) {
            logger.log(Level.ERROR, e, e.getMessage());
            throw e;
        } //catch
    } //getRule

    private void createInferenceNotFoundException(String sessionName, RuleSession[] sessions) throws BEMMEntityNotFoundException {
        String msg = null;
        if ((null != sessionName) && !"".equals(sessionName.trim()) && sessions.length==1) {
            msg = "Session \'" + sessionName + "\' is of type \'" + getAgentType(sessions[0]).name() + "\'";
        } else {
            msg = "No running inference agents were found";
        }
        msg+= ". This operation only applies to sessions of type Inference";

       throw new BEMMEntityNotFoundException(msg);
    }

    private void putRuleInTableRow(MBeanTabularDataHandler tabularDataHandler, int row,
                                                 String ruleUri, WorkingMemory wm) {
        //searches among deployed rules for the rule matching the URI provided by the user
        for (Rule rule : wm.getRuleLoader().getDeployedRules()) {
            if (rule.getUri().equals(ruleUri)) {
                Object[] itemValues = new Object[tabularDataHandler.getNumItems()];
                itemValues[0] = row;
                itemValues[1] = wm.getName();    //session
                itemValues[2] = rule.getUri();
                itemValues[3] = rule.getPriority();
                itemValues[4] = rule.getId();     //internal Id
//              todo  itemValues[4] = rule.getTotalNumFired();     num times this rule has been fired. When done also need to add entry to TabularDataDescriptor for it to work
                //ads current row to the table
                tabularDataHandler.put(itemValues);
                return;
             }
         } //for
    } //putRuleInTableRow

    public TabularDataSupport ActivateRule(String session, String URI) throws BEMMUserActivityException {
        return activateRule(session, URI, true);
    }

    public TabularDataSupport DeactivateRule(String session, String URI) throws BEMMUserActivityException {
        return activateRule(session, URI, false);
    }

    //boolean parameter isActivate is necessary for code reuse. True if activateRule was invoked,
    //false if deactivateRule was invoked.
    private TabularDataSupport activateRule(
                    String sessionName,
                    String URI,
                    boolean isActivate)
                throws BEMMUserActivityException {
        try {
            final String INVOKED_METHOD = isActivate ? "activateRule" : "deactivateRule";
            MBeanTabularDataHandler tabularDataHandler = new MBeanTabularDataHandler(logger);
            tabularDataHandler.setTabularData(INVOKED_METHOD);

            if (URI == null || URI.trim().length() == 0) {
                throw new IllegalArgumentException("Rule URI is empty. Please provide a valid Rule URI");
            }

            RuleSession[] sessions = getRuleSessions(ruleServiceProvider, sessionName);
            for(int i=0; i<sessions.length; i++) {
                //only activates/deactivates rules for agents of type inference
                if (getAgentType(sessions[i]) == CacheAgent.Type.INFERENCE) {
                    RuleSessionImpl rs = (RuleSessionImpl) sessions[i];           //rs.getConfig().getDeployedRuleUris(); //todo
                    putActivateRulesResultInTableRow(tabularDataHandler, URI, rs.getWorkingMemory(), isActivate);
                }
            }

            return tabularDataHandler.getTabularData(INVOKED_METHOD);
        } catch (BEMMUserActivityException be) {
            logger.log(Level.INFO, be.getMessage());
            throw be;
        } catch (Exception e){
            logger.log(Level.ERROR, e, e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        } //catch
    } //activateRule

    private void putActivateRulesResultInTableRow(
                        MBeanTabularDataHandler tabularDataHandler,
                        String ruleUri,
                        WorkingMemory session,
                        boolean isActivate)
            throws BEMMUserActivityException {

        final Rule rule = isActivate ? session.getRuleLoader().activateRule(ruleUri) : session.getRuleLoader().deactivateRule(ruleUri);
        if (rule == null) {
            throw new BEMMUserActivityException("Invalid Rule URI: " + ruleUri);
        }
        Object[] itemValues = new Object[tabularDataHandler.getNumItems()];
        itemValues[0] = session.getName();
        itemValues[1] = rule.getUri();
        itemValues[2] = isActivate ? rule.isActive() : !rule.isActive();
        //ads current row to the table
        tabularDataHandler.put(itemValues);
    }  //putTotalNumRulesFiredInTableRow

    public void ResetTotalNumberRulesFired(String sessionName) throws Exception {
        try {
            RuleSession[] sessions = getRuleSessions(ruleServiceProvider, sessionName);
		    for(int i=0; i<sessions.length; i++) {
                //only resets the number of rules fired for agents of type inference
                if (getAgentType(sessions[i]) == CacheAgent.Type.INFERENCE) {
                    logger.log(Level.DEBUG, "Resetting the number of rules fired for session " + sessions[i].getName());
                    WorkingMemory wm = ((RuleSessionImpl)sessions[i]).getWorkingMemory();
                    wm.resetTotalNumberRulesFired();
                }
            }
            //returns if no exception occurred
        } catch (BEMMUserActivityException be) {
            logger.log(Level.INFO, be.getMessage());
            throw be;
        } catch (Exception e) {
            logger.log(Level.ERROR, e, e.getMessage());
            throw e;
        }//catch
    } //resetTotalNumberRulesFired

    public TabularDataSupport GetWorkingMemoryDump(String sessionName) throws Exception {
        try {
            final String INVOKED_METHOD = "getWorkingMemoryDump";
            MBeanTabularDataHandler tabularDataHandler = new MBeanTabularDataHandler(logger);
            tabularDataHandler.setTabularData(INVOKED_METHOD);

            RuleSession[] sessions = getRuleSessions(ruleServiceProvider, sessionName);
            for (int i = 0; i < sessions.length; i++) {
                //only gets the working memory dump for agents of type inference
                if (getAgentType(sessions[i]) == CacheAgent.Type.INFERENCE) {
                    final RuleSessionImpl session = (RuleSessionImpl) sessions[i];
                    putWorkingMemoryDumpInTableRow(tabularDataHandler, i, session.getName(),
                            ((ReteWM) session.getWorkingMemory()).getMemoryDump().toString());
                }
            } //for

            return tabularDataHandler.getTabularData(INVOKED_METHOD);
        } catch(BEMMUserActivityException uae) {
            logger.log(Level.WARN, uae.getMessage());
            throw uae;
        }
        catch (Exception e){
            logger.log(Level.ERROR, e, e.getMessage());
            throw e;
		}
    } //getWorkingMemoryDump

    private void putWorkingMemoryDumpInTableRow(MBeanTabularDataHandler tabularDataHandler, int row,
                                                String sessionName, String WMemoryDump) {
        Object[] itemValues = new Object[tabularDataHandler.getNumItems()];
        itemValues[0] = row;
        itemValues[1] = sessionName;
        itemValues[2] = WMemoryDump;
        //ads current row to the table
        tabularDataHandler.put(itemValues);
    } //putWorkingMemoryDumpInTableRow


    /** Returns a table with the name of the specified session, or of every session. 
     *  @param sessionName - name of the session to retrieve. If it is null, iterates over every session *
     *  @return A table with the name of the specified session, or of every session.
     *  @throws IllegalArgumentException - if invalid session name provided
     */
    protected TabularDataSupport geTRuleSessions(String sessionName) throws Exception {
        try {
           final String INVOKED_METHOD = "getSessions";
           MBeanTabularDataHandler tabularDataHandler = new MBeanTabularDataHandler(logger);
           tabularDataHandler.setTabularData(INVOKED_METHOD);

           logger.log(Level.INFO,"Getting rule sessions info...");

           final RuleSession[] sessions = getRuleSessions(ruleServiceProvider,sessionName);
           for (int i = 0; i < sessions.length; i++) {
               final RuleSession session = sessions[i];
               putSessionsInTableRow(tabularDataHandler, i, session.getName());
            }//for
           return tabularDataHandler.getTabularData(INVOKED_METHOD);
        } catch (Exception e){
            logger.log(Level.ERROR, e, e.getMessage());
            throw e;
        }//catch
    }

    protected void putSessionsInTableRow(MBeanTabularDataHandler tabularDataHandler, int row, String sessionName) {
        Object[] itemValues = new Object[tabularDataHandler.getNumItems()];
        itemValues[0] = row;
        itemValues[1] = sessionName;
        //ads current row to the table
        tabularDataHandler.put(itemValues);
    } //putSessionsInTableRow
    
    public TabularDataSupport GetEntityStatistic(String sessionName, String eventUri) throws Exception {
        try {
            final String INVOKED_METHOD = "getEntityStatistic";
            MBeanTabularDataHandler tabularDataHandler = new MBeanTabularDataHandler(logger);
            tabularDataHandler.setTabularData(INVOKED_METHOD);
            
            final RuleSession[] sessions = getRuleSessions(ruleServiceProvider, sessionName);
            
            Object[] itemValues = new Object[tabularDataHandler.getNumItems()];
            if (eventUri != null && !eventUri.trim().isEmpty()) {
            	long assertedCount = 0;
                for (RuleSession session : sessions) {
                	EntityStatistic statistic = InmemoryRecorder.getEntityStatistic(session.getName(), eventUri);
                    if (statistic != null) {
                    	assertedCount = statistic.getAssertedCount();
                    }
                }
                itemValues[0] = eventUri;
                itemValues[1] = assertedCount;
                
                tabularDataHandler.put(itemValues);
            } else {
            	Set<String> uniqueUris = new HashSet<String>();
            	List<Map<String, EntityStatistic>> allSessionsStatistics = new ArrayList<>();
            	for (RuleSession session : sessions) {
                	Map<String, EntityStatistic> statistics = InmemoryRecorder.getEntityStatistics(session.getName());
                	uniqueUris.addAll(statistics.keySet());
                	allSessionsStatistics.add(statistics);
                }
            	for (String uri : uniqueUris) {
            		long assertedCount = 0;
            		for (Map<String, EntityStatistic> sessionStatistics : allSessionsStatistics) {
            			EntityStatistic statistic = sessionStatistics.get(uri);
            			if (statistic != null) {
            				assertedCount += statistic.getAssertedCount();
            			}
                	}
            		
                    itemValues[0] = uri;
                    itemValues[1] = assertedCount;
                    
                    tabularDataHandler.put(itemValues);
            	}
            }
            
            return tabularDataHandler.getTabularData(INVOKED_METHOD);
        } catch (Exception e){
            logger.log(Level.ERROR, e, e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}

package com.tibco.cep.runtime.service.management.process.impl;

import com.tibco.cep.cep_commonVersion;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.event.AdvisoryEvent;
import com.tibco.cep.runtime.model.event.AdvisoryEventDictionary;
import com.tibco.cep.runtime.model.event.impl.AdvisoryEventImpl;
import com.tibco.cep.runtime.service.ft.FTNode;
import com.tibco.cep.runtime.service.management.EntityMethodsImpl;
import com.tibco.cep.runtime.service.management.MBeanTabularDataHandler;
import com.tibco.cep.runtime.service.management.exception.BEMMUserActivityException;
import com.tibco.cep.runtime.service.management.process.EngineEngineMBean;
import com.tibco.cep.runtime.service.management.process.EngineMBeansSetter;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.FTAsyncRuleServiceProviderImpl;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;

import javax.management.openmbean.TabularDataSupport;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Mar 8, 2010
 * Time: 12:30:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class EngineEngineMBeanImpl extends EntityMethodsImpl implements EngineEngineMBean, EngineMBeansSetter {

    //entity methods that are common to agents and engines are implemented in the superclass

    Properties hostInfoProps;

    public static final String APPLICATION_STATE_UNINITIALIZED = "UNINITIALIZED";
    public static final String APPLICATION_STATE_INITIALIZING = "INITIALIZING";
    public static final String APPLICATION_STATE_RUNNING = "RUNNING";
    public static final String APPLICATION_STATE_STOPPING = "STOPPING";
    public static final String APPLICATION_STATE_STOPPED = "STOPPED";
    public static final String APPLICATION_STATE_STANDBY = "STANDBY";

    final String PROPERTY_NAME_APPLICATION_NAME = "Application Name";
    final String PROPERTY_NAME_APPLICATION_INSTANCE = "Application Instance";
    final String PROPERTY_NAME_APPLICATION_STATE = "Application State";

    public void setRuleServiceProvider(RuleServiceProvider ruleServiceProvider) {
        super.ruleServiceProvider = ruleServiceProvider;
        setProperties();
    }

    public void setLogger(Logger logger) {
        super.logger = logger;
    }

    private Properties setProperties() {
        hostInfoProps = new Properties();
        hostInfoProps.put(PROPERTY_NAME_APPLICATION_NAME, cep_commonVersion.getComponent());
        if (null != ruleServiceProvider) {
            hostInfoProps.put(PROPERTY_NAME_APPLICATION_INSTANCE, ruleServiceProvider.getName());
        }
        hostInfoProps.put(PROPERTY_NAME_APPLICATION_STATE, getApplicationState());
        return hostInfoProps;
    } //setProperties

    public void StopEngine() {
        new Thread(){
            @Override
            public void run() {
                Runtime.getRuntime().exit(0);  //it calls the shutdown hook
            }
        }.start();
    } //stopEngine

        /**
     * @return a String that represents the application State
     * (for example {@link #APPLICATION_STATE_UNINITIALIZED}, {@link #APPLICATION_STATE_INITIALIZING},
     * {@link #APPLICATION_STATE_RUNNING}, etc.
     */
        //TODO: Check this is still correct in 50
    private String getApplicationState() {
		if (ruleServiceProvider instanceof RuleServiceProviderImpl) {
			if (((RuleServiceProviderImpl) ruleServiceProvider).isContained()) {
				ruleServiceProvider = ((RuleServiceProviderImpl) ruleServiceProvider).getContainerRsp();
				final FTNode currentNode;
				try {
					currentNode = ((FTAsyncRuleServiceProviderImpl) ruleServiceProvider)
							.getCurrentNode();
				} catch (Exception e) {
					if (null != this.logger) {
						this.logger.log(Level.DEBUG,
                                "Application State set to INITIALIZING upon exception trying to get current node");
						this.logger.log(Level.DEBUG, "Current node will null before joining the cluster");
					}
					return APPLICATION_STATE_INITIALIZING;
				}
				if (null == currentNode) {
					if (null != this.logger) {
						this.logger.log(Level.DEBUG,
                                "Application State set to INITIALIZING upon finding current node is null.");
					}
					return APPLICATION_STATE_INITIALIZING;
				}

				final int nodeState = currentNode.getNodeState();
				switch (nodeState) {
                    case FTNode.NODE_SHUTDOWN:
                        return APPLICATION_STATE_STOPPING;
                    case FTNode.NODE_CREATED:
                    case FTNode.NODE_JOINED_CACHE:
                        return APPLICATION_STATE_INITIALIZING;
                    case FTNode.NODE_INACTIVE:
                    case FTNode.NODE_WAIT_FOR_ACTIVATION:
                        return APPLICATION_STATE_STANDBY;
				}
			} else {
				switch (((RuleServiceProviderImpl) ruleServiceProvider).getStatus()) {
                    case RuleServiceProviderImpl.STATE_PROJECT_INITIALIZED:
                    case RuleServiceProviderImpl.STATE_UNINITIALIZED:
                        return APPLICATION_STATE_INITIALIZING;
                    case RuleServiceProviderImpl.STATE_RUNNING:
                        return APPLICATION_STATE_RUNNING;
				}
			}
		}
		return APPLICATION_STATE_RUNNING;
	}// getApplicationState

    public TabularDataSupport GetHostInformation(String propName) throws Exception {
        final String INVOKED_METHOD = "getHostInformation";
        try {
            MBeanTabularDataHandler tabularDataHandler = new MBeanTabularDataHandler(logger);
            tabularDataHandler.setTabularData(INVOKED_METHOD);

            hostInfoProps.put(PROPERTY_NAME_APPLICATION_STATE, getApplicationState());

            if (propName == null || propName.trim().length() == 0) {
                Enumeration keys = hostInfoProps.keys();
                while (keys.hasMoreElements()) {
                    String key = (String) keys.nextElement();
                    String value = (String) hostInfoProps.get(key);
//                    if (debug()) debug("calling fillInOneReturnsEntry with key=" + key + " and value=" + value);
                    putHostInfoInTableRow(tabularDataHandler, key, value);
                }//while
            } else if(hostInfoProps.get(propName) != null) {
                    putHostInfoInTableRow(tabularDataHandler, propName, (String) hostInfoProps.get(propName));
            }//else
            else {
                Enumeration keys = hostInfoProps.keys();
                String str="\n\t";
                while(keys.hasMoreElements()) {
                     //NOTE: I am concatenating the strings with $ because this token is used in the UI to split the strings
                    str= str + keys.nextElement() + "\n\t";
                }
                str = str.substring(0, str.length()-1);     //remove the last $
                throw new BEMMUserActivityException("No information available for property '" + propName + "'." +
                                                    " Choose one of the following properties (case sensitive):"+str );
            }
            return tabularDataHandler.getTabularData(INVOKED_METHOD);
        } catch (BEMMUserActivityException uae) {
            logger.log(Level.WARN, uae.getMessage());
            throw uae;
        }
        catch (Exception e) {
            logger.log(Level.ERROR,e,"Exception occurred while invoking method %s .",INVOKED_METHOD);
            throw e;
        }
    } //getHostInformation

    /*public TabularDataSupport geTNumberOfEvents() {
        return getNumberOfEvents( null );
    }

    public TabularDataSupport geTNumberOfInstances() {
        return getNumberOfInstances( null );
    }*/

    private void putHostInfoInTableRow(MBeanTabularDataHandler tabularDataHandler, String propName /*key*/, String propValue) {
        Object[] itemValues = new Object[tabularDataHandler.getNumItems()];
        itemValues[0] = propName;
        itemValues[1] = propValue;
        //ads current row to the table
        tabularDataHandler.put(itemValues);
    }  //putHostInfoInTableRow

    public TabularDataSupport GetMemoryUsage() {
       try {
            final String INVOKED_METHOD = "getMemoryUsage";
            MBeanTabularDataHandler tabularDataHandler = new MBeanTabularDataHandler(logger);
            tabularDataHandler.setTabularData(INVOKED_METHOD);
			putMemoryUsageInTableRow(tabularDataHandler);
            return tabularDataHandler.getTabularData(INVOKED_METHOD);
        } catch (Exception e) {
            logger.log(Level.ERROR, e, e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        } //catch
    }  //putNumberOfEventsOrInstancesInTableRow

    /**
     * Prepares the data for GetVersionInfo MBean operation.
     */
    public TabularDataSupport GetVersionInfo() {//BE-22598
    	try {
    		final String INVOKED_METHOD = "getVersionInfo";
    		MBeanTabularDataHandler tabularDataHandler = new MBeanTabularDataHandler(logger);
    		tabularDataHandler.setTabularData(INVOKED_METHOD);
    		
    		Object[] itemValues = new Object[tabularDataHandler.getNumItems()];
    		itemValues[0] = cep_commonVersion.version + "." + cep_commonVersion.build;//BE Engine version
    		itemValues[1] = ruleServiceProvider.getProject().getVersion();//Application archive version
    		itemValues[2] = ruleServiceProvider.getProject().getName();//Application name
    		
    		tabularDataHandler.put(itemValues);
    		
    		return tabularDataHandler.getTabularData(INVOKED_METHOD);
    	} catch (Exception e) {
    		logger.log(Level.ERROR, e, e.getMessage());
    		throw new RuntimeException(e.getMessage(), e);
    	}
    }

    private void putMemoryUsageInTableRow(MBeanTabularDataHandler tabularDataHandler) {
        Object[] itemValues = new Object[tabularDataHandler.getNumItems()];
        final long max = Runtime.getRuntime().maxMemory();
        final long free = max - Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory();
        final long used = max - free;

        itemValues[0] = max;
        itemValues[1] = free;
        itemValues[2] = used;
        itemValues[3] = 100 * used / max;
        //ads current row to the table
        tabularDataHandler.put(itemValues);
    }  //geTMemoryUsage

     /*public boolean executeCommand(String command, String arguments) {
        try {
            String[] args = arguments.split(",", -1);                           //TODO: Check this
            Process p = Runtime.getRuntime().exec(command, args);
            return p.exitValue() == 0;  //returns 0 if normally terminated, i.e., method successfully invoked
        } catch (IOException e) {
            logger.log(Level.ERROR, e, e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
		}
    } //executeCommand
    */

    @Override
    public void SetLogLevel(String loggerNameOrPattern, String level) throws Exception {
        final String INVOKED_METHOD = "setLevel";
        try {
            logger.log(Level.DEBUG, "Activating level: "+level+" for loggername/loggernamepattern: "+loggerNameOrPattern);
            LogManagerFactory.getLogManager().setLevel(loggerNameOrPattern, level);
            String msg = "Log level for logger '" + loggerNameOrPattern + "' updated to level '" + level + "'";
            RuleSession[] ruleSessions = getRuleSessions(ruleServiceProvider, null);
            for (RuleSession ruleSession : ruleSessions) {
            	ruleSession.assertObject(new AdvisoryEventImpl(
            			ruleSession.getRuleServiceProvider().getIdGenerator().nextEntityId(AdvisoryEventImpl.class),
                 		null, AdvisoryEvent.CATEGORY_ENGINE, AdvisoryEventDictionary.ENGINE_LogLevelUpdated, msg), true);
            }
        } catch (IllegalArgumentException iae) {
            logger.log(Level.INFO, iae.getMessage());
            throw new BEMMUserActivityException(iae.getMessage());
        } catch (Exception e) {
            logger.log(Level.ERROR,e,"Exception occurred while invoking method %s", INVOKED_METHOD );
            throw e;
        }
    }

    @Override
    public TabularDataSupport GetLoggerNamesWithLevels() {
    	try {
            final String INVOKED_METHOD = "getLoggerNamesWithLevels";
            MBeanTabularDataHandler tabularDataHandler = new MBeanTabularDataHandler(logger);
            tabularDataHandler.setTabularData(INVOKED_METHOD);
            Logger[] loggers = LogManagerFactory.getLogManager().getLoggers();
            for (Logger logger : loggers) {
				Object[] values = new Object[tabularDataHandler.getNumItems()];
				values[0] = logger.getName();
				values[1] = logger.getLevel().toString();
				tabularDataHandler.put(values);
			}
            return tabularDataHandler.getTabularData(INVOKED_METHOD);
        } catch (Exception e) {
            logger.log(Level.ERROR, e, e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }
} //class

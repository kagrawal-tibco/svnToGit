package com.tibco.rta.service.persistence.as;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.xml.sax.InputSource;

import com.tibco.as.space.ASException;
import com.tibco.as.space.ASStatus;
import com.tibco.as.space.Space;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.browser.Browser;
import com.tibco.as.space.browser.BrowserDef;
import com.tibco.as.space.browser.BrowserDef.BrowserType;
import com.tibco.as.space.browser.BrowserDef.DistributionScope;
import com.tibco.as.space.browser.BrowserDef.TimeScope;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.rta.common.service.session.ServerSession;
import com.tibco.rta.common.service.session.ServerSessionRegistry;
import com.tibco.rta.common.service.session.command.SessionCommand;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.serialize.impl.SerializationUtils;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.query.QueryType;
import com.tibco.rta.service.persistence.SessionPersistenceService;


public class SessionASPersistenceService extends AbstractStartStopServiceImpl implements SessionPersistenceService{

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_RUNTIME_SESSION.getCategory());
	private Space space;
	private ASPersistenceService pService;

	@Override
	public void init(Properties configuration) throws Exception {
		space = ((ASPersistenceService) ServiceProviderManager.getInstance().getPersistenceService())
				.getSchemaManager().getOrCreateSessionSchema();
		pService = (ASPersistenceService) ServiceProviderManager.getInstance().getPersistenceService();
	}

	@Override
	public void start() throws Exception {
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "Starting Session Persistence service..");
		}
		super.start();
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "Starting Session Persistence service Complete.");
		}
	}

	@Override
	public void stop() throws Exception {
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "Stopping Session Persistence service..");
		}
		super.stop();
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "Stopping Session Persistence service Complete.");
		}
	}

	@Override
	public void suspend() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isStarted() {
		// TODO Auto-generated method stub
		return super.isStarted();
	}

	@Override
	public void persist(ServerSession<?> serverSession) throws Exception {

        Collection<SessionCommand<?>> checkpointDelta = serverSession.getCheckpointDelta();
        Iterator<SessionCommand<?>> iterator = checkpointDelta.iterator();
        if (space == null) {
            throw new RuntimeException("Space is not defined");
        }
        try {
        	pService.beginTransaction();
            while (iterator.hasNext()) {
                SessionCommand<?> sessionCommand = iterator.next();
                Object targetObject = sessionCommand.getTargetObject();

                if (targetObject instanceof ServerSession<?>) {
                    persistVanillaSession(serverSession.getId(), serverSession.getSessionName());
                } else if (targetObject instanceof QueryDef) {
                    persistQuery(serverSession.getId(), serverSession.getSessionName(), (QueryDef) targetObject);
                } else if (targetObject instanceof RuleDef) {
                    persistRule(serverSession.getId(), serverSession.getSessionName(), (RuleDef) targetObject);
                }
            }
            pService.commit();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Exception while persisting session", e);
        } finally {
        }
    		
	}

	private void persistRule(String id, String sessionName, RuleDef ruleDef) throws Exception {
		Tuple tuple = Tuple.create();
		tuple.putString(ASPersistenceService.SESSION_ID_FIELD, id);
		tuple.putString(ASPersistenceService.SESSION_NAME_FIELD, sessionName);
		tuple.putString(ASPersistenceService.SESSION_RULE_NAME_FIELD, ruleDef.getName());
		tuple.putString(ASPersistenceService.SESSION_RULE_DETAIL_FIELD, SerializationUtils.serializeRule(ruleDef));
		saveTuple(tuple);			
	}

	private void persistQuery(String id, String sessionName, QueryDef queryDef) throws Exception {
		if(queryDef.getQueryType()==QueryType.SNAPSHOT){
			return;
		}
		Tuple tuple = Tuple.create();
		tuple.putString(ASPersistenceService.SESSION_ID_FIELD, id);
		tuple.putString(ASPersistenceService.SESSION_NAME_FIELD, sessionName);
		tuple.putString(ASPersistenceService.SESSION_QUERY_NAME_FIELD, queryDef.getName());
		tuple.putString(ASPersistenceService.SESSION_QUERY_DETAIL_FIELD, SerializationUtils.serializeQuery(queryDef));
		saveTuple(tuple);
	}

	private void persistVanillaSession(String id, String sessionName) throws Exception {
		Tuple tuple = Tuple.create();
		tuple.putString(ASPersistenceService.SESSION_ID_FIELD, id);
		tuple.putString(ASPersistenceService.SESSION_NAME_FIELD, sessionName);
		saveTuple(tuple);
	}

	@Override
	public void cleanup(ServerSession<?> serverSession) throws Exception {

        Collection<SessionCommand<?>> checkpointDelta = serverSession.getCheckpointDelta();
        Iterator<SessionCommand<?>> iterator = checkpointDelta.iterator();
        if (space == null) {
            throw new RuntimeException("space is not defined.");
        }
        try {
        	pService.beginTransaction();
            while (iterator.hasNext()) {
                SessionCommand<?> sessionCommand = iterator.next();
                Object targetObject = sessionCommand.getTargetObject();

                if (targetObject instanceof QueryDef) {
                    removeQuery(serverSession.getId(), (QueryDef) targetObject);
                } else if (targetObject instanceof ServerSession) {
                    removeSession(serverSession.getId());
                }
            }
            pService.commit();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Exception while removing session", e);
        } finally {

        }
    
		
	}

	private void removeSession(String sessionID) throws ASException {
		Tuple tuple = Tuple.create();
		tuple.putString(ASPersistenceService.SESSION_ID_FIELD, sessionID);
		space.take(tuple);
	}

	private void removeQuery(String sessionID, QueryDef targetObject) throws ASException {
		Tuple tuple = Tuple.create();
		tuple.putString(ASPersistenceService.SESSION_ID_FIELD, sessionID);
		tuple.putString(ASPersistenceService.SESSION_QUERY_NAME_FIELD, targetObject.getName());
		space.take(tuple);
	}

	void saveTuple(Tuple tuple) throws Exception {

		int attempt = 0;
		while (attempt < 10) {
			try {
				attempt++;

				// PutOptions po = PutOptions.create();
				// po.setLockWait(SpaceDef.DEFAULT_LOCK_WAIT);
				// // po.setLockWait(10000);
				space.put(tuple);
				break;
			} catch (ASException exception) {
				// ASPersistenceService.LOGGER.log(Level.ERROR,
				// "Failed to lock key " + attempt);
				if (exception.getStatus() == ASStatus.LOCKED) {
					if (attempt == 10) {
						throw new Exception("failed to lock key 10 times");
					}
				} else if (exception.getStatus() == ASStatus.OPERATION_TIMEOUT) {
					LOGGER.log(Level.ERROR, "Put operation timed out. Space [%s] not ready for operation",
							space.getName());
					LOGGER.log(Level.ERROR, "Attempting retry to put operation, counter [%s] times after waiting for [%s] milis", attempt, space.getSpaceDef().getSpaceWait());
				} else {
					throw exception;
				}
			}
		}
	}
	
	@Override
	public ServerSession<?>[] recover() throws Exception {
		// TODO Auto-generated method stub
		List<ServerSession> recoveredSessionList = new ArrayList();
		BrowserDef browserDef = BrowserDef.create();
		browserDef.setDistributionScope(DistributionScope.ALL);
		browserDef.setTimeScope(TimeScope.SNAPSHOT);
		Browser browser = space.browse(BrowserType.GET, browserDef);
		Tuple tuple;
		while ((tuple = browser.next()) != null) {
			String sessionID = tuple.getString(ASPersistenceService.SESSION_ID_FIELD);
			String sessionName = tuple.getString(ASPersistenceService.SESSION_NAME_FIELD);
			ServerSession serverSession = ServerSessionRegistry.INSTANCE.getServerSession(sessionID);
			if(serverSession == null){
				serverSession = ServerSessionRegistry.INSTANCE.addOrCreateSession(sessionID, sessionName);
				recoveredSessionList.add(serverSession);
			}
			
			String queryDetails = tuple.getString(ASPersistenceService.SESSION_QUERY_DETAIL_FIELD);
			if (queryDetails != null) {
				QueryDef queryDef = SerializationUtils.deserializeQuery(new InputSource(queryDetails));
				serverSession.registerRecoveredQuery(queryDef);
			}
		}
		browser.stop();
		return recoveredSessionList.toArray(new ServerSession[recoveredSessionList.size()]);
	}

}

package com.tibco.rta.service.persistence.memory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.rta.common.service.session.ServerSession;
import com.tibco.rta.common.service.session.command.SessionCommand;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.service.persistence.SessionPersistenceService;

public class SessionMemoryPersistenceService extends
		AbstractStartStopServiceImpl implements SessionPersistenceService {

	private static final Logger LOGGER = LogManagerFactory.getLogManager()
			.getLogger(LoggerCategory.RTA_RUNTIME_SESSION.getCategory());
	private Map<String, ServerSession<?>> sessionStore = new ConcurrentHashMap<String, ServerSession<?>>();
	private Map<Integer, QueryDef> queryStore = new ConcurrentHashMap<Integer, QueryDef>();
	private List<String> queryNames = new ArrayList<String>();
	private Map<RuleDef, String> ruleStore = new ConcurrentHashMap<RuleDef, String>();
	private InMemoryPersistenceProvider pService;

	@Override
	public void init(Properties configuration) throws Exception {
		pService = (InMemoryPersistenceProvider) ServiceProviderManager
				.getInstance().getPersistenceService();
	}

	@Override
	public void start() throws Exception {
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "Starting Session Persistence service..");
		}
		super.start();
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO,
					"Starting Session Persistence service Complete.");
		}
	}

	@Override
	public void stop() throws Exception {
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "Stopping Session Persistence service..");
		}
		super.start();
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO,
					"Stopping Session Persistence service Complete.");
		}
	}

	@Override
	public void persist(ServerSession<?> serverSession) throws Exception {
		Collection<SessionCommand<?>> checkpointDelta = serverSession
				.getCheckpointDelta();
		Iterator<SessionCommand<?>> iterator = checkpointDelta.iterator();

		try {
			pService.beginTransaction();
			while (iterator.hasNext()) {
				SessionCommand<?> sessionCommand = iterator.next();
				Object targetObject = sessionCommand.getTargetObject();

				if (targetObject instanceof ServerSession<?>) {
					if (LOGGER.isEnabledFor(Level.INFO)) {
						LOGGER.log(Level.INFO, "Persisting session [%s]",
								serverSession.getSessionName());
					}
					sessionStore.put(serverSession.getId(), serverSession);
				} else if (targetObject instanceof QueryDef) {
					queryStore.put(serverSession.getId().hashCode()
							+ ((QueryDef) targetObject).hashCode(),
							(QueryDef) targetObject);
					queryNames.add(((QueryDef) targetObject).getName());
				} else if (targetObject instanceof RuleDef) {
					ruleStore
							.put((RuleDef) targetObject, serverSession.getId());
				}
			}
			pService.commit();
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "Exception while persisting session", e);
		}
	}

	@Override
	public void cleanup(ServerSession<?> serverSession) throws Exception {
		Collection<SessionCommand<?>> checkpointDelta = serverSession
				.getCheckpointDelta();
		Iterator<SessionCommand<?>> iterator = checkpointDelta.iterator();

		try {
			pService.beginTransaction();
			while (iterator.hasNext()) {
				SessionCommand<?> sessionCommand = iterator.next();
				Object targetObject = sessionCommand.getTargetObject();

				if (targetObject instanceof QueryDef) {
					queryStore.remove(serverSession.getId().hashCode()
							+ ((QueryDef) targetObject).hashCode());
				} else if (targetObject instanceof ServerSession) {
					sessionStore.remove(serverSession.getId());
				}
			}
			pService.commit();
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "Exception while removing session", e);
		}
	}

	@Override
	public ServerSession<?>[] recover() throws Exception {
		// NO RECOVERY FOR INMEMORY

		List<ServerSession> recoveredSessionList = new ArrayList();
		//
		// for (String sessionID : sessionStore.keySet()) {
		// ServerSession serverSession =
		// ServerSessionRegistry.INSTANCE.getServerSession(sessionID);
		// if(serverSession == null){
		// serverSession =
		// ServerSessionRegistry.INSTANCE.addOrCreateSession(sessionID,
		// sessionStore.get(sessionID).getSessionName());
		// recoveredSessionList.add(serverSession);
		// }
		//
		// for (String queryName : queryNames) {
		// QueryDef query = queryStore.get(sessionID.hashCode() +
		// queryName.hashCode());
		// if (query != null) {
		// serverSession.registerRecoveredQuery(query);
		// }
		// }
		// }
		//
		// return recoveredSessionList.toArray(new
		// ServerSession[recoveredSessionList.size()]);

		return recoveredSessionList.toArray(new ServerSession[0]);
	}

}

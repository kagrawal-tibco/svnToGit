package com.tibco.rta.service.persistence.db;

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
import org.xml.sax.InputSource;

import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import static com.tibco.rta.service.persistence.db.DBConstant.SESSION_QUERY_NAME;
import static com.tibco.rta.service.persistence.db.DBConstant.SESSION_RULE_NAME;
import static com.tibco.rta.service.persistence.db.DBConstant.SESSION_TABLE_NAME;
import static com.tibco.rta.service.persistence.db.DBConstant.SESSION_ID;
import static com.tibco.rta.service.persistence.db.DBConstant.SESSION_NAME;
import static com.tibco.rta.service.persistence.db.DBConstant.SESSION_QUERY_DETAIL;
import static com.tibco.rta.service.persistence.db.DBConstant.SESSION_RULE_DETAIL;
import static com.tibco.rta.service.persistence.db.DBConstant.SESSION_UID;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 15/4/13
 * Time: 2:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class SessionDBPersistenceService extends AbstractStartStopServiceImpl implements SessionPersistenceService {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_RUNTIME_SESSION.getCategory());

    private static final String SESSION_REGISTER_SQL = String.format("insert into %s (%s, %s, %s) values (?, ?, ?)", SESSION_TABLE_NAME, SESSION_UID, SESSION_ID, SESSION_NAME);

    private static final String QUERY_REGISTER_SQL = String.format("insert into %s (%s, %s, %s, %s, %s) values (?, ?, ?, ?, ?)", SESSION_TABLE_NAME, SESSION_UID, SESSION_ID, SESSION_NAME, SESSION_QUERY_NAME, SESSION_QUERY_DETAIL);

    private static final String RULE_REGISTER_SQL = String.format("insert into %s (%s, %s, %s, %s, %s) values (?, ?, ?, ?, ?)", SESSION_TABLE_NAME, SESSION_UID, SESSION_ID, SESSION_NAME, SESSION_RULE_NAME, SESSION_RULE_DETAIL);

    private static final String QUERY_DEREGISTER_SQL = String.format("delete from %s where %s = ? and %s = ? ", SESSION_TABLE_NAME, SESSION_ID, SESSION_QUERY_NAME);

    private static final String SESSION_CLEANUP_SQL = String.format("delete from %s where %s = ?", SESSION_TABLE_NAME, SESSION_ID);

    private static final String SESSION_RECOVERY_SQL = String.format("select * from %s", SESSION_TABLE_NAME);


    @Override
    public void init(Properties config) throws Exception {
        DatabaseSchemaManager databaseSchemaManager =
                ((DatabasePersistenceService) ServiceProviderManager.getInstance().getPersistenceService()).getSchemaManager();
        databaseSchemaManager.createSessionInfoTable();        
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
    public void cleanup(ServerSession<?> serverSession) throws Exception {
        Collection<SessionCommand<?>> checkpointDelta = serverSession.getCheckpointDelta();
        Iterator<SessionCommand<?>> iterator = checkpointDelta.iterator();
        DatabaseConnectionPool connectionPool = ((DatabasePersistenceService) ServiceProviderManager.getInstance().getPersistenceService()).getConnectionpool();
        Connection dbConnection = connectionPool.getSqlConnection();
        if (dbConnection == null) {
            throw new RuntimeException("Cannot get Connection from ThreadLocal.");
        }
        try {
            while (iterator.hasNext()) {
                SessionCommand<?> sessionCommand = iterator.next();
                Object targetObject = sessionCommand.getTargetObject();

                if (targetObject instanceof QueryDef) {
                    removeQuery(serverSession.getId(), (QueryDef) targetObject, dbConnection);
                } else if (targetObject instanceof ServerSession) {
                    removeSession(serverSession.getId(), dbConnection);
                }
            }
            dbConnection.commit();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Exception while removing session", e);
        } finally {
            connectionPool.releaseCurrentConnection();
        }
    }

    @Override
    public void persist(ServerSession<?> serverSession) throws Exception {
        Collection<SessionCommand<?>> checkpointDelta = serverSession.getCheckpointDelta();
        Iterator<SessionCommand<?>> iterator = checkpointDelta.iterator();
        DatabaseConnectionPool connectionPool = ((DatabasePersistenceService) ServiceProviderManager.getInstance().getPersistenceService()).getConnectionpool();
        Connection dbConnection = connectionPool.getSqlConnection();
        if (dbConnection == null) {
            throw new RuntimeException("Cannot get Connection from ThreadLocal.");
        }

        try {
            while (iterator.hasNext()) {
                SessionCommand<?> sessionCommand = iterator.next();
                Object targetObject = sessionCommand.getTargetObject();

                if (targetObject instanceof ServerSession<?>) {
                    persistVanillaSession(serverSession.getId(), serverSession.getSessionName(), dbConnection);
                } else if (targetObject instanceof QueryDef) {
                    persistQuery(serverSession.getId(), serverSession.getSessionName(), (QueryDef) targetObject, dbConnection);
                } else if (targetObject instanceof RuleDef) {
                    persistRule(serverSession.getId(), serverSession.getSessionName(), (RuleDef) targetObject, dbConnection);
                }
            }
            dbConnection.commit();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Exception while persisting session", e);
        } finally {
            connectionPool.releaseCurrentConnection();
        }
    }


    private void persistVanillaSession(String sessionId, String sessionName, Connection dbConnection) throws Exception {

        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Persisting session [%s]", sessionName);
        }

        PreparedStatement sessionRegisterPreparedStatement = dbConnection.prepareStatement(SESSION_REGISTER_SQL);
        
        int index = 1;
        sessionRegisterPreparedStatement.setString(index++, UUID.randomUUID().toString());
        sessionRegisterPreparedStatement.setString(index++, sessionId);
        sessionRegisterPreparedStatement.setString(index, sessionName);

        try {
            sessionRegisterPreparedStatement.execute();
        } finally {
            sessionRegisterPreparedStatement.close();
        }
    }


    private void persistQuery(String sessionId, String sessionName, QueryDef queryDef, Connection dbConnection) throws Exception {
        if (queryDef.getQueryType() == QueryType.SNAPSHOT) {
            //Do not persist
            return;
        }
        String serializedQuery = SerializationUtils.serializeQuery(queryDef);

        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Persisting query [%s]", serializedQuery);
        }

        PreparedStatement queryRegisterPreparedStatement = dbConnection.prepareStatement(QUERY_REGISTER_SQL);
        
        int index = 1;
        queryRegisterPreparedStatement.setString(index++, UUID.randomUUID().toString());
        queryRegisterPreparedStatement.setString(index++, sessionId);
        queryRegisterPreparedStatement.setString(index++, sessionName);
        queryRegisterPreparedStatement.setString(index++, queryDef.getName());
        queryRegisterPreparedStatement.setCharacterStream(index, new StringReader(serializedQuery), serializedQuery.length());

        try {
            queryRegisterPreparedStatement.execute();
        } finally {
            queryRegisterPreparedStatement.close();
        }
    }


    private void removeQuery(String sessionId, QueryDef queryDef, Connection dbConnection) throws Exception {
        String serializedQuery = SerializationUtils.serializeQuery(queryDef);
        
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Removing persisted query [%s]", serializedQuery);
        }

        PreparedStatement queryDeregisterPreparedStatement = dbConnection.prepareStatement(QUERY_DEREGISTER_SQL);

        queryDeregisterPreparedStatement.setString(1, sessionId);
        queryDeregisterPreparedStatement.setString(2, queryDef.getName());

        try {
            queryDeregisterPreparedStatement.execute();
        } finally {
            queryDeregisterPreparedStatement.close();
        }
    }


    private void persistRule(String sessionId, String sessionName, RuleDef ruleDef, Connection dbConnection) throws Exception {
        String serializedRule = SerializationUtils.serializeRule(ruleDef);

        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Persisting rule [%s]", serializedRule);
        }

        PreparedStatement rulePreparedStatement = dbConnection.prepareStatement(RULE_REGISTER_SQL);

        int index = 1;
        rulePreparedStatement.setString(index++, UUID.randomUUID().toString());
        rulePreparedStatement.setString(index++, sessionId);
        rulePreparedStatement.setString(index++, sessionName);
        rulePreparedStatement.setString(index++, ruleDef.getName());
        rulePreparedStatement.setCharacterStream(index, new StringReader(serializedRule), serializedRule.length());

        try {
            rulePreparedStatement.execute();
        } finally {
            rulePreparedStatement.close();
        }
    }


    private void removeSession(String sessionId, Connection dbConnection) throws Exception {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Cleaning session [%s]", sessionId);
        }

        PreparedStatement sessionRemovalPreparedStatement = dbConnection.prepareStatement(SESSION_CLEANUP_SQL);
        sessionRemovalPreparedStatement.setString(1, sessionId);

        try {
            sessionRemovalPreparedStatement.execute();
        } finally {
            sessionRemovalPreparedStatement.close();
        }
    }

    /**
     * @throws Exception
     */
    @Override
    public ServerSession<?>[] recover() throws Exception {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Recovering session info");
        }
        DatabaseConnectionPool connectionPool = ((DatabasePersistenceService) ServiceProviderManager.getInstance().getPersistenceService()).getConnectionpool();
        Connection dbConnection = connectionPool.getSqlConnection();
        try {
			if (dbConnection == null) {
			    throw new RuntimeException("Cannot get Connection from ThreadLocal.");
			}

			PreparedStatement sessionRecoveryPreparedStatement = dbConnection.prepareStatement(SESSION_RECOVERY_SQL);
			ResultSet sessionRecoverResultSet = null;

			try {
			    sessionRecoverResultSet = sessionRecoveryPreparedStatement.executeQuery();
			    ServerSession<?>[] sessions = recover(sessionRecoverResultSet);
			    
			    if (LOGGER.isEnabledFor(Level.INFO)) {
		            LOGGER.log(Level.INFO, "Recovering session info complete.");
		        }
			    return sessions;
			    
			} finally {
			    if (sessionRecoverResultSet != null) {
			        sessionRecoverResultSet.close();
			    }
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
			connectionPool.releaseCurrentConnection();
		}
    }

    private ServerSession<?>[] recover(ResultSet sessionRecoverResultSet) throws Exception {
        List<ServerSession<?>> serverSessionsRecovered = new ArrayList<ServerSession<?>>();

        while (sessionRecoverResultSet.next()) {
            String sessionId = sessionRecoverResultSet.getString(SESSION_ID);
            String sessionName = sessionRecoverResultSet.getString(SESSION_NAME);
            ServerSession<?> serverSession = ServerSessionRegistry.INSTANCE.getServerSession(sessionId);
            if (serverSession == null) {
                serverSession = ServerSessionRegistry.INSTANCE.addOrCreateSession(sessionId, sessionName, false);
                serverSessionsRecovered.add(serverSession);
            }
            
            Reader queryClob = sessionRecoverResultSet.getCharacterStream(SESSION_QUERY_DETAIL);
            if (queryClob != null) {
                InputSource queryStream = new InputSource(queryClob);
                //Deserialize
                QueryDef queryDef = SerializationUtils.deserializeQuery(queryStream);
                serverSession.registerRecoveredQuery(queryDef);
            }

            Reader ruleClob = sessionRecoverResultSet.getCharacterStream(SESSION_RULE_DETAIL);
            if (ruleClob != null) {
                InputSource ruleStream = new InputSource(ruleClob);
                //Deserialize
                RuleDef ruleDef = SerializationUtils.deserializeRule(ruleStream);
                serverSession.registerRecoveredRule(ruleDef);
            }
        }
        return serverSessionsRecovered.toArray(new ServerSession<?>[serverSessionsRecovered.size()]);
    }
}

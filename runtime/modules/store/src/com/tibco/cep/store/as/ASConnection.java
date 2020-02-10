/**
 * 
 */
package com.tibco.cep.store.as;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.store.Item;
import com.tibco.cep.store.StoreConnection;
import com.tibco.cep.store.StoreConnectionInfo;
import com.tibco.cep.store.StoreContainer;
import com.tibco.cep.store.StoreIterator;
import com.tibco.datagrid.BatchResult;
import com.tibco.datagrid.Connection;
import com.tibco.datagrid.DataGrid;
import com.tibco.datagrid.DataGridException;
import com.tibco.datagrid.ResultSet;
import com.tibco.datagrid.Row;
import com.tibco.datagrid.Session;
import com.tibco.datagrid.Statement;
import com.tibco.datagrid.Table;
import com.tibco.datagrid.TibDateTime;

/**
 * @author vpatil
 *
 */
public class ASConnection extends StoreConnection {
	private static final String SESSION_ID_PREFIX = "SESSION_ID_";
	
	private Connection connection;
	private ConcurrentHashMap<String, SessionHolder> sessionMap;
	private ConcurrentHashMap<String, Statement> statementMap;
	
	public ASConnection(StoreConnectionInfo dgProperties) {
		super(dgProperties);
		sessionMap = Optional.ofNullable(sessionMap).orElse(new ConcurrentHashMap<String, SessionHolder>());
		statementMap = Optional.ofNullable(statementMap).orElse(new ConcurrentHashMap<String, Statement>());
	}
	
	public Connection getConnection() {
		return this.connection;
	}
	
	private void addSession(Session session, boolean isTransacted) {
		sessionMap.put(getSessionId(), new SessionHolder(session, isTransacted));
	}
	
	private SessionHolder getSession() {
		if (sessionMap != null) {
			return sessionMap.get(getSessionId());
		}
		return null;
	}
	
	private void removeSession() {
		String sessionId = getSessionId();
		
		SessionHolder sessionHolder = sessionMap.remove(sessionId);
		if (sessionHolder != null) {
			Session session = sessionHolder.getSession();
			if (session != null) {
				try {
					closeSessionContainers(sessionId);
					closeAllStatements(sessionId);
					session.close();
					session = null;
				} catch (Exception exception) {
					throw new RuntimeException(exception);
				}
			}
			sessionHolder = null;
		} else {
			throw new RuntimeException("No valid Session available.");
		}
	}
	
	private String getSessionId() {
		return SESSION_ID_PREFIX + Thread.currentThread().getName();
	}
	
	@Override
	public void connect() throws Exception {
		if (connection == null) connection = DataGrid.connect(storeConnectionInfo.getUrl(), storeConnectionInfo.getName(), ((ASConnectionInfo)storeConnectionInfo).getConnectionProperties());
		if (connection != null) storeMetadata = new ASMetadata(connection.getGridMetadata());
	}
	
	@Override
	protected boolean hasContainerKeyPrefix(String containerKey) {
		return (containerKey != null && containerKey.contains(SESSION_ID_PREFIX));
	}
	
	@Override
	protected String getContainerKeyPrefix() {
		return getSessionId();
	}
	
	@Override
	public StoreContainer<? extends Item> createContainer(String containerName) throws Exception {
		StoreContainer<? extends Item> storeContainer = new ASContainer(containerName);
		
		Session session = null;
		SessionHolder sessionHolder = getSession();
		if (sessionHolder == null) {
			session = connection.createSession(getSessionProperties(false));
			addSession(session, false);
		} else {
			session = sessionHolder.getSession();
		}
		
		Table table = session.openTable(containerName, ((ASConnectionInfo)storeConnectionInfo).getConnectionProperties());
		((ASContainer) storeContainer).setTable(table);
		((ASContainer) storeContainer).setTableMetadata(((ASMetadata)this.storeMetadata).getTableMetadata(containerName));
		
		return storeContainer;
	}
	
	@Override
	public StoreIterator query(String query, Object[] queryParameters, Object queryOptions, String returnEntityPath)
			throws Exception {
		Session session = null;
		
		SessionHolder sessionHolder = getSession();
		if (sessionHolder == null) {
			session = connection.createSession(getSessionProperties(false));
			addSession(session, false);
		} else {
			session = sessionHolder.getSession();
		}
		
		Statement statement = null;
		ResultSet resultSet = null;
		boolean reuse = false;
		
		ASQueryOptions asQueryOptions = (ASQueryOptions)queryOptions;
		try {			
			Properties properties = null;
			String statementKey = null;
			
			if (asQueryOptions != null) {
				properties = asQueryOptions.getProperties();
				reuse = asQueryOptions.isReuse();
				if (reuse) {
					statementKey = getSessionId() + "_" + md5Hash(query);
					statement = statementMap.get(statementKey);
				}
			}
			
			if (statement == null) {
				statement = session.createStatement(query, properties);
				if (reuse) statementMap.put(statementKey, statement);
			}
			
			statement.clearParameters();
			if (queryParameters != null) setStatementParameters(statement, queryParameters);
			resultSet = statement.executeQuery(null);
		} catch(DataGridException dge) {
			if (statement != null && !reuse) statement.close();
			throw dge;
		}
		
		ASIterator datagridIterator = new ASIterator(resultSet, returnEntityPath, reuse?null:statement);
		String containerName = getContainerNameFromQuery(query);
		datagridIterator.setContainer(containerName, ((ASMetadata)this.storeMetadata).getTableMetadata(containerName));
		return datagridIterator;
	}
	
	@Override
	public void disconnect() throws Exception {
		if (connection != null) {
			((ASMetadata)this.storeMetadata).destroy();
			closeAllSessions();
			connection.close();
			connection = null;
		}
	}
	
	private void closeAllSessions() throws Exception {
		if (sessionMap != null) {
			for (String sessionKey: sessionMap.keySet()) {
				SessionHolder sessionHolder = sessionMap.remove(sessionKey);
				if (sessionHolder != null) {
					Session session = sessionHolder.getSession();
					if (session != null) { 
						closeSessionContainers(sessionKey);
						closeAllStatements(sessionKey);
						session.close();
						session = null;
					}
					sessionHolder = null;
				}
			}
			sessionMap.clear();
		}
	}
	
	private void closeAllStatements(String sessionKey) throws Exception {
		if (statementMap != null) {
			for (String stmtKey : statementMap.keySet()) {
				if (sessionKey != null && stmtKey.startsWith(sessionKey)) {
					Statement stmt = statementMap.remove(stmtKey);
					if (stmt != null) {
						stmt.close();
						stmt = null;
					}
				}
			}
		}
	}

	@Override
	public void enableTransactions() throws Exception {
		SessionHolder sessionHolder = getSession();
		if (sessionHolder == null || !sessionHolder.isTransacted()) createSession(true);
	}

	@Override
	public void disableTransactions() throws Exception {
		SessionHolder sessionHolder = getSession();
		if (sessionHolder == null || sessionHolder.isTransacted()) createSession(false);
	}
	
	private void createSession(boolean isTransacted) throws Exception {
		SessionHolder sessionHolder = getSession();
		if (sessionHolder != null) {
			removeSession();
		}
		Session session = connection.createSession(getSessionProperties(isTransacted));
		addSession(session, isTransacted);
	}

	@Override
	public void commit() throws Exception {
		SessionHolder sessionHolder = getSession();
		if (sessionHolder != null) {
			sessionHolder.getSession().commit();
		}
	}

	@Override
	public void rollback() throws Exception {
		SessionHolder sessionHolder = getSession();
		if (sessionHolder != null) {
			sessionHolder.getSession().rollback();
		}
	}
	
	@Override
	public boolean putAllItems(List<Item> putItems) throws Exception {
		List<Row> rows = new ArrayList<Row>();
		putItems.forEach(item -> rows.add(((ASItem)item).getRow()));
		
		SessionHolder sessionHolder = getSession();
		if (sessionHolder != null) {
			if (sessionHolder.isTransacted()) {
				throw new RuntimeException("Batch Operations cannot be performed when transactions are enabled.");
			}
			try (BatchResult batchResult = sessionHolder.getSession().putRows(rows.toArray(new Row[rows.size()]), null)) {
				return batchResult.allSucceeded();
			}
		}
		return false;
	}
	
	@Override
	public List<Item> getAllItems(List<Item> getItems) throws Exception {
		List<Row> rows = new ArrayList<Row>();
		getItems.forEach(item -> rows.add(((ASItem)item).getRow()));
		
		List<Item> resultItems = new ArrayList<Item>();
		
		SessionHolder sessionHolder = getSession();
		if (sessionHolder != null) {
			if (sessionHolder.isTransacted()) {
				throw new RuntimeException("Batch operations cannot be performed when transactions are enabled.");
			}
			try (BatchResult batchResult = sessionHolder.getSession().getRows(rows.toArray(new Row[rows.size()]), null)) {
				if (batchResult.allSucceeded()) {
					for (int i=0; i<batchResult.getSize(); i++) {
						Row row = batchResult.getRow(i);

						StoreContainer<? extends Item> storeContainer = getContainer(row.getTable().getName());
						if (storeContainer != null) {
							Item storeItem = storeContainer.createItem();
							((ASItem)storeItem).setRow(row.copy());
							resultItems.add(storeItem);
						}
					}
				}
			} 
		}
		return resultItems;
	}
	
	@Override
	public boolean deleteAllItems(List<Item> deleteItems) throws Exception {
		List<Row> rows = new ArrayList<Row>();
		deleteItems.forEach(item -> rows.add(((ASItem)item).getRow()));
		
		SessionHolder sessionHolder = getSession();
		if (sessionHolder != null) {
			if (sessionHolder.isTransacted()) {
				throw new RuntimeException("Batch Operations cannot be performed when transactions are enabled.");
			}
			try (BatchResult batchResult = sessionHolder.getSession().deleteRows(rows.toArray(new Row[rows.size()]), null)) {
				return batchResult.allSucceeded();
			}
		}
		return false;
		
	}
	
	@Override
	public long executeUpdate(String query) throws Exception {
		Session session = null;
		
		SessionHolder sessionHolder = getSession();
		if (sessionHolder == null) {
			session = connection.createSession(getSessionProperties(false));
			addSession(session, false);
		} else {
			session = sessionHolder.getSession();
		}
		return session.executeUpdate(query, null);
	}
	
	private Properties getSessionProperties(boolean isTransacted) {
		Properties sessionProperties = new Properties();
		sessionProperties.setProperty(Session.TIBDG_SESSION_PROPERTY_BOOLEAN_TRANSACTED, String.valueOf(isTransacted));
		return sessionProperties;
	}
	
	private String getContainerNameFromQuery(String query) {
		int endIndex = (query.indexOf("where") != -1) ? query.indexOf("where") : query.length();
		return query.toLowerCase().substring(query.indexOf("from") + "from".length(), endIndex).trim();
	}
	
	private void setStatementParameters(Statement statement, Object[] statementParameters) throws Exception {
		for (int i=0; i < statementParameters.length; i++) {
			Object parameter = statementParameters[i];
			if (parameter instanceof String) {
				statement.setString(i+1, (String)parameter);
			} else if (parameter instanceof Double || parameter instanceof Float) {
				Double doubleValue = (Double) ((parameter instanceof Float) ? ((Float) parameter).doubleValue() : parameter);
				statement.setDouble(i+1, doubleValue);
			} else if (parameter instanceof Boolean || parameter instanceof Integer || parameter instanceof Long) {
				Long longValue = null;
				if (parameter instanceof Boolean) {
					longValue = ((Boolean)parameter) ? 1L : 0L;
				} else {
					longValue = (Long) ((parameter instanceof Integer) ? ((Integer) parameter).longValue() : parameter);
				}
				statement.setLong(i+1, longValue);
			} else if (parameter instanceof Calendar) {
				TibDateTime tibDateTime = new TibDateTime();
				tibDateTime.setFromDate(((Calendar)parameter).getTime());
				statement.setDateTime(i+1, tibDateTime);
			} else if (parameter instanceof byte[]) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(parameter);
				oos.close();
				statement.setOpaque(i+1, baos.toByteArray());
			}
		}
	}
	
	private String md5Hash(String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte buf[] = string.getBytes();
            byte[] md5 = md.digest(buf);

            BigInteger number = new BigInteger(1, md5);

            return number.toString(16);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

	class SessionHolder {
		private Session session;
		private boolean isTransacted;
		
		public SessionHolder(Session session, boolean isTransacted) {
			super();
			this.session = session;
			this.isTransacted = isTransacted;
		}
		
		public Session getSession() {
			return session;
		}
		
		public boolean isTransacted() {
			return isTransacted;
		}
	}
}

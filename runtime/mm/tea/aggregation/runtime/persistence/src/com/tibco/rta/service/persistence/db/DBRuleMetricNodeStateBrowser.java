package com.tibco.rta.service.persistence.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.query.Browser;
import com.tibco.rta.runtime.model.rule.RuleMetricNodeState;

public class DBRuleMetricNodeStateBrowser implements Browser<RuleMetricNodeState> {
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(
			LoggerCategory.RTA_SERVICES_PERSISTENCE.getCategory());
	private DatabasePersistenceService pService;
	ResultSet rs;
	Statement st;
	RtaSchema schema;
	Connection connection;
	RuleMetricNodeState ruleNode;

	private String schemaName;
	private String cubeName;
	private String hierarchyName;

	public DBRuleMetricNodeStateBrowser(DatabasePersistenceService databasePersistenceService, String schemaName,
			String cubeName, String hierarchyName, long currentTimeMillis) {
		// TODO Auto-generated constructor stub
		schema = ModelRegistry.INSTANCE.getRegistryEntry(schemaName);
		this.pService = databasePersistenceService;
		this.schemaName = schemaName;
		this.cubeName = cubeName;
		this.hierarchyName = hierarchyName;

		String ruleTableName = pService.getSchemaManager().makeRuleMetricTableName(schemaName, cubeName, hierarchyName);

		StringBuilder query = new StringBuilder("SELECT * FROM " + ruleTableName + " WHERE "
				+ DBConstant.RULE_SCHEDULED_TIME_FIELD + " <= " + currentTimeMillis + " AND "
				+ DBConstant.RULE_SCHEDULED_TIME_FIELD + " > 0" + " ORDER BY " + DBConstant.RULE_SCHEDULED_TIME_FIELD);

		try {
			connection = pService.getConnectionpool().getSqlConnection();
			st = connection.createStatement();
			rs = st.executeQuery(query.toString());
		} catch (SQLException e) {
			LOGGER.log(Level.ERROR, "An error occurred, while fetching RuleMetrics ", e);
		} finally {
			pService.getConnectionpool().removeCurrentConnectionFromThreadLocal();
		}

	}

	@Override
	public boolean hasNext() {
		if (rs == null) {
			return false;
		}
		if (ruleNode == null) {
			ruleNode = pService.fetchRuleNodeFromRS(rs, schemaName, cubeName, hierarchyName);
			if (ruleNode == null) {
				// close result set and statement.
				pService.releaseResources(rs, st);
				rs = null;
				pService.getConnectionpool().releaseConnection(connection);
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	@Override
	public RuleMetricNodeState next() {
		// TODO Auto-generated method stub
		RuleMetricNodeState next = ruleNode;
		ruleNode = null;
		return next;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		pService.releaseResources(rs, st);
		rs = null;
		pService.getConnectionpool().releaseConnection(connection);
	}

}

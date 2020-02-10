package com.tibco.cep.studio.core.domain.importHandler.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;

import com.tibco.be.common.ConnectionPool;
import com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.domain.DomainEntry;
import com.tibco.cep.designtime.core.model.domain.DomainFactory;
import  com.tibco.be.jdbcstore.ssl.JdbcSSLConnectionInfo;
import com.tibco.cep.studio.core.domain.importHandler.AbstractDomainModelImportHandler;
import com.tibco.cep.studio.core.domain.importHandler.DomainConfiguration;
import com.tibco.cep.studio.core.domain.importHandler.DomainModelImportException;
import com.tibco.cep.studio.core.domain.importSource.DOMAIN_IMPORT_SOURCES;
import com.tibco.cep.studio.core.domain.importSource.impl.DomainModelDBConceptDataSource;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.validation.ValidationError;

public class DomainModelDBConceptImportHandler
		extends
		AbstractDomainModelImportHandler<Map<String, Object>, DomainModelDBConceptDataSource> {

	private final String SQL_SELECT_PROPERTY_DATA = "SELECT DISTINCT {0} FROM {1} WHERE {0} IS NOT NULL ORDER BY {0}";

	public DomainModelDBConceptImportHandler(
			List<ValidationError> validationErrors,
			DomainModelDBConceptDataSource domainImportDataSource) {
		super(domainImportDataSource, validationErrors);
		createDomainModel();
	}
	
	protected void createDomainModel() {
		if (domain == null) {
			domain = DomainFactory.eINSTANCE.createDomain();
		}
	}
	
	@Override
	public void importDomain(DomainConfiguration domainConfiguration)
			throws DomainModelImportException {
		getData(domainConfiguration);
		
		String projectDirectoryPath = domainConfiguration.getProjectDirectoryPath();
		String domainName = domainConfiguration.getDomainName();
		dataType = domainConfiguration.getDomainDataType();
		String domainFolderPath = domainConfiguration.getDomainFolderPath();
		String domainDescription = domainConfiguration.getDomainDescription();
		
		domain.setName(domainName);
		domain.setDescription(domainDescription);
		domain.setDataType(dataType);
		domain.setFolder(domainFolderPath);
		
		File rootProjectFile = new File(projectDirectoryPath);
		String projectName = rootProjectFile.getName();
		domain.setOwnerProjectName(projectName);
		
		PropertyMap extendedPropertyMap = ModelFactory.eINSTANCE.createPropertyMap();
		domain.setExtendedProperties(extendedPropertyMap);
		
		EList<Entity> plist = domain.getExtendedProperties().getProperties();
		plist.clear();
		SimpleProperty op = ModelFactory.eINSTANCE.createSimpleProperty();
		op.setName("JDBC_RESOURCE");
		op.setValue((String)this.domainImportDataSource.getSource().get("jdbcResource"));
		plist.add(op);
		
		op = ModelFactory.eINSTANCE.createSimpleProperty();
		op.setName("DOMAIN_RELOAD_DB_QUERY");
		op.setValue((String)this.domainImportDataSource.getSource().get("query"));
		plist.add(op);
		
		op = ModelFactory.eINSTANCE.createSimpleProperty();
		op.setName("DOMAIN_IMPORT_SOURCE");
		op.setValue(DOMAIN_IMPORT_SOURCES.DATABASE_CONCEPT.name());
		plist.add(op);
	}

	/**
	 * Read data from database
	 * 
	 * @return
	 */
	private void getData(DomainConfiguration config) {
		Map<String, Object> params = domainImportDataSource.getSource();

		List<DomainEntry> entries = domain.getEntries();
		String jdbcdriver = (String)params.get("jdbcDriver");
		String username = (String)params.get("jdbcUser");
		String password = (String)params.get("jdbcPassword");
		String url = (String)params.get("jdbcUrl");
		String columnName = (String)params.get("columnName");
		String colType = (String)params.get("columnType");
		String objectName = (String)params.get("objectName");
		String schemaName = (String)params.get("schemaName");
		
		JdbcSSLConnectionInfo sslConnectionInfo = (JdbcSSLConnectionInfo)params.get(JdbcSSLConnectionInfo.KEY_JDBC_SSL_CONNECTION_INFO);

		if (jdbcdriver.startsWith("oracle.jdbc.driver.OracleDriver"))
			jdbcdriver = "oracle.jdbc.OracleDriver";
		try {

			Class.forName(jdbcdriver);
			Connection conn;
			if (sslConnectionInfo == null) {
				conn = DriverManager.getConnection(url, username, password);
			}
			else {
				sslConnectionInfo.setUser(username);
				sslConnectionInfo.setPassword(password);
				sslConnectionInfo.loadSecurityProvider();
				conn = DriverManager.getConnection(url, sslConnectionInfo.getProperties());
			}
			ConnectionPool.unlockDDConnection(conn);

			String sqlQuery = MessageFormat.format(SQL_SELECT_PROPERTY_DATA,
					columnName,
					((schemaName != null && !schemaName.trim().isEmpty()) ? schemaName + "." : "") + objectName);
			
			this.domainImportDataSource.getSource().put("query", sqlQuery);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sqlQuery);
			
			DOMAIN_DATA_TYPES colDataType = DOMAIN_DATA_TYPES.get(colType);
			while (rs.next()) {
				String contents = DOMAIN_DATA_TYPES.DATE_TIME == colDataType
						? DomainModelDatabaseTableImportHandler.convertTimestamp(rs.getString(columnName))
						: rs.getString(columnName);
				DomainEntry e = CommonUtil.parseValuesToDomainEntry(contents, colDataType);
				e.setDescription("");
				entries.add(e);
			}
			conn.close();
		} catch (ClassNotFoundException e) {
			ValidationError err = new ValidationError(e, e.getMessage());
			err.setWarning(false);
			String errMsg = "Jdbc driver not found : " + e.getMessage();
			validationErrors.add(new ValidationError(e, errMsg));
		} catch (SQLException e) {
			ValidationError err = new ValidationError(e, e.getMessage());
			err.setWarning(false);
			validationErrors.add(new ValidationError(e, e.getMessage()));
		} catch (InstantiationException e) {
			ValidationError err = new ValidationError(e, e.getMessage());
			err.setWarning(false);
			validationErrors.add(new ValidationError(e, e.getMessage()));
		} catch (IllegalAccessException e) {
			ValidationError err = new ValidationError(e, e.getMessage());
			err.setWarning(false);
			validationErrors.add(new ValidationError(e, e.getMessage()));
		}
	}
}

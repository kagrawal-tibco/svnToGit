package com.tibco.cep.studio.core.domain.importHandler.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
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
import com.tibco.be.jdbcstore.ssl.JdbcSSLConnectionInfo;
import com.tibco.cep.studio.core.domain.importHandler.AbstractDomainModelImportHandler;
import com.tibco.cep.studio.core.domain.importHandler.DomainConfiguration;
import com.tibco.cep.studio.core.domain.importHandler.DomainModelImportException;
import com.tibco.cep.studio.core.domain.importHandler.IDomainModelImportHandler;
import com.tibco.cep.studio.core.domain.importSource.DOMAIN_IMPORT_SOURCES;
import com.tibco.cep.studio.core.domain.importSource.impl.DomainModelDatabaseTableDataSource;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.util.Messages;
import com.tibco.cep.studio.core.validation.ValidationError;

public class DomainModelDatabaseTableImportHandler
		extends
		AbstractDomainModelImportHandler<Map<String, Object>, DomainModelDatabaseTableDataSource>
		implements
		IDomainModelImportHandler<Map<String,Object>, DomainModelDatabaseTableDataSource> {

	
	private List<ValidationError> errors;
	private List<String> duplicateErrorList = new ArrayList<String>();
	private String rowDescription;
	Iterator<String> iterator1;
	int i;

	public DomainModelDatabaseTableImportHandler(List<ValidationError> validationErrors,
			DomainModelDatabaseTableDataSource domainImportDataSource
			) {
		super(domainImportDataSource, validationErrors);
		errors = validationErrors;
		rowDescription = "";
		
	}

	@Override
	public void importDomain(DomainConfiguration domainConfiguration)
			throws DomainModelImportException {
		String projectDirectoryPath = domainConfiguration
				.getProjectDirectoryPath();
		String domainName = domainConfiguration.getDomainName();
		dataType = domainConfiguration.getDomainDataType();
		String domainFolderPath = domainConfiguration.getDomainFolderPath();
		String domainDescription = domainConfiguration.getDomainDescription();
		File rootProjectFile = new File(projectDirectoryPath);
		String projectName = rootProjectFile.getName();
		try {
			i = 0;
			
			Map<String, Object> source = domainImportDataSource.getSource();
			String driver = (String)source.get(Messages.getString("import.domain.databse.table.driver"));
			String username = (String)source.get(Messages.getString("import.domain.databse.table.username"));
			String password = (String)source.get(Messages.getString("import.domain.databse.table.password"));
			String url = (String)source.get(Messages.getString("import.domain.databse.table.url"));
			String query = (String)source.get(Messages.getString("import.domain.databse.table.query"));
			JdbcSSLConnectionInfo sslConnectionInfo = (JdbcSSLConnectionInfo)source.get(JdbcSSLConnectionInfo.KEY_JDBC_SSL_CONNECTION_INFO);
			
			Driver driverClass = null;
			driverClass = (Driver) Class.forName(driver).newInstance();
			DriverManager.registerDriver(driverClass);
			
			Connection connection;
			if (sslConnectionInfo == null) {
				connection = DriverManager.getConnection(url, username, password);
			}
			else {
				sslConnectionInfo.setUser(username);
				sslConnectionInfo.setPassword(password);
				sslConnectionInfo.loadSecurityProvider();
				connection = DriverManager.getConnection(url, sslConnectionInfo.getProperties());
			}
			ConnectionPool.unlockDDConnection(connection);
			
			connection.setAutoCommit(false);
			Statement stmt;
			ResultSet rs;
			try {
				 stmt = connection.createStatement();
				 rs = stmt.executeQuery(query);
			}catch(SQLException sqle) {
				validationErrors.add(new ValidationError(domainName, "Error in SQL Statement"));
				return;
			}
		
			int i = 0;
			while(rs.next()) {
				ResultSetMetaData resultSetMetaData = rs.getMetaData();
				int columnCount = resultSetMetaData.getColumnCount();
				if(columnCount == 0) {
					throw new DomainModelImportException("Domain Value Required");
				}
				else{
					if(columnCount > 1) {
						Object description= rs.getObject(1);
						createDomainEntries(description.toString(), 0, i);
					}
					if(dataType.equals(DOMAIN_DATA_TYPES.DATE_TIME)) {
						Timestamp timestamp = rs.getTimestamp(1);
						if(timestamp != null) {
							createDomainEntries(convertTimestamp(timestamp.toString()), 1, i);
						}
					} else {
						Object value = rs.getObject(1);
						if(value != null) {
							createDomainEntries(value.toString(), 1, i);
						}
					}
					i++;
				}
			}
			if(domain != null) {
				domain.setName(domainName);
				domain.setDescription(domainDescription);
				domain.setDataType(dataType);
				domain.setFolder(domainFolderPath);
				domain.setOwnerProjectName(projectName);
				if(!duplicateErrorList.isEmpty()) {
					String message = "Duplicate entries in Domain Model:";
					for(String cellString:duplicateErrorList) {
						message = message + "\n" + cellString;
					}
					errors.add(new ValidationError(domain, message));
				}
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
				op.setValue(DOMAIN_IMPORT_SOURCES.DATABASE_TABLE.name());
				plist.add(op);
			}
		}  catch (Exception ioe) {
			throw new DomainModelImportException(ioe);
		}

	}

	public static String convertTimestamp(String string) {
		string = string.replace(' ', 'T');
		string = string.substring(0, string.indexOf('.'));
		return string;
	}

	private void createDomain() {
		while (iterator1.hasNext()) {
			String domainEntry = iterator1.next();
			createDomainEntries(domainEntry, i++, 0);
			createDomainEntries(domainEntry, i++, 1);
		}
	}

	private void createDomainEntries(String cellString, int column, int row) {
		createDomainModel();
		DomainEntry domainEntry = null;
		if (column == 0) {
			rowDescription = cellString;
		}
		if (column == 1) {
			domainEntry = 
				CommonUtil.parseValuesToDomainEntry(cellString, dataType);
			domainEntry.setDescription(rowDescription);
			//Reset description
			//rowDescription = null;			
			List<DomainEntry> entries = domain.getEntries();
			if (contains(entries, domainEntry)) {
				if(!duplicateErrorList.contains(cellString))
					duplicateErrorList.add(cellString);
				return;
			}
			entries.add(domainEntry);
		}
	}

}

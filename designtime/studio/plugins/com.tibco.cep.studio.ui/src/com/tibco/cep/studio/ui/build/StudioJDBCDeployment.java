package com.tibco.cep.studio.ui.build;

import java.io.File;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.be.common.ConnectionPool;
import com.tibco.be.jdbcstore.JdbcDeployment;
import com.tibco.be.jdbcstore.impl.DBHelper;
import com.tibco.be.util.BEProperties;
import com.tibco.cep.designtime.CommonOntologyAdapter;
import com.tibco.cep.designtime.CommonOntologyCache;
import com.tibco.cep.designtime.model.Ontology;
import  com.tibco.be.jdbcstore.ssl.JdbcSSLConnectionInfo;
import com.tibco.cep.repo.BEArchiveResource;
import com.tibco.cep.repo.DeployedProject;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.rt.AddonUtil;
import com.tibco.cep.studio.core.manager.GlobalVariablesMananger;

class StudioJDBCDeployment extends JdbcDeployment {

	private JDBCDeployJobManifest manifest;

	@SuppressWarnings("unused")
	private IProgressMonitor monitor;

	private Ontology ontology;

	private Connection connection;

	protected StudioJDBCDeployment(JDBCDeployJobManifest manifest, IProgressMonitor monitor, PrintStream outStream, PrintStream errStream) {
		super(outStream, errStream, manifest.buildArguments());
		this.manifest = manifest;
		this.monitor = monitor;
	}

	protected void run() throws Exception{
		try {
			createConnection();
			// Anand - We need to use com.tibco.cep.designtime.CommonOntologyAdapter.CommonOntologyAdapter(Map<AddOn, Ontology>)
			// else we get NPE in com.tibco.cep.bpmn.model.designtime.ontology.ProcessAdapter.cast(Class<T>)
			String name = manifest.getProject().getName();
			this.ontology = new CommonOntologyAdapter(AddonUtil.getAddOnOntologyAdapters(name));
			// we also need to set the ontology in CommonOntologyCache.INSTANCE
			CommonOntologyCache.INSTANCE.addOntology(name, this.ontology);
			this.deploySql();
		} catch (Exception e){
			e.printStackTrace(errStream);
		} finally {
			this.clearJDBCUtils();
			if (connection != null) {
				connection.close();
			}
		}
	}

	protected void createConnection() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		if (manifest.getDbURL() != null) {
			Class.forName(manifest.getDatabaseDriver()).newInstance();
			if (manifest.getSSLConnectionInfo() != null) {
				JdbcSSLConnectionInfo sslConnInfo = manifest.getSSLConnectionInfo();
				sslConnInfo.setUser(manifest.getDbUserName());
				sslConnInfo.setPassword(manifest.getDbPassword());
				sslConnInfo.loadSecurityProvider();
				connection = DriverManager.getConnection(manifest.getDbURL(), sslConnInfo.getProperties());
			}
			else {
				connection = DriverManager.getConnection(manifest.getDbURL(), manifest.getDbUserName(), manifest.getDbPassword());
			}
			ConnectionPool.unlockDDConnection(connection);
		}
	}

	@Override
	protected boolean checkFile(String url) {
		return true;
	}

	@Override
	protected BEProperties createBEProperties(Properties env) {
		String beHome = System.getProperty("BE_HOME");
		if (beHome != null) {
			File beHomeLoc = new File(beHome);
			if (beHomeLoc.exists() == true) {
				File beJDBCDeployTraLoc = new File(beHomeLoc,"/bin/be-jdbcdeploy.tra");
				if (beJDBCDeployTraLoc.exists() == true) {
					if (beJDBCDeployTraLoc.isFile() == true) {
						env.setProperty("be.bootstrap.property.file", beJDBCDeployTraLoc.getAbsolutePath());
						BEProperties beProps = super.createBEProperties(env);
						Properties javaPropsInTraMap = new Properties();
						for (Object key : beProps.keySet()) {
							String propertyName = String.valueOf(key);
							if(propertyName.startsWith("java.property.") == true) {
								javaPropsInTraMap.put(propertyName.substring(14), beProps.get(propertyName));
							}
						}
						javaPropsInTraMap.put("be.jdbc.schemamigration.populateObjectTable", String.valueOf(manifest.isPopulateObjectTable()));
						beProps.addNewProperties(javaPropsInTraMap);
						return beProps;
					}
					throw new RuntimeException("Invalid "+beJDBCDeployTraLoc);
				}
				throw new RuntimeException("Non existent "+beJDBCDeployTraLoc);
			}
			throw new RuntimeException("Invalid BE_HOME["+beHome+"]");
		}
		throw new RuntimeException("No BE_HOME specified");
	}

	@Override
	protected BEProperties createBSProperties(Properties env) {
		//we need to set this flag to prevent all the incoming properties from being set @ system level
		//if that happens, the next invocation will use the older output folder + file name prefix setting
		Properties editedEnv = new Properties();
		editedEnv.putAll(env);
		editedEnv.put("__update.system.property__", "false");
		return super.createBSProperties(editedEnv);
	}

	@Override
	protected DeployedProject getProject() throws Exception {
		return null;
	}

	@Override
	protected Collection<BEArchiveResource> getArchives(DeployedProject project) throws Exception {
		return Collections.emptyList();
	}

	@Override
	protected Ontology getOntology() throws Exception {
		return ontology;
	}

	//we need to override this method to prevent com.tibco.be.jdbcstore.JdbcDeployment from breaking
	//com.tibco.be.jdbcstore.JdbcDeployment supports global variables for TTL in events
	//but they are referred only under optimize flag == true.
	@Override
	protected String evaluateGlobalVariable(DeployedProject project, String globalVariable) {
		GlobalVariablesProvider provider = GlobalVariablesMananger.getInstance().getProvider(manifest.getProject().getName());
		GlobalVariableDescriptor variableDescriptor = provider.getVariable(globalVariable);
		if (variableDescriptor != null) {
			//TODO check in manifest.getCdd() for overrides
			return variableDescriptor.getValueAsString();
		}
		return globalVariable;
	}

	@Override
	protected Connection getConn() throws Exception {
		return connection;
	}

}

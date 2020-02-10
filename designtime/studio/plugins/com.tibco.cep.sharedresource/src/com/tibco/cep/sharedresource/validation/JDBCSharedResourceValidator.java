package com.tibco.cep.sharedresource.validation;

import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;

import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.sharedresource.jdbc.JdbcConfigModelMgr;
import com.tibco.cep.sharedresource.ssl.SslConfigJdbcModel;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.core.validation.ValidationContext;

/**
 * 
 * @author sasahoo
 *
 */
public class JDBCSharedResourceValidator extends SharedResourceValidator{

	private static String TYPE_JDBC = "JDBC";
	private static String TYPE_JNDI = "JNDI";
	private static String TYPE_XA = "XA";
	
	@Override
	public boolean canContinue() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.DefaultResourceValidator#validate(com.tibco.cep.studio.core.validation.ValidationContext)
	 */
	@Override
	public boolean validate(ValidationContext validationContext) {		
		IResource resource = validationContext.getResource();	
		if (resource == null) return true;
		super.validate(validationContext);
		Map<String, GlobalVariableDescriptor> glbVars = getGlobalVariableNameValues(resource.getProject().getName());
		Map<String, GlobalVariableDescriptor> glbVarsDesc = getGlobalVariableDescriptors(resource.getProject().getName());
		
		JdbcConfigModelMgr modelmgr = new JdbcConfigModelMgr(resource);
		modelmgr.parseModel();
		
		String connectionType = modelmgr.getStringValue("connectionType");
		if (connectionType.equalsIgnoreCase(TYPE_JDBC)) {
			String driver = modelmgr.getStringValue("driver");
			//validateStringField(resource, glbVars, driver, "jdbc.empty.driver", "jdbc.invalid.driver", IMarker.SEVERITY_ERROR);
			validateGvField(resource, glbVars, driver, "jdbc.empty.driver", "jdbc.invalid.driver", IMarker.SEVERITY_ERROR);
			
			String databaseURL = modelmgr.getStringValue("location");
			//validateStringField(resource, glbVars, databaseURL, "jdbc.empty.database.url", "jdbc.invalid.database.url", IMarker.SEVERITY_ERROR);
			validateGvField(resource, glbVars, databaseURL, "jdbc.empty.database.url", "jdbc.invalid.database.url", IMarker.SEVERITY_ERROR);
				
			String maxConnections = modelmgr.getStringValue("maxConnections");
			validateNumericField(resource, glbVars, maxConnections, "jdbc.empty.max.con", "jdbc.invalid.max.con", IMarker.SEVERITY_ERROR, true, true);

			String user = modelmgr.getStringValue("user");
			validateStringField(resource, glbVars, user, "jdbc.invalid.user", IMarker.SEVERITY_ERROR);
			String password = modelmgr.getStringValue("password");
			validateStringField(resource, glbVars, password, "jdbc.invalid.password", IMarker.SEVERITY_ERROR);
			
			String loginTimeout = modelmgr.getStringValue("loginTimeout");
			validateNumericField(resource, glbVars, loginTimeout, "jdbc.empty.login.timeout", "jdbc.invalid.login.timeout", IMarker.SEVERITY_ERROR, true, false);
			
		} else if (connectionType.equalsIgnoreCase(TYPE_JNDI)) {
			boolean useSharedJndiConfig = modelmgr.getBooleanValue("UseSharedJndiConfig");
			if(useSharedJndiConfig){
				String jndiSharedConfiguration = modelmgr.getStringValue("JndiSharedConfiguration");
				validateStringField(resource, glbVars, jndiSharedConfiguration, 
						"jms.con.empty.shared.jndi.config", "jms.con.invalid.shared.jndi.config", IMarker.SEVERITY_ERROR);
				if (!jndiSharedConfiguration.trim().equals("") && !GvUtil.isGlobalVar(jndiSharedConfiguration)) {
					IFile jndiFile = resource.getProject().getFile(jndiSharedConfiguration);
					if (!jndiFile.exists() || !jndiSharedConfiguration.endsWith(".sharedjndiconfig")) {
						reportProblem(resource, getMessageString("jms.con.invalid.shared.jndi.config", jndiSharedConfiguration) , IMarker.SEVERITY_ERROR);
					}
				}
			} else {
				String jndiContextUrl = modelmgr.getStringValue("ProviderUrl");
				validateStringField(resource, glbVars, jndiContextUrl, 
						"jms.con.empty.jndi.context.url", "jndi.context.invalid.url", IMarker.SEVERITY_ERROR);
			}
			String jndiDataSourceName = modelmgr.getStringValue("jndiDataSourceName");
			validateStringField(resource, glbVars, jndiDataSourceName, 
					"jdbc.jndi.empty.datasource.name", "jdbc.jndi.invalid.datasource.name", IMarker.SEVERITY_ERROR);
		} else if (connectionType.equalsIgnoreCase(TYPE_XA)) {
			String xaDsClass = modelmgr.getStringValue("xaDsClass");
			validateStringField(resource, glbVars, xaDsClass, 
					"jdbc.xaDsClass.empty.datasource.name", "jdbc.xaDsClass.invalid.datasource.name", IMarker.SEVERITY_ERROR);
			
			String maxConnections = modelmgr.getStringValue("maxConnections");
			validateNumericField(resource, glbVars, maxConnections, 
					"jdbc.empty.max.con", "jdbc.invalid.max.con", IMarker.SEVERITY_ERROR, true, true);
			
			String databaseURL = modelmgr.getStringValue("location");
			validateStringField(resource, glbVars, databaseURL, 
					"jdbc.empty.database.url", "jdbc.invalid.database.url", IMarker.SEVERITY_ERROR);
		}
		
		String useSsl = modelmgr.getStringValue("useSsl");
		useSsl = GvUtil.getGvDefinedValue(modelmgr.getProject(), useSsl);
		
		if("true".equalsIgnoreCase(useSsl)) {
			SslConfigJdbcModel sslConfigModel = modelmgr.getModel().getSslConfigJdbcModel();
			if (sslConfigModel != null) {
				
				reportTrustCertifactFolderProblem(sslConfigModel.getCert(), resource, glbVars, glbVarsDesc, "ssl.empty.trusted.certificates.folder", "ssl.invalid.trusted.certificates.folder");
				
				String clientAuth = GvUtil.getGvDefinedValue(resource.getProject(), sslConfigModel.getClientAuth());
				if (Boolean.parseBoolean(clientAuth)) 
					reportSSLProblem(resource, sslConfigModel.getIdentity(), "ssl.empty.identity", "ssl.invalid.identity", true);
				String verifyHost = GvUtil.getGvDefinedValue(resource.getProject(), sslConfigModel.getVerifyHostName());
				if (Boolean.parseBoolean(verifyHost)) {
					String host = sslConfigModel.getExpectedHostName();
					if (host != null && host.trim().equals("")) {
						reportProblem(resource, getMessageString("http.empty_host", host), IMarker.SEVERITY_ERROR);
					}
				}
					
			}
		}
		return true;
	}	
}
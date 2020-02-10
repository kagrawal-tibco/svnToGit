package com.tibco.cep.sharedresource.validation;

import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;

import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.sharedresource.jms.JmsConModelMgr;
import com.tibco.cep.sharedresource.ssl.SslConfigJmsModel;
import com.tibco.cep.sharedresource.ui.util.Messages;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.core.validation.ValidationContext;

/**
 * 
 * @author sasahoo
 *
 */
public class JMSConSharedResourceValidator extends SharedResourceValidator{

	@Override
	public boolean canContinue() {
		return true;
	}

	@Override
	public boolean validate(ValidationContext validationContext) {		
		IResource resource = validationContext.getResource();	
		if (resource == null) return true;
		super.validate(validationContext);
		
		Map<String, GlobalVariableDescriptor> glbVars = getGlobalVariableNameValues(resource.getProject().getName());
		Map<String, GlobalVariableDescriptor> glbVarsDesc = getGlobalVariableDescriptors(resource.getProject().getName());
		
		JmsConModelMgr modelmgr = new JmsConModelMgr(resource);
		modelmgr.parseModel();
		
		boolean useJNDI = modelmgr.getBooleanValue("UseJNDI");
		boolean useSharedJndiConfig = modelmgr.getBooleanValue("UseSharedJndiConfig");
		if (useJNDI){
			if (useSharedJndiConfig) {
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
				String jndiContextFactory = modelmgr.getStringValue("NamingInitialContextFactory");
				validateStringField(resource, glbVars, jndiContextFactory, 
						"jms.con.empty.jndi.context.factory", "jndi.context.invalid.factory", IMarker.SEVERITY_ERROR);

				String jndiContextUrl = modelmgr.getStringValue("NamingURL");
				validateStringField(resource, glbVars, jndiContextUrl, 
						"jms.con.empty.jndi.context.url", "jndi.context.invalid.url", IMarker.SEVERITY_ERROR);
				
				String jndiPassword = modelmgr.getStringValue("NamingCredential");
				validateStringField(resource, glbVars, jndiPassword, 
						"jms.con.empty.jndi.password", "jndi.context.invalid.password", IMarker.SEVERITY_WARNING);
			}
		}else{
			String providerUrl = modelmgr.getStringValue("ProviderURL");
			validateStringField(resource, glbVars, providerUrl,
					"jms.con.empty.providerurl", "jms.con.invalid.providerurl", IMarker.SEVERITY_ERROR);
		}
		
		String topicFactoryName = modelmgr.getStringValue("TopicFactoryName");
		String queueFactoryName = modelmgr.getStringValue("QueueFactoryName");
		if (topicFactoryName.trim().equals("") && queueFactoryName.trim().equals("")) {	//Report problem just once
			reportProblem(resource,  Messages.getString("jms.con.empty.queue.topic.factory.name", ""), IMarker.SEVERITY_ERROR);
			//validateStringField(resource, glbVars, topicFactoryName, "jms.con.empty.queue.topic.factory.name", "jms.con.invalid.topic.factory.name", IMarker.SEVERITY_ERROR);
			//validateStringField(resource, glbVars, queueFactoryName, "jms.con.empty.queue.topic.factory.name", "jms.con.invalid.queue.factory.name", IMarker.SEVERITY_ERROR);
		}
		
		boolean useSsl = modelmgr.getBooleanValue("useSsl");
		if(useSsl){
			SslConfigJmsModel sslConfigModel = modelmgr.getModel().getSslConfigJmsModel();
			if (sslConfigModel != null) {
				reportTrustCertifactFolderProblem(sslConfigModel.getCert(), resource, glbVars, glbVarsDesc, "ssl.empty.trusted.certificates.folder", "ssl.invalid.trusted.certificates.folder");
				reportSSLProblem(resource, sslConfigModel.getIdentity(), "ssl.empty.identity", "ssl.invalid.identity", false);
			}
		}
		return true;
	}	
}
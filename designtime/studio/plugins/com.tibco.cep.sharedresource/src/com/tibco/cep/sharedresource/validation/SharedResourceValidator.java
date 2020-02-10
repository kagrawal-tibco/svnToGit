package com.tibco.cep.sharedresource.validation;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;

import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.sharedresource.ui.util.Messages;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.core.validation.DefaultSharedResourceValidator;

public class SharedResourceValidator extends DefaultSharedResourceValidator {

	/**
	 * @param resource
	 * @param val
	 * @param emptyMsg
	 * @param invalidMsg
	 * @param isCert
	 */
	protected void reportSSLProblem(IResource resource, String val, String emptyMsg, String invalidMsg, boolean isCert) {
		if (val != null) {
			if (val.trim().equals("")) {
				if (isCert) {
					reportProblem(resource, getMessageString(emptyMsg),
							IMarker.SEVERITY_ERROR);
				}
			} else {
				String value = GvUtil.getGvDefinedValue(resource.getProject(), val); 
				if (value == null) {
					reportProblem(resource, getMessageString(invalidMsg, val), IMarker.SEVERITY_ERROR);
				} else { 
					if (isCert && value.endsWith("/.folder")) {
						value = value.substring(0, value.indexOf("/.folder"));
					}
					if (isCert || (!isCert && !value.equals("")) ) {
						if (value.startsWith("file:///")) {
							try {
								URI uri = new URI(value);
								String path = uri.getPath();
								if (!new File(path).exists())
									reportProblem(resource, getMessageString(invalidMsg, val), IMarker.SEVERITY_ERROR);
							} catch (URISyntaxException e) {
								e.printStackTrace();
							}
						} else if (!resource.getProject().exists(Path.fromOSString(value))) {
							reportProblem(resource, getMessageString(invalidMsg, val), IMarker.SEVERITY_ERROR);
						}
					}
				}
			}
		} else {
			reportProblem(resource, getMessageString(emptyMsg),
					IMarker.SEVERITY_ERROR);
		}
	}
	
	/**
	 * 
	 * @param trustCertificatesFolder
	 * @param resource
	 * @param glbVars
	 * @param glbVarsDesc
	 * @param emptyTrustCertificatesFolderKey
	 * @param invalidTrustCertifactesFolderKey
	 */
	protected void reportTrustCertifactFolderProblem(String trustCertificatesFolder, IResource resource, Map<String, GlobalVariableDescriptor> glbVars, Map<String, GlobalVariableDescriptor> glbVarsDesc, String emptyTrustCertificatesFolderKey, String invalidTrustCertifactesFolderKey) {
		boolean valid = false;
		String val = trustCertificatesFolder;

		// case for reference folder within BE project
		if (val.endsWith(".folder")) {
			reportSSLProblem(resource, trustCertificatesFolder, emptyTrustCertificatesFolderKey, invalidTrustCertifactesFolderKey, true);
		} else {
			// case of Global variable
			if (GvUtil.isGlobalVar(val)) {
				valid = validateStringField(resource, glbVars, val, emptyTrustCertificatesFolderKey, invalidTrustCertifactesFolderKey, IMarker.SEVERITY_ERROR);
				if (valid) {
					String variable = GvUtil.getGvVariable(val);
					GlobalVariableDescriptor desc = glbVarsDesc.get(variable);
					if (desc==null || !desc.isDeploymentSettable()) {		
						reportProblem(resource, getMessageString(invalidTrustCertifactesFolderKey, val), IMarker.SEVERITY_ERROR);
					}
				}
			} else {
				// case for a simple folder location entered manually
				try {
					val = val.replace("\\", "/");
					URI uri = new URI("file:///" + val);
					String path = uri.getPath();
					if (!new File(path).exists()) {
						reportProblem(resource, getMessageString(invalidTrustCertifactesFolderKey, val), IMarker.SEVERITY_ERROR);
					}
				} catch(URISyntaxException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
	@Override
	public String getMessageString(String key, Object... arguments) {
		return Messages.getString(key, arguments);
	}
}

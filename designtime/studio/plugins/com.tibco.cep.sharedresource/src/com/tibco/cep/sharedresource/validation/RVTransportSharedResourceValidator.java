package com.tibco.cep.sharedresource.validation;

import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;

import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.sharedresource.rvtransport.RvTransportModelMgr;
import com.tibco.cep.sharedresource.ssl.SslConfigRvModel;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.core.validation.ValidationContext;

/**
 * 
 * @author sasahoo
 *
 */
public class RVTransportSharedResourceValidator extends SharedResourceValidator{

	private static String TYPE_CERTIFIED = "Certified";
	private static String TYPE_CERTIFIEDQ = "CertifiedQ";
	
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
		if (resource == null) 
			return true;
		super.validate(validationContext);
		
		Map<String, GlobalVariableDescriptor> glbVars = getGlobalVariableNameValues(resource.getProject().getName());
		Map<String, GlobalVariableDescriptor> glbVarsDesc = getGlobalVariableDescriptors(resource.getProject().getName());
		
		RvTransportModelMgr modelmgr = new RvTransportModelMgr(resource);
		modelmgr.parseModel();

		String rvType = modelmgr.getStringValue("showExpertSettings");
		if (rvType.equalsIgnoreCase(TYPE_CERTIFIED)) {
			String cmName = modelmgr.getStringValue("cmName");
			validateStringField(resource, glbVars, cmName, "rvtransport.empty.cmName", "rvtransport.invalid.cmName", IMarker.SEVERITY_ERROR);
			String syncLedger = modelmgr.getStringValue("syncLedger");
			validateStringField(resource, glbVars, syncLedger, "rvtransport.empty.syncLedger", "rvtransport.invalid.syncLedger", IMarker.SEVERITY_WARNING);
			String timeOut = modelmgr.getStringValue("operationTimeout");
			validateNumericField(resource, glbVars, timeOut, "rvtransport.empty.timeOut", "rvtransport.invalid.timeOut", IMarker.SEVERITY_ERROR, true, false);
		} else if (rvType.equalsIgnoreCase(TYPE_CERTIFIEDQ)) {
			String cmqName = modelmgr.getStringValue("cmqName");
			validateStringField(resource, glbVars, cmqName, "rvtransport.empty.cmQName", "rvtransport.invalid.cmQName", IMarker.SEVERITY_ERROR);
			String workerWeight = modelmgr.getStringValue("workerWeight");
			validateNumericField(resource, glbVars, workerWeight, "rvtransport.invalid.workerWeight", IMarker.SEVERITY_ERROR, true, false);
			String workerTasks = modelmgr.getStringValue("workerTasks");
			validateNumericField(resource, glbVars, workerTasks, "rvtransport.invalid.workerTasks", IMarker.SEVERITY_ERROR, true, false);
			String schedulerWeight = modelmgr.getStringValue("schedulerWeight");
			validateNumericField(resource, glbVars, schedulerWeight,1,65535,"rvtransport.invalid.schedulerWeight", IMarker.SEVERITY_ERROR, true, false);
			String schedulerheartBeat = modelmgr.getStringValue("scheduleHeartbeat");
			validateFloatField(resource, glbVars, schedulerheartBeat, "rvtransport.invalid.heartBeat", IMarker.SEVERITY_ERROR, true, false);
			String schedulerActivation = modelmgr.getStringValue("scheduleActivation");
			validateFloatField(resource, glbVars, schedulerActivation, "rvtransport.invalid.activation", IMarker.SEVERITY_ERROR, true, false);
		}
		boolean useSsl = modelmgr.getBooleanValue("useSsl");
		if(useSsl){
			SslConfigRvModel sslConfigModel = modelmgr.getModel().getSslConfigRvModel();
			if(sslConfigModel != null){
				if(sslConfigModel.getCert().equalsIgnoreCase(""))
					reportProblem(resource, getMessageString("ssl.empty.daemon.certificate", sslConfigModel.getCert()), IMarker.SEVERITY_ERROR);
				else
					reportTrustCertifactFolderProblem(sslConfigModel.getCert(), resource, glbVars, glbVarsDesc, "ssl.empty.daemon.certificate", "ssl.invalid.daemon.certificate");
				reportSSLProblem(resource, sslConfigModel.getIdentity(), "ssl.empty.identity", "ssl.invalid.identity", true);
			}
		}
		return true;
	}
	
	
	private void validateNumericField(IResource resource,
			Map<String, GlobalVariableDescriptor> glbVars, String val, int min, int max,
			String invalidMsg, int severity, boolean isPositive,
			boolean isNatural) {
		boolean valid = validateNumericField(val, glbVars, isPositive,
				isNatural);
			if(GvUtil.isGlobalVar(val)){
				if(!valid){
					reportProblem(resource, getMessageString(invalidMsg, val), severity);
					return;
				}
			}else if(!val.trim().equals("")){
				if(valid){
					try{
					if(Integer.parseInt(val)<min||Integer.parseInt(val)>max)
						reportProblem(resource, getMessageString(invalidMsg, val), severity);	
					}catch(Exception e){
						reportProblem(resource, getMessageString(invalidMsg, val), severity);
					}
				}else{
					reportProblem(resource, getMessageString(invalidMsg, val), severity);
				}
			}
		}
		

}
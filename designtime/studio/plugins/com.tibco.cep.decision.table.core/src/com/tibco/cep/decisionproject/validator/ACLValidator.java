package com.tibco.cep.decisionproject.validator;

import java.util.Collection;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decisionproject.acl.ValidationError;
import com.tibco.cep.decisionproject.acl.event.AccessControlEvent;
import com.tibco.cep.decisionproject.acl.event.AccessControlEventListener;
import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.decisionproject.persistence.impl.DecisionProjectLoader;
import com.tibco.cep.decisionprojectmodel.DecisionProject;
import com.tibco.cep.security.tokens.AuthToken;
import com.tibco.cep.security.tokens.Authz;
import com.tibco.cep.security.util.AuthTokenUtils;

public class ACLValidator extends DPResourceValidator {
	
	private AccessControlEventListener<AccessControlEvent, Table> aclEventListener;
	
	public ACLValidator(final AccessControlEventListener<AccessControlEvent, Table> aclEventListener) {
		this.aclEventListener = aclEventListener;
	}

	@Override
	public void validate(DecisionProject decisionProject,
			Collection<ValidationError> vldErrorCollection)
			throws ValidationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validate(Implementation vrfImpl,
			Collection<ValidationError> vldErrorCollection)
			throws ValidationException {
		// TODO Auto-generated method stub
		// logic for ACL validation
		// get ACL Manager
		try {
			
//			AccessController accessController = null;
			// run ACL check on whole implementations 
			// get User role list
			AuthToken authToken = AuthTokenUtils.getToken();
			if (authToken == null){
//				LOGGER.logDebug(CLASS_NAME, "Auth Token :: null");
//				throw new ValidationException("Auth Token Null::");

			}
			Authz authz = authToken.getAuthz();
			if (authz == null || authz.getRoles().isEmpty()){
//				LOGGER.logDebug(CLASS_NAME, "No User Role present::");
//				throw new ValidationException("No User Role present::");
			}
			// load acl file from file system 
			String baseDir = DecisionProjectLoader.getInstance().getBaseDirectory();
			if (baseDir == null){
//					LOGGER.logDebug(CLASS_NAME, "DP base Directory null");
				throw new IllegalArgumentException("DP base directory can not be null::");
			}
//				String aclFilePath = baseDir + "policy.access";			
			//accessController = new AccessControllerImpl(aclFilePath,true);
//				accessController = new AccessControllerImpl2(authz.getRoles());

			
			aclEventListener.handleEvents(vldErrorCollection);
		
//		} catch (ACLCheckException e) {
//			// TODO Auto-generated catch block
////			LOGGER.logError(CLASS_NAME, "ACL Check Exception", e);
//			throw new ValidationException(e.getMessage(),e);
		} catch (Exception e) {
			//e.printStackTrace();
			// TODO Auto-generated catch block
//			LOGGER.logError(CLASS_NAME, "ACL Check Exception", e);
			throw new ValidationException(e.getMessage(),e);
		}
	}
}

package com.tibco.cep.decisionproject.validator;
import java.util.Collection;

import com.tibco.cep.decisionproject.acl.ValidationError;
import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.decisionprojectmodel.DecisionProject;

public abstract class DPResourceValidator implements IDPResourceValidator {
	//private IDPResourceValidator nextValidatorChain ;

	public abstract void validate(DecisionProject decisionProject,
			Collection<ValidationError> vldErrorCollection) throws ValidationException;


	public abstract void validate(Implementation vrfImpl,
			Collection<ValidationError> vldErrorCollection)throws ValidationException ;


}

package com.tibco.cep.decisionproject.validator;
import java.util.Collection;

import com.tibco.cep.decisionproject.acl.ValidationError;
import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.decisionprojectmodel.DecisionProject;


public interface IDPResourceValidator {
	
	public void validate(DecisionProject decisionProject,Collection<ValidationError> vldErrorCollection) throws ValidationException;
	
	public void validate(Implementation vrfImpl,Collection<ValidationError> vldErrorCollection) throws ValidationException;

}

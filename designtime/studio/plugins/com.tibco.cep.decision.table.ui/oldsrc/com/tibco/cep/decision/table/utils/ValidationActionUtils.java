/**
 * 
 */
package com.tibco.cep.decision.table.utils;

import java.util.ArrayList;
import java.util.Collection;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decisionproject.acl.ValidationError;
import com.tibco.cep.decisionproject.acl.event.AccessControlEvent;
import com.tibco.cep.decisionproject.acl.event.AccessControlEventListener;
import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.decisionproject.validator.ACLValidator;
import com.tibco.cep.decisionproject.validator.IDPResourceValidator;
import com.tibco.cep.decisionproject.validator.ValidationException;
import com.tibco.cep.studio.util.logger.problems.ProblemEventManager;

/**
 * @author aathalye
 *
 */
public class ValidationActionUtils {
	
	private static final String ACL_VALIDATION_ERRORS = "Authorization";
	
	/**
	 * Runs Access Control Validation checks on an {@code Implementation}.
	 * @param implementation: The <tt>Implementation</tt> to run checks against
	 * @param eventListener: The {@code AccessControlEventListener} associated with
	 *                       the editor of this table
	 * @return a collection of validation errors if any, else empty collection
	 * @throws ValidationException
	 */
	public static Collection<ValidationError> validateAccess(Implementation implementation,
			                                                 AccessControlEventListener<AccessControlEvent, Table> eventListener)
			                                                 throws ValidationException {
		Collection<ValidationError> validationErrors = 
							new ArrayList<ValidationError>();
		// create instances of validators according to the need
		// create instanceof ACL validator
		if (!implementation.isModified()) {
			return validationErrors;
		}
		IDPResourceValidator aclValidator = new ACLValidator(eventListener);
		// create empty validation error collection
		final Collection<ValidationError> vldErrorCollection = new ArrayList<ValidationError>();
		aclValidator.validate(implementation, vldErrorCollection);
		return vldErrorCollection;
	}
	
	/**
	 * Sends the list of errors to the <b><i>Problems View</i></b>
	 * @param errors
	 */
	public static void postErrors(final Collection<ValidationError> errors) {
//		ProblemEventManager pem = ProblemEventManager.getInstance();
		clearErrors();
//		for (ValidationError ve : errors) {
//			String problemCode = ACL_VALIDATION_ERRORS;// new
//														// Integer(ve.getErrorCode()).toString();
//			/*pem.postProblem(problemCode, ve.getErrorMessage(), ve
//					.getErrorSource(), ProblemEvent.ERROR);*/
//		}
	}
	
	public static void clearErrors() {
		ProblemEventManager pem = ProblemEventManager.getInstance();
		pem.clearProblems(ACL_VALIDATION_ERRORS);
	}
}

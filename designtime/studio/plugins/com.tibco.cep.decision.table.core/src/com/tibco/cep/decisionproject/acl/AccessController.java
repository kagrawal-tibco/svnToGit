package com.tibco.cep.decisionproject.acl;
/**
 * 
 * @author rmishra
 * 
 */
import java.util.Collection;
import java.util.List;

import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.decisionprojectmodel.DecisionProject;
import com.tibco.cep.decisionprojectmodel.DomainModel;
import com.tibco.cep.security.tokens.Role;

public interface AccessController {

	/**
	 * 
	 * @param decisionProject
	 * @param action
	 * @param userRole
	 * @param vldErrorCollection : it collects the Information about resource which does not have requested permission 
	 * @throws ACLCheckException
	 */
	public void checkACL(final DecisionProject decisionProject,
			final String action,final List<Role> userRole,Collection<ValidationError> vldErrorCollection) throws ACLCheckException;

	/**
	 * 
	 * @param domainModel
	 * @param action
	 * @param userRole
	 * @param vldErrorCollection :: it collects the Information about resource which does not have requested permission 
	 * @throws ACLCheckException
	 */
	public void checkACL(final DomainModel domainModel,
			final String action,final List<Role> userRole,Collection<ValidationError> vldErrorCollection) throws ACLCheckException;
	/**
	 * 
	 * @param ontologyResource
	 * @param action
	 * @param userRole
	 * @param vldErrorCollection :: it collects the Information about resource which does not have requested permission 
	 * @throws ACLCheckException
	 */
	public void checkACL(final AbstractResource ontologyResource,
			final String action,final List<Role> userRole,Collection<ValidationError> vldErrorCollection) throws ACLCheckException;

	/**
	 * 
	 * @param implementation
	 * @param action
	 * @param userRole
	 * @param vldErrorCollection : : it collects the Information about resource which does not have requested permission 
	 * @throws ACLCheckException
	 */


	public void checkACLForCustomConditions(
			final Implementation implementation, final String action,final List<Role> userRole,Collection<ValidationError> vldErrorCollection)
			throws ACLCheckException;
	
	/**
	 * 
	 * @param implementation
	 * @param action
	 * @param userRole
	 * @param vldErrorCollection : : it collects the Information about resource which does not have requested permission 
	 * @throws ACLCheckException
	 */


	public void checkACLForCustomActions(final Implementation implementation,
			final String action,final List<Role> userRole,Collection<ValidationError> vldErrorCollection) throws ACLCheckException;


	/**
	 * 
	 * @param implementation
	 * @param ruleId
	 * @param action
	 * @param userRole
	 * @param vldErrorCollection : : it collects the Information about resource which does not have requested permission 
	 * @throws ACLCheckException
	 */


	public void checkACL(Implementation implementation, String ruleId,
			String action, List<Role> userRole, Collection<ValidationError> vldErrorCollection) throws ACLCheckException;

	
	/**
	 * Implementation check comprehensive. Does not use any specific action
	 * @param implementation
	 * @param ruleId
	 * @param userRole
	 * @param vldErrorCollection
	 * @throws ACLCheckException
	 */
	public void checkACL(Implementation implementation, 
			List<Role> userRole, Collection<ValidationError> vldErrorCollection) throws ACLCheckException;
	
	/**
	 * 
	 * Does not use any specific action.
	 * @param impl: The Table this trv belongs to
	 * @param trv: a single cell
	 * @param localErrors: Collection of errors to fill
	 * @throws ACLCheckException
	 */
	public <I extends Implementation> void checkACL(I impl,
			                                        AccessControlCellData acd,
			                                        Collection<ValidationError> localErrors);

}

/**
 * 
 */
package com.tibco.cep.decisionproject.acl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tibco.be.parser.RuleError;
import com.tibco.be.parser.tree.NodeVisitor;
import com.tibco.be.parser.tree.RootNode;
import com.tibco.cep.decision.table.language.DTCellCompilationContext;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.decisionproject.persistence.impl.DecisionProjectLoader;
import com.tibco.cep.decisionproject.util.DTDomainUtil;
import com.tibco.cep.decisionprojectmodel.DecisionProject;
import com.tibco.cep.decisionprojectmodel.DomainModel;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.security.tokens.Role;

/**
 * @author aathalye
 * 
 */
public class AccessControllerImpl2 implements AccessController {

	
	
	private Ontology ontology;//The BE ontology
	
	private AuthzCompiler comp = null;
	
	private List<Role> roles;
	
	public AccessControllerImpl2(final Ontology ontology, 
			                     List<Role> roles) {
		this.ontology = ontology;
		this.roles = roles;
	}
	
	public AccessControllerImpl2(List<Role> roles) {
		this.roles = roles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.decisionproject.acl.AccessController#checkACL(com.tibco.cep.decisionprojectmodel.DecisionProject,
	 *      java.lang.String, java.util.List, java.util.Collection)
	 */
	public void checkACL(DecisionProject decisionProject, String action,
			List<Role> userRole, Collection<ValidationError> vldErrorCollection)
			throws ACLCheckException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.decisionproject.acl.AccessController#checkACL(com.tibco.cep.decisionprojectmodel.DomainModel,
	 *      java.lang.String, java.util.List, java.util.Collection)
	 */
	public void checkACL(DomainModel domainModel, String action,
			List<Role> userRole, Collection<ValidationError> vldErrorCollection)
			throws ACLCheckException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.decisionproject.acl.AccessController#checkACL(com.tibco.cep.decisionproject.ontology.AbstractResource,
	 *      java.lang.String, java.util.List, java.util.Collection)
	 */
	public void checkACL(AbstractResource ontologyResource, String action,
			List<Role> userRole, Collection<ValidationError> vldErrorCollection)
			throws ACLCheckException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.decisionproject.acl.AccessController#checkACL(com.tibco.cep.decisionproject.ontology.Implementation,
	 *      java.lang.String, java.lang.String, java.util.List,
	 *      java.util.Collection)
	 */
	public void checkACL(Implementation implementation, List<Role> userRole,
			Collection<ValidationError> vldErrorCollection)
			throws ACLCheckException {
		if (implementation instanceof Table) {
			Table table = (Table) implementation;
			TableRuleSet decisionTable = table.getDecisionTable();
			checkACL(decisionTable, userRole, vldErrorCollection);
			TableRuleSet exceptionTable = table.getExceptionTable();
			checkACL(exceptionTable, userRole, vldErrorCollection);
		}
	}
	
	private void checkACL(TableRuleSet contentsTable, List<Role> userRole, 
			Collection<ValidationError> vldErrorCollection) {
		if (contentsTable != null) {
			// get all conditions actions and see if user has proper access
			// to all the properties
			List<TableRule> tableruleList = contentsTable.getRule();
			NodeVisitor visitor = new AuthorizableNodeVisitor(
					vldErrorCollection, userRole);
			Handler handler = new Handler(visitor);
			for (TableRule tableRule : tableruleList) {
				List<TableRuleVariable> conditionList = tableRule
						.getCondition();
				handler.handleImplParts(conditionList, null);
				List<TableRuleVariable> actionList = tableRule.getAction();
				handler.handleImplParts(actionList, null);
			}
//			TRACE.logDebug("Completed the Access Validation cycle");
		}
	}

	private class Handler {

		Handler(final NodeVisitor visitor) {
		}

		
		void handleImplParts(final List<TableRuleVariable> condiList,
				final List<RuleError> errors) {
			
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.decisionproject.acl.AccessController#checkACLForCustomActions(com.tibco.cep.decisionproject.ontology.Implementation,
	 *      java.lang.String, java.util.List, java.util.Collection)
	 */
	public void checkACLForCustomActions(Implementation implementation,
			String action, List<Role> userRole,
			Collection<ValidationError> vldErrorCollection)
			throws ACLCheckException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.decisionproject.acl.AccessController#checkACLForCustomConditions(com.tibco.cep.decisionproject.ontology.Implementation,
	 *      java.lang.String, java.util.List, java.util.Collection)
	 */
	public void checkACLForCustomConditions(Implementation implementation,
			String action, List<Role> userRole,
			Collection<ValidationError> vldErrorCollection)
			throws ACLCheckException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.decisionproject.acl.AccessController#checkACL(com.tibco.cep.decisionproject.ontology.Implementation,
	 *      java.lang.String, java.lang.String, java.util.List,
	 *      java.util.Collection)
	 */
	public void checkACL(Implementation implementation, String ruleId,
			String action, List<Role> userRole,
			Collection<ValidationError> vldErrorCollection)
			throws ACLCheckException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.decisionproject.acl.AccessController#checkACL(com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable,
	 *      java.util.List, java.util.Collection, boolean)
	 */
	
	public <I extends Implementation> void checkACL(final I table,
										            final AccessControlCellData acd,
										            Collection<ValidationError> localErrors) {
		//Expression expr = acd.getTrv().getExpression();
		TableRuleVariable trv = acd.getTrv();
		String projectName = null;
//		TRACE.logDebug("Expression value {0}", expr);
		if (ontology == null) {
			DecisionProjectLoader dpl = DecisionProjectLoader.getInstance();
			if(dpl == null) return;
			DecisionProject dp = dpl.getDecisionProject();
			if(dp == null) return;
			projectName = dp.getName();
			if(projectName == null) return;
			String earPath = null;//DecisionProjectUtil.getEarFilePath(dp);
			if (earPath == null) return;
//			
//			try {
//				ontology = null;//DecisionProjectUtil.ontologyFromEAR(new File(earPath));
//			} catch (Exception e){
////				if(DecisionProjectLoader.LOGGER.isDebugEnabled()) 
////					DecisionProjectLoader.LOGGER.logDebug(e);
//				return;
//			}
		}
		if(comp == null && ontology != null) { 
			comp = AuthzCompiler.newAuthzCompiler(ontology, table);
		}
		// Get the parse tree nodes for this expression
		final List<RootNode> rootNodes = new ArrayList<RootNode>();
		//Construct column info
		Column col = acd.getAssociatedColumn();
		ColumnType columnType = col.getColumnType();
		CellAuthorizationPrivilegesHandler cellAuthorizationPrivilegesHandler = null;
		DTCellCompilationContext ctx = new DTCellCompilationContext(trv, col, DTDomainUtil.getDomainEntries(projectName, col));
		if (columnType.equals(ColumnType.ACTION) || columnType.equals(ColumnType.CUSTOM_ACTION)) {
			comp.compileActionExpression(ctx, null, rootNodes);
			cellAuthorizationPrivilegesHandler = 
				new CellAuthorizationPrivilegesHandler((Table)table, ontology, roles, rootNodes, false, localErrors);
		} else {
			comp.compileConditionExpression(ctx, null, rootNodes);
			cellAuthorizationPrivilegesHandler = 
				new CellAuthorizationPrivilegesHandler((Table)table, ontology, roles, rootNodes, true, localErrors);
		}
		cellAuthorizationPrivilegesHandler.authorize();
		
		/*AuthorizableNodeVisitor vi = (AuthorizableNodeVisitor)visitor;
		vi.setCompiler(comp);
		vi.setTable((Table)table);
		for (RootNode rNode : rootNodes) {
			// Do what
			final Iterator<Node> children = rNode.getChildren();
			while (children.hasNext()) {
				Node node = children.next();
				NodeType nodeType = node.getType();
				// sString name = nodeType.getName();
				TRACE.logDebug("Node Type {0}", nodeType);
				node.accept(visitor);
			}
		}*/
		// Assuming it was successful, set the modified to false
		//trv.setModified(false);
	}

}

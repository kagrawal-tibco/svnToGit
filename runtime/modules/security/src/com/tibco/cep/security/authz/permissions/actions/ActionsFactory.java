/**
 * 
 */
package com.tibco.cep.security.authz.permissions.actions;

import com.tibco.cep.security.authz.utils.ResourceType;

/**
 * @author aathalye
 *
 */
public class ActionsFactory {
	
	public static IAction getAction(final ResourceType resourceType,
			                        final String action) {
		if (resourceType == null) {
			return null;
		}
		switch (resourceType) {
		
		case CONCEPT:
        case SCORECARD:    
			return new ConceptAction(action);
			
		case EVENT:
			return new EventAction(action);
			
		case PROPERTY:
			return new PropertyAction(action);
			
		case RULESET:
			return new RulesetAction(action);
			
		case RULEFUNCTION:
			return new RuleFunctionAction(action);
		
		case RULE:
			return new RuleAction(action);

		case RULETEMPLATE:
			return new RuleTemplateAction(action);

		case RULETEMPLATEINSTANCE:
			return new RuleTemplateInstanceAction(action);

		case RULETEMPLATEVIEW:
			return new RuleTemplateViewAction(action);
			
		case CATALOGFUNCTION:
			return new FunctionsCatalogAction(action);
		
		case RULEFUNCTIONIMPL:
			return new AbstractImplementationAction(action);
		
		case FOLDER:
			return new FolderAction(action);
		
		case PROJECT:
			return new ProjectAction(action);
		
		case DOMAIN :
			return new DomainAction(action);
			
		case CHANNEL :
			return new ChannelResourceAction(action);
			
		case SHAREDHTTP :
		case RVTRANSPORT :
		case SHAREDJMSCON :
		case SHAREDASCON :
		case SHAREDSB :
		case SHAREDJDBC :
		case SHAREDJNDICONFIG :
		case IDENTITY :
		case ID :
			return new SharedResourceAction(action);
		
		case XSD :
			return new XSDAction(action);
			
		case CDD :
			return new CDDAction(action);
			
		case WSDL :
			return new WSDLAction(action);
		
		case METRIC :
		case DATASOURCE :
		case SERIESCOLOR :
		case SKIN :
		case SYSTEM :
		case DASHBOARDPAGE :
		case VIEW :
		case ROLEPREFERENCE :
		case CHART :
		case SMCOMPONENT :
		case PAGESELECTOR :
			return new DashboardResourceAction(action);
		case BEPROCESS :
			return new BEProcessAction(action);
				
		default:
			return null;
		}
	}
}

/**
 * 
 */
package com.tibco.cep.security.authz.permissions;


import com.tibco.cep.security.authz.domain.DomainResourceCollection;
import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.permissions.actions.AbstractImplementationAction;
import com.tibco.cep.security.authz.permissions.actions.BEProcessAction;
import com.tibco.cep.security.authz.permissions.actions.CDDAction;
import com.tibco.cep.security.authz.permissions.actions.ChannelResourceAction;
import com.tibco.cep.security.authz.permissions.actions.ConceptAction;
import com.tibco.cep.security.authz.permissions.actions.DashboardResourceAction;
import com.tibco.cep.security.authz.permissions.actions.DomainAction;
import com.tibco.cep.security.authz.permissions.actions.EventAction;
import com.tibco.cep.security.authz.permissions.actions.FolderAction;
import com.tibco.cep.security.authz.permissions.actions.FunctionsCatalogAction;
import com.tibco.cep.security.authz.permissions.actions.IAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.permissions.actions.ProjectAction;
import com.tibco.cep.security.authz.permissions.actions.PropertyAction;
import com.tibco.cep.security.authz.permissions.actions.RuleAction;
import com.tibco.cep.security.authz.permissions.actions.RuleFunctionAction;
import com.tibco.cep.security.authz.permissions.actions.RuleTemplateAction;
import com.tibco.cep.security.authz.permissions.actions.RuleTemplateInstanceAction;
import com.tibco.cep.security.authz.permissions.actions.RuleTemplateViewAction;
import com.tibco.cep.security.authz.permissions.actions.RulesetAction;
import com.tibco.cep.security.authz.permissions.actions.SharedResourceAction;
import com.tibco.cep.security.authz.permissions.actions.WSDLAction;
import com.tibco.cep.security.authz.permissions.actions.XSDAction;
import com.tibco.cep.security.authz.utils.ACLConstants;
import com.tibco.cep.security.authz.utils.ACLUtils;
import com.tibco.cep.security.authz.utils.PermissionType;
import com.tibco.cep.security.authz.utils.ResourceType;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

/**
 * @author aathalye
 * 
 */
public class DefaultResourcePermissionFactory implements IResourcePermissionFactory {
	
	protected static IResourcePermissionFactory factory;
	
	protected DomainResourceCollection resources;
	
	/**
	 * Intended to be used by parser class
	 * @param collection
	 * @return
	 */
	public static synchronized IResourcePermissionFactory newInstance(final DomainResourceCollection collection) {
	 	if (factory == null) {
			factory = new DefaultResourcePermissionFactory(collection);
		} else {
			if (!(factory.getResources().hashCode() == collection.hashCode())) {
				factory = new DefaultResourcePermissionFactory(collection);
			}
		}
		return factory;
	}
	
	public static synchronized IResourcePermissionFactory newInstance() {
	 	if (factory == null) {
	 		factory = new DefaultResourcePermissionFactory();
	 	}
	 	return factory;
	}
	
	private DefaultResourcePermissionFactory() {
		resources = new DomainResourceCollection();
	}
	
	private DefaultResourcePermissionFactory(final DomainResourceCollection collection) {
		this.resources = collection;
	}
	/**
	 *  
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.permissions.IResourcePermissionFactory#getPermission(com.tibco.cep.projectexplorer.cache.security.authz.domain.IDomainResource,
	 *      com.tibco.cep.projectexplorer.cache.security.authz.permissions.actions.IAction)
	 *      <p> Clients may override this </p>
	 */
	public IResourcePermission getPermission(IDomainResource resource,
			                                 IAction action) {
		// TODO optimize this
		ResourceType resourceType = resource.getType();
		IResourcePermission permission = null;
		switch (resourceType) {
		case CONCEPT :
		case SCORECARD :	
			if (!(action instanceof ConceptAction)) {
				throw new IllegalArgumentException("Action type should be ConceptAction");
			}
			permission = new ConceptResourcePermission(resource, (ConceptAction)action);
			return permission;
		case EVENT :
			if (!(action instanceof EventAction)) {
				throw new IllegalArgumentException("Action type should be EventAction");
			}
			permission = new EventResourcePermission(resource, (EventAction)action);
			return permission;	
		case PROPERTY :
			if (!(action instanceof PropertyAction)) {
				throw new IllegalArgumentException("Action type should be PropertyAction");
			}
			permission = new PropertyResourcePermission(resource, (PropertyAction)action);
			return permission;
		case RULESET :
			if (!(action instanceof RulesetAction)) {
				throw new IllegalArgumentException("Action type should be RulesetAction");
			}
			permission = new RulesetResourcePermission(resource, (RulesetAction)action);
			return permission;
		case RULEFUNCTION :
			if (!(action instanceof RuleFunctionAction)) {
				throw new IllegalArgumentException("Action type should be RuleFunctionAction");
			}
			permission = new RuleFunctionResourcePermission(resource, (RuleFunctionAction)action);
			return permission;
		case CATALOGFUNCTION :
			if (!(action instanceof FunctionsCatalogAction)) {
				throw new IllegalArgumentException("Action type should be FunctionsCatalogAction");
			}
			permission = new FunctionCatalogResourcePermission(resource, (FunctionsCatalogAction)action);
			return permission;
		case RULE :
			if (!(action instanceof RuleAction)) {
				throw new IllegalArgumentException("Action type should be RuleAction");
			}
			permission = new RuleResourcePermission(resource, (RuleAction)action);
			return permission;	
		case RULETEMPLATE :
			if (!(action instanceof RuleTemplateAction)) {
				throw new IllegalArgumentException("Action type should be RuleTemplateAction");
			}
			permission = new RuleTemplateResourcePermission(resource, (RuleTemplateAction)action);
			return permission;	
		case RULETEMPLATEVIEW :
			if (!(action instanceof RuleTemplateViewAction)) {
				throw new IllegalArgumentException("Action type should be RuleTemplateViewAction");
			}
			permission = new RuleTemplateViewResourcePermission(resource, (RuleTemplateViewAction)action);
			return permission;	
		case RULETEMPLATEINSTANCE :
			if (!(action instanceof RuleTemplateInstanceAction)) {
				throw new IllegalArgumentException("Action type should be RuleTemplateInstanceAction");
			}
			permission = new RuleTemplateInstanceResourcePermission(resource, (RuleTemplateInstanceAction)action);
			return permission;	
		case CHANNEL :
			if (!(action instanceof ChannelResourceAction)) {
				throw new IllegalArgumentException("Action type should be ChannelResourceAction");
			}
			permission = new ChannelResourcePermission(resource, (ChannelResourceAction)action);
			return permission;
		case FOLDER :
			if (!(action instanceof FolderAction)) {
				throw new IllegalArgumentException("Action type should be FolderAction");
			}
			permission = new FolderResourcePermission(resource, (FolderAction)action);
			return permission;	
		case RULEFUNCTIONIMPL :
			if (!(action instanceof AbstractImplementationAction)) {
				throw new IllegalArgumentException("Action type should be AbstractImplementationAction");
			}
			permission = new ImplementationResourcePermission(resource, (AbstractImplementationAction)action);
			return permission;
		case PROJECT :
			if (!(action instanceof ProjectAction)) {
				throw new IllegalArgumentException("Action type should be ProjectAction");
			}
			permission = new ProjectResourcePermission(resource, (ProjectAction)action);
			return permission;	
		case DOMAIN :
			if (!(action instanceof DomainAction)) {
				throw new IllegalArgumentException("Action type should be DomainAction");
			}
			permission = new DomainResourcePermission(resource, (DomainAction)action);
			return permission;
		case SHAREDHTTP :
		case RVTRANSPORT :
		case SHAREDJMSCON :
		case SHAREDASCON :
		case SHAREDSB :
		case SHAREDJDBC :
		case SHAREDJNDICONFIG :
		case IDENTITY :
		case SHAREDMQTTCON :
		case ID : {
			if (!(action instanceof SharedResourceAction)) {
				throw new IllegalArgumentException("Action type should be SharedResourceAction");
			}
			permission = new SharedResourcePermission<SharedResourceAction>(resource, (SharedResourceAction)action);
			return permission;
		} 

		case XSD : {
			if (!(action instanceof XSDAction)) {
				throw new IllegalArgumentException("Action type should be SharedResourceAction");
			}
			permission = new XSDResourcePermission(resource, (XSDAction)action);
			return permission;
		}
		
		case CDD : {
			if (!(action instanceof CDDAction)) {
				throw new IllegalArgumentException("Action type should be CDDAction");
			}
			permission = new CDDResourcePermission(resource, (CDDAction)action);
			return permission;
		}

		case WSDL : {
			if (!(action instanceof WSDLAction)) {
				throw new IllegalArgumentException("Action type should be SharedResourceAction");
			}
			permission = new WSDLResourcePermission(resource, (WSDLAction)action);
			return permission;
		}
		case BEPROCESS : {
			if (!(action instanceof BEProcessAction)) {
				throw new IllegalArgumentException("Action type should be BEProcessAction");
			}
			permission = new BEProcessResourcePermission(resource, (BEProcessAction)action);
			return permission;
		}
		
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
		case PAGESELECTOR : {
			if (!(action instanceof DashboardResourceAction)) {
				throw new IllegalArgumentException("Action type should be DashboardResourceAction");
			}
			permission = new DashboardResourcePermission(resource, (DashboardResourceAction)action);
			return permission;
		}

		default:
			throw new UnsupportedOperationException("To be done");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.permissions.IResourcePermissionFactory#getPermission(com.tibco.xml.datamodel.XiNode)
	 */
	public IResourcePermission getPermission(XiNode permissionNode) {
		if (permissionNode == null) {
			throw new IllegalArgumentException("Permission node cannot be null");
		}
		// Get resourceref
		String resourceRef = 
			permissionNode.getAttributeStringValue(ACLConstants.XMLConstants.RESOURCEREF_ATTR);
		// get matching resource for this
		IDomainResource dr = ACLUtils.getMatchingResource(resources, resourceRef);
		if (dr == null) {
			throw new IllegalArgumentException("Resource cannot be null");
		}
		//Get permission type
		String permType =
			permissionNode.getAttributeStringValue(ACLConstants.XMLConstants.TYPE_ATTR);
		if (permType == null) {
			permType = "BERESOURCE";
		}
		// Get the action
		XiNode actionNode = 
			XiChild.getChild(permissionNode, ACLConstants.XMLConstants.ACTION_ELEMENT);
		XiNode permitNode = actionNode.getFirstChild();
		String actionTypeValue = actionNode.getAttributeStringValue(ACLConstants.XMLConstants.TYPE_ATTR);
		return getPermission(dr, permType, actionTypeValue,
				Permit.valueOf(permitNode.getStringValue())); 
	}
	
	public DomainResourceCollection getResources() {
		return resources;
	}
	
	public IResourcePermission getPermission(IDomainResource dr,
											  String permType,
			                                  String actionType, 
			                                  Permit permit) {
		// Get resource type
		ResourceType resourceType = dr.getType();
		IResourcePermission permission = null;
		PermissionType permissionType;
		IAction action = null;
		if(permType == null){
			 permissionType = PermissionType.BERESOURCE;
		}else{
		 permissionType =
			PermissionType.valueOf(permType);
		}
		switch (resourceType) {
		case CONCEPT : 
		case SCORECARD : {
			action = new ConceptAction(actionType, permit, null);
			permission = new ConceptResourcePermission(dr, (ConceptAction)action, permissionType);
			return permission;
		}
		case EVENT : {
			action = new EventAction(actionType, permit, null);
			permission = new EventResourcePermission(dr, (EventAction)action, permissionType);
			return permission;
		}
		case PROPERTY : {
			action = new PropertyAction(actionType, permit, null);
			permission = new PropertyResourcePermission(dr, (PropertyAction)action, permissionType);
			return permission;
		}
		case RULESET : {
			action = new RulesetAction(actionType, permit, null);
			permission = new RulesetResourcePermission(dr, (RulesetAction)action);
			return permission;
		}
		case RULEFUNCTION : {
			action = new RuleFunctionAction(actionType, permit, null);
			permission = new RuleFunctionResourcePermission(dr, (RuleFunctionAction)action);
			return permission;
		}
		case CATALOGFUNCTION : {
			action = new FunctionsCatalogAction(actionType, permit, null);
			permission = new FunctionCatalogResourcePermission(dr, (FunctionsCatalogAction)action);
			return permission;
		}
		case RULE : {
			action = new RuleAction(actionType, permit, null);
			permission = new RuleResourcePermission(dr, (RuleAction)action);
			return permission;
		}
		case RULETEMPLATE : {
			action = new RuleTemplateAction(actionType, permit, null);
			permission = new RuleTemplateResourcePermission(dr, (RuleTemplateAction)action);
			return permission;
		}
		case RULETEMPLATEVIEW : {
			action = new RuleTemplateViewAction(actionType, permit, null);
			permission = new RuleTemplateViewResourcePermission(dr, (RuleTemplateViewAction)action);
			return permission;
		}
		case RULETEMPLATEINSTANCE : {
			action = new RuleTemplateInstanceAction(actionType, permit, null);
			permission = new RuleTemplateInstanceResourcePermission(dr, (RuleTemplateInstanceAction)action);
			return permission;
		}
		case CHANNEL : {
			action = new ChannelResourceAction(actionType, permit, null);
			permission = new ChannelResourcePermission(dr, (ChannelResourceAction)action);
			return permission;
		}
		case FOLDER : {
			action = new FolderAction(actionType, permit, null);
			permission = new FolderResourcePermission(dr, (FolderAction)action);
			return permission;
		}
		case RULEFUNCTIONIMPL : {
			action = new AbstractImplementationAction(actionType, permit, null);
			permission = 
				new ImplementationResourcePermission(dr, 
						(AbstractImplementationAction)action);
			return permission;
		}
		case PROJECT : {
			action = new ProjectAction(actionType, permit, null);
			permission = new ProjectResourcePermission(dr, (ProjectAction)action);
			return permission;
		}
		case DOMAIN : {
			action = new DomainAction(actionType, permit, null);
			permission = new DomainResourcePermission(dr, (DomainAction)action);
			return permission;
		}
		case BEPROCESS : {
			action = new BEProcessAction(actionType, permit, null);
			permission = new BEProcessResourcePermission(dr, (BEProcessAction)action);
			return permission;
		}
		
		case SHAREDHTTP :
		case RVTRANSPORT :
		case SHAREDJMSCON :
		case SHAREDJDBC :
		case SHAREDJNDICONFIG :
		case SHAREDASCON :
		case SHAREDSB :
		case IDENTITY : 
		case ID : {
			action = new SharedResourceAction(actionType, permit, null);
			permission = new SharedResourcePermission<SharedResourceAction>(dr, (SharedResourceAction)action);
			return permission;
		} 

		case XSD : {
			action = new XSDAction(actionType, permit, null);
			permission = new XSDResourcePermission(dr, (XSDAction)action);
			return permission;
		}
		
		case CDD : {
			action = new CDDAction(actionType, permit, null);
			permission = new CDDResourcePermission(dr, (CDDAction)action);
			return permission;
		}

		case WSDL : {
			action = new WSDLAction(actionType, permit, null); 
			permission = new WSDLResourcePermission(dr, (WSDLAction)action);
			return permission;
		}
		
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
		case PAGESELECTOR : {
			action = new DashboardResourceAction(actionType, permit, null);
			permission = new DashboardResourcePermission(dr, (DashboardResourceAction)action);
			return permission;
		}
		default:
			throw new UnsupportedOperationException("To be done");
		}
	}
}
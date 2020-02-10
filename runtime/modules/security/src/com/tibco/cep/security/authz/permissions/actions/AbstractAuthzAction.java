package com.tibco.cep.security.authz.permissions.actions;

import static com.tibco.cep.security.authz.utils.ACLConstants.XMLConstants.ACTION_ELEMENT;
import static com.tibco.cep.security.authz.utils.ACLConstants.XMLConstants.TYPE_ATTR;

import java.security.Principal;

import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;

public abstract class AbstractAuthzAction implements IAction {
	
	protected String actionType;
	
	protected int mask;
	
	//This permit is the configured one
	protected Permit permit;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8595024535748463214L;

	/* (non-Javadoc)
	 * @see com.tibco.cep.security.authz.core.SerializableObject#serialize(com.tibco.xml.datamodel.XiFactory, com.tibco.xml.datamodel.XiNode)
	 */
	@Override
	public void serialize(XiFactory factory, XiNode rootNode) {
		//Create <action> element
		XiNode actionNode = factory.createElement(ACTION_ELEMENT);
		//Serialize attrs
		XiNode actionTypeAttr = factory.createAttribute(TYPE_ATTR, actionType);
		actionNode.appendChild(actionTypeAttr);
		
		//Add permit
		if (permit != null) {
			actionNode.appendText(permit.name());
		}
		//Append to rootnode
		rootNode.appendChild(actionNode);
	}

	public AbstractAuthzAction(String actionType) {
		super();
		this.actionType = actionType;
		this.mask = getMask(actionType);
	}

	public AbstractAuthzAction(int mask) {
		super();
		this.mask = mask;
		this.actionType = getActionType(mask);
	}

	/**
	 * @param actionType
	 * @param mask
	 * @param permit
	 */
	public AbstractAuthzAction(String actionType, Permit permit, Principal principal) {
		super();
		this.actionType = actionType;
		this.mask = getMask(actionType);
		this.permit = permit;
	}
	
	/**
	 * 
	 * @param actionType
	 * @return
	 */
	protected abstract int getMask(String actionType);
	
	/**
	 * 
	 * @param mask
	 * @return
	 */
	protected abstract String getActionType(int mask);
	
	public String getActionType() {
		return this.actionType;
	}
	
	public String getPermitValue() {
		return permit.name();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	
	public int hashCode() {
		return actionType.hashCode();
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
	public String toString() {
		return "[" + this.getClass().getSimpleName() +
			 ":" + actionType + "]";
	}
}
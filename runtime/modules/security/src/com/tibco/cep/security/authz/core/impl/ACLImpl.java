/**
 * 
 */
package com.tibco.cep.security.authz.core.impl;

import static com.tibco.cep.security.authz.utils.ACLConstants.XMLConstants.ACL_ELEMENT;
import static com.tibco.cep.security.authz.utils.ACLConstants.XMLConstants.ENTRIES_ELEMENT;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.security.authz.core.ACL;
import com.tibco.cep.security.authz.core.ACLEntry;
import com.tibco.cep.security.authz.domain.DomainResourceCollection;
import com.tibco.cep.security.authz.domain.IDomainResourceCollection;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;


/**
 * @author aathalye
 *
 */
public class ACLImpl implements ACL {
	
	private static final long serialVersionUID = 28428712112L;
	
	private IDomainResourceCollection resources;
	
	private List<ACLEntry> aclEntries;
	
	public ACLImpl(final IDomainResourceCollection resources,
			       final List<ACLEntry> aclEntries) {
		this.resources = resources;
		this.aclEntries = aclEntries;
	}
	
	public ACLImpl() {
		resources = new DomainResourceCollection();
		aclEntries = new ArrayList<ACLEntry>();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.core.ACL#getACLEntries()
	 */
	
	public List<ACLEntry> getACLEntries() {
		return this.aclEntries;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.core.ACL#getResources()
	 */
	
	public IDomainResourceCollection getResources() {
		return this.resources;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.core.ACL#setResources(com.tibco.cep.projectexplorer.cache.security.authz.domain.DomainResourceCollection)
	 */
	
	public void setResources(IDomainResourceCollection resources) {
		this.resources = resources;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.core.ACL#setACLEntries(java.util.List)
	 */
	
	public void setACLEntries(List<ACLEntry> entries) {
		this.aclEntries = entries;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.security.authz.core.SerializableObject#serialize(com.tibco.xml.datamodel.XiFactory, com.tibco.xml.datamodel.XiNode)
	 */
	@Override
	public void serialize(XiFactory factory, XiNode node) {
		//Create root ACL element
		factory = (factory == null) ? XiFactoryFactory.newInstance() : factory;
		XiNode aclRootNode = factory.createElement(ACL_ELEMENT);
		//TODO Append attrs
		//Serialize resources
		resources.serialize(factory, aclRootNode);
		//Serialize entries
		XiNode aclEntriesRootNode = factory.createElement(ENTRIES_ELEMENT);
		aclRootNode.appendChild(aclEntriesRootNode);
		
		for (ACLEntry aclEntry : aclEntries) {
			//serialize each entry
			aclEntry.serialize(factory, aclEntriesRootNode);
		}
		//Append to root document node
		node.appendChild(aclRootNode);
	}
}

/**
 * 
 */
package com.tibco.cep.security.authz.core.impl;

import static com.tibco.cep.security.authz.utils.ACLConstants.XMLConstants.ACTION_ELEMENT;
import static com.tibco.cep.security.authz.utils.ACLConstants.XMLConstants.ENTRY_ELEMENT;
import static com.tibco.cep.security.authz.utils.ACLConstants.XMLConstants.PERMISSIONS_ELEMENT;
import static com.tibco.cep.security.authz.utils.ACLConstants.XMLConstants.PERMISSION_ELEMENT;
import static com.tibco.cep.security.authz.utils.ACLConstants.XMLConstants.RESOURCES_ELEMENT;
import static com.tibco.cep.security.authz.utils.ACLConstants.XMLConstants.RESOURCE_ELEMENT;
import static com.tibco.cep.security.authz.utils.ACLConstants.XMLConstants.ROLE_ELEMENT;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ext.DefaultHandler2;

import com.tibco.cep.security.authz.core.ACLEntry;
import com.tibco.cep.security.authz.domain.DomainResource;
import com.tibco.cep.security.authz.domain.DomainResourceCollection;
import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.domain.TemplateDomainResource;
import com.tibco.cep.security.authz.permissions.DefaultResourcePermissionFactory;
import com.tibco.cep.security.authz.permissions.IResourcePermission;
import com.tibco.cep.security.authz.permissions.PermissionsCollection;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.utils.ACLUtils;
import com.tibco.cep.security.authz.utils.ResourceType;
import com.tibco.cep.security.tokens.Role;
import com.tibco.cep.security.tokens.TokenFactory;

/**
 * @author aathalye
 * 
 */
public class ACLConfigurationHandler extends DefaultHandler2 {

	private DomainResourceCollection drc;

	private List<ACLEntry> aclEntries;

	private List<Permission> permissions = new ArrayList<Permission>();

	private int entriesCounter;

	private int permissionsCounter;

	private Action action;

//	private static PluginLoggerImpl TRACE = LoggerRegistry.getLogger(SecurityPlugin.PLUGIN_ID);

	public ACLConfigurationHandler() {
		drc = new DomainResourceCollection();
		drc.open();
		aclEntries = new ArrayList<ACLEntry>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.ext.DefaultHandler2#endCDATA()
	 */
	@Override
	public void endCDATA() throws SAXException {
		// TODO Auto-generated method stub
		super.endCDATA();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.ext.DefaultHandler2#startCDATA()
	 */
	@Override
	public void startCDATA() throws SAXException {
		super.startCDATA();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		StringBuilder sb = new StringBuilder();
		for (int i = start; i < start + length; i++) {
			switch (ch[i]) {
			case '\\':
				break;
			case '"':
				break;
			case '\n':
				break;
			case '\r':
				break;
			case '\t':
				break;
			default:
				if (!Character.isWhitespace(ch[i])) {
					sb.append(ch[i]);
				}
				break;
			}
			
		}
		if (sb.length() > 0) {
			String s = sb.toString();
			Permit permit = null;
			try {
				permit = Permit.valueOf(s);
			} catch (Exception e) {
				//Dirty fix
				s = "ALLOW";
				permit = Permit.valueOf(s);
			} finally {
				action.setPermit(permit);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#endDocument()
	 */
	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String arg0, String arg1, String arg2)
			throws SAXException {
		if (arg2 != null) {
			if (arg2.intern() == RESOURCES_ELEMENT.getLocalName().intern()) {
				drc.close();
			}
			if (arg2.intern() == ENTRY_ELEMENT.getLocalName().intern()) {
				entriesCounter++;
			}
			if (arg2.intern() == PERMISSION_ELEMENT.getLocalName().intern()) {
				Permission perm = permissions.get(permissionsCounter++);
				perm.setAction(action);
				// Get this stuff out
				IDomainResource dr = perm.getResource();
				String permType = perm.getPermType();
				String actionType = perm.getAction().getType();
				Permit permit = perm.getAction().getPermit();
				IResourcePermission permission = DefaultResourcePermissionFactory
						.newInstance(drc).getPermission(dr, permType,
								actionType, permit);
				// Add it to the proper collection
//				TRACE.logDebug(this.getClass().getName(), 
//						"Added permission {0} to the collection of permissions", permission);
				aclEntries.get(entriesCounter).getPermissions().addPermission(
						permission);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startDocument()
	 */
	@Override
	public void startDocument() throws SAXException {
		// Do nothing
		super.startDocument();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
	 *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String arg0, String arg1, String arg2,
			Attributes arg3) throws SAXException {
		if (arg2 != null) {
			if (arg2.intern() == RESOURCE_ELEMENT.getLocalName().intern()) {
				IDomainResource resource = getResource(arg3);
//				TRACE.logDebug(this.getClass().getName(), 
//						"Resource {0} added to collection", resource);
				drc.add(resource);
			}
			if (arg2.intern() == ENTRY_ELEMENT.getLocalName().intern()) {
				ACLEntry entry = new ACLEntryImpl();
				aclEntries.add(entry);
			}
			if (arg2.intern() == ROLE_ELEMENT.getLocalName().intern()) {
				aclEntries.get(entriesCounter).setRole(getRole(arg3));
			}
			if (arg2.intern() == ACTION_ELEMENT.getLocalName().intern()) {
				// permissions.get(permissionsCounter).setAction(action)
				action = getAction(arg3);
			}
			if (arg2.intern() == PERMISSION_ELEMENT.getLocalName().intern()) {
				permissions.add(getPermission(arg3));
			}
			if (arg2.intern() == PERMISSIONS_ELEMENT.getLocalName().intern()) {
				aclEntries.get(entriesCounter).setPermissions(
						new PermissionsCollection());
			}
		}
	}

	private IDomainResource getResource(final Attributes attributes) {
		String resourceName = attributes.getValue("", "name");
		String resType = attributes.getValue("", "type");
		String resourceID = attributes.getValue("", "id");
		ResourceType resourceType = ResourceType.valueOf(resType);
		IDomainResource resource = (resourceName != null) ? new DomainResource(
				resourceName, resourceID, resourceType)
				: new TemplateDomainResource(resourceID, resourceType);
		return resource;
	}

	private Role getRole(final Attributes attributes) {
		String roleName = attributes.getValue("", "name");
		//1/15/2010 - Modified By Anand To Support NON EMF Persistence Of Token
		Role role = TokenFactory.INSTANCE.createRole();
		role.setName(roleName);
		return role;
	}

	private Permission getPermission(final Attributes attributes) {
		String resourceRef = attributes.getValue("", "resourceref");
		String permType = attributes.getValue("", "type");
		if (permType == null || permType.length() == 0) {
			permType = "BERESOURCE";
		}
		IDomainResource dr = ACLUtils.getMatchingResource(drc, resourceRef);
		Permission permission = new Permission(dr);
		permission.setPermType(permType);
		return permission;
	}

	private class Permission {
		private IDomainResource resource;

		private Action action;

		private String permType;

		Permission(final IDomainResource resource) {
			this.resource = resource;
		}

		void setAction(final Action action) {
			this.action = action;
		}

		/**
		 * @return the resource
		 */
		public IDomainResource getResource() {
			return resource;
		}

		/**
		 * @return the action
		 */
		public Action getAction() {
			return action;
		}

		/**
		 * @return the permType
		 */
		public String getPermType() {
			return permType;
		}

		/**
		 * @param permType
		 *            the permType to set
		 */
		public void setPermType(String permType) {
			this.permType = permType;
		}
	}

	private class Action {
		String type;

		Permit permit;

		Action(String type) {
			this.type = type;
		}

		/**
		 * @return the permit
		 */
		public Permit getPermit() {
			return permit;
		}

		/**
		 * @param permit
		 *            the permit to set
		 */
		public void setPermit(Permit permit) {
			this.permit = permit;
		}

		/**
		 * @return the type
		 */
		public String getType() {
			return type;
		}

	}

	private Action getAction(final Attributes attributes) {
		String type = attributes.getValue("", "type");
		return new Action(type);
	}

	List<ACLEntry> getACLEntries() {
		return aclEntries;
	}

	DomainResourceCollection getResources() {
		return drc;
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#error(org.xml.sax.SAXParseException)
	 */
	@Override
	public void error(SAXParseException e) throws SAXException {
		super.error(e);
//		TRACE.logError(this.getClass().getName(), 
//				"Error reported while parsing", e);
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#fatalError(org.xml.sax.SAXParseException)
	 */
	@Override
	public void fatalError(SAXParseException e) throws SAXException {
		super.fatalError(e);
//		TRACE.logError(this.getClass().getName(), 
//				"Error reported while parsing", e);
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#warning(org.xml.sax.SAXParseException)
	 */
	@Override
	public void warning(SAXParseException e) throws SAXException {
		// TODO Auto-generated method stub
		super.warning(e);
	}
	
	
}

/**
 * 
 */
package com.tibco.cep.security.authz.utils;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 * @author aathalye
 *
 */
public class ACLConstants {
	
	public static class XMLConstants {
		public static final ExpandedName ACL_ELEMENT = ExpandedName.makeName("acl");
		public static final ExpandedName RESOURCES_ELEMENT = ExpandedName.makeName("resources");
		public static final ExpandedName RESOURCE_ELEMENT = ExpandedName.makeName("resource");
		public static final ExpandedName ENTRIES_ELEMENT = ExpandedName.makeName("entries");
		public static final ExpandedName ENTRY_ELEMENT = ExpandedName.makeName("entry");
		public static final ExpandedName PERMISSIONS_ELEMENT = ExpandedName.makeName("permissions");
		public static final ExpandedName PERMISSION_ELEMENT = ExpandedName.makeName("permission");
		public static final ExpandedName ACTIONS_ELEMENT = ExpandedName.makeName("actions");
		public static final ExpandedName ACTION_ELEMENT = ExpandedName.makeName("action");
		public static final ExpandedName ROLE_ELEMENT = ExpandedName.makeName("role");
		public static final ExpandedName OBLIGATIONS_ELEMENT = ExpandedName.makeName("obligations");
		public static final ExpandedName OBLIGATION_ELEMENT = ExpandedName.makeName("obligation");
		public static final ExpandedName OBLIGATION_PARAMS_ELEMENT = ExpandedName.makeName("params");
		public static final ExpandedName OBLIGATION_PARAM_ELEMENT = ExpandedName.makeName("params");
		
		
		public static final ExpandedName NAME_ATTR = ExpandedName.makeName("name");
		public static final ExpandedName ID_ATTR = ExpandedName.makeName("id");
		public static final ExpandedName TYPE_ATTR = ExpandedName.makeName("type");
		public static final ExpandedName PARENT_ATTR = ExpandedName.makeName("parent");
		public static final ExpandedName RESOURCEREF_ATTR = ExpandedName.makeName("resourceref");
		public static final ExpandedName CLASS_ATTR = ExpandedName.makeName("class");
		
	}
	
	public static final String USER_DATA_PROVIDER_TYPE_LDAP = "ldap";
	public static final String USER_DATA_PROVIDER_TYPE_FILE = "file";
	//public static final String ROLE_ATTR_LDAP = "nsroledn";
	
	//Constants for Writing out ACL
	public static final int ENCRYPT_MODE = 0;
	public static final int SERIALIZE_MODE = 1;
}

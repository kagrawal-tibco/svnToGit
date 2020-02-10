package com.tibco.be.ws.functions;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tibco.be.rms.functions.AuthorizationHelper;
import com.tibco.cep.security.authen.UserDataProviderFactory;
import com.tibco.cep.security.authz.core.ACL;
import com.tibco.cep.security.authz.core.ACLEntry;
import com.tibco.cep.security.authz.core.impl.ACLEntryImpl;
import com.tibco.cep.security.authz.core.impl.ACLImpl;
import com.tibco.cep.security.authz.domain.DomainResource;
import com.tibco.cep.security.authz.domain.DomainResourceCollection;
import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.domain.IDomainResourceCollection;
import com.tibco.cep.security.authz.permissions.DefaultResourcePermissionFactory;
import com.tibco.cep.security.authz.permissions.IResourcePermission;
import com.tibco.cep.security.authz.permissions.PermissionsCollection;
import com.tibco.cep.security.authz.permissions.actions.IAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.utils.ACLUtils;
import com.tibco.cep.security.authz.utils.ResourceType;
import com.tibco.cep.security.dataprovider.IUserDataProvider;
import com.tibco.cep.security.dataprovider.impl.DataProviderConstants;
import com.tibco.cep.security.dataprovider.impl.FileUserDataProvider;
import com.tibco.cep.security.dataprovider.impl.FileUserDataProvider.UserData;
import com.tibco.cep.security.tokens.Role;
import com.tibco.cep.security.tokens.TokenFactory;


@com.tibco.be.model.functions.BEPackage(
	catalog = "BRMS",
    category = "WS.Acl",
    synopsis = "Functions to work with RuleFunction.",
    enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.WS.Acl", value=true))
public class WebstudioACLFunctions {

	private static List<IResourcePermission> permissionList = new ArrayList<IResourcePermission>();
	private static List<IDomainResource> resourceList = new ArrayList<IDomainResource>();
	private static List<UserData> userDatalist;
	private static List<ACLEntry> aclEntryListArray = new ArrayList<ACLEntry>();
	private static Map<String, String> userEntryMap = new LinkedHashMap<String, String>();
	private static DomainResourceCollection drc = new DomainResourceCollection();

	private static HashMap<String, PermissionsCollection> permCollectionMap = new HashMap<String, PermissionsCollection>();

	@com.tibco.be.model.functions.BEFunction(
		name = "getRoleName",
		signature = "String getRoleName(Object entryObject)",
		params = {
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "entryObject", type = "Object", desc = "")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
		version = "5.4.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static String getRoleName(Object entryObject) {
		if (!(entryObject instanceof ACLEntry)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", ACLEntry.class.getName()));
		}

		ACLEntry entry = (ACLEntry) entryObject;
		return entry.getRole().getName();
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "getPermissionArray",
		signature = "Object[] getPermissionArray(Object aclEntryObject)",
		params = {
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "aclEntryObject", type = "Object", desc = "")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = ""),
		version = "5.4.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static Object[] getPermissionArray(Object aclEntryObject) {
		if (!(aclEntryObject instanceof ACLEntry)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", ACLEntry.class.getName()));
		}

		ACLEntry  aclEntry = (ACLEntry) aclEntryObject;
		permissionList.clear();

		PermissionsCollection permissionCollection = aclEntry.getPermissions();
		Iterator<IResourcePermission> permiElement = permissionCollection.getElements();

		while(permiElement.hasNext()){
			permissionList.add(permiElement.next());
		}

		if (permissionList != null) {
			return permissionList.toArray();
		}

		return new Object[0];
	}


	@com.tibco.be.model.functions.BEFunction(
		name = "getActionObject",
		signature = "Object getActionObject(Object permissionObject)",
		params = {
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "permissionObject", type = "Object", desc = "")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
		version = "5.4.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static Object getActionObject(Object permissionObject) {
		if (!(permissionObject instanceof IResourcePermission)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", IResourcePermission.class.getName()));
		}

		IResourcePermission permission = (IResourcePermission) permissionObject;
		IAction action = permission.getAction();
		if (action != null) {
			return action;
		}

		return new Object();
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "getActionType",
		signature = "String getActionType(Object actionObject)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "actionObject", type = "Object", desc = "")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
		version = "5.4.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static String getActionType(Object actionObject) {
		if (!(actionObject instanceof IAction)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", IAction.class.getName()));
		}

		IAction action = (IAction) actionObject;
		return action.getActionType();
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "getActionValue",
		signature = "String getActionValue(Object actionObject)",
		params = {
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "actionObject", type = "Object", desc = "")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
		version = "5.4.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static String getActionValue(Object actionObject){
		if (!(actionObject instanceof IAction)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", IAction.class.getName()));
		}

		IAction action = (IAction) actionObject;
		return 	action.getPermitValue();		
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "getPermissionResourceRef",
		signature = "String getPermissionResourceRef(Object permissionObject)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "permissionObject", type = "Object", desc = "")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
		version = "5.4.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static String getPermissionResourceRef(Object permissionObject) {
		if (!(permissionObject instanceof IResourcePermission)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", IResourcePermission.class.getName()));
		}

		IResourcePermission permission = (IResourcePermission) permissionObject;
		return permission.getResource().getId();
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "getPermissionType",
		signature = "String getPermissionType(Object permissionObject)",
		params = {
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "permissionObject", type = "Object", desc = "")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
		version = "5.4.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static String getPermissionType(Object permissionObject) {
		if (!(permissionObject instanceof IResourcePermission)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", IResourcePermission.class.getName()));
		}

		IResourcePermission permission = (IResourcePermission) permissionObject;
		return permission.getPermissionType().name();
	}


	@com.tibco.be.model.functions.BEFunction(
		name = "getAclEntryArray",
		signature = "Object[] getAclEntryArray(Object aclObject)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "aclObject", type = "Object", desc = "")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = ""),
		version = "5.4.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static Object[] getAclEntryArray(Object aclObject) {
		if (!(aclObject instanceof ACLImpl)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", ACLImpl.class.getName()));
		}

		ACLImpl acl = (ACLImpl) aclObject;
		return acl.getACLEntries().toArray();
	}


	@com.tibco.be.model.functions.BEFunction(
		name = "getAclResourcesArray",
		signature = "Object[] getAclResourcesArray(Object aclObject)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "aclObject", type = "Object", desc = "")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = ""),
		version = "5.4.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static Object[] getAclResourcesArray(Object aclObject) {
		if (!(aclObject instanceof ACLImpl)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", ACLImpl.class.getName()));
		}

		ACLImpl acl = (ACLImpl) aclObject;

		IDomainResourceCollection domainResourceCollection = acl.getResources();
		// resourceList = null;
		Iterator<IDomainResource> element = domainResourceCollection.getElements();
		while(element.hasNext()){
			resourceList.add(element.next());
		}

		return resourceList.toArray();
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "getResourceId",
		signature = "String getResourceId(Object resourceObject)",
		params = {
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "resourceObject", type = "Object", desc = "")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
		version = "5.4.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static String getResourceId(Object resourceObject) {
		if (!(resourceObject instanceof IDomainResource)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", IDomainResource.class.getName()));
		}

		IDomainResource resource = (IDomainResource) resourceObject;
		return resource.getId();
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "getResourceName",
		signature = "String getResourceName(Object resourceObject)",
		params = {
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "resourceObject", type = "Object", desc = "")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
		version = "5.4.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static String getResourceName(Object resourceObject) {
		if (!(resourceObject instanceof IDomainResource)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", IDomainResource.class.getName()));
		}

		IDomainResource resource = (IDomainResource) resourceObject;
		return resource.getName();
	}


	@com.tibco.be.model.functions.BEFunction(
		name = "getResourceType",
		signature = "String getResourceType(Object resourceObject)",
		params = {
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "resourceObject", type = "Object", desc = "")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
		version = "5.4.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static String getResourceType(Object resourceObject) {
		if (!(resourceObject instanceof IDomainResource)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", IDomainResource.class.getName()));
		}

		IDomainResource resource = (IDomainResource) resourceObject;
		return resource.getType().name();
	}

	private static void getUserDataProvider() {
		try {
			IUserDataProvider provider = UserDataProviderFactory.INSTANCE.getProvider();
			if (provider instanceof FileUserDataProvider) {
				FileUserDataProvider fdataProvider = (FileUserDataProvider) provider;
				userDatalist = fdataProvider.getUserDatas();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "getUserDataArray",
		signature = "Object[] getUserDataArray(String subscriptionId)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "subscriptionId", type = "String", desc = "")
			},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = ""),
		version = "5.4.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static Object[] getUserDataArray(String subscriptionId) {
		getUserDataProvider();
		
		List<UserData> filteredUserDataList = null;
		if (subscriptionId == null || subscriptionId.isEmpty()) {
			filteredUserDataList = userDatalist;
		} else {
			filteredUserDataList = new ArrayList<UserData>();
			for (UserData userData : userDatalist) {
				if (userData.getSubscriptionId().equalsIgnoreCase(subscriptionId)) filteredUserDataList.add(userData);
			}
		}
		return filteredUserDataList.toArray();	
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "getUserName",
		signature = "String getUserName(Object userDataObject)",
		params = {
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "userDataObject", type = "Object", desc = "")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
		version = "5.4.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static String getUserName(Object userDataObject) {
		UserData userData = (UserData) userDataObject;
		return userData.getUsername();
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "getUserPassword",
		signature = "String getUserPassword(Object userDataObject)",
		params = {
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "userDataObject", type = "Object", desc = "")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
		version = "5.4.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static String getUserPassword(Object userDataObject) {
		UserData userData = (UserData) userDataObject;
		return userData.getPassword();
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getSubscriptionId",
		signature = "String getSubscriptionId(Object userDataObject)",
		params = {
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "userDataObject", type = "Object", desc = "")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
		version = "5.5.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static String getSubscriptionId(Object userDataObject) {
			UserData userData = (UserData) userDataObject;
			return userData.getSubscriptionId();
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "getUserRoleString",
		signature = "String getUserRoleString(Object userDataObject)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "userDataObject", type = "Object", desc = "")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
		version = "5.4.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static String getUserRoleString(Object userDataObject) {
		UserData userData = (UserData) userDataObject;
		StringBuilder roleString = new StringBuilder();

		for (String role : userData.getRoles()){
			roleString.append(role);
			roleString.append(",");
		}

		String userRoleString =  roleString.toString();
		return userRoleString.substring(0, userRoleString.length()-1);
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getUser",
		signature = "Object getUser(String username, String subscriptionId)",
		params = {
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "username", type = "String", desc = ""),
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "subscriptionId", type = "String", desc = "")	
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "return the user object"),
		version = "5.5.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns the user object if it exits",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static Object getUser(String username, String subscriptionId) {		
		if (userDatalist == null || userDatalist.isEmpty()) getUserDataProvider();
		
		for (UserData user : userDatalist) {
			if (user.getUsername().equals(username)) {
				if (subscriptionId != null && !subscriptionId.isEmpty()) {
					return (user.getSubscriptionId().equals(subscriptionId)) ? user : null;
				} else {
					return user;
				}
			}		
		}
		return null;
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "addResourceToCollection",
		signature = "void addResourceToCollection(String name, String id, String type)",
		params = {
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = ""),
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "id", type = "String", desc = ""),
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "type", type = "String", desc = "")
		},
		version = "5.4.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static void addResourceToCollection(String name, String id, String type) {
		DomainResource resource = new DomainResource(name, id, ResourceType.valueOf(type));
		drc.open();
		drc.add(resource);
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "addPermissionToCollection",
		signature = "void addPermissionToCollection(String resourceref, String permissionType, String actionType, String actionValue)",
		params = {
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "resourceref", type = "String", desc = ""),
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "permissionType", type = "String", desc = ""),
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "actionType", type = "String", desc = ""),
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "actionValue", type = "String", desc = ""),
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "roleName", type = "String", desc = "")
		},
		version = "5.4.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static void addPermissionToCollection(String resourceref, String permissionType, String actionType, String actionValue, String roleName) {
		IDomainResource dr = ACLUtils.getMatchingResource(drc, "#"+resourceref);

		Permit permit = Permit.ALLOW;
		if (actionValue.equals(Permit.DENY.toString())){
			permit = Permit.DENY;
		}

		IResourcePermission permission = DefaultResourcePermissionFactory
				.newInstance(drc).getPermission(dr, permissionType,
						actionType, permit);

		permCollectionMap.get(roleName).addPermission(permission);
	}


	@com.tibco.be.model.functions.BEFunction(
		name = "createAndUpdateEntryObjectList",
		signature = "void createAndUpdateEntryObjectList(String roleName)",
		params = {
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "roleName", type = "String", desc = "")
		},
		version = "5.4.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static void createAndUpdateEntryObjectList(String roleName) {
		ACLEntry aclEntry = new ACLEntryImpl();
		aclEntry.setPermissions(permCollectionMap.get(roleName));

		Role role = TokenFactory.INSTANCE.createRole();
		role.setName(roleName);

		aclEntry.setRole(role);
		aclEntryListArray.add(aclEntry);
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "clearCollections",
		signature = "void clearCollections()",
		version = "5.4.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static void clearCollections() {
		drc.removeAll();
		permCollectionMap.clear();
		aclEntryListArray.clear();
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "updatePermCollectionMap",
		signature = "void updatePermCollectionMap(String roleName)",
		params = {
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "roleName", type = "String", desc = "")
		},
		version = "5.4.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static void updatePermCollectionMap(String roleName) {
		PermissionsCollection permissionCollection = new PermissionsCollection();
		permCollectionMap.put(roleName, permissionCollection);
	}


	@com.tibco.be.model.functions.BEFunction(
		name = "saveAclContentToFile",
		signature = "void saveAclContentToFile(String projectName, String aclPath, String subscriptionId)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = ""),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "aclPath", type = "String", desc = ""),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "subscriptionId", type = "String", desc = "")
		},
		version = "5.4.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static void saveAclContentToFile(String projectName, String aclPath, String subscriptionId) {
		ACL aclObject = new ACLImpl();
		aclObject.setResources(drc);
		aclObject.setACLEntries(aclEntryListArray);

		try {
			saveToFile(aclObject, aclPath);
			AuthorizationHelper.updateACLManagerMap(projectName, aclPath, subscriptionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void saveToFile(ACL aclObject, String aclFilePath) throws Exception{
		File aclFile = new File(aclFilePath);
		aclFile.getParentFile().mkdirs();
		ACLUtils.writeACL(aclObject, new FileOutputStream(aclFile), 1, null);
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "clearUserEntryArray",
		signature = "void clearUserEntryArray()",
		version = "5.4.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static void clearUserEntryArray() {
		userEntryMap.clear();
		
		if (userDatalist == null || userDatalist.isEmpty()) {
			getUserDataProvider();
		}
		
		for (UserData user : userDatalist) {
			addUserEntryToList(user.getUsername(), user.getPassword(), String.join(",", user.getRoles()),
					user.getSubscriptionId(), "NA");
		}
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "addUserEntryToList",
		signature = "void addUserEntryToList(String userName, String userPwd, String userRoles, String subscriptionId, String actionType)",
		params = {
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "userName", type = "String", desc = ""),
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "userPwd", type = "String", desc = ""),
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "userRoles", type = "String", desc = ""),
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "subscriptionId", type = "String", desc = ""),
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "actionType", type = "String", desc = "")
		},
		version = "5.4.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static void addUserEntryToList(String userName, String userPwd, String userRoles, String subscriptionId, String actionType) {
		boolean encrypted = userPwd.matches("[a-fA-F0-9]{32}");

		String userEntry = null;
		if (!encrypted) {
			try {
				userEntry = getEncryptedUserEntry(userName, userPwd, userRoles, subscriptionId);
			} catch (Exception e) {
				throw new RuntimeException(e);
			} 
		} else {
			if (subscriptionId == null || subscriptionId.isEmpty()) {
				userEntry = String.format("%s:%s:%s;",userName, userPwd, userRoles);
			} else {
				userEntry = String.format("%s:%s:%s:%s;",userName, userPwd, userRoles, subscriptionId);
			}
		}
		if (actionType.equals("REMOVE") && userEntryMap.containsKey(userName)) {
			userEntryMap.remove(userName);
		} else {
			userEntryMap.put(userName, userEntry);
		}	
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "removeUserEntryFromList",
		signature = "void removeUserEntryFromList(String userName)",
		params = {
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "userName", type = "String", desc = "")
		},
		version = "5.6.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static void removeUserEntryFromList(String userName) {
		userEntryMap.remove(userName);
	}

	private static String getEncryptedUserEntry(String userName, String userPwd, String userRoles, String subscriptionId) throws Exception {
		MessageDigest md = MessageDigest.getInstance(DataProviderConstants.MD5_ALGORITHM);
		byte[] hash = md.digest(userPwd.getBytes("UTF-8"));

		//converting byte array to Hexadecimal String
		StringBuilder sb = new StringBuilder(2*hash.length);
		for (byte b : hash) {
			sb.append(String.format("%02x", b&0xff));
		}
		return (subscriptionId == null || subscriptionId.isEmpty()) ?
				String.format("%s:%s:%s;",userName, sb.toString(), userRoles) : 
					String.format("%s:%s:%s:%s;",userName, sb.toString(), userRoles, subscriptionId);
	}


	@com.tibco.be.model.functions.BEFunction(
		name = "saveUserContentToFile",
		signature = "void saveUserContentToFile()",
		version = "5.4.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static void saveUserContentToFile() {
		BufferedWriter writer = null;

		try {   
			writer = new BufferedWriter( new FileWriter(System.getProperties().getProperty(DataProviderConstants.BE_AUTH_FILE_LOCATION)));

			for (String userEntry : userEntryMap.values()) {
				writer.write(userEntry);
				writer.newLine();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (writer != null) writer.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		updateUserDataList();
	}

	private static void updateUserDataList() {
		try {
			IUserDataProvider provider = UserDataProviderFactory.INSTANCE.getProvider();
			if (provider instanceof FileUserDataProvider) {
				FileUserDataProvider fdataProvider = (FileUserDataProvider) provider;
				fdataProvider.init(System.getProperties());
				userDatalist = fdataProvider.getUserDatas();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

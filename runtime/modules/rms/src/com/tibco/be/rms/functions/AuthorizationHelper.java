package com.tibco.be.rms.functions;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.security.authz.core.ACLEntry;
import com.tibco.cep.security.authz.core.ACLManager;
import com.tibco.cep.security.authz.core.impl.ACLManagerImpl;
import com.tibco.cep.security.authz.permissions.actions.ActionsFactory;
import com.tibco.cep.security.authz.permissions.actions.IAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.utils.IOUtils;
import com.tibco.cep.security.authz.utils.PermissionType;
import com.tibco.cep.security.tokens.AuthToken;
import com.tibco.cep.security.tokens.Role;
import com.tibco.cep.security.tokens.TokenFactory;
import com.tibco.cep.security.util.SecurityUtil;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: May 10, 2008
 * Time: 11:59:48 PM
 * To change this template use File | Settings | File Templates.
 */

@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
        category = "RMS.Authorization",
        synopsis = "Functions for running Authorization checks",
        enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.RMS.Authorization", value=true))

public class AuthorizationHelper {
	private static final String ROOT_SUBSCRIPTION = "ROOT";
    private static Map<String, List<AuthorizationHelper.ProjectToACL>> ACL_MANAGERS = new LinkedHashMap<String, List<AuthorizationHelper.ProjectToACL>>();

    @com.tibco.be.model.functions.BEFunction(
        name = "validateACLConfig",
        signature = "void validateACLConfig(final String project, final String aclFilePath, final String subscriptionId)",
        params = {
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "project", type = "String", desc = "Project name"),
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "aclFilePath", type = "String", desc = "the full path of the Access Control Configuration file"),
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "subscriptionId", type = "String", desc = "subscription Id if exits")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "4.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Function performs Authorization checks on any rules/individual cells that were modified by\nthe user who checked in this project. For the sake of optimizing overheads of running the check\non the entire decision table, this method is used. This also ensures checks are not run\non anything user did not touch in a decision table and which potentially the user did not\nhave access to.\nThe function runs Authorization checks only on catalog functions used in the table.\nIf the user's role did not have permissions to invoke a catalog function and if it was used\nin a rule/cell modified by the user, an error saying $1Insufficient Access Privileges$1\nis thrown on RMS consle as well as back to the Decision Manager.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static void validateACLConfig(final String project, final String aclFilePath, final String subscriptionId) {
        File f = new File(aclFilePath);
        if (!f.exists()) {
            throw new RuntimeException("Access Control file at " + aclFilePath + " not found");
        }
        initializeAClManager(project, aclFilePath, subscriptionId);
    }
//
//    /**
//     * @param token
//     * @return
//     */
//    public static String getUsernameFromToken(final Object token) {
//        if (token == null) {
//            throw new IllegalArgumentException("Invalid inputs. Token should be of AuthToken type");
//        }
//        AuthToken authToken = (AuthToken)token;
//        Authen auth = authToken.getAuthen();
//        String username = auth.getUser().getUsername();
//        return username;
//    }
//
//    /**
//     *
//     * @param tokenString
//     * @return
//     */
//    public static Object deserializeToken(final String tokenString) {
//    	//1/15/2010 - Modified By Anand To Support NON EMF Persistence Of Token
//    	return SecurityHelper.deserializeAuthToken(tokenString);
//    }

//    /**
//     * This method performs access control checks for read on implementations
//     * present in the approved implementations directory, and only sends
//     * those implementations during checkout/update to which requestor has read access.
//     * @param token The authentication token
//     * @param resourcePath The path of the resource requested.
//     * @return Permit
//     */
//    public static Permit checkAccessForImplementation(final AuthToken token,
//                                                      final String resourcePath) {
//        //Get roles in it
//        List<Role> roles = token.getAuthz().getRoles();
//        IAction action = ActionsFactory.getAction(com.tibco.cep.security.authz.utils.ResourceType.DECISIONTABLE,
//                                                  "read");
//        return SecurityUtil.checkACL(resourcePath,
//                                     com.tibco.cep.security.authz.utils.ResourceType.DECISIONTABLE,
//                                     roles, action);
//    }

    /**
     *
     * @param project
     * @param aclConfig
     */
    private static void initializeAClManager(final String project, final String aclConfig, final String subscriptionId) {
        //Create the ACLManager instance
        try {
            FileInputStream is = new FileInputStream(aclConfig);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            IOUtils.writeBytes(is, bos);
            byte[] bytes = bos.toByteArray();
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ACLManager aclManager = new ACLManagerImpl();
            aclManager.load(bis, false);
            
            String subscription = (subscriptionId != null && !subscriptionId.isEmpty()) ? subscriptionId : ROOT_SUBSCRIPTION;
            List<ProjectToACL> poaList = ACL_MANAGERS.get(subscription);
            if (poaList == null) {
            	poaList = new ArrayList<ProjectToACL>();
            	ACL_MANAGERS.put(subscription, poaList);
            }
            
            ProjectToACL poaToCheck = null;
        	for (ProjectToACL poa : poaList) {
        		if (poa.getProjectName().equals(project)) {
        			poaToCheck = poa;
        			break;
        		}
        	}
            
            if (poaToCheck == null) poaList.add(new AuthorizationHelper().new ProjectToACL(project, aclManager));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "ensureAccess",
        signature = "boolean ensureAccess(final String project, final String subscriptionId, final String[] roles, String artifactPath, String artifactType, final String actionName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "project", type = "String", desc = "Project name."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "subscriptionId", type = "String", desc = "subscription Id if exits"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "roles", type = "String[]", desc = "User roles array."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "artifactPath", type = "String", desc = "Artifact path."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "artifactType", type = "String", desc = "Artifact type."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "actionName", type = "String", desc = "Action name.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if user has acess, false other-wise."),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Checks if user roles have access to perform the action on the specified artifact in the project.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )    
    public static boolean ensureAccess(final String project, final String subscriptionId, final String[] roles,
                                       String artifactPath, String artifactType,
                                       final String actionName) {

    	com.tibco.cep.security.authz.utils.ResourceType resourceType =
                com.tibco.cep.security.authz.utils.ResourceType.valueOf(artifactType);

        IAction action = ActionsFactory.getAction(resourceType, actionName);
        List<Role> allRoles = new ArrayList<Role>(roles.length);
        for (String roleName : roles) {
            Role role = TokenFactory.INSTANCE.createRole();
            role.setName(roleName);
            allRoles.add(role);
        }
        
        ACLManager aclManager = getACLManagerByProject(project, subscriptionId);
        Permit permit = null;
		try {
            permit  = SecurityUtil.ensurePermission(aclManager,
                                                   artifactPath,
                                                   resourceType,
                                                   allRoles,
                                                   action,
                                                   PermissionType.BERESOURCE);
        } catch (Throwable e) {
            if (e.getCause() instanceof IllegalArgumentException) {
                permit = Permit.ALLOW;
            }
        }
        return Permit.ALLOW == permit;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "verifyOperationAccess",
        signature = "boolean verifyOperationAccess(String aclConfig,",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "aclConfig", type = "String", desc = "the full path of the Access Control Configuration file"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "token", type = "Object", desc = "The authentication token <code>EObject</code>"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "resourcePath", type = "String", desc = "Full resource path"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "operationName", type = "String", desc = "Operation name like $1checkout$1, or $1commit$1")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = ""),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Perform Access Control checks for the requested operation on a project",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static boolean verifyOperationAccess(final String aclConfig,
                                                final Object token,
                                                final String resourcePath,
                                                final String operationName) {
        if (!(token instanceof AuthToken)) {
            throw new IllegalArgumentException("Illegal token type");
        }
        AuthToken authToken = (AuthToken)token;
        //Get roles in it
        List<Role> roles = authToken.getAuthz().getRoles();
        IAction action = ActionsFactory.getAction(com.tibco.cep.security.authz.utils.ResourceType.PROJECT,
                                                  operationName);
        Permit permit = SecurityUtil.checkACL(resourcePath,
                              com.tibco.cep.security.authz.utils.ResourceType.PROJECT,
                              roles, action);
        if (Permit.ALLOW.equals(permit)) {
            return true;
        }
        return false;
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "isValidProjectName",
        signature = "boolean isValidProjectName(final String projectName, final String subscriptionId)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectname", type = "String", desc = "projectname to verify"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "subscriptionId", type = "String", desc = "subscription Id if exits")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = ""),
        version = "5.4.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Verify if the project name is valid or not",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static boolean isValidProjectName(final String projectName, final String subscriptionId) {
		ACLManager aclManager = getACLManagerByProject(projectName, subscriptionId);
	
		if (aclManager == null) {
			return false;
		}
	
		return true;
	}
    
    @com.tibco.be.model.functions.BEFunction(
        name = "getAllRoles",
        signature = "Object getAllRoles(final String subscriptionId)",
        params = { 
        		@com.tibco.be.model.functions.FunctionParamDescriptor(name = "subscriptionId", type = "String", desc = "subscription Id if exits")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "List of all the roles configured for this server"),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a list of all roles configured for this server",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object getAllRoles(final String subscriptionId) {
    	List<String> roles = new ArrayList<String>();
    	
    	String subscription = (subscriptionId != null && !subscriptionId.isEmpty()) ? subscriptionId : ROOT_SUBSCRIPTION;
        List<ProjectToACL> poaList = ACL_MANAGERS.get(subscription);
    	
    	for (ProjectToACL poa : poaList) {
    		ACLManager aclManager = poa.getAclManager();
    		for (ACLEntry aclEntry : aclManager.getConfiguredACL().getACLEntries()) {
    			String role = aclEntry.getRole().getName();
    			if (!roles.contains(role)) {
    				roles.add(role);
    			}
    		}
    	}
    	
    	Object aclRole = roles.toArray(new String[roles.size()]);
    	return aclRole;
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "getACLObjectForProject",
        signature = "Object getACLObjectForProject(final String projectName, final String subscriptionId)",
        params = { 
        		 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = ""),
        		 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "subscriptionId", type = "String", desc = "subscription Id if exits")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "5.4.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a list of all roles configured for this server",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object getACLObjectForProject(final String projectName, final String subscriptionId) {
    	ACLManager aclManager = getACLManagerByProject(projectName, subscriptionId);
		return (aclManager != null) ? aclManager.getConfiguredACL() : null;
    	
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "removeACLManager",
        signature = "boolean removeACLManager(final String projectName, final String subscriptionId)",
        params = { 
        		 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = ""),
        		 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "subscriptionId", type = "String", desc = "subscription Id if exits")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = ""),
        version = "5.4.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a list of all roles configured for this server",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static boolean removeACLManager(final String projectName, final String subscriptionId) {
    	String subscription = (subscriptionId != null && !subscriptionId.isEmpty()) ? subscriptionId : ROOT_SUBSCRIPTION;
        List<ProjectToACL> poaList = ACL_MANAGERS.get(subscription);
    	
        ProjectToACL poaToRemove = null;
    	for (ProjectToACL poa : poaList) {
    		if (poa.getProjectName().equals(projectName)) {
    			poaToRemove = poa;
    			break;
    		}
    	}
    	
    	boolean bReturn = false;
    	if (poaToRemove != null) {
    		bReturn = poaList.remove(poaToRemove);
    		poaToRemove = null;
    	}
    	return bReturn;
    	
    }
    
    public static void updateACLManagerMap(String projectName, String projectPath, String subscriptionId) {
    	removeACLManager(projectName, subscriptionId);
    	initializeAClManager(projectName, projectPath, subscriptionId);
    }
    
    /**
     * 
     * @param projectName
     * @param subscriptionId
     * @return
     */
    private static ACLManager getACLManagerByProject(final String projectName, final String subscriptionId) {
    	String subscription = (subscriptionId != null && !subscriptionId.isEmpty()) ? subscriptionId : ROOT_SUBSCRIPTION;
        List<ProjectToACL> poaList = ACL_MANAGERS.get(subscription);
    	for (ProjectToACL poa : poaList) {
    		if (poa.getProjectName().equals(projectName)) return poa.getAclManager();
    	}
    	return null;
    }
    
    /**
     * Model to maintain project to ACL manager mapping
     * @author vpatil
     */
    class ProjectToACL {
    	private String projectName;
    	private ACLManager aclManager;
    	
		public ProjectToACL(String projectName, ACLManager aclManager) {
			super();
			this.projectName = projectName;
			this.aclManager = aclManager;
		}

		public String getProjectName() {
			return projectName;
		}

		public ACLManager getAclManager() {
			return aclManager;
		}
    }
}

package com.tibco.tea.agent.be.role;

/**
 * The class used to define the default roles of the user to access the
 * application functionality.
 * 
 * @author dijadhav
 */
public class Role {

	/*-------------BE ROLES-------------------*/

	public static final String APP_MANAGER_ROLE = "APP_MANAGER";
	
	public static final String APP_MANAGER_ROLE_DESCRIPTION = "The user with this role has all permissions of BE-TEA agent.";

	public static final String DEPLOYER_ROLE = "DEPLOYER";
	
	public static final String DEPLOYER_ROLE_DESCRIPTION = "The user with this role has deploy/undeploy/redeploy PU instance and realated permissions of BE-TEA agent.";

	public static final String OPERATOR_ROLE = "OPERATOR";
	
	public static final String OPERATOR_ROLE_DESCRIPTION = "The user with this role has start/stop PU instance and realated permissions of BE-TEA agent.";

	public static final String VIEW_ALL_ROLE = "VIEW_ALL";
	
	public static final String VIEW_ALL_ROLE_DESCRIPTION = "The user with this role has read-only permissions of BE-TEA agent.";
	
	public static final String RULE_AUTHOR = "RULE_AUTHOR";
	
	public static final String RULE_AUTHOR_DESCRIPTION = "The user with this role has permissions to create non-health rules";
	
	public static final String RULE_AUTHOR_ADMIN = "RULE_AUTHOR_ADMIN";
	
	public static final String RULE_AUTHOR_ADMIN_DESCRIPTION = "The user with this role has permissions perform all rules/alerts related operations";

	// More roles will be added here

	/*---------------------------------------*/

}

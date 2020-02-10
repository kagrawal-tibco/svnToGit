package com.tibco.cep.studio.rms.cli;

import static java.io.File.separator;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.security.authz.core.ACL;
import com.tibco.cep.security.authz.core.ACLEntry;
import com.tibco.cep.security.authz.core.impl.ACLEntryImpl;
import com.tibco.cep.security.authz.core.impl.ACLImpl;
import com.tibco.cep.security.authz.domain.DomainResourceCollection;
import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.domain.IDomainResourceCollection;
import com.tibco.cep.security.authz.domain.TemplateDomainResource;
import com.tibco.cep.security.authz.permissions.ConceptResourcePermission;
import com.tibco.cep.security.authz.permissions.EventResourcePermission;
import com.tibco.cep.security.authz.permissions.FunctionCatalogResourcePermission;
import com.tibco.cep.security.authz.permissions.IResourcePermission;
import com.tibco.cep.security.authz.permissions.PermissionsCollection;
import com.tibco.cep.security.authz.permissions.ProjectResourcePermission;
import com.tibco.cep.security.authz.permissions.RuleFunctionResourcePermission;
import com.tibco.cep.security.authz.permissions.RuleResourcePermission;
import com.tibco.cep.security.authz.permissions.RulesetResourcePermission;
import com.tibco.cep.security.authz.permissions.actions.ConceptAction;
import com.tibco.cep.security.authz.permissions.actions.EventAction;
import com.tibco.cep.security.authz.permissions.actions.FunctionsCatalogAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.permissions.actions.ProjectAction;
import com.tibco.cep.security.authz.permissions.actions.RuleAction;
import com.tibco.cep.security.authz.permissions.actions.RuleFunctionAction;
import com.tibco.cep.security.authz.permissions.actions.RulesetAction;
import com.tibco.cep.security.authz.utils.ACLConstants;
import com.tibco.cep.security.authz.utils.ACLUtils;
import com.tibco.cep.security.authz.utils.ResourceType;
import com.tibco.cep.security.tokens.Role;
import com.tibco.cep.security.tokens.TokenFactory;

/*
 * @author rektare
 */

public class RMSProjectSetupCLI extends RMSCLI {
	
	private String baseLocation;
	private String projectName;
	private String sourceLocation;
	private String aclPath;

	private String projectDir;
	private String decisiondataDir;
	private String workspaceDir;
	private String configDir;
	private String binDir;
	private String deploymentDir;

	private static final String DECISION_DATA_DIR_NAME = "decisiondata";
	private static final String WORKSPACE_DIR_NAME = "workspace";
	private static final String CONFIG_DIR_NAME = "config";
	private static final String BIN_DIR_NAME = "bin";
	private static final String DEPLOYMENT_DIR_NAME = "deployment";
	private static final String REL_PATH_USER_INFO = "/rms/config/security/users.pwd";
	private static final String OPERATION_PROJECT_SETUP = "setup";
	private static final String FLAG_RMS_PROJECT_NAME = "-projName";
	private static final String FLAG_BASE_LOCATION = "-baseLocation";
	private static final String FLAG_SOURCE_LOCATION = "-sourceLocation";
	private static final String FLAG_ACL_PATH = "-aclPath";
	private static final String FLAG_RMS_PROJECT_SETUP_HELP = "-h";
	
	public void run() {
		File root = new File(baseLocation);
		root.mkdir();

		File projectRoot = new File(projectDir);
		projectRoot.mkdir();
		
		//create sub directories
		File decisiondata = new File(decisiondataDir);
		decisiondata.mkdir();

		File workspace = new File(workspaceDir);
		workspace.mkdir();

		File config = new File(configDir);
		config.mkdir();

		File bin = new File(binDir);
		bin.mkdir();

		File deployment = new File(deploymentDir);
		deployment.mkdir();
				
		if (aclPath.isEmpty()) {
			ACL acl = generateACL();
			try {
				FileOutputStream fos = new FileOutputStream(configDir+separator+projectName+".ac");
				try {
					ACLUtils.writeACL(acl, fos, ACLConstants.SERIALIZE_MODE, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			copyFile(new File(aclPath), new File(configDir+separator+projectName+".ac"));
		}
		// copy contents
		if (!sourceLocation.isEmpty()) {
			copyFolder(new File(sourceLocation), decisiondata);
		}
	}
	
	private ACL generateACL() {
		File userInfo = new File(System.getProperty("tibco.env.BE_HOME") + REL_PATH_USER_INFO);
		Set<String> roleNameSet = new HashSet<String>();

		try {
			FileReader reader = new FileReader(userInfo);
			BufferedReader  buffIn = new BufferedReader(reader);
			String line = "";
			while ((line = buffIn.readLine()) != null) {
				String[] parts = line.split(":");
				if (parts.length == 3) {
					String role = parts[2].substring(0, parts[2].length()-1);
					roleNameSet.add(role.trim());
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ACL acl = new ACLImpl();

		//Add some resources to it
		IDomainResource concept = new TemplateDomainResource("C", ResourceType.CONCEPT);
		IDomainResource event = new TemplateDomainResource("E", ResourceType.EVENT);
		IDomainResource ruleSet = new TemplateDomainResource("R", ResourceType.RULESET);
		IDomainResource ruleFunction = new TemplateDomainResource("RF", ResourceType.RULEFUNCTION);
		IDomainResource rule = new TemplateDomainResource("R", ResourceType.RULE);		
		IDomainResource impl = new TemplateDomainResource("DT", ResourceType.IMPLEMENTATION);
		IDomainResource catalogFunction = new TemplateDomainResource("CF", ResourceType.CATALOGFUNCTION);
		IDomainResource project = new TemplateDomainResource("PR", ResourceType.PROJECT);

		IDomainResourceCollection drc = new DomainResourceCollection();
		drc.open();
		drc.add(concept);
		drc.add(event);
		drc.add(ruleSet);
		drc.add(ruleFunction);
		drc.add(rule);
		drc.add(impl);
		drc.add(catalogFunction);
		drc.add(project);

		acl.setResources(drc);

		//Add permissions
		IResourcePermission readConcept = new ConceptResourcePermission(concept, new ConceptAction("read", Permit.ALLOW, null));

		IResourcePermission readEvent = new EventResourcePermission(event, new EventAction("read", Permit.ALLOW, null));

		IResourcePermission readRuleSet = new RulesetResourcePermission(ruleSet, new RulesetAction("read", Permit.ALLOW, null));
		
		IResourcePermission readRuleFunction = new RuleFunctionResourcePermission(ruleFunction, new RuleFunctionAction("read", Permit.ALLOW, null));
		
		IResourcePermission delRuleFuctionImpl = new RuleFunctionResourcePermission(ruleFunction, new RuleFunctionAction("del_impl", Permit.ALLOW, null));
		
		IResourcePermission addRuleFunctionImpl = new RuleFunctionResourcePermission(ruleFunction, new RuleFunctionAction("add_impl", Permit.ALLOW, null));
		
		IResourcePermission invokeRuleFunctionImpl = new RuleFunctionResourcePermission(ruleFunction, new RuleFunctionAction("invoke", Permit.ALLOW, null));
		
		IResourcePermission readRule = new RuleResourcePermission(rule, new RuleAction("read", Permit.ALLOW, null));
		
		IResourcePermission readCatalogFunction = new FunctionCatalogResourcePermission(catalogFunction, new FunctionsCatalogAction("invoke", Permit.ALLOW, null));
		
		IResourcePermission checkoutProject = new ProjectResourcePermission(project, new ProjectAction("checkout", Permit.ALLOW, null));
		
		IResourcePermission updateProject = new ProjectResourcePermission(project, new ProjectAction("update", Permit.ALLOW, null));
		
		IResourcePermission commitProject = new ProjectResourcePermission(project, new ProjectAction("commit", Permit.ALLOW, null));
		
		PermissionsCollection permissions = new PermissionsCollection();
		permissions.addPermission(readConcept);
		permissions.addPermission(readEvent);
		permissions.addPermission(readRuleSet);
		permissions.addPermission(readRuleFunction);
		permissions.addPermission(delRuleFuctionImpl);
		permissions.addPermission(addRuleFunctionImpl);
		permissions.addPermission(invokeRuleFunctionImpl);
		permissions.addPermission(readRule);
		permissions.addPermission(readCatalogFunction);
		permissions.addPermission(checkoutProject);
		permissions.addPermission(updateProject);
		permissions.addPermission(commitProject);
		
		List<ACLEntry> aclEntries = acl.getACLEntries();
		for (String roleName : roleNameSet) {
			Role role = TokenFactory.INSTANCE.createRole();
			role.setName(roleName);
			ACLEntry aclEntry = new ACLEntryImpl(role);
			aclEntry.setPermissions(permissions);
			aclEntries.add(aclEntry);
		}
		
		return acl;
	}

	private void copyFolder(final File srcFolder,	File destFolder) {
		if (srcFolder.isDirectory()) {
			if (!destFolder.exists()) {
				destFolder.mkdir();
			}

			String[] children = srcFolder.list();
			for (String child : children) {
				copyFolder(new File(srcFolder, child), new File(destFolder, child));
			}
		} else {
			if(destFolder.isDirectory()) {
				copyFile(srcFolder, new File(destFolder, srcFolder.getName()));
			} else {
				copyFile(srcFolder, destFolder);
			}
		}
	}

	private void copyFile(File srcFile,	File destFile) {
		InputStream in = null;
		OutputStream out = null;

		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);
	
			byte[] bytes = new byte[1024];
			int len;
			BufferedInputStream buff = new BufferedInputStream(in);
	
			while ((len = buff.read(bytes)) > 0) {
				out.write(bytes, 0, len);
			}
		}  catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}  catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				in.close();
		        out.close();
			} catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	@Override
	public String[] getFlags() {
		return new String[] { 
			FLAG_ACL_PATH,	
			FLAG_BASE_LOCATION, 
			FLAG_SOURCE_LOCATION, 
			FLAG_RMS_PROJECT_NAME
		};
	}

	public String getUsageFlags() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");
		stringBuilder.append(FLAG_RMS_PROJECT_SETUP_HELP);
		stringBuilder.append("]");
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_RMS_PROJECT_NAME);
		stringBuilder.append(" ");
		stringBuilder.append("<RMS Project Name>");
		stringBuilder.append(" ");
		stringBuilder.append("[");
		stringBuilder.append(FLAG_BASE_LOCATION);
		stringBuilder.append(" ");
		stringBuilder.append("<RMS Base Location>");
		stringBuilder.append("]");
		stringBuilder.append(" ");
		stringBuilder.append("[");
		stringBuilder.append(FLAG_SOURCE_LOCATION);
		stringBuilder.append(" ");
		stringBuilder.append("<Studio Project Location>");
		stringBuilder.append("]");
		stringBuilder.append(" ");		
		stringBuilder.append("[");
		stringBuilder.append(FLAG_ACL_PATH);
		stringBuilder.append("<ACL path>");
		stringBuilder.append("]");
		
		return stringBuilder.toString();
	}

	@Override
	public String getHelp() {
		String helpMsg = "Usage: " + getOperationCategory() + " " + getOperationFlag() + " " + getUsageFlags() + "\n" +
		"where, \n" +
		"	-h (optional) prints this usage \n" +
		"	-projName (required) RMS project name. \n" +
		"	-baseLocation (optional) Base location for project. \n" +
		"	-sourceLocation (optional) Source location for project. \n" +
		"	-aclPath (optional) ACL path. \n";
		return helpMsg;
	}
	
	@Override
	public String getOperationFlag() {
		return OPERATION_PROJECT_SETUP;
	}
	
	@Override
	public String getOperationName() {
		return ("RMS Project Setup");
	}

	@Override
	public boolean runOperation(Map<String, String> argsMap) throws Exception {
		if (checkIfExcludeOperation(argsMap))
			return true;
		
		if (argsMap.containsKey(FLAG_RMS_PROJECT_SETUP_HELP)) {
			System.out.println(getHelp());
			return true;
		}
		System.out.println("Setting up RMS project...");

		this.projectName = argsMap.get(FLAG_RMS_PROJECT_NAME);
		if (projectName == null || projectName.isEmpty()) {
			System.out.println(getHelp());
			throw new Exception("No project name specified");
		}
		
		this.baseLocation = (argsMap.containsKey(FLAG_BASE_LOCATION)) 
									? argsMap.get(FLAG_BASE_LOCATION) : ".";
		this.sourceLocation = (argsMap.containsKey(FLAG_SOURCE_LOCATION)) 
									? argsMap.get(FLAG_SOURCE_LOCATION) : "";
		if (sourceLocation.isEmpty()) sourceLocation = this.baseLocation + separator + this.projectName;
		if (!new File(sourceLocation).exists()){
			throw new Exception("Invalid source location Path");
		}
		
		this.aclPath = (argsMap.containsKey(FLAG_ACL_PATH)) 
									? argsMap.get(FLAG_ACL_PATH) : "";
		
		projectDir = this.baseLocation + separator + this.projectName;
		decisiondataDir = projectDir + separator + DECISION_DATA_DIR_NAME;
		workspaceDir = projectDir + separator + WORKSPACE_DIR_NAME;
		configDir = projectDir + separator + CONFIG_DIR_NAME;
		binDir = projectDir + separator + BIN_DIR_NAME;
		deploymentDir = projectDir + separator + DEPLOYMENT_DIR_NAME;
		
		if (projectDir.equals(sourceLocation)){
			throw new Exception("Project setup location and source directory cannot have same path");
		}
		
		run();
		System.out.println("RMS project is setup successfully...");
		return true;
	}
}

package com.tibco.be.ws.functions;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.tibco.be.ws.scs.IArtifactFilter;
import com.tibco.be.ws.scs.ISCSIntegration;
import com.tibco.be.ws.scs.ISCSLockableIntegration;
import com.tibco.be.ws.scs.SCSConstants;
import com.tibco.be.ws.scs.SCSIntegrationFactory;
import com.tibco.be.ws.scs.impl.filter.DefaultArtifactFilter;
import com.tibco.be.ws.scs.impl.filter.DefaultFilterContext;
import com.tibco.be.ws.scs.impl.filter.IFilterContext;
import com.tibco.cep.security.authz.utils.IOUtils;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 18/11/11
 * Time: 11:14 AM
 * @.category WS.SCS
 */

@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
        category = "WS.SCS",
        synopsis = "Functions For Webstudio Source Control",
        enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.WS.SCS", value=true))

public class WebstudioServerSCSFunctions {

    @com.tibco.be.model.functions.BEFunction(
        name = "executeScript",
        synopsis = "Executes a batch script and returns a string result",
        signature = "String executeScript(String scriptPath, String charset)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "scriptPath", type = "String", desc = "The absolute path of script to be executed."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arguments", type = "String[]", desc = "Optional arguments for script (name=value form)."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "charset", type = "Default", desc = "to UTF-8 if not specified")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Executes a batch script and returns a string result.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String executeScript(String scriptPath, String[] arguments, String charset) {
        if (scriptPath == null) {
            throw new IllegalArgumentException("Script path cannot be null");
        }
        File scriptFile = new File(scriptPath);
        if (!scriptFile.exists()) {
            throw new IllegalArgumentException("Script File " + scriptPath + " does not exist");
        }
        if (!scriptFile.canExecute()) {
            throw new IllegalArgumentException("Cannot execute Script File " + scriptPath);
        }
        try {
            Process process = Runtime.getRuntime().exec(scriptPath, arguments);
            if (process.waitFor() == 0) {
                InputStream inputStream = process.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                IOUtils.writeBytes(inputStream, bos);
                return bos.toString(charset = (charset == null) ? "UTF-8" : charset);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "listManagedProjects",
        synopsis = "Lists projects managed by the WS-Server.",
        signature = "String listManagedProjects(String scsIntegrationType, String repoRootURL, String userName, String password)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsIntegrationType", type = "String", desc = "The type of SCS integration"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "repoRootURL", type = "String", desc = "Base repo URL for the source control system."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "userName", type = "String", desc = "User Name for the SCS repository"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "password", type = "String", desc = "Password for the SCS repository.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Lists projects managed by the WS-Server.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String listManagedProjects(String scsIntegrationType,
                                             String repoRootURL,
                                             String userName,
                                             String password) {
        try {
            ISCSIntegration scsIntegration = SCSIntegrationFactory.INSTANCE.getSCSIntegrationClass(scsIntegrationType);
            return scsIntegration.listManagedProjects(repoRootURL, userName, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "listManagedProjectArtifacts",
        synopsis = "Lists artifacts of a managed project.",
        signature = "String listManagedProjectArtifacts(String scsIntegrationType, String repoRootURL, String projectName, String updatePath, String userName, String password, String artifactFilterClasses, String filterContextClass)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsIntegrationType", type = "String", desc = "The type of SCS integration"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "repoRootURL", type = "String", desc = "Base repo URL for the source control system."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "The project whose artifacts are to be fetched."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "updatePath", type = "String", desc = "The project whose artifacts are to be fetched."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "userName", type = "String", desc = "User Name for the SCS repository"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "password", type = "String", desc = "Password for the SCS repository."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "artifactFilterClasses", type = "String", desc = "FQN of class which can be used to filter out any artifacts if required. Pass null to use default."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filterContextClass", type = "String", desc = "FQN of class which can be used to provide a context to the filter. Pass null to use default.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Lists artifacts of a managed project.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String listManagedProjectArtifacts(String scsIntegrationType,
                                                     String repoRootURL,
                                                     String projectName,
                                                     String updatePath,
                                                     String userName,
                                                     String password,
                                                     String artifactFilterClass,
                                                     String filterContextClass) {
        try {
            ISCSIntegration scsIntegration = SCSIntegrationFactory.INSTANCE.getSCSIntegrationClass(scsIntegrationType);
            if (filterContextClass == null || filterContextClass.isEmpty()) {
                filterContextClass = DefaultFilterContext.class.getName();
            }
            if (artifactFilterClass == null || artifactFilterClass.isEmpty()) {
                artifactFilterClass = DefaultArtifactFilter.class.getName();
            }
            Class<?> filterContextClazz = Class.forName(filterContextClass);
            Object filterContextInstance;
            if (IFilterContext.class.isAssignableFrom(filterContextClazz)) {
                filterContextInstance = filterContextClazz.newInstance();
            } else {
                throw new RuntimeException("Filter class should implement com.tibco.be.ws.scs.filter.IFilterContext");
            }
            Class<?> artifactFilterClazz = Class.forName(artifactFilterClass);
            if (IArtifactFilter.class.isAssignableFrom(artifactFilterClazz)) {
                Object instance = artifactFilterClazz.newInstance();
                return scsIntegration.listManagedProjectArtifacts(repoRootURL, projectName, updatePath, userName, password, (IFilterContext)filterContextInstance, (IArtifactFilter)instance);
            } else {
                throw new RuntimeException("Filter class should implement com.tibco.be.ws.scs.IArtifactFilter");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "showArtifactContents",
        synopsis = "Returns contents of artifacts of a managed project from SCS/Filesystem",
        signature = "String showArtifactContents(String scsIntegrationType,String repoRootURL, String projectName, String artifactPath, String artifactExtn, String userName, String password)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsIntegrationType", type = "String", desc = "The type of SCS integration"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "repoRootURL", type = "String", desc = "Base repo URL for the source control system."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "The project path whose artifacts' contents are to be fetched."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "artifactPath", type = "String", desc = "The FQN of the artifact whose contents are to be retrieved."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "artifactExtn", type = "String", desc = "The extension of the artifact."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "userName", type = "String", desc = "User Name for the SCS repository"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "password", type = "String", desc = "Password for the SCS repository.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns contents of artifacts of a managed project from SCS/Filesystem",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String showArtifactContents(String scsIntegrationType,
                                              String repoRootURL,
                                              String projectName,
                                              String artifactPath,
                                              String artifactExtn,
                                              String userName, 
                                              String password) {
        try {
            ISCSIntegration scsIntegration = SCSIntegrationFactory.INSTANCE.getSCSIntegrationClass(scsIntegrationType);
            return scsIntegration.showFileContents(repoRootURL, projectName, artifactPath, artifactExtn, userName, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "commitFileContents",
        synopsis = "Writes contents of artifact of a managed project to SCS/Filesystem.",
        signature = "void commitFileContents(String scsIntegrationType, String repoRootURL, String projectName, String artifactPath, String artifactExtn, Object artifactContents, String commitComments, Long revisionId)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsIntegrationType", type = "String", desc = "The type of SCS integration"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "repoRootURL", type = "String", desc = "Base repo URL for the source control system."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "The project path whose artifacts' contents are to be fetched."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "artifactPath", type = "String", desc = "The FQN of the artifact whose contents are to be retrieved."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "artifactExtn", type = "String", desc = "The extension of the artifact."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "artifactContents", type = "Object", desc = "The byte[] of the artifact to be committed."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "commitComments", type = "String", desc = "Comments associated with this change"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "revisionId", type = "Long", desc = "Revision Id associated to this change")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Writes contents of artifact of a managed project to SCS/Filesystem",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static void commitFileContents(String scsIntegrationType,
                                          String repoRootURL,
                                          String projectName,
                                          String artifactPath,
                                          String artifactExtn,
                                          Object artifactContents,
                                          String commitComments,
                                          Long revisionId) {
        if (!(artifactContents instanceof byte[])) {
            throw new IllegalArgumentException("Contents should be array of bytes");
        }
        try {
            ISCSIntegration scsIntegration = SCSIntegrationFactory.INSTANCE.getSCSIntegrationClass(scsIntegrationType);
            scsIntegration.commitFileContents(repoRootURL, projectName, artifactPath, artifactExtn, (byte[])artifactContents, commitComments, revisionId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "deleteFile",
        synopsis = "Deletes artifact of a managed project from SCS/Filesystem.",
        signature = "void deleteFile(String scsIntegrationType, String repoRootURL, String projectName, String artifactPath, String artifactExtn, String deleteComments, Long revisionId)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsIntegrationType", type = "String", desc = "The type of SCS integration"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "repoRootURL", type = "String", desc = "Base repo URL for the source control system."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "The project path whose artifacts' contents are to be fetched."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "artifactPath", type = "String", desc = "The FQN of the artifact whose contents are to be retrieved."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "artifactExtn", type = "String", desc = "The extension of the artifact."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "deleteComments", type = "String", desc = "Comments associated with this commit."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "revisionId", type = "Long", desc = "Revision Id associated to this change")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Deletes artifact of a managed project from SCS/Filesystem.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static void deleteFile(String scsIntegrationType,
                                     String repoRootURL,
                                     String projectName,
                                     String artifactPath,
                                     String artifactExtn,
                                     String deleteComments,
                                     Long revisionId) {
        try {
            ISCSIntegration scsIntegration = SCSIntegrationFactory.INSTANCE.getSCSIntegrationClass(scsIntegrationType);
            scsIntegration.deleteFile(repoRootURL, projectName, artifactPath, artifactExtn, deleteComments, revisionId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @com.tibco.be.model.functions.BEFunction(
		name = "checkoutProjectArtifacts",
		synopsis = "Checkout managed project from SCS/Filesystem.",
		signature = "boolean checkoutProjectArtifacts(String scsIntegrationType, String repoRootURL, String projectName, String artifactPath, String artifactExtn, String userName, String password)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsIntegrationType", type = "String", desc = "The type of SCS integration"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "repoRootURL", type = "String", desc = "Base repo URL for the source control system."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "The project path whose artifacts' contents are to be fetched."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "destinationDirectory", type = "String", desc = "Destination directory where the SCS project contents are copied to."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "userName", type = "String", desc = "User Name for the SCS repository"),
	            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "password", type = "String", desc = "Password for the SCS repository.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = ""),
		version = "5.1.2",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Checkout managed project from SCS/Filesystem.",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
    public static boolean checkoutProjectArtifacts(String scsIntegrationType,
									    		String repoRootURL,
									    		String projectName,
									    		String destinationDirectory,
									    		String userName,
									    		String password) {
    	try {
    		ISCSIntegration scsIntegration = SCSIntegrationFactory.INSTANCE.getSCSIntegrationClass(scsIntegrationType);
    		return scsIntegration.checkoutProjectArtifacts(repoRootURL, projectName, destinationDirectory, userName, password);
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    }
    
    @com.tibco.be.model.functions.BEFunction(
		name = "setSCSCommandPath",
		synopsis = "Set the SCS command path.",
		signature = "void setSCSCommandPath(String scsIntegrationType, String repoRootURL, String command)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsIntegrationType", type = "String", desc = "The type of SCS integration"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "repoRootURL", type = "String", desc = "Base repo URL for the source control system."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "command", type = "String", desc = "The SCS command path.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.1.2",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Set the SCS command path..",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
    public static void setSCSCommandPath(String scsIntegrationType,
    		String repoRootURL,
    		String command) {
    	try {
    		ISCSIntegration scsIntegration = SCSIntegrationFactory.INSTANCE.getSCSIntegrationClass(scsIntegrationType);
    		scsIntegration.setSCSCommandPath(command);
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    }
    
    @com.tibco.be.model.functions.BEFunction(
		name = "flushContentsToSCS",
		synopsis = "Flushes the changes to SCS repository.",
		signature = "String flushContentsToSCS(String scsIntegrationType, String repoRootURL, String command)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsIntegrationType", type = "String", desc = "The type of SCS integration"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "repoRootURL", type = "String", desc = "The type of SCS integration"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "revisionId", type = "Long", desc = "Base repo URL for the source control system."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "userName", type = "String", desc = "User Name for the SCS repository"),
	            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "password", type = "String", desc = "Password for the SCS repository.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "SCS commit revision, applicable only for repository based SCS"),
		version = "5.1.2",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Set the SCS command path..",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
    public static String flushContentsToSCS(String scsIntegrationType,
    		String repoRootURL, 
    		Long revisionId, 
    		String userName, 
    		String password) {
    	try {
    		ISCSIntegration scsIntegration = SCSIntegrationFactory.INSTANCE.getSCSIntegrationClass(scsIntegrationType);
    		return scsIntegration.flushContentsToSCS(repoRootURL, revisionId, userName, password);
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    }

    @com.tibco.be.model.functions.BEFunction(
		name = "lockArtifact",
		synopsis = "Locks the given artifact in the SCS",
		signature = "void lockArtifact(String scsIntegrationType, String repoRootURL, String projectName, String artifactPath, String artifactExtn, String userName, String password)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsIntegrationType", type = "String", desc = "The type of SCS integration"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "repoRootURL", type = "String", desc = "Base repo URL for the source control system."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "The project path whose artifacts' contents are to be fetched."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "artifactPath", type = "String", desc = "The FQN of the artifact whose contents are to be retrieved."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "artifactExtn", type = "String", desc = "The extension of the artifact."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "userName", type = "String", desc = "User Name for the SCS repository"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "password", type = "String", desc = "Password for the SCS repository."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "rmsOperation", type = "String", desc = "The RMS operation type (Approve/Reject/etc)")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = ""),
		version = "5.1.3",
		see = "",
		fndomain = {ACTION},
		description = "Locks the given artifact in the SCS",
		cautions = "",
		domain = "",
		example = ""
	)
    public static boolean lockArtifact(String scsIntegrationType,
    		String repoRootURL,
    		String projectName,
    		String artifactPath,
    		String artifactExtn,
    		String userName,
    		String password) {
    	try {
    		if (isImplicitLock()) {
    			ISCSIntegration scsIntegration = SCSIntegrationFactory.INSTANCE.getSCSIntegrationClass(scsIntegrationType);
    			if (scsIntegration instanceof ISCSLockableIntegration) {
    				return ((ISCSLockableIntegration) scsIntegration).lockArtifact(repoRootURL, userName, password, projectName, artifactPath, artifactExtn);
    			}
    		}
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    	return true; // return true by default to avoid throwing an error
    }

    private static boolean isImplicitLock() {
		return "true".equalsIgnoreCase(System.getProperty(SCSConstants.PROP_SCS_IMPLICIT_LOCK, "false"));
	}

    @com.tibco.be.model.functions.BEFunction(
		name = "unlockArtifact",
		synopsis = "Unlocks the given artifact in the SCS",
		signature = "void unlockArtifact(String scsIntegrationType, String repoRootURL, String projectName, String artifactPath, String artifactExtn, String userName, String password)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsIntegrationType", type = "String", desc = "The type of SCS integration"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "repoRootURL", type = "String", desc = "Base repo URL for the source control system."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "The project path whose artifacts' contents are to be fetched."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "artifactPath", type = "String", desc = "The FQN of the artifact whose contents are to be retrieved."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "artifactExtn", type = "String", desc = "The extension of the artifact."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "userName", type = "String", desc = "User Name for the SCS repository"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "password", type = "String", desc = "Password for the SCS repository."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "rmsOperation", type = "String", desc = "The RMS operation type (Approve/Reject/etc)")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = ""),
		version = "5.1.3",
		see = "",
        fndomain = {ACTION},
		description = "Unlocks the given artifact in the SCS",
		cautions = "",
		domain = "",
		example = ""
	)
    public static boolean unlockArtifact(String scsIntegrationType,
    		String repoRootURL,
    		String projectName,
    		String artifactPath,
    		String artifactExtn,
    		String userName,
    		String password,
    		String rmsOperation) {
    	try {
    		if (isImplicitLock()) {
    			ISCSIntegration scsIntegration = SCSIntegrationFactory.INSTANCE.getSCSIntegrationClass(scsIntegrationType);
    			if (scsIntegration instanceof ISCSLockableIntegration) {
    				return ((ISCSLockableIntegration) scsIntegration).unlockArtifact(repoRootURL, userName, password, projectName, artifactPath, artifactExtn, rmsOperation);
    			}
    		}
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    	return true;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getExistingLockInfo",
        synopsis = "Gets any lock information pertaining to the given artifact",
        signature = "void getExistingLockInfo(String scsIntegrationType, String repoRootURL, String projectName, String artifactPath, String artifactExtn, String userName, String password)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsIntegrationType", type = "String", desc = "The type of SCS integration"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "repoRootURL", type = "String", desc = "Base repo URL for the source control system."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "The project path whose artifacts' contents are to be fetched."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "artifactPath", type = "String", desc = "The FQN of the artifact whose contents are to be retrieved."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "artifactExtn", type = "String", desc = "The extension of the artifact."),
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "userName", type = "String", desc = "User Name for the SCS repository"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "password", type = "String", desc = "Password for the SCS repository.")
            },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
        version = "5.1.3",
        see = "",
    	fndomain = {ACTION},
        description = "Gets any lock information pertaining to the given artifact",
        cautions = "",
        domain = "",
        example = ""
    )
    public static String getExistingLockInfo(String scsIntegrationType,
                                          String repoRootURL,
                                          String projectName,
                                          String artifactPath,
                                          String artifactExtn,
                                          String userName,
                                          String password) {
        try {
        	if (isImplicitLock()) {
        		ISCSIntegration scsIntegration = SCSIntegrationFactory.INSTANCE.getSCSIntegrationClass(scsIntegrationType);
        		if (scsIntegration instanceof ISCSLockableIntegration) {
        			return ((ISCSLockableIntegration) scsIntegration).getExistingLockInfo(repoRootURL, userName, password, projectName, artifactPath, artifactExtn);
        		}
        	}
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
		name = "getExistingLockOwner",
		synopsis = "Gets the current owner of the lock on the given artifact, or null if there is no lock associated with the artifact",
		signature = "void getExistingLockOwner(String scsIntegrationType, String repoRootURL, String projectName, String artifactPath, String artifactExtn, String userName, String password)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsIntegrationType", type = "String", desc = "The type of SCS integration"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "repoRootURL", type = "String", desc = "Base repo URL for the source control system."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "The project path whose artifacts' contents are to be fetched."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "artifactPath", type = "String", desc = "The FQN of the artifact whose contents are to be retrieved."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "artifactExtn", type = "String", desc = "The extension of the artifact."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "userName", type = "String", desc = "User Name for the SCS repository"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "password", type = "String", desc = "Password for the SCS repository.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The owner of the lock on the artifact"),
		version = "5.1.3",
		see = "",
        fndomain = {ACTION},
		description = "Gets the current owner of the lock on the given artifact, if any",
		cautions = "",
		domain = "",
		example = ""
	)
    public static String getExistingLockOwner(String scsIntegrationType,
    		String repoRootURL,
    		String projectName,
    		String artifactPath,
    		String artifactExtn,
    		String userName,
    		String password) {
    	try {
    		if (isImplicitLock()) {
    			ISCSIntegration scsIntegration = SCSIntegrationFactory.INSTANCE.getSCSIntegrationClass(scsIntegrationType);
    			if (scsIntegration instanceof ISCSLockableIntegration) {
    				return ((ISCSLockableIntegration) scsIntegration).getExistingLockOwner(repoRootURL, userName, password, projectName, artifactPath, artifactExtn);
    			}
    		}
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    	return null;
    }
    
    @com.tibco.be.model.functions.BEFunction(
		name = "revertChangesFromSCS",
		signature = "String revertChangesFromSCS(String scsIntegrationType, String repoRootURL, Long revisionId, String userName, String password)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsIntegrationType", type = "String", desc = "The type of SCS integration"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "repoRootURL", type = "String", desc = "Base repo URL for the source control system."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "revisionId", type = "Long", desc = "Revision Id to revert"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "userName", type = "String", desc = "User Name for the SCS repository"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "password", type = "String", desc = "Password for the SCS repository.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.4.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Reverts the changes from SCS repository",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
    public static void revertChangesFromSCS(String scsIntegrationType,
    		String repoRootURL, 
    		Long revisionId, 
    		String userName, 
    		String password) {
    	try {
    		ISCSIntegration scsIntegration = SCSIntegrationFactory.INSTANCE.getSCSIntegrationClass(scsIntegrationType);
    		scsIntegration.revertChangesFromSCS(repoRootURL, revisionId, userName, password);
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    }
    
    @com.tibco.be.model.functions.BEFunction(
		name = "updateProjectArtifacts",
		signature = "String updateProjectArtifacts(String scsIntegrationType, String repoRootURL, String projectName, String userName, String password)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsIntegrationType", type = "String", desc = "The type of SCS integration"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "repoRootURL", type = "String", desc = "Base repo URL for the source control system."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "Project to update"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "userName", type = "String", desc = "User Name for the SCS repository"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "password", type = "String", desc = "Password for the SCS repository.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.4.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Update the specified project",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
    public static void updateProjectArtifacts(String scsIntegrationType,
    		String repoRootURL,
    		String projectName, 
    		String userName, 
    		String password) {
    	try {
    		ISCSIntegration scsIntegration = SCSIntegrationFactory.INSTANCE.getSCSIntegrationClass(scsIntegrationType);
    		scsIntegration.updateProjectArtifacts(repoRootURL, projectName, userName, password);
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    }
}

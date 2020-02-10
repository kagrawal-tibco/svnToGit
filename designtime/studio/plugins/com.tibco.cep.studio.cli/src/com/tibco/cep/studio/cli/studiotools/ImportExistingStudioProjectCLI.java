package com.tibco.cep.studio.cli.studiotools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EPackage;



import com.tibco.cep.designtime.core.model.domain.DomainPackage;
import com.tibco.cep.studio.common.configuration.XPATH_VERSION;
import com.tibco.cep.studio.core.StudioCore;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessingUnit;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.core.util.StudioProjectMigrationUtils;
import com.tibco.cep.studio.core.util.StudioProjectUtil;

/*
@author rhollom
 */
public class ImportExistingStudioProjectCLI extends CoreCLI {

	// Import Previous Existing BE Project
	private final static String OPERATION_IMPORT_EXISTING_PROJECT = "importExistingProject";
	private final static String FLAG_IMPORT_HELP = "-h";	// Path of project in Studio
	private final static String FLAG_IMPORT_STUDIO_PROJECT = "-p";	// Path of project in Studio
	private final static String FLAG_TARGET_DIR	= "-o";	// The target path of the project
	private final static String CDD_FILE_LOCATION	= "-c";	// CDD target location
	private final static String PROCESSING_UNIT_NAME	= "-u";	// CDD PU name
	private final static String FLAG_XPATH_VERSION	= "-xp";	// xpath version
	
	final static String CONNECTION_TIMEOUT = "connection-timeout";
	final static String ACCEPT_COUNT = "accept-count";
	final static String CONNECTION_LINGER = "connection-linger";
	final static String SOCKET_OUTPUT_BUFFER_SIZE = "socket-output-buffer-size";
	final static String TRUST_MANAGER_ALGORITHM = "trust-manager-algorithm";
	final static String KEY_MANAGER_ALGORITHM = "key-manager-algorithm";
	final static String MAX_PROCESSORS = "max-processors";
	final static String TCP_NO_DELAY = "tcp-no-delay";
	final static String DOCUMENT_PAGE = "document-page";
	final static String DOCUMENT_ROOT = "document-root";
	final static String STALE_CONNECTION_CHECK = "stale-connection-check";
	
	final static String HTTP_DRIVER_TYPE_NAME = "driverTypeName=\"HTTP\"";
	final static String HTTP_DRIVER_TYPE = "driverType=\"HTTP\"";

	private String defaultProcessingUnitName = null;
	private File cddFile = null;
	
	static {
        final EPackage.Registry i = EPackage.Registry.INSTANCE;
		i.put("http:///com/tibco/cep/designtime/core/model/domain", DomainPackage.eINSTANCE);
	}
	
	public ImportExistingStudioProjectCLI() {
	}
	
	@Override
	public String[] getFlags() {
		return new String[] { FLAG_IMPORT_HELP, FLAG_IMPORT_STUDIO_PROJECT, FLAG_TARGET_DIR };
	}
	
	@Override
	public String getHelp() {
		String helpMsg = "Usage: studio-tools " + getOperationCategory() + " " + getOperationFlag() + " " + getUsageFlags() + "\n" +
			"where \n" +
			"    -h (optional) prints this usage \n" +
			"    -p is the absolute path to the directory of the Studio project to be imported \n" +
			"    -o (optional) is the absolute path to the target directory of the Studio project to be imported.  If unspecified, the original project contents will be updated and the project will no longer be compatible with older versions of TIBCO BusinessEventss\n" + 
		    "    -c (optional) is relative path to a CDD of Studio project to be imported. If unspecified, one found first will be used\n" +
		    "    -u (optional) is name of Processing Unit within specified CDD, whose httpProperties will be migrated to HTTP Channel if present. If unspecified, one found first will be used\n" +
		    "    -xp {1.0|2.0} (optional) is the xpath version to be compatible for the Studio project\n" ; 
		return helpMsg;
	}
	
	@Override
	public String getOperationFlag() {
		return OPERATION_IMPORT_EXISTING_PROJECT;
	}
	
	@Override
	public String getOperationName() {
		return ("Import Existing TIBCO BusinessEvents Studio Project");
	}
	
	@Override
	public String getUsageFlags() {
		return ("[" + FLAG_IMPORT_HELP + "] " +
			FLAG_IMPORT_STUDIO_PROJECT + " <studioProjDir> " +
			"[" + FLAG_TARGET_DIR + " <targetProjDir>] " + CDD_FILE_LOCATION + " <CDD Name> " + PROCESSING_UNIT_NAME + " <Processing Unit Name>" );
	}
	
	@Override
	public boolean runOperation(Map<String, String> argsMap) throws Exception {
		if (checkIfExcludeOperation(argsMap))
			return true;
		
		if (argsMap.containsKey(FLAG_IMPORT_HELP)) {
			System.out.println(getHelp());
			return true;
		}

		String studioProj = argsMap.get(FLAG_IMPORT_STUDIO_PROJECT);
		if (studioProj == null) {
			throw new Exception(Messages.getString("Import.ProjectPath.Invalid"));
		}

		File studioProjDir = new File(studioProj);
		if (!studioProjDir.exists()) {
			throw new Exception(Messages.getString("Import.ProjectPath.Invalid"));
		}
		
		String targetDir = argsMap.get(FLAG_TARGET_DIR);
		File tdDest = null;
		if(targetDir != null){
			tdDest = new File(targetDir);
		}
		
		String xpathv = argsMap.get(FLAG_XPATH_VERSION);
		XPATH_VERSION xpathversion = null;
		if (xpathv != null) {
			xpathversion = XPATH_VERSION.get(xpathv);
			if (xpathversion == null) {
				throw new Exception(Messages.getString("Import.xpath.Invalid"));
			}
		}
		
		String projectName = new File(studioProj).getName();
		IWorkspace workspace= ResourcesPlugin.getWorkspace(); 
		IWorkspaceDescription desc= workspace.getDescription(); 
		boolean isAutoBuilding= desc.isAutoBuilding(); 
		if (isAutoBuilding) { 
			desc.setAutoBuilding(false); 
			workspace.setDescription(desc); 
		} 
		IProject studioProjectInstance = workspace.getRoot().getProject(projectName);
		boolean isDesignerProjectImport = false;
		
		File newSource=null;
		File targetDirFile = null;
		if (targetDir != null) {
			targetDirFile = new File(targetDir);
			if (!targetDirFile.getName().equals(studioProjDir.getName())) {
				// append the project name to the path, this is what is expected
				targetDirFile = new File(targetDirFile, studioProjDir.getName());
			} else {
				isDesignerProjectImport =  true;
			}
			// check for existing resources
			if (targetDirFile.exists() && targetDirFile.list().length > 0) {
				throw new Exception(Messages.getString("Import.Existing.TargetPath.Exists"));
			}
			// check for overlapping target/source location
			if (targetDirFile.getAbsolutePath().startsWith(studioProjDir.getAbsolutePath())) {
				throw new Exception(Messages.getString("Import.Existing.TargetPath.Overlaps"));
			}
		} else {
			targetDirFile = studioProjDir;
			String srcPath = studioProjDir.getAbsolutePath();
			srcPath = srcPath + "_old";
			newSource = new File(srcPath);
			if (!newSource.exists()) {
				newSource.mkdirs();
			}
			StudioProjectMigrationUtils.copyProjectFiles(studioProjDir, newSource, new NullProgressMonitor());
			StudioProjectMigrationUtils.deleteDir(studioProjDir);
			studioProjDir.delete();
			StudioProjectUtil.createProject(studioProjectInstance, null, new NullProgressMonitor(), new File(studioProjDir.getParent()), isDesignerProjectImport, true, false, xpathversion);
			StudioProjectMigrationUtils.copyProjectFiles(newSource, studioProjDir, new NullProgressMonitor());
			StudioProjectMigrationUtils.deleteDir(newSource);
			newSource.delete();
		}
		
		//Set the transformer for AIX
		if(System.getProperty("os.name").equals("AIX") || System.getProperty("os.arch").equals("s390x")){
			System.setProperty("javax.xml.transform.TransformerFactory","org.apache.xalan.processor.TransformerFactoryImpl");
		}

		getCDDAndPUName(studioProjDir, argsMap);
		
		File beprojFile = new File(studioProj, ".beproject");
		if (!beprojFile.exists()) {
			throw new Exception(Messages.getString("Import.Existing.ProjectPath.Invalid"));
		}

		try {
			if (targetDir != null) {
				if (!targetDirFile.exists()) {
					targetDirFile.mkdirs();
				}
				StudioProjectUtil.createProject(studioProjectInstance, null, new NullProgressMonitor(), tdDest, isDesignerProjectImport, true, false, xpathversion);
				StudioProjectMigrationUtils.copyProjectFiles(studioProjDir, targetDirFile, new NullProgressMonitor());
			} 
			Map<String, String> httpProperties = null;
			if(cddFile != null) { 
				httpProperties = retriveHTTPPropertiesFromCDD(cddFile, defaultProcessingUnitName);
				final String relativeLocationOfCDD = studioProjDir.getAbsoluteFile().toURI().relativize(cddFile.getAbsoluteFile().toURI()).toString();
				System.out.println("\n" + Messages.formatMessage("Import.Existing.Project.UsingCDDAndPU", defaultProcessingUnitName, relativeLocationOfCDD));
			} 
			StudioProjectMigrationUtils.convertStudioProject(targetDirFile, httpProperties, xpathversion, new NullProgressMonitor());
			
			File projFile = new File(targetDirFile, ".project");
			if (!projFile.exists()) {
				throw new Exception(Messages.getString("Import.Existing.EclipseProjectPath.Invalid"));
			}

			System.out.println("\n" + Messages.formatMessage("Import.Existing.Project.Success", targetDirFile.getAbsolutePath()));
			if (targetDir == null) {
				System.out.println(Messages.getString("Import.Existing.Project.Incompatible.Warning"));
			}
			
		} catch (Exception e) {
			throw new Exception(Messages.getString("Import.Existing.Project.Error") + " - " + e.getMessage(), e);
		}
		finally{
			if(studioProjectInstance!=null && studioProjectInstance.exists()){
				studioProjectInstance.close(null);
				studioProjectInstance.delete(true, null);
			}
		}
		
		return true;
	}

	
	private void getCDDAndPUName(final File studioProjectDir,
			final Map<String, String> argsMap) throws Exception {
		
		if(!isHTTPChannelPresent(studioProjectDir)) {
			return;
		}
		String cddLocation = argsMap.get(CDD_FILE_LOCATION);
		boolean userSpecifiedCDD = false;
		if (cddLocation == null) {
			cddLocation = findCDDFile(studioProjectDir);
		} else {
			userSpecifiedCDD = true;
		}
		
		defaultProcessingUnitName = argsMap.get(PROCESSING_UNIT_NAME);
		if (defaultProcessingUnitName == null && userSpecifiedCDD) {
			throw new Exception(Messages.getString("Import.PUName.Invalid"));
		}
		
		cddFile = new File(studioProjectDir.getAbsolutePath() + Path.SEPARATOR + cddLocation);
		if (!cddFile.exists() && userSpecifiedCDD) {
			throw new Exception(Messages.getString("Import.CDDLocation.Invalid"));
		} 
		
		if(cddLocation == null) {
			cddFile = null;
		}
		
	}

	private boolean isHTTPChannelPresent(final File studioProjectDir) {
		final FileFilter channelFileFilter = new FileFilter(){
			@Override
			public boolean accept(File name) {
				return (name.toString().endsWith(".channel"));
			}
		};
		File channel = getChannelName(studioProjectDir, channelFileFilter);
		if(channel != null) {
			return isHTTPChannel(channel);
		}
		for(final File file:studioProjectDir.listFiles()) {
			if(file.isDirectory()) {
				channel = getChannelName(file, channelFileFilter);
				if(channel != null) {
					return isHTTPChannel(channel);
				}
			}
		}
		return false;
	}

	//hack as we can't read 4.0 channel as a model resource 
	private boolean isHTTPChannel(final File channel) {
		final String fileContents = readFileAsString(channel);
		boolean contains = fileContents.contains(HTTP_DRIVER_TYPE);
		if(!contains) {
			contains = fileContents.contains(HTTP_DRIVER_TYPE_NAME);
		}
		return contains;
	}
	
	private String readFileAsString(File file){
	    byte[] buffer = new byte[(int) file.length()];
	    BufferedInputStream f = null;
	    try {
	        f = new BufferedInputStream(new FileInputStream(file));
	        try {
				f.read(buffer);
			} catch (IOException ignore) {
			}
	    } catch (FileNotFoundException ignore) {

	    } finally {
	        if (f != null) {
	        	try { f.close(); } catch (IOException ignored) { }
	        }
	    }
	    return new String(buffer);
	}

	private File getChannelName(File directory, FileFilter channelFileFilter) {
		final File[] fileList = directory.listFiles(channelFileFilter);
		if(fileList.length != 0 ) {
			return fileList[0]; //first found CDD will be used
		}
		return null;
	}

	private String findCDDFile(final File studioProjectDir) {
		final FilenameFilter cddFileFilter = new FilenameFilter(){
			@Override
			public boolean accept(File dir, String name) {
				return (name.endsWith(".cdd"));
			}
		};
		String cddLocation = getCDDName(studioProjectDir, cddFileFilter);
		if(cddLocation != null) {
			return cddLocation;
		}
		for(final File file:studioProjectDir.listFiles()) {
			if(file.isDirectory()) {
				cddLocation = getCDDName(file, cddFileFilter);
				if(cddLocation != null) {
					return cddLocation;
				}
			}
		}
		
		return null;
	}

	private String getCDDName(final File directory, final FilenameFilter cddFileFilter) {
		final String[] fileList = directory.list(cddFileFilter);
		if(fileList.length != 0 ) {
			return fileList[0]; //first found CDD will be used
		}
		return null;
	}

	/**
	 * @param puName 
	 * @param cddFile 
	 * @return httpProperties
	 * @throws Exception 
	 */
	private Map<String, String> retriveHTTPPropertiesFromCDD(final File cddFile, String puName) throws Exception {
		final ClusterConfigModelMgr cddModelMgr = new ClusterConfigModelMgr(null, cddFile.getAbsolutePath());
		cddModelMgr.parseModel();
		List<ProcessingUnit> processingUnits = cddModelMgr.getProcessingUnits();
		Map<String, String> httpProperties = null;
		boolean userSpecifiedPU = false;
		for(final ProcessingUnit pu:processingUnits) {
			if(puName == null) {
				httpProperties = getHTTPProperties(pu);
				defaultProcessingUnitName = pu.getName();
				break;
			}else if(pu.getName().equals(puName)) {
				userSpecifiedPU = true;
				httpProperties = getHTTPProperties(pu);
				break;
			}
		}
		if(userSpecifiedPU && (httpProperties == null )) { //error out only if user specifies PU
			throw new Exception(Messages.getString("Import.PUName.Invalid"));
		}
		return httpProperties;
	}

	private Map<String, String> getHTTPProperties(final ProcessingUnit pu) {
		Map<String, String> httpProperties = new HashMap<String, String>();
		Map<String, String> properties = pu.httpProperties.properties;
		for(Map.Entry<String, String> entry:properties.entrySet()) {
			final String key = entry.getKey();
			if(key.equals(CONNECTION_TIMEOUT)) {
				httpProperties.put("be.http.connectionTimeout", entry.getValue());
			} else if(key.equals(ACCEPT_COUNT)){
				httpProperties.put("be.http.acceptCount", entry.getValue());
			} else if(key.equals(CONNECTION_LINGER)){
				httpProperties.put("be.http.connectionLinger", entry.getValue());
			} else if(key.equals(SOCKET_OUTPUT_BUFFER_SIZE)){
				httpProperties.put("be.http.socketBufferSize", entry.getValue());
			} else if(key.equals(MAX_PROCESSORS)){
				httpProperties.put("be.http.maxProcessors", entry.getValue());
			} else if(key.equals(TCP_NO_DELAY)){
				httpProperties.put("be.http.tcpNoDelay", entry.getValue());
			} else if(key.equals(DOCUMENT_PAGE)){
				httpProperties.put("be.http.docPage", entry.getValue());
			} else if(key.equals(DOCUMENT_ROOT)){
				httpProperties.put("be.http.docRoot", entry.getValue());
			} else if(key.equals(STALE_CONNECTION_CHECK)){
				httpProperties.put("be.http.async.staleConnectionCheck", entry.getValue());
			} 
		}
		for(Map.Entry<String, String> entry:pu.httpProperties.ssl.properties.entrySet()) {
			final String key = entry.getKey();
			if(key.equals(KEY_MANAGER_ALGORITHM)) {
				httpProperties.put("be.http.ssl_server_keymanageralgorithm", entry.getValue());
			} else if (key.equals(TRUST_MANAGER_ALGORITHM)) {
				httpProperties.put("be.http.ssl_server_trustmanageralgorithm", entry.getValue());
			}
		}
		final StringBuffer ciphers = new StringBuffer();
		for(String cipher:pu.httpProperties.ssl.ciphers) {
			ciphers.append(cipher);
			ciphers.append(",");
		}
		if(ciphers.length() > 0) {
			ciphers.replace(ciphers.length()-1, ciphers.length(), "");
			httpProperties.put("be.http.ssl_server_ciphers", ciphers.toString());
		} else {	// BE-27283 - set default value for be.http.ssl_server_ciphers
			httpProperties.put("be.http.ssl_server_ciphers", StudioCore.HTTP_SSL_SERVER_CIPHERS);
		}
		final StringBuffer protocols = new StringBuffer();
		for(String protocol:pu.httpProperties.ssl.protocols) {
			// BE-27283 - replace TLSv1 w/ TLSv1.3 to disable/disallow support for weaker ciphers
			if (protocol.equalsIgnoreCase("TLSv1")) {
				protocols.append("TLSv1.3");
			} else {
				protocols.append(protocol);
			}
			protocols.append(",");
		}
		if(protocols.length() > 0) {
			protocols.replace(protocols.length()-1, protocols.length(), "");
			httpProperties.put("be.http.ssl_server_enabledprotocols", protocols.toString());
		} else {	// BE-27283 - set default value for be.http.ssl_server_enabledprotocols
			httpProperties.put("be.http.ssl_server_enabledprotocols", StudioCore.HTTP_SSL_SERVER_ENABLED_PROTOCOLS);
		}
		return httpProperties;
	}
    
}

/**
 * 
 */
package com.tibco.cep.liveview.cli;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.tibco.cep.liveview.project.LiveViewProjectGenerator;
import com.tibco.cep.studio.cli.studiotools.BuildEarCLI;
import com.tibco.cep.studio.cli.studiotools.CoreCLI;
import com.tibco.cep.studio.cli.studiotools.StudioCommandLineInterpreter;
import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.core.index.model.DesignerProject;

/**
 * @author vpatil
 *
 */
public class LiveViewProjectGeneratorCLI extends CoreCLI {
	
	private final static String OPERATION_GENERATELDMPROJECT = "generateLDMProject";
	private final static String FLAG_GENERATELDMPROJECT_HELP = "-h"; 	// Prints help
	private final static String FLAG_GENERATELDMPROJECT_CDD_PATH = "-c"; // cdd path
	private final static String FLAG_GENERATELDMPROJECT_OUTPUT_PATH = "-o"; // LDM project output path
	private final static String FLAG_GENERATELDMPROJECT_EARPATH = "-e"; // BE project EAR path
	private final static String FLAG_GENERATELDMPROJECT_BE_PROJECTNAME = "-p"; // BE project Name
	
		
	public static void main(String[] args) throws Exception {
		if (args.length == 0 || 
				(args.length == 1 && args[0].trim().endsWith("help"))) {
			StudioCommandLineInterpreter.printPrologue(new String[]{});
			System.out.println(new BuildEarCLI().getHelp());
			return;
		}
		String newArgs[] = new String[args.length+2];
		newArgs[0] = CoreCLI.OPERATION_CATEGORY_CORE;
		newArgs[1] = OPERATION_GENERATELDMPROJECT; 
		System.arraycopy(args, 0, newArgs, 2, args.length);
		StudioCommandLineInterpreter.executeCommandLine(newArgs);
	}
	
	@Override
	public String getHelp() {
		String helpMsg = "Usage: generateLDMProject " + getUsageFlags() + "\n" +
				"where, \n" +
				"	-h (optional) prints this usage \n" +
				"	-o (optional) Output path for the generated project \n" +
				"	-c BE project cdd path \n" +
				"	-p BE project path";
        return helpMsg;
	}
	
	@Override
	public boolean runOperation(Map<String, String> argsMap) throws Exception {
		if (checkIfExcludeOperation(argsMap))
			return true;
		
		if (argsMap.containsKey(FLAG_GENERATELDMPROJECT_HELP)) {
			System.out.println(getHelp());
			return true;
		}
		
		String projectName = argsMap.get(FLAG_GENERATELDMPROJECT_BE_PROJECTNAME);
		if (projectName == null) {
			throw new Exception("Invalid BE Project Name");
		}
		
		System.out.println("Generating LDM project for [" + projectName + "]");
		
		String archiveFile = argsMap.get(FLAG_GENERATELDMPROJECT_EARPATH);
		if (archiveFile == null) {
			throw new Exception("Invalid BE EAR Path");
		}
		if (!new File(archiveFile).exists()) {
			throw new Exception("BE EAR Path does not exist.");
		}
		
		String ldmOutputPath = argsMap.get(FLAG_GENERATELDMPROJECT_OUTPUT_PATH);
		if (ldmOutputPath != null) {
			File ldmOutputFile = new File(ldmOutputPath);
			if (!ldmOutputFile.exists()) {
				ldmOutputFile.mkdirs();
			}
		}
		
		String cddPath = argsMap.get(FLAG_GENERATELDMPROJECT_CDD_PATH);
		if (cddPath == null) {
			throw new Exception("Invalid Cdd Path");
		}
		if (!new File(cddPath).exists()) {
			throw new Exception("Cdd Path does not exist.");
		}
		
		loadProjectIndex(projectName, archiveFile);
		
		IProject iProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		ClusterConfigModelMgr ccMM = new ClusterConfigModelMgr(iProject, cddPath);
		ccMM.parseModel();
		
		// get the default path set in cdd
		if (ldmOutputPath == null) {
			ldmOutputPath = ccMM.getLDMProjectOutputPath();
		}
		
		generateLDMProject(ccMM, iProject, ldmOutputPath);
		
		System.out.println("\nLDM Project successfully generated at [" + ldmOutputPath + File.separator + projectName + "].");
		return true;
	}
	
	@Override
	public String getUsageFlags() {
		return ("[" + FLAG_GENERATELDMPROJECT_HELP + "] " +
				"[" + FLAG_GENERATELDMPROJECT_OUTPUT_PATH + " <LDM project output path>] " +
				FLAG_GENERATELDMPROJECT_BE_PROJECTNAME + " <BE project name>" + 
				FLAG_GENERATELDMPROJECT_CDD_PATH + " <BE project cdd path>" +
				FLAG_GENERATELDMPROJECT_EARPATH + " <BE EAR Path>");
	}
	
	@Override
	public String getOperationName() {
		return ("Generate LDM Project");
	}
	
	@Override
	public String getOperationFlag() {
		return OPERATION_GENERATELDMPROJECT;
	}
	
	@Override
	public String[] getFlags() {
		return new String[] { FLAG_GENERATELDMPROJECT_HELP,
				FLAG_GENERATELDMPROJECT_OUTPUT_PATH,
				FLAG_GENERATELDMPROJECT_BE_PROJECTNAME,
				FLAG_GENERATELDMPROJECT_CDD_PATH,
				FLAG_GENERATELDMPROJECT_EARPATH};
	}
	
	/**
     * @param rootURL SCS root URL
     * @param projectName
     * @throws MalformedURLException
     * @throws IOException
     */
    private void loadProjectIndex(String projectName, String archivePath) throws MalformedURLException, IOException {
    	File archiveFile = null;
    	if (archivePath != null) {
    		archiveFile = new File(archivePath);
    	}	
		if (archiveFile != null && archiveFile.exists() && archiveFile.isFile()) {
			loadProjectIndex(projectName, archiveFile);
		}
    }
	
	/**
     * @param projectName
     * @param archivePath Path of the EAR
     * @throws MalformedURLException
     * @throws IOException
     */
	private void loadProjectIndex(String projectName, File archiveFile) throws MalformedURLException, IOException {

    	DesignerProject index = StudioProjectCache.getInstance().getIndex(projectName);
    	if (index == null) {
	    	java.net.URI earURI = archiveFile.toURI();
	    	ResourceSet resourceSet = new ResourceSetImpl();
	        final Resource resource = resourceSet.createResource(
	                URI.createURI("archive:jar:" + earURI.toString() + "!/Shared%20Archive.sar!/" + ".idx"));
	
	        final File earFile = new File (earURI);
	        final int length = (int) earFile.length();
	        final byte[] fileAsBytes = new byte[length];
	        
	        final InputStream is = earURI.toURL().openStream();
	        try {
	            for (int numRead = 0, offset = 0; (numRead >= 0) && (length > offset); offset += numRead) {
	                numRead = is.read(fileAsBytes, offset, length - offset);
	            }
	        } finally {
	            is.close();
	        }
	
	        final ByteArrayInputStream bais = new ByteArrayInputStream(fileAsBytes);
	        try {
	            final ZipInputStream earZis = new ZipInputStream(bais);
	            try {
	                for (ZipEntry earEntry = earZis.getNextEntry(); null != earEntry; earEntry = earZis.getNextEntry()) {
	                    if ("Shared Archive.sar".equals(earEntry.getName())) {
	                        final ZipInputStream sarZis = new ZipInputStream(earZis);
	                        try {
	                            for (ZipEntry sarEntry = sarZis.getNextEntry(); null != sarEntry; sarEntry = sarZis.getNextEntry()) {
	                                if (".idx".equals(sarEntry.getName())) {
	                                    resource.load(sarZis, null);
	                                    break;
	                                }
	                            }
	                        } finally {
	                            sarZis.close();
	                        }
	                        break;
	                    }
	                }
	            } finally {
	                earZis.close();
	            }
	        } finally {
	            bais.close();
	        }
		
	        DesignerProject designerProject = null;
	        if (resource.getContents().size() > 0) {
	        	designerProject = (DesignerProject) resource.getContents().get(0);
	        	StudioProjectCache.getInstance().putIndex(projectName, designerProject);
	        }     	
    	}    
    }
	
	private void generateLDMProject(ClusterConfigModelMgr ccMM, IProject project, String outputPath) {
		LiveViewProjectGenerator lvProjectGenerator = new LiveViewProjectGenerator();
		lvProjectGenerator.generateLVConfigFiles(ccMM.getEntityURIList(), project, outputPath, ccMM.isSharedNothingStorage(), ccMM.getOverideDomainModelMap());
	}
}

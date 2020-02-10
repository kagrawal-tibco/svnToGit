package com.tibco.cep.studio.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;

import com.tibco.cep.studio.common.configuration.ProjectLibraryEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.StudioCore;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.builder.AbstractBPMNProcessor;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.dependency.IDependencyCalculator;
import com.tibco.cep.studio.core.dependency.IDependencyCalculatorExtension;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

public class DependencyUtils {
	
	private static final String BPMN_PROCESS_EXT_PT_ID = "bpmnProcessExtension";
	private static AbstractBPMNProcessor[] fBpmnProcessors;

	private static IDependencyCalculator[] fDependencyCalculators;
	
	protected static final String DEPENDENCY_VALIDATOR = "dependencyCalculationParticipant";

	protected static final String ATTR_PARTICIPANT 	= "participant";
	protected static final String ATTR_EXTENSIONS	= "extensions";
	private static final String PROJLIB_EXT = ".projlib";
	private static final String BEPROJECT_EXT = ".beproject";
	
	public static synchronized IDependencyCalculator[] getDependencyCalculators() {
		if (fDependencyCalculators == null) {
			List<IDependencyCalculator> calculators = new ArrayList<IDependencyCalculator>();
			IConfigurationElement[] configurationElementsFor = Platform.getExtensionRegistry().getConfigurationElementsFor(StudioCorePlugin.PLUGIN_ID, DEPENDENCY_VALIDATOR);
			for (IConfigurationElement configurationElement : configurationElementsFor) {
				String attribute = configurationElement.getAttribute(ATTR_PARTICIPANT);
				IDependencyCalculator calculator = null;
				if (attribute != null) {
					try {
						Object executableExtension = configurationElement.createExecutableExtension(ATTR_PARTICIPANT);
						if (executableExtension instanceof IDependencyCalculator) {
							calculator = (IDependencyCalculator) executableExtension;
							String extensionsAttr = configurationElement.getAttribute(ATTR_EXTENSIONS);
							calculator.setValidExtensions(extensionsAttr);
							calculators.add(calculator);
						}
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
//				String extensionsAttr = configurationElement.getAttribute(ATTR_EXTENSIONS);
//				calculator.setValidExtensions(extensionsAttr);
//				calculators.add(calculator);
				
			}
			fDependencyCalculators = new IDependencyCalculator[calculators.size()];
			return calculators.toArray(fDependencyCalculators);
		}
		return fDependencyCalculators;
	}

	public static List<IResource> getDependentResources(IResource resource) {
		return getDependentResources(resource, true);
	}
	
	public static List<File> getDependentResources(File projectDir, File resource, boolean processDependencies) {
		IDependencyCalculator[] dependencyCalculators = getDependencyCalculators();
		List<File> allDependencies = new ArrayList<File>();
		List<File> processedFiles = new ArrayList<File>();
		
		processedFiles.add(resource);
		for (int i = 0; i < dependencyCalculators.length; i++) {
			IDependencyCalculator calculator = dependencyCalculators[i];
			if (calculator instanceof IDependencyCalculatorExtension) {
				List<File> dependencies = ((IDependencyCalculatorExtension)calculator).calculateDependencies(projectDir, resource);
				if (dependencies != null && dependencies.size() > 0) {
					if (processDependencies) {
						processDependencies(projectDir, dependencies, dependencies, processedFiles);
					}
					allDependencies.addAll(dependencies);
				}
			}
		}
		return allDependencies;
	}
	
	public static List<IResource> getDependentResources(IResource resource, boolean processDependencies) {
		IDependencyCalculator[] dependencyCalculators = getDependencyCalculators();
		List<IResource> allDependencies = new ArrayList<IResource>();
		List<IResource> processedFiles = new ArrayList<IResource>();
		
		processedFiles.add(resource);
		for (int i = 0; i < dependencyCalculators.length; i++) {
			IDependencyCalculator calculator = dependencyCalculators[i];
			List<IResource> dependencies = calculator.calculateDependencies(resource);
			if (dependencies != null && dependencies.size() > 0) {
				if (processDependencies) {
					processDependencies(dependencies, dependencies, processedFiles);
				}
				allDependencies.addAll(dependencies);
			}
		}
		return allDependencies;
	}

	private static void processDependencies(List<IResource> allDependencies, List<IResource> recDependencies, List<IResource> processedFiles) {
		for (int j=0; j<allDependencies.size(); j++) {
			IResource dependentResource = allDependencies.get(j);
			if (processedFiles.contains(dependentResource)) {
				continue;
			}
			processedFiles.add(dependentResource);
			List<IResource> recursiveDependencies = getDependentResources(dependentResource, false);
			if (recursiveDependencies == null) {
				continue;
			}
			for (int i = 0; i < recursiveDependencies.size(); i++) {
				IResource resource = recursiveDependencies.get(i);
				if (!allDependencies.contains(resource)) {
					allDependencies.add(resource);
				}
			}
			processDependencies(allDependencies, recursiveDependencies, processedFiles);
		}
	}
	
	private static void processDependencies(File project, List<File> allDependencies, List<File> recDependencies, List<File> processedFiles) {
		for (int j=0; j<allDependencies.size(); j++) {
			File dependentResource = allDependencies.get(j);
			if (processedFiles.contains(dependentResource)) {
				continue;
			}
			processedFiles.add(dependentResource);
			List<File> recursiveDependencies = getDependentResources(project, dependentResource, false);
			if (recursiveDependencies == null) {
				continue;
			}
			for (int i = 0; i < recursiveDependencies.size(); i++) {
				File resource = recursiveDependencies.get(i);
				if (!allDependencies.contains(resource)) {
					allDependencies.add(resource);
				}
			}
			processDependencies(project, allDependencies, recursiveDependencies, processedFiles);
		}
	}
	
	/**
	 * This method builds Designer Project Library from the the selected IResource
	 * @param targetFile --> ".projlib" file on file system
	 * @param resources --> List of Resources that needs to go in Project Library
	 */
	public static void buildProjectLibrary(final File targetFile, final List<IResource> resources) throws Exception {
		
		if (targetFile == null) {
			throw new IllegalArgumentException("Supplied Target File is null ..");
		}
		if (resources == null) return ;
		// target file is not created then create it 
		if (!targetFile.exists()){
			File parent = targetFile.getParentFile();			
			parent.mkdirs();
			
		}
		// iterate through all resources 
		OutputStream projLibFileOutputStream = null;
		OutputStream projLibJarOutputStream = null;
		try {
			projLibFileOutputStream = new FileOutputStream(targetFile);
			projLibJarOutputStream = new JarOutputStream(projLibFileOutputStream);
			for (IResource res : resources){
				processResource(projLibFileOutputStream, projLibJarOutputStream, res);
			}
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			// close open Streams
			try {
				if (projLibJarOutputStream != null){
					projLibJarOutputStream.close();
				}
				if (projLibFileOutputStream != null){
					projLibFileOutputStream.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
	}
	
	public static void buildProjectLibrary(final File projectDir, final File targetFile, final List<File> resources) throws Exception {
		
		if (targetFile == null) {
			throw new IllegalArgumentException("Supplied Target File is null ..");
		}
		if (resources == null) return ;
		// target file is not created then create it 
		if (!targetFile.exists()){
			File parent = targetFile.getParentFile();	
			if (parent != null) {
				parent.mkdirs();
			}
			
		}
		// iterate through all resources 
		OutputStream projLibFileOutputStream = null;
		OutputStream projLibJarOutputStream = null;
		try {
			projLibFileOutputStream = new FileOutputStream(targetFile);
			projLibJarOutputStream = new JarOutputStream(projLibFileOutputStream);
			for (File res : resources){
				processResource(projLibFileOutputStream, projLibJarOutputStream, projectDir, res);
			}
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			// close open Streams
			try {
				if (projLibJarOutputStream != null){
					projLibJarOutputStream.close();
				}
				if (projLibFileOutputStream != null){
					projLibFileOutputStream.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
	}
	
	private static void processResource(OutputStream projLibFileOutputStream,
			OutputStream projLibJarOutputStream, IResource res) throws IOException {
		boolean isProjectLibResource=false;
		if (res == null) return;
		if(!isValidExportLibraryResource(res, res.getProject().getName())) return;

		String projectName=res.getProject().getName();

		StudioProjectConfiguration projectConfig = StudioProjectConfigurationManager
				.getInstance().getProjectConfiguration(projectName);

		EList<ProjectLibraryEntry> projectLibEntries = projectConfig.getProjectLibEntries();
		if(projectLibEntries!=null && projectLibEntries.size()>0 && res.getFullPath().toString().indexOf(PROJLIB_EXT)!=-1 )
		{	
			isProjectLibResource=false;
			for(ProjectLibraryEntry entry:projectLibEntries)
			{	
				Path projLibPath=new Path(entry.getPath());
				String projLibName=projLibPath.lastSegment();
				String rootProjLib=projLibPath.removeLastSegments(1).toOSString();
				Path archivePath = new Path(IndexUtils.getArchivePath(res));
				if(projLibPath.equals(archivePath) && res.getFullPath().toString().indexOf(BEPROJECT_EXT)==-1)
				{
					IPath relPath = res.getFullPath().removeFirstSegments(2);
					rootProjLib=rootProjLib.concat(File.separator).concat(projLibName);
					IPath rootPath = new Path(rootProjLib);
					IPath absPath = rootPath.append(relPath);
					processProjectLibraryPath(projLibJarOutputStream, relPath, absPath,rootPath);
					isProjectLibResource=true;
					break;
				}
			}
		}
		if(!isProjectLibResource && res.getFullPath().toString().indexOf(PROJLIB_EXT)==-1 && res.getFullPath().toString().indexOf(BEPROJECT_EXT)==-1){
			IPath relPath = res.getFullPath().removeFirstSegments(1);
			IPath rootPath = res.getProject().getLocation();
			IPath absPath = rootPath.append(relPath);
			processPath(projLibJarOutputStream, relPath, absPath);
		}

	}

	private static void processResource(OutputStream projLibFileOutputStream,
			OutputStream projLibJarOutputStream, File projectDir, File res) throws IOException {
		if (res == null) return;
		if(!isValidExportLibraryResource(res, projectDir.getName())) return;
		
		IPath rootPath = new Path(projectDir.getAbsolutePath());
		IPath absPath = new Path(res.getAbsolutePath());
		IPath relPath = absPath.removeFirstSegments(rootPath.segmentCount());
		relPath = relPath.setDevice(null);
		processPath(projLibJarOutputStream, relPath, absPath);
		
	}
	
	private static void processPath(OutputStream projLibJarOutputStream,
			IPath relPath, IPath absPath) throws IOException,
			FileNotFoundException {
		String strAbsPath = absPath.toOSString();
		IPath resRelPathToProject = relPath;//.removeFirstSegments(1);
		String relPathToProject = resRelPathToProject.toOSString();
		relPathToProject = relPathToProject.replace('\\', '/'); // other areas expect this to be UNIX style separators
		// create a jar entry for this resource				
		JarEntry jarEntry = new JarEntry(new ZipEntry(relPathToProject));
		// add this jar entry to JarOoutputStream
		((JarOutputStream)projLibJarOutputStream).putNextEntry(jarEntry);
		// put the resource bytes inside this entry 
//		assumes in workspace?
		InputStream resStream = new FileInputStream(strAbsPath);
		// get a channel
		ReadableByteChannel rbc = Channels.newChannel(resStream);
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		int noOfBytesRead = rbc.read(byteBuffer);
		while (noOfBytesRead > 0){
			// write the buffer content to Jar OutputStream
			projLibJarOutputStream.write(byteBuffer.array(), 0, noOfBytesRead);
			/*
				if (noOfBytesRead <= 1024){
				// the whole file is read but it might be that bytes are not read fully so try reading once more from Channel
				break;
				}
			*/
			((Buffer)byteBuffer).clear();
			noOfBytesRead = rbc.read(byteBuffer);
		}
		// close InputStream
		resStream.close();
		// close channel
		rbc.close();
		// close JarEntry
		((JarOutputStream)projLibJarOutputStream).closeEntry();
	}
	
	private static void processProjectLibraryPath(OutputStream projLibJarOutputStream,
			IPath relPath, IPath absPath, IPath rootPath) throws IOException,
			FileNotFoundException {
		String strAbsPath = absPath.toOSString();
		IPath resRelPathToProject = relPath;//.removeFirstSegments(1);
		String relPathToProject = resRelPathToProject.toOSString();
		relPathToProject = relPathToProject.replace('\\', '/'); // other areas expect this to be UNIX style separators
		// create a jar entry for this resource				
		JarEntry jarEntry = new JarEntry(new ZipEntry(relPathToProject));
		// add this jar entry to JarOoutputStream
		((JarOutputStream)projLibJarOutputStream).putNextEntry(jarEntry);
		// put the resource bytes inside this entry 
		// assumes in workspace?
		
		JarFile projLibfile = new JarFile(new File(rootPath.toOSString()));
		JarEntry entry = projLibfile.getJarEntry(relPathToProject);
		InputStream resStream = projLibfile.getInputStream(entry);
		// get a channel
		ReadableByteChannel rbc = Channels.newChannel(resStream);
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		int noOfBytesRead = rbc.read(byteBuffer);
		while (noOfBytesRead > 0){
			// write the buffer content to Jar OutputStream
			projLibJarOutputStream.write(byteBuffer.array(), 0, noOfBytesRead);
			/*
				if (noOfBytesRead <= 1024){
				// the whole file is read but it might be that bytes are not read fully so try reading once more from Channel
				break;
				}
			*/
			((Buffer)byteBuffer).clear();
			noOfBytesRead = rbc.read(byteBuffer);
		}
		
		// close InputStream
		resStream.close();
		projLibfile.close();
		// close channel
		rbc.close();
		// close JarEntry
		((JarOutputStream)projLibJarOutputStream).closeEntry();

	}
	
	/**
	 * @param resource
	 * @param projectName
	 * @return
	 */
	public static boolean isValidExportLibraryResource(Object resource, String projectName) {
		//Check for Java Resources inside project
		if (resource instanceof IFile) {
			IFile file = (IFile)resource;
			if (StudioJavaUtil.isInsideJavaSourceFolder(file.getFullPath().toString(), 
					projectName)) {
				return true;
			}
		} else if (resource instanceof File) {
			File f = (File) resource;
			if (StudioJavaUtil.isInsideJavaSourceFolder(f.getAbsolutePath().toString(), 
					projectName, true)) {
				return true;
			}
		}
		
		Collection<String> extns = getValidExportLibraryExtensions();
		if(resource instanceof IFile){
			IFile file = (IFile)resource;
			if(extns.contains(file.getFileExtension())){
				return true;
			}
		} else if (resource instanceof File) {
			File f = (File) resource;
			int idx = f.getName().lastIndexOf('.');
			if (idx >= 0) {
				String ext = f.getName().substring(idx+1);
				if(extns.contains(ext)){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * @return
	 */
	public static Collection<String> getValidExportLibraryExtensions(){
		return StudioCore.getValidLibraryExtensions(new String[] {StudioCore.EXPORT_LIBRARY_EXTENSIONS,
				StudioCore.SHARED_RESOURCE_EXTENSIONS});
		
	}

	public synchronized static AbstractBPMNProcessor[] getBPMNProcessors() {
		List<AbstractBPMNProcessor> participants = new ArrayList<AbstractBPMNProcessor>();
		IConfigurationElement[] configurationElementsFor = Platform.getExtensionRegistry().getConfigurationElementsFor(StudioCorePlugin.PLUGIN_ID, BPMN_PROCESS_EXT_PT_ID);
		for (IConfigurationElement configurationElement : configurationElementsFor) {
			String processorAttribute = configurationElement.getAttribute("processor");
			String accessType = configurationElement.getAttribute("accessType");
			if (processorAttribute != null) {
				try {
					Object executableExtension = configurationElement.createExecutableExtension("processor");
					if (executableExtension instanceof AbstractBPMNProcessor) {
						AbstractBPMNProcessor bpmnProcessor = (AbstractBPMNProcessor) executableExtension;
						bpmnProcessor.setAccessIdentifier(accessType);
						participants.add(bpmnProcessor);
					}
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
		fBpmnProcessors = new AbstractBPMNProcessor[participants.size()];
		return participants.toArray(fBpmnProcessors);
	}
	
	

}

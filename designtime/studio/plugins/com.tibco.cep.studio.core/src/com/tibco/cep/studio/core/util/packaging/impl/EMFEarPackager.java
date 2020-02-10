/**
 * 
 */
package com.tibco.cep.studio.core.util.packaging.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.tibco.be.parser.codegen.JavacUtil;
import com.tibco.be.parser.semantic.FunctionsCatalogManager;
import com.tibco.be.util.packaging.descriptors.ApplicationArchiveFactory;
import com.tibco.be.util.packaging.descriptors.MutableApplicationArchive;
import com.tibco.be.util.packaging.descriptors.impl.NameValuePairs;
import com.tibco.be.util.packaging.descriptors.impl.RepoInstance;
import com.tibco.cep.designtime.core.model.archive.ArchivePackage;
import com.tibco.cep.designtime.core.model.archive.EnterpriseArchive;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.repo.BEProject;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.studio.common.configuration.ConfigurationPackage;
import com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.repo.emf.EMFProject;
import com.tibco.cep.studio.core.util.Utils;
import com.tibco.cep.studio.core.util.packaging.EARPackager;
import com.tibco.objectrepo.object.SubstitutionVariable;

/**
 * Client class to invoke BE packaging APIs
 * 
 * @author aathalye
 * @author ssailapp
 * 
 */
public class EMFEarPackager implements EARPackager {

	protected static final QualifiedName BE_PROJECT = new QualifiedName("BE", "Project");
	
	protected static final String BE_PROJECT_CONFIG = ".beproject";
	
	static {
		ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put(
				"*", new XMIResourceFactoryImpl());
		final EPackage.Registry i = EPackage.Registry.INSTANCE;
		i.put("http:///com/tibco/cep/studio/common/configuration/model/project_configuration.ecore",
			  ConfigurationPackage.eINSTANCE);
	}

	/**
	 * Get the configuration from here
	 */
	private EMFBarPackager barPackager;
	
	private EMFSarPackager sarPackager;
	
	private EMFProject project;
	
	private IPath projectPath;
	
	private boolean fOverWrite;
	
	private File earFile;
	
	private IProgressMonitor monitor;
	
	private boolean console;
	
	private String extendedClassPath; // for console/command line  use only
	
	private boolean useLegacyCompiler;

	public EMFEarPackager(EMFProject project, IProgressMonitor monitor, boolean isConsole, boolean useLegacyCompiler) throws Exception {
		setConsole(isConsole);
		setProject(project);
		setMonitor(monitor);
		setUseLegacyCompiler(useLegacyCompiler);
		monitor.beginTask("Build EAR Package...", 7);
		monitor.subTask("Loading project configuration...");
		if (monitor.isCanceled()) {
			throw new CoreException(Status.CANCEL_STATUS);
		}
		StudioProjectConfiguration projectConfiguration = project.getProjectConfiguration();
		EnterpriseArchiveEntry enterpriseArchiveEntry = projectConfiguration.getEnterpriseArchiveConfiguration();
		setOverWrite(enterpriseArchiveEntry.isOverwrite());
		monitor.worked(1);
		if (monitor.isCanceled()) {
			throw new CoreException(Status.CANCEL_STATUS);
		}
		monitor.subTask("Loading project global variables...");
		GlobalVariables gv = project.getGlobalVariables();
		String earFilePath = getSubstitutedStringValue(gv,
				enterpriseArchiveEntry.getPath());
		setEarFile(new File(earFilePath));
		if (monitor.isCanceled()) {
			throw new CoreException(Status.CANCEL_STATUS);
		}
		monitor.worked(1);

	}

	public EMFEarPackager(EMFProject project, boolean isConsole, String extendedClassPath, boolean useLegacyCompiler) throws Exception {
		this(project, new NullProgressMonitor(), isConsole, useLegacyCompiler);
		setExtendedClassPath(extendedClassPath);
	}

	@Override
	public void close() throws Exception {
		try {
			monitor.subTask("Loading ontology functions...");
			loadOntologyFunctions(project.getName(),projectPath);
			monitor.worked(1);

			if (monitor.isCanceled()) {
				throw new CoreException(Status.CANCEL_STATUS);
			}

			monitor.subTask("Setting EAR parameters...");
			final MutableApplicationArchive ear = ApplicationArchiveFactory.newApplicationArchive(project.getName());
			try {
				StudioProjectConfiguration projectConfiguration = project
						.getProjectConfiguration();
				EnterpriseArchiveEntry enterpriseArchiveEntry = projectConfiguration.getEnterpriseArchiveConfiguration();
				ear.addDeploymentDescriptor(new RepoInstance());
				ear.setOwner(enterpriseArchiveEntry.getAuthor());
				ear.setName(enterpriseArchiveEntry.getName());
				ear.setCreationDate(Calendar.getInstance().getTime());
				ear.setDescription(enterpriseArchiveEntry.getDescription());
				ear.setVersion("" + enterpriseArchiveEntry.getVersion());

				monitor.subTask("Setting EAR global variables...");
				// Get all global variables
				SubstitutionVariable[] globalVars = getGlobalVariablesAsArray(project);
				setGlobalVars(ear, globalVars);
				monitor.worked(1);

				if (monitor.isCanceled()) {
					throw new CoreException(Status.CANCEL_STATUS);
				}

				
				String classpath = null;
				if(isConsole()){
					List<String> corePaths = new ArrayList<String>();
					JavacUtil.loadCoreInternalLibraries(corePaths);
					StringBuilder class_path = new StringBuilder();
					for(String path:corePaths){
						class_path.append(path).append(File.pathSeparator);
					}
					if (getExtendedClassPath() != null && !getExtendedClassPath().isEmpty()) {
						class_path.append(getExtendedClassPath()).append(File.pathSeparator);
					}
					class_path.append(PackagingHelper.getProjectClassPath(project));
					classpath = class_path.toString();
				} else {
					classpath = PackagingHelper.getProjectClassPath(project);
				}

				/**
				 * We will have to see how to create process, and shared
				 * archives
				 */
				Ontology ontology = project.getOntology();
				monitor.subTask("Build BAR package...");
				this.barPackager = new EMFBarPackager(project, ear, ontology,
						globalVars, classpath, new SubProgressMonitor(
								getMonitor(), 70), useLegacyCompiler);
				// Close the packager
				barPackager.close();
				monitor.worked(1);
				monitor.subTask("Build SAR package...");
				if (monitor.isCanceled()) {
					throw new CoreException(Status.CANCEL_STATUS);
				}
				this.sarPackager = new EMFSarPackager(project, ear);
				sarPackager.close();
				monitor.worked(1);
				if (monitor.isCanceled()) {
					throw new CoreException(Status.CANCEL_STATUS);
				}
				monitor.subTask("Writing EAR file...");
				File earFile = getEarFile();
				File parentFile = earFile.getParentFile();
				if (parentFile != null && !parentFile.exists()) {
					parentFile.mkdirs();
				} else {
					String userHomeDir = System.getProperty("user.home");
					// If despite the mkdirs the base dir could not be
					// created...
					File earFileTemp = (parentFile != null) ? earFile
							: new File(userHomeDir, earFile.getName());
					// setEarFile(new File(, getEarFile().getName()));
					this.earFile = earFileTemp;
				}
				if (earFile.exists()) {
					if (isOverWrite()) {
						final FileOutputStream out = new FileOutputStream(earFile);
						ear.save(out);
						out.close();
					}
				} else {
					final FileOutputStream out = new FileOutputStream(earFile);
					ear.save(out);
					out.close();
				}
				monitor.worked(1);
			}catch (Exception e) {
				if (e instanceof CoreException && ((CoreException) e).getStatus().getSeverity() == Status.CANCEL) {
					throw new CoreException(Status.CANCEL_STATUS);
				}
				if (e instanceof NumberFormatException) {
					throw new NumberFormatException("Value out of range : " + e.getMessage());
				}
				throw e;
			} finally {
				ear.destroy();
			}
		} catch (Exception e) {
			if (e instanceof CoreException
					&& ((CoreException) e).getStatus().getSeverity() == Status.CANCEL) {
				throw new CoreException(Status.CANCEL_STATUS);
			}
			throw e;
		} finally {
			monitor.done();
		}
	}

	
	/**
	 * load functions here
	 * 
	 * @param projectName
	 * @param projectPath 
	 * @throws Exception
	 */
	private void loadOntologyFunctions(String projectName, IPath projectPath) throws Exception {
		// FunctionsCatalog.getINSTANCE();
		FunctionsCatalogManager.getInstance().getStaticRegistry();
		/**
		 * workspace java catalog functions needs to be loaded separately in console mode
		 */
		String prop = System.getProperty("studio.console","false");
		if (!prop.equalsIgnoreCase("false")){
			FunctionsCatalogManager.getInstance().loadWorkspaceCustomFunctions(projectName,projectPath);
		}
		FunctionsCatalogManager.waitForStaticRegistryUpdates();
	}
	
	
	/**
	 * @param gv
	 * @param value
	 * @return
	 */
	protected String getSubstitutedStringValue(GlobalVariables gv, String value) {
		final CharSequence cs = gv.substituteVariables(value);
		if (null == cs) {
			return "";
		} else {
			return cs.toString();
		}
	}

	/**
	 * @param project
	 * @return
	 */
	private SubstitutionVariable[] getGlobalVariablesAsArray(BEProject project) {
		final List<SubstitutionVariable> gvs = new ArrayList<SubstitutionVariable>();
		ArrayList<GlobalVariableDescriptor> gvars = new ArrayList<GlobalVariableDescriptor>(
				project.getGlobalVariables().getVariables());
		Collections.sort(gvars, new Comparator<GlobalVariableDescriptor>() {
			@Override
			public int compare(GlobalVariableDescriptor o1, GlobalVariableDescriptor o2) {
				return o1.getFullName().compareTo(o2.getFullName());
			}
		});

        boolean foundDeployment = false;
        boolean foundDomain = false;
		boolean foundMessageEncoding = false;
		for (Iterator<GlobalVariableDescriptor> it = project
				.getGlobalVariables().getVariables().iterator(); it.hasNext();) {
			final GlobalVariableDescriptor gv = (GlobalVariableDescriptor) it
					.next();
			String name = gv.getFullName();
			if(name.length() > 0){
				if (gv.getFullName().charAt(0) == '/') {
					name = gv.getFullName().substring(1);
				}
			}
			final SubstitutionVariable substGV = new SubstitutionVariable(name,
					gv.getValueAsString(), gv.getDescription(), gv
							.isDeploymentSettable(), gv.isServiceSettable(), gv
							.getType(), gv.getConstraint(), gv
							.getModificationTime());
			gvs.add(substGV);
            foundDeployment = foundDeployment || "Deployment".equals(name);
            foundDomain = foundDomain || "Domain".equals(name);
            foundMessageEncoding = foundMessageEncoding || "MessageEncoding".equals(name);
		}
        if (!foundDeployment) {
            gvs.add(new SubstitutionVariable("Deployment", project.getName(),
                    "", true, false, "String", "", System.currentTimeMillis()));
        }
        if (!foundDomain) {
            gvs.add(new SubstitutionVariable("Domain", "domain",
                    "", true, false, "String", "", System.currentTimeMillis()));
        }
		if (!foundMessageEncoding) {
			gvs.add(new SubstitutionVariable("MessageEncoding", "ISO8859-1",
					"", true, false, "String", "", System.currentTimeMillis()));
		}

		return (SubstitutionVariable[]) gvs
				.toArray(new SubstitutionVariable[0]);
	}

	/**
	 * @param earPath
	 * @return
	 */
	protected EnterpriseArchive loadArchiveResource(String earPath) {
		if (earPath == null) {
			return null;
		}
		ResourceSet resourceSet = new ResourceSetImpl();
		URI uri = URI.createFileURI(earPath);
		if (resourceSet.getResourceFactoryRegistry().getFactory(uri) == null) {
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
					.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
							new XMIResourceFactoryImpl());
			ArchivePackage archiveInstance = ArchivePackage.eINSTANCE; // initialize
																		// the
																		// package
		}
		try {
			Resource resource = resourceSet.getResource(uri, true);
			if (resource == null)
				return null;
			for (EObject eobject : resource.getContents()) {
				if (eobject instanceof EnterpriseArchive) {
					EnterpriseArchive entrArchive = (EnterpriseArchive) eobject;
					return entrArchive;
				}
			}
			return null;
		} catch (WrappedException we) {
			StudioCorePlugin.log(we);
			return null;
		}
	}

	/**
	 * @param ear
	 * @param globalVars
	 */
	protected void setGlobalVars(MutableApplicationArchive ear,
			SubstitutionVariable[] globalVars) {
		final List<SubstitutionVariable> gvList = Arrays.asList(globalVars);
		final NameValuePairs globalVarsDD = (NameValuePairs) Utils
				.getSubstitutionVariablesAsDescriptor(
						gvList,
						NameValuePairs.REPO_INSTANCE_GLOBAL_VARIABLES_DEPLOYMENT_DESCRIPTOR_NAME);
		if (globalVarsDD != null) {
			ear.addDeploymentDescriptor(globalVarsDD);
		}
	}

	/**
	 * @return the project
	 */
	public EMFProject getProject() {
		return project;
	}

	/**
	 * @param project
	 *            the project to set
	 */
	public void setProject(EMFProject project) {
		this.projectPath = new Path(project.getRepoPath());
		this.project = project;
	}

	/**
	 * @return the canSave
	 */
	public boolean isOverWrite() {
		return fOverWrite;
	}

	/**
	 * @param overwrite
	 *            the canSave to set
	 */
	public void setOverWrite(boolean overwrite) {
		this.fOverWrite = overwrite;
	}

	/**
	 * @return the earFile
	 */
	public File getEarFile() {
		return earFile;
	}

	/**
	 * @param earFile
	 *            the earFile to set
	 */
	public void setEarFile(File earFile) {
		this.earFile = earFile;
	}

	/**
	 * @return the monitor
	 */
	public IProgressMonitor getMonitor() {
		return monitor;
	}

	/**
	 * @param monitor
	 *            the monitor to set
	 */
	public void setMonitor(IProgressMonitor monitor) {
		this.monitor = monitor;
	}

	/**
	 * @return
	 */
	public boolean isConsole() {
		return console;
	}

	/**
	 * @param console
	 */
	public void setConsole(boolean console) {
		this.console = console;
	}
	
	/**
	 * @return
	 */
	public String getExtendedClassPath() {
		return extendedClassPath;
	}
	
	/**
	 * @param extendedClassPath
	 */
	public void setExtendedClassPath(String extendedClassPath) {
		this.extendedClassPath = extendedClassPath;
	}

	/**
	 * @return the barPackager
	 */
	public EMFBarPackager getBarPackager() {
		return barPackager;
	}

	/**
	 * @param barPackager the barPackager to set
	 */
	public void setBarPackager(EMFBarPackager barPackager) {
		this.barPackager = barPackager;
	}

	/**
	 * @return the sarPackager
	 */
	public EMFSarPackager getSarPackager() {
		return sarPackager;
	}

	/**
	 * @param sarPackager the sarPackager to set
	 */
	public void setSarPackager(EMFSarPackager sarPackager) {
		this.sarPackager = sarPackager;
	}

	/**
	 * @return the fOverWrite
	 */
	public boolean isfOverWrite() {
		return fOverWrite;
	}

	/**
	 * @param fOverWrite the fOverWrite to set
	 */
	public void setfOverWrite(boolean fOverWrite) {
		this.fOverWrite = fOverWrite;
	}

	/**
	 * @return the useLegacyCompiler
	 */
	public boolean isUseLegacyCompiler() {
		return useLegacyCompiler;
	}

	/**
	 * @param useLegacyCompiler the useLegacyCompiler to set
	 */
	public void setUseLegacyCompiler(boolean useLegacyCompiler) {
		this.useLegacyCompiler = useLegacyCompiler;
	}
	
	
	
	
}

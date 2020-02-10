/**
 * 
 */
package com.tibco.cep.studio.rms.artifacts.manager.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EPackage;

import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.artifacts.ArtifactChanger;
import com.tibco.cep.studio.rms.artifacts.ArtifactOperation;
import com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry;
import com.tibco.cep.studio.rms.artifacts.ArtifactsFactory;
import com.tibco.cep.studio.rms.artifacts.ArtifactsPackage;
import com.tibco.cep.studio.rms.artifacts.ArtifactsSummary;
import com.tibco.cep.studio.rms.artifacts.ArtifactsType;
import com.tibco.cep.studio.rms.artifacts.RMSRepo;
import com.tibco.cep.studio.rms.core.RMSCorePlugin;

/**
 * @author aathalye
 * 
 */
@SuppressWarnings("restriction")
public class RMSArtifactsSummaryManager {

	public static RMSArtifactsSummaryManager INSTANCE;
	
	private static Map</**project**/String, ArtifactsSummary> artifactSummaries = new HashMap<String, ArtifactsSummary>();
	
	private ArtifactsSummaryChangeListener summaryChangeListener;
	
	private ArtifactsSummaryPersistenceHandler persistenceHandler;
	
	private RMSArtifactsSummaryManager() {
		persistenceHandler = new ArtifactsSummaryPersistenceHandler();
		EPackage.Registry.INSTANCE.put(ArtifactsPackage.eNS_URI, ArtifactsPackage.eINSTANCE);
		loadAll();
		summaryChangeListener = new ArtifactsSummaryChangeListener();
		ResourcesPlugin.getWorkspace().
			addResourceChangeListener(summaryChangeListener, IResourceChangeEvent.POST_CHANGE);
	}
	
	public static final RMSArtifactsSummaryManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new RMSArtifactsSummaryManager();
		}
		return INSTANCE;
	}

	
	/**
	 * Remove a {@link ArtifactSummaryEntry} from the repository for the project.
	 * @param file -> The resource
	 */
	public void removeArtifactEntry(IFile file, 
			                        ArtifactChanger artifactChanger,
			                        boolean softDelete) {
		//Get project name
		IProject project = file.getProject();
		String name = project.getName();
		//Project name and this name should be same
		List<ArtifactSummaryEntry> artifactSummaryEntries = getAllArtifactSummaries(name);
		//Get file path
		String artifactPath = IndexUtils.getFullPath(file);
		//From extension get artifact type
		ArtifactSummaryEntry contained = 
			containsArtifact(artifactSummaryEntries, artifactPath, artifactChanger, false);
		if (contained != null) {
			//Remove the entry
			removeArtifactEntry(name, contained, softDelete);
		}
	}
	
	/**
	 * Remove a {@link ArtifactSummaryEntry} from the repository for the project.
	 * @param project -> The project name
	 * @param artifactPathToRemove -> Path of artifact to remove
	 * @param softDelete -> Whether to permanently delete from summary or add to deleted section
	 */
	public void removeArtifactEntry(String project,
			                        String artifactPathToRemove, 
			                        ArtifactChanger artifactChanger,
			                        boolean softDelete) {
		removeArtifactEntry(project, artifactPathToRemove, artifactChanger, softDelete, false);
	}
	
	/**
	 * Remove a {@link ArtifactSummaryEntry} from the repository for the project.
	 * @param project -> The project name
	 * @param artifactPathToRemove -> Path of artifact to remove
	 * @param softDelete -> Whether to permanently delete from summary or add to deleted section
	 * @param ignoreArtifactChanger
	 */
	public void removeArtifactEntry(String project,
            String artifactPathToRemove, 
            ArtifactChanger artifactChanger,
            boolean softDelete,
            boolean ignoreArtifactChanger) {
		List<ArtifactSummaryEntry> artifactSummaryEntries = getAllArtifactSummaries(project);
		
		ArtifactSummaryEntry contained = 
			containsArtifact(artifactSummaryEntries, artifactPathToRemove, artifactChanger, ignoreArtifactChanger);
		if (contained != null) {
			//Remove the entry
			removeArtifactEntry(project, contained, softDelete);
		}
	}
	
	/**
	 * Remove a {@link ArtifactSummaryEntry} from the repository for the project.
	 * @param project -> The project name
	 * @param summaryEntryToRemove -> {@link ArtifactSummaryEntry} to remove
	 * @param softDelete -> Whether to permanently delete from summary or add to deleted section
	 */
	public void removeArtifactEntry(String project,
			                        ArtifactSummaryEntry summaryEntryToRemove,
			                        boolean softDelete) {
		//Remove the entry
		ArtifactsSummary artifactsSummary = artifactSummaries.get(project);
		//If one does not exist create it
		artifactsSummary = 
			(artifactsSummary == null) ? createProjectArtifactsSummary(project)
					: artifactsSummary;
		persistenceHandler.removeEntry(summaryEntryToRemove, artifactsSummary, softDelete);
		persistenceHandler.save(artifactsSummary);
	}
	
	
	/**
	 * @param name
	 * @return
	 */
	private List<ArtifactSummaryEntry> getAllArtifactSummaries(String name) {
		List<ArtifactSummaryEntry> allSummaryEntries = new ArrayList<ArtifactSummaryEntry>();
		if (artifactSummaries.containsKey(name)) {
			ArtifactsSummary artifactsSummary = artifactSummaries.get(name);
			//Get all added artifacts
			allSummaryEntries.addAll(artifactsSummary.getAddedArtifacts());
			allSummaryEntries.addAll(artifactsSummary.getModifiedArtifacts());
			allSummaryEntries.addAll(artifactsSummary.getDeletedArtifacts());
		}
		return allSummaryEntries;
	}
	
	/**
	 * @param allSummaryEntries
	 * @param artifactPath
	 * @param artifactsType
	 * @return
	 */
	private ArtifactSummaryEntry containsArtifact(List<ArtifactSummaryEntry> allSummaryEntries,
			                                      String artifactPath,
			                                      ArtifactChanger artifactChanger,
			                                      boolean ignoreArtifactChanger) {
		boolean matchFound = false;
		for (ArtifactSummaryEntry artifactSummaryEntry : allSummaryEntries) {
			Artifact artifact = artifactSummaryEntry.getArtifact();
			//Not sure if this check is really needed
			if (artifact != null) {
				if (artifact.getArtifactPath().equals(artifactPath)){
					matchFound = ignoreArtifactChanger? true : artifactChanger == artifactSummaryEntry.getArtifactChanger();
					if (matchFound) {
						return artifactSummaryEntry;
					}
				}
			}
		}
		return null;
	}
	

	/**
	 * Load Artifacts summaries for all projects in the workspace
	 */
	private void loadAll() {
		IProject[] projects = CommonUtil.getAllStudioProjects();
		for (IProject project : projects) {
			loadProjectArtifactsSummary(project);
		}
	}
	
	public void createArtifactEntry(String project,
            String artifactPath,
            String containerPath,
			String artifactExtn,
            ArtifactOperation artifactOperation,
            ArtifactChanger artifactChanger) {
		createArtifactEntry(project, 
                artifactPath, 
                containerPath,
                artifactExtn,
                null,
                null,
                ArtifactOperation.ADD,
                ArtifactChanger.LOCAL);
	}	
	
	/**
	 * Create a new entry (if one does not exist)
	 * @param project
	 * @param artifactPath
	 * @param containerPath
	 * @param artifactExtn
	 * @param artifactOperation
	 * @param artifactChanger
	 */
	public void createArtifactEntry(String project,
			                        String artifactPath,
			                        String containerPath,
									String artifactExtn,
									Date updateTime,
									String commitVersion,
			                        ArtifactOperation artifactOperation,
			                        ArtifactChanger artifactChanger) {
		
		//Project name and this name should be same
		ArtifactsType artifactsType = ArtifactsType.get(artifactExtn);
		if (artifactsType == null) {
			return;
		}
		
		Artifact newArtifact = ArtifactsFactory.eINSTANCE.createArtifact();
		newArtifact.setArtifactPath(artifactPath);
		newArtifact.setContainerPath(containerPath);
		newArtifact.setArtifactType(artifactsType);
		newArtifact.setUpdateTime(updateTime);
		newArtifact.setCommittedVersion(commitVersion);
		ArtifactSummaryEntry summaryEntry =
			ArtifactsFactory.eINSTANCE.createArtifactSummaryEntry();
		summaryEntry.setArtifact(newArtifact);
		summaryEntry.setOperationType(artifactOperation);
		summaryEntry.setArtifactChanger(artifactChanger);
		ArtifactsSummary artifactsSummary = artifactSummaries.get(project);
		//If one does not exist create it
		artifactsSummary = 
			(artifactsSummary == null) ? createProjectArtifactsSummary(project)
					: artifactsSummary;
		persistenceHandler.addEntry(summaryEntry, artifactsSummary);
		persistenceHandler.save(artifactsSummary);
	}
	
	/**
	 * load {@link ArtifactSummary} for existing project
	 * @param project
	 * @return
	 */
	
	private ArtifactsSummary loadProjectArtifactsSummary(IProject project) {
		String projectName = project.getName();
		ArtifactsSummary artifactsSummary = artifactSummaries.get(projectName);
		if (artifactsSummary != null) {
			return artifactsSummary;
		}
//		final IFile resolvedRes = getArtifactsSummaryFileForProject(project);
		final String summaryFilePath = getArtifactsSummaryFilePathForProject(project);
		artifactsSummary = persistenceHandler.loadArtifactSummaryFromFile(summaryFilePath);
		
		IFile resolvedResFile = (IFile) ((Workspace)ResourcesPlugin.getWorkspace()).newResource(new Path(summaryFilePath), IResource.FILE);
		if (artifactsSummary == null) {
			//Create one and persist
			return createProjectArtifactsSummary(project, resolvedResFile);
		}
		//Check if it exists
		if (!artifactSummaries.containsKey(projectName)) {
			artifactSummaries.put(projectName, artifactsSummary);
		}
		return artifactsSummary;
	}
	
	/**
	 * Create new {@link ArtifactsSummary} for this project
	 * @param project
	 * @param summaryFile
	 * @return
	 */
	private ArtifactsSummary createProjectArtifactsSummary(IProject project, 
			                                               IFile summaryFile) {
		ArtifactsSummary artifactsSummary = 
			persistenceHandler.createProjectArtifactsSummary(project, summaryFile);
		artifactSummaries.put(project.getName(), artifactsSummary);
		return artifactsSummary;
	}
	
	/**
	 * Create new {@link ArtifactsSummary} for this project
	 * @param project
	 * @return
	 */
	private ArtifactsSummary createProjectArtifactsSummary(IProject project) {
		final IFile resolvedRes = getArtifactsSummaryFileForProject(project);
		return createProjectArtifactsSummary(project, resolvedRes);
	}
	
	/**
	 * Create new {@link ArtifactsSummary} for this project
	 * @param project
	 * @return
	 */
	private ArtifactsSummary createProjectArtifactsSummary(String projectName) {
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = workspaceRoot.getProject(projectName);
		final IFile resolvedRes = getArtifactsSummaryFileForProject(project);
		return createProjectArtifactsSummary(project, resolvedRes);
	}
	
	/**
	 * Same as getSummaryEntry(String, String, ArtifactChanger, false);
	 * @param projectName
	 * @param artifactPath
	 * @param artifactChanger
	 * @return
	 * @see #getSummaryEntry(String, String, ArtifactChanger, boolean)
	 */
	public ArtifactSummaryEntry getSummaryEntry(String projectName, 
            									String artifactPath,
                                                ArtifactChanger artifactChanger) {
		return getSummaryEntry(projectName, artifactPath, artifactChanger, false);
	}
	
	/**
	 * 
	 * @param projectName
	 * @param artifactPath
	 * @param artifactChanger
	 * @param commitStatus
	 * @return
	 */
	public ArtifactSummaryEntry getSummaryEntry(String projectName, 
			                                    String artifactPath,
			                                    ArtifactChanger artifactChanger,
			                                    boolean commitStatus) {
		return getSummaryEntry(projectName, artifactPath, artifactChanger, commitStatus, false, false);
	}
	
	/**
	 * 
	 * @param projectName
	 * @param artifactPath
	 * @param artifactChanger
	 * @param commitStatus
	 * @param ignoreCommitStatus
	 * @return
	 */
	public ArtifactSummaryEntry getSummaryEntry(String projectName, 
            String artifactPath,
            ArtifactChanger artifactChanger,
            boolean commitStatus,
            boolean ignoreCommitStatus,
            boolean ignoreArtifactChanger) {
		
		boolean matchFound = false;
		
		//Check if it contains a summary
		ArtifactsSummary artifactsSummary = artifactSummaries.get(projectName);
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = workspaceRoot.getProject(projectName);
		if (artifactsSummary == null) {
			artifactsSummary = createProjectArtifactsSummary(project);
		}
		List<ArtifactSummaryEntry> summaryEntries = new ArrayList<ArtifactSummaryEntry>();
		summaryEntries.addAll(artifactsSummary.getAddedArtifacts());
		summaryEntries.addAll(artifactsSummary.getModifiedArtifacts());
		summaryEntries.addAll(artifactsSummary.getDeletedArtifacts());
		ArtifactSummaryEntry match = 
				containsArtifact(summaryEntries, artifactPath, artifactChanger, ignoreArtifactChanger);
		if (match != null) {
			matchFound = ignoreCommitStatus?true : match.isCommitStatus() == commitStatus;
			if (matchFound) {
				return match;
			}
		}
		return null;
	}
	
	public ArtifactsSummaryChangeListener getSummaryChangeListener() {
		return summaryChangeListener;
	}
	
	public List<ArtifactSummaryEntry> getLocalSummaryEntries(String projectName, boolean allowCommittedResources) {
		return getLocalSummaryEntries(projectName, null, allowCommittedResources);
	}
	
	/**
	 * 
	 * @param projectName
	 * @param folderPath
	 * @return
	 */
	public List<ArtifactSummaryEntry> getLocalSummaryEntries(String projectName, 
			                                                 String folderPath,
			                                                 boolean allowCommittedResources) {
		List<ArtifactSummaryEntry> localEntries = new ArrayList<ArtifactSummaryEntry>();
		ArtifactsSummary artifactsSummary = artifactSummaries.get(projectName);
		if (artifactsSummary == null) {
			artifactsSummary = createProjectArtifactsSummary(projectName);
		}
		List<ArtifactSummaryEntry> summaryEntries = new ArrayList<ArtifactSummaryEntry>();
		summaryEntries.addAll(artifactsSummary.getAddedArtifacts());
		summaryEntries.addAll(artifactsSummary.getModifiedArtifacts());
		summaryEntries.addAll(artifactsSummary.getDeletedArtifacts());
		
		for (ArtifactSummaryEntry summaryEntry : summaryEntries) {
			//Check for Local ones
			if (ArtifactChanger.LOCAL == summaryEntry.getArtifactChanger()) {
				if (folderPath == null) {
					//Return all project local entries
					if (!allowCommittedResources) {
						if (!summaryEntry.isCommitStatus()) {
							localEntries.add(summaryEntry);
						}
					} else {
						localEntries.add(summaryEntry);
					}
				} else {
					String containerPath = summaryEntry.getArtifact().getContainerPath();
					//Look for partial match also so that we allow nested files to be included
					if (containerPath.contains(folderPath)) {
						if (!allowCommittedResources) {
							if (!summaryEntry.isCommitStatus()) {
								localEntries.add(summaryEntry);
							}
						} else {
							localEntries.add(summaryEntry);
						}
					}
				}
			}
		}
		return localEntries;
	}
	

	/**
	 * @param projectName
	 * @param folderPath
	 * @param filePath
	 * @param allowCommittedResources
	 * @return
	 */
	public List<ArtifactSummaryEntry> getLocalSummaryEntries(String projectName, 
			                                                 String folderPath, 
			                                                 String filePath,
			                                                 boolean allowCommittedResources) {
		List<ArtifactSummaryEntry> localFileEntries = new ArrayList<ArtifactSummaryEntry>();
		List<ArtifactSummaryEntry> localEntries = getLocalSummaryEntries(projectName, allowCommittedResources);
		for (ArtifactSummaryEntry entry : localEntries) {
			if (entry.getArtifact().getArtifactPath().equals(filePath)) {
				localFileEntries.add(entry);
			}
		}
		return localFileEntries;
	}
	
	/**
	 * Lists all the undeleted artifacts within a specified path
	 * @param projectName
	 * @param containerPath
	 * @return
	 */
	public List<ArtifactSummaryEntry> getUnDeletedSummaryEntries(String projectName, String containerPath){
		ArtifactsSummary artifactsSummary = artifactSummaries.get(projectName);
		if (artifactsSummary == null) {
			artifactsSummary = createProjectArtifactsSummary(projectName);
		}
		List<ArtifactSummaryEntry> summaryEntries = new ArrayList<ArtifactSummaryEntry>();
		summaryEntries.addAll(artifactsSummary.getAddedArtifacts());
		summaryEntries.addAll(artifactsSummary.getModifiedArtifacts());
		
		List<ArtifactSummaryEntry> unDeletedEntries = new ArrayList<ArtifactSummaryEntry>();
		for (ArtifactSummaryEntry artifactSummaryEntry : summaryEntries){
			if (artifactSummaryEntry.getArtifact().getArtifactPath().startsWith(containerPath) && artifactSummaryEntry.getArtifactChanger() == ArtifactChanger.REMOTE){
				unDeletedEntries.add(artifactSummaryEntry);
			}
		}
		
		return unDeletedEntries;
	}
	
	/**
	 * 
	 * @param project
	 * @param artifactPath
	 * @param artifactChanger
	 * @param commitStatus
	 */
	public void reverseCommitStatus(String project,
			                        String artifactPath,
			                        String commitVersion,
			                        ArtifactChanger artifactChanger,
			                        boolean commitStatus) {
		ArtifactSummaryEntry summaryEntry = 
			getSummaryEntry(project, artifactPath, ArtifactChanger.LOCAL, commitStatus);

		reverseCommitStatus(project, summaryEntry, commitStatus, commitVersion);
	}
	
	/**
	 * 
	 * @param project
	 * @param artifactPath
	 * @param artifactChanger
	 * @param commitStatus
	 */
	public void reverseCommitStatus(String project,
			                        ArtifactSummaryEntry artifactSummaryEntry,
			                        boolean commitStatus,
			                        String commitVersion) {
		//If it is deleted remove it
		if (artifactSummaryEntry.getOperationType() == ArtifactOperation.DELETE) {
			removeArtifactEntry(project, artifactSummaryEntry, false);
		}
		if (artifactSummaryEntry != null) {
			artifactSummaryEntry.setCommitStatus(!commitStatus);
			if (artifactSummaryEntry.isCommitStatus()) {
				artifactSummaryEntry.getArtifact().setUpdateTime(new Date());
				artifactSummaryEntry.getArtifact().setCommittedVersion(commitVersion);
			}	
		}
		ArtifactsSummary artifactsSummary = artifactSummaries.get(project);
		persistenceHandler.save(artifactsSummary);
	}
	
	/**
	 * 
	 * @param localProject
	 * @param rmsRepoURL
	 * @param rmsProject
	 */
	public void createRMSRepoInfo(String localProject,
			                      String rmsRepoURL,
			                      String rmsProject) {
		ArtifactsSummary artifactsSummary = artifactSummaries.get(localProject);
		//Check if it already has a repo
		RMSRepo rmsRepo = artifactsSummary.getRmsRepo();
		if (rmsRepo == null) {
			rmsRepo = persistenceHandler.createRMSRepo();
			//update it
			rmsRepo.setRepoURL(rmsRepoURL);
			rmsRepo.setRmsProject(rmsProject);
			artifactsSummary.setRmsRepo(rmsRepo);
			//Save it
			persistenceHandler.save(artifactsSummary);
		}
	}
	
	public RMSRepo getRMSRepoInfo(String localProject) {
		ArtifactsSummary artifactsSummary = artifactSummaries.get(localProject);
		//Check if it already has a repo
		return artifactsSummary.getRmsRepo();
	}
	
	private IFile getArtifactsSummaryFileForProject(IProject project) {
		String path = getArtifactsSummaryFilePathForProject(project);
		final IFile resolvedRes = (IFile) ((Workspace)ResourcesPlugin.getWorkspace()).newResource(new Path(path), IResource.FILE);
		return resolvedRes;
	}
	
	private String getArtifactsSummaryFilePathForProject(IProject project) {
		IPath dtlPath = 
				Path.fromOSString("." + ArtifactsSummaryPersistenceHandler.RMS_ARTIFACTS_SUMMARY_FILENAME);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(RMSCorePlugin.getDefault().getStateLocation().toOSString());
		stringBuilder.append(File.separator);
		stringBuilder.append(project.getName());
		stringBuilder.append(File.separator);
		stringBuilder.append(dtlPath);
		return stringBuilder.toString();
	}
}

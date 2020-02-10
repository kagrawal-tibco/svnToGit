package com.tibco.cep.studio.core.index.update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.decision.table.checkpoint.UndoableCommandCheckpointEntry;
import com.tibco.cep.decision.table.checkpoint.handlers.ICheckpointDeltaEntryHandler;
import com.tibco.cep.decision.table.checkpoint.handlers.UndoableCommandCheckpointEntryHandler;
import com.tibco.cep.decision.table.command.CommandFacade;
import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.archive.ArchiveResource;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.java.JavaResource;
import com.tibco.cep.designtime.core.model.java.JavaSource;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.ArchiveElement;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.IndexFactory;
import com.tibco.cep.studio.core.index.model.InstanceElement;
import com.tibco.cep.studio.core.index.model.JavaElement;
import com.tibco.cep.studio.core.index.model.JavaResourceElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.model.StateMachineElement;
import com.tibco.cep.studio.core.index.model.TypeElement;
import com.tibco.cep.studio.core.index.utils.BaseStateMachineElementCreator;
import com.tibco.cep.studio.core.index.utils.EventElementCreator;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.index.utils.JavaElementCreator;
import com.tibco.cep.studio.core.index.utils.RuleElementCreator;
import com.tibco.cep.studio.core.util.ResourceHelper;


public class IndexUpdateVisitor implements IResourceDeltaVisitor {

	private HashMap<DesignerProject, StudioProjectDelta> fAffectedProjects = new HashMap<DesignerProject, StudioProjectDelta>();
	private boolean fHasReferenceChanges = false;
	private static HashMap<IProject, Object> fLocks = new HashMap<IProject, Object>();

	public IndexUpdateVisitor(DesignerProject index) {
	}

	public boolean visit(IResourceDelta delta) throws CoreException {
        if (delta.getResource() instanceof IProject
                && delta.getKind() == IResourceDelta.REMOVED) {
            removeIndex((IProject) delta.getResource(), true);            
            return false;
        }
		IResource resource = delta.getResource();
		if (IndexUtils.isProjectLibraryType(resource.getFileExtension())) {
			fHasReferenceChanges = true;
			DesignerProject index = StudioCorePlugin.getDesignerModelManager().getIndex(resource.getProject());
			if (!fAffectedProjects.containsKey(index)) {
				fAffectedProjects.put(index, new StudioProjectDelta(index, IStudioElementDelta.CHANGED));
			}
			EList<DesignerProject> referencedProjects = index.getReferencedProjects();
//			String linkedPath = IndexUtils.getArchivePath(resource.getLocation());
			for (int i = 0; i < referencedProjects.size(); i++) {
				DesignerProject proj = referencedProjects.get(i);
				if (proj.getName().equals(resource.getName())) {
					referencedProjects.remove(i);
					break;
				}
			}
			if (delta.getKind() == IResourceDelta.REMOVED) {
				return false;
			}
			// re-index project library
//			return false;
		}
			
		if (resource instanceof IProject) {
			return handleProjectChanged(delta, (IProject) resource);
		}
		
		if (!(resource instanceof IFile)) {
			return true;
		}
		
		IFile file = (IFile) resource;
		return handleFileChanged(delta, file);
	}

	private boolean handleFileChanged(IResourceDelta delta, IFile file) {
//		if (!file.exists()) {
			// just return?  Or remove the entry?
//			return false;
//		}
		if (!file.isSynchronized(IResource.DEPTH_ZERO)) {
			// this can occur if there are two different projects that reference the same project library
			// for some reason, a resource delta is fired for the 2nd project with files that are out-of-sync, cause errors
			return false;
		}
		if (!StudioCorePlugin.getDesignerModelManager().isIndexCached(file.getProject()) 
				&& StudioCorePlugin.getDesignerModelManager().createIsScheduled(file.getProject())) {
			// a full create has been scheduled for this file's project
		
			// no need to individually process this file, as it will
			// be done by processing the entire project
			return false;
		}
		
		if ((delta.getFlags() == IResourceDelta.MARKERS
				|| delta.getFlags() == IResourceDelta.SYNC)
				&& delta.getKind() != IResourceDelta.REMOVED){
			// don't process the file if only the markers
			// or synchronization status have changed
			// still continue during a remove, however
			return true;
		}

		IProject project = file.getProject();
		DesignerProject index = StudioCorePlugin.getDesignerModelManager().getIndex(project);

    	if (!fAffectedProjects.containsKey(index)) {
    		fAffectedProjects.put(index, new StudioProjectDelta(index, IStudioElementDelta.CHANGED));
    	}

		if (delta.getKind() == IResourceDelta.REMOVED || !file.exists()) {
			boolean shared = file.isLinked(IResource.CHECK_ANCESTORS);
			if (!shared && !IndexUtils.isSupportedType(file)) {
				return false;
			}
			if (shared) {
				index = getSharedIndex(index, file);
			}
			removeIndexEntry(index, file, IStudioElementDelta.REMOVED);
			return false;
		}
		
		return reindexFile(delta.getKind(), file, index, true);
	}

	public boolean reindexFile(int deltaKind, IFile file,
			DesignerProject index, boolean buildDelta) {
		// need to process this file
		// if the file is an archive file, update the archive index entry
		if (IndexUtils.isArchiveType(file)) {
			processArchive(index, file, deltaKind, buildDelta);
			return false;
		}
		
		// if the file is an implementation file, update the decision table index entry
		if (IndexUtils.isImplementationType(file)) {
			processImplementation(index, file, deltaKind, buildDelta);
			return false;
		}
		
		// if the file is a rule, re-parse and update the entry
		if (IndexUtils.isRuleType(file)) {
			processRule(index, file, deltaKind, buildDelta);
			return false;
		}
		
		if(IndexUtils.isJavaType(file)) {
			processJava(index,file,deltaKind,buildDelta);
			return false;
		}
		
		if(IndexUtils.isJavaResource(file)) {
			processJavaResource(index,file,deltaKind,buildDelta);
			return false;
		}
		
		// if the file is an entity, update the entity index entry
		if (IndexUtils.isEntityType(file)) {
			if (IndexUtils.getFileType(file) == ELEMENT_TYPES.STATE_MACHINE) {
				processStateMachine(index, file, deltaKind, buildDelta);
			} else {
				processEntity(index, file, deltaKind, buildDelta);
			}
			return false;
		}
		
		if (file.isLinked(IResource.CHECK_ANCESTORS)) {
			processLinkedFile(index, file, deltaKind, buildDelta);
		}
		
		return true;
	}

	private void processLinkedFile(DesignerProject index, IFile file, int changeType,
			boolean buildDelta) {
		boolean shared = file.isLinked(IResource.CHECK_ANCESTORS);
		if (shared) {
			index = getSharedIndex(index, file);
		}
		DesignerElement removedEntry = null;
		if (shared || changeType == IResourceDelta.CHANGED) { // always remove for shared elements, otherwise duplicate elements will be inserted
			// a change, first remove the existing entry
			// then, create the new one
			removedEntry = removeIndexEntry(index, file, IStudioElementDelta.CHANGED);
		}
		
		SharedElement element = IndexFactory.eINSTANCE.createSharedElement();
		if (shared) {
			String folder = "/"+file.getParent().getFullPath().removeFirstSegments(2).addTrailingSeparator().toString();
			populateSharedElement(file, (SharedElement) element, folder);
		}
		
		insertElement(index, removedEntry, element, file, buildDelta);
	}

	private void processRule(DesignerProject index, IFile file, int changeType, boolean buildDelta) {
		boolean shared = file.isLinked(IResource.CHECK_ANCESTORS);
		if (shared) {
			index = getSharedIndex(index, file);
		}

		DesignerElement removedEntry = null;
		if (shared || changeType == IResourceDelta.CHANGED) {
			// a change, first remove the existing entry
			// then, create the new one
			removedEntry = removeIndexEntry(index, file, IStudioElementDelta.CHANGED);
		}
		
		RuleElement ruleIndexEntry = new RuleElementCreator().createRuleElement(file);
		if (ruleIndexEntry == null) {
			ruleIndexEntry = IndexFactory.eINSTANCE.createRuleElement();
			String name = file.getFullPath().removeFileExtension().lastSegment();
			ruleIndexEntry.setName(name);
			if (IndexUtils.isRuleType(file)) {
				ruleIndexEntry.setElementType(ELEMENT_TYPES.RULE);
			} else {
				ruleIndexEntry.setElementType(ELEMENT_TYPES.RULE_FUNCTION);
			}
			ruleIndexEntry.setIndexName(file.getProject().getName());
			IPath path = file.getFullPath().removeLastSegments(1);
			path = path.removeFirstSegments(1);
			ruleIndexEntry.setFolder(path.toString());
		}
		if (shared) {
			String folder = ruleIndexEntry.getFolder();
			populateSharedElement(file, (SharedElement) ruleIndexEntry, folder);
		}
		index.getRuleElements().add(ruleIndexEntry);
		insertElement(index, removedEntry, ruleIndexEntry, file, buildDelta);
	}

	private void processStateMachine(DesignerProject index, IFile file, int changeType, boolean buildDelta) {
		boolean shared = file.isLinked(IResource.CHECK_ANCESTORS);
		if (shared) {
			index = getSharedIndex(index, file);
		}
		DesignerElement removedEntry = null;
		if (shared || changeType == IResourceDelta.CHANGED) {
			// a change, first remove the existing entry
			// then, create the new one
			removedEntry = removeIndexEntry(index, file, IStudioElementDelta.CHANGED);
		}
		
		EObject eObj = IndexUtils.loadEObject(ResourceHelper.getLocationURI(file));
		if (!(eObj instanceof StateMachine)) {
			System.out.println("Not a state machine: "+eObj);
			return;
		}
		StateMachine stateMachine = (StateMachine) eObj;
		StateMachineElement smIndexEntry = new BaseStateMachineElementCreator().createStateMachineElement(file.getProject().getName(), stateMachine, shared);
		if (shared) {
			populateSharedElement(file, (SharedElement) smIndexEntry, stateMachine.getFolder());
			stateMachine.setOwnerProjectName(file.getProject().getName());
			((SharedEntityElement)smIndexEntry).setSharedEntity(stateMachine);
		}
		index.getEntityElements().add(smIndexEntry);
		insertElement(index, removedEntry, smIndexEntry, file, buildDelta);
	}
	
	private void processArchive(DesignerProject index, IFile file, int changeType, boolean buildDelta) {
		DesignerElement removedEntry = null;
		if (changeType == IResourceDelta.CHANGED) {
			// a change, first remove the existing entry
			// then, create the new one
			removedEntry = removeIndexEntry(index, file, IStudioElementDelta.CHANGED);
		}

		ArchiveResource archive = (ArchiveResource) IndexUtils.loadEObject(ResourceHelper.getLocationURI(file));
		ArchiveElement archiveElement = IndexFactory.eINSTANCE.createArchiveElement();
		archiveElement.setName(archive.getName());
		archiveElement.setArchive(archive);
		archiveElement.setFolder(IndexUtils.getFileFolder(file));
		index.getArchiveElements().add(archiveElement);
		insertElement(index, removedEntry, archiveElement, file, buildDelta);
	}

	private void processImplementation(DesignerProject index, IFile file, int changeType, boolean buildDelta) {
		if (IResourceDelta.CHANGED == changeType) {
			processImplementationChange(index, file, changeType, buildDelta);
		} else {
			reIndexImplementation(index, file, changeType, buildDelta);
		}
	}
	
	/**
	 * Re-Index the entire table.
	 * @param index
	 * @param file
	 * @param changeType
	 * @param buildDelta
	 */
	private void reIndexImplementation(DesignerProject index,
			                           IFile file, 
			                           int changeType, 
			                           boolean buildDelta) {
		boolean shared = file.isLinked(IResource.CHECK_ANCESTORS);
		if (shared) {
			index = getSharedIndex(index, file);
		}

		/**
		 * A change, first remove the existing entry, then create the new one.
		 */
		DesignerElement removedEntry = 
			removeIndexEntry(index, file, IStudioElementDelta.CHANGED);
		Implementation impl = (Implementation) IndexUtils.loadEObject(ResourceHelper.getLocationURI(file));
		DecisionTableElement decisionTableElement = null;
		if (shared) {
			decisionTableElement = IndexFactory.eINSTANCE.createSharedDecisionTableElement();
			populateSharedElement(file, (SharedElement) decisionTableElement, impl.getFolder());
		} else {
			decisionTableElement = IndexFactory.eINSTANCE.createDecisionTableElement();
		}
		decisionTableElement.setName(impl.getName());
		decisionTableElement.setFolder(impl.getFolder());
		decisionTableElement.setElementType(ELEMENT_TYPES.DECISION_TABLE);
		decisionTableElement.setImplementation(impl);
		index.getDecisionTableElements().add(decisionTableElement);
		insertElement(index, removedEntry, decisionTableElement, file, buildDelta);
	}
	
	/**
	 * Update the index with only the checkpoint delta
	 * @param index
	 * @param file
	 */
	private void processImplementationChange(DesignerProject index, 
			                                 IFile file, 
			                                 int changeType, 
			                                 boolean needsDelta) {
		StudioCorePlugin.debug("Entered Index Update for implementation change");
		boolean shared = file.isLinked(IResource.CHECK_ANCESTORS);
		if (shared) {
			index = getSharedIndex(index, file);
		}

		//TODO put next 15 lines code in some reusable method
		ElementContainer parentFolder = IndexUtils.getFolderForFile(index, file);
		String folder = IndexUtils.getFileFolder(file);
		EList<DesignerElement> entries = parentFolder.getEntries();
		DesignerElement element = null;
		
		for (DesignerElement designerElement : entries) {
			if (designerElement instanceof TypeElement) {
				TypeElement typeElement = (TypeElement) designerElement;
				String fileName = file.getFullPath().removeFileExtension().lastSegment();
				if (fileName.equals(typeElement.getName()) && folder.equalsIgnoreCase(typeElement.getFolder().replaceAll("\\\\", "/"))) {
					element = typeElement;
					break;
				}
			}
		}
		if (needsDelta && element instanceof DecisionTableElement) {
			DecisionTableElement decisionTableElement = (DecisionTableElement)element;
			List<UndoableCommandCheckpointEntry<IExecutableCommand>> checkpointDelta = 
					getCheckpointDeltaObjects(index, decisionTableElement);
			//Iterate in the order commands were executed.
			//Here we follow FIFO model because for index updates
			//the sequence of operations performed is important.
			if (checkpointDelta.size() > 0) {
				for (int loop = checkpointDelta.size() - 1; loop >= 0; loop--) {
					UndoableCommandCheckpointEntry<IExecutableCommand> deltaEntry = checkpointDelta.get(loop);
					if (deltaEntry.needsReIndex()) {
						StudioCorePlugin.debug("ReIndex required for entry with command {0}", deltaEntry.getCommand());
						//Simply re-index
						reIndexImplementation(index, file, changeType, needsDelta);
						break;
					}
					ICheckpointDeltaEntryHandler checkpointDeltaEntryHandler = 
							new UndoableCommandCheckpointEntryHandler(deltaEntry, (Table)decisionTableElement.getImplementation());
					if (checkpointDeltaEntryHandler != null) {
						checkpointDeltaEntryHandler.handleEntry();
					}
				}
			} else {
				// Simply re-index - the delta length can be 0 if file is overwritten externally
				reIndexImplementation(index, file, changeType, needsDelta);
			}
		}
	}
	
	private List<UndoableCommandCheckpointEntry<IExecutableCommand>> getCheckpointDeltaObjects(DesignerProject index, 
			                                       DecisionTableElement element) {
		String projectName = index.getName();
		Table tableEModel = (Table)element.getImplementation();
		return CommandFacade.getInstance().getCheckpointDeltaObjects(projectName, tableEModel);
	}
	
	
	private void processJava(DesignerProject index, IFile file, int changeType, boolean buildDelta) {
		boolean shared = file.isLinked(IResource.CHECK_ANCESTORS);
		if (shared) {
			index = getSharedIndex(index, file);
		}
		
		DesignerElement removedEntry = null;
		if (shared || changeType == IResourceDelta.CHANGED) {
			// a change, first remove the existing entry
			// then, create the new one
			removedEntry = removeIndexEntry(index, file, IStudioElementDelta.CHANGED);
		}
		
		JavaSource javaEntity = IndexUtils.loadJavaEntity(file);
		JavaElement javaElement = JavaElementCreator.createJavaElement(file, javaEntity, shared);
		if (shared) {
			String folder = javaEntity.getFolder();
			populateSharedElement(file, (SharedElement) javaElement, folder);
		}
		
		index.getEntityElements().add(javaElement);
		insertElement(index, removedEntry, javaElement, file, buildDelta);
	}
	
	private void processJavaResource(DesignerProject index, IFile file, int changeType, boolean buildDelta) {
		boolean shared = file.isLinked(IResource.CHECK_ANCESTORS);
		if (shared) {
			index = getSharedIndex(index, file);
		}

		DesignerElement removedEntry = null;
		if (shared || changeType == IResourceDelta.CHANGED) {
			// a change, first remove the existing entry
			// then, create the new one
			removedEntry = removeIndexEntry(index, file, IStudioElementDelta.CHANGED);
		}

		JavaResource javaEntity = IndexUtils.loadJavaResource(file);
		JavaResourceElement javaElement = JavaElementCreator.createJavaResourceElement(file, javaEntity, shared);
		if (shared) {
			String folder = javaEntity.getFolder();
			populateSharedElement(file, (SharedElement) javaElement, folder);
		}

		index.getEntityElements().add(javaElement);
		insertElement(index, removedEntry, javaElement, file, buildDelta);
	}
	
	private void processEntity(DesignerProject index, IFile file, int changeType, boolean buildDelta) {
		boolean shared = file.isLinked(IResource.CHECK_ANCESTORS);
		if (shared) {
			index = getSharedIndex(index, file);
		}
		DesignerElement removedEntry = null;
		if (shared || changeType == IResourceDelta.CHANGED) {
			// a change, first remove the existing entry
			// then, create the new one
			removedEntry = removeIndexEntry(index, file, IStudioElementDelta.CHANGED);
		}

		EObject eObj = IndexUtils.loadEObject(ResourceHelper.getLocationURI(file));
		if (!(eObj instanceof Entity)) {
			System.out.println("Not an entity: "+eObj);
			return;
		}
		Entity entity = (Entity) eObj;
		EntityElement indexEntry = null;
		if (entity instanceof SimpleEvent) {
			indexEntry = new EventElementCreator().createEventElement(file.getProject().getName(), (SimpleEvent) entity, shared);
		} else {
			indexEntry = shared ? IndexFactory.eINSTANCE.createSharedEntityElement() : IndexFactory.eINSTANCE.createEntityElement();
			indexEntry.setName(entity.getName());
			indexEntry.setFolder(entity.getFolder());
			indexEntry.setEntity(entity);
			indexEntry.setElementType(IndexUtils.getElementType(entity));
		}
		if (shared) {
			String folder = entity.getFolder();
			populateSharedElement(file, (SharedElement) indexEntry, folder);
			entity.setOwnerProjectName(file.getProject().getName());
			((SharedEntityElement)indexEntry).setSharedEntity(entity);
		}
		index.getEntityElements().add(indexEntry);
		addDomainInstanceEntries(index, entity, file);
		insertElement(index, removedEntry, indexEntry, file, buildDelta);
	}

	private void populateSharedElement(IFile file, SharedElement indexEntry,
			String folder) {
		if (folder.charAt(0) == '/') {
			folder = folder.substring(1);
		}
		indexEntry.setEntryPath(folder);
		IPath fullPath = file.getFullPath();
		String fileName;
		if (fullPath.lastSegment().length() > 0 && fullPath.lastSegment().charAt(0) == '.') {
			fileName = fullPath.lastSegment();
			if (fileName.lastIndexOf('.') > 1) {
				// for names like .stuff.xsd
				fileName = fullPath.removeFileExtension().lastSegment();
			}
		} else {
			fileName = fullPath.removeFileExtension().lastSegment();
		}

		indexEntry.setName(fileName);
		indexEntry.setFileName(file.getName());
		indexEntry.setArchivePath(IndexUtils.getArchivePath(file));
	}
	
	

	private DesignerProject getSharedIndex(DesignerProject index,
			IFile linkedFile) {
		IResource archiveFile = IndexUtils.getArchiveFile(linkedFile);
		String archivePath = archiveFile.getLocation().toString();
		EList<DesignerProject> referencedProjects = index.getReferencedProjects();
		for (int i = 0; i < referencedProjects.size(); i++) {
			DesignerProject proj = referencedProjects.get(i);
			if (proj.getArchivePath().equals(archivePath)) {
				return proj;
			}
		}
		// need to create one
		DesignerProject project = IndexFactory.eINSTANCE.createDesignerProject();
		project.setName(archivePath); // other areas seem to assume the name is the full path...
		project.setRootPath(archivePath);
		project.setArchivePath(archivePath);
		index.getReferencedProjects().add(project);
		return project;
	}

	/*
	 * (non-javadoc) Based on the path of the 
	 * file, find the entry in the appropriate
	 * index and remove the entry
	 * @param file
	 */
	private DesignerElement removeIndexEntry(DesignerProject index, IFile file, int type) {
		ElementContainer parentFolder = IndexUtils.getFolderForFile(index, file);
		if (parentFolder == null) {
			// the entry likely does not exist.  No need to remove it
			return null;
		}
//		String folder = IndexUtils.getFileFolder(file); // no need to check the folder, as we're getting the entries from the parent folder already
		EList<DesignerElement> entries = parentFolder.getEntries();
		DesignerElement elementToRemove = null;
		for (DesignerElement designerElement : entries) {
//			if (designerElement instanceof TypeElement) {
//				TypeElement typeElement = (TypeElement) designerElement;
			IPath fullPath = file.getFullPath();
			String fileName;
			if (fullPath.lastSegment().length() > 0 && fullPath.lastSegment().charAt(0) == '.') {
				fileName = fullPath.lastSegment();
				if (fileName.lastIndexOf('.') > 1) {
					// for names like .stuff.xsd
					fileName = fullPath.removeFileExtension().lastSegment();
				}
			} else {
				fileName = fullPath.removeFileExtension().lastSegment();
			}
			if (fileName.equals(designerElement.getName())) { // && folder.equalsIgnoreCase(typeElement.getFolder().replaceAll("\\\\", "/"))) {
				elementToRemove = designerElement;
				break;
			}
//			}
		}
		return removeDesignerElement(index, file, type, parentFolder,
				elementToRemove);
	}

	public void removeDesignerElements(IFile file, List<? extends DesignerElement> elementsToRemove, DesignerProject index) {
		for (DesignerElement typeElement : elementsToRemove) {
			EObject container = typeElement.eContainer();
			if (container instanceof ElementContainer) {
				removeDesignerElement(index, file, -1, (ElementContainer) container, typeElement);
			}
		}

	}
	
	private DesignerElement removeDesignerElement(DesignerProject index,
			IFile file, int type, ElementContainer parentFolder,
			DesignerElement elementToRemove) {
		if (elementToRemove != null) {
			parentFolder.getEntries().remove(elementToRemove);
			if (type == IStudioElementDelta.REMOVED) {
				insertDeltaElement(index, parentFolder, elementToRemove, null, type);
			}
			if (elementToRemove instanceof EntityElement) {
				removeDomainInstanceEntries(index, (EntityElement) elementToRemove);
			}
			List<? extends TypeElement> children = null;
			if (IndexUtils.isArchiveType(file)) {
				children = index.getArchiveElements();
			} else if (IndexUtils.isRuleType(file)) {
				children = index.getRuleElements();
			} else if (IndexUtils.isImplementationType(file)) {
				children = index.getDecisionTableElements();
			} else if (IndexUtils.isEMFType(file)) {
				children = index.getEntityElements();
			}
			checkForEmptyContainer(parentFolder);
			if (children == null) {
				// will be null for java types, ignore
//				System.out.println("could not find parent list for "+file.toString());
				return null;
			}
			children.remove(elementToRemove);
			return elementToRemove;
		}
		return null;
	}

	private void checkForEmptyContainer(ElementContainer container) {
		if (container.getEntries().size() == 0) {
			if (container.eContainer() instanceof ElementContainer) {
				ElementContainer parentContainer = (ElementContainer) container.eContainer();
				parentContainer.getEntries().remove(container);
				checkForEmptyContainer(parentContainer);
			}
		}
	}

	private void removeDomainInstanceEntries(DesignerProject index, EntityElement elementToRemove) {
		Entity entity = elementToRemove.getEntity();
		if (!(entity instanceof Concept) && !(entity instanceof Event)) {
			return;
		}
		EList<InstanceElement<?>> elements = index.getDomainInstanceElements();
		InstanceElement<?> entryToRemove = null;
		for (int i=0; i<elements.size(); i++) {
			InstanceElement<?> instanceEntry = elements.get(i);
			if (instanceEntry.getEntityPath().equals(entity.getFullPath())) {
				entryToRemove = instanceEntry;
			}
		}
		if (entryToRemove != null) {
			elements.remove(entryToRemove);
		}
	}

	private void addDomainInstanceEntries(DesignerProject index, Entity entity, IFile file) {
		if (!(entity instanceof Concept) && !(entity instanceof Event)) {
			return;
		}
		EList<PropertyDefinition> properties = null;
		if (entity instanceof Concept) {
			properties = ((Concept) entity).getProperties();
		} else if (entity instanceof Event) {
			properties = ((Event) entity).getProperties();
		}
		if (properties == null) {
			return;
		}
		EList<InstanceElement<?>> elements = index.getDomainInstanceElements();
		InstanceElement<?> resourceEntry = null;
		String fullPath = entity.getFullPath();
		for (int i=0; i<elements.size(); i++) {
			InstanceElement<?> instanceEntry = elements.get(i);
			if (instanceEntry.getEntityPath().equals(fullPath)) {
				resourceEntry = instanceEntry;
			}
		}
		if (resourceEntry == null) {
			resourceEntry = IndexFactory.eINSTANCE.createInstanceElement();
			resourceEntry.setEntityPath(fullPath);
			resourceEntry.setElementType(ELEMENT_TYPES.DOMAIN_INSTANCE);
			resourceEntry.setName(file.getName());
		}
		for (int i = 0; i < properties.size(); i++) {
			PropertyDefinition propDef = properties.get(i);
			EList<DomainInstance> domainInstances = propDef.getDomainInstances();
			for (int j = 0; j < domainInstances.size(); j++) {
				DomainInstance domainInstance = domainInstances.get(j);
				EList instances = resourceEntry.getInstances();
				instances.add(domainInstance);
			}
		}
		if (resourceEntry.getInstances().size() > 0) {
			elements.add(resourceEntry);
		}
	}
	
	private void insertDeltaElement(DesignerProject index,
			ElementContainer parentFolder, DesignerElement oldElement, DesignerElement insertedElement, int type) {
		if (insertedElement instanceof SharedElement) {
			// TODO : Insert delta element in reference project delta
			return;
		}
		if (index.eContainer() instanceof DesignerProject) {
			index = (DesignerProject) index.eContainer();
		}
		StudioProjectDelta delta = getDelta(index);
		ElementContainer parentDelta = getDeltaContainer(delta, parentFolder);
		parentDelta.getEntries().add(new StudioElementDelta(oldElement, insertedElement, type));
	}

	private ElementContainer getDeltaContainer(StudioProjectDelta projectDelta, ElementContainer parentFolder) {
		if (parentFolder.equals(projectDelta.getAffectedChild())) {
			return projectDelta;
		}
		// need to get/create the folder delta structure for this file
		Stack<ElementContainer> containerStack = new Stack<ElementContainer>();
		containerStack.push(parentFolder);
		ElementContainer container = (ElementContainer) parentFolder.eContainer();
		while (container != null && !(container instanceof DesignerProject)) {
			containerStack.push(container);
			container = (ElementContainer) container.eContainer();
		}
		ElementContainer folderDelta = getElementContainerDelta((ElementContainer)projectDelta, parentFolder, containerStack);
		return folderDelta;
	}

	private ElementContainer getElementContainerDelta(
			ElementContainer parentDelta, ElementContainer targetFolder, Stack<ElementContainer> stack) {
		ElementContainer elementContainer = stack.pop();
		EList<DesignerElement> entries = parentDelta.getEntries();
		for (DesignerElement designerElement : entries) {
			if (elementContainer.equals(((IStudioElementDelta)designerElement).getAffectedChild())) {
				if (stack.size() == 0) {
					return (ElementContainer) designerElement;
				}
				return getElementContainerDelta((ElementContainer) designerElement, targetFolder, stack);
			}
		}
		// this folder delta does not yet exist
		FolderDelta delta = new FolderDelta(elementContainer, IStudioElementDelta.CHANGED);
		parentDelta.getEntries().add(delta);
		if (stack.size() == 0) {
			return delta;
		}
		return getElementContainerDelta(delta, targetFolder, stack);
	}

	private StudioProjectDelta getDelta(DesignerProject index) {
		return fAffectedProjects.get(index);
	}

	private boolean handleProjectChanged(IResourceDelta delta,
			IProject project) {
		synchronized (getLock(project)) {
			if (delta.getKind() == IResourceDelta.REMOVED) {
				removeIndex(project, true);
				return false;
			} else if (delta.getKind() == IResourceDelta.CHANGED) {
				if (!project.isOpen()) {
					removeIndex(project, false);
					return false;
				}
				if (!StudioCorePlugin.getDesignerModelManager().isIndexCached(project) && IndexUtils.indexFileExists(project.getName())) {
					// the index exists but is not loaded, schedule a load job
					StudioCorePlugin.getDesignerModelManager().loadIndex(project);
					return false;
				}
			}
			if (!StudioCorePlugin.getDesignerModelManager().isIndexCached(project) && !StudioCorePlugin.getDesignerModelManager().createIsScheduled(project)) {
				StudioCorePlugin.getDesignerModelManager().createIndex(project, false, new NullProgressMonitor());
				DesignerProject index = StudioCorePlugin.getDesignerModelManager().getIndex(project);
				if (!fAffectedProjects.containsKey(index)) {
					fAffectedProjects.put(index, new StudioProjectDelta(index, IStudioElementDelta.ADDED));
				}
				return false;
			}

			if (StudioCorePlugin.getDesignerModelManager().createIsScheduled(project)) {
				// create is scheduled, no need to process delta
				return false;
			}

		}
		
		return true;
	}
	
	private synchronized Object getLock(IProject project) {
		Object obj = fLocks.get(project);
		if (obj == null) {
			obj = new Object();
			fLocks.put(project, obj);
		}
		return obj;
	}

	public void insertElement(DesignerProject index, DesignerElement oldEntry, DesignerElement entry, IFile file, boolean buildDelta) {
		ElementContainer folder = IndexUtils.getFolderForFile(index, file, true);
		folder.getEntries().add(entry);
		int type = IStudioElementDelta.ADDED;
		if (oldEntry != null) {
			type = IStudioElementDelta.CHANGED;
		}
		if (buildDelta) {
			insertDeltaElement(index, folder, oldEntry, entry, type);
		}
	}

    void removeIndex(IProject project, boolean removeIndexFile) {
    	DesignerProject index = StudioCorePlugin.getDesignerModelManager().getIndex(project);
    	if (!fAffectedProjects.containsKey(index)) {
    		fAffectedProjects.put(index, new StudioProjectDelta(index, IStudioElementDelta.REMOVED));
    	}

    	StudioCorePlugin.getDesignerModelManager().removeIndex((IProject) project, removeIndexFile);			
	}

	public List<StudioProjectDelta> getAffectedProjects() {
		List<StudioProjectDelta> changedProjects = new ArrayList<StudioProjectDelta>();
		changedProjects.addAll(fAffectedProjects.values());
		return changedProjects;
	}

	public boolean hasReferenceChanges() {
		return fHasReferenceChanges ;
	}

}



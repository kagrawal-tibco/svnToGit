package com.tibco.cep.studio.core.index.jobs;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.jar.JarFile;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.archive.ArchiveResource;
import com.tibco.cep.designtime.core.model.element.ElementFactory;
import com.tibco.cep.designtime.core.model.element.ProcessEntity;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.java.JavaResource;
import com.tibco.cep.designtime.core.model.java.JavaSource;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.common.configuration.ProjectLibraryEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.index.model.ArchiveElement;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.EventElement;
import com.tibco.cep.studio.core.index.model.IndexFactory;
import com.tibco.cep.studio.core.index.model.JavaElement;
import com.tibco.cep.studio.core.index.model.JavaResourceElement;
import com.tibco.cep.studio.core.index.model.ProcessElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.StateMachineElement;
import com.tibco.cep.studio.core.index.utils.BaseBinaryStorageIndexCreator;
import com.tibco.cep.studio.core.index.utils.BaseStateMachineElementCreator;
import com.tibco.cep.studio.core.index.utils.EventElementCreator;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.index.utils.JavaElementCreator;
import com.tibco.cep.studio.core.index.utils.RuleElementCreator;
import com.tibco.cep.studio.core.manager.GlobalVariablesMananger;
import com.tibco.cep.studio.core.util.ResourceHelper;

public class IndexCreationResourceVisitor implements IResourceVisitor {

//	private IProgressMonitor fProgressMonitor;
	private DesignerProject fIndex;

	public IndexCreationResourceVisitor(DesignerProject index) {
		this.fIndex = index;
	}

	public boolean visit(IResource resource) throws CoreException {
		if (resource.isLinked() && !resource.getFileExtension().equals(GlobalVariablesMananger.GLOBAL_VAR_EXTENSION)) {
			DesignerProject project = IndexFactory.eINSTANCE
					.createDesignerProject();
			JarFile jarFile;
			try {
				String jarPath = ResourceHelper.getFileLocation(resource);
				jarFile = new JarFile(jarPath);
				BaseBinaryStorageIndexCreator creator = new BaseBinaryStorageIndexCreator(resource.getProject().getName(), project, jarFile);
				creator.index();
				project.setName(jarFile.getName());
				project.setRootPath(jarPath);
				project.setArchivePath(jarPath);
				fIndex.getReferencedProjects().add(project);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}

		if (resource instanceof IContainer) {
			return true;
		}
		if (!(resource instanceof IFile)) {
			return true;
		}
		
		IFile file = (IFile) resource;
		
		if (file.isLinked(IFile.CHECK_ANCESTORS)) {
			System.out.println("Shouldn't happen");
		}
		
		if (IndexUtils.isJavaResource(file) && IndexUtils.isJavaType(file)) {
			indexJava(file);
			return false;
		}
		
		if (IndexUtils.isJavaResource(file) && !IndexUtils.isJavaType(file)) {
			indexJavaResource(file);
			return false;
		}
		
		if (IndexUtils.isEMFType(file) && !IndexUtils.isJavaType(file)) {
			URI uri = ResourceHelper.getLocationURI(file);
			if (uri != null) {
				EObject eObj = IndexUtils.loadEObject(uri);
				indexEObject(file, eObj);
			}
		}
		if (IndexUtils.isRuleType(file)) {
			indexRule(file);
		}
		if (IndexUtils.isProcessType(file)) {
			indexProcess(file);
		}
		return false;
	}
	
	private void indexJava(IFile file) {
		JavaSource javaEntity = IndexUtils.loadJavaEntity(file);
		JavaElement javaIndexEntry = JavaElementCreator.createJavaElement(file,javaEntity,file.isLinked(IResource.CHECK_ANCESTORS));
		fIndex.getEntityElements().add(javaIndexEntry);
		insertElement(javaIndexEntry, file);
	}
	
	private void indexJavaResource(IFile file) {
		JavaResource javaEntity = IndexUtils.loadJavaResource(file);
		JavaResourceElement javaResourcesIndexEntry = JavaElementCreator.createJavaResourceElement(file, javaEntity,file.isLinked(IResource.CHECK_ANCESTORS));
		fIndex.getEntityElements().add(javaResourcesIndexEntry);
		insertElement(javaResourcesIndexEntry, file);
	}
	
	private void indexProcess(IFile file) throws CoreException {
		try {
			ResourceSet rset = new ResourceSetImpl();
			EObject eObj = IndexUtils.loadEObject(ResourceHelper.getLocationURI(file), rset, true);
			EClass eClassType = eObj.eClass();
			IPath fPath = file.getProjectRelativePath().makeAbsolute().removeFileExtension();
			String name = (String) eObj.eGet(eClassType.getEStructuralFeature("name"), true);
			String folder = (String) eObj.eGet(eClassType.getEStructuralFeature("folder"), true);
			IPath folderPath = new Path(folder);
			ProcessEntity pc = ElementFactory.eINSTANCE.createProcessEntity();
			pc.setName(name);
			pc.setFolder(folderPath.addTrailingSeparator().toPortableString());
			EStructuralFeature sf = eClassType.getEStructuralFeature("properties");
			EList<EObject> properties = (EList<EObject>) eObj.eGet(sf);
			for (EObject prop : properties) {

				EStructuralFeature nm = prop.eClass().getEStructuralFeature("name");
				String pname = (String) prop.eGet(nm);
				/**
				 * TODO: Getting the property types is difficult, without moving the
				 * ExtensionHelper,ROEObjectWrapper code to common.
				 */
				PropertyDefinition pd = ElementFactory.eINSTANCE.createPropertyDefinition();
				pd.setName(pname);
				pd.setOwnerProjectName(file.getProject().getName());
				pd.setOwnerPath(fPath.toPortableString());
				pc.getProperties().add(pd);
				

			}

			ProcessElement indexEntry = IndexFactory.eINSTANCE.createProcessElement();
			// pc.setTransient(true);
			indexEntry.setProcess(pc);
			indexEntry.setName(name);
			indexEntry.setFolder(folderPath.addTrailingSeparator().toPortableString());
			indexEntry.setElementType(ELEMENT_TYPES.PROCESS);
			fIndex.getEntityElements().add(indexEntry);
			insertElement(indexEntry, file);
			// if (entity.eResource() != null) {
			// insertElement(indexEntry, file);
			// }

		} catch (Exception e) {
			throw new CoreException(new org.eclipse.core.runtime.Status(IStatus.ERROR, StudioCorePlugin.PLUGIN_ID, "Failed to load and index process file:"
					+ file.getFullPath(), e));
		}
	}

	private void indexRule(IFile file) {
		RuleElement ruleIndexEntry = new RuleElementCreator().createRuleElement(file);
		if (ruleIndexEntry != null) {
			fIndex.getRuleElements().add(ruleIndexEntry);
			insertElement(ruleIndexEntry, file);
		}
	}

	private void indexEObject(IFile file, EObject eObj) {
		boolean isLinkedResource = file.isLinked(IResource.CHECK_ANCESTORS);
		String ownerProject = file.getProject().getName();
		if (eObj instanceof StateMachine) {
			if(isLinkedResource){
				((StateMachine)eObj).setOwnerProjectName(ownerProject);
			}
			StateMachineElement smIndexEntry = new BaseStateMachineElementCreator().createStateMachineElement(file.getProject().getName(), (StateMachine) eObj);
			fIndex.getEntityElements().add(smIndexEntry);
			if (smIndexEntry.eResource() != null) {
				smIndexEntry.eResource().unload();
			}
			insertElement(smIndexEntry, file);
		} else if (eObj instanceof SimpleEvent) {
			EventElement indexEntry = new EventElementCreator().createEventElement(file.getProject().getName(), (SimpleEvent)eObj);
			SimpleEvent entity = (SimpleEvent) indexEntry.getEntity();
			if(isLinkedResource){
				entity.setOwnerProjectName(ownerProject);
			}
//			EventElement indexEntry = IndexFactory.eINSTANCE.createEventElement();
//			indexEntry.setEntity(entity);
//			indexEntry.setName(entity.getName());
//			indexEntry.setFolder(entity.getFolder());
//			indexEntry.setElementType(IndexUtils.getElementType(entity));
			
			fIndex.getEntityElements().add(indexEntry);
			if (entity.eResource() != null) {
				entity.eResource().unload();
			}
			insertElement(indexEntry, file);
		} else if (eObj instanceof Entity) {
			Entity entity = (Entity) eObj;
			if(isLinkedResource){
				entity.setOwnerProjectName(ownerProject);
			}
			EntityElement indexEntry = IndexFactory.eINSTANCE.createEntityElement();
			indexEntry.setEntity(entity);
			indexEntry.setName(entity.getName());
			indexEntry.setFolder(entity.getFolder());
			indexEntry.setElementType(IndexUtils.getElementType(entity));
			fIndex.getEntityElements().add(indexEntry);
			if (entity.eResource() != null) {
				entity.eResource().unload();
			}
			insertElement(indexEntry, file);
		} else if (eObj instanceof ArchiveResource) {
			ArchiveResource archive = (ArchiveResource) eObj;
			ArchiveElement archiveIndexEntry = IndexFactory.eINSTANCE.createArchiveElement();
			archiveIndexEntry.setName(archive.getName());
			archiveIndexEntry.setArchive(archive);
			archiveIndexEntry.setFolder(IndexUtils.getFileFolder(file));
			archiveIndexEntry.setElementType(ELEMENT_TYPES.ENTERPRISE_ARCHIVE);
			if (archive.eResource() != null) {
				archive.eResource().unload();
			}
			fIndex.getArchiveElements().add(archiveIndexEntry);
			insertElement(archiveIndexEntry, file);
		} else if (eObj instanceof Implementation) {
			Implementation impl = (Implementation) eObj;
			if(isLinkedResource){
				impl.setOwnerProjectName(ownerProject);
			}
			DecisionTableElement decisionTableElement = IndexFactory.eINSTANCE.createDecisionTableElement();
			decisionTableElement.setName(impl.getName());
			decisionTableElement.setFolder(impl.getFolder());
			decisionTableElement.setElementType(ELEMENT_TYPES.DECISION_TABLE);
			decisionTableElement.setImplementation(impl);
			fIndex.getDecisionTableElements().add(decisionTableElement);
			insertElement(decisionTableElement, file);
		}
	}
	
	public void indexProjectLibraries(IProject resource) {
		/*
		 * Get all project libs
		 * For each lib, get the jar file
		 * For each jar file, invoke a jar file visitor to index entries
		 */
		try {
			StudioProjectConfiguration configuration = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(resource.getName());
			for (ProjectLibraryEntry ple: configuration.getProjectLibEntries()) {
				String libRef = ple.getPath(ple.isVar());
				System.out.println("indexing "+libRef);
				JarFile file = new JarFile(libRef);
				DesignerProject project = IndexFactory.eINSTANCE.createDesignerProject();
				project.setName(file.getName());
				project.setRootPath(libRef);
				project.setArchivePath(libRef);
				/**
				 * After Projlib FileStore the artifact in the projlib are getting indexed twice
				 */
//				BinaryStorageIndexCreator creator = new BinaryStorageIndexCreator(project, file, fProgressMonitor, fIndex.getName());
//				creator.index();
				fIndex.getReferencedProjects().add(project);
			}
//			IDependencyRootNode rootNode = ProjectLibraryResourceProvider.getInstance().getDependencyRootNode(resource.getName());
//			if (rootNode == null) {
//				return;
//			}
//			List<IResourceNode> children = rootNode.getChildren();
//			for (IResourceNode resourceNode : children) {
//				String libRef = resourceNode.getLibRef();
//				JarFile file = new JarFile(libRef);
//				DesignerProject project = IndexFactory.eINSTANCE.createDesignerProject();
//				project.setName(file.getName());
//				project.setRootPath(file.toString());
//				BinaryStorageIndexCreator creator = new BinaryStorageIndexCreator(project, file, fProgressMonitor);
//				creator.index();
//				fIndex.getReferencedProjects().add(project);
//			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void insertElement(DesignerElement entry, IFile file) {
		ElementContainer folder = IndexUtils.getFolderForFile(fIndex, file, true);
		if (entry != null) {
			folder.getEntries().add(entry);
		}
	}

}

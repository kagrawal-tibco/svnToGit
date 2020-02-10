package com.tibco.cep.bpmn.core.index.visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.core.index.update.BpmnElementDelta;
import com.tibco.cep.bpmn.core.index.update.BpmnProjectDelta;
import com.tibco.cep.bpmn.core.index.update.IBpmnElementDelta;
import com.tibco.cep.bpmn.core.nature.BpmnProjectNatureManager;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;

public class BpmnIndexUpdateVisitor implements IResourceDeltaVisitor {
	
	private HashMap<IProject, BpmnProjectDelta> fAffectedProjects = new HashMap<IProject, BpmnProjectDelta>();
	private static HashMap<IProject, Object> fLocks = new HashMap<IProject, Object>();
	
	public BpmnIndexUpdateVisitor() {
	}

	@Override
	public boolean visit(IResourceDelta delta) throws CoreException {
		IResource resource = delta.getResource();
		if(resource instanceof IWorkspaceRoot) {
			IResourceDelta[] affectedResources = delta.getAffectedChildren();
			if(affectedResources.length > 0) {
				return true;
			} 
		}
		
		if(!isBpmnProjectResource(resource))
			return false;
		
		if (resource instanceof IContainer) {
			if (resource instanceof IProject ) {				
				return handleProjectChanged(delta);
			}
			return true;
		}
		if(resource instanceof IFile) {
			return handleFileChanged(delta);
		}
		return false;
	}
	
	private boolean isBpmnProjectResource(IResource resource){
		IProject project = resource.getProject();
		if(project != null && BpmnProjectNatureManager.getInstance().isBpmnProject(project))
			return true;
		
		return false;
		
	}
	
	private synchronized Object getLock(IProject project) {
		Object obj = fLocks.get(project);
		if (obj == null) {
			obj = new Object();
			fLocks.put(project, obj);
		}
		return obj;
	}
	
	
	void removeIndex(IProject project, boolean removeIndexFile,boolean removeEntry) {
    	EObject index = BpmnIndexUtils.getIndex(project);
    	if (!fAffectedProjects.containsKey(index)) {
    		fAffectedProjects.put(project, new BpmnProjectDelta(index, IBpmnElementDelta.REMOVED));
    	}

    	BpmnCorePlugin.getDefault().getBpmnModelManager().removeIndex((IProject) project, removeIndexFile, removeEntry);			
	}
	
	
	private boolean handleFileChanged(IResourceDelta delta) throws CoreException {
		IFile file = (IFile) delta.getResource();
		if (!BpmnCorePlugin.getDefault().getBpmnModelManager().isIndexCached(file.getProject()) 
				&& BpmnCorePlugin.getDefault().getBpmnModelManager().createIsScheduled(file.getProject())) {
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
		EObject index = BpmnIndexUtils.getIndex(project);
		// load import again if there is any change in BE artifacts
//		if (!BpmnIndexUtils.isBpmnType(file)) {
//			try {
//				IExtensionRegistry reg = Platform.getExtensionRegistry();
//				IConfigurationElement[] extensions = reg
//						.getConfigurationElementsFor(BpmnCoreConstants.EXTENSION_POINT_INDEX_UPDATE);
//				for (int i = 0; i < extensions.length; i++) {
//					IConfigurationElement element = extensions[i];
//					final Object o = element.createExecutableExtension(BpmnCoreConstants.EXTENSION_POINT_ATTR_INDEX);
//					if (o instanceof IBpmnIndexUpdate) {
//						((IBpmnIndexUpdate) o).onIndexUpdate(project, index);
//					}
//				}	
//			}catch (Exception e) {
//				BpmnCorePlugin.log(e);
//			}
//		}

    	if (!fAffectedProjects.containsKey(index)) {
    		fAffectedProjects.put(project, new BpmnProjectDelta(index, IBpmnElementDelta.CHANGED));
    	}

		if (delta.getKind() == IResourceDelta.REMOVED || !file.exists()) {
			if (!BpmnIndexUtils.isBpmnType(file)) {
				return false;
			}
			removeIndexEntry(index, file, IBpmnElementDelta.REMOVED);
			return false;
		}
		
		return reindexFile(delta.getKind(), file, index, true);
	}
	
	
	public boolean reindexFile(int kind, IFile file, EObject index,
			boolean buildDelta) throws CoreException {
		if(BpmnIndexUtils.isBpmnType(file)) {
			handleBpmnProcess(kind,file,index,buildDelta);
		}
		return false;
	}

	private void handleBpmnProcess(int kind, IFile file, EObject index,
			boolean buildDelta) throws CoreException {
		if(index == null)
			return;
		EObjectWrapper<EClass, EObject> indexWrapper = EObjectWrapper.wrap(index);
		if (kind == IResourceDelta.CHANGED) {
			// a change, first remove the existing entry
			// then, create the new one
			removeIndexEntry(index, file, BpmnElementDelta.CHANGED);
		}
		BpmnIndexRefreshVisitor indexRefreshVisitor = new BpmnIndexRefreshVisitor(indexWrapper,file.getProject(),true);
		file.accept(indexRefreshVisitor);
		
	}
	
	/**
	 * @param index
	 * @param file
	 * @param removed
	 * @return 
	 * @throws CoreException 
	 */
	private void removeIndexEntry(EObject index, IFile file, int deltaKind) throws CoreException {
		EObjectWrapper<EClass, EObject> indexWrapper = EObjectWrapper.wrap(index);
		List<EObject> rootElements = BpmnCommonIndexUtils.getRootElements(indexWrapper);
		final Map<String,EObject> rElements = new HashMap<String,EObject>();
		// get all the root elements from all processes
		BpmnElementVisitor visitor = new BpmnElementVisitor(false,new NullProgressMonitor(),true) {
			@Override
			public boolean visit(EObject obj) {
				EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper.wrap(obj);
				if(wrapper.isInstanceOf(BpmnModelClass.ROOT_ELEMENT)){
					String id = wrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
					BpmnCorePlugin.debug("id="+id);
					rElements.put(id,obj);
				}
				return true;
			}
		};
		file.getProject().accept(visitor);
		
		for(Iterator<EObject> iter = rootElements.iterator();iter.hasNext();) {
			EObjectWrapper<EClass, EObject> elementWrapper = EObjectWrapper.wrap(iter.next());
			String id = elementWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			if(!rElements.containsKey(id) && !elementWrapper.isInstanceOf(BpmnModelClass.ITEM_DEFINITION) && !elementWrapper.isInstanceOf(BpmnModelClass.INTERFACE)) {
				if(elementWrapper.getEInstance().eIsProxy()) {
					URI proxyURI = ((org.eclipse.emf.ecore.InternalEObject)elementWrapper.getEInstance()).eProxyURI();
					URI fileURI  = URI.createPlatformResourceURI(file.getFullPath().toPortableString(),false);
					if(proxyURI.path().equals(fileURI.path())){
						BpmnCorePlugin.debug("Removing rootElement->"+proxyURI.fragment());
						BpmnCommonIndexUtils.removeRootElement(indexWrapper, elementWrapper.getEInstance());
					}
				} else {
					BpmnCorePlugin.debug("Removing rootElement->"+id);
					BpmnCommonIndexUtils.removeRootElement(indexWrapper, elementWrapper.getEInstance());
				}
//				removeElement(index, deltaKind,elementWrapper.getEInstance());
			}			
		}
		Set<String> rootElementIds = getRootElementIds(index);
		for(Entry<String, EObject> rElement:rElements.entrySet()){
			EObjectWrapper<EClass, EObject> elementWrapper = EObjectWrapper.wrap(rElement.getValue());
			String id = elementWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			if(!rootElementIds.contains(id)) {
				BpmnCorePlugin.debug("Adding rootElement->"+id);
				BpmnCommonIndexUtils.addRootElement(indexWrapper, rElement.getValue());
			}
		}
		
		boolean hasProcess = (BpmnIndexUtils.getAllProcesses(file.getProject().getName()).size() > 0);
		BpmnProjectNatureManager.getInstance().enableBpmnNature(file.getProject(),hasProcess);
	}

	private Set<String> getRootElementIds(EObject index) {
		Set<String> ids = new HashSet<String>();
		EObjectWrapper<EClass, EObject> indexWrapper = EObjectWrapper.wrap(index);
		List<EObject> rootElements = BpmnCommonIndexUtils.getRootElements(indexWrapper);
		for(EObject re:rootElements){
			EObjectWrapper<EClass, EObject> elementWrapper = EObjectWrapper.wrap(re);
			ids.add((String)elementWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID));
		}
		return ids;
	}
	


	/**
	 * removes root elements from the index
	 * @param index
	 * @param elementToRemove
	 * @param element 
	 * @return 
	 */
	@SuppressWarnings("unused")
	private void removeElement(EObject index,int deltaKind, EObject element) {
		if(element != null) {			
			EObjectWrapper<EClass, EObject> indexWrapper = EObjectWrapper.wrap(index);
			BpmnCommonIndexUtils.removeRootElement(indexWrapper, element);
//			if (deltaKind == IBpmnElementDelta.REMOVED) {
//				insertDeltaElement(index,elementToRemove, null, deltaKind);
//			}
			
		}		
	}
	
	@SuppressWarnings("unused")
	private void insertDeltaElement(EObject index,
									EObject oldElement, 
									EObject insertedElement, int type) {
//		BpmnProjectDelta delta = getDelta(index);
//		ElementContainer parentDelta = getDeltaContainer(delta, parentFolder);
//		parentDelta.getEntries().add(new BpmnElementDelta(oldElement, insertedElement, type));
	}
	
	
	@SuppressWarnings("unused")
	private BpmnProjectDelta getDelta(EObject index) {
		return fAffectedProjects.get(index);
	}

	private boolean handleProjectChanged(IResourceDelta delta) {
		IProject project = (IProject) delta.getResource();
		synchronized (getLock(project)) {
			switch (delta.getKind()) {
			case IResourceDelta.REMOVED:
				removeIndex(project, true,true);            
				return false;
			case IResourceDelta.CHANGED:
				if (!project.isOpen()) {
					removeIndex(project, false, false);
					return false;
				}
				if (BpmnIndexUtils.indexFileExists(project.getName())) {
					if (!BpmnCorePlugin.getDefault().getBpmnModelManager()
							.isIndexCached(project)) {

						// the index exists but is not loaded, schedule a load
						// job
						BpmnCorePlugin.getDefault().getBpmnModelManager()
								.loadIndex(project);
						return false;
					}
				} 
				break;
			default:
				return true;
			}

			if (!BpmnCorePlugin.getDefault().getBpmnModelManager().isIndexCached(project) && 
					!BpmnCorePlugin.getDefault().getBpmnModelManager().createIsScheduled(project)) {
				
				BpmnCorePlugin.getDefault().getBpmnModelManager().createIndex(project, false, new NullProgressMonitor());
				EObject index = BpmnCorePlugin.getDefault().getBpmnModelManager().getBpmnIndex(project);
				if (!fAffectedProjects.containsKey(index)) {
					fAffectedProjects.put(project, new BpmnProjectDelta(index, IBpmnElementDelta.ADDED));
				}
				return false;
			}

			if (BpmnCorePlugin.getDefault().getBpmnModelManager().createIsScheduled(project)) {
				// create is scheduled, no need to process delta
				return false;
			}

		}
		
		return true;
	}

	public List<BpmnProjectDelta> getAffectedProjects() {
		List<BpmnProjectDelta> changedProjects = new ArrayList<BpmnProjectDelta>();
		changedProjects.addAll(fAffectedProjects.values());
		return changedProjects;
	}

}

package com.tibco.cep.bpmn.core.index.jobs;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.cep.bpmn.core.BpmnCoreConstants;
import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.core.index.IBpmnIndexUpdate;
import com.tibco.cep.bpmn.core.index.visitor.BpmnIndexUpdateVisitor;
import com.tibco.cep.bpmn.core.index.visitor.BpmnIndexVerificationVisitor;
import com.tibco.cep.bpmn.core.utils.ECoreHelper;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnIndexModificationAdapter;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.studio.core.index.jobs.IndexJob;
import com.tibco.cep.studio.core.index.jobs.IndexJobResult;

public class LoadBpmnIndexJob extends IndexJob<EObject>{
	private IProject fProject;
	
	public LoadBpmnIndexJob(IProject project) {
		super("Load BPMN Index:"+project.getName());
		this.fProject = project;
	}

	@Override
	public IStatus runJob(IProgressMonitor monitor) {
		BpmnCorePlugin.debug("Starting job:"+getName());
		
		if (!fProject.isOpen()) {
			return new IndexJobResult<EObject>(Status.INFO, 
					BpmnCorePlugin.PLUGIN_ID,
					"Failed to load bpmn index of "+fProject.getName()+ ": project is closed"
					,null,null);
		}
		
//		if (!BpmnProjectNatureManager.getInstance().isBpmnProject(fProject)) {
//			return new IndexJobResult<EObject>(Status.INFO, 
//					BpmnCorePlugin.PLUGIN_ID,
//					"Failed to load bpmn index of "+fProject.getName()+ ": project is not bpmn type"
//					,null,new Exception());
//		}
		
		
		String indexLocation = BpmnIndexUtils.getIndexLocation(fProject.getName());
		EList<EObject> contents = null;
		try {
			URI uri = URI.createFileURI(indexLocation);
			contents = ECoreHelper.deserializeModelXMI(fProject,uri, false);
		} catch (FileNotFoundException e) {
			IndexJobResult<EObject> result = new IndexJobResult<EObject>(Status.OK,
					BpmnCorePlugin.PLUGIN_ID,
					"Bpmn Index not found for project :"+fProject.getName(),
					null,null);
			return result;
		} catch (Exception e) {
			IndexJobResult<EObject> result = new IndexJobResult<EObject>(Status.ERROR,
					BpmnCorePlugin.PLUGIN_ID,
					"Failed to load project "+fProject.getName(),
					null,e);
			return result;
		}
		if( contents != null && contents.size() > 0) {
			
			EObject index = contents.get(0);
			EObjectWrapper<EClass, EObject> indexWrapper = EObjectWrapper.wrap(index);
			indexWrapper.adapt(new BpmnIndexModificationAdapter(index));
			setProperty(getKey(), indexWrapper.getEInstance());

//			ECoreHelper.loadExtensions(indexWrapper);
//			ECoreHelper.loadImports(indexWrapper, StudioCorePlugin.getCache(fProject.getName()));
			
			BpmnIndexVerificationVisitor verificationVisitor = new BpmnIndexVerificationVisitor(fProject.getName(),monitor);

			indexWrapper.accept(verificationVisitor);

			List<IResource> invalidResources = verificationVisitor.getInvalidResources();
			
			BpmnIndexUpdateVisitor updater = new BpmnIndexUpdateVisitor();
			
			HashMap<IFile,List<EObject>> duplicateElements = verificationVisitor.getDuplicateElements();
			if (duplicateElements.size() > 0) {
				Set<IFile> keySet = duplicateElements.keySet();
				for (IFile file : keySet) {
//					List<? extends EObject> list = 
					duplicateElements.get(file);
					//updater.removeDesignerElements(file, list, index);
					if (!invalidResources.contains(file)) {
						invalidResources.add(file);
					}
				}
			}
			
			if (invalidResources.size() > 0) {
				for (IResource invalidResource : invalidResources) {
					try {
						if (!(invalidResource instanceof IFile)) {
							continue;
						}
//						invalidResource.touch(new NullProgressMonitor()); // touch requires a scheduling rule, which we cannot guarantee.  Need to directly re-index file
						updater.reindexFile(IResourceDelta.CHANGED, (IFile) invalidResource, index, false);
					} catch (Exception e) {
						BpmnCorePlugin.log(e);
					}
				}
			}
			
			try {
				IExtensionRegistry reg = Platform.getExtensionRegistry();
				IConfigurationElement[] extensions = reg
						.getConfigurationElementsFor(BpmnCoreConstants.EXTENSION_POINT_INDEX_UPDATE);
				for (int i = 0; i < extensions.length; i++) {
					IConfigurationElement element = extensions[i];
					final Object o = element.createExecutableExtension(BpmnCoreConstants.EXTENSION_POINT_ATTR_INDEX);
					if (o instanceof IBpmnIndexUpdate) {
						((IBpmnIndexUpdate) o).onIndexUpdate(fProject, index);
					}
				}	
			}catch (Exception e) {
				BpmnCorePlugin.log(e);
			}
			
			ECoreHelper.loadExtensions(indexWrapper);
//			ECoreHelper.loadImports(indexWrapper, BpmnCorePlugin.getCache(fProject.getName()));
			EcoreUtil.resolveAll(index);
			IndexJobResult<EObject> result = new IndexJobResult<EObject>(Status.OK,
					BpmnCorePlugin.PLUGIN_ID,
					"Load successful.",
					contents.get(0),null);
			
			BpmnCorePlugin.debug("Completing job:"+getName());
			return result;
		}
		
		IndexJobResult<EObject> result = new IndexJobResult<EObject>(Status.INFO,
				BpmnCorePlugin.PLUGIN_ID,
				"Failed to load index for project :"+fProject.getName(),
				null,new Exception());
		return result;

	}
	

	
	@Override
	public boolean belongsTo(Object family) {
		return fProject.equals(family);
	}

	public IProject getProject() {
		return fProject;
	}

	

}

package com.tibco.cep.bpmn.core.index.jobs;

import java.util.Date;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.index.visitor.BpmnIndexCreationResourceVisitor;
import com.tibco.cep.bpmn.core.utils.ECoreHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.jobs.IndexJob;
import com.tibco.cep.studio.core.index.jobs.IndexJobResult;
import com.tibco.cep.studio.core.repo.EMFTnsCache;


public class CreateBpmnIndexJob extends IndexJob<EObject>{
	
	IProject fProject;
	boolean fPersistResult = true;
	
	/**
	 * @param project
	 */
	public CreateBpmnIndexJob(IProject project) {
		this(project,true);
	}
	/**
	 * @param project
	 * @param persistResult
	 */
	public CreateBpmnIndexJob(IProject project,boolean persistResult) {
		super("Create New BPMN Index for project "+project.getName());
		this.fProject = project;
		this.fPersistResult = persistResult;
	}

	@Override
	public IStatus runJob(IProgressMonitor monitor) {
		BpmnCorePlugin.debug("Starting job:"+getName());
		
		try {
			
			if (!fProject.isOpen()) {
				return new IndexJobResult<EObject>(Status.INFO, 
											  BpmnCorePlugin.PLUGIN_ID,
						"Failed to create project "+fProject.getName()+ ": project is closed"
						,null,null);
			}
			
			
//			if (!BpmnProjectNatureManager.getInstance().isBpmnProject(fProject)) {
//				return new IndexJobResult<EObject>(Status.INFO, 
//						BpmnCorePlugin.PLUGIN_ID,
//						"Failed to create bpmn index of "+fProject.getName()+ ": project is not bpmn type"
//						,null,new Exception());
//			}
			
			
			EObjectWrapper<EClass, EObject> indexWrapper = EObjectWrapper.createInstance(BpmnModelClass.DEFINITIONS);
			setProperty(getKey(), indexWrapper.getEInstance());
			indexWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, fProject.getName());
			indexWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_CREATION_DATE,new Date());
//			ECoreHelper.loadExtensions(indexWrapper);
//			ECoreHelper.loadImports(indexWrapper, StudioCorePlugin.getCache(fProject.getName()));
			BpmnIndexCreationResourceVisitor visitor = new BpmnIndexCreationResourceVisitor(indexWrapper,fProject,false);
			if (!fProject.isOpen()) {
				return Status.OK_STATUS;
			}
			fProject.accept(visitor);	
//			ECoreHelper.addAllProcessItemDefinition(indexWrapper);
			ResourceSet rset = BpmnCorePlugin.getDefault().getBpmnModelManager().getModelResourceSet(fProject);
			BpmnCorePlugin.getDefault().getBpmnModelManager().resolveAll(rset);
			
			
			
			ECoreHelper.loadExtensions(indexWrapper);
			EcoreUtil.resolveAll(indexWrapper.getEInstance());
			
			EMFTnsCache cache = StudioCorePlugin.getCache(getProject().getName());
			ECoreHelper.loadImports(indexWrapper, cache, getProject().getName());// this call insures, all itemdef loaded in to index
			
			if (fPersistResult) {
				IStatus status = BpmnCorePlugin.getDefault().getBpmnModelManager().saveIndex(indexWrapper.getEInstance(), true);
				if (!status.isOK()) {
					return status;
				}
			}
			
			IndexJobResult<EObject> result = new IndexJobResult<EObject>(Status.OK,
					BpmnCorePlugin.PLUGIN_ID,
					"Create successful.",
					indexWrapper.getEInstance(),null);
			
			BpmnCorePlugin.debug("Completing job:"+getName());
			return result;
		} catch (Exception e) {
			IndexJobResult<EObject> result = new IndexJobResult<EObject>(Status.ERROR,
					BpmnCorePlugin.PLUGIN_ID,
					"Create index failed for project:"+fProject.getName(),
					null,e);
			BpmnCorePlugin.log(e);
			return result;
		}
	}
	
	
	
	@Override
	public boolean belongsTo(Object family) {
		return fProject.equals(family);
	}

	public IProject getProject() {
		return fProject;
	}

}

package com.tibco.cep.bpmn.core.index.jobs;

import java.util.Date;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.core.utils.ECoreHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.studio.core.index.jobs.IndexJob;
import com.tibco.cep.studio.core.index.jobs.IndexJobResult;

public class PersistBpmnIndexJob extends IndexJob<EObject>{
	private EObject fIndex;
	
	public PersistBpmnIndexJob(EObject index) {
		super("Saving BPMN Index -> "+ EObjectWrapper.wrap(index).getAttribute(BpmnMetaModelConstants.E_ATTR_NAME));
		this.fIndex = index;
	}
	
	@Override
	public boolean belongsTo(Object family) {
		return family.equals(fIndex);
	}
	

	@Override
	public IStatus runJob(IProgressMonitor monitor) {
		BpmnCorePlugin.debug("Starting job:"+getName());
		
		try {
			synchronized (fIndex) {
				EObjectWrapper<EClass, EObject> indexWrapper = EObjectWrapper.wrap(fIndex);
				String indexName = (String) indexWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
				String indexLocation = BpmnIndexUtils.getIndexLocation(indexName);
				IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(indexName);
				URI uri = URI.createFileURI(indexLocation);				
				// saving the index causes a traversal/iteration over the index contents, 
				// so we need to synchronize on it to avoid concurrent modification exceptions
				indexWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_LAST_PERSISTED, new Date());
				ECoreHelper.serializeModelXMI(project,uri,fIndex);
				
				IndexJobResult<EObject> result = new IndexJobResult<EObject>(Status.OK,
						BpmnCorePlugin.PLUGIN_ID,
						"Save successful.",
						fIndex,null);
				
				BpmnCorePlugin.debug("Completing job:"+getName());
				return result;
			}
		} catch (Exception e) {
			IndexJobResult<EObject> result = new IndexJobResult<EObject>(Status.ERROR,
					BpmnCorePlugin.PLUGIN_ID,
					"Save failed.",
					null,e);
			return result;
		}
	}

}

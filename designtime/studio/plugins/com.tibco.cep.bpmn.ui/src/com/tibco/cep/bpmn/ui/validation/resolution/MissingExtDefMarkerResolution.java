package com.tibco.cep.bpmn.ui.validation.resolution;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMarkerResolution2;

import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.core.utils.ECoreHelper;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelChangeAdapterFactory;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelChangeEvent;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelChangeListener;
import com.tibco.cep.bpmn.ui.validation.ValidationURIHandler;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

public class MissingExtDefMarkerResolution implements IMarkerResolution2, ModelChangeListener {

	private boolean changed;


	@Override
	public String getDescription() {
		return BpmnMessages.getString("missingExtDefMarkerRes_description_label");
	}

	@Override
	public Image getImage() {
		return BpmnUIPlugin.getDefault().getImage("icons/appicon16x16.gif");
	}

	@Override
	public String getLabel() {
		return Messages.getString("process.validate.quickfix.missing.extdata");
	}

	@Override
	public void run(IMarker marker) {
		final IResource resource = marker.getResource();
		if (!StudioUIUtils.saveDirtyEditor(resource)) {
			return;
		}
		@SuppressWarnings("unused")
		final EObject element = BpmnIndexUtils.getElement(resource);
		try {
			final ResourceSet rset = ECoreHelper.createModelResourceSet(resource.getProject());
			
			final ValidationURIHandler uriHandler = new ValidationURIHandler(resource,BpmnIndexUtils.getIndexLocationMap());
			
			EList<EObject> eObjList = ECoreHelper.deserializeModelXMI(rset,resource, false, uriHandler);
			if(eObjList.isEmpty())
				return;
			final EObject modelObj = eObjList.get(0);
			try {
				EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper.wrap(modelObj);
				ModelChangeAdapterFactory adapterFactory = new ModelChangeAdapterFactory(this);
				adapterFactory.adapt(modelObj, ModelChangeListener.class);
				BpmnProcessRefreshVisitor visitor = new BpmnProcessRefreshVisitor(true);
				wrapper.accept(visitor);
				final URI uri = URI.createPlatformResourceURI(
						resource.getFullPath().toPortableString(), false);
				final Map<Object,Object> options = new HashMap<Object,Object>();
				options.put(XMIResource.OPTION_URI_HANDLER, uriHandler);			
//				options.put(XMIResource.OPTION_SKIP_ESCAPE, true);
//				options.put(XMIResource.OPTION_SKIP_ESCAPE_URI, true);
				ECoreHelper.serializeModelXMI(rset, uri, modelObj, options);
				
				
				if (this.changed) {
					CommonUtil.refresh(resource, 1, false);
				}
				try {
					resource.getProject().build(IncrementalProjectBuilder.INCREMENTAL_BUILD, new NullProgressMonitor());
				} catch (CoreException e) {
					BpmnUIPlugin.log(e);
				}
			} catch (IOException e) {
				BpmnUIPlugin.log(e);
			}
			
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
		

	}
	
	
	@Override
	public void modelChanged(ModelChangeEvent mce) {
		this.changed = true;		
	}
	

}

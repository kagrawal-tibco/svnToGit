package com.tibco.cep.bpmn.ui.validation.resolution;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMarkerResolution2;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.core.utils.ECoreHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.ontology.impl.DefaultBpmnIndex;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.validation.ValidationURIHandler;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 * 
 * @author majha
 *
 */
public class MissingResourceMarkerResolution implements IMarkerResolution2 {

	@Override
	public String getDescription() {
		return BpmnMessages.getString("missingResourceMarkerRes_description_label");
	}

	@Override
	public Image getImage() {
		return BpmnUIPlugin.getDefault().getImage("icons/appicon16x16.gif");
	}

	@Override
	public String getLabel() {
		return Messages.getString("resource.validation.quickfix.label");
	}

	@Override
	public void run(IMarker marker) {
		final IResource resource = marker.getResource();
		if (!StudioUIUtils.saveDirtyEditor(resource)) {
			return;
		}
		try {
			final ResourceSet rset = ECoreHelper.createModelResourceSet(resource.getProject());
			
			final ValidationURIHandler uriHandler = new ValidationURIHandler(resource,BpmnIndexUtils.getIndexLocationMap());
			
			EList<EObject> eObjList = ECoreHelper.deserializeModelXMI(resource, true);
			if(eObjList.isEmpty())
				return;
			final EObject modelObj = eObjList.get(0);
			String projectName = resource.getProject().getName();
			List<EObject> allFlowNodes = BpmnModelUtils.getAllFlowNodes(modelObj);
			allFlowNodes.add(modelObj);
			boolean changed = false;
			for (EObject flowNode : allFlowNodes) {
				Object resReferenced = BpmnModelUtils.getAttachedResource(flowNode);
				if (resReferenced != null) {
					if (resReferenced instanceof EObject) {
						EObjectWrapper<EClass, EObject> resReferencedWrapper = EObjectWrapper.wrap((EObject)resReferenced);
						String refProject =resReferencedWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT);
						IFile file = BpmnIndexUtils.getFile(projectName,
								(EObject) resReferenced);
						if (refProject != projectName) {
							if (file != null && file.exists()) {
								EObjectWrapper<EClass, EObject> refProcess = getProcessInProjectHavingSameLocation(resource.getProject(), (EObject)resReferenced);
								BpmnModelUtils.setResourceAttr(flowNode, refProcess.getEInstance());
								changed = true;
							}
						}
						if (!file.exists()) {
							BpmnModelUtils.setResourceAttr(flowNode, null);
							changed = true;
						}

					} else if (resReferenced instanceof String && !((String)resReferenced).isEmpty()){
						DesignerElement desElement = IndexUtils.getElement(
								projectName, (String) resReferenced);
						if (desElement == null){
							BpmnModelUtils.setResourceAttr(flowNode, null);
							changed = true;
						}
					}else if (resReferenced instanceof Collection<?>
							&& !((Collection<?>) resReferenced).isEmpty()) {
						@SuppressWarnings("unchecked")
						Collection<String> rules = (Collection<String>) resReferenced;
						List<String> toBeRemoved = new ArrayList<String>();
						for (String ruleName : rules) {
							// find corresponding object (Rule)
							DesignerElement re = IndexUtils.getElement(
									projectName, (String) ruleName);
							if (re == null)
								toBeRemoved.add(ruleName);
						}
						if (!toBeRemoved.isEmpty()){
							rules.removeAll(toBeRemoved);
							changed = true;
						}
					}
				}

			}
			if(changed) {
				WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
					@Override
					protected void execute(IProgressMonitor monitor)
							throws CoreException, InvocationTargetException,
							InterruptedException {
						try {
							final URI uri = URI.createPlatformResourceURI(
									resource.getFullPath().toPortableString(), false);
							final Map<Object,Object> options = new HashMap<Object,Object>();
							options.put(XMIResource.OPTION_URI_HANDLER, uriHandler);			
//							options.put(XMIResource.OPTION_SKIP_ESCAPE, true);
//							options.put(XMIResource.OPTION_SKIP_ESCAPE_URI, true);
							ECoreHelper.serializeModelXMI(rset, uri, modelObj, options);
							
							if (uriHandler.isChanged()) {
								
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
						
					}
				};
				op.run(new NullProgressMonitor());
			}
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
	}
	
	private EObjectWrapper<EClass, EObject> getProcessInProjectHavingSameLocation(
			IProject project, EObject objRef) {
		String resourcePath = getProjectRelativePath(EObjectWrapper.wrap(objRef));
		EObject index = BpmnIndexUtils.getIndex(project);
		DefaultBpmnIndex defaultBpmnOntology = new DefaultBpmnIndex(index);
		Collection<EObject> procs = defaultBpmnOntology.getAllProcesses();
		for (EObject proc : procs) {
			EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper
					.wrap(proc);
			
			String fullpath = getProjectRelativePath(processWrapper);
			
			if (fullpath.equals(resourcePath)) {
				return processWrapper;
			}
		}
		return null;
	}
	
	private String getProjectRelativePath(EObjectWrapper<EClass, EObject> wrapper) {
		String folder = wrapper
				.getAttribute(BpmnMetaModelConstants.E_ATTR_FOLDER);
		String name = wrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		String fullpath = null;

		if (folder.endsWith(Character.toString(IPath.SEPARATOR)))
			fullpath = folder + name;
		else
			fullpath = folder + IPath.SEPARATOR + name;
		
		return fullpath;
	}

}

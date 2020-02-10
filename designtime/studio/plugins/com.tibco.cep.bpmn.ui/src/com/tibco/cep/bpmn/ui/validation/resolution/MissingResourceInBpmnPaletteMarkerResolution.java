package com.tibco.cep.bpmn.ui.validation.resolution;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IMarkerResolution2;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.bpmn.common.palette.model.BpmnCommonPaletteGroupEmfItemType;
import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnEditorInput;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.editors.bpmnPalette.BpmnPaletteConfigurationEditor;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroup;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroupItem;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteModel;
import com.tibco.cep.bpmn.ui.utils.BpmnPaletteResourceUtil;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 * 
 * @author majha
 *
 */
public class MissingResourceInBpmnPaletteMarkerResolution implements IMarkerResolution2 {

	@Override
	public String getDescription() {
		return BpmnMessages.getString("missingResourceInBpmnPaletteMarkerRes_description_label");
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
		boolean changed = false;
		String projectName = resource.getProject().getName();
		BpmnPaletteModel bpmnPalette = getBpmnPaletteFromEditorIfOpened(resource);
		if(bpmnPalette == null)
			bpmnPalette =  loadBpmnPalette(resource);
		List<BpmnPaletteGroup> paletteGroups = bpmnPalette.getBpmnPaletteGroups();
		StringBuilder builder = new StringBuilder();
		for (BpmnPaletteGroup bpmnPaletteGroup : paletteGroups) {
			List<BpmnPaletteGroupItem> paletteItems = bpmnPaletteGroup.getPaletteItems();
			for (BpmnPaletteGroupItem bpmnPaletteGroupItem : paletteItems) {
				String attachedResource = bpmnPaletteGroupItem
						.getAttachedResource();
				String[] split = attachedResource.split(",");
				for (String string : split) {
					if (!string.isEmpty()) {
						BpmnCommonPaletteGroupEmfItemType itemType = (BpmnCommonPaletteGroupEmfItemType) bpmnPaletteGroupItem
								.getItemType();
						EClass modelType = BpmnMetaModel.getInstance().getEClass(
								itemType.getEmfType());
						if (modelType.equals(BpmnModelClass.CALL_ACTIVITY)) {
							IFile file = BpmnIndexUtils
									.getFile(projectName, string, BpmnCommonIndexUtils.BPMN_PROCESS_EXTENSION);
							if (file.exists()) {
								builder.append(string + ",");
							}else
								changed = true;
						} else {
							DesignerElement element = IndexUtils.getElement(
									projectName, string);
							if (element != null)
								builder.append( string + ",");
							else
								changed = true;
						}
					}

				}
				String attachedRes = builder.toString();
				if (attachedRes.endsWith(","))
					attachedRes = attachedRes.substring(0,
							attachedRes.length() - 1);
				bpmnPaletteGroupItem.setAttachedResource(attachedRes);
			}
		}

		if(changed)
			saveModel((IFile)resource, bpmnPalette);
			
	}
	
	private BpmnPaletteModel loadBpmnPalette(IResource paletteResource) {
		BpmnPaletteModel root = null;
		try {
			root = BpmnPaletteResourceUtil.loadBpmnPalette(paletteResource, null);
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
		return root;
	}
	
	protected void saveModel(IFile resource , BpmnPaletteModel bpmnPalette) {
		IEditorPart openEditor = getOpenEditor(resource);
		if(openEditor != null && openEditor instanceof BpmnPaletteConfigurationEditor){
			openEditor.doSave(new NullProgressMonitor());
		}else{
			try {
				BpmnPaletteResourceUtil.saveBpmnPalette(bpmnPalette, resource);
			} catch (Exception e) {
				BpmnUIPlugin.log(e);
			}
		}

	}	
	
	private BpmnPaletteModel getBpmnPaletteFromEditorIfOpened(IResource resourse){
		IEditorPart openEditor = getOpenEditor(resourse);
		BpmnPaletteModel bpmnPaletteModel = null;
		if(openEditor != null && openEditor instanceof BpmnPaletteConfigurationEditor){
			BpmnPaletteConfigurationEditor editor = (BpmnPaletteConfigurationEditor)openEditor;
			bpmnPaletteModel = editor.getBpmnPaletteModel();
		}
		return bpmnPaletteModel;
	}
	
	private IEditorPart getOpenEditor(IResource resource){
		IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart openPart = null;
		IEditorReference[] editorReferences = activePage.getEditorReferences();
		for (IEditorReference iEditorReference : editorReferences) {
			IEditorPart editorPart = iEditorReference.getEditor(false);
			if (editorPart != null && editorPart.getEditorInput() instanceof BpmnEditorInput) {
				BpmnEditorInput fei = (BpmnEditorInput) editorPart.getEditorInput();
				if (resource.equals(fei.getFile())) {
					openPart = editorPart;
					break;
				}
			}
		}
		return openPart;
	}

}

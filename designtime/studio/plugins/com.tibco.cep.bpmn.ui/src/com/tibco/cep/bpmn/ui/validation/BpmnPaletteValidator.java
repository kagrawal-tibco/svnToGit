package com.tibco.cep.bpmn.ui.validation;


import java.io.File;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.bpmn.common.palette.model.BpmnCommonPaletteGroupEmfItemType;
import com.tibco.cep.bpmn.core.BpmnCoreConstants;
import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroup;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroupItem;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteModel;
import com.tibco.cep.bpmn.ui.utils.BpmnPaletteResourceUtil;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.validation.DefaultResourceValidator;
import com.tibco.cep.studio.core.validation.IResourceValidator;
import com.tibco.cep.studio.core.validation.ValidationContext;

/**
 * 
 * @author majha
 * 
 */
public class BpmnPaletteValidator extends DefaultResourceValidator implements
		IResourceValidator {
	public static final String MISSING_RESOURCE_MARKER_TYPE = BpmnUIPlugin.PLUGIN_ID
			+ ".missingResourceInBpmnPaletteProblem";

	public BpmnPaletteValidator() {

	}

	@Override
	public boolean canContinue() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean enablesFor(IResource resource) {
		return BpmnCoreConstants.PALETTE_FILE_EXTN.equalsIgnoreCase(resource
				.getFileExtension());
	}

	@Override
	public boolean validate(ValidationContext validationContext) {
		IResource resource = validationContext.getResource();
		if (resource == null)
			return true;

		super.validate(validationContext);
		try {
			BpmnPaletteModel loadBpmnPalette = loadBpmnPalette(resource);
			if (loadBpmnPalette != null) {
				validatePalette(resource, loadBpmnPalette);
			}

		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}

		return true;
	}

	private void validatePalette(IResource resource,
			BpmnPaletteModel bpmnPalette) throws Exception {
		String projectName = resource.getProject().getName();
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
							if (!file.exists() && !builder.toString().contains(string)) {
								builder.append("\"" + string + "\", ");
							}
						}else if (modelType.equals(BpmnModelClass.SERVICE_TASK)) {
							boolean valid = false;
							try {
								IFile file = resource.getProject().getFile(string);
								String pathWsdl = file.getLocation().toPortableString()+".wsdl"; 
								File file2 = new File(pathWsdl);
								valid = file2.exists();
							} catch (Exception e) {
								valid = false;
							}
							if (!valid && !builder.toString().contains(string)) {
								builder.append("\"" + string + "\", ");
							}
						}  else {
							DesignerElement element = IndexUtils.getElement(
									projectName, string);
							if (element == null && !builder.toString().contains(string))
								builder.append("\"" + string + "\", ");
						}
					}

				}
			}
		}


		String faultNodeNames = builder.toString().trim();
		if (faultNodeNames.endsWith(","))
			faultNodeNames = faultNodeNames.substring(0,
					faultNodeNames.length() - 1);

		if (!faultNodeNames.isEmpty()){
			String portableString = resource.getFullPath().makeAbsolute().toPortableString();
			reportProblem(resource,
					Messages.getString(
							"palette.validate.referenced.entity.error",
							faultNodeNames, portableString), portableString,
					IMarker.SEVERITY_ERROR, MISSING_RESOURCE_MARKER_TYPE);
		}

		// TODO: further model validation can be done here
	}

	protected void deleteMarkers(IResource file) {
		super.deleteMarkers(file);
		try {
			file.deleteMarkers(MISSING_RESOURCE_MARKER_TYPE, false,
					IResource.DEPTH_ZERO);
		} catch (CoreException ce) {
		}
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

}

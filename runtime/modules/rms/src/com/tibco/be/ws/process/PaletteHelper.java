/**
 * 
 */
package com.tibco.be.ws.process;

import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_EMFTYPE;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_EMF_EXTENDED_TYPE;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_ICON;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_ID;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_TITLE;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_TOOLTIP;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_VISIBLE;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.bpmn.common.palette.model.BpmnCommonPaletteModel;
import com.tibco.cep.bpmn.model.designtime.utils.BPMNCommonImages;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonPaletteResourceUtil;
import com.tibco.cep.studio.common.palette.EmfType;
import com.tibco.cep.studio.common.palette.PaletteGroup;
import com.tibco.cep.studio.common.palette.PaletteItem;
import com.tibco.cep.studio.common.palette.PaletteModel;

/**
 * API's to interact with Process Palette
 * 
 * @author Vikram Patil
 */
public class PaletteHelper {
	public static final String ICON_PREFIX = "com.tibco.cep.bpmn.ui.utils.images.";
	private static BPMNCommonImages bpmnImages = new BPMNCommonImages();
	
	/**
	 * Get the Palette Model
	 * 
	 * @return
	 */
	public static Object getPaletteModel() {
		PaletteModel paletteModel = BpmnCommonPaletteResourceUtil.loadDefaultProcesspalette();
		BpmnCommonPaletteModel bpmnPaletteModel = new BpmnCommonPaletteModel(paletteModel);
		return bpmnPaletteModel;
	}
	
	/**
	 * Get Palette Groups
	 * 
	 * @param bpmnPaletteModel
	 * @return
	 */
	public static Object[] getPaletteGroups(BpmnCommonPaletteModel bpmnPaletteModel) {
		EList<PaletteGroup> paletteToolSet = bpmnPaletteModel.getModel().getPaletteToolSet();
		return paletteToolSet.toArray();
	}
	
	/**
	 * Get Palette Group details
	 * 
	 * @param paletteGroup
	 * @return
	 */
	public static Map<String, String> getGroupDetails(PaletteGroup paletteGroup) {
		Map<String, String> groupDetails = null;

		groupDetails = new HashMap<String, String>();
		groupDetails.put(PROP_ID, paletteGroup.getId());
		groupDetails.put(PROP_TITLE, paletteGroup.getTitle());
		groupDetails.put(PROP_TOOLTIP, paletteGroup.getTooltip());
		groupDetails.put(PROP_VISIBLE, new Boolean(paletteGroup.isVisible()).toString());
		
		String iconPath = bpmnImages.getImagePath(paletteGroup.getIcon());
		groupDetails.put(PROP_ICON, iconPath.substring(iconPath.lastIndexOf("/")+1));

		return groupDetails;
	}
	
	/**
	 * Get Palette Items
	 * 
	 * @param paletteGroup
	 * @return
	 */
	public static Object[] getPaletteItems(PaletteGroup paletteGroup) {
		EList<PaletteItem> paletteItems = paletteGroup.getPaletteItem();
		return paletteItems.toArray();
	}
	
	/**
	 * Get Palette Item details
	 * 
	 * @param paletteItem
	 * @return
	 */
	public static Map<String, String> getItemDetails(PaletteItem paletteItem) {
		Map<String, String> itemDetails = null;
			
		itemDetails = new HashMap<String, String>();
		itemDetails.put(PROP_ID, paletteItem.getId());
		itemDetails.put(PROP_TITLE, paletteItem.getTitle());
		itemDetails.put(PROP_TOOLTIP, paletteItem.getTooltip());		
		itemDetails.put(PROP_VISIBLE, new Boolean(paletteItem.isVisible()).toString());
		
		String iconPath = bpmnImages.getImagePath(paletteItem.getIcon());
		itemDetails.put(PROP_ICON, iconPath.substring(iconPath.lastIndexOf("/")+1));
		
		EmfType emfType = paletteItem.getEmfItemType();
		if (emfType != null) {
			itemDetails.put(PROP_EMFTYPE, emfType.getEmfType());
			
			String extendedType = emfType.getExtendedType();
			if (extendedType != null && !extendedType.isEmpty()) {
				itemDetails.put(PROP_EMF_EXTENDED_TYPE, emfType.getExtendedType());
			}
		}
		
		return itemDetails;
	}
	
	public static void main(String[] args) throws IOException {
		Object paletteModel = getPaletteModel();
		
		Object[] paletteGroups = getPaletteGroups((BpmnCommonPaletteModel)paletteModel);
		for (Object pgroup : paletteGroups) {
			System.out.println(getGroupDetails((PaletteGroup)pgroup));
			Object[] paletteItems = getPaletteItems((PaletteGroup)pgroup);
			for (Object pItem : paletteItems) {
				System.out.println(getItemDetails((PaletteItem)pItem));
			}
		}
	}

}

package com.tibco.cep.bpmn.ui.graph.palette.model;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.bpmn.common.palette.model.BpmnCommonPaletteIdGenerator;



/**
 * 
 * @author majha
 *
 */
		
public class BpmnPaletteIdGenerator extends BpmnCommonPaletteIdGenerator{
//		private static final String PALETTE_GROUP_PREFIX="paletteGroup";
//		private static final String PALETTE_ITEM_PREFIX="paletteItem";
	

	public static String getNextIdForBpmnPaletteGroup(BpmnPaletteModel model) {
		String idPrefix = model.getName()+"."+PALETTE_GROUP_PREFIX+".";
		List<BpmnPaletteGroup> bpmnPaletteGroups = model.getBpmnPaletteGroups();
		int numMax = bpmnPaletteGroups.size();
		
		for (int i = numMax; ; i++) {
			String id = idPrefix+ i;
			if(model.getBpmnPaletteGroup(id)== null)
				return id;
		}

	}

	public static String getNextIdForBpmnPaletteItem(BpmnPaletteModel model) {
		String idPrefix = model.getName()+"."+PALETTE_ITEM_PREFIX+".";
		
		List<BpmnPaletteGroupItem> itemList = new ArrayList<BpmnPaletteGroupItem>();
		
		List<BpmnPaletteGroup> bpmnPaletteGroups = model.getBpmnPaletteGroups();
		for (BpmnPaletteGroup bpmnPaletteGroup : bpmnPaletteGroups) {
			itemList.addAll(bpmnPaletteGroup.getPaletteItems());
		}
		int numMax = itemList.size();
		
		for (int i = numMax; ; i++) {
			String id = idPrefix+ i;
			if(model.getBpmnPaletteGroup(id)== null)
				return id;
		}

	}
	

}

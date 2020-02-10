package com.tibco.cep.bpmn.common.palette.model;




/**
 * 
 * @author majha
 *
 */
		
public class BpmnCommonPaletteIdGenerator {
		protected static final String PALETTE_GROUP_PREFIX="paletteGroup";
		protected static final String PALETTE_ITEM_PREFIX="paletteItem";
	

//	public static String getNextIdForBpmnPaletteGroup(BpmnCommonPaletteModel model) {
//		String idPrefix = model.getName()+"."+PALETTE_GROUP_PREFIX+".";
//		List<BpmnPaletteGroup> bpmnPaletteGroups = model.getBpmnPaletteGroups();
//		int numMax = bpmnPaletteGroups.size();
//		
//		for (int i = numMax; ; i++) {
//			String id = idPrefix+ i;
//			if(model.getBpmnPaletteGroup(id)== null)
//				return id;
//		}
//		return null;
//
//	}

//	public static String getNextIdForBpmnPaletteItem(BpmnCommonPaletteModel model) {
//		String idPrefix = model.getName()+"."+PALETTE_ITEM_PREFIX+".";
//		
//		List<BpmnCommonPaletteGroupItem> itemList = new ArrayList<BpmnCommonPaletteGroupItem>();
//		
//		List<BpmnPaletteGroup> bpmnPaletteGroups = model.getBpmnPaletteGroups();
//		for (BpmnPaletteGroup bpmnPaletteGroup : bpmnPaletteGroups) {
//			itemList.addAll(bpmnPaletteGroup.getPaletteItems());
//		}
//		int numMax = itemList.size();
//		
//		for (int i = numMax; ; i++) {
//			String id = idPrefix+ i;
//			if(model.getBpmnPaletteGroup(id)== null)
//				ret
//		return null;
//
//	}
//	

}

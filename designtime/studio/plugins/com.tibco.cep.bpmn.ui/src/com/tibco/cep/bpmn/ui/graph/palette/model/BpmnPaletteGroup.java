package com.tibco.cep.bpmn.ui.graph.palette.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.swt.graphics.RGB;

import com.tibco.cep.studio.common.palette.PaletteGroup;
import com.tibco.cep.studio.common.palette.PaletteItem;

/**
 * 
 * @author majha
 *
 */
public class BpmnPaletteGroup extends BpmnPaletteGroupTypeInfo {
	private Map<String, BpmnPaletteGroupItem> itemsMap = new LinkedHashMap<String, BpmnPaletteGroupItem>();
	private List<BpmnPaletteGroupItem> itemsList = new ArrayList<BpmnPaletteGroupItem>();
	private BpmnPaletteModel parent;
	
	public BpmnPaletteGroup(BpmnPaletteModel model, PaletteGroup  group) {
		super(group);
		this.parent = model;
		initializeBpmnPaletteItems();
	}
	
	private void initializeBpmnPaletteItems(){
		EList<PaletteItem> paletteItems = group.getPaletteItem();
		for (PaletteItem paletteItem : paletteItems) {
			BpmnPaletteGroupItem bpmnPaletteGroupItem = new BpmnPaletteGroupItem(this, paletteItem);
			itemsMap.put(paletteItem.getId(), bpmnPaletteGroupItem);
			itemsList.add(bpmnPaletteGroupItem);
		}
	}

	public BpmnPaletteModel getParent() {
		return parent;
	}
	

	public void setParent(BpmnPaletteModel parent) {
		this.parent = parent;
	}
	

	public List<BpmnPaletteGroupItem> getPaletteItems(){
		return itemsList;
	}
	
	public List<BpmnPaletteGroupItem> getPaletteItems(boolean includeInvisible) {
		List<BpmnPaletteGroupItem> arrayList =null;
		if(includeInvisible)
			arrayList = itemsList;
		else{
			arrayList = new ArrayList<BpmnPaletteGroupItem>();
			for (BpmnPaletteGroupItem bpmnPaletteGroup : itemsList) {
				if(bpmnPaletteGroup.isVisible())
					arrayList.add(bpmnPaletteGroup);
			}
		}
		
		return arrayList;
	}
	
	public BpmnPaletteGroupItem getPaletteItem(String id){
		return itemsMap.get(id);
	}
	
	
	public void moveUp(BpmnPaletteGroupItem item){
		EList<PaletteItem> paletteItems = group.getPaletteItem();
		int index = paletteItems.indexOf(item.getItem()); 
		if(index != 0){
			paletteItems.move(index-1, index);
			Collections.swap(itemsList,index-1, index);
		}
	}
	
	public void moveDown(BpmnPaletteGroupItem item){
		EList<PaletteItem> paletteItems = group.getPaletteItem();
		int index = paletteItems.indexOf(item.getItem()); 
		if(index != (paletteItems.size() -1)){
			paletteItems.move(index+1, index);
			Collections.swap(itemsList,index, index+1);
		}
	}
		
	public void addPaletteItem(BpmnPaletteGroupItem item) {
		String itemID = item.getId();
		itemsMap.put(itemID, item);
		group.getPaletteItem().add(item.getItem());
		itemsList.add(item);
	}
	
	public void removePaletteItem(String itemID) {
		BpmnPaletteGroupItem item = itemsMap.remove(itemID);
		itemsList.remove(item);
		if (item != null){
			group.getPaletteItem().remove(item.getItem());
		}
	}
	
	public String getId() {
		return group.getId();
	}

	public void setId(String id) {
		if(group.getId() == null || !(group.getId().equalsIgnoreCase(id))){
			group.setId(id);
		}
	}
	public static RGB hex2Rgb(String colorStr) { 
		int ri = colorStr.indexOf(",");
		int gi = colorStr.indexOf(",",ri+1);
		
		String r = colorStr.substring( 1,ri);
		String b = colorStr.substring(ri+1,gi);
		String g = colorStr.substring(gi+1,	colorStr.indexOf(")",gi+1));
		    return new RGB( 
		            Integer.valueOf( r ), 
		            Integer.valueOf( g ), 
		            Integer.valueOf( b )); 
		}  
	
	public RGB getColor() {
		if(group.getColor() == null){
			group.setColor("(255,81,220)");
		}
		RGB color = hex2Rgb(group.getColor());
		 return color;
	}

	public void setColor(RGB color) {
		
	//	if(!item.getColor().equals(color.toString())){
		String col = "("+color.red+","+color.blue+","+color.green+")";
		group.setColor(col);
		//}
	}
	
	
}
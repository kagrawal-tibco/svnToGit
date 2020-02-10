package com.tibco.cep.bpmn.common.palette.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.cep.studio.common.palette.Help;
import com.tibco.cep.studio.common.palette.PaletteItem;

/**
 * 
 * @author majha
 *
 */
public  class BpmnCommonPaletteGroupItem extends BpmnCommonPaletteItemTypeInfo {
	BpmnCommonPaletteGroupItemType itemType;
	private BpmnCommonPaletteGroup parent;
//	private PaletteEntry entry;

	public BpmnCommonPaletteGroupItem(BpmnCommonPaletteGroup parent, PaletteItem item) {
		super(item);
		this.parent=parent;
		initializeItemType();
	}
	
	private void initializeItemType(){
		if(item.getEmfItemType() != null){
			itemType = new BpmnCommonPaletteGroupEmfItemType(item.getEmfItemType());
		}else if(item.getJavaItemType() != null){
			itemType = new BpmnCommonPaletteGroupJavaItemType(item.getJavaItemType());
		}else if(item.getModelItemType() != null){
			itemType = new BpmnCommonPaletteGroupModelItemType(item.getModelItemType());
		}
	}

	public BpmnCommonPaletteGroupItemType getItemType() {
		return itemType;
	}


	public String getId() {
		return item.getId();
	}

	public void setId(String id) {
		if(this.item.getId() == null || !(this.item.getId().equalsIgnoreCase(id))){
			item.setId(id);
		}
	}
	
	public String getAttachedResource() {
		return item.getAttachedResource();
	}

	public void setAttachedResource(String attachedResource) {
		if(!item.getAttachedResource().equalsIgnoreCase(attachedResource)){
			item.setAttachedResource(attachedResource);
		}
	}
//	public static RGB hex2Rgb(String colorStr) { 
//	int ri = colorStr.indexOf(",");
//	int gi = colorStr.indexOf(",",ri+1);
//	
//	String r = colorStr.substring( 1,ri);
//	String g = colorStr.substring(ri+1,gi);
//	String b = colorStr.substring(gi+1,	colorStr.indexOf(")",gi+1));
//	    return new RGB( 
//	            Integer.valueOf( r ), 
//	            Integer.valueOf( g ), 
//	            Integer.valueOf( b )); 
//	}  
//
//	public RGB getColor() {
//		if(item.getColor() == null){
//			item.setColor("(255,153,0)");
//		}
//		RGB color = hex2Rgb(item.getColor());
//		 return color;
//	}

//	public void setColor(RGB color) {
//		
//	//	if(!item.getColor().equals(color.toString())){
//		String col = "("+color.red+","+color.green+","+color.blue+")";
//			item.setColor(col);
//		//}
//	}
	
	public String getHelp(String tab) {
		String helpStr = "";
		EList<Help> helpContent = item.getHelpContent();
		for (Help help : helpContent) {
			if(help.getTab().getName().equals(tab)){
				helpStr = help.getContent();
				break;
			}
		}
		return helpStr;
	}
	
	public List<Help> getHelps(){
		EList<Help> helpContent = item.getHelpContent();
		return helpContent;
	}

	public void setHelp(String tab, String helpStr) {
		EList<Help> helpContent = item.getHelpContent();
		for (Help help : helpContent) {
			if(help.getTab().getName().equals(tab)){
				String content = help.getContent();
				if(!content.equals(helpStr)){
					help.setContent(helpStr);
					break;
				}
			}
		}
	}
	
	
	public List<String> getAvailableHelpTabs(){
		EList<Help> helpContent = item.getHelpContent();
		List<String> tabs = new ArrayList<String>();
		for (Help help : helpContent) {
			tabs.add(help.getTab().getName());
		}
		
		return tabs;
	}

	public void addhelpContent(Help help){
		EList<Help> helpContent = item.getHelpContent();
		String name = help.getTab().getName();
		Help existingHelp = null;
		for (Help h : helpContent) {
			if(h.getTab().getName().equalsIgnoreCase(name)){
				existingHelp = h;
				break;
			}
		}
		if(existingHelp != null){
			existingHelp.setContent(help.getContent());
		}else{
			helpContent.add(EcoreUtil.copy(help));
		}
		
		
	}
	
	public BpmnCommonPaletteGroup getParentBpmnPaletteGroup(){
		return parent;
	}

	
	public void setParentBpmnPaletteGroup(BpmnCommonPaletteGroup group){
		parent = group;
	}
	

//	public PaletteEntry getEntry() {
//		return entry;
//	}
//
//	public void setEntry(PaletteEntry entry) {
//		this.entry = entry;
//	}
}
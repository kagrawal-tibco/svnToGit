package com.tibco.cep.bpmn.common.palette.model;

import com.tibco.cep.studio.common.palette.PaletteGroup;

/**
 * 
 * @author majha
 *
 */
public class BpmnCommonPaletteGroupTypeInfo {

	
	protected PaletteGroup group;

	public BpmnCommonPaletteGroupTypeInfo( PaletteGroup group) {
		this.group = group;
	}

	public boolean isRef() {
		return group.isSetTitleRef();
	}

	public String getTitle() {
		return group.getTitle();
	}

	public void setTitle(String title) {
		if(!this.group.getTitle().equalsIgnoreCase(title)){
			group.setTitle(title);
		}
	}
		

	public String getTooltip() {
		return group.getTooltip();
	}

	public void setTooltip(String tooltip) {
		if(!group.getTooltip().equalsIgnoreCase(tooltip)){
			group.setTooltip(tooltip);
		}
	}

	public String getIcon() {
		return group.getIcon();
	}

	public void setIcon(String icon) {
		if(! group.getIcon().equalsIgnoreCase(icon)){
			group.setIcon(icon);
		}
	}

	public boolean isVisible() {
		return group.isVisible();
	}

	public void setVisible(boolean visible) {
		if(group.isVisible() != visible){
			group.setVisible(visible) ;
		}
	}

	public boolean isInternal() {
		return group.isInternal();
	}

	public void setInternal(boolean internal) {
		if(group.isInternal() != internal){
			group.setInternal(internal);
		}
	}
	
	public PaletteGroup getGroup() {
		return group;
	}
	

}
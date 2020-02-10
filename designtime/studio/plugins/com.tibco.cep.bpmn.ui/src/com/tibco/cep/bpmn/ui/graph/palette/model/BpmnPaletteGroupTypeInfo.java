package com.tibco.cep.bpmn.ui.graph.palette.model;

import com.tibco.cep.bpmn.common.palette.model.BpmnCommonPaletteGroupTypeInfo;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.graph.BpmnImages;
import com.tibco.cep.studio.common.palette.PaletteGroup;

/**
 * 
 * @author majha
 *
 */
public class BpmnPaletteGroupTypeInfo extends BpmnCommonPaletteGroupTypeInfo{

	
	protected PaletteGroup group;

	public BpmnPaletteGroupTypeInfo( PaletteGroup group) {
		super(group);
		this.group = group;
		if(isRef()){
			group.setTitle(Messages.getString(group.getTitleRef()));
			group.setTooltip(Messages.getString(group.getTooltipRef()));
			group.setIcon(BpmnImages.IMAGES_INIT+group.getIconRef());
		}
	}

//	public boolean isRef() {
//		return group.isSetTitleRef();
//	}
//
//	public String getTitle() {
//		return group.getTitle();
//	}
//
//	public void setTitle(String title) {
//		if(!this.group.getTitle().equalsIgnoreCase(title)){
//			group.setTitle(title);
//		}
//	}
//		
//
//	public String getTooltip() {
//		return group.getTooltip();
//	}
//
//	public void setTooltip(String tooltip) {
//		if(!group.getTooltip().equalsIgnoreCase(tooltip)){
//			group.setTooltip(tooltip);
//		}
//	}
//
//	public String getIcon() {
//		return group.getIcon();
//	}
//
//	public void setIcon(String icon) {
//		if(! group.getIcon().equalsIgnoreCase(icon)){
//			group.setIcon(icon);
//		}
//	}
//
//	public boolean isVisible() {
//		return group.isVisible();
//	}
//
//	public void setVisible(boolean visible) {
//		if(group.isVisible() != visible){
//			group.setVisible(visible) ;
//		}
//	}
//
//	public boolean isInternal() {
//		return group.isInternal();
//	}
//
//	public void setInternal(boolean internal) {
//		if(group.isInternal() != internal){
//			group.setInternal(internal);
//		}
//	}
//	
//	public PaletteGroup getGroup() {
//		return group;
//	}
//	

}
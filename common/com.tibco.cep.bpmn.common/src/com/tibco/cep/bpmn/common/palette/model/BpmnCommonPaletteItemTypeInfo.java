package com.tibco.cep.bpmn.common.palette.model;

import com.tibco.cep.studio.common.palette.PaletteItem;

/**
 * 
 * @author majha
 *
 */
public class BpmnCommonPaletteItemTypeInfo {

	
	protected PaletteItem item;

	public BpmnCommonPaletteItemTypeInfo( PaletteItem item) {
		this.item = item;
//		if(isRef()){
//			item.setTitle(Messages.getString(item.getTitleRef()));
//			item.setTooltip(Messages.getString(item.getTooltipRef()));
//			item.setIcon(BpmnImages.IMAGES_INIT+item.getIconRef());
//		}
	}

	public boolean isRef() {
		return item.isSetTitleRef();
	}

	public String getTitle() {
		return item.getTitle();
	}

	public void setTitle(String title) {
		if(!this.item.getTitle().equalsIgnoreCase(title)){
			item.setTitle(title);
		}
	}
		

	public String getTooltip() {
		return item.getTooltip();
	}

	public void setTooltip(String tooltip) {
		if(!item.getTooltip().equalsIgnoreCase(tooltip)){
			item.setTooltip(tooltip);
		}
	}

	public String getIcon() {
		return item.getIcon();
	}

	public void setIcon(String icon) {
		if(! item.getIcon().equalsIgnoreCase(icon)){
			item.setIcon(icon);
		}
	}

	public boolean isVisible() {
		return item.isVisible();
	}

	public void setVisible(boolean visible) {
		if(item.isVisible() != visible){
			item.setVisible(visible) ;
		}
	}

	public boolean isInternal() {
		return item.isInternal();
	}

	public void setInternal(boolean internal) {
		if(item.isInternal() != internal){
			item.setInternal(internal);
		}
	}
	
	public PaletteItem getItem() {
		return item;
	}

}
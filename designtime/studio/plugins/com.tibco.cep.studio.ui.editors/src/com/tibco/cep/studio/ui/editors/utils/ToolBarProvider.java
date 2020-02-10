

package com.tibco.cep.studio.ui.editors.utils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.ColorConstants;

/**
 * 
 * @author aasingh
 */

public class ToolBarProvider {

	AbstractSaveableEntityEditorPart editor;
	Entity ruleParticipant;
	protected static Composite parent;
	protected  ToolItem addItem;
	protected  ToolItem delItem;
	protected ToolItem fitContentItem;

	protected ToolItem addGroupItem;
	protected static final int BUTTON_ADD_GRP = 0;
	protected static final int BUTTON_ADD = 1;
	protected static final int BUTTON_DELETE = 2;
	
	/*private static final String ADD_ICON = "/icons/add_16x16.png";
	private static final String DEL_ICON = "/icons/delete.png";
	private static final String ADDGRP_ICON = "/icons/add_group.gif";
	*/
   enum toolItems {
		addToolItem("Add","/icons/add_16x16.png"),
		delToolItem("Delete","/icons/delete.png"),
		addGroupToolItem("Add Group","/icons/add_group.gif");

		
		private String name;
		private String imageIcon;
		
		private toolItems(String name, String path){
			this.name = name;
			this.imageIcon = path;
		}
		
	   public String getName() {
			return name;
		}
		
	   public String getImageIcon() {
			return imageIcon;
		}
		
	}
	
	public ToolBarProvider(Composite temp_parent,AbstractSaveableEntityEditorPart editor) {
	//	this.ruleParticipant=entity;
		parent = temp_parent;
		this.editor=editor;
	}

	

	public ToolBar createPropertiesToolbar(Entity entity) {
        ToolBar toolBar = new ToolBar(parent, SWT.HORIZONTAL | SWT.RIGHT | SWT.FLAT);
        toolBar.setBackground(ColorConstants.white);
        toolBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        add_addItem(toolBar);
        add_delItem(toolBar);
        toolBar.pack();
        return toolBar;
	}
	

	
	public ToolBar CreateHierarchicalToolbar(){
		 ToolBar toolBar = new ToolBar(parent, SWT.HORIZONTAL | SWT.RIGHT | SWT.FLAT);
	     toolBar.setBackground(ColorConstants.white);
	     toolBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	     add_addGroupItem(toolBar);
	     add_addItem(toolBar);
	     add_delItem(toolBar);
		 toolBar.pack();
        return toolBar;
	}

	protected void add_addItem(ToolBar toolBar ){
		
		setAddItem(new ToolItem(toolBar, SWT.PUSH));
        Image addImg = StudioUIPlugin.getDefault().getImage(toolItems.addToolItem.getImageIcon());
        getAddItem().setImage(addImg);
        getAddItem().setText(toolItems.addToolItem.getName());
        getAddItem().setToolTipText(toolItems.addToolItem.getName());
       
	}
	
	protected void add_delItem( ToolBar toolBar){
		setDelItem(new ToolItem(toolBar, SWT.PUSH));
        Image delImg = StudioUIPlugin.getDefault().getImage(toolItems.delToolItem.getImageIcon());
        getDelItem() .setImage(delImg);
        getDelItem() .setText(toolItems.delToolItem.getName());
        getDelItem() .setToolTipText(toolItems.delToolItem.getName());
	}
	
	
	protected  void add_addGroupItem(ToolBar toolBar){
		addGroupItem = new ToolItem(toolBar, SWT.PUSH);
        Image addGroupImg = StudioUIPlugin.getDefault().getImage(toolItems.addGroupToolItem.getImageIcon());
        addGroupItem .setImage( addGroupImg);
        addGroupItem .setText(toolItems.addGroupToolItem.getName());
        addGroupItem .setToolTipText(toolItems.addGroupToolItem.getName());
	}
	
	public void dispose() {
	}
	
	public void setAddItemListener(Listener listener) {
		getAddItem().addListener(SWT.Selection, listener);
	}
	
	public void setDelItemListener(Listener listener) {
		getDelItem().addListener(SWT.Selection, listener);
	}

	public void setFitContentItemListener(Listener listener) {
		getFitContentItem().addListener(SWT.Selection, listener);
	}
	
	public void setAddGrpItemListener(Listener listener) {
		if (addGroupItem != null)
			addGroupItem.addListener(SWT.Selection, listener);
	}
	
	
	public void enableActionButton(int buttonId, boolean en) {
		switch (buttonId) {
		    case BUTTON_ADD_GRP:enableActionButton(getAddGroupItem(), en); break;
			case BUTTON_ADD: enableActionButton(getAddItem(), en); break;
			case BUTTON_DELETE: enableActionButton(getDelItem(), en); break;
			
		}
	}
	
	public void enableActionButton(ToolItem item, boolean en) {
		if (item != null)
			item.setEnabled(en);
	}

	public ToolItem getAddItem() {
		return addItem;
	}

	public void setAddItem(ToolItem addItem) {
		this.addItem = addItem;
	}

	public ToolItem getDelItem() {
		return delItem;
	}

	public void setDelItem(ToolItem delItem) {
		this.delItem = delItem;
	}
	
	public ToolItem getFitContentItem() {
		return fitContentItem;
	}
	
	public void setFitContentItem(ToolItem fitContentItem) {
		this.fitContentItem = fitContentItem;
	}

	public ToolItem getAddGroupItem() {
		return addGroupItem;
	}

	public void setAddGroupItem(ToolItem addGroupItem) {
		this.addGroupItem = addGroupItem;
	}
}



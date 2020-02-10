package com.tibco.cep.studio.ui.forms.advancedEventPayloadEditor;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.StudioUIPlugin;

public class PayloadEditorToolBarProvider {
	
	AbstractSaveableEntityEditorPart editor;
	Entity ruleParticipant;
	protected static Composite parent;
	private ToolItem payloadAddItem;
	private ToolItem payloadAddTopOrChildItem ;
	private ToolItem payloadDeleteItem ;
	private ToolItem payloadMoveUpItem ;
	private ToolItem payloadMoveDownItem ;
	private ToolItem payloadMoveOutItem ;
	private ToolItem payloadMoveInItem ;
	private static String PLUGIN_ID ="com.tibco.cep.studio.mapper";
	
	private static Map<String,Image> imgCache = new HashMap<String,Image>();

	protected static Color toolBarBackgroundColor = null;
//    static {
//        ResourceBundleManager.addResourceBundle("com.tibco.cep.studio.mapper.ui.data.Resources", null);
//        ResourceBundleManager.addResourceBundle("com.tibco.cep.studio.mapper.ui.data.bind.Resources", null);
//    }
    
    enum toolItems {
  		addToolItem("Add","ui/src/com/tibco/ui/data/resources/iconAdd.gif"),
  		addTopOrChildItem("AddTopOrChild","ui/src/com/tibco/ui/data/resources/iconAddChild.gif"),
  		delToolItem("Delete","ui/src/com/tibco/ui/data/resources/iconDelete.gif"),
  		movUpToolItem("MoveUp","ui/src/com/tibco/ui/data/resources/iconMoveUp.gif"),
  		movDownToolItem("MoveDown","ui/src/com/tibco/ui/data/resources/iconMoveDown.gif"),
  		movOutToolItem("MoveOut","ui/src/com/tibco/ui/data/resources/iconMoveLeft.gif"),
  		movInToolItem("MoveIn","ui/src/com/tibco/ui/data/resources/iconMoveRight.gif");
  		
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
    
	public ToolBar createPayloadToolBar(Composite comp){
		parent = comp;
		ToolBar toolBar = new ToolBar(comp, SWT.HORIZONTAL | SWT.RIGHT
				| SWT.FLAT);
		toolBarBackgroundColor = new Color(null, 255, 255, 255);
		toolBar.setBackground(toolBarBackgroundColor);
		toolBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		add_addItem(toolBar);
		add_addTopOrChildItem(toolBar);
		add_delItem(toolBar);
		add_movUpItem(toolBar);
		add_movDownItem(toolBar);
		add_movInItem(toolBar);
		add_movOutItem(toolBar);
		toolBar.pack();
		return toolBar;
	}
	/*
	 * todo : implement image cache
	 */
	
	private void add_movInItem(ToolBar toolBar){
		setPayloadMoveInItem(new ToolItem(toolBar, SWT.PUSH|SWT.BORDER));
		ImageDescriptor imgDesc = StudioUIPlugin.getImageDescriptor(PLUGIN_ID ,toolItems.movInToolItem.getImageIcon());
		Image img = null;
		if(! getImgCache().containsKey(toolItems.movOutToolItem.getName())) {
			imgDesc = StudioUIPlugin.getImageDescriptor(PLUGIN_ID ,toolItems.movInToolItem.getImageIcon());
			img =  imgDesc.createImage();
			getImgCache().put(toolItems.movOutToolItem.getName(), img);
           
		} else {
			img = getImgCache().get(toolItems.movOutToolItem.getName());
		}
        getPayloadMoveInItem().setImage(img);
        getPayloadMoveInItem().setToolTipText(toolItems.movInToolItem.getName());
       
	}
	private void add_movOutItem(ToolBar toolBar){
		setPayloadMoveOutItem(new ToolItem(toolBar, SWT.PUSH|SWT.BORDER));
		ImageDescriptor imgDesc = StudioUIPlugin.getImageDescriptor(PLUGIN_ID ,toolItems.movOutToolItem.getImageIcon());
        Image addImg =  imgDesc.createImage();
        getPayloadMoveOutItem().setImage(addImg);
        getPayloadMoveOutItem().setToolTipText(toolItems.movOutToolItem.getName());
       
	}
	private void add_movUpItem(ToolBar toolBar){
		setPayloadMoveUpItem(new ToolItem(toolBar, SWT.PUSH|SWT.BORDER));
		ImageDescriptor imgDesc = StudioUIPlugin.getImageDescriptor(PLUGIN_ID ,toolItems.movUpToolItem.getImageIcon());
        Image addImg =  imgDesc.createImage();
        getPayloadMoveUpItem().setImage(addImg);
        getPayloadMoveUpItem().setToolTipText(toolItems.movUpToolItem.getName());
       
	}
	
	private void add_movDownItem(ToolBar toolBar){
		setPayloadMoveDownItem(new ToolItem(toolBar, SWT.PUSH|SWT.BORDER));
		ImageDescriptor imgDesc = StudioUIPlugin.getImageDescriptor(PLUGIN_ID ,toolItems.movDownToolItem.getImageIcon());
        Image addImg =  imgDesc.createImage();
        getPayloadMoveDownItem().setImage(addImg);
        getPayloadMoveDownItem().setToolTipText(toolItems.movDownToolItem.getName());
       
	}
	private void add_delItem(ToolBar toolBar){
		setPayloadDelete(new ToolItem(toolBar,SWT.PUSH|SWT.BORDER));
		ImageDescriptor imgDesc = StudioUIPlugin.getImageDescriptor(PLUGIN_ID ,toolItems.delToolItem.getImageIcon());
        Image addImg =  imgDesc.createImage();
        getPayloadDelete().setImage(addImg);
        getPayloadDelete().setToolTipText(toolItems.delToolItem.getName());
       
	}
	private void add_addTopOrChildItem(ToolBar toolBar){
		setPayloadAddTopOrChildItem(new ToolItem(toolBar,SWT.PUSH|SWT.BORDER));
		ImageDescriptor imgDesc = StudioUIPlugin.getImageDescriptor(PLUGIN_ID ,toolItems.addTopOrChildItem.getImageIcon());
        Image addImg =  imgDesc.createImage();
        getPayloadAddTopOrChildItem().setImage(addImg);
        getPayloadAddTopOrChildItem().setToolTipText(toolItems.addTopOrChildItem.getName());
       
	}
	private void add_addItem(ToolBar toolBar ){
		
		setPayloadAddItem(new ToolItem(toolBar, SWT.PUSH|SWT.BORDER));
		ImageDescriptor imgDesc = StudioUIPlugin.getImageDescriptor(PLUGIN_ID ,toolItems.addToolItem.getImageIcon());
        Image addImg =  imgDesc.createImage();
        getPayloadAddItem().setImage(addImg);
        getPayloadAddItem().setToolTipText(toolItems.addToolItem.getName());
       
	}
	public ToolItem getPayloadAddItem() {
		return payloadAddItem;
	}

	public void setPayloadAddItem(ToolItem payloadAddItem) {
		this.payloadAddItem = payloadAddItem;
	}

	public static Composite getParent() {
		return parent;
	}

	public static void setParent(Composite parent) {
		PayloadEditorToolBarProvider.parent = parent;
	}

	public ToolItem getPayloadAddTopOrChildItem() {
		return payloadAddTopOrChildItem;
	}

	public void setPayloadAddTopOrChildItem(ToolItem payloadAddTopOrChildItem) {
		this.payloadAddTopOrChildItem = payloadAddTopOrChildItem;
	}

	public ToolItem getPayloadDelete() {
		return payloadDeleteItem;
	}

	public void setPayloadDelete(ToolItem payloadDelete) {
		this.payloadDeleteItem = payloadDelete;
	}

	public ToolItem getPayloadDeleteItem() {
		return payloadDeleteItem;
	}

	public void setPayloadDeleteItem(ToolItem payloadDeleteItem) {
		this.payloadDeleteItem = payloadDeleteItem;
	}

	public ToolItem getPayloadMoveUpItem() {
		return payloadMoveUpItem;
	}

	public void setPayloadMoveUpItem(ToolItem payloadMoveUpItem) {
		this.payloadMoveUpItem = payloadMoveUpItem;
	}

	public ToolItem getPayloadMoveDownItem() {
		return payloadMoveDownItem;
	}

	public void setPayloadMoveDownItem(ToolItem payloadMoveDownItem) {
		this.payloadMoveDownItem = payloadMoveDownItem;
	}

	public ToolItem getPayloadMoveOutItem() {
		return payloadMoveOutItem;
	}

	public void setPayloadMoveOutItem(ToolItem payloadMoveOutItem) {
		this.payloadMoveOutItem = payloadMoveOutItem;
	}

	public ToolItem getPayloadMoveInItem() {
		return payloadMoveInItem;
	}

	public void setPayloadMoveInItem(ToolItem payloadMoveInItem) {
		this.payloadMoveInItem = payloadMoveInItem;
	}

	public static Map<String, Image> getImgCache() {
		return imgCache;
	}

	public static void setImgCache(Map<String, Image> imgCache) {
		PayloadEditorToolBarProvider.imgCache = imgCache;
	}
	
	public void setAllItemSelectionListener(Listener listener) {
//		setAddGrpItemListener(listener);
		setAddItemListener(listener);
//		setDelItemListener(listener);
//		setfitContentItemListener(listener);
//		setMoveUpItemListener(listener);
//		setMoveDownItemListener(listener);
//		setDuplicateItemListener(listener);
	}
	
	public void setAddItemListener(Listener listener) {
		getPayloadAddTopOrChildItem().addListener(SWT.Selection, listener);
	}

}

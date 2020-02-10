package com.tibco.cep.studio.ui.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.tibco.cep.studio.ui.StudioUIPlugin;

/*
@author ssailapp
@date Nov 28, 2009 7:38:29 PM
 */

public class ToolBarProvider {

	private static String TOOLBAR_ICON_PATH = "/icons/toolbar/";
	public static String ICON_TOOLBAR_LIST_ADD = TOOLBAR_ICON_PATH + "add_16x16.png";
	public static String ICON_TOOLBAR_LIST_ADD_GROUP = TOOLBAR_ICON_PATH + "add_group.gif";
	public static String ICON_TOOLBAR_LIST_DELETE = TOOLBAR_ICON_PATH + "delete.png";
	public static String ICON_TOOLBAR_LIST_FIT_CONTENT = TOOLBAR_ICON_PATH + "fit_content_16x16.png";
	public static String ICON_TOOLBAR_LIST_DUPLICATE = TOOLBAR_ICON_PATH + "list_duplicate.png";
	public static String ICON_TOOLBAR_LIST_UP = TOOLBAR_ICON_PATH + "arrow_up.png";
	public static String ICON_TOOLBAR_LIST_DOWN = TOOLBAR_ICON_PATH + "arrow_down.png";
	public static String ICON_TOOLBAR_EDIT = TOOLBAR_ICON_PATH + "edit.png";
	
	private Composite parent;
	private ToolItem addGrpItem, addItem, delItem,fitcontentItem, duplItem, editItem;
	private ToolItem upItem, downItem;
	private Color toolBarBackgroundColor = null;
	
	public static final int BUTTON_ADD_GRP = 0;
	public static final int BUTTON_ADD = 1;
	public static final int BUTTON_DELETE = 2;
	public static final int BUTTON_MOVE_UP = 3;
	public static final int BUTTON_MOVE_DOWN = 4;
	public static final int BUTTON_DUPLICATE = 5;
	public static final int BUTTON_EDIT = 6;
	
	private boolean showBackgroundColor = true;
	private boolean showButtonText = false;

	public ToolBarProvider(Composite parent) {
		this.parent = parent;
	}
	
	public ToolBar createToolbar(boolean hasLevels, boolean hasOrdering, boolean duplicate,boolean fitcontent) {
		ToolBar toolBar = createToolbar(hasLevels, hasOrdering);
		
		if (duplicate) {
			duplItem = new ToolItem(toolBar, SWT.PUSH);
			duplItem.setImage(getDuplicateImage());
			duplItem.setToolTipText("Duplicate");
		}
		
		if(fitcontent){
			fitcontentItem=new ToolItem(toolBar,SWT.PUSH);
	        fitcontentItem.setImage(getFitContentImage());
	        fitcontentItem.setToolTipText("Fit Content");
		}
		
		
		setButtonText();
		toolBar.pack();
		return toolBar;
	}

	public ToolBar createToolbar(boolean hasLevels, boolean hasOrdering) {
        return createToolbar(hasLevels, hasOrdering, false);
	}
	public ToolBar createToolbar(boolean hasLevels, boolean hasOrdering, boolean hasEdit) {
        ToolBar toolBar = new ToolBar(parent, SWT.HORIZONTAL | SWT.RIGHT | SWT.FLAT);

        if (showBackgroundColor) {
        	toolBarBackgroundColor = new Color(null, 225, 225, 225);
        	toolBar.setBackground(toolBarBackgroundColor);
        }
        
        toolBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        if (hasLevels) {
	        addGrpItem = new ToolItem(toolBar, SWT.PUSH);
	        addGrpItem.setImage(getAddGroupImage());
	        addGrpItem.setToolTipText("Add Group");
        }
	        
        addItem = new ToolItem(toolBar, SWT.PUSH);
        addItem.setImage(getAddImage());
        addItem.setToolTipText("Add");
        
        delItem = new ToolItem(toolBar, SWT.PUSH);
        delItem.setImage(getDeleteImage());
        delItem.setToolTipText("Delete");
        
        if (hasOrdering) {
        	upItem = new ToolItem(toolBar, SWT.PUSH);
        	upItem.setImage(getUpImage());
        	upItem.setToolTipText("Move Up");
        	downItem = new ToolItem(toolBar, SWT.PUSH);
        	downItem.setImage(getDownImage());
        	downItem.setToolTipText("Move Down");
        }
        
		if (hasEdit) {
			editItem = new ToolItem(toolBar, SWT.PUSH);
			editItem.setImage(getEditImage());
			editItem.setToolTipText("Edit");
		}
        setButtonText();
        toolBar.pack();
        return toolBar;
	}

	public ToolBar createToolbar() {
        ToolBar toolBar = new ToolBar(parent, SWT.HORIZONTAL | SWT.RIGHT | SWT.FLAT);

        if (showBackgroundColor) {
        	toolBarBackgroundColor = new Color(null, 225, 225, 225);
        	toolBar.setBackground(toolBarBackgroundColor);
        }
        
        toolBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

                
        addItem = new ToolItem(toolBar, SWT.PUSH);
        addItem.setImage(getAddImage());
        addItem.setToolTipText("Add");
        
        delItem = new ToolItem(toolBar, SWT.PUSH);
        delItem.setImage(getDeleteImage());
        delItem.setToolTipText("Delete");
       
        fitcontentItem=new ToolItem(toolBar,SWT.PUSH);
        fitcontentItem.setImage(getFitContentImage());
        fitcontentItem.setToolTipText("Fit Content");
        
        setButtonText();
        toolBar.pack();
        return toolBar;
	}

	
	public void dispose() {
		if (toolBarBackgroundColor != null)
			toolBarBackgroundColor.dispose();
	}
	
	private void setButtonText() {
		if (showButtonText) {
			if (addGrpItem != null) {			
				addGrpItem.setText("Add Group");
			}
			addItem.setText("Add");
			delItem.setText("Delete");
			if(fitcontentItem!=null){
				fitcontentItem.setText("Fit Content");
			}
			if (duplItem != null) {			
				duplItem.setText("Duplicate");
			}

			if (upItem != null) {
				upItem.setText("Move Up");	
			}
			if (downItem != null) {
				downItem.setText("Move Down");   
			}
		}
	}
	
	public void setAddGrpItemListener(Listener listener) {
		if (addGrpItem != null)
			addGrpItem.addListener(SWT.Selection, listener);
	}
	
	public void setAddItemListener(Listener listener) {
		addItem.addListener(SWT.Selection, listener);
	}
	
	public void setDelItemListener(Listener listener) {
		delItem.addListener(SWT.Selection, listener);
	}
	
	public void setfitContentItemListener(Listener listener) {
		if(fitcontentItem!=null)
		fitcontentItem.addListener(SWT.Selection, listener);
	}
	
	public void setMoveUpItemListener(Listener listener) {
		if (upItem != null)
			upItem.addListener(SWT.Selection, listener);
	}

	public void setMoveDownItemListener(Listener listener) {
		if (downItem != null)
			downItem.addListener(SWT.Selection, listener);
	}
	
	public void setDuplicateItemListener(Listener listener) {
		if (duplItem != null) {
			duplItem.addListener(SWT.Selection, listener);
		}
	}
	
	   public void setEditItemListener(Listener listener) {
	        if (editItem != null) {
	            editItem.addListener(SWT.Selection, listener);
	        }
	    }
	
	public void setAllItemSelectionListener(Listener listener) {
		setAddGrpItemListener(listener);
		setAddItemListener(listener);
		setDelItemListener(listener);
		setfitContentItemListener(listener);
		setMoveUpItemListener(listener);
		setMoveDownItemListener(listener);
		setDuplicateItemListener(listener);
	}
	

	public void enableActionButton(int buttonId, boolean en) {
		switch (buttonId) {
			case BUTTON_ADD_GRP: enableActionButton(addGrpItem, en); break;
			case BUTTON_ADD: enableActionButton(addItem, en); break;
			case BUTTON_DELETE: enableActionButton(delItem, en); break;
			case BUTTON_MOVE_UP: enableActionButton(upItem, en); break;
			case BUTTON_MOVE_DOWN: enableActionButton(downItem, en); break;
			case BUTTON_DUPLICATE: enableActionButton(duplItem, en); break;
			case BUTTON_EDIT: enableActionButton(editItem, en); break;
		}
	}
	
	private void enableActionButton(ToolItem item, boolean en) {
		if (item != null)
			item.setEnabled(en);
	}
	
	protected Image getAddImage() {
		return StudioUIPlugin.getDefault().getImage(ICON_TOOLBAR_LIST_ADD);
	}
	
	protected Image getDeleteImage() {
		return StudioUIPlugin.getDefault().getImage(ICON_TOOLBAR_LIST_DELETE);
	}
	
	protected Image getFitContentImage() {
		return StudioUIPlugin.getDefault().getImage(ICON_TOOLBAR_LIST_FIT_CONTENT);
	}
	
	protected Image getDuplicateImage() {
		return StudioUIPlugin.getDefault().getImage(ICON_TOOLBAR_LIST_DUPLICATE);
	}
	
	protected Image getAddGroupImage() {
		return StudioUIPlugin.getDefault().getImage(ICON_TOOLBAR_LIST_ADD_GROUP);
	}
	
	protected Image getUpImage() {
		return StudioUIPlugin.getDefault().getImage(ICON_TOOLBAR_LIST_UP);
	}
	
	protected Image getDownImage() {
		return StudioUIPlugin.getDefault().getImage(ICON_TOOLBAR_LIST_DOWN);
	}
	
   protected Image getEditImage() {
        return StudioUIPlugin.getDefault().getImage(ICON_TOOLBAR_EDIT);
    }
	
	public ToolItem getAddGroupItem() {
		return addGrpItem;
	}

	public ToolItem getAddItem() {
		return addItem;
	}

	public ToolItem getDeleteItem() {
		return delItem;
	}

	public ToolItem getDuplicateItem() {
		return duplItem;
	}

	public ToolItem getUpItem() {
		return upItem;
	}

	public ToolItem getDownItem() {
		return downItem;
	}
	
	public ToolItem getFitcontentItem() {
		return fitcontentItem;
	}
	
	public void setShowBackgroundColor(boolean showBackgroundColor) {
		this.showBackgroundColor = showBackgroundColor;
	}
	
	public void setShowButtonText(boolean showButtonText) {
		this.showButtonText = showButtonText;
	}

}
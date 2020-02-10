package com.tibco.cep.studio.dashboard.ui.viewers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.images.KnownImageKeys;

public class TableToolbar {

    private ToolBar toolBar;

    public static final int TOOL_ITEM_ADD = 0;
    public static final int TOOL_ITEM_ADD_BIT = 1;

    public static final int TOOL_ITEM_DELETE = 1;
    public static final int TOOL_ITEM_DELETE_BIT = 2;
    
    public static final int TOOL_ITEM_UP = 2;
    public static final int TOOL_ITEM_UP_BIT = 4;

    public static final int TOOL_ITEM_DOWN = 3;
    public static final int TOOL_ITEM_DOWN_BIT = 8;

    public static final int TOOL_ITEM_SEPARATOR = 4;
    public static final int TOOL_ITEM_SEPARATOR_BIT = 16;

    public static final int TOOL_ITEM_SHOW_SYSTEM_FIELDS = 5;
    public static final int TOOL_ITEM_SHOW_SYSTEM_FIELDS_BIT = 32;

    private boolean isReadOnly = false;

    private Map<Integer, ToolItem> buttonMap = new HashMap<Integer, ToolItem>();

    private Map<String,Object> buttonLocked = new HashMap<String, Object>();

    private List<String> currentActive = new ArrayList<String>();

    private int currentActiveButton = 0;
    
    public TableToolbar(Composite parent, int style) {

        toolBar = new ToolBar(parent, style);//| SWT.BORDER | SWT.FLAT);
        toolBar.setSize(200, 32);

        /*
         * creates the add button
         */
        ToolItem item = new ToolItem(toolBar, SWT.PUSH, TOOL_ITEM_ADD);
        item.setImage(DashboardUIPlugin.getInstance().getImageRegistry().get(KnownImageKeys.IMG_KEY_ADD));
        item.setEnabled(false);
        item.setToolTipText("Add");
        buttonMap.put(new Integer(TOOL_ITEM_ADD), item);

        /*
         * creates the delete button
         */
        item = new ToolItem(toolBar, SWT.PUSH, TOOL_ITEM_DELETE);
        item.setImage(DashboardUIPlugin.getInstance().getImageRegistry().get(KnownImageKeys.IMG_KEY_DELETE));
        item.setEnabled(false);
        item.setToolTipText("Delete");
        buttonMap.put(new Integer(TOOL_ITEM_DELETE), item);

//        /*
//         * creates the up button
//         */
//        item = new ToolItem(toolBar, SWT.PUSH, TOOL_ITEM_UP);
//        item.setImage(DashboardUIPlugin.getInstance().getImageRegistry().get(DashboardCoreImageRegistry.IMG_KEY_UP));
//        item.setEnabled(false);
//        item.setToolTipText("Move up");
//
//        buttonMap.put(new Integer(TOOL_ITEM_UP), item);
//
//        /*
//         * creates the down button
//         */
//        item = new ToolItem(toolBar, SWT.PUSH, TOOL_ITEM_DOWN);
//        item.setImage(DashboardUIPlugin.getInstance().getImageRegistry().get(DashboardCoreImageRegistry.IMG_KEY_DOWN));
//        item.setEnabled(false);
//        item.setToolTipText("Move down");
//
//        buttonMap.put(new Integer(TOOL_ITEM_DOWN), item);

        /*
         * creates the separator
         */
//        item = new ToolItem(toolBar, SWT.SEPARATOR, TOOL_ITEM_SEPARATOR);

        /*
         * creates the down button
         */
//        item = new ToolItem(toolBar, SWT.CHECK, TOOL_ITEM_SHOW_SYSTEM_FIELDS);
//        item.setImage(DashboardUIPlugin.getInstance().getImageRegistry().get(DashboardCoreImageRegistry.IMG_KEY_IS_SYSTEM));
//        item.setEnabled(false);
//        item.setToolTipText("Show system fields");
//
//        buttonMap.put(new Integer(TOOL_ITEM_SHOW_SYSTEM_FIELDS), item);

    }

    /**
     * 
     * @return the <code>ToolBar</code>
     */
    public ToolBar getToolBar() {
        return toolBar;
    }
    
    /**
     * dynamically created buttons.
     * @param parent
     * @param style
     * @param button
     */
    public TableToolbar(Composite parent, int style, int buttons) {

    	currentActiveButton = buttons;
        toolBar = new ToolBar(parent, style);//| SWT.BORDER | SWT.FLAT);
        toolBar.setSize(200, 32);

        ToolItem item = null;
        /*
         * creates the add button
         */
        if ( (currentActiveButton & TOOL_ITEM_ADD_BIT) == TOOL_ITEM_ADD_BIT ) {
	        item = new ToolItem(toolBar, SWT.PUSH);
	        item.setImage(DashboardUIPlugin.getInstance().getImageRegistry().get(KnownImageKeys.IMG_KEY_ADD));
	        item.setEnabled(false);
	        item.setToolTipText("Add");
	        buttonMap.put(new Integer(TOOL_ITEM_ADD), item);
        }

        /*
         * creates the delete button
         */
        if ( (currentActiveButton & TOOL_ITEM_DELETE_BIT) == TOOL_ITEM_DELETE_BIT ) {
	        item = new ToolItem(toolBar, SWT.PUSH);
	        item.setImage(DashboardUIPlugin.getInstance().getImageRegistry().get(KnownImageKeys.IMG_KEY_DELETE));
	        item.setEnabled(false);
	        item.setToolTipText("Delete");
	        buttonMap.put(new Integer(TOOL_ITEM_DELETE), item);
        }

        /*
         * creates the up button
         */
        if ( (currentActiveButton & TOOL_ITEM_UP_BIT) == TOOL_ITEM_UP_BIT ) {
	        item = new ToolItem(toolBar, SWT.PUSH);
	        item.setImage(DashboardUIPlugin.getInstance().getImageRegistry().get(KnownImageKeys.IMG_KEY_UP));
	        item.setEnabled(false);
	        item.setToolTipText("Move up");
	        buttonMap.put(new Integer(TOOL_ITEM_UP), item);
        }

        /*
         * creates the down button
         */
        if ( (currentActiveButton & TOOL_ITEM_DOWN_BIT) == TOOL_ITEM_DOWN_BIT ) {
	        item = new ToolItem(toolBar, SWT.PUSH);
	        item.setImage(DashboardUIPlugin.getInstance().getImageRegistry().get(KnownImageKeys.IMG_KEY_DOWN));
	        item.setEnabled(false);
	        item.setToolTipText("Move down");
	        buttonMap.put(new Integer(TOOL_ITEM_DOWN), item);
        }

        if ( (currentActiveButton & TOOL_ITEM_SHOW_SYSTEM_FIELDS_BIT) == TOOL_ITEM_SHOW_SYSTEM_FIELDS_BIT ) {
	        /*
	         * creates the separator
	         */
	        item = new ToolItem(toolBar, SWT.SEPARATOR);
	        /*
	         * creates the down button
	         */
	        item = new ToolItem(toolBar, SWT.CHECK);
	        item.setImage(DashboardUIPlugin.getInstance().getImageRegistry().get(KnownImageKeys.IMG_KEY_IS_SYSTEM));
	        item.setEnabled(false);
	        item.setToolTipText("Show system fields");
	        buttonMap.put(new Integer(TOOL_ITEM_SHOW_SYSTEM_FIELDS), item);
        }


    }

    public boolean isLocked(int itemID) {
        return buttonLocked.containsKey(itemID + "");
    }

    public void setLock(int item, boolean flag) {
        if (true == flag) {
            buttonLocked.put(item + "", new Object());
        }
        else {
            buttonLocked.remove(item + "");
        }

    }

    public void setLockAll(boolean flag) {
        if (true == flag) {
        	if ( (currentActiveButton & TOOL_ITEM_ADD_BIT) == TOOL_ITEM_ADD_BIT) {
                buttonLocked.put(TOOL_ITEM_ADD + "", new Object());
        	}
        	if ( (currentActiveButton & TOOL_ITEM_DELETE_BIT) == TOOL_ITEM_DELETE_BIT) {
                buttonLocked.put(TOOL_ITEM_DELETE + "", new Object());
        	}
        	if ( (currentActiveButton & TOOL_ITEM_UP_BIT) == TOOL_ITEM_UP_BIT) {
                buttonLocked.put(TOOL_ITEM_UP + "", new Object());
        	}
        	if ( (currentActiveButton & TOOL_ITEM_DOWN_BIT) == TOOL_ITEM_DOWN_BIT) {
                buttonLocked.put(TOOL_ITEM_DOWN + "", new Object());
        	}

            currentActive.clear();
        	if ( (currentActiveButton & TOOL_ITEM_ADD_BIT) == TOOL_ITEM_ADD_BIT) {
	            if (true == getToolItem(TOOL_ITEM_ADD).isEnabled()) {
	                currentActive.add(TOOL_ITEM_ADD + "");
	            }
        	}
        	if ( (currentActiveButton & TOOL_ITEM_DELETE_BIT) == TOOL_ITEM_DELETE_BIT) {
	            if (true == getToolItem(TOOL_ITEM_DELETE).isEnabled()) {
	                currentActive.add(TOOL_ITEM_DELETE + "");
	            }
        	}
        	if ( (currentActiveButton & TOOL_ITEM_UP_BIT) == TOOL_ITEM_UP_BIT) {
	            if (true == getToolItem(TOOL_ITEM_UP).isEnabled()) {
	                currentActive.add(TOOL_ITEM_UP + "");
	            }
        	}
	        if ( (currentActiveButton & TOOL_ITEM_DOWN_BIT) == TOOL_ITEM_DOWN_BIT) {
	            if (true == getToolItem(TOOL_ITEM_DOWN).isEnabled()) {
	                currentActive.add(TOOL_ITEM_DOWN + "");
	            }
	        }
        }
        else {
        	if ( (currentActiveButton & TOOL_ITEM_ADD_BIT) == TOOL_ITEM_ADD_BIT) {
                buttonLocked.remove(TOOL_ITEM_ADD + "");
        	}
        	if ( (currentActiveButton & TOOL_ITEM_DELETE_BIT) == TOOL_ITEM_DELETE_BIT) {
                buttonLocked.remove(TOOL_ITEM_DELETE + "");
        	}
        	if ( (currentActiveButton & TOOL_ITEM_UP_BIT) == TOOL_ITEM_UP_BIT) {
                buttonLocked.remove(TOOL_ITEM_UP + "");
        	}
        	if ( (currentActiveButton & TOOL_ITEM_DOWN_BIT) == TOOL_ITEM_DOWN_BIT) {
                buttonLocked.remove(TOOL_ITEM_DOWN + "");
        	}
            

        	if ( (currentActiveButton & TOOL_ITEM_ADD_BIT) == TOOL_ITEM_ADD_BIT) {
	            if(true == currentActive.contains(TOOL_ITEM_ADD + "")) {
	                getToolItem(TOOL_ITEM_ADD).setEnabled(true);
	            }
        	}
        	if ( (currentActiveButton & TOOL_ITEM_DELETE_BIT) == TOOL_ITEM_DELETE_BIT) {
	            if(true == currentActive.contains(TOOL_ITEM_DELETE + "")) {
	                getToolItem(TOOL_ITEM_DELETE).setEnabled(true);
	            }
        	}
        	if ( (currentActiveButton & TOOL_ITEM_UP_BIT) == TOOL_ITEM_UP_BIT) {
	            if(true == currentActive.contains(TOOL_ITEM_UP + "")) {
	                getToolItem(TOOL_ITEM_UP).setEnabled(true);
	            }
        	}
        	if ( (currentActiveButton & TOOL_ITEM_DOWN_BIT) == TOOL_ITEM_DOWN_BIT) {
	            if(true == currentActive.contains(TOOL_ITEM_DOWN + "")) {
	                getToolItem(TOOL_ITEM_DOWN).setEnabled(true);
	            }
        	}	
       }
    }

    public ToolItem getToolItem(int itemID) {

        Object item = buttonMap.get(new Integer(itemID));

        if (null != item) {

            return (ToolItem) item;
        }

        return null;

    }

    public boolean isReadOnly() {

        return isReadOnly;
    }

    public void setReadOnly(boolean isReadOnly) {

        this.isReadOnly = isReadOnly;

    	if ( (currentActiveButton & TOOL_ITEM_ADD_BIT) == TOOL_ITEM_ADD_BIT) {
            getToolItem(TOOL_ITEM_ADD).setEnabled(!isReadOnly);
    	}
    	if ( (currentActiveButton & TOOL_ITEM_DELETE_BIT) == TOOL_ITEM_DELETE_BIT) {
            getToolItem(TOOL_ITEM_DELETE).setEnabled(!isReadOnly);
    	}
    	if ( (currentActiveButton & TOOL_ITEM_UP_BIT) == TOOL_ITEM_UP_BIT) {
            getToolItem(TOOL_ITEM_UP).setEnabled(!isReadOnly);
    	}
    	if ( (currentActiveButton & TOOL_ITEM_DOWN_BIT) == TOOL_ITEM_DOWN_BIT) {
            getToolItem(TOOL_ITEM_DOWN).setEnabled(!isReadOnly);
    	}
    }
}
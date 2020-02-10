package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.tibco.be.util.config.ConfigNS.Elements;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Property;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.PropertyElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.PropertyElementList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.PropertyGroup;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Feb 2, 2010 5:40:48 PM
 */

public class PropertyTreeProviderModel implements TreeProviderModel {

	protected ClusterConfigModelMgr modelmgr;
	
	public String columns[] = new String[]{"Name", "Value"};
	public String keys[] = new String[]{"name", "value"}; 
	//public String types[] = new String[]{"string", "integer", "boolean"};

	public PropertyTreeProviderModel(ClusterConfigModelMgr modelmgr) {
		this.modelmgr = modelmgr;
	}
	
	@Override
	public TreeItem addGroupItem(Tree tree, TreeItem parItem) {
		TreeItem item;
		if (parItem == null) {
			item = new TreeItem(tree, SWT.NONE);
		} else {
			if (parItem.getData() instanceof Property) {
				TreeItem gParItem = parItem.getParentItem();
				if (gParItem != null)
					item = new TreeItem(gParItem, SWT.NONE);
				else
					item = new TreeItem(tree, SWT.NONE);
			} else {
				if (resolveItemParent(tree, parItem, true))
					item = new TreeItem(parItem, SWT.NONE);
				else
					item = new TreeItem(tree, SWT.NONE);
			}
		}
		ArrayList<String> curNames = getPropertyNames(tree);
		String newName = PanelUiUtil.generateSequenceId(Elements.PROPERTY_GROUP.localName, curNames);
		item.setText(0, newName);
		item.setImage(StudioUIPlugin.getDefault().getImage("icons/group.png"));
		PropertyGroup propGrp = modelmgr.getModel().new PropertyGroup(newName);
		item.setData(propGrp);
		notifyTreeListener(tree);
		return item;
	}

	@Override
	public TreeItem addItem(Tree tree, TreeItem parItem) {
		TreeItem item;
		if (parItem == null) {
			item = new TreeItem(tree, SWT.NONE);
		} else {
			if (parItem.getData() instanceof Property) {
				TreeItem gParItem = parItem.getParentItem();
				if (gParItem != null)
					item = new TreeItem(gParItem, SWT.NONE);
				else
					item = new TreeItem(tree, SWT.NONE);
			} else {
				if (resolveItemParent(tree, parItem, false))
					item = new TreeItem(parItem, SWT.NONE);
				else
					item = new TreeItem(tree, SWT.NONE);
			}
		}
		ArrayList<String> curNames = getPropertyNames(tree);
		String newName = PanelUiUtil.generateSequenceId(Elements.PROPERTY.localName, curNames);
		item.setText(0, newName);
		//item.setText(2, "string");
		item.setImage(StudioUIPlugin.getDefault().getImage("icons/iconString16.gif"));
		Property prop = modelmgr.getModel().new Property();
		item.setData(prop);
		notifyTreeListener(tree);
		return item;
	}

	@Override
	public void deleteItem(Tree tree, TreeItem item) {
		if (item==null)
			return;
		item.removeAll();
		item.dispose();
		notifyTreeListener(tree);
	}

	@Override
	public void moveDownItem(Tree tree, int index, TreeItem treeItem) {
		if (index == -1)
			return;
		moveItem(tree, index, treeItem , false);
	}

	@Override
	public void moveUpItem(Tree tree, int index, TreeItem treeItem) {		
		if (index == -1)
			return;
		moveItem(tree, index, treeItem , true);
		
	}
	
	private void moveItem(Tree tree, int index, TreeItem treeItem, boolean upOrDown){
		String text, data;
		TreeItem item, newTreeItem;
		
		if(treeItem != null){
			 item = treeItem.getItem(index);

			 text = treeItem.getItem(index).getText(0);
			 data = treeItem.getItem(index).getText(1);
		}
		else{
			 item = tree.getItem(index);

			 text = tree.getItem(index).getText(0);
			 data = tree.getItem(index).getText(1);
		}
		
	
		Map<String, String>childNodes = new LinkedHashMap<String, String>();
		if(item.getItemCount() != 0){						
			/*for (TreeItem childitem: item.getItems()) {
				childNodes.put(childitem.getText(0), childitem.getText(1));
			}*/
			for(int i = item.getItemCount() -1 ; i >= 0; i--){
				childNodes.put(item.getItem(i).getText(0), item.getItem(i).getText(1));
			}
		}
		
		item.removeAll();
		item.dispose();
		
		if(upOrDown){
			if(treeItem != null)
				newTreeItem = new TreeItem(treeItem, SWT.NONE, index - 1);
			else 
				newTreeItem = new TreeItem(tree, SWT.NONE, index - 1);
		}
		else {
			if(treeItem != null)
				newTreeItem = new TreeItem(treeItem, SWT.NONE, index + 1);
			else
				newTreeItem = new TreeItem(tree, SWT.NONE, index + 1);
		}
		
		
		newTreeItem.setText(text);
		newTreeItem.setText(1, data);
		
		newTreeItem.setImage(StudioUIPlugin.getDefault().getImage("icons/iconString16.gif"));
		
	    if(childNodes.entrySet().size() > 0){
	    	newTreeItem.setImage(StudioUIPlugin.getDefault().getImage("icons/group.png"));
	    	PropertyGroup newPropGrp = modelmgr.getModel().new PropertyGroup(text);
	    	
	    	newTreeItem.setData(newPropGrp);
	    	int position = 0;
			for(Map.Entry<String,String> entry : childNodes.entrySet()){
				TreeItem ChildTreeItem = new TreeItem(newTreeItem, position, SWT.None);
				ChildTreeItem.setImage(StudioUIPlugin.getDefault().getImage("icons/iconString16.gif"));
				ChildTreeItem.setText(entry.getKey());
				ChildTreeItem.setText(1, entry.getValue());
				Property prop = modelmgr.getModel().new Property();
				prop.fieldMap.put(entry.getKey(), entry.getValue());
				ChildTreeItem.setData(prop);
				newPropGrp.propertyList.add(prop);
				position = position + 1;
			}
			
	    }
	    else{
	    	Property prop = modelmgr.getModel().new Property();
	    	newTreeItem.setData(prop);
	    }
		
		notifyTreeListener(tree);
		updateModel(tree);
		
	}

	private boolean resolveItemParent(Tree tree, TreeItem item, boolean isGroup) {
		//if (item.getParentItem() == null && tree.getItemCount()==1) {
		String name = "property";
		if (isGroup)
			name = "property group";
		if (item.getParentItem() == null) {
			return MessageDialog.openQuestion(tree.getShell(), "Add " + name, "Add this " + name + " as a child of currently selected property group [" + item.getText(0) + "] ?");
		}
		return true;
	}
	
	private ArrayList<String> getPropertyNames(Tree tree) {
		ArrayList<String> names = new ArrayList<String>();
		for (TreeItem item: tree.getItems()) {
			names.add(item.getText(0));
			ArrayList<String> chdNames = getPropertyNames(item);
			names.addAll(chdNames);
		}
		return names;
	}

	private ArrayList<String> getPropertyNames(TreeItem parItem) {
		ArrayList<String> names = new ArrayList<String>();
		for (TreeItem item: parItem.getItems()) {
			names.add(item.getText(0));
			ArrayList<String> chdNames = getPropertyNames(item);
			names.addAll(chdNames);
		}
		return names;
	}
	
	private void notifyTreeListener(Tree tree) {
		tree.notifyListeners(SWT.Modify, new Event());
	}
		
	public Listener getTreeModifyListener(final Tree tree) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				updateModel(tree);
				setTreeItemIcons(tree);
			}
		};
		return listener;
	}
	
	public void updateModel(Tree tree) {
		PropertyElementList propList = modelmgr.getModel().properties;
		propList.propertyList.clear();
    	for (TreeItem item: tree.getItems()) {
    		PropertyElement propElement = getPropertyElement(item); 
    		if (propElement != null)
    			propList.propertyList.add(propElement);
    	}
		modelmgr.updateProperties(propList);
	}
	
	private void setTreeItemIcons(Tree tree) {
		for (TreeItem item: tree.getItems()) {
			setTreeItemIcons(item);
		}
	}
	
	private void setTreeItemIcons(TreeItem item) {
		setPropertyIconImage(item);
		for (TreeItem chd: item.getItems()) {
			setTreeItemIcons(chd);
		}
	}
	
	private PropertyGroup getPropertyGroup(TreeItem item) {
		PropertyGroup propGrp = (PropertyGroup) item.getData();
		propGrp.name = item.getText(0);
		propGrp.propertyList.clear();
		for (TreeItem chd: item.getItems()) {
    		PropertyElement propElement = getPropertyElement(chd);
			if (propElement != null)
				propGrp.propertyList.add(propElement);
		}
		return propGrp;
	}
	
	protected PropertyElement getPropertyElement(TreeItem item) {
		Object object = item.getData();
		if (object instanceof Property) {
			Property prop = (Property) object;
			prop.fieldMap.put("name", item.getText(0));
			prop.fieldMap.put("value", item.getText(1));
			/*
			String type = item.getText(2);
			prop.fieldMap.put("type", type);
			if (type.equalsIgnoreCase("Boolean"))
				prop.presentation.type = PropertyPresentation.TYPE_BOOLEAN;
			else if (type.equalsIgnoreCase("Integer"))
				prop.presentation.type = PropertyPresentation.TYPE_INT;
			else
				prop.presentation.type = PropertyPresentation.TYPE_STRING;
			*/	
			return prop;
		} else if (object instanceof PropertyGroup) {
			PropertyGroup propGrp = getPropertyGroup(item);
			return propGrp;
		}
		return null;
	}
	
    public void setPropertyIconImage(TreeItem propItem) {
    	propItem.setImage(StudioUIPlugin.getDefault().getImage("icons/iconString16.gif"));
    	/*
    	PropertyPresentation pres = null; //((PropertyElement) propItem.getData()).presentation;
		if (pres != null) {
			if (pres.type.equals(PropertyPresentation.TYPE_BOOLEAN)) {
				propItem.setImage(StudioUIPlugin.getDefault().getImage("icons/iconBoolean16.gif"));	
	    	} else if (pres.type.equals(PropertyPresentation.TYPE_CONNECTION)) {
	    		propItem.setImage(StudioUIPlugin.getDefault().getImage("icons/iconString16.gif"));
	    	} else if (pres.type.equals(PropertyPresentation.TYPE_DOMAIN_OBJ)) {
	    		propItem.setImage(StudioUIPlugin.getDefault().getImage("icons/iconString16.gif"));
	    	} else if (pres.type.equals(PropertyPresentation.TYPE_GROUP)) {
	    		propItem.setImage(StudioUIPlugin.getDefault().getImage("icons/group.png"));
	    	} else if (pres.type.equals(PropertyPresentation.TYPE_INT)) {
	    		propItem.setImage(StudioUIPlugin.getDefault().getImage("icons/iconInteger16.gif"));
	    	} else if (pres.type.equals(PropertyPresentation.TYPE_SELECTION)) {
	    		propItem.setImage(StudioUIPlugin.getDefault().getImage("icons/iconString16.gif"));
	    	} else if (pres.type.equals(PropertyPresentation.TYPE_STRING)) {
	    		propItem.setImage(StudioUIPlugin.getDefault().getImage("icons/iconString16.gif"));
	    	} else {
	    		propItem.setImage(StudioUIPlugin.getDefault().getImage("icons/iconString16.gif"));
	    	}
		} else {
			// catch all
			propItem.setImage(StudioUIPlugin.getDefault().getImage("icons/iconString16.gif"));
		}
		*/
    }
    
    /*
    private boolean isGroup(TreeItem propItem) {
    	PropertyPresentation pres = null; //((PropertyElement) propItem.getData()).presentation;
    	if (pres.type.equals(PropertyPresentation.TYPE_GROUP))
    		return true;
    	return false;
    }
    */
}

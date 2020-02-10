package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.DomainObject;
import com.tibco.cep.studio.ui.util.PanelUiUtil;
import com.tibco.cep.studio.ui.util.TableProviderEditModel;

public class CompIndexTableProviderModel implements TableProviderEditModel {

    private DomainObject domainObj;
    private Composite comp;
    public CompIndexTableProviderModel(Composite comp) {
       this.comp = comp;
    }
    @Override
    public void addItem(Table table) {
        openDialog(table, 0, false);
    }
    @Override
    public void deleteItem(Table table, int index) {
        table.remove(index);
        updateModel(table);
    }
    @Override
    public void moveUpItem(Table table, int index) {
        return;
    }
    @Override
    public void moveDownItem(Table table, int index) {
        return;
    }

    @Override
    public void editItem(Table table, int index) {
        openDialog(table, index, true);
    }
    
	private void openDialog(Table table, int index, boolean isEdit) {
		CompositeIndexSelectionDialog dialog = new CompositeIndexSelectionDialog(this.comp);
		ArrayList<String> propertyArrayList = new ArrayList<String>();
		propertyArrayList = new ArrayList<>(domainObj.props.keySet());

		ArrayList<String> filterList = null;
		TableItem item = null;

		if (isEdit) {
			item = table.getItem(index);
			filterList = getListFromString(item.getText(1));
		} else {
			filterList = new ArrayList<>();
		}
		dialog.open(propertyArrayList, filterList);
		List<String> selectedProps = dialog.getselectedProperties();
		if (selectedProps.size() > 0) {
			if (!isEdit) {
				ArrayList<String> curNames = getIndexNames(table);
				String newName = PanelUiUtil.generateSequenceId("cmpidx", curNames);
				item = new TableItem(table, SWT.NONE);
				item.setText(0, newName);
			}
			item.setText(1, getCommaSeperatedValues(selectedProps));
			updateModel(table);
		}
	}
    
    private ArrayList<String> getIndexNames(Table table) {
        ArrayList<String> names = new ArrayList<String>();
        for (TableItem item: table.getItems()) { 
            names.add(item.getText(0));
        }
        return names;
    }
    
    private void updateModel(Table table) {
        table.notifyListeners(SWT.Modify, new Event());
    }
    
    
    public static String getCommaSeperatedValues(List<String> strs){
        StringBuilder sb = new StringBuilder();
        
        for(String str : strs){
            sb.append(str);
            sb.append(",");
        }
        if(sb.length()>0){
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }
    
    public static ArrayList<String> getListFromString(String str){
        String[] strs = str.split(",");
        ArrayList<String> list = new ArrayList<String>();
        Collections.addAll(list, strs);
        return list;
    }

    public void setDomainObject(DomainObject domainObj) {
        this.domainObj = domainObj;
    }
    
}

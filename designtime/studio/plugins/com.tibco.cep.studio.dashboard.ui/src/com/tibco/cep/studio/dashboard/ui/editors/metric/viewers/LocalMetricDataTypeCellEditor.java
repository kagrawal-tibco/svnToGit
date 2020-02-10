package com.tibco.cep.studio.dashboard.ui.editors.metric.viewers;


import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.studio.dashboard.core.model.impl.attribute.LocalAttribute;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.forms.components.TextAndDialogCellEditor;


public class LocalMetricDataTypeCellEditor extends TextAndDialogCellEditor {

	public LocalMetricDataTypeCellEditor(Composite comp, String columnName, AbstractSaveableEntityEditorPart editor) {
		super(comp,columnName,editor);
	}

    @Override
	protected Object openDialogBox(Control cellEditorWindow) {
		Table table = (Table) cellEditorWindow.getParent();
		TableItem[] selectedProperty = table.getSelection();
		LocalAttribute localAttribute = (LocalAttribute) selectedProperty[0].getData();
		try{
			DataTypeSelector dataTypeSelector = new DataTypeSelector(editor.getSite().getShell(), localAttribute.getSupportedDataTypes(), localAttribute.getPropertyValue(LocalAttribute.PROP_KEY_DATA_TYPE));
			dataTypeSelector.open();
			final String newDataType = (String) dataTypeSelector.getFirstResult();
			if (newDataType != null && newDataType.equals(localAttribute.getPropertyValue(LocalAttribute.PROP_KEY_DATA_TYPE)) == false) {
				return PROPERTY_TYPES.get(newDataType);
			}
		}
		catch(Exception e){
			DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.ERROR, DashboardUIPlugin.PLUGIN_ID, "could not set datatype for " + localAttribute.getName()+" in " + editor.getEditorInput(), e));
		}
		return null;
	}

}

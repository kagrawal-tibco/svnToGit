package com.tibco.cep.studio.dashboard.ui.editors.metric.viewers;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.cep.designtime.core.model.METRIC_AGGR_TYPE;
import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetricField;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.forms.components.TextAndDialogCellEditor;

public class AggregationTypeCellEditor extends TextAndDialogCellEditor {

	public AggregationTypeCellEditor(Composite comp, String columnName, AbstractSaveableEntityEditorPart editor) {
		super(comp, columnName, editor);
	}

	@Override
	protected Object openDialogBox(Control cellEditorWindow) {
		Table table = (Table) cellEditorWindow.getParent();
		TableItem[] selectedProperty = table.getSelection();
		LocalMetricField localMetricField = (LocalMetricField) selectedProperty[0].getData();
		try {
			AggregationTypeSelector aggregationTypeSelector = new AggregationTypeSelector(editor.getSite().getShell(), localMetricField.getSupportedAggregateFuncs(), localMetricField.getAggregationFunction());
			aggregationTypeSelector.open();
			String newAggregationType = (String) aggregationTypeSelector.getFirstResult();
			if (newAggregationType != null && newAggregationType.equals(localMetricField.getAggregationFunction()) == false) {
				return METRIC_AGGR_TYPE.get(newAggregationType);
			}
		} catch (Exception e) {
			ILog log = DashboardUIPlugin.getInstance().getLog();
			log.log(new Status(IStatus.ERROR, DashboardUIPlugin.PLUGIN_ID, "could not edit aggregrate function on " + localMetricField.getName() + " in " + editor.getEditorInput(), e));
		}
		return null;
	}

}

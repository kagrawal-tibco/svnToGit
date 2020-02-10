package com.tibco.cep.studio.dashboard.ui.editors.metric.viewers;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;

import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetric;

public class GroupByCellEditor extends DefaultCellEditor {

	private static final long serialVersionUID = 1465357409076727898L;

	private LocalMetric localMetric;

	public GroupByCellEditor(LocalMetric localMetric) {
		super(new JCheckBox());
		JCheckBox checkBox = (JCheckBox) getComponent();
		checkBox.setHorizontalAlignment(JCheckBox.CENTER);
		this.localMetric = localMetric;
	}

	@Override
	public boolean stopCellEditing() {
		boolean valueChanged = super.stopCellEditing();
		if (valueChanged == true) {
			localMetric.normalizeGroupByPosition();
		}
		return valueChanged;
	}
}

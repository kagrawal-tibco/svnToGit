package com.tibco.cep.studio.dashboard.ui.editors.metric.viewers;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.studio.common.util.EntityNameHelper;
import com.tibco.cep.studio.dashboard.core.model.impl.attribute.LocalAttribute;
import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetric;
import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetricField;
import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetricUserDefinedField;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;

public class FieldNameCellEditor extends DefaultCellEditor {

	private static final long serialVersionUID = -7389952828542767778L;

	private LocalMetric localMetric;
	@SuppressWarnings("unused")
	private JTable table;
	private AbstractSaveableEntityEditorPart editor;

	private JTextField txtField;
	private String oldValue;
	private int row;
	@SuppressWarnings("unused")
	private int column;

	private String particleName;

	public FieldNameCellEditor(LocalMetric localMetric, AbstractSaveableEntityEditorPart editor, String particleName) {
		super(new JTextField());
		txtField = (JTextField) getComponent();
		this.localMetric = localMetric;
		this.editor = editor;
		this.particleName = particleName;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		JTextField ftf = (JTextField) super.getTableCellEditorComponent(table, value, isSelected, row, column);
		this.table = table;
		this.row = row;
		this.column = column;
		oldValue = (String) value;
		return ftf;
	}

	public Object getCellEditorValue() {
		JTextField tf = (JTextField) getComponent();
		Object o = tf.getText();
		return o;
	}

	@Override
	public boolean stopCellEditing() {
		boolean valueChanged = super.stopCellEditing();
		if (valueChanged == true) {
			try {
				JTextField txtComponent = (JTextField) getComponent();
				LocalAttribute localMetricField = null;
				if (this.particleName.equals(LocalMetric.ELEMENT_KEY_FIELD)) {
					localMetricField = (LocalMetricField) localMetric.getField(row);
				} else if (this.particleName.equals(LocalMetric.ELEMENT_KEY_USER_DEFINED_FIELD)) {
					localMetricField = (LocalMetricUserDefinedField) localMetric.getUserDefinedField(row);
				}

				// LocalMetricField localMetricField = (LocalMetricField)localMetric.getFieldByName(txtComponent.getText());
				// check if entered value is not empty string or does not confirm to Java naming standards
				boolean isValidDef = isValidPropertyDefinitionName(txtComponent.getText());
				boolean isNameUnique = localMetric.isNameUnique(particleName, txtComponent.getText());
				boolean isValidMetric = localMetric.isValid();

				if (!isValidDef) {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							MessageDialog.openError(Display.getDefault().getActiveShell(), "Invalid Property", "Invalid Property.");
						}
					});
					txtField.selectAll();
					txtField.setText(oldValue);
					localMetricField.setName(oldValue);
				} else if (!isNameUnique) {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							MessageDialog.openError(Display.getDefault().getActiveShell(), "Duplicate Property", "Duplicate Property.");
						}
					});
					txtField.selectAll();
					txtField.setText(oldValue);
					localMetricField.setName(oldValue);
				} else if (!isValidMetric) {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							MessageDialog.openError(Display.getDefault().getActiveShell(), "Invalid Property", localMetric.getValidationMessage().getMessage());
						}
					});
					txtField.selectAll();
					txtField.setText(oldValue);
					localMetricField.setName(oldValue);
				} else { // validation successful
					oldValue = txtField.getText();
					asyncEditorModified();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return valueChanged;
	}

	private boolean isValidPropertyDefinitionName(String name) {
		try {
			if (name.trim().equalsIgnoreCase("")) {
				return false;
			}
			if (!EntityNameHelper.isValidBEEntityIdentifier(name)) {
				return false; // validating the character
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	private void asyncEditorModified() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				editor.modified();
			}
		});
	}
}

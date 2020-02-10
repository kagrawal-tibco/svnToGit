/**
 * 
 */
package com.tibco.cep.studio.tester.utilities;

import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.studio.tester.core.model.TestDataModel;
import com.tibco.cep.studio.tester.ui.editor.data.TestDataEditor;
import com.tibco.cep.studio.tester.ui.tools.DomainValuesCellDialog;
import com.tibco.cep.studio.ui.StudioUIPlugin;

/**
 * @author mgujrath
 * 
 */
public class DomainComboBoxCellEditor extends DialogCellEditor {

	protected Label textField;
	private Composite parent;
	private String columnType;
	private String columnName;
	TestDataEditor editor;
	TestDataModel model;
	private String[][] domainEntries;

	public static String ICON_TOOLBAR_LIST_BROWSE = "/icons/browse_file_system.gif";
	
	public DomainComboBoxCellEditor(Composite comp, String columnType,
			String columnName, TestDataEditor editor, TestDataModel model,
			String[][] domainEntries) {
		super(comp);
		this.parent = comp;
		this.model = model;
		this.columnType = columnType;
		this.columnName = columnName;
		this.editor = editor;
		this.domainEntries = domainEntries;
	}
	
	protected Button createButton(Composite parent) {
		Button result = new Button(parent, SWT.DOWN);
		result.setImage(StudioUIPlugin.getDefault().getImage(ICON_TOOLBAR_LIST_BROWSE));
		//result.setText("..."); //$NON-NLS-1$
		return result;
	}


	@Override
	protected Object openDialogBox(Control arg0) {
		Shell shell = parent.getShell();
		DomainValuesCellDialog domainValuesCellDialog = new DomainValuesCellDialog(
				editor, shell, columnName, columnType, "Multiple",
				getDialogInitialValue(), domainEntries);
		domainValuesCellDialog.open();
		return domainValuesCellDialog.passValue();
	}

	protected String getDialogInitialValue() {
		Object value = getValue();
		if (value == null) {
			return null;
		} else {
			return value.toString();
		}
	}

}

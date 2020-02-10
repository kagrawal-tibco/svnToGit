package com.tibco.cep.studio.dashboard.ui.editors.metric.viewers;

import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.cep.studio.dashboard.core.model.impl.attribute.LocalAttribute;
import com.tibco.cep.studio.ui.actions.RenameElementAction;

public class NameCellEditor extends DialogCellEditor {

	protected NameCellEditor(Composite parent) {
		super(parent);
	}

	@Override
	protected void updateContents(Object value) {
		String text = "";
		if (value != null) {
			text = ((LocalAttribute) value).getName();
		}
		getDefaultLabel().setText(text);

	}

	@Override
	protected Object openDialogBox(Control cellEditorWindow) {
		LocalAttribute attribute = (LocalAttribute) getValue();
		try {
			RenameElementAction renameElementAction = new RenameElementAction();
			renameElementAction.selectionChanged(null, new StructuredSelection(attribute.getEObject()));
			renameElementAction.run(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return attribute;
	}

}
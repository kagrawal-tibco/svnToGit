package com.tibco.cep.studio.dashboard.ui.forms;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;

public class ComboPropertyControl extends PropertyControl {

	private ComboViewer comboViewer;

	protected ComboPropertyControl(SimplePropertyForm parentForm, String displayName, SynProperty property) {
		super(parentForm, displayName, property);
	}

	@Override
	protected Control doCreateControl(SimplePropertyForm parentForm, Composite parent) {
		comboViewer = new ComboViewer(parent, SWT.READ_ONLY);
		comboViewer.setContentProvider(new PropertyEnumerationContentProvider());
		comboViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if (comboViewer.getSelection().isEmpty() == false) {
					IStructuredSelection section = (IStructuredSelection) comboViewer.getSelection();
					setValue(String.valueOf(section.getFirstElement()));
				}
			}

		});

		return comboViewer.getControl();
	}

	@Override
	protected void refreshEnumerations() {
		comboViewer.setInput(getProperty());
	}

	@Override
	protected void refreshSelection() {
		if (comboViewer != null) {
			comboViewer.setSelection(new StructuredSelection(getValue()), true);
		}
	}

}
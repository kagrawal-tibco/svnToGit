package com.tibco.cep.studio.debug.ui.propertypages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.PropertyPage;

import com.tibco.cep.studio.debug.core.model.IRuleBreakpoint;

public class RuleBreakpointAdvancedPage extends PropertyPage {
	
	ThreadFilterEditor fThreadFilterEditor;
//	InstanceFilterEditor fInstanceFilterEditor;

	/**
	 * @see org.eclipse.jface.preference.IPreferencePage#performOk()
	 */
	public boolean performOk() {
		doStore();
		return super.performOk();
	}

	/**
	 * Stores the values configured in this page.
	 */
	protected void doStore() {
		fThreadFilterEditor.doStore();
//		if (fInstanceFilterEditor != null) {
//			fInstanceFilterEditor.doStore();
//		}
	}

	/**
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createContents(Composite parent) {
		noDefaultAndApplyButton();
		Composite mainComposite = new Composite(parent, SWT.NONE);
		mainComposite.setFont(parent.getFont());
		mainComposite.setLayout(new GridLayout());
		mainComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		createThreadFilterEditor(mainComposite);
		createTypeSpecificEditors(mainComposite);
//		createInstanceFilterEditor(mainComposite);
		setValid(true);
		return mainComposite;
	}
	
//	public void createInstanceFilterEditor(Composite parent) {
//		RuleBreakpoint breakpoint= getBreakpoint();
//		try {
//			IJavaObject[] instances = breakpoint.getInstanceFilters();
//			if (instances.length > 0) {
//				fInstanceFilterEditor= new InstanceFilterEditor(parent, breakpoint);
//			}
//		} catch (CoreException e) {
//			StudioDebugUIPlugin.log(e);
//		}
//	}

	/**
	 * Allow subclasses to create type-specific editors.
	 * @param parent
	 */
	protected void createTypeSpecificEditors(Composite parent) {
		// Do nothing.
	}

	protected void createThreadFilterEditor(Composite parent) {
		fThreadFilterEditor = new ThreadFilterEditor(parent, this);
	}

	public IRuleBreakpoint getBreakpoint() {
		return (IRuleBreakpoint) getElement();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		super.createControl(parent);
//		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), IJavaDebugHelpContextIds.JAVA_BREAKPOINT_ADVANCED_PROPERTY_PAGE);
	}

}

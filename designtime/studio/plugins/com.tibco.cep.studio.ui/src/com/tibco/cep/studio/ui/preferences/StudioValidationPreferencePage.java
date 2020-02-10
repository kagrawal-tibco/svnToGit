package com.tibco.cep.studio.ui.preferences;

import java.util.Collection;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.preferences.StudioCorePreferenceConstants;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.validation.IResourceValidator;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.actions.ValidateProjectAction;
import com.tibco.cep.studio.ui.actions.ValidateProjectAction.ResourceVisitor;
import com.tibco.cep.studio.ui.util.Messages;


/**
 * 
 * @author sasahoo
 *
 */
public class StudioValidationPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage, SelectionListener {

	private ShowWarningEditor editor;
	private boolean oldWarning; 
	
	private Table ignoreTable;
	private Button selectAllCheck;
	private Button showWarningButton;
	private String ignoreValues;
	private Set<String> ignoreExtensions;
	
	public StudioValidationPreferencePage() {
		super(GRID);
		setPreferenceStore(StudioUIPlugin.getDefault().getPreferenceStore());
	}

	public void init(IWorkbench workbench) {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	@Override
	protected void createFieldEditors() {
		Composite parent = getFieldEditorParent();
		parent.setLayout(new GridLayout());
		parent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		editor = new ShowWarningEditor(StudioUIPreferenceConstants.STUDIO_SHOW_WARNINGS, Messages.getString("studio.show.warning"),parent);
		addField(editor);
		showWarningButton = editor.getButton(parent);
		showWarningButton.addSelectionListener(this);
		
		oldWarning = getPreferenceStore().getBoolean(StudioUIPreferenceConstants.STUDIO_SHOW_WARNINGS);
		ignoreValues = getPreferenceStore().getString(StudioUIPreferenceConstants.STUDIO_SHOW_WARNINGS_IGNORE_PATTERNS);
		ignoreExtensions = CommonUtil.getUpdatedExtension(ignoreValues);
		
		GridData data = new GridData();
		Label l1 = new Label(parent, SWT.NULL);
		l1.setText(Messages.getString("pref.valid.include.patterns")); 
		data = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		data.horizontalSpan = 2;
		l1.setLayoutData(data);
		
		ignoreTable = new Table(parent, SWT.CHECK | SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_BOTH);
		//gd.widthHint = convertWidthInCharsToPixels(30);
		gd.heightHint = 300;
		ignoreTable.setLayoutData(gd);
		selectAllCheck = new Button(parent, SWT.CHECK);
		selectAllCheck.setText(Messages.getString("pref.valid.select.all.patterns"));
		fillTable(CommonUtil.getAllExtensions());
		selectAllCheck.addSelectionListener(this);
		Dialog.applyDialogFont(parent);
	}
	
	@Override
	protected void performDefaults() {
		super.performDefaults();
		ignoreValues = getPreferenceStore().getString(StudioUIPreferenceConstants.STUDIO_SHOW_WARNINGS_IGNORE_PATTERNS);
		ignoreExtensions = CommonUtil.getUpdatedExtension(ignoreValues);

		for (TableItem item: ignoreTable.getItems()) {
//			item.setChecked(ignoreExtensions.contains(itemString));
			item.setChecked(true);
		}
		//if (ignoreTable.getItemCount() == ignoreExtensions.size()) {
			selectAllCheck.setSelection(true);
		//}
	}

	/**
	 * @param set
	 */
	private void fillTable(Collection<String> set) {
		for (String ext: set) {
			final TableItem item = new TableItem(ignoreTable, SWT.NONE);
			item.setText("*." + ext);
			item.setChecked(ignoreExtensions.contains(ext));
		}		
		if (ignoreTable.getItemCount() == ignoreExtensions.size()) {
			selectAllCheck.setSelection(true);
		}
		ignoreTable.setEnabled(oldWarning);
		selectAllCheck.setEnabled(oldWarning);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#performOk()
	 */
	public boolean performOk() {
		super.performOk();

		for (TableItem item : ignoreTable.getItems()) {
			String iStr = item.getText();
			iStr = iStr.replace("*.", "");
			if (item.getChecked()) {
				ignoreExtensions.add(iStr);
			} else {
				ignoreExtensions.remove(iStr);
			}
		}
		String value = CommonUtil.getUpdatedValues(ignoreExtensions);
		getPreferenceStore().setValue(StudioUIPreferenceConstants.STUDIO_SHOW_WARNINGS_IGNORE_PATTERNS, value);
		StudioCorePlugin.getDefault().getPluginPreferences().setValue(StudioCorePreferenceConstants.STUDIO_SHOW_WARNINGS_PATTERNS, value);
	
		boolean warning = editor.getBooleanValue();
		StudioCorePlugin.getDefault().getPluginPreferences().setValue(StudioCorePreferenceConstants.STUDIO_SHOW_WARNINGS, warning);
		if (oldWarning != warning) {
			buildStudioProjects();
		}
		return true;
	}
	
	public void buildStudioProjects() {
		if (MessageDialog.openQuestion(getShell(), Messages.getString("project.build.show.validation.title"), 
				Messages.getString("project.build.show.validation.desc"))) {
			Job buildJob = new Job("Build project") {
				@Override
				protected IStatus run(final IProgressMonitor monitor) {
					monitor.beginTask("Validating project", IProgressMonitor.UNKNOWN);
					IProject[] projects = CommonUtil.getAllStudioProjects();
					for (final IProject project : projects) {
						try {
							validate(project, new SubProgressMonitor(monitor, 100));
						} catch (CoreException e) {
							if (e.getStatus().getSeverity() == Status.CANCEL) {
								return Status.CANCEL_STATUS;
							} else {
								StudioUIPlugin.log(e);
							}
						}
					}
					return Status.OK_STATUS;
				}
			};
			buildJob.schedule();	
		}
	}
	
	/**
	 * Validate project 
	 * @param project
	 * @param monitor
	 * @return
	 * @throws CoreException
	 */
	public boolean validate(final IProject project,IProgressMonitor monitor) throws CoreException {
		monitor.beginTask(Messages.getString("Build.EAR.Validation.task",project.getName()), 1);
		if (monitor.isCanceled()) {
			throw new CoreException(Status.CANCEL_STATUS);
		}
		SubProgressMonitor subMon = new SubProgressMonitor(monitor, 1);
		ValidateProjectAction.ResourceVisitor visitor = new ResourceVisitor(subMon);
		try {
			// clear all existing validation errors
			project.deleteMarkers(IResourceValidator.VALIDATION_MARKER_TYPE, true, IResource.DEPTH_INFINITE);
			visitor.setCountOnly(true);
			project.accept(visitor);
			subMon.beginTask(Messages.getString("Build.EAR.Validation.task", project.getName()), 1);
			visitor.setCountOnly(false);
			project.accept(visitor);
		} catch (CoreException e) {
			if (e.getStatus().getSeverity() == Status.CANCEL) {
				throw new CoreException(Status.CANCEL_STATUS);
			} else {
				StudioUIPlugin.log(e);
			}
		}
		return true;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		if (e.getSource() ==  showWarningButton) {
			ignoreTable.setEnabled(showWarningButton.getSelection());
			selectAllCheck.setEnabled(showWarningButton.getSelection());
		}
		if (e.getSource() == selectAllCheck) {
			for (TableItem item: ignoreTable.getItems()) {
				item.setChecked(selectAllCheck.getSelection());
			}
		}
	}
	
	/**
	 * This custom preference field Editor allows to access the Check button
	 * Hence allows to enable/disable Ignore Table and Select/Deselect All Button 
	 * @author sasahoo
	 *
	 */
	protected class ShowWarningEditor extends BooleanFieldEditor {
		
		/**
		 * @param name
		 * @param labelText
		 * @param parent
		 */
		public ShowWarningEditor(String name, String labelText, Composite parent) {
			super(name, labelText, parent);
		}

		  
		/**
		 * @param parent
		 * @return
		 */
		public Button getButton(Composite parent) {
			return getChangeControl(parent);
		}
	}
}
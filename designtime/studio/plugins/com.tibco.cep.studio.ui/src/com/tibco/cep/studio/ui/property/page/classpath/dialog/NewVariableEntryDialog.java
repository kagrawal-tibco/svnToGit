package com.tibco.cep.studio.ui.property.page.classpath.dialog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.PreferencesUtil;

import com.tibco.cep.studio.core.StudioCore;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.preferences.StudioClasspathVariablesPreferencePage;
import com.tibco.cep.studio.ui.preferences.classpathvar.ClasspathVariableElement;
import com.tibco.cep.studio.ui.preferences.classpathvar.ClasspathVariableElementLabelProvider;
import com.tibco.cep.studio.ui.preferences.classpathvar.IVariableDialogFieldListener;
import com.tibco.cep.studio.ui.preferences.classpathvar.IVariableListAdapter;
import com.tibco.cep.studio.ui.preferences.classpathvar.VariableDialogField;
import com.tibco.cep.studio.ui.preferences.classpathvar.VariableListDialogField;
import com.tibco.cep.studio.ui.preferences.classpathvar.VariableSelectButtonDialogField;
import com.tibco.cep.studio.ui.property.page.classpath.VariableStatusInfo;
import com.tibco.cep.studio.ui.util.ClasspathVariableUiUtils;
import com.tibco.cep.studio.ui.util.Messages;


public class NewVariableEntryDialog extends StatusDialog {

	private class VariablesAdapter implements IVariableDialogFieldListener, IVariableListAdapter<ClasspathVariableElement> {

		// -------- IListAdapter --------

		public void customButtonPressed(VariableListDialogField<ClasspathVariableElement> field, int index) {
			switch (index) {
			case IDX_EXTEND: /* extend */
				extendButtonPressed();
				break;
			}
		}

		public void selectionChanged(VariableListDialogField<ClasspathVariableElement> field) {
			doSelectionChanged();
		}

		public void doubleClicked(VariableListDialogField<ClasspathVariableElement> field) {
			doDoubleClick();
		}

		// ---------- IDialogFieldListener --------

		public void dialogFieldChanged(VariableDialogField field) {
			if (field == fConfigButton) {
				configButtonPressed();
			}

		}

	}

	private final int IDX_EXTEND= 0;

	private VariableListDialogField<ClasspathVariableElement> fVariablesList;
	private boolean fCanExtend;
	private boolean fIsValidSelection;

	private IPath[] fResultPaths;

	private VariableSelectButtonDialogField fConfigButton;

	private CLabel fWarning;
	
	private boolean projectLib;

	public NewVariableEntryDialog(Shell parent, boolean projectLib) {
		super(parent);
		setTitle(Messages.getString("NewVariableEntryDialog_title"));
		
		this.projectLib = projectLib;
		
		updateStatus(new VariableStatusInfo(IStatus.ERROR, "")); //$NON-NLS-1$

		String[] buttonLabels= new String[] {
				Messages.getString("NewVariableEntryDialog_vars_extend"),
		};

		VariablesAdapter adapter= new VariablesAdapter();

		ClasspathVariableElementLabelProvider labelProvider= new ClasspathVariableElementLabelProvider(false);

		fVariablesList= new VariableListDialogField<ClasspathVariableElement>(adapter, buttonLabels, labelProvider);
		fVariablesList.setDialogFieldListener(adapter);
		fVariablesList.setLabelText(Messages.getString("NewVariableEntryDialog_vars_label"));

		fVariablesList.enableButton(IDX_EXTEND, false);

		fVariablesList.setViewerComparator(new ViewerComparator() {
			@SuppressWarnings("unchecked")
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				if (e1 instanceof ClasspathVariableElement && e2 instanceof ClasspathVariableElement) {
					return getComparator().compare(((ClasspathVariableElement)e1).getName(), ((ClasspathVariableElement)e2).getName());
				}
				return super.compare(viewer, e1, e2);
			}
		});


		fConfigButton= new VariableSelectButtonDialogField(SWT.PUSH);
		fConfigButton.setLabelText(Messages.getString("NewVariableEntryDialog_configbutton_label"));
		fConfigButton.setDialogFieldListener(adapter);

		initializeElements();

		fCanExtend= false;
		fIsValidSelection= false;
		fResultPaths= null;

		fVariablesList.selectFirstElement();
	}

	/*
	 * @see org.eclipse.jface.dialogs.Dialog#isResizable()
	 * @since 3.4
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}

	private void initializeElements() {
		String[] entries= StudioCore.getPathVariableNames();
		ArrayList<ClasspathVariableElement> elements= new ArrayList<ClasspathVariableElement>(entries.length);
		for (int i= 0; i < entries.length; i++) {
			String name= entries[i];
			IPath entryPath= StudioCore.getClasspathVariable(name);
			if (entryPath != null) {
				String val = System.getProperty(name);
				if(val == null) {
					System.setProperty(name, entryPath.toPortableString());
				}
				elements.add(new ClasspathVariableElement(name, entryPath));
			}
		}

		fVariablesList.setElements(elements);
	}


	/* (non-Javadoc)
	 * @see Window#configureShell(Shell)
	 */
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
//		PlatformUI.getWorkbench().getHelpSystem().setHelp(shell, IJavaHelpContextIds.NEW_VARIABLE_ENTRY_DIALOG);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#getDialogBoundsSettings()
	 */
	@Override
	protected IDialogSettings getDialogBoundsSettings() {
		return StudioUIPlugin.getDefault().getDialogSettingsSection(getClass().getName());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		initializeDialogUnits(parent);

		Composite composite= (Composite) super.createDialogArea(parent);
		GridLayout layout= (GridLayout) composite.getLayout();
		layout.numColumns= 2;

		fVariablesList.doFillIntoGrid(composite, 3);

		ClasspathVariableUiUtils.setHorizontalSpan(fVariablesList.getLabelControl(null), 2);

		GridData listData= (GridData) fVariablesList.getListControl(null).getLayoutData();
		listData.grabExcessHorizontalSpace= true;
		listData.heightHint= convertHeightInCharsToPixels(10);
		listData.widthHint= convertWidthInCharsToPixels(70);

		fWarning= new CLabel(composite, SWT.NONE);
		fWarning.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, fVariablesList.getNumberOfControls() - 1, 1));

		Composite lowerComposite= new Composite(composite, SWT.NONE);
		lowerComposite.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));

		layout= new GridLayout();
		layout.marginHeight= 0;
		layout.marginWidth= 0;
		lowerComposite.setLayout(layout);

		fConfigButton.doFillIntoGrid(lowerComposite, 1);

		applyDialogFont(composite);
		return composite;
	}

	public IPath[] getResult() {
		return fResultPaths;
	}

	/*
 	 * @see IDoubleClickListener#doubleClick(DoubleClickEvent)
 	 */
	private void doDoubleClick() {
		if (fIsValidSelection) {
			okPressed();
		} else if (fCanExtend) {
			extendButtonPressed();
		}
	}

	private void doSelectionChanged() {
		boolean isValidSelection= true;
		boolean canExtend= false;
		VariableStatusInfo status= new VariableStatusInfo();

		List<ClasspathVariableElement> selected= fVariablesList.getSelectedElements();
		int nSelected= selected.size();

		if (nSelected > 0) {
			fResultPaths= new Path[nSelected];
			for (int i= 0; i < nSelected; i++) {
				ClasspathVariableElement curr= selected.get(i);
				fResultPaths[i]= new Path(curr.getName());
				File file= curr.getPath().toFile();
				if (!file.exists()) {
					status.setError(Messages.getString("NewVariableEntryDialog_info_notexists"));
					isValidSelection= false;
					break;
				}
				if (file.isDirectory()) {
					canExtend= true;
				}
			}
		} else {
			isValidSelection= false;
			status.setInfo(Messages.getString("NewVariableEntryDialog_info_noselection"));
		}
		if (isValidSelection && nSelected > 1) {
			String str= Messages.getString("NewVariableEntryDialog_info_selected", String.valueOf(nSelected));
			status.setInfo(str);
		}
		fCanExtend= nSelected == 1 && canExtend;
		fVariablesList.enableButton(0, fCanExtend);

		updateStatus(status);
		fIsValidSelection= isValidSelection;
		Button okButton= getButton(IDialogConstants.OK_ID);
		if (okButton != null  && !okButton.isDisposed()) {
			okButton.setEnabled(isValidSelection);
		}
		updateDeprecationWarning();
	}

	private void updateDeprecationWarning() {
		if (fWarning == null || fWarning.isDisposed())
			return;

		for (Iterator<ClasspathVariableElement> iter= fVariablesList.getSelectedElements().iterator(); iter.hasNext();) {
			ClasspathVariableElement element= iter.next();
			String deprecationMessage= element.getDeprecationMessage();
			if (deprecationMessage != null) {
				fWarning.setText(deprecationMessage);
				fWarning.setImage(JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_WARNING));
				return;
			}
		}
		fWarning.setText(null);
		fWarning.setImage(null);
	}

	private IPath[] chooseExtensions(ClasspathVariableElement elem) {
		File file= elem.getPath().toFile();

		ArchiveFileSelectionDialog dialog= new ArchiveFileSelectionDialog(getShell(), true, true, true, projectLib);
		dialog.setTitle(Messages.getString("NewVariableEntryDialog_ExtensionDialog_title"));
		dialog.setMessage(Messages.getString("NewVariableEntryDialog_ExtensionDialog_description", elem.getName()));
		dialog.setInput(file);
		if (dialog.open() == Window.OK) {
			Object[] selected= dialog.getResult();
			IPath[] paths= new IPath[selected.length];
			for (int i= 0; i < selected.length; i++) {
				IPath filePath= Path.fromOSString(((File) selected[i]).getPath());
				IPath resPath=  new Path(elem.getName());
				for (int k= elem.getPath().segmentCount(); k < filePath.segmentCount(); k++) {
					resPath= resPath.append(filePath.segment(k));
				}
				paths[i]= resPath;
			}
			return paths;
		}
		return null;
	}

	protected final void extendButtonPressed() {
		List<ClasspathVariableElement> selected= fVariablesList.getSelectedElements();
		if (selected.size() == 1) {
			IPath[] extendedPaths= chooseExtensions(selected.get(0));
			if (extendedPaths != null) {
				fResultPaths= extendedPaths;
				super.buttonPressed(IDialogConstants.OK_ID);
			}
		}
	}

	protected final void configButtonPressed() {
		String id= StudioClasspathVariablesPreferencePage.ID;
		Map<String, String> options= new HashMap<String, String>();
		List<ClasspathVariableElement> selected= fVariablesList.getSelectedElements();
		if (!selected.isEmpty()) {
			String varName= selected.get(0).getName();
			options.put(StudioClasspathVariablesPreferencePage.DATA_SELECT_VARIABLE, varName);
		}
		PreferencesUtil.createPreferenceDialogOn(getShell(), id, new String[] { id }, options).open();

		List<ClasspathVariableElement> oldElements= fVariablesList.getElements();
		initializeElements();
		List<ClasspathVariableElement> newElements= fVariablesList.getElements();
		newElements.removeAll(oldElements);
		if (!newElements.isEmpty()) {
			fVariablesList.selectElements(new StructuredSelection(newElements));
		} else if (fVariablesList.getSelectedElements().isEmpty()) {
			fVariablesList.selectFirstElement();
		}
	}

}

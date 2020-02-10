package com.tibco.cep.studio.ui.preferences.classpathvar;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.common.configuration.CustomFunctionLibEntry;
import com.tibco.cep.studio.common.configuration.ProjectLibraryEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.ThirdPartyLibEntry;
import com.tibco.cep.studio.core.StudioCore;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.ClasspathVariableUiUtils;
import com.tibco.cep.studio.ui.util.Messages;


public class VariableBlock {
	
	private final VariableListDialogField<ClasspathVariableElement> fVariablesList;
	private Control fControl;
	private CLabel fWarning;
	private boolean fHasChanges;

	private List<ClasspathVariableElement> fSelectedElements;
	private boolean fAskToBuild;
	private final boolean fEditOnDoubleclick;

	public VariableBlock(boolean inPreferencePage, String initSelection) {

		fSelectedElements= new ArrayList<ClasspathVariableElement>(0);
		fEditOnDoubleclick= inPreferencePage;
		fAskToBuild= true;

		String[] buttonLabels= new String[] {
											"Add",
											"Edit",
											"Remove"
		};

		VariablesAdapter adapter= new VariablesAdapter();

		ClasspathVariableElementLabelProvider labelProvider= new ClasspathVariableElementLabelProvider(inPreferencePage);

		fVariablesList= new VariableListDialogField<ClasspathVariableElement>(adapter, buttonLabels, labelProvider);
		fVariablesList.setDialogFieldListener(adapter);
		fVariablesList.setLabelText("Defined &classpath variables:");
		fVariablesList.setRemoveButtonIndex(2);

		fVariablesList.enableButton(1, false);

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
		refresh(initSelection);
	}

	public boolean hasChanges() {
		return fHasChanges;
	}

	public void setChanges(boolean hasChanges) {
		fHasChanges= hasChanges;
	}

	public Control createContents(Composite parent) {
		Composite composite= new Composite(parent, SWT.NONE);
		composite.setFont(parent.getFont());

		ClasspathVariableUiUtils.doDefaultLayout(composite, new VariableDialogField[] { fVariablesList }, true, 0, 0);
		ClasspathVariableUiUtils.setHorizontalGrabbing(fVariablesList.getListControl(null));

		fWarning= new CLabel(composite, SWT.NONE);
		fWarning.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, fVariablesList.getNumberOfControls() - 1, 1));

		fControl= composite;
		updateDeprecationWarning();

		return composite;
	}

	public void addDoubleClickListener(IDoubleClickListener listener) {
		fVariablesList.getTableViewer().addDoubleClickListener(listener);
	}

	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		fVariablesList.getTableViewer().addSelectionChangedListener(listener);
	}


	private Shell getShell() {
		if (fControl != null) {
			return fControl.getShell();
		}
		return StudioUIPlugin.getShell();
	}

	private class VariablesAdapter implements IVariableDialogFieldListener, IVariableListAdapter<ClasspathVariableElement> {

		// -------- IListAdapter --------

		public void customButtonPressed(VariableListDialogField<ClasspathVariableElement> field, int index) {
			switch (index) {
			case 0: /* add */
				editEntries(null);
				break;
			case 1: /* edit */
				List<ClasspathVariableElement> selected= field.getSelectedElements();
				editEntries(selected.get(0));
				break;
			}
		}

		public void selectionChanged(VariableListDialogField<ClasspathVariableElement> field) {
			doSelectionChanged(field);
		}

		public void doubleClicked(VariableListDialogField<ClasspathVariableElement> field) {
			if (fEditOnDoubleclick) {
				List<ClasspathVariableElement> selected= field.getSelectedElements();
				if (canEdit(selected, containsReadOnly(selected))) {
					editEntries(selected.get(0));
				}
			}
		}

		// ---------- IDialogFieldListener --------

		public void dialogFieldChanged(VariableDialogField field) {
		}

	}

	private boolean containsReadOnly(List<ClasspathVariableElement> selected) {
		for (int i= selected.size()-1; i >= 0; i--) {
			if (selected.get(i).isReadOnly()) {
				return true;
			}
		}
		return false;
	}

	private boolean canEdit(List<ClasspathVariableElement> selected, boolean containsReadOnly) {
		return selected.size() == 1 && !containsReadOnly;
	}

	/**
	 * @param field the dialog field
	 */
	private void doSelectionChanged(VariableDialogField field) {
		List<ClasspathVariableElement> selected= fVariablesList.getSelectedElements();
		boolean containsReadOnly= containsReadOnly(selected);

		// edit
		fVariablesList.enableButton(1, canEdit(selected, containsReadOnly));
		// remove button
		fVariablesList.enableButton(2, !containsReadOnly);

		fSelectedElements= selected;
		updateDeprecationWarning();
	}

	private void updateDeprecationWarning() {
		if (fWarning == null || fWarning.isDisposed())
			return;

		for (Iterator<ClasspathVariableElement> iter= fSelectedElements.iterator(); iter.hasNext();) {
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

	private void editEntries(ClasspathVariableElement entry) {
		List<ClasspathVariableElement> existingEntries= fVariablesList.getElements();

		VariableCreationDialog dialog= new VariableCreationDialog(getShell(), entry, existingEntries);
		if (dialog.open() != Window.OK) {
			return;
		}
		ClasspathVariableElement newEntry= dialog.getClasspathElement();
		if (entry == null) {
			fVariablesList.addElement(newEntry);
			entry= newEntry;
			fHasChanges= true;
		} else {
			boolean hasChanges= !(entry.getName().equals(newEntry.getName()) && entry.getPath().equals(newEntry.getPath()));
			if (hasChanges) {
				fHasChanges= true;
				entry.setName(newEntry.getName());
				entry.setPath(newEntry.getPath());
				fVariablesList.refresh();
			}
		}
		fVariablesList.selectElements(new StructuredSelection(entry));
	}

	public List<ClasspathVariableElement> getSelectedElements() {
		return fSelectedElements;
	}

	public boolean performOk() {
		ArrayList<String> removedVariables= new ArrayList<String>();
		ArrayList<String> changedVariables= new ArrayList<String>();
		removedVariables.addAll(Arrays.asList(StudioCore.getPathVariableNames()));

		// remove all unchanged
		List<ClasspathVariableElement> changedElements= fVariablesList.getElements();
		for (int i= changedElements.size()-1; i >= 0; i--) {
			ClasspathVariableElement curr= changedElements.get(i);
			if (curr.isReadOnly()) {
				changedElements.remove(curr);
			} else {
				IPath path= curr.getPath();
				IPath prevPath= StudioCore.getClasspathVariable(curr.getName());
				if (prevPath != null && prevPath.equals(path)) {
					changedElements.remove(curr);
				} else {
					changedVariables.add(curr.getName());
				}
			}
			removedVariables.remove(curr.getName());
		}
		int steps= changedElements.size() + removedVariables.size();
		if (steps > 0) {

			boolean needsBuild= false;
			if (fAskToBuild && doesChangeRequireFullBuild(removedVariables, changedVariables)) {
				String title= Messages.getString("VariableBlock_needsbuild_title");
				String message= Messages.getString("VariableBlock_needsbuild_message");

				MessageDialog buildDialog= new MessageDialog(getShell(), title, null, message, MessageDialog.QUESTION, new String[] { IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL, IDialogConstants.CANCEL_LABEL }, 2);
				int res= buildDialog.open();
				if (res != 0 && res != 1) {
					return false;
				}
				needsBuild= (res == 0);
			}

			final VariableBlockRunnable runnable= new VariableBlockRunnable(removedVariables, changedElements);
			final ProgressMonitorDialog dialog= new ProgressMonitorDialog(getShell());
			try {
				PlatformUI.getWorkbench().getProgressService().runInUI(dialog, runnable, ResourcesPlugin.getWorkspace().getRoot());
			} catch (InvocationTargetException e) {
				StudioUIPlugin.handle(new InvocationTargetException(new NullPointerException()), getShell(), Messages.getString("VariableBlock_variableSettingError_titel"), 
						Messages.getString("VariableBlock_variableSettingError_message"));
				return false;
			} catch (InterruptedException e) {
				return false;
			}

			if (needsBuild) {
				ClasspathVariableUiUtils.getBuildJob(null).schedule();
			}
		}
		return true;
	}

	private boolean doesChangeRequireFullBuild(List<String> removed, List<String> changed) {
		IProject[] studioProjects = CommonUtil.getAllStudioProjects();
		Set<IProject> affectedProjectClasspaths = new HashSet<IProject>();
		List<String> variableNames = new ArrayList<String>();
		variableNames.addAll(removed);
		variableNames.addAll(changed);
		for (int i = 0, projectLength = studioProjects.length; i < projectLength; i++){
			// check to see if any of the modified variables is present on the classpath
			StudioProjectConfigurationManager manager = StudioProjectConfigurationManager.getInstance();
			StudioProjectConfiguration config = manager.getProjectConfiguration(studioProjects[i].getName());
			EList<ProjectLibraryEntry> projLibs = config.getProjectLibEntries();
			EList<ThirdPartyLibEntry> tpLibs = config.getThirdpartyLibEntries();
			EList<CustomFunctionLibEntry> cfLibs = config.getCustomFunctionLibEntries();
			
			for (String variableName: variableNames ) {
				for (ProjectLibraryEntry var : projLibs) {
					IPath vPath = new Path(var.getPath());
					if (var.isVar()
							&& variableName.equals(vPath.segment(0))) {
						affectedProjectClasspaths.add(studioProjects[i]);
					}
				}
				for (ThirdPartyLibEntry var : tpLibs) {
					IPath vPath = new Path(var.getPath());
					if (var.isVar()
							&& variableName.equals(vPath.segment(0))) {
						affectedProjectClasspaths.add(studioProjects[i]);
					}
				}
				for (CustomFunctionLibEntry var : cfLibs) {
					IPath vPath = new Path(var.getPath());
					if (var.isVar()
							&& variableName.equals(vPath.segment(0))) {
						affectedProjectClasspaths.add(studioProjects[i]);
					}
				}
			
			} // end vars
		} // end project

		return !affectedProjectClasspaths.isEmpty();
	}

	private class VariableBlockRunnable implements IRunnableWithProgress {
		private final List<String> fToRemove;
		private final List<ClasspathVariableElement> fToChange;

		public VariableBlockRunnable(List<String> toRemove, List<ClasspathVariableElement> toChange) {
			fToRemove= toRemove;
			fToChange= toChange;
		}

		/*
	 	 * @see IRunnableWithProgress#run(IProgressMonitor)
		 */
		public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
			monitor.beginTask(Messages.getString("VariableBlock_operation_desc"), 1);
			try {
				setVariables(monitor);

			} catch (CoreException e) {
				throw new InvocationTargetException(e);
			} catch (OperationCanceledException e) {
				throw new InterruptedException();
			} finally {
				monitor.done();
			}
		}

		public void setVariables(IProgressMonitor monitor) throws CoreException {
			int nVariables= fToChange.size() + fToRemove.size();

			String[] names= new String[nVariables];
			IPath[] paths= new IPath[nVariables];
			int k= 0;

			for (int i= 0; i < fToChange.size(); i++) {
				ClasspathVariableElement curr= fToChange.get(i);
				names[k]= curr.getName();
				paths[k]= curr.getPath();
				k++;
			}
			for (int i= 0; i < fToRemove.size(); i++) {
				names[k]= fToRemove.get(i);
				paths[k]= null;
				k++;
			}
			StudioCore.setPathVariables(names, paths, new SubProgressMonitor(monitor, 1));
		}
	}

	/**
	 * If set to true, a dialog will ask the user to build on variable changed
	 * @param askToBuild The askToBuild to set
	 */
	public void setAskToBuild(boolean askToBuild) {
		fAskToBuild= askToBuild;
	}

	/**
	 * @param initSelection the initial selection
	 */
	public void refresh(String initSelection) {
		ClasspathVariableElement initSelectedElement= null;

		String[] entries= StudioCore.getPathVariableNames();
		ArrayList<ClasspathVariableElement> elements= new ArrayList<ClasspathVariableElement>(entries.length);
		for (int i= 0; i < entries.length; i++) {
			String name= entries[i];
			ClasspathVariableElement elem;
			IPath entryPath= StudioCore.getClasspathVariable(name);
			if (entryPath != null) {
				elem= new ClasspathVariableElement(name, entryPath);
				elements.add(elem);
				if (name.equals(initSelection)) {
					initSelectedElement= elem;
				}
			}
		}

		fVariablesList.setElements(elements);

		if (initSelectedElement != null) {
			ISelection sel= new StructuredSelection(initSelectedElement);
			fVariablesList.selectElements(sel);
		} else {
			fVariablesList.selectFirstElement();
		}

		fHasChanges= false;
	}

	public void setSelection(String elementName) {
		for (int i= 0; i < fVariablesList.getSize(); i++) {
			ClasspathVariableElement elem= fVariablesList.getElement(i);
			if (elem.getName().equals(elementName)) {
				fVariablesList.selectElements(new StructuredSelection(elem));
				return;
			}
		}
	}


}

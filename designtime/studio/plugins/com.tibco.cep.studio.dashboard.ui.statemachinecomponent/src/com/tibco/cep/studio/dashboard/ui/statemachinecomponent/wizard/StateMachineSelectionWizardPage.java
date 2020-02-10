package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.wizard;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.StateMachineElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDataSource;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.DashboardStateMachineComponentPlugin;
import com.tibco.cep.studio.dashboard.ui.wizards.DashboardEntityFileCreationWizard;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;
import com.tibco.cep.studio.ui.dialog.StudioResourceSelectionDialog;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

public class StateMachineSelectionWizardPage extends DashboardEntityFileCreationWizard {

	private Text txt_StateMachineFullPath;
	private Button stateMachineBrowseButton;

	private StateMachine stateMachine;

	private LocalDataSource defaultDataSource;

	public StateMachineSelectionWizardPage(String pageName, IStructuredSelection selection, String type, String typeName) {
		super(pageName, selection, type, typeName);
	}

	@Override
	protected void createTypeDescControl(Composite parent) {
		super.createTypeDescControl(parent);
		createLabel(parent, "State Model");

		Composite stateMachineBrowserComposite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;

		stateMachineBrowserComposite.setLayout(layout);
		stateMachineBrowserComposite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));

		txt_StateMachineFullPath = createText(stateMachineBrowserComposite);
		txt_StateMachineFullPath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		txt_StateMachineFullPath.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				validatePage();
			}

		});

		stateMachineBrowseButton = new Button(stateMachineBrowserComposite, SWT.NONE);
		stateMachineBrowseButton.setText(Messages.getString("Browse"));
		stateMachineBrowseButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (getProject() == null) {
					IResource resource = StudioResourceUtils.getResourcePathFromContainerPath(getContainerFullPath());
					if (resource instanceof IProject) {
						setProject((IProject) resource);
					} else {
						setProject(resource.getProject());
					}
				}
				StudioResourceSelectionDialog selectionDialog = new StudioResourceSelectionDialog(getShell(), getProject().getName(), null, ELEMENT_TYPES.STATE_MACHINE);
				int btnClicked = selectionDialog.open();
				if (btnClicked == StudioResourceSelectionDialog.OK) {
					IFile firstResult = (IFile) selectionDialog.getFirstResult();
					String path = firstResult.getProjectRelativePath().toString();
					path = path.replace(".statemachine", "");
					txt_StateMachineFullPath.setText("/" + path);
				}
			}

		});

		if (getContainerFullPath() != null) {
			IResource resource = StudioResourceUtils.getResourcePathFromContainerPath(getContainerFullPath());
			if (resource instanceof IProject) {
				setProject((IProject) resource);
			} else {
				setProject(resource.getProject());
			}
			StateMachine stateMachineToUse = getStateMachineToUse();
			if (stateMachineToUse != null) {
				txt_StateMachineFullPath.setText(stateMachineToUse.getFullPath());
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private StateMachine getStateMachineToUse() {
		if (_selection != null && _selection.isEmpty() == false) {
			Iterator selectionIterator = _selection.iterator();
			while (selectionIterator.hasNext()) {
				Object selectedObject = (Object) selectionIterator.next();
				if (selectedObject instanceof IResource) {
					DesignerElement element = IndexUtils.getElement((IResource) selectedObject);
					if (element != null && element.getElementType().compareTo(ELEMENT_TYPES.STATE_MACHINE) == 0) {
						return (StateMachine) ((EntityElement)element).getEntity();
					}
				}
			}
		}
		List<DesignerElement> allStateMachines = IndexUtils.getAllElements(getProject().getName(), ELEMENT_TYPES.STATE_MACHINE);
		if (allStateMachines.isEmpty() == false) {
			return (StateMachine) ((EntityElement)allStateMachines.get(0)).getEntity();
		}
		return null;
	}

	@Override
	protected boolean validatePage() {
		boolean validatePage = super.validatePage();
		if (validatePage == true) {
			String stateMachinePath = txt_StateMachineFullPath.getText();
			if (stateMachinePath != null && stateMachinePath.trim().length() != 0) {
				StateMachineElement stateMachineElement = (StateMachineElement) IndexUtils.getElement(getProject().getName(), stateMachinePath, ELEMENT_TYPES.STATE_MACHINE);
				if (stateMachineElement != null) {
					stateMachine = (StateMachine) stateMachineElement.getEntity();
				}
			}
			if (stateMachine == null) {
				setErrorMessage("Select state model");
				return false;
			}
			try {
				LocalECoreFactory localECoreFactory = LocalECoreFactory.getInstance(getProject());
				defaultDataSource = getDefaultDataSource(localECoreFactory);
				if (defaultDataSource == null){
					if (localECoreFactory.getChildren(BEViewsElementNames.METRIC).isEmpty() == true){
						//changed behavior to fix BE-10408
						//setMessage("No metrics were found...", IStatus.WARNING);
						setErrorMessage("No metrics were found...");
						return false;
					}
					else {
						//changed behavior to fix BE-10408
						//setMessage("No data sources were found...", IStatus.WARNING);
						setErrorMessage("No data sources were found...");
						return false;
					}
				}
			} catch (Exception e) {
				IStatus status = new Status(IStatus.WARNING, DashboardStateMachineComponentPlugin.PLUGIN_ID, "could not get default data source in state model component wizard for " + _selection, e);
				DashboardStateMachineComponentPlugin.getInstance().getLog().log(status);
				setMessage("could not find default data source...", IStatus.WARNING);
			}
			setPageComplete(true);
			return true;
		}
		return validatePage;
	}

	@SuppressWarnings("rawtypes")
	private LocalDataSource getDefaultDataSource(LocalECoreFactory localECoreFactory) throws Exception {
		if (_selection != null && _selection.isEmpty() == false) {
			Iterator selectionIterator = _selection.iterator();
			while (selectionIterator.hasNext()) {
				Object selectedObject = (Object) selectionIterator.next();
				if (selectedObject instanceof IResource) {
					DesignerElement element = IndexUtils.getElement((IResource) selectedObject);
					if (element != null && element.getElementType().compareTo(ELEMENT_TYPES.DATA_SOURCE) == 0) {
						return (LocalDataSource) LocalECoreFactory.toLocalElement(LocalECoreFactory.getInstance(getProject()), ((EntityElement)element).getEntity());
					}
				}
			}
		}
		return StateMachineComponentHelper.getDefaultDataSource(localECoreFactory);
	}

	public StateMachine getStateMachine() {
		return stateMachine;
	}

	public final LocalDataSource getDefaultDataSource() {
		return defaultDataSource;
	}

}
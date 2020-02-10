package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StatesFactory;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalExternalReference;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateMachineComponent;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateVisualization;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper.Difference;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.util.LocalECoreUtils;
import com.tibco.cep.studio.dashboard.ui.editors.AbstractEntityEditorPage;
import com.tibco.cep.studio.dashboard.ui.forms.AbstractSelectionListener;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.DashboardStateMachineComponentPlugin;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.wizards.NewStatesResyncWizard;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.wizards.StateMachineReplacementWizard;
import com.tibco.cep.studio.ui.statemachine.diagram.editors.StateMachineEditorInput;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 *
 * @author anpatil
 */
public class StateMachineComponentPage extends AbstractEntityEditorPage {

	private Text txt_StateMachinePath;
	private Button btn_StateMachineReplace;
	private Button btn_StateMachineResync;

	private ReadOnlyEmbeddedStateMachineEditor stateMachinePreviewer;

	public StateMachineComponentPage(FormEditor editor, LocalElement localElement) {
		super(editor, localElement);
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) {
		super.init(site, input);
	}

	@Override
	protected String getElementTypeName() {
		return "State Model Component";
	}

	@Override
	protected void createProperties(IManagedForm mform, Composite client) throws Exception {
		FormToolkit toolkit = mform.getToolkit();
		Section stateMachineSection = toolkit.createSection(client, ExpandableComposite.TITLE_BAR);
		stateMachineSection.setText("State Model Navigator");
		stateMachineSection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		stateMachineSection.setLayout(new FillLayout(SWT.VERTICAL));
		Composite stateMachineComposite = toolkit.createComposite(stateMachineSection, SWT.NULL);
		createStateMachineComposite(toolkit, stateMachineComposite);
		stateMachineSection.setClient(stateMachineComposite);
	}

	protected void createStateMachineComposite(FormToolkit toolkit, Composite composite) throws Exception {
		composite.setLayout(new GridLayout(4, false));
		// basic state machine information
		Label lblStateMachinePath = toolkit.createLabel(composite, "State Model:");
		lblStateMachinePath.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));
		// read only text field
		txt_StateMachinePath = toolkit.createText(composite, "", SWT.READ_ONLY);
		txt_StateMachinePath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		// browse button
		btn_StateMachineReplace = toolkit.createButton(composite, "Replace...", SWT.PUSH);
		btn_StateMachineReplace.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		btn_StateMachineReplace.addSelectionListener(new AbstractSelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				replaceButtonSelected();
			}

		});
		// resync button
		btn_StateMachineResync = toolkit.createButton(composite, "Resync...", SWT.PUSH);
		btn_StateMachineResync.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		btn_StateMachineResync.addSelectionListener(new AbstractSelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				resyncButtonSelected();
			}

		});

		Composite diagramHolder = toolkit.createComposite(composite, SWT.BORDER);
		diagramHolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));

		GridLayout diagramHolderLayout = new GridLayout();
		diagramHolderLayout.marginHeight = 0;
		diagramHolderLayout.marginWidth = 0;
		diagramHolder.setLayout(diagramHolderLayout);

		IFile file = ((FileEditorInput) this.getEditorInput()).getFile();
		StateMachine stateMachine = null;
		LocalExternalReference stateMachineRef = (LocalExternalReference) getLocalElement().getElement("StateMachine");
		if (stateMachineRef != null) {
			stateMachine = (StateMachine) stateMachineRef.getEObject();
		}
		stateMachinePreviewer = new ReadOnlyEmbeddedStateMachineEditor(getEditor());
		stateMachinePreviewer.setManagedForm((ManagedForm) getManagedForm());
		stateMachinePreviewer.setContainer(diagramHolder);
		// Modified to fix BE-10393 - 01/17/2011 - Anand
		stateMachinePreviewer.init(getEditorSite(), new StateMachineEditorInput(file, stateMachine == null ? StatesFactory.eINSTANCE.createStateMachine() : stateMachine));
		refreshStateMachineWidgets(stateMachine);
		StudioUIUtils.setWorkbenchSelection(getLocalElement(), getEditor());
	}

	private void refreshStateMachineWidgets(StateMachine stateMachine) throws Exception {
		txt_StateMachinePath.setText(stateMachine == null ? "" : stateMachine.getFullPath());

		Difference difference = StateMachineComponentHelper.computeDifference((LocalStateMachineComponent) getLocalElement());
		if (difference.hasDifferences() == true) {
			btn_StateMachineResync.setEnabled(true);
			StringBuilder msg = new StringBuilder();
			int size = difference.getStatesToAdd().size();
			if (size != 0) {
				msg.append("Settings for ");
				msg.append(size);
				if (size > 1) {
					msg.append(" states to add");
				} else {
					msg.append(" state to add");
				}
			}
			size = difference.getStatesVizualizationToBeDeleted().size();
			if (size != 0) {
				if (msg.length() != 0) {
					msg.append(", ");
				}
				msg.append("Settings for ");
				msg.append(size);
				if (size > 1) {
					msg.append(" states to delete");
				} else {
					msg.append(" state to delete");
				}
			}
			btn_StateMachineResync.setToolTipText(msg.toString());
		} else {
			btn_StateMachineResync.setEnabled(false);
		}
	}

	protected void replaceButtonSelected() {
		Shell activeShell = Display.getCurrent().getActiveShell();
		MessageDialog messageDialog = new MessageDialog(activeShell, "Replace State Model", null, "Replacing the state model will reset the component. Do you wish to continue?", MessageDialog.QUESTION, new String[] {
				"Yes", "No" }, 1);
		int open = messageDialog.open();
		if (open == MessageDialog.OK) {
			try {
				LocalStateMachineComponent localStateMachineComponent = (LocalStateMachineComponent) getLocalElement();
				StateMachineReplacementWizard wizard = new StateMachineReplacementWizard(localStateMachineComponent);
				WizardDialog wizardDialog = new WizardDialog(activeShell, wizard);
				open = wizardDialog.open();
				if (open == WizardDialog.OK) {
					StateMachine newStateMachine = (StateMachine) getLocalElement().getElement(LocalStateMachineComponent.ELEMENT_KEY_STATE_MACHINE).getEObject();
					txt_StateMachinePath.setText(newStateMachine.getFullPath());
					stateMachinePreviewer.setStateMachine(newStateMachine);
				}
			} catch (Exception e) {
				IStatus status = new Status(IStatus.ERROR, DashboardStateMachineComponentPlugin.PLUGIN_ID, "could not replace state model in " + getEditorInput().getName(), e);
				DashboardStateMachineComponentPlugin.getInstance().getLog().log(status);
				MessageDialog.openError(Display.getCurrent().getActiveShell(), "State Model Component Editor", "could not replace state model");
			}
		}
	}

	protected void resyncButtonSelected() {
		IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		activePage.saveEditor(this.getEditor(), true);
		if (getEditor().isDirty() == false) {
			try {
				LocalStateMachineComponent component = (LocalStateMachineComponent) getLocalElement();
				Difference difference = StateMachineComponentHelper.computeDifference(component);
				boolean statesAdded = false;
				boolean statesRemoved = false;
				if (difference.getStatesToAdd().isEmpty() == false) {
					WizardDialog dialog = new WizardDialog(Display.getCurrent().getActiveShell(), new NewStatesResyncWizard(component, difference.getStatesToAdd()));
					if (dialog.open() == WizardDialog.OK) {
						statesAdded = true;
					}
				}
				if (difference.getStatesVizualizationToBeDeleted().isEmpty() == false) {
					for (LocalStateVisualization visualization : difference.getStatesVizualizationToBeDeleted()) {
						component.removeElement(visualization);
					}
					statesRemoved = true;
				}
				if (statesAdded == true || statesRemoved == true) {
					StringBuilder msg = new StringBuilder();
					int size = difference.getStatesToAdd().size();
					if (statesAdded == true && size != 0) {
						msg.append("Settings for ");
						msg.append(size);
						if (size > 1) {
							msg.append(" states added");
						} else {
							msg.append(" state added");
						}
					}
					size = difference.getStatesVizualizationToBeDeleted().size();
					if (statesRemoved == true && size != 0) {
						if (msg.length() != 0) {
							msg.append(", ");
						}
						msg.append("Settings for ");
						msg.append(size);
						if (size > 1) {
							msg.append(" states deleted");
						} else {
							msg.append(" state deleted");
						}
					}
					btn_StateMachineResync.setToolTipText(msg.toString());
					btn_StateMachineResync.setEnabled(false);
					MessageDialog askToSave = new MessageDialog(Display.getCurrent().getActiveShell(),"Resync State Model",null,msg.toString(),MessageDialog.QUESTION,new String[]{"Save Now", "Save Later"},0);
					int userSelection = askToSave.open();
					if (userSelection == MessageDialog.OK) {
						boolean saved = activePage.saveEditor(this.getEditor(), false);
						if (saved == false) {
							MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "Resync State Model", "Could not save resync changes");
						}
					}
				}
			} catch (Exception e) {
				IStatus status = new Status(IStatus.ERROR, DashboardStateMachineComponentPlugin.PLUGIN_ID, "could not resync state model in " + getEditorInput().getName(), e);
				DashboardStateMachineComponentPlugin.getInstance().getLog().log(status);
				MessageDialog.openError(Display.getCurrent().getActiveShell(), "State Model Component Editor", "could not resync state model");
			}
		}
	}

	final ReadOnlyEmbeddedStateMachineEditor getStateMachinePreviewer() {
		return stateMachinePreviewer;
	}

	@Override
	protected void handleOutsideElementChange(int change, LocalElement element) {
		try {
			LocalElement localElementInEditor = getLocalElement();
			if (element.equals(localElementInEditor) == true) {
				//refresh the element
				localElementInEditor.refresh(element.getEObject());
				//load the element fully
				LocalECoreUtils.loadFully(localElementInEditor, true, true);
				// refresh the selection
				ISelectionProvider selectionProvider = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor().getSite().getSelectionProvider();
				ISelection selection = stateMachinePreviewer.getSelection();
				selectionProvider.setSelection(StructuredSelection.EMPTY);
				//get the state machine after refresh, it might be null
				LocalElement stateMachineRef = localElementInEditor.getElement(LocalStateMachineComponent.ELEMENT_KEY_STATE_MACHINE);
				if (stateMachineRef == null) {
					//the state machine has been deleted
					refreshStateMachineWidgets(null);
					stateMachinePreviewer.setStateMachine(StatesFactory.eINSTANCE.createStateMachine());
				} else {
					//update the state machine widgets
					refreshStateMachineWidgets((StateMachine) stateMachineRef.getEObject());
					// refresh the current selection
					selectionProvider.setSelection(selection);
				}
			} else if (element instanceof LocalExternalReference) {
				if (element.equals(localElementInEditor.getElement(LocalStateMachineComponent.ELEMENT_KEY_STATE_MACHINE)) == true) {
					if (change == IResourceDelta.CHANGED) {
						// update the state machine reference
						localElementInEditor.setElement(LocalStateMachineComponent.ELEMENT_KEY_STATE_MACHINE, element);
						// refresh the path & resync button
						refreshStateMachineWidgets((StateMachine) element.getEObject());
						// update the previewer
						stateMachinePreviewer.setStateMachine((StateMachine) element.getEObject());
					}
				} else if (change == IResourceDelta.REMOVED) {
					// do nothing , since a delete will force a re-factor which will update the user of this reference
				}
			}
		} catch (Exception e) {
			IStatus status = new Status(IStatus.ERROR, DashboardStateMachineComponentPlugin.PLUGIN_ID, "could not refresh " + getEditorInput().getName(), e);
			DashboardStateMachineComponentPlugin.getInstance().getLog().log(status);
		}
	}

	@Override
	public void dispose() {
		stateMachinePreviewer.dispose();
	}

}
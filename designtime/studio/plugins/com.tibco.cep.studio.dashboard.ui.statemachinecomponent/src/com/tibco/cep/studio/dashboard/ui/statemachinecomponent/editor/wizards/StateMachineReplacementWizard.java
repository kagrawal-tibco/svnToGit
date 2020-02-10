package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.wizards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.navigator.INavigatorActivationService;
import org.eclipse.ui.navigator.INavigatorContentService;
import org.eclipse.ui.navigator.NavigatorContentServiceFactory;

import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.StateMachineElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalExternalReference;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDataSource;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateMachineComponent;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateVisualization;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.DashboardStateMachineComponentPlugin;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.wizard.AbstractTemplateSettingsWizardPage;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.wizard.ContentTemplateAlertSettingsWizardPage;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.wizard.ContentTemplateDataSettingsWizardPage;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.wizard.IndicatorTemplateAlertSettingsWizardPage;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.wizard.IndicatorTemplateDataSettingsWizardPage;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.wizard.StateMachineComponentTemplatesController;
import com.tibco.cep.studio.ui.filter.ProjectLibraryFilter;
import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;
import com.tibco.cep.studio.ui.wizards.OnlyFileInclusionFilter;

public class StateMachineReplacementWizard extends Wizard {

	private static final String STATE_MACHINE_EXTENSION = IndexUtils.getFileExtension(ELEMENT_TYPES.STATE_MACHINE);
	
	private LocalStateMachineComponent localStateMachineComponent;

	private IProject project;

	private StateMachine currentStateMachine;
	
	private StateMachineReplacementSelectionWizardPage stateMachineReplacementSelectionWizardPage;
	
	private StateMachineComponentTemplatesController templatesController;

	private LocalDataSource defaultDataSource;

	public StateMachineReplacementWizard(LocalStateMachineComponent localStateMachineComponent) throws Exception {
		this.localStateMachineComponent = localStateMachineComponent;
		this.project = ((LocalECoreFactory)this.localStateMachineComponent.getRoot()).getProject();
		if (localStateMachineComponent.getElement(LocalStateMachineComponent.ELEMENT_KEY_STATE_MACHINE) != null) {
			this.currentStateMachine = (StateMachine) localStateMachineComponent.getElement(LocalStateMachineComponent.ELEMENT_KEY_STATE_MACHINE).getEObject();
		}
		defaultDataSource = StateMachineComponentHelper.getDefaultDataSource(localStateMachineComponent);
		setHelpAvailable(false);
		setNeedsProgressMonitor(false);
		setWindowTitle("State Model Replacement Wizard");
	}

	@Override
	public void addPages() {
		stateMachineReplacementSelectionWizardPage = new StateMachineReplacementSelectionWizardPage();
		addPage(stateMachineReplacementSelectionWizardPage);
		addPage(new IndicatorTemplateDataSettingsWizardPage());
		addPage(new ContentTemplateDataSettingsWizardPage());
		addPage(new IndicatorTemplateAlertSettingsWizardPage());
		addPage(new ContentTemplateAlertSettingsWizardPage());
	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		IWizardPage nextPage = super.getNextPage(page);
		if (nextPage instanceof AbstractTemplateSettingsWizardPage){
			try {
				getTemplatesController().prepPage((AbstractTemplateSettingsWizardPage) nextPage);
			} catch (Exception e) {
				IStatus status = new Status(IStatus.WARNING,DashboardStateMachineComponentPlugin.PLUGIN_ID,"could not prep '"+nextPage.getName()+"' for "+localStateMachineComponent,e);
				DashboardStateMachineComponentPlugin.getInstance().getLog().log(status);
			}
		}
		return nextPage;
	}
	
	private StateMachineComponentTemplatesController getTemplatesController() {
		if (templatesController == null){
			String folder = localStateMachineComponent.getFolder();
			templatesController = new StateMachineComponentTemplatesController(project,folder,folder,defaultDataSource);
			try {
				templatesController.createTemplateComponent();
			} catch (Exception e) {
				IStatus status = new Status(IStatus.WARNING,DashboardStateMachineComponentPlugin.PLUGIN_ID,"could not create template state visualization for "+localStateMachineComponent,e);
				DashboardStateMachineComponentPlugin.getInstance().getLog().log(status);
			}
		}
		return templatesController;
	}
	
	@Override
	public boolean performFinish() {
		try {
			LocalExternalReference stateMachineReference = new LocalExternalReference(stateMachineReplacementSelectionWizardPage.replacementStateMachine);
			LocalStateVisualization templateStateVisualization = getTemplatesController().getTemplateStateVisualization();
			StateMachineComponentHelper.setStateMachine(localStateMachineComponent, stateMachineReference,templateStateVisualization);
			return true;
		} catch (Exception e) {
			IStatus status = new Status(IStatus.WARNING,DashboardStateMachineComponentPlugin.PLUGIN_ID,"could not replace state model in "+localStateMachineComponent,e);
			DashboardStateMachineComponentPlugin.getInstance().getLog().log(status);
			return false;
		} finally {
			if (templatesController != null){
				try {
					getTemplatesController().deleteTemplateComponent();
				} catch (Exception ignore) {
				}
			}
		}
	}
	
	@Override
	public boolean performCancel() {
		if (templatesController != null){
			try {
				getTemplatesController().deleteTemplateComponent();
			} catch (Exception ignore) {
			}
		}
		return true;
	}

	class StateMachineReplacementSelectionWizardPage extends WizardPage {

		private TreeViewer treeViewer;

		private INavigatorContentService navigatorService;

		private List<ViewerFilter> filters;

		private StateMachine replacementStateMachine;
		
		protected StateMachineReplacementSelectionWizardPage() {
			super("StateMachineReplacementSelectionPage", "State Model Replacement Selection Page", null);
			setImageDescriptor(DashboardStateMachineComponentPlugin.getInstance().getImageRegistry().getDescriptor("statemachinecomponent_wizard.png"));
			filters = new ArrayList<ViewerFilter>(3);
			filters.add(new StudioProjectsOnly(project.getName()));
			List<String> extensions = Arrays.asList(STATE_MACHINE_EXTENSION);
			filters.add(new OnlyFileInclusionFilter(new HashSet<String>(extensions)));
			filters.add(new ProjectLibraryFilter(ELEMENT_TYPES.STATE_MACHINE));
			filters.add(new SpecificStateMachineFilter());
		}

		@Override
		public void createControl(Composite parent) {
			treeViewer = new TreeViewer(parent, SWT.BORDER | SWT.SINGLE);
			// Get the navigator content service
			NavigatorContentServiceFactory fact = NavigatorContentServiceFactory.INSTANCE;
			navigatorService = fact.createContentService("com.tibco.cep.studio.projectexplorer.view", treeViewer); //$NON-NLS-1$
			if (navigatorService != null) {
				/*
				 * Deactivate the Java Element content service in the picker as
				 * it can cause undesirable effects. One commom problem is when
				 * a initial selection is made - this causes a
				 * CyclicPathException when it comes to finding the parents of
				 * the selected object
				 */
				INavigatorActivationService service = navigatorService.getActivationService();
				if (service != null) {
					service.deactivateExtensions(new String[] { "org.eclipse.jdt.java.ui.javaContent" }, false); //$NON-NLS-1$
				}
				// Set the content and label providers
				treeViewer.setContentProvider(navigatorService.createCommonContentProvider());
				treeViewer.setLabelProvider(navigatorService.createCommonLabelProvider());

				// Apply filters
				if (filters != null) {
					for (ViewerFilter filter : filters) {
						treeViewer.addFilter(filter);
					}
				}
				// Apply input
				treeViewer.setInput(ResourcesPlugin.getWorkspace().getRoot());

				treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {

					public void selectionChanged(SelectionChangedEvent event) {
						treeSelectionChanged();
					}

				});
			}
			setControl(treeViewer.getControl());
		}

		protected void treeSelectionChanged() {
			setMessage(null, ERROR);
			setMessage(null, WARNING);
			IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
			if (selection.isEmpty() == false) {
				Object element = selection.getFirstElement();
				if (element instanceof IAdaptable) {
					IResource res = (IResource) ((IAdaptable) element).getAdapter(IResource.class);
					if (res instanceof IFile) {
						IFile file = (IFile) res;
						if (file.getFileExtension().equals(STATE_MACHINE_EXTENSION) == true) {
							String path = file.getProjectRelativePath().removeFileExtension().toString();
							StateMachineElement stateMachineElement = (StateMachineElement) IndexUtils.getElement(project.getName(), path, ELEMENT_TYPES.STATE_MACHINE);
							replacementStateMachine = (StateMachine) stateMachineElement.getEntity();
						}
					}
				}
			}
			if (replacementStateMachine == null) {
				setMessage("Select a state model...", ERROR);
				setPageComplete(false);
				return;
			} 
			setPageComplete(true);
		}
	}

	class SpecificStateMachineFilter extends ViewerFilter {

		@Override
		public boolean select(Viewer viewer, Object parentElement, Object element) {
			if (element instanceof IAdaptable) {
				IResource res = (IResource) ((IAdaptable) element).getAdapter(IResource.class);
				if (res instanceof IFile) {
					IFile file = (IFile) res;
					if (file.getFileExtension().equals(STATE_MACHINE_EXTENSION) == true) {
						if (currentStateMachine == null) {
							return true;
						}
						IPath projectRelativePath = file.getProjectRelativePath();
						String selectedElementFullPath = "/" + projectRelativePath.removeFileExtension();
						return !(selectedElementFullPath.equals(currentStateMachine.getFullPath()) == true);
					}
				}
			}
			return true;
		}
	}
}

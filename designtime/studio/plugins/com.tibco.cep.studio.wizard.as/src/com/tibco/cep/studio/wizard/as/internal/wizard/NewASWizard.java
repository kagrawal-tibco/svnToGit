package com.tibco.cep.studio.wizard.as.internal.wizard;

import static com.tibco.cep.studio.wizard.as.ASPlugin._FLAG_PERFORM_SYNC_EXEC;
import static com.tibco.cep.studio.wizard.as.ASPlugin._PLUGIN_ID;
import static com.tibco.cep.studio.wizard.as.internal.utils.PluginUtils.monitorFor;
import static com.tibco.cep.studio.wizard.as.presentation.models.INewASWizardModel._PROP_NAME_WIZARD_WINDOW_TITLE;
import static org.eclipse.core.databinding.UpdateValueStrategy.POLICY_NEVER;
import static org.eclipse.core.resources.IResource.DEPTH_INFINITE;
import static org.eclipse.core.runtime.IStatus.CANCEL;
import static org.eclipse.core.runtime.IStatus.ERROR;
import static org.eclipse.core.runtime.IStatus.OK;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IPageChangeProvider;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.IPageChangingListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.dialogs.PageChangingEvent;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.studio.wizard.as.ASPlugin;
import com.tibco.cep.studio.wizard.as.ASPlugin.IOpenableInShell;
import com.tibco.cep.studio.wizard.as.commons.utils.IContext;
import com.tibco.cep.studio.wizard.as.commons.utils.SimpleContext;
import com.tibco.cep.studio.wizard.as.internal.presentation.PresentationFactory;
import com.tibco.cep.studio.wizard.as.internal.services.ServiceFactory;
import com.tibco.cep.studio.wizard.as.internal.services.spi.PersistenceStageParticipant;
import com.tibco.cep.studio.wizard.as.internal.utils.Messages;
import com.tibco.cep.studio.wizard.as.internal.utils.PluginUtils;
import com.tibco.cep.studio.wizard.as.internal.wizard.pages.WarningWizardPage;
import com.tibco.cep.studio.wizard.as.presentation.controllers.INewASWizardController;
import com.tibco.cep.studio.wizard.as.presentation.models.INewASWizardModel;
import com.tibco.cep.studio.wizard.as.services.api.IASService;
import com.tibco.cep.studio.wizard.as.services.spi.IStageParticipant;
import com.tibco.cep.studio.wizard.as.wizard.IASWizardPage;
import com.tibco.cep.studio.wizard.as.wizard.INewASWizard;
import com.tibco.cep.studio.wizard.as.wizard.handlers.IWizardPageHandler;

public class NewASWizard extends Wizard implements INewASWizard {

	// Presentation
	private INewASWizardController         controller;

	// Raw Pages
	private List<IWizardPage>              rawPages;

	// Bindings
	private DataBindingContext             bc;

	// Wizard Context
	// private IWorkbench workbench;
	private IContext                       wizardContext;
	private IStructuredSelection           selection;
	private boolean                        presentationInitialized; // this flag indicate if all of AS related resources are initialized.
	private boolean                        wizardReady; // this flag indicate if the wizard is ready for using.

	// Handler
	private Handler                        handler;


	@Override
	public boolean performFinish() {
		boolean result = false;
		final IStageParticipant participant = new PersistenceStageParticipant();
		IRunnableWithProgress job = new WorkspaceModifyOperation() {

			@Override
			protected void execute(IProgressMonitor monitor) throws CoreException, InvocationTargetException, InterruptedException {
				try {
	                controller.generateASResources(monitor, participant);
                }
                catch (Exception ex) {
	                throw new CoreException(new Status(ERROR, _PLUGIN_ID, ERROR, Messages.getString("NewASWizard.failed_create_res"), ex)); //$NON-NLS-1$
                }
			}

		};
		try {
	        getContainer().run(false, true, job);
	        IStatus status = participant.getStatus();
	        if (OK == status.getCode()) {
	        	controller.getModel().getOwnerProject().refreshLocal(DEPTH_INFINITE, monitorFor(null));
	        	IOpenableInShell openable = new IOpenableInShell() {

					@Override
					public void open(Shell shell) {
						MessageDialog.openInformation(shell, Messages.getString("NewASWizard.title_info"), Messages.getString("NewASWizard.succeed_create_res")); //$NON-NLS-1$ //$NON-NLS-2$
					}
				};
	        	ASPlugin.openDialog(getShell(), openable, _FLAG_PERFORM_SYNC_EXEC);
	        	result = true;
	        } else if (CANCEL == status.getCode()) {
	        	IOpenableInShell openable = new IOpenableInShell() {

					@Override
					public void open(Shell shell) {
						MessageDialog.openInformation(shell, Messages.getString("NewASWizard.title_hint"), Messages.getString("NewASWizard.task_canceled")); //$NON-NLS-1$ //$NON-NLS-2$
					}
				};
	        	ASPlugin.openDialog(getShell(), openable, _FLAG_PERFORM_SYNC_EXEC);
	        }
        }
        catch (Exception ex) {
        	ASPlugin.openError(getShell(), Messages.getString("NewASWizard.title_error"), ex.getMessage(), ex); //$NON-NLS-1$
        }
		return result;
	}

	@Override
	public void setContainer(IWizardContainer wizardContainer) {
		super.setContainer(wizardContainer);
		// wait for dialog call this method, and initialize the listeners.
		if (presentationInitialized && null != getContainer()) {
			initListeners();
		}
	}

	@Override
	public void addPages() {
		if (wizardReady) {
    		if (presentationInitialized && ownASChannel()) {
    			for (IWizardPage page : rawPages) {
    				addPage(page);
    			}
    		}
    		else {
    			IWizardPage warningPage = new WarningWizardPage(); //$NON-NLS-1$
    			addPage(warningPage);
    		}
		}
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// this.workbench = workbench;
		this.selection = selection;
		initialize();
	}

	private void initialize() {
		initPresentation();
		if (presentationInitialized) {
    		attachBindings();
    		initData();
		}
	}

	@Override
	public void dispose() {
		if (presentationInitialized) {
    		destroyListeners();
    		unattachBindings();
		}
		super.dispose();
	}

    private void initPresentation() {
        try {
            this.wizardContext = SimpleContext.newInstance();
            IASService service = ServiceFactory.getASService();
            IProject project = PluginUtils.getProject(selection);
            if (project != null) {
                this.controller = PresentationFactory.createNewASWizardController(service, project, wizardContext);
                setNeedsProgressMonitor(controller.getModel().isNeedsProgressMonitor());
                this.presentationInitialized = true;
            }
            this.wizardReady = true;
        }
        catch (Throwable ex) {
            String msg = null;
            if (ex instanceof NoClassDefFoundError) {
                msg = Messages.getString("Common.missing_as_clazz");
            }
            else {
                String localizedMessage = ex.getLocalizedMessage();
                msg = Messages.getString("NewASWizard.init_wizard_error") + (localizedMessage != null ? localizedMessage : ex.toString()); //$NON-NLS-1$
            }
            ASPlugin.openError(getShell(), Messages.getString("NewASWizard.title_error"), msg, ex); //$NON-NLS-1$
        }
    }

	private void attachBindings() {
		bc = new DataBindingContext();
		INewASWizardModel model = controller.getModel();

		// Property windowTitle
		bc.bindValue(BeansObservables.observeValue(this, "windowTitle"), //$NON-NLS-1$
		        BeansObservables.observeValue(model, _PROP_NAME_WIZARD_WINDOW_TITLE), //$NON-NLS-1$
		        // Model -> View one-way update strategy
		        new UpdateValueStrategy(POLICY_NEVER), new UpdateValueStrategy());
	}

	private void unattachBindings() {
		bc.dispose();
		bc = null;
	}

	private void initListeners() {
		handler = new Handler();

		IWizardContainer container = getContainer();
		if (container instanceof WizardDialog) {
			WizardDialog dialog = (WizardDialog) container;
			dialog.addPageChangingListener(handler);
		}
		if (container instanceof IPageChangeProvider) {
			IPageChangeProvider provider = (IPageChangeProvider) container;
			provider.addPageChangedListener(handler);
		}
	}

	private void destroyListeners() {
		IWizardContainer container = getContainer();
		if (container instanceof WizardDialog) {
			WizardDialog dialog = (WizardDialog) container;
			dialog.removePageChangingListener(handler);
		}
		if (container instanceof IPageChangeProvider) {
			IPageChangeProvider provider = (IPageChangeProvider) container;
			provider.removePageChangedListener(handler);
		}

		handler = null;
	}

	private void initData() {
		this.rawPages = controller.getModel().getAllRawWizardPages();
	}

	private boolean ownASChannel() {
		boolean ownASChannel = false;
		if (presentationInitialized) {
			IProject ownerProject;
            try {
	            ownerProject = PluginUtils.getProject(selection);
				IASService service = controller.getModel().getASService();
				List<Channel> channels = service.getASChannels(ownerProject);
				ownASChannel = null != channels && !channels.isEmpty();
            }
            catch (Exception ex) {
	            ASPlugin.openError(getShell(), Messages.getString("NewASWizard.title_error"), Messages.getString("NewASWizard.failed_get_channels"), ex); //$NON-NLS-1$ //$NON-NLS-2$
            }

		}
		return ownASChannel;
	}

	private class Handler implements IPageChangingListener, IPageChangedListener {

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public void handlePageChanging(PageChangingEvent event) {
			if (event.getCurrentPage() instanceof IASWizardPage<?, ?> && event.getTargetPage() instanceof IASWizardPage<?, ?>) {
				IASWizardPage<?, ?> currentPage = (IASWizardPage<?, ?>) event.getCurrentPage();
				IASWizardPage<?, ?> targetPage = (IASWizardPage<?, ?>) event.getTargetPage();
				IWizardPageHandler pageHandler = currentPage.getPageHandler();
				try {
					pageHandler.handlePageChanging(currentPage, targetPage, wizardContext);
				}
				catch (Exception ex) {
					String currenWizardPageName = currentPage.getASWizardPageController().getModel().getWizardPageName();
					String targetWizardPageName = targetPage.getASWizardPageController().getModel().getWizardPageName();
					ASPlugin.openError(getShell(), Messages.getString("NewASWizard.title_error"), //$NON-NLS-1$
					        Messages.getString("NewASWizard.error_when_switch_page") + currenWizardPageName + " to " + targetWizardPageName, ex); //$NON-NLS-1$ //$NON-NLS-2$
					event.doit = false;
				}
			}
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public void pageChanged(PageChangedEvent event) {
			if (event.getSelectedPage() instanceof IASWizardPage<?, ?>) {
				IASWizardPage<?, ?> currentPage = (IASWizardPage<?, ?>) event.getSelectedPage();
				IWizardPageHandler pageHandler = currentPage.getPageHandler();
				try {
					pageHandler.handlePageChanged(currentPage, wizardContext);
				}
				catch (Exception ex) {
					String currentWizardPageName = currentPage.getASWizardPageController().getModel().getWizardPageName();
					ASPlugin.openError(getShell(), Messages.getString("NewASWizard.title_error"), Messages.getString("NewASWizard.enter_page_error") + currentWizardPageName, ex); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
		}

	}

}

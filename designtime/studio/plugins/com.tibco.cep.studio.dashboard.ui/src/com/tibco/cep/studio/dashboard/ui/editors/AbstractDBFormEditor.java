package com.tibco.cep.studio.dashboard.ui.editors;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.refreshPaletteAndOverview;

import java.util.Arrays;
import java.util.LinkedList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.listeners.IMessageProvider;
import com.tibco.cep.studio.dashboard.core.listeners.ISynElementChangeListener;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.utils.DashboardResourceUtils;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

public abstract class AbstractDBFormEditor extends FormEditor {

	private LocalElement localElement = null;

	private ElementChangeListener listener = new ElementChangeListener();
	private ISynElementChangeListener refreshListener;
	private boolean inSaveCycle;
	protected boolean outOfSync;

	private boolean disposed;

	public AbstractDBFormEditor() {
		super();
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		disposed = false;
		if (input instanceof FileEditorInput) {
			try {
				IFile file = ((FileEditorInput) input).getFile();
				LocalElement localElement = loadLocalElement(input, file);
				//set the local element
				setLocalElement(localElement);
				// set part name
				setPartName(localElement.getName());
				localElement.subscribe(listener, localElement.getElementType());
				// add listener to handle out of editor refreshes
				refreshListener = new RefreshListener();
				LinkedList<String> interestingTypes = new LinkedList<String>();
				interestingTypes.add(localElement.getElementType());
				interestingTypes.addAll(Arrays.asList(getInterestingElementTypes()));
				((LocalECoreFactory) localElement.getRoot()).addElementChangeSubscription(refreshListener, interestingTypes);
			} catch (Exception e) {
				throw new PartInitException("could not load " + input.getName(), e);
			}
		}

		site.getPage().addPartListener(partListener);
		super.init(site, input);

		refreshPaletteAndOverview(getEditorSite());
	}

	protected LocalElement loadLocalElement(IEditorInput input, IFile file) throws Exception {
		IProject project = StudioResourceUtils.getProjectForInput(input);
		return LocalECoreFactory.toLocalElement(LocalECoreFactory.getInstance(project), file);
	}

	protected void setLocalElement(LocalElement localElement) {
		if (this.localElement != null) {
			//if current local element is not null, then unsubscribe
			this.localElement.unsubscribe(listener, localElement.getElementType());
		}
		this.localElement = localElement;
		// subscribe to element changes for marking dirty flag
		this.localElement.subscribe(listener, this.localElement.getElementType());
	}

	protected String[] getInterestingElementTypes() {
		return new String[0];
	}

	@Override
	protected void createPages() {
		super.createPages();
		if (getPageCount() == 1 && getContainer() instanceof CTabFolder) {
			((CTabFolder) getContainer()).setTabHeight(0);
		}
		updateTitle();
	}

	protected void updateTitle() {
		IEditorInput input = getEditorInput();
		setPartName(input.getName());
		setTitleToolTip(input.getToolTipText());
	}

	private void updateTitleStatus() {
		getSite().getShell().getDisplay().syncExec(new Runnable() {
			public void run() {
				firePropertyChange(PROP_DIRTY);
			}
		});
	}

	@Override
	protected FormToolkit createToolkit(Display display) {
		// Create a toolkit that shares colors between editors.
		return new FormToolkit(DashboardUIPlugin.getInstance().getFormColors(display));
	}

	@Override
	public boolean isDirty() {
		boolean isDirty = localElement.isModified() || localElement.isNew();
		// System.out.println("AbstractDBFormEditor.isDirty()::"+isDirty);
		return isDirty;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		try {
			inSaveCycle = true;
			monitor.beginTask("Saving " + localElement.getFullPath() + "...", 3);
			LocalElement localElement = synchronize();
			monitor.worked(1);
			if (true == monitor.isCanceled()) {
				return;
			}
			IProject project = StudioResourceUtils.getProjectForInput(getEditorInput());
			String baseURI = DashboardResourceUtils.getCurrentProjectBaseURI(project);
			DashboardResourceUtils.persistEntity((Entity) localElement.getEObject(), baseURI, project, monitor);
			monitor.worked(1);
			this.localElement.setInternalStatus(InternalStatusEnum.StatusExisting, true);
			firePropertyChange(PROP_DIRTY);
			((IFileEditorInput) getEditorInput()).getFile().refreshLocal(IResource.DEPTH_ONE, monitor);
			monitor.worked(1);
			monitor.done();
		} catch (CoreException e) {
			throw new RuntimeException("could not refresh " + getEditorInput().getName() + " after save", e);
		} catch (Exception e) {
			throw new RuntimeException("could not save " + getEditorInput().getName(), e);
		} finally {
			monitor.done();
			inSaveCycle = false;
		}
	}

	protected LocalElement synchronize() throws Exception {
		localElement.synchronize();
		return localElement;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void doSaveAs() {
		// do nothing
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {
		if (IProject.class.equals(adapter)) {
			IEditorInput editorInput  = (IEditorInput) getEditorInput();
			if(editorInput != null && editorInput instanceof IFileEditorInput) {
				IFileEditorInput fei = (IFileEditorInput) editorInput;
				return fei.getFile().getProject();
			}
		}
		return super.getAdapter(adapter);
	}

	@Override
	public void dispose() {
		disposed = true;
		if (null != localElement) {
			// unsubscribe all listeners
			localElement.unsubscribeAll(listener);
			// remove refresh listener
			try {
				((LocalECoreFactory) localElement.getRoot()).removeElementChangeSubscription(refreshListener);
				refreshListener = null;
			} catch (Exception e) {
				// log and move on
				DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.WARNING, DashboardUIPlugin.PLUGIN_ID, "could not remove refresh listener on editor for " + getEditorInput(), e));
			}
		}
		if (partListener != null) {
			getSite().getPage().removePartListener(partListener);
		}
		super.dispose();
	}

	@SuppressWarnings("unused")
	private void setOutOfSync() {
		outOfSync = true;
		for (Object possiblePage : pages) {
			if (possiblePage instanceof AbstractEntityEditorPage) {
				AbstractEntityEditorPage page = (AbstractEntityEditorPage) possiblePage;
				Control partControl = page.getPartControl();
				if (partControl != null) {
					partControl.setEnabled(false);
				}
			}
		}
	}

	protected void handleOutsideElementChange(int change, LocalElement element) {
		if (pages == null) {
			return;
		}
		for (Object possiblePage : pages) {
			if (possiblePage instanceof AbstractEntityEditorPage) {
				((AbstractEntityEditorPage) possiblePage).handleOutsideElementChange(change, element);
			}
		}
	}

	@Override
	public void setFocus() {
		if (outOfSync == true) {
			final IEditorInput input = getEditorInput();
			Display.getCurrent().asyncExec(new Runnable() {

				@Override
				public void run() {
					String name = input.getName();
					MessageDialog.openWarning(getEditorSite().getShell(), name, name + " is out of sync. Click Ok to re-open the editor. All pending changes will be lost...");
					getEditorSite().getPage().closeEditor(AbstractDBFormEditor.this, false);
					IEditorRegistry registry = PlatformUI.getWorkbench().getEditorRegistry();
					IEditorDescriptor ed = registry.getDefaultEditor(name);
					try {
						getEditorSite().getPage().openEditor(input, ed.getId());
					} catch (PartInitException e) {
						throw new RuntimeException("could not open editor for " + name, e);
					}
				}

			});
			outOfSync = false;
		}
		super.setFocus();
	}

	public LocalElement getLocalElement() {
		return localElement;
	}

	/**
	 * Handles activation of the editor or it's associated views.
	 */
	protected void handleActivate() {
	}

	/**
	 * This listens for when the outline becomes active
	 */
	protected IPartListener partListener = new IPartListener() {
		public void partActivated(IWorkbenchPart p) {
			if (p instanceof AbstractDBFormEditor) {
				handleActivate();
			}
		}

		public void partBroughtToTop(IWorkbenchPart p) {
		}

		public void partClosed(IWorkbenchPart p) {
		}

		public void partDeactivated(IWorkbenchPart p) {
		}

		public void partOpened(IWorkbenchPart p) {
		}
	};

	private final class ElementChangeListener implements ISynElementChangeListener {

		public void elementAdded(IMessageProvider parent, IMessageProvider newElement)  {
			updateTitleStatus();
		}

		public void elementRemoved(IMessageProvider parent, IMessageProvider removedElement)  {
			updateTitleStatus();
		}

		public void elementChanged(IMessageProvider parent, IMessageProvider changedElement)  {
			updateTitleStatus();
		}

		public void propertyChanged(IMessageProvider provider, SynProperty property, Object oldValue, Object newValue)  {
			updateTitleStatus();
		}

		public String getName()  {
			return AbstractDBFormEditor.this.getClass().getName();
		}

		public void elementStatusChanged(IMessageProvider parent, InternalStatusEnum status)  {
			updateTitleStatus();
		}
	}

	private final class RefreshListener implements ISynElementChangeListener {

		@Override
		public void elementAdded(IMessageProvider parent, final IMessageProvider newElement)  {
			if (inSaveCycle == true) {
				return;
			}
			if (disposed == true) {
				return;
			}
			System.out.println(this+".elementAdded("+parent+","+newElement+")");
			getSite().getShell().getDisplay().syncExec(new Runnable() {

				@Override
				public void run() {
					InternalStatusEnum existingStatus = AbstractDBFormEditor.this.localElement.getInternalStatus();
					AbstractDBFormEditor.this.localElement.setBulkOperation(true);
//					 if (existingStatus.equals(InternalStatusEnum.StatusModified) == true) {
//						setOutOfSync();
//						return;
//					}
					try {
						handleOutsideElementChange(IResourceDelta.ADDED, (LocalElement) newElement);
					} finally {
						// we will reset the status of the element after refresh
						try {
							AbstractDBFormEditor.this.localElement.setInternalStatus(existingStatus);
						} catch (Exception e) {
							// log and move on
							Status status = new Status(IStatus.WARNING, DashboardUIPlugin.PLUGIN_ID, "could not change " + localElement + "'s status to " + existingStatus
									+ " during an external element add refresh cycle", e);
							DashboardUIPlugin.getInstance().getLog().log(status);
						} finally {
							AbstractDBFormEditor.this.localElement.setBulkOperation(false);
						}
					}
				}

			});

		}

		@Override
		public void elementChanged(IMessageProvider parent, final IMessageProvider changedElement)  {
			if (inSaveCycle == true) {
				return;
			}
			if (disposed == true) {
				return;
			}
			System.out.println(this+".elementChanged("+parent+","+changedElement+")");
			getSite().getShell().getDisplay().syncExec(new Runnable() {

				@Override
				public void run() {
					InternalStatusEnum existingStatus = AbstractDBFormEditor.this.localElement.getInternalStatus();
					AbstractDBFormEditor.this.localElement.setBulkOperation(true);
//					if (existingStatus.equals(InternalStatusEnum.StatusModified) == true) {
//						setOutOfSync();
//						return;
//					}
					try {
						handleOutsideElementChange(IResourceDelta.CHANGED, (LocalElement) changedElement);
					} finally {
						// we will reset the status of the element after refresh
						try {
							AbstractDBFormEditor.this.localElement.setInternalStatus(existingStatus);
						} catch (Exception e) {
							// log and move on
							Status status = new Status(IStatus.WARNING, DashboardUIPlugin.PLUGIN_ID, "could not change " + localElement + "'s status to " + existingStatus
									+ " during an external element change refresh cycle", e);
							DashboardUIPlugin.getInstance().getLog().log(status);
						} finally {
							AbstractDBFormEditor.this.localElement.setBulkOperation(false);
						}

					}
				}

			});
		}

		@Override
		public void elementRemoved(IMessageProvider parent, final IMessageProvider removedElement)  {
			if (inSaveCycle == true) {
				return;
			}
			if (disposed == true) {
				return;
			}
			System.out.println(this+".elementRemoved("+parent+","+removedElement+")");
			getSite().getShell().getDisplay().syncExec(new Runnable() {

				@Override
				public void run() {
					InternalStatusEnum existingStatus = AbstractDBFormEditor.this.localElement.getInternalStatus();
//					if (existingStatus.equals(InternalStatusEnum.StatusModified) == true) {
//						setOutOfSync();
//						return;
//					}
					try {
						handleOutsideElementChange(IResourceDelta.REMOVED, (LocalElement) removedElement);
					} finally {
						// we will reset the status of the element after refresh
						AbstractDBFormEditor.this.localElement.setBulkOperation(true);
						try {
							AbstractDBFormEditor.this.localElement.setInternalStatus(existingStatus);
						} catch (Exception e) {
							// log and move on
							Status status = new Status(IStatus.WARNING, DashboardUIPlugin.PLUGIN_ID, "could not change " + localElement + "'s status to " + existingStatus
									+ " during an external element removed refresh cycle", e);
							DashboardUIPlugin.getInstance().getLog().log(status);
						} finally {
							AbstractDBFormEditor.this.localElement.setBulkOperation(false);
						}

					}
				}

			});
		}

		@Override
		public void elementStatusChanged(IMessageProvider parent, InternalStatusEnum status)  {
			// do nothing
		}

		@Override
		public String getName()  {
			return getEditorInput() + " out of editor changes listener";
		}

		@Override
		public void propertyChanged(IMessageProvider provider, SynProperty property, Object oldValue, Object newValue)  {
			// do nothing
		}

		@Override
		public String toString() {
			try {
				return "["+getName()+"]";
			} catch (Exception e) {
				return super.toString();
			}
		}
	}
}

package com.tibco.cep.studio.dashboard.ui.editors.metric;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.refreshPaletteAndOverview;

import java.util.Arrays;
import java.util.LinkedList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.listeners.IMessageProvider;
import com.tibco.cep.studio.dashboard.core.listeners.ISynElementChangeListener;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.util.LocalECoreUtils;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.editors.metric.viewers.LocalMetricViewer;
import com.tibco.cep.studio.dashboard.ui.images.ContributableImageRegistry;
import com.tibco.cep.studio.dashboard.ui.utils.DashboardResourceUtils;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

public class MetricEditor extends AbstractMetricSaveableEditorPart {

	public LocalMetricViewer metricFormViewer;
	private MetricEditorInput metricFormEditorInput;

	private LocalElement localElement = null;
	private ElementChangeListener listener = new ElementChangeListener();
	private ISynElementChangeListener refreshListener;
	private boolean inSaveCycle;
	protected boolean outOfSync;

	private boolean disposed;

	@Override
	protected void addFormPage() throws PartInitException {
		metricFormViewer = new LocalMetricViewer(this);
		metricFormViewer.createPartControl(getContainer());
		pageIndex = addPage(metricFormViewer.getControl());
		this.setActivePage(0);
		this.setForm(metricFormViewer.getForm());
	}

	protected void updateTitle() {
		IEditorInput input = getEditorInput();
		setPartName(input.getName());
		setTitleToolTip(input.getToolTipText());
		if (metricFormEditorInput != null) {
			setTitleImage(((ContributableImageRegistry) DashboardUIPlugin.getInstance().getImageRegistry()).get(localElement));
		}
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		// work with LocalElement model similar to AbstractDBFormEditor
		disposed = false;
		// this.site = site;
		if (input instanceof FileEditorInput) {
			try {
				IFile file = ((FileEditorInput) input).getFile();
				LocalElement localElement = loadLocalElement(input, file);
				// load the element fully
				LocalECoreUtils.loadFully(localElement, true, true);
				// set the local element
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
				metricFormEditorInput = new MetricEditorInput(file, localElement);
				site.getPage().addPartListener(partListener);
				setSite(site);
				setInput(input);
				createModel(file);
			} catch (Exception e) {
				throw new PartInitException("could not load " + input.getName(), e);
			}
		} else {
			super.init(site, input);
		}
		refreshPaletteAndOverview(getEditorSite());

	}

	protected LocalElement loadLocalElement(IEditorInput input, IFile file) throws Exception {
		IProject project = StudioResourceUtils.getProjectForInput(input);
		LocalECoreFactory localECoreFactory = LocalECoreFactory.getInstance(project);
		LocalElement localMetric = LocalECoreFactory.toLocalElement(localECoreFactory, file);
		// Anand - 1/4/2012 : I have to force load the metric of the project. This causes the local particle to initialize
		// if the local particle is not initialized, then com.tibco.cep.studio.dashboard.core.model.impl.ProjectChangesListener
		// does not process the change in the metric (due to rename of a metric field)
		// see com.tibco.cep.studio.dashboard.core.model.impl.ProjectChangesListener.processFileChange(IFile)
		localECoreFactory.loadChildren(localMetric.getElementType());
		return localMetric;
	}

	protected void setLocalElement(LocalElement localElement) {
		if (this.localElement != null) {
			// if current local element is not null, then unsubscribe
			this.localElement.unsubscribe(listener, localElement.getElementType());
		}
		this.localElement = localElement;
		// subscribe to element changes for marking dirty flag
		this.localElement.subscribe(listener, this.localElement.getElementType());
	}

	public LocalElement getLocalElement() {
		return localElement;
	}

	protected String[] getInterestingElementTypes() {
		return new String[0];
	}

	@Override
	public void dispose() {
		try {
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
			if (metricFormViewer != null) {
				metricFormViewer.dispose();
			}
			super.dispose();
			if (partListener != null) {
				getSite().getPage().removePartListener(partListener);
			}
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
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

		/*	if (metricFormViewer.getPropTable().getCellEditor() != null) {
				metricFormViewer.getPropTable().getCellEditor().stopCellEditing();
			}*/
			super.doSave();

			setPropertyTableModifictionType(-1);

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
	protected void createPages() {
		createUIEditorPage();
		if (getPageCount() == 1 && getContainer() instanceof CTabFolder) {
			((CTabFolder) getContainer()).setTabHeight(0);
		}
		updateTitle();
	}

	@Override
	public String getPerspectiveId() {
		return null;
	}

	/**
	 * This listens for when the outline becomes active
	 */
	protected IPartListener partListener = new IPartListener() {
		public void partActivated(IWorkbenchPart p) {
			if (p == MetricEditor.this) {
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

	public LocalMetricViewer getMetricFromViewer() {
		return metricFormViewer;
	}

	private final class ElementChangeListener implements ISynElementChangeListener {

		public void elementAdded(IMessageProvider parent, IMessageProvider newElement) {
			updateTitleStatus();
		}

		public void elementRemoved(IMessageProvider parent, IMessageProvider removedElement) {
			updateTitleStatus();
		}

		public void elementChanged(IMessageProvider parent, IMessageProvider changedElement) {
			updateTitleStatus();
		}

		public void propertyChanged(IMessageProvider provider, SynProperty property, Object oldValue, Object newValue) {
			updateTitleStatus();
		}

		public String getName() {
			return MetricEditor.this.getClass().getName();
		}

		public void elementStatusChanged(IMessageProvider parent, InternalStatusEnum status) {
			updateTitleStatus();
		}
	}

	private final class RefreshListener implements ISynElementChangeListener {

		@Override
		public void elementAdded(IMessageProvider parent, final IMessageProvider newElement) {
			if (inSaveCycle == true) {
				return;
			}
			if (disposed == true) {
				return;
			}
			System.out.println(this + ".elementAdded(" + parent + "," + newElement + ")");
			getSite().getShell().getDisplay().syncExec(new Runnable() {

				@Override
				public void run() {
					InternalStatusEnum existingStatus = MetricEditor.this.localElement.getInternalStatus();
					MetricEditor.this.localElement.setBulkOperation(true);
					// if (existingStatus.equals(InternalStatusEnum.StatusModified) == true) {
					// setOutOfSync();
					// return;
					// }
					try {
						handleOutsideElementChange(IResourceDelta.ADDED, (LocalElement) newElement);
					} finally {
						// we will reset the status of the element after refresh
						try {
							MetricEditor.this.localElement.setInternalStatus(existingStatus);
						} catch (Exception e) {
							// log and move on
							Status status = new Status(IStatus.WARNING, DashboardUIPlugin.PLUGIN_ID, "could not change " + localElement + "'s status to " + existingStatus + " during an external element add refresh cycle", e);
							DashboardUIPlugin.getInstance().getLog().log(status);
						} finally {
							MetricEditor.this.localElement.setBulkOperation(false);
						}
					}
				}

			});

		}

		@Override
		public void elementChanged(IMessageProvider parent, final IMessageProvider changedElement) {
			if (inSaveCycle == true) {
				return;
			}
			if (disposed == true) {
				return;
			}
			System.out.println(this + ".elementChanged(" + parent + "," + changedElement + ")");
			getSite().getShell().getDisplay().syncExec(new Runnable() {

				@Override
				public void run() {
					InternalStatusEnum existingStatus = MetricEditor.this.localElement.getInternalStatus();
					MetricEditor.this.localElement.setBulkOperation(true);
					// if (existingStatus.equals(InternalStatusEnum.StatusModified) == true) {
					// setOutOfSync();
					// return;
					// }
					try {
						handleOutsideElementChange(IResourceDelta.CHANGED, (LocalElement) changedElement);
					} finally {
						// we will reset the status of the element after refresh
						try {
							MetricEditor.this.localElement.setInternalStatus(existingStatus);
						} catch (Exception e) {
							// log and move on
							Status status = new Status(IStatus.WARNING, DashboardUIPlugin.PLUGIN_ID, "could not change " + localElement + "'s status to " + existingStatus + " during an external element change refresh cycle",
									e);
							DashboardUIPlugin.getInstance().getLog().log(status);
						} finally {
							MetricEditor.this.localElement.setBulkOperation(false);
						}

					}
				}

			});
		}

		@Override
		public void elementRemoved(IMessageProvider parent, final IMessageProvider removedElement) {
			if (inSaveCycle == true) {
				return;
			}
			if (disposed == true) {
				return;
			}
			System.out.println(this + ".elementRemoved(" + parent + "," + removedElement + ")");
			getSite().getShell().getDisplay().syncExec(new Runnable() {

				@Override
				public void run() {
					InternalStatusEnum existingStatus = MetricEditor.this.localElement.getInternalStatus();
					// if (existingStatus.equals(InternalStatusEnum.StatusModified) == true) {
					// setOutOfSync();
					// return;
					// }
					try {
						handleOutsideElementChange(IResourceDelta.REMOVED, (LocalElement) removedElement);
					} finally {
						// we will reset the status of the element after refresh
						MetricEditor.this.localElement.setBulkOperation(true);
						try {
							MetricEditor.this.localElement.setInternalStatus(existingStatus);
						} catch (Exception e) {
							// log and move on
							Status status = new Status(IStatus.WARNING, DashboardUIPlugin.PLUGIN_ID,
									"could not change " + localElement + "'s status to " + existingStatus + " during an external element removed refresh cycle", e);
							DashboardUIPlugin.getInstance().getLog().log(status);
						} finally {
							MetricEditor.this.localElement.setBulkOperation(false);
						}

					}
				}

			});
		}

		@Override
		public void elementStatusChanged(IMessageProvider parent, InternalStatusEnum status) {
			// do nothing
		}

		@Override
		public String getName() {
			return getEditorInput() + " out of editor changes listener";
		}

		@Override
		public void propertyChanged(IMessageProvider provider, SynProperty property, Object oldValue, Object newValue) {
			// do nothing
		}

		@Override
		public String toString() {
			try {
				return "[" + getName() + "]";
			} catch (Exception e) {
				return super.toString();
			}
		}
	}

	private void updateTitleStatus() {
		getSite().getShell().getDisplay().syncExec(new Runnable() {
			public void run() {
				firePropertyChange(PROP_DIRTY);
			}
		});
	}

	@Override
	public boolean isDirty() {
		boolean isDirty = localElement.isModified() || localElement.isNew();
		return isDirty;
	}

	// Anand : 1/4/2012 : We have to override the getEntity API since the underlying getEntity API always
	// points to the oldest/initial EMF model loaded when we launch the editor. We want to point it to the
	// latest version to allow rename action to work repeatedly on the same editor instance.
	@Override
	public Entity getEntity() {
		return (Entity) localElement.getEObject();
	}

	protected void handleOutsideElementChange(int change, LocalElement element) {
		try {
			if (change == IResourceDelta.CHANGED && element.getID().equals(getLocalElement().getID()) == true) {
				// reset all the properties, children
				getLocalElement().refresh(element.getEObject());
				// super.handleOutsideElementChange(change, element);
			}
		} catch (Exception e) {
			DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.ERROR, DashboardUIPlugin.PLUGIN_ID, "could not refresh " + getEditorInput().getName(), e));
		}
	}
}

package com.tibco.cep.studio.dashboard.ui.chartcomponent.editor;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.core.search.SearchResult;
import com.tibco.cep.studio.core.search.SearchUtils;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalPreviewableComponent;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.util.LocalECoreUtils;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.ChartEditingController;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.ControllablePage;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.DashboardChartPlugin;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.editor.pages.ChartEditorAlertsPage;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.editor.pages.ChartEditorDataPage;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.editor.pages.ChartEditorOptionsPage;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.editor.pages.ChartEditorTypePage;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.editor.pages.ChartRelatedEditorPage;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedComponent;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartType;
import com.tibco.cep.studio.dashboard.ui.editors.AbstractDBFormEditor;
import com.tibco.cep.studio.dashboard.ui.utils.BEViewsURIHandler;
import com.tibco.cep.studio.dashboard.ui.utils.DashboardResourceUtils;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

public class ChartEditor extends AbstractDBFormEditor implements ISelectionProvider {

	public static final String EDITOR_ID = "com.tibco.cep.studio.dashboard.ui.chartcomponent.editor.ChartEditor";

	private ChartEditingController chartEditingController;
	private List<ChartEditorBasePage> pages;

	private ChartType lastKnownChartType;

	private LocalElement nativeComponent;

	private List<ISelectionChangedListener> listeners;

	private ISelection selection;


	public ChartEditor() {
		super();
		pages = new LinkedList<ChartEditorBasePage>();
		listeners = new LinkedList<ISelectionChangedListener>();
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		getSite().setSelectionProvider(this);
		setSelection(new StructuredSelection(getLocalElement()));
	}

	protected LocalElement loadLocalElement(IEditorInput input, IFile file) throws PartInitException {
		try {
			LocalElement element = super.loadLocalElement(input, file);
			chartEditingController = new ChartEditingController();
			chartEditingController.setOriginalElement(new LocalUnifiedComponent(element.getParent(), (LocalPreviewableComponent) element));
			lastKnownChartType = chartEditingController.getOriginalChartType();
			return chartEditingController.getOriginalElement();
			// return element;
		} catch (Exception e) {
			throw new PartInitException("could not initialize editor for " + file.getName(), e);
		}
	}

	@Override
	protected void addPages() {
		try {
			ChartEditorTypePage masterPage = new ChartEditorTypePage(this, getLocalElement());
			chartEditingController.setMasterPage(masterPage);
			pages.add(masterPage);

			ChartEditorOptionsPage optionsPage = new ChartEditorOptionsPage(this, getLocalElement());
			chartEditingController.addSlavePage(optionsPage);
			pages.add(optionsPage);

			ChartEditorDataPage dataSettingsPage = new ChartEditorDataPage(this, getLocalElement());
			chartEditingController.addSlavePage(dataSettingsPage);
			pages.add(dataSettingsPage);

			ChartEditorAlertsPage alertsSettingsPage = new ChartEditorAlertsPage(this, getLocalElement());
			chartEditingController.addSlavePage(alertsSettingsPage);
			pages.add(alertsSettingsPage);

			ChartRelatedEditorPage relatedSettingsPage = new ChartRelatedEditorPage(this, getLocalElement());
			chartEditingController.addSlavePage(relatedSettingsPage);
			pages.add(relatedSettingsPage);

			for (ChartEditorBasePage page : pages) {
				addPage(page);
			}
		} catch (PartInitException e) {
			DashboardChartPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, DashboardChartPlugin.PLUGIN_ID, "could not initialize editor for " + getEditorInput().getName(), e));
			// disable all the pages
			for (ChartEditorBasePage page : pages) {
				page.disable();
			}
		} catch (Exception e) {
			DashboardChartPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, DashboardChartPlugin.PLUGIN_ID, "could not initialize editor for " + getEditorInput().getName(), e));
			// disable all the pages
			for (ChartEditorBasePage page : pages) {
				page.disable();
			}
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		try {
			super.doSave(monitor);
			ChartType currentChartType = chartEditingController.getCurrentChartType();
			if (lastKnownChartType.getFamily().equals(currentChartType.getFamily()) == false) {
				// we have a translation on our hand, re-sync all referrer(s) to
				// make sure EMF stores the new EMF type in the href
				resyncAllReferers();
				lastKnownChartType = currentChartType;
			}
		} catch (Exception e) {
			chartEditingController.logAndAlert(getTitle(), new Status(IStatus.WARNING, chartEditingController.getPluginId(), "could not update all referencers of " + getEditorInput().getName(), e));
		} finally {
			nativeComponent = null;
		}
	}

	@Override
	protected LocalElement synchronize() throws Exception {
		nativeComponent = chartEditingController.synchronize();
		return nativeComponent;
	}

	@Override
	public void dispose() {
		//fire an empty selection
		setSelection(StructuredSelection.EMPTY);
		getSite().setSelectionProvider(null);
		selection = null;
		listeners.clear();
		listeners = null;
		if (chartEditingController != null) {
			chartEditingController.dispose();
		}
		super.dispose();
	}

	@Override
	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);
		chartEditingController.setActivePage((ControllablePage) getActivePageInstance());
	}

	private void resyncAllReferers() throws Exception {
		IProject project = StudioResourceUtils.getProjectForInput(getEditorInput());
		String baseURI = DashboardResourceUtils.getCurrentProjectBaseURI(project);
		LocalECoreFactory localECoreFactory = LocalECoreFactory.getInstance(project);
		BEViewsElement element = (BEViewsElement) nativeComponent.getEObject();
		XMIResource.URIHandler handler = new BEViewsURIHandler();
		SearchResult searchResult = SearchUtils.searchIndex(element, project.getName(), element.getName(), null);
		List<EObject> exactMatches = searchResult.getExactMatches();
		for (EObject object : exactMatches) {
			LocalElement referringElement = LocalECoreFactory.toLocalElement(localECoreFactory, object);
			referringElement.replaceElementByObject(element.getGUID(), nativeComponent);
			referringElement.synchronize();
			if (referringElement.isModified() == true) {
				Entity referringEObject = (Entity) referringElement.getEObject();
				DashboardResourceUtils.persistEntity(referringEObject, baseURI, project, handler, null);
				IResource resource = project.findMember(referringEObject.getFullPath() + "." + LocalECoreUtils.getExtensionFor(referringEObject));
				if (resource != null) {
					resource.refreshLocal(IResource.DEPTH_ZERO, null);
				}
			}
		}
	}

	@Override
	protected String[] getInterestingElementTypes() {
		return new String[] { BEViewsElementNames.METRIC, BEViewsElementNames.DATA_SOURCE, BEViewsElementNames.TEXT_COMPONENT, BEViewsElementNames.CHART_COMPONENT , BEViewsElementNames.SKIN};
	}

	@Override
	protected void handleOutsideElementChange(int change, LocalElement element) {
		try {
			if (element.equals(getLocalElement()) == true) {
				chartEditingController.sync(element);
			}
			chartEditingController.getActivePage().refresh();
		} catch (Exception e) {
			chartEditingController.log(new Status(IStatus.WARNING, chartEditingController.getPluginId(), "could not refresh " + getEditorInput().getName(), e));
		}
	}

	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		if (listeners.contains(listener) == false) {
			listeners.add(listener);
		}
	}

	@Override
	public ISelection getSelection() {
		return selection;
	}

	@Override
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void setSelection(ISelection selection) {
		this.selection = selection;
		for (int i = 0; i < listeners.size() ; i++) {
			listeners.get(i).selectionChanged(new SelectionChangedEvent(this,selection));
		}
	}

}
package com.tibco.cep.studio.ui.editors.events;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.designtime.core.model.event.TimeEvent;
import com.tibco.cep.studio.ui.forms.AbstractEventSaveableEditorPart;
import com.tibco.cep.studio.ui.notification.EventAdapterImpl;

/**
 * 
 * @author sasahoo
 *
 */
public class TimeEventFormEditor extends AbstractEventSaveableEditorPart {

	public final static String ID = "com.tibco.cep.studio.entities.editor.timeevent";
	public TimeEventFormViewer timeEventFormViewer;
	
	protected void addFormPage() throws PartInitException {
		timeEventFormViewer = new TimeEventFormViewer(this);
		timeEventFormViewer.createPartControl(getContainer());
		pageIndex = addPage(timeEventFormViewer.getControl());
		this.setActivePage(0);
		this.setForm(timeEventFormViewer.getForm());
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		this.site = site;
		if (input instanceof FileEditorInput) {
			file = ((FileEditorInput)input).getFile();
			createModel(input);
			TimeEvent event = getTimeEvent();
			event.eAdapters().add(getAdapter());
 			setTimeEvent(event);
			setProject(file.getProject());
			timeEventFormEditorInput = new TimeEventFormEditorInput(file, (TimeEvent)getEntity());
			super.init(site, timeEventFormEditorInput);
			site.getPage().addPartListener(partListener);
		} else {
			super.init(site, input);
		}
	}
	
	@Override
	protected Adapter getAdapter() {
		if (adapter == null) {
			adapter = new EventAdapterImpl(this);
		}
		return adapter;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor progressMonitor) {
		super.doSave();
		timeEventFormViewer.refreshScheduleWidgets();
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#dispose()
	 */
	@Override
	public void dispose(){
		try{
			if (timeEventFormViewer != null) {
				timeEventFormViewer.dispose();
			}
			removeAdapter();
			getSite().getPage().removePartListener(partListener);
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * This listens for when the outline becomes active
	 */
	protected IPartListener partListener = new IPartListener() {
		public void partActivated(IWorkbenchPart p) {
			if (p == TimeEventFormEditor.this) {
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

	@Override
	public String getPerspectiveId() {
		return "com.tibco.cep.studio.application.perspective";
	}
}
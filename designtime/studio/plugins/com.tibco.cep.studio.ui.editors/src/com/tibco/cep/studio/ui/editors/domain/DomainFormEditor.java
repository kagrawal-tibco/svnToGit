/**
 * 
 */
package com.tibco.cep.studio.ui.editors.domain;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.designtime.core.model.domain.Domain;

/**
 * @author aathalye
 *
 */
public class DomainFormEditor extends AbstractDomainSaveableEditorPart {

	public final static String ID = "com.tibco.cep.editors.ui.editor.domain";
	
	protected void addFormPage() throws PartInitException {
		domainFormViewer = new DomainFormViewer(this);
		domainFormViewer.createPartControl(getContainer());
		pageIndex = addPage(domainFormViewer.getControl());
		this.setActivePage(0);
		this.setForm(domainFormViewer.getForm());
	}
	
	@Override
	public void init(IEditorSite site, IEditorInput input)throws PartInitException {
		this.site = site;
		if (input instanceof FileEditorInput) {
			file = ((FileEditorInput)input).getFile();
			createModel(input);
			Domain domain = getDomain();
			domain.eAdapters().add(getAdapter());
 			setDomain(domain);
 			setProject(file.getProject());
			domainFormEditorInput = new DomainFormEditorInput(file, domain);
			super.init(site, domainFormEditorInput);
			site.getPage().addPartListener(partListener);
		} else {
			super.init(site, input);
		}
	}
	
	@Override
	protected Adapter getAdapter() {
		if (adapter == null) {
			adapter = new DomainAdapterImpl(this);
		}
		return adapter;
	}

	/**
	 * This is for implementing {@link IEditorPart} and simply saves the model
	 * file.
	 */
	@Override
	public void doSave(IProgressMonitor progressMonitor) {
		boolean isValid = new DomainSaveAction().doSave(domainFormViewer);
		if(isValid){
			super.doSave(progressMonitor);
		}
	}
	
	@Override
	public void dispose() {
		if (domainFormViewer != null) {
			domainFormViewer.dispose();
		}
		removeAdapter();
		super.dispose();
	}
	
	/**
	 * This listens for when the outline becomes active
	 */
	protected IPartListener partListener = new IPartListener() {
		public void partActivated(IWorkbenchPart p) {
			if (p == DomainFormEditor.this) {
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
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart#doRefresh(org.eclipse.core.resources.IFile)
	 */
	public void doRefresh(IFile file){
		createModel(file);
		setEntity(null);
		Domain domain = getDomain();
		domain.eAdapters().add(new DomainAdapterImpl(this));
        ((DomainFormEditorInput)getEditorInput()).setDomain(domain);
        domainFormViewer.doRefresh(domain);
	}

	@Override
	public String getPerspectiveId() {
		return "com.tibco.cep.studio.application.perspective";
	}
}
package com.tibco.cep.studio.ui.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.designtime.core.model.rule.RuleTemplateView;
import com.tibco.cep.studio.ui.forms.AbstractRuleTemplateViewSaveableEditorPart;

/**
 * 
 * @author sasahoo
 *
 */
public class RuleTemplateViewFormEditor extends AbstractRuleTemplateViewSaveableEditorPart {

	public final static String ID = "com.tibco.cep.ruletemplateview.editors.formeditor";
	
	private RuleTemplateViewFormEditorInput ruleTemplateViewFormEditorInput;
	private RuleTemplateViewFormDesignViewer ruleTemplateViewFormDesignViewer;

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#addFormPage()
	 */
	protected void addFormPage()  {
		ruleTemplateViewFormDesignViewer = new RuleTemplateViewFormDesignViewer(this);
		ruleTemplateViewFormDesignViewer.createPartControl(getContainer());
		pageIndex = addPage(ruleTemplateViewFormDesignViewer.getControl());
		this.setActivePage(0);
		this.setForm(ruleTemplateViewFormDesignViewer.getForm());
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#updateTitle()
	 */
	protected void updateTitle() {
		IEditorInput input = getEditorInput();
		setPartName(input.getName());
		setTitleToolTip(input.getToolTipText());
		if(ruleTemplateViewFormEditorInput!=null)
			setTitleImage(EditorsUIPlugin.getDefault().getImage("icons/rulesTemplateView.png"));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.MultiPageEditorPart#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		this.site = site;
		if (input instanceof FileEditorInput) {
			file = ((FileEditorInput)input).getFile();
			createModel(input);
			RuleTemplateView view = getRuleTemplateView();
			view.eAdapters().add(getAdapter());
			setProject(file.getProject());
			ruleTemplateViewFormEditorInput = new RuleTemplateViewFormEditorInput(file, view);
			super.init(site, ruleTemplateViewFormEditorInput);
			site.getPage().addPartListener(partListener);
		} 
		 else {
			super.init(site, input);
		}
	}

	@Override
	protected Adapter getAdapter() {
		if (adapter == null) {
			adapter = new Adapter() {
			
				@Override
				public void setTarget(Notifier newTarget) {
				}
			
				@Override
				public void notifyChanged(Notification notification) {
				}
			
				@Override
				public boolean isAdapterForType(Object type) {
					return false;
				}
			
				@Override
				public Notifier getTarget() {
					return null;
				}
			};
//			adapter = new RuleTemplateViewAdapterImpl(this);
		}
		return adapter;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#dispose()
	 */
	@Override
	public void dispose(){
		try{
			removeAdapter();
			if (getSite() != null && getSite().getPage() != null) {
				getSite().getPage().removePartListener(partListener);
			}
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
			ruleTemplateViewFormEditorInput = null;
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor progressMonitor) {
		super.doSave();
	}
	
	@Override
	protected void createPages() {
		createUIEditorPage();
		if (getPageCount() == 1 && getContainer() instanceof CTabFolder) {
			((CTabFolder) getContainer()).setTabHeight(0);
		}
		updateTitle();
	}
	
	/**
	 * This listens for when the outline becomes active
	 */
	protected IPartListener partListener = new IPartListener() {
		public void partActivated(IWorkbenchPart p) {
			if (p == RuleTemplateViewFormEditor.this) {
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
	protected void handleActivate() {
		super.handleActivate();
		ruleTemplateViewFormDesignViewer.update();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart#doRefresh(org.eclipse.core.resources.IFile)
	 */
	public void doRefresh(IFile file){
		createModel(file);
		setEntity(null);
		RuleTemplateView view = getRuleTemplateView();			
//        view.eAdapters().add(new RuleTemplateViewAdapterImpl(this));
        ((RuleTemplateViewFormEditorInput)getEditorInput()).setRuleTemplateView(view);
        ruleTemplateViewFormDesignViewer.doRefresh(view);
	}
	
	@Override
	public void setFocus() {
		super.setFocus();
	}

	@Override
	public String getPerspectiveId() {
		return "com.tibco.cep.studio.application.perspective";
	}
}
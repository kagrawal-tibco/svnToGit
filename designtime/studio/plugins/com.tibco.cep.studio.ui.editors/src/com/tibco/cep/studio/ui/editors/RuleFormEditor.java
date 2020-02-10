package com.tibco.cep.studio.ui.editors;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.studio.ui.editors.rules.RulesEditor;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants;


public class RuleFormEditor extends AbstractRuleFormEditor {

	public static final String ID =  "com.tibco.cep.editors.ui.rulesEditor";
	public RuleFormViewer ruleFormViewer;
	private Boolean isFormFirstPage;
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#createPages()
	 */
	@Override
	protected void createPages() {
		createUIEditorPage();
		updateTitle();
		this.setForm(ruleFormViewer.getForm());
		setCatalogFunctionDrag(true);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.MultiPageEditorPart#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		this.site = site;
		if (input instanceof FileEditorInput) {
			super.init(site, input);
			site.getPage().addPartListener(partListener);
			EditorsUIPlugin.getDefault().getPreferenceStore().addPropertyChangeListener(this);
		} else{
			super.init(site, input);}
	}

	protected void createUIEditorPage() {
		try {
			addFormPage();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String getShowFormPagePreferenceConstant() {
		return StudioPreferenceConstants.RULE_FORM_INTIAL_PAGE;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#addFormPage()
	 */
	protected void addFormPage() throws PartInitException {
		isFormFirstPage = EditorsUIPlugin.getDefault().getPreferenceStore().getBoolean(getShowFormPagePreferenceConstant());
		ruleFormViewer = new RuleFormViewer(this);
		ruleFormViewer.createPartControl(getContainer());
		rulesEditor = new RulesEditor(this);
		rulesEditor.addPropertyListener(new IPropertyListener() {
		
			public void propertyChanged(Object source, int propId) {
				if (propId == IEditorPart.PROP_DIRTY && source instanceof RulesEditor) {
					setDirty(((RulesEditor)source).isDirty());
				}
			}
		});
//		rulesEditor.addReconcileListener(this);

		if(isFormFirstPage){
			pageIndex = addPage(ruleFormViewer.getControl());
			setPageText(pageIndex, Messages.getString("RULES_EDITOR_FORM_TAB_TITLE")); 
			setPageImage(pageIndex, EditorsUIPlugin.getDefault().getImage("icons/form_16x16.png"));
			
			pageIndex = addPage(rulesEditor, getEditorInput());
	        setPageText(pageIndex, Messages.getString("RULES_EDITOR_SOURCE_TAB_TITLE"));
	        setPageImage(pageIndex, PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE));
	        ruleFormViewer.refresh();
		}else{
			pageIndex = addPage(rulesEditor, getEditorInput());
	        setPageText(pageIndex, Messages.getString("RULES_EDITOR_SOURCE_TAB_TITLE"));
	        setPageImage(pageIndex, PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE));
	        
			pageIndex = addPage(ruleFormViewer.getControl());
			setPageText(pageIndex, Messages.getString("RULES_EDITOR_FORM_TAB_TITLE")); 
			setPageImage(pageIndex, EditorsUIPlugin.getDefault().getImage("icons/form_16x16.png"));
		}
		this.setActivePage(0);
		
		if(!ruleFormViewer.isEditorEnabled()){
			rulesEditor.setEnabled(false);
		}
	}

	@Override
	public void dispose() {
		if (ruleFormViewer != null) {
			ruleFormViewer.dispose();
			ruleFormViewer = null;
		}
		super.dispose();
		EditorsUIPlugin.getDefault().getPreferenceStore().removePropertyChangeListener(this);
		getSite().getPage().removePartListener(partListener);
		partListener = null;
	}
	
	/**
	 * This listens for when the outline becomes active
	 */
	protected IPartListener partListener = new IPartListener() {
		public void partActivated(IWorkbenchPart p) {
			if (p == RuleFormEditor.this) {
				if(isFormFirstPage && getActivePage() == 0) {
					ruleFormViewer.refreshRank();
				}
				if(!isFormFirstPage && getActivePage() == 1) {
					ruleFormViewer.refreshRank();
				}
				handleRuleActivate();
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
	protected AbstractRuleEntitiesFormViewer getFormViewer() {
		return ruleFormViewer;
	}

	@Override
	public void setFocus() {
		super.setFocus();
		ruleFormViewer.refreshRulesFormSourceViewerConfiguration(); // re-reconcile
	}

	@Override
	public String getPerspectiveId() {
		return "com.tibco.cep.studio.application.perspective";
	}

}
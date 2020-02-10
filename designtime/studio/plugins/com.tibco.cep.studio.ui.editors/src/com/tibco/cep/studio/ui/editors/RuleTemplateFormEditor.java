package com.tibco.cep.studio.ui.editors;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.studio.ui.editors.rules.RulesEditor;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants;

public class RuleTemplateFormEditor extends AbstractRuleFormEditor {

	public static final String ID =  "com.tibco.cep.editors.ui.rulesTemplateEditor";
	public RuleTemplateFormViewer ruleFormViewer;
	private RuleTemplateBindingsFormViewer ruleBindingViewer;
	private Boolean isFormFirstPage;
	private RuleTemplateDisplayFormViewer ruleDisplayViewer;
	
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
		return StudioPreferenceConstants.RULE_TEMPLATE_FORM_INTIAL_PAGE;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#addFormPage()
	 */
	protected void addFormPage() throws PartInitException {
		isFormFirstPage = EditorsUIPlugin.getDefault().getPreferenceStore().getBoolean(getShowFormPagePreferenceConstant());
		ruleFormViewer = new RuleTemplateFormViewer(this);
		ruleFormViewer.createPartControl(getContainer());
		ruleBindingViewer = new RuleTemplateBindingsFormViewer(this);
		ruleBindingViewer.createPartControl(getContainer());
		ruleDisplayViewer = new RuleTemplateDisplayFormViewer(this);
		ruleDisplayViewer.createPartControl(getContainer());
		rulesEditor = new RulesEditor(this);
		rulesEditor.addPropertyListener(dirtyListener);
//		rulesEditor.addReconcileListener(this);

		if(isFormFirstPage) {
			pageIndex = addPage(ruleFormViewer.getControl());
			setPageText(pageIndex, Messages.getString("RULES_EDITOR_FORM_TAB_TITLE")); 
			setPageImage(pageIndex, EditorsUIPlugin.getDefault().getImage("icons/form_16x16.png"));

			pageIndex = addPage(ruleBindingViewer.getControl());
			setPageText(pageIndex, Messages.getString("RULES_BINDINGS_EDITOR_FORM_TAB_TITLE")); 
			setPageImage(pageIndex, EditorsUIPlugin.getDefault().getImage("icons/form__bind_16x16.png"));

			pageIndex = addPage(ruleDisplayViewer.getControl());
			setPageText(pageIndex, Messages.getString("RULES_DISPLAY_EDITOR_FORM_TAB_TITLE")); 
			setPageImage(pageIndex, EditorsUIPlugin.getDefault().getImage("icons/locale-alt16.png"));
			
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
			
			pageIndex = addPage(ruleBindingViewer.getControl());
			setPageText(pageIndex, Messages.getString("RULES_BINDINGS_EDITOR_FORM_TAB_TITLE")); 
			setPageImage(pageIndex, EditorsUIPlugin.getDefault().getImage("icons/form__bind_16x16.png"));
			
			pageIndex = addPage(ruleDisplayViewer.getControl());
			setPageText(pageIndex, Messages.getString("RULES_DISPLAY_EDITOR_FORM_TAB_TITLE")); 
			setPageImage(pageIndex, EditorsUIPlugin.getDefault().getImage("icons/locale-alt16.png"));
		}
		this.setActivePage(0);
	}

	@Override
	protected void refreshFormViewers() {
		super.refreshFormViewers();
		ruleBindingViewer.refresh();
		ruleDisplayViewer.refresh();
	}

	protected void setSourceAsActivePage() {
		Boolean isFormFirstPage = EditorsUIPlugin.getDefault().getPreferenceStore().getBoolean(getShowFormPagePreferenceConstant());
		if (isFormFirstPage) {
			setActivePage(2);
		} else {
			setActivePage(0);
		}
	}

	@Override
	public void dispose() {
		if (ruleFormViewer != null) {
			ruleFormViewer.dispose();
		}
		if (ruleBindingViewer != null) {
			ruleBindingViewer.dispose();
		}
		if (ruleDisplayViewer != null) {
			ruleDisplayViewer.dispose();
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
			if (p == RuleTemplateFormEditor.this) {
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

	protected void initPages() throws PartInitException {
		removePage(2);removePage(1);removePage(0);addFormPage();
	}

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
	
	@Override
	protected boolean isFormPage(int index) {
		Boolean isFormFirstPage = EditorsUIPlugin.getDefault().getPreferenceStore().getBoolean(getShowFormPagePreferenceConstant());
		if(isFormFirstPage && (index == 0 || index == 1)){
			return true;
		} else if (!isFormFirstPage && index >= 1) {
			return true;
		}
		return false;
	}

	@Override
	protected void pageChange(int newPageIndex) {
		if (newPageIndex == 2) {
			ruleBindingViewer.refresh();
			ruleDisplayViewer.refresh();
			super.pageChange(newPageIndex);
		} else {
			super.pageChange(newPageIndex);
		}
	}

}
package com.tibco.cep.studio.ui.editors;

import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.swt.graphics.Image;
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

import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.editors.rules.RulesEditor;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

public class RuleFunctionFormEditor extends AbstractRuleFormEditor {

	public static final String ID =  "com.tibco.cep.editors.ui.rulefunctionEditor";
    public RuleFunctionFormViewer ruleFunctionFormViewer;
    
    private Image rfImage;

	@Override
	protected void createPages() {
		createUIEditorPage();
		updateTitle();
		this.setForm(ruleFunctionFormViewer.getForm());
		setCatalogFunctionDrag(true);
	}
	
	/**
	 * @param isVirtual
	 * @param name
	 */
	public void setTitleAndImage(boolean isVirtual, String name){
		rfImage = EditorsUIPlugin.getDefault().getImage("icons/rule-function.png");
		String title = Messages.getString("rulefunction.editor.title") + " " + name;
		if(isVirtual){
			rfImage = new DecorationOverlayIcon(rfImage, 
					StudioUIPlugin.getImageDescriptor("icons/virtualrulefunction_overlay.gif"),IDecoration.TOP_LEFT).createImage();
			title = Messages.getString("rulefunction.virtual.editor.title") + " " +name;
			
		}	
		setTitleImage(rfImage);
		ruleFunctionFormViewer.setTitleAndImage(rfImage, title);
	}
	
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		this.site = site;
		if (input instanceof FileEditorInput) {
			rfImage = EditorsUIPlugin.getDefault().getImage("icons/rule-function.png");
			if(StudioResourceUtils.isVirtual(((FileEditorInput)input).getFile())){
				rfImage = new DecorationOverlayIcon(rfImage, 
						StudioUIPlugin.getImageDescriptor("icons/virtualrulefunction_overlay.gif"),IDecoration.TOP_LEFT).createImage();
				setTitleImage(rfImage);
			}else{
				setTitleImage(rfImage);
			}
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
		return StudioPreferenceConstants.RULE_FUNCTION_FORM_INTIAL_PAGE;
	}

	protected void addFormPage() throws PartInitException {
		Boolean isFormFirstPage = EditorsUIPlugin.getDefault().getPreferenceStore().getBoolean(getShowFormPagePreferenceConstant());
		ruleFunctionFormViewer = new RuleFunctionFormViewer(this);
		ruleFunctionFormViewer.createPartControl(getContainer());
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
			pageIndex = addPage(ruleFunctionFormViewer.getControl());
			setPageText(pageIndex, Messages.getString("RULES_EDITOR_FORM_TAB_TITLE")); 
			setPageImage(pageIndex, EditorsUIPlugin.getDefault().getImage("icons/form_16x16.png"));
			
			pageIndex = addPage(rulesEditor, getEditorInput());
	        setPageText(pageIndex, Messages.getString("RULES_EDITOR_SOURCE_TAB_TITLE"));
	        setPageImage(pageIndex, PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE));
	        ruleFunctionFormViewer.refresh();
		}else{
			pageIndex = addPage(rulesEditor, getEditorInput());
	        setPageText(pageIndex, Messages.getString("RULES_EDITOR_SOURCE_TAB_TITLE"));
	        setPageImage(pageIndex, PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE));
	        
			pageIndex = addPage(ruleFunctionFormViewer.getControl());
			setPageText(pageIndex, Messages.getString("RULES_EDITOR_FORM_TAB_TITLE")); 
			setPageImage(pageIndex, EditorsUIPlugin.getDefault().getImage("icons/form_16x16.png"));
		}
		this.setActivePage(0);
		
		if(!ruleFunctionFormViewer.isEditorEnabled()){
			rulesEditor.setEnabled(false);
		}
	}
	
	@Override
	public void dispose() {
		if (ruleFunctionFormViewer != null) {
			ruleFunctionFormViewer.dispose();
			ruleFunctionFormViewer = null;
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
			if (p == RuleFunctionFormEditor.this) {
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

	public Image getRfImage() {
		return rfImage;
	}
	
	@Override
	protected AbstractRuleEntitiesFormViewer getFormViewer() {
		return ruleFunctionFormViewer;
	}

	@Override
	public void setFocus() {
		super.setFocus();
		ruleFunctionFormViewer.refreshRulesFormSourceViewerConfiguration(); // re-reconcile
	}

	@Override
	public String getPerspectiveId() {
		return "com.tibco.cep.studio.application.perspective";
	}

}
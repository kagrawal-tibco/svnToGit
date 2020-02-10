package com.tibco.cep.bpmn.ui.editors.bpmnPalette;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.tibco.cep.studio.ui.util.StudioImages;

/*
 * @author   mgoel
 */

public class BpmnPalettePage extends FormPage {
	
//	private BpmnPaletteConfigurationModel model;
	private BpmnPaletteConfigurationModelMgr modelmgr;
	private BpmnPaletteConfigurationEditor editor;
	@SuppressWarnings("unused")
	private Composite editorParent;
	private BpmnPaletteConfigBlock block;
	
	public BpmnPalettePage( BpmnPaletteConfigurationEditor editor, BpmnPaletteConfigurationModelMgr mdlmgr) {
		super(editor, "BpmnPalette", getName());
		this.editor = editor;
		this.modelmgr = mdlmgr;
//		this.model=mdlmgr.getModel();
		block = new BpmnPaletteConfigBlock(this, this.modelmgr);
	}
	
	protected void createFormContent(final IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		form.setBackgroundImage(StudioImages.getImage(StudioImages.IMG_FORM_BG));
		form.setText(editor.getTitle());
		form.setImage(editor.getTitleImage());
		block.createContent(managedForm);
	}
	
	public static String getName() {
		return ("Palette");
	}
	
	public void update() {
		this.modelmgr.reloadModel();
//		this.model = modelmgr.getModel();
		block.updateBlock();
	}


}

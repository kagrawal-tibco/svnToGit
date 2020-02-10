package com.tibco.cep.bpmn.ui.editors.bpmnPalette;

import org.eclipse.jface.wizard.Wizard;

import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroup;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroupItem;


public class IconSelectionWizard extends Wizard {

	private BpmnPaletteConfigurationModelMgr mdlmgr;
	private IconSelectionWizardPage fIconSelectionWizardPage;
	private BpmnPaletteItemConfigPage fBpmnPaletteItemConfigPage;
	private Object selectedObject;
	
	/**
	 * @param title
	 * @param bpmnPaletteItemConfigPage
	 */
	public IconSelectionWizard(String title, BpmnPaletteItemConfigPage bpmnPaletteItemConfigPage) {
		setWindowTitle(title);
		this.fBpmnPaletteItemConfigPage = bpmnPaletteItemConfigPage;
		this.mdlmgr = bpmnPaletteItemConfigPage.getBpmnPaletteConfigurationModelMgr();
		this.selectedObject = bpmnPaletteItemConfigPage.getSelectedObject();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		this.fIconSelectionWizardPage = new IconSelectionWizardPage("", mdlmgr);
		addPage(this.fIconSelectionWizardPage);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		String data = (String) fIconSelectionWizardPage.getCanvas().getData();
		if(data != null){
			if(selectedObject instanceof BpmnPaletteGroupItem){
				((BpmnPaletteGroupItem) selectedObject).setIcon(data);

			} else if(selectedObject instanceof BpmnPaletteGroup){
				((BpmnPaletteGroup) selectedObject).setIcon(data);
			}
			fBpmnPaletteItemConfigPage.update();
		}
		
		return true;
	}

}
package com.tibco.cep.studio.ui.xml.wizards;

import org.eclipse.swt.custom.SashForm;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.IExpansionListener;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

/**
 * 
 * @author sasahoo
 *
 */
public class MapperSectionsExpansionListener implements IMapperConstants,IExpansionListener{

	private Section inputSection;
	private Section functionSection;
	private ScrolledForm form;
	private SashForm sashForm;
	
	/**
	 * 
	 * @param page
	 */
	public MapperSectionsExpansionListener(AbstractMapperWizardPage page){
		form = page.getForm();
		inputSection = page.getInputSection();
		functionSection = page.getFunctionSection();
		sashForm = page.getSashForm();
	}
	
	/**
	 * 
	 * @param e
	 */
	public void expansionStateChanged(ExpansionEvent e) {
	
		if(e.getSource() == functionSection)	{
			if (functionSection.isExpanded()) {
				if(inputSection.isExpanded()){
					sashForm.setWeights(new int[] { WSfn, WSin });
				}else{
					sashForm.setWeights(new int[] { WSfn,WSin  });
				}
			} else {
				if(inputSection.isExpanded()){
					sashForm.setWeights(new int[] {  Wsm, Wst-Wsm });
				}else{
					sashForm.setWeights(new int[] {  Wsm, Wst-Wsm-Wsc});
				}
			}
		}
		if(e.getSource() == inputSection)	{
			
			if (inputSection.isExpanded()) {
				if(functionSection.isExpanded()){
					sashForm.setWeights(new int[] {WSfn,WSin });
				}else{
					sashForm.setWeights(new int[] { Wsm,Wst-Wsm  });
				}
			} else {
				if(functionSection.isExpanded()){
					sashForm.setWeights(new int[] {WSfn,WSin });
				}else{
					sashForm.setWeights(new int[] { Wsm,Wst-Wsm-Wsc});
				}
			}
		}
		form.reflow(true);
	}

	public void expansionStateChanging(ExpansionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}

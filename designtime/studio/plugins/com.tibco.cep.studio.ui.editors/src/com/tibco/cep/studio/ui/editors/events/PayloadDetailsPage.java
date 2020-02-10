/**
 * 
 */
package com.tibco.cep.studio.ui.editors.events;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;

import com.tibco.cep.studio.ui.forms.advancedEventPayloadEditor.AdvancedEventPayloadTree;

/**
 * @author mgujrath
 *
 */
public class PayloadDetailsPage extends AbstractPayloadDetailsPage{
	
	
	public PayloadDetailsPage(AdvancedEventPayloadTree m_tree, AdvancedEventMasterDetailFormDesignViewer advancedEventMasterDetailFormDesignViewer, String type){
		this.m_tree=m_tree;
		this.type=type;
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#initialize(org.eclipse.ui.forms.IManagedForm)
	 */
	public void initialize(IManagedForm managedForm) {
		this.managedForm = managedForm;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#inputChanged(org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void selectionChanged(IFormPart part, ISelection selection) {
		try{
			IStructuredSelection sel = (IStructuredSelection)selection;
			update();
		}catch(Exception e){
			e.printStackTrace();
		}
	}



	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public void commit(boolean onSave) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public boolean setFormInput(Object input) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public boolean isStale() {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}
	
	public void update(){
		payloadDetailsPageBook.showPage(type);
	}

	
}

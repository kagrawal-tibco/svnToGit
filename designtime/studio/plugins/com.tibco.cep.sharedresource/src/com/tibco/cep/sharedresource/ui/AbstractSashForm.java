package com.tibco.cep.sharedresource.ui;

import org.eclipse.swt.custom.SashForm;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.ScrolledForm;

/*
@author ssailapp
@date Oct 2, 2009 3:42:12 PM
 */

public abstract class AbstractSashForm {

	protected ManagedForm managedForm;
    protected SashForm sashForm;
	protected final java.awt.Font font = new java.awt.Font("Tahoma", 0, 11);

    
    /**
     * Defining Orientation Tool bar.
     */
	protected void createToolBarActions() {
		final ScrolledForm form = getForm();

		/*
		Action haction = new Action("hor", Action.AS_RADIO_BUTTON) {
			public void run() {
				sashForm.setOrientation(SWT.HORIZONTAL);
				form.reflow(true);
			}
		};
		haction.setChecked(false);
		haction.setToolTipText("Horizontal orientation");
		haction.setImageDescriptor(SharedResourceImages.getImageDescriptor(SharedResourceImages.IMG_HORIZONTAL));
		Action vaction = new Action("ver", Action.AS_RADIO_BUTTON) {
			public void run() {
				sashForm.setOrientation(SWT.VERTICAL);
				form.reflow(true);
			}
		};
		vaction.setChecked(true);
		vaction.setToolTipText("Vertical orientation");
		vaction.setImageDescriptor(SharedResourceImages.getImageDescriptor(SharedResourceImages.IMG_VERTICAL));
		form.getToolBarManager().add(haction);
		form.getToolBarManager().add(vaction);
		*/
		
		form.updateToolBar();
	}
	
	public ScrolledForm getForm(){
		return managedForm.getForm();
	}
}

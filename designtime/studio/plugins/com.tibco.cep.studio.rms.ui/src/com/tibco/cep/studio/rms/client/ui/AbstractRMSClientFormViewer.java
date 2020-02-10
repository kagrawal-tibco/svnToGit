package com.tibco.cep.studio.rms.client.ui;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.studio.ui.util.StudioImages;

public abstract class AbstractRMSClientFormViewer extends AbstractMasterDetailsFormViewer 
                                                  implements TreeListener, ISelectionChangedListener, SelectionListener{

	protected RMSClientWorklistEditor editor;
	protected Section deatailsSection;
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.tester.result.AbstractMasterDetailsFormViewer#createPartControl(org.eclipse.swt.widgets.Composite, java.lang.String, org.eclipse.swt.graphics.Image)
	 */
	@Override
	protected void createPartControl(Composite container,String title,Image titleImage){
		managedForm = new ManagedForm(container);
		final ScrolledForm form = managedForm.getForm();

		FormToolkit toolkit = managedForm.getToolkit();
		form.setText(title);
		form.setImage(titleImage);
		form.setBackgroundImage(StudioImages.getImage(StudioImages.IMG_FORM_BG));
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.numColumns = 1;
		form.getBody().setLayout(layout);

		createGeneralPart(form, toolkit);

		sashForm = new MDSashForm(form.getBody(), SWT.HORIZONTAL);

		sashForm.setData("form", managedForm);
		toolkit.adapt(sashForm, false, false);
		sashForm.setMenu(form.getBody().getMenu());
		sashForm.setLayoutData(new GridData(GridData.FILL_BOTH));

		createMasterPart(managedForm, sashForm);
		createDetailsPart(managedForm, sashForm);

		hookResizeListener();
		managedForm.initialize();
	}
	
	@Override
	public void treeCollapsed(TreeEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
	}
}
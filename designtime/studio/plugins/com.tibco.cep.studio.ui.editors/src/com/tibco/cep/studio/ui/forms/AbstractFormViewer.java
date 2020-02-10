package com.tibco.cep.studio.ui.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.studio.ui.util.StudioImages;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractFormViewer extends AbstractSashForm {
    
	/**
	 * 
	 * @param form
	 * @param toolkit
	 */
	protected abstract void createGeneralPart(final ScrolledForm form,final FormToolkit toolkit);
    
    /**
     * 
     * @param managedForm
     * @param parent
     */
    protected  void createPropertiesPart(final IManagedForm managedForm, Composite parent){
    	//Override this
    }
    /**
     * 
     * @param managedForm
     * @param parent
     */
    protected  void createExtendedPropertiesPart(final IManagedForm managedForm, Composite parent){
    	//Override this
    }
    
    /**
     * 
     * @param managedForm
     * @param parent
     */
    protected void createPayloadPart(final IManagedForm managedForm, Composite parent){
    	//Override this
    }
    
    protected void createExpiryActionPart(final IManagedForm managedForm, Composite parent) {
    	//Override this
    }
    
    protected void createExtensionContributedParts(final IManagedForm managedForm, Composite parent) {
    	//Override this
    }
    /**
     * This is optional toolbar for the section
     * @param section
     */
    protected void configureSectionToolBar(Section section){
		
    	ToolBar tbar = new ToolBar(section, SWT.FLAT | SWT.HORIZONTAL);
		ToolItem titem = new ToolItem(tbar, SWT.NULL);
		titem.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_CUT));
		titem = new ToolItem(tbar, SWT.PUSH);
		titem.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_COPY));
		titem = new ToolItem(tbar, SWT.SEPARATOR);
		titem = new ToolItem(tbar, SWT.PUSH);
		titem.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE));
		section.setTextClient(tbar);
		
    }
    
    /**
     * 
     * @param container
     */
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
		
		sashForm = new MDSashForm(form.getBody(), SWT.VERTICAL);
		
		sashForm.setData("form", managedForm);
		toolkit.adapt(sashForm, false, false);
		sashForm.setMenu(form.getBody().getMenu());
		sashForm.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		createExpiryActionPart(managedForm, sashForm);
		createPropertiesPart(managedForm, sashForm);
		createPayloadPart(managedForm, sashForm);//for Event
		createExtendedPropertiesPart(managedForm, sashForm);
		createExtensionContributedParts(managedForm, sashForm);
		
		hookResizeListener();
		
		managedForm.initialize();
    }
   
	public Control getControl() {
		return getForm();
	}
}
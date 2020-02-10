package com.tibco.cep.studio.rms.client.ui;

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
public abstract class AbstractMasterDetailsFormViewer extends AbstractSashForm{
	
	/**
	 * 
	 * @param form
	 * @param toolkit
	 */
	protected void createGeneralPart(final ScrolledForm form,final FormToolkit toolkit){
		//TODO
	}

	/**
	 * Details part created by the block. No attempt should be made to access
	 * this field inside <code>createMasterPart</code> because it has not been
	 * created yet and will be <code>null</code>.
	 */
	protected ClientDetailsPart detailsPart;
	
	static final int DRAGGER_SIZE = 40;

	/**
	 * Creates the content of the master/details block inside the managed form.
	 * This method should be called as late as possible inside the parent part.
	 * 
	 * @param contianer
	 *            the container to create the block in
	 * @param title
	 * @param titleImage
	 *            
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
		
		sashForm = new MDSashForm(form.getBody(), SWT.NULL);
		
		sashForm.setData("form", managedForm);
		toolkit.adapt(sashForm, false, false);
		sashForm.setMenu(form.getBody().getMenu());
		sashForm.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		createMasterPart(managedForm, sashForm);
		createDetailsPart(managedForm, sashForm);
		
		hookResizeListener();
		managedForm.initialize();
    }
    
    /**
     * Defining Orientation Tool bar.
     */
    @Override
	protected void createToolBarActions() {
//		final ScrolledForm form = getForm();
//		
//		Action haction = new Action(Messages.getString("hor.orientation"), Action.AS_RADIO_BUTTON) {
//			public void run() {
//				sashForm.setOrientation(SWT.HORIZONTAL);
//				form.reflow(true);
//			}
//		};
//		haction.setChecked(true);
//		haction.setToolTipText(Messages.getString("hor.orientation"));
//		haction.setImageDescriptor(StudioImages.getImageDescriptor(StudioImages.IMG_HORIZONTAL));
//		
//		Action vaction = new Action(Messages.getString("ver.orientation"), Action.AS_RADIO_BUTTON) {
//			public void run() {
//				sashForm.setOrientation(SWT.VERTICAL);
//				form.reflow(true);
//			}
//		};
//		vaction.setChecked(false);
//		vaction.setToolTipText(Messages.getString("ver.orientation"));
//		vaction.setImageDescriptor(StudioImages.getImageDescriptor(StudioImages.IMG_VERTICAL));
//		
//		form.getToolBarManager().add(haction);
//		form.getToolBarManager().add(vaction);
//		form.updateToolBar();
	}
	
	/**
	 * Implement this method to create a master part in the provided parent.
	 * Typical master parts are section parts that contain tree or table viewer.
	 * 
	 * @param managedForm
	 *            the parent form
	 * @param parent
	 *            the parent composite
	 */
	protected void createMasterPart(IManagedForm managedForm,Composite parent){
		//Override this
	}
	
	/**
	 * Implement this method to create a details part in the provided parent.
	 * Typical details parts are section parts that contain tree or table viewer.
	 * 
	 * @param managedForm
	 *            the parent form
	 * @param parent
	 *            the parent composite
	 */
	protected void createDetailsPart(IManagedForm managedForm, Composite parent){
		//Override this
	}

	/**
	 * Implement this method to statically register pages for the expected
	 * object types. This mechanism can be used when there is 1-&gt;1 mapping
	 * between object classes and details pages.
	 * 
	 * @param detailsPart
	 *            the details part
	 */
	protected void registerPages(ClientDetailsPart detailsPart){
		//Override this
	}
	
	public Control getControl() {
		return getForm();
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
}

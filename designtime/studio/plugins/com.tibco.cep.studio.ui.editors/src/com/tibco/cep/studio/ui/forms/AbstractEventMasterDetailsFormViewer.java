package com.tibco.cep.studio.ui.forms;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.util.StudioImages;
import com.tibco.cep.studio.ui.util.ToolBarProvider;

public abstract class AbstractEventMasterDetailsFormViewer extends AbstractSashForm {
	
	protected void createGeneralPart(final ScrolledForm form,final FormToolkit toolkit){
		//TODO
	}

    protected void createExpiryActionPart(final IManagedForm managedForm, Composite parent) {
    	//Override this
    }
	
	protected PayloadDetailsPart payloadDetailsPart;
	
	static final int DRAGGER_SIZE = 40;

	protected ToolItem addRowButton;
	protected ToolItem removeRowButton;
	protected ToolItem duplicateButton;

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
		final ScrolledForm form = getForm();
		
		Action haction = new Action(Messages.getString("hor.orientation"), Action.AS_RADIO_BUTTON) {
			public void run() {
				sashForm.setOrientation(SWT.HORIZONTAL);
				form.reflow(true);
			}
		};
		haction.setChecked(true);
		haction.setToolTipText(Messages.getString("hor.orientation"));
		haction.setImageDescriptor(StudioImages.getImageDescriptor(StudioImages.IMG_HORIZONTAL));
		
		Action vaction = new Action(Messages.getString("ver.orientation"), Action.AS_RADIO_BUTTON) {
			public void run() {
				sashForm.setOrientation(SWT.VERTICAL);
				form.reflow(true);
			}
		};
		vaction.setChecked(false);
		vaction.setToolTipText(Messages.getString("ver.orientation"));
		vaction.setImageDescriptor(StudioImages.getImageDescriptor(StudioImages.IMG_VERTICAL));
		
		form.getToolBarManager().add(haction);
		form.getToolBarManager().add(vaction);
		form.updateToolBar();
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
	protected void registerPages(PayloadDetailsPart detailsPart){
		//Override this
	}
	
	/**
	 * @return
	 */
	public Control getControl() {
		return getForm();
	}
	
	public ToolItem getAddRowButton() {
		return addRowButton;
	}

	public ToolItem getRemoveRowButton() {
		return removeRowButton;
	}
	
	public ToolItem getDuplicateButton() {
		return duplicateButton;
	}
	
	/**
	 * @param parent
	 * @param duplicate
	 * @return
	 */
	protected ToolBar createToolbar(Composite parent, boolean duplicate) {
        ToolBar toolBar = new ToolBar(parent, SWT.HORIZONTAL | SWT.RIGHT | SWT.FLAT);
        toolBar.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
        toolBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	        
        addRowButton = new ToolItem(toolBar, SWT.PUSH);
        Image addImg = StudioUIPlugin.getDefault().getImage(ToolBarProvider.ICON_TOOLBAR_LIST_ADD);
        addRowButton.setImage(addImg);
        addRowButton.setToolTipText("Add");
        addRowButton.setText("Add");
        
        addRowButton.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				add();
			}
		});
        
        removeRowButton = new ToolItem(toolBar, SWT.PUSH);
        Image delImg = StudioUIPlugin.getDefault().getImage(ToolBarProvider.ICON_TOOLBAR_LIST_DELETE);
        removeRowButton.setImage(delImg);
        removeRowButton.setEnabled(false);
        removeRowButton.setToolTipText("Delete");
        removeRowButton.setText("Delete");
        removeRowButton.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				remove();
			}
		});
        
        if (duplicate) {
        	duplicateButton = new ToolItem(toolBar, SWT.PUSH);
        	Image duplImg = EditorsUIPlugin.getDefault().getImage("icons/domain_duplicate.png");
        	duplicateButton.setImage(duplImg);
        	duplicateButton.setToolTipText("Duplicate");
        	duplicateButton.setText("Duplicate");
        	duplicateButton.setEnabled(false);
        	duplicateButton.addListener(SWT.Selection, new Listener() {

        		@Override
        		public void handleEvent(Event event) {
        			duplicate();
        		}
        	});
        }
        toolBar.pack();
        return toolBar;
	}
	
	protected abstract void add();
	protected abstract void remove();
	protected abstract void duplicate();

}
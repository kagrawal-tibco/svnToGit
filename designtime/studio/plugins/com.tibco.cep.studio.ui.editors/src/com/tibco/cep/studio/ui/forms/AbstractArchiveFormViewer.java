package com.tibco.cep.studio.ui.forms;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Panel;

import javax.swing.JRootPane;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
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

import com.tibco.cep.designtime.core.model.archive.ArchiveResource;
import com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource;
import com.tibco.cep.designtime.core.model.archive.EnterpriseArchive;
import com.tibco.cep.designtime.core.model.archive.SharedArchive;
import com.tibco.cep.diagramming.utils.SyncXErrorHandler;
import com.tibco.cep.studio.ui.editors.archives.EnterpriseArchiveEditor;
import com.tibco.cep.studio.ui.editors.archives.IArchiveConstants;
import com.tibco.cep.studio.ui.util.StudioImages;

public abstract class AbstractArchiveFormViewer extends AbstractSashForm implements IArchiveConstants{
    
	protected final java.awt.Font font = new java.awt.Font("Tahoma", 0, 11);
	protected EnterpriseArchiveEditor editor;
	protected EnterpriseArchive archive;
	protected ArchiveResource archiveResource;
	/**
	 * 
	 * @param form
	 * @param toolkit
	 */
	protected abstract void createConfigurationPart(final ScrolledForm form,final FormToolkit toolkit);
    
    /**
     * 
     * @param managedForm
     * @param parent
     */
    protected  void createRulesetsPart(final IManagedForm managedForm, Composite parent){/*Override this*/};
    /**
     * 
     * @param managedForm
     * @param parent
     */
    protected void createInputPart(final IManagedForm managedForm, Composite parent){/*Override this*/};
    
    /**
     * 
     * @param managedForm
     * @param parent
     */
    protected void createStartupShutDownFunctionsPart(final IManagedForm managedForm, Composite parent){/*Override this*/};
    
    /**
     * 
     * @param managedForm
     * @param parent
     */
    protected void createObjectManagementPart(final IManagedForm managedForm, Composite parent){/*Override this*/};
    
    /**
     * 
     * @param managedForm
     * @param parent
     */
    protected void createResourcesPart(final IManagedForm managedForm, Composite parent){/*Override this*/};
    
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
		
		createConfigurationPart(form, toolkit); // for both Business Events Archive and Shared Archive Tabs 
		
		sashForm = new MDSashForm(form.getBody(), SWT.VERTICAL);
		
		sashForm.setData("form", managedForm);
		toolkit.adapt(sashForm, false, false);
		sashForm.setMenu(form.getBody().getMenu());
		sashForm.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		createRulesetsPart(managedForm, sashForm);
		createInputPart(managedForm, sashForm);
		createStartupShutDownFunctionsPart(managedForm, sashForm);
		createObjectManagementPart(managedForm, sashForm);
		createResourcesPart(managedForm, sashForm);//For shared Archives
		
		hookResizeListener();
		
		managedForm.initialize();
    }
   
	public Control getControl() {
		return getForm();
	}
	
	@SuppressWarnings("serial")
	protected Container getSwingContainer(Composite parent) {
		java.awt.Frame frame = SWT_AWT.new_Frame(parent);
		new SyncXErrorHandler().installHandler();
		Panel panel = new Panel(new BorderLayout()) {
			public void update(java.awt.Graphics g) {
				paint(g);
			}
		};
		frame.add(panel);
		JRootPane root = new JRootPane();
		panel.add(root);
		return root.getContentPane();
	}
	
	protected ArchiveResource getArchiveResource() {
		return archiveResource;
	}

	protected void setArchiveResource(ArchiveResource archiveResource) {
		this.archiveResource = archiveResource;
	}
	
	protected EnterpriseArchiveEditor getEditor() {
		return editor;
	}

	protected void setEditor(EnterpriseArchiveEditor editor) {
		this.editor = editor;
	}
	
	protected BusinessEventsArchiveResource businessEventsArchiveResource;

	protected BusinessEventsArchiveResource getBusinessEventsArchiveResource() {
		return businessEventsArchiveResource;
	}

	protected void setBusinessEventsArchiveResource(
			BusinessEventsArchiveResource businessEventsArchiveResource) {
		this.businessEventsArchiveResource = businessEventsArchiveResource;
	}

	protected SharedArchive sharedArchive;

	public SharedArchive getSharedArchive() {
		return sharedArchive;
	}

	public void setSharedArchive(SharedArchive sharedArchive) {
		this.sharedArchive = sharedArchive;
	}
    
	protected EditingDomain getEditingDomain() {
		if (editor instanceof IEditingDomainProvider) {
			IEditingDomainProvider editingDomainProvider = (IEditingDomainProvider) editor;
			return editingDomainProvider.getEditingDomain() ;
		}
		return null ; 
	}

}

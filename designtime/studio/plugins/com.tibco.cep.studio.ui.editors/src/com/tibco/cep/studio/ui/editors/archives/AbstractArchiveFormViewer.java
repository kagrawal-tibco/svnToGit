package com.tibco.cep.studio.ui.editors.archives;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.tibco.cep.designtime.core.model.archive.ArchiveResource;
import com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource;
import com.tibco.cep.designtime.core.model.archive.EnterpriseArchive;
import com.tibco.cep.designtime.core.model.archive.SharedArchive;
import com.tibco.cep.studio.ui.forms.AbstractMasterDetailsFormViewer;
import com.tibco.cep.studio.ui.util.StudioImages;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractArchiveFormViewer extends AbstractMasterDetailsFormViewer implements IArchiveConstants{

	protected EnterpriseArchiveEditor editor;
	protected EnterpriseArchive archive;
	protected BusinessEventsArchiveResource businessEventsArchiveResource;
	protected ArchiveResource archiveResource;
	
	
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

		sashForm = new MDSashForm(form.getBody(), SWT.VERTICAL);

		sashForm.setData("form", managedForm);
		toolkit.adapt(sashForm, false, false);
		sashForm.setMenu(form.getBody().getMenu());
		sashForm.setLayoutData(new GridData(GridData.FILL_BOTH));

		createMasterPart(managedForm, sashForm);
		createDetailsPart(managedForm, sashForm);

		hookResizeListener();
		managedForm.initialize();
	}
	
	protected SharedArchive sharedArchive;
	
	public SharedArchive getSharedArchive() {
		return sharedArchive;
	}

	public void setSharedArchive(SharedArchive sharedArchive) {
		this.sharedArchive = sharedArchive;
	}

	protected BusinessEventsArchiveResource getBusinessEventsArchiveResource() {
		return businessEventsArchiveResource;
	}

	protected void setBusinessEventsArchiveResource(
			BusinessEventsArchiveResource businessEventsArchiveResource) {
		this.businessEventsArchiveResource = businessEventsArchiveResource;
	}

	protected ArchiveResource getArchiveResource() {
		return archiveResource;
	}

	protected void setArchiveResource(ArchiveResource archiveResource) {
		this.archiveResource = archiveResource;
	}

	protected TableViewer viewer;

	public TableViewer getViewer() {
		return viewer;
	}

	protected EnterpriseArchiveEditor getEditor() {
		return editor;
	}

	protected void setEditor(EnterpriseArchiveEditor editor) {
		this.editor = editor;
	}
    
	protected EditingDomain getEditingDomain() {
		if (editor instanceof IEditingDomainProvider) {
			IEditingDomainProvider editingDomainProvider = (IEditingDomainProvider) editor;
			return editingDomainProvider.getEditingDomain() ;
		}
		return null ; 
	}
}

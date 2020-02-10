package com.tibco.cep.studio.ui.editors.archives;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.designtime.core.model.archive.ArchiveResource;
import com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource;
import com.tibco.cep.designtime.core.model.archive.EnterpriseArchive;
import com.tibco.cep.designtime.core.model.archive.SharedArchive;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.navigator.model.ArchiveNode;
import com.tibco.cep.studio.ui.navigator.view.ProjectExplorer;

/**
 * 
 * @author sasahoo
 *
 */
public class EnterpriseArchiveEditor extends AbstractArchiveSaveableEditorPart{

	public final static String ID = "com.tibco.cep.studio.ui.editor.enterprisearchive";
	public ArchiveConfigurationFormViewer configurationFormViewer;
	public BusinessEventsArchiveFormViewer businessEventsArchiveFormViewer;
	public SharedArchiveFormViewer sharedArchiveFormViewer;
    private BusinessEventsArchiveResource barSelectedResource; 
    private SharedArchive sarSelectedResource; 
    
	@Override
	protected void createPages() {
		createUIEditorPage();
		updateTitle();
	}
	
	protected void createUIEditorPage() {
		try {
			addFormPage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void addFormPage() throws PartInitException {
		configurationFormViewer = new ArchiveConfigurationFormViewer(this,archive);
		configurationFormViewer.createPartControl(getContainer());
		pageIndex = addPage(configurationFormViewer.getControl());
        setPageText(pageIndex, Messages.getString("GENERAL_SECTION_TITLE"));
        setPageImage(pageIndex, EditorsUIPlugin.getImageDescriptor("icons/enterpriseArchive16x16.gif").createImage());
        
        businessEventsArchiveFormViewer = new BusinessEventsArchiveFormViewer(this,archive);
        businessEventsArchiveFormViewer.createPartControl(getContainer());
		pageIndex = addPage(businessEventsArchiveFormViewer.getControl());
		setPageText(pageIndex, Messages.getString("bar.editor.page.title")); 
		setPageImage(pageIndex, EditorsUIPlugin.getImageDescriptor("icons/beArchives16x16.png").createImage());
		
		sharedArchiveFormViewer = new SharedArchiveFormViewer(this,archive);
		sharedArchiveFormViewer.createPartControl(getContainer());
		pageIndex = addPage(sharedArchiveFormViewer.getControl());
		setPageText(pageIndex,Messages.getString("sar.editor.page.title")); 
		setPageImage(pageIndex, EditorsUIPlugin.getImageDescriptor("icons/sharedArchives16x16.png").createImage());
		
//		setActivePage();
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		this.site = site;
		if (input instanceof FileEditorInput) {
			file = ((FileEditorInput)input).getFile();
			createModel(input);
			EnterpriseArchive archive = getArchive();
			// archive.eAdapters().add(new ArchiveAdapterImpl(this));
			setEnterpriseArchive(archive);
			setProject(file.getProject());
			archiveEditorInput = new EnterpriseArchiveEditorInput(file, archive);
			super.init(site, archiveEditorInput);
			site.getPage().addPartListener(partListener);
			getProjectExplorerStructuredResources(site);

		} else {
			super.init(site, input);
		}
	}
	
	/**
	 * @param site
	 */
	private void getProjectExplorerStructuredResources(IEditorSite site){
		ProjectExplorer explorer = (ProjectExplorer)site.getPage().findView("com.tibco.cep.studio.projectexplorer.view");
		if(explorer!=null){
			if(explorer.getCommonViewer().getSelection()!=null){
				if(explorer.getCommonViewer().getSelection() instanceof IStructuredSelection){
					IStructuredSelection selection =(IStructuredSelection) explorer.getCommonViewer().getSelection();
					Object element = selection.getFirstElement();
					if(element instanceof ArchiveNode){
						ArchiveResource resource = ((ArchiveNode)element).getArchiveResource();
						if( resource instanceof BusinessEventsArchiveResource){
							barSelectedResource = (BusinessEventsArchiveResource)resource; 
						}
						if( resource instanceof SharedArchive){
							sarSelectedResource = (SharedArchive)resource; 
						}
					}
				}
			}
		}
	}
	
//	private void setActivePage(){
//		int index = 0;
//		if(barSelectedResource != null){
//			this.activePage(BUSINNESS_EVENTS_ARCHIVE);
//			for(BusinessEventsArchiveResource res:archive.getBusinessEventsArchives()){
//				if(res.getName().equalsIgnoreCase(barSelectedResource.getName())){break;}index++;
//			}
//			businessEventsArchiveFormViewer.getViewer().setSelection(
//					new StructuredSelection((BusinessEventsArchiveResource)businessEventsArchiveFormViewer.getViewer().getElementAt(index)));
//		}else if(sarSelectedResource != null){
//			this.activePage(SHARED_ARCHIVE);
//			for(SharedArchive res:archive.getSharedArchives()){
//				if(res.getName().equalsIgnoreCase(sarSelectedResource.getName())){break;}index++;
//			}
//			sharedArchiveFormViewer.getViewer().setSelection(
//					new StructuredSelection(((SharedArchive)sharedArchiveFormViewer.getViewer().getElementAt(index))));
//		}else{
//			this.setActivePage(CONFIGURATION);
//		}
//	}
	
	/**
	 * This listens for when the outline becomes active
	 */
	protected IPartListener partListener = new IPartListener() {
		public void partActivated(IWorkbenchPart p) {
			if (p == EnterpriseArchiveEditor.this) {
				handleActivate();
			}
		}

		public void partBroughtToTop(IWorkbenchPart p) {
		}

		public void partClosed(IWorkbenchPart p) {
		}

		public void partDeactivated(IWorkbenchPart p) {
		}

		public void partOpened(IWorkbenchPart p) {
		}
	};
	
	public BusinessEventsArchiveFormViewer getBusinessEventsArchiveFormViewer() {
		return businessEventsArchiveFormViewer;
	}

	public SharedArchiveFormViewer getSharedArchiveFormViewer() {
		return sharedArchiveFormViewer;
	}
	
	public ArchiveConfigurationFormViewer getConfigurationFormViewer() {
		return configurationFormViewer;
	}

	@Override
	public String getPerspectiveId() {
		// TODO Auto-generated method stub
		return null;
	}
}
package com.tibco.cep.studio.ui.editors.clusterconfig;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.openPerspective;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.sharedresource.ui.SchemaEditorUtil;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.core.clusterconfig.IClusterConfigModelModifier;
import com.tibco.cep.studio.ui.StudioUIManager;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/*
@author ssailapp
@date Nov 21, 2009 6:44:31 PM
 */

public class ClusterConfigEditor extends FormEditor implements IResourceChangeListener, IClusterConfigModelModifier {
	private boolean isEditorDirty;
	private TextEditor sourceEditor;
	private ArrayList<Control> controls;
	private ClusterConfigModelMgr modelmgr;
	protected IEditorSite site;
	private IProject project;
	private boolean isSourceEditing = false;
	private int prevPageIndex = 0;
	
	private ClusterOmPage clusterOmPage;
	private GroupsPage collectionsPage;
	private AgentClassesPage agentClassesPage;
	private ProcUnitPage processingUnitsPage;
	private LoadBalancerPage loadBalancerPage;

	private static final int ClUSTER_OM_PAGE_INDEX = 0;
	private static final int COLLECTIONS_PAGE_INDEX = 1;
	private static final int AGENT_CLASSES_PAGE_INDEX = 2;
	private static final int PROCESSING_UNITS_PAGE_INDEX = 3;
	private static final int LOAD_BALANCER_PAGE_INDEX = 4;
	private static final int SOURCE_PAGE_INDEX = 5;
	
	/*
	private String cddErrorMsg = "There are errors present in the configuration. Please check the Problems view for details. " +
								  "It is recommended that you fix these errors before saving the configuration. \n\n" + 
								  "Do you want to continue to save ?";
	*/
	
	public ClusterConfigEditor() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
		isEditorDirty = false;
		controls = new ArrayList<Control>();
	}
	
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);
		this.site = site;
        StudioUIManager.getInstance();
		if (getEditorFile() != null)
			project = getEditorFile().getProject();
        if (input != null) {
        	setPartName(getEditorFileName());
    		setTitleToolTip(input.getToolTipText());
       }
       site.getPage().addPartListener(partListener);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// Check if we are currently in the source editor
		if (getActivePage() != controls.size() && isEditorDirty) {
			updateSourceEditorFromClusterConfigPages(true);
		}
		isEditorDirty = false;
		sourceEditor.doSave(monitor);
	}

	@Override
	public void doSaveAs() {
		/*
		if (getActivePage() != controls.size() && isEditorDirty) {
			updateSourceEditorFromClusterConfigPages();
		}
		isEditorDirty = false;
		sourceEditor.doSaveAs();
		setInput(sourceEditor.getEditorInput());
		updateTitle();
		*/
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void modified() {
		isEditorDirty = true;
		if (!super.isDirty()) {
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}
	}
	
	public boolean isDirty() {
		if (isSourceEditing) {
			return (isEditorDirty || super.isDirty());
		} else {
			return isEditorDirty;
		}
	}
	
	protected void handlePropertyChange(int propertyId) {
		if (propertyId == IEditorPart.PROP_DIRTY) {
			isEditorDirty = isDirty();
		}
		super.handlePropertyChange(propertyId);
	}
	
	@SuppressWarnings("unused")
	private void createClusterOmPage() throws PartInitException {
		clusterOmPage = new ClusterOmPage(this, modelmgr);
		int index = addPage(clusterOmPage);
		controls.add(clusterOmPage.getPartControl());
	}
	
	@SuppressWarnings("unused")
	private void createGroupsPage() throws PartInitException {
		collectionsPage = new GroupsPage(this, modelmgr);
		int index = addPage(collectionsPage);
		controls.add(collectionsPage.getPartControl());
	}
	
	@SuppressWarnings("unused")
	private void createAgentClassesPage() throws PartInitException {
		agentClassesPage = new AgentClassesPage(this, modelmgr);
		int index = addPage(agentClassesPage);
		controls.add(agentClassesPage.getPartControl());
	}

	@SuppressWarnings("unused")
	private void createProcessingUnitPage() throws PartInitException {
		processingUnitsPage = new ProcUnitPage(this, modelmgr);
		int index = addPage(processingUnitsPage);
		controls.add(processingUnitsPage.getPartControl());
	}
	
	@SuppressWarnings("unused")
	private void createLoadBalancerPage() throws PartInitException {
		loadBalancerPage = new LoadBalancerPage(this, modelmgr);
		int index = addPage(loadBalancerPage);
		controls.add(loadBalancerPage.getPartControl());
	}	
	
	private void createSourcePage() {
		try {
			sourceEditor = new TextEditor();
			int index = addPage(sourceEditor, getEditorInput());
			setPageText(index, "Source");
		} catch (PartInitException pie) {
			pie.printStackTrace();
		}
	}
	
	/*	
 	private void updateTitle() {
		IEditorInput input = getEditorInput();
		setTitle(input.getName());
		setTitleToolTip(input.getToolTipText());
	}
 	*/	
	public void setFocus() {
		super.setFocus();
	}

	/*
	public void goToMarker(IMarker marker) {
		setActivePage(SOURCE_PAGE_INDEX);
		sourceEditor.gotoMarker(marker);
	}
	*/
	
	public String getTitle() {
		String displayName = SchemaEditorUtil.getDisplayName(getEditorFileName(), "Cluster Configuration", ".cdd");
		return (displayName);		
	}
	
	public IFile getEditorFile() {
		if (getEditorInput() instanceof FileEditorInput) {
			return ((FileEditorInput)getEditorInput()).getFile();
		}
		return null;
	}
	
	public String getEditorFileName() {
		if (getEditorInput() != null)
			return (getEditorInput().getName());
		return null;
	}
	
	public String getEditorFilePath() {
		IFile editorFile = getEditorFile();
		if (editorFile != null) {
			if(editorFile.getLocation()!= null){
				return editorFile.getLocation().toString();
			}
		}
		return null;
	}
	
	private void loadModel() {
		modelmgr = new ClusterConfigModelMgr(project, getEditorFilePath(), this, getEditorFileName());
		modelmgr.parseModel();
	}
	
	/*
	protected void pageChange(int newPageIndex) {
		switch (newPageIndex) {
			case GROUPS_PAGE_INDEX			: groupsPage.update();
			case AGENT_CLASSES_PAGE_INDEX	: agentClassesPage.update(); 
			case PROC_UNIT_PAGE_INDEX		: procUnitPage.update();
			case SOURCE_PAGE_INDEX			: if (isEditorDirty) {
												updateSourceEditorFromClusterConfigPages(false);
												isEditorDirty = false;
											  }
											  break;
			default: if (isDirty())
						updateClusterConfigPagesFromSourceEditor();
					 break;
		}
		//isEditorDirty = false;
		super.pageChange(newPageIndex);
	}
	*/

	protected void pageChange(int newPageIndex) {
		boolean needToRefreshPages = isDirty();
		switch (newPageIndex) {
			case ClUSTER_OM_PAGE_INDEX		: //clusterOmPage.update();
											  isEditorDirty = isDirty();	
											  isSourceEditing = false;
											  break;
			case COLLECTIONS_PAGE_INDEX		: //collectionsPage.update();
											  isEditorDirty = isDirty();
											  isSourceEditing = false;
											  break;
			case AGENT_CLASSES_PAGE_INDEX	: //agentClassesPage.update();
											  isEditorDirty = isDirty();
											  isSourceEditing = false;
											  break;
			case PROCESSING_UNITS_PAGE_INDEX: //processingUnitsPage.update();
											  isEditorDirty = isDirty();
											  isSourceEditing = false;
											  break;
			case LOAD_BALANCER_PAGE_INDEX	: //loadBalancerPage.update();
											  isEditorDirty = isDirty();
											  isSourceEditing = false;
											  break;
			case SOURCE_PAGE_INDEX			: if (isEditorDirty) {
												// Handle tab to source switch
												updateSourceEditorFromClusterConfigPages(false);
												isEditorDirty = false;
											  }
											  isSourceEditing = true;
											  break;
			default: if (isDirty())
						updateClusterConfigPagesFromSourceEditor();
					 break;
		}

		/* No need to handle tab-to-tab switch case
		if (isEditorInitialized && prevPageIndex != SOURCE_PAGE_INDEX && newPageIndex != SOURCE_PAGE_INDEX) {
			// Handle tab to tab switch.
			updateSourceEditorFromClusterConfigPages(false);
		}
		isEditorInitialized = true;
		*/
		
		if (needToRefreshPages && prevPageIndex == SOURCE_PAGE_INDEX && newPageIndex != SOURCE_PAGE_INDEX) {
			// Handle source to tab switch
			updateClusterConfigPagesFromSourceEditor();
		}
		prevPageIndex = newPageIndex;
		super.pageChange(newPageIndex);
	}
	
	private void updateClusterConfigPagesFromSourceEditor() {
		// Convert source editor to forms (loading model)
		String clusterConfigText = sourceEditor.getDocumentProvider().getDocument(sourceEditor.getEditorInput()).get();
		modelmgr.refreshModel(clusterConfigText);
		updateEditorPages();
	}
	
	private void updateEditorPages() {
		clusterOmPage.update();
		collectionsPage.update();
		agentClassesPage.update();
		processingUnitsPage.update();
		loadBalancerPage.update();
	}
	
	private void updateSourceEditorFromClusterConfigPages(boolean updateVersion) {
		String clusterConfigText = modelmgr.saveModel(updateVersion);
		sourceEditor.getDocumentProvider().getDocument(sourceEditor.getEditorInput()).set(clusterConfigText);
	}
	
	@Override
	protected void addPages() {
		loadModel();

		try {
			createClusterOmPage();
			createGroupsPage();
			createAgentClassesPage();
			createProcessingUnitPage();
			createLoadBalancerPage();
			createSourcePage();
			
			// Auto-save if something wasn't initialized correctly, and default values were assigned
			if (isDirty()) {
				doSave(null);
			}
		}
		catch (PartInitException e) {
			//
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.editor.FormEditor#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		getSite().getPage().removePartListener(partListener);
		sourceEditor = null;
		controls = null;
	}
	
	
	
	/**
	 * This listens for when the outline becomes active
	 */
	protected IPartListener partListener = new IPartListener() {
		public void partActivated(IWorkbenchPart p) {
			if (p == ClusterConfigEditor.this) {
				StudioUIUtils.refreshPaletteAndOverview(site);
				openPerspective(site, getPerspectiveId());
			}
		}

		public void partBroughtToTop(IWorkbenchPart p) {
		}

		public void partClosed(IWorkbenchPart p) {
			// Reload errors list from file, so that errors from dirty (unsaved) editor are wiped
			// loadModel();
			//ClusterConfigModelValidator validator = new ClusterConfigModelValidator(modelmgr);
			//validator.validateModel(getEditorFile());
		}

		public void partDeactivated(IWorkbenchPart p) {
		}

		public void partOpened(IWorkbenchPart p) {
		}
	};

	public String getPerspectiveId() {
		return "com.tibco.cep.studio.application.perspective";
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		try {
			IResourceDelta rootDelta = event.getDelta();
			if (rootDelta == null)
				return;
			if (!(getEditorInput() instanceof FileEditorInput)){
				return;
			}
			final IFile file = ((FileEditorInput)getEditorInput()).getFile();
			if (file == null)
				return;
			
			IResourceDelta resourceDelta = rootDelta.findMember(file.getFullPath());
			if (resourceDelta == null) {
				return;
			}
			IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
				public boolean visit(IResourceDelta delta) {
					if (delta.getKind() == IResourceDelta.CHANGED){
						int flags = delta.getFlags();
						if (flags == IResourceDelta.MARKERS) {
							return false;
						}
						doRefresh(file);
					}
					return true;
				}
			};
			resourceDelta.accept(visitor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void doRefresh(IFile file) {
		String clusterConfigText = null; //sourceEditor.getDocumentProvider().getDocument(sourceEditor.getEditorInput()).get();
		
		Writer writer = new StringWriter();
		char[] buffer = new char[1024];
		InputStream is = null;
		try {
			is = file.getContents();
			if (is == null)
				return;
			Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			int n;
			while ((n = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, n);
			}
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		clusterConfigText = writer.toString();
		boolean isRefreshed = modelmgr.refreshModel(clusterConfigText);
		if (isRefreshed) {
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					updateEditorPages();
				}
			});
		}
	}

}

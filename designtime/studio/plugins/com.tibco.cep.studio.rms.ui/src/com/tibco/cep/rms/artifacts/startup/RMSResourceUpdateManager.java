package com.tibco.cep.rms.artifacts.startup;

import static com.tibco.cep.studio.rms.ui.utils.ActionConstants.COMMIT_ACTION;
import static com.tibco.cep.studio.rms.ui.utils.ActionConstants.REVERT_ACTION;
import static com.tibco.cep.studio.rms.ui.utils.ActionConstants.SHOW_HISTORY_ACTION;
import static com.tibco.cep.studio.rms.ui.utils.ActionConstants.UPDATE_ACTION;
import static com.tibco.cep.studio.rms.ui.utils.ActionConstants.WORKLIST_ACTION;
import static com.tibco.cep.studio.rms.ui.utils.RMSUIUtils.enableDisableAction;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.rms.client.ui.RMSClientWorklistEditorInput;
import com.tibco.cep.studio.rms.history.RMSHistoryEditorInput;
import com.tibco.cep.studio.rms.ui.RMSUIPlugin;

/**
 * 
 * @author sasahoo
 *
 */
public class RMSResourceUpdateManager implements IResourceChangeListener {

	private IWorkbenchPage page;
	private List<IResource> projects;
	private static RMSResourceUpdateManager INSTANCE;
	private boolean closeRMSClientEditors = true;
	
	/**
	 * @return
	 */
	public static final RMSResourceUpdateManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new RMSResourceUpdateManager();
			ResourcesPlugin.getWorkspace().addResourceChangeListener(INSTANCE, 
					                                                 IResourceChangeEvent.POST_CHANGE);
		}
		return INSTANCE;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
	 */
	public void resourceChanged(IResourceChangeEvent event) {
		IResourceDelta delta = event.getDelta();
		projects =   new ArrayList<IResource>();
		if (event.getType() == IResourceChangeEvent.POST_CHANGE){
			IResource resource = delta.getResource();
			if (resource instanceof IWorkspaceRoot) {
				IResourceDelta[] affectedChildren = delta.getAffectedChildren();
				for (IResourceDelta resourceDelta : affectedChildren) {
					processDelta(resourceDelta);
				}
			}
		}
		run();
	}
	
	private void run() {
		Display.getDefault().asyncExec(new Runnable(){
			/* (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				try {
					if (!PlatformUI.isWorkbenchRunning() || PlatformUI.getWorkbench().isClosing()) {
						return;
					}
					if (page == null) {
						page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					}
					updateActions();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Update RMS actions (disable)
	 */
	private void updateActions() {
		if (CommonUtil.getAllStudioProjects().length == projects.size() ) {
			enableDisableAction(false, COMMIT_ACTION);
			enableDisableAction(false, WORKLIST_ACTION);
			enableDisableAction(false, SHOW_HISTORY_ACTION);
			enableDisableAction(false, UPDATE_ACTION);
			enableDisableAction(false, REVERT_ACTION);
			if (closeRMSClientEditors) {
				closeRMSAssociatedEditors();
			}
		}
	}
	
	/**
	 * Close all editors associated to RMS 
	 * This is optional, controlled by flag closeRMSClientEditors
	 */
	private void closeRMSAssociatedEditors() {
		int count = 0;
		for (IEditorReference reference: page.getEditorReferences()) {
			try {
				if (reference.getEditorInput() instanceof RMSHistoryEditorInput 
						|| reference.getEditorInput() instanceof RMSClientWorklistEditorInput){
					page.closeEditor(reference.getEditor(false), false);
					count++;
				}
				if (count == 2) {
					break;
				}
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param delta
	 */
	private void processDelta(IResourceDelta delta) {
		IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
			/* (non-Javadoc)
			 * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.IResourceDelta)
			 */
			public boolean visit(IResourceDelta delta) {
				IResource resource = delta.getResource();
				if (delta.getKind() == IResourceDelta.REMOVED){
					if (resource.getType() == IResource.PROJECT) {
						IProject project = (IProject)resource;
						if (CommonUtil.isStudioProject(project)) {
							projects.add((IProject)resource);
						}
						return true;
					}
				}
				return true;
			}
		};
		try {
			delta.accept(visitor);
		} catch (CoreException e) {
			RMSUIPlugin.debug(e.getMessage());
		}
	}
}
package com.tibco.cep.studio.ui.navigator.providers;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.ide.ResourceSelectionUtil;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.Utils;
import com.tibco.cep.studio.ui.StudioNavigatorNode;
import com.tibco.cep.studio.ui.navigator.actions.StudioCopyAction;
import com.tibco.cep.studio.ui.navigator.actions.StudioCutAction;
import com.tibco.cep.studio.ui.navigator.actions.StudioDeleteAction;
import com.tibco.cep.studio.ui.navigator.actions.StudioPasteAction;
import com.tibco.cep.studio.ui.navigator.model.ChannelDestinationNode;
import com.tibco.cep.studio.ui.navigator.model.DomainInstanceNode;

public class StudioNavigatorEditActionGroup extends ActionGroup {

    private Clipboard fClipboard;
    private StudioCopyAction fCopyAction;
    private StudioPasteAction fPasteAction;
    private StudioDeleteAction fDeleteAction;
    private StudioCutAction fCutAction;

	public StudioNavigatorEditActionGroup(ICommonActionExtensionSite site) {
		makeActions(site.getViewSite().getShell());
	}

	@Override
	public void fillActionBars(IActionBars actionBars) {
		super.fillActionBars(actionBars);
		
		//Cut Global Action Handler overriden for Navigator
		actionBars.setGlobalActionHandler(ActionFactory.CUT.getId(), fCutAction);
		
		actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(), fPasteAction);
		actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(), fCopyAction);
		actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(), fDeleteAction);
		updateActionBars();
	}

	@Override
	public void fillContextMenu(IMenuManager menu) {
		IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();
		Object object = selection.getFirstElement();
		
		if (object instanceof IResource) {
			IResource resource = (IResource)object;
			if (IndexUtils.isProjectLibType(resource)) {
				return;
			}
		}
		
		//Temporary hiding Copy/Paste Context menu for Designer Navigator Node Elements
		//Such as Property Definition etc.
		boolean designeNavigatorNodeselected = !selection.isEmpty()
				&& object instanceof StudioNavigatorNode;
		if(designeNavigatorNodeselected) {
			if (selection.getFirstElement() instanceof ChannelDestinationNode) {
				handleDestinationEdit(selection, menu);
				return;
			} else {
				return;
			}
		}

		//For Standalone DM
		if (Utils.isStandaloneDecisionManger()) {
			if (!(object instanceof IProject)) {
				if (object instanceof IFile && (((IFile)object).getFileExtension().equals("domain") || 
						((IFile)object).getFileExtension().equals("rulefunctionimpl"))) { 
					//TODO
				}else {
					return;
				}
			} else {
				fDeleteAction.selectionChanged(selection);
				menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT, fDeleteAction);
				return;
			}
		}
		
		if (object instanceof DomainInstanceNode) {
			fDeleteAction.selectionChanged(selection);
			menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT, fDeleteAction);
			return;
		}
		
		boolean anyResourceSelected = !selection.isEmpty()
		&& ResourceSelectionUtil.allResourcesAreOfType(selection,
				IResource.PROJECT | IResource.FOLDER | IResource.FILE);
		
		fCopyAction.selectionChanged(selection);
		menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT, fCopyAction);
		fPasteAction.selectionChanged(selection);
		//menu.insertAfter(copyAction.getId(), pasteAction);
		menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT, fPasteAction);
		
		if (anyResourceSelected) {
			fDeleteAction.selectionChanged(selection);
			//menu.insertAfter(pasteAction.getId(), deleteAction);
			menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT, fDeleteAction);
		}
		
		if (object instanceof StudioNavigatorNode && ((StudioNavigatorNode) object).isSharedElement()) {
			fCopyAction.setEnabled(false);
			fPasteAction.setEnabled(false);
			fDeleteAction.setEnabled(false);
			return;
		}
		
		if (object instanceof IProject && !((IProject)object).isOpen()) {
			fCopyAction.setEnabled(false);
			fPasteAction.setEnabled(false);
			return;
		} 
		
		if (!allSelection(selection)) {
			fCopyAction.setEnabled(false);
			fPasteAction.setEnabled(false);
		}
	}
	
	/**
	 * @param selection
	 * @return
	 */
	private boolean allSelection(IStructuredSelection selection) {
		Object object = selection.getFirstElement();
		boolean isResource = false;
		boolean isNonResource = false;
		if (object instanceof IResource) {
			isResource = true; 
		} else {
			isNonResource = true;
		}
		Object[] objects = selection.toArray();
		for (int k = 1; k <= objects.length - 1; k++) {
			if (isResource == true) {
				if (! (objects[k] instanceof IResource)) {
					return false;
				}
			}
			if (isNonResource == true) {
				if (objects[k] instanceof IResource) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * @param selection
	 * @param menu
	 */
	private void handleDestinationEdit(IStructuredSelection selection, IMenuManager menu) {
		fCopyAction.selectionChanged(selection);
		menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT, fCopyAction);
		fPasteAction.selectionChanged(selection);
		menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT, fPasteAction);
		fDeleteAction.selectionChanged(selection);
		menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT, fDeleteAction);
		
		Object object = selection.getFirstElement();
		if (object instanceof StudioNavigatorNode && ((StudioNavigatorNode) object).isSharedElement()) {
			fCopyAction.setEnabled(false);
			fPasteAction.setEnabled(false);
			fDeleteAction.setEnabled(false);
			return;
		} 
		if (!allSelection(selection)) {
			fCopyAction.setEnabled(false);
			fPasteAction.setEnabled(false);
			return;
		}
		if (!allSameLevelDestinations(selection)) {
			fCopyAction.setEnabled(false);
			fPasteAction.setEnabled(false);
		}
	}
	
	private boolean allSameLevelDestinations(IStructuredSelection selection) {
		Object object = selection.getFirstElement();
		if (object instanceof ChannelDestinationNode) {
			ChannelDestinationNode destNode = (ChannelDestinationNode)object;
			Channel channel = ((Destination)destNode.getEntity()).getDriverConfig().getChannel();
			Object[] objects = selection.toArray();
			for (int k = 1; k <= objects.length -1 ; k++) {
				if (objects[k] instanceof ChannelDestinationNode) {
					ChannelDestinationNode node = (ChannelDestinationNode)objects[k];
					Channel channelS = ((Destination)node.getEntity()).getDriverConfig().getChannel();
					if (channel != channelS) {
						return false;
					}
				}
			}
		}
		return true;
	}
	

	/* (non-Javadoc)
	 * @see org.eclipse.ui.actions.ActionGroup#updateActionBars()
	 */
	@Override
	public void updateActionBars() {
		super.updateActionBars();
		IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();
		fCutAction.selectionChanged(selection);
		fCopyAction.selectionChanged(selection);
		fPasteAction.selectionChanged(selection);
		fDeleteAction.selectionChanged(selection); 
	}

	private void makeActions(final Shell shell) {
		IShellProvider provider = new IShellProvider() {
		
			@Override
			public Shell getShell() {
				return shell;
			}
		};
		fClipboard = new Clipboard(shell.getDisplay());
		fPasteAction = new StudioPasteAction("Paste", fClipboard, shell);
		fCutAction = new StudioCutAction("Cut", fClipboard);
		fCopyAction = new StudioCopyAction("Copy", fClipboard);
		fDeleteAction = new StudioDeleteAction("Delete", provider);
		fDeleteAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
	}
}
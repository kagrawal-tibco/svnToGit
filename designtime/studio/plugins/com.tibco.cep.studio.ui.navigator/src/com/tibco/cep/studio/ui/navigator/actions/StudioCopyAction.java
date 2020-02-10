package com.tibco.cep.studio.ui.navigator.actions;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.SelectionListenerAction;
import org.eclipse.ui.part.ResourceTransfer;

import com.tibco.cep.studio.ui.navigator.model.ChannelDestinationNode;

public class StudioCopyAction extends SelectionListenerAction {

	private Clipboard fClipboard;
	private Transfer[] fTransfers = new Transfer[] {ResourceTransfer.getInstance(),
													FileTransfer.getInstance(),
													TextTransfer.getInstance()};

	/**
	 * @param text
	 * @param clipboard
	 */
	public StudioCopyAction(String text, Clipboard clipboard) {
		super(text);
		this.fClipboard = clipboard;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#getImageDescriptor()
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		return PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_COPY);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void run() {
		//For Channel Destinations
		List selectedNonResources = getSelectedNonResources();
		if (selectedNonResources != null && selectedNonResources.size() > 0) {
			try {
				ChannelDestinationNode[] destNodes = new ChannelDestinationNode[selectedNonResources.size()];
				selectedNonResources.toArray(destNodes);
				Object[] contents = new Object[] { destNodes};
				fClipboard.setContents(contents, new  Transfer[]{DestinationTransfer.getInstance()});
			} catch (SWTError e) {
				if (e.code != DND.ERROR_CANNOT_SET_CLIPBOARD) {
					throw e;
				}
				e.printStackTrace();
			}
		}

		List selectedResources = getSelectedResources();
		if (selectedResources == null || selectedResources.size() == 0) {
			return;
		}
		IResource[] resources = new IResource[selectedResources.size()];
		selectedResources.toArray(resources);
		String[] fileNames = new String[resources.length];
		StringBuffer names = new StringBuffer();
		for (int i=0; i < resources.length; i++) {
			fileNames[i] = resources[i].getLocation().toOSString();
			names.append(resources[i].getName());
			names.append('\n');
		}
		setClipboard(resources, fileNames, names.toString());
	}

	/**
	 * @param resources
	 * @param fileNames
	 * @param names
	 */
	private void setClipboard(IResource[] resources, String[] fileNames,
													 String names) {
		try {
			Object[] contents = new Object[] { resources, fileNames, names };
			fClipboard.setContents(contents, fTransfers );
		} catch (SWTError e) {
			if (e.code != DND.ERROR_CANNOT_SET_CLIPBOARD) {
				throw e;
			}
			e.printStackTrace();
		}
	}
}

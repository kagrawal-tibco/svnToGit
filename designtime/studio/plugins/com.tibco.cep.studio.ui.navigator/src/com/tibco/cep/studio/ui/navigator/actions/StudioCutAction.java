package com.tibco.cep.studio.ui.navigator.actions;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.SelectionListenerAction;
import org.eclipse.ui.part.ResourceTransfer;

public class StudioCutAction extends SelectionListenerAction {

	@SuppressWarnings("unused")
	private Clipboard fClipboard;
	@SuppressWarnings("unused")
	private Transfer[] fTransfers = new Transfer[] { ResourceTransfer.getInstance(),
			FileTransfer.getInstance(),
			TextTransfer.getInstance() };

	public StudioCutAction(String text, Clipboard clipboard) {
		super(text);
		this.fClipboard = clipboard;
		setEnabled(false);
	}
	
	@Override
	public ImageDescriptor getImageDescriptor() {
		return PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_CUT_DISABLED);
	}

	@Override
	public void run() {
//		List selectedResources = getSelectedResources();
//		if (selectedResources == null || selectedResources.size() == 0) {
//			return;
//		}
//		IResource[] resources = new IResource[selectedResources.size()];
//		selectedResources.toArray(resources);
//		String[] fileNames = new String[resources.length];
//		StringBuffer names = new StringBuffer();
//		for (int i=0; i<resources.length; i++) {
//			fileNames[i] = resources[i].getLocation().toOSString();
//			names.append(resources[i].getName());
//			names.append('\n');
//		}
//		setClipboard(resources, fileNames, names.toString());
	}
	
//    private void setClipboard(IResource[] resources, String[] fileNames,
//            String names) {
////    	try {
//    		Object[] contents = new Object[] { resources, fileNames, names };
//			fClipboard.setContents(contents, fTransfers );
//        } catch (SWTError e) {
//            if (e.code != DND.ERROR_CANNOT_SET_CLIPBOARD) {
//				throw e;
//			}
//            e.printStackTrace();
//        }
//    }

}
package com.tibco.cep.studio.ui.navigator.dnd;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.navigator.CommonDropAdapter;
import org.eclipse.ui.navigator.CommonDropAdapterAssistant;

import com.tibco.cep.studio.ui.actions.MoveResourceAction;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 * 
 * @author sasahoo
 *
 */
public class NavigatorRefactoringDropAdapterAssistant extends CommonDropAdapterAssistant{

	/* (non-Javadoc)
	 * @see org.eclipse.ui.navigator.CommonDropAdapterAssistant#handleDrop(org.eclipse.ui.navigator.CommonDropAdapter, org.eclipse.swt.dnd.DropTargetEvent, java.lang.Object)
	 */
	@Override
	public IStatus handleDrop(CommonDropAdapter dropAdapter,DropTargetEvent dropTargetEvent, Object target) {
		if (!(target instanceof IResource)) {
			return Status.CANCEL_STATUS;
		}
		IResource targetResource = (IResource) target;
		Object data = dropTargetEvent.data;
		if (!(data instanceof StructuredSelection)) {
			return Status.CANCEL_STATUS;
		}
		StructuredSelection selection = (StructuredSelection) data;
		if (selection.toArray().length > 1) {
			MessageDialog.openError(new Shell(), "Move error", "Dropping multiple objects is not currently supported");
			return Status.CANCEL_STATUS;
		}
		Object obj = selection.getFirstElement();
		if (!(obj instanceof IResource)) {
			return Status.CANCEL_STATUS;
		}
		if (targetResource instanceof IFile) {
			targetResource = targetResource.getParent();
		}
		//Validation for Bad Folder
		if(targetResource instanceof IContainer){
			if(!StudioUIUtils.isValidContainer(targetResource.getFullPath())){
				String problemMessage = com.tibco.cep.studio.core.util.Messages.getString("Resource.folder.bad", targetResource.getFullPath());
				MessageDialog.openError(new Shell(), "Move error", problemMessage);
				return Status.CANCEL_STATUS;
			}
			Object firstElement = selection.getFirstElement();
			//Check whether source and target owner project are same when Drag And Drop Move 
			if(!StudioUIUtils.checkOwnerProjectValid((IContainer)targetResource, new IResource[] {(IResource) firstElement})){
				MessageDialog.openError(new Shell(), "Owner Project", "Owner Project mismatch.");
				return Status.CANCEL_STATUS;
			}
		}
		MoveResourceAction action = new MoveResourceAction((IContainer) targetResource);
		action.selectionChanged(null, selection);
		action.run(null);
		return Status.OK_STATUS;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.navigator.CommonDropAdapterAssistant#validateDrop(java.lang.Object, int, org.eclipse.swt.dnd.TransferData)
	 */
	@Override
	public IStatus validateDrop(Object target, int operation, TransferData transferType) {
		if (!(target instanceof IResource)) {
			return Status.CANCEL_STATUS;           			
		}
		IResource targetResource = (IResource) target;
		if (LocalSelectionTransfer.getTransfer().isSupportedType(transferType)) {
			ISelection selection = LocalSelectionTransfer.getTransfer().getSelection();
			if (selection instanceof StructuredSelection) {
//				if (((StructuredSelection) selection).toArray().length > 1) {
//					return Status.CANCEL_STATUS;
//				}
				Object firstElement = ((StructuredSelection) selection).getFirstElement();
				if (!(firstElement instanceof IResource)) {
					return Status.CANCEL_STATUS;
				}
				IResource sourceElement = (IResource) firstElement;
				
//				if (!sourceElement.getProject().equals(targetResource.getProject())) {
//					return Status.CANCEL_STATUS;
//				}
				
				if (targetResource instanceof IFile) {
					targetResource = targetResource.getParent();
				}
				if (sourceElement.getParent().equals(targetResource)) {
					return Status.CANCEL_STATUS;
				}
				return Status.OK_STATUS;
			}
		}
		
//		if (FileTransfer.getInstance().isSupportedType(transferType)) {
//			System.out.println("supp2");
//		}
//		if (TextTransfer.getInstance().isSupportedType(transferType)) {
//			System.out.println("supp3");
//		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.navigator.CommonDropAdapterAssistant#isSupportedType(org.eclipse.swt.dnd.TransferData)
	 */
	public boolean isSupportedType(TransferData aTransferType) {
		return FileTransfer.getInstance().isSupportedType(aTransferType);
	}
	
}

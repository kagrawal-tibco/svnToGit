package com.tibco.cep.studio.ui.navigator.dnd;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.navigator.CommonDropAdapter;
import org.eclipse.ui.navigator.CommonDropAdapterAssistant;

import com.tibco.cep.studio.ui.navigator.model.EventPropertyNode;
import com.tibco.cep.studio.ui.navigator.model.PropertyNode;

/**
 * 
 * @author sasahoo
 *
 */
public class StudioNavigatorDropAdapterAssistant extends CommonDropAdapterAssistant{

	/* (non-Javadoc)
	 * @see org.eclipse.ui.navigator.CommonDropAdapterAssistant#handleDrop(org.eclipse.ui.navigator.CommonDropAdapter, org.eclipse.swt.dnd.DropTargetEvent, java.lang.Object)
	 */
	@Override
	public IStatus handleDrop(CommonDropAdapter dropAdapter,DropTargetEvent dropTargetEvent, Object target) {
		if(target instanceof PropertyNode || target instanceof EventPropertyNode){
			return new DomainDropHandler(dropTargetEvent,target).handleDomainDrop();
		}
		else if(target instanceof IFile){
			return new StateMachineDropHandler(dropTargetEvent,target).handleStateMachineDrop();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.navigator.CommonDropAdapterAssistant#validateDrop(java.lang.Object, int, org.eclipse.swt.dnd.TransferData)
	 */
	@Override
	public IStatus validateDrop(Object target, int operation, TransferData transferType) {
		if(target instanceof PropertyNode || target instanceof EventPropertyNode){
              return Status.OK_STATUS;           			
		}
		
		if(target instanceof IFile){
		   IFile file = (IFile)target;
		   if(file.getFileExtension().equals("concept")){
			   return Status.OK_STATUS;
		   }
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.navigator.CommonDropAdapterAssistant#isSupportedType(org.eclipse.swt.dnd.TransferData)
	 */
	public boolean isSupportedType(TransferData aTransferType) {
		return FileTransfer.getInstance().isSupportedType(aTransferType);
	}
	
}

/**
 * 
 */
package com.tibco.cep.bpmn.core.builder;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectVisitor;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;

public class BpmnResourceVisitor implements IResourceVisitor ,EObjectVisitor{
	/**
	 * 
	 */
	protected boolean fCountOnly;
	protected int fCount = 0;
	protected int fCurrentCount = 1;
	protected IProgressMonitor fMonitor;
	protected boolean fResolve = false;
	protected IFile file = null;
	
	

	public BpmnResourceVisitor(boolean countOnly, IProgressMonitor monitor, boolean resolve) {
		super();
		fCountOnly = countOnly;
		fMonitor = monitor == null ? new NullProgressMonitor():monitor;
		fResolve = resolve;
	}
	
	public boolean visit(IFile file) {
		if(BpmnIndexUtils.isBpmnType(file)){
			EObject eObj;
			try {
				eObj = BpmnIndexUtils.loadEObject(file, fResolve);
				if(eObj != null){
					EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper.wrap(eObj);
					wrapper.accept(this);
					return true;					
				} else {
					BpmnCorePlugin.log("Failed to load process data");
				}
			} catch (Exception e) {
				BpmnCorePlugin.log("Failed to load process file:"+file.getFullPath().toString(),e);
			}
		}
		return false;
	}
	
	public boolean visit(EObject eObj) {
		return true;		
	}



	@Override
	public boolean visit(IResource resource) throws CoreException {
		if (fMonitor.isCanceled()) {
			return false;
		}
		if (resource instanceof IContainer) {
			return true;
		}
		if (resource instanceof IFile) {
			this.file = (IFile) resource;
			if (BpmnIndexUtils.isBpmnType(file)) {
				
				fMonitor.subTask("Visiting "+file.getName()+" ("+fCurrentCount+" of "+fCount+")");
				if (fCountOnly) {
					fCount++;
					return true;
				}
				fMonitor.worked(1);
				fCurrentCount++;
				return visit(file);
			}
		}
		return true;
	}
	
	public IResource getFile() {
		return file;
	}
	
	public int getCount() {
		return fCount;
	}

	public void setCountOnly(boolean countOnly) {
		fCountOnly = countOnly;
	}
	
	
	
}
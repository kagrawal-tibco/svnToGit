package com.tibco.cep.bpmn.core.index.visitor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectVisitor;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;

public class BpmnIndexVerificationVisitor implements EObjectVisitor  {
	

	private String fProjectName;
	protected IProgressMonitor fMonitor;
	private HashMap<IFile, EObject> fProcessedFiles = new HashMap<IFile, EObject>();
	private List<IResource> fInvalidResources = new ArrayList<IResource>();
	private HashMap<IFile, List<EObject>> fDuplicateElements = new HashMap<IFile, List<EObject>>();
	
	public BpmnIndexVerificationVisitor(String projectName, IProgressMonitor monitor) {
		this.fMonitor = monitor;
		this.fProjectName = projectName;
	}

	public boolean visit(EObject eObj) {	
		if(fMonitor.isCanceled()){
			return false;
		}
		 EObjectWrapper<EClass, EObject> eWrapper = EObjectWrapper.wrap(eObj);
		 // Clean up empty extensions
		if(eWrapper.isInstanceOf(BpmnModelClass.DEFINITIONS)) {
			EList<EObject> extensions = eWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSIONS);
			for(Iterator<EObject> iter = extensions.listIterator();iter.hasNext();) {
				
				EObjectWrapper<EClass, EObject> extensionWrapper = EObjectWrapper.wrap(iter.next());
				
				EObject extdef = extensionWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_DEFINITION);
				if(extdef == null) {
					iter.remove();
				}
			}
		}
		
		if(eWrapper.isInstanceOf(BpmnModelClass.SERIALIZABLE_ELEMENT)) {
			EObject typeElement = eObj;
			IFile file = BpmnIndexUtils.getFile(fProjectName, eObj);
			if (file == null || !file.exists()) {
				// perhaps the file was removed from the file system?
				BpmnCorePlugin.log("File not found :" + file.getFullPath());
				return true;
			}
			Date lastModified = eWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_LAST_MODIFIED);
			long modificationStamp = file.getLocalTimeStamp();
			long elementLastModStamp = lastModified == null ? 0 : lastModified.getTime();
			if (modificationStamp > elementLastModStamp) {
				if (!fInvalidResources.contains(file)) {
					fInvalidResources.add(file);
				}
			}
			if (fProcessedFiles.containsKey(file)) {
				// duplicate entry in index, mark for removal
				List<EObject> list = fDuplicateElements.get(file);
				if (list == null) {
					EObject dupElement = fProcessedFiles.get(file);
					list = new ArrayList<EObject>();
					list.add(dupElement);
					list.add(typeElement);
					fDuplicateElements.put(file, list);
				} else {
					list.add(typeElement);
				}
			} else {
				fProcessedFiles.put(file, typeElement);
			}
			
			
		} 
		return true;
	}

	public HashMap<IFile, List<EObject>> getDuplicateElements() {
		return fDuplicateElements;
	}

	public List<IResource> getInvalidResources() {
		return fInvalidResources;
	}
	
	

	
	
}

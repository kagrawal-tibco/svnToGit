package com.tibco.cep.bpmn.ui.graph.properties.filter;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.cep.bpmn.core.utils.ECoreHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;

/**
 * 
 * @author majha
 *
 */
public class BpmnProcessTreeViewerFilter extends ViewerFilter {
	
	private EObjectWrapper<EClass, EObject> process;

	public BpmnProcessTreeViewerFilter(
			EObjectWrapper<EClass, EObject> process) {
		this.process = process;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement,
			Object element) {
		try {
			if(element instanceof IFile) {
				IFile file = (IFile) element;
				if(file.getFileExtension().equalsIgnoreCase(BpmnCommonIndexUtils.BPMN_PROCESS_EXTENSION)&& isDifferentProcess(file.getFullPath()) && isPrivateProcess(file)) {
						return true;
				} 
			} else if( element instanceof IContainer) {
				if(element instanceof IFolder || element instanceof IProject)  {
					return hasProcesses((IContainer)element);
				}
				return true;
			}	
		} catch (CoreException e) {
			BpmnUIPlugin.log(e);
		}
		
		return false;
	}
	
	private boolean isDifferentProcess(IPath fullpath){
		if(process == null)
			return true;
		String path = fullpath.removeFirstSegments(1).makeAbsolute().toPortableString();
		String folder = process.getAttribute(BpmnMetaModelConstants.E_ATTR_FOLDER);
		String name = process.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		String processPath = null;
		
		if(folder.endsWith(Character.toString(IPath.SEPARATOR)))
			processPath =folder+name+"."+BpmnCommonIndexUtils.BPMN_PROCESS_EXTENSION;
		else
			processPath = folder+IPath.SEPARATOR+name+"."+BpmnCommonIndexUtils.BPMN_PROCESS_EXTENSION;
		
		if (processPath.equals(path))
			return false;
		
		return true;
	}

	private boolean hasProcesses(IContainer parentFolder) throws CoreException {
		boolean found = false;
		for(IResource r:parentFolder.members()){
			if(r instanceof IFolder) {
				IFolder folder = (IFolder) r;
				found = found || hasProcesses(folder);
			} else if(r instanceof IFile) {
				IFile file = (IFile) r;
				if(file.getFileExtension() != null) {
					found = file.getFileExtension().equalsIgnoreCase(BpmnCommonIndexUtils.BPMN_PROCESS_EXTENSION) && isDifferentProcess(file.getFullPath()) && isPrivateProcess(file);	
				} 
			}
			if(found) {
				return found;
			}
		}
		return found;
	}

	private boolean isPrivateProcess(IResource res){
		boolean isPrivate = false;
		EObject loadBpmnProcess = loadBpmnProcess(res);
		if(loadBpmnProcess != null){
			EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper.wrap(loadBpmnProcess);
			Object attribute = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_PROCESS_TYPE);
			if(attribute != null){
				EEnumLiteral type =(EEnumLiteral)attribute;
				if(type!= null){
					if(!type.equals(BpmnModelClass.ENUM_PROCESS_TYPE_PUBLIC))
						isPrivate = true;
				}
			}
		}
		return isPrivate;
	}
	
	
	private EObject loadBpmnProcess(IResource processResource){
		EObject process = null;
		try {
			EList<EObject> resources = ECoreHelper.deserializeModelXMI(processResource, true);
			process = resources.get(0);
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
		return process;
	}
}
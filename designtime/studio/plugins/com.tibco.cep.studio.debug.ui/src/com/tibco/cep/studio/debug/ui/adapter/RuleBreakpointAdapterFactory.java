package com.tibco.cep.studio.debug.ui.adapter;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;
import org.eclipse.ui.texteditor.ITextEditor;

import com.tibco.cep.studio.core.StudioCore;
import com.tibco.cep.studio.ui.editors.AbstractRuleFormEditor;

/*
@author ssailapp
@date Jul 2, 2009 2:05:36 PM
 */
@SuppressWarnings("rawtypes")
public class RuleBreakpointAdapterFactory implements IAdapterFactory {
	public static String[] validExtensions =StudioCore.getCodeExtensions(); 
	
	public RuleBreakpointAdapterFactory() {
	}
	
	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adaptableObject instanceof ITextEditor) {
			ITextEditor editorPart = (ITextEditor) adaptableObject;
			IResource resource = (IResource) editorPart.getEditorInput().getAdapter(IResource.class);
			if (resource != null) {
				String extension = resource.getFileExtension();
				if (extension != null ) {
					if(isValidExtension(extension) && IToggleBreakpointsTarget.class.equals(adapterType)) 
					return new RuleToggleBreakpointAdapter();
				}
			} else {
				IStorage storage = (IStorage) editorPart.getEditorInput().getAdapter(IStorage.class);
				if(storage != null) {
					String extension = storage.getFullPath().getFileExtension();
					if (extension != null ) {
						if(isValidExtension(extension) && IToggleBreakpointsTarget.class.equals(adapterType)) 
						return new RuleToggleBreakpointAdapter();
					}
				}
			}
		}
		if( adaptableObject instanceof AbstractRuleFormEditor ) {
			AbstractRuleFormEditor editorPart = (AbstractRuleFormEditor) adaptableObject;
			IResource resource = (IResource) editorPart.getEditorInput().getAdapter(IResource.class);
			if (resource != null) {
				String extension = resource.getFileExtension();
				if (extension != null ) {
					if(isValidExtension(extension)&& IToggleBreakpointsTarget.class.equals(adapterType)) 
					return new RuleToggleBreakpointAdapter();
				}
			} else {
				IStorage storage = (IStorage) editorPart.getEditorInput().getAdapter(IStorage.class);
				if(storage != null) {
					String extension = storage.getFullPath().getFileExtension();
					if (extension != null ) {
						if(isValidExtension(extension)&& IToggleBreakpointsTarget.class.equals(adapterType)) 
						return new RuleToggleBreakpointAdapter();
					}
				}
			}
		} 
		return null;
	}
	
	public boolean isValidExtension(String extension) {
		for(String s:validExtensions) {
			if(s.equalsIgnoreCase(extension)){
				return true;
			}
		}
		return false;
	}

	@Override
	public Class[] getAdapterList() {
		return new Class[]{IToggleBreakpointsTarget.class};
	}

}

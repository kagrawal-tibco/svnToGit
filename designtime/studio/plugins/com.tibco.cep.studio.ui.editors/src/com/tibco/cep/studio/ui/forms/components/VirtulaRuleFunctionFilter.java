package com.tibco.cep.studio.ui.forms.components;

import java.util.Set;

import org.eclipse.core.resources.IFile;

import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.wizards.OnlyFileInclusionFilter;

/**
 * 
 * @author sasahoo
 *
 */
public class VirtulaRuleFunctionFilter extends OnlyFileInclusionFilter{

	public VirtulaRuleFunctionFilter(Set<String> inclusions) {
		super(inclusions);
	}

	@Override
	protected boolean isEntityFile(Object file){
		if(StudioResourceUtils.isVirtual((IFile) file)){
			return inclusions.contains(((IFile) file).getFileExtension());
		}
		return false;
	}
}

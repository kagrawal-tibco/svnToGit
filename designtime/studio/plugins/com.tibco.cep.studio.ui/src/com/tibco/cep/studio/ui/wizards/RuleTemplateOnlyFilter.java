package com.tibco.cep.studio.ui.wizards;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.designtime.core.model.rule.RuleTemplate;
import com.tibco.cep.studio.core.SharedElementRootNode;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.SharedRuleElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.ui.filter.FileInclusionFilter;

/**
 */
public class RuleTemplateOnlyFilter extends FileInclusionFilter {

	/**
	 * @param inclusions
	 */
	@SuppressWarnings("unchecked")
	public RuleTemplateOnlyFilter(Set inclusions) {
		super(inclusions);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.components.FileInclusionFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IAdaptable) {
			IResource res = (IResource) ((IAdaptable) element).getAdapter(IResource.class);
			if (res instanceof IFile) {
				IFile file = (IFile) res;
				return isEntityFile(file) && IndexUtils.RULE_TEMPLATE_EXTENSION.equalsIgnoreCase(file.getFileExtension());
			}
			if (res instanceof IFolder) {
			    IFolder folder = (IFolder)res;
			    visible = false;
                return isVisible(folder);
			}
		}
		if (element instanceof SharedElementRootNode || element instanceof ElementContainer) {
			return true;
		} else if (element instanceof SharedRuleElement && ((SharedRuleElement) element).getRule() instanceof RuleTemplate) {
			return true;
		} else {
			if(!(element instanceof IResource)) {
				return false;
			}
		}
		return true;
	}

	@Override
	protected boolean isVisible(Object element) {

		Object[] object = CommonUtil.getResources((IFolder) element);
		
		for (Object obj : object) {
			if (obj instanceof IFolder) {
				isVisible(obj);
			}
			if (obj instanceof IFile){
				if (isEntityFile(obj) && IndexUtils.RULE_TEMPLATE_EXTENSION.equalsIgnoreCase(((IFile)obj).getFileExtension())) {
					visible = true;
				}
			}
		}
		
		if (visible == true) {
			return true;
		}
		return false;
	}
	
	
}

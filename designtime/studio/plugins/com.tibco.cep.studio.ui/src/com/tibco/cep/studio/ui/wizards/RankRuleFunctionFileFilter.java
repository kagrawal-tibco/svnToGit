package com.tibco.cep.studio.ui.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.studio.core.SharedElementRootNode;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.Folder;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.SharedRuleElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;

/**
 * 
 * @author sasahoo
 *
 */
public class RankRuleFunctionFileFilter extends ViewerFilter {
	
	protected boolean visible = false;
	
	/**
	 * @param viewer
	 * @param parentElement
	 * @param element
	 */
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IAdaptable) {
			IResource res = (IResource) ((IAdaptable) element).getAdapter(IResource.class);
			if (res instanceof IFile) {
				IFile file = (IFile) res;
				return isValidRankRuleFunction(file);
			}
			if (res instanceof IFolder){
			    IFolder folder = (IFolder)res;
			    visible = false;
                return isVisible(folder);
			}
		}
		if(element instanceof SharedElementRootNode){
			return true;
		}else if(element instanceof DesignerElement){
			if (element instanceof Folder) {
				return true;
			}
			//Check for SharedRuleElement
			if(element instanceof SharedRuleElement){
				SharedRuleElement ruleElement =  (SharedRuleElement)element;
				if(ruleElement.getRule()!= null && ruleElement.getRule().getReturnType() != null 
						&& ruleElement.getRule().getReturnType().equals("double")){
					return true;
				}
				return false;
			}
		}else{
			if(!(element instanceof IResource)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @param element
	 * @return
	 */
	protected boolean isVisible(Object element) {
		try{
			Object[] object = CommonUtil.getResources((IFolder) element);
			for(Object obj : object){
				if(obj instanceof IFolder){
					isVisible(obj);
				}
				if(obj instanceof IFile){
					if(isValidRankRuleFunction((IFile)obj)){
						visible = true;
					}
				}
			}
			if (visible == true) {
				return true;
			}
		}catch(Exception e){
			visible = false;
			e.printStackTrace();

		}
		return false;
	}

	
	/**
	 * Checks for Null values, VRF, type double
	 * @param file
	 * @return
	 */
	protected boolean isValidRankRuleFunction(IFile file){
		RuleElement ruleElement = IndexUtils.getRuleElement(file.getProject().getName(), IndexUtils.getFullPath(file), ELEMENT_TYPES.RULE_FUNCTION);
		if(ruleElement != null && ruleElement.getRule() != null && 
				ruleElement.getRule() instanceof RuleFunction){
			RuleFunction ruleFunction = (RuleFunction)ruleElement.getRule();
			if(!ruleFunction.isVirtual()){
				if(ruleElement.getRule().getReturnType() != null && ruleElement.getRule().getReturnType().equals("double")){
					return true;
				}
			}
		}
		return false;
	}
}
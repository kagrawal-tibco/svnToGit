package com.tibco.cep.studio.ui.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.Symbol;
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
 * @author rhollom
 *
 */
public class PreprocessorFileFilter extends ViewerFilter {
	
	protected boolean visible = false;
	private Entity targetEvent;
	
	public PreprocessorFileFilter(Entity targetEvent) {
		this.targetEvent = targetEvent;
	}
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
				return isValidPreprocessorFunction(file);
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
				if(ruleElement.getRule()!= null && ruleElement.getRule().getSymbols().getSymbolList().size() == 1) { 
					Symbol symbol = ruleElement.getRule().getSymbols().getSymbolList().get(0);
					if (targetEvent.getFullPath().equals(symbol.getType())) {
						return true;
					}
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
					if(isValidPreprocessorFunction((IFile)obj)){
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
	 * Checks whether the rule function contains exactly one argument matching the target event type
	 * @param file
	 * @return
	 */
	protected boolean isValidPreprocessorFunction(IFile file){
		RuleElement ruleElement = IndexUtils.getRuleElement(file.getProject().getName(), IndexUtils.getFullPath(file), ELEMENT_TYPES.RULE_FUNCTION);
		if(ruleElement != null && ruleElement.getRule() != null && 
				ruleElement.getRule() instanceof RuleFunction){
			RuleFunction ruleFunction = (RuleFunction)ruleElement.getRule();
			if(!ruleFunction.isVirtual()){
				if(ruleElement.getRule()!= null && ruleElement.getRule().getSymbols().getSymbolList().size() == 1) { 
					Symbol symbol = ruleElement.getRule().getSymbols().getSymbolList().get(0);
					if (targetEvent.getFullPath().equals(symbol.getType())) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
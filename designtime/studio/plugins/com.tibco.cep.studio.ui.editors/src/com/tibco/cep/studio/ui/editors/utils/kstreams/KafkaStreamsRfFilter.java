package com.tibco.cep.studio.ui.editors.utils.kstreams;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

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

public class KafkaStreamsRfFilter extends ViewerFilter {

	// Could be boolean or Object
	private String returnType;
	// Minimum number of arguments a rule function accepts
	private int minNumberOfArgs;
	// Maximum number of arguments a rule function accepts
	private int maxNumberOfArgs;

	public KafkaStreamsRfFilter(String returnType, int minNumberOfArgs, int maxNumberOfArgs) {
		this.returnType = returnType;
		this.minNumberOfArgs = minNumberOfArgs;
		this.maxNumberOfArgs = maxNumberOfArgs;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IAdaptable) {
			IResource res = (IResource) ((IAdaptable) element).getAdapter(IResource.class);
			if (res instanceof IFile) {
				IFile file = (IFile) res;
				return isValidStreamPreprocessor(file);
			}
			if (res instanceof IFolder) {
				IFolder folder = (IFolder) res;
				return isVisible(folder);
			}
		}
		if (element instanceof SharedElementRootNode) {
			return true;
		} else if (element instanceof DesignerElement) {
			if (element instanceof Folder) {
				return true;
			}
			// Check for SharedRuleElement
			if (element instanceof SharedRuleElement) {
				SharedRuleElement ruleElement = (SharedRuleElement) element;
				if (ruleElement.getRule() != null && ruleElement.getRule().getSymbols().getSymbolList().size() == 1) {
					Symbol symbol = ruleElement.getRule().getSymbols().getSymbolList().get(0);
//					if (targetEvent.getFullPath().equals(symbol.getType())) {
					return true;
//					}
				}
				return false;
			}
		} else {
			if (!(element instanceof IResource)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks whether the rule function contains arguments >= min and <= max and the
	 * return types is matching
	 * 
	 * @param file
	 * @return
	 */
	protected boolean isValidStreamPreprocessor(IFile file) {
		RuleElement ruleElement = IndexUtils.getRuleElement(file.getProject().getName(), IndexUtils.getFullPath(file),
				ELEMENT_TYPES.RULE_FUNCTION);
		if (ruleElement != null && ruleElement.getRule() != null && ruleElement.getRule() instanceof RuleFunction) {
			RuleFunction ruleFunction = (RuleFunction) ruleElement.getRule();
			if (!ruleFunction.isVirtual()) {
				if (ruleElement.getRule() != null) {
					String ruleReturnType = ruleElement.getRule().getReturnType();
					int numberOfArgs = ruleElement.getRule().getSymbols().getSymbolList().size();
					if (numberOfArgs >= minNumberOfArgs && numberOfArgs <= maxNumberOfArgs
							&& ((returnType == null && ruleReturnType == null)
									|| (ruleReturnType != null && ruleReturnType.matches(returnType))))
						return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @param element
	 * @return
	 */
	protected boolean isVisible(Object element) {
		try {
			Object[] object = CommonUtil.getResources((IFolder) element);
			for (Object obj : object) {
				if (obj instanceof IFolder) {
					isVisible(obj);
				}

			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}

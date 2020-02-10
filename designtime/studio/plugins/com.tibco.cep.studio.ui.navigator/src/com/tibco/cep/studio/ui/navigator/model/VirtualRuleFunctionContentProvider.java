/**
 * 
 */
package com.tibco.cep.studio.ui.navigator.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;

import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.StudioNavigatorNode;
import com.tibco.cep.studio.ui.navigator.providers.EntityContentProvider;

/**
 * Content provider to display inherited decision tables.
 * @author aathalye
 *
 */
public class VirtualRuleFunctionContentProvider extends EntityContentProvider {
	
	public boolean hasChildren(Object element) {
		if (!(element instanceof IFile)) {
			return false;
		}
		return true;
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.navigator.providers.EntityContentProvider#getChildren(java.lang.Object)
	 */
	@Override
	public Object[] getChildren(Object parentElement) {
		if (!(parentElement instanceof IFile)) {
			return EMPTY_CHILDREN;
		}
		IFile file = (IFile) parentElement;
		//Get 
		DesignerElement designerElement = IndexUtils.getElement(file);
		if (designerElement instanceof RuleElement) {
			RuleElement ruleElement = (RuleElement)designerElement;
			//Check if it is virtual
			if (!ruleElement.isVirtual()) {
				return EMPTY_CHILDREN;
			}
			return getChildren(ruleElement, file);
		}
		return EMPTY_CHILDREN;
	}
	
	private Object[] getChildren(RuleElement ruleElement, IFile ruleFunctionFile) {
		DesignerProject index = 
			StudioCorePlugin.getDesignerModelManager().getIndex(ruleFunctionFile.getProject());
		
		//The VRF can be moved to any directory.
		//So we can't check only container folder 
//		ElementContainer folder = 
//			IndexUtils.getFolderForFile(index, ruleFunctionFile);
//		List<DesignerElement> childEntries = folder.getEntries();

		List<DesignerElement> childEntries = IndexUtils.getAllElements(index.getName(), ELEMENT_TYPES.DECISION_TABLE);
		List<StudioNavigatorNode> navigatorNodes = new ArrayList<StudioNavigatorNode>();
		//Check dt entries
		for (DesignerElement designerElement : childEntries) {
			if (designerElement instanceof DecisionTableElement) {
				DecisionTableElement decisionTableElement = (DecisionTableElement)designerElement;
				Implementation implementation = 
					(Implementation)decisionTableElement.getImplementation();
				//Get implmented resource path
				String vrfPath = implementation.getImplements();
				if (ruleElement.getRule().getFullPath().equals(vrfPath)) {
					navigatorNodes.add(new VirtualRuleFunctionImplementationNavigatorNode(decisionTableElement, index.getName()));
				}
			}
		}
		return navigatorNodes.toArray();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.navigator.providers.EntityContentProvider#getEntityChildren(com.tibco.cep.designtime.core.model.Entity)
	 */
	@Override
	protected Object[] getEntityChildren(Entity entity, boolean isSharedElement) {
		// TODO Auto-generated method stub
		return null;
	}

	
}

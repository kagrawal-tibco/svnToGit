package com.tibco.cep.bpmn.ui.viewers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.rule.Symbols;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.StudioNavigatorNode;
import com.tibco.cep.studio.ui.viewers.TreeViewerFilter;

/**
 * @author majha
 * 
 */
public class RuleFunctionTreeViewerFilterForGatewayJoin extends TreeViewerFilter {

	public RuleFunctionTreeViewerFilterForGatewayJoin(IProject project, ELEMENT_TYPES[] types) {
		super(project, types);
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		boolean select = super.select(viewer, parentElement, element);
		
		if(element instanceof IFile){
			DesignerElement res = IndexUtils
					.getElement((IResource) element);
			select = isJoinRuleFunction(res);
		}

		return select;
	}
	
	protected boolean hasChildren(Object parent,ELEMENT_TYPES[] elTypes) throws CoreException {
		@SuppressWarnings("unused")
		List<Object> children = new ArrayList<Object>();
		boolean hasChildren = false;
		if(parent instanceof IProject){
			IResource[] members = ((IProject) parent).members();
			for (IResource resource : members) {
				hasChildren = hasChildren || hasChildren(resource, elTypes);
			}
		} else if (parent instanceof IContainer) {
			IResource[] members = ((IContainer)parent).members();
			for (IResource resource : members) {
				hasChildren = hasChildren || hasChildren(resource, elTypes);
			}
		} else if(parent instanceof IFile) {
			DesignerElement res = IndexUtils
			.getElement((IResource) parent);
			if (res != null){
					
					if(Arrays.asList(getTypes()).contains(
							res.getElementType())) {
						if(isJoinRuleFunction(res))
							hasChildren = true;
					} else if(res.getElementType() == ELEMENT_TYPES.CHANNEL && 
							Arrays.asList(getTypes()).contains(ELEMENT_TYPES.DESTINATION)) {
						hasChildren = true;
					}
			}
		} else if(parent instanceof StudioNavigatorNode) {
			StudioNavigatorNode node = (StudioNavigatorNode) parent;
			Entity e = node.getEntity();
			 if(e != null && Arrays.asList(getTypes()).contains(IndexUtils.getElementType(e))){
				 hasChildren = true;
			 }
		} else if(parent instanceof SharedEntityElement) {
			SharedEntityElement se = (SharedEntityElement) parent;
			Entity e = se.getEntity();
			if(e != null && Arrays.asList(getTypes()).contains(IndexUtils.getElementType(e))){
				 hasChildren = true;
			}
		}
		return hasChildren;
	}
	
	private boolean isJoinRuleFunction(DesignerElement res){
		boolean joinRulefnc = false;
		if (res != null && res instanceof RuleElement) {
			RuleElement rf = (RuleElement) res;
			Entity entity = IndexUtils.getRule(getProject().getName(), rf.getFolder(), rf.getName(), ELEMENT_TYPES.RULE_FUNCTION);
			if(entity instanceof RuleFunction){
				RuleFunction rfn = (RuleFunction)entity;
				String returnType = rfn.getReturnType();
				if(returnType != null && returnType.equalsIgnoreCase("object")){
					Symbols symbols = rfn.getSymbols();
					EList<Symbol> symbolList = symbols.getSymbolList();
					if(symbolList.size() == 2){
						Symbol symbol = symbolList.get(0);
			            String typeName = symbol.getType();
						if(!symbol.isArray() && typeName.equalsIgnoreCase("object")){
							 symbol = symbolList.get(1);
				             typeName = symbol.getType();
				             if(!symbol.isArray() && typeName.equalsIgnoreCase("string")){
				            	 joinRulefnc = true;
				             }
						}
					}
				}
				
			}
		}
		return joinRulefnc;
	}

}
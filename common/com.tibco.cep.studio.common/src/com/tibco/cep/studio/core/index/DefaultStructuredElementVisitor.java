package com.tibco.cep.studio.core.index;

import com.tibco.cep.studio.core.index.model.ArchiveElement;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.Folder;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.IStructuredElementVisitor;
import com.tibco.cep.studio.core.index.model.LocalVariableDef;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.StateMachineElement;
import com.tibco.cep.studio.core.index.model.StructuredElement;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;

public class DefaultStructuredElementVisitor implements
		IStructuredElementVisitor {

	public boolean postVisit(StructuredElement element) {
		return true;
	}

	public boolean preVisit(StructuredElement element) {
		return true;
	}

	public boolean visit(StructuredElement element) {
		if (element instanceof DesignerProject) {
			return visitDesignerProject((DesignerProject) element);
		} else if (element instanceof Folder) {
			return visitFolder((Folder) element);
		} else if (element instanceof GlobalVariableDef) {
			return visitGlobalVariableDefinition((GlobalVariableDef) element);
		} else if (element instanceof LocalVariableDef) {
			return visitLocalVariableDefinition((LocalVariableDef) element);
		} else if (element instanceof ArchiveElement) {
			return visitArchiveElement((ArchiveElement) element);
		} else if (element instanceof DecisionTableElement) {
			return visitDecisionTableElement((DecisionTableElement) element);
		} else if (element instanceof StateMachineElement) {
			// this must come before EntityElement
			return visitStateMachineElement((StateMachineElement) element);
		} else if (element instanceof EntityElement) {
			return visitEntityElement((EntityElement) element);
		} else if (element instanceof RuleElement) {
			return visitRuleElement((RuleElement) element);
		} else if (element instanceof ScopeBlock) {
			return visitScopeBlock((ScopeBlock) element);
		}
		
		return true;
	}

	public boolean visitChildren(StructuredElement element) {
		return true;
	}

	public boolean visitArchiveElement(ArchiveElement element) {
		return true;
	}

	public boolean visitDecisionTableElement(DecisionTableElement element) {
		return true;
	}

	public boolean visitDesignerProject(DesignerProject element) {
		return true;
	}

	public boolean visitEntityElement(EntityElement element) {
		return true;
	}

	public boolean visitFolder(Folder element) {
		return true;
	}

	public boolean visitGlobalVariableDefinition(GlobalVariableDef element) {
		return true;
	}

	public boolean visitLocalVariableDefinition(LocalVariableDef element) {
		return true;
	}

	public boolean visitRuleElement(RuleElement element) {
		return true;
	}

	public boolean visitScopeBlock(ScopeBlock element) {
		return true;
	}

	public boolean visitStateMachineElement(StateMachineElement element) {
		return true;
	}

}

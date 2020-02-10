package com.tibco.cep.decision.table;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.decision.table.model.dtmodel.Argument;
import com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelFactory;
import com.tibco.cep.decision.table.model.dtmodel.ResourceType;
import com.tibco.cep.decision.table.model.dtmodel.impl.TableImpl;
import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.rule.Symbols;
import com.tibco.cep.designtime.core.model.rule.impl.RuleFunctionImpl;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.update.IStudioElementDelta;
import com.tibco.cep.studio.core.index.update.IStudioModelChangedListener;
import com.tibco.cep.studio.core.index.update.StudioElementDelta;
import com.tibco.cep.studio.core.index.update.StudioModelChangedEvent;
import com.tibco.cep.studio.core.index.update.StudioModelDelta;
import com.tibco.cep.studio.core.index.update.StudioProjectDelta;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.utils.ModelUtils;

public class DecisionTableArgUpdater implements IStudioModelChangedListener {

	@Override
	public void modelChanged(StudioModelChangedEvent event) {
		StudioModelDelta delta = event.getDelta();
		List<StudioProjectDelta> changedProjects = delta.getChangedProjects();
		for (StudioProjectDelta designerProjectDelta : changedProjects) {
			DesignerProject changedProject = designerProjectDelta.getChangedProject();
			if (designerProjectDelta.getType() == IStudioElementDelta.ADDED
					|| designerProjectDelta.getType() == IStudioElementDelta.REMOVED) {
				break;
			}
			List<RuleElement> elementsToRefresh = new ArrayList<RuleElement>();
			collectRuleElements(designerProjectDelta, elementsToRefresh);
			for (RuleElement ruleElement : elementsToRefresh) {
				updateDTModel(changedProject, ruleElement);
			}
		}
	}

	protected void collectRuleElements(ElementContainer delta, List<RuleElement> elementsToRefresh) {
		EList<DesignerElement> entries = delta.getEntries();
		for (DesignerElement designerElement : entries) {
			if (designerElement instanceof ElementContainer) {
				collectRuleElements((ElementContainer) designerElement, elementsToRefresh);
			} else if (designerElement instanceof StudioElementDelta) {
				DesignerElement affectedChild = ((IStudioElementDelta) designerElement).getAffectedChild();
				if (!elementsToRefresh.contains(affectedChild)) {
					if (affectedChild instanceof RuleElement && affectedChild.getElementType().equals(ELEMENT_TYPES.RULE_FUNCTION) && ((RuleElement)affectedChild).isVirtual()) {
						elementsToRefresh.add((RuleElement) affectedChild);
					}
				}
			}
		}
	}

	private void updateDTModel(DesignerProject index,
			RuleElement ruleIndexEntry) {
		try {
			// Get the new rule element : NOTE: CommonIndexUtils MUST be used here to avoid re-joining update job
			RuleElement ruleElement = CommonIndexUtils.getRuleElement(ruleIndexEntry.getIndexName(), ruleIndexEntry.getFolder(), ruleIndexEntry.getName(), ELEMENT_TYPES.RULE_FUNCTION);
			if (ruleElement == null) {
				return;
			}
			RuleFunctionImpl ruleFunction = (RuleFunctionImpl) ruleElement
					.getRule();
			Object[] ruleFunctionImplArray = getChildren(index, ruleIndexEntry);
			for (Object ruleFunctionImpl : ruleFunctionImplArray) {
				DecisionTableElement decisionTableElement = (DecisionTableElement) ruleFunctionImpl;
				if (!requiresUpdate(decisionTableElement, ruleElement)) {
					continue;
				}
				((TableImpl) decisionTableElement.getImplementation())
						.getArgument().clear();
				EList<Argument> decisionTableArguments = ((TableImpl) decisionTableElement
						.getImplementation()).getArgument();
				EList<Symbol> map = ruleFunction.getSymbols().getSymbolList();
				// Transform the arguments
				for (Symbol symbol : map) {
					Argument argument = DtmodelFactory.eINSTANCE
							.createArgument();
					ArgumentProperty argProperty = DtmodelFactory.eINSTANCE
							.createArgumentProperty();
					String alias = symbol.getIdName();
					argProperty.setArray(symbol.isArray());
					argProperty.setAlias(alias);
					String typePath = symbol.getType();
					String direction = getElementDirection(decisionTableElement);
					argument.setDirection(direction);
					DesignerElement designerElement = CommonIndexUtils
							.getElement(ruleFunction.getOwnerProjectName(),
									typePath);
					setResourceType(designerElement, argProperty);
					argProperty.setPath(typePath);
					String extension = symbol.getTypeExtension();
					argProperty.setType(extension);
					argument.setProperty(argProperty);
					decisionTableArguments.add(argument);

				}

				ModelUtils.saveEObject(decisionTableElement.getImplementation());
				IFile file = IndexUtils.getFile(ruleElement.getIndexName(), decisionTableElement);
				CommonUtil.refresh(file, 0, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	    	
	private boolean requiresUpdate(DecisionTableElement decisionTableElement,
			RuleElement ruleElement) {
		Symbols symbols = ruleElement.getRule().getSymbols();
		EList<Symbol> symbolList = symbols.getSymbolList();
		EList<Argument> arguments = ((TableImpl) decisionTableElement.getImplementation()).getArgument();
		if (symbolList.size() != arguments.size()) {
			return true;
		}
		for (int i = 0; i < arguments.size(); i++) {
			Argument arg = arguments.get(i);
			if (symbolList.size() > i) {
				ArgumentProperty property = arg.getProperty();
				String alias = property.getAlias();
				String type = property.getPath();
				Symbol ruleSymbol = symbolList.get(i);
				if (alias.equals(ruleSymbol.getIdName()) && type.equals(ruleSymbol.getType()) && property.isArray() == ruleSymbol.isArray()) {
					continue;
				}
			}
			return true;
		}
		return false;
	}

	private String getElementDirection(DesignerElement designerElement) {
		ELEMENT_TYPES element_type = designerElement.getElementType();
		switch (element_type) {
		case CONCEPT:
			return "BOTH";
		case SIMPLE_EVENT:
			return "IN";
		case TIME_EVENT:
			return "IN";
		default:
			return "BOTH";
		}
	}
	
	private void setResourceType(DesignerElement designerElement,
			                     ArgumentProperty argumentProperty) {
		if (designerElement == null) {
			return; // element was deleted (?)
		}
		ELEMENT_TYPES element_type = designerElement.getElementType();
		
		switch (element_type) {
		case CONCEPT:
			argumentProperty.setResourceType(ResourceType.CONCEPT);
			break;
		case SIMPLE_EVENT:
		case TIME_EVENT:
			argumentProperty.setResourceType(ResourceType.EVENT);
			break;
		default:
			
		}
	}
	
	private Object[] getChildren(DesignerProject index, RuleElement ruleElement) {
		List<DecisionTableElement> childEntries = index.getDecisionTableElements();
		List<DecisionTableElement> decisionTables = new ArrayList<DecisionTableElement>();
		//Check dt entries
		for (DecisionTableElement decisionTableElement : childEntries) {
			try {
				Implementation implementation = null;
				EObject object = decisionTableElement.getImplementation();
				if(object instanceof Implementation) {
					implementation = (Implementation)object;

					//Get implmented resource path
					String vrfPath = implementation.getImplements();
					if (ruleElement.getRule().getFullPath().equals(vrfPath)) {
						decisionTables.add(decisionTableElement);;
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return decisionTables.toArray();
	}

}

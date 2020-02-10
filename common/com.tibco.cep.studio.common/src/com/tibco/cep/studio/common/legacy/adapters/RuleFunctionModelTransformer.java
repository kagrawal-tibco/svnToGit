/**
 * 
 */
package com.tibco.cep.studio.common.legacy.adapters;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.decision.table.model.dtmodel.Argument;
import com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelFactory;
import com.tibco.cep.decision.table.model.dtmodel.ResourceType;
import com.tibco.cep.decisionproject.ontology.Arguments;
import com.tibco.cep.decisionproject.ontology.OntologyFactory;
import com.tibco.cep.decisionproject.ontology.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/**
 * @author aathalye
 *
 */
public class RuleFunctionModelTransformer implements IModelTransformer<RuleFunction, com.tibco.cep.designtime.core.model.rule.RuleFunction> {

	
	public com.tibco.cep.designtime.core.model.rule.RuleFunction adapt(RuleFunction adaptFrom,
			com.tibco.cep.designtime.core.model.rule.RuleFunction adaptTo) {
		throw new UnsupportedOperationException("To be Done");
	}

	
	public RuleFunction transform(com.tibco.cep.designtime.core.model.rule.RuleFunction adaptFrom,
								  RuleFunction adaptTo) {
		if (adaptFrom == null || adaptTo == null) {
			throw new IllegalArgumentException("Adapter parameters illegal");
		}
		adaptTo.setName(adaptFrom.getName());
		adaptTo.setFolder(adaptFrom.getFolder());
		adaptTo.setDescription(adaptFrom.getDescription());
		Arguments arguments = OntologyFactory.eINSTANCE.createArguments();
		transform(adaptFrom, arguments);
		adaptTo.setArguments(arguments);
		return adaptTo;
	}
	
	private void transform(com.tibco.cep.designtime.core.model.rule.RuleFunction adaptFrom,
			               Arguments arguments) {
		String ownerProject = adaptFrom.getOwnerProjectName();
    	EList<Symbol> map = adaptFrom.getSymbols().getSymbolList();
    	//Transform the arguments
    	for (Symbol symbol : map) {
			Argument argument = DtmodelFactory.eINSTANCE.createArgument();
			ArgumentProperty argProperty = DtmodelFactory.eINSTANCE.createArgumentProperty();
			//Get alias
			String alias = symbol.getIdName();
			argProperty.setArray(symbol.isArray());
			argProperty.setAlias(alias);
			String typePath = symbol.getType();
			//Resolve element type of each symbol
			DesignerElement designerElement = CommonIndexUtils.getElement(ownerProject, typePath);
			
			if (designerElement != null) {
				//This is probably a primitive
				String direction = getElementDirection(designerElement);
				argument.setDirection(direction);
				//For this type path get the actual type Concept/Event etc.
				setResourceType(designerElement, argProperty);
			}
			
			argProperty.setPath(typePath);
			String extension = symbol.getTypeExtension();
			argProperty.setType(extension);
			argument.setProperty(argProperty);
			arguments.getArgument().add(argument);
		}
	}

	
	public com.tibco.cep.designtime.core.model.rule.RuleFunction transform(
			RuleFunction adaptFrom,
			com.tibco.cep.designtime.core.model.rule.RuleFunction adaptTo) {
		// TODO Auto-generated method stub
		return null;
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
}

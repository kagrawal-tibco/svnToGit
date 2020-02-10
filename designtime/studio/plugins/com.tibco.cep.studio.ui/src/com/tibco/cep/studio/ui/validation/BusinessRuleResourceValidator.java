/**
 * 
 */
package com.tibco.cep.studio.ui.validation;

import java.io.InputStream;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.rule.Symbols;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.validation.DefaultResourceValidator;
import com.tibco.cep.studio.core.validation.ValidationContext;
import com.tibco.cep.webstudio.model.rule.instance.Actions;
import com.tibco.cep.webstudio.model.rule.instance.Binding;
import com.tibco.cep.webstudio.model.rule.instance.BuilderSubClause;
import com.tibco.cep.webstudio.model.rule.instance.Command;
import com.tibco.cep.webstudio.model.rule.instance.Filter;
import com.tibco.cep.webstudio.model.rule.instance.FilterValue;
import com.tibco.cep.webstudio.model.rule.instance.MultiFilter;
import com.tibco.cep.webstudio.model.rule.instance.RelatedFilterValue;
import com.tibco.cep.webstudio.model.rule.instance.RelatedLink;
import com.tibco.cep.webstudio.model.rule.instance.RuleTemplateInstance;
import com.tibco.cep.webstudio.model.rule.instance.SimpleFilterValue;
import com.tibco.cep.webstudio.model.rule.instance.SingleFilter;
import com.tibco.cep.webstudio.model.rule.instance.util.RuleTemplateInstanceDeserializer;

/**
 */
public class BusinessRuleResourceValidator extends DefaultResourceValidator {

	@Override
	public boolean canContinue() {
		return true;
	}

	@Override
	public boolean enablesFor(IResource resource) {
		return super.enablesFor(resource);
	}

	@Override
	public boolean validate(ValidationContext validationContext) {
		IResource resource = validationContext.getResource();
		if (resource == null) return true;
		// perform basic resource validation for folder etc
		super.validate(validationContext);
		
		// get Model Object
		InputStream is = null;
		try {
			is = ((IFile)resource).getContents();
			RuleTemplateInstanceDeserializer ds = new RuleTemplateInstanceDeserializer(is);
			ds.deserialize();
			RuleTemplateInstance rti = ds.getDeserialized();
			validateBusinessRule((IFile) resource, rti);
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (Exception e) {
			}
		}
		
		return true;
	}

	private void validateBusinessRule(IFile file,
			RuleTemplateInstance rti) {
		validateBindings(rti.getBindings());
		
		validateConditions(file, rti.getConditionFilter());
		validateActions(file, rti.getActions());
	}

	private void validateBindings(List<Binding> bindings) {
		
	}

	private void validateActions(IFile file, Actions actions) {
		if (actions == null || actions.getActions().isEmpty()) {
			return;
		}
		List<Command> actionsList = actions.getActions();
		for (Command command : actionsList) {
			validateCommand(file, command);
		}
	}

	private void validateCommand(IFile file, Command command) {
		if ("call".equalsIgnoreCase(command.getActionType())) {
			String type = command.getType();
			int idx = type.lastIndexOf('.');
			if (idx > 0) {
				type = type.substring(0, idx);
			}
			RuleElement ruleElement = IndexUtils.getRuleElement(file.getProject().getName(), type, ELEMENT_TYPES.RULE_FUNCTION);
			if (ruleElement == null) {
				// report error?
				return;
			}
			Symbols symbols = ruleElement.getRule().getSymbols();
			EList<Symbol> symbolList = symbols.getSymbolList();
			BuilderSubClause subClause = command.getSubClause();
			if (subClause == null) {
				return; // report invalid RTI?
			}
			List<Filter> filters = subClause.getFilters();
			
			if (symbolList.size() != filters.size()) {
				reportInvalidMethodCallProblem(command, ruleElement, file, "Invalid number of parameters");
				return;
			}
			// check types
			for (int i=0; i<symbolList.size(); i++) {
				Symbol symbol = symbolList.get(i);
				SingleFilter filter = (SingleFilter) filters.get(i); // should always be SingleFilter (correct?)
				matchSymbolType(command, ruleElement, file, symbol, filter);
				matchValueType(command, ruleElement, file, symbol, filter);
			}
		}
	}

	private void reportInvalidMethodCallProblem(Command callCommand, RuleElement ruleElement,
			IFile file, String message) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Invalid call to Rule Function '").append(callCommand.getType()).append("'.  ");
		buffer.append(message);
		
		reportProblem(file, buffer.toString());
	}

	private void matchValueType(Command command, RuleElement ruleElement, IFile file, Symbol symbol, SingleFilter filter) {
		String type = symbol.getType();
		FilterValue filterValue = filter.getFilterValue();
		int paramIndex = command.getSubClause().getFilters().indexOf(filter)+1;
		try {
			String valueType = getValueType(type, filterValue);
			if (!type.equals(valueType)) {
				reportInvalidMethodCallProblem(command, ruleElement, file, "Invalid parameter type at parameter "+paramIndex+", expecting '"+type+"', got '"+valueType+"'");
			}
		} catch (IllegalArgumentException e) {
			reportInvalidMethodCallProblem(command, ruleElement, file, "Invalid parameter type at parameter "+paramIndex+": "+e.getMessage());
		}
	}

	private String getValueType(String expectedType, FilterValue filterValue) throws IllegalArgumentException {
		if (filterValue instanceof RelatedFilterValue) {
			return getRelatedFilterValueType(expectedType, (RelatedFilterValue) filterValue);
		} else if (filterValue instanceof SimpleFilterValue) {
			String value = ((SimpleFilterValue) filterValue).getValue();
			// see if the value can be cast to the expectedType
			if ("String".equals(expectedType)) {
				return "String";
			} else if ("Object".equals(expectedType)) {
				return "Object";
			} else if ("int".equals(expectedType)) {
				try {
					Integer.parseInt(value);	
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException(e);
				}
			} else if ("double".equals(expectedType)) {
				try {
					Double.parseDouble(value);	
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException(e);
				}
			} else if ("boolean".equals(expectedType)) {
				try {
					Boolean.parseBoolean(value);	
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException(e);
				}
			} else if ("long".equals(expectedType)) {
				try {
					Long.parseLong(value);	
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException(e);
				}
			} else {
				// for now, don't validate DateTimes, Event types, Concept types, etc
				// TODO : Properly validate DateTimes, Event/Concept types, including hierarchy
			}
		}
		return expectedType;
	}

	private String getRelatedFilterValueType(String expectedType, RelatedFilterValue filterValue) {
		List<RelatedLink> links = filterValue.getLinks();
		if (links.isEmpty()) {
			return expectedType; // the field is set via the operator (i.e. set to null or set to true), return the expected type
		}
		RelatedLink relatedLink = links.get(links.size()-1);
		String linkType = relatedLink.getLinkType();
		int idx = linkType.lastIndexOf('.');
		if (idx > 0) {
			linkType = linkType.substring(0, idx);
		}
		return linkType;
	}

	private void matchSymbolType(Command command, RuleElement ruleElement, IFile file, Symbol symbol, SingleFilter filter) {
		String idName = symbol.getIdName();
		List<RelatedLink> links = filter.getLinks();
		int paramIndex = command.getSubClause().getFilters().indexOf(filter)+1;
		if (links.size() != 1) {
			// should this ever happen?
			reportInvalidMethodCallProblem(command, ruleElement, file, "Invalid parameter type for parameter "+paramIndex);
		}
		RelatedLink relatedLink = links.get(0);
		String linkText = relatedLink.getLinkText();
		if (!idName.equals(linkText)) {
			reportInvalidMethodCallProblem(command, ruleElement, file, "Improper parameter order, expecting id '"+idName+"' but got '"+linkText+"' at parameter "+paramIndex);
		}
	}

	private void validateConditions(IFile file, MultiFilter conditions) {
		// TODO Validate conditions in Business Rule
	}
	
}

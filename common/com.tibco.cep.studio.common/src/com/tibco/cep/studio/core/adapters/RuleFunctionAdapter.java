package com.tibco.cep.studio.core.adapters;

import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.designtime.model.rule.Symbol;
import com.tibco.cep.designtime.model.rule.Symbols;
import com.tibco.cep.studio.core.rules.CommonRulesParserManager;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;

public class RuleFunctionAdapter<R extends com.tibco.cep.designtime.core.model.rule.RuleFunction> extends CompilableAdapter<R> implements
		RuleFunction {

	public RuleFunctionAdapter(R adapted,
			                   Ontology emfOntology) {
		super(adapted, emfOntology);
	}

	@Override
	protected R getAdapted() {
		return adapted;
	}

	public String getArgumentType(String identifier) {
		SymbolsAdapter symbolsAdapter = new SymbolsAdapter(adapted.getSymbols(),emfOntology);
		Symbol symbol = symbolsAdapter.getSymbol(identifier);
		if (symbol != null) {
			return symbol.getType();
		}
		return null;
	}

	public Symbols getArguments() {
		return getScope();
	}

	public String getBody() {
		return getActionText();
	}

	public String getReturnTypeWithExtension() {
		return adapted.getReturnType();
	}

	public Validity getValidity() {
		return Validity.valueOf(adapted.getValidity().getLiteral());
	}

	public boolean isVirtual() {
		return adapted.isVirtual();
	}
	
	public CodeBlock getScopeCodeBlock() {
		return CommonRulesParserManager.calculateOffset(RulesParser.SCOPE_BLOCK, getSource());
	}
	
	public CodeBlock getBodyCodeBlock() {
		return CommonRulesParserManager.calculateOffset(RulesParser.BODY_BLOCK, getSource());
	}
	
	public String getSource() {
//		return ModelUtils.getRuleFunctionAsSource(adapted);
		return adapted.getFullSourceText();
	}

//	@Override
//	public Map getExtendedProperties() {
//		if (adapted.getAlias() != null) {
//			LinkedHashMap<Object, Object> map = new LinkedHashMap<Object, Object>();
//			map.put("alias", adapted.getAlias());
//			return map;
//		}
//		return super.getExtendedProperties();
//	}

	@Override
	public String getAlias() {
		return adapted.getAlias();
	}	
	
	
}

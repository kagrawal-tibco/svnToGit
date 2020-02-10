package com.tibco.cep.studio.core.adapters;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.tibco.cep.designtime.core.model.rule.impl.RuleFunctionImpl;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.designtime.model.rule.RuleSet;
import com.tibco.cep.designtime.model.rule.Symbol;
import com.tibco.cep.designtime.model.rule.Symbols;
import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.common.util.Path;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.rules.CommonRulesParserManager;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.xml.datamodel.XiNode;

public class RuleAdapter<R extends com.tibco.cep.designtime.core.model.rule.Rule> extends CompilableAdapter<R> implements Rule {

	public RuleAdapter(R adapted,
			           Ontology emfOntology) {
		super(adapted, emfOntology);
	}

	@Override
	protected R getAdapted() {
		return adapted;
	}

	public boolean doesRequeue() {
		return adapted.isRequeue();
	}

	public String getAuthor() {
		return adapted.getAuthor();
	}

	public Symbols getDeclarations() {
		com.tibco.cep.designtime.core.model.rule.Symbols symbols = adapted.getSymbols();
		return new SymbolsAdapter(symbols.getSymbolList(),emfOntology);
	}

	public String getEntityPathForIdentifier(String identifier) {
		SymbolsAdapter symbolsAdapter = new SymbolsAdapter(adapted.getSymbols(),emfOntology);
		Symbol symbol = symbolsAdapter.getSymbol(identifier);
		if (symbol != null) {
			return symbol.getType();
		}
		return null;
	}

	public int getPriority() {
		return adapted.getPriority();
	}

	public int getRequeueCount() {
		return 0;
	}

	public Set<String> getRequeueIdentifiers() {
		List<String> requeVars = adapted.getRequeueVars();
		Set<String> set = new LinkedHashSet<String>(requeVars);
		return set;
	}

	public RuleSet getRuleSet() {
		if (emfOntology instanceof CoreOntologyAdapter) {
			ElementContainer folder = getContainer();
			
			FolderAdapter adapter = new FolderAdapter(emfOntology, folder);
			
			return adapter;
		}
		return null;
	}

	protected ElementContainer getContainer() {
		String projectName = emfOntology.getName();
		String filePath = adapted.getFullPath();
		//Get parent dir
		Path fullPath = new Path(filePath);
		//Remove last part
		String[] segments = fullPath.segments();
		
		fullPath = fullPath.uptoSegment(segments.length - 1);
		ElementContainer folder = 
			CommonIndexUtils.getFolderForFile(projectName, fullPath.toString());
		if (folder == null) {
			// check whether this folder exists in a project library
			folder = CommonIndexUtils.getFolderForFile(StudioProjectCache.getInstance().getIndex(projectName), fullPath.toString(), false, true);
		}
		return folder;
	}

	public long getStartTime() {
		return adapted.getStartTime();
	}

	public XiNode getTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getTestInterval() {
		// TODO Auto-generated method stub
		return adapted.getTestInterval();
	}

	public boolean isConditionFunction() {
		// TODO Auto-generated method stub
		return adapted.isConditionFunction();
	}

	public boolean isEmptyRule() {
		return adapted.isEmpty();
	}

	public boolean isFunction() {
		return adapted.isFunction();
	}

	public boolean usesBackwardChaining() {
		return adapted.isBackwardChain();
	}

	public boolean usesForwardChaining() {
		return adapted.isForwardChain();
	}
	
	public CodeBlock getDeclCodeBlock() {
		return CommonRulesParserManager.calculateOffset(RulesParser.DECLARE_BLOCK, getSource());
	}
	
	public CodeBlock getWhenCodeBlock() {
		return CommonRulesParserManager.calculateOffset(RulesParser.WHEN_BLOCK, getSource());
	}
	
	public CodeBlock getThenCodeBlock() {
		return CommonRulesParserManager.calculateOffset(RulesParser.THEN_BLOCK, getSource());
	}
	
	public String getSource() {
		if(adapted.getFullSourceText() != null) {
			return adapted.getFullSourceText();
		} else {
			return ModelUtils.getRuleAsSource(adapted);
		}
	}

	@Override
	public RuleFunction getRank() {
		com.tibco.cep.designtime.core.model.rule.RuleFunction rankRuleFunction = adapted.getRankRuleFunction();
		if (rankRuleFunction != null) {
			return new RuleFunctionAdapter<RuleFunctionImpl>((RuleFunctionImpl) rankRuleFunction, getOntology());
		}
		return null;
	}

	@Override
	public String getRankPath() {
		return adapted.getRank();
	}
	
	
}

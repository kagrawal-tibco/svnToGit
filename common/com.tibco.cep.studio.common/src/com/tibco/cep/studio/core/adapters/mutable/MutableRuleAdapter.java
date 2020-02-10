package com.tibco.cep.studio.core.adapters.mutable;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.rule.RuleSet;
import com.tibco.cep.designtime.model.rule.Symbols;
import com.tibco.cep.studio.core.adapters.RuleAdapter;

public class MutableRuleAdapter extends RuleAdapter<Rule> implements com.tibco.cep.designtime.model.rule.Rule {

    

	private RuleSet m_ruleset;

	public MutableRuleAdapter(Rule adapted, Ontology o) {
		super(adapted, o);
		// TODO Auto-generated constructor stub
	}



	public MutableRuleAdapter(Ontology ontology, RuleSet ruleSet, String name,
			boolean isFunction) {
		this(RuleFactory.eINSTANCE.createRule(),ontology);
		adapted.setName(name);
		if(ruleSet != null) {
			adapted.setFolder(ruleSet.getFolderPath());
		}
		adapted.setPriority(5);
		adapted.setTestInterval(-1);
		adapted.setStartTime(-1);
		adapted.setRequeue(false);
		adapted.setMaxRules(1);
		adapted.setForwardChain(true);
		adapted.setBackwardChain(false);
		adapted.setConditionText("");
		adapted.setActionText("");
		adapted.setFunction(isFunction);
		adapted.setAuthor("");
		adapted.setCompilationStatus(-1);

	}
	
	public void setStartTime(long startTimeInMilliseconds) {
		setStartTime(startTimeInMilliseconds);
    }

	public void setName(String name) {
		adapted.setName(name);
	}
	
	@Override
	public RuleSet getRuleSet() {
		if(m_ruleset != null) {
			return m_ruleset;
		} else
			return super.getRuleSet();
	}

	public void setDefaultRuleSet(RuleSet ruleSet) {
		this.m_ruleset = ruleSet;
		adapted.setFolder(ruleSet.getFullPath());
	}
	
	public void setDeclarations(Symbols declarations) {
		this.setScope(declarations);		
	}
	
	@Override
	public String getFullPath() {
		if(m_ruleset != null) {
			return m_ruleset.getFullPath() + Folder.FOLDER_SEPARATOR_CHAR + getName();
		} else 
			return super.getFullPath();
	}

	private void setScope(com.tibco.cep.designtime.model.rule.Symbols declarations) {
		for(Iterator it = declarations.entrySet().iterator();it.hasNext();){
			Map.Entry<String, com.tibco.cep.designtime.model.rule.Symbol> entry = (Entry<String, com.tibco.cep.designtime.model.rule.Symbol>) it.next();
			com.tibco.cep.designtime.model.rule.Symbol symbol = entry.getValue();
			Symbol s = RuleFactory.eINSTANCE.createSymbol();
			s.setIdName(symbol.getName());
			s.setType(symbol.getType());
			s.setTypeExtension(symbol.getTypeExtension());
			adapted.getSymbols().getSymbolMap().put(s.getIdName(),s);
		}
	}

	public void setConditionText(String text) {
		if (text == null) {
            text = "";
        }
        adapted.setConditionText(text);	
	}

	public void setActionText(String text) {
		if (text == null) {
            text = "";
        }
        adapted.setActionText(text);		
	}

	public void addDeclaration(String identifier, String entityPath) {
		Symbol s = RuleFactory.eINSTANCE.createSymbol();
		s.setIdName(identifier);
		s.setType(entityPath);
		adapted.getSymbols().getSymbolMap().put(identifier, s);
	}

	public void setPriority(int priority) {
		adapted.setPriority(priority);		
	}
	
	public void setRank(String rank) {
		adapted.setRank(rank);
	}

	
}

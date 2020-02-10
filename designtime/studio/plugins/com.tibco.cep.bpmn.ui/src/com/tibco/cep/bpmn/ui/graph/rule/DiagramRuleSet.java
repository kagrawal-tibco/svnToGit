package com.tibco.cep.bpmn.ui.graph.rule;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DiagramRuleSet {

	protected List<DiagramRule> rules;

	
	public DiagramRuleSet() {
		this.rules = new LinkedList<DiagramRule>();
	}
	
	public void addRule(DiagramRule rule) {
		this.rules.add(rule);
	}

	public boolean deleteRule(DiagramRule rule) {
		if (this.rules.contains(rule)) {
			this.rules.remove(rule);
			return true;
		}
		else {
			return false;
		}
	}

	public void clearRules() {
		this.rules.clear();
	}

	@SuppressWarnings("rawtypes")
	public List getRules() {
		return this.rules;
	}

	public boolean isAllowed(Object ... args) {
		Iterator<?> iter = this.rules.iterator();
		while (iter.hasNext()) {
			if (((DiagramRule) iter.next()).isAllowed(args)) {
				return true;
			}
		}
		return false;
	}

}

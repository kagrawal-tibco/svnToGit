package com.tibco.cep.studio.ui.diagrams.rule;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * 
 * @author smarathe
 *
 */
public class ConceptDiagramRuleSet {
	private List rules;
	private TSENode srcNode;
	private TSENode tgtNode;

	public ConceptDiagramRuleSet() {
		this.rules = new LinkedList();
	}

	public void addRule(ConceptDiagramRule rule) {
		this.rules.add(rule);
	}

	public boolean deleteRule(ConceptDiagramRule rule) {
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

	public List getRules() {
		return this.rules;
	}

	public TSENode getSourceNode() {
		return this.srcNode;
	}

	public TSENode getTargetNode() {
		return this.tgtNode;
	}

	public void resetNodes() {
		this.srcNode = null;
		this.tgtNode = null;
	}

	public boolean isAllowed() {
		this.resetNodes();
		Iterator iter = this.rules.iterator();
		while (iter.hasNext()) {
			if (!((ConceptDiagramRule) iter.next()).isAllowed()) {
				return false;
			}
		}
		return true;
	}

	public boolean isAllowed(TSENode src, TSENode tgt) {
		this.srcNode = src;
		this.tgtNode = tgt;
		Iterator iter = this.rules.iterator();
		while (iter.hasNext()) {
			if (!((ConceptDiagramRule) iter.next()).isAllowed()) {
				return false;
			}
		}
		return true;
	}
}

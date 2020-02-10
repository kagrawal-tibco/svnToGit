package com.tibco.cep.studio.ui.statemachine.diagram.rule;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * 
 * @author ggrigore
 *
 */
public class StateMachineDiagramRuleset {

	private List<StateMachineDiagramRule> rules;
	private TSENode srcNode;
	private TSENode tgtNode;
	
	public StateMachineDiagramRuleset() {
		this.rules = new LinkedList<StateMachineDiagramRule>();
	}
	
	public void addRule(StateMachineDiagramRule rule) {
		this.rules.add(rule);
	}
	
	public boolean deleteRule(StateMachineDiagramRule rule) {
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
	
	public List<StateMachineDiagramRule> getRules() {
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
		Iterator<StateMachineDiagramRule> iter = this.rules.iterator();
		while (iter.hasNext()) {
			if (!((StateMachineDiagramRule) iter.next()).isAllowed()) {
				return false;
			}
		}
		return true;
	}

	public boolean isAllowed(TSENode src, TSENode tgt) {
		this.srcNode = src;
		this.tgtNode = tgt;
		Iterator<StateMachineDiagramRule> iter = this.rules.iterator();
		while (iter.hasNext()) {
			if (!((StateMachineDiagramRule) iter.next()).isAllowed()) {
				return false;
			}
		}
		return true;
	}
}

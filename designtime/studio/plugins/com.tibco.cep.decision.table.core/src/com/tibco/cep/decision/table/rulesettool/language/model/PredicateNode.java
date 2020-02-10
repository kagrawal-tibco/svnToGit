package com.tibco.cep.decision.table.rulesettool.language.model;

import java.io.Serializable;

import com.tibco.be.model.functions.FunctionsCategory;
import com.tibco.be.model.functions.Predicate;

/**
 * Data Object to set in the treeviewer in rule editor.
 */
public class PredicateNode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 117874284195286204L;
	private Object obj;

	public PredicateNode(Object o) {
		this.obj = o;
	}

	public String toString() {
		if (obj instanceof FunctionsCategory) {
			return ((FunctionsCategory) obj).getName().getLocalName();
		} else if (obj instanceof Predicate) {
			return ((Predicate) obj).getName().getLocalName();
		}
		return obj.toString();
	}

	public String getFullName() {
		return obj.toString();
	}

	public String getDescription() {
		try {
			if (obj instanceof Predicate) {
				return ((Predicate) obj).toString();
			}
		} catch (Exception e) {
			return "";
		}
		return "";
	}

	public boolean isValidInAction() {
		if (obj instanceof Predicate) {
			return ((Predicate) obj).isValidInAction();
		}
		return false;
	}

	public boolean isValidInCondition() {
		if (obj instanceof Predicate) {
			return ((Predicate) obj).isValidInCondition();
		}
		return false;

	}

	public boolean isValidInBUI() {
		if (obj instanceof Predicate) {
			return ((Predicate) obj).isValidInBUI();
		}
		return false;

	}

	public boolean isValidInQuery() {
		if (obj instanceof Predicate) {
			return ((Predicate) obj).isValidInQuery();
		}
		return false;
	}

	public Object getObject() {
		return this.obj;
	}
}

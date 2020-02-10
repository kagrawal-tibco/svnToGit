package com.tibco.cep.decision.table.view;

import org.eclipse.emf.ecore.EObject;

public class DecisionTableTreeNode {
	
	public static final Object[] EMPTY_CHILDREN = new Object[0];
	
	protected Object input;
	
	private int featureId; // the feature id of the input object, from the input's parent

	/**
	 * Constructor
	 * 
	 * @param input - the wrapped EMF object
	 * @param featureId - the EMF feature ID of the input, so that the input
	 * can be properly replaced/removed/added in a merge
	 */
	public DecisionTableTreeNode(Object input, int featureId) {
		this.input = input;
		this.featureId = featureId;
	}

	public Object getInput() {
		return input;
	}

	public void setInput(EObject input) {
		this.input = input;
	}
	
	public boolean isEqualTo(Object obj) {
		return this.equals(obj);
	}

	public int getFeatureId() {
		return featureId;
	}

	/**
	 * Handle replace/add/remove.  Default implementation does nothing.
	 * Subclasses should override if specific action needs to be taken during a replace.
	 * For instance, if a condition is replaced, the 'modified' flag might need to
	 * be set on the new condition.
	 * 
	 * @param dest
	 * @param src
	 * @param newObject - the new object that replaces the object in <code>dest</code>
	 */
	public void handleReplace(DecisionTableTreeNode dest, DecisionTableTreeNode src, EObject newObject) {
		// default implementation does nothing
	}

	/**
	 * Returns whether the wrapped <code>input</code> can be set to null
	 * @return <code>true</code> if the <code>input</code> can be set to null, <code>false</code> otherwise
	 */
	public boolean isUnsettable() {
		return false;
	}

}

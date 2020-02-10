package com.tibco.cep.studio.ui.editors.rules.text;

/**
 * Allows clients to be notified when a reconcile has taken
 * place, and gives clients access to the result of the reconcile
 * @author rhollom
 *
 */
public interface IReconcilingListener {

	/**
	 * Notifies client that the current document has been reconciled
	 * @param result
	 */
	public void reconciled(Object result);
	
}

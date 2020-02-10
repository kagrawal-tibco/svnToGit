/**
 * 
 */
package com.tibco.cep.studio.common.legacy.adapters;

import org.eclipse.emf.ecore.EObject;

/**
 * A generic interface to transfer state of an Object to an
 * {@link EObject}.
 * 
 * @author aathalye
 *
 */
public interface ITransformer<O extends Object, E extends EObject> {
	
	/**
	 * @param adaptFrom
	 * @param adaptTo
	 * @return
	 */
	E transform(O adaptFrom, E adaptTo);

}

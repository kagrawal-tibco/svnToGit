/**
 * 
 */
package com.tibco.cep.studio.rms.model.event;

import java.util.EventObject;

/**
 * @author aathalye
 *
 */
public class ModelChangeEvent extends EventObject {
	
	private Features feature;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7403299901567937737L;

	/**
	 * @param source
	 */
	public ModelChangeEvent(Object source, Features feature) {
		super(source);
		this.feature = feature;
	}
	
	public static enum Features {
		STATUS_CHANGE;
	}

	/**
	 * @return the feature
	 */
	public final Features getFeature() {
		return feature;
	}
}

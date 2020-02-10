/**
 * 
 */
package com.tibco.cep.studio.ui.editors.utils.kstreams;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shivkumarchelwa
 *
 */
public class Topology {

	private final List<Transformation> transformations;

	public Topology() {
		this.transformations = new ArrayList<>();
	}

	/**
	 * @return the list
	 */
	public List<Transformation> getTransformations() {
		return transformations;
	}

	public void addTransformation(Transformation t) {
		transformations.add(t);
	}

	public void removeTransformation(Transformation t) {
		transformations.remove(t);
	}

	public void replaceTransformation(int index, Transformation t) {
		transformations.set(index, t);
	}
}

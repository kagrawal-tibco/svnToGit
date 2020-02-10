package com.tibco.cep.studio.dashboard.core.model.XSD.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAnnotation;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDModelGroup;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDParticle;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDTerm;

/**
 * A Model Group that contains a List of elements and can be a sequence or a choice group
 *
 * Each element in the model group can be another nested model group or SynXElement.
 *
 * The Model Group is responsible for enforcing occurrence constraints on the chidren elements
 */
public abstract class SynXSDModelGroup extends SynXSDSchemaElement implements ISynXSDModelGroup {

	private ISynXSDAnnotation annotation;

	/**
	 * A List of ISynXSDParticle's
	 */
	private List<ISynXSDParticle> particles = new ArrayList<ISynXSDParticle>();

	private ISynXSDModelGroup ref;

	public final boolean hasContent() {
		return !particles.isEmpty();
	}

	public List<ISynXSDTerm> getContents() {
		List<ISynXSDTerm> contents = new ArrayList<ISynXSDTerm>();
		for (Iterator<ISynXSDParticle> iter = particles.iterator(); iter.hasNext();) {
			contents.add(((ISynXSDParticle) iter.next()).getTerm());

		}
		return contents;
	}

	public List<ISynXSDParticle> getParticles() {
		return particles;
	}

	public void addParticle(ISynXSDParticle element) {
		if (false == particles.contains(element)) {
			particles.add(element);
		}
	}

	public void removeParticle(ISynXSDParticle element) {
		if (null != particles) {
			particles.remove(element);
		}
	}

	/**
	 * @return Returns the annotation.
	 */
	public ISynXSDAnnotation getAnnotation() {
		return annotation;
	}

	/**
	 * @param annotation
	 *            The annotation to set.
	 */
	public void setAnnotation(ISynXSDAnnotation annotation) {
		this.annotation = annotation;
	}

	/**
	 * @return Returns the ref.
	 */
	public ISynXSDModelGroup getRef() {
		return ref;
	}

	/**
	 * @param ref
	 *            The ref to set.
	 */
	public void setRef(ISynXSDModelGroup ref) {
		this.ref = ref;
	}

	// ================================================================
	// Folowing are convenience methods
	// ================================================================

	/**
	 * Convenience method for retrieving concrete model groups in case of ref usage this THIS model group
	 *
	 * @return
	 */
	public ISynXSDModelGroup getConcreteModelGroup() {
		return getConcreteModelGroup(this);
	}

	/**
	 * Convenience method for retrieving concrete model groups in case of ref usage for the given model group
	 *
	 * @param modelGroup
	 * @return
	 */
	public ISynXSDModelGroup getConcreteModelGroup(ISynXSDModelGroup modelGroup) {
		if (null == modelGroup) {
			throw new IllegalArgumentException("modelGroup can not be null");
		}
		/*
		 * If it has no ref then it is a concrete model group already
		 */
		if (null == modelGroup.getRef()) {
			return modelGroup;
		}
		/*
		 * If the ref points to a model group that is also a ref then traverse through until a concrete model group is found
		 */
		ISynXSDModelGroup ref = modelGroup.getRef();
		while (null != ref.getRef()) {
			ref = ref.getRef();
		}
		return ref;
	}

	protected void cloneThis(SynXSDModelGroup clone) throws Exception {
		super.cloneThis(clone);
		if (this.annotation == null) {
			clone.annotation = null;
		} else {
			clone.annotation = (ISynXSDAnnotation) this.annotation.cloneThis();
		}
	}
}

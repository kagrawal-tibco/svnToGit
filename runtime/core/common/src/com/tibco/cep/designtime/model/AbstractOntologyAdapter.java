/**
 * 
 */
package com.tibco.cep.designtime.model;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.CommonOntologyCache;

/**
 * @author pdhar
 *
 */
public abstract class AbstractOntologyAdapter<T extends EObject> implements Ontology {
	
	protected T index;
	protected String fOntologyName;
	protected Ontology ontology;

	
	/**
	 * @param name
	 */
	public AbstractOntologyAdapter(String name) {
		this.fOntologyName = name;
	}
	
	/**
	 * @param ind
	 */
	public AbstractOntologyAdapter(T ind) {
		this.index = ind;
	}
	/**
	 * 
	 * @return
	 */
	public T getIndex() {
		return index;
	}
	
	/**
	 * @param ind
	 */
	public void setIndex(T ind) {
		this.index = ind;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Ontology#getName()
	 */
	@Override
	public String getName() {
		return this.fOntologyName;
	}

	/**
	 * @param nm
	 */
	public void setName(String nm) {
		this.fOntologyName = nm;
	}
	
	
	public Ontology getCommonOntology() {
		return CommonOntologyCache.INSTANCE.getOntology(getName());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fOntologyName == null) ? 0 : fOntologyName.hashCode());
		result = prime * result + ((index == null) ? 0 : index.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof AbstractOntologyAdapter)) {
			return false;
		}
		AbstractOntologyAdapter other = (AbstractOntologyAdapter) obj;
		if (fOntologyName == null) {
			if (other.fOntologyName != null) {
				return false;
			}
		} else if (!fOntologyName.equals(other.fOntologyName)) {
			return false;
		}
		if (index == null) {
			if (other.index != null) {
				return false;
			}
		} else if (!index.equals(other.index)) {
			return false;
		}
		return true;
	}
	
	
	
	
	
	
	

}

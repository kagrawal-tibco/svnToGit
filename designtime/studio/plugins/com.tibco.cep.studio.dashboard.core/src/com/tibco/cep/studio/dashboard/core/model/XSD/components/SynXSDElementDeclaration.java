package com.tibco.cep.studio.dashboard.core.model.XSD.components;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.exception.SynValidationMessage;
import com.tibco.cep.studio.dashboard.core.model.ISynInternalStatusProvider;
import com.tibco.cep.studio.dashboard.core.model.ISynValidationProvider;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAnnotation;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDElementDeclaration;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDParticle;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDTerm;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDTypeDefinition;
import com.tibco.cep.studio.dashboard.core.model.XSD.modifiers.SynXSDElementBlockModifier;
import com.tibco.cep.studio.dashboard.core.model.XSD.modifiers.SynXSDElementFinalModifier;

/**
 * This is the base element implementation.
 *
 */
public abstract class SynXSDElementDeclaration extends SynXSDSchemaElement implements ISynXSDElementDeclaration, ISynInternalStatusProvider, ISynValidationProvider, Serializable {

	private static final long serialVersionUID = 3690451719664341165L;

	/**
	 * The internal status is default to new. Subclasses should set the internal status appropriately upon instantiation of this class
	 */
	protected transient InternalStatusEnum internalStatus = InternalStatusEnum.StatusNew;

	private boolean isSystemStatus = false;

	private transient SynValidationMessage validationMessage = null;

	private boolean system = false;

	/**
	 * The default value for this element. Only takes effect when the element is declared with no content
	 */
	private Object defaultValue = null;

	private boolean nillable = false;

	/**
	 * An abstract element is only allowed if getSubstitutionGroup() != null
	 */
	private boolean isAbstract = false;

	private ISynXSDElementDeclaration substitutionGroup;

	/**
	 * This type definition is for when the element has no content model (ISynXSDElementContent)
	 */
	private ISynXSDTypeDefinition typeDefinition;

	private List<SynXSDElementBlockModifier> blockModifiers;

	private List<SynXSDElementFinalModifier> finalModifiers;

	private ISynXSDAnnotation annotation;

	private Object fixed;

	/**
	 * The single instance of ISynXSDParticle
	 */
	private ISynXSDParticle particle = null;

	/**
	 * All subclasses can make use of the default logger or use their own logger implementation
	 */
//	protected Logger logger = Logger.getLogger(SynXSDElementDeclaration.class.getName());

	/**
     *
     */
	public SynXSDElementDeclaration() {
		super();
	}

	/**
	 * @param name
	 */
	public SynXSDElementDeclaration(String name) {
		super(name);
	}

	/**
	 * Return the internal status for this element
	 *
	 * @return InternalStatusEnum
	 * @see InternalStatusEnum
	 */
	public InternalStatusEnum getInternalStatus() {
		return internalStatus;
	}

	/**
	 * Set the internal status for this element
	 *
	 * @see InternalStatusEnum
	 */
	public void setInternalStatus(InternalStatusEnum status) throws Exception {
		internalStatus = status;

	}

	/**
	 * Set the internal status for this element
	 *
	 * @see InternalStatusEnum
	 */
	public void setInternalStatus(InternalStatusEnum status, boolean dummy) throws Exception {
		internalStatus = status;

	}

	/**
	 * @return Returns the isSystemStatus.
	 */
	public boolean isSystemStatus() {
		return isSystemStatus;
	}

	/**
	 * @param isSystemStatus
	 *            The isSystemStatus to set.
	 */
	public void setSystemStatus(boolean isSystemStatus) {
		this.isSystemStatus = isSystemStatus;
	}

	/**
	 * Delegating method for determining the validity of the currently set value for this type
	 *
	 * @return true if the value is acceptable for this type
	 */
	public boolean isValid() throws Exception, Exception {
		return true;
	}

	/**
	 * Delegating to the datatype pbject
	 */
	public boolean isValid(Object value) {
		return true;
	}

	/**
	 * Returns the validation message object; callers of this method should check for null
	 *
	 * @return the vlidation message object
	 * @throws Exception
	 */
	public SynValidationMessage getValidationMessage() throws Exception {

		if (false == isValid()) {
			return validationMessage;
		}

		return null;
	}

	/**
	 * The validation messages should normally be set from within the element being validated this method is to provide for the case where the container element is doing the validation and so must set the message in each
	 * child element
	 *
	 * @param validationMessage
	 */
	public void setValidationMessage(SynValidationMessage validationMessage) {
		this.validationMessage = validationMessage;
	}

	//INFO we override cloneThis to prevent spurious execution of com.tibco.cep.studio.dashboard.core.model.XSD.components.SynXSDSchemaElement.cloneThis()
	public abstract Object cloneThis() throws Exception;

	public void cloneThis(SynXSDElementDeclaration sClone) throws Exception {
		sClone.setAbstract(isAbstract());
		sClone.setAnnotation((ISynXSDAnnotation) getAnnotation().cloneThis());
		sClone.setDefault(getDefault());
		sClone.setFixed(sClone);
		sClone.setTypeDefinition(getTypeDefinition());
		sClone.setSubstitutionGroup(getSubstitutionGroup());
		sClone.setTargetNameSpace(getTargetNameSpace());
		sClone.setMaxOccurs(getMaxOccurs());
		sClone.setMinOccurs(getMinOccurs());

		for (Iterator<SynXSDElementBlockModifier> iter = getBlockModifiers().iterator(); iter.hasNext();) {
			sClone.addBlockModifier(iter.next());
		}

		for (Iterator<SynXSDElementFinalModifier> iter = getFinalModifiers().iterator(); iter.hasNext();) {
			sClone.addFinalModifier((SynXSDElementFinalModifier) iter.next());
		}
	}

	/**
	 * Returns whether this property is always maintained by the system such as internal ID
	 *
	 * @return
	 */
	public boolean isSystem() {
		return system;
	}

	public void setSystem(boolean system) {
		this.system = system;
	}

	/**
	 * SimpleTypes are equal if their names and datatypes are equal
	 */
	public boolean equals(Object obj) {
		if (obj instanceof SynXSDElementDeclaration) {
			SynXSDElementDeclaration element = (SynXSDElementDeclaration) obj;
			if (false == element.getName().equals(getName())) {
				return false;
			}
			return true;
		}
		return super.equals(obj);
	}

	public Object getDefault() {
		return defaultValue;
	}

	public void setDefault(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	public ISynXSDElementDeclaration getSubstitutionGroup() {
		return substitutionGroup;
	}

	public void setSubstitutionGroup(ISynXSDElementDeclaration substitutionGroup) {
		this.substitutionGroup = substitutionGroup;
	}

	/**
	 * @return Returns the nillible.
	 */
	public boolean isNillible() {
		return nillable;
	}

	/**
	 * @param nillible
	 *            The nillible to set.
	 */
	public void setNillible(boolean nillible) {
		this.nillable = nillible;
	}

	/**
	 * @return Returns the isAbstract.
	 */
	public boolean isAbstract() {
		return isAbstract;
	}

	/**
	 * @param isAbstract
	 *            The isAbstract to set.
	 */
	public void setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
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
	 * @return Returns the blockModifier.
	 */
	public List<SynXSDElementBlockModifier> getBlockModifiers() {
		return blockModifiers;
	}

	/**
	 * @param blockModifier
	 *            The blockModifier to set.
	 */
	public void addBlockModifier(SynXSDElementBlockModifier blockModifier) {
		if (null == blockModifiers) {
			blockModifiers = new ArrayList<SynXSDElementBlockModifier>();
		}
		blockModifiers.add(blockModifier);
	}

	public void removeBlockModifier(SynXSDElementBlockModifier blockModifier) {
		if (null != blockModifiers) {
			blockModifiers.remove(blockModifier);
		}
	}

	// /**
	// * A private convenience method for cloning block modifiers
	// *
	// * @param blockModifiers
	// * The blockModifiers to set.
	// */
	// private void setBlockModifiers(List<SynXSDElementBlockModifier> blockModifiers) {
	// this.blockModifiers = blockModifiers;
	// }
	//
	// /**
	// * A private convenience method for cloning modifiers
	// *
	// * @param finalModifiers
	// * The finalModifiers to set.
	// */
	// private void setFinalModifiers(List<SynXSDElementFinalModifier> finalModifiers) {
	// this.finalModifiers = finalModifiers;
	// }

	/**
	 * @return Returns the finalModifier.
	 */
	public List<SynXSDElementFinalModifier> getFinalModifiers() {
		return finalModifiers;
	}

	/**
	 * @param finalModifier
	 *            The finalModifier to set.
	 */
	public void addFinalModifier(SynXSDElementFinalModifier finalModifier) {
		if (null == finalModifiers) {
			finalModifiers = new ArrayList<SynXSDElementFinalModifier>();
		}
		finalModifiers.add(finalModifier);
	}

	public void removeFinalModifier(SynXSDElementFinalModifier finalModifier) {
		if (null != finalModifiers) {
			finalModifiers.remove(finalModifier);
		}
	}

	/**
	 * @return Returns the fixed.
	 */
	public Object getFixed() {
		return fixed;
	}

	/**
	 * @param fixed
	 *            The fixed to set.
	 */
	public void setFixed(Object fixed) {
		this.fixed = fixed;
	}

	/**
	 * @return Returns the typeDefinition.
	 */
	public ISynXSDTypeDefinition getTypeDefinition() {
		return typeDefinition;
	}

	/**
	 * @param typeDefinition
	 *            The typeDefinition to set.
	 */
	public void setTypeDefinition(ISynXSDTypeDefinition typeDefinition) {
		this.typeDefinition = typeDefinition;
	}

	public ISynXSDElementDeclaration getConcreteElement() {
		return getConcreteElement(this);
	}

	public ISynXSDElementDeclaration getConcreteElement(ISynXSDElementDeclaration element) {
		if (null == element) {
			throw new IllegalArgumentException("element can not be null");
		}
		/*
		 * Or if it is a substitution
		 */
		if (null != element.getSubstitutionGroup()) {
			/*
			 * If the ref points to an element that is also a ref then traverse through until a concrete element is found
			 */
			ISynXSDElementDeclaration sub = element.getSubstitutionGroup();
			while (null != sub.getSubstitutionGroup()) {
				sub = sub.getSubstitutionGroup();
			}
			return sub;
		}
		return null;
	}

	/**
	 * @return Returns the particle.
	 */
	public ISynXSDParticle getParticle() {
		if (null == particle) {
			particle = new SynXSDParticle();
			particle.setMinOccurs(1);
			particle.setMaxOccurs(-1);
		}
		return particle;
	}

	/**
	 * @param particle The particle to set.
	 */
	public void setParticle(ISynXSDParticle particle) {
		this.particle = particle;
	}

	// =============================================================
	// These are delegates to the particle
	// =============================================================

	public int getMaxOccurs() {
		return particle.getMaxOccurs();
	}

	public int getMinOccurs() {
		return particle.getMinOccurs();
	}

	public ISynXSDTerm getTerm() {
		return particle.getTerm();
	}

	public void setMaxOccurs(int max) {
		particle.setMaxOccurs(max);
	}

	public void setMinOccurs(int min) {
		particle.setMinOccurs(min);
	}

}
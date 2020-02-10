package com.tibco.cep.designtime.model.element.mutable;


import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.mutable.MutableEntity;


/**
 * A PropertyDefinition exists on a Concept and specifies the metadata for a Concept's Instances.
 *
 * @author ishaan
 * @version Mar 17, 2004 2:58:31 PM
 */
public interface MutablePropertyDefinition extends PropertyDefinition, MutableEntity {


    /**
     * Sets the type of the PropertyDefinition.
     *
     * @param type            The new type for this PropertyDefinition.
     * @param conceptTypePath The path of the new concept Type, or null if type is not PROPERTY_TYPE_CONCEPT.
     */
    public void setType(int type, String conceptTypePath) throws ModelException;


    /**
     * Sets the multiplicity of this PropertyDefintion.
     *
     * @param isArray Whether or not this PropertyDefinition should have array semantics.
     */
    public void setIsArray(boolean isArray);


    /**
     * Sets the owning Concept for this PropertyDefiniton.
     *
     * @param owner The new owning Concept.
     * @throws com.tibco.cep.designtime.model.ModelException
     *
     */
    public void setOwner(Concept owner) throws ModelException;


    /**
     * Sets the history policy
     *
     * @param policy the policy to use
     */
    public void setHistoryPolicy(int policy);


    /**
     * Sets the history size for this PropertyDefinition.
     *
     * @param size The new history size.  Any value of size less than 1, will set the size to 1.
     */
    public void setHistorySize(int size);


    /**
     * Sets the default value for instances of this PropertyDefinition.
     *
     * @param value The new default value.
     */
    public void setDefaultValue(String value);


    /**
     * Modifies a MutablePropertyDefinition.  Any supplied parameters that are the same as the current state of the PropertyDefinition
     * are ignored.
     *
     * @param typeFlag        The type for the PropertyDefinition.
     * @param conceptTypePath The Concept type for the PropertyDefinition.  This is ignored if typeFlag is a primitive's flag.
     * @param historyPolicy   The history policy for the PropertyDefinition.
     * @param historySize     The history for the PropertyDefinition.
     * @param isArray         Whether or not the PropertyDefinition is an array.
     * @param defaultValue    The default value for the PropertyDefinition.
     */
    public void modify(int typeFlag, String conceptTypePath, int historyPolicy, int historySize, boolean isArray, String defaultValue) throws ModelException;


    /**
     * Sets the parent of this PropertyDefinition.
     *
     * @param parent The new parent.
     * @throws ModelException Thrown if inheriting from parent would cause a loop in the inheritance hierarchy.
     */
    public void setParent(MutablePropertyDefinition parent) throws ModelException;


    /**
     * Sets whether or not this PropertyDefinition is transitive.
     *
     * @param isTransitive true to enable transitivity, false to disable it.
     */
    public void setTransitive(boolean isTransitive);


    /**
     * Sets whether or not this PropertyDefinition is equivalent to another.
     *
     * @param pd       The PropertyDefinition against which to set equivalency.
     * @param isSameAs true, to enable equivalency, false to disable it.
     */
    public void setSameAs(PropertyDefinition pd, boolean isSameAs);


    /**
     * Sets whether or not this PropertyDefinition is disjoint from the supplied one.
     *
     * @param pd             The PropertyDefinition on which to enable or disable disjointedness.
     * @param isDisjointFrom If true, disjointedness is enabled, if false, it's disabled.
     */
    public void setDisjointFrom(PropertyDefinition pd, boolean isDisjointFrom);


    /**
     * Creates an instance of this PropertyDefinition on the specified instance.
     *
     * @param instance The Instance on which the instantiated property will reside.
     * @return An instance of this PropertyDefinition.
     * @throws com.tibco.cep.designtime.model.ModelException
     *          If instance is null, or is not an Instance of this definition's Concept.
     */
    public MutablePropertyInstance instantiate(MutableInstance instance) throws ModelException;


}
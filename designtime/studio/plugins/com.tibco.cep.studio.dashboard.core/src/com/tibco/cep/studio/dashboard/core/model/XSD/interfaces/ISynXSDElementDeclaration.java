package com.tibco.cep.studio.dashboard.core.model.XSD.interfaces;

import java.util.List;

import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.providers.ISynXSDPropertyProvider;
import com.tibco.cep.studio.dashboard.core.model.XSD.modifiers.SynXSDElementBlockModifier;
import com.tibco.cep.studio.dashboard.core.model.XSD.modifiers.SynXSDElementFinalModifier;

/**
 * An element declaration is an association of a name with a type definition,
 * either simple or complex, an (optional) default value and a (possibly empty)
 * set of identity-constraint definitions
 *
 * This interface form the base of all elements (simple and complex)
 *
 */
public interface ISynXSDElementDeclaration extends ISynXSDTerm, ISynXSDPropertyProvider ,ISynXSDParticle {

    /**
     * NOTE: Declaring the element to be 'abstract' means that substitution is
     * required for instantiation of the element in an 'instance document'
     *
     * @return
     * @throws Exception
     */
    public boolean isAbstract();

    public void setAbstract(boolean value);

    public boolean isNillible();

    public void setNillible(boolean value);

    public ISynXSDTypeDefinition getTypeDefinition();

    public void setTypeDefinition(ISynXSDTypeDefinition typeDefinition);

    public ISynXSDElementDeclaration getSubstitutionGroup();

    public void setSubstitutionGroup(ISynXSDElementDeclaration substitutionGroup);

    public Object getDefault();

    public void setDefault(Object value);

    public Object getFixed();

    public void setFixed(Object value);

    /**
     * Returns a List of SynXSDElementBlockModifier representing the
     * {block}modifier for this element.
     *
     * NOTE: a List with a single SynXSDElementBlockModifier.blockALL is
     * equivalent to a List containing [restriction], [extension], and
     * [substitution]
     *
     * @return
     */
    public List<SynXSDElementBlockModifier> getBlockModifiers();

    public void addBlockModifier(SynXSDElementBlockModifier blockModifier);

    public void removeBlockModifier(SynXSDElementBlockModifier blockModifier);

    /**
     * Returns a List of SynXSDElementFinalModifier representing the
     * {block}modifier for this element.
     *
     * NOTE: a List with a single SynXSDElementFinalModifier.finalALL is
     * equivalent to a List containing [restriction] and [extension]
     *
     * @return
     */
    public List<SynXSDElementFinalModifier> getFinalModifiers();

    public void addFinalModifier(SynXSDElementFinalModifier finalModifier);

    public void removeFinalModifier(SynXSDElementFinalModifier finalModifier);

}

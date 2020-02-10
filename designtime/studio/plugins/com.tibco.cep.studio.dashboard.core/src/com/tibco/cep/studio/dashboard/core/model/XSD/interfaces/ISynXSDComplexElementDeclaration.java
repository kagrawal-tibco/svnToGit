package com.tibco.cep.studio.dashboard.core.model.XSD.interfaces;

/**
 * An element declaration is an association of a name with a type definition,
 * either simple or complex, an (optional) default value and a (possibly empty)
 * set of identity-constraint definitions
 *
 */
public interface ISynXSDComplexElementDeclaration extends ISynXSDElementDeclaration {

    public ISynXSDComplexElementDeclaration getRef();

    public void setRef(ISynXSDComplexElementDeclaration ref);

    /**
     * An element content can be a simple type or complex type
     *
     * @return ISynXSDElementContent An instance of ISynXSDElementContent
     */
    public ISynXSDElementContent getContent();

    /**
     * The particle for an element declaration is only applicable for when the
     * element declaration is embedded within a complex type or group. When it
     * is so the particle itself must be one with an element declaration
     *
     * @return ISynXSDParticle An instance of ISynXSDParticle
     */
    public ISynXSDParticle getParticle();
}

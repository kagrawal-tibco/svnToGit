package com.tibco.cep.mapper.xml.xdata.bind;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmAttribute;
import com.tibco.xml.schema.SmComponent;
import com.tibco.xml.schema.SmComponentProvider;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmNamespace;
import com.tibco.xml.schema.SmNamespaceProvider;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.helpers.NoNamespace;

/**
 * Implements a {@link SmComponentProvider} in terms of an (expected) {@link SmParticleTerm}
 * and a {@link SmNamespaceProvider}.  If the expected term is present then
 * components requested from its namespace will be derived from the schema in the term's
 * {@link SmParticleTerm#getSchema} reference rather than the namespace provider.
 */
class BindingComponentProvider implements SmComponentProvider {
    private SmParticleTerm term;
    private SmNamespaceProvider nsProvider;

    public BindingComponentProvider(SmParticleTerm term, SmNamespaceProvider nsProvider) {
        this.term = term;
        this.nsProvider = nsProvider;
    }

    private SmComponent getComponent(int type, ExpandedName name) {
        SmNamespace ns;
        if (term != null && NoNamespace.isNoNamespaceURI(name.getNamespaceURI())
                && NoNamespace.isNoNamespaceURI(term.getNamespace())) {
            ns = term.getSchema();
        } else if (term != null && term.getNamespace() != null &&
                term.getNamespace().equals(name.getNamespaceURI())) {
            ns = term.getSchema();
        } else {
            ns = nsProvider.getNamespace(name.getNamespaceURI());
        }
        if (ns != null) {
            return ns.getComponent(type, name.getLocalName());
        }
        return null;
    }

    public SmAttribute getAttribute(ExpandedName name) {
        return (SmAttribute) getComponent(SmComponent.ATTRIBUTE_TYPE, name);
    }

    public SmElement getElement(ExpandedName name) {
        return (SmElement) getComponent(SmComponent.ELEMENT_TYPE, name);
    }

    public SmType getType(ExpandedName name) {
        return (SmType) getComponent(SmComponent.TYPE_TYPE, name);
    }
}

package com.tibco.cep.mapper.xml.xdata.bind.virt;

import com.tibco.cep.mapper.xml.xdata.bind.Binding;

/**
 * A mixin interface to be used with {@link com.tibco.cep.mapper.xml.xdata.bind.Binding} to indicate that this is a 'virtual' binding.
 */
public interface VirtualXsltElement
{
    /**
     * Normalize the binding into real XSLT.
     * @param parent The parent to which this should be added (may be null, must check).<br>
     * The implementation IS responsible for adding themselves to the parent (and null checking the parent),
     * the parent is also there for ensuring namespace declarations.
     */
    Binding normalize(Binding parent);
}


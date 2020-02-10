package com.tibco.cep.mapper.xml.xdata.bind;



/**
 * A type safe enumeration of output context errors used in {@link TemplateReport}.<br>
 * An output context error is analogous to a content-model (or similar) validation error when validating a plain old
 * xml instance document.
 */
public final class TemplateOutputContextError
{
    private String m_msg;


    public final static TemplateOutputContextError UNEXPECTED = new TemplateOutputContextError("unexpected");

    /**
     * Same as {@link #UNEXPECTED} except for when there's only a namespace mismatch causing the problem.
     * This will be used only for copy-of or call-template, etc., not for element or attributes. (element or attributes
     * can be 'fixed', so those will show up differently)
     */
    public final static TemplateOutputContextError UNEXPECTED_NS_MISMATCH = new TemplateOutputContextError("unexpected-ns-mismatch");

    public final static TemplateOutputContextError UNEXPECTED_TEXT = new TemplateOutputContextError("unexpected-text");

    /**
     * Indicates that the schema can't be located for the element/attribute.
     */
    public final static TemplateOutputContextError NO_SCHEMA = new TemplateOutputContextError("no-schema");

    /**
     * Indicates that the element/attribute with that local name can't be found the schema (which was found).
     */
    public final static TemplateOutputContextError NO_COMPONENT_IN_SCHEMA = new TemplateOutputContextError("no-component-in-schema");

    /**
     * Indicates that the element/attribute is found in multiple copies of schemas which share the same namespace & aren't disambiguated by an import.
     */
    public final static TemplateOutputContextError AMBIGUOUS_COMPONENT_IN_PROJECT = new TemplateOutputContextError("ambiguous-component-in-project");

    /**
     * Indicates that the component can't be located in the local context.<br>
     * This is similar to {@link #NO_COMPONENT_IN_SCHEMA} but only occurs if the component could have been a locally
     * defined element/attribute (i.e. if the namespace is no-namespace or the same as the containing element namespace).
     */
    public final static TemplateOutputContextError NO_COMPONENT_IN_CONTEXT = new TemplateOutputContextError("no-component-in-context");

    /**
     * Indicates that the output, while in the right 'spot', has exceeded the maximum expected number.<br>
     * For example, if the schema indicates there should be just a single 'Item' element, but 2 appear, the
     * 2nd would be flagged with this type of output context error.
     */
    public final static TemplateOutputContextError CARDINALITY = new TemplateOutputContextError("cardinality");

    private TemplateOutputContextError(String msg)
    {
        m_msg = msg;
    }

    /**
     * Indicates if this is an error where the schema component was located, but was found to be in error.<br>
     * If false, it means that the schema component could not be located.
     */
    public boolean getHadSchemaComponent()
    {
        return this!=NO_COMPONENT_IN_SCHEMA && this!=NO_SCHEMA && this!=AMBIGUOUS_COMPONENT_IN_PROJECT && this!=NO_COMPONENT_IN_CONTEXT;
    }

    public String getType()
    {
        return m_msg;
    }

    public String toString()
    {
        return m_msg;
    }
}


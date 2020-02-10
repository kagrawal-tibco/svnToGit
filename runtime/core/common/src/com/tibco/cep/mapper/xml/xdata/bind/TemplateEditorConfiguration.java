package com.tibco.cep.mapper.xml.xdata.bind;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.xdata.xpath.CoercionSet;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.FunctionResolver;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSequenceType;

/**
 * A simple structure holding the BindingInput, the expected output type & the binding itself.
 * Contains both a virtual and a normalized binding; this class doesn't
 * enforce that they are equivalent, it merely holds one of each.
 */
public final class TemplateEditorConfiguration {
    private ExprContext mInput;
    private CoercionSet mCoercions = new CoercionSet();
    private ImportRegistry m_importRegistry;
    private SmSequenceType mExpectedOutput;
    //private SmNamespaceProvider m_schemaNamespaceProvider;
    private StylesheetResolver mStylesheetResolver;

    /**
     * This binding is whatever contains the template content; often it will be a template, but the editor does not
     * require that.
     */
    private TemplateBinding mTemplateContainerBinding;

    /**
     * @param input
     * @param expectedOutput
     * @param binding The root binding of the template. (?? replace w/ template itself)
     */
    public TemplateEditorConfiguration(ExprContext input, SmSequenceType expectedOutput, TemplateBinding binding) {
        mInput = input;
        mExpectedOutput = expectedOutput;
        mTemplateContainerBinding = binding;
    }

    public TemplateEditorConfiguration() {
    }

    public FunctionResolver getFunctions() {
        if (mInput == null) {
            return null;
        }
        return mInput.getXPathFunctionSet();
    }

    /**
     * Gets the template container binding --- the editor will show the contents of this binding, but not the binding
     * itself.  Often this may be a {@link TemplateBinding}, but it is not required to be so; it just contains the
     * template body.
     * @return
     */
    public TemplateBinding getBinding() {
        return mTemplateContainerBinding;
    }

    public void setBinding(TemplateBinding binding)
    {
        mTemplateContainerBinding = binding;
    }

    public CoercionSet getCoercionSet() {
        return mCoercions;
    }

    public void setCoercionSet(CoercionSet s) {
        if (s==null) {
            throw new NullPointerException("May not be null");
        }
        mCoercions = s;
    }

    public ExprContext getExprContext() {
        return mInput;
    }

    public void setExprContext(ExprContext expr) {
        mInput = expr;
    }

    public SmSequenceType getExpectedOutput() {
        return mExpectedOutput;
    }

    public SmParticleTerm getExpectedOutputTerm() {
        if (mExpectedOutput==null) {
            return null;
        }
        return mExpectedOutput.getParticleTerm();
    }

    public void setExpectedOutput(SmSequenceType type) {
        mExpectedOutput = type;
    }

    /*
    public void setSchemaProvider(SmNamespaceProvider scp) {
        m_schemaNamespaceProvider = scp;
    }

    public SmNamespaceProvider getSchemaProvider() {
        return m_schemaNamespaceProvider;
    }*/

    public void setStylesheetResolver(StylesheetResolver sr) {
        mStylesheetResolver = sr;
    }

    public StylesheetResolver getStylesheetResolver() {
        return mStylesheetResolver;
    }

    public ImportRegistry getImportRegistry()
    {
        return m_importRegistry;
    }

    public void setImportRegistry(ImportRegistry importRegistry)
    {
        m_importRegistry = importRegistry;
    }

    public Object clone() {
        TemplateEditorConfiguration bc = new TemplateEditorConfiguration(mInput,mExpectedOutput,mTemplateContainerBinding);
        bc.setCoercionSet(mCoercions);
        bc.setStylesheetResolver(getStylesheetResolver());
        bc.setImportRegistry(getImportRegistry());
        return bc;
    }
}


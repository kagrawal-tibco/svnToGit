package com.tibco.cep.mapper.xml.xdata.xpath;

import com.tibco.cep.mapper.xml.xdata.DefaultNamespaceMapper;
import com.tibco.cep.mapper.xml.xdata.bind.StylesheetResolver;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmComponentProviderEx;
import com.tibco.xml.schema.SmNamespaceProvider;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * A context object used (ONCE) when doing type computation.  This object should
 * not be reused from call to call.
 */
public final class ExprContext {
    private VariableDefinitionList mVariables;
    private SmSequenceType mInput; // the input (node set) type to 'eval'
    private FunctionResolver mFunctionSet;
    private SmSequenceType mCurrentGroup; // for group-by.
    private SmSequenceType mCurrent = SMDT.PREVIOUS_ERROR; // for XSLT current-function
    private NamespaceContextRegistry m_mNamespaceMapper;
    private StylesheetResolver mStylesheetResolver;
   private SmNamespaceProvider mInputSchemaProvider;
   private SmNamespaceProvider mOutputSchemaProvider;
   private SmComponentProviderEx mInputSmComponentProvider;
   private SmComponentProviderEx mOutputSmComponentProvider;
    private boolean mInsideLocationPath = false; // A marker used to indicate when inside a location path (for code complete)

    static class CCDRef {
        CodeCompleteData ref;
    }
    private CCDRef mRef;

    public ExprContext(VariableDefinitionList variables, FunctionResolver functions)
    {
        this(variables,SMDT.VOID, functions);
    }

    public ExprContext(VariableDefinitionList variables, SmSequenceType inputType, FunctionResolver functions)
    {
        this(variables,inputType,SMDT.VOID, functions);
    }

    public ExprContext(VariableDefinitionList variables, SmSequenceType inputType, SmSequenceType currentGroup, FunctionResolver functions)
    {
        if (functions == null) {
            throw new NullPointerException("Null functions");
        }
        mFunctionSet = functions;
        mVariables = variables;
        mInput = inputType;
        mCurrentGroup = currentGroup;
        if (mInput==null)
        {
            throw new NullPointerException("Null input");
        }
        if (mVariables==null)
        {
            throw new NullPointerException("Null variables");
        }
        if (mCurrentGroup==null)
        {
            throw new NullPointerException("Current group null");
        }
        mRef = new CCDRef();
        m_mNamespaceMapper = new DefaultNamespaceMapper();
    }

    private ExprContext(ExprContext ec) {
        mVariables = ec.mVariables;
        mInput = ec.mInput;
        mRef = ec.mRef;
        //mCoercionSet = ec.mCoercionSet;
        mFunctionSet = ec.mFunctionSet;
        m_mNamespaceMapper = ec.m_mNamespaceMapper;
        mCurrentGroup = ec.mCurrentGroup;
        mCurrent = ec.mCurrent;
        mInsideLocationPath = ec.mInsideLocationPath;
        mInputSchemaProvider = ec.mInputSchemaProvider;
        mOutputSchemaProvider = ec.mOutputSchemaProvider;
       mInputSmComponentProvider = ec.mInputSmComponentProvider;
       mOutputSmComponentProvider = ec.mOutputSmComponentProvider;
        mStylesheetResolver = ec.mStylesheetResolver;
    }

    public FunctionResolver getXPathFunctionSet() {
        return mFunctionSet;
    }

    public VariableDefinitionList getVariables() {
        return mVariables;
    }

    /**
     * Retrieves the current 'input' type, i.e. in evaluating an expr, this is the
     * type of the node set passed in.
     */
    public SmSequenceType getInput() {
        return mInput;
    }

    /**
     * Creates an equivalent context where the input has been replaced with 'input'
     */
    public ExprContext createWithInput(SmSequenceType input) {
        if (input==null) {
            throw new NullPointerException();
        }
        ExprContext ec = new ExprContext(this);
        ec.mInput = input;
        return ec;
    }

    /**
     * Creates an equivalent context where the variable list has been replaced.
     */
    public ExprContext createWithVariableList(VariableDefinitionList list) {
        if (list==null) {
            throw new NullPointerException();
        }
        ExprContext ec = new ExprContext(this);
        ec.mVariables = list;
        return ec;
    }

    /**
     * Creates an equivalent context where the input has been replaced with 'input'
     */
    public ExprContext createWithCurrentGroup(SmSequenceType currentGroup) {
        ExprContext ec = new ExprContext(this);
        ec.mCurrentGroup = currentGroup;
        return ec;
    }


    /**
     * Creates an equivalent context where the input schema provider has been replaced.<br>
     */
/*
    public ExprContext createWithInputSchemaProvider(SmNamespaceProvider namespaceProvider) {
        ExprContext ec = new ExprContext(this);
        ec.mInputSchemaProvider = namespaceProvider;
        if (namespaceProvider==null)
        {
            throw new NullPointerException();
        }
        return ec;
    }
*/
   /**
    * Creates an equivalent context where the input component provider has been replaced.<br>
    */
/*
   public ExprContext createWithInputComponentProvider(SmComponentProviderEx componentProvider) {
       ExprContext ec = new ExprContext(this);
       ec.mInputSmComponentProvider = componentProvider;
       if (componentProvider==null)
       {
           throw new NullPointerException();
       }
       return ec;
   }
*/
   /**
    * Creates an equivalent context where the input schema provider has been replaced.<br>
    */
   public ExprContext createWithInputSchemaAndComponentProvider(SmNamespaceProvider namespaceProvider,
                                                                SmComponentProviderEx componentProvider) {
       ExprContext ec = new ExprContext(this);
       ec.mInputSchemaProvider = namespaceProvider;
       ec.mInputSmComponentProvider = componentProvider;
       if (namespaceProvider==null && componentProvider == null)
       {
           throw new NullPointerException();
       }
       return ec;
   }

   /**
    * Creates an equivalent context where the input and output schema provider has been replaced.<br>
    */
/*
   public ExprContext createWithInputAndOutputSchemaProvider(SmNamespaceProvider namespaceProvider) {
       ExprContext ec = new ExprContext(this);
       ec.mInputSchemaProvider = namespaceProvider;
       ec.mOutputSchemaProvider = namespaceProvider;
       if (namespaceProvider==null)
       {
           throw new NullPointerException();
       }
       return ec;
   }
*/
   /**
    * Creates an equivalent context where the input and output component provider has been replaced.<br>
    */
/*
   public ExprContext createWithInputAndOutputComponentProvider(SmComponentProviderEx componentProvider) {
       ExprContext ec = new ExprContext(this);
       ec.mInputSmComponentProvider = componentProvider;
       ec.mOutputSmComponentProvider = componentProvider;
       if (componentProvider==null)
       {
           throw new NullPointerException();
       }
       return ec;
   }
*/
   /**
     * Creates an equivalent context where the input and output schema provider has been replaced.<br>
     */
    public ExprContext createWithInputAndOutputSchemaAndComponentProvider(
           SmNamespaceProvider namespaceProvider,
           SmComponentProviderEx componentProvider) {
        ExprContext ec = new ExprContext(this);
        ec.mInputSchemaProvider = namespaceProvider;
        ec.mOutputSchemaProvider = namespaceProvider;
        ec.mInputSmComponentProvider = componentProvider;
        ec.mOutputSmComponentProvider = componentProvider;
        if (namespaceProvider==null && componentProvider == null)
        {
            throw new NullPointerException();
        }
        return ec;
    }

    /**
     * Creates an equivalent context where the output schema provider has been replaced.<br>
     */
/*
    public ExprContext createWithOutputSchemaProvider(SmNamespaceProvider namespaceProvider) {
        ExprContext ec = new ExprContext(this);
        ec.mOutputSchemaProvider = namespaceProvider;
        if (namespaceProvider==null)
        {
            throw new NullPointerException();
        }
        return ec;
    }
*/
   /**
    * Creates an equivalent context where the output component provider has been replaced.<br>
    */
/*
   public ExprContext createWithOutputComponentProvider(SmComponentProviderEx componentProvider) {
       ExprContext ec = new ExprContext(this);
       ec.mOutputSmComponentProvider = componentProvider;
       if (componentProvider==null)
       {
           throw new NullPointerException();
       }
       return ec;
   }
*/
   /**
    * Creates an equivalent context where the output schema provider has been replaced.<br>
    */
   public ExprContext createWithOutputSchemaAndComponentProvider(
           SmNamespaceProvider namespaceProvider, SmComponentProviderEx componentProvider) {
       ExprContext ec = new ExprContext(this);
       ec.mOutputSchemaProvider = namespaceProvider;
       ec.mOutputSmComponentProvider = componentProvider;
       if (namespaceProvider==null && componentProvider == null)
       {
           throw new NullPointerException();
       }
       return ec;
   }

    /**
     * Creates an equivalent context where the input has been replaced with 'input'
     */
    public ExprContext createWithStylesheetResolver(StylesheetResolver stylesheetResolver) {
        ExprContext ec = new ExprContext(this);
        ec.mStylesheetResolver = stylesheetResolver;
        return ec;
    }

    /**
     * Creates an equivalent context where the input has been replaced with 'input'
     */
    public ExprContext createWithCurrent(SmSequenceType current) {
        ExprContext ec = new ExprContext(this);
        ec.mCurrent = current;
        return ec;
    }

    public ExprContext createWithNewVariable(VariableDefinition varDef) {
        ExprContext ec = new ExprContext(this);
        ec.mVariables = new VariableDefinitionList(ec.mVariables.getVariables());
        ec.mVariables.add(varDef);
        ec.mVariables.lock();
        return ec;
    }

    public ExprContext createInsideLocationPath(boolean inside) {
        if (mInsideLocationPath==inside) {
            return this;
        }
        ExprContext ec = new ExprContext(this);
        ec.mInsideLocationPath = inside;
        return ec;
    }

    public boolean isInsideLocationPath() {
        return mInsideLocationPath;
    }

    public ExprContext createWithNamespaceMapper(NamespaceContextRegistry mapper) {
        ExprContext ec = new ExprContext(this);
        ec.m_mNamespaceMapper = mapper;
        return ec;
    }

    /**
     *
     * @return
     */
    public SmNamespaceProvider getInputSchemaProvider() {
        return mInputSchemaProvider;
    }

    /**
     *
     * @return
     */
    public SmNamespaceProvider getOutputSchemaProvider() {
        return mOutputSchemaProvider;
    }

   /**
    *
    * @return
    */
   public SmComponentProviderEx getInputSmComponentProvider() {
       return mInputSmComponentProvider;
   }

   /**
    *
    * @return
    */
   public SmComponentProviderEx getOutputSmComponentProvider() {
       return mOutputSmComponentProvider;
   }
    public StylesheetResolver getStylesheetResolver() {
        return mStylesheetResolver;
    }

    public NamespaceContextRegistry getNamespaceMapper() {
        return m_mNamespaceMapper;
    }

    public SmSequenceType getCurrentGroup() {
        return mCurrentGroup;
    }

    public SmSequenceType getCurrent() {
        return mCurrent;
    }
}


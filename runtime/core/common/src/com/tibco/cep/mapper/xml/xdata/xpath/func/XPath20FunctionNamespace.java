package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.cep.mapper.xml.xdata.xpath.FunctionNamespace;

/**
 * Encapsulates the knowledge of the builtin XPath 2.0 functions that need to be supported.
 */
public class XPath20FunctionNamespace
{
    // WCETODO -- for now, treat xpath 2.0 functions as xpath 1.0 functions (no-namespace); having 1.0 versus 2.0 in separate namespaces didn't work (refactor away later)
    private static final DefaultFunctionNamespace IINSTANCE = (DefaultFunctionNamespace)XPath10FunctionNamespace.INSTANCE;
    public static final FunctionNamespace INSTANCE = IINSTANCE;

    static {
        //IINSTANCE = new DefaultFunctionNamespace(TibXPath20Functions.NAMESPACE);
        //IINSTANCE.setSuggestedPrefix(TibXPath20Functions.SUGGESTED_PREFIX);
        //IINSTANCE.setBuiltIn(true);
        //INSTANCE = IINSTANCE;
        addComparison();
        addOther();
        addQName();
        addConditional();
        addString();
        addDateTime();
        addSequence();
        IINSTANCE.lock();
    }
    private static void addComparison() {
        IINSTANCE.add(new CompareXFunction());
    }
    private static void addOther() {
    	// Not sure if the commented out functions are needed.  They exist in designer, but
    	// until they are requested in BE, leave these out.  The underlying implementation exists,
    	// so uncommenting these will make the functions available in the XPath function mapper
//        IINSTANCE.add(new TraceXFunction());
        IINSTANCE.add(new AbsXFunction());
//        IINSTANCE.add(new GenerateIdXFunction());
//        IINSTANCE.add(new RootXFunction());
//        IINSTANCE.add(new KeyXFunction());
    }
    private static void addQName() {
        IINSTANCE.add(new ExpandedQNameXFunction());
        IINSTANCE.add(new LocalNameFromQNameXFunction());
        IINSTANCE.add(new NamespaceURIFromQNameXFunction());
        IINSTANCE.add(new QNameXFunction());
        IINSTANCE.add(new ResolveQNameXFunction());
    }
    private static void addString() {
        IINSTANCE.add(new LowerCaseXFunction());
        IINSTANCE.add(new UpperCaseXFunction());
        IINSTANCE.add(new EndsWithXFunction());
    }

    private static void addConditional()
    {
        // not yet done: add(new IfEmptyXFunction());
    }

    private static void addDateTime()
    {
        // Most moved to tibco namespace.
        IINSTANCE.add(new CurrentDateTimeXFunction());
        IINSTANCE.add(new CurrentDateXFunction());
    }

    private static void addSequence() {
        IINSTANCE.add(new AvgXFunction());
        IINSTANCE.add(new MinXFunction());
        IINSTANCE.add(new MaxXFunction());
        IINSTANCE.add(new ExistsXFunction());
        IINSTANCE.add(new EmptyXFunction());
    }
}

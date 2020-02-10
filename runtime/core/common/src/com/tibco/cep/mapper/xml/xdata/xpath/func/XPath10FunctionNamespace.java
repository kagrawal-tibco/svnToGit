package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.cep.mapper.xml.xdata.xpath.FunctionNamespace;

/**
 * Encapsulates the knowledge of the builtin XPath 1.0 functions that need to be supported.
 */
public class XPath10FunctionNamespace
{
    public static final FunctionNamespace INSTANCE;
    private static final DefaultFunctionNamespace IINSTANCE;

    static {
        IINSTANCE = new DefaultFunctionNamespace(null);
        INSTANCE = IINSTANCE;
        IINSTANCE.setBuiltIn(true);
        addNodeSet();
        addBoolean();
        addString();
        addNumber();
        addXslt10();
        addXslt20();

        ((DefaultFunctionNamespace)XPath20FunctionNamespace.INSTANCE).lock(); // force those to load (refactor into one place later)

        IINSTANCE.lock();
    }

    private static void addNodeSet() {
        IINSTANCE.add(new LastXFunction());
        IINSTANCE.add(new PositionXFunction());
        IINSTANCE.add(new CountXFunction());
        IINSTANCE.add(new IdXFunction());
        IINSTANCE.add(new LocalNameXFunction());
        IINSTANCE.add(new NamespaceURIXFunction());
        IINSTANCE.add(new NameXFunction());
    }

    private static void addBoolean() {
        IINSTANCE.add(new TrueXFunction());
        IINSTANCE.add(new FalseXFunction());
        IINSTANCE.add(new BooleanXFunction());
        IINSTANCE.add(new NotXFunction());
        IINSTANCE.add(new LangXFunction());
    }

    private static void addNumber() {
    	IINSTANCE.add(new AbsXFunction());
        IINSTANCE.add(new NumberXFunction());
        IINSTANCE.add(new SumXFunction());
        IINSTANCE.add(new FloorXFunction());
        IINSTANCE.add(new CeilingXFunction());
        IINSTANCE.add(new RoundXFunction());
    }

    private static void addString() {
        IINSTANCE.add(new StringXFunction());
        IINSTANCE.add(new ConcatXFunction());
        IINSTANCE.add(new StartsWithXFunction());
        IINSTANCE.add(new ContainsXFunction());
        IINSTANCE.add(new SubstringBeforeXFunction());
        IINSTANCE.add(new SubstringAfterXFunction());
        IINSTANCE.add(new SubstringXFunction());
        IINSTANCE.add(new StringLengthXFunction());

        IINSTANCE.add(new NormalizeSpaceXFunction());
        IINSTANCE.add(new TranslateXFunction());
    }

    private static void addXslt10() {
        IINSTANCE.add(new CurrentXFunction());
        IINSTANCE.add(new DocumentXFunction());
    }

    private static void addXslt20() {
        IINSTANCE.add(new CurrentGroupXFunction());
    }
}

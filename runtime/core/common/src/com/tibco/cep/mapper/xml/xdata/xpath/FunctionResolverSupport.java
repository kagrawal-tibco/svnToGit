package com.tibco.cep.mapper.xml.xdata.xpath;

import java.util.Iterator;

import com.tibco.cep.mapper.xml.xdata.xpath.func.TibXPath20Functions;
import com.tibco.cep.mapper.xml.xdata.xpath.func.XFunction;
import com.tibco.xml.data.primitive.QName;

/**
 * A set of helper functions working against the {@link FunctionResolver}.
 * @author Bill Eidson
 */
public class FunctionResolverSupport
{
    /**
     * Finds the namespace that this function name is in, returns null if none or ambiguous.
     * @param unqualifiedFunctionName The function NCName (no prefix) to find.
     * @return The function namespace or null if not found or ambiguous.
     */
    public static FunctionNamespace getNamespaceUnambiguous(FunctionResolver functionResolver, String unqualifiedFunctionName)
    {
        if (unqualifiedFunctionName==null)
        {
            throw new NullPointerException();
        }
        FunctionNamespace ns=null;
        Iterator i = functionResolver.getNamespaces();
        while (i.hasNext())
        {
            FunctionNamespace fn = (FunctionNamespace) i.next();
            if (fn.get(unqualifiedFunctionName)!=null)
            {
                if (ns!=null)
                {
                    return null; // ambiguous.
                }
                ns = fn;
            }
        }
        return ns;
    }

    /**
     * Finds the built-in namespace that this function name is in, returns null if none or ambiguous.
     * @param unqualifiedFunctionName The function NCName (no prefix) to find.
     * @return The function namespace or null if not found or ambiguous.
     */
    public static FunctionNamespace getBuiltInNamespaceUnambiguous(FunctionResolver functionResolver, String unqualifiedFunctionName)
    {
        if (unqualifiedFunctionName==null)
        {
            throw new NullPointerException();
        }
        FunctionNamespace ns=null;
        Iterator i = functionResolver.getNamespaces();
        while (i.hasNext())
        {
            FunctionNamespace fn = (FunctionNamespace) i.next();
            if (fn.isBuiltIn())
            {
                if (fn.get(unqualifiedFunctionName)!=null)
                {
                    if (ns!=null)
                    {
                        return null; // ambiguous.
                    }
                    ns = fn;
                }
            }
        }
        return ns;
    }

    /**
     * For an unqualified name, attempts to find a function matching that name ignoring namespace.
     * If it's ambiguous, returns null. If qualified, finds the matching qualified name (??)
     * @param unqualifiedFunctionName The function NCName (no prefix) to find.
     * @return The function or null if ambiguous or not found.
     */
    public static XFunction getXFunctionUnambiguous(FunctionResolver functionResolver, String unqualifiedFunctionName)
    {
        FunctionNamespace ns = getNamespaceUnambiguous(functionResolver, unqualifiedFunctionName);
        if (ns==null)
        {
            return null;
        }
        return ns.get(unqualifiedFunctionName);
    }

    /**
     * For an unresolved qname, attempts to find a function matching where the suggested prefix of the namespace matches &
     * it has a locally-named fn that matches.
     * @return The function or null if not found.
     */
    public static XFunction getXFunctionWithSuggestedPrefix(FunctionResolver functionResolver, QName name)
    {
        Iterator i = functionResolver.getNamespaces();
        while (i.hasNext())
        {
            FunctionNamespace fn = (FunctionNamespace) i.next();
            if (fn.getSuggestedPrefix()!=null && fn.getSuggestedPrefix().equals(name.getPrefix()))
            {
                XFunction g = fn.get(name.getLocalName());
                if (g!=null)
                {
                    return g;
                }
            }
        }
        return null;
    }

    /**
     * For an unresolved prefix, attempts to find a function set where the suggested prefix matches the given prefix.
     * @return The function namespace or null if not found.
     */
    public static FunctionNamespace getXFunctionNamespaceWithSuggestedPrefix(FunctionResolver functionResolver, String prefix)
    {
        Iterator i = functionResolver.getNamespaces();
        while (i.hasNext())
        {
            FunctionNamespace fn = (FunctionNamespace) i.next();
            if (fn.getSuggestedPrefix()!=null && fn.getSuggestedPrefix().equals(prefix))
            {
                return fn;
            }
        }
        return null;
    }

    /**
     * For an unqualified name, attempts to find a built-in function matching that name ignoring namespace.
     * If it's ambiguous, returns null. If qualified, finds the matching qualified name (??)
     * @param unqualifiedFunctionName The function NCName (no prefix) to find.
     * @return The function or null if ambiguous or not found.
     */
    public static XFunction getBuiltInXFunctionUnambiguous(FunctionResolver functionResolver, String unqualifiedFunctionName)
    {
        FunctionNamespace ns = getBuiltInNamespaceUnambiguous(functionResolver, unqualifiedFunctionName);
        if (ns==null)
        {
            return null;
        }
        return ns.get(unqualifiedFunctionName);
    }

    /**
     * For an unqualified name, attempts to find the default-namespace function matching that name.
     * @param unqualifiedFunctionName The function NCName (no prefix) to find.
     * @return The function or null if ambiguous or not found.
     */
    public static XFunction getDefaultNamespaceXFunction(FunctionResolver functionResolver, String unqualifiedFunctionName)
    {
        FunctionNamespace ns = functionResolver.getNamespace("");
        if (ns!=null)
        {
            // Should never be null...
            XFunction f = ns.get(unqualifiedFunctionName);
            if (f!=null)
            {
                return f;
            }
        }
        // For XPath 1.0, go ahead & check the XPath20 namespace, too.
        ns = functionResolver.getNamespace(TibXPath20Functions.NAMESPACE);
        if (ns!=null)
        {
            // Should never be null...
            XFunction f = ns.get(unqualifiedFunctionName);
            if (f!=null)
            {
                return f;
            }
        }
        return null;
    }
}


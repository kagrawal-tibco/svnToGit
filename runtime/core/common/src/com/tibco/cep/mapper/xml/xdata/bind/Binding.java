package com.tibco.cep.mapper.xml.xdata.bind;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.nsutils.NamespaceManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.util.coll.Iterators;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.serialization.DefaultXmlContentIndenter;
import com.tibco.xml.serialization.DefaultXmlContentSerializer;

/**
 * Base class for XSLT instructions.
 */
public abstract class Binding
{
    public static final String XSLT_URI = "http://www.w3.org/1999/XSL/Transform";

    /*
    // 0 = none, i.e. release 1.0, 1.1x
    // 1 = (somewhere before 2.0)
    // 2 = 2.0 release
    public final static int CURRENT_SOURCE_VERSION = 10; // skipped alot of #s...
    */

    protected static final String[] EMPTY_STRINGS = new String[0];
    private String mFormula;
    private Binding mParent;
    private ArrayList mChildren; // allocated when required.
    private BindingElementInfo mInfo;

    /**
     * Name is (optionally) a qname for dealing with ambiguous terms,
     * for content model terms, the name is computed by some formula (?? MData)
     */
    public Binding(BindingElementInfo info) {
        mInfo = info;
        if (info==null) {
            throw new NullPointerException("Null info");
        }
    }

    public Binding(BindingElementInfo info, String formula) {
        this(info);
        setFormula(formula);
    }

    public final Binding getParent() {
        return mParent;
    }

    public final int getIndexOfChild(Binding binding) {
        int sz = getChildCount();
        for (int i=0;i<sz;i++) {
            if (binding==getChild(i)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns an iterator of child bindings.
     */
    public final Iterator getChildIterator() {
        if (mChildren == null) {
            return Iterators.EMPTY;
        }
        return mChildren.iterator();
    }

    public final boolean hasChildren() {
        return mChildren!=null && mChildren.size() > 0;
    }

    public final void setChildAt(int index, Binding val) {
        Binding old = (Binding) mChildren.get(index);
        if (old!=null) {
            old.mParent = null;
        }
        if (val.mParent!=null) {
            throw new IllegalStateException("Binding: " + val + " already has parent");
        }
        val.mParent = this;
        mChildren.set(index,val);
    }

    public final void addChild(Binding val)
    {
        if (val == null) {
            throw new NullPointerException("Null child");
        }
        if (val.mParent!=null) {
            throw new IllegalStateException("Binding: " + val + " already has parent, class " + val.mParent.getClass().getName() + " name " + val.mParent.getName());
        }
        val.mParent = this;
        if (mChildren==null) {
            mChildren = new ArrayList();
        }
        mChildren.add(val);
    }

    /**
     * Adds a child to the Binding.
     */
    public final void addChild(int pos, Binding val)
    {
        if (val.mParent!=null) {
            throw new IllegalStateException("Binding: " + val + " already has parent");
        }
        val.mParent = this;
        if (mChildren==null) {
            mChildren = new ArrayList();
        }
        if (pos > mChildren.size()) {
            pos = mChildren.size();
        }
        mChildren.add(pos, val);
    }

    /**
     * Is this binding allowed to have children.
     */
    public final boolean canHaveChildren() {
        return true;
    }

    public void removeChild(int index) {
        Binding b = (Binding) mChildren.get(index);
        b.mParent = null;
        mChildren.remove(index);
    }

    /**
     * Returns the script that defines this binding.  null if there is no script.
     */
    public final String getFormula()
    {
        return mFormula;
    }

    /**
     * Set the script that defines this binding.  Set with null if there is no script.
     * Only legal if canSetScript()
     */
    public final void setFormula(String xpath)
    {
        mFormula = xpath;
    }

    /**
     * Gets the number of children.
     * @deprecated Use getChildCount
     */
    public int getChildrenSize() {
        if (mChildren == null) {
            return 0;
        }
        return mChildren.size();
    }

    /**
     * Gets the number of children (more consistent naming than getChildrenSize)
     */
    public int getChildCount() {
        if (mChildren == null) {
            return 0;
        }
        return mChildren.size();
    }

    /**
     * Gets a child binding at a given position
     */
    public Binding getChild(int pos)
    {
        return (Binding) mChildren.get(pos);
    }

    public void removeAllChildren() {
        if (mChildren!=null) {
            for (int i=0;i<mChildren.size();i++) {
                Binding t = (Binding) mChildren.get(i);
                t.mParent = null;
            }
        }
        mChildren = null;
    }

    /**
     * Java Object clone, calls cloneDeep()
     */
    public final Object clone() {
        return cloneDeep();
    }

    /**
     * Self explanatory, no?
     */
    public final Binding cloneDeep() {
        Binding n = cloneShallow();
        int cc = getChildCount();
        for (int i=0;i<cc;i++) {
            n.addChild(getChild(i).cloneDeep());
        }
        return n;
    }

    /**
     * For debugging purposes only.
     */
    public final String toString() {
        StringWriter sw = new StringWriter();
        XmlContentHandler ch = new DefaultXmlContentIndenter(new DefaultXmlContentSerializer(sw,"UTF-8"));
        try
        {
            format(ch);
        }
        catch (SAXException se)
        {
            throw new RuntimeException("Format error: " + se.getMessage());
        }
        return sw.toString();
    }

    public final void format(XmlContentHandler handler) throws SAXException
    {

        // FIXME declare these locally (if not here)
        // Get all the parent
        handler.startDocument();
        HashSet set = new HashSet();
        declareNamespacesRecursive(set);

        NamespaceContextRegistry ni = BindingNamespaceManipulationUtils.createNamespaceImporter(this);
        Iterator i = set.iterator();
        while (i.hasNext())
        {
            String ns = (String) i.next();
            ni.getOrAddPrefixForNamespaceURI(ns);
        }

        NamespaceManipulationUtils.issueAncestorNamespaceDeclarations(asXiNode(), handler);

        formatFragment(handler);
        handler.endDocument();
    }

    private final void declareNamespacesRecursive(HashSet set)
    {
        declareNamespaces(set);
        for (int i=0;i<getChildCount();i++)
        {
            getChild(i).declareNamespacesRecursive(set);
        }
    }

    /**
     * Declares namespaces FOR THIS element only, not recursively.
     * @param set A set to which to add namespaces (strings).
     */
    public void declareNamespaces(Set set)
    {
    }

    /**
     * Renames a prefix FOR THIS element only, not recursively.
     * @param oldToNewPrefixMap The a map of old prefix (String) to new prefix (String)
     */
    public boolean renamePrefixUsages(Map oldToNewPrefixMap)
    {
        return false;
    }

    public abstract XiNode asXiNode();
    public abstract void formatFragment(XmlContentHandler handler) throws SAXException;

    protected final void issueNamespaceDeclarations(XmlContentHandler handler) throws SAXException {
        BindingElementInfo.NamespaceDeclaration[] decls = mInfo.getNamespaceDeclarations();
        int l = decls.length;
        for (int i=0;i<l;i++) {
            BindingElementInfo.NamespaceDeclaration nd = decls[i];
            String ns = nd.getNamespace();
            if (ns!=null && ns.length()==0) {
                // why can't code just accept either, this is a nightmare...
                ns = null;
            }
            handler.prefixMapping(nd.getPrefix(),ns);
        }
    }

    protected final void issueAdditionalAttributes(XmlContentHandler handler) throws SAXException {
        BindingElementInfo.Attribute[] atts = mInfo.getAdditionalAttributes();
        int l = atts.length;
        for (int i=0;i<l;i++) {
            BindingElementInfo.Attribute nd = atts[i];
            handler.attribute(nd.getName(),nd.getValue(),null);
        }
    }

    public final StylesheetBinding getContainingStylesheetBinding() {
        if (this instanceof StylesheetBinding) {
            return (StylesheetBinding) this;
        }
        Binding p = getParent();
        if (p==null) {
            return null;
        }
        return p.getContainingStylesheetBinding();
    }

    public final Binding[] getChildren() {
        if (mChildren==null) {
            return new Binding[0];
        }
        return (Binding[]) mChildren.toArray(new Binding[mChildren.size()]);
    }

    public BindingElementInfo getElementInfo() {
        return mInfo;
    }

    public void setElementInfo(BindingElementInfo info) {
        if (info==null) {
            throw new NullPointerException();
        }
        mInfo = info;
    }

    /**
     * Shallow clones this binding node.
     * @return Binding A binding node of this type.
     */
    public abstract Binding cloneShallow();

    /**
     * Analyzes the content, building a structure describing its use & any errors.
     * @param parent
     * @param context
     * @param expectedOutput
     * @param outputContext
     * @param outputValidationLevel The level of validation of output elements, code is from xsd:wildcard,
     *        such as {@link com.tibco.xml.schema.SmWildcard#STRICT}.
     * @param templateReportFormulaCache
     * @param templateReportArguments
     * @return
     */
    public abstract TemplateReport createTemplateReport(TemplateReport parent, ExprContext context, SmSequenceType expectedOutput, SmSequenceType outputContext, int outputValidationLevel, TemplateReportFormulaCache templateReportFormulaCache, TemplateReportArguments templateReportArguments);

    /**
     * Retrieves the logical expanded name for this binding.<br>
     * For literal element/attribute bindings, the name is the literal element name.
     * For real xsl instructions (or extension instructions), it is that element name.
     * For pseudo-instructions, it is a ficticious, but stable, name.
     * @return The name, <b>never</b> null.
     */
    public abstract ExpandedName getName();
}

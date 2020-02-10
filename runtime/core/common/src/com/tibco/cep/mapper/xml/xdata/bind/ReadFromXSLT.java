/*******************************************************************************
 * Copyright 2001 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.bind;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.tibco.cep.mapper.xml.xdata.xpath.func.TibExtFunctions;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.PrefixToNamespaceResolver;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.schema.SmAttribute;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.helpers.NoNamespace;

/**
 * Reads a full XSLT or string & converts it into Binding.
 */
public final class ReadFromXSLT implements XmlContentHandler
{
    public static final String XSLT_URI = "http://www.w3.org/1999/XSL/Transform"; // don't intern, all literals already interned.

    /**
     * Makes a name in the {@link #XSLT_URI} namespace.
     * @param localName The local name to use.
     */
    public static ExpandedName makeName(String localName)
    {
        return ExpandedName.makeName(XSLT_URI,localName);
    }

    private Binding mRoot = new ElementBinding(BindingElementInfo.EMPTY_INFO, ExpandedName.makeName("fake-root"));
    private Stack mStack = new Stack();
    private ArrayList mNamespaceDeclarations = new ArrayList();
    private StringBuffer mContent; // the current text value.
    private NamespaceContextRegistry m_mIgnoreDeclarations;
    private ExpandedName m_currentElementName; // Used for attribute accumulation.
    private AttributesImpl m_currentAttributes = new AttributesImpl();

    public ReadFromXSLT()
    {
        // set up a fake top element:
        mStack.push(mRoot);
    }

    public ReadFromXSLT(NamespaceContextRegistry ignoreDeclarations)
    {
        // set up a fake top element:
        mStack.push(mRoot);
        m_mIgnoreDeclarations = ignoreDeclarations;
    }

    private final void addNamespaceURI(String prefix, String namespaceURI)
    {
        if (prefix==null) prefix = "";
        if (namespaceURI==null) namespaceURI = "";
        if (mStack.size()==1) {
            // root.
            if (m_mIgnoreDeclarations!=null)
            {
                boolean required = false;
                try {
                    m_mIgnoreDeclarations.getNamespaceURIForPrefix(prefix);
                } catch (PrefixToNamespaceResolver.PrefixNotFoundException pnfe) {
                    required = true;
                }
                if (!required)
                {
                    return;
                }
            }
        }
        for (int i=0;i<mNamespaceDeclarations.size();i++) {
            if (((BindingElementInfo.NamespaceDeclaration)mNamespaceDeclarations.get(i)).getPrefix().equals(prefix)) {
                addNamespaceURI(prefix+"_dup",namespaceURI);
                return;
            }
        }
        mNamespaceDeclarations.add(new BindingElementInfo.NamespaceDeclaration(prefix,namespaceURI));
    }

    public Binding getRoot() {
        if (mRoot.getChildCount()>1)
        {
            ArrayList before = new ArrayList();
            ArrayList after = new ArrayList();
            StylesheetBinding root = null;
            for (int i=0;i<mRoot.getChildCount();i++)
            {
                Binding r = mRoot.getChild(i);
                if (r instanceof StylesheetBinding)
                {
                    root = (StylesheetBinding)r;
                }
                else
                {
                    if (root!=null)
                    {
                        after.add(r);
                    }
                    else
                    {
                        before.add(r);
                    }
                }
            }
            if (root!=null)
            {
                root.setLeadingComments((Binding[])before.toArray(new Binding[0]));
                root.setTrailingComments((Binding[])after.toArray(new Binding[0]));
                return root;
            }
        }
        Binding c = mRoot.getChild(0);
        mRoot.removeAllChildren();
        return c;
    }

    public static StylesheetBinding read(String xslt) {
        return (StylesheetBinding) read(xslt, new ReadFromXSLT());
    }

    public static Binding readFragment(String xslt) {
        return read(xslt, new ReadFromXSLT());
    }

    /**
     * This is needed now, reading & pasting isn't really working for complex stuff if a stylesheet root is passed in,
     * let's clean this up by rearranging what's what.
     */
/*
    public static Binding readTemplateContent(String xslt, NamespaceMapper namespaceMap) {
        Binding b = read(xslt,namespaceMap);
        TemplateBinding tb = null;
        for (int i=0;i<b.getChildCount();i++) {
            Binding cb = b.getChild(i);
            if (cb instanceof TemplateBinding) {
                tb = (TemplateBinding) cb;
                break;
            }
        }
        if (tb!=null && tb.getChildCount()>0) {
            return tb.getChild(0);
        }
        return null;
    }
*/

    /*public static StylesheetBinding read(String xslt, NamespaceMapper namespaceMap, SharedResourceProvider provider) {
        return read(xslt, new ReadFromXSLT(provider, namespaceMap));
    }*/

    private static Binding read(String xslt, ReadFromXSLT r)
    {
        try
        {
            // The default (crimson) parser had an issue with '%' appearing inside attribute values where there were
            // also declared character entity references (!???).  Switched to xerces & everything is good.
            //
            XiParser parser = XiParserFactory.newInstance();
            parser.parse(new InputSource(new StringReader(xslt)),r);
            if (r.mStack.size()>1)
            {
                throw new RuntimeException("Internal error, stack too big");
            }
            Binding root = r.getRoot();
            return root;
        }
        catch (SAXException se)
        {
            throw new RuntimeException(se.getMessage());
        }
        catch (IOException ioe)
        {
            throw new RuntimeException(ioe.getMessage());
        }
    }

    private boolean isXSLT(ExpandedName name) {
        return XSLT_URI.equals(name.getNamespaceURI());
    }

    /*
    private static String getLocalName(String localName, String qName) {
        if (localName!=null) {
            return localName;
        }
        int colonPos = qName.indexOf(":");
        if (colonPos>=0) {
            return qName.substring(colonPos+1);
        }
        return qName;
    }*/

    private void pushBinding(Binding child)
    {
        if (child != null)
        {
            Binding parent = getCurrentBinding();
            parent.addChild(child);

            mStack.push(child);
        }
    }

    private Binding getCurrentBinding() {
        return (Binding) mStack.peek();
    }

    private static String[] NO_ATTRS = {};
    private static String[] TEST_ATTR = {"test"};
    private static String[] NAME_ATTR = {"name"};
    private static String[] NAME_AND_NAMESPACE_ATTRS = {"name","namespace"};
    private static String[] SELECT_ATTR = {"select"};
    private static String[] WITH_PARAM_ATTRS = {"with-param","test"};
    private static String[] TEMPLATE_ATTRS = {"name","match"};
    private static String[] TEMPLATE_TIB_ATTRS = {"input","output"};
    private static String[] FOR_EACH_GROUP_ATTRS = {"select","group-by","group-adjacent","group-starting-with","group-ending-with"};
    private static String[] PARAM_ATTRS = {"name", "select"};
    private static String[] VARIABLE_ATTRS = {"name", "select"};
    private static String[] PARAM_TIB_ATTRS = {"type"};
    private static String[] STYLESHEET_ATTRS = {"version","exclude-result-prefixes"};
    private static String[] SORT_ATTRS = {"select","lang","data-type","order","case-order"};
    private static String[] MALFORMED_ATTR = {ElementBinding.MALFORMED_ATTR.getLocalName()};
    private static String[] COPY_OF_ATTRS = {"select","copy-namespaces"};

    public void startElement(ExpandedName expandedName, SmElement smElement, SmType type) throws SAXException
    {
        flushStartElement();
        flushText();
        m_currentElementName = expandedName;
    }

    private void flushStartElement()
    {
        if (m_currentElementName==null)
        {
            return;
        }
        Binding b = readElement();
        mNamespaceDeclarations.clear();
        pushBinding(b);
        m_currentElementName = null;
        m_currentAttributes.clear();
    }

    private Binding readElement()
    {
        ExpandedName name = m_currentElementName;
        if (isXSLT(name))
        {
            return makeXslBinding(name.getLocalName(),m_currentAttributes);
        }
        if (TibExtFunctions.NAMESPACE.equals(name.getNamespaceURI()))
        {
            if (name.getLocalName().equals("illegal-literal-attribute"))
            {
                String aname = m_currentAttributes.getValue("name");
                if (aname==null) aname = "";
                String val = m_currentAttributes.getValue("value");
                if (val==null) val = "";
                QName qn = new QName(aname);
                ExpandedName ename = null;
                try
                {
                    ename = qn.getExpandedName(BindingNamespaceManipulationUtils.createNamespaceImporter(getCurrentBinding()));
                }
                catch (Exception e)
                {
                }
                if (ename==null)
                {
                    ename = ExpandedName.makeName(qn.getLocalName()); // be nice; just let it go.
                }
                AttributeBinding ab = new AttributeBinding(BindingElementInfo.EMPTY_INFO,ename);
                ab.setFormula(val);
                return ab;
            }
        }
        // it's an element create:
        Binding b = new ElementBinding(getElementInfo(null,NO_ATTRS),name);
        // Find all remaining attributes:
        Attributes atts = m_currentAttributes;
        for (int i=0;i<atts.getLength();i++) {
            ExpandedName en = ExpandedName.makeName(atts.getURI(i),atts.getLocalName(i));
            AttributeBinding ab = new AttributeBinding(BindingElementInfo.EMPTY_INFO,en);
            ab.setFormula(atts.getValue(i));
            b.addChild(ab);
        }
        return b;
    }

    private Binding makeXslBinding(String localName, Attributes atts)
    {
        if (localName.equals("attribute"))
        {
            String aname = atts.getValue("name");
            String ns = atts.getValue("namespace");
            String malformed = atts.getValue(ElementBinding.MALFORMED_ATTR.getNamespaceURI(),ElementBinding.MALFORMED_ATTR.getLocalName());
            if (aname==null)
            {
                // be happy go lucky here.
                aname = "";
            }
            /*String prefixName = getPrefixName(aname);
            aname = getLocalName(null, aname);
            if (aname == null) {
                aname = "<missing>";
            }*/
            // If it is marked as malformed, it re-read it in as a literal element (and the expanded name is not legal)
            if ("true".equals(malformed))
            {
                if (ns==null)
                {
                    ns = NoNamespace.URI;
                }
                ExpandedName en = ExpandedName.makeName(ns,aname);
                return new AttributeBinding(getElementInfo(atts,NAME_AND_NAMESPACE_ATTRS,MALFORMED_ATTR),en);
            }

            //String attributeNamespaceURI = getNamespaceURI(prefixName);
            return new AttributeBinding(getElementInfo(atts,NAME_AND_NAMESPACE_ATTRS),ns,aname);
        }
        if (localName.equals("value-of"))
        {
            String xpath = atts.getValue("select");
            // virtualize character refs:
            return new ValueOfBinding(getElementInfo(atts,SELECT_ATTR),xpath);
        }
        if (localName.equals("call-template"))
        {
            String name = atts.getValue("name");
            return new CallTemplateBinding(getElementInfo(atts,NAME_ATTR),name);
        }
        if (localName.equals("apply-templates"))
        {
            String sel = atts.getValue("select");
            Binding r = new ApplyTemplatesBinding(getElementInfo(atts,SELECT_ATTR));
            r.setFormula(sel);
            return r;
        }
        if (localName.equals("with-param"))
        {
            String varName = atts.getValue("name");
            String selectExpr = atts.getValue("select");
            Binding b = new WithParamBinding(getElementInfo(atts,WITH_PARAM_ATTRS),varName);
            if (selectExpr != null) {
                b.setFormula(selectExpr);
            }
            return b;
        }
        if (localName.equals("for-each"))
        {
            String xpath = atts.getValue("select");
            return new ForEachBinding(getElementInfo(atts,SELECT_ATTR),xpath);
        }
        if (localName.equals("for-each-group"))
        {
            String select = atts.getValue("select");
            String groupBy = atts.getValue("group-by");
            String groupAdjacent = atts.getValue("group-adjacent");
            String groupStartingWith = atts.getValue("group-starting-with");
            String groupEndingWith = atts.getValue("group-ending-with");
            ForEachGroupBinding b = new ForEachGroupBinding(getElementInfo(atts,FOR_EACH_GROUP_ATTRS),select);

            // Since all the groupXXX are mutex, ignore the possibility of more than 1 being set (just randomly pick 1)
            if (groupBy!=null)
            {
                b.setGrouping(groupBy);
                b.setGroupType(ForEachGroupBinding.GROUP_TYPE_GROUP_BY);
            }
            if (groupAdjacent!=null)
            {
                b.setGrouping(groupAdjacent);
                b.setGroupType(ForEachGroupBinding.GROUP_TYPE_GROUP_ADJACENT);
            }
            if (groupStartingWith!=null)
            {
                b.setGrouping(groupStartingWith);
                b.setGroupType(ForEachGroupBinding.GROUP_TYPE_GROUP_STARTING_WITH);
            }
            if (groupEndingWith!=null)
            {
                b.setGrouping(groupEndingWith);
                b.setGroupType(ForEachGroupBinding.GROUP_TYPE_GROUP_ENDING_WITH);
            }
            return b;
        }
        if (localName.equals("copy-of"))
        {
            String xpath = atts.getValue("select");
            String cov = atts.getValue("copy-namespaces");
            CopyOfBinding cob = new CopyOfBinding(getElementInfo(atts,COPY_OF_ATTRS),xpath);
            if (cov!=null)
            {
                cob.setCopyNamespaces(cov.equalsIgnoreCase("yes") ? Boolean.TRUE : Boolean.FALSE);
            }
            return cob;
        }
        if (localName.equals("copy"))
        {
            String xpath = atts.getValue("select");
            return new CopyBinding(getElementInfo(atts,SELECT_ATTR),xpath);
        }
        if (localName.equals("if"))
        {
            String test = atts.getValue("test");
            return new IfBinding(getElementInfo(atts,TEST_ATTR),test);
        }
        if (localName.equals("choose"))
        {
            return new ChooseBinding(getElementInfo(atts,NO_ATTRS));
        }
        if (localName.equals("when"))
        {
            String test = atts.getValue("test");
            return new WhenBinding(getElementInfo(atts,TEST_ATTR),test);
        }
        if (localName.equals("otherwise"))
        {
            return new OtherwiseBinding(getElementInfo(atts,NO_ATTRS));
        }
        if (localName.equals("sort"))
        {
            String sel = atts.getValue("select");
            String lang = atts.getValue("lang");
            String dataType = atts.getValue("data-type");
            String order = atts.getValue("order");
            String caseOrder = atts.getValue("case-order");
            SortBinding sb = new SortBinding(getElementInfo(atts,SORT_ATTRS),sel);
            sb.setLang(lang);
            sb.setDataType(dataType);
            sb.setOrder(order);
            sb.setCaseOrder(caseOrder);
            return sb;
        }
        if (localName.equals("variable"))
        {
            Binding b = new SetVariableBinding(getElementInfo(atts,VARIABLE_ATTRS),
                                               ExpandedName.makeName(NoNamespace.URI,
                                                                     atts.getValue("name")));
            String select = atts.getValue("select");
            b.setFormula(select);
            return b;
        }
        if (localName.equals("template"))
        {
            String name = atts.getValue("name");
            TemplateBinding tb = new TemplateBinding(
                    getElementInfo(atts,TEMPLATE_ATTRS,TEMPLATE_TIB_ATTRS),
                    name,
                    atts.getValue("match"));
            String it = atts.getValue(TibExtFunctions.NAMESPACE,"input");
            tb.setInputType(it);
            String out = atts.getValue(TibExtFunctions.NAMESPACE,"output");
            tb.setOutputType(out);
            return tb;
        }
        if (localName.equals("stylesheet") || localName.equals("transform"))
        {
            boolean isT = localName.equals("transform");
            String erp = atts.getValue("exclude-result-prefixes");
            StylesheetBinding tb = new StylesheetBinding(getElementInfo(atts,STYLESHEET_ATTRS),erp,new Binding[0],new Binding[0]);
            tb.setIsTransformTag(isT);
            return tb;
        }
        if (localName.equals("script"))
        {
            return new ScriptBinding(getElementInfo(atts,NO_ATTRS)).setScriptBindingAttributes(atts, "");
        }
        if (localName.equals("param"))
        {
            String t = atts.getValue(TibExtFunctions.NAMESPACE,"type");
            return new ParamBinding(getElementInfo(atts,PARAM_ATTRS,PARAM_TIB_ATTRS),
                                    ExpandedName.makeName(NoNamespace.URI, atts.getValue("name")), 
                                    atts.getValue("select"),t);
        }
        if (localName.equals("element"))
        {
            String ns = atts.getValue("namespace");
            String n = atts.getValue("name");
            String malformed = atts.getValue(ElementBinding.MALFORMED_ATTR.getNamespaceURI(),ElementBinding.MALFORMED_ATTR.getLocalName());
            if (n==null)
            {
                // be happy go lucky here.
                n = "";
            }
            // If it is marked as malformed, it re-read it in as a literal element (and the expanded name is not legal)
            if ("true".equals(malformed))
            {
                if (ns==null)
                {
                    ns = NoNamespace.URI;
                }
                ExpandedName en = ExpandedName.makeName(ns,n);
                return new ElementBinding(getElementInfo(atts,NAME_AND_NAMESPACE_ATTRS,MALFORMED_ATTR),en);
            }
            return new ElementBinding(getElementInfo(atts,NAME_AND_NAMESPACE_ATTRS),ns,n);
        }
        if (localName.equals("text"))
        {
            TextBinding te = new TextBinding(getElementInfo(atts,NO_ATTRS));
            te.setFullTag(true);
            return te;
        }
        if (localName.equals("comment"))
        {
            XslCommentBinding te = new XslCommentBinding(getElementInfo(atts,NO_ATTRS));
            return te;
        }
        if (localName.equals("processing-instruction"))
        {
            XslPIBinding te = new XslPIBinding(getElementInfo(atts,NAME_ATTR));
            String n = atts.getValue("name");
            if (n==null)
            {
                n = ""; // be nice.
            }
            te.setNameAVT(n);
            return te;
        }
        // What's this?? Who cares?? Just add an unknown:
        return new UnknownBinding(getElementInfo(atts,NO_ATTRS),ExpandedName.makeName(XSLT_URI,localName));
    }

    public BindingElementInfo getElementInfo(Attributes attrs, String[] excludeAttrs) {
        return getElementInfo(attrs,excludeAttrs,null);
    }

    public BindingElementInfo getElementInfo(Attributes attrs, String[] excludeAttrs, String[] excludedTibAttrs) {
        BindingElementInfo.NamespaceDeclaration[] nd;
        if (mNamespaceDeclarations.size()==0) {
            nd = BindingElementInfo.EMPTY_NAMESPACE_DECLARATIONS;
        } else {
            nd = (BindingElementInfo.NamespaceDeclaration[]) mNamespaceDeclarations.toArray(new BindingElementInfo.NamespaceDeclaration[mNamespaceDeclarations.size()]);
        }
        ArrayList extraAtts = null;
        if (attrs!=null) {
            for (int i=0;i<attrs.getLength();i++) {
                String ns = attrs.getURI(i);
                String ln = attrs.getLocalName(i);
                boolean addIt;
                if (ns!=null && ns.length()>0) {
                    if (ns.equals(TibExtFunctions.NAMESPACE))
                    {
                        if (excludedTibAttrs!=null)
                        {
                            boolean found = false;
                            for (int xi=0;xi<excludedTibAttrs.length;xi++)
                            {
                                if (ln.equals(excludedTibAttrs[xi]))
                                {
                                    found = true;
                                }
                            }
                            addIt = !found;
                        }
                        else
                        {
                            addIt = true;
                        }
                    }
                    else
                    {
                        addIt = true;
                    }
                } else {
                    boolean exluded = false;
                    for (int j=0;j<excludeAttrs.length;j++) {
                        if (ln.equals(excludeAttrs[j])) {
                            exluded = true;
                            break;
                        }
                    }
                    addIt = !exluded;
                }
                if (addIt) {
                    if (extraAtts==null) {
                        extraAtts = new ArrayList();
                    }
                    ExpandedName name = ExpandedName.makeName(ns,ln);
                    extraAtts.add(new BindingElementInfo.Attribute(name,attrs.getValue(i)));
                }
            }
        }
        BindingElementInfo.Attribute[] ats;
        if (extraAtts==null) {
            ats = BindingElementInfo.EMPTY_ATTRIBUTES;
        } else {
            ats = (BindingElementInfo.Attribute[]) extraAtts.toArray(new BindingElementInfo.Attribute[extraAtts.size()]);
        }
        if (nd.length==0 && ats.length==0) {
            // don't bother allocating one for this common case:
            return BindingElementInfo.EMPTY_INFO;
        }
        return new BindingElementInfo(nd,ats);
    }

    public void attribute(ExpandedName expandedName, String s, SmAttribute smAttribute) throws SAXException
    {
        String ns = NoNamespace.saxNormalize(expandedName.getNamespaceURI());
        m_currentAttributes.addAttribute(ns,expandedName.getLocalName(),expandedName.getLocalName(),"",s);
    }

    public void attribute(ExpandedName expandedName, XmlTypedValue xmlTypedValue, SmAttribute smAttribute) throws SAXException
    {
        String val = xmlTypedValue.getSerializedForm(null); //WCETODO WTF! There's no reasonable default implementation of SerializationContext -
        // I had implemented it before, but that broke whenever the ****ing interfaces changes.
        // Now this method is called & the other one never is, this is silly.
        // Pass in null since we know it's always untyped values (in this build, anyway).
        attribute(expandedName,val,smAttribute);
    }

    public void text(String s, boolean b) throws SAXException
    {
        flushStartElement();
        if (mContent == null)
        {
            mContent = new StringBuffer();
        }
        mContent.append(s);
    }

    public void text(XmlTypedValue xmlTypedValue, boolean b) throws SAXException
    {
        String val = xmlTypedValue.getSerializedForm(null);
        text(val,b);
    }

    public void endElement(ExpandedName expandedName, SmElement smElement, SmType type) throws SAXException
    {
        flushStartElement();
        flushText();
        Binding b = getCurrentBinding();
        if (mContent != null)
        {
            if (b instanceof ScriptBinding)
            {
                ScriptBinding scriptBinding = (ScriptBinding) b;
                scriptBinding.setFormula(mContent.toString());
            }
            mContent = null;
        }
        mStack.pop();
    }

    private void flushText() {
        if (mContent!=null) {
            Binding cb = getCurrentBinding();
            if (cb instanceof TextBinding) {
                String str = mContent.toString(); // don't trim.
                cb.setFormula(str);
            } else {
                String str = mContent.toString().trim();
                if (str.length()>0) {
                    cb.addChild(new TextBinding(getElementInfo(null,NO_ATTRS),str));
                }
            }
            mContent = null;
        }
    }

    public void ignorableWhitespace(String s, boolean b) throws SAXException
    {
        // ignore.
    }

    public void prefixMapping(String prefix, String uri) throws SAXException
    {
        flushStartElement();
        addNamespaceURI(prefix, uri);
    }

    public void processingInstruction(String s, String s1) throws SAXException
    {
        // ignore for now...
    }

    public void comment(String comment) throws SAXException
    {
        flushStartElement();
        // Check to see if we are a parameter binding, and to see if its the correct kind of comment.
        Binding b = getCurrentBinding();
        if (b instanceof ParamBinding)
        {
            if (comment.startsWith("type=\"") && comment.endsWith("\""))
            {
                ((ParamBinding)b).setType(comment.substring(6, comment.length()-1));
                return;
            }
        }
        else
        {
            if (comment.startsWith("Unfilled:"))
            {
                // temporary hack for back compat.; remove this sometime.
                return;
            }
            Binding cb = new CommentBinding(new String(comment));
            getCurrentBinding().addChild(cb);
        }
    }

    public void setDocumentLocator(Locator locator)
    {
    }

    public void skippedEntity(String s) throws SAXException
    {
    }

    public void startDocument() throws SAXException
    {
    }

    public void endDocument() throws SAXException
    {
    }
}


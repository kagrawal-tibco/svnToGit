/*******************************************************************************
 * Copyright 2001 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.bind.virt;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.ForEachGroupBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportExtendedError;
import com.tibco.cep.mapper.xml.xdata.bind.fix.BindingFixerChange;
import com.tibco.cep.mapper.xml.xdata.xpath.MessageCode;
import com.tibco.cep.mapper.xml.xdata.xpath.func.TibExtFunctions;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.NamespaceToPrefixResolver;

/**
 * A virtualizized XSLT for-each-group operation where the 'group-by' is off in another binding.
 */
public class VirtualForEachGroupBinding extends ForEachGroupBinding implements VirtualXsltElement
{
    public VirtualForEachGroupBinding(BindingElementInfo ns)
    {
        this(ns,null);
    }

    public VirtualForEachGroupBinding(BindingElementInfo ns, String formula)
    {
        super(ns, formula);
    }

    private VirtualGroupingBinding findGroupByBinding()
    {
        if (getChildCount()==0)
        {
            return null;
        }
        Binding b = getChild(0);
        if (b instanceof VirtualGroupingBinding)
        {
            return (VirtualGroupingBinding) b;
        }
        // o.w. not there.
        return null;
    }

    private int hasAnyBadGroupByBinding()
    {
        for (int i=1;i<getChildCount();i++)
        {
            if (getChild(i) instanceof VirtualGroupingBinding)
            {
                return i;
            }
        }
        return -1;
    }

    /**
     * Clones this binding node.
     * @return Binding A binding node of this type.
     */
    public Binding cloneShallow()
    {
        return new VirtualForEachGroupBinding(getElementInfo(),this.getFormula());
    }

    /**
     * Override to allow for our special group-by-binding.
     * @param b
     * @return
     */
    public boolean isChildAllowedBeforeSort(Binding b)
    {
        return b instanceof VirtualGroupingBinding;
    }

    /**
     * An extended error that will turn off implicit if.
     */
    static class AddGroupBy implements TemplateReportExtendedError
    {
        private static final ExpandedName NAME = TibExtFunctions.makeName("add-group-by");

        public static final AddGroupBy INSTANCE = new AddGroupBy();

        private AddGroupBy()
        {
        }

        public boolean canFix(TemplateReport onReport)
        {
            return true;
        }

        public int getCategory()
        {
            return BindingFixerChange.CATEGORY_ERROR;
        }

        public String formatMessage(TemplateReport report)
        {
            return ResourceBundleManager.getMessage(MessageCode.GROUP_BY_REQUIRED_IN_FOR_EACH_GROUP);
        }

        public void fix(TemplateReport report)
        {
            VirtualForEachGroupBinding veb = (VirtualForEachGroupBinding) report.getBinding();
            veb.addChild(0,new VirtualGroupingBinding(BindingElementInfo.EMPTY_INFO,""));
        }

        public void formatFragment(XmlContentHandler handler, NamespaceToPrefixResolver resolver) throws SAXException
        {
            handler.startElement(NAME,null,null);
            handler.endElement(NAME,null,null);
        }

        public void registerNamespaces(NamespaceContextRegistry nsContextRegistry)
        {
            nsContextRegistry.getOrAddPrefixForNamespaceURI(NAME.getNamespaceURI());
        }
    }

    static class MoveGroupBy implements TemplateReportExtendedError
    {
        private static final ExpandedName NAME = TibExtFunctions.makeName("move-group-by");
        private int m_from;

        public MoveGroupBy(int at)
        {
            m_from = at;
        }

        public boolean canFix(TemplateReport onReport)
        {
            return true;
        }

        public int getCategory()
        {
            return BindingFixerChange.CATEGORY_ERROR;
        }

        public String formatMessage(TemplateReport report)
        {
            return ResourceBundleManager.getMessage(MessageCode.GROUP_BY_MUST_BE_FIRST_CHILD);
        }

        public void fix(TemplateReport report)
        {
            VirtualForEachGroupBinding veb = (VirtualForEachGroupBinding) report.getBinding();
            Binding b = veb.getChild(m_from);
            veb.removeChild(m_from);
            veb.addChild(0,b);
        }

        public void formatFragment(XmlContentHandler handler, NamespaceToPrefixResolver resolver) throws SAXException
        {
            handler.startElement(NAME,null,null);
            handler.endElement(NAME,null,null);
        }

        public void registerNamespaces(NamespaceContextRegistry nsContextRegistry)
        {
            nsContextRegistry.getOrAddPrefixForNamespaceURI(NAME.getNamespaceURI());
        }
    }


    static class DeleteGroupBy implements TemplateReportExtendedError
    {
        private static final ExpandedName NAME = TibExtFunctions.makeName("delete-group-by");
        private int m_from;

        public DeleteGroupBy(int at)
        {
            m_from = at;
        }

        public boolean canFix(TemplateReport onReport)
        {
            return true;
        }

        public int getCategory()
        {
            return BindingFixerChange.CATEGORY_ERROR;
        }

        public String formatMessage(TemplateReport report)
        {
            return ResourceBundleManager.getMessage(MessageCode.ONLY_1_GROUP_BY_ALLOWED);
        }

        public void fix(TemplateReport report)
        {
            VirtualForEachGroupBinding veb = (VirtualForEachGroupBinding) report.getBinding();
            veb.removeChild(m_from);
        }

        public void formatFragment(XmlContentHandler handler, NamespaceToPrefixResolver resolver) throws SAXException
        {
            handler.startElement(NAME,null,null);
            handler.endElement(NAME,null,null);
        }

        public void registerNamespaces(NamespaceContextRegistry nsContextRegistry)
        {
            nsContextRegistry.getOrAddPrefixForNamespaceURI(NAME.getNamespaceURI());
        }
    }

    public void checkGrouping(TemplateReport report)
    {
        VirtualGroupingBinding vgb = findGroupByBinding();
        if (vgb==null)
        {
            int bad = hasAnyBadGroupByBinding();
            if (bad<0)
            {
                // just missing, add it:
                report.addExtendedError(AddGroupBy.INSTANCE);
            }
            else
            {
                // we have 1, but in the wrong spot, move it:
                report.addExtendedError(new MoveGroupBy(bad));
            }
            return;
        }
        // We have one where we expected it, any where we don't?
        int bad = hasAnyBadGroupByBinding();
        if (bad>=0)
        {
            // we don't want this.
            report.addExtendedError(new DeleteGroupBy(bad));
        }
        // ok, no problem, let the VGBB actually do the group-by analysis.
    }

    public Binding normalize(Binding parent)
    {
        ForEachGroupBinding feg = new ForEachGroupBinding(getElementInfo(),getFormula());
        VirtualGroupingBinding gbb = findGroupByBinding();
        if (gbb!=null)
        {
            feg.setGrouping(gbb.getFormula());
            feg.setGroupType(gbb.getGroupType());
        }
        for (int i=0;i<getChildCount();i++)
        {
            Binding c = getChild(i);
            if (i>0 || (!(c instanceof VirtualGroupingBinding)))
            {
                BindingVirtualizer.normalize(c,feg);
            }
        }
        if (parent!=null)
        {
            parent.addChild(feg);
        }
        return feg;
    }
}

/*******************************************************************************
 * Copyright 2001 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.bind.virt;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.xdata.bind.AbstractBinding;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.ForEachGroupBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportArguments;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportFormulaCache;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportSupport;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.func.TibExtFunctions;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceToPrefixResolver;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmSequenceTypeSource;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;

/**
 * Represents an XSLT group-by part of a for-each (group-by).
 * (Because it is never serialized on it's own but only by a parent, it is not treated as totally virtual.)
 */
public class VirtualGroupingBinding extends AbstractBinding
{
    private int m_groupType; // enum defined in ForEachGroupBinding

    public VirtualGroupingBinding(BindingElementInfo info)
    {
        this(info,null);
    }

    public VirtualGroupingBinding(BindingElementInfo ns, String formula)
    {
        super(ns, formula);
    }

    public TemplateReport createTemplateReport(TemplateReport parent, ExprContext context, SmSequenceType expectedOutput, SmSequenceType outputContext, int outputValidationLevel, TemplateReportFormulaCache templateReportFormulaCache, TemplateReportArguments templateReportArguments)
    {
        // mostly useless:
        TemplateReport tr = new TemplateReport(this,parent);
        tr.setInitialOutputType(expectedOutput);
        tr.setContext(context);
        tr.setChildContext(context);
        tr.setOutputContext(outputContext);
        tr.setChildOutputContext(outputContext);
        tr.setRemainingOutputType(expectedOutput);

        TemplateReportSupport.initParsedFormula(tr,templateReportFormulaCache,templateReportArguments);

        // update current-group()
        SmSequenceType currentGroupType = SmSequenceTypeFactory.createRepeats(tr.getContext().getInput(),SmCardinality.AT_LEAST_ONE);
        // (Record the path here:)
        currentGroupType = currentGroupType.createWithContext(
                currentGroupType.getContext().createWithSource(
                        new FunctionCallSmSequenceTypeSource("current-group")));
        ExprContext nec = context.createWithCurrentGroup(currentGroupType);
        tr.setFollowingContext(nec);

        // Record it on the 'shared' report:
        tr.getSharedTemplateReport().setCurrentGroup(currentGroupType);

        TemplateReportSupport.traverseNoChildren(tr,false,templateReportFormulaCache,templateReportArguments);

        TemplateReportSupport.initIsRecursivelyErrorFree(tr);

        return tr;
    }

    public void formatFragment(XmlContentHandler handler) throws SAXException
    {
        handler.startElement(ILLEGAL_NAME,null,null);
        //WCETODO add guts.handler.attribute();
        handler.endElement(ILLEGAL_NAME,null,null);
    }

    public ExpandedName getName()
    {
        return ILLEGAL_NAME;
    }

    /**
     * Returns the group type, i.e. {@link ForEachGroupBinding#GROUP_TYPE_GROUP_BY}.
     */
    public int getGroupType()
    {
        return m_groupType;
    }

    public void setGroupType(int gt)
    {
        m_groupType = gt;
    }

    private static final ExpandedName ILLEGAL_NAME = ExpandedName.makeName(TibExtFunctions.NAMESPACE,"grouping");

    /**
     * Clones this binding node.
     * @return Binding A binding node of this type.
     */
    public Binding cloneShallow()
    {
        VirtualGroupingBinding vgb = new VirtualGroupingBinding(getElementInfo(),this.getFormula());
        vgb.setGroupType(getGroupType());
        return vgb;
    }
   /**
    * Formerly named FunctionCallXtypeSource, this inner class was once a public final class
    * w/its own source file.
    */
   class FunctionCallSmSequenceTypeSource implements SmSequenceTypeSource
   {
       private final String mFnName;

       public FunctionCallSmSequenceTypeSource(String fnName)
       {
           if (fnName==null)
           {
               throw new NullPointerException();
           }
           if (fnName.endsWith("()"))
           {
               throw new IllegalArgumentException();
           }
           mFnName = fnName;
       }

       public String getXPath(NamespaceToPrefixResolver resolver) {
           return mFnName + "()";
       }
   }
}

/*******************************************************************************
 * Copyright 2001 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.bind;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinition;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinitionList;
import com.tibco.cep.mapper.xml.xdata.xpath.func.TibExtFunctions;
import com.tibco.cep.mapper.xml.xdata.xpath.type.XQueryTypesParser;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmComponentProviderEx;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * Represents an XSLT param tag.
 */
public class ParamBinding extends ControlBinding
{
    /**
     * Params name attribute.
     */
    private ExpandedName mParamName;
    private String mSelectExpr;
    private String mType;

    public ParamBinding(BindingElementInfo info, ExpandedName pname)
    {
        this(info,pname, null, null);
    }

    public ParamBinding(BindingElementInfo info, ExpandedName pname, String selectExpr)
    {
        this(info, pname, selectExpr, null);
    }

    public ParamBinding(BindingElementInfo info, ExpandedName pname, String selectExpr, String type)
    {
        super(info,"[param]");
        mParamName = pname;
        mSelectExpr = selectExpr;
        mType = type;
    }

    public final void setType(String type)
    {
        mType = type;
    }

    public final String getType()
    {
        return mType;
    }

    public final ExpandedName getParamName()
    {
        return mParamName;
    }

    public final String getSelectExpr()
    {
        return mSelectExpr;
    }

    /**
     * Returns the default parameter input as a string. Note select expr and the children
     * should be exclusive (there can only be a selectExpr or children, not both.
     * ???
     * @return String
     */
    public String getDefault()
    {
        /*String def = mSelectExpr;
        if (mSelectExpr == null)
        {
            StringBuffer xslt = new StringBuffer();
            this.traverseChildrenForXSLT(xslt, 0, new DefaultNamespaceMapper());
            def = xslt.toString();
        }

        if (def.length() == 0)
        {
            def = null;
        }
        return def;*/
        return "default??";
    }

    public TemplateReport createTemplateReport(TemplateReport parent, ExprContext context, SmSequenceType expectedOutput, SmSequenceType outputContext, int outputValidationLevel, TemplateReportFormulaCache templateReportFormulaCache, TemplateReportArguments templateReportArguments) {
        // don't treat these as part of the report...
        TemplateReport tr = new TemplateReport(this,parent);
        boolean isValidSpot = false;
        if (parent!=null && parent.getBinding() instanceof TemplateBinding) {
            // we're good so long as the previous binding is also a param.
            TemplateReport ps = BindingManipulationUtils.getPreviousSibling(tr);
            if (ps==null || ps.getBinding() instanceof ParamBinding || (ps.getBinding() instanceof CommentBinding))
            {
                isValidSpot = true;// we're good.
            }
        }
        if (isValidSpot) {
            tr.setContext(context);
            tr.setChildContext(context);

            ExpandedName varName = mParamName;
            VariableDefinitionList newList = (VariableDefinitionList) context.getVariables().clone();
            TemplateBinding variableContext = BindingManipulationUtils.getContainingTemplateBinding(this);

            SmSequenceType t = computeType(context.getInputSmComponentProvider());
            VariableDefinition vd = newList.getVariable(varName);
            if (vd!=null) {
                if (vd.getDeclarationContext()==variableContext) {
                    // illegal.
                    tr.setStructuralError("Parameter '" + varName + "' already defined in this template");
                } else {
                    // shadow, remove old one:
                    newList.remove(varName);
                }
            }
            newList.add(new VariableDefinition(varName,t,true,variableContext));
            ExprContext followingContext = context.createWithVariableList(newList);
            tr.setFollowingContext(followingContext);

            tr.setOutputContext(outputContext);
            tr.setChildOutputContext(outputContext);
            tr.setInitialOutputType(expectedOutput);
            tr.setRemainingOutputType(expectedOutput);
        } else {
            tr.setContext(context);
            tr.setChildContext(context);
            tr.setFollowingContext(context);
            tr.setOutputContext(outputContext);
            tr.setChildOutputContext(outputContext);
            tr.setInitialOutputType(expectedOutput);
            tr.setRemainingOutputType(expectedOutput);
            tr.setStructuralError("The statement 'param' cannot appear here");
        }
        TemplateReportSupport.traverseNoChildren(tr,true,templateReportFormulaCache,templateReportArguments);
        TemplateReportSupport.initIsRecursivelyErrorFree(tr);
        return tr;
    }

    public SmSequenceType computeType(SmComponentProviderEx sp)
    {
        String t = getType();
        if (t!=null) {
            NamespaceContextRegistry ni = BindingNamespaceManipulationUtils.createNamespaceImporter(this);
            return XQueryTypesParser.parse(ni,sp,SMDT.NONE,t);
        } else {
            return SMDT.REPEATING_ITEM;
        }
    }

    private static final ExpandedName NAME = ReadFromXSLT.makeName("param");

    public ExpandedName getName()
    {
        return NAME;
    }

    public void formatFragment(XmlContentHandler handler) throws SAXException
    {
        super.issueNamespaceDeclarations(handler);
        handler.startElement(NAME,null,null);
        handler.attribute(NAME_ATTR,mParamName,null);
        if (mType!=null)
        {
            handler.attribute(TYPE_ATTR,mType,null);
        }
        super.issueAdditionalAttributes(handler);
        for (int i=0;i<getChildCount();i++) {
            getChild(i).formatFragment(handler);
        }
        handler.endElement(NAME,null,null);
    }

    private static final ExpandedName NAME_ATTR = ExpandedName.makeName("name");
    private static final ExpandedName TYPE_ATTR = TibExtFunctions.makeName("type");

    public Binding cloneShallow()
    {
        return new ParamBinding(getElementInfo(),mParamName, mSelectExpr, mType);
    }

}

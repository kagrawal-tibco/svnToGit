package com.tibco.cep.mapper.xml.xdata.bind;

import java.util.Map;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprUtilities;
import com.tibco.cep.mapper.xml.xdata.xpath.MessageCode;
import com.tibco.cep.mapper.xml.xdata.xpath.Parser;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinition;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinitionList;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * A binding for xsl:variable.
 */
public final class SetVariableBinding extends TemplateContentBinding
{
    private ExpandedName mVarName;

    public SetVariableBinding(BindingElementInfo info, ExpandedName name) {
        this(info,name,null);
    }

    public SetVariableBinding(BindingElementInfo info, ExpandedName name, String formula) {
        super(info);
        mVarName = name;
        setFormula(formula);
    }

    public TemplateReport createTemplateReport(TemplateReport parent, ExprContext context, SmSequenceType expectedOutput, SmSequenceType outputContext, int outputValidationLevel, TemplateReportFormulaCache templateReportFormulaCache, TemplateReportArguments templateReportArguments) {
        TemplateReport ret = new TemplateReport(this,parent);
        ret.setOutputContext(outputContext);
        // Unclear if we should be resetting child output context or what here...
        ret.setChildOutputContext(outputContext);
        ret.setInitialOutputType(expectedOutput);
        ret.setContext(context);
        ret.setChildContext(context);
        SmSequenceType variableType;
        if (getFormula()!=null)
        {
            // 'select' mode
            TemplateReportSupport.initParsedFormula(ret,templateReportFormulaCache,templateReportArguments);
            TemplateReportSupport.traverseNoChildren(ret,true,templateReportFormulaCache,templateReportArguments);
            variableType = ret.getFormulaType();
        }
        else
        {
            // build mode:
            TemplateReportSupport.traverseChildren(ret,SMDT.PREVIOUS_ERROR,outputValidationLevel,templateReportFormulaCache,templateReportArguments);
            variableType = TemplateReportSupport.computeProducedOutput(ret);
        }
        ExpandedName varName = mVarName;
        if (varName==null || varName.getLocalName().length()==0)
        {
            ret.setStructuralError(ResourceBundleManager.getMessage(MessageCode.ILLEGAL_VARIABLE_NAME));
            ret.setFollowingContext(context);
        }
        else
        {
            VariableDefinitionList newList = (VariableDefinitionList) context.getVariables().clone();
            TemplateBinding variableContext = BindingManipulationUtils.getContainingTemplateBinding(this);

            VariableDefinition vd = newList.getVariable(varName);
            if (vd!=null) {
                if (vd.getDeclarationContext()==variableContext)
                {
                    // illegal.
                    String msg = ResourceBundleManager.getMessage(MessageCode.VARIABLE_ALREADY_DEFINED_IN_TEMPLATE,varName);
                    ret.setStructuralError(msg);
                } else {
                    // shadow, remove old one:
                    newList.remove(varName);
                }
            }
            VariableDefinition varDefs = new VariableDefinition(mVarName,variableType,true,variableContext);
            // Record it on the 'shared' report:
            ret.getSharedTemplateReport().addVariable(varDefs);

            newList.add(varDefs);
            ExprContext followingContext = context.createWithVariableList(newList);
            ret.setFollowingContext(followingContext);
        }
        ret.setRemainingOutputType(expectedOutput);
        TemplateReportSupport.initIsRecursivelyErrorFree(ret);
        return ret;
    }

    private static ExpandedName NAME = ExpandedName.makeName(ReadFromXSLT.XSLT_URI,"variable");
    private static ExpandedName SEL_ATTR = ExpandedName.makeName("select");
    private static ExpandedName NAME_ATTR = ExpandedName.makeName("name");

    public ExpandedName getVariableName() {
        return mVarName;
    }

    public void setVariableName(ExpandedName name)
    {
        mVarName = name;
    }

    public boolean renamePrefixUsages(Map oldToNewPrefixMap)
    {
        String formula = getFormula();
        if (formula==null)
        {
            return false;
        }
        Expr parseFormula = Parser.parse(formula);
        Expr newFormula = ExprUtilities.renamePrefixes(parseFormula,oldToNewPrefixMap);
        String newStr = newFormula.toExactString();
        if (newStr.equals(formula))
        {
            return false;
        }
        setFormula(newStr);
        return true;
    }

    public void formatFragment(XmlContentHandler handler) throws SAXException
    {
        super.issueNamespaceDeclarations(handler);
        handler.startElement(NAME,null,null);
        handler.attribute(NAME_ATTR,mVarName,null);
        if (getFormula()!=null)
        {
            handler.attribute(SEL_ATTR,getFormula(),null);
        }
        super.issueAdditionalAttributes(handler);
        for (int i=0;i<getChildCount();i++)
        {
            getChild(i).formatFragment(handler);
        }
        handler.endElement(NAME,null,null);
    }

    public ExpandedName getName() {
        return NAME;
    }

    public Binding cloneShallow() {
        SetVariableBinding b = new SetVariableBinding(getElementInfo(),mVarName);
        b.setFormula(getFormula());
        b.setVariableName(getVariableName());
        return b;
    }
}

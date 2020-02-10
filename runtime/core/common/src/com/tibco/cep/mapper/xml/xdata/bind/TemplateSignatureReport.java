package com.tibco.cep.mapper.xml.xdata.bind;

import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.Parser;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinition;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinitionList;
import com.tibco.cep.mapper.xml.xdata.xpath.type.XQueryTypesParser;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmComponentProviderEx;
import com.tibco.xml.schema.SmGlobalComponentNotFoundException;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * A report indicating the signature of a template.
 */
public final class TemplateSignatureReport
{
    private final VariableDefinitionList mParameters;
    private SmSequenceType mInput;
    private SmSequenceType mOutput;
    private TemplateBinding mTemplate;
    // add some sort of match thing...

    public static TemplateSignatureReport create(TemplateBinding tb, ExprContext ec)
    {
        if (ec.getInputSchemaProvider()==null) {
            throw new NullPointerException("Null schema provider");
        }
        return new TemplateSignatureReport(tb,ec);
    }

    public TemplateSignatureReport(VariableDefinitionList varDefs, SmSequenceType input, SmSequenceType output)
    {
        mParameters = varDefs;
        mInput = input;
        mOutput = output;
    }

    private TemplateSignatureReport(TemplateBinding tb, ExprContext ec) {
        SmSequenceType expectedOuptut = computeExpectedOutput(ec.getOutputSmComponentProvider(),tb);
        VariableDefinitionList vars = computeInputVariables(tb,ec.getInputSmComponentProvider());
        mParameters = vars;
        mOutput = expectedOuptut;
        mInput = computeExpectedInput(ec,tb);
        mTemplate = tb;
    }

    private static VariableDefinitionList computeInputVariables(TemplateBinding tb, SmComponentProviderEx sp) {
        VariableDefinitionList varDefs = new VariableDefinitionList();
        for (int i=0;i<tb.getChildCount();i++) {
            Binding b = tb.getChild(i);
            if (b instanceof CommentBinding) {
                continue;
            }
            if (b instanceof ParamBinding) {
                ParamBinding pb = (ParamBinding) b;
                SmSequenceType t = pb.computeType(sp);
                ExpandedName n = pb.getParamName();
                if (varDefs.getVariable(n)==null) {
                    VariableDefinition vd = new VariableDefinition(n,t);
                    varDefs.add(vd);
                }
            } else {
                break;
            }
        }
        varDefs.lock();
        return varDefs;
    }

    public TemplateBinding getTemplate() {
        return mTemplate;
    }

    public SmSequenceType getInput() {
        return mInput;
    }

    public SmSequenceType getOutput() {
        return mOutput;
    }

    public VariableDefinitionList getParameters() {
        return mParameters;
    }

    public ExprContext createTemplateContextFrom(ExprContext stylesheetContext) {
        // add parameters.
        ExprContext ec = stylesheetContext.createWithInput(getInput());
        VariableDefinitionList vars = (VariableDefinitionList) stylesheetContext.getVariables().clone();
        VariableDefinition[] ar = mParameters.getVariables();
        for (int i=0;i<ar.length;i++) {
            VariableDefinition vd = ar[i];
            if (vars.hasVariable(vd.getName())) {
                vars.remove(vd.getName());
            } else {
                vars.add(vd);
            }
        }
        ExprContext nec = ec.createWithVariableList(vars);
        return nec;
    }

    private static SmSequenceType computeExpectedOutput(SmComponentProviderEx sp, TemplateBinding template) {
        String outputType = template.getOutputType();
        if (outputType!=null) {
            // declared, sweet:
            SmSequenceType t = XQueryTypesParser.parse(template.asXiNode(),sp,SMDT.NONE,outputType);
            return t;
        }

        int cc = template.getChildCount();
        for (int i=0;i<cc;i++) {
            Binding c = template.getChild(i);
            if (c instanceof ElementBinding) {
                return findElement(sp,c.getName());
            }
            if (c instanceof AttributeBinding) {
                return findAttribute(sp,c.getName());
            }
        }
        // can't find it.
        return SMDT.PREVIOUS_ERROR;
    }

    private static SmSequenceType computeExpectedInput(ExprContext ec, TemplateBinding template) {
        String inputType = template.getInputType();
        if (inputType==null) {
            // Check the match first:
            String match = template.getFormula();
            if (match!=null) {
                Expr e = Parser.parse(match);
                ExprContext nec = ec.createWithNamespaceMapper(BindingNamespaceManipulationUtils.createNamespaceImporter(template));
                try
                {
                    SmSequenceType t = e.assertMatch(nec).getInput();
                    return t;
                }
                catch (Exception ex)
                {
                    System.out.println("Can not analyze:" + e);
                    // still give up..
                    return SMDT.REPEATING_ITEM;
                }
            } else {
                String sel = findTopmostXPath(template);
                if (sel==null) {
                    // give up.
                    return SMDT.REPEATING_ITEM;
                } else {
                    Expr e = Parser.parse(sel);
                    ExprContext nec = ec.createWithNamespaceMapper(BindingNamespaceManipulationUtils.createNamespaceImporter(template));
                    try{
                        SmSequenceType t = e.assertMatch(nec).getInput();
                        return t;
                    }
                    catch (Exception ex)
                    {
                        System.out.println("Can not analyze:" + e);
                        // still give up..
                        return SMDT.REPEATING_ITEM;
                    }
                }
            }
        } else {
            // declared, sweet:
            SmSequenceType t = XQueryTypesParser.parse(template.asXiNode(),
                                                       ec.getInputSmComponentProvider(),
                                                       SMDT.NONE,
                                                       inputType);
            return t;
        }
    }

    private static boolean isXPathFormula(Binding b)
    {
        if (b instanceof ValueOfBinding || b instanceof ForEachBinding)
        {
            return true;
        }
        return false;
    }

    private static String findTopmostXPath(Binding b) {
        String f = b.getFormula();
        if (f!=null && isXPathFormula(b)) {
            return f;
        }
        for (int i=0;i<b.getChildCount();i++) {
            Binding c = b.getChild(i);
            String ff = findTopmostXPath(c);
            if (ff!=null) {
                return ff;
            }
        }
        return null;
    }

    private static SmSequenceType findElement(SmComponentProviderEx sp, ExpandedName ename) {
       SmSequenceType sst = null;
       try {
          sst = SmSequenceTypeSupport.getElement(sp, ename);
          if (sst==null) {
              return SMDT.PREVIOUS_ERROR;
          }
       }
       catch (SmGlobalComponentNotFoundException e) {
          return SMDT.PREVIOUS_ERROR;
       }
       return sst;
    }

    private static SmSequenceType findAttribute(SmComponentProviderEx sp,
                                                ExpandedName ename) {
       SmSequenceType sst = SmSequenceTypeSupport.getAttributeInContext(ename, null, sp);
       return sst;
/*
        String ns = ename.getNamespaceURI();
        SmNamespace schema = sp.getNamespace(ns);
        if (schema==null) {
            return SMDT.PREVIOUS_ERROR;
        }
        SmAttribute el = SmSequenceTypeSupport.getAttribute(schema,ename.getLocalName());
        if (el==null) {
            return SMDT.PREVIOUS_ERROR;
        }
        return SmSequenceTypeFactory.create(el);
*/
    }
}


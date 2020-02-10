package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.xpath.CodeCompleteData;
import com.tibco.cep.mapper.xml.xdata.xpath.CodeCompleteResult;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessage;
import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.FunctionNamespace;
import com.tibco.cep.mapper.xml.xdata.xpath.FunctionResolver;
import com.tibco.cep.mapper.xml.xdata.xpath.MessageCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinition;
import com.tibco.cep.mapper.xml.xdata.xpath.func.XFunction;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * A pseudo-Expr subclass used for doing Code Complete text completion.
 */
public final class CodeCompleteNameTestExpr extends NodeTestExpr
{
    private final String mNameMatch;

    public CodeCompleteNameTestExpr(String name, TextRange range) {
        super(range,"");
        mNameMatch = name;
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_CODE_COMPLETE_NAME_TEST;
    }

    static final Comparator ORDERER = new Comparator() {
        public int compare(Object o1, Object o2) {
            CodeCompleteResult ccr1 = (CodeCompleteResult) o1;
            CodeCompleteResult ccr2 = (CodeCompleteResult) o2;
            return ccr1.getDisplayString().compareToIgnoreCase(ccr2.getDisplayString());
        }
    };

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo info) {
        ArrayList ret = new ArrayList();
        addCodeCompletesForNameTest(context,mNameMatch,false,ret);
        CodeCompleteResult[] res = (CodeCompleteResult[]) ret.toArray(new CodeCompleteResult[0]);
        Arrays.sort(res,ORDERER);
        info.setCodeComplete(new CodeCompleteData(context.getInput(),info.getCodeCompleteReturnType(),res,getTextRange()));
        // Add an error --- someone could actually type this (%%)!
        String msg = ResourceBundleManager.getMessage(MessageCode.UNEXPECTED_TOKEN,"%%");
        info.addError(new ErrorMessage(msg,getTextRange()));
        return SMDT.PREVIOUS_ERROR;
    }

    private static void addCodeCompletesForVariable(ExprContext context, String nameMatch, ArrayList addCodeCompletesTo) {
        VariableDefinition[] vars = context.getVariables().getVariables();
        String matchName = nameMatch.substring(1).trim(); // remove $ and whitespace
        String ucmatchName = matchName.toUpperCase(); // because we don't have startsWithIgnoreCase...
        for (int i=0;i<vars.length;i++) {
            VariableDefinition vd = vars[i];
            //WCETODO ignores namespaced variables entirely for now.
            String localName = vd.getName().getLocalName();
            String ucname = localName.toUpperCase(); // Need starts with upper case!!!
            if (ucname.startsWith(ucmatchName)) {
                addCodeCompletesTo.add(new CodeCompleteResult("$" + localName,"$"+ localName)); // ignore namespace again...
            }
        }
    }

    static void addCodeCompletesForNameTest(ExprContext context, String nameMatch, boolean checkFunctions, ArrayList addCodeCompletesTo) {
        if (nameMatch.startsWith("$")) {
            addCodeCompletesForVariable(context,nameMatch,addCodeCompletesTo);
        }
        QName qn = new QName(nameMatch);
        String localMatch = qn.getLocalName();
        ExpandedName[] str = SmSequenceTypeSupport.getPossibleMatchingNamesStartingWith(context.getInput(),localMatch,true);
        for (int i=0;i<str.length;i++) {
            ExpandedName s = str[i];
            addCodeCompletesTo.add(new CodeCompleteResult(s.getLocalName(),s.getLocalName(),s.getNamespaceURI()));
        }
        String localMatchLC = localMatch.toLowerCase();
        if (!context.isInsideLocationPath() && checkFunctions) {
            // add functions, too.
            FunctionResolver fr = context.getXPathFunctionSet();
            Iterator i = fr.getNamespaces();
            while (i.hasNext()) {
                FunctionNamespace fn = (FunctionNamespace) i.next();
                Iterator i2 = fn.getFunctions();
                String ns = fn.getNamespaceURI();
                while (i2.hasNext())
                {
                    XFunction xfn = (XFunction) i2.next();
                    String nn = xfn.getName().getLocalName();
                    if (nn.toLowerCase().startsWith(localMatchLC))
                    {
                        // inefficient way of doing startsWithIgnoreCase (no such function)
                        //XFunction xf = fn.get(nn);
                        //xf.get
                        String fnDesc = nn + "()";
                        addCodeCompletesTo.add(new CodeCompleteResult(fnDesc,fnDesc,ns));
                    }
                }
            }
        }
    }

    public String getExprValue() {
        return mNameMatch;
    }

    public String getTestString() {
        return mNameMatch + "%%";
    }

    public void format(StringBuffer toBuffer, int style) {
        toBuffer.append(mNameMatch);
        toBuffer.append("%%");
    }
}

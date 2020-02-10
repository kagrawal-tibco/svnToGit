package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessage;
import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.FunctionNamespace;
import com.tibco.cep.mapper.xml.xdata.xpath.FunctionResolver;
import com.tibco.cep.mapper.xml.xdata.xpath.FunctionResolverSupport;
import com.tibco.cep.mapper.xml.xdata.xpath.MessageCode;
import com.tibco.cep.mapper.xml.xdata.xpath.RenameFunctionXPathFilter;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.mapper.xml.xdata.xpath.func.XFunction;
import com.tibco.xml.data.primitive.PrefixToNamespaceResolver;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 */
public final class FunctionCallExpr extends Expr
{
    private final Expr[] mArgs;
    private final String mFunctionName;
    private final String mExtraWhitespace;

    /**
     * whitespace str is comma separated, one extra comma for the first lparen
     */
    public FunctionCallExpr(String functionName, Expr[] args, TextRange range, String extraWhitespaceStr, String trailingWhitespace)
    {
        super(range, trailingWhitespace);
        mArgs = (Expr[]) args.clone();
        for (int i=0;i<mArgs.length;i++) {
            if (mArgs[i]==null) {
                throw new NullPointerException("Null type");
            }
        }
        mFunctionName = functionName;
        if (extraWhitespaceStr==null) {
            mExtraWhitespace = "";
        } else {
            mExtraWhitespace = extraWhitespaceStr;
        }
    }

    public boolean hasNonSubTreeTraversal(FunctionResolver functions) {
        if (super.hasNonSubTreeTraversal()) {
            return true;
        }
        // although incorrect, this method isn't really used anyhow anymore:
        //XFunction f = functions.getXFunctionUnambiguous(mFunctionName);
        //if (f==null) {
        // (true is the pessimistic answer)
            return true;
        //}
        //return f.hasNonSubTreeTraversal();
    }


    public Expr[] getChildren() {
        return (Expr[]) mArgs.clone();
    }


    public SmSequenceType evalType(ExprContext context, EvalTypeInfo info)
    {
        FunctionResolver functionSet = context.getXPathFunctionSet();
        QName qn = new QName(mFunctionName);
        XFunction func =null;
        boolean funcErrorAdded = false; // set to true if the error message about function not found is added.
        if (qn.getPrefix().length()>0) {
            try {
                String ns = context.getNamespaceMapper().getNamespaceURIForPrefix(qn.getPrefix());
                FunctionNamespace fn = functionSet.getNamespace(ns);
                if (fn==null) {
                    String msg = ResourceBundleManager.getMessage(MessageCode.NO_FUNCTIONS_IN_NAMESPACE,ns);
                    info.addError(new ErrorMessage(msg,getTextRange()));
                    func = null;
                    funcErrorAdded = true;
                    // continue on so that we traverse the children:
                } else {
                    func = fn.get(qn.getLocalName());
                }
            } catch (PrefixToNamespaceResolver.PrefixNotFoundException pnfe) {
                if (info.getFixCustomFunctionNamespaces())
                {
                    // only happens in migrate loading step (WCETODO refactor this to be nicer)
                    func = FunctionResolverSupport.getXFunctionUnambiguous(functionSet,qn.getLocalName());
                    if (func==null)
                    {
                        func = FunctionResolverSupport.getXFunctionWithSuggestedPrefix(functionSet,qn);
                    }
                    if (func==null)
                    {
                        // Ok, couldn't find it unambiguously that way, just try the suggested prefix:
                        FunctionNamespace ns = FunctionResolverSupport.getXFunctionNamespaceWithSuggestedPrefix(functionSet,qn.getPrefix());
                        if (ns!=null)
                        {
                            String nns = ns.getNamespaceURI();
                            context.getNamespaceMapper().getOrAddPrefixForNamespaceURI(nns,qn.getPrefix());
                        }
                        // continue on so that we traverse the children:
                    }
                    else
                    {
                        String ns = func.getName().getNamespaceURI();
                        context.getNamespaceMapper().getOrAddPrefixForNamespaceURI(ns,qn.getPrefix());
                    }
                }
                else
                {
                    XFunction unambiguousFunc = FunctionResolverSupport.getXFunctionUnambiguous(functionSet, qn.getLocalName());
                    String suggestedPrefix = qn.getPrefix();
                    ErrorMessage errMsg;

                    if (unambiguousFunc==null)
                    {
                        String msg = ResourceBundleManager.getMessage(MessageCode.CANNOT_RESOLVE_PREFIX,suggestedPrefix);
                        errMsg = new ErrorMessage(msg,getTextRange());
                    }
                    else
                    {
                        String ns = unambiguousFunc.getName().getNamespaceURI();
                        errMsg = new ErrorMessage(
                                ErrorMessage.TYPE_ERROR,
                                ResourceBundleManager.getMessage(MessageCode.CANNOT_RESOLVE_PREFIX,suggestedPrefix),
                                ErrorMessage.CODE_MISSING_PREFIX,
                                ns,
                                suggestedPrefix,
                                getTextRange());
                    }
                    info.addError(errMsg);

                    funcErrorAdded = true;
                }
                // continue on so that we traverse the children:
            }
        } else {
            // no prefix case:
            if (info.getFixCustomFunctionNamespaces())
            {
                func = FunctionResolverSupport.getXFunctionUnambiguous(functionSet,mFunctionName);
            }
            else
            {
                // only get unambiguous for the names:
                func = FunctionResolverSupport.getDefaultNamespaceXFunction(functionSet,mFunctionName);
                if (func == null)
                {
                    XFunction unambiguousFunc = FunctionResolverSupport.getXFunctionUnambiguous(functionSet,mFunctionName);
                    if (unambiguousFunc != null)
                    {
                        String ns = unambiguousFunc.getName().getNamespaceURI();
                        FunctionNamespace funcNamespace = functionSet.getNamespace(ns);

                        ErrorMessage errMsg = new ErrorMessage(
                                ErrorMessage.TYPE_ERROR,
                                ResourceBundleManager.getMessage(MessageCode.CANNOT_RESOLVE_FUNCTION_WITHOUT_PREFIX),
                                ErrorMessage.CODE_MISSING_PREFIX,
                                ns,
                                funcNamespace.getSuggestedPrefix(),
                                getTextRange());
                        errMsg.setFilter(new RenameFunctionXPathFilter(getTextRange(),unambiguousFunc.getName(),funcNamespace.getSuggestedPrefix()));
                        info.addError(errMsg);

                        funcErrorAdded = true;
                    }
                }
            }
        }
        if (func==null && !funcErrorAdded)
        {
            TextRange fstart = getTextRange(); // this argsRange covers the entire function call,
            // we just want the part for the function name:
            int startPos = fstart.getStartPosition();
            TextRange range = new TextRange(startPos,startPos+mFunctionName.length());
            info.addError(new ErrorMessage(ResourceBundleManager.getMessage(MessageCode.NO_SUCH_FUNCTION_IN_NS),range));
        }
        // Analyze the arguments:
        SmSequenceType[] at = new SmSequenceType[mArgs.length];
        for (int i=0;i<mArgs.length;i++) {
            SmSequenceType argType;
            if (func!=null) { // if available, do per argument type-checks:
                if (i>=func.getNumArgs()) {
                    // repeating:
                    if (func.getNumArgs()>0 && func.getLastArgRepeats())
                    {
                        argType = func.getArgType(func.getNumArgs()-1, mArgs.length);
                    } else {
                        argType = SMDT.PREVIOUS_ERROR;
                    }
                } else {
                    argType = func.getArgType(i, mArgs.length);
                }
                SmSequenceType prevType = info.getCodeCompleteReturnType();
                info.setCodeCompleteReturnType(argType);
                at[i] = mArgs[i].evalType(context,info);
                info.setCodeCompleteReturnType(prevType);
                SmSequenceType t = at[i];
                if (SmSequenceTypeSupport.isPrimitiveBoolean(t))
                {
                    // Kind of hacky, but if we don't do this, we may get a warning about 'always true()' for a boolean (if there's a true() value specified);
                    // in this case, that warning doesn't make sense.
                    t = SMDT.BOOLEAN;
                }
                info.addErrors(TypeChecker.checkConversion(t,argType,mArgs[i].getTextRange(),info.getXPathCheckArguments()));
            }
            else
            {
                at[i] = mArgs[i].evalType(context,info);
            }
        }
        if (func==null) {
            // without a function definition, can't analyze anymore:
            return info.recordReturnType(this,SMDT.PREVIOUS_ERROR);
        }
        // Do 'too many/too few' arguments type checks:
        int na = func.getNumArgs();
        int minArgs = func.getMinimumNumArgs();
        if (mArgs.length<minArgs)
        {
            String msg;
            if (minArgs==na)
            {
                if (na==1)
                {
                    msg = ResourceBundleManager.getMessage(MessageCode.EXPECTED_1_ARGUMENT);
                }
                else
                {
                    msg = ResourceBundleManager.getMessage(MessageCode.EXPECTED_N_ARGUMENTS,""+na);
                }
            }
            else
            {
                if (na-1==minArgs)
                {
                    msg = ResourceBundleManager.getMessage(MessageCode.EXPECTED_N_MINUS_OR_N_ARGUMENTS,""+(na-1),""+na);
                }
                else
                {
                    if (minArgs==1)
                    {
                        msg = ResourceBundleManager.getMessage(MessageCode.EXPECTED_AT_LEAST_N_ARGUMENTS,""+(na));
                    }
                    else
                    {
                        msg = ResourceBundleManager.getMessage(MessageCode.EXPECTED_AT_LEAST_N_ARGUMENTS,""+(na));
                    }
                }
            }
            info.addError(new ErrorMessage(msg,getTextRange()));
            return info.recordReturnType(this,SMDT.PREVIOUS_ERROR);
        }
        else
        {
            if (mArgs.length>na && !func.getLastArgRepeats())
            {
                String msg;
                if (na==1)
                {
                    msg = ResourceBundleManager.getMessage(MessageCode.EXPECTED_1_ARGUMENT);
                }
                else
                {
                    msg = ResourceBundleManager.getMessage(MessageCode.EXPECTED_N_ARGUMENTS,""+na);
                }
                info.addError(new ErrorMessage(msg,getTextRange()));
                return info.recordReturnType(this,SMDT.PREVIOUS_ERROR);
            }
            // otherwise we're ok.
        }
        SmSequenceType ret = func.typeCheck(at,context,info, this, mArgs);
        if (ret==null)
        {
            // Internal error, not localized:
            throw new NullPointerException("Internal Error: function: " + func.getName() + " returned null on a type check");
        }
        return info.recordReturnType(this,ret);
    }

    public String getExprValue() {
        return mFunctionName;
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_FUNCTION_CALL;
    }

    public void format(StringBuffer toBuffer, int style) {
        toBuffer.append(mFunctionName);
        boolean isExact = style==STYLE_TO_EXACT_STRING;
        int wspos = 0; // used for when isExact
        int n = mExtraWhitespace.indexOf(',',wspos);
        if (isExact) {
            if (n>wspos) {
                toBuffer.append(mExtraWhitespace.substring(wspos,n));
            }
        }
        wspos = n+1;
        toBuffer.append('(');
        for (int i=0;i<mArgs.length;i++) {
            if (i>0) {
                toBuffer.append(',');
            }
            if (isExact) {
                int nn = mExtraWhitespace.indexOf(',',wspos);
                if (nn>wspos) {
                    toBuffer.append(mExtraWhitespace.substring(wspos,nn));
                }
                wspos = nn+1;
            } else {
                if (i>0) {
                    toBuffer.append(' ');
                }
            }

            mArgs[i].format(toBuffer,style);
        }
        toBuffer.append(')');
        if (isExact)
        {
            toBuffer.append(getWhitespace());
        }
    }

    public String getRepresentationClosure() {
        return mExtraWhitespace;
    }
}

package com.tibco.be.util;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.xquery.Expr;
import com.tibco.xml.xquery.ExprLinkContext;
import com.tibco.xml.xquery.model.QmEvalTypeInfo;
import com.tibco.xml.xquery.model.QmExpr;
import com.tibco.xml.xquery.model.QmExprContext;
import com.tibco.xml.xquery.model.QmFunction;
import com.tibco.xml.xquery.model.QmFunctionResolver;
import com.tibco.xml.xquery.model.function.XQueryFunctions;

public class BECustomFunctionQMResolver implements QmFunctionResolver, ExprLinkContext {

	class BECustomQMFunction implements QmFunction {

		private Expr functionCallExpr;
		private int airity;

		public BECustomQMFunction(Expr functionCallExpr, int paramInt) {
			this.functionCallExpr = functionCallExpr;
			this.airity = paramInt;
		}
		
		@Override
		public int getMinimumNumArgs() {
			return getMaximumNumArgs();
		}

		@Override
		public int getMaximumNumArgs() {
			return this.airity;
		}

		@Override
		public SmSequenceType getArgType(int paramInt1, int paramInt2) {
			return null;
		}

		@Override
		public boolean getLastArgRepeats() {
			return false;
		}

		@Override
		public boolean hasNonSubTreeTraversal() {
			return false;
		}

		@Override
		public boolean isIndependentOfFocus(int paramInt) {
			return false;
		}

		@Override
		public SmSequenceType typeCheck(
				SmSequenceType[] paramArrayOfSmSequenceType,
				QmExprContext paramQmExprContext,
				QmEvalTypeInfo paramQmEvalTypeInfo, QmExpr paramQmExpr,
				QmExpr[] paramArrayOfQmExpr) {
			return null;
		}
		
	}
	
    private QmFunctionResolver fBuiltInFunctions = XQueryFunctions.getInstance();
	private BECustomFunctionResolver fResolver;

	
	public BECustomFunctionQMResolver(
			BECustomFunctionResolver resolver) {
		this.fResolver = resolver;
	}


	@Override
	public QmFunction getFunction(ExpandedName paramExpandedName, int paramInt) {
		boolean functionAvailable = fResolver.getAsFunctionGroup().isFunctionAvailable(paramExpandedName, paramInt);
		if (functionAvailable) {
			Expr functionCallExpr = fResolver.getAsFunctionGroup().getFunctionCallExpr(paramExpandedName, new Expr[paramInt]);
			return new BECustomQMFunction(functionCallExpr, paramInt);
		}
		return fBuiltInFunctions.getFunction(paramExpandedName, paramInt);
	}


	@Override
	public Expr getFunctionCallExpr(ExpandedName name, Expr[] args) {
		return fResolver.getAsFunctionGroup().getFunctionCallExpr(name, args);
	}

}

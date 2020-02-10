package com.tibco.jxpath.compiler;

import java.util.List;

import javax.xml.namespace.QName;

import com.tibco.jxpath.AxisName;
import com.tibco.jxpath.Expression;
import com.tibco.jxpath.objects.XNumber;
import com.tibco.jxpath.objects.XString;
import com.tibco.jxpath.operations.And;
import com.tibco.jxpath.operations.BinaryOperation;
import com.tibco.jxpath.operations.Div;
import com.tibco.jxpath.operations.Equals;
import com.tibco.jxpath.operations.FilterExpression;
import com.tibco.jxpath.operations.FunctionCall;
import com.tibco.jxpath.operations.Gt;
import com.tibco.jxpath.operations.Gte;
import com.tibco.jxpath.operations.Lt;
import com.tibco.jxpath.operations.Lte;
import com.tibco.jxpath.operations.Minus;
import com.tibco.jxpath.operations.Mod;
import com.tibco.jxpath.operations.Mult;
import com.tibco.jxpath.operations.NamedAxisStep;
import com.tibco.jxpath.operations.NotEquals;
import com.tibco.jxpath.operations.Or;
import com.tibco.jxpath.operations.PathExpression;
import com.tibco.jxpath.operations.Plus;
import com.tibco.jxpath.operations.StepAxisExpression;
import com.tibco.jxpath.operations.Variable;


public class JXPathASTWalker extends DefaultJXPathASTVisitor {

	private Expression root;
	private Expression currentExpr;
	
	private void visitBinaryOperation(BinaryOperation op, JXPathASTNode node) {
		int count = node.getChildCount();
		if (count != 2) {
			System.out.println("Should not happen");
		}
		JXPathASTNode l = (JXPathASTNode) node.getChild(0);
		JXPathASTNode r = (JXPathASTNode) node.getChild(1);
		
		Expression left = createExpression(l);
		Expression right = createExpression(r);
		
		op.setLeft(left);
		op.setRight(right);
		
		if (this.root == null) {
			this.root = op;
			this.currentExpr = op;
		}
	}
	
	private Expression createExpression(JXPathASTNode l) {
		JXPathASTWalker walker = new JXPathASTWalker();
		l.accept(walker);
		return walker.getRootExpression();
	}

	public Expression getRootExpression() {
		return root;
	}

	@Override
	public boolean visitFunctionCall(JXPathASTNode node) {
		FunctionCall op = new FunctionCall();
		JXPathASTNode nameNode = (JXPathASTNode) node.getChild(0);
		if (nameNode.getChildCount() == 2) {
			// assume this is a prefix/local name pair?
			JXPathASTNode pfxNode = (JXPathASTNode) nameNode.getChild(0);
			JXPathASTNode fnNameNode = (JXPathASTNode) nameNode.getChild(1);
			op.setFunctionName(fnNameNode.getText(), pfxNode.getText());
		} else if (nameNode.getChildCount() == 1) {
			String varName = getName((JXPathASTNode) node.getChild(0));
			op.setFunctionName(varName);
		}
        JXPathASTNode argsNode = (JXPathASTNode) node.getFirstChildWithType(JXPathParser.ARGUMENTS);
        if (argsNode != null && argsNode.getChildCount() > 0) {
        	List children = argsNode.getChildren();
    		for (Object object : children) {
    			JXPathASTNode child = (JXPathASTNode) object;
    			Expression exp =  createExpression(child);
    			op.addExpression(exp);
    		}

        }
		if (this.root == null) {
			this.root = op;
		}
		return false;
	}

	@Override
	public boolean visitFilterExprWithPredicate(JXPathASTNode node) {
		FilterExpression op = new FilterExpression();
		JXPathASTNode primExp = (JXPathASTNode) node.getChild(0);
		Expression expression = createExpression(primExp);
		op.setExpression(expression);
		
        JXPathASTNode predicates = (JXPathASTNode) node.getFirstChildWithType(JXPathParser.PREDICATES);
        if (predicates != null && predicates.getChildCount() > 0) {
        	List children = predicates.getChildren();
    		for (Object object : children) {
    			JXPathASTNode child = (JXPathASTNode) object;
    			Expression exp =  createExpression(child);
    			op.addPredicate(exp);
    		}
        }
		if (this.root == null) {
			this.root = op;
		}
		return false;
	}

	@Override
	public boolean visitVarRef(JXPathASTNode node) {
		Variable op = new Variable();
		String varName = getName((JXPathASTNode) node.getChild(0));
		op.setVariableName(varName);
		if (this.root == null) {
			this.root = op;
		}
		return false;
	}

	private String getName(JXPathASTNode node) {
		if (node.getType() == JXPathParser.QUALIFIED_NAME) {
			node = (JXPathASTNode) node.getChild(0);
		}
		return node.getText();
	}

	@Override
	public boolean visitPathExpr(JXPathASTNode node) {
		PathExpression op = new PathExpression();
		List children = node.getChildren();
		for (int i=0; i<children.size(); i++) {
			Object object = children.get(i);
			JXPathASTNode child = (JXPathASTNode) object;
			Expression exp =  createExpression(child);
            if (exp == null) {
                System.out.println("Expression is null :" + child.getText());
            }
			op.getSteps().add(exp);
			if (i == 0 && exp instanceof Variable) {
				if ("globalVariables".equalsIgnoreCase(getName((JXPathASTNode) child.getChild(0)))) {
					// the GlobalVariables is a flat list, rather than a tree, so we must collapse these steps into one step
					// this makes an assumption that the path does not contain any predicates, etc.
					NamedAxisStep combinedStep = new NamedAxisStep();
					StringBuffer nameBuf = new StringBuffer();
					for (int j=i+2; j<children.size(); j++) { // skip the first '/' after $globalVariables
						JXPathASTNode stepNode = (JXPathASTNode) children.get(j);
						nameBuf.append(getName((JXPathASTNode) stepNode.getChild(0)));
					}
					combinedStep.setQName(new QName(nameBuf.toString()));
					op.getSteps().add(combinedStep);
					if (this.root == null) {
						this.root = op;
					}
					return false;
				}
			}
		}
		if (this.root == null) {
			this.root = op;
		}
		return false;
	}

	@Override
    //TODO - RYAN : Please remove once you are done. It seems sometimes it can have sub PathExpressions, or just plain ASTNode when I use ns0:xxx
    //Also needs to be optimized, so we don't call recursively just to get a StringLiteral for PathExpression.
	public boolean visitNamedAxisStep(JXPathASTNode node) {
		NamedAxisStep op = new NamedAxisStep();
		JXPathASTNode axisSpecifier = (JXPathASTNode) node.getFirstChildWithType(JXPathParser.AXIS_SPECIFIER);
		if (axisSpecifier != null) {
            AxisName axisName = AxisName.axisNamefromXPath(axisSpecifier.getChild(0).getText());
            op.setAxisName(axisName);
		}
		JXPathASTNode nameNode = (JXPathASTNode) node.getFirstChildWithType(JXPathParser.QUALIFIED_NAME);
		QName qName = createQName(nameNode);
        op.setQName(qName);
        
        JXPathASTNode predicates = (JXPathASTNode) node.getFirstChildWithType(JXPathParser.PREDICATES);
        if (predicates != null && predicates.getChildCount() > 0) {
        	List children = predicates.getChildren();
    		for (Object object : children) {
    			JXPathASTNode child = (JXPathASTNode) object;
    			Expression exp =  createExpression(child);
    			op.addPredicate(exp);
    		}
        }
		if (this.root == null) {
			this.root = op;
		}
		return false;
	}

	private QName createQName(JXPathASTNode nameNode) {
		if (nameNode == null) {
			return null;
		}
		if (nameNode.getChildCount() == 1) {
			return new QName(nameNode.getChild(0).getText());
		}
		// should only be two parts to QN, the NS and the localName
		if (nameNode.getChildCount() == 2) {
			String prefix = nameNode.getChild(0).getText();
			return new QName(lookupNamespaceFromPrefix(prefix), nameNode.getChild(1).getText(), prefix);
		}
		
		return null;
	}

	private String lookupNamespaceFromPrefix(String nsPrefix) {
		// TODO : use namespace resolver to lookup namespace
		return nsPrefix;
	}

	@Override
	public boolean visitOrExpr(JXPathASTNode node) {
		BinaryOperation op = new Or();
		visitBinaryOperation(op, node);
		return false;
	}

	@Override
	public boolean visitAndExpr(JXPathASTNode node) {
		BinaryOperation op = new And();
		visitBinaryOperation(op, node);
		return false;
	}

	@Override
	public boolean visitEqualityExpr(JXPathASTNode node) {
		BinaryOperation op = null;
		switch (node.getType()) {
		case JXPathParser.EQUAL:
			op = new Equals();
			break;
			
		case JXPathParser.NOT_EQUAL:
			op = new NotEquals();
			break;

		}
		visitBinaryOperation(op, node);
		return false;
	}

	@Override
	public boolean visitRelationalExpr(JXPathASTNode node) {
		BinaryOperation op = null;
		switch (node.getType()) {
		case JXPathParser.LESS_THAN:
			op = new Lt();
			break;
			
		case JXPathParser.LESS_THAN_EQUALS:
			op = new Lte();
			break;
			
		case JXPathParser.GREATER_THAN:
			op = new Gt();
			break;
			
		case JXPathParser.GREATER_THAN_EQUALS:
			op = new Gte();
			break;

		}
		visitBinaryOperation(op, node);
		return false;
	}

	@Override
	public boolean visitMultiplicativeExpr(JXPathASTNode node) {
		BinaryOperation op = null;
		switch (node.getType()) {
		case JXPathParser.STAR:
			op = new Mult();
			break;
			
		case JXPathParser.DIV:
			op = new Div();
			break;
			
		case JXPathParser.MOD:
			op = new Mod();
			break;
		}
		visitBinaryOperation(op, node);
		return false;
	}

	@Override
	public boolean visitAdditiveExpr(JXPathASTNode node) {
		BinaryOperation op = null;
		switch (node.getType()) {
		case JXPathParser.PLUS:
			op = new Plus();
			break;
			
		case JXPathParser.MINUS:
			op = new Minus();
			break;
			
		}
		visitBinaryOperation(op, node);
		return false;
	}

	@Override
	public boolean visitUnaryExpr(JXPathASTNode node) {
		System.out.println("Found unary expr "+node.toStringTree());
		return true;
	}

	@Override
	public boolean visitConstant(JXPathASTNode node) {
		Expression op = null;
		switch (node.getType()) {
		case JXPathParser.Number:
			op = new XNumber(node.getText());
			break;
			
		case JXPathParser.Literal:
            String literal = node.getText();
			op = new XString(literal.substring(1, literal.length()-1));

			break;

		default:
			break;
		}
		if (this.root == null) {
			this.root = op;
		}
		return false;
	}
	
	@Override
	public boolean visitAbbrStep(JXPathASTNode node) {
		StepAxisExpression op = new StepAxisExpression();
		op.setAbbreviated(true);
		if (this.root == null) {
			this.root = op;
		}
		return false;
	}

	@Override
	public boolean visitSimpleAxisStep(JXPathASTNode node) {
		StepAxisExpression op = new StepAxisExpression();
		op.setAbbreviated(node.getChild(0).getType() == JXPathParser.ABRPATH);
		if (this.root == null) {
			this.root = op;
		}
		return false;
	}
	
}

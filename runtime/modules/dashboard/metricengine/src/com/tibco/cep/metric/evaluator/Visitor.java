package com.tibco.cep.metric.evaluator;

import static com.tibco.cep.metric.condition.ast.filterParser.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Stack;

import org.antlr.runtime.tree.Tree;

import com.tibco.cep.runtime.model.element.Concept;

public class Visitor {

	private static final String SYSTEM_PROPERTY_ID = "id";
	private static final String SYSTEM_PROPERTY_PID = "$pid";
	private static final String SYSTEM_PROPERTY_EXTID = "extId";
	
	private boolean testForPID;

	protected final void visit(final Tree tree, final Concept concept,
			Map<String, Object> bindValues, final Stack<Object> stack)
			throws Exception {
		int tokenType = tree.getType();
		switch (tokenType) {
		case TOK_SELECTOR:
		case PARENTHESES_EXPRESSION:
			visitSelectorNode(tree, concept, bindValues, stack);
			break;
		case TOK_NOT:
			visitNotNode(tree, concept, bindValues, stack);
			break;
		case TOK_LIKE:
			visitLikeNode(tree, concept, bindValues, stack);
			break;
		case TOK_NOTLIKE:
			visitNotLikeNode(tree, concept, bindValues, stack);
			break;
		case TOK_NOTBETWEEN:
		case TOK_OR:
			visitOrNode(tree, concept, bindValues, stack);
			break;
		case TOK_NOR:
			visitNorNode(tree, concept, bindValues, stack);
			break;
		case TOK_IN:
			visitInNode(tree, concept, bindValues, stack);
			break;
		case TOK_NOTIN:
			visitNotInNode(tree, concept, bindValues, stack);
			break;
		case UNARY_MINUS:
		case TOK_ABS:
			visitUnaryOpNode(tree, concept, bindValues, stack);
			break;
		case TOK_GT:
		case TOK_GE:
		case TOK_LT:
		case TOK_LE:
		case TOK_NE:
		case TOK_ISNOT:
		case TOK_EQ:
		case TOK_IS:
		case TOK_PLUS:
		case TOK_MINUS:
		case TOK_STAR:
		case TOK_SLASH:
		case TOK_MOD:
		case TOK_CONCAT:
		case TOK_BETWEEN:
		case TOK_AND:
			visitBinaryOpNode(tree, concept, bindValues, stack);
			break;
		case BIND_VARIABLE_EXPRESSION:
		case PROPERTY:
		case IDENTIFIER:
		case TOK_NULL:
		case TOK_TRUE:
		case TOK_FALSE:
		case DecimalLiteral:
		case HexLiteral:
		case OctalLiteral:
		case StringLiteral:
		case DateLiteral:
		case TimeLiteral:
		case DateTimeLiteral:
		case DIGITS:
		case TOK_APPROXIMATE_NUMERIC_LITERAL:
		case TOK_EXACT_NUMERIC_LITERAL:
			visitIdentifierAndLiteralNode(tree, concept, bindValues, stack);
			break;
		default:
			throw new Exception(
					"AST is either invalid or uses an unsupported op");
		}
	}

	private void visitNotInNode(final Tree tree, final Concept concept,
			Map<String, Object> bindValues, final Stack<Object> stack)
			throws Exception {
		visitNorNode(tree, concept, bindValues, stack);
	}

	private void visitNorNode(final Tree tree, final Concept concept,
			Map<String, Object> bindValues, final Stack<Object> stack)
			throws Exception {
		visitOrNode(tree, concept, bindValues, stack);
		Object result = stack.pop();
		if (Ops.checkBoolean(result)) {
			if (result.equals(true)) {
				stack.push(new Boolean(false));
			} else {
				stack.push(new Boolean(true));
			}
		} else {
			stack.push(result);
		}
	}

	private void visitInNode(final Tree tree, final Concept concept,
			Map<String, Object> bindValues, final Stack<Object> stack)
			throws Exception {
		visitOrNode(tree, concept, bindValues, stack);
	}

	private void visitOrNode(final Tree tree, final Concept concept,
			Map<String, Object> bindValues, final Stack<Object> stack)
			throws Exception {
		for (int i = 0; i < tree.getChildCount(); i++) {
			Tree node = tree.getChild(i);
			visit(node, concept, bindValues, stack);
			Object result = stack.pop();
			if (Ops.checkBoolean(result)) {
				if (result.equals(true)) {
					stack.push(new Boolean(true));
					return; // short-circuit for or evaluation
				}
			} else {
				stack.push(result);
			}
		}
		stack.push(new Boolean(false));
	}

	private void visitNotLikeNode(final Tree tree, final Concept concept,
			Map<String, Object> bindValues, final Stack<Object> stack)
			throws Exception {
		visitLikeNode(tree, concept, bindValues, stack);
		Object result = stack.pop();
		if (Ops.checkBoolean(result)) {
			if (result.equals(true)) {
				stack.push(new Boolean(false));
			} else {
				stack.push(new Boolean(true));
			}
		} else {
			stack.push(result);
		}
	}

	private void visitLikeNode(final Tree tree, final Concept concept,
			Map<String, Object> bindValues, final Stack<Object> stack)
			throws Exception {
		visit(tree.getChild(0), concept, bindValues, stack);
		Object lhs = stack.pop();

		visit(tree.getChild(1), concept, bindValues, stack);
		Object rhs = stack.pop();

		if (Ops.checkString(rhs)) {
			stack.push(Ops.like(lhs, rhs));
		} else {
			throw new Exception("like pattern is not string type");
		}
	}

	private void visitNotNode(final Tree tree, final Concept concept,
			Map<String, Object> bindValues, final Stack<Object> stack)
			throws Exception {
		visit(tree.getChild(0), concept, bindValues, stack);
		Object result = stack.pop();
		if (Ops.checkBoolean(result)) {
			if (result.equals(true)) {
				stack.push(new Boolean(false));
			} else {
				stack.push(new Boolean(true));
			}
		} else {
			stack.push(result);
		}
	}

	private void visitSelectorNode(final Tree tree, final Concept concept,
			Map<String, Object> bindValues, final Stack<Object> stack)
			throws Exception {
		Tree child = tree.getChild(0);
		visit(child, concept, bindValues, stack);
	}

	private void visitBinaryOpNode(final Tree tree, final Concept concept,
			Map<String, Object> bindValues, final Stack<Object> stack)
			throws Exception {
		int tokenType = tree.getType();

		visit(tree.getChild(0), concept, bindValues, stack);
		Object lhs = stack.pop();

		visit(tree.getChild(1), concept, bindValues, stack);
		Object rhs = stack.pop();

		Object result = false;
		switch (tokenType) {
		case TOK_GT:
			result = Ops.greaterThan(lhs, rhs);
			break;
		case TOK_GE:
			result = Ops.greaterThanEquals(lhs, rhs);
			break;
		case TOK_LT:
			result = Ops.lessThan(lhs, rhs);
			break;
		case TOK_LE:
			result = Ops.lessThanEquals(lhs, rhs);
			break;
		case TOK_NE:
		case TOK_ISNOT:
			result = Ops.notEquals(lhs, rhs);
			break;
		case TOK_EQ:
		case TOK_IS:
			result = Ops.equals(lhs, rhs);
			break;
		case TOK_PLUS:
		case TOK_CONCAT:
			result = Ops.concat(lhs, rhs);
			break;
		case TOK_BETWEEN:
		case TOK_AND:
			result = Ops.and(lhs, rhs);
			break;
		case TOK_MINUS:
			result = Ops.minus(lhs, rhs);
			break;
		case TOK_STAR:
			result = Ops.star(lhs, rhs);
			break;
		case TOK_SLASH:
			result = Ops.slash(lhs, rhs);
			break;
		case TOK_MOD:
			result = Ops.mod(lhs, rhs);
			break;
		default:
			throw new Exception("[" + tokenType + "] is not a valid binary op");
		}
		stack.push(result);
	}

	private void visitUnaryOpNode(final Tree tree, final Concept concept,
			Map<String, Object> bindValues, final Stack<Object> stack)
			throws Exception {

		visit(tree.getChild(0), concept, bindValues, stack);
		Object obj = stack.pop();
		if (obj == null) {
			stack.push(null);
			return;
		}

		Object result = false;
		int tokenType = tree.getType();
		switch (tokenType) {
		case UNARY_MINUS:
			result = Ops.minus(obj);
			break;
		case TOK_ABS:
			result = Ops.abs(obj);
			break;
		default:
			throw new Exception("[" + tokenType + "] is not a valid unary op");
		}
		stack.push(result);
	}

	private void visitIdentifierAndLiteralNode(final Tree tree,
			final Concept concept, Map<String, Object> bindValues,
			final Stack<Object> stack) throws Exception {

		int tokenType = tree.getType();
		String text = tree.getText();
		Object result = null;

		switch (tokenType) {
		case BIND_VARIABLE_EXPRESSION:
			try {
				result = bindValues.get(text.substring(1));
			} catch (Exception e) {
				throw new Exception("missing bind value for "
						+ "bind variable [" + text + "]", e);
			}
			break;
		case PROPERTY:
			try {
				if (text != null
						&& text.substring(1).equalsIgnoreCase(
								SYSTEM_PROPERTY_ID)) {
					if (testForPID) {
						result = concept.getPropertyValue(SYSTEM_PROPERTY_PID);
					}
					result = concept.getId();
				} else if (text != null
						&& text.substring(1).equalsIgnoreCase(
								SYSTEM_PROPERTY_EXTID)) {
					result = concept.getExtId();
				} else {
					throw new Exception();
				}
			} catch (Exception e) {
				throw new Exception("system property [" + text
						+ "] does not exist in entity ["
						+ concept.getClass().getSimpleName() + "]", e);
			}
			break;
		case IDENTIFIER:
			try {
				result = concept.getPropertyValue(text);
			} catch (Exception e) {
				throw new Exception("property value [" + text
						+ "] does not exist in entity ["
						+ concept.getClass().getSimpleName() + "]", e);
			}
			break;
		case TOK_NULL:
			result = null;
			break;
		case TOK_TRUE:
		case TOK_FALSE:
			result = Boolean.valueOf(text);
			break;
		case DIGITS:
		case HexLiteral:
		case OctalLiteral:
			result = Long.valueOf(text);
			break;
		case TOK_EXACT_NUMERIC_LITERAL:
		case TOK_APPROXIMATE_NUMERIC_LITERAL:
		case DecimalLiteral:
			result = Double.valueOf(text);
			break;
		case StringLiteral:
			result = text.substring(1, text.length() - 1);
			break;
		case DateLiteral:
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar1 = Calendar.getInstance();
			Date date1 = dateFormat.parse(text.substring(1, text
					.length() - 1));
			calendar1.setLenient(false);
			calendar1.setTime(date1);
			result = calendar1;
			break;
		case TimeLiteral:
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss.SSSZ");
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setLenient(false);
			Date date2 = timeFormat.parse(text.substring(1,
					text.length() - 1));
			calendar2.setTime(date2);
			result = calendar2;
			break;
		case DateTimeLiteral:
			SimpleDateFormat datetimeFormat = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			Calendar calendar3 = Calendar.getInstance();
			calendar3.setLenient(false);
			Date date3 = datetimeFormat.parse(text.substring(1, text
					.length() - 1));
			calendar3.setTime(date3);
			result = calendar3;
			break;
		default:
			throw new Exception("not a valid identifier or a literal node");
		}
		stack.push(result);
	}
	
	public void setTestForPID() {
		testForPID = true;
	}
}

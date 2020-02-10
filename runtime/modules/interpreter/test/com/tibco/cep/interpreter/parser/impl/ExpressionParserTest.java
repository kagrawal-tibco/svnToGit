package com.tibco.cep.interpreter.parser.impl;

import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Properties;

import junit.framework.TestCase;

import org.antlr.runtime.tree.CommonTree;
import org.junit.Test;

import com.tibco.be.util.idgenerators.IdentifierGenerator;
import com.tibco.be.util.idgenerators.serial.LongGenerator;
import com.tibco.cep.interpreter.evaluator.factory.impl.EvaluatorDescriptorFactoryImpl;
import com.tibco.cep.interpreter.parser.ParserResult;
import com.tibco.cep.query.ast.ASTWalker;
import com.tibco.cep.query.ast.parser.BEOqlParser;
import com.tibco.cep.query.exec.codegen.QueryTemplateRegistry;
import com.tibco.cep.query.exec.descriptors.TupleInfoDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.EvaluatorDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.TupleInfoColumnDescriptorImpl;
import com.tibco.cep.query.exec.descriptors.impl.TupleInfoDescriptorImpl;
import com.tibco.cep.query.exec.impl.EvaluatorDescriptorFactory;
import com.tibco.cep.query.model.ProjectContext;
import com.tibco.cep.query.model.RootContext;
import com.tibco.cep.query.model.impl.AbstractOperator;
import com.tibco.cep.query.model.impl.TypeInfoImpl;
import com.tibco.cep.query.model.impl.UnaryExpressionImpl;
import com.tibco.cep.query.model.impl.UnaryOperatorImpl;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.LiteTuple;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.TypeManager.TypeDescriptor;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.PropertyArrayString;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.studio.core.rules.CommonRulesParserManager;
import com.tibco.cep.studio.core.rules.DefaultProblemHandler;

/**
 * User: nprade Date: 8/25/11 Time: 6:54 PM
 */
public class ExpressionParserTest extends TestCase {

	private static final int LOOP_COUNT = (int) 1E6;
	
	private static final RuleServiceProvider RSP;
	static {
		try {
			QueryTemplateRegistry
					.getInstance()
					.loadTemplates(
							"com/tibco/cep/query/exec/codegen/QueryExecutionPlanFactory.stg");
			final Properties props = new Properties();
			props.load(new FileReader(
					"/home/nprade/workspace/runtime/5.1/build/015/be/5.1/bin/be-engine.tra"));
			props.setProperty("tibco.repourl",
					"/home/nprade/workspace/runtime/5.1/issues/test/test.ear");
			RSP = RuleServiceProviderManager.getInstance().newProvider("test",
					props);
			RSP.initProject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private ExpressionParser expressionParser;

	public ExpressionParserTest() throws Exception {
		this.expressionParser = new ExpressionParser();
	}

	@Test
	public void testEvaluatorDescriptorFactoryImpl() throws Exception {
		// final String s = "123 == 345 || (true) && 1<2"; // +x(\"abc\")"
		final String varName = "c";
		final String s = varName + ".p1.p2[2];";

		final DefaultProblemHandler problemHandler = new DefaultProblemHandler();

		CommonTree tree = CommonRulesParserManager.parseActionBlockString(
				"test", "return " + s + ";", problemHandler);
		assertFalse(problemHandler.hasProblems());

		assertTrue(tree.getChildCount() > 0);

		tree = (CommonTree) tree.getChild(0);
		assertTrue(tree.getChildCount() > 0);

		tree = (CommonTree) tree.getChild(0);
		StringBuffer sb = new StringBuffer();
		printNodes(tree, sb, "");

		final TypeManager tm = RSP.getTypeManager();

		final EvaluatorDescriptorFactoryImpl factory = new EvaluatorDescriptorFactoryImpl(
				tm);
		try {
			CommonTree wrappingGroupNode = new CommonTree() {
				public int getType() {
					return BEOqlParser.SELECT_EXPR;
				}
			};
			wrappingGroupNode.addChild(tree);
			WrappingContext parentCtx = new WrappingContext(wrappingGroupNode);

			final TupleInfoDescriptor inputDescriptor = new TupleInfoDescriptorImpl(
					parentCtx);

			final TypeDescriptor td = tm.getTypeDescriptor("/Concepts/C1");
			final com.tibco.cep.designtime.model.element.Concept c = RSP
					.getProject().getOntology().getConcept("/Concepts/C1");

			final TupleInfoColumnDescriptorImpl col = new TupleInfoColumnDescriptorImpl(
					varName, new TypeInfoImpl(c), td.getImplClass().getName(),
					null, null);
			inputDescriptor.addColumn(col);

			for (int i = 0; i < LOOP_COUNT; i++) {
				final EvaluatorDescriptor e = factory.make(tree,
						inputDescriptor);
			}

		} catch (Exception e1) {
			e1.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void testEvaluatorDescriptorFactoryImpl2() throws Exception {
		// final String s = "123 == 345 || (true) && 1<2"; // +x(\"abc\")"
		final String varName = "c";
		final String s = varName + ".p1.p2[2];";

		final DefaultProblemHandler problemHandler = new DefaultProblemHandler();
		CommonTree tree = CommonRulesParserManager.parseActionBlockString(
				"test", "return " + s + ";", problemHandler);
		assertFalse(problemHandler.hasProblems());

		assertTrue(tree.getChildCount() > 0);

		tree = (CommonTree) tree.getChild(0);
		assertTrue(tree.getChildCount() > 0);

		tree = (CommonTree) tree.getChild(0);
		StringBuffer sb = new StringBuffer();
		printNodes(tree, sb, "");

		final TypeManager tm = RSP.getTypeManager();

		final EvaluatorDescriptorFactoryImpl factory = new EvaluatorDescriptorFactoryImpl(
				tm);
		try {
			CommonTree wrappingGroupNode = new CommonTree() {
				public int getType() {
					return BEOqlParser.SELECT_EXPR;
				}
			};
			wrappingGroupNode.addChild(tree);
			WrappingContext parentCtx = new WrappingContext(wrappingGroupNode);

			final TupleInfoDescriptor inputDescriptor = new TupleInfoDescriptorImpl(
					parentCtx);

			final TypeDescriptor td = tm.getTypeDescriptor("/Concepts/C1");
			final com.tibco.cep.designtime.model.element.Concept c = RSP
					.getProject().getOntology().getConcept("/Concepts/C1");

			final TupleInfoColumnDescriptorImpl col = new TupleInfoColumnDescriptorImpl(
					varName, new TypeInfoImpl(c), td.getImplClass().getName(),
					null, null);
			inputDescriptor.addColumn(col);

			final EvaluatorDescriptor e = factory.make(tree, inputDescriptor);

			final FixedKeyHashMap<String, Tuple> inputMap = new FixedKeyHashMap<String, Tuple>(
					Arrays.asList(new String[] { varName }));

			long id = 50;
			final Concept c1 = (Concept) td.getImplClass()
					.getConstructor(long.class).newInstance(id++);
			final Concept c2 = (Concept) tm.getTypeDescriptor("/Concepts/C2")
					.getImplClass().getConstructor(long.class)
					.newInstance(id++);

			c1.setPropertyValue("p1", c2);

			final PropertyArrayString p2 = (PropertyArrayString) c2
					.getProperty("p2");
			p2.set(0, "test0");
			p2.set(1, "test1");
			p2.set(2, "test2");
			p2.set(3, "test3");

			final LiteTuple tuple = new LiteTuple(123, new Object[] { c1 });
			inputMap.setValueAndGetInternalPosition(varName, tuple);

			final Object o = e.getEvaluator().evaluate(null, null, inputMap);
			System.out.println(o);

			assertTrue("test2".equals(o));
		} catch (Exception e1) {
			e1.printStackTrace();
			assertTrue(false);
		}

	}

	private void printNodes(CommonTree tree, StringBuffer stringBuffer,
			String indent) {
		stringBuffer.append(indent).append(tree.getType()).append("=")
				.append(tree.getText()).append("\n");
		for (final Object child : tree.getChildren()) {
			printNodes((CommonTree) child, stringBuffer, indent + "- ");
		}
	}

	@Test
	public void testParseString() throws Exception {

		CommonTree ast = null;

		CommonTree wrappingGroupNode = new CommonTree() {
			public int getType() {
				return BEOqlParser.SELECT_EXPR;
			}
		};
		wrappingGroupNode.addChild(ast);
		WrappingContext parentCtx = new WrappingContext(wrappingGroupNode);

		ASTWalker astWalker = new ASTWalker(wrappingGroupNode, parentCtx);
		astWalker.walkExpression(ast, parentCtx);

		TypeManager typeManager = null;
		EvaluatorDescriptorFactory edf = new EvaluatorDescriptorFactory(
				typeManager);

		for (int i = 0; i < LOOP_COUNT; i++) {
			final ParserResult parserResult = this.expressionParser.parse(
					// "1 = test"
							"123 = 345 or (true) and 1 < 2");
//						    "c.p1.p2[2];");
					ast = parserResult.getAST();
					assertTrue(null != ast);

			EvaluatorDescriptor d = edf.newEvaluatorDescriptor(parentCtx,
					new TupleInfoDescriptorImpl(null));			
			ExpressionEvaluator e = d.getEvaluator();
		}
		
	}

	@Test
	public void testParseString2() throws Exception {

		final ParserResult parserResult = this.expressionParser.parse(
		// "1 = test"
				"123 = 345 or (true) and 1 < 2");
		// "c.p1.p2[2];");
		final CommonTree ast = parserResult.getAST();
		assert (null != ast);

		CommonTree wrappingGroupNode = new CommonTree() {
			public int getType() {
				return BEOqlParser.SELECT_EXPR;
			}
		};
		wrappingGroupNode.addChild(ast);
		WrappingContext parentCtx = new WrappingContext(wrappingGroupNode);

		ASTWalker astWalker = new ASTWalker(wrappingGroupNode, parentCtx);
		astWalker.walkExpression(ast, parentCtx);

		TypeManager typeManager = null;
		EvaluatorDescriptorFactory edf = new EvaluatorDescriptorFactory(
				typeManager);
		EvaluatorDescriptor d = edf.newEvaluatorDescriptor(parentCtx,
				new TupleInfoDescriptorImpl(null));

		ExpressionEvaluator e = d.getEvaluator();
		assert (e.evaluateBoolean(null, null, null));
	}

	@Test
	public void testParseBAIS() throws Exception {

		final ByteArrayInputStream bais = new ByteArrayInputStream(
				"test = 1".getBytes("UTF-8"));
		final ParserResult parserResult = this.expressionParser.parse(bais,
				"UTF-8");
		final CommonTree ast = parserResult.getAST();

		assert (null != ast);
	}

	static class WrappingContext extends UnaryExpressionImpl implements
			RootContext {

		private static final UnaryOperatorImpl op = new UnaryOperatorImpl(
				AbstractOperator.getValidMask(UnaryOperatorImpl.OP_GROUP),
				UnaryOperatorImpl.OP_GROUP);

		public WrappingContext(CommonTree t) {
			super(null, t, op);
		}

		@Override
		public IdentifierGenerator getIdGenerator() {
			return new LongGenerator(true, 1);
		}

		@Override
		public int getContextType() {
			return BEOqlParser.BLOCK_EXPRESSION;
		}

		public ProjectContext getProjectContext() {
			return null;
		}

		public int getType() {
			return BEOqlParser.BLOCK_EXPRESSION;
		}
	}
}

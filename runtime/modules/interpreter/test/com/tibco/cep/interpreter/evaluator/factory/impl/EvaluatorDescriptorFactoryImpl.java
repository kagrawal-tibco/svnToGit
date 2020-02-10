package com.tibco.cep.interpreter.evaluator.factory.impl;

import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.interpreter.evaluator.factory.IEvaluatorDescriptorFactory;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.query.exception.ResolveException;
import com.tibco.cep.query.exec.descriptors.TupleInfoColumnDescriptor;
import com.tibco.cep.query.exec.descriptors.TupleInfoDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.*;
import com.tibco.cep.query.exec.impl.ConversionHelper;
import com.tibco.cep.query.model.*;
import com.tibco.cep.query.model.impl.TypeInfoImpl;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.impl.expression.ConstantValueEvaluator;
import com.tibco.cep.query.stream.impl.expression.TupleContainerExtractor;
import com.tibco.cep.query.stream.impl.expression.TupleExtractor;
import com.tibco.cep.query.stream.impl.expression.TupleExtractorToEvaluatorAdapter;
import com.tibco.cep.query.stream.impl.expression.array.*;
import com.tibco.cep.query.stream.impl.expression.bool.AndEvaluator;
import com.tibco.cep.query.stream.impl.expression.bool.NotEvaluator;
import com.tibco.cep.query.stream.impl.expression.bool.OrEvaluator;
import com.tibco.cep.query.stream.impl.expression.comparison.EqualityEvaluator;
import com.tibco.cep.query.stream.impl.expression.comparison.GreaterThanEvaluator;
import com.tibco.cep.query.stream.impl.expression.comparison.GreaterThanOrEqualToEvaluator;
import com.tibco.cep.query.stream.impl.expression.comparison.InequalityEvaluator;
import com.tibco.cep.query.stream.impl.expression.numeric.*;
import com.tibco.cep.query.stream.impl.expression.property.ConceptPropertyArrayEvaluator;
import com.tibco.cep.query.stream.impl.expression.property.ConceptPropertyAtomEvaluator;
import com.tibco.cep.query.stream.impl.expression.property.ConceptPropertyAtomValueEvaluator;
import com.tibco.cep.query.stream.impl.expression.property.SimpleEventPropertyValueEvaluator;
import com.tibco.cep.query.stream.impl.expression.reflection.InstanceOfEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.utils.TypeHelper;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.TypeManager.TypeDescriptor;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import org.antlr.runtime.tree.CommonTree;

import java.util.ArrayList;

/*
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Jul 28, 2008
 * Time: 6:17:32 PM
 */


public class EvaluatorDescriptorFactoryImpl
        implements IEvaluatorDescriptorFactory {


    private final ConversionHelper conversionHelper;
    private final DecimalLiteralFactory decimalLiteralFactory = new DecimalLiteralFactory();
    private final FloatingPointLiteralFactory floatingPointLiteralFactory = new FloatingPointLiteralFactory();
    private final StringLiteralFactory stringLiteralFactory = new StringLiteralFactory();
    private final TypeManager typeManager;


    /**
     * @param typeManager TypeManager used to find runtime classes.
     */
    public EvaluatorDescriptorFactoryImpl(
            TypeManager typeManager) {

        this.typeManager = typeManager;
        this.conversionHelper = new ConversionHelper(typeManager);
    }


    protected EvaluatorDescriptor composeEvaluator(
            CommonTree expression,
            TupleInfoDescriptor inputDescriptor)
            throws Exception {

        return this.composeEvaluator(expression, inputDescriptor, null);
    }


    protected EvaluatorDescriptor composeEvaluator(
            CommonTree expression,
            TupleInfoDescriptor inputDescriptor,
            TypeInfo expectedType)
            throws Exception {

        if (null == expression) {
            return null; //todo is this always ok?
        }

        switch (expression.getType()) {

            case RulesParser.AND:
                return this.processAND(expression, inputDescriptor, expectedType);

            case RulesParser.DecimalLiteral:
                return this.processDecimalLiteral(expression, expectedType);

            case RulesParser.DIVIDE:
                return this.processDIVIDE(expression, inputDescriptor, expectedType);

            case RulesParser.EQ:
                return this.processEQ(expression, inputDescriptor, expectedType);

            case RulesParser.FALSE:
                return this.processFALSE(expectedType);

            case RulesParser.FloatingPointLiteral:
                return this.processFloatingPointLiteral(expression, expectedType);

            case RulesParser.GE:
                return this.processGE(expression, inputDescriptor, expectedType);

            case RulesParser.GT:
                return this.processGT(expression, inputDescriptor, expectedType);

            case RulesParser.HexLiteral:
                return this.processHexLiteral(expression, expectedType);

            case RulesParser.INSTANCE_OF:
                return this.processINSTANCE_OF(expression, inputDescriptor, expectedType);

            case RulesParser.LE:
                return this.processLE(expression, inputDescriptor, expectedType);

            case RulesParser.LT:
                return this.processLT(expression, inputDescriptor, expectedType);

            case RulesParser.MINUS:
                return this.processMINUS(expression, inputDescriptor, expectedType);

            case RulesParser.MULT:
                return this.processMULT(expression, inputDescriptor, expectedType);

            case RulesParser.MOD:
                return this.processMOD(expression, inputDescriptor, expectedType);

            case RulesParser.NE:
                return this.processNE(expression, inputDescriptor, expectedType);

            case RulesParser.NullLiteral:
                return this.processNullLiteral(expectedType);

            case RulesParser.OR:
                return this.processOR(expression, inputDescriptor, expectedType);

            case RulesParser.PLUS:
                return this.processPLUS(expression, inputDescriptor, expectedType);

            case RulesParser.POUND:
                return this.processPOUND(expression, inputDescriptor, expectedType);

            case RulesParser.PRIMARY_EXPRESSION:
                return this.processPRIMARY_EXPRESSION(expression, inputDescriptor, expectedType);

            case RulesParser.QUALIFIED_NAME:
                return this.processQUALIFIED_NAME(expression, inputDescriptor, expectedType);

            case RulesParser.SIMPLE_NAME:
                return this.processSIMPLE_NAME(expression, inputDescriptor, expectedType);

            case RulesParser.StringLiteral:
                return this.processStringLiteral(expression, expectedType);

            case RulesParser.TRUE:
                return this.processTRUE(expectedType);

            // Pass through
            case RulesParser.EXPRESSION:
            case RulesParser.PREFIX:
            case RulesParser.QUALIFIER:
                return this.composeEvaluator((CommonTree) expression.getChild(0), inputDescriptor, expectedType);


            // Done elsewhere
//            case RulesParser.ARRAY_ACCESS_SUFFIX:

            // todo other CommonTree types
            case RulesParser.ACTION_CONTEXT_BLOCK:
            case RulesParser.ACTION_CONTEXT_STATEMENT:
            case RulesParser.ACTION_TYPE:
            case RulesParser.ALIAS_STATEMENT:
            case RulesParser.ANNOTATE:
            case RulesParser.ANNOTATION:
            case RulesParser.ARGS:
            case RulesParser.ARRAY_ALLOCATOR:
            case RulesParser.ARRAY_LITERAL:
            case RulesParser.ASSIGN:
            case RulesParser.ASSIGNMENT_SUFFIX:
            case RulesParser.ATTRIBUTE_BLOCK:
            case RulesParser.BACKWARD_CHAIN_STATEMENT:
            case RulesParser.BINARY_RELATION_NODE:
            case RulesParser.BINDINGS_BLOCK:
            case RulesParser.BINDING_STATEMENT:
            case RulesParser.BINDINGS_VIEWS_BLOCK:
            case RulesParser.BINDING_VIEW_STATEMENT:
            case RulesParser.BLOCK:
            case RulesParser.BLOCKS:
            case RulesParser.BODY:
            case RulesParser.BODY_BLOCK:
            case RulesParser.BREAK_STATEMENT:
            case RulesParser.CATCH_CLAUSE:
            case RulesParser.CATCH_RULE:
            case RulesParser.CharacterLiteral:
            case RulesParser.COMMENT:
            case RulesParser.CONTINUE_STATEMENT:
            case RulesParser.DECLARATOR:
            case RulesParser.DECLARATORS:
            case RulesParser.DECLARE_BLOCK:
            case RulesParser.DECR:
            case RulesParser.DIV_EQUAL:
            case RulesParser.DOMAIN_MODEL:
            case RulesParser.DOMAIN_MODEL_STATEMENT:
            case RulesParser.DOT:
            case RulesParser.ELSE_STATEMENT:
            case RulesParser.EscapeSequence:
            case RulesParser.Exponent:
            case RulesParser.EXPRESSIONS:
            case RulesParser.FINALLY_CLAUSE:
            case RulesParser.FINALLY_RULE:
            case RulesParser.FloatTypeSuffix:
            case RulesParser.FOR_LOOP:
            case RulesParser.FOR_RULE:
            case RulesParser.FORWARD_CHAIN_STATEMENT:
            case RulesParser.HEADER:
            case RulesParser.HeaderSection:
            case RulesParser.HEADER_START:
            case RulesParser.HexDigit:
            case RulesParser.Identifier:
            case RulesParser.IDENTIFIER:
            case RulesParser.IF_RULE:
            case RulesParser.INCR:
            case RulesParser.INITIALIZER:
            case RulesParser.IntegerTypeSuffix:
            case RulesParser.JavaIDDigit:
            case RulesParser.LASTMOD_STATEMENT:
            case RulesParser.LBRACE:
            case RulesParser.LBRACK:
            case RulesParser.Letter:
            case RulesParser.LHS:
            case RulesParser.LINE_COMMENT:
            case RulesParser.LITERAL:
            case RulesParser.LOCAL_INITIALIZER:
            case RulesParser.LOCAL_VARIABLE_DECL:
            case RulesParser.LPAREN:
            case RulesParser.MAPPING_BLOCK:
            case RulesParser.MappingEnd:
            case RulesParser.METHOD_CALL:
            case RulesParser.MINUS_EQUAL:
            case RulesParser.MOD_EQUAL:
            case RulesParser.MODIFIERS:
            case RulesParser.MULT_EQUAL:
            case RulesParser.NAME:
            case RulesParser.OctalEscape:
            case RulesParser.OPERATOR:
            case RulesParser.PLUS_EQUAL:
            case RulesParser.PREDICATES:
            case RulesParser.PREDICATE_STATEMENT:
            case RulesParser.PRIMARY_ASSIGNMENT_EXPRESSION:
            case RulesParser.PRIMITIVE_TYPE:
            case RulesParser.PRIORITY:
            case RulesParser.PRIORITY_STATEMENT:
            case RulesParser.RANGE_END:
            case RulesParser.RANGE_EXPRESSION:
            case RulesParser.RANGE_START:
            case RulesParser.RANK_STATEMENT:
            case RulesParser.RBRACE:
            case RulesParser.RBRACK:
            case RulesParser.REQUEUE_STATEMENT:
            case RulesParser.RETURN_STATEMENT:
            case RulesParser.RETURN_TYPE:
            case RulesParser.RHS:
            case RulesParser.RPAREN:
            case RulesParser.RULE:
            case RulesParser.RULE_BLOCK:
            case RulesParser.RULE_DECL:
            case RulesParser.RULE_FUNC_DECL:
            case RulesParser.RULE_FUNCTION:
            case RulesParser.RULE_TEMPLATE:
            case RulesParser.RULE_TEMPLATE_DECL:
            case RulesParser.SCOPE_BLOCK:
            case RulesParser.SCOPE_DECLARATOR:
            case RulesParser.SEMICOLON:
            case RulesParser.SET_MEMBERSHIP_EXPRESSION:
            case RulesParser.STATEMENT:
            case RulesParser.STATEMENTS:
            case RulesParser.SUFFIX:
            case RulesParser.SUFFIXES:
            case RulesParser.SUFFIX_EXPRESSION:
            case RulesParser.T__161:
            case RulesParser.T__162:
            case RulesParser.T__163:
            case RulesParser.T__164:
            case RulesParser.T__165:
            case RulesParser.T__166:
            case RulesParser.T__167:
            case RulesParser.T__168:
            case RulesParser.T__169:
            case RulesParser.T__170:
            case RulesParser.T__171:
            case RulesParser.T__172:
            case RulesParser.T__173:
            case RulesParser.T__174:
            case RulesParser.T__175:
            case RulesParser.T__176:
            case RulesParser.T__177:
            case RulesParser.T__178:
            case RulesParser.T__179:
            case RulesParser.T__180:
            case RulesParser.T__181:
            case RulesParser.T__182:
            case RulesParser.T__183:
            case RulesParser.T__184:
            case RulesParser.T__185:
            case RulesParser.T__186:
            case RulesParser.T__187:
            case RulesParser.T__188:
            case RulesParser.T__189:
            case RulesParser.T__190:
            case RulesParser.T__191:
            case RulesParser.T__192:
            case RulesParser.T__193:
            case RulesParser.T__194:
            case RulesParser.T__195:
            case RulesParser.T__196:
            case RulesParser.T__197:
            case RulesParser.T__198:
            case RulesParser.T__199:
            case RulesParser.T__200:
            case RulesParser.T__201:
            case RulesParser.T__202:
            case RulesParser.T__203:
            case RulesParser.T__204:
            case RulesParser.T__205:
            case RulesParser.T__206:
            case RulesParser.T__207:
            case RulesParser.T__208:
            case RulesParser.T__209:
            case RulesParser.T__210:
            case RulesParser.T__211:
            case RulesParser.T__212:
            case RulesParser.T__213:
            case RulesParser.T__214:
            case RulesParser.T__215:
            case RulesParser.T__216:
            case RulesParser.T__217:
            case RulesParser.T__218:
            case RulesParser.T__219:
            case RulesParser.T__220:
            case RulesParser.T__221:
            case RulesParser.T__222:
            case RulesParser.T__223:
            case RulesParser.T__224:
            case RulesParser.T__225:
            case RulesParser.T__226:
            case RulesParser.T__227:
            case RulesParser.T__228:
            case RulesParser.T__229:
            case RulesParser.T__230:
            case RulesParser.T__231:
            case RulesParser.T__232:
            case RulesParser.T__233:
            case RulesParser.T__234:
            case RulesParser.T__235:
            case RulesParser.T__236:
            case RulesParser.T__237:
            case RulesParser.T__238:
            case RulesParser.T__239:
            case RulesParser.T__240:
            case RulesParser.T__241:
            case RulesParser.T__242:
            case RulesParser.T__243:
            case RulesParser.TEMPLATE_DECLARATOR:
            case RulesParser.THEN_BLOCK:
            case RulesParser.THROW_STATEMENT:
            case RulesParser.TRY_RULE:
            case RulesParser.TRY_STATEMENT:
            case RulesParser.TRY_STATEMET:
            case RulesParser.TYPE:
            case RulesParser.UNARY_EXPRESSION_NODE:
            case RulesParser.UnicodeEscape:
            case RulesParser.VALIDITY:
            case RulesParser.VALIDITY_STATEMENT:
            case RulesParser.VARIABLE_DECLARATOR:
            case RulesParser.VIRTUAL:
            case RulesParser.VOID_LITERAL:
            case RulesParser.WHEN_BLOCK:
            case RulesParser.WHILE_RULE:
            case RulesParser.WS:
            default:
                throw new Exception(
                        "Unsupported expression type: (" + expression.getType() + ") " + expression.getText());
        }

    }


    protected TupleExtractorDescriptor composeExtractorFromTuple( //todo remove dependency on query
    		CommonTree context,
            TupleInfoDescriptor descriptor) {

        // Tries to find the context directly in a column
        final TupleInfoColumnDescriptor directColumnDescriptor = descriptor.getColumnByName(context.getText());
        if (null != directColumnDescriptor) {
            return descriptor.getColumnExtractor(directColumnDescriptor);
        }

        // Looks in every column that contains an Expression, or a Tuple
        int columnIndex = 0;
        for (final TupleInfoColumnDescriptor columnDescriptor : descriptor.getColumns()) {

            ModelContext ctx = columnDescriptor.getModelContext();

            if (ctx instanceof Expression) {
                try {
                    if (context.equals(((Expression) ctx).getIdentifiedContext())) {
                        return descriptor.getColumnExtractor(columnDescriptor);
                    }
                } catch (ResolveException shouldNotHappen) {
                    shouldNotHappen.printStackTrace();
                }
            }

            final TupleInfoDescriptor containedTupleDescriptor = columnDescriptor.getTupleInfoDescriptor();
            if (null != containedTupleDescriptor) {
                // This column contains a tuple.

                final TupleExtractorDescriptor found =
                        this.composeExtractorFromTuple(context, containedTupleDescriptor);
                if (null != found) {
                    // The data can be extracted from the column tuple.

                    final TupleExtractor container = new TupleContainerExtractor(
                            found.getTupleExtractor(), columnIndex);

                    final TupleExtractorDescriptor result = new TupleExtractorDescriptor(
                            container, found.getTypeInfo());
                    result.addUsedColumnName(columnDescriptor.getName(), columnDescriptor.getClassName());
                    return result;
                }
            }
            columnIndex++;
        }

        return null;
    }


    private EvaluatorDescriptor convert(
            EvaluatorDescriptor evaluatorDescriptor,
            TypeInfo expectedType)
            throws Exception {

        return this.conversionHelper.convert(evaluatorDescriptor, expectedType);
    }


    private TypeInfo findNumbersCombinationType(
            EvaluatorDescriptor left,
            EvaluatorDescriptor right)
            throws Exception {

        return new TypeInfoImpl(
                TypeHelper.getNumericCoercionClass(
                        this.getRuntimeClass(left.getTypeInfo()),
                        this.getRuntimeClass(right.getTypeInfo())));

//        final TypeInfo typeLeft = left.getTypeInfo();
//        if (!typeLeft.isNumber()) {
//            throw new IllegalArgumentException("Not a number: " + left);
//        }
//
//        final TypeInfo typeRight = right.getTypeInfo();
//        if (!typeRight.isNumber()) {
//            throw new IllegalArgumentException("Not a number: " + right);
//        }
//
//        final Class resultClass = TypeHelper.getNumericCoercionClass(
//                this.getRuntimeClass(typeLeft),
//                this.getRuntimeClass(typeRight));
//
//        return new TypeInfoImpl(resultClass);
    }


    private Class getRuntimeClass(
            TypedContext typedCtx)
            throws Exception {
        return this.getRuntimeClass(typedCtx.getTypeInfo());
    }


    private Class getRuntimeClass(
            TypeInfo typeInfo)
            throws Exception {
        return typeInfo.getRuntimeClass(this.typeManager);
    }


    public EvaluatorDescriptor make(
            CommonTree expression,
            TupleInfoDescriptor inputDescriptor)
            throws Exception {

        //todo Do we need AliasMap etc?
        if (inputDescriptor instanceof AliasMapDescriptor) {
            return this.composeEvaluator(expression, inputDescriptor);
        }

        final TupleInfoDescriptor wrapper = new TupleInfoDescriptorImpl(null);
        wrapper.addColumn(new TupleInfoColumnDescriptorImpl("0",
                new TypeInfoImpl(Tuple.class),
                inputDescriptor.getTupleClassName(),
                null,
                inputDescriptor));
        return this.composeEvaluator(expression, wrapper);
    }


    private EvaluatorDescriptor processAND(
            CommonTree expression,
            TupleInfoDescriptor inputDescriptor,
            TypeInfo expectedType)
            throws Exception {

        final ArrayList<EvaluatorDescriptor> childDescriptors = new ArrayList<EvaluatorDescriptor>(
                expression.getChildCount());
        final ArrayList<ExpressionEvaluator> childEvaluators = new ArrayList<ExpressionEvaluator>(
                expression.getChildCount());

        for (Object child : expression.getChildren()) {
            final EvaluatorDescriptor descriptor = this.composeEvaluator(
                    (CommonTree) child, inputDescriptor, TypeInfoImpl.BOOLEAN);
            childDescriptors.add(descriptor);
            childEvaluators.add(descriptor.getEvaluator());
        }

        final EvaluatorDescriptor d = new EvaluatorDescriptor(
                new AndEvaluator(childEvaluators.toArray(new ExpressionEvaluator[expression.getChildCount()])),
                TypeInfoImpl.BOOLEAN);

        for (final EvaluatorDescriptor descriptor : childDescriptors) {
            d.addUsedColumnNames(descriptor);
        }

        return this.convert(d, expectedType);
    }


    private EvaluatorDescriptor processARRAY_ACCESS_SUFFIX(
            EvaluatorDescriptor prefixDescriptor,
            CommonTree suffixChild,
            TupleInfoDescriptor inputDescriptor,
            TypeInfo expectedType)
            throws Exception {

        final TypeInfo arrayTypeInfo = prefixDescriptor.getTypeInfo();
        final TypeInfo arrayItemTypeInfo = arrayTypeInfo.getArrayItemType();

        final EvaluatorDescriptor indexDescriptor = this.composeEvaluator(
                (CommonTree) suffixChild.getChild(0), inputDescriptor, TypeInfoImpl.INTEGER);

        final EvaluatorDescriptor d;

        if (arrayTypeInfo.isPropertyArray()) {
            d = new EvaluatorDescriptor(
                    new PropertyArrayItemEvaluator(prefixDescriptor.getEvaluator(), indexDescriptor.getEvaluator()),
                    arrayItemTypeInfo);
        }
        else if (arrayTypeInfo.isArray()) {
            final Class resultType = this.getRuntimeClass(arrayItemTypeInfo);
            final ExpressionEvaluator e;
            if (boolean.class.equals(resultType)) {
                e = new BooleanArrayItemEvaluator(prefixDescriptor.getEvaluator(), indexDescriptor.getEvaluator());
            }
            else if (byte.class.equals(resultType)) {
                e = new ByteArrayItemEvaluator(prefixDescriptor.getEvaluator(), indexDescriptor.getEvaluator());
            }
            else if (char.class.equals(resultType)) {
                e = new CharArrayItemEvaluator(prefixDescriptor.getEvaluator(), indexDescriptor.getEvaluator());
            }
            else if (double.class.equals(resultType)) {
                e = new DoubleArrayItemEvaluator(prefixDescriptor.getEvaluator(), indexDescriptor.getEvaluator());
            }
            else if (float.class.equals(resultType)) {
                e = new FloatArrayItemEvaluator(prefixDescriptor.getEvaluator(), indexDescriptor.getEvaluator());
            }
            else if (int.class.equals(resultType)) {
                e = new IntArrayItemEvaluator(prefixDescriptor.getEvaluator(), indexDescriptor.getEvaluator());
            }
            else if (long.class.equals(resultType)) {
                e = new LongArrayItemEvaluator(prefixDescriptor.getEvaluator(), indexDescriptor.getEvaluator());
            }
            else if (short.class.equals(resultType)) {
                e = new ShortArrayItemEvaluator(prefixDescriptor.getEvaluator(), indexDescriptor.getEvaluator());
            }
            else {
                e = new ObjectArrayItemEvaluator(prefixDescriptor.getEvaluator(), indexDescriptor.getEvaluator());
            }
            d = new EvaluatorDescriptor(e, arrayItemTypeInfo);
        }
        else {
            d = null;
        }

        if (null == d) {
            throw new Exception("Unsuported array type: " + arrayTypeInfo);
        }

        d.addUsedColumnNames(prefixDescriptor);
        d.addUsedColumnNames(indexDescriptor);

        return this.convert(d, expectedType);
    }


    private EvaluatorDescriptor processDecimalLiteral(
            CommonTree expression,
            TypeInfo expectedType)
            throws Exception {

        final TypedValue v = this.decimalLiteralFactory.make(expression.getText().trim(), 10);

        return this.convert(
                new EvaluatorDescriptor(new ConstantValueEvaluator(v.getValue()), v.getType()),
                expectedType);
    }


    private EvaluatorDescriptor processDIVIDE(
            CommonTree expression,
            TupleInfoDescriptor inputDescriptor,
            TypeInfo expectedType)
            throws Exception {

//        if (null == expectedType) { // todo ?
//            expectedType = TypeInfoImpl.DOUBLE;
//        }

        final EvaluatorDescriptor left = this.composeEvaluator(
                (CommonTree) expression.getChild(0), inputDescriptor, expectedType);

        final EvaluatorDescriptor right = this.composeEvaluator(
                (CommonTree) expression.getChild(1), inputDescriptor, expectedType);

        final TypeInfo resultType = this.findNumbersCombinationType(left, right);

        final EvaluatorDescriptor d = new EvaluatorDescriptor(
                new DivisionEvaluator(left.getEvaluator(), right.getEvaluator()),
                resultType);

        d.addUsedColumnNames(left);
        d.addUsedColumnNames(right);

        return this.convert(d, expectedType);
    }


    private EvaluatorDescriptor processEQ(
            CommonTree expression,
            TupleInfoDescriptor inputDescriptor,
            TypeInfo expectedType)
            throws Exception {

        final EvaluatorDescriptor left = this.composeEvaluator((CommonTree) expression.getChild(0), inputDescriptor);
        final EvaluatorDescriptor right = this.composeEvaluator((CommonTree) expression.getChild(1), inputDescriptor);
        //todo handle immediately if left and right types cannot match

        final EvaluatorDescriptor d = new EvaluatorDescriptor(
                new EqualityEvaluator(left.getEvaluator(), right.getEvaluator()),
                TypeInfoImpl.BOOLEAN);

        d.addUsedColumnNames(left);
        d.addUsedColumnNames(right);

        return this.convert(d, expectedType);
    }


    private EvaluatorDescriptor processFALSE(TypeInfo expectedType)
            throws Exception {

        return this.convert(
                new EvaluatorDescriptor(ConstantValueEvaluator.FALSE, TypeInfoImpl.BOOLEAN),
                expectedType);
    }


    private EvaluatorDescriptor processFloatingPointLiteral(
            CommonTree expression,
            TypeInfo expectedType)
            throws Exception {

        final TypedValue v = this.floatingPointLiteralFactory.make(expression.getText().trim());
        return this.convert(
                new EvaluatorDescriptor(new ConstantValueEvaluator(v.getValue()), v.getType()),
                expectedType);
    }


    private EvaluatorDescriptor processGE(
            CommonTree expression,
            TupleInfoDescriptor inputDescriptor,
            TypeInfo expectedType)
            throws Exception {

        final EvaluatorDescriptor left = this.composeEvaluator((CommonTree) expression.getChild(0), inputDescriptor);
        final EvaluatorDescriptor right = this.composeEvaluator((CommonTree) expression.getChild(1), inputDescriptor);
        //todo handle immediately if left and right types cannot match

        final EvaluatorDescriptor d = new EvaluatorDescriptor(
                new GreaterThanOrEqualToEvaluator(
                        left.getEvaluator(),
                        right.getEvaluator()),
                TypeInfoImpl.BOOLEAN);

        d.addUsedColumnNames(left);
        d.addUsedColumnNames(right);

        return this.convert(d, expectedType);
    }


    private EvaluatorDescriptor processGT(
            CommonTree expression,
            TupleInfoDescriptor inputDescriptor,
            TypeInfo expectedType)
            throws Exception {

        final EvaluatorDescriptor left = this.composeEvaluator((CommonTree) expression.getChild(0), inputDescriptor);
        final EvaluatorDescriptor right = this.composeEvaluator((CommonTree) expression.getChild(1), inputDescriptor);
        //todo handle immediately if left and right types cannot match

        final EvaluatorDescriptor d = new EvaluatorDescriptor(
                new GreaterThanEvaluator(
                        left.getEvaluator(),
                        right.getEvaluator()),
                TypeInfoImpl.BOOLEAN);

        d.addUsedColumnNames(left);
        d.addUsedColumnNames(right);

        return this.convert(d, expectedType);
    }


    private EvaluatorDescriptor processHexLiteral(
            CommonTree expression,
            TypeInfo expectedType)
            throws Exception {

        final TypedValue v = this.decimalLiteralFactory.make(expression.getText().trim().substring(2), 16);
        return this.convert(
                new EvaluatorDescriptor(new ConstantValueEvaluator(v.getValue()), v.getType()),
                expectedType);
    }


    private EvaluatorDescriptor processINSTANCE_OF(
            CommonTree expression,
            TupleInfoDescriptor inputDescriptor,
            TypeInfo expectedType)
            throws Exception {

        final EvaluatorDescriptor left = this.composeEvaluator(
                (CommonTree) expression.getChild(0), inputDescriptor, TypeInfoImpl.OBJECT);
        final EvaluatorDescriptor right = this.composeEvaluator(
                (CommonTree) expression.getChild(1), inputDescriptor, TypeInfoImpl.CLASS);

        return this.convert(
                new EvaluatorDescriptor(
                        new InstanceOfEvaluator(left.getEvaluator(), right.getEvaluator()),
                        TypeInfoImpl.BOOLEAN),
                expectedType);
    }


    private EvaluatorDescriptor processLE(
            CommonTree expression,
            TupleInfoDescriptor inputDescriptor,
            TypeInfo expectedType)
            throws Exception {

        final EvaluatorDescriptor left = this.composeEvaluator((CommonTree) expression.getChild(0), inputDescriptor);
        final EvaluatorDescriptor right = this.composeEvaluator((CommonTree) expression.getChild(1), inputDescriptor);
        //todo handle immediately if left and right types cannot match

        final EvaluatorDescriptor d = new EvaluatorDescriptor(
                new GreaterThanOrEqualToEvaluator(right.getEvaluator(), left.getEvaluator()),
                TypeInfoImpl.BOOLEAN);

        d.addUsedColumnNames(left);
        d.addUsedColumnNames(right);

        return this.convert(d, expectedType);
    }


    private EvaluatorDescriptor processLT(
            CommonTree expression,
            TupleInfoDescriptor inputDescriptor,
            TypeInfo expectedType)
            throws Exception {

        final EvaluatorDescriptor left = this.composeEvaluator((CommonTree) expression.getChild(0), inputDescriptor);
        final EvaluatorDescriptor right = this.composeEvaluator((CommonTree) expression.getChild(1), inputDescriptor);
        //todo handle immediately if left and right types cannot match

        final EvaluatorDescriptor d = new EvaluatorDescriptor(
                new GreaterThanEvaluator(right.getEvaluator(), left.getEvaluator()),
                TypeInfoImpl.BOOLEAN);

        d.addUsedColumnNames(left);
        d.addUsedColumnNames(right);

        return this.convert(d, expectedType);
    }


    private EvaluatorDescriptor processMINUS(
            CommonTree expression,
            TupleInfoDescriptor inputDescriptor,
            TypeInfo expectedType)
            throws Exception {

        final EvaluatorDescriptor left = this.composeEvaluator(
                (CommonTree) expression.getChild(0), inputDescriptor, expectedType);

        //todo unary op ?
        final EvaluatorDescriptor right = this.composeEvaluator(
                (CommonTree) expression.getChild(1), inputDescriptor, expectedType);

        final TypeInfo resultType = this.findNumbersCombinationType(left, right);

        final EvaluatorDescriptor d = new EvaluatorDescriptor(
                new SubtractionEvaluator(left.getEvaluator(), right.getEvaluator()),
                resultType);

        d.addUsedColumnNames(left);
        d.addUsedColumnNames(right);

        return this.convert(d, expectedType);
    }


    private EvaluatorDescriptor processMOD(
            CommonTree expression,
            TupleInfoDescriptor inputDescriptor,
            TypeInfo expectedType)
            throws Exception {

        final EvaluatorDescriptor left = this.composeEvaluator(
                (CommonTree) expression.getChild(0), inputDescriptor, expectedType);

        final EvaluatorDescriptor right = this.composeEvaluator(
                (CommonTree) expression.getChild(1), inputDescriptor, expectedType);

        final TypeInfo resultType = this.findNumbersCombinationType(left, right);

        final EvaluatorDescriptor d = new EvaluatorDescriptor(
                new ModuloEvaluator(left.getEvaluator(), right.getEvaluator()),
                resultType);

        d.addUsedColumnNames(left);
        d.addUsedColumnNames(right);

        return this.convert(d, expectedType);
    }


    private EvaluatorDescriptor processMULT(
            CommonTree expression,
            TupleInfoDescriptor inputDescriptor,
            TypeInfo expectedType)
            throws Exception {

        final EvaluatorDescriptor left = this.composeEvaluator(
                (CommonTree) expression.getChild(0), inputDescriptor, expectedType);

        final EvaluatorDescriptor right = this.composeEvaluator(
                (CommonTree) expression.getChild(1), inputDescriptor, expectedType);

        final TypeInfo resultType = this.findNumbersCombinationType(left, right);

        final EvaluatorDescriptor d = new EvaluatorDescriptor(
                new MultiplicationEvaluator(left.getEvaluator(), right.getEvaluator()),
                resultType);

        d.addUsedColumnNames(left);
        d.addUsedColumnNames(right);

        return this.convert(d, expectedType);
    }


    private EvaluatorDescriptor processNE(
            CommonTree expression,
            TupleInfoDescriptor inputDescriptor,
            TypeInfo expectedType)
            throws Exception {

        final EvaluatorDescriptor left = this.composeEvaluator((CommonTree) expression.getChild(0), inputDescriptor);
        final EvaluatorDescriptor right = this.composeEvaluator((CommonTree) expression.getChild(1), inputDescriptor);
        //todo handle immediately if left and right types cannot match

        final EvaluatorDescriptor d = new EvaluatorDescriptor(
                new InequalityEvaluator(left.getEvaluator(), right.getEvaluator()),
                TypeInfoImpl.BOOLEAN);

        d.addUsedColumnNames(left);
        d.addUsedColumnNames(right);

        return this.convert(d, expectedType);
    }


    private EvaluatorDescriptor processNullLiteral(
            TypeInfo expectedType)
            throws Exception {

        return this.convert(
                new EvaluatorDescriptor(ConstantValueEvaluator.NULL, TypeInfoImpl.OBJECT),
                expectedType);
    }


    private EvaluatorDescriptor processOR(
            CommonTree expression,
            TupleInfoDescriptor inputDescriptor,
            TypeInfo expectedType)
            throws Exception {

        final ArrayList<EvaluatorDescriptor> childDescriptors = new ArrayList<EvaluatorDescriptor>(
                expression.getChildCount());
        final ArrayList<ExpressionEvaluator> childEvaluators = new ArrayList<ExpressionEvaluator>(
                expression.getChildCount());
        for (Object child : expression.getChildren()) {
            final EvaluatorDescriptor descriptor = this.composeEvaluator(
                    (CommonTree) child, inputDescriptor, TypeInfoImpl.BOOLEAN);
            childDescriptors.add(descriptor);
            childEvaluators.add(descriptor.getEvaluator());
        }

        final EvaluatorDescriptor d = new EvaluatorDescriptor(
                new OrEvaluator(childEvaluators.toArray(new ExpressionEvaluator[expression.getChildCount()])),
                TypeInfoImpl.BOOLEAN);

        for (final EvaluatorDescriptor descriptor : childDescriptors) {
            d.addUsedColumnNames(descriptor);
        }

        return this.convert(d, expectedType);
    }


    private EvaluatorDescriptor processPLUS(
            CommonTree expression,
            TupleInfoDescriptor inputDescriptor,
            TypeInfo expectedType)
            throws Exception {

        final EvaluatorDescriptor left = this.composeEvaluator(
                (CommonTree) expression.getChild(0), inputDescriptor, expectedType);

        //todo unary op ?
        final EvaluatorDescriptor right = this.composeEvaluator(
                (CommonTree) expression.getChild(1), inputDescriptor, expectedType);

        final TypeInfo resultType = this.findNumbersCombinationType(left, right);

        final EvaluatorDescriptor d = new EvaluatorDescriptor(
                new AdditionEvaluator(left.getEvaluator(), right.getEvaluator()),
                resultType);

        d.addUsedColumnNames(left);
        d.addUsedColumnNames(right);

        return this.convert(d, expectedType);
    }


    private EvaluatorDescriptor processPOUND( // POUND is a bad name for NOT.
            CommonTree expression,
            TupleInfoDescriptor inputDescriptor,
            TypeInfo expectedType)
            throws Exception {

        final EvaluatorDescriptor childDescriptor = this.composeEvaluator(
                (CommonTree) expression.getChild(0), inputDescriptor, TypeInfoImpl.BOOLEAN);

        final EvaluatorDescriptor d = new EvaluatorDescriptor(
                new NotEvaluator(childDescriptor.getEvaluator()),
                TypeInfoImpl.BOOLEAN);

        d.addUsedColumnNames(childDescriptor);

        return this.convert(d, expectedType);
    }


    private EvaluatorDescriptor processPRIMARY_EXPRESSION(
            CommonTree pe,
            TupleInfoDescriptor inputDescriptor,
            TypeInfo expectedType)
            throws Exception {

        final CommonTree prefixChild = (CommonTree) pe.getFirstChildWithType(RulesParser.PREFIX).getChild(0);
        final CommonTree suffixes = (CommonTree) pe.getFirstChildWithType(RulesParser.SUFFIXES);

        final EvaluatorDescriptor prefixDescriptor = (null == prefixChild)
                ? null
                : this.composeEvaluator(prefixChild, inputDescriptor, expectedType);

        if (0 == suffixes.getChildCount()) {
            return prefixDescriptor;
        }

        CommonTree firstSuffix = (CommonTree) suffixes.getChild(0);
        switch (firstSuffix.getType()) {

            case RulesParser.ARRAY_ACCESS_SUFFIX: {
                return processARRAY_ACCESS_SUFFIX(prefixDescriptor, firstSuffix, inputDescriptor, expectedType);
            }

            //todo other suffix cases
        }

        throw new UnsupportedOperationException("Suffix case TBD");
    }


    private EvaluatorDescriptor processQUALIFIED_NAME(
            CommonTree expression,
            TupleInfoDescriptor inputDescriptor,
            TypeInfo expectedType)
            throws Exception {

        final EvaluatorDescriptor descriptorQualifier = this.composeEvaluator(
                (CommonTree) expression.getChild(0), inputDescriptor);
        if (null == descriptorQualifier) {
            throw new Exception("Name qualifier not understood: " + expression.getChild(0));
        }

        String name = expression.getChild(1).getChild(0).getText();

        final TypeInfo qualifierTypeInfo = descriptorQualifier.getTypeInfo();
        final Class qualifierClass = this.getRuntimeClass(qualifierTypeInfo);

        final EvaluatorDescriptor d;

        if (Event.class.isAssignableFrom(qualifierClass)) {        	
            d = new EvaluatorDescriptor(
                    new SimpleEventPropertyValueEvaluator(descriptorQualifier.getEvaluator(), name),
                    expectedType);
        }
        else if (com.tibco.cep.runtime.model.element.Concept.class.isAssignableFrom(qualifierClass)) {
//        	final Class dtClass = qualifierTypeInfo.getDesigntimeClass();
//            final Ontology ontology = this.projectContext.getOntology();       
        	final Object o = qualifierTypeInfo.getBeNameSpaceObject();
        	final Concept c;
        	if (o instanceof Concept) {
        		c = (Concept) o;
        	} else if (o instanceof PropertyDefinition) {
        		c = ((PropertyDefinition) o).getConceptType();
        	} else {
                throw new Exception("Unsupported type for qualifier '" + name + "': " + o);
        	}
            final PropertyDefinition propDef = c.getPropertyDefinition(name, false);
        	if (null == propDef) {
        		throw new Exception ("Could not resolve concept property: " + name);
        	}        	
        	final TypeInfo expressionTypeInfo = new TypeInfoImpl(propDef);
        	
            final TypeDescriptor qualifierTypeDescriptor = this.typeManager.getTypeDescriptor(qualifierClass);
            
            if (expressionTypeInfo.isPropertyArray()) {
                d = new EvaluatorDescriptor(
                        new ConceptPropertyArrayEvaluator(descriptorQualifier.getEvaluator(), name),
                        expressionTypeInfo);
            }
            else if (expressionTypeInfo.isPropertyAtom()) {
            	if ((expectedType != null) && (expectedType.isPropertyAtom())) { 
            		d = new EvaluatorDescriptor(
                        new ConceptPropertyAtomEvaluator(descriptorQualifier.getEvaluator(), name),
                        expressionTypeInfo);
            	} else {
            		d = new EvaluatorDescriptor(
                            new ConceptPropertyAtomValueEvaluator(descriptorQualifier.getEvaluator(), name),
                            expressionTypeInfo);            		
            	}
            } else {
                d = null;
            }
        }
//        todo other package notation e.g. function
//        else if () {
//        }
        else {
            d = null;
        }

        if (null == d) {
            throw new Exception("QUALIFIED_NAME TBD: " + expression.getText());
        }

        d.addUsedColumnNames(descriptorQualifier);

        return this.convert(d, expectedType);
    }


    private EvaluatorDescriptor processSIMPLE_NAME( //todo
            CommonTree expression,
            TupleInfoDescriptor inputDescriptor,
            TypeInfo expectedType)
            throws Exception {

//        Expression e = null; //todo get from expression, remove dependency on query
//
//        if (null == e) {
//            return null;
//        }


    	expression = (CommonTree) expression.getChild(0); 
        // Looks in every column that contains a Tuple
        int columnIndex = 0;
        for (final TupleInfoColumnDescriptor columnDescriptor : inputDescriptor.getColumns()) {

            final TupleInfoDescriptor containedTupleDescriptor = columnDescriptor.getTupleInfoDescriptor();
            if (null != containedTupleDescriptor) {
                // This column contains a tuple.

                final TupleExtractorDescriptor found = this.composeExtractorFromTuple(expression,
                        containedTupleDescriptor);
                if (null != found) {
                    // The data can be extracted from the column tuple.

                    final ExpressionEvaluator adapter = new TupleExtractorToEvaluatorAdapter(
                            found.getTupleExtractor(), columnDescriptor.getName());
                    final EvaluatorDescriptor result = new EvaluatorDescriptor(adapter, found.getTypeInfo());
                    result.addUsedColumnName(columnDescriptor.getName(), columnDescriptor.getClassName());
                    return result;

                }
            }
            columnIndex++;
        }

        return null;
    }


    private EvaluatorDescriptor processStringLiteral(
            CommonTree expression,
            TypeInfo expectedType)
            throws Exception {

        final TypedValue v = this.stringLiteralFactory.make(expression.getText());
        return this.convert(
                new EvaluatorDescriptor(new ConstantValueEvaluator(v.getValue()), v.getType()),
                expectedType);
    }


    private EvaluatorDescriptor processTRUE(
            TypeInfo expectedType)
            throws Exception {

        return this.convert(
                new EvaluatorDescriptor(ConstantValueEvaluator.TRUE, TypeInfoImpl.BOOLEAN),
                expectedType);
    }


}

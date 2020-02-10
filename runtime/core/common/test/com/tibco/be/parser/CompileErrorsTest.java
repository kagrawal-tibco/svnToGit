package com.tibco.be.parser;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Aug 5, 2004
 * Time: 7:03:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class CompileErrorsTest {
    public static void main(String[] args) {
        System.out.println(CompileErrors.syntaxError(new ParseException(Token.newToken(12, "image"), new int[][]{new int[]{2}}, RuleGrammarConstants.tokenImage)));
        System.out.println(CompileErrors.unknownTypeName());
        System.out.println(CompileErrors.longLiteralOutOfRange("literal"));
        System.out.println(CompileErrors.intLiteralOutOfRange("literal"));
        System.out.println(CompileErrors.unaryOpIncompatible("op", "type"));
        System.out.println(CompileErrors.unknownProperty("propertyName", "parentTypeName"));
        System.out.println(CompileErrors.unknownAttribute("attributeName", "parentTypeName"));
        System.out.println(CompileErrors.unknownIdentifier("identifier"));
        System.out.println(CompileErrors.binaryOpIncompatible("op", "lhsType", "rhsType"));
        System.out.println(CompileErrors.functionNotFound("functionName"));
        System.out.println(CompileErrors.functionSignatureMismatch("fnName", "fnArgs", "userArgs"));
        System.out.println(CompileErrors.propertyOfAttributeInCondition());
        System.out.println(CompileErrors.attributeOfAttributeInCondition());
        System.out.println(CompileErrors.attributeOfAttributeInNonActionOnlyRuleFunction());
        System.out.println(CompileErrors.attributeOfAttributeInNonActionOnlyRuleFunction());
        System.out.println(CompileErrors.propertyOfConceptReferenceInCondition());
        System.out.println(CompileErrors.propertyOfConceptReferenceInNonActionOnlyRuleFunction());
        System.out.println(CompileErrors.predicateExpected());
        System.out.println(CompileErrors.statementExpected());
        System.out.println(CompileErrors.functionNotAllowedInAction("fnName"));
        System.out.println(CompileErrors.functionNotAllowedInCondition("fnName"));
        System.out.println(CompileErrors.functionNotAllowedInNonActionOnlyRuleFunction("fnName"));
        System.out.println(CompileErrors.functionNotAllowedInRuleFunction("fnName"));
        System.out.println(CompileErrors.noBETypeForFunctionArgumentType("typeName"));
        System.out.println(CompileErrors.noBETypeForFunctionReturnType("typeName"));
        System.out.println(CompileErrors.exceptionNotAllowed());
        System.out.println(CompileErrors.incompatibleTypes("requiredTypeName", "foundTypeName"));
        System.out.println(CompileErrors.propertyAssignmentNotAllowedInNonActionOnlyRuleFunction());
        System.out.println(CompileErrors.declaredTypeUnknown());
        System.out.println(CompileErrors.identifierAlreadyDefined("id"));
        System.out.println(CompileErrors.loopOnlyStatementNotExpected("break"));
        System.out.println(CompileErrors.couldntResolveName("unresolvable.name"));
        System.out.println(CompileErrors.unexpectedArrayLiteral());
        System.out.println(CompileErrors.multiDimensionalArraysNotSupported());
        System.out.println(CompileErrors.nonEntityInstanceof());
        System.out.println(CompileErrors.instanceofNotPossible("castFrom", "castTo"));
        
        System.out.println(CompileErrors.InternalErrors.intLiteralRangeCheckNumberFormatException("literal"));
        System.out.println(CompileErrors.InternalErrors.intLiteralRangeCheckNullPointerException("literal"));
        System.out.println(CompileErrors.InternalErrors.unexpectedNullRelation("tokenImage"));
        System.out.println(CompileErrors.InternalErrors.unaryRHSNull());
        System.out.println(CompileErrors.InternalErrors.unexpectedUnaryOperator("operator"));
        System.out.println(CompileErrors.InternalErrors.productionNodeNullToken());
        System.out.println(CompileErrors.InternalErrors.binaryLHSNull(null));
        System.out.println(CompileErrors.InternalErrors.binaryRHSNull("token"));
    }
}
package com.tibco.be.parser;

import com.tibco.be.util.BEResourceBundle;
/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Jul 27, 2004
 * Time: 2:42:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class CompileErrors {
    public static BEResourceBundle bundle = new BEResourceBundle("com.tibco.be.parser.compiler_error_messages");
    
    public static String syntaxError(ParseException pe) {
        int[][] sequences = pe.expectedTokenSequences;
        if(sequences == null || sequences.length <= 0) {
            return bundle.getString("UnknownError");
        }
        int min = sequences[0].length;
        int minIndex = 0;
        for(int ii = 1; ii < sequences.length; ii++) {
            if(sequences[ii].length < min) {
                min = sequences[ii].length;
                minIndex = ii;
            }
        }
        if(sequences[minIndex].length <=0) {
            return bundle.getString("UnknownError");
        } else if(sequences[minIndex].length > 1) {
            StringBuffer sb = new StringBuffer();
            int[] tokens = sequences[minIndex];
            for(int ii = 0; ii < tokens.length; ii++) {
                if(ii > 0) sb.append(", ");
                sb.append(RuleGrammarConstants.tokenImage[tokens[ii]]);
            }
            return bundle.formatString("OneOfManyTokensExpected", sb.toString());
        } else {
            return bundle.formatString("OneTokenExpected", RuleGrammarConstants.tokenImage[sequences[minIndex][0]]);
        }
    }
        
    /*
    * Semantic Errors
    */
    
    //this is used by NodeType to give a name to the UNKNOWN type
    public static String unknownTypeName() {
        return bundle.getString("UnknownTypeName");
    }
    
    public static String longLiteralOutOfRange(String literal) {
        return bundle.formatString("LongLiteralOutOfRange", literal);
    }
    
    public static String intLiteralOutOfRange(String literal) {
        return bundle.formatString("IntLiteralOutOfRange", literal);
    }
    
    public static String unaryOpIncompatible(String op, String type) {
        return bundle.formatString("UnaryOpIncompatible", op, type);
    }
    
    public static String unknownProperty(String propertyName, String parentTypeName) {
        return bundle.formatString("UnknownProperty", propertyName, parentTypeName);
    }
    
    public static String unknownAttribute(String attributeName, String parentTypeName) {
        return bundle.formatString("UnknownAttribute", attributeName, parentTypeName);
    }
    
    public static String unknownIdentifier(String identifier) {
        return bundle.formatString("UnknownIdentifier", identifier);
    }
    
    public static String binaryOpIncompatible(String op, String lhsType, String rhsType) {
        return bundle.formatString("BinaryOpIncompatible", op, lhsType, rhsType);
    }

    public static String RangeType() {
        return bundle.getString("RangeType");
    }

    public static String SetMembershipType() {
        return bundle.getString("SetMembershipType");
    }
    
    public static String functionNotFound(String functionName) {
        return bundle.formatString("FunctionNotFound", functionName);
    }
    
    public static String functionSignatureMismatch(String functionName, String functionArgs, String userArgs) {
        return bundle.formatString("FunctionSignatureMismatch", functionName, functionArgs, userArgs);
    }
    
    public static String immutableAssignmentTarget() {
        return bundle.getString("ImmutableAssignmentTarget");
    }
    
    public static String assignmentTypeMismatch(String targetType, String sourceType) {
        return bundle.formatString("AssignmentTypeMismatch", targetType, sourceType);
    }
    
    public static String noJavaFilesToCompile() {
        return bundle.getString("NoJavaFilesToCompile");
    }
    
    public static String propertyOfAttributeInCondition() {
        return bundle.getString("PropertyOfAttributeInCondition");
    }
    
    public static String attributeOfAttributeInCondition() {
        return bundle.getString("AttributeOfAttributeInCondition");
    }
    
    public static String propertyOfAttributeInNonActionOnlyRuleFunction() {
        return bundle.getString("PropertyOfAttributeInNonActionOnlyRuleFunction");
    }
    
    public static String attributeOfAttributeInNonActionOnlyRuleFunction() {
        return bundle.getString("AttributeOfAttributeInNonActionOnlyRuleFunction");
    }
    
    public static String propertyOfConceptReferenceInCondition() {
        return bundle.getString("PropertyOfConceptReferenceInCondition");
    }
    
    public static String propertyOfConceptReferenceInNonActionOnlyRuleFunction() {
        return bundle.getString("PropertyOfConceptReferenceInNonActionOnlyRuleFunction");
    }
    
    public static String predicateExpected() {
        return bundle.getString("PredicateExpected");
    }
    
    public static String statementExpected() {
        return bundle.getString("StatementExpected");
    }
    
    public static String functionNotAllowedInCondition(String name) {
        return bundle.formatString("FunctionNotAllowedInCondition", name);
    }
    
    public static String functionNotAllowedInAction(String name) {
        return bundle.formatString("FunctionNotAllowedInAction", name);
    }
    
    public static String functionNotAllowedInNonActionOnlyRuleFunction(String name) {
        return bundle.formatString("FunctionNotAllowedInNonActionOnlyRuleFunction", name);
    }

    public static String functionNotAllowedInQueryRuleFunction(String name) {
        return bundle.formatString("FunctionNotAllowedInQueryRuleFunction",name);
    }
    
    public static String functionNotAllowedInRuleFunction(String name) {
        return bundle.formatString("FunctionNotAllowedInRuleFunction", name);
    }
    
    public static String noBETypeForFunctionReturnType(String typeName) {
        return bundle.formatString("NoBETypeForFunctionReturnType", typeName);
    }
    
    public static String noBETypeForFunctionArgumentType(String typeName) {
        return bundle.formatString("NoBETypeForFunctionArgumentType", typeName);
    }
    
    public static String exceptionNotAllowed() {
        return bundle.getString("ExceptionNotAllowed");
    }
    
    public static String incompatibleTypes(String requiredTypeName, String foundTypeName) {
        return bundle.formatString("IncompatibleTypes", requiredTypeName, foundTypeName);
    }
    
    public static String propertyAssignmentNotAllowedInNonActionOnlyRuleFunction() {
        return bundle.getString("PropertyAssignmentNotAllowedInNonActionOnlyRuleFunction");
    }
    
    public static String declaredTypeUnknown() {
        return bundle.getString("DeclaredTypeUnknown");
    }
    
    public static String identifierAlreadyDefined(String id) {
        return bundle.formatString("IdentifierAlreadyDefined", id);
    }

    public static String loopOnlyStatementNotExpected(String keyword) {
        return bundle.formatString("LoopOnlyStatementNotExpected", keyword);
    }
    
    public static String couldntResolveName(String name) {
        return bundle.formatString("CouldntResolveName", name);
    }
    
    public static String unexpectedArrayLiteral() {
        return bundle.getString("UnexpectedArrayLiteral");
    }
    
    public static String multiDimensionalArraysNotSupported() {
        return bundle.getString("MultiDimensionalArraysNotSupported");
    }
    
    public static String incompatibleInstanceofLHS(String s) {
        return bundle.formatString("IncompatibleInstanceofLHS", s);
    }
    
    public static String instanceofNotPossible(String castFromTypeName, String castToTypeName) {
        return bundle.formatString("InstanceofNotPossible", castFromTypeName, castToTypeName);
    }
    
    public static String processInstanceof() {
    	return bundle.getString("ProcessInstanceof");
    }

    /**
     * Putting internal errors in a separate class is mostly for organizational purposes
     */ 
    public static class InternalErrors {
        public static String intLiteralRangeCheckNumberFormatException(String literal) {
            return bundle.formatString("IntLiteralRangeCheckNumberFormatException", literal);
        }
        
        public static String intLiteralRangeCheckNullPointerException(String literal) {
            return bundle.formatString("IntLiteralRangeCheckNullPointerException", literal);
        }
        
        public static String unexpectedNullRelation(String tokenImage) {
            return bundle.formatString("UnexpectedNullRelation", tokenImage);
        }
        
        public static String unaryRHSNull() {
            return bundle.getString("UnaryRHSNull");
        }
        
        public static String unexpectedUnaryOperator(String operator) {
            return bundle.formatString("UnexpectedUnaryOperator", operator);
        }
        
        public static String productionNodeNullToken() {
            return bundle.getString("ProductionNodeNullToken");
        }

        public static String binaryLHSNull(String token) {
            return bundle.formatString("BinaryLHSNull", token);
        }
        
        public static String binaryRHSNull(String token) {
            return bundle.formatString("BinaryRHSNull", token);
        }
        
        public static String unexpectedBinaryOperator(String operator) {
            return bundle.formatString("UnexpectedBinaryOperator", operator);
        }
    }
}

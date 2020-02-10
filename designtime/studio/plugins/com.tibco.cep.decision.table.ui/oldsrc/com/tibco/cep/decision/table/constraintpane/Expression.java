package com.tibco.cep.decision.table.constraintpane;

import static com.tibco.cep.decision.table.constraintpane.DecisionTableAnalyzerUtils.canonicalizeExpression;
import static com.tibco.cep.decision.table.constraintpane.Operator.AND_OP;
import static com.tibco.cep.decision.table.constraintpane.Operator.DONT_CARE_OPERATOR;
import static com.tibco.cep.decision.table.constraintpane.Operator.OR_OP;
import static com.tibco.cep.decision.table.constraintpane.Operator.RANGE_OPERATOR;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Aug 2, 2008
 * Time: 5:06:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class Expression {

    byte operatorKind;
    byte rangeKind;
    String expr;

    Object[] operands = new Object[2];
    
    /**
     * This is required to maintain what is the range kind for each operand
     */
    Map<String, Byte> operandRangeKinds = new HashMap<String, Byte>(2);

    public Expression(String expr) {
        this.expr = expr;
    }

    public Expression parse() throws Exception {
        if (null == expr) {
            operatorKind = Operator.DONT_CARE_OPERATOR;
            return this;
        }

        expr = expr.trim();

        if (expr.startsWith("\"")) expr = expr.substring(1, expr.length() - 1);
        if (expr.length() == 0) {
            operatorKind = DONT_CARE_OPERATOR;
            return this;
        }

        if (((expr.lastIndexOf(AND_OP) == -1) 
        		|| (expr.lastIndexOf(OR_OP) == -1))
                && (expr.lastIndexOf(">") == -1)
                && (expr.lastIndexOf("<") == -1)
                ) {
            //either a function call or constant
            parseFunctionOrConstant();
        }
        else {
            operatorKind = RANGE_OPERATOR;
            parseRange();
        }
        return this;
    }

    private void parseRange() {
        String[] rangeElements = new String[] { expr};
        boolean isBounded = false;
        if (expr.lastIndexOf(AND_OP) != -1) {
            rangeElements = expr.split(AND_OP);
        } else if (expr.lastIndexOf(OR_OP) != -1) {
        	String orOp = "\\|\\|";
            rangeElements = expr.split(orOp);
        }
        isBounded = true;
        parseBoundedRange(rangeElements, isBounded);
    }

    private void parseBoundedRange(String[] rangeElements, boolean bounded) {
    	rangeKind = Operator.RANGE_BOUNDED; // always a bounded range
    	operands[0] = Integer.MIN_VALUE; 
    	operands[1] = Integer.MAX_VALUE; 
    	for (String rangeEle : rangeElements) {
    		rangeEle = canonicalizeExpression(rangeEle.trim());
    		char opChar = rangeEle.charAt(0);
    		switch (opChar) {
    		case '>': {
    			int index = 2;
    			boolean added = true;
    			try{
    				//to handle the situation like >=1 && <= 10 && >=5  , the range should be [1, 5]
    				if(operands[0].equals(Integer.MIN_VALUE) || Integer.parseInt(operands[0].toString()) > Integer.parseInt(rangeEle.substring(index).trim()) ){    	    			    				
	    				operands[0] = Integer.parseInt(rangeEle.substring(index).trim());	
    				} else{
    					added = false;
    				}
	    		} catch (NumberFormatException nfeInteger) {
    				try { // try parsing it to Double
    					Double elem = Double.parseDouble(rangeEle.substring(index).trim());
    					if (elem > Integer.MAX_VALUE) {
    						operands[0] = Integer.MAX_VALUE - 1; // use a lesser value to represent it in rangeMap keySet
    					} else if (elem < Integer.MIN_VALUE) {
    						operands[0] = Integer.MIN_VALUE + 1; // use a greater value to represent it in rangeMap keySet
    					} else {
    						operands[0] = elem.intValue();
    						
    					}
    				} catch(NumberFormatException nfeDouble) {	
    					operands[0] = rangeEle.substring(index).trim();
    				}
	    		}
	    		
    			// 
    			if(added){
    				if (rangeEle.charAt(1) != '=') {
	    				operandRangeKinds.put(rangeEle, Operator.RANGE_MIN_EXCL_BOUNDED);
	    				rangeKind = (byte) (rangeKind + Operator.RANGE_MIN_EXCL_BOUNDED);
	    				index = 1;
	    			} else {
	    				operandRangeKinds.put(rangeEle, Operator.RANGE_BOUNDED);
	    			}
	    				
    			}
    		}
    		break;
    		case '<': {
    			int index = 2;
    			boolean added = true;    			
    			try{    					
	    			if(operands[0].equals(Integer.MAX_VALUE) || Integer.parseInt(operands[0].toString()) < Integer.parseInt(rangeEle.substring(index).trim()) ){
	    				operands[1] = Integer.parseInt(rangeEle.substring(index).trim());
	    			} else{
	    				added = false;
	    			}
    			} catch (NumberFormatException nfe) {
    				try { // try parsing it to Double
    					Double elem = Double.parseDouble(rangeEle.substring(index).trim());
    					if (elem > Integer.MAX_VALUE) {
    						operands[1] = Integer.MAX_VALUE - 1; // use a lesser value to represent it in rangeMap keySet
    					} else if (elem < Integer.MIN_VALUE) {
    						operands[1] = Integer.MIN_VALUE + 1;// use a greater value to represent it in rangeMap keySet
    					} else {
    						operands[1] = elem.intValue();
    					}
    				} catch(NumberFormatException nfeDouble) {
    					operands[1] = rangeEle.substring(index).trim();
    				}
    			}
    			if(added){
	    			if (rangeEle.charAt(1) != '=') {
	    				operandRangeKinds.put(rangeEle, Operator.RANGE_MAX_EXCL_BOUNDED);
	    				rangeKind = (byte) (rangeKind + Operator.RANGE_MAX_EXCL_BOUNDED);
	    				index = 1;
	    			} else {
	    				operandRangeKinds.put(rangeEle, Operator.RANGE_BOUNDED);
	    			}
    			}
    		}
    		break;
    		}
    	}
    }

    /**
     * TODO Handle ranges
     */
    private void parseFunctionOrConstant() {
        if (expr.lastIndexOf("(") == -1) {
        	char opChar = expr.charAt(0);
        	int index = -1;
        	switch (opChar) {
	        	case '!' :
	        		index = 0;
	        		if (expr.charAt(1) == '=') {
	        			operatorKind = Operator.NOTEQ_OPERATOR;
	        			index = 1;
	        		}
	        		break;
	        	default :
	        		operatorKind = Operator.EQUALS_OPERATOR;
        	}
        	String massagedExpr = (index == -1) ? expr : /** Without the operator **/expr.substring(index + 1).trim();
        	if ((operatorKind == Operator.NOTEQ_OPERATOR) && massagedExpr.startsWith("\"")) {
        		massagedExpr = massagedExpr.substring(1, massagedExpr.length() - 1);
        	}	
        	operands[0] = canonicalizeExpression(massagedExpr.split("\"")[0]);
        }
        else {
            operatorKind = Operator.EQUALS_OPERATOR;
            //Create a single operand
            operands[0] = canonicalizeExpression(expr.split(";")[0]);
        }
    }

    public String toString() {
        return super.toString();
    }


//    public static void main(String[] args) {
//        try {
////            Expression e = new Expression("> System.debugOut(\"Hello\")").parse();
//            Expression e = new Expression("AA").parse();
//            System.out.println("e is " + e);
//
////            Expression e1 = new Expression(">20").parse();
////            Expression e2 = new Expression("<20").parse();
////            Expression e3 = new Expression(">20 && <30").parse();
////            Expression e4 = new Expression(">=20 && <= 30").parse();
////            Expression e5 = new Expression("< 20 && >= 10").parse();
//
//
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

}


package com.tibco.cep.runtime.service.decision;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Aug 2, 2008
 * Time: 5:06:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class Expression {

    //static String INTEGER_MAX_VALUE =  new Integer(Integer.MAX_VALUE).toString(); //everything is a string
    //static String INTEGER_MIN_VALUE =  new Integer(Integer.MIN_VALUE).toString(); //everything is a string
    byte operatorKind;
    byte rangeKind;
    String expr;

    Object[] operands = new Object[2];

    public Expression(String expr) {
        this.expr = expr;
    }

    public Expression parse() throws Exception {
        if (null == expr) {
            operatorKind = Operator.DONT_CARE_OPERATOR;
            return this;
        }

        expr = expr.trim();

        if (expr.startsWith("\"")) expr = expr.substring(1,expr.length()-1);
        if (expr.length() == 0) {
            operatorKind = Operator.DONT_CARE_OPERATOR;
            return this;
        }

        if ((expr.lastIndexOf("&&") == -1)
                && (expr.lastIndexOf(">") == -1)
                && (expr.lastIndexOf("<") == -1)
                )
        {
            //either a function call or constant
            parseFunctionOrConstant();
        }
        else {
            operatorKind = Operator.RANGE_OPERATOR;
            parseRange();
        }

        return this;

    }

    private void parseRange() {
        String[] rangeElements = new String[] { expr};
        boolean isBounded = false;
        if (expr.lastIndexOf("&&") != -1) {
            rangeElements = expr.split("&&");
            isBounded = true;
        }

        parseBoundedRange(rangeElements, isBounded);

    }

    private void parseBoundedRange(String[] rangeElements, boolean bounded) {
        rangeKind = Operator.RANGE_BOUNDED; //always a bounded range
        operands[0] = Integer.MIN_VALUE ; //INTEGER._MIN_VALUE;
        operands[1] = Integer.MAX_VALUE ; //INTEGER_MAX_VALUE;
        for (String rangeEle : rangeElements) {
            rangeEle = rangeEle.trim();
            char opChar = rangeEle.charAt(0);
            switch (opChar) {
                case '>' :
                {
                    int index = 2;
                    if (rangeEle.charAt(1) != '=') {
                        rangeKind = (byte)(rangeKind + Operator.RANGE_MIN_EXCL_BOUNDED);
                        index = 1;
                    }
                    operands[0] = Integer.parseInt(rangeEle.substring(index));
                }
                break;
                case '<':
                {

                    int index = 2;
                    if (rangeEle.charAt(1) != '=') {
                        rangeKind = (byte)(rangeKind + Operator.RANGE_MAX_EXCL_BOUNDED);
                        index = 1;
                    }
                    operands[1] = Integer.parseInt(rangeEle.substring(index));
                }
                break;

            }
        }
    }


    private void parseFunctionOrConstant() {
        if (expr.lastIndexOf("(") == -1) {
            operatorKind = Operator.EQUALS_OPERATOR;
            operands[0] = expr.split("\"")[0];
        }
        else {
            operatorKind = Operator.FUNCTION_OPERATOR;
            //todo function operator;
        }
    }

    public String toString() {
        return super.toString();
    }


//    public static void main(String[] args) {
//        try {
//            Expression e = new Expression("AA").parse();
//            System.out.println("e is " + e);
//
//            Expression e1 = new Expression(">20").parse();
//            Expression e2 = new Expression("<20").parse();
//            Expression e3 = new Expression(">20 && <30").parse();
//            Expression e4 = new Expression(">=20 && <= 30").parse();
//            Expression e5 = new Expression("< 20 && >= 10").parse();
//
//
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

}

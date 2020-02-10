package com.tibco.cep.query.model.impl;

import java.util.HashMap;

import com.tibco.cep.query.model.Operator;
import com.tibco.cep.query.model.TypeNames;



/**
 * @author pdhar
 *
 */
public abstract class AbstractOperator implements Operator {

    protected int opMask;
    protected int opType;

    private static final HashMap<Integer,String> oqlOpMap = new HashMap<Integer,String>();
    static {
        oqlOpMap.put(OP_VOID, TypeNames.VOID);
        oqlOpMap.put(OP_PLUS,TypeNames.PLUS);
        oqlOpMap.put(OP_ARRAY,TypeNames.ARRAY);
        oqlOpMap.put(OP_RANGE,TypeNames.RANGE);
        oqlOpMap.put(OP_UMINUS,TypeNames.UNARY_MINUS);
        oqlOpMap.put(OP_MINUS,TypeNames.MINUS);
        oqlOpMap.put(OP_ABS,TypeNames.ABS);
        oqlOpMap.put(OP_NOT,TypeNames.NOT);
        oqlOpMap.put(OP_OR,TypeNames.OR);
        oqlOpMap.put(OP_AND,TypeNames.AND);
        oqlOpMap.put(OP_EQ,TypeNames. EQ);
        oqlOpMap.put(OP_NE,TypeNames.NE);
        oqlOpMap.put(OP_GT,TypeNames.GT);
        oqlOpMap.put(OP_LT,TypeNames.LT);
        oqlOpMap.put(OP_GE,TypeNames.GE);
        oqlOpMap.put(OP_LE,TypeNames.LE);
        oqlOpMap.put(OP_CONCAT,TypeNames.CONCAT);
        oqlOpMap.put(OP_AT,TypeNames.AT);
        oqlOpMap.put(OP_MOD,TypeNames.MOD);
        oqlOpMap.put(OP_LIKE,TypeNames.LIKE);
        oqlOpMap.put(OP_BETWEEN,TypeNames.BETWEEN);
        oqlOpMap.put(OP_EXISTS,TypeNames.EXISTS);
        oqlOpMap.put(OP_IN,TypeNames. IN);
        oqlOpMap.put(OP_DOT,TypeNames.DOT);
        oqlOpMap.put(OP_CONCAT,TypeNames.CONCAT);
        oqlOpMap.put(OP_GROUP,TypeNames.PARENTHESES);
        oqlOpMap.put(OP_MULTIPLY, TypeNames.MULTIPLY);
        oqlOpMap.put(OP_DIVIDE, TypeNames.DIVIDE);
        oqlOpMap.put(OP_CAST, TypeNames.CAST);
    }

    private static final HashMap<Integer,String> javaOpMap = new HashMap<Integer,String>();
    static {
    	javaOpMap.put(OP_VOID,TypeNames.VOID);
        javaOpMap.put(OP_PLUS,TypeNames.PLUS);
        javaOpMap.put(OP_MINUS,TypeNames.MINUS);
        javaOpMap.put(OP_NOT,TypeNames.JAVA_NOT);
        javaOpMap.put(OP_OR,TypeNames.JAVA_OR);
        javaOpMap.put(OP_AND,TypeNames.JAVA_AND);
        javaOpMap.put(OP_EQ,TypeNames. JAVA_EQ);
        javaOpMap.put(OP_NE,TypeNames.JAVA_NE);
        javaOpMap.put(OP_GT,TypeNames.JAVA_GT);
        javaOpMap.put(OP_LT,TypeNames.JAVA_LT);
        javaOpMap.put(OP_GE,TypeNames.JAVA_GE);
        javaOpMap.put(OP_LE,TypeNames.JAVA_LE);
        javaOpMap.put(OP_MOD,TypeNames.JAVA_MOD);
        javaOpMap.put(OP_DOT,TypeNames.JAVA_REF);
        javaOpMap.put(OP_ARRAY,TypeNames.ARRAY);
        javaOpMap.put(OP_MULTIPLY, TypeNames.MULTIPLY);
        javaOpMap.put(OP_DIVIDE, TypeNames.DIVIDE);    	
    }


    protected AbstractOperator(int opMask, int opType) {
        this.opMask = opMask;
        this.opType = opType;      
        if(oqlOpMap.get(opType) != null) {
        	opMask |= OP_MASK_OQL;
        }
        if(javaOpMap.get(opType) != null) {
        	opMask |= OP_MASK_JAVA;
        }
    }


    public int getOpMask() {
        return opMask;
    }


    public void setOpMask(int opMask) {
        this.opMask = opMask;
    }


    public int getOpType() {
        return opType;
    }


    public void setOpType(int opType) {
        this.opType = opType;
    }


    /**
     * @param mask int the Operator mask
     * @return int[] the array of valid operators for the given context;
     */
    public int[] getValidOps(int mask) {
        switch(mask) {
            case OP_MASK_UNARY:
                return UNARY_OP_TYPES;

            case OP_MASK_BINARY:
                return BINARY_OP_TYPES;

            case OP_MASK_LOGICAL:
                return LOGICAL_OP_TYPES;

            case OP_MASK_RELATIONAL:
                return RELATIONAL_OP_TYPES;

            case OP_MASK_SIMPLE:
                return SIMPLE_OP_TYPES;

            case OP_MASK_COMPLEX:
                return COMPLEX_OP_TYPES;

            default:
                return null;
        }
    }


    /**
     * @param operator
     * @return int the masks this operator can have
     */
    public static int getValidMask(int operator) {
        int mask = 0;
        for(int i=0; i<UNARY_OP_TYPES.length;i++) {
           if(UNARY_OP_TYPES[i] == operator){
               mask |= OP_MASK_UNARY;
           }
        }
        for(int i=0; i<BINARY_OP_TYPES.length;i++) {
           if(BINARY_OP_TYPES[i] == operator){
               mask |= OP_MASK_BINARY;
           }
        }
        for(int i=0; i<LOGICAL_OP_TYPES.length;i++) {
           if(LOGICAL_OP_TYPES[i] == operator){
               mask |= OP_MASK_LOGICAL;
           }
        }
        for(int i=0; i<RELATIONAL_OP_TYPES.length;i++) {
           if(RELATIONAL_OP_TYPES[i] == operator){
               mask |= OP_MASK_RELATIONAL;
           }
        }
        for(int i=0; i<SIMPLE_OP_TYPES.length;i++) {
           if(SIMPLE_OP_TYPES[i] == operator){
               mask |= OP_MASK_SIMPLE;
           }
        }
        for(int i=0; i<COMPLEX_OP_TYPES.length;i++) {
           if(COMPLEX_OP_TYPES[i] == operator){
               mask |= OP_MASK_COMPLEX;
           }
        }
        if(oqlOpMap.get(operator) != null) {
        	mask |= OP_MASK_OQL;
        }
        if(javaOpMap.get(operator) != null) {
        	mask |= OP_MASK_JAVA;
        }
        return mask;
    }

    
    /* (non-Javadoc)
     * @see com.tibco.cep.query.ast.Operator#getJavaOperator()
     */
    public String getJavaOperator() {
        if ((this.opMask & OP_MASK_SIMPLE) == OP_MASK_SIMPLE) {
                switch(this.opType) {
                    case OP_PLUS:       return TypeNames.JAVA_PLUS;
                    case OP_MINUS:      return TypeNames.JAVA_MINUS;
                    case OP_MULTIPLY:   return TypeNames.JAVA_MULTIPLY;
                    case OP_DIVIDE:     return TypeNames.JAVA_DIVIDE;
                    case OP_NOT:        return TypeNames.JAVA_NOT;
                    case OP_AND:        return TypeNames.JAVA_AND;
                    case OP_OR:         return TypeNames.JAVA_OR;
                    case OP_EQ:         return TypeNames.JAVA_EQ;
                    case OP_NE:         return TypeNames.JAVA_NE;
                    case OP_GT:         return TypeNames.JAVA_GT;
                    case OP_LT:         return TypeNames.JAVA_LT;
                    case OP_GE:         return TypeNames.JAVA_GE;
                    case OP_LE:         return TypeNames.JAVA_LE;
                    case OP_CONCAT:     return TypeNames.JAVA_CONCAT;
                    case OP_DOT:        return TypeNames.JAVA_REF;
                    case OP_ARRAY:		return TypeNames.ARRAY;
                    case OP_MOD:		return TypeNames.JAVA_MOD;
                }
        }
        return null;
    }


    /* (non-Javadoc)
	 * @see com.tibco.cep.query.ast.Operator#getOqlOperator()
	 */
	public String getOqlOperator() {
		switch(this.opMask) {
	        case OP_MASK_SIMPLE:
	            switch(this.opType) {
	                case OP_PLUS:       return TypeNames.PLUS;
	                case OP_MINUS:      return TypeNames.MINUS;
	                case OP_MULTIPLY:   return TypeNames.MULTIPLY;
	                case OP_DIVIDE:     return TypeNames.DIVIDE;
	                case OP_NOT:        return TypeNames.NOT;
	                case OP_AND:        return TypeNames.AND;
	                case OP_OR:         return TypeNames.OR;
	                case OP_EQ:         return TypeNames.EQ;
	                case OP_NE:         return TypeNames.NE;
	                case OP_GT:         return TypeNames.GT;
	                case OP_LT:         return TypeNames.LT;
	                case OP_GE:         return TypeNames.GE;
	                case OP_LE:         return TypeNames.LE;
	                case OP_CONCAT:     return TypeNames.CONCAT;
	                case OP_DOT:        return TypeNames.DOT;
	                case OP_ARRAY:		return TypeNames.ARRAY;
	                case OP_MOD:		return TypeNames.MOD;
	                default:   			break;
	            }
	        case OP_MASK_COMPLEX:  
	        	switch(this.opType) {
                    case OP_CAST:		return TypeNames.CAST;
                    case OP_CONCAT:		return TypeNames.CONCAT;
		        	case OP_BETWEEN:	return TypeNames.BETWEEN;
		        	case OP_IN:			return TypeNames.IN;
		        	case OP_LIKE:		return TypeNames.LIKE;
		        	case OP_EXISTS:		return TypeNames.EXISTS;
		        	case OP_ABS:		return TypeNames.ABS;
		        	case OP_MOD:		return TypeNames.MOD;
		        	default:			break;
	        	}
	            break;
	        default:
	            break;
	    }
    return null;
	}


    public boolean hasJavaOperator() {
		return ((this.opMask & OP_MASK_JAVA) != 0);
	}

    
    public boolean hasOqlOperator() {
		return ((this.opMask & OP_MASK_OQL) != 0);
	}


    /**
     * Returns a string representation of the object. In general, the
     * <code>toString</code> method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p/>
     * The <code>toString</code> method for class <code>Object</code>
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `<code>@</code>', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    public String toString() {
        return oqlOpMap.get(this.opType);
    }


    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (! (o instanceof Operator)) {
            return false;
        }
        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }

        final Operator that = (Operator) o;

        return (this.opMask == that.getOpMask())
                && (this.opType == that.getOpType());
    }


    public int hashCode() {
        long longHash;
        longHash = this.opMask;
        longHash = 29 * longHash + this.opType;
        longHash ^= (longHash >>> 32);
        return (int) longHash;
    }
}

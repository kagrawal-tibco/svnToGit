package com.tibco.be.parser;



/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Jun 30, 2004
 * Time: 10:11:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuleError {
	protected String name;
    protected String message;
    protected Token failure;
    protected Token beginExtent;
    protected Token endExtent;
    protected int errorType;

    protected String columnId;
	protected String rowId;
    
    public static final int SYNTAX_TYPE = 0;
    public static final int SEMANTIC_TYPE = 1;
    public static final int WARNING_TYPE = 2;
    //for internal compiler errors not directly related to user input
    public static final int INTERNAL_TYPE = 3;
    
    public RuleError(String message, Token failure, Token beginExtent, Token endExtent, int type) {
        this.message = message;
        this.failure = failure;
        this.beginExtent = beginExtent;
        this.endExtent = endExtent;
        this.errorType = type;
    }
    
    public RuleError(String message, Token failure, int type) {
        this(message, failure, null, null, type);
    }
    
    public RuleError(String message, int type) {
        this(message, null, type);
    }

    public RuleError(RuleError err) {
        this(err.message, err.failure, err.beginExtent, err.endExtent, err.errorType);
    }
    
    
    
    /**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
     * An error message regarding the particular error
     */ 
    public String getMessage() {
        return message;
    }
    
    /**
     * Required.  The token where the error was detected
     */ 
    public Token getPointOfFailure() {
        return failure;
    }
    
    /**
     * Optional.  The first token in an erroneous region.
     * Returns getPointOfFailure() if a value wasn't specified.
     */ 
    public Token getBeginExtent() {
        if(beginExtent == null) return getPointOfFailure();
        return beginExtent;
    }
    
    /**
     * Optional.  The last token in an erroneous region.
     * Returns getPointOfFailure() if a value wasn't specified.
     */ 
    public Token getEndExtent() {
        if(endExtent == null) return getPointOfFailure();
        return endExtent;
    }
    
    public String toString() {
        StringBuffer sb = new StringBuffer();
        
        if (failure != null) {
        	sb.append("Error at token \"" + failure.image + "\" at line " + failure.beginLine + " column " + failure.beginColumn + "\n");
        }
        
        if (beginExtent != null && endExtent != null) {
            sb.append("Error region starts at line " + beginExtent.beginLine + " column " + beginExtent.beginColumn + "\n");
            sb.append("Error region ends at line " + endExtent.endLine + " column " + endExtent.endColumn + "\n");
        }
        
        sb.append(message.trim() + "\n");
        
        return sb.toString();
    }
    
    /**
     * @return one of the XXXX_TYPE constants from this class
     */ 
    public int getType(){
        return errorType;
    }
    
    public boolean isSyntaxError() {
        return errorType == SYNTAX_TYPE;
    }
    
    public boolean isSemanticError() {
        return errorType == SEMANTIC_TYPE;
    }
    
    public boolean isWarning() {
        return errorType == WARNING_TYPE;
    }
    
    public boolean isInternalError() {
        return errorType == INTERNAL_TYPE;
    }
    
    protected Object source = null;
    /**
     * set the source property
     * @see #getSource
     * @param source an object representing the rule that caused this error
     */ 
    public void setSource(Object source) {
        this.source = source;
    }
    /**
     * Providers of RuleErrors may set a source object to aid in associating errors with rules.
     * The default value is null
     * @return an object representing the rule that caused this error
     */ 
    public Object getSource() {
        return source;
    }
    
    public static RuleError makeSyntaxError(ParseException pe) {
        return makeSyntaxError(pe, CompileErrors.syntaxError(pe));
    }
    
    public static RuleError makeSyntaxError(ParseException pe, String message) {
        //first error token
        Token pointOfFailure = pe.currentToken.next;
        //last good token
        if(pointOfFailure == null || pointOfFailure.image == null || pointOfFailure.image.trim().length() <= 0) {
            pointOfFailure = pe.currentToken;
        }
        
        return new RuleError(message, pointOfFailure, SYNTAX_TYPE);
    }
    
    public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

}

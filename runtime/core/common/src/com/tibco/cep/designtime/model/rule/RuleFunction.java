package com.tibco.cep.designtime.model.rule;




/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 26, 2006
 * Time: 12:33:57 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RuleFunction extends Compilable {


    /**
     * Whether or not this RuleFunction can be used in the condition of a Rule or in a query.  In general,
     * RuleFunctions which modify other Entities should not be allowed in a Rule's condition.
     *
     * @return Validity of this RuleFunction.
     */
    Validity getValidity();


    /**
     * Returns a map of the arguments for this RuleFunction.
     *
     * @return A Symbols.
     */
    Symbols getArguments();


    /**
     * Returns the type for a given identifier.
     *
     * @param identifier the name of the identifier
     * @return A String that is either the name of an RDFType, or an Entity Path.  If it is an entity path, it begins with Folder.FOLDER_SEPARATOR_CHAR.
     */
    String getArgumentType(String identifier);


    /**
     * Returns the body text of the RuleFunction.
     *
     * @return
     */
    String getBody();

    String getReturnTypeWithExtension();


    boolean isVirtual();
    
    /**
     * Returns the line offset of the Scope block
     * @since 4.0
     * @return
     */
    public CodeBlock getScopeCodeBlock();
	
    /**
     * Returns the line offset of the Body block
     * @since 4.0
     * @return
     */
	public CodeBlock getBodyCodeBlock();
	
	/**
	 * Returns the source representation of the RuleFunction as shown in editor
	 * @return
	 */
	public String getSource();


    public static enum Validity {
        ACTION(0,"Action"),
        CONDITION(1,"Condition"),
        QUERY(2,"Query");
        
        private int value;
        private String name;
        public int getValue() { return value; }
        public String getName() { return name; }
        private Validity(int v,String name) { 
        	this.value = v; 
        	this.name = name; 
        }
    }

}
